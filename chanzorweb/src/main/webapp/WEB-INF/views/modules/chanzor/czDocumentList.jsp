<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资讯信息管理</title>
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
		<li class="active"><a href="${ctx}/chanzor/czDocument/list">资讯信息列表</a></li>
		<li><a href="${ctx}/chanzor/czDocument/form">资讯信息添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="czDocument" action="${ctx}/chanzor/czDocument/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="czDocument">
			<tr>
				<td><a href="${ctx}/chanzor/czDocument/form?id=${czDocument.id}">
					${czDocument.title}
				</a></td>
				<td>
					${czDocument.description}
				</td>
				<td>
					${czDocument.createTime}
				</td>
				<td>
					${czDocument.updateTime}
				</td>
				<td>
    				<a href="${ctx}/chanzor/czDocument/form?id=${czDocument.id}">修改</a>
					<a href="${ctx}/chanzor/czDocument/delete?id=${czDocument.id}" onclick="return confirmx('确认要删除该资讯信息吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>