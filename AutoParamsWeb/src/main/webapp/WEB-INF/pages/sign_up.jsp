<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">

    <title>注册 - AutoParams</title>

    <link rel="stylesheet" media="all" href="/css/sign/style.css">

    <link rel="stylesheet" media="all" href="/css/sign/sign.css">
    <style>
        .reg_to_mobile {
            font-size: 14px;
            color: #a3a3a3;
            cursor: pointer;
        }
    </style>
</head>

<body class="no-padding reader-black-font" lang="zh-CN">
<div class="sign">
    <div class="logo"><a href="https://www.jianshu.com/">AutoParams</a></div>
    <div class="main">


        <h4 class="title">
            <div class="normal-title">
                <a class="" href="/sign_in">登录</a>
                <b>·</b>
                <a id="js-sign-up-btn" class="active" href="/sign_up">注册</a>
            </div>
        </h4>
        <div class="js-sign-up-container">
            <form class="new_user" id="new_tel_user" action="/tel_register" accept-charset="UTF-8"
                  method="post">
                <div class="input-prepend restyle">
                    <input placeholder="你的用户名" type="text" value="" name="user[nickname]" id="tel_username">
                    <i class="iconfont ic-user"></i>
                </div>
                <div class="input-prepend restyle no-radius js-normal">
                    <input placeholder="手机号" type="tel" pattern="^[1][0-9]{10}$" name="user[mobile_number]"
                        id="tel_user_mobile_number">
                    <i class="iconfont ic-phonenumber"></i>
                </div>
                <div class="input-prepend restyle no-radius security-up-code js-security-number">
                    <input type="text" name="tel_sms_code" id="sms_code" placeholder="验证码">
                    <i class="iconfont ic-verify"></i>
                    <a tabindex="-1" class="btn-up-resend js-send-code-button disable"
                       href="javascript:void(0);">发送验证码</a>
                </div>
                <div class="input-prepend restyle no-radius">
                    <input placeholder="设置密码" type="password" name="user[password]" id="tel_user_password">
                    <i class="iconfont ic-password"></i>
                </div>
                <div class="input-prepend">
                    <input placeholder="再次输入密码" type="password" name="user[password]" id="tel_user_password_again">
                    <i class="iconfont ic-password"></i>
                </div>
                <div class="reg_to_mobile" style="text-align:right;margin-top: 15px;">通过邮箱注册>></div>
                <input type="submit" name="commit" value="注册" class="sign-up-button" data-disable-with="注册">
                <p class="sign-up-msg">点击 “注册” 即表示您同意并愿意遵守简书<br> <a target="_blank"
                                                                    href="javascript:void(0);">用户协议</a>
                    和 <a target="_blank" href="javascript:void(0);">隐私政策</a> 。</p>
            </form>

            <form class="new_user" id="new_email_user" action="/tel_register" accept-charset="UTF-8"
                  method="post" style="display: none;">
                <div class="input-prepend restyle">
                    <input placeholder="你的用户名" type="text" value="" name="user[nickname]" id="email_username">
                    <i class="iconfont ic-user"></i>
                </div>
                <div class="input-prepend restyle no-radius js-normal">
                    <input placeholder="手机号" type="tel" pattern="^[1][0-9]{10}$" name="user[mobile_number]"
                           id="email_user_mobile_number">
                    <i class="iconfont ic-phonenumber"></i>
                </div>
                <div class="input-prepend restyle no-radius security-up-code js-security-number">
                    <input type="text" name="sms_code" id="email_sms_code" placeholder="验证码">
                    <i class="iconfont ic-verify"></i>
                    <a tabindex="-1" class="btn-up-resend js-send-code-button disable"
                       href="javascript:void(0);">发送验证码</a>
                </div>
                <div class="input-prepend restyle no-radius">
                    <input placeholder="设置密码" type="password" name="user[password]" id="email_user_password">
                    <i class="iconfont ic-password"></i>
                </div>
                <div class="input-prepend">
                    <input placeholder="再次输入密码" type="password" name="user[password]" id="email_user_password_again">
                    <i class="iconfont ic-password"></i>
                </div>
                <div class="reg_to_mobile" style="text-align:right;margin-top: 15px;">通过邮箱注册>></div>
                <input type="submit" name="commit" value="注册" class="sign-up-button" data-disable-with="注册">
                <p class="sign-up-msg">点击 “注册” 即表示您同意并愿意遵守简书<br> <a target="_blank"
                                                                    href="javascript:void(0);">用户协议</a>
                    和 <a target="_blank" href="javascript:void(0);">隐私政策</a> 。</p>
            </form>
            <!-- 更多注册方式 -->
            <div class="more-sign">
                <h6>社交帐号直接注册</h6>
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
<script>
    document.getElementById("user_mobile_number").setCustomValidity("请输入正确的11位手机号码");
</script>
</html>