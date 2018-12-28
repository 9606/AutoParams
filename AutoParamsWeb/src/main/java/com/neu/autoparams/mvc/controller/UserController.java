package com.neu.autoparams.mvc.controller;

import com.neu.autoparams.mvc.entity.Permission;
import com.neu.autoparams.mvc.entity.Role;
import com.neu.autoparams.mvc.entity.User;
import com.neu.autoparams.mvc.service.UserService;
import com.neu.autoparams.util.GlobalResponseCode;
import com.neu.autoparams.util.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    private static Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

    @Resource
    UserService userService;

    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * 查询用户列表
     *
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserManageList(HttpServletRequest request) {
        try {
            List<User> userList = userService.getUserList(request);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(userList).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/api/user/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfoByUserId(@PathVariable(name = "id") int userId) {
        try {
            User user = userService.getUserInfo(userId);
            List<Role> roleList = userService.getAllRoleAndPerCount();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(user).put("allRoles", roleList).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 根据用户名称查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/api/user/changeSelfInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfoByUserName() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.getUserInfoByUserName(username);
            List<Role> roleList = userService.getAllRoleAndPerCount();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(user).put("allRoles", roleList).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveUser(@RequestBody User user) {
        try {
            if (user == null)
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            User userInfo = userService.getUserInfoByUserName(user.getUsername());
            if (userInfo != null)
                return RestResponse.create(GlobalResponseCode.ERROR_USER_EXISTED).build();
            boolean addUser = userService.saveUser(user);
            if (!addUser)
                return RestResponse.create(GlobalResponseCode.ERROR_CREATE).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(addUser).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * admin编辑用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editUser(@RequestBody User user) {
        try {
            if (user == null)
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            boolean editUser = userService.editUser(user);
            if (!editUser)
                return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 个人更新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/api/user/changeSelfInfo", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editUserInfo(@RequestBody User user) {
        try {
            if (user == null)
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            boolean editUserInfo = userService.editUserInfo(user);
            if (!editUserInfo)
                return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteUser(@RequestBody User user) {
        try {
            if (user == null)
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            boolean userDelete = userService.deleteUser(user.getUserId());
            if (!userDelete)
                return RestResponse.create(GlobalResponseCode.ERROR_DELETE).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 重置密码、更新密码
     *
     * @param responseBody
     * @return
     */
    @RequestMapping(value = {"/api/user/resetPassword", "/api/user/changeSelfPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestBody Map<String, Object> responseBody) {
        try {
            if (responseBody == null)
                return RestResponse.create(GlobalResponseCode.ERROR_RESPONSEBODY).build();
            if (!responseBody.containsKey("currentPwd") || !responseBody.containsKey("newPwd") || !responseBody.containsKey("userId"))
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            if (responseBody.get("currentPwd") != "" && responseBody.get("currentPwd") != null) {
                User user = userService.getUserInfo(Integer.parseInt(responseBody.get("userId").toString()));
                if (!passwordEncoder.matches(responseBody.get("currentPwd").toString(), user.getPassword()))
                    return RestResponse.create(GlobalResponseCode.ERROR_PASSWORD).build();
                boolean resetPwd = userService.changePwd(responseBody.get("newPwd").toString(), Integer.parseInt(responseBody.get("userId").toString()));
                if (!resetPwd)
                    return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
                return RestResponse.create(GlobalResponseCode.SUCCESS).build();
            }

            boolean resetPwd = userService.changePwd(responseBody.get("newPwd").toString(), Integer.parseInt(responseBody.get("userId").toString()));
            if (!resetPwd)
                return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 用户启用、禁用
     *
     * @param responseBody
     * @return
     */
    @RequestMapping(value = "/api/user/editState", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editState(@RequestBody Map<String, Object> responseBody) {
        try {
            if (responseBody == null)
                return RestResponse.create(GlobalResponseCode.ERROR_RESPONSEBODY).build();
            if (!responseBody.containsKey("state") || !responseBody.containsKey("userId"))
                return RestResponse.create(GlobalResponseCode.ERROR_PARAMETER).build();
            boolean isState = userService.editState(Integer.parseInt(responseBody.get("state").toString()), Integer.parseInt(responseBody.get("userId").toString()));
            if (!isState)
                return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    @RequestMapping(value = "/api/role", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getRoleList() {
        try {
            List<Role> roleList = userService.getAllRoleAndPerCount();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(roleList).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).putData(new HashMap<>()).build();
        }
    }

    //某角色拥有的权限
    @RequestMapping(value = "/api/permission/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPermission(@PathVariable(value = "id") int roleId) {
        try {
            List<Permission> permissions = userService.getPermission(roleId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(permissions).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).putData(new HashMap<>()).build();
        }
    }

    //角色信息 和拥有的权限集合
    @RequestMapping(value = "/api/role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getRoleById(@PathVariable(value = "id") int id) {
        try {
            Role roleInfo = userService.getRoleAndPermissions(id);
            List<Permission> permissions = userService.getPermissions();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(roleInfo).put("permissions", permissions).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    //修改角色的信息及权限
    @RequestMapping(value = "/api/role", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editRoleAndRolePermissions(@RequestBody Role role) {
        try {
            if (role.getId() == 1 || role.getId() == 2 || role.getId() == 3 || role.getId() == 4) {
                return RestResponse.create(GlobalResponseCode.ERROR_ROLE_NOT_EDIT).build();
            }
            boolean isSuccess = userService.updateRoleAndRolePermissions(role);
            if (!isSuccess) {
                return RestResponse.create(GlobalResponseCode.ERROR_EDIT).build();
            } else {
                return RestResponse.create(GlobalResponseCode.SUCCESS).build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    //查询所有权限
    @RequestMapping(value = "/api/permission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPermissions() {
        try {
            List<Permission> permissions = userService.getPermissions();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(permissions).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    //添加角色
    @RequestMapping(value = "/api/role", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveRole(@RequestBody Role role) {
        try {
            //1.判断是否已有角色
            Role roleByName = userService.getRoleByName(role.getName());
            if (roleByName != null) {
                return RestResponse.create(GlobalResponseCode.ERROR_ROLE_EXISTED).build();
            }
            //2.添加角色 并添加role_per 中间表
            boolean isSuccess = userService.saveRole(role);
            if (!isSuccess) {
                return RestResponse.create(GlobalResponseCode.ERROR_CREATE).build();
            }
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    //删除角色
    @RequestMapping(value = "/api/role", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteRole(@RequestBody Role role) {
        try {
            if (role.getId() == 1 || role.getId() == 2 || role.getId() == 3 || role.getId() == 4) {
                return RestResponse.create(GlobalResponseCode.ERROR_ROLE_NOT_DELETE).build();
            }
            userService.deleteRole(role);
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }
}
