package com.emate.shop.admin.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OilVirtualTopupService;
import com.emate.shop.business.model.OilGoodsAddress;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.vo.OilTypeGoodsListVo;
import com.emate.shop.business.vo.OilTypeGoodsTypeShowVo;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Length;
import com.emate.shop.web.validator.Min;
import com.emate.shop.web.validator.Regex;

/**
 * 虚拟充值
 * @file VirtualTopupController.java
 * @author dongpengxin
 * @mail dongpengxin@emateglobal.com
 * @since 2018年6月26日
 */
@Controller
@RequestMapping("virtualtopup")
public class OilVirtualTopupController {

    private OilVirtualTopupService oilVirtualTopupService;

    @RemoteService
    public void setOilVirtualTopupService(
            OilVirtualTopupService oilVirtualTopupService) {
        this.oilVirtualTopupService = oilVirtualTopupService;
    }

    /**
     * 商品列表查询
     * @param request
     * @param productId
     * @param goodsName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AuthAction
    @ResponseBody
    @RequestMapping(value = "/queryVirtualTopupList", method = RequestMethod.GET)
    public DatasetList<OilTypeGoodsListVo> queryVirtualTopupList(
            HttpServletRequest request, String productId, String goodsName,
            @Regex("\\d+") String pageNo, @Regex("\\d+") String pageSize) {
        String regex = "^[0-9]{6,9}$";
        if (StringUtils.isNotBlank(productId)) {
            if (!productId.matches(regex)) {
                throw new BusinessException("请输入正确的商品编号");
            }
        }
        if (StringUtils.isNotBlank(goodsName)) {
            String regex1 = "^[\u4e00-\u9fa5A-Za-z0-9]{1,20}$";
            if (!goodsName.matches(regex1)) {
                throw new BusinessException("输入商品名称规格有误，请重新输入");
            }
        }
        return oilVirtualTopupService.queryVirtualTopupList(productId,
                goodsName, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
    }

    /**
     * 修改商品信息
     * @param request
     * @param id
     * @param productId
     * @param goodsName
     * @param sale
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/updateOilGoods", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> updateOilGoods(HttpServletRequest request,
            @Min(1L) Long id, String productId, String goodsName,String denomination,
            @Min(0.00) BigDecimal sale) {
        if (Objects.isNull(id)) {
            throw new BusinessException("商品ID不能为空");
        }
        if (StringUtils.isBlank(goodsName)) {
            throw new BusinessException("商品名称不能为空");
        } else {
            String regex = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!goodsName.matches(regex)) {
                throw new BusinessException("输入商品名称规格有误，请重新输入");
            }
        }
        
        if (StringUtils.isBlank(denomination)) {
            throw new BusinessException("商品规格不能为空");
        } else {
            String regex1 = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!denomination.matches(regex1)) {
                throw new BusinessException("输入商品规格有误，请重新输入");
            }
        }

        if (Objects.isNull(sale)) {
            throw new BusinessException("商品价格不能为空");
        }
        return oilVirtualTopupService.updateOilGoods(id, productId, goodsName,denomination,
                sale);

    }

    /**
     * 修改商品状态
     * @param request
     * @param id
     * @param productId
     * @param status
     * @param oneStatus
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/updateOilGoodsStatus", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> updateOilGoodsStatus(
            HttpServletRequest request, @Min(1L) Long id, String productId,
            Integer status, Integer oneStatus) {
        return oilVirtualTopupService.updateOilGoodsStatus(id, productId,
                status, oneStatus);

    }

    /**
     * 添加商品类目
     * @param request
     * @param typeName
     * @param typeGrade
     * @param parentId
     * @param remark
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/addOilGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> addOilGoodsType(HttpServletRequest request,
            String typeName, Integer typeGrade, @Min(0) Long parentId,
            @Length(min = 0, max = 150) String remark) {
        if (StringUtils.isBlank(typeName)) {
            throw new BusinessException("类目名称不能为空");
        } else {
            String regex = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!typeName.matches(regex)) {
                throw new BusinessException("输入类目规格有误，请重新输入");
            }
        }
        return oilVirtualTopupService.addOilGoodsType(typeName, typeGrade,
                parentId, remark);

    }

    /**
     * 修改商品类目
     * @param request
     * @param typeName
     * @param id
     * @param remark
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/updateOilGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> updateOilGoodsType(HttpServletRequest request,
            String typeName, @Min(0) Long id,
            @Length(min = 0, max = 150) String remark) {
        if (StringUtils.isBlank(typeName)) {
            throw new BusinessException("类目名称不能为空");
        } else {
            String regex = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!typeName.matches(regex)) {
                throw new BusinessException("输入类目规格有误，请重新输入");
            }
        }
        
        
        if (Objects.isNull(id)) {
            throw new BusinessException("类目ID不能为空");
        }
        return oilVirtualTopupService.updateOilGoodsType(typeName, id,remark);

    }

    /**
     * 查询商品类目
     * @param request
     * @param typeName
     * @param typeGrade
     * @param parentId
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/queryOilGoodsType", method = RequestMethod.GET)
    @ResponseBody
    public DatasetList<OilGoodsType> queryOilGoodsType(
            HttpServletRequest request, @Min(1) Long parentId) {
        return oilVirtualTopupService.queryOilGoodsType(parentId);

    }

    /**
     * 查询商品类目
     * @param request
     * @param typeName
     * @param typeGrade
     * @param parentId
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/queryOilGoodsTypeALL", method = RequestMethod.GET)
    @ResponseBody
    public DatasetSimple<Map<String, Object>> queryOilGoodsTypeALL(
            HttpServletRequest request) {
        DatasetList<OilGoodsType> allOilGoodsType = oilVirtualTopupService
                .queryOilGoodsTypeALL();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("allOilGoodsType", allOilGoodsType);
        dataMap.put("ztreeData", JacksonHelper
                .toJSON(this.oilVirtualTopupService.categoryTree()));
        return DatasetBuilder.fromDataSimple(dataMap);
    }

    /**
     * 查询上级类目
     * @param request
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/queryOilGoodsTypeName", method = RequestMethod.GET)
    @ResponseBody
    public DatasetList<OilTypeGoodsTypeShowVo> queryOilGoodsTypeName(
            HttpServletRequest request) {
        return oilVirtualTopupService.queryOilGoodsTypeName();

    }

    /**
     * 添加商品信息
     * @param request
     * @param typeName
     * @param parentId
     * @param productId
     * @param goodsName
     * @param sale
     * @param denomination
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/addOilGoods", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> addOilGoods(HttpServletRequest request,
            String typeName, @Min(0) Long parentId, String productId,
            String goodsName, @Min(0.00) BigDecimal sale,String denomination) {
        if (StringUtils.isBlank(typeName)) {
            throw new BusinessException("类目名称不能为空");
        }
        String regex = "^[0-9]{6,9}$";
        if (StringUtils.isNotBlank(productId)) {
            if (!productId.matches(regex)) {
                throw new BusinessException("请输入正确的商品编号");
            }
        }
        if (Objects.isNull(sale)) {
            throw new BusinessException("商品售价不能为空");
        }
        if (StringUtils.isBlank(goodsName)) {
            throw new BusinessException("商品名称不能为空");
        } else {
            String regex1 = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!goodsName.matches(regex1)) {
                throw new BusinessException("输入商品名称规格有误，请重新输入");
            }
        }
        
        if (StringUtils.isBlank(denomination)) {
            throw new BusinessException("商品规格不能为空");
        } else {
            String regex1 = "^[\u4e00-\u9fa5A-Za-z0-9]{1,15}$";
            if (!denomination.matches(regex1)) {
                throw new BusinessException("输入商品规格有误，请重新输入");
            }
        }
        return oilVirtualTopupService.addOilGoods(typeName, parentId, productId,
                goodsName, sale, denomination);

    }

    /**
     * 查询中石化油卡所属城市
     * @param request
     * @param goodId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AuthAction
    @ResponseBody
    @RequestMapping(value = "/queryoiladdress", method = RequestMethod.POST)
    public DatasetList<OilGoodsAddress> queryOilAddress(
            HttpServletRequest request, String goodId,
            @Regex("\\d+") String pageNo, @Regex("\\d+") String pageSize) {
    	
        return oilVirtualTopupService.queryOilAddress(goodId,
                 Integer.valueOf(pageNo), Integer.valueOf(pageSize));
    }
    
    /**
     * 更新中石化油卡所属城市状态
     * @param request
     * @param goodId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AuthAction
    @ResponseBody
    @RequestMapping(value = "/updateoiladdress", method = RequestMethod.POST)
    public DatasetSimple<Boolean> updateOilAddress(
            HttpServletRequest request, String id,String status) {
    	
        return oilVirtualTopupService.updateOilAddress(id,status);
    }
}
