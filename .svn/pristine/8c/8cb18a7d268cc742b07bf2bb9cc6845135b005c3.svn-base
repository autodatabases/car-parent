package com.emate.shop.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.SystemAuthService;
import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.vo.ResourceTree;
import com.emate.shop.business.vo.SystemUserAuth;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Length;
import com.emate.shop.web.validator.Min;
import com.emate.shop.web.validator.NotBlank;
import com.emate.shop.web.validator.NotEmpty;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.shop.web.validator.ValiParamName;

/**
 * @file SystemAuthController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月4日
 */
@Controller
@RequestMapping("permission")
public class SystemAuthController implements AuthUtil {

	private SystemAuthService systemAuthService;

	@RemoteService
	public void setSystemAuthService(SystemAuthService systemAuthService) {
		this.systemAuthService = systemAuthService;
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryUserResourcs.json")
	public DatasetSimple<SystemUserAuth> queryUserResourcs(HttpServletRequest request) {
		return this.systemAuthService.index(this.getUserId(request,AuthUtil.CAR_ADMIN_TOKEN));
	}

	/**
	 * 返回用户列表
	 * @param request
	 * @param dataMap
	 * @return
	 */
	@AuthAction
	@RequestMapping("userManager.json")
	@ResponseBody
	public DatasetList<SystemUser> userManager(HttpServletRequest request,
			@Required @Regex("\\d+")String pageNo,
			@Required @Regex("\\d+")String pageSize) {
		DatasetList<SystemUser> users = this.systemAuthService.queryAllusers(pageNo,pageSize);
//		if (users.isSuccess()) {
//			List<SystemUser> list = users.getList().stream().filter(user -> !user.getUserName().equals("admin"))
//					.collect(Collectors.toList());
//			users.setList(list);
//		}
		return users;
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryUserById.json")
	public DatasetSimple<SystemUser> queryUserById(HttpServletRequest request,
			@ValiParamName("用户ID[userId]") @Required @Min(1) Long userId) {
		return this.systemAuthService.queryUserById(userId);
	}

	@AuthAction
	@RequestMapping("roleManager.json")
	@ResponseBody
	public DatasetList<SystemRole> roleManager(HttpServletRequest request, @Required @Regex("\\d+") String pageNo,
			@Required @Regex("\\d+") String pageSize) {
		return this.systemAuthService.queryAllRolls(pageNo, pageSize);
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryRoleById.json")
	public DatasetSimple<SystemRole> queryRoleById(HttpServletRequest request, Long roleId) {
		if (roleId != 0) {
			DatasetSimple<SystemRole> role = this.systemAuthService.queryRoleById(roleId);
			return role;
		} else {
			SystemRole role = new SystemRole();
			DatasetSimple<SystemRole> result = DatasetBuilder.fromDataSimple(role);
			result.putDataset("ztreeData4Role", this.systemAuthService.resourceTree4Role(roleId));
			return result;
		}
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryMenuById.json")
	public DatasetSimple<SystemMenu> queryMenuById(HttpServletRequest request,
			@ValiParamName("菜单ID[menuId]") @Required @Min(1) Long menuId) {
		return this.systemAuthService.queryMenuById(menuId);
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryPageById.json")
	public DatasetSimple<SystemPage> queryPageById(HttpServletRequest request,
			@ValiParamName("页面ID[pageId]") @Required @Min(1) Long pageId) {
		return this.systemAuthService.queryPageById(pageId);
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("queryActionById.json")
	public DatasetSimple<SystemAction> queryActionById(HttpServletRequest request,
			@ValiParamName("资源ID[actionId]") @Required @Min(1) Long actionId) {
		return this.systemAuthService.queryActionById(actionId);
	}

	@AuthAction
	@RequestMapping("resourceManager.html")
	public String resourceManager(HttpServletRequest request, Map<String, Object> dataMap) {
		DatasetSimple<ResourceTree> resourceTreeData = this.systemAuthService.queryAllResources();
		dataMap.put("resourceTree", resourceTreeData);
		dataMap.put("ztreeData", JacksonHelper.toJSON(this.systemAuthService.resourceTree4Role(0L)));
		return "/auth/resourceManager";
	}

	@AuthAction
	@ResponseBody
	@RequestMapping("updatePassword.json")
	public DatasetSimple<Boolean> updatePassword(HttpServletRequest request,
			@ValiParamName("旧密码[oldPassword]") @Required String oldPassword,
			@ValiParamName("新密码[password]") @Required @Length(min = 5, max = 20) String password) {
		return this.systemAuthService.updatePassword(this.getUserId(request,AuthUtil.CAR_ADMIN_TOKEN), oldPassword, password);
	}

	/**
	 * 新增系统用户
	 * 
	 * @param request
	 * @param systemUser
	 * @param userName
	 * @param password
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("addUser.json")
	public DatasetSimple<SystemUser> addSystemUser(HttpServletRequest request, SystemUser systemUser,
			@ValiParamName("用户名[userName]") @Required @NotEmpty @Length(min = 3, max = 20) String userName,
			@ValiParamName("密码[password]") @Required @NotEmpty @Length(min = 5, max = 20) String password,
			@Required @Regex("\\d+") String roleId) {
		return this.systemAuthService.addUser(systemUser, Long.parseLong(roleId));
	}

	/**
	 * 删除系统用户
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("deleteUser.json")
	public DatasetSimple<Boolean> deleteSystemUser(HttpServletRequest request,
			@ValiParamName("用户ID[userId]") @Required @Min(1) Long userId) {
		if(userId==1){
			return DatasetBuilder.fromMessageSimple("管理员无法删除！");
		}
		return this.systemAuthService.deleteUser(userId);
	}

	/**
	 * 更新系统用户
	 * 
	 * @param request
	 * @param systemUser
	 * @param userId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updateUser.json")
	public DatasetSimple<SystemUser> updateSystemUser(HttpServletRequest request, SystemUser systemUser,
			@ValiParamName("用户名[userName]") @Length(min = 3, max = 20) String userName,
			@ValiParamName("密码[password]") @Length(min = 5, max = 20) String password,
			@ValiParamName("用户ID[id]") @Required @Min(1) Long id,
			@Required @Regex("\\d+") String roleId) {
		if(id==1){
			return DatasetBuilder.fromMessageSimple("管理员无法修改！");
		}
		return this.systemAuthService.updateUser(systemUser,Long.parseLong(roleId));
	}

	/**
	 * 新增系统角色
	 * 
	 * @param request
	 * @param systemRole
	 * @param name
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("addRole.json")
	public DatasetSimple<SystemRole> addSystemRole(HttpServletRequest request, SystemRole systemRole,
			@Required @Regex("^(\\d+(\\,\\d+)*)?$") String pageIds, String actionIds) {
		return this.systemAuthService.addRole(systemRole, pageIds, actionIds);
	}

	/**
	 * 删除系统角色
	 * 
	 * @param request
	 * @param roleId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("deleteRole.json")
	public DatasetSimple<Boolean> deleteSystemRole(HttpServletRequest request,
			@ValiParamName("角色") @Required @Min(1) Long roleId) {
		return this.systemAuthService.deleteRole(roleId);
	}

	/**
	 * 更新系统角色
	 * 
	 * @param request
	 * @param systemRole
	 * @param id
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updateRole.json")
	public DatasetSimple<SystemRole> updateSystemRole(HttpServletRequest request, SystemRole systemRole,
			@ValiParamName("角色名称") @Required @NotBlank String name,
			@Required @Regex("^(\\d+(\\,\\d+)*)?$") String pageIds, String actionIds) {
		return this.systemAuthService.updateRole(systemRole, pageIds, actionIds);
	}

	/**
	 * 重新分配角色的资源
	 * 
	 * @param request
	 * @param roleId
	 * @param actionIds
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updateRoleResources.json")
	public DatasetSimple<Boolean> updateRoleResources(HttpServletRequest request,
			@ValiParamName("角色ID[roleId]") @Required @Min(1) Long roleId,
			@ValiParamName("页面IDs[pageIds]") @Required @Regex("^(\\d+(\\,\\d+)*)?$") String pageIds,
			@ValiParamName("角色") @Required String actionIds) {
		return this.systemAuthService.updateRoleActions(roleId, pageIds, actionIds);
	}

	/**
	 * 新增系统菜单
	 * 
	 * @param request
	 * @param systemMenu
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("addMenu.json")
	public DatasetSimple<SystemMenu> addMenu(HttpServletRequest request, SystemMenu systemMenu,
			@ValiParamName("菜单名称[name]") @Required @NotBlank String name) {
		return this.systemAuthService.addMenu(systemMenu);
	}

	/**
	 * 新增系统页面
	 * 
	 * @param request
	 * @param systemPage
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("addPage.json")
	public DatasetSimple<SystemPage> addPage(HttpServletRequest request, SystemPage systemPage,
			@ValiParamName("页面名称[name]") @Required @NotBlank String name,
			@ValiParamName("页面URL[pageUrl]") @Required @NotBlank String pageUrl,
			@ValiParamName("菜单ID[menuId]") @Required @Min(1) Long menuId) {
		return this.systemAuthService.addPage(systemPage);
	}

	/**
	 * 新增系统资源
	 * 
	 * @param request
	 * @param systemAction
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("addAction.json")
	public DatasetSimple<SystemAction> addAction(HttpServletRequest request, SystemAction systemAction,
			@ValiParamName("资源名称[name]") @Required @NotEmpty String name,
			@ValiParamName("资源URL[actionUrl]") @Required @NotBlank String actionUrl,
			@ValiParamName("页面ID[pageId]") @Required @Min(1) Long pageId) {
		return this.systemAuthService.addAction(systemAction);
	}

	/**
	 * 删除系统菜单
	 * 
	 * @param request
	 * @param menuId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("deleteMenu.json")
	public DatasetSimple<Boolean> deleteMenu(HttpServletRequest request,
			@ValiParamName("菜单ID[menuId]") @Required @Min(1) Long menuId) {
		return this.systemAuthService.deleteMenu(menuId);
	}

	/**
	 * 删除系统页面
	 * 
	 * @param request
	 * @param pageId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("deletePage.json")
	public DatasetSimple<Boolean> deletePage(HttpServletRequest request,
			@ValiParamName("页面ID[pageId]") @Required @Min(1) Long pageId) {
		return this.systemAuthService.deletePage(pageId);
	}

	/**
	 * 删除系统资源
	 * 
	 * @param request
	 * @param actionId
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("deleteAction.json")
	public DatasetSimple<Boolean> deleteAction(HttpServletRequest request,
			@ValiParamName("资源ID[actionId]") @Required @Min(1) Long actionId) {
		return this.systemAuthService.deleteAction(actionId);
	}

	/**
	 * 更新系统菜单
	 * 
	 * @param request
	 * @param systemMenu
	 * @param id
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updateMenu.json")
	public DatasetSimple<SystemMenu> updateMenu(HttpServletRequest request, SystemMenu systemMenu,
			@ValiParamName("菜单名称[name]") @Required @NotBlank String name,
			@ValiParamName("菜单ID[id]") @Required @Min(1) Long id) {
		return this.systemAuthService.updateMenu(systemMenu);
	}

	/**
	 * 删除系统页面
	 * 
	 * @param request
	 * @param systemPage
	 * @param id
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updatePage.json")
	public DatasetSimple<SystemPage> updatePage(HttpServletRequest request, SystemPage systemPage,
			@ValiParamName("页面名称[name]") @Required @NotBlank String name,
			@ValiParamName("菜单ID[menuId]") @Required @Min(1) Long menuId,
			@ValiParamName("页面URL[pageUrl]") @Required @NotBlank String pageUrl,
			@ValiParamName("页面ID[id]") @Required @Min(1) Long id) {
		return this.systemAuthService.updatePage(systemPage);
	}

	/**
	 * 删除系统资源
	 * 
	 * @param request
	 * @param systemAction
	 * @param id
	 * @return
	 */
	@AuthAction
	@ResponseBody
	@RequestMapping("updateAction.json")
	public DatasetSimple<SystemAction> updateAction(HttpServletRequest request, SystemAction systemAction,
			@ValiParamName("资源名称[name]") @Required @NotBlank String name,
			@ValiParamName("页面ID[pageId]") @Required @Min(1) Long pageId,
			@ValiParamName("资源URL[actionUrl]") @Required @NotBlank String actionUrl,
			@ValiParamName("资源ID[id]") @Required @Min(1) Long id) {
		return this.systemAuthService.updateAction(systemAction);
	}

}
