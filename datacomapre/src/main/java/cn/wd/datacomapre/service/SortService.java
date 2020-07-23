package cn.wd.datacomapre.service;

import cn.wd.datacomapre.util.MergeSortedTxt;
import cn.wd.datacomapre.util.TxtSingleSort;
import cn.wd.datacomapre.util.TxtSplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzhou.Saint
 * @version 1.0
 * @date 2020/7/20 9:42
 */
@Slf4j
@Component
public class SortService {

    /**
     * 将文件拆分、单排、多路归并排序。
     * String filePath = "D:\\xzhou.saint\\0088\\compareTest\\two.txt";
     *
     * @param filePath 需要拆分的文件路径名
     */
    public String sort(String filePath, int fileCount) {

        if (!filePath.endsWith(".txt")) {
            return "请输入正确的文件格式";
        } else if (fileCount < 2) {
            return "拆分的文件数量必须大于1！！";
        } else {
            //开始时间
            long startTime = System.currentTimeMillis();

            int dotIndex = filePath.lastIndexOf(".txt");
            String filePathPre = filePath.substring(0, dotIndex);
            try {
                List<String> files = TxtSplitUtil.splitFile(filePath, fileCount);
                log.error("文件拆分完成!");

                //这里可以添加线程池进行使用
                for (String file : files) {
                    //对小文件进行排序，这里可以使用多线程
                    TxtSingleSort.SortedSingleTxt(file);
                }
                log.error("小文件排序完成!");
                long centerTime = System.currentTimeMillis();
                log.error("当前用时 ： " + (centerTime - startTime) + "ms");
                //文件集合，多路归并排序使用
                ArrayList<File> fileList = new ArrayList<>();
                for (int i = 1; i <= fileCount; i++) {
                    String fileName = filePathPre + i + "_sorted.txt";
                    fileList.add(new File(fileName));
                }
                //对文件进行多路归并排序
                MergeSortedTxt.merge(fileList);
                log.error("多路归并排序完成！");
                //结束时间
                long endTime = System.currentTimeMillis();
                log.error("归并排序耗时： " + (endTime - centerTime) + "ms");
                log.error("共耗时： " + (endTime - startTime) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "sort success, 排序后生成的文件与排序前的文件在同一目录";
        }
    }
}
