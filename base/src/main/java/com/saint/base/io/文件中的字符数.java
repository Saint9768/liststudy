package com.saint.base.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 统计一个字符串在文件中出现的次数
 *
 * @author Saint
 * @createTime 2020-02-25 6:57
 */
public class 文件中的字符数 {

    /**
     * 工具类中的方法都是静态方式访问的，因此将构造器私有不允许创建对象。
     */
    private 文件中的字符数() {
        throw new AssertionError();
    }

    public static int countWordInFile(String filename, String word) {
        int counter = 0;
        try (FileReader fr = new FileReader(filename)) {
            //读取文本从一个二进制输入流中
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                //使line等于从缓冲读取器中取出的一行
                while ((line = br.readLine()) != null) {
                    int index = -1;
                    //当从缓冲读取器中取出的一行的长度大于Word的长度，并且含有word时
                    while (line.length() > word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
