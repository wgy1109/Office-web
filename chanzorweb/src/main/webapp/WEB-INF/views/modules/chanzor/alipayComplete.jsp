<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta name="renderer" content="webkit" />
<meta name="description" content="Bootstrap Admin App + jQuery">
<meta name="keywords"
	content="app, responsive, jquery, bootstrap, dashboard, admin">
<title>畅卓验证码短信飞常快---畅卓官网 在线短信平台,SDK短信接口,手机短信验证码,验证码通道</title>
<meta name="description"
	content="北京畅卓科技有限公司是一家拥有卓越运营支撑能力的短信服务商，为企业提供一站式的短信验证码应用解决方案，拥有106高端通道免费试用，3秒最快，99.99%到达率，咨询热线：400-793-0000，产品包括：短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信">
<meta name="keywords"
	content="短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信，SDK短信接口">
<style type="text/css">
.headli {
	width: 140px;
	height: 55px;
	border-right: 1px white solid;
	text-align: center;
	vertical-align: inherit;
	line-height: 55px;
	cursor: pointer;
}

.headli:hover {
	background-color: grey;
}
</style>
<html>
<body>
	<div class="wrapper">
		</nav> </header>
		<div style="width: 500px; height: 380px; position: absolute; left: 50%; margin-left: -250px; top: 50%; margin-top: -190px;">
			<div style="text-align: center;">
					<div class="img-responsive">
						<c:if test="${chargeType == 1}"><img src="<%=basePath%>static/img/payComplete.png" alt="App Logo" style="width: 72px; height: 72px; margin-bottom: 15px;" /></c:if>
						<c:if test="${chargeType != 1}"><img src="<%=basePath%>static/img/payFail.png" alt="App Logo" style="width: 72px; height: 72px; margin-bottom: 15px;" /></c:if>
						<label style="font-size: 25px">${sHtmlText}</label>
					</div>
				<button id="closePage" type="button" class="btn btn-info">关闭本页面</button>
			</div>
		</div>
	</div>
</body>
</html>
<script src="<%=basePath%>static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#closePage").click(function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
					window.opener = null;
					window.close();
				} else {
					window.open('', '_top');
					window.top.close();
				}
			} else if (navigator.userAgent.indexOf("Chrome") > 0) {
				window.opener = null;
				window.open('', '_self');
				window.close();
			} else if (navigator.userAgent.indexOf("Firefox") > 0) {
				window.open('', '_parent', '');
				window.close();
			} else {
				window.opener = null;
				window.open('', '_self', '');
				window.close();
			}
		});
	});
</script>