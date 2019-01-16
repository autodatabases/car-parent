
package com.emate.shop.business.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emate.shop.business.api.AuthCodeCheckService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AuthCode;
import com.emate.shop.business.model.AuthCodeExample;
import com.emate.shop.business.model.AuthCodeVo;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AuthCodeMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;

/**
 * @file AuthCodeCheckServiceImpl.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月29日 
 */
@Service
public class AuthCodeCheckServiceImpl implements AuthCodeCheckService {

    @Resource
    private AuthCodeMapper authCodeMapper;

    @Override
    public DatasetList<AuthCodeVo> authCodeCheck(String userPhone,
            Integer pageNo, Integer pageSize) {
        AuthCodeExample authCodeExample = new AuthCodeExample();
        if (Objects.nonNull(userPhone) && !userPhone.equals("")) {
            authCodeExample.or().andUserphoneEqualTo(userPhone);
        }
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                authCodeMapper.countByExample(authCodeExample));
        authCodeExample.setLimitStart(page.getStartRow());
        authCodeExample.setLimitEnd(page.getSize());
        authCodeExample.setOrderByClause(AuthCodeExample.ID_ASC);
        List<AuthCode> authCodeList = authCodeMapper
                .selectByExample(authCodeExample);
        if (Objects.isNull(authCodeList)) {
            throw new BusinessException("信息不存在!");
        }
        List<AuthCodeVo> authCodeVoList = authCodeList.stream()
                .map(authCode -> {
                    AuthCodeVo authCodeVo = new AuthCodeVo();
                    authCodeVo.setId(authCode.getId());
                    authCodeVo.setUserphone(authCode.getUserphone());
                    authCodeVo.setPlatform(
                            authCode.getSmsType() == 0 ? "惠+车服" : "追电科技");
                    authCodeVo.setCreateTime(
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(authCode.getCreateTime()));
                    authCodeVo.setCode(authCode.getCode());
                    authCodeVo.setEffectiveTime("十分钟之内有效");
                    Integer status = authCode.getStatus();
                    if (status == 0) {
                        authCodeVo.setStatus("成功");
                    } else if (status == 1) {
                        authCodeVo.setStatus("失败");
                    } else {
                        authCodeVo.setStatus("过期");
                    }
                    authCodeVo.setFailureFeedback(authCode.getStatus() == 1
                            ? authCode.getResultMsg() : "-");
                    return authCodeVo;
                }).collect(Collectors.toList());
        return DatasetBuilder.fromDataList(authCodeVoList,
                page.createPageInfo());
    }
}
