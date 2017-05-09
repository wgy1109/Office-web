<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>资讯信息管理</title>
	<meta name="decorator" content="12122"/>
	<script src="static/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
		function pay(){
			 window.open("payMth");
		}
	</script>
</head>
<body>
<div class='charging_online'>
	<div class='chanzorDataList_title'>
		<p>充值</p>
	</div>
	<input type="button" class='btn btn-primary btn-lg' onclick="pay()" value='充值' />
</div>
</body>
</html>