package com.wsc.framework.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Apache POI工具类
 */
public class POIUtil {

	public POIUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取单元格对象
	 * @param sheet Sheet对象
	 * @param row 行号
	 * @param column 列号
	 * @return 单元格对象。如果单元格为编辑过则为null。
	 */
	public static Cell getCell(Sheet sheet,int row,int column){
		Row rowobj = sheet.getRow(row);
		if(rowobj == null){
			return null;
		}
		return rowobj.getCell(column);
	}
	
	 /**    
	  * 获取单元格的值    
	  * @param cell 单元格
	  * @return 单元格的值，如果为空或null返回空
	  */     
	public static String getCellValue(Cell cell){  
		if(cell == null) return "";
		switch(cell.getCellType()){
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_FORMULA:
				return cell.getCellFormula();
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_BLANK:
				return "";
		}
		return "";
     }
	
	/**
	 * 根据行号和列号获取单元格的值
	 * @param sheet Sheet对象
	 * @param row 行号
	 * @param column 列号
	 * @return 单元格的值
	 */
	public static String getCellValueByIndex(Sheet sheet,int row,int column){
		return getCellValue(getCell(sheet, row, column));
	}
     
     /**   
      * 判断指定的单元格是否是合并单元格   
      * @param sheet Sheet对象
      * @param row 行下标   
      * @param column 列下标   
      * @return  如果是合并单元格返回True，否则返回False
      */
     public static boolean isMergedRegion(Sheet sheet,int row ,int column) {   
    	 return (getIndexOfMergedRegion(sheet, row, column)==null)?false:true;
     }
     
     /**
      * 获取合并单元格的起始结束行号和列号
      * @param sheet Sheet对象
      * @param row 行号
      * @param column 列号
      * @return 合并单元格的起始结束行号和列号，依次为：起始行号、结束行号、起始列号、结束列号。如果不是合并单元格返回NULL。
      */
     public static int[] getIndexOfMergedRegion(Sheet sheet,int row,int column){
    	 int[] index = new int[4];
    	 int sheetMergeCount = sheet.getNumMergedRegions();
    	 for (int i = 0; i < sheetMergeCount; i++) {
    		 CellRangeAddress range = sheet.getMergedRegion(i);
    		 int firstColumn = range.getFirstColumn();
             int lastColumn = range.getLastColumn();
             int firstRow = range.getFirstRow();
             int lastRow = range.getLastRow();
             if(row >= firstRow && row <= lastRow){
            	 if(column >= firstColumn && column <= lastColumn){
            		 index[0] = firstRow;
            		 index[1] = lastRow;
            		 index[2] = firstColumn;
            		 index[3] = lastColumn;
            		 return index;
            	 }
             }
    	 }
    	 return null;
     }

     /**
      * 获取合并单元格的合并行数
      * @param sheet Sheet对象
      * @param row 行号，合并单元格的任一行号
      * @param column 列号，合并单元格的任一列号
      * @return 合并单元格的合并行数
      */
     public static int getRowMergedNum(Sheet sheet,int row,int column){
    	 int[] index = getIndexOfMergedRegion(sheet, row, column);
    	 return index[1] - index[0] + 1;
     }
     
}
