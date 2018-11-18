package com.neu.autoparams.entity;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class Permission {
    public final static String P_ID_KEY="p_id";
    public final static String P_NAME_KEY="p_name";
    public final static String P_DESC_KEY="p_desc";
    public final static String P_DISPLAYNAMEW_KEY="p_displayname";


    private int id;
    private String name;
    private String desc;
    private String displayName;

    private List<Role> roles;

    private static final RowMapper<Permission> rowMapper = (resultSet, i) -> {
        Permission permission = new Permission();
        permission.setId(resultSet.getInt(P_ID_KEY));
        permission.setName(resultSet.getString(P_NAME_KEY));
        permission.setDesc(resultSet.getString(P_DESC_KEY));
        permission.setDisplayName(resultSet.getString(P_DISPLAYNAMEW_KEY));
        return permission;
    };

    public static RowMapper<Permission> getRowMapper() {
        return rowMapper;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
