package com.saint.base.big.file.sorted;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 对文件进行拆分
 *
 * @author Saint
 * @version 1.0
 * @date 2020/7/15 14:44
 */
public class TxtSplit {

    public static List<String> splitFile(String filePath, int fileCount) throws IOException {

        //返回的集合
        List<String> list = new ArrayList<>();

        int index = filePath.lastIndexOf("\\");
        int endIndex = filePath.lastIndexOf(".");
        //新的文件前缀
        String fileNamePre = filePath.substring(index + 1, endIndex);
        //新的文件路径前缀
        String filePathPre = filePath.substring(0, index + 1);

        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        //平均值
        long average = fileSize / fileCount;
        //缓存块大小，200B
        long bufferSize = 200;
        // 申请一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + ""));
        //子文件开始位置
        long startPosition = 0;
        //子文件结束位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                // 读取数据
                int read = inputChannel.read(byteBuffer, endPosition);
                readW:
                while (read != -1) {
                    //切换读模式
                    byteBuffer.flip();
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        //判断\n\r
                        if (b == 10 || b == 13) {
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    //重置缓存块指针
                    byteBuffer.clear();
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            } else {
                //最后一个文件直接指向文件末尾
                endPosition = fileSize;
            }
            String fileName = filePathPre + fileNamePre + (i + 1) + ".txt";
            FileOutputStream fos = new FileOutputStream(fileName);
            FileChannel outputChannel = fos.getChannel();
            //通道传输文件数据
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);
            //将文件添加到返回的集合中
            list.add(fileName);
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();

        return list;
    }



    /*public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        splitFile("D:\\xzhou.saint\\0088\\sort\\0088大数据.txt",3);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");

    }*/
}
