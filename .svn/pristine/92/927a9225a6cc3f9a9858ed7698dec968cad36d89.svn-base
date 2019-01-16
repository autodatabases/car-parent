package com.emate.shop.business.api;

import com.emate.shop.business.model.OilCardConfig;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface OilCardConfigService {

    /**
     * 根据id查询油卡商品面额
     * @param id
     * @return
     */
    public DatasetSimple<OilCardConfig> getOilCardConfig(Long id);

    /**
     * 查询油卡商品面额
     * @param oilCardConfig
     * @param pageNo
     * @param pageSize
     * @return
     */
    public DatasetList<OilCardConfig> queryOilCardConfig(
            OilCardConfig oilCardConfig, Integer pageNo, Integer pageSize);

    /**
     * 新增或更新油卡商品面额
     * @param oilCardConfig
     * @return
     */
    public DatasetSimple<Integer> addOrUpdateOilCardConfig(
            OilCardConfig oilCardConfig);

    /**
     * 更新状态
     * @param status
     * @return
     */
    public DatasetSimple<Integer> updateStatus(Long id, String status);

    /**
     * 删除某个油卡面额
     * @param id
     * @return
     */
    public DatasetSimple<Integer> deloilCardConfig(String id);

    /**
     * 根据油卡类型和面额查询油卡商品面额
     * @param cardType
     * @param content
     * @return
     */
    public DatasetSimple<OilCardConfig> getOilCardConfigByCon(String cardType,
            String content, String supplier);

}
