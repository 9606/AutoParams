<%@ page import="com.neu.autoparams.mvc.entity.Authority" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Authority[] menus = (Authority[]) pageContext.getSession().getAttribute("menus");
%>
<!-- Left navbar-header -->
<div id="left_sidebar" class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
        <ul class="nav" id="side-menu">
            <%--<li class="sidebar-search hidden-sm hidden-md hidden-lg">--%>
                <%--<!-- input-group -->--%>
                <%--<div class="input-group custom-search-form">--%>
                    <%--<input type="text" class="form-control" placeholder="Search...">--%>
                    <%--<span class="input-group-btn">--%>
                            <%--<button class="btn btn-default" type="button">--%>
                                <%--<i class="fa fa-search"></i>--%>
                            <%--</button>--%>
                        <%--</span>--%>
                <%--</div>--%>
                <%--<!-- /input-group -->--%>
            <%--</li>--%>

            <c:forEach items="${menus}" var="parent">
                <c:choose>
                    <c:when test="${empty parent.children}">
                        <li>
                            <a href="${parent.url}" class="waves-effect">
                                <i class="${parent.icon}"></i>
                                <span class="hide-menu">${parent.name}</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="javascript:" class="waves-effect">
                                <i class="${parent.icon}"></i>
                                <span class="hide-menu">${parent.name}<span class="fa arrow"></span></span>
                            </a>
                            <ul class="nav nav-second-level collapse">
                                <c:forEach items="${parent.children}" var="child">
                                    <li>
                                        <a href="${child.url}">${child.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <div class="slimScrollBar"
         style="width: 5px; position: absolute; top: 179px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 740.194px; background: rgb(220, 220, 220);"></div>
    <div class="slimScrollRail"
         style="width: 5px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(51, 51, 51);"></div>
</div>
<!-- Left navbar-header end -->