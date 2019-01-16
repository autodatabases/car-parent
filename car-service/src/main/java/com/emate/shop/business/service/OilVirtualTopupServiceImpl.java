package com.emate.shop.business.service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.emate.shop.business.api.OilVirtualTopupService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.OilGoods;
import com.emate.shop.business.model.OilGoodsAddress;
import com.emate.shop.business.model.OilGoodsAddressExample;
import com.emate.shop.business.model.OilGoodsExample;
import com.emate.shop.business.model.OilGoodsExample.Criteria;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.model.OilGoodsTypeExample;
import com.emate.shop.business.vo.OilTypeGoodsListVo;
import com.emate.shop.business.vo.OilTypeGoodsTypeShowVo;
import com.emate.shop.business.vo.ZTreeNode;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.OilGoodsAddressMapper;
import com.emate.shop.mapper.OilGoodsMapper;
import com.emate.shop.mapper.OilGoodsTypeMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 虚拟充值服务
 * @file OilVirtualTopupServiceImpl.java
 * @author dongpengxin
 * @mail dongpengxin@emateglobal.com
 * @since 2018年6月26日
 */
@Service
public class OilVirtualTopupServiceImpl implements OilVirtualTopupService {

    @Resource
    private OilGoodsTypeMapper oilGoodsTypeMapper;

    @Resource
    private OilGoodsMapper     oilGoodsMapper;
    
    @Resource
    private OilGoodsAddressMapper     oilGoodsAddressMapper;

    /**
     * 商品列表查询
     */
    @Override
    public DatasetList<OilTypeGoodsListVo> queryVirtualTopupList(
            String productId, String goodsName, Integer pageNo,
            Integer pageSize) {
        OilGoodsExample oilGoodsExample = new OilGoodsExample();
        oilGoodsExample.setOrderByClause(OilGoodsExample.UPDATE_TIME_DESC);
        Criteria oilGoodsCriteria = oilGoodsExample.createCriteria();
        if (StringUtils.isNotEmpty(productId) && !productId.contains("--")) {
            oilGoodsCriteria.andProductIdEqualTo(productId);
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            oilGoodsCriteria.andGoodsNameLike("%" + goodsName + "%");
        }
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilGoodsMapper.countByExample(oilGoodsExample));
        oilGoodsExample.setLimitStart(page.getStartRow());
        oilGoodsExample.setLimitEnd(page.getSize());
        List<OilGoods> oilGoodsList = oilGoodsMapper
                .selectByExample(oilGoodsExample);
        if (CollectionUtils.isEmpty(oilGoodsList)) {
            if (StringUtils.isNotEmpty(productId)
                    || StringUtils.isNotEmpty(goodsName)) {
                throw new BusinessException("没有该商品信息");
            }
        }
        List<OilTypeGoodsListVo> oilTypeGoodsList = new ArrayList<OilTypeGoodsListVo>();
        oilGoodsList.stream().forEach(oilGoods -> {
            OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
            OilGoodsType selectByPrimaryKey = oilGoodsTypeMapper
                    .selectByPrimaryKey(oilGoods.getTypeId());
            if (Objects.nonNull(selectByPrimaryKey)) {
                Long parentId = selectByPrimaryKey.getParentId();
                OilGoodsType oilGoodsType = null;
                while (parentId != 0L) {
                    oilGoodsTypeExample.clear();
                    oilGoodsType = oilGoodsTypeMapper
                            .selectByPrimaryKey(parentId);
                    parentId = oilGoodsType.getParentId();
                }
                String typeName = null;
                if (Objects.isNull(oilGoodsType)) {
                    typeName = selectByPrimaryKey.getTypeName();
                } else {
                    typeName = oilGoodsType.getTypeName();
                }
                OilTypeGoodsListVo oilTypeGoodsListVo = new OilTypeGoodsListVo();
                oilTypeGoodsListVo.setId(oilGoods.getId());
                oilTypeGoodsListVo.setProductId(oilGoods.getProductId());
                oilTypeGoodsListVo.setTypeName(typeName);
                if (oilGoods.getGoodsName()
                        .contains(selectByPrimaryKey.getTypeName())) {
                    oilTypeGoodsListVo.setGoodsName(oilGoods.getGoodsName());
                } else {
                    oilTypeGoodsListVo
                            .setGoodsName(selectByPrimaryKey.getTypeName()
                                    + oilGoods.getGoodsName());
                }

                oilTypeGoodsListVo.setSale(oilGoods.getSale());
                oilTypeGoodsListVo.setStatus(oilGoods.getStatus());
                oilTypeGoodsListVo.setDenomination(oilGoods.getDenomination());
                oilTypeGoodsListVo.setOneStatus(oilGoods.getOneStatus());
                oilTypeGoodsListVo.setCreateTime(oilGoods.getCreateTime());
                oilTypeGoodsListVo.setUpdateTime(oilGoods.getUpdateTime());
                //判断是否是中石化商品
                List<String> asList = Arrays.asList(new String[] { "64157001","64157002","64157003","64157004","64157005"});
                oilTypeGoodsListVo.setFlag(false);
                if(asList.contains(oilGoods.getProductId())){
                	oilTypeGoodsListVo.setFlag(true);;
                };
                oilTypeGoodsList.add(oilTypeGoodsListVo);
            }
        });
        if (CollectionUtils.isEmpty(oilTypeGoodsList)) {
            if (StringUtils.isNotEmpty(productId)
                    || StringUtils.isNotEmpty(goodsName)) {
                throw new BusinessException("没有该商品信息");
            }
        }
        //返回的结果
        return DatasetBuilder.fromDataList(oilTypeGoodsList,
                page.createPageInfo());
    }

    /**
     * 修改商品信息
     */
    @Override
    public DatasetSimple<Boolean> updateOilGoods(Long id, String productId,
            String goodsName,String denomination, BigDecimal sale) {
        OilGoods selectByPrimaryKey = this.oilGoodsMapper
                .selectByPrimaryKey(id);
        Integer status = selectByPrimaryKey.getStatus();
        if (OilGoods.STATUS_1 == status) {
            throw new BusinessException("上架商品不能修改！");
        }
        OilGoodsExample example = new OilGoodsExample();
        Criteria createCriteria = example.createCriteria();
        createCriteria.andIdEqualTo(id);
        if (StringUtils.isNotBlank(productId) && !productId.contains("--")) {
            createCriteria.andProductIdEqualTo(productId);
        }
        OilGoods oilGoods = new OilGoods();
        oilGoods.setGoodsName(goodsName);
        oilGoods.setSale(sale);
        oilGoods.setDenomination(denomination);
        oilGoods.setUpdateTime(new Date());
        if (oilGoodsMapper.updateByExampleSelective(oilGoods, example) == 1) {
            return DatasetBuilder.fromDataSimple(true);
        }
        throw new BusinessException("修改商品信息失败！");
    }

    /**
     * 修改商品状态
     */
    @Override
    public DatasetSimple<Boolean> updateOilGoodsStatus(Long id,
            String productId, Integer status, Integer oneStatus) {
        OilGoodsExample example = new OilGoodsExample();
        Criteria createCriteria = example.createCriteria();
        OilGoods oilGoods = new OilGoods();
        if (Objects.isNull(oneStatus)) {
            if (Objects.isNull(id) || Objects.isNull(status)) {
                throw new BusinessException("修改商品信息失败！");
            }
            createCriteria.andIdEqualTo(id);
            if (StringUtils.isNotBlank(productId)
                    && !productId.contains("--")) {
                createCriteria.andProductIdEqualTo(productId);
            }
            oilGoods.setStatus(status);
            oilGoods.setUpdateTime(new Date());
            if (oilGoodsMapper.updateByExampleSelective(oilGoods,
                    example) == 1) {
                return DatasetBuilder.fromDataSimple(true);
            }
            throw new BusinessException("修改商品信息失败！");
        }
        //一键上下架
        oilGoods.setOneStatus(oneStatus);
        oilGoods.setUpdateTime(new Date());
        oilGoodsMapper.updateByExampleSelective(oilGoods, example);
        return DatasetBuilder.fromDataSimple(true);

    }

    /**
     * 查询商品类目
     */
    @Override
    public DatasetList<OilGoodsType> queryOilGoodsType(Long parentId) {
        OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
        oilGoodsTypeExample
                .setOrderByClause(OilGoodsTypeExample.UPDATE_TIME_DESC);
        com.emate.shop.business.model.OilGoodsTypeExample.Criteria createCriteria = oilGoodsTypeExample
                .createCriteria();
        if (Objects.nonNull(parentId)) {
            createCriteria.andParentIdEqualTo(parentId);
        } else {
            createCriteria.andParentIdEqualTo(0L)
                    .andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_1);
        }
        List<OilGoodsType> oilGoodsTypeList = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);
        if (CollectionUtils.isEmpty(oilGoodsTypeList)) {
            throw new BusinessException("没有下一级商品类目信息了");
        }
        return DatasetBuilder.fromDataList(oilGoodsTypeList);
    }

    /**
     * 查询所有商品类目
     */
    @Override
    public DatasetList<OilGoodsType> queryOilGoodsTypeALL() {
        OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
        oilGoodsTypeExample
                .setOrderByClause(OilGoodsTypeExample.UPDATE_TIME_DESC);
        List<OilGoodsType> oilGoodsTypeList = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);
        return DatasetBuilder.fromDataList(oilGoodsTypeList);
    }

    /**
     * ZTreeNode展示
     */
    @Override
    public DatasetList<ZTreeNode> categoryTree() {
        OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
        oilGoodsTypeExample.or().andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_1);
        List<OilGoodsType> level1OilGoodsType = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);
        oilGoodsTypeExample.clear();
        oilGoodsTypeExample.or().andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_2);
        List<OilGoodsType> level2OilGoodsType = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);
        oilGoodsTypeExample.clear();
        oilGoodsTypeExample.or().andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_3);
        List<OilGoodsType> level3OilGoodsType = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);

        List<ZTreeNode> level3 = level3OilGoodsType.stream().map(type3 -> {
            ZTreeNode node = new ZTreeNode();
            node.setName(type3.getTypeName());
            node.setOpen(false);
            node.setEntity(type3);

            return node;
        }).collect(Collectors.toList());

        List<ZTreeNode> level2 = level2OilGoodsType.stream().map(type2 -> {
            ZTreeNode node = new ZTreeNode();
            node.setName(type2.getTypeName());
            node.setOpen(true);
            node.setEntity(type2);
            level3.stream().filter(typeNode -> Objects.equals(type2.getId(),
                    ((OilGoodsType) typeNode.getEntity()).getParentId()))
                    .forEach(node::addChild);
            return node;
        }).collect(Collectors.toList());
        List<ZTreeNode> level1 = level1OilGoodsType.stream().map(type1 -> {
            ZTreeNode node = new ZTreeNode();
            node.setName(type1.getTypeName());
            node.setOpen(true);
            node.setEntity(type1);
            level2.stream().filter(typeNode -> Objects.equals(type1.getId(),
                    ((OilGoodsType) typeNode.getEntity()).getParentId()))
                    .forEach(node::addChild);
            return node;
        }).collect(Collectors.toList());
        return DatasetBuilder.fromDataList(level1);
    }

    /**
     * 查询上级类目
     */
    @Override
    public DatasetList<OilTypeGoodsTypeShowVo> queryOilGoodsTypeName() {
        OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
        oilGoodsTypeExample.setOrderByClause(OilGoodsTypeExample.PARENT_ID_ASC);
        List<Integer> values = new ArrayList<Integer>();
        values.add(OilGoodsType.TYPE_GRADE_1);
        values.add(OilGoodsType.TYPE_GRADE_2);
        oilGoodsTypeExample.createCriteria().andTypeGradeIn(values);
        List<OilGoodsType> oilGoodsTypeList = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);

        List<OilTypeGoodsTypeShowVo> list = new ArrayList<OilTypeGoodsTypeShowVo>();
        OilTypeGoodsTypeShowVo one = new OilTypeGoodsTypeShowVo();
        OilGoodsType oilGoodsTypeOne = new OilGoodsType();
        oilGoodsTypeOne.setId(0L);
        oilGoodsTypeOne.setTypeGrade(0);
        oilGoodsTypeOne.setParentId(0L);
        one.setShowName("无");
        one.setOilGoodsType(oilGoodsTypeOne);
        list.add(one);
        if (!CollectionUtils.isEmpty(oilGoodsTypeList)) {
            oilGoodsTypeList.stream().forEach(oilGoodsType -> {
                Integer typeGrade = oilGoodsType.getTypeGrade();
                String typeName = oilGoodsType.getTypeName();
                if (OilGoodsType.TYPE_GRADE_1 == typeGrade) {
                    OilTypeGoodsTypeShowVo oilTypeGoodsTypeShowVo = new OilTypeGoodsTypeShowVo();
                    oilTypeGoodsTypeShowVo.setShowName("一级类目：" + typeName);
                    oilTypeGoodsTypeShowVo.setOilGoodsType(oilGoodsType);
                    list.add(oilTypeGoodsTypeShowVo);
                } else {
                    OilTypeGoodsTypeShowVo oilTypeGoodsTypeShowVo = new OilTypeGoodsTypeShowVo();
                    oilTypeGoodsTypeShowVo.setShowName("二级类目：" + typeName);
                    oilTypeGoodsTypeShowVo.setOilGoodsType(oilGoodsType);
                    list.add(oilTypeGoodsTypeShowVo);
                }
            });
        }

        return DatasetBuilder.fromDataList(list);
    }

    /**
     * 添加商品类目
     */
    @Override
    @Transactional
    public DatasetSimple<Boolean> addOilGoodsType(String typeName,
            Integer typeGrade, Long parentId, String remark) {
        OilGoodsTypeExample example = new OilGoodsTypeExample();
        example.createCriteria().andTypeNameEqualTo(typeName)
                .andTypeGradeEqualTo(typeGrade);
        List<OilGoodsType> list = oilGoodsTypeMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("该商品类目已添加！");
        }
        OilGoodsType oilGoodsType = new OilGoodsType();
        oilGoodsType.setParentId(parentId);
        oilGoodsType.setTypeName(typeName);
        if (StringUtils.isNotBlank(remark)) {
            oilGoodsType.setRemark(remark);
        }
        oilGoodsType.setTypeGrade(typeGrade);
        oilGoodsType.setCreateTime(new Date());
        oilGoodsType.setUpdateTime(new Date());
        if (oilGoodsTypeMapper.insertSelective(oilGoodsType) == 1) {
            return DatasetBuilder.fromDataSimple(true);
        }
        throw new BusinessException("添加商品类目信息失败！");

    }

    /**
     * 修改商品类目
     */
    @Override
    public DatasetSimple<Boolean> updateOilGoodsType(String typeName, Long id,
             String remark) {
        OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
        OilGoodsType selectByPrimaryKey = oilGoodsTypeMapper
                .selectByPrimaryKey(id);
        if (Objects.isNull(selectByPrimaryKey)) {
            throw new BusinessException("该商品类目不存在！");
        }
        Long parentId = selectByPrimaryKey.getParentId();
        oilGoodsTypeExample.createCriteria().andParentIdEqualTo(parentId);
        List<OilGoodsType> list = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeExample);
        list.stream().forEach(oilGoodsType1 -> {
            String typeName1 = oilGoodsType1.getTypeName();
            if (typeName1.equals(typeName)) {
                throw new BusinessException("该类目名称已存在！");
            }
        });
        OilGoodsType oilGoodsType = new OilGoodsType();
        oilGoodsType.setId(id);
        oilGoodsType.setTypeName(typeName);
        oilGoodsType.setRemark(remark);
        oilGoodsType.setUpdateTime(new Date());
        if (oilGoodsTypeMapper.updateByPrimaryKeySelective(oilGoodsType) == 1) {
            return DatasetBuilder.fromDataSimple(true);
        }
        throw new BusinessException("修改商品类目信息失败！");
    }

    /**
     * 添加商品信息
     */
    @Override
    @Transactional
    public DatasetSimple<Boolean> addOilGoods(String typeName, Long parentId,
            String productId, String goodsName,BigDecimal sale, String denomination
            ) {
        if (!typeName.contains("话费") && !typeName.contains("流量")) {
            if (StringUtils.isBlank(productId)) {
                throw new BusinessException("商品编号不能为空！");
            }
        }
        OilGoodsExample example = new OilGoodsExample();
        example.createCriteria().andProductIdEqualTo(productId)
                .andGoodsNameEqualTo(goodsName);
        List<OilGoods> list = oilGoodsMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("该商品已添加！");
        }
        OilGoods oilGoods = new OilGoods();
        oilGoods.setGoodsName(goodsName);
        if (StringUtils.isNotBlank(productId)) {
            oilGoods.setProductId(productId);
        }
        oilGoods.setTypeId(parentId);
        oilGoods.setSale(sale);
        oilGoods.setStatus(OilGoods.STATUS_1);
        oilGoods.setOneStatus(OilGoods.ONE_STATUS_1);
        oilGoods.setCreateTime(new Date());
        oilGoods.setUpdateTime(new Date());
        if (StringUtils.isNotBlank(denomination)) {
            oilGoods.setDenomination(denomination);
        }
        if (oilGoodsMapper.insertSelective(oilGoods) == 1) {
            return DatasetBuilder.fromDataSimple(true);
        }
        throw new BusinessException("添加商品信息失败！");
    }

	@Override
	@Read
	public DatasetList<OilGoodsAddress> queryOilAddress(String goodId, Integer pageNo, Integer pageSize) {
		if(StringUtils.isEmpty(goodId)){
			throw new BusinessException("商品id不能为空");
		}
		
		OilGoodsAddressExample oilGoodsAddressEx = new OilGoodsAddressExample();
		oilGoodsAddressEx.createCriteria().
			andGoodIdEqualTo(Long.valueOf(goodId));
		
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, 
				oilGoodsAddressMapper.countByExample(oilGoodsAddressEx));
		
		oilGoodsAddressEx.setLimitStart(page.getStartRow());
		oilGoodsAddressEx.setLimitEnd(page.getSize());
		oilGoodsAddressEx.setOrderByClause(OilGoodsAddressExample.ID_ASC);
		List<OilGoodsAddress> oilGoodsAddressList = oilGoodsAddressMapper
				.selectByExample(oilGoodsAddressEx);
		
		OilGoods oilGoods = oilGoodsMapper.selectByPrimaryKey(Long.valueOf(goodId));
		Boolean status = false;
		if(OilGoods.ONE_STATUS_1.equals(oilGoods.getOneStatus())
				&&OilGoods.STATUS_1.equals(oilGoods.getStatus())){
			status = true;
		}
		DatasetList<OilGoodsAddress> oilGoodsAddresss = DatasetBuilder.fromDataList(oilGoodsAddressList, 
				page.createPageInfo());
		oilGoodsAddresss.putDataset("status", DatasetBuilder.fromDataSimple(status));
		return oilGoodsAddresss;
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Boolean> updateOilAddress(String id, String status) {
		
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("所属城市id不能为空");
		}
		
		if(StringUtils.isEmpty(status)){
			throw new BusinessException("所属城市状态不能为空");
		}
		OilGoodsAddress oilGoodsAddress = oilGoodsAddressMapper.selectByPrimaryKey(Long.valueOf(id));
		if("0".equals(status)){
			oilGoodsAddress.setStatus(OilGoodsAddress.STATUS_0);
		}else if("1".equals(status)){
			oilGoodsAddress.setStatus(OilGoodsAddress.STATUS_1);
		}else{
			throw new BusinessException("未知所属状态status");
		}
		if(oilGoodsAddressMapper.updateByPrimaryKeySelective(oilGoodsAddress)!=1){
			throw new BusinessException("更新所属城市状态失败！！！");
		};
		return DatasetBuilder.fromDataSimple(true);
	}

}
