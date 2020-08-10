package com.saint.poi.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-11 7:00
 */
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;

    /**
     * 或略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
