package com.emate.tools;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emate.shop.common.Log4jHelper;

//http://image.tuhu.cn/Images/Logo/biyadi.png

public class ExcelUtil{
	public static String file = null;
	@SuppressWarnings("resource")
	public static Workbook createWorkBook(String fileName)
			throws IOException {
		InputStream in = new FileInputStream(fileName);
		if (fileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(in);
		}else if (fileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(in);
		}
		return null;
	}
	
	public static Workbook createWorkBook(InputStream in,String fileName)
			throws IOException {
		if (fileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(in);
		}else if (fileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(in);
		}
		return null;
	}
	
	/**
	 * 从excel文件中获取参数map列表
	 * 
	 * @param book
	 * 			excel文件对象
	 * @param paramNames
	 * 			参数名称数组
	 * @param rowStart
	 * 			数据开始行
	 * @param rowEnd
	 * 			数据结束行
	 * @return
	 */
	@SuppressWarnings( "deprecation")
	public static List<Map<String, Object>> getRecodeMapByExcel(Workbook book, int sheetIndex,String[] paramNames, int rowStart, int rowEnd){
		if(book == null){
			return null;
		}
		
		Sheet sheet = book.getSheetAt(sheetIndex);
		rowStart = Math.max(rowStart, sheet.getFirstRowNum());
		rowEnd = Math.min(rowEnd, sheet.getLastRowNum());
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int rowNum=rowStart; rowNum<=rowEnd; rowNum++){
			Row r = sheet.getRow(rowNum);
			if(r == null){
				continue;
			}
			Map<String, Object> rowMap = new HashMap<String, Object>();
			for(int col=0; col<paramNames.length; col++){
				if("".equals(paramNames[col])){
					continue;
				}
				Cell c = r.getCell(col);
				if(c == null){
					rowMap.put(paramNames[col], null);
				}else{
					switch(c.getCellType()){
						case Cell.CELL_TYPE_STRING:
							rowMap.put(paramNames[col], c.getRichStringCellValue().getString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(c)){
								rowMap.put(paramNames[col], HSSFDateUtil.getJavaDate(c.getNumericCellValue()));
							}else{
								rowMap.put(paramNames[col], new BigDecimal(c.getNumericCellValue()));
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							rowMap.put(paramNames[col], String.valueOf(c.getBooleanCellValue()));
							break;
						case Cell.CELL_TYPE_FORMULA:
							rowMap.put(paramNames[col], c.getCellFormula());
							break;
						default:
							rowMap.put(paramNames[col], null);
					}
				}
			}
			dataList.add(rowMap);
		}
		return dataList;
	}
	/**
	 * 从excel文件中获取参数map列表
	 * @param book
	 * excel文件对象
	 * @param sheetIndex
	 * @param paramNames
	 * 参数名称数组
	 * @param rowStart
	 * 数据开始行
	 * @param rowEnd
	 * 数据结束行
	 * @param excelType
	 * Excel类型是xls还是xlsx
	 * @param pathOne
	 * 图片存储地址
	 * @param pathTwo
	 * 数据库保存地址
	 * @param imgpath
	 * 图片存储位置
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( "deprecation")
	public static List<Map<String, Object>> getRecodeMapByExcelTwo(Workbook book, int sheetIndex,
			String[] paramNames, int rowStart, int rowEnd,String excelType,String pathOne,String pathTwo,String imgpath) throws Exception{
		//保存图片并传递图片地址
		Map<Integer, String> map = getImgMap(book,sheetIndex,pathOne,pathTwo,excelType,imgpath);
		//获取数据
		Sheet sheet = book.getSheetAt(sheetIndex);
		rowStart = Math.max(rowStart, sheet.getFirstRowNum());
		rowEnd = Math.min(rowEnd, sheet.getLastRowNum());
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int rowNum=rowStart; rowNum<=rowEnd; rowNum++){
			Row r = sheet.getRow(rowNum);
			if(r == null){
				continue;
			}
			Map<String, Object> rowMap = new HashMap<String, Object>();
			for(int col=0; col<paramNames.length; col++){
				if("".equals(paramNames[col])){
					continue;
				}
				Cell c = r.getCell(col);
				if(c == null){
					rowMap.put(paramNames[col], null);
				}else{
					switch(c.getCellType()){
						case Cell.CELL_TYPE_STRING:
							rowMap.put(paramNames[col], c.getRichStringCellValue().getString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(c)){
								rowMap.put(paramNames[col], HSSFDateUtil.getJavaDate(c.getNumericCellValue()));
							}else{
								rowMap.put(paramNames[col], new BigDecimal(c.getNumericCellValue()));
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							rowMap.put(paramNames[col], String.valueOf(c.getBooleanCellValue()));
							break;
						case Cell.CELL_TYPE_FORMULA:
							rowMap.put(paramNames[col], c.getCellFormula());
							break;
						default:
							rowMap.put(paramNames[col], null);
					}
				}
			}
			rowMap.put("good_img", map.get(rowNum));
			dataList.add(rowMap);
		}
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<Integer,String> getImgMap(Workbook book,Integer sheetIndex,String pathOne,String pathTwo,String excelType,String imgpath) throws Exception{
		//判断Excel是否为空
		if(book == null){
			return null;
		}
		Map<Integer, String> map = new HashMap<Integer,String>();
		if("0".equals(excelType)){
			//获取 Excel中的图片
			List<HSSFPictureData> pictures = (List<HSSFPictureData>) book.getAllPictures();
			//获取sheet
			Sheet sheetAt = book.getSheetAt(sheetIndex);
			HSSFSheet sheet = (HSSFSheet) sheetAt;
			//保存Excel中的图片
			for(HSSFShape shape :sheet.getDrawingPatriarch().getChildren()){
				HSSFClientAnchor anchor = (HSSFClientAnchor)shape.getAnchor();
				if(shape instanceof HSSFPicture){
					//该图片所处的行数
					int row = anchor.getRow1();
					Log4jHelper.getLogger().info("该业务员商品图片所处的行数....."+row);
					//该行的图片是哪一个
					HSSFPicture pic = (HSSFPicture) shape;
					int pictureIndex = pic.getPictureIndex()-1;
					HSSFPictureData picData = pictures.get(pictureIndex);
					//组织保存图片的地址
					String imagePath = pathOne + File.separator + "resources"
			        		+ File.separator + "imgs" + File.separator
			        		+ "upload"+ File.separator + imgpath;
					
			        File imagePathFile = new File(imagePath);
			        if (!imagePathFile.exists()) {
			            imagePathFile.mkdirs();
			        }
					//图片类型
					String ext= picData.suggestFileExtension();
					//保存地址
					String path = imagePath + File.separator
				                + UUID.randomUUID().toString().replace("-", "");
					if(ext.equals("jpeg")){
						Log4jHelper.getLogger().info("该业务员商品图片类型....jpg类型");
				        path =path+".jpg";
				        while(new File(path).exists()){
				    	    path = imagePath + File.separator
					               + UUID.randomUUID().toString().replace("-", "")+".jpg";
				        }
					}else if(ext.equals("png")){
						Log4jHelper.getLogger().info("该业务员商品图片类型....png类型");
				        path = path+".png";
				        while(new File(path).exists()){
				        	path = imagePath + File.separator
					              + UUID.randomUUID().toString().replace("-", "")+".png";
				       }
					}else{
						return null;
					}
					byte[] data = picData.getData();
					FileOutputStream out = new FileOutputStream(path);
					out.write(data);
					out.close();
				    String realPath = path.replace(pathOne,pathTwo)
				    		.replace(File.separator, "/");
				    map.put(row, realPath);
				}
			}
		}else if("1".equals(excelType)){
			Sheet sheetAt = book.getSheetAt(sheetIndex);
			XSSFSheet sheet = (XSSFSheet) sheetAt;
			//保存Excel中的图片
			for(XSSFShape shape :sheet.getDrawingPatriarch().getShapes()){
				XSSFClientAnchor anchor = (XSSFClientAnchor)shape.getAnchor();
				if(shape instanceof XSSFPicture){
					//该图片所处的行数
					int row = anchor.getRow1();
					Log4jHelper.getLogger().info("该业务员商品图片所处的行数....."+row);
					//该行的图片是哪一个
					XSSFPicture pic =(XSSFPicture) shape;
					XSSFPictureData picData = pic.getPictureData();
					byte[] data = picData.getData();
					//组织保存图片的地址
					String imagePath = pathOne + File.separator + "resources"
			        		+ File.separator + "imgs" + File.separator
			        		+ "upload"+ File.separator + imgpath;
					
			        File imagePathFile = new File(imagePath);
			        if (!imagePathFile.exists()) {
			            imagePathFile.mkdirs();
			        }
					//图片类型
					String ext= picData.suggestFileExtension();
					//保存地址
					String path = imagePath + File.separator
				                + UUID.randomUUID().toString().replace("-", "");
					if(ext.equals("jpeg")){
						Log4jHelper.getLogger().info("该业务员商品图片类型....jpg类型");
				        path =path+".jpg";
				        while(new File(path).exists()){
				    	    path = imagePath + File.separator
					               + UUID.randomUUID().toString().replace("-", "")+".jpg";
				        }
					}else if(ext.equals("png")){
						Log4jHelper.getLogger().info("该业务员商品图片类型....png类型");
				        path = path+".png";
				        while(new File(path).exists()){
				        	path = imagePath + File.separator
					              + UUID.randomUUID().toString().replace("-", "")+".png";
				       }
					}else{
						return null;
					}
					FileOutputStream out = new FileOutputStream(path);
					out.write(data);
					out.close();
				    String realPath = path.replace(pathOne,pathTwo)
				    		.replace(File.separator, "/");
				    map.put(row, realPath);
				}
			}
		}else{
			return null;
		}
		return map;
	}
}