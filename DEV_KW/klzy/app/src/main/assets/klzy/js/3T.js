var iLS={
close:function(i){
	if (iLS[i]) delete iLS[i];
},
ajax:function(o){
	if(o.url && KWZ && KWZ.baseUrl){
		o.url = KWZ.baseUrl + o.url;
	}
	if(o.async)$.ajax(o);
	else {
		var rs="";
		$.ajax({url:o.url,async:false,data:o.data,type:o.type,dataType:o.dataType,cache:false,/*ifModified:true,*/beforeSend: function(request) {
            if(KWZ && KWZ.browserid && KWZ.browserid !="")request.setRequestHeader("bid", KWZ.browserid);
         }, success:function (html) {	rs=html;}});
		if(o.success)o.success(rs);
		return rs;
	}
},
ready:function(p){if(iLS.LS1){iLS.LS1.ready(p)}else{
	iLS.LS1=iLS.open("KW3T",{NS:"KW3T"});
	iLS.LS1.ready(function(){
		if(!document.getElementById("loadjs")){
			var loadjs=document.createElement('div');loadjs.id="loadjs";loadjs.style.display="none";document.body.appendChild(loadjs);
		}
        iLS.LS1.clear();       
	})
	iLS.LS1.ready(p)
}}, 
open:function(i,o){
	if (iLS[i]) return iLS[i];
	else {
		iLS[i]={};
		var j=iLS[i];
		j.opt={type:"DOMSTORAGE",pf:"LS",NS:"storage",size:2*1024*1024};
		for(var q in o)j.opt[q]=o[q];
		j.n=[];
		j.isReady=false;
		j.draw=function(){j.isReady=true;for(var q=0,p=j.n.length;q<p;q++){j.n[q].call(j)}j.n=null};
		j.ready=function(p){if(j.isReady){p.call(j)}else{j.n.push(p)}};
		
		if(j.opt.type=="DATABASE")j.e={//todo
		}
		else if( window.localStorage||window.globalStorage ){
			j.e={
			storage:null,
			isAvailable:function(){return window.localStorage||window.globalStorage},
			initialize:function(p){this.storage=window.localStorage?window.localStorage:window.globalStorage[location.hostname];p.call(null)},
			set:function(p,q){this.storage.setItem(p,q)},
			get:function(p){return this.storage.getItem(p)},
			getKeys:function(q){var p=[],r,t=this.storage;for(var s=t.length;s--;){r=t.key(s);if(q){if(0===r.indexOf(q))p.push(r)}else p.push(r)}return p},
			remove:function(p){this.storage.removeItem(p)},
			getAll:function(){var p={},r,s,t=this.storage;for(var q=t.length;q--;){r=t.key(q);s=t.getItem(r);p[r]=(s&&s.value)?s.value:s}return p},
			clear:function(){this.storage.clear()}
			}
				j.clear=function(){return j.e.clear();}
				j.get=function(q){return j.e.get(q);}
				j.set=function(q,r){j.e.set(q,r)}
				if(!j.isReady){
					if(j.e.isAvailable()){j.e.initialize(j.draw);}
				}
		} else {
			j.e={
			isAvailable:true,
			initialize:function(p){p.call(null)},
			set:function(p,q){},
			get:function(p){return null},
			getKeys:function(q){ return []},
			remove:function(p){},
			getAll:function(){},
			clear:function(){}
				}
			j.clear=function(){return j.e.clear();}
			j.get=function(q){return j.e.get(q);}
			j.set=function(q,r){j.e.set(q,r)}
			j.isReady=true;
		}
	}
	return iLS[i]
},
L:{}
}
iLS.u2t=function(url,cbk){
 if("function" == typeof (cbk || "")){
	iLS.ajax({url:url,dataType:'html',async:true,type:'GET',timeout:8000,error:function(html){ alert("u2t error "+url) },success:cbk});
 }else {
 return iLS.ajax({url:url,dataType:'html',async:false,type:'GET',timeout:8000,error:function(html){ alert("u2t error "+url) }});
 }
}
iLS.css=function(f,cb){
	LS = iLS.LS1;
	if(!(f in iLS.L)){
		var st = document.createElement('link');
		st.href = f;
		st.rel = 'stylesheet';
		st.type = 'text/css';
		document.getElementsByTagName('head')[0].appendChild(st);	
		iLS.L[f]=true;	
	}
}
iLS.js=function(f,cb){ LS = iLS.LS1;if(!(f in iLS.L)){var v=LS.e.get(f.replace(/[\/\.]/g,'_'));
	var async = "function" == typeof (cb || "");
	var cbk=function(v){
		if(v){LS.set(f.replace(/[\/\.]/g,'_'),v)
			iLS.L[f]=true;document.getElementById("loadjs").innerHTML="";
			var script=document.createElement('script'); script.type='text/javascript'; script.text= v;document.getElementById("loadjs").appendChild(script);
			document.getElementById("loadjs").innerHTML="";
		}
		if(async)cb();
	}
	if(!v){if(async){iLS.u2t(f,cbk)}else{v=iLS.u2t(f);cbk(v)}}
	else cbk(v);
}}
iLS.ajs=function(f,cb){ cb="function" == typeof (cb || "")?cb : function(){};iLS.js(f,cb)}
iLS.tpl=function(f,cb){ LS = iLS.LS1;var v=LS.e.get(f.replace(/[\/\.]/g,'_'));
    var async = "function" == typeof (cb || "");
	if(!v){
		if(async)iLS.u2t(f,function(v){LS.set(f.replace(/[\/\.]/g,'_'),v);cb(v);})
		else{v=iLS.u2t(f);if(v){LS.set(f.replace(/[\/\.]/g,'_'),v);return v}else return '' }
	} else { if(async){cb(v)}else return v;}
}
iLS.bind=function(f, o){return function(){return f.apply(o, arguments)}}
iLS.o2s = function (o) {
		if ("number" == (szTp = typeof o)) {
			return isFinite(o) ? o : 0;
		} else {
			if ("boolean" == szTp || null == o) {
				return o;
			}if ("object" == szTp){
				if(o.constructor==Array)return iLS.list2s(o);
				return iLS.o2json(o);
			} else {
				k = {"\\":"\\","\r":"r", "\n":"n", "\t":"t", "\b":"b", "\f":"f", "'":"\'"};
				return "\'" + (o || "").toString().replace(/([\\\r\n\t\b\f'])/gm, function (a, b) {
					return "\\" + k[b];
				}) + "\'";
			}
		}
	};
iLS.list2s = function (o) {
	if(o.length>0){
		var k, a=[];
		for (k in o)a.push(iLS.o2s(o[k]));
		return "[" + a.join(",").replace(/([\r\n\t\b\f"])/gm, "\\$1") + "]";
	} else return '[]';
};	
iLS.o2json = function (o) {var k, a=[];
		if ("object" == typeof o && o) {
			for (k in o) {
				a.push("'" + k + "':" + iLS.o2s(o[k]));
			}
			return "{" + a.join(",").replace(/([\r\n\t\b\f"])/gm, "\\$1") + "}";
		} else {
			return iLS.o2s(o);
		}
	};
iLS.arg2s=function (p){var b=[],i=0, j=p.length;for (;i<j;i++) b.push(iLS.o2json(p[i]));return "[" + b.join(",")+"]";}
iLS._A=function (p) {var r = [], i = 0, j = p.length;for (; i < j; i++)r.push(p[i]);return r;};
iLS.RPC=function(){
	var params = iLS._A(arguments),cbk = params[params.length-1],bAsync = "function" == typeof (cbk || ""),oRst = {};
	bAsync && params.pop();
	var key="{\"allPms\":{},\"method\":\"" + this.methodName + "\",\"_id_\":\"" + this["_id_"] + "\",\"params\":" +iLS.arg2s(params)+ "}" ;
	key=KWencode(key);
	iLS.ajax({url:this.url,dataType: 'html',async:bAsync,type: 'POST',	timeout: this.timeout,
	data:key,
	error: function(html){ },   
	success: function(html){
		html=html.replace(/&#(\d+);/gm,function(){	return String.fromCharCode(arguments[1]);});
		try {
			eval("var oTmp = " + html);
			oRst = oTmp;
			bAsync && cbk.apply(oRst, [oRst]);
		}catch (e){}
	}
	});
	return oRst;
};
iLS.getUrl=function(url,hkey){
	if(url){
		url += "?token="+hkey;
	}else{ 
		url = "JRPC?token="+hkey;
	}
	return url;
}
iLS.initRPC=function(url,timeout){
	if(url)url += "?token="+KWZ.hkey;else url = "JRPC?token="+KWZ.hkey;
	var lct=document.location;
	if (iLS['_'+url+'_'])return(iLS['_'+url+'_']);
	else iLS['_'+url+'_']={}
	if(-1 == url.indexOf("http:") && -1==url.indexOf(lct.host))return iLS.initLocal(url,timeout,iLS['_'+url+'_']);
	else return iLS.initRemote(url,timeout,iLS['_'+url+'_']);
};
iLS.initLocal=function(url,timeout,o){
	timeout || (timeout=10000);
//	iLS.ajax({url: url,dataType: 'html',async:false,type: 'GET',	timeout: timeout,
//			error: function(html){ },   
//			success: function(html){
//				eval("obj = " + html );
//				obj = obj.result;
//			}  
//	});
	obj=[{_name_:'KwRpc',_id_:'KwRpc',methods:['getTime','call','getTplMap','getAppTpl','getTpl','reloadPrep']}];
	if(obj){
		for (var i = 0; i < obj.length; i++){
			var n=obj[i]['_id_'];
			o[n]={};
			var m=obj[i]["methods"];
			for (var j=m.length-1; j >= 0; j--){
				o[n][m[j]]=iLS.bind(iLS.RPC,{url:url,timeout:timeout, methodName:m[j], "_id_":obj[i]['_id_']})
			}
		}
	}
	o['url']=url;
	iLS['_'+url+'_']=o;
	return o;
};
iLS.RRPC=function(){
	var oRst = {}
	var params=[];
	var i = 0,j=arguments.length-1;
	var cbk = (j>=0)?arguments[j]:function(){};
	for (; i < j; i++)	params.push(arguments[i]);
	var key="{'allPms':{},'method':'" + this.methodName + "','_id_':'" + this["_id_"] + "','params':" +iLS.arg2s(params)+ "}" ;
	iLS.domainData(this.url,this.timeout,key,function(data){
		eval("var oTmp = " + data);
		oRst = oTmp;
		cbk(oRst);
	});
};
iLS.initRemote=function(url,timeout,o){
	timeout || (timeout=10000)
	var proxyUrl=url.replace(/JRPC/, "proxy.html");
	iLS.domainData(proxyUrl,timeout,'',function(data){
		eval("obj = " + data );
		obj = obj.result;
		if(obj)for (var i = 0; i < obj.length; i++){
			var n=obj[i]['_id_'];
			o[n]={};
			var m=obj[i]["methods"];
			
			for (var j=m.length-1; j >= 0; j--){
				o[n][m[j]]=iLS.bind(iLS.RRPC,{url:proxyUrl,timeout:timeout, methodName:m[j], "_id_":obj[i]['_id_']})
			}
			iLS['_'+url+'_']=o;
		}
	});
	return o;
};
iLS.domainData=function (url,timeout,key, fn){
    var state = 0;
	var iframe ;
	function setret(e){
			 var data = e.data;
			 fn(data);
	}
	var loadfn = function(){
            if(state==0){
				state += 1;
				var lct = document.location.href;
				var appPath=lct.substring(0,lct.lastIndexOf("/")+1);
                iframe.contentWindow.location = appPath+'robot.html';
			}else {
				fn(iframe.contentWindow.name);
				iframe.contentWindow.document.write('');
                iframe.contentWindow.close();
                document.body.removeChild(iframe);
                iframe.src = '';
                iframe = null;
            }
	}
	if (typeof window.postMessage != 'undefined') {
		if(iLS[url+'_iframe']){
			iframe=iLS[url+'_iframe'];
			if (typeof window.addEventListener != 'undefined') {
						window.addEventListener('message', setret, false);
			} else if (typeof window.attachEvent != 'undefined') {
						window.attachEvent('onmessage', setret);
			}
			if(key=="")key="{'url':'JRPC'}";
			iframe.contentWindow.postMessage(key,'*');
		}
		else {
			iframe=document.createElement('iframe');
			iLS[url+'_iframe']=iframe;
			iframe.src = url;
			iframe.style.display='none';
			document.body.appendChild(iframe);
			var rr=function(){
				if (typeof window.addEventListener != 'undefined') {
						window.addEventListener('message', setret, false);
				} else if (typeof window.attachEvent != 'undefined') {
						window.attachEvent('onmessage', setret);
				}	
				if(key=="")key="{'url':'JRPC'}";
				iframe.contentWindow.postMessage(key,'*');
			}
			if(iframe.attachEvent){
				iframe.attachEvent('onload', rr);
			} else {
				iframe.onload = rr;
			}
		}
	} else {
		if(key=="")key="{'url':'JRPC'}";
		if(window.attachEvent){
			iframe = document.createElement('<iframe name="' + key + '"  style="display:none" >');
			iframe.attachEvent('onload', loadfn);
		}else{
			iframe = document.createElement('iframe');
			iframe.style.display='none';
			iframe.name=key;
			iframe.onload = loadfn;
		}
		iframe.src = url;
		document.body.appendChild(iframe);
	}		
};

