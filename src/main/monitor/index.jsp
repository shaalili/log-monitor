<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<html>
<head>
<title>WebSoket Demo</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/monitor/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/monitor/js/modernizr.custom.79639.js"></script> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/monitor/css/style.css" />
<noscript><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/monitor/css/noJS.css" /></noscript>
<script type="text/javascript">

var ws;  
var uCode = randomString();
$(function(){
	//验证浏览器是否支持WebSocket协议  
	if (!window.WebSocket) {   
		alert("WebSocket not supported by this browser!");   
	}
	display();
}) 

function display() {
	//创建webSocket，后面的${uCode}与{uCode}相对应  
	ws=new WebSocket("ws://${pageContext.getRequest().getServerName()}:${pageContext.getRequest().getServerPort()}${pageContext.request.contextPath}/websocket/" + uCode);
	//监听消息  
	ws.onmessage = function(event) {
		if(event.data.slice(0,4) == "<li>"){
			showLogFileName(event.data)
			var dd = new DropDown( $('#dd') );
		} else if(event.data.slice(0,8) == 'download') {
			window.open("http://${pageContext.getRequest().getServerName()}:${pageContext.getRequest().getServerPort()}/" + event.data.slice(8));
		} else {
			log(event.data);
		}
	}; 
	//建立websocket的事件，可以用来做一些初始化操作；比如如果用户不在线其他人发送了消息我可以放在数据库里，用户一上线就调用查询方法  
	ws.onopen = function(event) {
		sendPath();
	};    
}
//打印消息的方法 
var log = function(s) { 
	if (document.readyState !== "complete") {    
		log.buffer.push(s);    
	} else {
		var contenValue = $("#contentId").val();
		$("#contentId").val(contenValue + s + "\n");
	}    
}  
  
//发送消息调用函数
function sendMsg(){
	//清除日志显示框中的错误信息
	cleanLogMontorManager(); 
	var client=uCode;
	var filePath = $('#filePath').text().slice(8);
	ws.send(filePath);  
}

//发送消息查询可查看日志文件
function sendPath(){
	//查询日志框下所有的可读日志文件
	ws.send("filePaht");
}
//随机生成访问日志页面用户代码
function randomString() {
	var timestamp = new Date().getTime();
    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    
	var maxPos = $chars.length;
	var randomStr = '';
　　for (let i = 0; i < 10; i++) {
      randomStr += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return randomStr + timestamp;
}

//清空日志输出
function cleanLogMontorManager(){
	$("#contentId").val("");
}

//将后台生成的html代码加载到ul下
function showLogFileName(fileName){
	$('ul li').remove();
	$("ul").append(fileName);
}

//下载当前选中日志
function downLoadLogFile(){
	var downloadFile = $('#filePath').text().slice(8);
	ws.send("download" + downloadFile);
}
			
function DropDown(el) {
	this.dd = el;
	this.placeholder = this.dd.children('span');
	this.opts = this.dd.find('ul.dropdown > li');
	this.val = '';
	this.index = -1;
	this.initEvents();
}
DropDown.prototype = {
	initEvents : function() {
		var obj = this;

		obj.dd.on('click', function(event){
			$(this).toggleClass('active');
			return false;
		});

		obj.opts.on('click',function(){
			var opt = $(this);
			obj.val = opt.text();
			obj.index = opt.index();
			obj.placeholder.text('日志文件名称: ' + obj.val);
		});
	},
	getValue : function() {
		return this.val;
	},
	getIndex : function() {
		return this.index;
	}
}
</script>
</head>
<body">
	<section class="main">
		<div class="wrapper-demo">
			<div id="dd" class="wrapper-dropdown-1" tabindex="1">
				<span id="filePath">日志文件名称:</span>
				<ul class="dropdown" tabindex="1">
				</ul>
			</div>
			<button id="sendButton" onClick="javascript:sendMsg()">查看日志输出</button>
			<button onClick="cleanLogMontorManager()">清空日志输出</button>
			<button onClick="downLoadLogFile()">下载选中日志</button>
		</div>
	</section>
	<div id="valueLabel"></div>
	<textarea id="contentId" readonly="readonly"></textarea>
	<br />
	
</body>
</html>