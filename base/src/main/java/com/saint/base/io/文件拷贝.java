package com.saint.base.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Saint
 * @createTime 2020-02-25 6:42
 */
public class 文件拷贝 {

    /**
     * 工具类中的方法都是静态方式访问的，因此将构造器私有不允许创建对象。
     */
    private 文件拷贝() {
        throw new AssertionError();
    }

    /**
     * 阻塞性IO
     *
     * @param source
     * @param target
     */
    public static void fileCopy(String source, String target) {
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                //记录数据的字节长度
                int bytesToRead;
                //read方法从输入流中读取数据，并装载进buffer中
                while ((bytesToRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 非阻塞性IO
     *
     * @param source
     * @param target
     */
    public static void fileCopyNIO(String source, String target) {
        try (FileInputStream in = new FileInputStream(source)) {
            try (FileOutputStream out = new FileOutputStream(target)) {
                //文件输入管道
                FileChannel inChannel = in.getChannel();
                //文件输出管道
                FileChannel outChannel = out.getChannel();
                //创建一个大小为4096个字节的缓冲区。
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (inChannel.read(buffer) != -1) {
                    //用在write前面，开启缓冲区。
                    buffer.flip();
                    //文件拷贝
                    outChannel.write(buffer);
                    //清空缓存区
                    buffer.clear();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
