package com.emate.shop.business.service;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.AdminLogService;
import com.emate.shop.business.model.AdminLogExample;
import com.emate.shop.datasource.Write;
import com.emate.shop.mapper.AdminLogMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class AdminLogServiceImpl implements AdminLogService{

    @Resource
     private AdminLogMapper adminLogMapper;
    

	@Override
	@Write
	@Transactional
	public DatasetSimple<String> deleteUnActiveLog() {
		AdminLogExample ex = new AdminLogExample();
		ex.createCriteria().andCreateTimeLessThan(new Date(System.currentTimeMillis()-10*60*1000));//删除10分钟之前没有活跃的自动踢出去
		adminLogMapper.deleteByExample(ex);
		return DatasetBuilder.fromDataSimple("ok");
	}

	public static void main(String[] args) {
		System.out.println(new Date(System.currentTimeMillis()-3*60*1000));
	}
}
