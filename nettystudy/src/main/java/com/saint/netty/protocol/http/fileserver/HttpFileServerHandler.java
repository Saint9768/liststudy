package com.saint.netty.protocol.http.fileserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Saint
 * @createTime 2020-06-13 14:08
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //对HTTP请求消息的解码结果进行判断
        if (!msg.decoderResult().isSuccess()) {
            //400 错误的请求
            sendError(ctx, BAD_REQUEST);
            return;
        }
        //对请求行中的方法进行判断，不是GET请求则返回405错误
        if (msg.method() != GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = msg.uri();
        //对请求URL进行包装
        final String path = sanitizeUri(uri);
        //如果构造的URI不合法，则返回HTTP 403错误。
        if (path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        //使用新组装的URI路径构造File对象
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            //如果文件不存或者是系统隐藏文件，返回404
            sendError(ctx, NOT_FOUND);
            return;
        }
        //如果文件是目录，则发送目录的连接给客户端浏览器
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                sendRedirect(ctx, uri + '/');
            }
            return;
        }
        if (!file.isFile()) {
            //403 资源不可用
            sendError(ctx, FORBIDDEN);
            return;
        }
        //使用随机文件读写类以只读的方式打开文件，如果打开失败，则返回404
        RandomAccessFile randomAccessFile = null;
        try {
            //以只读的方式打开文件
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException fnfe) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        //获取文件的长度
        long fileLength = randomAccessFile.length();
        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        //设置文本长度和类型
        setContentLength(response, fileLength);
        setContentTypeHeader(response, file);
        //判断消息是否是活着的，是的话在应答消息头中设置Connection为Keep-Alive
        if (isKeepAlive(msg)) {
            msg.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        //发送响应消息
        ctx.write(response);
        ChannelFuture sendFileFuture;
        //通过Netty的ChunkedFile对象直接将文件写入到发送缓冲区中
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192),
                ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("Transfer progress: " + progress);
                } else {
                    System.err.println("Transfer progress: " + progress + " /" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer complete.");
            }
        });
        //如果使用chunked编码，最后需要发送一个编码结束的空消息体，将LastHTTPContent的EMPTY_LAST_CONTENT
        //发送到缓冲区中，表示所有的消息体已经发送完毕。最后将消息flush到SocketChannel中发送给对方。
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果是非Keep-ALive的，最后一包消息发送完成之后，服务端要主动关闭连接。
        if (!isKeepAlive(msg)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static final Pattern INSECUE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * 净化URI
     *
     * @param uri
     * @return
     */
    private String sanitizeUri(String uri) {
        try {
            //使用UTF-8字符集，对URL进行解码
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException ex) {
                throw new Error();
            }
        }
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        //将硬编码的文件路径分隔符替换为本地操作系统的文件路径分隔符
        uri = uri.replace('/', File.separatorChar);
        //对新的URI做二次合法性校验。
        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator)
                || uri.startsWith(".") || uri.endsWith(".") || INSECUE_URI.matcher(uri).matches()) {
            return null;
        }
        //进行文件路径拼接
        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    /**
     * 输出文件列表
     *
     * @param ctx
     * @param dir
     */
    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        //设置消息头的类型
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        //构造响应消息体，采用HTML格式
        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();
        //\r\n表示回车
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><header><title>");
        buf.append(dirPath);
        buf.append(" 目录：");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append(" 目录：");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        //这里是添加一个..链接
        buf.append("<li>链接： <a href=\"../\">..</a></li>\r\n");
        //展示根目录下的所有文件和文件夹，同时用超链接来标识。
        for (File f : dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            buf.append("<li>链接：<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        //分配对应消息的缓冲对象，并将缓冲区中的响应消息存放到HTTP应答消息中，最后释放缓冲区。
        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        //将响应消息发送到缓冲区并刷新到SocketChannel中
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 转发
     *
     * @param ctx
     * @param newUri
     */
    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
        response.headers().set(LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString()
                        + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 设置响应消息头文件返回类型
     *
     * @param response
     * @param file
     */
    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
    }
}
