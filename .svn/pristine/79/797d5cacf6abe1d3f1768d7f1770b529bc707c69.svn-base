package com.emate.shop.business.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.CountermanGoodService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.business.model.CountermanCaiDotExample;
import com.emate.shop.business.model.CountermanExample;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.business.model.CountermanGoodExample;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.CountermanGoodExample.Criteria;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanCaiDotMapper;
import com.emate.shop.mapper.CountermanGoodMapper;
import com.emate.shop.mapper.CountermanMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 业务员商品信息
 * @author dong
 *
 */
@Service
public class CountermanGoodServiceImpl implements CountermanGoodService{

	@Resource
	private CountermanGoodMapper countermanGoodMapper;
	
	@Resource
	private CountermanMapper countermanMapper;
	
	@Resource
	private CountermanCaiDotMapper countermanCaiDotMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;

	@Read
	@Override
	public DatasetList<CountermanGood> adminCountermanGoodList(Integer pageNo,Integer pageSize,CountermanGood countermanGood,Long userId){
		//查看登录人
		String pinYin = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
		countermanCaiDotEx.createCriteria().andPinYinEqualTo(pinYin);
		List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
		
		CountermanGoodExample ex = new CountermanGoodExample();
		ex.setOrderByClause(CountermanGoodExample.CREATE_TIME_DESC);
		Criteria criteria = ex.createCriteria();
		if(!countermanCaiDots.isEmpty()&&Objects.nonNull(countermanCaiDots.get(0))){
			criteria.andCaiDotIdEqualTo(countermanCaiDots.get(0).getId());
		}
		if(StringUtils.isNotEmpty(countermanGood.getGoodName())){
			criteria.andGoodNameLike("%"+countermanGood.getGoodName()+"%");
		}
		if(Objects.nonNull(countermanGood.getGoodStatus())){
			criteria.andGoodStatusEqualTo(countermanGood.getGoodStatus());
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanGoodMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanGood> countermanGoods = countermanGoodMapper.selectByExample(ex);
/*		for(CountermanGood countermangood :countermanGoods){
			if("黄埔_萝岗".equals(countermangood.getCaiDotName())){
				countermangood.setCaiDotName("萝岗");
			}
			if("黄埔_南岗".equals(countermangood.getCaiDotName())){
				countermangood.setCaiDotName("南岗");
			}
		}*/
		return DatasetBuilder.fromDataList(countermanGoods, page.createPageInfo());
	}

	@Read
	@Override
	public DatasetSimple<CountermanGood> queryCountermanGoodById(String id) {
		
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("商品id不能为空");
		}
		CountermanGood countermanGood = countermanGoodMapper.selectByPrimaryKey(Long.parseLong(id));
		if(countermanGood == null){
			throw new BusinessException("该商品不存在！");
		}

/*		if("黄埔_萝岗".equals(countermanGood.getCaiDotName())){
			countermanGood.setCaiDotName("萝岗");
		}
		if("黄埔_南岗".equals(countermanGood.getCaiDotName())){
			countermanGood.setCaiDotName("南岗");
		}*/
		return DatasetBuilder.fromDataSimple(countermanGood);
	}
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addOrUpdateCountermanGood(CountermanGood countermanGood,Long userId) {
		//查看登录人
		String pinYin = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
		countermanCaiDotEx.createCriteria().andPinYinEqualTo(pinYin);
		List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
		
		//校验筛选条件
		if(Objects.isNull(countermanGood.getCaiDotId())){
			if(!countermanCaiDots.isEmpty()&&Objects.nonNull(countermanCaiDots.get(0))){
				countermanGood.setCaiDotId(countermanCaiDots.get(0).getId());
			}else{
				throw new BusinessException("请选择财险网点!");
			}
		}
		CountermanCaiDot countermanCaiDot = countermanCaiDotMapper.selectByPrimaryKey(countermanGood.getCaiDotId());
		if(Objects.isNull(countermanCaiDot)){
			throw new BusinessException("该网点不存在,请重新选择财险网点!");
		}
		//保存财险网点名称
		countermanGood.setCaiDotName(countermanCaiDot.getDotName());
		//获取商品价格
		BigDecimal goodPrice = countermanGood.getGoodPrice();
		if(goodPrice.intValue()>1000000){
			throw new BusinessException("商品金额过大(0~999999.99元之间),请重新填写");
		}
		if(countermanGood.getGoodNumber().intValue()>100000000){
			throw new BusinessException("商品库存过大(0~100000000之间),请重新填写");
		}
		if(Objects.isNull(goodPrice)){
			throw new BusinessException("商品价格不能为空");
		}
		if(goodPrice.compareTo(BigDecimal.ZERO)!=1){
			throw new BusinessException("商品价格不能小于零或等于零");
		}
		String goodImg = countermanGood.getGoodImg();
		if(StringUtils.isEmpty(goodImg)){
			throw new BusinessException("商品图片没上传,请上传商品~~");
		}
		//计算商品积分
		//BigDecimal goodScore = goodPrice.multiply(countermanCaiDot.getRatio()).setScale(0, BigDecimal.ROUND_UP);
		//countermanGood.setGoodScore(goodScore);
		BigDecimal goodScore = countermanGood.getGoodScore();
		if(goodScore.intValue()>100000000||goodScore.intValue()<0){
			throw new BusinessException("商品积分过大(0~100000000之间),请重新填写");
		}
		CountermanGoodExample ex = new CountermanGoodExample();
		ex.createCriteria().andGoodNameEqualTo(countermanGood.getGoodName())
		.andCaiDotIdEqualTo(countermanGood.getCaiDotId());
		List<CountermanGood> countermanGoods = countermanGoodMapper.selectByExample(ex);
		if(countermanGood.getId()==null){
			if(!countermanGoods.isEmpty()){
				throw new BusinessException("该商品名称已被使用");
			}
			countermanGood.setCreateTime(new Date());
			countermanGood.setUpdateTime(new Date());
			if(countermanGoodMapper.insertSelective(countermanGood)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}else{
			if(!countermanGoods.isEmpty()){
				if(!countermanGoods.get(0).getId().equals(countermanGood.getId())){
					throw new BusinessException("该商品名称已被使用");
				}
			}
			countermanGood.setUpdateTime(new Date());
			if(countermanGoodMapper.updateByPrimaryKeySelective(countermanGood)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}
		throw new BusinessException("添加或更新商品信息失败！");
	}

	@Override
	public DatasetList<CountermanGood> h5CountermanGoodList(Long userId,Integer pageNo, Integer pageSize, String belongArea) {
		//判断业务员代码是否为空
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		//判断是否绑定业务员
		if(countermans.isEmpty()){
			throw new BusinessException("请绑定业务员~~");
		}
		//判断业务员状态是否在职
		if(Counterman.STATUS_0 !=countermans.get(0).getStatus()){
			throw new BusinessException("您已不在职,请联系客服");
		}
		//查询业务员有没有所属的财险网点
		if(Objects.isNull(countermans.get(0).getCaiDotId())){
			throw new BusinessException("未查到您所在的财险网点");
		};
		//根据网点id和商品状态查询商品
		CountermanGoodExample ex = new CountermanGoodExample();
		Criteria criteria = ex.createCriteria();
		criteria.andCaiDotIdEqualTo(countermans.get(0).getCaiDotId());
		criteria.andGoodStatusEqualTo(CountermanGood.GOOD_STATUS_0);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanGoodMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		return DatasetBuilder.fromDataList(countermanGoodMapper.selectByExample(ex), page.createPageInfo());
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String, Object>> batchInsertGood(List<Map<String, Object>> params, Long userId) {
		if(params == null || params.isEmpty()){
			return DatasetBuilder.fromMessageSimple("参数为空!");
		}
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(userId);
		CountermanCaiDotExample caiDotEx= new CountermanCaiDotExample();
		caiDotEx.createCriteria().andPinYinEqualTo(systemUser.getUserName());
		List<CountermanCaiDot> caiDots = countermanCaiDotMapper.selectByExample(caiDotEx);
		final AtomicInteger successCount = new AtomicInteger(0);
		params.stream().forEach(map -> {
			CountermanGood countermanGood = new CountermanGood();
			Arrays.stream(CountermanGood.class.getDeclaredMethods())
			.filter(m -> m.getName().startsWith("set"))
			.filter(m -> m.getName().indexOf("createTime")==-1)
			.filter(m -> m.getName().indexOf("updateTime")==-1)
			.filter(m -> m.getName().indexOf("id")==-1)
			.filter(m -> m.getName().indexOf("caiDotId")==-1)
			.filter(m -> m.getName().indexOf("status")==-1)
			.filter(method -> Objects.equals(1,
                    method.getParameterCount()))
			.filter(method -> Modifier.isPublic(method.getModifiers()))
	         .forEach(method -> {
	        	 String key = method.getName().substring(3);
                   try {
	               		int i = 0;
	               		int first = 0;
	               		int second = 0;
	               		for(char r : key.toCharArray()){
	               			if(i!=0 && (int)r >=65 && (int)r <=90){
	               				if(first==0){
	               					first =i;
	               				}else{
	               					second = i;
	               				}
	               			}
	               			i++;
	               		}
	               		if(second>first){
	               			key = key.substring(0, first)+"_"+key.substring(first,second)+"_"+key.substring(second);
	               		}else if(first!=0){
	               			key = key.substring(0, first)+"_"+key.substring(first);
	               		}
	               			
	               		key = key.toLowerCase();
                       //使用set方法注入代理对象
	               		if("good_name".equals(key) 
	               				|| "good_img".equals(key) 
	               				|| "remark".equals(key)){
	               			if(map.get(key)!=null){
	               				map.put(key, String.valueOf(map.get(key)).trim());
	               			}
	               		}
	               		if("good_price".equals(key) || "good_price".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
	               		if("guo_price".equals(key) || "guo_price".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
	               		if("good_score".equals(key) || "good_score".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
	               		if("good_number".equals(key) || "good_number".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
                       method.invoke(countermanGood,map.get(key));
                   } catch (IllegalAccessException
                           | IllegalArgumentException
                           | InvocationTargetException e) {
                	   System.out.println(key + ":" + map.get(key));
                       e.printStackTrace();
                       throw new BusinessException("业务员商品数据格式不正确!错误字段："+key + ":" + map.get(key));
                   }
            });
			if(caiDots.isEmpty()&&StringUtils.isEmpty(countermanGood.getCaiDotName())){
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入失败，没有所属的财险网点:"+countermanGood.getGoodName());
			}
			CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
			countermanCaiDotEx.createCriteria()
			.andDotNameEqualTo(countermanGood.getCaiDotName());
			List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
			if(countermanCaiDots.isEmpty()||countermanCaiDots.size()>1){
				if(!caiDots.isEmpty()){
					countermanGood.setCaiDotId(caiDots.get(0).getId());
					countermanGood.setCaiDotName(caiDots.get(0).getDotName());
				}else{
					Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
					throw new BusinessException("导入失败，查不到所属的财险网点或所属网点不止一个:"+countermanGood.getGoodName());
				}
			}else{
				//赋值财险网点id
				countermanGood.setCaiDotId(countermanCaiDots.get(0).getId());
			}

			//校验参数
			if(StringUtils.isEmpty(countermanGood.getGoodName())){
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入失败，没有商品名称");
			}
			if(countermanGood.getGoodName().length()>28){
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入失败，商品名称不能超过28个字符");
			}
			if(StringUtils.isEmpty(countermanGood.getGoodImg())){
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入失败，没有上传图片");
			}
			
			//查重
			countermanGood.setGoodName(countermanGood.getGoodName().replace("\n", ""));
			countermanGood.setGoodName(countermanGood.getGoodName().replace("\r\n", ""));
			CountermanGoodExample countermanGoodEx = new CountermanGoodExample();
			countermanGoodEx.createCriteria()
			.andGoodNameEqualTo(countermanGood.getGoodName())
			.andCaiDotIdEqualTo(countermanGood.getCaiDotId());
			List<CountermanGood> countermanGoods = countermanGoodMapper.selectByExample(countermanGoodEx);
			if(!countermanGoods.isEmpty()){
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入失败，业务员商品已存在:"+countermanGood.getGoodName());
			}
			//更新业务员
			countermanGood.setGoodStatus(CountermanGood.GOOD_STATUS_0);;
			countermanGood.setUpdateTime(new Date());
			countermanGood.setCreateTime(new Date());;	
			if(countermanGoodMapper.insertSelective(countermanGood)==1){
				successCount.incrementAndGet();
				Log4jHelper.getLogger().info("新增业务员商品信息成功: " + JacksonHelper.toPrettyJSON(countermanGood));
			}else{
				Log4jHelper.getLogger().info("新增业务员商品信息失败: " + JacksonHelper.toPrettyJSON(countermanGood));
				throw new BusinessException("导入业务员失败，插入数据库异常:"+countermanGood.getGoodName());
			}
			
		});
		//组织返回结果
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", successCount.intValue());
		result.put("fail", params.size()-successCount.intValue());
		return DatasetBuilder.fromDataSimple(result);
	}
}
