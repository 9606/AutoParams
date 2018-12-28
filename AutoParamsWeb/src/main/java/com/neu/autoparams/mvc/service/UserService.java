package com.neu.autoparams.mvc.service;

import com.neu.autoparams.mvc.entity.Permission;
import com.neu.autoparams.mvc.entity.Role;
import com.neu.autoparams.mvc.entity.User;
import com.neu.autoparams.mvc.dao.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class UserService {
    @Resource
    UserDao userDao;

    @Resource
    PasswordEncoder passwordEncoder;


    public List<Role> getAllRoleAndPerCount() {
        return userDao.getRoleAndPermCount();
    }

    // 获取某角色的所有权限
    // +判断 原因：角色无权限时,返回1条数据 并值均为null
    public List<Permission> getPermission(int roleId) {
        List<Permission> permissions = userDao.getPermissionByRoleId(roleId);
        return permissions;
    }

    //获取角色 根据roleName
    public Role getRoleByName(String roleName) {
        return userDao.getRoleByName(roleName);
    }

    //角色信息 和拥有的权限集合
    public Role getRoleAndPermissions(int roleId) {
        //1.获取角色
        Role role = userDao.getRoleById(roleId);
        if (role == null) {
            return null;
        }
        //2.获取权限
        List<Permission> rolePermissions = userDao.getPermissionByRoleId(roleId);
        role.setPermissions(rolePermissions);
        return role;
    }

    //查询所有权限
    public List<Permission> getPermissions() {
        return userDao.getPermissions();
    }

    //修改角色信息 及 权限
    @Transactional
    public boolean updateRoleAndRolePermissions(Role role) {
        userDao.updateRole(role);
        //删除
        userDao.deleteRolePermissions(role.getId());

        if (role.getPermissionIds() == null) {
            return true;
        }
        //添加角色的权限
        Role roleByName = userDao.getRoleById(role.getId());
        if (roleByName == null) {
            return false;
        }
        userDao.batchInsertRoleAndPermissions(role.getId(), role.getPermissionIds());
        return true;
    }

    //添加角色信息
    @Transactional
    public boolean saveRole(Role role) {
        int insertId = userDao.saveRole(role);
        if (role.getPermissionIds() == null) {
            return true;
        }
        //添加角色的权限
        Role roleByName = userDao.getRoleByName(role.getName());
        if (roleByName == null) {
            return false;
        }
        userDao.batchInsertRoleAndPermissions(insertId, role.getPermissionIds());
        return true;
    }

    //删除角色
    @Transactional
    public void deleteRole(Role role) {
        //1.删除用户-角色
        userDao.deleteRoleUsers(role.getId());
        //2.删除角色-权限
        userDao.deleteRolePermissions(role.getId());
        //3.删除角色表
        userDao.deleteRole(role.getId());
    }

    /**
     * setup初始化用户
     *
     * @param user
     * @return
     */
    @Transactional
    public void register(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userDao.changePwd(password, 1);
        userDao.editState(user.getEnabled(), 1);
    }

    public List<User> getUserList(HttpServletRequest request) {
        return userDao.getUserList();
    }

    public User getUserInfo(int userId) {
        User user = userDao.getUserInfo(userId);
        if (user != null) {
            List<Role> roles = userDao.getRoles(userId);
            List<Permission> permissions = userDao.getPermissions(userId);
            user.setRoles(roles);
            user.setPermissions(permissions);
        }
        return user;
    }

    public User getUserInfoByUserName(String username) {
        User user = userDao.getUserByUserName(username);
        if (user != null) {
            List<Role> roles = userDao.getRoles(user.getUserId());
            user.setRoles(roles);
        }
        return user;
    }

    /**
     * 新建用户
     *
     * @param user
     * @return
     */
    @Transactional
    public boolean saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        int userId = userDao.saveUser(user);
        if (user.getRoleId() == 0) {
            return true;
        }
        userDao.addUserRole(userId, user.getRoleId());
        return true;
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    @Transactional
    public boolean editUser(User user) {
        userDao.updateUser(user.getUserId(), user.getNickname(), user.getEmail(), user.getTelephone());
        userDao.deleteUserRoles(user.getUserId());
        userDao.addUserRole(user.getUserId(), user.getRoleId());
        return true;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Transactional
    public boolean editUserInfo(User user) {
        userDao.updateUser(user.getUserId(), user.getNickname(), user.getEmail(), user.getTelephone());
        return true;
    }


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Transactional
    public boolean deleteUser(int userId) {
        userDao.deleteUser(userId);
        userDao.deleteUserRoles(userId);
        return true;
    }

    /**
     * 启用、禁用
     *
     * @param state
     * @param userId
     * @return
     */
    @Transactional
    public boolean editState(int state, int userId) {
        userDao.editState(state, userId);
        return true;
    }

    /**
     * 修改、重置密码
     *
     * @param newPwd
     * @param userId
     * @return
     */
    @Transactional
    public boolean changePwd(String newPwd, int userId) {
        userDao.changePwd(passwordEncoder.encode(newPwd), userId);
        return true;
    }

    public void updateLastLoginTime(String username) {
        userDao.updateLastLoginTime(username);
    }

}

