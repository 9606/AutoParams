package com.neu.autoparams.mvc.controller;

import com.neu.autoparams.entity.User;
import com.neu.autoparams.mvc.handler.LoginFailureHandler;
import com.neu.autoparams.mvc.handler.LoginSuccessHandler;
import com.neu.autoparams.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class HomeController {
    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource
    UserService userService;

    @Resource
    LoginSuccessHandler loginSuccessHandler;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        loginSuccessHandler.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }
    @RequestMapping(value = "/login1", method = RequestMethod.GET)
    public String getLogin1() {
        return "login1";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "register";
    }
    @RequestMapping(value = "/sign_in", method = RequestMethod.GET)
    public String geSignIn(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("failure-time") == null){
            request.getSession().setAttribute("failure-time", 0);
            request.getSession().setAttribute("need-check", false);
        }
        return "sign_in";
    }
    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public String getSignUp() {
        return "sign_up";
    }


    @RequestMapping(value = "/task/submit", method = RequestMethod.GET)
    public ModelAndView getSubmitTask() {
        ModelMap model = new ModelMap();
        model.addAttribute("isRestart", false);
        model.addAttribute("taskId", "null");

        return new ModelAndView("submit_task", model);
    }

    @RequestMapping(value = "/file/manage", method = RequestMethod.GET)
    public String getFileManage() {
        return "file";
    }

    @RequestMapping(value = "/api/userInfo/changePwd", method = RequestMethod.GET)
    public String getChangePwd(ModelMap modelMap) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserInfoByUserName(username);
        modelMap.put("userId", user.getUserId());
        return "change_password";
    }

    @RequestMapping(value = "/api/userInfo/update", method = RequestMethod.GET)
    public String getUserInfoUpdate() {
        return "update_userInfo";
    }

    @RequestMapping(value = "/admin/user/manage", method = RequestMethod.GET)
    public String getUserManage() {
        return "admin_user_manage";
    }

    @RequestMapping(value = "/admin/role/list", method = RequestMethod.GET)
    public String getRole() {
        return "admin_role";
    }

    @RequestMapping(value = "/task/manage", method = RequestMethod.GET)
    public String getFile() {
        return "task_history";
    }


}
