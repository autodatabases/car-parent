package com.emate.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExportExcelData {
	
	private static Logger logger = Logger.getLogger(ExportExcelData.class);
	
	private static int SHEET_LIMIT_ROWS = 50000;
	
	public static void export(HttpServletRequest request,
			HttpServletResponse response, List<Map<String, String>> dataList,
			String fileName,String[] titleColumn,String[] resultColumn,String sheetName){
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
		} catch (IOException ex) {
			logger.error(ex.toString());
			return;
		}
		if (null == dataList || dataList.isEmpty()) {
			try {
				response.setContentType("text/html;charset=GBK");
				response.resetBuffer();
				sos.write("<script language=\"JavaScript\">alert('所查询报表无数据，未能导出报表');</script>".getBytes("GBK"));
				sos.flush();
				sos.close();
				return;
			} catch (IOException ioex) {
				logger.error(ioex.toString());
			}
		}
		HSSFWorkbook wb = null;
		if(fileName.startsWith("查勘录入信息")){
			try {
				wb = createExcelFileTwo(dataList,titleColumn,resultColumn,sheetName);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.toString());
				try {
					response.setContentType("text/html;charset=GBK");
					response.resetBuffer();
					sos.write("<script language=\"JavaScript\">alert('所查询报表数据中，存在图片被删除现象,不能能导出报表');</script>".getBytes("GBK"));
					sos.flush();
					sos.close();
					return;
				} catch (IOException ioex) {
					logger.error(ioex.toString());
				}
			}
		}else{
			wb = createExcelFile(dataList,titleColumn,resultColumn,sheetName);
		}
		try {
			//response.setc
			fileName=java.net.URLEncoder.encode(fileName, "utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + ".xls\"");
			response.resetBuffer();
			wb.write(sos);
			sos.flush();
		} catch (IOException ex) {
			logger.error(ex.toString());
		}
	}

	private static HSSFWorkbook createExcelFile(
			List<Map<String, String>> dataList,String[] titleColumn,String[] resultColumn,String sheetName) {
		
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		HSSFFont font = null;
		cellStyle = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// 首行表头行
		int shortIndex = 0;


		// 内容行
		HSSFCellStyle style1 = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("宋体");
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setFont(font);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map = dataList.get(i);
			//Set<String> cols = map.keySet();
			shortIndex = 0;
			if(i%SHEET_LIMIT_ROWS == 0){
				sheet = wb.createSheet(sheetName+i/SHEET_LIMIT_ROWS);
				row = sheet.createRow(i%SHEET_LIMIT_ROWS);
				for(String s : titleColumn){
					cell = row.createCell(shortIndex,HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(s);	
					shortIndex++;
				}
				shortIndex = 0;
			}
			row = sheet.createRow(i%SHEET_LIMIT_ROWS + 1);
			for (String s : resultColumn) {
				cell = row.createCell(shortIndex);
				cell.setCellStyle(style1);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(String.valueOf(map.get(s)).equals("null")||String.valueOf(map.get(s))==""?"":String.valueOf(map.get(s)));
				shortIndex++;
			}
		}
		return wb;
	}
	
	private static HSSFWorkbook createExcelFileTwo(
			List<Map<String, String>> dataList,String[] titleColumn,String[] resultColumn,String sheetName) throws IOException {
		
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		HSSFFont font = null;
		cellStyle = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cellStyle.setFont(font);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// 首行表头行
		int shortIndex = 0;


		// 内容行
		HSSFCellStyle style1 = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("宋体");
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style1.setFont(font);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map = dataList.get(i);
			//Set<String> cols = map.keySet();
			shortIndex = 0;
			if(i%SHEET_LIMIT_ROWS == 0){
				sheet = wb.createSheet(sheetName+i/SHEET_LIMIT_ROWS);
				sheet.setDefaultColumnWidth(20);
				row = sheet.createRow(i%SHEET_LIMIT_ROWS);
				row.setHeightInPoints(50);
				for(String s : titleColumn){
					cell = row.createCell(shortIndex,HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(s);	
					shortIndex++;
				}
				shortIndex = 0;
			}
			row = sheet.createRow(i%SHEET_LIMIT_ROWS + 1);
			row.setHeightInPoints(50);
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			for (String s : resultColumn) {
				cell = row.createCell(shortIndex);
				cell.setCellStyle(style1);
				if("picture".equals(s)){
					String pic = map.get(s);
					if(StringUtils.isEmpty(pic)){
						cell.setCellValue("");
					}else{
						BufferedImage img = ImageIO.read(new File(pic));
	
						String formatName = "jpg";
						if (pic.lastIndexOf('.') > 0) {
							formatName = pic.substring(pic.lastIndexOf('.')+1, pic.length());
				        }
						ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
						ImageIO.write(img, formatName, byteArrayOut);
						HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,0,0,(short)shortIndex,(i%SHEET_LIMIT_ROWS + 1),(short)(shortIndex+1),(i%SHEET_LIMIT_ROWS + 2));
						anchor.setAnchorType(0);
						if("jpg".equals(formatName)){
							patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
						}else if("png".equals(formatName)){
							patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
						}else if("jpeg".equals(formatName)){
							patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
						}
					}
				}else{
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(String.valueOf(map.get(s)).equals("null")||String.valueOf(map.get(s))==""?"":String.valueOf(map.get(s)));
				}
				shortIndex++;
			}
		}
		return wb;
	}
	
	
	public static HSSFWorkbook createExcelTemplate(List<String> titles){
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		HSSFFont font = null;
		cellStyle = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("微软雅黑");

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//cellStyle.setFont(font);
		//cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// 首行表头行
		row = sheet.createRow(0);
		int shortIndex = 0;


		// 内容行
		HSSFCellStyle style1 = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("微软雅黑");
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//style1.setFont(font);
		//style1.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		//style1.setBorderRight(HSSFCellStyle.BORDER_THICK);
		//style1.setBorderTop(HSSFCellStyle.BORDER_THICK);
		//style1.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		
		for(String s : titles){
			cell = row.createCell(shortIndex);
			cell.setCellStyle(cellStyle);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(s);	
			shortIndex++;
		}
		
		return wb;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		HSSFWorkbook wb = createExcelTemplate(Arrays.asList("第1列","第2列","第3列"));
		FileOutputStream fos = new FileOutputStream("C:\\Users\\likk\\Desktop\\export.xls");
		try {
			wb.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HSSFWorkbook createExcelFile1(
			List<Map<String, String>> dataList,String[] titleColumn,String[] resultColumn,String sheetName) {
		
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		HSSFFont font = null;
		cellStyle = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// 首行表头行
		int shortIndex = 0;


		// 内容行
		HSSFCellStyle style1 = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("宋体");
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setFont(font);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map = dataList.get(i);
			//Set<String> cols = map.keySet();
			shortIndex = 0;
			if(i%SHEET_LIMIT_ROWS == 0){
				sheet = wb.createSheet(sheetName+i/SHEET_LIMIT_ROWS);
				row = sheet.createRow(i%SHEET_LIMIT_ROWS);
				for(String s : titleColumn){
					cell = row.createCell(shortIndex,HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(s);	
					shortIndex++;
				}
				shortIndex = 0;
			}
			row = sheet.createRow(i%SHEET_LIMIT_ROWS + 1);
			for (String s : resultColumn) {
				cell = row.createCell(shortIndex);
				cell.setCellStyle(style1);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(String.valueOf(map.get(s)).equals("null")||String.valueOf(map.get(s))==""?"":String.valueOf(map.get(s)));
				shortIndex++;
			}
		}
		return wb;
	}
}
