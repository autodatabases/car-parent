package com.emate.shop.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.ScoreChannelService;
import com.emate.shop.business.model.ScoreChannel;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("scoreChannel")
public class ScoreChannelController implements AuthUtil {
	
	private ScoreChannelService scoreChannelService;
	
	@RemoteService
	public void setScoreChannelService(ScoreChannelService scoreChannelService){
		this.scoreChannelService = scoreChannelService;
	}
	
	/**
	 * 查询所有城市渠道规则记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param scoreChannel
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryScoreChannelList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryScoreChannelList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,ScoreChannel scoreChannel) {
    	return scoreChannelService.adminQueryScoreChannelList(Integer.parseInt(pageNo),Integer.parseInt(pageSize),scoreChannel);
    }
    
    /**
     * 查询某个城市某个渠道的规则
     * @param scoreChannel
     * @return
     */
    @ResponseBody
    @RequestMapping("queryScoreChannel")
    @AuthAction
    public DatasetList<ScoreChannel> queryScoreChannel(HttpServletRequest request,ScoreChannel scoreChannel) {
    	return scoreChannelService.queryScoreChannel(scoreChannel);
    }
    
    /**
     * 修改或添加城市渠道规则
     * @param request
     * @param scoreChannels
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/updateScoreChannel", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateScoreChannel(HttpServletRequest request,@RequestBody List<ScoreChannel> scoreChannels) throws Exception {
		return scoreChannelService.addOrUpdateScoreChannel(scoreChannels);
	}
	
    /**
     * 删除该城市该渠道规则
     * @param request
     * @param scoreChannels
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/delScoreChannel", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> deleteScoreChannel(HttpServletRequest request,ScoreChannel scoreChannel) throws Exception {
		return scoreChannelService.delScoreChannel(scoreChannel);
	}
    
}
