package com.saint.poi.testpoi;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 此处报错是因为使用的EasyExcel依赖，如果直接使用poi最新依赖则没有问题。
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-10 22:00
 */
public class ExcelWriteTest {
    String PATH = "E:\\workplace\\ideaWorkplace\\Study\\liststudy\\poi";

    @Test
    public void testWrite07() throws IOException {
        //1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        //2. 创建一个工作表
        Sheet sheet = workbook.createSheet("saint明星表");
        // 3. 创建第一行, 默认从0开始，0就代表第一行
        Row row1 = sheet.createRow(0);
        // 4. 创建一个单元格（1,1）
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("6666");

        //第二行
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        //生成一个表（IO流）,03版本就是使用xlsx结尾。
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "\\07.xlsx");
        //输出
        workbook.write(fileOutputStream);

        fileOutputStream.close();
        System.out.println("文件生成完毕");
    }

    @Test
    public void testWrite03() throws IOException {
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //2. 创建一个工作表
        Sheet sheet = workbook.createSheet("saint明星表");
        // 3. 创建第一行, 默认从0开始，0就代表第一行
        Row row1 = sheet.createRow(0);
        // 4. 创建一个单元格（1,1）
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增观众");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("6666");

        //第二行
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        //生成一个表（IO流）,03版本就是使用xls结尾。
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "\\03.xls");
        //输出
        workbook.write(fileOutputStream);

        fileOutputStream.close();
        System.out.println("文件生成完毕");
    }

    @Test
    public void testWriteBigDataSuper07() throws IOException {
        long cur = System.currentTimeMillis();
        //1.创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        //2. 创建一个工作表
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 65537; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 8; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(j);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "\\bigDatas07.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        //清除临时文件
        ((SXSSFWorkbook) workbook).dispose();
        long end = System.currentTimeMillis();
        System.out.println("用时：" + (end - cur));
    }

    @Test
    public void ReadExcel03() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(PATH + "\\03.xls");
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        //2. 得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3. 得到行
        Row row1 = sheet.getRow(0);
        // 4. 得到列
        Cell cell = row1.getCell(1);
        // 读取值的时候，一定要注意类型！！！
        // getStringCellValue  字符串类型
        // getNumericCellValue 数字类型
        System.out.println(cell.getNumericCellValue());
        fileInputStream.close();
    }

   /* @Test
    public void testReadExcel07(FileInputStream fileInputStream) throws IOException {
        //1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        //获取标题行
        Row row1 = sheet.getRow(0);
        if (row1 != null) {
            //获取所有的列数
            int cellCount = row1.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = row1.getCell(cellNum);
                if (cell != null) {
                    CellType cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.println(cellValue + " | ");
                }
            }
        }

        // 获取表中的内容
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                //读取列
                int cellCount = row.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    //匹配列的数据类型
                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        String cellValue = "";

                        switch (cellType) {
                            //字符串
                            case STRING:
                                System.out.print("[String]");
                                cellValue = cell.getStringCellValue();
                                break;
                            //布尔类型
                            case BOOLEAN:
                                System.out.print("[Boolean]");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            //空
                            case BLANK:
                                System.out.print("[Blank]");
                                break;
                            //数字（日期、普通数字）
                            case NUMERIC:
                                System.out.print("[Numeric]");
                                //日期
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                } else {
                                    // 普通数字, 使用科学计数法，防止数字过长！
                                    cell.setCellType(CellType.STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            // 错误
                            case ERROR:
                                break;
                            default:
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }

        fileInputStream.close();
    }*/

   /* @Test
    public void testFormula(FileInputStream fileInputStream) throws IOException {
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        //获取某一单元格，单元格的内容为计算公式
        Row row1 = sheet.getRow(4);
        Cell cell = row1.getCell(0);

        //拿到计算公式 eval
        FormulaEvaluator hssfFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);

        //输出单元格的内容
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case FORMULA:
                String formula = cell.getCellFormula();
                System.out.println(formula);

                //计算
                CellValue evaluate = hssfFormulaEvaluator.evaluate(cell);
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
            default:
                break;
        }
        fileInputStream.close();
    }*/

}
