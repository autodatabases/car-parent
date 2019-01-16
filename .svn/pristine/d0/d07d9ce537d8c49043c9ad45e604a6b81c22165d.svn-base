package com.emate.shop.business.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.SystemAuthService;
import com.emate.shop.business.check.SystemDeleteCheck;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.helper.EntitySeqHelper;
import com.emate.shop.business.helper.SystemAuthDuplicateHelper;
import com.emate.shop.business.helper.SystemAuthRelaHelper;
import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemActionExample;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemMenuExample;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemPageExample;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemRoleActionRelation;
import com.emate.shop.business.model.SystemRoleActionRelationExample;
import com.emate.shop.business.model.SystemRoleExample;
import com.emate.shop.business.model.SystemRolePageRelation;
import com.emate.shop.business.model.SystemRolePageRelationExample;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.SystemUserExample;
import com.emate.shop.business.model.SystemUserRoleRelation;
import com.emate.shop.business.model.SystemUserRoleRelationExample;
import com.emate.shop.business.vo.ResourceTree;
import com.emate.shop.business.vo.SystemUserAuth;
import com.emate.shop.business.vo.ZTreeNode;
import com.emate.shop.common.NumberHelper;
import com.emate.shop.common.StringHelper;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.exception.ParameterException;
import com.emate.shop.mapper.SystemActionMapper;
import com.emate.shop.mapper.SystemMenuMapper;
import com.emate.shop.mapper.SystemPageMapper;
import com.emate.shop.mapper.SystemRoleActionRelationMapper;
import com.emate.shop.mapper.SystemRoleMapper;
import com.emate.shop.mapper.SystemRolePageRelationMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.SystemUserRoleRelationMapper;
import com.emate.shop.rpc.dto.Dataset;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class SystemAuthServiceImpl implements SystemAuthService,
        SystemAuthDuplicateHelper, EntitySeqHelper, SystemAuthRelaHelper {

    @Resource
    private SystemMenuMapper               systemMenuMapper;

    @Resource
    private SystemPageMapper               systemPageMapper;

    @Resource
    private SystemActionMapper             systemActionMapper;

    @Resource
    private SystemRoleMapper               systemRoleMapper;

    @Resource
    private SystemUserMapper               systemUserMapper;

    @Resource
    private SystemRoleActionRelationMapper systemRoleActionRelationMapper;

    @Resource
    private SystemRolePageRelationMapper   systemRolePageRelationMapper;

    @Resource
    private SystemUserRoleRelationMapper   systemUserRoleRelationMapper;

    @Resource
    private PasswordEncoder                passwordEncoder;

    @Resource
    private SystemDeleteCheck              systemDeleteCheck;
    
    public static void main(String[] args) {
    	PasswordEncoder pass = new org.springframework.security.crypto.password.StandardPasswordEncoder("emate");
    	System.out.println(pass.encode("ematecar2018"));//ematecar2018
    	System.out.println(pass.matches("lg68730457", "97f70b36ad7bfda4535bb3b2c3c488e4f1694873a4e1481afa9f0a8e78b9d7e3bf8d1e1f2f00f07a"));
    	System.out.println(pass.matches("lg6873045", "e66f69480721895a047029f9cdbefa698ab0463d9d43c39bb14ba20eca6c0e729311523d586d35d9"));
    }
    
    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemMenu> addMenu(SystemMenu systemMenu) {
        if (this.isMenuNameDuplicate(systemMenu.getName(),
                this.systemMenuMapper)) {
            throw new BusinessException("系统菜单名称重复");
        }
        systemMenu.setSeq(this.nextMenuSeq(this.systemMenuMapper));
        int cnt = this.systemMenuMapper.insertSelective(systemMenu);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统菜单失败");
        }
        systemMenu = this.systemMenuMapper
                .selectByPrimaryKey(systemMenu.getId());
        if (Objects.isNull(systemMenu)) {
            throw new BusinessException("新增系统菜单失败");
        }
        return DatasetBuilder.fromDataSimple(systemMenu);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemMenu> updateMenu(SystemMenu systemMenu) {
        if (this.isMenuNameDuplicate(systemMenu.getName(), systemMenu.getId(),
                this.systemMenuMapper)) {
            throw new BusinessException("系统菜单名称重复");
        }
        int cnt = this.systemMenuMapper.updateByPrimaryKeySelective(systemMenu);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("更新系统菜单失败");
        }
        systemMenu = this.systemMenuMapper
                .selectByPrimaryKey(systemMenu.getId());
        if (Objects.isNull(systemMenu)) {
            throw new BusinessException("更新系统菜单失败");
        }
        return DatasetBuilder.fromDataSimple(systemMenu);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> deleteMenu(Long menuId) {
        this.systemDeleteCheck.menuDeleteCheck(menuId);
        int cnt = this.systemMenuMapper.deleteByPrimaryKey(menuId);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("删除系统菜单失败");
        }
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemPage> addPage(SystemPage systemPage) {
        if (this.isPageNameDuplicate(systemPage.getName(),
                this.systemPageMapper)) {
            throw new BusinessException("系统菜单名称重复");
        }
        systemPage.setSeq(this.nextPageSeq(systemPage.getMenuId(),
                this.systemPageMapper));
        int cnt = this.systemPageMapper.insertSelective(systemPage);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统页面失败");
        }
        systemPage = this.systemPageMapper
                .selectByPrimaryKey(systemPage.getId());
        if (Objects.isNull(systemPage)) {
            throw new BusinessException("新增系统页面失败");
        }
        return DatasetBuilder.fromDataSimple(systemPage);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemPage> updatePage(SystemPage systemPage) {
        if (this.isPageNameDuplicate(systemPage.getName(), systemPage.getId(),
                this.systemPageMapper)) {
            throw new BusinessException("系统页面名称重复");
        }
        int cnt = this.systemPageMapper.updateByPrimaryKeySelective(systemPage);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("更新系统页面失败");
        }
        systemPage = this.systemPageMapper
                .selectByPrimaryKey(systemPage.getId());
        if (Objects.isNull(systemPage)) {
            throw new BusinessException("更新系统页面失败");
        }
        return DatasetBuilder.fromDataSimple(systemPage);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> deletePage(Long pageId) {
        this.systemDeleteCheck.pageDeleteCheck(pageId);
        int cnt = this.systemPageMapper.deleteByPrimaryKey(pageId);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("删除系统页面失败");
        }
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemAction> addAction(SystemAction systemAction) {
        if (this.isActionNameDuplicate(systemAction.getName(),
                systemAction.getPageId(), this.systemActionMapper)) {
            throw new BusinessException("系统资源名称重复");
        }
        systemAction.setSeq(this.nextActionSeq(systemAction.getPageId(),
                this.systemActionMapper));
        int cnt = this.systemActionMapper.insertSelective(systemAction);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统资源失败");
        }
        systemAction = this.systemActionMapper
                .selectByPrimaryKey(systemAction.getId());
        if (Objects.isNull(systemAction)) {
            throw new BusinessException("新增系统资源失败");
        }
        return DatasetBuilder.fromDataSimple(systemAction);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemAction> updateAction(SystemAction systemAction) {
        if (this.isActionNameDuplicate(systemAction.getName(),
                systemAction.getId(), systemAction.getPageId(),
                this.systemActionMapper)) {
            throw new BusinessException("系统资源名称重复");
        }
        int cnt = this.systemActionMapper
                .updateByPrimaryKeySelective(systemAction);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("更新系统资源失败");
        }
        systemAction = this.systemActionMapper
                .selectByPrimaryKey(systemAction.getId());
        if (Objects.isNull(systemAction)) {
            throw new BusinessException("更新系统资源失败");
        }
        return DatasetBuilder.fromDataSimple(systemAction);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> deleteAction(Long actionId) {
        this.systemDeleteCheck.actionDeleteCheck(actionId);
        int cnt = this.systemActionMapper.deleteByPrimaryKey(actionId);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("删除系统资源失败");
        }
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemRole> addRole(SystemRole systemRole,String pageIds,String actionIds) {
        if (this.isRoleNameDuplicate(systemRole.getName(),
                this.systemRoleMapper)) {
            throw new BusinessException("系统角色名称重复");
        }
        systemRole.setCreateTime(new Date());
        systemRole.setUpdateTime(new Date());
        int cnt = this.systemRoleMapper.insertSelective(systemRole);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统角色失败");
        }
        systemRole = this.systemRoleMapper
                .selectByPrimaryKey(systemRole.getId());
        if (Objects.isNull(systemRole)) {
            throw new BusinessException("新增系统角色失败");
        }
        Long roleId = systemRole.getId();
//        //新增所有选择的关联信息
//        Stream.of(userIds).filter(Objects::nonNull)
//                .flatMap(StringHelper::splitByComma)
//                .filter(StringHelper::notBlank).map(Long::parseLong).distinct()
//                .forEach(userId -> {
//                    SystemUserRoleRelation relation = new SystemUserRoleRelation();
//                    relation.setRoleId(roleId);
//                    relation.setUserId(userId);
//                    this.systemUserRoleRelationMapper.insertSelective(relation);
//                });
        Stream.of(pageIds).filter(StringHelper::notBlank)
        .flatMap(StringHelper::splitByComma)
        .filter(StringHelper::isInteger).map(Long::parseLong)
        .map(pageId -> {
            SystemRolePageRelation relation = new SystemRolePageRelation();
            relation.setRoleId(roleId);
            relation.setPageId(pageId);
            return relation;
        }).forEach(this.systemRolePageRelationMapper::insertSelective);
		Stream.of(actionIds).filter(StringHelper::notBlank)
		        .flatMap(StringHelper::splitByComma)
		        .filter(StringHelper::isInteger).map(Long::parseLong)
		        .map(actionId -> {
		            SystemRoleActionRelation relation = new SystemRoleActionRelation();
		            relation.setActionId(actionId);
		            relation.setRoleId(roleId);
		            return relation;
		        })
        .forEach(this.systemRoleActionRelationMapper::insertSelective);
        return this.queryRoleById(roleId);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemRole> updateRole(SystemRole systemRole,String pageIds,String actionIds) {
        if (this.isRoleNameDuplicate(systemRole.getName(), systemRole.getId(),
                this.systemRoleMapper)) {
            throw new BusinessException("系统角色名称重复");
        }
        systemRole.setUpdateTime(new Date());
        int cnt = this.systemRoleMapper.updateByPrimaryKeySelective(systemRole);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("更新系统角色失败");
        }
        systemRole = this.systemRoleMapper
                .selectByPrimaryKey(systemRole.getId());
        if (Objects.isNull(systemRole)) {
            throw new BusinessException("更新系统角色失败");
        }
        Long roleId = systemRole.getId();
        SystemRolePageRelationExample rolePageRelationExample = new SystemRolePageRelationExample();
        rolePageRelationExample.or().andRoleIdEqualTo(roleId);
        this.systemRolePageRelationMapper
                .deleteByExample(rolePageRelationExample);
        Stream.of(pageIds).filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByComma)
                .filter(StringHelper::isInteger).map(Long::parseLong)
                .map(pageId -> {
                    SystemRolePageRelation relation = new SystemRolePageRelation();
                    relation.setRoleId(roleId);
                    relation.setPageId(pageId);
                    return relation;
                }).forEach(this.systemRolePageRelationMapper::insertSelective);
        SystemRoleActionRelationExample example = new SystemRoleActionRelationExample();
        example.or().andRoleIdEqualTo(roleId);
        this.systemRoleActionRelationMapper.deleteByExample(example);
        Stream.of(actionIds).filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByComma)
                .filter(StringHelper::isInteger).map(Long::parseLong)
                .map(actionId -> {
                    SystemRoleActionRelation relation = new SystemRoleActionRelation();
                    relation.setActionId(actionId);
                    relation.setRoleId(roleId);
                    return relation;
                })
                .forEach(this.systemRoleActionRelationMapper::insertSelective);
//        //删除所有角色用户关联信息
//        SystemUserRoleRelationExample systemUserRoleRelationExample = new SystemUserRoleRelationExample();
//        systemUserRoleRelationExample.or().andRoleIdEqualTo(systemRole.getId());
//        this.systemUserRoleRelationMapper
//                .deleteByExample(systemUserRoleRelationExample);
//        //新增所有选择的关联信息
//        Stream.of(userIds).filter(Objects::nonNull)
//                .flatMap(StringHelper::splitByComma)
//                .filter(StringHelper::notBlank).map(Long::parseLong).distinct()
//                .forEach(userId -> {
//                    SystemUserRoleRelation relation = new SystemUserRoleRelation();
//                    relation.setRoleId(roleId);
//                    relation.setUserId(userId);
//                    this.systemUserRoleRelationMapper.insertSelective(relation);
//                });
        return this.queryRoleById(roleId);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> deleteRole(Long roleId) {
        this.systemDeleteCheck.roleDeleteCheck(roleId);
        int cnt = this.systemRoleMapper.deleteByPrimaryKey(roleId);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("删除系统角色失败");
        }
        SystemRolePageRelationExample rp = new SystemRolePageRelationExample();
        rp.createCriteria().andRoleIdEqualTo(roleId);
        systemRolePageRelationMapper.deleteByExample(rp);
        SystemRoleActionRelationExample ra = new SystemRoleActionRelationExample();
        ra.createCriteria().andRoleIdEqualTo(roleId);
        systemRoleActionRelationMapper.deleteByExample(ra);
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemUser> addUser(SystemUser systemUser,Long roleId) {
        if (this.isUserNameDuplicate(systemUser.getUserName(),
                this.systemUserMapper)) {
            throw new BusinessException("系统用户名称重复");
        }
        systemUser.setPassword(
                this.passwordEncoder.encode(systemUser.getPassword()));
        systemUser.setCreateTime(new Date());
        systemUser.setUpdateTime(new Date());
        int cnt = this.systemUserMapper.insertSelective(systemUser);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统用户失败");
        }
        systemUser = this.systemUserMapper
                .selectByPrimaryKey(systemUser.getId());
        if (Objects.isNull(systemUser)) {
            throw new BusinessException("新增系统用户失败");
        }
        systemUser.setPassword("");
        
        SystemUserRoleRelation userRole = new SystemUserRoleRelation();
        userRole.setUserId(systemUser.getId());
        userRole.setRoleId(roleId);
        userRole.setUpdateTime(new Date());
        userRole.setCreateTime(new Date());
        cnt = systemUserRoleRelationMapper.insertSelective(userRole);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("新增系统用户失败,新增用户角色关联失败！");
        }
        return DatasetBuilder.fromDataSimple(systemUser);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<SystemUser> updateUser(SystemUser systemUser,Long roleId) {
        if (this.isUserNameDuplicate(systemUser.getUserName(),
                systemUser.getId(), this.systemUserMapper)) {
            throw new BusinessException("系统用户名称重复");
        }
        if (Objects.nonNull(systemUser.getPassword())) {
            systemUser.setPassword(
                    this.passwordEncoder.encode(systemUser.getPassword()));
        }
        systemUser.setUpdateTime(new Date());
        int cnt = this.systemUserMapper.updateByPrimaryKeySelective(systemUser);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("更新系统用户失败");
        }
        systemUser = this.systemUserMapper
                .selectByPrimaryKey(systemUser.getId());
        if (Objects.isNull(systemUser)) {
            throw new BusinessException("更新系统用户失败");
        }
        systemUser.setPassword("");
        
        SystemUserRoleRelationExample ex = new SystemUserRoleRelationExample();
        ex.createCriteria().andUserIdEqualTo(systemUser.getId());
        if(roleId != systemUserRoleRelationMapper.selectByExample(ex).get(0).getRoleId()){
        	systemUserRoleRelationMapper.deleteByExample(ex);
        	 SystemUserRoleRelation re = new SystemUserRoleRelation();
        	 re.setUserId(systemUser.getId());
        	 re.setRoleId(roleId);
        	 re.setCreateTime(new Date());
        	 re.setUpdateTime(new Date());
        	 systemUserRoleRelationMapper.insertSelective(re);
        }
        return DatasetBuilder.fromDataSimple(systemUser);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> deleteUser(Long userId) {
        int cnt = this.systemUserMapper.deleteByPrimaryKey(userId);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("删除系统用户失败");
        }
        //删除用户角色关联
        SystemUserRoleRelationExample ex = new SystemUserRoleRelationExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        if(systemUserRoleRelationMapper.deleteByExample(ex)!=1){
        	 throw new BusinessException("删除系统用户角色关联失败！");
        }
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> updateUserRoles(Long userId, String roleIds) {
        SystemUserRoleRelationExample systemUserRoleRelationExample = new SystemUserRoleRelationExample();
        systemUserRoleRelationExample.or().andUserIdEqualTo(userId);
        this.systemUserRoleRelationMapper
                .deleteByExample(systemUserRoleRelationExample);
        Stream.of(roleIds).filter(Objects::nonNull)
                .filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByComma)
                .filter(StringHelper::isInteger).map(Long::parseLong)
                .map(roleId -> {
                    SystemUserRoleRelation relation = new SystemUserRoleRelation();
                    relation.setUserId(userId);
                    relation.setRoleId(roleId);
                    return relation;
                }).forEach(this.systemUserRoleRelationMapper::insertSelective);
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#dologin(java.lang.String, java.lang.String)
     */
    @Read
    @Override
    public DatasetSimple<SystemUser> dologin(String userName, String password) {
        SystemUserExample systemUserExample = new SystemUserExample();
        systemUserExample.or().andUserNameEqualTo(userName);
        List<SystemUser> systemUsers = this.systemUserMapper
                .selectByExample(systemUserExample);
        if (systemUsers.size() == 0) {
            throw new BusinessException("用户名不存在");
        }
        if (systemUsers.size() > 1) {
            throw new BusinessException("用户重名，请联系管理员");
        }
        SystemUser systemUser = systemUsers.get(0);
        boolean isPasswordRight = this.passwordEncoder.matches(password,
                systemUser.getPassword());
        if (!isPasswordRight) {
            throw new BusinessException("用户名或者密码错误");
        }
        systemUser.setPassword("");
        return DatasetBuilder.fromDataSimple(systemUser);
    }

    @Read
    @Override
    public DatasetSimple<SystemUserAuth> index(Long userId) {
        SystemUser systemUser = this.systemUserMapper
                .selectByPrimaryKey(userId);
        if (Objects.isNull(systemUser)) {
            throw new BusinessException("用户不存在");
        }
        systemUser.setPassword("");
        if (!systemUser.getUserName().equals("admin")) {
            //查询user，role关系
            SystemUserRoleRelationExample systemUserRoleRelationExample = new SystemUserRoleRelationExample();
            systemUserRoleRelationExample.or()
                    .andUserIdEqualTo(systemUser.getId());
            List<SystemUserRoleRelation> systemUserRoleRelations = this.systemUserRoleRelationMapper
                    .selectByExample(systemUserRoleRelationExample);
            //查询role
            List<Long> roleIds = systemUserRoleRelations.stream()
                    .map(SystemUserRoleRelation::getRoleId).distinct()
                    .collect(Collectors.toList());
            roleIds.add(0L);
            SystemRoleExample systemRoleExample = new SystemRoleExample();
            systemRoleExample.or().andIdIn(roleIds);
            systemRoleExample.setOrderByClause(SystemRoleExample.NAME_ASC);
            List<SystemRole> systemRoles = this.systemRoleMapper
                    .selectByExample(systemRoleExample);
            //查询role，action关系
            roleIds = systemRoles.stream().map(SystemRole::getId)
                    .collect(Collectors.toList());
            SystemRoleActionRelationExample systemRoleActionRelationExample = new SystemRoleActionRelationExample();
            systemRoleActionRelationExample.or().andRoleIdIn(roleIds);
            List<SystemRoleActionRelation> systemRoleActionRelations = this.systemRoleActionRelationMapper
                    .selectByExample(systemRoleActionRelationExample);
            //查询role，page关系
            SystemRolePageRelationExample systemRolePageRelationExample = new SystemRolePageRelationExample();
            systemRolePageRelationExample.or().andRoleIdIn(roleIds);
            List<SystemRolePageRelation> systemRolePageRelations = this.systemRolePageRelationMapper
                    .selectByExample(systemRolePageRelationExample);
            //查询action
            List<Long> actionIds = systemRoleActionRelations.stream()
                    .map(SystemRoleActionRelation::getActionId).distinct()
                    .collect(Collectors.toList());
            actionIds.add(0L);
            SystemActionExample systemActionExample = new SystemActionExample();
            systemActionExample.setOrderByClause(SystemActionExample.SEQ_ASC);
            systemActionExample.or().andIdIn(actionIds);
            List<SystemAction> systemActions = this.systemActionMapper
                    .selectByExample(systemActionExample);
            //查询page
            actionIds = systemActions.stream().map(SystemAction::getId)
                    .collect(Collectors.toList());
            List<Long> pageIds = systemActions.stream()
                    .map(SystemAction::getPageId).distinct()
                    .collect(Collectors.toList());
            systemRolePageRelations.stream()
                    .map(SystemRolePageRelation::getPageId)
                    .forEach(pageIds::add);
            pageIds.add(0L);
            SystemPageExample systemPageExample = new SystemPageExample();
            systemPageExample.setOrderByClause(SystemPageExample.SEQ_ASC);
            systemPageExample.or().andIdIn(pageIds);
            List<SystemPage> systemPages = this.systemPageMapper
                    .selectByExample(systemPageExample);
            //查询menu
            pageIds = systemPages.stream().map(SystemPage::getId)
                    .collect(Collectors.toList());
            List<Long> menuIds = systemPages.stream().map(SystemPage::getMenuId)
                    .distinct().collect(Collectors.toList());
            menuIds.add(0L);
            SystemMenuExample systemMenuExample = new SystemMenuExample();
            systemMenuExample.setOrderByClause(SystemMenuExample.SEQ_ASC);
            systemMenuExample.or().andIdIn(menuIds);
            List<SystemMenu> systemMenus = this.systemMenuMapper
                    .selectByExample(systemMenuExample);

            SystemUserAuth systemUserAuth = new SystemUserAuth();
            systemUserAuth.setSystemUser(systemUser);
            systemUserAuth.setSystemMenus(systemMenus);
            systemUserAuth.setSystemPages(systemPages);
            systemUserAuth.setSystemActions(systemActions);
            systemUserAuth.setSystemRoles(systemRoles);
            return DatasetBuilder.fromDataSimple(systemUserAuth);
        } else {
            SystemUserAuth systemUserAuth = new SystemUserAuth();
            systemUserAuth.setSystemUser(systemUser);
            systemUserAuth.setSystemMenus(this.queryAllMenus().getList());
            systemUserAuth.setSystemPages(this.queryAllPages().getList());
            systemUserAuth.setSystemActions(this.queryAllActions().getList());
            return DatasetBuilder.fromDataSimple(systemUserAuth);
        }
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryAllActionByPageIdAndUserId(java.lang.Long, java.lang.Long)
     */
    @Read
    @Override
    public DatasetList<SystemAction> queryAllActionByPageIdAndUserId(
            Long pageId, Long userId) {
        //查询user，role关系
        SystemUserRoleRelationExample systemUserRoleRelationExample = new SystemUserRoleRelationExample();
        systemUserRoleRelationExample.or().andUserIdEqualTo(userId);
        List<SystemUserRoleRelation> systemUserRoleRelations = this.systemUserRoleRelationMapper
                .selectByExample(systemUserRoleRelationExample);
        //查询role
        List<Long> roleIds = systemUserRoleRelations.stream()
                .map(SystemUserRoleRelation::getRoleId).distinct()
                .collect(Collectors.toList());
        roleIds.add(0L);
        //查询role，action关系
        SystemRoleActionRelationExample systemRoleActionRelationExample = new SystemRoleActionRelationExample();
        systemRoleActionRelationExample.or().andRoleIdIn(roleIds);
        List<SystemRoleActionRelation> systemRoleActionRelations = this.systemRoleActionRelationMapper
                .selectByExample(systemRoleActionRelationExample);
        //查询action
        List<Long> actionIds = systemRoleActionRelations.stream()
                .map(SystemRoleActionRelation::getActionId).distinct()
                .collect(Collectors.toList());
        actionIds.add(0L);
        SystemActionExample systemActionExample = new SystemActionExample();
        systemActionExample.setOrderByClause(SystemActionExample.SEQ_ASC);
        systemActionExample.or().andIdIn(actionIds);
        List<SystemAction> systemActions = this.systemActionMapper
                .selectByExample(systemActionExample);
        return DatasetBuilder.fromDataList(systemActions);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryallusers()
     */
    @Read
    @Override
    public DatasetList<SystemUser> queryAllusers(String pageNo,String pageSize) {
        SystemUserExample systemUserExample = new SystemUserExample();
        systemUserExample.setOrderByClause(SystemUserExample.ID_ASC);
        PaginationUtil page = new PaginationUtil(Integer.parseInt(pageNo), Integer.parseInt(pageSize), systemUserMapper.countByExample(systemUserExample));
        systemUserExample.setLimitStart(page.getStartRow());
        systemUserExample.setLimitEnd(page.getSize());
        List<SystemUser> systemUsers = this.systemUserMapper
                .selectByExample(systemUserExample);
        systemUsers.stream().forEach(systemUser -> {
        	systemUser.setPassword("");
        	SystemUserRoleRelationExample ex = new SystemUserRoleRelationExample();
        	ex.createCriteria().andUserIdEqualTo(systemUser.getId());
        	List<SystemUserRoleRelation>  l = systemUserRoleRelationMapper.selectByExample(ex);
        	if(!l.isEmpty()){
        		systemUser.setCreatorId(l.get(0).getRoleId());
        		systemUser.setCreator(systemRoleMapper.selectByPrimaryKey(l.get(0).getRoleId()).getName());
        	}
        });
        return DatasetBuilder.fromDataList(systemUsers,page.createPageInfo());
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryallRolls()
     */
    @Read
    @Override
    public DatasetList<SystemRole> queryAllRolls(String pageNo,String pageSize) {
        SystemRoleExample systemRoleExample = new SystemRoleExample();
        systemRoleExample.setOrderByClause(SystemRoleExample.ID_ASC);
        PaginationUtil page = new PaginationUtil(Integer.parseInt(pageNo), Integer.parseInt(pageSize), systemRoleMapper.countByExample(systemRoleExample));
        systemRoleExample.setLimitStart(page.getStartRow());
        systemRoleExample.setLimitEnd(page.getSize());
        return DatasetBuilder.fromDataList(
                this.systemRoleMapper.selectByExample(systemRoleExample),page.createPageInfo());
    }

//    /* (non-Javadoc)
//     * @see com.emate.shop.business.api.SystemAuthService#queryAllRoles4User(java.lang.Long)
//     */
//    @Read
//    @Override
//    public Dataset<String, SystemRole> queryAllRoles4User(Long userId) {
//        SystemUserRoleRelationExample systemUserRoleRelationExample = new SystemUserRoleRelationExample();
//        systemUserRoleRelationExample.or().andUserIdEqualTo(userId);
//        systemUserRoleRelationExample
//                .setOrderByClause(SystemUserRoleRelationExample.ROLE_ID_ASC);
//        List<SystemUserRoleRelation> systemUserRoleRelations = this.systemUserRoleRelationMapper
//                .selectByExample(systemUserRoleRelationExample);
//        List<String> roleIds = systemUserRoleRelations.stream()
//                .map(SystemUserRoleRelation::getRoleId).distinct()
//                .map(String::valueOf).collect(Collectors.toList());
//        return DatasetBuilder.fromAll(StringHelper.concatByComma(roleIds),
//                this.queryAllRolls().getList(), null);
//    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryAllActions4Role(java.lang.Long)
     */
    @Read
    @Override
    public Dataset<String, SystemAction> queryAllActions4Role(Long roleId) {
        SystemRoleActionRelationExample systemRoleActionRelationExample = new SystemRoleActionRelationExample();
        systemRoleActionRelationExample.or().andRoleIdEqualTo(roleId);
        systemRoleActionRelationExample.setOrderByClause(
                SystemRoleActionRelationExample.ACTION_ID_ASC);
        List<SystemRoleActionRelation> systemRoleActionRelations = this.systemRoleActionRelationMapper
                .selectByExample(systemRoleActionRelationExample);
        List<String> actionIds = systemRoleActionRelations.stream()
                .map(SystemRoleActionRelation::getActionId).distinct()
                .map(String::valueOf).collect(Collectors.toList());
        return DatasetBuilder.fromAll(StringHelper.concatByComma(actionIds),
                this.queryAllActions().getList(), null);
    }

    /**
     * @return
     */
    private DatasetList<SystemAction> queryAllActions() {
        SystemActionExample systemActionExample = new SystemActionExample();
        systemActionExample.setOrderByClause(SystemActionExample.SEQ_ASC);
        return DatasetBuilder.fromDataList(
                this.systemActionMapper.selectByExample(systemActionExample));
    }

    /**
     * @return
     */
    private DatasetList<SystemPage> queryAllPages() {
        SystemPageExample systemPageExample = new SystemPageExample();
        systemPageExample.setOrderByClause(SystemPageExample.SEQ_ASC);
        return DatasetBuilder.fromDataList(
                this.systemPageMapper.selectByExample(systemPageExample));
    }

    /**
     * @return
     */
    private DatasetList<SystemMenu> queryAllMenus() {
        SystemMenuExample systemMenuExample = new SystemMenuExample();
        systemMenuExample.setOrderByClause(SystemMenuExample.SEQ_ASC);
        return DatasetBuilder.fromDataList(
                this.systemMenuMapper.selectByExample(systemMenuExample));
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#updateRoleActions(java.lang.Long, java.lang.String)
     */
    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> updateRoleActions(Long roleId, String pageIds,
            String actionIds) {
        SystemRolePageRelationExample rolePageRelationExample = new SystemRolePageRelationExample();
        rolePageRelationExample.or().andRoleIdEqualTo(roleId);
        this.systemRolePageRelationMapper
                .deleteByExample(rolePageRelationExample);
        Stream.of(pageIds).filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByComma)
                .filter(StringHelper::isInteger).map(Long::parseLong)
                .map(pageId -> {
                    SystemRolePageRelation relation = new SystemRolePageRelation();
                    relation.setRoleId(roleId);
                    relation.setPageId(pageId);
                    return relation;
                }).forEach(this.systemRolePageRelationMapper::insertSelective);
        SystemRoleActionRelationExample example = new SystemRoleActionRelationExample();
        example.or().andRoleIdEqualTo(roleId);
        this.systemRoleActionRelationMapper.deleteByExample(example);
        Stream.of(actionIds).filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByComma)
                .filter(StringHelper::isInteger).map(Long::parseLong)
                .map(actionId -> {
                    SystemRoleActionRelation relation = new SystemRoleActionRelation();
                    relation.setActionId(actionId);
                    relation.setRoleId(roleId);
                    return relation;
                })
                .forEach(this.systemRoleActionRelationMapper::insertSelective);
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryAllResources()
     */
    @Read
    @Override
    public DatasetSimple<ResourceTree> queryAllResources() {
        List<SystemAction> actions = this.queryAllActions().getList();
        List<SystemPage> pages = this.queryAllPages().getList();
        List<SystemMenu> menus = this.queryAllMenus().getList();
        ResourceTree resourceTree = new ResourceTree();
        resourceTree.setSystemActions(actions);
        resourceTree.setSystemMenus(menus);
        resourceTree.setSystemPages(pages);
        return DatasetBuilder.fromDataSimple(resourceTree);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryUserById(java.lang.Long)
     */
    @Override
    public DatasetSimple<SystemUser> queryUserById(Long userId) {
        SystemUser systemUser = this.systemUserMapper
                .selectByPrimaryKey(userId);
        if (Objects.isNull(systemUser)) {
            throw new BusinessException("用户不存在");
        }
        systemUser.setPassword("");
        return DatasetBuilder.fromDataSimple(systemUser);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#updatePassword(com.emate.shop.business.model.SystemUser, java.lang.String, java.lang.String)
     */
    @Write
    @Override
    @Transactional
    public DatasetSimple<Boolean> updatePassword(Long userId,
            String oldPassword, String password) {
        SystemUser systemUser = this.systemUserMapper
                .selectByPrimaryKey(userId);
        if (Objects.isNull(systemUser)) {
            throw new BusinessException("用户不存在");
        }
        if (!this.passwordEncoder.matches(oldPassword,
                systemUser.getPassword())) {
            throw new ParameterException("密码错误");
        }
        systemUser.setPassword(this.passwordEncoder.encode(password));
        int cnt = this.systemUserMapper.updateByPrimaryKeySelective(systemUser);
        if (NumberHelper.isNotOne(cnt)) {
            throw new BusinessException("修改密码失败");
        }
        return DatasetBuilder.fromDataSimple(Boolean.TRUE);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryRoleById(java.lang.Long)
     */
    @Read
    @Override
    public DatasetSimple<SystemRole> queryRoleById(Long roleId) {
        SystemRole role = this.systemRoleMapper.selectByPrimaryKey(roleId);
        if (Objects.isNull(role)) {
            throw new BusinessException("查询不到系统角色");
        }
        DatasetSimple<SystemRole> result = DatasetBuilder.fromDataSimple(role);
        result.putDataset("relaPages", this.queryRelaPages(role,
                this.systemRolePageRelationMapper, this.systemPageMapper));
        result.putDataset("relaActions", this.queryRelaActions(role,
                this.systemRoleActionRelationMapper, this.systemActionMapper));
        return result;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryMenuById(java.lang.Long)
     */
    @Read
    @Override
    public DatasetSimple<SystemMenu> queryMenuById(Long menuId) {
        SystemMenu systemMenu = this.systemMenuMapper
                .selectByPrimaryKey(menuId);
        if (Objects.isNull(systemMenu)) {
            throw new BusinessException("查询不到系统菜单");
        }
        return DatasetBuilder.fromDataSimple(systemMenu);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryPageById(java.lang.Long)
     */
    @Read
    @Override
    public DatasetSimple<SystemPage> queryPageById(Long pageId) {
        SystemPage systemPage = this.systemPageMapper
                .selectByPrimaryKey(pageId);
        if (Objects.isNull(systemPage)) {
            throw new BusinessException("查询不到系统页面");
        }
        return DatasetBuilder.fromDataSimple(systemPage);
    }

    /* (non-Javadoc)
     * @see com.emate.shop.business.api.SystemAuthService#queryActionById(java.lang.Long)
     */
    @Read
    @Override
    public DatasetSimple<SystemAction> queryActionById(Long actionId) {
        SystemAction systemAction = this.systemActionMapper
                .selectByPrimaryKey(actionId);
        if (Objects.isNull(systemAction)) {
            throw new BusinessException("查询不到系统资源");
        }
        return DatasetBuilder.fromDataSimple(systemAction);
    }
    
    @Read
    @Override
    public DatasetList<ZTreeNode> resourceTree4Role(Long roleId) {
        SystemRole role = new SystemRole();
        role.setId(Objects.isNull(roleId) ? 0L : roleId);
        Set<Long> selectedPageIds = this
                .queryRelaPages(role, this.systemRolePageRelationMapper,
                        this.systemPageMapper)
                .getList().stream().map(SystemPage::getId)
                .collect(Collectors.toSet());
        Set<Long> selectedActionIds = this
                .queryRelaActions(role, this.systemRoleActionRelationMapper,
                        this.systemActionMapper)
                .getList().stream().map(SystemAction::getId)
                .collect(Collectors.toSet());
        SystemMenuExample menuExample = new SystemMenuExample();
        menuExample.setOrderByClause(SystemMenuExample.SEQ_ASC);
        List<SystemMenu> menus = this.systemMenuMapper.selectByExample(menuExample);
        SystemPageExample pageExample = new SystemPageExample();
        pageExample.setOrderByClause(SystemPageExample.SEQ_ASC);
        List<SystemPage> pages = this.systemPageMapper.selectByExample(pageExample);
        SystemActionExample actionExample = new SystemActionExample();
        actionExample.setOrderByClause(SystemActionExample.SEQ_ASC);
        List<SystemAction> actions = this.systemActionMapper
                .selectByExample(actionExample);
        List<ZTreeNode> actionNodes = actions.stream().map(action -> {
            ZTreeNode node = new ZTreeNode();
            node.setName("[操作]" + action.getName());
            node.setOpen(false);
            node.setEntity(action);
            if (selectedActionIds.contains(action.getId())) {
                node.setChecked(true);
            }
            return node;
        }).collect(Collectors.toList());
        List<ZTreeNode> pageNodes = pages.stream().map(page -> {
            ZTreeNode node = new ZTreeNode();
            node.setName("[页面]" + page.getName());
            node.setOpen(true);
            node.setEntity(page);
            if (selectedPageIds.contains(page.getId())) {
                node.setChecked(true);
            }
            actionNodes
                    .stream().filter(
                            actionNode -> Objects.equals(page.getId(),
                                    ((SystemAction) actionNode.getEntity())
                                            .getPageId()))
                    .forEach(node::addChild);
            return node;
        }).collect(Collectors.toList());
        List<ZTreeNode> menuNodes = menus.stream().map(menu -> {
            ZTreeNode node = new ZTreeNode();
            node.setName("[菜单]" + menu.getName());
            node.setOpen(true);
            node.setEntity(menu);
            pageNodes.stream()
                    .filter(pageNode -> Objects.equals(menu.getId(),
                            ((SystemPage) pageNode.getEntity()).getMenuId()))
                    .forEach(node::addChild);
            node.setNocheck(true);
            return node;
        }).collect(Collectors.toList());
        return DatasetBuilder.fromDataList(menuNodes);
    }
}
