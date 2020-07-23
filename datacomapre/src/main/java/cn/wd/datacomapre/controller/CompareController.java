package cn.wd.datacomapre.controller;

import cn.wd.datacomapre.service.CompareService;
import cn.wd.datacomapre.service.SortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzhou.Saint
 * @version 1.0
 * @date 2020/7/20 9:55
 */
@Slf4j
@RestController
@RequestMapping("/v1/compare")
public class CompareController {

    @Autowired
    private SortService sortService;
    @Autowired
    private CompareService compareService;

    /**
     * 将指定文件拆分为fileCount个小文件进行排序
     *
     * @param filePath  文件路径名
     * @param fileCount 拆分成的小文件数量
     * @return
     */
    @RequestMapping("/sortTxt")
    public String sort(String filePath, int fileCount) {
        return sortService.sort(filePath, fileCount);
    }

    /**
     * 对两个有序文件进行比对。
     * 文件后缀必须为_sorted(直接使用sort(String filePath)方法中最后生成的有序文件)。
     * 最终将在originalFileName目录下生成3个文件，分别为original_insert.txt、original_update.txt、original_delete.txt。
     *
     * @param originalFileName stone库排序后的文件
     * @param secondFileName   二级库排序后的文件
     * @return
     */
    @RequestMapping("/compareTxt")
    public String compare(String originalFileName, String secondFileName) {
        return compareService.service(originalFileName, secondFileName);
    }
}