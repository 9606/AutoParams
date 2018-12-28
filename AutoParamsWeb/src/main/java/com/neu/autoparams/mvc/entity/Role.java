package com.neu.autoparams.mvc.entity;

import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class Role {
    public final static String R_ID_KEY = "r_id";
    public final static String R_NAME_KEY = "r_name";
    public final static String R_DESC_KEY = "r_desc";

    public final static String R_PERCOUNT_KEY = "PERMCOUNT";

    private int id;
    private String name;
    private String description;

    private List<Integer> permissionIds;

    private int permissionCount;

    private List<User> users;
    private List<Permission> permissions;

    private static final RowMapper<Role> rowMapper = (resultSet, i) -> {
        Role role = new Role();
        role.setId(resultSet.getInt(R_ID_KEY));
        role.setName(resultSet.getString(R_NAME_KEY));
        role.setDescription(resultSet.getString(R_DESC_KEY));
        return role;
    };

    private static final RowMapper<Role> rowMapperAndPerCont = (resultSet, i) -> {
        Role role = new Role();
        role.setId(resultSet.getInt(R_ID_KEY));
        role.setName(resultSet.getString(R_NAME_KEY));
        role.setDescription(resultSet.getString(R_DESC_KEY));

        role.setPermissionCount(resultSet.getInt(R_PERCOUNT_KEY));
        return role;
    };

    public static RowMapper<Role> getRowMapper() {
        return rowMapper;
    }

    public static RowMapper<Role> getRowMapperAndPerCont(){
        return rowMapperAndPerCont;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(int permissionCount) {
        this.permissionCount = permissionCount;
    }

    public List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Integer> permissionIds) {
        if (permissionIds == null){
            this.permissionIds = new ArrayList<>();
        }else {
            this.permissionIds = permissionIds;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
