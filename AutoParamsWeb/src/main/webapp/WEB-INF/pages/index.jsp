<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>登录 - AutoParams</title>
    <link  rel="stylesheet" href="/css/bootstrap/css/bootstrap.min.css">

	<!-- Custom Google Web Font -->
	<link href="/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Arvo:400,700' rel='stylesheet' type='text/css'>

	<!-- Custom CSS-->
	<link href="/css/index/general.css" rel="stylesheet">
	<!-- Owl-Carousel -->
	<%--<link href="/css/index/custom.css" rel="stylesheet">--%>
	<link href="/css/index/style.css" rel="stylesheet">
	<link href="/css/index/animate.css" rel="stylesheet">
	<style>
		.intro-header {
			height: 100%;
			text-align: center;
		}
	</style>

</head>
<body>
<div id="particles-box" style="position: absolute;width: 100%;"></div>
<div class="intro-header">
	<div class="col-xs-12 text-center abcen1">
		<h1 class="h1_home wow fadeIn" data-wow-delay="0.4s">AutoParams</h1>
		<h3 class="h3_home wow fadeIn" data-wow-delay="0.6s">简化您的调参流程</h3>
		<ul class="list-inline intro-social-buttons">
			<li><a href="/sign_in" class="btn  btn-lg mybutton_cyano wow fadeIn" data-wow-delay="0.8s"><span class="network-name">登录</span></a>
			</li>
			<li id="download" ><a href="/sign_up" class="btn  btn-lg mybutton_cyano wow fadeIn" data-wow-delay="1.2s"><span class="network-name">注册</span></a>
			</li>
		</ul>
	</div>
</div>

<script src='/js/index/particles.js' type="text/javascript"></script>
<script src='/js/index/background.js' type="text/javascript"></script>
<script src='/js/jquery/jquery-2.1.4.min.js' type="text/javascript"></script>
<script src="/js/index/wow.min.js"></script>

<script>
    new WOW().init();
</script>
</body>
</html>
