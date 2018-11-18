package com.neu.autoparams.mvc.dao;

import com.neu.autoparams.entity.Permission;
import com.neu.autoparams.entity.Role;
import com.neu.autoparams.entity.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@Service
public class UserDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    private static final String findRoleAndPermCountQuery = "select a.R_ID,a.R_NAME,a.R_DESC,count(c.p_id) as PERMCOUNT from roles a left join role_perm_rela b on a.r_id = b.r_id left join permission c on b.p_id = c.p_id group by a.r_id,a.r_name,a.r_desc;";
    private static final String findRoleByNameQuery = "select * from roles where r_name = ?;";
    private static final String findPermissionQuery = "select c.p_id,c.p_name,c.p_displayname,c.p_desc from roles a left join role_perm_rela b on a.r_id = b.r_id left join permission c on b.p_id = c.p_id where a.r_id = ?;";
    private static final String findRoleQuery = "select * from roles where r_id = ?;";
    private static final String findPermissionsQuery = "select * from permission";
    private static final String deleteRolePermissions = "delete from role_perm_rela where r_id = ?";
    private static final String insertRolePermissions = "insert into role_perm_rela (r_id,p_id) values (?,?);";
    private static final String insertRole = "insert into roles (r_name,r_desc) values (?,?)";
    private static final String updateRole = "update roles set r_name = ?, r_desc = ? where r_id = ?;";
    private static final String deleteRole = "delete from roles where r_id = ?;";
    private static final String deleteRoleUsers = "delete from user_role_rela where r_id = ?;";

    private static final String findUserListQuery = "select fu.* from users fu";
    private static final String register = "insert into users(username, password, nickname,enabled,create_time,update_time) values (?, ? ,?, ?, now(), now())";
    private static final String insertUser = "insert into users(username, password, nickname, email, telephone, create_time) values (?, ?, ?, ?, ?, now())";
    private static final String editUser = "update users set nickname=?,email=?,telephone=?,update_time=now() where u_id=?";
    private static final String deleteUser = "delete from users where u_id=?";
    private static final String deleteUserRoles = "delete from user_role_rela where u_id=?";
    private static final String changeState = "update users set enabled=? where u_id=?";
    private static final String changePwd = "update users set password=? where u_id=?";

    private static final String findUserByUserNameQuery = "select fu.* from users fu where fu.username=?";
    private static final String findUserQuery= "select fu.* from users fu where fu.u_id=?";

    private static final String addUserRoleQuery = "insert into user_role_rela(u_id,r_id) values(?, ?)";
    private static final String findRolesByUserIdQuery = "select fr.* from roles fr left join user_role_rela furr on furr.r_id=fr.r_id left join users fu on fu.u_id=furr.u_id where fu.u_id=?";
    private static final String findPermissionsByUserIdQuery = "select distinct e.* from users a, user_role_rela b, roles c, role_perm_rela d, permission e where a.u_id = b.u_id and b.r_id = c.r_id and b.r_id = d.r_id and d.p_id = e.p_id and a.u_id=?";
    private static final String updateLastLoginTimeByUserName = "update users set last_time=now() where username=?";

    //获取所有角色 和角色所拥有的权限个数
    public List<Role> getRoleAndPermCount() {
        return jdbcTemplate.query(findRoleAndPermCountQuery,Role.getRowMapperAndPerCont());
    }

    //获取角色 通过角色name
    public Role getRoleByName(String name) {
        List<Role> roles = jdbcTemplate.query(findRoleByNameQuery, new Object[]{name},Role.getRowMapper());
        if (roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

    //获取角色拥有的全部权限
    public List<Permission> getPermissionByRoleId(int roleId) {
        List<Permission> permissions = jdbcTemplate.query(findPermissionQuery, new Object[]{roleId},Permission.getRowMapper());
        if(permissions.size() == 1 && permissions.get(0).getId() == 0){
            return new ArrayList<>();
        }
        return permissions;
    }

    public Role getRoleById(int id) {
        List<Role> roles = jdbcTemplate.query(findRoleQuery, new Object[]{id},Role.getRowMapper());
        if (roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

    public List<Permission> getPermissions() {
        return jdbcTemplate.query(findPermissionsQuery, Permission.getRowMapper());
    }

    //删除角色所有权限
    public int deleteRolePermissions(int roleId) {
        return jdbcTemplate.update(deleteRolePermissions, roleId);
    }

    //角色添加权限
    public int insertRoleAndPermissions(String roleId, String permissionId) {
        return jdbcTemplate.update(insertRolePermissions, roleId, permissionId);
    }

    //批量添加角色的权限
    public void batchInsertRoleAndPermissions(int roleId,List<Integer> permissionIds) {
        jdbcTemplate.batchUpdate(insertRolePermissions, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, roleId);
                preparedStatement.setInt(2, permissionIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return permissionIds.size();
            }
        });
    }

    //添加角色信息
    public int saveRole(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertRole, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());
            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    //修改角色信息
    public int updateRole(Role role) {
        return jdbcTemplate.update(updateRole, role.getName(),role.getDescription(), role.getId());
    }

    //删除角色
    public int deleteRole(int roleId) {
        return jdbcTemplate.update(deleteRole, roleId);
    }

    //删除用户的指定角色(删除user-role-rel有关某一角色)
    public int deleteRoleUsers(int roleId) {
        return jdbcTemplate.update(deleteRoleUsers, roleId);
    }

    public List<User> getUserList() {
        return jdbcTemplate.query(findUserListQuery, User.getRowMapper());
    }

    public User getUserByUserName(String username) {
        List<User> user = jdbcTemplate.query(findUserByUserNameQuery, new Object[]{username}, User.getRowMapper());
        if (user == null || user.size() == 0)
            return null;
        return user.get(0);
    }

    public User getUserInfo(int userId) {
        List<User> user = jdbcTemplate.query(findUserQuery, new Object[]{userId}, User.getRowMapper());
        if (user == null || user.size() == 0)
            return null;
        return user.get(0);
    }

    public int register(String username, String password, String nickname, int enabled) {
        return jdbcTemplate.update(register, username, password, nickname, enabled);
    }

    public int saveUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps= connection.prepareStatement(insertUser,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getNickname());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getTelephone());
            return ps;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    public int updateUser(int userId, String nickname, String email, String tel) {
        return jdbcTemplate.update(editUser, nickname, email, tel, userId);
    }

    public int deleteUser(int userId) {
        return jdbcTemplate.update(deleteUser, userId);
    }

    public int deleteUserRoles(int userId) {
        return jdbcTemplate.update(deleteUserRoles, userId);
    }

    public void addUserRole(int userId, Integer roleId) {
        jdbcTemplate.update(addUserRoleQuery,new Object[]{userId,roleId});
    }

    public List<Role> getRoles(int userId) {
        return jdbcTemplate.query(findRolesByUserIdQuery, new Object[]{userId},Role.getRowMapper());
    }

    public List<Permission> getPermissions(int userId) {
        return jdbcTemplate.query(findPermissionsByUserIdQuery, new Object[]{userId},Permission.getRowMapper());
    }

    public int editState(int state, int userId) {
        return jdbcTemplate.update(changeState, state, userId);
    }

    public int changePwd(String newPwd, int userId) {
        return jdbcTemplate.update(changePwd, newPwd, userId);
    }

    public void updateLastLoginTime(String username) {
        jdbcTemplate.update(updateLastLoginTimeByUserName, username);
    }


}
