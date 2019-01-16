package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface UserService {
	
	/**
	 * 根据用户名查询用户
	 * @return
	 */
	public DatasetList<User> queryUserByName(String userName);
	
	public DatasetSimple<User> queryUserById(String id);
	
	public DatasetSimple<SystemUser> adminLogin(String userName,String password,String ip);
	
	public DatasetSimple<User> userRegister(String regType,String userName, String smsCode,String tokenCode,String ip,String chepai,String password);
	
	public DatasetSimple<Map<String,String>> bind(Long userId,String chepai,String password,String source);
	
	public DatasetSimple<User> userLogin(String userName, String smsCode,String tokenCode,String ip);
	
	public DatasetSimple<Boolean> setCarInfo(Long userId,Long autoId,String pic,String carCode,String mileage,String license);
	
	public DatasetSimple<Boolean> setAddressInfo(Long userId,String address);
	
	public DatasetSimple<ImportUserInfo> getUserInfo(Long userId);
	
	public DatasetSimple<ImportUserInfo> getImportUserInfo(Long userId);
	
	public DatasetList<Map<String,String>> myCars(Long userId);
	
	public DatasetSimple<User> updateUserInfo(Long userId,String nickName,Integer gender);
	
	public DatasetList<Map<String,String>> userList(User user, Integer pageNo,Integer pageSize);
	
	public DatasetSimple<Map<String,String>> queryUserForEdit(Long userId);
	
	public DatasetSimple<Map<String,String>> createOrUpdateUserInfo(User user,UserInfo userInfo,ImportUserInfo importUserInfo);

	public DatasetList<Map<String,Object>> queryImportErrList(Integer pageNo,Integer pageSize,String flag,ImportUserInfo importUserInfo);
	
	public DatasetSimple<Map<String,String>> hasUnCompletOrder(Long userId,String orderType);
	
	public DatasetSimple<Map<String, String>> bindBusiness(Long userId, String businessCode,String realName);
	
	public DatasetSimple<Map<String, String>> getBusinessInfo(Long userId);
	
	public DatasetSimple<Map<String, String>> deleteOverdueImportInfo();
	
	public DatasetList<Map<String,String>> queryExpiredUserInfo();
	
	/**
	 * 更新车服用户和车辆信息
	 * @param userInfo
	 * @param phone
	 * @return
	 */

	public DatasetSimple<Boolean> updateCarUserInfo(ImportUserInfo userInfo,String phone);
	
	/**
	 * 更新车服用户和车辆信息2
	 * @param orderPrice
	 * @param signTime
	 * @param startTime
	 * @param endTime
	 * @param paymentTime
	 * @param infoId
	 * @return
	 */
	public DatasetSimple<Boolean> updateCarUserInfoTwo(String orderPrice, String signTime, String startTime,
			String endTime, String paymentTime, String infoId);
	
	/**
	 * 更新用户手机号
	 * @param newphone
	 * @param smsCode
	 * @param tokenCode
	 * @param license
	 * @param baoDan
	 * @param oldPhone
	 * @return
	 */
	public DatasetSimple<User> updateUserPhone(String newphone,String license,
			String baoDan,String oldPhone);
	
	/**
	 * 判断短信是否正确
	 * @param newphone
	 * @param smsCode
	 * @param tokenCode
	 * @return
	 */
	public DatasetSimple<String> judgeMessage(String newphone,String smsCode,
			String tokenCode);
}
