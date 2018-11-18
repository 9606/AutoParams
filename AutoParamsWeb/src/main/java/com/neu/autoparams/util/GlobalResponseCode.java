package com.neu.autoparams.util;

import java.util.HashMap;
import java.util.Map;


public enum GlobalResponseCode {
    SUCCESS,

    ERROR,

    //role
    ERROR_ROLE_EXISTED,
    ERROR_ROLE_NOT_DELETE,
    ERROR_ROLE_NOT_EDIT,

    //user
    ERROR_USER_EXISTED,
    ERROR_PASSWORD,

    //Exception
    ERROR_Exception_ParseException,
    ERROR_CREATE,
    ERROR_EDIT,
    ERROR_DELETE,
    ERROR_PARAMETER,
    ERROR_RESPONSEBODY;

    private static final Map<GlobalResponseCode, String> messages = new HashMap<GlobalResponseCode, String>() {{
        put(SUCCESS, "操作成功!");

        put(ERROR,"操作失败");

        put(ERROR_ROLE_EXISTED,"角色已存在");
        put(ERROR_ROLE_NOT_DELETE,"该角色不能删除");
        put(ERROR_ROLE_NOT_EDIT,"该角色不能修改");

        put(ERROR_USER_EXISTED,"用户已存在！");
        put(ERROR_PASSWORD,"密码错误！");
        put(ERROR_Exception_ParseException, "操作错误!");


        put(ERROR_CREATE,"创建失败!");
        put(ERROR_EDIT,"修改失败!");
        put(ERROR_DELETE,"删除失败!");
        put(ERROR_PARAMETER,"传入参数错误!");
        put(ERROR_RESPONSEBODY,"响应参数为空！");

    }};

    public String getMessage() {
        return messages.get(this);
    }
}
