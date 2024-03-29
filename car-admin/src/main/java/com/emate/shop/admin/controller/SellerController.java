package com.emate.shop.admin.controller;

import java.io.InputStream;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.emate.shop.business.api.AutoInfoService;
import com.emate.shop.business.api.SellerInfoService;
import com.emate.shop.business.api.SellerService;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.business.model.SellerPolicy;
import com.emate.shop.business.model.SellerReport;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExcelUtil;

/**
 * 
 * @author likk
 *
 */
@Controller
@RequestMapping("seller")
public class SellerController implements AuthUtil{
    

	private SellerService sellerService;
	
	private SellerInfoService sellerInfoService;
	
	private AutoInfoService autoInfoService;
	
	@RemoteService
	public void setAutoInfoService(AutoInfoService autoInfoService) {
		this.autoInfoService = autoInfoService;
	}

	@RemoteService
	public void setSellerService(SellerService sellerService){
		this.sellerService = sellerService;
	}
	
	@RemoteService
	public void setSellerInfoService(SellerInfoService sellerInfoService){
		this.sellerInfoService = sellerInfoService;
	}
	
	/**
	 * 查询所有商家
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("querySellerList")
    @AuthAction
    public DatasetList<Seller> querySellerList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,Seller seller) {
    	return sellerService.adminSellerList(Integer.parseInt(pageNo),Integer.parseInt(pageSize),seller);
    }
    
	/**
	 *	导入保单用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "importUserInfo")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> importUserInfo(HttpServletRequest request) throws Exception {
	        try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		        List<MultipartFile> fileList = multipartRequest.getFiles("file_upload");  
		        for (MultipartFile mf : fileList) {  
		            if(!mf.isEmpty()){
		            	if (mf.getOriginalFilename().toLowerCase().endsWith("xls")
		            			|| mf.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
		            		Log4jHelper.getLogger().info("开始导入用户数据....."+mf.getOriginalFilename());
			        		return this.sellerService.importUserInfo(readInfoFromExcel(mf.getInputStream(),mf.getOriginalFilename()));
		        		}else{
		        			throw new BusinessException("导入用户失败!，导入文件格式不对！");
		        		}
		            	
		            }  
		        }  
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusinessException("导入用户异常!"+e.getMessage());
	        }
	        return DatasetBuilder.fromMessageSimple("本次导入文件为空！");
	}
	
	private static List<Map<String, Object>> readInfoFromExcel(InputStream in,String fileName) throws Exception{
		Workbook book = ExcelUtil.createWorkBook(in,fileName);
		String[] paramNames = new String[]{"real_name","address","phone","che_pai","che_jia","engine_code","auto_brand","car_factory","auto_type","price","auto_line",
				"engine_disp","product_year","source","bao_dan","sign_time","start_time","end_time","payment_time","order_price","discount","use_type","agent","seller","baoyang_times","penqi_times"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,2,2000);
		if(data.size()>100){
			throw new BusinessException("一次导入数据不能超过100条！");
		}
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("che_pai")==null && t.get("che_jia")==null){
				throw new BusinessException("车牌为空！保单号为："+t.get("bao_dan"));
				//it.remove();
				//continue;
			}
			if(t.get("engine_disp") !=null && StringUtils.isNotEmpty(String.valueOf(t.get("engine_disp"))) && !"null".equals(String.valueOf(t.get("engine_disp")))){
				if(t.get("engine_disp") instanceof CharSequence){
				
				}else{
					t.put("engine_disp", new java.text.DecimalFormat("#.0").format(t.get("engine_disp")));
				}
				
			}
			if(t.get("order_price")==null|| "".equals(t.get("order_price"))){
				throw new BusinessException("保费价格为空！");
			}
			if(t.get("price")==null|| "".equals(t.get("price"))){
				throw new BusinessException("车辆价格为空！");
			}
			if(t.get("order_price") instanceof Number){
				t.put("order_price", new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("order_price"))));
			}else{
				if(t.get("order_price") instanceof CharSequence && ((String)t.get("order_price")).matches("\\d+")){
					t.put("order_price", new BigDecimal((String)t.get("order_price")));
				}else{
					throw new BusinessException("保单价格格式必须为整数！");
				}
			}
			
			if(t.get("price") instanceof Number){
				t.put("price", new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("price"))));
			}else{
				if(t.get("price") instanceof CharSequence && ((String)t.get("price")).matches("\\d+")){
					t.put("price", new BigDecimal((String)t.get("price")));
				}else{
					throw new BusinessException("车辆价格格式必须为整数！");
				}
				
			}
			
			if(t.get("discount") instanceof Number){
				t.put("discount", new BigDecimal(new java.text.DecimalFormat("#.00").format(t.get("discount"))).multiply(new BigDecimal(100)));
			}else{
				if(t.get("discount") instanceof CharSequence && ((String)t.get("discount")).matches("\\d+")){
					t.put("discount", new BigDecimal((String)t.get("discount")));
				}else{
					t.put("discount", new BigDecimal(0));
				}
				
			}
			
			t.put("phone", String.valueOf(t.get("phone")));
			/*for(String s:t.keySet()){
				t.put(s, String.valueOf(t.get(s)).replaceAll("\n", "").replaceAll("\r", ""));
				if(t.get(s)=="null"){
					t.put(s, null);
				}
			}*/
			SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
			String[] dates = new String[]{"payment_time","sign_time","start_time","end_time"};
			for(String key:dates){
				if(t.get(key) instanceof CharSequence){
					if(((String)t.get(key)).split("/").length!=3){
						throw new BusinessException(key+"日期格式不对,应该为 2018/6/16");
					}
					t.put(key, sm.parse((String)t.get(key)));
				}else if(t.get(key)==null||!(t.get(key) instanceof Date)){
					throw new BusinessException(key+"日期格式不对，或日期为空");
				}
			}
		}
		Log4jHelper.getLogger().info(data);
		return data;
	}
	
	/**
	 *	赠送服务次数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendService")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> sendService(HttpServletRequest request,@Regex("[0,1,2,3,4,5,6]") String serviceType, @Regex("(\\+|\\-)?\\d+") String serviceValue) throws Exception {
	        try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		        List<MultipartFile> fileList = multipartRequest.getFiles("file");  
		        for (MultipartFile mf : fileList) {
		            if(!mf.isEmpty()){
		            	if (mf.getOriginalFilename().toLowerCase().endsWith("xls")
		            			|| mf.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
		            		Workbook book = ExcelUtil.createWorkBook(mf.getInputStream(),mf.getOriginalFilename());
		            		String[] paramNames = new String[]{"che_pai","remark"};
		            		List<Map<String, Object>> chePais = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		            		Iterator<Map<String, Object>> it = chePais.iterator();
		            		while(it.hasNext()){
		            			Map<String, Object> next = it.next();
		            			if(next.get("che_pai")==null){
		            				it.remove();
		            				continue;
		            			}
		            			String chePai = String.valueOf(next.get("che_pai"));
		            			chePai = chePai.trim();
		            			next.put("che_pai", chePai);
		            		}
		            		Log4jHelper.getLogger().info(chePais);
		            		Log4jHelper.getLogger().info("开始赠送服务次数数据....."+mf.getOriginalFilename());
			        		return this.sellerService.sendService(chePais, Integer.valueOf(serviceType), Integer.valueOf(serviceValue), this.getUserId(request, CAR_ADMIN_TOKEN));
		        		}else{
		        			throw new BusinessException("赠送服务失败!，导入文件格式不对！");
		        		}
		            }  
		        }  
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusinessException("赠送服务异常!"+e.getMessage());
	        }
	        return DatasetBuilder.fromMessageSimple("本次导入文件为空！");
	}
	/**
	 * 审核商家,修改商家状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSellerStatus", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateSellerStatus(HttpServletRequest request,
			@Required @Regex("\\d+") String status,@Required @Regex("\\d+") String sellerId) throws Exception {
		Seller seller = new Seller();
		seller.setId(Long.parseLong(sellerId));
		seller.setAuditStatus(Integer.parseInt(status));
		return sellerInfoService.updateSellerStatus(seller);
	}
	
	/**
	 * 设置商家信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setSellerInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> setSellerInfo(HttpServletRequest request,
			HttpServletResponse response,SellerInfo sellerInfo,Seller seller) throws Exception {
		String penqimoney = request.getParameter("penqimoney");
		if(StringUtils.isEmpty(penqimoney) || !penqimoney.matches("\\d+")){
			throw new BusinessException("喷漆金额不对！");
		}
		if(seller.getSellerGrade().equals(Seller.SELLER_GRADE_2)&&((seller.getProperties()&Seller.PROPERTIES_16)!=0)){
			if(StringUtils.isNotEmpty(seller.getCarBrand())){
				DatasetSimple<Boolean> brand = autoInfoService.queryAutoposeByBrand(seller.getCarBrand());
				if(!brand.getData()){
					throw new BusinessException("该汽车品牌不存在,请重新填写~");
				}
			}else{
				throw new BusinessException("若存在喷漆且是4s店,请填写汽车品牌~");
			}
		}
		String baoyangmoney = request.getParameter("baoyangmoney");
		if(StringUtils.isEmpty(baoyangmoney) || !baoyangmoney.matches("\\d+")){
			throw new BusinessException("保养金额不对！");
		}
		String baoyangcheck = request.getParameter("baoyangcheck");
		String penqicheck = request.getParameter("penqicheck");
		SellerPolicy sellerPolicy = new SellerPolicy();
		sellerPolicy.setBaoyangMoney(new Integer(baoyangmoney));
		sellerPolicy.setPenqiMoney(new Integer(penqimoney));
		sellerPolicy.setBaoyangCheck(Boolean.valueOf(baoyangcheck));
		sellerPolicy.setPenqiCheck(Boolean.valueOf(penqicheck));
		seller.setSellerPolicy(JacksonHelper.toJSON(sellerPolicy));
		boolean updateSeller = false;
		if(Objects.nonNull(seller.getId())){
			sellerInfo.setSellerId(seller.getId());
			updateSeller = sellerInfoService.updateSeller(seller).isSuccess();
		}else{
			DatasetSimple<String> addResult = sellerInfoService.addSeller(seller);
			updateSeller = addResult.isSuccess();
			if(updateSeller){
				sellerInfo.setSellerId(Long.parseLong(addResult.getData()));
			}else{
				return DatasetBuilder.fromMessageSimple(addResult.getMessage());
			}
		}
		if(updateSeller){
			return sellerInfoService.addSellerInfo(sellerInfo);
		}
		return DatasetBuilder.fromMessageSimple("更新商家信息失败！");
	}
	
	
	/**
	 * 查询商家信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSellerInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<SellerInfo> getSellerInfo(HttpServletRequest request,
			@Required @Regex("\\d+")String sellerId) throws Exception {
		DatasetSimple<SellerInfo> result = sellerInfoService.querySellerInfo(Long.parseLong(sellerId));
		result.putDataset("seller", sellerService.querySellerById(sellerId));
		return result;
	}
	
	
	/**
	 * 删除商家
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSeller", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<String> deleteSeller(HttpServletRequest request,
			@Required @Regex("\\d+")String sellerId) throws Exception {
		return  sellerInfoService.deleteSeller(Long.parseLong(sellerId));
	}
	
	/**
	 * 查询商家结算信息
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param date
	 * @param sellerName
	 * @return
	 */
    @ResponseBody
    @RequestMapping("querySellerReportList")
    @AuthAction
    public DatasetList<Map<String,Object>> querySellerReportList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,String date,String sellerName) {
    	return sellerService.adminSellerReportList(Integer.parseInt(pageNo),Integer.parseInt(pageSize),date,sellerName);
    }
    
    /**
	 * 审核商家,修改商家状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSellerReportStatus", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateSellerReportStatus(HttpServletRequest request,
			@Required @Regex("[3]") String status,@Required @Regex("\\d+") String reportId) throws Exception {
		SellerReport sellerReport = new SellerReport();
		sellerReport.setId(Long.parseLong(reportId));
		sellerReport.setStatus(SellerReport.STATUS_3);
		return sellerService.updateSellerReport(sellerReport);
	}
}
