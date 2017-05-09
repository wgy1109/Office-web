<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>操作日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/operateLog/list">操作日志列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="operateLog" action="${ctx}/sys/operateLog/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>操作者：</label><input type="search" name="user_chinese_name" class="textinput"  value="${returnMap.user_chinese_name}">&nbsp;</li>
		 	<li><label>操作日期：&nbsp;</label><input id="create_date_begin" name="create_date_begin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" 
				value="${returnMap.create_date_begin}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
				&nbsp;&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;<input id="create_date_end" name="create_date_end" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"  
				value="${returnMap.create_date_end}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
		 	<li><label>日志类型：</label>
		 		<select class="form-control"
					style="width:175px; height:30px; padding:0px" name="log_type">
						<option value="">请选择</option>
						<option value="10" <c:if test="${returnMap.log_type == 10}">selected</c:if>>企业审核通过 </option>
						<option value="15" <c:if test="${returnMap.log_type == 15}">selected</c:if>>企业审核驳回</option>
						<option value="20" <c:if test="${returnMap.log_type == 20}">selected</c:if>>应用审核通过</option>
						<option value="25" <c:if test="${returnMap.log_type == 25}">selected</c:if>>应用审核驳回</option>
						<option value="30" <c:if test="${returnMap.log_type == 30}">selected</c:if>>企业账户充值</option>
						<option value="35" <c:if test="${returnMap.log_type == 35}">selected</c:if>>开票审核</option>
						<option value="40" <c:if test="${returnMap.log_type == 40}">selected</c:if>>应用充值</option>
						<option value="45" <c:if test="${returnMap.log_type == 45}">selected</c:if>>应用配置修改</option>
						<option value="50" <c:if test="${returnMap.log_type == 50}">selected</c:if>>通道启用 </option>
						<option value="55" <c:if test="${returnMap.log_type == 55}">selected</c:if>>通道禁用</option>
						<option value="60" <c:if test="${returnMap.log_type == 60}">selected</c:if>>通道编辑</option>
						<option value="65" <c:if test="${returnMap.log_type == 65}">selected</c:if>>通道删除 </option>
						<option value="70" <c:if test="${returnMap.log_type == 70}">selected</c:if>>通道组启用</option>
						<option value="75" <c:if test="${returnMap.log_type == 75}">selected</c:if>>通道组禁用</option>
						<option value="80" <c:if test="${returnMap.log_type == 80}">selected</c:if>>通道组编辑</option>
						<option value="85" <c:if test="${returnMap.log_type == 85}">selected</c:if>>通道组删除 </option>
						<option value="90" <c:if test="${returnMap.log_type == 90}">selected</c:if>>短信审核通过</option>
						<option value="95" <c:if test="${returnMap.log_type == 95}">selected</c:if>>短信审核驳回 </option>
						<option value="100" <c:if test="${returnMap.log_type == 100}">selected</c:if>>语音审核通过</option>
						<option value="105" <c:if test="${returnMap.log_type == 105}">selected</c:if>>语音审核驳回</option>
						<option value="110" <c:if test="${returnMap.log_type == 110}">selected</c:if>>模板审核通过</option>
						<option value="115" <c:if test="${returnMap.log_type == 115}">selected</c:if>>模板审核驳回 </option>
						<option value="120" <c:if test="${returnMap.log_type == 120}">selected</c:if>>系统模板新增</option>
						<option value="125" <c:if test="${returnMap.log_type == 125}">selected</c:if>>系统模板修改</option>
						<option value="130" <c:if test="${returnMap.log_type == 130}">selected</c:if>>系统模板删除</option>
				</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>操作者</th>
				<th>操作日期</th>
				<th>日志类型</th>
				<th>日志内容</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="operateLog">
			<tr>
			   <td>${operateLog.userChineseName}</td>
			   <td><fmt:formatDate value="${operateLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			   <td><c:if test="${operateLog.logType == '10'}" >
			         	 企业审核通过 
			       </c:if>
			   	   <c:if test="${operateLog.logType == '15'}" >
			         	企业审核驳回
			       </c:if>
			       <c:if test="${operateLog.logType == '20'}" >
			         	应用审核通过
			       </c:if>
			   	   <c:if test="${operateLog.logType == '25'}" >
			         	应用审核驳回
			       </c:if>
			   	   <c:if test="${operateLog.logType == '30'}" >
			         	企业账户充值
			       </c:if>
			  	   <c:if test="${operateLog.logType == '35'}" >
			         	开票审核
			       </c:if>
			       <c:if test="${operateLog.logType == '40'}" >
			         	应用充值
			       </c:if>
			       <c:if test="${operateLog.logType == '45'}" >
			         	应用配置修改
			       </c:if>
			      <c:if test="${operateLog.logType == '50'}" >
			         	通道启用
			       </c:if>
			       <c:if test="${operateLog.logType == '55'}" >
			         	通道禁用
			       </c:if>
			       <c:if test="${operateLog.logType == '60'}" >
			         	通道编辑
			       </c:if>
			       <c:if test="${operateLog.logType == '65'}" >
			         	通道删除
			       </c:if>
			       <c:if test="${operateLog.logType == '70'}" >
			         	通道组启用
			       </c:if>
			       <c:if test="${operateLog.logType == '75'}" >
			         	通道组禁用
			       </c:if>
			        <c:if test="${operateLog.logType == '80'}" >
			         	通道组编辑
			       </c:if>
			        <c:if test="${operateLog.logType == '85'}" >
			         	通道组删除
			       </c:if>
			       <c:if test="${operateLog.logType == '90'}" >
			         	短信审核通过
			       </c:if>
			       <c:if test="${operateLog.logType == '95'}" >
			         	短信审核驳回
			       </c:if>
			        <c:if test="${operateLog.logType == '100'}" >
			         	语音审核通过
			       </c:if>
			       <c:if test="${operateLog.logType == '105'}" >
			         	语音审核驳回
			       </c:if>
			       <c:if test="${operateLog.logType == '110'}" >
			         	  模板审核通过
			       </c:if>
			        <c:if test="${operateLog.logType == '115'}" >
			         	 模板审核驳回
			       </c:if>
			        <c:if test="${operateLog.logType == '120'}" >
			         	 系统模板新增
			       </c:if>
			        <c:if test="${operateLog.logType == '125'}" >
			         	 系统模板修改
			       </c:if>
			        <c:if test="${operateLog.logType == '130'}" >
			         	系统模板删除
			       </c:if>
			   </td>
			   <td style="text-align:left;" title="${operateLog.logContent}">&nbsp;&nbsp;<c:if test="${fn:length(operateLog.logContent)>150 }">${ fn:substring(operateLog.logContent ,0,150)} ...</c:if><c:if test="${fn:length(operateLog.logContent)<=150 }">${operateLog.logContent}</c:if></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>