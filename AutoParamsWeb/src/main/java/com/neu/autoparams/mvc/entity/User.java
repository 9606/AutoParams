package com.neu.autoparams.mvc.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Date;
import java.util.List;

public class User {
    public static final String USER_ID_KEY = "u_id";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String NICKNAME_KEY = "nickname";
    public static final String EMAIL_KEY = "email";
    public static final String TELEPHONE_KEY = "telephone";
    public static final String CREATE_TIME_KEY = "create_time";
    public static final String DELETE_TIME_KEY = "delete_time";
    public static final String UPDATE_TIME_KEY = "update_time";
    public static final String LAST_TIME_KEY = "last_time";
    public static final String ENABLED = "enabled";

    private int userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String telephone;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    private Date lastTime;
    private int enabled;
    private int roleId;


    private List<Permission> permissions;

    private List<Integer> roleIds;

    private List<Role> roles;

    private static final RowMapper<User> rowMapper = (resultSet, i) -> {
        User user = new User();
        user.setUserId(resultSet.getInt(USER_ID_KEY));
        user.setUsername(resultSet.getString(USERNAME_KEY));
        user.setNickname(resultSet.getString(NICKNAME_KEY));
        user.setPassword(resultSet.getString(PASSWORD_KEY));
        user.setEmail(resultSet.getString(EMAIL_KEY));
        user.setTelephone(resultSet.getString(TELEPHONE_KEY));
        if (resultSet.getTime(CREATE_TIME_KEY) != null) {
            user.setCreateTime(new Date(resultSet.getTime(CREATE_TIME_KEY).getTime()));
        }
        if (resultSet.getTime(UPDATE_TIME_KEY)!=null){
            user.setUpdateTime(new Date(resultSet.getTime(UPDATE_TIME_KEY).getTime()));
        }
       if (resultSet.getTime(LAST_TIME_KEY)!=null){
           user.setLastTime(new Date(resultSet.getTime(LAST_TIME_KEY).getTime()));
       }
        user.setEnabled(resultSet.getInt(ENABLED));
        return user;
    };

    public static RowMapper<User> getRowMapper() {
        return rowMapper;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public static void main(String[] args) {
        String pass = "zhangenbo1996";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(pass);
        System.out.println(hashPass);
    }
}
