package com.emate.shop.business.service;

//import java.math.BigDecimal;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
//import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.CountermanScoreService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanExample;
//import com.emate.shop.business.model.CountermanScore;
//import com.emate.shop.business.model.CountermanScoreExample;
//import com.emate.shop.business.model.CountermanScoreExample.Criteria;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.business.model.CountermanScoreRecordExample;
import com.emate.shop.datasource.Read;
//import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanMapper;
import com.emate.shop.mapper.CountermanScoreMapper;
import com.emate.shop.mapper.CountermanScoreRecordMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
//import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 业务员积分
 * @author dong
 *
 */
@Service
public class CountermanScoreServiceImpl implements CountermanScoreService{

	@Resource
	private CountermanScoreMapper countermanScoreMapper;
	
	@Resource
	private CountermanScoreRecordMapper countermanScoreRecordMapper;
	
	@Resource
	private CountermanMapper countermanMapper;

/*	@Read
	@Override
	public DatasetList<CountermanScore> adminCountermanScoreList(Integer pageNo, Integer pageSize,
			CountermanScore countermanScore) {
		CountermanScoreExample ex = new CountermanScoreExample();
		Criteria criteria = ex.createCriteria();
		
		if(StringUtils.isNotEmpty(countermanScore.getCountermanCode())){
			criteria.andCountermanCodeLike("%"+countermanScore.getCountermanCode()+"%");
		}
		if(StringUtils.isNotEmpty(countermanScore.getCountermanName())){
			criteria.andCountermanNameLike("%"+countermanScore.getCountermanName()+"%");
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanScoreMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(ex);
		for(CountermanScore countermanscore:countermanScores){
			if("黄埔_萝岗".equals(countermanscore.getBelongArea())){
				countermanscore.setBelongArea("萝岗");
			}
			if("黄埔_南岗".equals(countermanscore.getBelongArea())){
				countermanscore.setBelongArea("南岗");
			}
		}
		return DatasetBuilder.fromDataList(countermanScores, page.createPageInfo());
	}*/
	
/*	@Read
	@Override
	public DatasetSimple<CountermanScore> queryCountermanScoreById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("积分记录id不能为空");
		}
		CountermanScore countermanScore = countermanScoreMapper.selectByPrimaryKey(Long.parseLong(id));
		if(countermanScore == null){
			throw new BusinessException("该条记录不存在！");
		}
		if("黄埔_萝岗".equals(countermanScore.getBelongArea())){
			countermanScore.setBelongArea("萝岗");
		}
		if("黄埔_南岗".equals(countermanScore.getBelongArea())){
			countermanScore.setBelongArea("南岗");
		}
		return DatasetBuilder.fromDataSimple(countermanScore);
	}*/

	/*@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addOrUpdateCountermanScore(CountermanScore countermanScore,String remark,Long userId) {
		if(countermanScore.getId()==null){
			throw new BusinessException("传递参数id为空");
		}
		CountermanScore countermanScores = countermanScoreMapper.selectByPrimaryKey(countermanScore.getId());
		if(Objects.isNull(countermanScores)){
			throw new BusinessException("该条记录不存在");
		}
		if(countermanScore.getScore().intValue()>1000000000){
			throw new BusinessException("积分超过10亿,请重新修改");
		}		
		if(countermanScore.getScore().intValue()<0){
			throw new BusinessException("积分不能小于0,请重新修改");
		}
		BigDecimal subtractScore = countermanScore.getScore().subtract(countermanScores.getScore());
		if(subtractScore.intValue() != 0){
			countermanScores.setScore(countermanScore.getScore());
			countermanScores.setUpdateTime(new Date());
			if(countermanScoreMapper.updateByPrimaryKeySelective(countermanScores)!=1){
				throw new BusinessException("修改积分失败");
			}
			CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
			countermanScoreRecord.setCountermanCode(countermanScores.getCountermanCode());
			countermanScoreRecord.setCountermanId(countermanScores.getCountermanId());
			countermanScoreRecord.setBelongArea(countermanScores.getBelongArea());
			countermanScoreRecord.setScore(subtractScore);
			countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_1);
			countermanScoreRecord.setRecordRemark(remark);
			countermanScoreRecord.setOperater(userId);
			countermanScoreRecord.setCreateTime(new Date());
			countermanScoreRecord.setUpdateTime(new Date());
			if(countermanScoreRecordMapper.insertSelective(countermanScoreRecord)!=1){
				throw new BusinessException("修改积分记录失败");
			}
		}
		return DatasetBuilder.fromDataSimple(true);
	}
*/
	@Override
	@Read
	public DatasetList<CountermanScoreRecord> h5CountermanScoreRecordList(Integer pageNo, Integer pageSize,
			Long userId) {
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> counterman = countermanMapper.selectByExample(countermanEx);
		if(counterman.isEmpty()){
			throw new BusinessException("请重新绑定~~");
		}
		if(Counterman.STATUS_1.equals(counterman.get(0).getStatus())){
			throw new BusinessException("您已不在职,请联系客服");
		}
		String countermanCode = counterman.get(0).getCountermanCode();
		CountermanScoreRecordExample recordEx = new CountermanScoreRecordExample();
		recordEx.createCriteria().andCountermanCodeEqualTo(countermanCode);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanScoreRecordMapper.countByExample(recordEx));
		recordEx.setLimitStart(page.getStartRow());
		recordEx.setLimitEnd(page.getSize());
		recordEx.setOrderByClause(CountermanScoreRecordExample.CREATE_TIME_DESC);
		countermanScoreRecordMapper.selectByExample(recordEx);
		return DatasetBuilder.fromDataList(countermanScoreRecordMapper.selectByExample(recordEx),page.createPageInfo());
	}

/*	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String, String>> delCountermanScoreQuartz() {
		//查询countermanScore表的所有数据
		CountermanScoreExample countermanScoreEx = new CountermanScoreExample();
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreEx);
		final AtomicInteger success = new AtomicInteger(0);
		final AtomicInteger fail = new AtomicInteger(0);
		//遍历每条记录,修改积分为0
		if(!countermanScores.isEmpty()){
			for(CountermanScore countermanScore:countermanScores){
				countermanScore.setScore(new BigDecimal("0"));
				countermanScore.setChangScore(new BigDecimal("0"));
				countermanScore.setCaiScore(new BigDecimal("0"));
				countermanScore.setUpdateTime(new Date());
				if(countermanScoreMapper.updateByPrimaryKeySelective(countermanScore)==1){
					success.incrementAndGet();
				}else{
					fail.incrementAndGet();
				}
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", success.intValue()+"");
		result.put("fail", fail.intValue()+"");
		return DatasetBuilder.fromDataSimple(result);
	}*/

/*	@Override
	public DatasetList<CountermanScore> h5GetCountermanScoreByUserId(Long userId) {
		//判断业务员代码是否为空
		if(Objects.isNull(userId)){
			throw new BusinessException("请重新登录~~");
		}
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		if(countermans.isEmpty()){
			throw new BusinessException("请绑定业务员~~");
		}
		if(!Counterman.STATUS_0.equals(countermans.get(0).getStatus())){
			throw new BusinessException("您绑定业务员已离职~~");
		}
		String countermanCode = countermans.get(0).getCountermanCode();
		//查询业务员积分:查询条件:所属片区,业务员代码
		CountermanScoreExample countermanScoreEx = new CountermanScoreExample(); 
		com.emate.shop.business.model.CountermanScoreExample.Criteria criteria = countermanScoreEx.createCriteria();
		List<String> belongAreaList = new ArrayList<String>();
		belongAreaList.add("黄埔");
		belongAreaList.add("黄埔_萝岗");
		belongAreaList.add("黄埔_南岗");
		criteria.andBelongAreaIn(belongAreaList);
		criteria.andCountermanCodeEqualTo(countermanCode);
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreEx);
		return DatasetBuilder.fromDataList(countermanScores);
	}*/

	/*@Override
	public DatasetList<CountermanScoreRecord> adminCountermanScoreRecordList(Integer pageNo, Integer pageSize,
			CountermanScoreRecord countermanScoreRecord) {
		if(StringUtils.isEmpty(countermanScoreRecord.getCountermanCode())){
			throw new BusinessException("业务员工号为空");
		}
		CountermanScoreRecordExample ex = new CountermanScoreRecordExample();
		ex.createCriteria().andCountermanCodeEqualTo(countermanScoreRecord.getCountermanCode());
		ex.setOrderByClause(CountermanScoreRecordExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanScoreRecordMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanScoreRecord> countermanScoreRecords = countermanScoreRecordMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(countermanScoreRecords, page.createPageInfo());
	}
*/
/*	@Override
	public DatasetSimple<Map<String,Object>> queryCountermanScoreByCode(String code) {
		if(StringUtils.isEmpty(code)){
			throw new BusinessException("业务员code不能为空");
		}
		CountermanScoreExample countermanScoreExample = new CountermanScoreExample();
		countermanScoreExample.createCriteria().andCountermanCodeEqualTo(code);
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreExample);
		if(countermanScores.isEmpty()){
			throw new BusinessException("业务员积分不存在！");
		}
		if("黄埔_萝岗".equals(countermanScores.get(0).getBelongArea())){
			countermanScores.get(0).setBelongArea("萝岗");
		}
		if("黄埔_南岗".equals(countermanScores.get(0).getBelongArea())){
			countermanScores.get(0).setBelongArea("南岗");
		}
		Counterman counterman = countermanMapper.selectByPrimaryKey(countermanScores.get(0).getCountermanId());
		if(Objects.isNull(counterman)){
			throw new BusinessException("该业务员不存在！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("countermanCode",counterman.getCountermanCode());
		result.put("countermanName",counterman.getName());
		result.put("score", countermanScores.get(0).getScore());
		return DatasetBuilder.fromDataSimple(result);
	}*/
}
