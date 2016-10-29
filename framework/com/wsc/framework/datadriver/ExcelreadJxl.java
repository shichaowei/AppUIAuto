package com.wsc.framework.datadriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.testng.Assert;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ExcelreadJxl {
	private int      currentRowNo = 0;
    private int      columnNum    = 0;
    private String[] columnnName;
	public int readxls(String excelname, String sheetname,String modifycolname) {
    	//System.out.println(sheetname);
    	Workbook book         = null;
    	Sheet    sheet        = null;
		int resultcolnum=0;
		int colnum=0;
        try {
            int dotNum = excelname.indexOf(".");
            if (dotNum > 0) {
                excelname = excelname.substring(excelname.lastIndexOf(".") + 1,
                        excelname.length());
            }
            String path = "data/" + excelname + ".xls";
            System.out.println(path);
            
            WorkbookSettings setting = new WorkbookSettings();
            setting.setSuppressWarnings(true);
            book=Workbook.getWorkbook(new File(path), setting);
            
            sheet = book.getSheet(sheetname);
            //sheet = book.getSheet(0);
            //modifyrownum = sheet.getRows();
            Cell[] cell = sheet.getRow(0);
            columnNum = cell.length;
            columnnName = new String[cell.length];

            for (int i = 0; i < cell.length; i++) {
                columnnName[i] = cell[i].getContents().toString();
            }
            
        	for(String columnn:columnnName){
        		if(columnn.equals(modifycolname)){
        	  		resultcolnum=colnum;
            	}
        		colnum++;
        	}
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("unable to read Excel data");
        }
        finally {
        	
        	try {
        		
				book.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
        	return resultcolnum;
		}
    }

}
