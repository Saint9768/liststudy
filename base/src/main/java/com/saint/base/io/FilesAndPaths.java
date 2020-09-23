package com.saint.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-24 7:34
 */
public class FilesAndPaths {
    public static void main(String[] args) {
        // Paths.get方法获取文件路径
        Path path = Paths.get("D:\\", "wind", "22条军规.txt");
        try {
            File file = new File("D:\\test\\test.txt");
            Path path1 = file.toPath();
            System.out.println(path1);
            // 获取文件的所有信息
            byte[] bytes = Files.readAllBytes(path);
            String content = new String(bytes, Charset.defaultCharset());
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
