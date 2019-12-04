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
	//��֤������Ƿ�֧��WebSocketЭ��  
	if (!window.WebSocket) {   
		alert("WebSocket not supported by this browser!");   
	}
	display();
}) 

function display() {
	//����webSocket�������${uCode}��{uCode}���Ӧ  
	ws=new WebSocket("ws://${pageContext.getRequest().getServerName()}:${pageContext.getRequest().getServerPort()}${pageContext.request.contextPath}/websocket/" + uCode);
	//������Ϣ  
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
	//����websocket���¼�������������һЩ��ʼ����������������û������������˷�������Ϣ�ҿ��Է������ݿ���û�һ���߾͵��ò�ѯ����  
	ws.onopen = function(event) {
		sendPath();
	};    
}
//��ӡ��Ϣ�ķ��� 
var log = function(s) { 
	if (document.readyState !== "complete") {    
		log.buffer.push(s);    
	} else {
		var contenValue = $("#contentId").val();
		$("#contentId").val(contenValue + s + "\n");
	}    
}  
  
//������Ϣ���ú���
function sendMsg(){
	//�����־��ʾ���еĴ�����Ϣ
	cleanLogMontorManager(); 
	var client=uCode;
	var filePath = $('#filePath').text().slice(8);
	ws.send(filePath);  
}

//������Ϣ��ѯ�ɲ鿴��־�ļ�
function sendPath(){
	//��ѯ��־�������еĿɶ���־�ļ�
	ws.send("filePaht");
}
//������ɷ�����־ҳ���û�����
function randomString() {
	var timestamp = new Date().getTime();
    /****Ĭ��ȥ�������׻������ַ�oOLl,9gq,Vv,Uu,I1****/
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    
	var maxPos = $chars.length;
	var randomStr = '';
����for (let i = 0; i < 10; i++) {
      randomStr += $chars.charAt(Math.floor(Math.random() * maxPos));
����}
����return randomStr + timestamp;
}

//�����־���
function cleanLogMontorManager(){
	$("#contentId").val("");
}

//����̨���ɵ�html������ص�ul��
function showLogFileName(fileName){
	$('ul li').remove();
	$("ul").append(fileName);
}

//���ص�ǰѡ����־
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
			obj.placeholder.text('��־�ļ�����: ' + obj.val);
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
				<span id="filePath">��־�ļ�����:</span>
				<ul class="dropdown" tabindex="1">
				</ul>
			</div>
			<button id="sendButton" onClick="javascript:sendMsg()">�鿴��־���</button>
			<button onClick="cleanLogMontorManager()">�����־���</button>
			<button onClick="downLoadLogFile()">����ѡ����־</button>
		</div>
	</section>
	<div id="valueLabel"></div>
	<textarea id="contentId" readonly="readonly"></textarea>
	<br />
	
</body>
</html>