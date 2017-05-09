<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资讯信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" charset="utf-8" src="/chanzorweb/static/utf8-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/chanzorweb/static/utf8-jsp/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/chanzorweb/static/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		var ue = UE.getEditor('editor');
		
		function getContent() {
	        var arr = [];
	        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
	        arr.push("内容为：");
	        arr.push(UE.getEditor('editor').getContent());
	    }
		
		function clickImgUpload(fileAreaId) {
			$("#" + fileAreaId).click();
		}
		
		// 异步上传文件
		function updateImage(obj) {
			var textid = $(obj).attr('id') + "_SRC";
			$.ajaxFileUpload({
						url : 'updateImage',// 用于文件上传的服务器端请求地址
						secureuri : false,// 一般设置为false
						fileElementId : $(obj).attr('id'),// 文件上传控件的id属性
						dataType : 'text',// 返回值类型 一般设置为json
						type : 'post',
						success : function(data, status) // 服务器成功响应处理函数
						{
							console.log(data);
							data = data.substring(59,data.length-6);
							console.log(data);
							$("#title_img_SRC_example").html('<img src="http://123.56.30.170/Uploads/'+data+'" >');
							$('#image').val(data);
							
						},
						error : function(data, status, e)// 服务器响应失败处理函数
						{
							layer.msg(e);
						}
					})
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/chanzor/czDocument/list">资讯信息列表</a></li>
		<li class="active"><a href="${ctx}/chanzor/czDocument/form?id=${czDocument.id}">资讯信息<shiro:hasPermission name="chanzor:czDocument:edit">${not empty czDocument.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="chanzor:czDocument:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="czDocument" action="${ctx}/chanzor/czDocument/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题图片：</label>
			<input type="hidden" name="image" id="image" value="${czDocument.image}" />
			<div id="title_img_SRC_example" name="title_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="${czDocument.image}" >
			</div>
			<div class="controls">
				<a href="javascript:clickImgUpload('title_img')">点击上传图片</a>
				<input type="file" name="file" id="title_img" style="display:none" filename="title_img" class="btn btn-default" onchange="updateImage(this);" value="${vatinvoice.title_img}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="140" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文章内容：</label>
			<div class="controls">
				<script id="editor" type="text/plain" style="width:1024px;height:500px;" name="content">${czDocument.content}</script>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>