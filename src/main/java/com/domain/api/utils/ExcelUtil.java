package com.domain.api.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 86183 on 2021/5/29.
 */
public class ExcelUtil {

    private static XSSFSheet excelSheet;
    private static XSSFWorkbook excelBook;
    private static XSSFCell cell;
    private static XSSFRow row;

    public ExcelUtil(){

    }
    /**
     * 读取excel
     * @param path
     * @param sheetName
     */
    public static void setExcelFile(String path,String sheetName){
        FileInputStream excelFile;
        try {
            excelFile = new FileInputStream(path);
            excelBook = new XSSFWorkbook(excelFile);
            excelSheet = excelBook.getSheet(sheetName);
        }catch (Exception e){
            Log.error("读取sheet出错");
        }
    }

    /**
     * 获取excel数据
     * @param rownum
     * @param celnum
     * @return
     */
    public static String getCellData(int rownum,int celnum){
        cell = excelSheet.getRow(rownum).getCell(celnum);
        return cell.getCellTypeEnum()== CellType.STRING?cell.getStringCellValue().toString()
                :String.valueOf(Math.round(cell.getNumericCellValue()));
    }

    /**
     * 写入excel数据
     * @param rownum
     * @param celnum
     * @return
     */
    public static void setCellData(int rownum,int celnum,String result,String path){
        row = excelSheet.getRow(rownum);
        cell = row.getCell(celnum,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            cell = row.createCell(celnum);
            cell.setCellValue(result);
        }else{
            cell.setCellValue(result);
        }
        try {
            FileOutputStream out = new FileOutputStream(path);
            excelBook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            Log.error(e.toString());
        }
    }

    /**
     * 静态数据
     * @param sheetName
     * @param sheetName
     * @return
     * @throws IOException
     */
    public static Object[][] getExcelData(Object obj,String sheetName) {
        Object[][] result = null;
        Workbook workbook = null;
        try{
            File files = new File(obj.getClass().getResource(GlobalSettings.getProperty("dir.path")).getPath());
            File file = new File(files.getCanonicalPath());
            FileInputStream inputStream = new FileInputStream(file);
            String fileExtensName = files.getCanonicalPath().substring(files.getCanonicalPath().indexOf("."));
            if (fileExtensName.equals(".xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }else if (fileExtensName.equals(".xls")){
                workbook = new HSSFWorkbook(inputStream);
            }
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
            List<Object[]> records = new ArrayList<Object[]>();
            for (int i = 0; i <rowCount+1 ; i++) {
                Row row = sheet.getRow(i);
                String[] fields = new String[row.getLastCellNum() - 2];
                if (row.getCell(row.getLastCellNum()- 2).getStringCellValue().equals("Y")) {
                    for (int j = 0; j < row.getLastCellNum()-2; j++) {
                        fields[j] = row.getCell(j).getCellTypeEnum()== CellType.STRING?row.getCell(j).getStringCellValue().toString()
                                :String.valueOf(Math.round(row.getCell(j).getNumericCellValue()));
                    }
                    records.add(fields);
                }
            }
            result = new Object[records.size()][];
            List list = new ArrayList();
            for (int i = 0; i < records.size(); i++) {
                result[i] = records.get(i);
            }
        } catch (NullPointerException e) {
            Log.error("没有excelfile文件或者" + obj.getClass().getSimpleName() + ".xlsx未找到");
        }catch (FileNotFoundException e) {
            Log.error(obj.getClass().getSimpleName() + ".xlsx未找到");
        }catch (IOException e) {
            Log.error("获取数据失败");
        }

        return  result;

    }

    /**
     * 获取列数
     * @return
     */
    public static int getLastColumnNum(){
        return excelSheet.getRow(0).getLastCellNum()-1;
    }
}
