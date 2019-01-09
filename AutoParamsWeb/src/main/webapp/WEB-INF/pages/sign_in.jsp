<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">

    <title>登录 - AutoParams</title>

    <link href="/css/sweetalert.css" rel="stylesheet">
    <link href="/css/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" media="all" href="/css/sign/style.css">
    <link rel="stylesheet" media="all" href="/css/sign/sign.css">

<body class="no-padding reader-black-font" lang="zh-CN">
<div class="sign">
    <div class="logo">
        <a href="/index">AutoParams</a>
    </div>
    <div class="main">


        <h4 class="title">
            <div class="normal-title">
                <a class="active" href="/sign_in">登录</a>
                <b>·</b>
                <a id="js-sign-up-btn" class="" href="/sign_up">注册</a>
            </div>
        </h4>
        <div class="js-sign-in-container">
            <form id="new_session" action="/login" method="post" accept-charset="UTF-8">
                <div class="row" id="checkCode-error" style="display: none;">
                    <div class="alert alert-danger">登录失败! 验证码错误！</div>
                </div>
                <div class="row" id="login-error"  style="display: none;">
                    <div class="alert alert-danger">登录失败! 用户名或密码错误！</div>
                </div>

                <!-- 正常登录登录名输入框 -->
                <div class="input-prepend restyle js-normal">
                    <input placeholder="手机号或邮箱或用户名" type="text" name="username"
                           id="session_email_or_mobile_number">
                    <i class="iconfont ic-user"></i>
                </div>

                <c:choose>
                    <c:when test="${pageContext.session.getAttribute('need-check')}">
                        <div class="input-prepend restyle no-radius js-normal">
                            <input placeholder="密码" type="password" name="password" id="session_password">
                            <i class="iconfont ic-password"></i>
                        </div>
                        <div id="check_code_div" class="input-prepend">
                    </c:when>
                    <c:otherwise>
                        <div id="password_div" class="input-prepend">
                            <input placeholder="密码" type="password" name="password" id="session_password">
                            <i class="iconfont ic-password"></i>
                        </div>
                        <div id="check_code_div" class="input-prepend" style="display: none;">
                    </c:otherwise>
                </c:choose>
                    <input placeholder="验证码" type="text" name="validateCode" id="session_check_code">
                    <i class="iconfont ic-verify"></i>
                    <div class="lbl" style="position: absolute;right:5px;bottom: 15px;top: 15px;">
                        <img src="/CheckCode" id="checkCode"
                             onclick="reloadCheckCode()" style="cursor:pointer;"
                             title="点击刷新验证码"/>
                    </div>
                </div>

                <div class="remember-btn">
                    <input type="checkbox" value="true" checked="checked" name="session[remember_me]"
                           id="session_remember_me"><span>记住我</span>
                </div>
                <div class="forget-btn">
                    <a class="" data-toggle="dropdown" href aria-expanded="false">登录遇到问题?</a>
                    <ul class="dropdown-menu">
                        <li><a href="/mobile_reset">用手机号重置密码</a></li>
                        <li><a href="/email_reset">用邮箱重置密码</a></li>
                    </ul>
                </div>
                <button class="sign-in-button" id="sign-in-form-submit-btn" type="button">
                    <span id="sign-in-loading"></span>
                    登录
                </button>

            </form>
            <!-- 更多登录方式 -->
            <div class="more-sign">
                <h6>社交帐号登录</h6>
                <ul>
                    <li id="weibo-link-wrap" class="">
                        <a class="weibo" id="weibo-link">
                            <i class="iconfont ic-weibo"></i>
                        </a>
                    </li>
                    <li><a class="weixin" target="_blank" href="javascript:void(0);"><i
                            class="iconfont ic-wechat"></i></a></li>
                    <li><a class="qq" target="_blank" href="javascript:void(0);"><i
                            class="iconfont ic-qq_connect"></i></a></li>
                </ul>

            </div>
        </div>

    </div>
</div>
</body>
</html>
<script src="/js/jquery/jquery-2.1.4.min.js"></script>
<script src="/js/sweetalert/sweetalert.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>

<script>
    $('.dropdown-toggle').dropdown();
    function reloadCheckCode() {
        document.getElementById("checkCode").src = $("#ctx").val() + "/CheckCode?r=" + new Date().getTime();
    }
    $('#sign-in-form-submit-btn').click(function () {
        var form = $('#new_session');
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize()
        }).success(function (event, xhr, options) {
            if (options.getResponseHeader("NEED-CHECK") === 'true') {
                $('#password_div').addClass('restyle');
                $('#password_div').addClass('no-radius');
                $('#password_div').addClass('js-normal');
                $('#check_code_div').css('display','block');
            }
            if (options.getResponseHeader("REDIRECT") === 'REDIRECT'){
                window.location.href = options.getResponseHeader("CONTEXT-PATH");
            } else {
                if (options.getResponseHeader("CONTEXT-CONTENT") === 'Error CheckCode.') {
                    $('#checkCode-error').css('display', 'block');
                    $('#login-error').css('display', 'none');
                }else {
                    $('#checkCode-error').css('display', 'none');
                    $('#login-error').css('display', 'block');
                }
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            window.location.href = '/sign_in';
        });
    });
    $('.weixin').click(function () {
        swal({
            title:"暂不支持微信登录",
            text:"由于授权原因，暂不支持微信登录，请等待网站日后更新",
            type:"warning",
            showCancelButton:false,
            showConfirmButton:true,
            confirmButtonText: '确定',
            animation:"slide-from-top"
        });
    });
</script>