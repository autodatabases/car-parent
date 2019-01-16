package com.emate.shop.business.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.NewsService;
import com.emate.shop.business.model.News;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.mapper.NewsMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class NewsServiceImpl implements NewsService{

    
	@Resource
	private NewsMapper newsMapper;

	
	@Read
	@Override
	public DatasetSimple<News> queryNewsById(Long newsId) {
		return DatasetBuilder.fromDataSimple(newsMapper.selectByPrimaryKey(newsId));
	}

	@Write
	@Override
	@Transactional
	public DatasetSimple<Boolean> updateCount(Long newsId) {
		News  news = newsMapper.selectByPrimaryKey(newsId);
		news.setView(news.getView()+1);
		return DatasetBuilder.fromDataSimple(newsMapper.updateByPrimaryKeySelective(news) == 1);
	}

}
