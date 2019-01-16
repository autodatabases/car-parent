package com.emate.shop.business.api;

import java.math.BigDecimal;

import com.emate.shop.business.model.OilGoodsAddress;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.vo.OilTypeGoodsListVo;
import com.emate.shop.business.vo.OilTypeGoodsTypeShowVo;
import com.emate.shop.business.vo.ZTreeNode;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 虚拟充值服务
 * @file OilVirtualTopupService.java
 * @author dongpengxin
 * @mail dongpengxin@emateglobal.com
 * @since 2018年6月26日
 */
public interface OilVirtualTopupService {

    /**
     * 商品列表查询
     * @param productId
     * @param goodsName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Read
    DatasetList<OilTypeGoodsListVo> queryVirtualTopupList(String productId,
            String goodsName, Integer pageNo, Integer pageSize);

    /**
     * 添加商品类目
     * @param typeName
     * @param typeGrade
     * @param remark
     * @return
     */
    @Write
    DatasetSimple<Boolean> addOilGoodsType(String typeName, Integer typeGrade,Long parentId,
            String remark);
    
    /**
     * 修改商品类目
     * @param typeName
     * @param id
     * @param remark
     * @return
     */
    @Write
    DatasetSimple<Boolean> updateOilGoodsType(String typeName, Long id,
            String remark);

    /**
     * 查询商品类目
     * @param parentId
     * @return
     */
    @Read
    DatasetList<OilGoodsType> queryOilGoodsType(Long parentId);
    
    @Read
    DatasetList<OilGoodsType> queryOilGoodsTypeALL();

    /**
     * 根据上级类目查询商品
     * @return
     */
    @Read
    DatasetList<OilTypeGoodsTypeShowVo> queryOilGoodsTypeName();

    /**
     * 添加商品信息
     * @param typeName
     * @param parentId
     * @param productId
     * @param goodsName
     * @param sale
     * @param denomination
     * @return
     */
    @Write
    DatasetSimple<Boolean> addOilGoods(String typeName, Long parentId,
            String productId, String goodsName, BigDecimal sale,String denomination);

    /**
     * 修改商品信息
     * @param id
     * @param productId
     * @param goodsName
     * @param sale
     * @return
     */
    @Write
    DatasetSimple<Boolean> updateOilGoods(Long id, String productId,
            String goodsName, String denomination,BigDecimal sale);

    /**
     * 修改商品状态
     * @param id
     * @param productId
     * @param status
     * @param oneStatus
     * @return
     */
    @Write
    DatasetSimple<Boolean> updateOilGoodsStatus(Long id, String productId,
            Integer status, Integer oneStatus);

    /**
     * ZTreeNode展示
     * @return
     */
    @Read
    public DatasetList<ZTreeNode> categoryTree();
    
    /**
     * 根据商品筛选所属城市
     * @param goodId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Read
    DatasetList<OilGoodsAddress> queryOilAddress(String goodId,
    		Integer pageNo, Integer pageSize);
    
    /**
     * 修改所属城市状态
     * @param id
     * @param status
     * @return
     */
    DatasetSimple<Boolean> updateOilAddress(String id,String status);

}
