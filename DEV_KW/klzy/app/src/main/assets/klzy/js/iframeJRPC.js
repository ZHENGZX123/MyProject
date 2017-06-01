var lct = document.location.href;
var appPath=lct.substring(0,lct.lastIndexOf("/")+1);
var AJAX = function (o) {
	if (window == this)
		return new AJAX(o);
	var _this = this;
	var fncbk = function(){
		if (_this.xml && 4 == _this.xml.readyState) {
			(200 == _this.xml.status || 404 == _this.xml.status || 500 == _this.xml.status)
			&& o.clbkFun 
			&& o.clbkFun(_this.xml.responseText.replace(/&#(\d+);/gm,
			function(){	return String.fromCharCode(arguments[1]);})),
				_this.xml && (delete _this.xml.onreadystatechange, delete _this.xml);
			}
	};
	if(-1 == o.url.indexOf("http:"))o.url = appPath + o.url;
	if (this.xml = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest()) {
		o.bAsync && (this.xml.onreadystatechange = function () {
		fncbk();
		});
		this.xml.open("POST", o.url + (-1 < o.url.indexOf("?") ? '&' : '?')
		+ "xui="+ new Date().getTime()
		, o.bAsync, "", "");
		this.xml.setRequestHeader("XUIAJAX",1);
		this.xml.setRequestHeader("CMHS","JsonRpc");
		this.xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		this.xml.setRequestHeader("user-agent", navigator.userAgent);
		this.xml.send(o.data && o.data.replace(/[\u4E00-\u9FA5]/gm, function()
		{
			return "&#" + arguments[0].charCodeAt(0) + ";";
		}) || "");
		if(!o.bAsync)fncbk();
	}
};
	
function setcall(e){
	try { eval ( "var obj=" + e.data  );}catch (e) {}
	if(obj){
	if(obj.url){
		AJAX({url:obj.url, bAsync:false, clbkFun:function () {
			try {
				window.parent.postMessage(arguments[0],'*');
			}catch (e) {}
		}});
	}else if (obj.login){
		AJAX({url:obj.login, bAsync:false, data:obj.params,clbkFun:function () {
				window.parent.postMessage(arguments[0],'*');
		}});
	}else{
		AJAX({url:'JRPC', bAsync:false, data:e.data,clbkFun:function () {
				window.parent.postMessage(arguments[0],'*');
		}});
	}
	}
}
if (typeof window.postMessage != 'undefined'){
	if (typeof window.addEventListener != 'undefined')
		window.addEventListener('message', setcall, false);
	else if (typeof window.attachEvent != 'undefined') 
		window.attachEvent('onmessage', setcall);
} else 	{
	data=window.name;
	try { eval ( "var obj=" + data  );}catch (e) {}
	if(obj){
	if(obj.url){
		AJAX({url:obj.url, bAsync:false, clbkFun:function () {
			try {
				window.name=arguments[0];
			}catch (e) {}
		}});
	}else if (obj.login){
		AJAX({url:obj.login, bAsync:false, data:obj.params,clbkFun:function () {
				window.name=arguments[0];
		}});
	}else {
		AJAX({url:'JRPC', bAsync:false, data:data,clbkFun:function () {
			try {
				window.name=arguments[0];
			}catch (e) {}
		}});
	}
	}
}