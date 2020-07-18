package com.saint.base.big.file.sorted;

import java.io.*;
import java.util.ArrayList;

/**
 * 合并排序后的多个TXT文件
 *
 * @author Saint
 * @version 1.0
 * @date 2020/7/15 16:56
 */
public class MergeSortedTxt {


    public static void merge(ArrayList<File> list) {
        int fileSize = list.size();
        if (fileSize == 1) {
            return;
        }
        //输出文件
        String fileName = list.get(0).getPath();
        int beginIndex = fileName.lastIndexOf("\\");
        int endIndex = fileName.lastIndexOf("_sorted.txt");
        String outFilePath = fileName.substring(0, beginIndex) + fileName.substring(beginIndex, endIndex - 1) + "_sorted.txt";
        File outFile = new File(outFilePath);
        //输出文件
        BufferedWriter out = null;

        //进行多路归并的叶子节点数组
        ArrayList<String> leaves = new ArrayList<>(fileSize);
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            //每个归并段生成一个输入流
            ArrayList<BufferedReader> inputList = new ArrayList<>();
            for (int i = 0; i < fileSize; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(list.get(i))));
                inputList.add(i, reader);
            }
            //初始化叶子数组
            String data = "";
            for (int i = 0; i < inputList.size(); i++) {
                data = inputList.get(i).readLine();
                leaves.add(data);
            }
            //初始化败者树
            FailedTree<Integer> failedTree = new FailedTree(leaves);
            //输出胜者
            Integer s = failedTree.getWinner();
            out.write(failedTree.getLeaf(s) + "");
            out.newLine();
            out.flush();
            while (inputList.size() > 0) {
                //新增叶子节点
                String newLeaf = inputList.get(s).readLine();
                //如果文件读取完成，关闭读取流，删除叶子几点
                if (newLeaf == null || newLeaf.equals("")) {
                    //当一个文件读取完成之后，关闭文件输入流、移除出List集合。
                    inputList.get(s).close();
                    int remove = s;
                    inputList.remove(remove);
                    failedTree.del(s);
                } else {
                    failedTree.add(newLeaf, s);
                }

                s = failedTree.getWinner();
                if (s == null) {
                    break;
                }

                //输出胜利者
                out.write(failedTree.getLeaf(s) + "");
                out.newLine();
                out.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /*public static void main(String[] args) {
        ArrayList<File> list = new ArrayList<>();
        list.add(new File("D:/xzhou.saint/0088/sort/0088大数据1_sorted.txt"));
        list.add(new File("D:/xzhou.saint/0088/sort/0088大数据2_sorted.txt"));
        list.add(new File("D:/xzhou.saint/0088/sort/0088大数据3_sorted.txt"));
        long startTime = System.currentTimeMillis();
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time + "    开始处理");
        merge(list);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
    }*/
}

