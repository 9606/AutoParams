package com.neu.autoparams.mvc.handler;

import com.neu.autoparams.entity.Authority;
import com.neu.autoparams.entity.User;
import com.neu.autoparams.mvc.service.UserService;
import com.neu.autoparams.util.Definition;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    UserService userService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, AuthenticationException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        userService.updateLastLoginTime(username);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        List<Authority> authorities = Definition.authority.loadData(Authority.class);

        User loginUser = userService.getUserInfoByUserName(username);
        Authority[] authorityArray = new Authority[authorities.size()];

        authorities.toArray(authorityArray);
        Authority[] menus = loadMenu(roles, authorityArray);
        request.getSession().setAttribute("menus", menus);
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("userId", loginUser.getUserId());

        String type = request.getHeader("X-Requested-With") == null ? "" : request.getHeader("X-Requested-With");
        String redirectUrl =  "";
        if (roles.contains("submitTask")) {
            redirectUrl = "/task/submit";
        }else if (roles.contains("userManage")) {
            redirectUrl = "admin/user/manage";
        }else if (roles.contains("roleList")) {
            redirectUrl = "/admin/role/list";
        }else {
            redirectUrl = "";
        }
        if ("XMLHttpRequest".equals(type)) {
            response.setHeader("REDIRECT", "REDIRECT");
            response.setHeader("CONTEXT-PATH", redirectUrl);
            request.getSession().setAttribute("failure-time", 0);
            request.getSession().setAttribute("need-check", false);
            response.setHeader("NEED-CHECK", String.valueOf(false));
        }else{
            response.sendRedirect(redirectUrl);
        }

    }

    private Authority[] loadMenu(Set<String> roles, Authority[] authorities) {
        if (roles == null || roles.size() == 0)
            return null;

        ArrayList<Authority> menuList = new ArrayList<>();
        // Authority[] menus = new Authority[authorities.length];
        int i = 0;

        for (; i < authorities.length; i++) {
            Authority authority = authorities[i];
            if (authority.getPermissions() == null || authority.getPermissions().length == 0) {
                Authority a = new Authority();
                a.setName(authority.getName());
                a.setIcon(authority.getIcon());
                a.setUrl(authority.getUrl());
                a.setChildren(loadMenu(roles, authority.getChildren()));
                if (a.getChildren() != null && a.getChildren().length != 0)
                    menuList.add(a);
            } else {
                for (String s : authority.getPermissions()) {
                    if (roles.contains(s)) {
                        menuList.add(authority);
                        break;
                    }
                    i++;
                }
            }
        }
        return menuList.toArray(new Authority[menuList.size()]);
    }
}
