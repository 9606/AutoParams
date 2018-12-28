package com.neu.autoparams.mvc.entity;

import java.io.Serializable;

public class Authority implements Serializable {

    private String name;
    private String icon;
    private String url;
    private String[] permissions;
    private Authority[] children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public Authority[] getChildren() {
        return children;
    }

    public void setChildren(Authority[] children) {
        this.children = children;
    }
}
