//$(function() {
//	if (typeof (queryQueueReplyStatus) != "undefined") {
//		clearInterval(queryQueueReplyStatus);
//	}
//	if (typeof (queryReplyStatus) != "undefined") {
//		alert("定时器不为空，清除定时器");
//		clearInterval(queryReplyStatus);
//	}
//})
function pageName() {
	var strUrl = location.href;
	var arrUrl = strUrl.split("/");
	var strPage = arrUrl[arrUrl.length - 1];
	return strPage;
}
// 单选
function SelectOneAuth() {
	if (isallchecked()) {
		document.getElementById("sltAll").checked = true;
	}
	if (isalldischecked()) {
		document.getElementById("sltAll").checked = false;
	}
}

// 全选、反选
function selectAllAuth() {
	var ischecked = document.getElementById("sltAll").checked;
	if (ischecked) {
		checkallbox();
	} else {
		discheckallbox();
	}
}
function checkallbox() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = true;
	}
}
function discheckallbox() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = false;
	}
}
function isallchecked() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		if (!boxarray[i].checked) {
			return false;
		}
	}
	return true;
}
function isalldischecked() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		if (!boxarray[i].checked) {
			return true;
		}
	}
	return false;
}

var imgIndex;
function showImage(obj) {
	var scrnH = $(document).height() - 200;
	var scrnW = $(document).width() - 400;
	var img = new Image();
	img.src = $(obj).attr("src");
	var hight = img.height;
	var width = img.width;
	if (width > hight) {
		var per = (hight / width);
		if (width > scrnW) {
			width = scrnW;
			hight = width * per;
			if (hight > scrnH) {
				hight = scrnH;
				width = hight / per;
			}
		}
	} else {
		var per = width / hight;
		if (hight > scrnH) {
			hight = scrnH;
			width = hight * per;
		}
	}
	if ($("#hiddenImg").attr("src")) {
		$("#hiddenImg").attr("src", $(obj).attr("src"));
		$("#hiddenImg").width(width);
		$("#hiddenImg").height(hight);
	} else {
		$("body").append(
				"<img src='" + $(obj).attr("src")
						+ "' id='hiddenImg' style='display:none;width:" + width
						+ "px;height:" + hight + "px;'>");
	}

	imgIndex = layer.open({
		type : 1,
		title : false,
		closeBtn : true,
		area : [ width, hight ],
		skin : 'layui-layer-nobg', // 没有背景色
		shadeClose : true,
		content : $("#hiddenImg")
	});
}

function CurentTimes(startTime, addday) {
	var now0 = new Date(startTime);
	var now = new Date(now0);
	now.setDate(now0.getDate() + addday);
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day;
	return (clock);
}

function CurentTimeshms(startTime, addday) {
	var now0 = new Date(startTime);
	var now = new Date(now0);
	now.setDate(now0.getDate() + addday);
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day;
	clock = clock + " "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
	return (clock);
}