<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日历</title>
<meta name="decorator" content="default" />
<style type="text/css">
body {
	color: #51555c;
	font: 12px/18px "Microsoft Yahei", Tahoma, Helvetica, Arial, Verdana,
		"宋体", sans-serif;
}

#calendar {
	width: 1300px;
	margin: 0px auto 10px auto
}

.fancy {
	width: 450px;
	height: auto
}

.fancy h3 {
	height: 30px;
	line-height: 30px;
	border-bottom: 1px solid #d3d3d3;
	font-size: 14px
}

.fancy form {
	padding: 10px
}

.fancy p {
	height: 28px;
	line-height: 28px;
	padding: 4px;
	color: #999
}

.input {
	height: 20px;
	line-height: 20px;
	padding: 2px;
	border: 1px solid #d3d3d3;
	width: 100px
}

.btn {
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	padding: 5px 12px;
	cursor: pointer
}

.btn_ok {
	background: #360;
	border: 1px solid #390;
	color: #fff
}

.btn_cancel {
	background: #f0f0f0;
	border: 1px solid #d3d3d3;
	color: #666
}

.btn_del {
	background: #f90;
	border: 1px solid #f80;
	color: #fff
}

.sub_btn {
	height: 32px;
	line-height: 32px;
	padding-top: 6px;
	border-top: 1px solid #f0f0f0;
	text-align: right;
	position: relative
}

.sub_btn .del {
	position: absolute;
	left: 2px
}

.form-horizontal .control-label {
	width: 80px;
	line-height: 21px;
}
.fc-event-titlebg{
color:white
}
</style>
<script type="text/javascript">
	$(function() {
		$('#calendar')
				.fullCalendar(
						{
							header : {
								left : 'prev,next today',
								center : 'title',
								right : 'month,agendaWeek,agendaDay'
							},
							height : 580,
							editable : true,
							firstDay : 0,//每行第一天为周一 
							dragOpacity : {
								agenda : .5,
								'' : .6
							},
							textColor: 'white',
							events : function(start, end, callback) {
								var viewStart = $.fullCalendar.formatDate(start, "yyyy-MM-dd HH:mm:ss");
								var viewEnd = $.fullCalendar.formatDate(end, "yyyy-MM-dd HH:mm:ss");
								var events = [];
								$.ajax({
									url : "${ctx}/crm/calendar/initCalendar?startTime="+viewStart+"&endTime="+viewEnd,
									async : false,
									success : function(data) {
										if (data.data) {
											$.each(data.data, function(index,info) {
												events.push({
													id:info.id,
													title : info.title,
													start : info.startTime,
													end : info.endTime,
													allDay:info.isAllDay==1?true:false
												});
											})
										}
									}
								});
								callback(events);
							},
							viewDisplay : function(view) {
								var viewStart = $.fullCalendar.formatDate(view.start, "yyyy-MM-dd HH:mm:ss");
								var viewEnd = $.fullCalendar.formatDate(view.end, "yyyy-MM-dd HH:mm:ss");
								var events = [];
								$.ajax({
									url : "${ctx}/crm/calendar/initCalendar?startTime="+viewStart+"&endTime="+viewEnd,
									async : false,
									success : function(data) {
										if (data.data) {
											$.each(data.data, function(index,info) {
												events.push({
													id:info.id,
													title : info.title,
													start : info.startTime,
													end : info.endTime,
													allDay:info.isAllDay==1?true:false
												});
											})
										}
									}
								});
								$("#calendar").fullCalendar('renderEvent',events, true);
							},
							eventDrop : function(event, dayDelta, minuteDelta,allDay, revertFunc) {
								$.post("${ctx}/crm/calendar/updCalendar?id="+event.id+"&startTime="+$.fullCalendar.formatDate(event.start, 'yyyy-MM-dd HH:mm:ss')+"&endTime="+ $.fullCalendar.formatDate(event.end,'yyyy-MM-dd HH:mm:ss'),function(data){
									if(data.returnCode=='success'){
									}
								})
							},

							eventResize : function(event, dayDelta,minuteDelta, revertFunc) {
								alert("Resize");
							},
							selectable : true,
							select : function(startDate, endDate, allDay,jsEvent, view) {
								var start = $.fullCalendar.formatDate(startDate, 'yyyy-MM-dd HH:mm:ss');
								var end = $.fullCalendar.formatDate(endDate,'yyyy-MM-dd HH:mm:ss');
								$("#calendarForm").find("#id").val('');
								$("#title").val('');
								$("#startTime").val(start);
								$("#endTime").val(end);
								  layer.open({
										area : [ '350px', '350px' ],
										title : "新建日程",
										type : 1,
										shadeClose : true,
										content : $('#showdiv'),
										btn : [ '确定', '取消' ],
										yes : function(index, layero) {
											if($("#title").val().length<1){
												layer.msg("日程主题不能为空!");
											    return;
											}
											$.post("${ctx}/crm/calendar/addCalendar",{title:$("#title").val(),startTime:$("#startTime").val(),endTime:$("#endTime").val()},function(data){
												if(data.returnCode=='success'){
													layer.close(index);
												    $('#calendar').fullCalendar('refetchEvents');
												}
											})
										}
									});
							},
							dayClick : function(date, allDay, jsEvent, view) {
							},
							eventClick : function(calEvent, jsEvent, view) {
								$.ajax({
									url : "${ctx}/crm/calendar/selCalendarById?id="+calEvent.id,
									async : false,
									success : function(data) {
										if (data.data) {
											$("#title").val(data.data.title);
											$("#startTime").val(data.data.startTime);
											$("#endTime").val(data.data.endTime);
											 layer.open({
													area : [ '350px', '350px' ],
													title : "编辑日程",
													type : 1,
													shadeClose : true,
													content : $('#showdiv'),
													btn : [ '修改','删除', '取消' ],
													yes : function(index, layero) {
														if($("#title").val().length<1){
															layer.msg("日程主题不能为空!");
														    return;
														}
														$.post("${ctx}/crm/calendar/updCalendar",{id:data.data.id,title:$("#title").val(),startTime:$("#startTime").val(),endTime:$("#endTime").val()},function(data){
															if(data.returnCode=='success'){
																layer.msg("修改成功");
																layer.close(index);
															    $('#calendar').fullCalendar('refetchEvents');
															}
														})
													},
													btn2:function(index,layero){
														$.post("${ctx}/crm/calendar/delCalendar?id="+data.data.id,function(data){
															if(data.returnCode=='success'){
																layer.msg("删除成功");
																layer.close(index);
															    $('#calendar').fullCalendar('refetchEvents');
															}
																
														})
													}
												});
										}
									}
								});
							},
							eventMouseover:function(calEvent, jsEvent, view){
								layer.tips('日程信息:'+calEvent.title+'<br/>开始时间:'+$.fullCalendar.formatDate(calEvent.start, 'yyyy-MM-dd HH:mm:ss')+'<br/>结束时间:'
								+$.fullCalendar.formatDate(calEvent.end, 'yyyy-MM-dd HH:mm:ss'),$("#"+calEvent.id),{
									  tips: [3, '#74AAC5'] //还可配置颜色
								});
							},
							eventMouseout:function(event, jsEvent, view){
								layer.closeAll('tips');
							},
							eventAfterRender : function(event, element, view) {//数据绑定上去后添加相应信息在页面上
								var fstart = $.fullCalendar.formatDate(
										event.start, "HH:mm");
								var fend = $.fullCalendar.formatDate(event.end,
										"HH:mm");
								var confbg = '';
                           confbg = confbg	+ '<span class="fc-event-bg" id="'+event.id+'"></span>';
								if (view.name == "month") {//按月份
									var evtcontent = '<div class="fc-event-vert"><a>';
									evtcontent = evtcontent + confbg;
									evtcontent = evtcontent
											+ '<span class="fc-event-titlebg">'
											+ fstart + " - " + fend+"  内容:"
											+ event.title + '</span>';
									element.html(evtcontent);
								} else if (view.name == "agendaWeek") {//按周
									var evtcontent = '<div class="fc-event-vert"><a>';
									evtcontent = evtcontent + confbg;
									evtcontent = evtcontent
									+ '<span class="fc-event-titlebg">'
									+ fstart + " - " + fend+"  内容:"
									+ event.title + '</span>';
									element.html(evtcontent);
								} else if (view.name == "agendaDay") {//按日
									var evtcontent = '<div class="fc-event-vert"><a>';
									evtcontent = evtcontent + confbg;
									evtcontent = evtcontent
									+ '<span class="fc-event-titlebg">'
									+ fstart + " - " + fend+"  内容:"
									+ event.title + '</span>';
									element.html(evtcontent);
								}
							},
						});

	});
</script>
</head>
<body>
	<div id="main" style="width: 1060px">
		<div id='calendar'></div>
		<div id="showdiv" style="display: none; width: 350px;">
			<form class="js-validation-upload-auth form-horizontal"
				id="calendarForm" method="post">
				<input type="hidden" id="id" name="id">
				<div class="control-group" id="spInfoChargePrice"
					style="margin-top: 20px;">
					<label class="col-md-2 control-label"><span
						style="color: red">*</span>主题：</label>
					<div class="col-md-10" id="reChargeText">
						<input type="text" id="title" name="title"
							class="form-control">
					</div>
				</div>
				<div class="control-group" id="spInfoChargePrice">
					<label class="col-md-4 control-label"><span
						style="color: red">*</span>起止时间：</label>
					<div class="col-md-7" id="reChargeText">
						<input type="text" id="startTime" readOnly="readOnly"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"
							name="startTime" class="form-control">
					</div>
				</div>
				<div class="control-group" id="spInfoChargePrice">
					<label class="col-md-4 control-label"></label>
					<div class="col-md-7" id="reChargeText">
						<input type="text" id="endTime" readOnly="readOnly"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"
							 name="endTime" class="form-control">
					</div>
				</div>
				<div class="control-group" id="spInfoChargePrice">
					<label class="col-md-4 control-label">备注：</label>
					<div class="col-md-7" id="reChargeText">
						<textarea type="text" id="note" name="note"
							class="form-control"></textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>