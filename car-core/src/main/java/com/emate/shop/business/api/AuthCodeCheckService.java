
package com.emate.shop.business.api;

import com.emate.shop.business.model.AuthCodeVo;
import com.emate.shop.datasource.Read;
import com.emate.shop.rpc.dto.DatasetList;

/**
 * 惠+车服后台添加查看验证码的相关需求 
 * @file AuthCodeCheckService.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月29日 
 */
public interface AuthCodeCheckService {

    /**
     * @param
     * @return
     */
    @Read
    DatasetList<AuthCodeVo> authCodeCheck(String userPhone, Integer pageNo,
            Integer pageSize);

}
