<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>付费记录管理</title>
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
		<li class="active"><a href="${ctx}/chanzor/czChargeRecord/list">付费记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="czChargeRecord" action="${ctx}/chanzor/czChargeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${czChargeRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>批次ID：</label>
				<form:input path="productId" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>充值状态：</label>
				<form:select path="alipayType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="未付款"/>
					<form:option value="1" label="付款成功"/>
					<form:option value="-1" label="付款失败"/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>批次ID</th>
				<th>充值金额</th>
				<th>冲执状态</th>
				<th>到账时间</th>
				<th>充值IP</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="czChargeRecord">
			<tr>
				<td><%-- <a href="${ctx}/chanzor/czChargeRecord/form?id=${czChargeRecord.id}">
						<fmt:formatDate value="${czChargeRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</a> --%>
					<fmt:formatDate value="${czChargeRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${czChargeRecord.productId}
				</td>
				<td>
					${czChargeRecord.amount}
				</td>
				<td>
					${fns:getDictLabel(czChargeRecord.alipayType, '', '')}
				</td>
				<td>
					<fmt:formatDate value="${czChargeRecord.chargeArrivalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${czChargeRecord.ip}
				</td>
			 	<%-- <td>
    				<a href="${ctx}/chanzor/czChargeRecord/form?id=${czChargeRecord.id}">修改</a>
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>