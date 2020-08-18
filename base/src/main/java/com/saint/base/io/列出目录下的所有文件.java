package com.saint.base.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Saint
 * @createTime 2020-02-25 7:11
 */
public class 列出目录下的所有文件 {

    /**
     * 只获取文件夹下的文件，不管别的文件夹。
     * @param args
     */
    /*public static void main(String[] args) {
        File f = new File("D:/images");
        for(File temp : f.listFiles()){
            if(temp.isFile()){
                System.out.println(temp.getName());
            }
        }
    }*/

    /**
     * 展开文件夹的目录
     *
     * @param args
     */
    /*public static void main(String[] args) {
        showDirectory(new File("D:/images"));
    }

    public static void showDirectory(File f) {
        _walkDirectory(f, 0);
    }

    private static void _walkDirectory(File f, int level) {
        if(f.isDirectory()) {
            for(File temp : f.listFiles()) {
                _walkDirectory(temp, level + 1);
            }
        }
        else {
            for(int i = 0; i < level - 1; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
        }
    }*/
    public static void main(String[] args) throws IOException {
        Path initPath = Paths.get("D:/images");
        Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }

        });
    }
}
