package com.taihe.springbootsparksubmit.util;

import lombok.extern.apachecommons.CommonsLog;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/4/14
 */
@CommonsLog
public class ExcelUtils {


    public void readExcelFile(InputStream inputStream, String fileName) {

        /**
         * 这个inputStream文件可以来源于本地文件的流，
         *  也可以来源与上传上来的文件的流，也就是MultipartFile的流，
         *  使用getInputStream()方法进行获取。
         */
        /**
         * 然后再读取文件的时候，应该excel文件的后缀名在不同的版本中对应的解析类不一样
         * 要对fileName进行后缀的解析
         */
        Workbook workbook = null;
        try {
            //判断什么类型文件
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (Exception e) {
            log.error("Unknown File Type");
        }
        if (workbook == null) {
            return;
        } else {
            //获取所有的工作表的的数量
            int numOfSheet = workbook.getNumberOfSheets();
            log.info("文件包含几个sheet" + numOfSheet);
            //获取第一个sheet页
            Sheet firstSheet = workbook.getSheetAt(0);
            firstSheet.getRow(0).getFirstCellNum();
            //遍历表
            for (int i = 0; i < numOfSheet; i++) {
                //获取一个sheet也就是一个工作本。
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                //获取一个sheet有多少Row
                int lastRowNum = sheet.getLastRowNum();
                if (lastRowNum == 0) {
                    continue;
                }
                Row row;
                for (int j = 1; j <= lastRowNum; j++) {
                    row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    //获取一个Row有多少Cell
                    short lastCellNum = row.getFirstCellNum();
                    for (int k = 0; k <= lastCellNum; k++) {
                        if (row.getCell(k) == null) {
                            continue;
                        }
                        String res = row.getCell(k).getStringCellValue().trim();
                        log.info(res);
                    }

                }
            }
        }

    }
}
