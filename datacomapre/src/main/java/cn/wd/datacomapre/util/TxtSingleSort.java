package cn.wd.datacomapre.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 对单个TXT文本进行排序
 *
 * @author xzhou.Saint
 * @version 1.0
 * @date 2020/7/15 15:53
 */
public class TxtSingleSort {

    /**
     * 对单个文件中的内容按行排序
     *
     * @param filePath 文件路径
     * @return 返回一个文件名
     */
    public static File SortedSingleTxt(String filePath) {
        int index = filePath.lastIndexOf("/");
        int endIndex = filePath.lastIndexOf(".");
        //新的文件前缀
        String fileNamePre = filePath.substring(index + 1, endIndex);
        //新的文件路径前缀
        String filePathPre = filePath.substring(0, index + 1);
        String outFilePath = filePathPre + fileNamePre + "_sorted.txt";

        File fileIn = new File(filePath);
        File fileOut = new File(outFilePath);
        //排序使用
        ArrayList<String> arrayList = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            //从文件中读
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));
            //输出到文件
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));
            //给ArrayList设置容量为1百万 + 1千
            arrayList = new ArrayList<>(1001000);
            String line;
            while ((line = in.readLine()) != null) {
                //去掉拆分文件时，子文件最上方的空格
                if (line.trim().length() > 0) {
                    arrayList.add(line);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(arrayList);
        int i = 0;
        int size = arrayList.size();
        //将排序后的内容输出到文件中
        for (String s : arrayList) {
            try {
                out.write(s);
                i++;
                //添加最后一个元素之后不再添加空行
                if (i != size) {
                    out.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //删除未排序的旧文件
        fileIn.delete();

        return fileOut;
    }

}
