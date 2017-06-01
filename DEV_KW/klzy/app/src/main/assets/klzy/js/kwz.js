KWZ = {
	baseUrl : '../',
	acall : function (app, p,cbk) {
		if (null != p && "function" == typeof p) {
			p = p();
		}
		KWZ.rpc.KwRpc.call(app, p,KWZ.sys,function(ret){
			if (KWZ.checkStatusCode(ret)) {
				KWZ.logApp(app,p,ret,KWZ.sys);
				var data = ret.data;
				cbk(data);
			}
		});
	},
	rcall : function (app, p, t, c) {
		if (null != p && "function" == typeof p) {
			p = p();
		}
		var ret;
		ret = KWZ.rpc.KwRpc.call(app, p,KWZ.sys);
		if (KWZ.checkStatusCode(ret)) {
			KWZ.logApp(app,p,ret,KWZ.sys);
			var data = ret.data;
			if ("object" == typeof t) {
				data.tempData = t;
			}
			if ("object" == typeof c) {
				data.cons = {};
				for (key in c) {
					data.cons[key] = KWZ.getCons(c[key]);
				}
			}
			return data;
		}
		return null;
	}
};