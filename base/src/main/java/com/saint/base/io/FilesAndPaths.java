package com.saint.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-24 7:34
 */
public class FilesAndPaths {
    public static void main(String[] args) throws IOException {
        // Paths.get方法获取文件路径
        Path path = Paths.get("D:\\wind\\", "develop");
        //Path path = Paths.get("D:\\wind\\", "develop", "22条军规.txt");
        BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime fileTime = basicFileAttributes.creationTime();
        System.out.println(fileTime);
        try {
//            File file = new File("D:\\test\\test.txt");
//            Path path1 = file.toPath();
//            System.out.println(path1);
            // 获取文件的所有信息
//            byte[] bytes = Files.readAllBytes(path);
//            String content = new String(bytes, Charset.defaultCharset());
//            System.out.println(content);
            // 2. 获取文件的子节点
//            Stream<Path> list = Files.list(path);
//            list.forEach(path1 -> System.out.println(path1));
            // 3. 处理目录中的所有子目录
//            Stream<Path> walk = Files.walk(path);
//            walk.forEach(allSonPath -> System.out.println(allSonPath));
            // 4. 目录流，对遍历过程进行更加细粒度的控制
            DirectoryStream<Path> paths = Files.newDirectoryStream(path, "**.txt");
            paths.forEach(path1 -> System.out.println(path1));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
