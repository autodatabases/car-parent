package com.emate.shop.business.api;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.vo.ResourceTree;
import com.emate.shop.business.vo.SystemUserAuth;
import com.emate.shop.business.vo.ZTreeNode;
import com.emate.shop.rpc.dto.Dataset;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 系统管理员权限管理
 * dddd
 * 
 * @author caisu
 *
 */
public interface SystemAuthService {

    /**
     * 新增系统菜单
     * 
     * @param systemMenu
     * @return
     */
    public DatasetSimple<SystemMenu> addMenu(SystemMenu systemMenu);

    public DatasetSimple<SystemMenu> updateMenu(SystemMenu systemMenu);

    public DatasetSimple<Boolean> deleteMenu(Long menuId);

    /**
     * 新增系统页面
     * 
     * @param systemPage
     * @return
     */
    public DatasetSimple<SystemPage> addPage(SystemPage systemPage);

    public DatasetSimple<SystemPage> updatePage(SystemPage systemPage);

    public DatasetSimple<Boolean> deletePage(Long pageId);

    /**
     * 新增系统页面操作按钮
     * 
     * @param systemAction
     * @return
     */
    public DatasetSimple<SystemAction> addAction(SystemAction systemAction);

    public DatasetSimple<SystemAction> updateAction(SystemAction systemAction);

    public DatasetSimple<Boolean> deleteAction(Long actionId);

    /**
     * 新增系统角色
     * 
     * @param systemRole
     * @param userIds 
     * @return
     */
    public DatasetSimple<SystemRole> addRole(SystemRole systemRole,String pageIds,String actionIds);

    public DatasetSimple<SystemRole> updateRole(SystemRole systemRole,String pageIds,String actionIds);

    public DatasetSimple<Boolean> deleteRole(Long roleId);

    /**
     * 新增系统用户
     * 
     * @param systemUser
     * @return
     */
    public DatasetSimple<SystemUser> addUser(SystemUser systemUser,Long roleId);

    public DatasetSimple<SystemUser> updateUser(SystemUser systemUser,Long roleId);

    public DatasetSimple<Boolean> deleteUser(Long userId);

    /**
     * 系统角色批量添加操作按钮,删除原有的所有关系后再添加
     * 
     * @param roleId
     * @param actionIds
     * @return
     */
    //    public DatasetSimple<Boolean> addSystemRoleActionRelations(Long roleId,
    //            String actionIds);

    /**
     * 系统用户批量添加角色,删除原有的所有关系后再添加
     * 
     * @param userId
     * @param roleIds
     * @return
     */
    public DatasetSimple<Boolean> updateUserRoles(Long userId, String roleIds);

    /**
     * 登陆并查询用户可访问资源
     * 
     * @param userName
     * @param password
     * @return
     */
    public DatasetSimple<SystemUserAuth> index(Long userId);

    /**
     * 根据登陆用户和页面来查询用户资源
     * @param pageId
     * @param userId
     * @return
     */
    public DatasetList<SystemAction> queryAllActionByPageIdAndUserId(
            Long pageId, Long userId);

    /**
     * 查询所有的用户
     * @return
     */
    public DatasetList<SystemUser> queryAllusers(String pageNo,String pageSize);

    /**
     * 查询所有的角色
     * @return
     */
    public DatasetList<SystemRole> queryAllRolls(String pageNo,String pageSize);

//    /**
//     * @param userId
//     * @return
//     */
//    public Dataset<String, SystemRole> queryAllRoles4User(Long userId);

    /**
     * @param roleId
     * @return
     */
    public Dataset<String, SystemAction> queryAllActions4Role(Long roleId);

    /**
     * @param roleId
     * @param actionIds
     * @param actionIds2 
     * @return
     */
    public DatasetSimple<Boolean> updateRoleActions(Long roleId, String pageIds,
            String actionIds);

    /**
     * 查询所有的资源
     * @return
     */
    public DatasetSimple<ResourceTree> queryAllResources();

    /**
     * @param userName
     * @param password
     * @return
     */
    public DatasetSimple<SystemUser> dologin(String userName, String password);

    /**
     * @param userId
     * @return
     */
    public DatasetSimple<SystemUser> queryUserById(Long userId);

    /**
     * @param systemUser
     * @param oldPassword
     * @param password
     * @return
     */
    public DatasetSimple<Boolean> updatePassword(Long userId,
            String oldPassword, String password);

    /**
     * @param roleId
     * @return
     */
    public DatasetSimple<SystemRole> queryRoleById(Long roleId);

    /**
     * @param menuId
     * @return
     */
    public DatasetSimple<SystemMenu> queryMenuById(Long menuId);

    /**
     * @param pageId
     * @return
     */
    public DatasetSimple<SystemPage> queryPageById(Long pageId);

    /**
     * @param actionId
     * @return
     */
    public DatasetSimple<SystemAction> queryActionById(Long actionId);
    
    
    public DatasetList<ZTreeNode> resourceTree4Role(Long roleId);

}
