<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Top Section -->
    <nav class="navbar navbar-default navbar-static-top" style="margin-bottom: 0px;">
        <div class="navbar-header">
            <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse"
               data-target=".navbar-collapse">
                <i class="fa fa-list-ul"></i>
            </a>
            <div class="top-left-part">
                <a class="logo" href="/task/submit">
                    <img src="/images/logo-image.png" alt="home">
                    <span class="hidden-xs"><img src="/images/logo-text.png" alt="home" width="70%"></span>
                </a>
            </div>
            <ul class="nav navbar-top-links navbar-left hidden-xs active">
                <li><a href="javascript:void(0)" class="open-close hidden-xs waves-effect waves-light"><i
                        class="fa fa-arrow-left fa-list-ul"></i></a></li>
            </ul>
            <ul class="nav navbar-top-links navbar-right pull-right">
                <li class="dropdown">
                    <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#">
                        <img src="/images/varun.jpg" alt="user-img" width="36" class="img-circle">
                        <b class="hidden-xs">${pageContext.session.getAttribute("username")}</b>
                    </a>
                    <ul class="dropdown-menu dropdown-user scale-up">
                        <li><a href="/api/userInfo/update"><i class="fa fa-user-circle-o"></i> 更新用户信息</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/api/userInfo/changePwd"><i class="fa fa-key fa-fw"></i> 修改密码</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/logout"><i class="fa fa-power-off"></i> 退出</a></li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
            </ul>
        </div>
    </nav>
    <!-- Top Section End -->
</div>