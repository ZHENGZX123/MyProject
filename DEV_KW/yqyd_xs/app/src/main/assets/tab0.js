// { "framework": "Vue" }

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 10);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var Utils = {
  dir: 'v1',
  //ip : 'http://192.168.8.6:8180/'
  ip: 'http://192.168.8.6:8882/v1'
  //ip : 'http://192.168.8.6:8881/v1'
};
Utils.setOpenUrl = function (context, arr) {
  var bundleUrl = context.bundleUrl;
  bundleUrl = new String(bundleUrl);
  var nativeBase;
  var isAndroidAssets = bundleUrl.indexOf('file://assets/') >= 0;

  var isiOSAssets = bundleUrl.indexOf('file:///') >= 0; //&& bundleUrl.indexOf('WeexDemo.app') > 0;
  if (isAndroidAssets) {
    nativeBase = 'file://assets/';
  } else if (isiOSAssets) {
    // file:///var/mobile/Containers/Bundle/Application/{id}/WeexDemo.app/
    // file:///Users/{user}/Library/Developer/CoreSimulator/Devices/{id}/data/Containers/Bundle/Application/{id}/WeexDemo.app/
    nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
  } else {
    var host = '192.168.8.23:12680';
    var matches = /\/\/([^\/]+?)\//.exec(context.bundleUrl);
    if (matches && matches.length >= 2) {
      host = matches[1];
    }
    nativeBase = 'http://' + host + '/' + Utils.dir + '/weex_jzd/';
  }
  var h5Base = './index.html?page=./' + Utils.dir + '/weex_jzd/';
  // in Native
  var base = nativeBase;
  if ((typeof window === 'undefined' ? 'undefined' : _typeof(window)) === 'object') {
    // in Browser or WebView
    base = h5Base;
  }

  if (Object.prototype.toString.call(arr) === '[object Array]') {
    //参数是数组类型[{url:'showcase/new-fashion/sub-type'},{url:'showcase/new-fashion/sub-type'}]
    for (var i in arr) {
      var obj = arr[i];
      if (obj.url) {
        obj.url = base + obj.url + '.js';
      }
    }
  } else if (Object.prototype.toString.call(arr) === '[object String]') {
    //参数是String类型 'showcase/new-fashion/sub-type'
    if (arr) {
      arr = base + arr + '.js';
    }
  } else if (Object.prototype.toString.call(arr) === '[object Object]') {
    //参数是对象类型{url:'showcase/new-fashion/sub-type'}
    if (arr.url) {
      arr.url = base + arr.url + '.js';
    }
  }
  return arr;
};

/***
* 改变图片路径
* arr: 需要改变图片路径的参数
* imgNameArr:(可选;必须是数组) 对象的key
*/
Utils.changeImg = function (arr, imgNameArr, subArrName) {
  //{picUrl:'aa.jpg',url:'b.jpg'} ['picUrl','url']
  if (Object.prototype.toString.call(arr) === '[object Array]') {
    for (var j in imgNameArr) {
      for (var i in arr) {
        var obj = arr[i];
        if (subArrName) {
          var subObj = obj[subArrName];
          var osubOjb;
          for (var k in subObj) {
            osubOjb = subObj[k];
            osubOjb[imgNameArr[j]] = Utils.ip + osubOjb[imgNameArr[j]];
          }
        }
        obj[imgNameArr[j]] = Utils.ip + obj[imgNameArr[j]];
      }
    }
  } else if (Object.prototype.toString.call(arr) === '[object String]') {
    arr = Utils.ip + arr;
  } else if (Object.prototype.toString.call(arr) === '[object Object]') {
    if (Object.prototype.toString.call(imgNameArr) !== '[object Array]') {
      console.log("必须是数组");
      return;
    }
    for (var i in imgNameArr) {
      arr[imgNameArr[i]] = Utils.ip + arr[imgNameArr[i]];
    }
  }
  return arr;
};

Utils.navigate = {
  /***
  * 打开一个新页面 
  * @params obj 当前页面作业域(传参数时为this)
  * @params url 页面的地址
  * @params animate 是否显示动画；值为'true'/'false'
  * @params callback 回调函数
  */
  push: function push(obj, url, animate, callback) {
    // var navigator = require('@weex-module/navigator');
    var params = {
      'url': url,
      'animated': animate
    };
    var vm = obj;
    /*navigator.push(params,function(e){
      if(typeof callback == 'function')
           callback();
    })
    */vm.$call('navigator', 'push', params, function () {
      if (typeof callback == 'function') callback();
    });
  },
  /***
  * 关闭当前页面 
  * @params obj 当前页面作业域(传参数时为this)
  * @params animate 是否显示动画；值为'true'/'false'
  * @params callback 回调函数
  */
  pop: function pop(obj, animate, callback) {
    // var navigator = require('@weex-module/navigator');
    var params = {
      'animated': animate
    };
    /*navigator.pop(params,function(e){
        if(typeof callback == 'function')
            callback();
    })*/
    obj.$call('navigator', 'pop', params, function () {
      if (typeof callback == 'function') callback();
    });
  },
  /***
  * 打开一个新页面 (防止在当前滑动到上一页面)
  * @params obj 当前页面作业域(传参数时为this)
  * @params url 页面的地址
  * @params animate 是否显示动画；值为'true'/'false'
  * @params callback 回调函数
  */
  present: function present(obj, url, animate, callback) {
    var params = {
      'url': url,
      'animated': animate
    };
    var vm = obj;
    /*navigator.present(params,function(e){
        if(typeof callback == 'function')
          callback();
    })*/
    vm.$call('navigator', 'present', params, function () {
      if (typeof callback == 'function') callback();
    });
  }
};

// Utils.navigate = {
// 	/***
// 	* 打开一个新页面 
//   * @params obj 当前页面作业域(传参数时为this)
// 	* @params url 页面的地址
// 	* @params animate 是否显示动画；值为'true'/'false'
// 	* @params callback 回调函数
// 	*/
// 	push : function(obj,url,animate,callback){
// 		var params = {
// 			'url': url,
// 			'animated' : animate,
// 		}
//     var vm = obj;    
//     vm.$call('navigator','push',params, function () {
//       if(typeof callback == 'function')
//          callback();
//     });
// 	},
// 	/***
// 	* 关闭当前页面 
//   * @params obj 当前页面作业域(传参数时为this)
// 	* @params animate 是否显示动画；值为'true'/'false'
// 	* @params callback 回调函数
// 	*/
// 	pop : function(obj,animate,callback){
// 		var params = {
// 			'animated' : animate,
// 		}

//     obj.$call('navigator','pop',params, function () {
//       if(typeof callback == 'function')
//         callback();
//     });
// 	},
//   /***
//   * 打开一个新页面 (防止在当前滑动到上一页面)
//   * @params obj 当前页面作业域(传参数时为this)
//   * @params url 页面的地址
//   * @params animate 是否显示动画；值为'true'/'false'
//   * @params callback 回调函数
//   */
//   present :  function(obj,url,animate,callback){
//     var params = {
//       'url': url,
//       'animated' : animate,
//     }
//     var vm = obj;    
//     vm.$call('navigator','present',params, function () {
//       if(typeof callback == 'function')
//          callback();
//     });
//   }
// }


/***
* 网络请求数据
* options :
     url:请求地址
     data:请求参数(可选)
     method:请求方法(可选默认为GET)（GET/POST）
     dataType:请求类型（'json','text' 或是 'jsonp'）(可选默认为'text')
     headers:HTTP请求头.
     success:成功返回方法
            return:
             status(number)：返回的状态码.
             ok(boolean): 如果状态码在200~299之间就为真。
             statusText(string)：状态描述文本
             data: 返回的数据，如果请求类型是json和jsonp，则它就是一个 object ，如果不是，则它就是一个 string 。
             headers(object): 响应头
     complete:请求完成返回方法
             return:
              readyState(number): 当前状态 state.'1':请求连接中 opened;'2':返回响应头中 received.;'3':正在加载返回数据
              status(number)：响应状态码.
              length(number): 已经接受到的数据长度. 你可以从响应头中获取总长度
              statusText(string)：状态文本
              headers(object): 响应头
*/
Utils.fetch = function (options) {
  if (!options) {
    console.log("options is not null");
    return;
  }
  if (!options.url || options.url == "") {
    console.log("url is not null");
    return;
  }
  var method = "GET";
  var type = "text";
  var stream = weex.requireModule('stream');
  var modal = weex.requireModule('modal');
  var headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
  // var requestUrl = Utils.ip + 'yjpts';
  var requestUrl = "";
  if (options.ip) {
    requestUrl = options.url;
  } else {
    requestUrl = Utils.ip + options.url;
  }

  // var requestUrl = 'http://192.168.8.19:8080/yjpts';
  // modal.alert({
  //           message:JSON.stringify(options),
  //           okTitle:'好的'
  //       },function(){
  //               // self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));
  //       });

  stream.fetch({
    headers: options.headers || headers,
    method: options.method || method,
    url: requestUrl,
    type: options.dataType || type,
    body: options.data ? options.data : ''
  }, function (response) {
    // modal.alert({
    //     message:JSON.stringify(response),
    //     okTitle:'好的'
    // },function(){
    //         // self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));
    // });
    //      self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));

    //debugger
    //console.log("response----"+JSON.stringify(response));
    if (!response.ok || response.ok == 0) {
      modal.toast({
        'message': '网络故障，请稍后再试！',
        'duration': 1
      });
    } else {
      if (options.success && typeof options.success == 'function') {
        var datas = {
          status: response.status,
          headers: response.headers,
          data: eval("(" + response.data + ")") // 用于手机 端
          // data :response.data //  用于PC端
        };
        options.success(datas);
      }
    }
  }, function (response) {
    if (options.complete && typeof options.complete == 'function') {
      var data = {
        readyState: response.readyState,
        status: response.status
      };
      options.complete(data);
    }
  }); //fetch end
};
Utils.acall = function (data) {
  if (!data.url) {
    console.log('url 不能为空');
    return;
  }
  var requestUrl = Utils.ip + data.url;
  var params = {};
  var method = "GET";
  var type = "json";
  if (data.data) {
    params = data.data;
  }
  if (data.method) {
    method = data.method;
  }
  if (data.type) {
    type = data.type;
  }
  $.ajax({
    type: method,
    url: requestUrl,
    dataType: type,
    data: params,
    async: true,
    success: function success(ret) {
      if (data.success && typeof data.success == 'function') {
        data.success(ret);
      }
    },
    error: function error(ret) {
      if (data.error && typeof data.error == 'function') {
        var datas = eval("(" + ret.responseText + ")");
        data.error(datas);
      }
    }

  });
};

//去除数组中的相同元素[1,1,1,2,3] --->[1,2,3]
Utils.unique = function (arr) {
  var result = [],
      hash = {};
  for (var i = 0, elem; (elem = arr[i]) != null; i++) {
    if (!hash[elem]) {
      result.push(elem);
      hash[elem] = true;
    }
  }
  return result;
};

/**输出几天前、几分钟前的内容*/
Utils.time = function (beginData) {
  var result;
  var v = beginData.replace(/-/g, "/");
  var s = new Date(v);
  var minute = 1000 * 60;
  var hour = minute * 60;
  var day = hour * 24;
  var halfamonth = day * 15;
  var month = day * 30;
  var now = new Date().getTime();
  var diffValue = now - s.getTime();
  var monthC = diffValue / month;
  var weekC = diffValue / (7 * day);
  var dayC = diffValue / day;
  var hourC = diffValue / hour;
  var minC = diffValue / minute;
  if (monthC > 12) {
    var s2 = new Date(v);
    result = s2.getFullYear() + "年" + (s2.getMonth() + 1) + "月" + s2.getDate() + "日";
  } else if (monthC >= 1) {
    result = parseInt(monthC) + "个月前";
  } else if (weekC >= 1) {
    result = parseInt(weekC) + "周前";
  } else if (dayC >= 1) {
    result = parseInt(dayC) + "天前";
  } else if (hourC >= 1) {
    result = parseInt(hourC) + "小时前";
  } else if (minC >= 1) {
    result = parseInt(minC) + "分钟前";
  } else result = "刚刚";
  return result;
};

module.exports = Utils;

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(64)
)

/* script */
__vue_exports__ = __webpack_require__(42)

/* template */
var __vue_template__ = __webpack_require__(87)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\kstfb_tjh.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-1fab10d2"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(67)
)

/* script */
__vue_exports__ = __webpack_require__(54)

/* template */
var __vue_template__ = __webpack_require__(91)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_mryl.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-388affa3"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.host = host;
exports.https = https;
exports.timeAgo = timeAgo;
exports.unescape = unescape;
function host(url) {
  if (!url) return '';
  var host = url.replace(/^https?:\/\//, '').replace(/\/.*$/, '');
  var parts = host.split('.').slice(-3);
  if (parts[0] === 'www') parts.shift();
  return parts.join('.');
}

function https(url) {
  if (WXEnvironment.platform === 'iOS' && typeof url === 'string') {
    return url.replace(/^http\:/, 'https:');
  }
  return '';
}

function timeAgo(time) {
  var between = Date.now() / 1000 - Number(time);
  if (between < 3600) {
    return pluralize(~~(between / 60), ' minute');
  } else if (between < 86400) {
    return pluralize(~~(between / 3600), ' hour');
  } else {
    return pluralize(~~(between / 86400), ' day');
  }
}

function pluralize(time, label) {
  if (time === 1) {
    return time + label;
  }
  return time + label + 's';
}

function unescape(text) {
  var res = text || '';[['<p>', '\n'], ['&amp;', '&'], ['&amp;', '&'], ['&apos;', '\''], ['&#x27;', '\''], ['&#x2F;', '/'], ['&#39;', '\''], ['&#47;', '/'], ['&lt;', '<'], ['&gt;', '>'], ['&nbsp;', ' '], ['&quot;', '"']].forEach(function (pair) {
    res = res.replace(new RegExp(pair[0], 'ig'), pair[1]);
  });

  return res;
}

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = {
  methods: {
    jump: function jump(to) {
      if (this.$router) {
        this.$router.push(to);
      }
    }
  }
};

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _vueRouter = __webpack_require__(15);

var _vueRouter2 = _interopRequireDefault(_vueRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

Vue.use(_vueRouter2.default); // import Vue from 'vue'
exports.default = new _vueRouter2.default({
  // mode: 'abstract',
  routes: [
  /* { path: '/login', name: 'login', component: require('./components/login.vue')},
   { path: '/wjmm', name: 'wjmm', component: require('./components/forget_pwd.vue')},*/
  // { path: '/wjzh', name: 'wjzh', component: require('./components/forget_admin.vue')},

  { path: '/index', name: 'index', component: __webpack_require__(17) },

  // 阅读任务
  { path: '/ydrw', name: 'ydrw', component: __webpack_require__(26) },

  // 任务详情
  { path: '/ydrw/ckjh', name: 'ydrw_jh', component: __webpack_require__(29) },
  // 阅读任务详情
  { path: '/ydrw_xq', name: 'ydrw_xq', component: __webpack_require__(35) },

  // 阅读任务 写读后感
  { path: '/ydrw/xdhg', name: 'ydrw_xdhg', component: __webpack_require__(34) },
  // 阅读任务 我的读后感
  { path: '/ydrw/wddhg', name: 'ydrw_wddhg', component: __webpack_require__(33) },

  // 阅读任务 开始闯关
  { path: '/ydrw/kscg', name: 'ydrw_kscg', component: __webpack_require__(30) },
  // 阅读任务 我的闯关
  { path: '/ydrw/wdcg', name: 'ydrw_wdcg', component: __webpack_require__(32) },

  //考试提分宝
  //   { path: '/kstfb', name: 'kstfb', component: require('./components/kstfb.vue')},


  // 阅读任务 闯关结果
  { path: '/ydrw/cgjg', name: 'ydrw_cgjg', component: __webpack_require__(1) },
  // 阅读任务 每日一练
  { path: '/wdrw_mryl', name: 'wdrw_mryl', component: __webpack_require__(21) },

  // 学习报告
  { path: '/ydbg', name: 'ydbg', component: __webpack_require__(22) },
  // 学习报告 阅读报告
  { path: '/ydbg/rwbg', name: 'ckbg_rwbg', component: __webpack_require__(23) },
  // 学习报告 查看详情
  { path: '/ydrw/ckxq', name: 'ckxq', component: __webpack_require__(28) },
  // 学习报告 报告闯关详情
  { path: '/ydrw/cgsb', name: 'ydrw_cgsb', component: __webpack_require__(27) },
  // 学习报告 任务详情
  { path: '/ydbg_rwxq', name: 'ydbg_rwxq', component: __webpack_require__(24) },
  //学习报告 每日一练详情
  { path: '/ydrw/mryl', name: 'ydrw_mril', component: __webpack_require__(2) },
  //我的闯关
  { path: '/ydrw_mryl', name: 'ydrw_mryl', component: __webpack_require__(2) },
  //提交
  { path: '/ydrw_tj', name: 'ydrw_tj', component: __webpack_require__(31) },
  //完成
  { path: '/kstfb_tjh', name: 'kstfb_tjh', component: __webpack_require__(1) },
  //学习报告 开始闯关
  { path: '/ydbg_wdcg', name: 'ydbg_wdcg', component: __webpack_require__(25) },

  //考试提分宝
  { path: '/kstfb', name: 'kstfb', component: __webpack_require__(18) },
  //考试提分宝 每日一练
  { path: '/kstfb/mryl', name: 'kstfb_mril', component: __webpack_require__(19) },

  // 书香榜
  { path: '/sxb', name: 'sxb', component: __webpack_require__(20) },
  // // 阅读资讯
  { path: '/ydzx', name: 'ydzx', component: __webpack_require__(36) },
  // 阅读资讯 详情
  { path: '/ydzx/xq', name: 'ydzx_xq', component: __webpack_require__(37) }]

});

/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _vuex = __webpack_require__(16);

var _vuex2 = _interopRequireDefault(_vuex);

var _actions = __webpack_require__(11);

var actions = _interopRequireWildcard(_actions);

var _mutations = __webpack_require__(13);

var mutations = _interopRequireWildcard(_mutations);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// Vuex is auto installed on the web
if (WXEnvironment.platform !== 'Web') {
  Vue.use(_vuex2.default);
} // import Vue from 'vue'


var store = new _vuex2.default.Store({
  actions: actions,
  mutations: mutations,

  state: {
    activeType: null,
    items: {},
    users: {},
    counts: {
      top: 20,
      new: 20,
      show: 15,
      ask: 15,
      job: 15
    },
    lists: {
      top: [],
      new: [],
      show: [],
      ask: [],
      job: []
    }
  },

  getters: {
    // ids of the items that should be currently displayed based on
    // current list type and current pagination
    activeIds: function activeIds(state) {
      var activeType = state.activeType,
          lists = state.lists,
          counts = state.counts;

      return activeType ? lists[activeType].slice(0, counts[activeType]) : [];
    },


    // items that should be currently displayed.
    // this Array may not be fully fetched.
    activeItems: function activeItems(state, getters) {
      return getters.activeIds.map(function (id) {
        return state.items[id];
      }).filter(function (_) {
        return _;
      });
    }
  }
});

exports.default = store;

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var host = 'http://192.168.8.6:8280/v1/';
// var host='http://m.kiway.cn/yqyd/v1/';
var urls = {
	// 班级列表
	bjlb: 'app/class/myclass',
	// 排名列表
	pmlb: 'app/class/classInfo',
	// 阅读教研分类列表
	fllb: 'app/res/module',
	// 阅读教研列表
	zylb: 'app/res/list',
	// 阅读教研详情
	zyxqlb: 'app/res/info',
	// 阅读资讯
	ydzx: 'app/information/',
	// 阅读资讯详情
	zxxq: 'app/information/{id}',
	// 书库年级列表
	sknj: 'app/book/grade',
	// 书库类别列表
	sklb: 'app/book/module',
	// 报告列表
	bglb: 'app/report/planReport',
	// 报告概括
	bggk: 'app/report/reportInfo',
	// 报告详情
	bgxq: 'app/report/reportDetail'
};
exports.default = {
	host: host,
	url: urls
};

/***/ }),
/* 8 */
/***/ (function(module, exports) {

exports.sync = function (store, router, options) {
  var moduleName = (options || {}).moduleName || 'route'

  store.registerModule(moduleName, {
    state: cloneRoute(router.currentRoute),
    mutations: {
      'router/ROUTE_CHANGED': function (state, transition) {
        store.state[moduleName] = cloneRoute(transition.to, transition.from)
      }
    }
  })

  var isTimeTraveling = false
  var currentPath

  // sync router on store change
  store.watch(
    function (state) { return state[moduleName] },
    function (route) {
      if (route.fullPath === currentPath) {
        return
      }
      isTimeTraveling = true
      currentPath = route.fullPath
      router.push(route)
    },
    { sync: true }
  )

  // sync store on router navigation
  router.afterEach(function (to, from) {
    if (isTimeTraveling) {
      isTimeTraveling = false
      return
    }
    currentPath = to.fullPath
    store.commit('router/ROUTE_CHANGED', { to: to, from: from })
  })
}

function cloneRoute (to, from) {
  var clone = {
    name: to.name,
    path: to.path,
    hash: to.hash,
    query: to.query,
    params: to.params,
    fullPath: to.fullPath,
    meta: to.meta
  }
  if (from) {
    clone.from = cloneRoute(from)
  }
  return Object.freeze(clone)
}


/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* script */
__vue_exports__ = __webpack_require__(38)

/* template */
var __vue_template__ = __webpack_require__(89)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\App.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _App = __webpack_require__(9);

var _App2 = _interopRequireDefault(_App);

var _router = __webpack_require__(5);

var _router2 = _interopRequireDefault(_router);

var _store = __webpack_require__(6);

var _store2 = _interopRequireDefault(_store);

var _vuexRouterSync = __webpack_require__(8);

var _filters = __webpack_require__(3);

var filters = _interopRequireWildcard(_filters);

var _mixins = __webpack_require__(4);

var _mixins2 = _interopRequireDefault(_mixins);

var _url = __webpack_require__(7);

var _url2 = _interopRequireDefault(_url);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_router2.default.beforeEach(function (to, from, next) {
    var fullPath = to.fullPath;
    var globalEvent = weex.requireModule('globalEvent');
    if (globalEvent) {
        var index = fullPath.match('/index') != null;
        var sc = fullPath.match('/sc') != null;
        var main = fullPath.match('/main') != null;
        var login = fullPath.match('/login') != null;
        globalEvent.removeEventListener("clickback");
        if (index || sc || main || login) {
            globalEvent.addEventListener("clickback", function (e) {
                var sjevent = weex.requireModule('SJevent');
                if (sjevent) {
                    sjevent.finish();
                }
            });
        } else {
            globalEvent.addEventListener("clickback", function (e) {
                _router2.default.back();
            });
        }
    }
    next(true);
});

// sync the router with the vuex store.
// this registers `store.state.route`
// import Vue from 'vue'
(0, _vuexRouterSync.sync)(_store2.default, _router2.default);

Vue.prototype.globalurl = _url2.default;
/*
var globalEvent = require('@weex-module/globalEvent');
globalEvent.addEventListener("clickback", function (e) {
        //toast: clickback is called

});*/
// register global utility filters.
Object.keys(filters).forEach(function (key) {
    Vue.filter(key, filters[key]);
});

// register global mixins.
Vue.mixin(_mixins2.default);

// create the app instance.
// here we inject the router and store to all child components,
// making them available everywhere as `this.$router` and `this.$store`.
new Vue(Vue.util.extend({ el: '#root', router: _router2.default, store: _store2.default }, _App2.default));

_router2.default.push('/index');

/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.FETCH_LIST_DATA = FETCH_LIST_DATA;
exports.LOAD_MORE_ITEMS = LOAD_MORE_ITEMS;
exports.ENSURE_ACTIVE_ITEMS = ENSURE_ACTIVE_ITEMS;
exports.FETCH_ITEMS = FETCH_ITEMS;
exports.FETCH_USER = FETCH_USER;

var _fetch = __webpack_require__(12);

var LOAD_MORE_STEP = 10;

// ensure data for rendering given list type
function FETCH_LIST_DATA(_ref, _ref2) {
  var commit = _ref.commit,
      dispatch = _ref.dispatch,
      state = _ref.state;
  var type = _ref2.type;

  commit('SET_ACTIVE_TYPE', { type: type });
  return (0, _fetch.fetchIdsByType)(type).then(function (ids) {
    return commit('SET_LIST', { type: type, ids: ids });
  }).then(function () {
    return dispatch('ENSURE_ACTIVE_ITEMS');
  });
}

// load more items
function LOAD_MORE_ITEMS(_ref3) {
  var dispatch = _ref3.dispatch,
      state = _ref3.state;

  state.counts[state.activeType] += LOAD_MORE_STEP;
  return dispatch('ENSURE_ACTIVE_ITEMS');
}

// ensure all active items are fetched
function ENSURE_ACTIVE_ITEMS(_ref4) {
  var dispatch = _ref4.dispatch,
      getters = _ref4.getters;

  return dispatch('FETCH_ITEMS', {
    ids: getters.activeIds
  });
}

function FETCH_ITEMS(_ref5, _ref6) {
  var commit = _ref5.commit,
      state = _ref5.state;
  var ids = _ref6.ids;

  // only fetch items that we don't already have.
  var newIds = ids.filter(function (id) {
    return !state.items[id];
  });
  return newIds.length ? (0, _fetch.fetchItems)(newIds).then(function (items) {
    return commit('SET_ITEMS', { items: items });
  }) : Promise.resolve();
}

function FETCH_USER(_ref7, _ref8) {
  var commit = _ref7.commit,
      state = _ref7.state;
  var id = _ref8.id;

  return state.users[id] ? Promise.resolve(state.users[id]) : (0, _fetch.fetchUser)(id).then(function (user) {
    return commit('SET_USER', { user: user });
  });
}

/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.fetch = fetch;
exports.fetchIdsByType = fetchIdsByType;
exports.fetchItem = fetchItem;
exports.fetchItems = fetchItems;
exports.fetchUser = fetchUser;
var stream = weex.requireModule('stream');
var baseURL = 'https://hacker-news.firebaseio.com/v0';

function fetch(path) {
  return new Promise(function (resolve, reject) {
    stream.fetch({
      method: 'GET',
      url: baseURL + '/' + path + '.json',
      type: 'json'
    }, function (response) {
      if (response.status == 200) {
        resolve(response.data);
      } else {
        reject(response);
      }
    }, function () {});
  });
}

function fetchIdsByType(type) {
  return fetch(type + 'stories');
}

function fetchItem(id) {
  return fetch('item/' + id);
}

function fetchItems(ids) {
  return Promise.all(ids.map(function (id) {
    return fetchItem(id);
  }));
}

function fetchUser(id) {
  return fetch('user/' + id);
}

/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.SET_ACTIVE_TYPE = SET_ACTIVE_TYPE;
exports.SET_LIST = SET_LIST;
exports.SET_ITEMS = SET_ITEMS;
exports.SET_USER = SET_USER;
function SET_ACTIVE_TYPE(state, _ref) {
  var type = _ref.type;

  state.activeType = type;
}

function SET_LIST(state, _ref2) {
  var type = _ref2.type,
      ids = _ref2.ids;

  state.lists[type] = ids;
}

function SET_ITEMS(state, _ref3) {
  var items = _ref3.items;

  items.forEach(function (item) {
    if (item) {
      Vue.set(state.items, item.id, item);
    }
  });
}

function SET_USER(state, _ref4) {
  var user = _ref4.user;

  Vue.set(state.users, user.id, user);
}

/***/ }),
/* 14 */
/***/ (function(module, exports) {

// shim for using process in browser
var process = module.exports = {};

// cached from whatever global is present so that test runners that stub it
// don't break things.  But we need to wrap it in a try catch in case it is
// wrapped in strict mode code which doesn't define any globals.  It's inside a
// function because try/catches deoptimize in certain engines.

var cachedSetTimeout;
var cachedClearTimeout;

function defaultSetTimout() {
    throw new Error('setTimeout has not been defined');
}
function defaultClearTimeout () {
    throw new Error('clearTimeout has not been defined');
}
(function () {
    try {
        if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
        } else {
            cachedSetTimeout = defaultSetTimout;
        }
    } catch (e) {
        cachedSetTimeout = defaultSetTimout;
    }
    try {
        if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
        } else {
            cachedClearTimeout = defaultClearTimeout;
        }
    } catch (e) {
        cachedClearTimeout = defaultClearTimeout;
    }
} ())
function runTimeout(fun) {
    if (cachedSetTimeout === setTimeout) {
        //normal enviroments in sane situations
        return setTimeout(fun, 0);
    }
    // if setTimeout wasn't available but was latter defined
    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
        cachedSetTimeout = setTimeout;
        return setTimeout(fun, 0);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedSetTimeout(fun, 0);
    } catch(e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
        } catch(e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
        }
    }


}
function runClearTimeout(marker) {
    if (cachedClearTimeout === clearTimeout) {
        //normal enviroments in sane situations
        return clearTimeout(marker);
    }
    // if clearTimeout wasn't available but was latter defined
    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
        cachedClearTimeout = clearTimeout;
        return clearTimeout(marker);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedClearTimeout(marker);
    } catch (e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
        } catch (e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
        }
    }



}
var queue = [];
var draining = false;
var currentQueue;
var queueIndex = -1;

function cleanUpNextTick() {
    if (!draining || !currentQueue) {
        return;
    }
    draining = false;
    if (currentQueue.length) {
        queue = currentQueue.concat(queue);
    } else {
        queueIndex = -1;
    }
    if (queue.length) {
        drainQueue();
    }
}

function drainQueue() {
    if (draining) {
        return;
    }
    var timeout = runTimeout(cleanUpNextTick);
    draining = true;

    var len = queue.length;
    while(len) {
        currentQueue = queue;
        queue = [];
        while (++queueIndex < len) {
            if (currentQueue) {
                currentQueue[queueIndex].run();
            }
        }
        queueIndex = -1;
        len = queue.length;
    }
    currentQueue = null;
    draining = false;
    runClearTimeout(timeout);
}

process.nextTick = function (fun) {
    var args = new Array(arguments.length - 1);
    if (arguments.length > 1) {
        for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
    }
    queue.push(new Item(fun, args));
    if (queue.length === 1 && !draining) {
        runTimeout(drainQueue);
    }
};

// v8 likes predictible objects
function Item(fun, array) {
    this.fun = fun;
    this.array = array;
}
Item.prototype.run = function () {
    this.fun.apply(null, this.array);
};
process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];
process.version = ''; // empty string to avoid regexp issues
process.versions = {};

function noop() {}

process.on = noop;
process.addListener = noop;
process.once = noop;
process.off = noop;
process.removeListener = noop;
process.removeAllListeners = noop;
process.emit = noop;

process.binding = function (name) {
    throw new Error('process.binding is not supported');
};

process.cwd = function () { return '/' };
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};
process.umask = function() { return 0; };


/***/ }),
/* 15 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process) {Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/**
  * vue-router v2.2.1
  * (c) 2017 Evan You
  * @license MIT
  */
/*  */

function assert (condition, message) {
  if (!condition) {
    throw new Error(("[vue-router] " + message))
  }
}

function warn (condition, message) {
  if (!condition) {
    typeof console !== 'undefined' && console.warn(("[vue-router] " + message));
  }
}

var View = {
  name: 'router-view',
  functional: true,
  props: {
    name: {
      type: String,
      default: 'default'
    }
  },
  render: function render (h, ref) {
    var props = ref.props;
    var children = ref.children;
    var parent = ref.parent;
    var data = ref.data;

    data.routerView = true;

    var name = props.name;
    var route = parent.$route;
    var cache = parent._routerViewCache || (parent._routerViewCache = {});

    // determine current view depth, also check to see if the tree
    // has been toggled inactive but kept-alive.
    var depth = 0;
    var inactive = false;
    while (parent) {
      if (parent.$vnode && parent.$vnode.data.routerView) {
        depth++;
      }
      if (parent._inactive) {
        inactive = true;
      }
      parent = parent.$parent;
    }
    data.routerViewDepth = depth;

    // render previous view if the tree is inactive and kept-alive
    if (inactive) {
      return h(cache[name], data, children)
    }

    var matched = route.matched[depth];
    // render empty node if no matched route
    if (!matched) {
      cache[name] = null;
      return h()
    }

    var component = cache[name] = matched.components[name];

    // inject instance registration hooks
    var hooks = data.hook || (data.hook = {});
    hooks.init = function (vnode) {
      matched.instances[name] = vnode.child;
    };
    hooks.prepatch = function (oldVnode, vnode) {
      matched.instances[name] = vnode.child;
    };
    hooks.destroy = function (vnode) {
      if (matched.instances[name] === vnode.child) {
        matched.instances[name] = undefined;
      }
    };

    // resolve props
    data.props = resolveProps(route, matched.props && matched.props[name]);

    return h(component, data, children)
  }
};

function resolveProps (route, config) {
  switch (typeof config) {
    case 'undefined':
      return
    case 'object':
      return config
    case 'function':
      return config(route)
    case 'boolean':
      return config ? route.params : undefined
    default:
      warn(false, ("props in \"" + (route.path) + "\" is a " + (typeof config) + ", expecting an object, function or boolean."));
  }
}

/*  */

var encodeReserveRE = /[!'()*]/g;
var encodeReserveReplacer = function (c) { return '%' + c.charCodeAt(0).toString(16); };
var commaRE = /%2C/g;

// fixed encodeURIComponent which is more comformant to RFC3986:
// - escapes [!'()*]
// - preserve commas
var encode = function (str) { return encodeURIComponent(str)
  .replace(encodeReserveRE, encodeReserveReplacer)
  .replace(commaRE, ','); };

var decode = decodeURIComponent;

function resolveQuery (
  query,
  extraQuery
) {
  if ( extraQuery === void 0 ) extraQuery = {};

  if (query) {
    var parsedQuery;
    try {
      parsedQuery = parseQuery(query);
    } catch (e) {
      process.env.NODE_ENV !== 'production' && warn(false, e.message);
      parsedQuery = {};
    }
    for (var key in extraQuery) {
      parsedQuery[key] = extraQuery[key];
    }
    return parsedQuery
  } else {
    return extraQuery
  }
}

function parseQuery (query) {
  var res = {};

  query = query.trim().replace(/^(\?|#|&)/, '');

  if (!query) {
    return res
  }

  query.split('&').forEach(function (param) {
    var parts = param.replace(/\+/g, ' ').split('=');
    var key = decode(parts.shift());
    var val = parts.length > 0
      ? decode(parts.join('='))
      : null;

    if (res[key] === undefined) {
      res[key] = val;
    } else if (Array.isArray(res[key])) {
      res[key].push(val);
    } else {
      res[key] = [res[key], val];
    }
  });

  return res
}

function stringifyQuery (obj) {
  var res = obj ? Object.keys(obj).map(function (key) {
    var val = obj[key];

    if (val === undefined) {
      return ''
    }

    if (val === null) {
      return encode(key)
    }

    if (Array.isArray(val)) {
      var result = [];
      val.slice().forEach(function (val2) {
        if (val2 === undefined) {
          return
        }
        if (val2 === null) {
          result.push(encode(key));
        } else {
          result.push(encode(key) + '=' + encode(val2));
        }
      });
      return result.join('&')
    }

    return encode(key) + '=' + encode(val)
  }).filter(function (x) { return x.length > 0; }).join('&') : null;
  return res ? ("?" + res) : ''
}

/*  */

var trailingSlashRE = /\/?$/;

function createRoute (
  record,
  location,
  redirectedFrom
) {
  var route = {
    name: location.name || (record && record.name),
    meta: (record && record.meta) || {},
    path: location.path || '/',
    hash: location.hash || '',
    query: location.query || {},
    params: location.params || {},
    fullPath: getFullPath(location),
    matched: record ? formatMatch(record) : []
  };
  if (redirectedFrom) {
    route.redirectedFrom = getFullPath(redirectedFrom);
  }
  return Object.freeze(route)
}

// the starting route that represents the initial state
var START = createRoute(null, {
  path: '/'
});

function formatMatch (record) {
  var res = [];
  while (record) {
    res.unshift(record);
    record = record.parent;
  }
  return res
}

function getFullPath (ref) {
  var path = ref.path;
  var query = ref.query; if ( query === void 0 ) query = {};
  var hash = ref.hash; if ( hash === void 0 ) hash = '';

  return (path || '/') + stringifyQuery(query) + hash
}

function isSameRoute (a, b) {
  if (b === START) {
    return a === b
  } else if (!b) {
    return false
  } else if (a.path && b.path) {
    return (
      a.path.replace(trailingSlashRE, '') === b.path.replace(trailingSlashRE, '') &&
      a.hash === b.hash &&
      isObjectEqual(a.query, b.query)
    )
  } else if (a.name && b.name) {
    return (
      a.name === b.name &&
      a.hash === b.hash &&
      isObjectEqual(a.query, b.query) &&
      isObjectEqual(a.params, b.params)
    )
  } else {
    return false
  }
}

function isObjectEqual (a, b) {
  if ( a === void 0 ) a = {};
  if ( b === void 0 ) b = {};

  var aKeys = Object.keys(a);
  var bKeys = Object.keys(b);
  if (aKeys.length !== bKeys.length) {
    return false
  }
  return aKeys.every(function (key) { return String(a[key]) === String(b[key]); })
}

function isIncludedRoute (current, target) {
  return (
    current.path.replace(trailingSlashRE, '/').indexOf(
      target.path.replace(trailingSlashRE, '/')
    ) === 0 &&
    (!target.hash || current.hash === target.hash) &&
    queryIncludes(current.query, target.query)
  )
}

function queryIncludes (current, target) {
  for (var key in target) {
    if (!(key in current)) {
      return false
    }
  }
  return true
}

/*  */

// work around weird flow bug
var toTypes = [String, Object];
var eventTypes = [String, Array];

var Link = {
  name: 'router-link',
  props: {
    to: {
      type: toTypes,
      required: true
    },
    tag: {
      type: String,
      default: 'a'
    },
    exact: Boolean,
    append: Boolean,
    replace: Boolean,
    activeClass: String,
    event: {
      type: eventTypes,
      default: 'click'
    }
  },
  render: function render (h) {
    var this$1 = this;

    var router = this.$router;
    var current = this.$route;
    var ref = router.resolve(this.to, current, this.append);
    var location = ref.location;
    var route = ref.route;
    var href = ref.href;
    var classes = {};
    var activeClass = this.activeClass || router.options.linkActiveClass || 'router-link-active';
    var compareTarget = location.path ? createRoute(null, location) : route;
    classes[activeClass] = this.exact
      ? isSameRoute(current, compareTarget)
      : isIncludedRoute(current, compareTarget);

    var handler = function (e) {
      if (guardEvent(e)) {
        if (this$1.replace) {
          router.replace(location);
        } else {
          router.push(location);
        }
      }
    };

    var on = { click: guardEvent };
    if (Array.isArray(this.event)) {
      this.event.forEach(function (e) { on[e] = handler; });
    } else {
      on[this.event] = handler;
    }

    var data = {
      class: classes
    };

    if (this.tag === 'a') {
      data.on = on;
      data.attrs = { href: href };
    } else {
      // find the first <a> child and apply listener and href
      var a = findAnchor(this.$slots.default);
      if (a) {
        // in case the <a> is a static node
        a.isStatic = false;
        var extend = _Vue.util.extend;
        var aData = a.data = extend({}, a.data);
        aData.on = on;
        var aAttrs = a.data.attrs = extend({}, a.data.attrs);
        aAttrs.href = href;
      } else {
        // doesn't have <a> child, apply listener to self
        data.on = on;
      }
    }

    return h(this.tag, data, this.$slots.default)
  }
};

function guardEvent (e) {
  // don't redirect with control keys
  if (e.metaKey || e.ctrlKey || e.shiftKey) { return }
  // don't redirect when preventDefault called
  if (e.defaultPrevented) { return }
  // don't redirect on right click
  if (e.button !== undefined && e.button !== 0) { return }
  // don't redirect if `target="_blank"`
  if (e.target && e.target.getAttribute) {
    var target = e.target.getAttribute('target');
    if (/\b_blank\b/i.test(target)) { return }
  }
  // this may be a Weex event which doesn't have this method
  if (e.preventDefault) {
    e.preventDefault();
  }
  return true
}

function findAnchor (children) {
  if (children) {
    var child;
    for (var i = 0; i < children.length; i++) {
      child = children[i];
      if (child.tag === 'a') {
        return child
      }
      if (child.children && (child = findAnchor(child.children))) {
        return child
      }
    }
  }
}

var _Vue;

function install (Vue) {
  if (install.installed) { return }
  install.installed = true;

  _Vue = Vue;

  Object.defineProperty(Vue.prototype, '$router', {
    get: function get () { return this.$root._router }
  });

  Object.defineProperty(Vue.prototype, '$route', {
    get: function get () { return this.$root._route }
  });

  Vue.mixin({
    beforeCreate: function beforeCreate () {
      if (this.$options.router) {
        this._router = this.$options.router;
        this._router.init(this);
        Vue.util.defineReactive(this, '_route', this._router.history.current);
      }
    }
  });

  Vue.component('router-view', View);
  Vue.component('router-link', Link);

  var strats = Vue.config.optionMergeStrategies;
  // use the same hook merging strategy for route hooks
  strats.beforeRouteEnter = strats.beforeRouteLeave = strats.created;
}

/*  */

var inBrowser = typeof window !== 'undefined';

/*  */

function resolvePath (
  relative,
  base,
  append
) {
  if (relative.charAt(0) === '/') {
    return relative
  }

  if (relative.charAt(0) === '?' || relative.charAt(0) === '#') {
    return base + relative
  }

  var stack = base.split('/');

  // remove trailing segment if:
  // - not appending
  // - appending to trailing slash (last segment is empty)
  if (!append || !stack[stack.length - 1]) {
    stack.pop();
  }

  // resolve relative path
  var segments = relative.replace(/^\//, '').split('/');
  for (var i = 0; i < segments.length; i++) {
    var segment = segments[i];
    if (segment === '.') {
      continue
    } else if (segment === '..') {
      stack.pop();
    } else {
      stack.push(segment);
    }
  }

  // ensure leading slash
  if (stack[0] !== '') {
    stack.unshift('');
  }

  return stack.join('/')
}

function parsePath (path) {
  var hash = '';
  var query = '';

  var hashIndex = path.indexOf('#');
  if (hashIndex >= 0) {
    hash = path.slice(hashIndex);
    path = path.slice(0, hashIndex);
  }

  var queryIndex = path.indexOf('?');
  if (queryIndex >= 0) {
    query = path.slice(queryIndex + 1);
    path = path.slice(0, queryIndex);
  }

  return {
    path: path,
    query: query,
    hash: hash
  }
}

function cleanPath (path) {
  return path.replace(/\/\//g, '/')
}

/*  */

function createRouteMap (
  routes,
  oldPathMap,
  oldNameMap
) {
  var pathMap = oldPathMap || Object.create(null);
  var nameMap = oldNameMap || Object.create(null);

  routes.forEach(function (route) {
    addRouteRecord(pathMap, nameMap, route);
  });

  return {
    pathMap: pathMap,
    nameMap: nameMap
  }
}

function addRouteRecord (
  pathMap,
  nameMap,
  route,
  parent,
  matchAs
) {
  var path = route.path;
  var name = route.name;
  if (process.env.NODE_ENV !== 'production') {
    assert(path != null, "\"path\" is required in a route configuration.");
    assert(
      typeof route.component !== 'string',
      "route config \"component\" for path: " + (String(path || name)) + " cannot be a " +
      "string id. Use an actual component instead."
    );
  }

  var record = {
    path: normalizePath(path, parent),
    components: route.components || { default: route.component },
    instances: {},
    name: name,
    parent: parent,
    matchAs: matchAs,
    redirect: route.redirect,
    beforeEnter: route.beforeEnter,
    meta: route.meta || {},
    props: route.props == null
      ? {}
      : route.components
        ? route.props
        : { default: route.props }
  };

  if (route.children) {
    // Warn if route is named and has a default child route.
    // If users navigate to this route by name, the default child will
    // not be rendered (GH Issue #629)
    if (process.env.NODE_ENV !== 'production') {
      if (route.name && route.children.some(function (child) { return /^\/?$/.test(child.path); })) {
        warn(
          false,
          "Named Route '" + (route.name) + "' has a default child route. " +
          "When navigating to this named route (:to=\"{name: '" + (route.name) + "'\"), " +
          "the default child route will not be rendered. Remove the name from " +
          "this route and use the name of the default child route for named " +
          "links instead."
        );
      }
    }
    route.children.forEach(function (child) {
      var childMatchAs = matchAs
        ? cleanPath((matchAs + "/" + (child.path)))
        : undefined;
      addRouteRecord(pathMap, nameMap, child, record, childMatchAs);
    });
  }

  if (route.alias !== undefined) {
    if (Array.isArray(route.alias)) {
      route.alias.forEach(function (alias) {
        var aliasRoute = {
          path: alias,
          children: route.children
        };
        addRouteRecord(pathMap, nameMap, aliasRoute, parent, record.path);
      });
    } else {
      var aliasRoute = {
        path: route.alias,
        children: route.children
      };
      addRouteRecord(pathMap, nameMap, aliasRoute, parent, record.path);
    }
  }

  if (!pathMap[record.path]) {
    pathMap[record.path] = record;
  }

  if (name) {
    if (!nameMap[name]) {
      nameMap[name] = record;
    } else if (process.env.NODE_ENV !== 'production' && !matchAs) {
      warn(
        false,
        "Duplicate named routes definition: " +
        "{ name: \"" + name + "\", path: \"" + (record.path) + "\" }"
      );
    }
  }
}

function normalizePath (path, parent) {
  path = path.replace(/\/$/, '');
  if (path[0] === '/') { return path }
  if (parent == null) { return path }
  return cleanPath(((parent.path) + "/" + path))
}

var index$1 = Array.isArray || function (arr) {
  return Object.prototype.toString.call(arr) == '[object Array]';
};

var isarray = index$1;

/**
 * Expose `pathToRegexp`.
 */
var index = pathToRegexp;
var parse_1 = parse;
var compile_1 = compile;
var tokensToFunction_1 = tokensToFunction;
var tokensToRegExp_1 = tokensToRegExp;

/**
 * The main path matching regexp utility.
 *
 * @type {RegExp}
 */
var PATH_REGEXP = new RegExp([
  // Match escaped characters that would otherwise appear in future matches.
  // This allows the user to escape special characters that won't transform.
  '(\\\\.)',
  // Match Express-style parameters and un-named parameters with a prefix
  // and optional suffixes. Matches appear as:
  //
  // "/:test(\\d+)?" => ["/", "test", "\d+", undefined, "?", undefined]
  // "/route(\\d+)"  => [undefined, undefined, undefined, "\d+", undefined, undefined]
  // "/*"            => ["/", undefined, undefined, undefined, undefined, "*"]
  '([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))'
].join('|'), 'g');

/**
 * Parse a string for the raw tokens.
 *
 * @param  {string}  str
 * @param  {Object=} options
 * @return {!Array}
 */
function parse (str, options) {
  var tokens = [];
  var key = 0;
  var index = 0;
  var path = '';
  var defaultDelimiter = options && options.delimiter || '/';
  var res;

  while ((res = PATH_REGEXP.exec(str)) != null) {
    var m = res[0];
    var escaped = res[1];
    var offset = res.index;
    path += str.slice(index, offset);
    index = offset + m.length;

    // Ignore already escaped sequences.
    if (escaped) {
      path += escaped[1];
      continue
    }

    var next = str[index];
    var prefix = res[2];
    var name = res[3];
    var capture = res[4];
    var group = res[5];
    var modifier = res[6];
    var asterisk = res[7];

    // Push the current path onto the tokens.
    if (path) {
      tokens.push(path);
      path = '';
    }

    var partial = prefix != null && next != null && next !== prefix;
    var repeat = modifier === '+' || modifier === '*';
    var optional = modifier === '?' || modifier === '*';
    var delimiter = res[2] || defaultDelimiter;
    var pattern = capture || group;

    tokens.push({
      name: name || key++,
      prefix: prefix || '',
      delimiter: delimiter,
      optional: optional,
      repeat: repeat,
      partial: partial,
      asterisk: !!asterisk,
      pattern: pattern ? escapeGroup(pattern) : (asterisk ? '.*' : '[^' + escapeString(delimiter) + ']+?')
    });
  }

  // Match any characters still remaining.
  if (index < str.length) {
    path += str.substr(index);
  }

  // If the path exists, push it onto the end.
  if (path) {
    tokens.push(path);
  }

  return tokens
}

/**
 * Compile a string to a template function for the path.
 *
 * @param  {string}             str
 * @param  {Object=}            options
 * @return {!function(Object=, Object=)}
 */
function compile (str, options) {
  return tokensToFunction(parse(str, options))
}

/**
 * Prettier encoding of URI path segments.
 *
 * @param  {string}
 * @return {string}
 */
function encodeURIComponentPretty (str) {
  return encodeURI(str).replace(/[\/?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
  })
}

/**
 * Encode the asterisk parameter. Similar to `pretty`, but allows slashes.
 *
 * @param  {string}
 * @return {string}
 */
function encodeAsterisk (str) {
  return encodeURI(str).replace(/[?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
  })
}

/**
 * Expose a method for transforming tokens into the path function.
 */
function tokensToFunction (tokens) {
  // Compile all the tokens into regexps.
  var matches = new Array(tokens.length);

  // Compile all the patterns before compilation.
  for (var i = 0; i < tokens.length; i++) {
    if (typeof tokens[i] === 'object') {
      matches[i] = new RegExp('^(?:' + tokens[i].pattern + ')$');
    }
  }

  return function (obj, opts) {
    var path = '';
    var data = obj || {};
    var options = opts || {};
    var encode = options.pretty ? encodeURIComponentPretty : encodeURIComponent;

    for (var i = 0; i < tokens.length; i++) {
      var token = tokens[i];

      if (typeof token === 'string') {
        path += token;

        continue
      }

      var value = data[token.name];
      var segment;

      if (value == null) {
        if (token.optional) {
          // Prepend partial segment prefixes.
          if (token.partial) {
            path += token.prefix;
          }

          continue
        } else {
          throw new TypeError('Expected "' + token.name + '" to be defined')
        }
      }

      if (isarray(value)) {
        if (!token.repeat) {
          throw new TypeError('Expected "' + token.name + '" to not repeat, but received `' + JSON.stringify(value) + '`')
        }

        if (value.length === 0) {
          if (token.optional) {
            continue
          } else {
            throw new TypeError('Expected "' + token.name + '" to not be empty')
          }
        }

        for (var j = 0; j < value.length; j++) {
          segment = encode(value[j]);

          if (!matches[i].test(segment)) {
            throw new TypeError('Expected all "' + token.name + '" to match "' + token.pattern + '", but received `' + JSON.stringify(segment) + '`')
          }

          path += (j === 0 ? token.prefix : token.delimiter) + segment;
        }

        continue
      }

      segment = token.asterisk ? encodeAsterisk(value) : encode(value);

      if (!matches[i].test(segment)) {
        throw new TypeError('Expected "' + token.name + '" to match "' + token.pattern + '", but received "' + segment + '"')
      }

      path += token.prefix + segment;
    }

    return path
  }
}

/**
 * Escape a regular expression string.
 *
 * @param  {string} str
 * @return {string}
 */
function escapeString (str) {
  return str.replace(/([.+*?=^!:${}()[\]|\/\\])/g, '\\$1')
}

/**
 * Escape the capturing group by escaping special characters and meaning.
 *
 * @param  {string} group
 * @return {string}
 */
function escapeGroup (group) {
  return group.replace(/([=!:$\/()])/g, '\\$1')
}

/**
 * Attach the keys as a property of the regexp.
 *
 * @param  {!RegExp} re
 * @param  {Array}   keys
 * @return {!RegExp}
 */
function attachKeys (re, keys) {
  re.keys = keys;
  return re
}

/**
 * Get the flags for a regexp from the options.
 *
 * @param  {Object} options
 * @return {string}
 */
function flags (options) {
  return options.sensitive ? '' : 'i'
}

/**
 * Pull out keys from a regexp.
 *
 * @param  {!RegExp} path
 * @param  {!Array}  keys
 * @return {!RegExp}
 */
function regexpToRegexp (path, keys) {
  // Use a negative lookahead to match only capturing groups.
  var groups = path.source.match(/\((?!\?)/g);

  if (groups) {
    for (var i = 0; i < groups.length; i++) {
      keys.push({
        name: i,
        prefix: null,
        delimiter: null,
        optional: false,
        repeat: false,
        partial: false,
        asterisk: false,
        pattern: null
      });
    }
  }

  return attachKeys(path, keys)
}

/**
 * Transform an array into a regexp.
 *
 * @param  {!Array}  path
 * @param  {Array}   keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function arrayToRegexp (path, keys, options) {
  var parts = [];

  for (var i = 0; i < path.length; i++) {
    parts.push(pathToRegexp(path[i], keys, options).source);
  }

  var regexp = new RegExp('(?:' + parts.join('|') + ')', flags(options));

  return attachKeys(regexp, keys)
}

/**
 * Create a path regexp from string input.
 *
 * @param  {string}  path
 * @param  {!Array}  keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function stringToRegexp (path, keys, options) {
  return tokensToRegExp(parse(path, options), keys, options)
}

/**
 * Expose a function for taking tokens and returning a RegExp.
 *
 * @param  {!Array}          tokens
 * @param  {(Array|Object)=} keys
 * @param  {Object=}         options
 * @return {!RegExp}
 */
function tokensToRegExp (tokens, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */ (keys || options);
    keys = [];
  }

  options = options || {};

  var strict = options.strict;
  var end = options.end !== false;
  var route = '';

  // Iterate over the tokens and create our regexp string.
  for (var i = 0; i < tokens.length; i++) {
    var token = tokens[i];

    if (typeof token === 'string') {
      route += escapeString(token);
    } else {
      var prefix = escapeString(token.prefix);
      var capture = '(?:' + token.pattern + ')';

      keys.push(token);

      if (token.repeat) {
        capture += '(?:' + prefix + capture + ')*';
      }

      if (token.optional) {
        if (!token.partial) {
          capture = '(?:' + prefix + '(' + capture + '))?';
        } else {
          capture = prefix + '(' + capture + ')?';
        }
      } else {
        capture = prefix + '(' + capture + ')';
      }

      route += capture;
    }
  }

  var delimiter = escapeString(options.delimiter || '/');
  var endsWithDelimiter = route.slice(-delimiter.length) === delimiter;

  // In non-strict mode we allow a slash at the end of match. If the path to
  // match already ends with a slash, we remove it for consistency. The slash
  // is valid at the end of a path match, not in the middle. This is important
  // in non-ending mode, where "/test/" shouldn't match "/test//route".
  if (!strict) {
    route = (endsWithDelimiter ? route.slice(0, -delimiter.length) : route) + '(?:' + delimiter + '(?=$))?';
  }

  if (end) {
    route += '$';
  } else {
    // In non-ending mode, we need the capturing groups to match as much as
    // possible by using a positive lookahead to the end or next path segment.
    route += strict && endsWithDelimiter ? '' : '(?=' + delimiter + '|$)';
  }

  return attachKeys(new RegExp('^' + route, flags(options)), keys)
}

/**
 * Normalize the given path string, returning a regular expression.
 *
 * An empty array can be passed in for the keys, which will hold the
 * placeholder key descriptions. For example, using `/user/:id`, `keys` will
 * contain `[{ name: 'id', delimiter: '/', optional: false, repeat: false }]`.
 *
 * @param  {(string|RegExp|Array)} path
 * @param  {(Array|Object)=}       keys
 * @param  {Object=}               options
 * @return {!RegExp}
 */
function pathToRegexp (path, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */ (keys || options);
    keys = [];
  }

  options = options || {};

  if (path instanceof RegExp) {
    return regexpToRegexp(path, /** @type {!Array} */ (keys))
  }

  if (isarray(path)) {
    return arrayToRegexp(/** @type {!Array} */ (path), /** @type {!Array} */ (keys), options)
  }

  return stringToRegexp(/** @type {string} */ (path), /** @type {!Array} */ (keys), options)
}

index.parse = parse_1;
index.compile = compile_1;
index.tokensToFunction = tokensToFunction_1;
index.tokensToRegExp = tokensToRegExp_1;

/*  */

var regexpCache = Object.create(null);

function getRouteRegex (path) {
  var hit = regexpCache[path];
  var keys, regexp;

  if (hit) {
    keys = hit.keys;
    regexp = hit.regexp;
  } else {
    keys = [];
    regexp = index(path, keys);
    regexpCache[path] = { keys: keys, regexp: regexp };
  }

  return { keys: keys, regexp: regexp }
}

var regexpCompileCache = Object.create(null);

function fillParams (
  path,
  params,
  routeMsg
) {
  try {
    var filler =
      regexpCompileCache[path] ||
      (regexpCompileCache[path] = index.compile(path));
    return filler(params || {}, { pretty: true })
  } catch (e) {
    if (process.env.NODE_ENV !== 'production') {
      warn(false, ("missing param for " + routeMsg + ": " + (e.message)));
    }
    return ''
  }
}

/*  */

function normalizeLocation (
  raw,
  current,
  append
) {
  var next = typeof raw === 'string' ? { path: raw } : raw;
  // named target
  if (next.name || next._normalized) {
    return next
  }

  // relative params
  if (!next.path && next.params && current) {
    next = assign({}, next);
    next._normalized = true;
    var params = assign(assign({}, current.params), next.params);
    if (current.name) {
      next.name = current.name;
      next.params = params;
    } else if (current.matched) {
      var rawPath = current.matched[current.matched.length - 1].path;
      next.path = fillParams(rawPath, params, ("path " + (current.path)));
    } else if (process.env.NODE_ENV !== 'production') {
      warn(false, "relative params navigation requires a current route.");
    }
    return next
  }

  var parsedPath = parsePath(next.path || '');
  var basePath = (current && current.path) || '/';
  var path = parsedPath.path
    ? resolvePath(parsedPath.path, basePath, append || next.append)
    : (current && current.path) || '/';
  var query = resolveQuery(parsedPath.query, next.query);
  var hash = next.hash || parsedPath.hash;
  if (hash && hash.charAt(0) !== '#') {
    hash = "#" + hash;
  }

  return {
    _normalized: true,
    path: path,
    query: query,
    hash: hash
  }
}

function assign (a, b) {
  for (var key in b) {
    a[key] = b[key];
  }
  return a
}

/*  */

function createMatcher (routes) {
  var ref = createRouteMap(routes);
  var pathMap = ref.pathMap;
  var nameMap = ref.nameMap;

  function addRoutes (routes) {
    createRouteMap(routes, pathMap, nameMap);
  }

  function match (
    raw,
    currentRoute,
    redirectedFrom
  ) {
    var location = normalizeLocation(raw, currentRoute);
    var name = location.name;

    if (name) {
      var record = nameMap[name];
      if (process.env.NODE_ENV !== 'production') {
        warn(record, ("Route with name '" + name + "' does not exist"));
      }
      var paramNames = getRouteRegex(record.path).keys
        .filter(function (key) { return !key.optional; })
        .map(function (key) { return key.name; });

      if (typeof location.params !== 'object') {
        location.params = {};
      }

      if (currentRoute && typeof currentRoute.params === 'object') {
        for (var key in currentRoute.params) {
          if (!(key in location.params) && paramNames.indexOf(key) > -1) {
            location.params[key] = currentRoute.params[key];
          }
        }
      }

      if (record) {
        location.path = fillParams(record.path, location.params, ("named route \"" + name + "\""));
        return _createRoute(record, location, redirectedFrom)
      }
    } else if (location.path) {
      location.params = {};
      for (var path in pathMap) {
        if (matchRoute(path, location.params, location.path)) {
          return _createRoute(pathMap[path], location, redirectedFrom)
        }
      }
    }
    // no match
    return _createRoute(null, location)
  }

  function redirect (
    record,
    location
  ) {
    var originalRedirect = record.redirect;
    var redirect = typeof originalRedirect === 'function'
        ? originalRedirect(createRoute(record, location))
        : originalRedirect;

    if (typeof redirect === 'string') {
      redirect = { path: redirect };
    }

    if (!redirect || typeof redirect !== 'object') {
      process.env.NODE_ENV !== 'production' && warn(
        false, ("invalid redirect option: " + (JSON.stringify(redirect)))
      );
      return _createRoute(null, location)
    }

    var re = redirect;
    var name = re.name;
    var path = re.path;
    var query = location.query;
    var hash = location.hash;
    var params = location.params;
    query = re.hasOwnProperty('query') ? re.query : query;
    hash = re.hasOwnProperty('hash') ? re.hash : hash;
    params = re.hasOwnProperty('params') ? re.params : params;

    if (name) {
      // resolved named direct
      var targetRecord = nameMap[name];
      if (process.env.NODE_ENV !== 'production') {
        assert(targetRecord, ("redirect failed: named route \"" + name + "\" not found."));
      }
      return match({
        _normalized: true,
        name: name,
        query: query,
        hash: hash,
        params: params
      }, undefined, location)
    } else if (path) {
      // 1. resolve relative redirect
      var rawPath = resolveRecordPath(path, record);
      // 2. resolve params
      var resolvedPath = fillParams(rawPath, params, ("redirect route with path \"" + rawPath + "\""));
      // 3. rematch with existing query and hash
      return match({
        _normalized: true,
        path: resolvedPath,
        query: query,
        hash: hash
      }, undefined, location)
    } else {
      warn(false, ("invalid redirect option: " + (JSON.stringify(redirect))));
      return _createRoute(null, location)
    }
  }

  function alias (
    record,
    location,
    matchAs
  ) {
    var aliasedPath = fillParams(matchAs, location.params, ("aliased route with path \"" + matchAs + "\""));
    var aliasedMatch = match({
      _normalized: true,
      path: aliasedPath
    });
    if (aliasedMatch) {
      var matched = aliasedMatch.matched;
      var aliasedRecord = matched[matched.length - 1];
      location.params = aliasedMatch.params;
      return _createRoute(aliasedRecord, location)
    }
    return _createRoute(null, location)
  }

  function _createRoute (
    record,
    location,
    redirectedFrom
  ) {
    if (record && record.redirect) {
      return redirect(record, redirectedFrom || location)
    }
    if (record && record.matchAs) {
      return alias(record, location, record.matchAs)
    }
    return createRoute(record, location, redirectedFrom)
  }

  return {
    match: match,
    addRoutes: addRoutes
  }
}

function matchRoute (
  path,
  params,
  pathname
) {
  var ref = getRouteRegex(path);
  var regexp = ref.regexp;
  var keys = ref.keys;
  var m = pathname.match(regexp);

  if (!m) {
    return false
  } else if (!params) {
    return true
  }

  for (var i = 1, len = m.length; i < len; ++i) {
    var key = keys[i - 1];
    var val = typeof m[i] === 'string' ? decodeURIComponent(m[i]) : m[i];
    if (key) { params[key.name] = val; }
  }

  return true
}

function resolveRecordPath (path, record) {
  return resolvePath(path, record.parent ? record.parent.path : '/', true)
}

/*  */


var positionStore = Object.create(null);

function setupScroll () {
  window.addEventListener('popstate', function (e) {
    saveScrollPosition();
    if (e.state && e.state.key) {
      setStateKey(e.state.key);
    }
  });
}

function handleScroll (
  router,
  to,
  from,
  isPop
) {
  if (!router.app) {
    return
  }

  var behavior = router.options.scrollBehavior;
  if (!behavior) {
    return
  }

  if (process.env.NODE_ENV !== 'production') {
    assert(typeof behavior === 'function', "scrollBehavior must be a function");
  }

  // wait until re-render finishes before scrolling
  router.app.$nextTick(function () {
    var position = getScrollPosition();
    var shouldScroll = behavior(to, from, isPop ? position : null);
    if (!shouldScroll) {
      return
    }
    var isObject = typeof shouldScroll === 'object';
    if (isObject && typeof shouldScroll.selector === 'string') {
      var el = document.querySelector(shouldScroll.selector);
      if (el) {
        position = getElementPosition(el);
      } else if (isValidPosition(shouldScroll)) {
        position = normalizePosition(shouldScroll);
      }
    } else if (isObject && isValidPosition(shouldScroll)) {
      position = normalizePosition(shouldScroll);
    }

    if (position) {
      window.scrollTo(position.x, position.y);
    }
  });
}

function saveScrollPosition () {
  var key = getStateKey();
  if (key) {
    positionStore[key] = {
      x: window.pageXOffset,
      y: window.pageYOffset
    };
  }
}

function getScrollPosition () {
  var key = getStateKey();
  if (key) {
    return positionStore[key]
  }
}

function getElementPosition (el) {
  var docEl = document.documentElement;
  var docRect = docEl.getBoundingClientRect();
  var elRect = el.getBoundingClientRect();
  return {
    x: elRect.left - docRect.left,
    y: elRect.top - docRect.top
  }
}

function isValidPosition (obj) {
  return isNumber(obj.x) || isNumber(obj.y)
}

function normalizePosition (obj) {
  return {
    x: isNumber(obj.x) ? obj.x : window.pageXOffset,
    y: isNumber(obj.y) ? obj.y : window.pageYOffset
  }
}

function isNumber (v) {
  return typeof v === 'number'
}

/*  */

var supportsPushState = inBrowser && (function () {
  var ua = window.navigator.userAgent;

  if (
    (ua.indexOf('Android 2.') !== -1 || ua.indexOf('Android 4.0') !== -1) &&
    ua.indexOf('Mobile Safari') !== -1 &&
    ua.indexOf('Chrome') === -1 &&
    ua.indexOf('Windows Phone') === -1
  ) {
    return false
  }

  return window.history && 'pushState' in window.history
})();

// use User Timing api (if present) for more accurate key precision
var Time = inBrowser && window.performance && window.performance.now
  ? window.performance
  : Date;

var _key = genKey();

function genKey () {
  return Time.now().toFixed(3)
}

function getStateKey () {
  return _key
}

function setStateKey (key) {
  _key = key;
}

function pushState (url, replace) {
  saveScrollPosition();
  // try...catch the pushState call to get around Safari
  // DOM Exception 18 where it limits to 100 pushState calls
  var history = window.history;
  try {
    if (replace) {
      history.replaceState({ key: _key }, '', url);
    } else {
      _key = genKey();
      history.pushState({ key: _key }, '', url);
    }
  } catch (e) {
    window.location[replace ? 'replace' : 'assign'](url);
  }
}

function replaceState (url) {
  pushState(url, true);
}

/*  */

function runQueue (queue, fn, cb) {
  var step = function (index) {
    if (index >= queue.length) {
      cb();
    } else {
      if (queue[index]) {
        fn(queue[index], function () {
          step(index + 1);
        });
      } else {
        step(index + 1);
      }
    }
  };
  step(0);
}

/*  */


var History = function History (router, base) {
  this.router = router;
  this.base = normalizeBase(base);
  // start with a route object that stands for "nowhere"
  this.current = START;
  this.pending = null;
  this.ready = false;
  this.readyCbs = [];
};

History.prototype.listen = function listen (cb) {
  this.cb = cb;
};

History.prototype.onReady = function onReady (cb) {
  if (this.ready) {
    cb();
  } else {
    this.readyCbs.push(cb);
  }
};

History.prototype.transitionTo = function transitionTo (location, onComplete, onAbort) {
    var this$1 = this;

  var route = this.router.match(location, this.current);
  this.confirmTransition(route, function () {
    this$1.updateRoute(route);
    onComplete && onComplete(route);
    this$1.ensureURL();

    // fire ready cbs once
    if (!this$1.ready) {
      this$1.ready = true;
      this$1.readyCbs.forEach(function (cb) {
        cb(route);
      });
    }
  }, onAbort);
};

History.prototype.confirmTransition = function confirmTransition (route, onComplete, onAbort) {
    var this$1 = this;

  var current = this.current;
  var abort = function () { onAbort && onAbort(); };
  if (
    isSameRoute(route, current) &&
    // in the case the route map has been dynamically appended to
    route.matched.length === current.matched.length
  ) {
    this.ensureURL();
    return abort()
  }

  var ref = resolveQueue(this.current.matched, route.matched);
    var updated = ref.updated;
    var deactivated = ref.deactivated;
    var activated = ref.activated;

  var queue = [].concat(
    // in-component leave guards
    extractLeaveGuards(deactivated),
    // global before hooks
    this.router.beforeHooks,
    // in-component update hooks
    extractUpdateHooks(updated),
    // in-config enter guards
    activated.map(function (m) { return m.beforeEnter; }),
    // async components
    resolveAsyncComponents(activated)
  );

  this.pending = route;
  var iterator = function (hook, next) {
    if (this$1.pending !== route) {
      return abort()
    }
    hook(route, current, function (to) {
      if (to === false) {
        // next(false) -> abort navigation, ensure current URL
        this$1.ensureURL(true);
        abort();
      } else if (typeof to === 'string' || typeof to === 'object') {
        // next('/') or next({ path: '/' }) -> redirect
        (typeof to === 'object' && to.replace) ? this$1.replace(to) : this$1.push(to);
        abort();
      } else {
        // confirm transition and pass on the value
        next(to);
      }
    });
  };

  runQueue(queue, iterator, function () {
    var postEnterCbs = [];
    var isValid = function () { return this$1.current === route; };
    var enterGuards = extractEnterGuards(activated, postEnterCbs, isValid);
    // wait until async components are resolved before
    // extracting in-component enter guards
    runQueue(enterGuards, iterator, function () {
      if (this$1.pending !== route) {
        return abort()
      }
      this$1.pending = null;
      onComplete(route);
      if (this$1.router.app) {
        this$1.router.app.$nextTick(function () {
          postEnterCbs.forEach(function (cb) { return cb(); });
        });
      }
    });
  });
};

History.prototype.updateRoute = function updateRoute (route) {
  var prev = this.current;
  this.current = route;
  this.cb && this.cb(route);
  this.router.afterHooks.forEach(function (hook) {
    hook && hook(route, prev);
  });
};

function normalizeBase (base) {
  if (!base) {
    if (inBrowser) {
      // respect <base> tag
      var baseEl = document.querySelector('base');
      base = (baseEl && baseEl.getAttribute('href')) || '/';
    } else {
      base = '/';
    }
  }
  // make sure there's the starting slash
  if (base.charAt(0) !== '/') {
    base = '/' + base;
  }
  // remove trailing slash
  return base.replace(/\/$/, '')
}

function resolveQueue (
  current,
  next
) {
  var i;
  var max = Math.max(current.length, next.length);
  for (i = 0; i < max; i++) {
    if (current[i] !== next[i]) {
      break
    }
  }
  return {
    updated: next.slice(0, i),
    activated: next.slice(i),
    deactivated: current.slice(i)
  }
}

function extractGuards (
  records,
  name,
  bind,
  reverse
) {
  var guards = flatMapComponents(records, function (def, instance, match, key) {
    var guard = extractGuard(def, name);
    if (guard) {
      return Array.isArray(guard)
        ? guard.map(function (guard) { return bind(guard, instance, match, key); })
        : bind(guard, instance, match, key)
    }
  });
  return flatten(reverse ? guards.reverse() : guards)
}

function extractGuard (
  def,
  key
) {
  if (typeof def !== 'function') {
    // extend now so that global mixins are applied.
    def = _Vue.extend(def);
  }
  return def.options[key]
}

function extractLeaveGuards (deactivated) {
  return extractGuards(deactivated, 'beforeRouteLeave', bindGuard, true)
}

function extractUpdateHooks (updated) {
  return extractGuards(updated, 'beforeRouteUpdate', bindGuard)
}

function bindGuard (guard, instance) {
  return function boundRouteGuard () {
    return guard.apply(instance, arguments)
  }
}

function extractEnterGuards (
  activated,
  cbs,
  isValid
) {
  return extractGuards(activated, 'beforeRouteEnter', function (guard, _, match, key) {
    return bindEnterGuard(guard, match, key, cbs, isValid)
  })
}

function bindEnterGuard (
  guard,
  match,
  key,
  cbs,
  isValid
) {
  return function routeEnterGuard (to, from, next) {
    return guard(to, from, function (cb) {
      next(cb);
      if (typeof cb === 'function') {
        cbs.push(function () {
          // #750
          // if a router-view is wrapped with an out-in transition,
          // the instance may not have been registered at this time.
          // we will need to poll for registration until current route
          // is no longer valid.
          poll(cb, match.instances, key, isValid);
        });
      }
    })
  }
}

function poll (
  cb, // somehow flow cannot infer this is a function
  instances,
  key,
  isValid
) {
  if (instances[key]) {
    cb(instances[key]);
  } else if (isValid()) {
    setTimeout(function () {
      poll(cb, instances, key, isValid);
    }, 16);
  }
}

function resolveAsyncComponents (matched) {
  return flatMapComponents(matched, function (def, _, match, key) {
    // if it's a function and doesn't have Vue options attached,
    // assume it's an async component resolve function.
    // we are not using Vue's default async resolving mechanism because
    // we want to halt the navigation until the incoming component has been
    // resolved.
    if (typeof def === 'function' && !def.options) {
      return function (to, from, next) {
        var resolve = once(function (resolvedDef) {
          match.components[key] = resolvedDef;
          next();
        });

        var reject = once(function (reason) {
          warn(false, ("Failed to resolve async component " + key + ": " + reason));
          next(false);
        });

        var res = def(resolve, reject);
        if (res && typeof res.then === 'function') {
          res.then(resolve, reject);
        }
      }
    }
  })
}

function flatMapComponents (
  matched,
  fn
) {
  return flatten(matched.map(function (m) {
    return Object.keys(m.components).map(function (key) { return fn(
      m.components[key],
      m.instances[key],
      m, key
    ); })
  }))
}

function flatten (arr) {
  return Array.prototype.concat.apply([], arr)
}

// in Webpack 2, require.ensure now also returns a Promise
// so the resolve/reject functions may get called an extra time
// if the user uses an arrow function shorthand that happens to
// return that Promise.
function once (fn) {
  var called = false;
  return function () {
    if (called) { return }
    called = true;
    return fn.apply(this, arguments)
  }
}

/*  */


var HTML5History = (function (History$$1) {
  function HTML5History (router, base) {
    var this$1 = this;

    History$$1.call(this, router, base);

    var expectScroll = router.options.scrollBehavior;

    if (expectScroll) {
      setupScroll();
    }

    window.addEventListener('popstate', function (e) {
      this$1.transitionTo(getLocation(this$1.base), function (route) {
        if (expectScroll) {
          handleScroll(router, route, this$1.current, true);
        }
      });
    });
  }

  if ( History$$1 ) HTML5History.__proto__ = History$$1;
  HTML5History.prototype = Object.create( History$$1 && History$$1.prototype );
  HTML5History.prototype.constructor = HTML5History;

  HTML5History.prototype.go = function go (n) {
    window.history.go(n);
  };

  HTML5History.prototype.push = function push (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      pushState(cleanPath(this$1.base + route.fullPath));
      handleScroll(this$1.router, route, this$1.current, false);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HTML5History.prototype.replace = function replace (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      replaceState(cleanPath(this$1.base + route.fullPath));
      handleScroll(this$1.router, route, this$1.current, false);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HTML5History.prototype.ensureURL = function ensureURL (push) {
    if (getLocation(this.base) !== this.current.fullPath) {
      var current = cleanPath(this.base + this.current.fullPath);
      push ? pushState(current) : replaceState(current);
    }
  };

  HTML5History.prototype.getCurrentLocation = function getCurrentLocation () {
    return getLocation(this.base)
  };

  return HTML5History;
}(History));

function getLocation (base) {
  var path = window.location.pathname;
  if (base && path.indexOf(base) === 0) {
    path = path.slice(base.length);
  }
  return (path || '/') + window.location.search + window.location.hash
}

/*  */


var HashHistory = (function (History$$1) {
  function HashHistory (router, base, fallback) {
    History$$1.call(this, router, base);
    // check history fallback deeplinking
    if (fallback && checkFallback(this.base)) {
      return
    }
    ensureSlash();
  }

  if ( History$$1 ) HashHistory.__proto__ = History$$1;
  HashHistory.prototype = Object.create( History$$1 && History$$1.prototype );
  HashHistory.prototype.constructor = HashHistory;

  // this is delayed until the app mounts
  // to avoid the hashchange listener being fired too early
  HashHistory.prototype.setupListeners = function setupListeners () {
    var this$1 = this;

    window.addEventListener('hashchange', function () {
      if (!ensureSlash()) {
        return
      }
      this$1.transitionTo(getHash(), function (route) {
        replaceHash(route.fullPath);
      });
    });
  };

  HashHistory.prototype.push = function push (location, onComplete, onAbort) {
    this.transitionTo(location, function (route) {
      pushHash(route.fullPath);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HashHistory.prototype.replace = function replace (location, onComplete, onAbort) {
    this.transitionTo(location, function (route) {
      replaceHash(route.fullPath);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HashHistory.prototype.go = function go (n) {
    window.history.go(n);
  };

  HashHistory.prototype.ensureURL = function ensureURL (push) {
    var current = this.current.fullPath;
    if (getHash() !== current) {
      push ? pushHash(current) : replaceHash(current);
    }
  };

  HashHistory.prototype.getCurrentLocation = function getCurrentLocation () {
    return getHash()
  };

  return HashHistory;
}(History));

function checkFallback (base) {
  var location = getLocation(base);
  if (!/^\/#/.test(location)) {
    window.location.replace(
      cleanPath(base + '/#' + location)
    );
    return true
  }
}

function ensureSlash () {
  var path = getHash();
  if (path.charAt(0) === '/') {
    return true
  }
  replaceHash('/' + path);
  return false
}

function getHash () {
  // We can't use window.location.hash here because it's not
  // consistent across browsers - Firefox will pre-decode it!
  var href = window.location.href;
  var index = href.indexOf('#');
  return index === -1 ? '' : href.slice(index + 1)
}

function pushHash (path) {
  window.location.hash = path;
}

function replaceHash (path) {
  var i = window.location.href.indexOf('#');
  window.location.replace(
    window.location.href.slice(0, i >= 0 ? i : 0) + '#' + path
  );
}

/*  */


var AbstractHistory = (function (History$$1) {
  function AbstractHistory (router, base) {
    History$$1.call(this, router, base);
    this.stack = [];
    this.index = -1;
  }

  if ( History$$1 ) AbstractHistory.__proto__ = History$$1;
  AbstractHistory.prototype = Object.create( History$$1 && History$$1.prototype );
  AbstractHistory.prototype.constructor = AbstractHistory;

  AbstractHistory.prototype.push = function push (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      this$1.stack = this$1.stack.slice(0, this$1.index + 1).concat(route);
      this$1.index++;
      onComplete && onComplete(route);
    }, onAbort);
  };

  AbstractHistory.prototype.replace = function replace (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      this$1.stack = this$1.stack.slice(0, this$1.index).concat(route);
      onComplete && onComplete(route);
    }, onAbort);
  };

  AbstractHistory.prototype.go = function go (n) {
    var this$1 = this;

    var targetIndex = this.index + n;
    if (targetIndex < 0 || targetIndex >= this.stack.length) {
      return
    }
    var route = this.stack[targetIndex];
    this.confirmTransition(route, function () {
      this$1.index = targetIndex;
      this$1.updateRoute(route);
    });
  };

  AbstractHistory.prototype.getCurrentLocation = function getCurrentLocation () {
    var current = this.stack[this.stack.length - 1];
    return current ? current.fullPath : '/'
  };

  AbstractHistory.prototype.ensureURL = function ensureURL () {
    // noop
  };

  return AbstractHistory;
}(History));

/*  */

var VueRouter = function VueRouter (options) {
  if ( options === void 0 ) options = {};

  this.app = null;
  this.apps = [];
  this.options = options;
  this.beforeHooks = [];
  this.afterHooks = [];
  this.matcher = createMatcher(options.routes || []);

  var mode = options.mode || 'hash';
  this.fallback = mode === 'history' && !supportsPushState;
  if (this.fallback) {
    mode = 'hash';
  }
  if (!inBrowser) {
    mode = 'abstract';
  }
  this.mode = mode;

  switch (mode) {
    case 'history':
      this.history = new HTML5History(this, options.base);
      break
    case 'hash':
      this.history = new HashHistory(this, options.base, this.fallback);
      break
    case 'abstract':
      this.history = new AbstractHistory(this, options.base);
      break
    default:
      if (process.env.NODE_ENV !== 'production') {
        assert(false, ("invalid mode: " + mode));
      }
  }
};

var prototypeAccessors = { currentRoute: {} };

VueRouter.prototype.match = function match (
  raw,
  current,
  redirectedFrom
) {
  return this.matcher.match(raw, current, redirectedFrom)
};

prototypeAccessors.currentRoute.get = function () {
  return this.history && this.history.current
};

VueRouter.prototype.init = function init (app /* Vue component instance */) {
    var this$1 = this;

  process.env.NODE_ENV !== 'production' && assert(
    install.installed,
    "not installed. Make sure to call `Vue.use(VueRouter)` " +
    "before creating root instance."
  );

  this.apps.push(app);

  // main app already initialized.
  if (this.app) {
    return
  }

  this.app = app;

  var history = this.history;

  if (history instanceof HTML5History) {
    history.transitionTo(history.getCurrentLocation());
  } else if (history instanceof HashHistory) {
    var setupHashListener = function () {
      history.setupListeners();
    };
    history.transitionTo(
      history.getCurrentLocation(),
      setupHashListener,
      setupHashListener
    );
  }

  history.listen(function (route) {
    this$1.apps.forEach(function (app) {
      app._route = route;
    });
  });
};

VueRouter.prototype.beforeEach = function beforeEach (fn) {
  this.beforeHooks.push(fn);
};

VueRouter.prototype.afterEach = function afterEach (fn) {
  this.afterHooks.push(fn);
};

VueRouter.prototype.onReady = function onReady (cb) {
  this.history.onReady(cb);
};

VueRouter.prototype.push = function push (location, onComplete, onAbort) {
  this.history.push(location, onComplete, onAbort);
};

VueRouter.prototype.replace = function replace (location, onComplete, onAbort) {
  this.history.replace(location, onComplete, onAbort);
};

VueRouter.prototype.go = function go (n) {
  this.history.go(n);
};

VueRouter.prototype.back = function back () {
  this.go(-1);
};

VueRouter.prototype.forward = function forward () {
  this.go(1);
};

VueRouter.prototype.getMatchedComponents = function getMatchedComponents (to) {
  var route = to
    ? this.resolve(to).route
    : this.currentRoute;
  if (!route) {
    return []
  }
  return [].concat.apply([], route.matched.map(function (m) {
    return Object.keys(m.components).map(function (key) {
      return m.components[key]
    })
  }))
};

VueRouter.prototype.resolve = function resolve (
  to,
  current,
  append
) {
  var location = normalizeLocation(to, current || this.history.current, append);
  var route = this.match(location, current);
  var fullPath = route.redirectedFrom || route.fullPath;
  var base = this.history.base;
  var href = createHref(base, fullPath, this.mode);
  return {
    location: location,
    route: route,
    href: href,
    // for backwards compat
    normalizedTo: location,
    resolved: route
  }
};

VueRouter.prototype.addRoutes = function addRoutes (routes) {
  this.matcher.addRoutes(routes);
  if (this.history.current !== START) {
    this.history.transitionTo(this.history.getCurrentLocation());
  }
};

Object.defineProperties( VueRouter.prototype, prototypeAccessors );

function createHref (base, fullPath, mode) {
  var path = mode === 'hash' ? '#' + fullPath : fullPath;
  return base ? cleanPath(base + '/' + path) : path
}

VueRouter.install = install;
VueRouter.version = '2.2.1';

if (inBrowser && window.Vue) {
  window.Vue.use(VueRouter);
}

/* harmony default export */ __webpack_exports__["default"] = (VueRouter);

/* WEBPACK VAR INJECTION */}.call(__webpack_exports__, __webpack_require__(14)))

/***/ }),
/* 16 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Store", function() { return Store; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapState", function() { return mapState; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapMutations", function() { return mapMutations; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapGetters", function() { return mapGetters; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapActions", function() { return mapActions; });
/**
 * vuex v2.2.1
 * (c) 2017 Evan You
 * @license MIT
 */
var applyMixin = function (Vue) {
  var version = Number(Vue.version.split('.')[0]);

  if (version >= 2) {
    var usesInit = Vue.config._lifecycleHooks.indexOf('init') > -1;
    Vue.mixin(usesInit ? { init: vuexInit } : { beforeCreate: vuexInit });
  } else {
    // override init and inject vuex init procedure
    // for 1.x backwards compatibility.
    var _init = Vue.prototype._init;
    Vue.prototype._init = function (options) {
      if ( options === void 0 ) options = {};

      options.init = options.init
        ? [vuexInit].concat(options.init)
        : vuexInit;
      _init.call(this, options);
    };
  }

  /**
   * Vuex init hook, injected into each instances init hooks list.
   */

  function vuexInit () {
    var options = this.$options;
    // store injection
    if (options.store) {
      this.$store = options.store;
    } else if (options.parent && options.parent.$store) {
      this.$store = options.parent.$store;
    }
  }
};

var devtoolHook =
  typeof window !== 'undefined' &&
  window.__VUE_DEVTOOLS_GLOBAL_HOOK__;

function devtoolPlugin (store) {
  if (!devtoolHook) { return }

  store._devtoolHook = devtoolHook;

  devtoolHook.emit('vuex:init', store);

  devtoolHook.on('vuex:travel-to-state', function (targetState) {
    store.replaceState(targetState);
  });

  store.subscribe(function (mutation, state) {
    devtoolHook.emit('vuex:mutation', mutation, state);
  });
}

/**
 * Get the first item that pass the test
 * by second argument function
 *
 * @param {Array} list
 * @param {Function} f
 * @return {*}
 */
/**
 * Deep copy the given object considering circular structure.
 * This function caches all nested objects and its copies.
 * If it detects circular structure, use cached copy to avoid infinite loop.
 *
 * @param {*} obj
 * @param {Array<Object>} cache
 * @return {*}
 */


/**
 * forEach for object
 */
function forEachValue (obj, fn) {
  Object.keys(obj).forEach(function (key) { return fn(obj[key], key); });
}

function isObject (obj) {
  return obj !== null && typeof obj === 'object'
}

function isPromise (val) {
  return val && typeof val.then === 'function'
}

function assert (condition, msg) {
  if (!condition) { throw new Error(("[vuex] " + msg)) }
}

var Module = function Module (rawModule, runtime) {
  this.runtime = runtime;
  this._children = Object.create(null);
  this._rawModule = rawModule;
};

var prototypeAccessors$1 = { state: {},namespaced: {} };

prototypeAccessors$1.state.get = function () {
  return this._rawModule.state || {}
};

prototypeAccessors$1.namespaced.get = function () {
  return !!this._rawModule.namespaced
};

Module.prototype.addChild = function addChild (key, module) {
  this._children[key] = module;
};

Module.prototype.removeChild = function removeChild (key) {
  delete this._children[key];
};

Module.prototype.getChild = function getChild (key) {
  return this._children[key]
};

Module.prototype.update = function update (rawModule) {
  this._rawModule.namespaced = rawModule.namespaced;
  if (rawModule.actions) {
    this._rawModule.actions = rawModule.actions;
  }
  if (rawModule.mutations) {
    this._rawModule.mutations = rawModule.mutations;
  }
  if (rawModule.getters) {
    this._rawModule.getters = rawModule.getters;
  }
};

Module.prototype.forEachChild = function forEachChild (fn) {
  forEachValue(this._children, fn);
};

Module.prototype.forEachGetter = function forEachGetter (fn) {
  if (this._rawModule.getters) {
    forEachValue(this._rawModule.getters, fn);
  }
};

Module.prototype.forEachAction = function forEachAction (fn) {
  if (this._rawModule.actions) {
    forEachValue(this._rawModule.actions, fn);
  }
};

Module.prototype.forEachMutation = function forEachMutation (fn) {
  if (this._rawModule.mutations) {
    forEachValue(this._rawModule.mutations, fn);
  }
};

Object.defineProperties( Module.prototype, prototypeAccessors$1 );

var ModuleCollection = function ModuleCollection (rawRootModule) {
  var this$1 = this;

  // register root module (Vuex.Store options)
  this.root = new Module(rawRootModule, false);

  // register all nested modules
  if (rawRootModule.modules) {
    forEachValue(rawRootModule.modules, function (rawModule, key) {
      this$1.register([key], rawModule, false);
    });
  }
};

ModuleCollection.prototype.get = function get (path) {
  return path.reduce(function (module, key) {
    return module.getChild(key)
  }, this.root)
};

ModuleCollection.prototype.getNamespace = function getNamespace (path) {
  var module = this.root;
  return path.reduce(function (namespace, key) {
    module = module.getChild(key);
    return namespace + (module.namespaced ? key + '/' : '')
  }, '')
};

ModuleCollection.prototype.update = function update$1 (rawRootModule) {
  update(this.root, rawRootModule);
};

ModuleCollection.prototype.register = function register (path, rawModule, runtime) {
    var this$1 = this;
    if ( runtime === void 0 ) runtime = true;

  var parent = this.get(path.slice(0, -1));
  var newModule = new Module(rawModule, runtime);
  parent.addChild(path[path.length - 1], newModule);

  // register nested modules
  if (rawModule.modules) {
    forEachValue(rawModule.modules, function (rawChildModule, key) {
      this$1.register(path.concat(key), rawChildModule, runtime);
    });
  }
};

ModuleCollection.prototype.unregister = function unregister (path) {
  var parent = this.get(path.slice(0, -1));
  var key = path[path.length - 1];
  if (!parent.getChild(key).runtime) { return }

  parent.removeChild(key);
};

function update (targetModule, newModule) {
  // update target module
  targetModule.update(newModule);

  // update nested modules
  if (newModule.modules) {
    for (var key in newModule.modules) {
      if (!targetModule.getChild(key)) {
        console.warn(
          "[vuex] trying to add a new module '" + key + "' on hot reloading, " +
          'manual reload is needed'
        );
        return
      }
      update(targetModule.getChild(key), newModule.modules[key]);
    }
  }
}

var Vue; // bind on install

var Store = function Store (options) {
  var this$1 = this;
  if ( options === void 0 ) options = {};

  assert(Vue, "must call Vue.use(Vuex) before creating a store instance.");
  assert(typeof Promise !== 'undefined', "vuex requires a Promise polyfill in this browser.");

  var state = options.state; if ( state === void 0 ) state = {};
  var plugins = options.plugins; if ( plugins === void 0 ) plugins = [];
  var strict = options.strict; if ( strict === void 0 ) strict = false;

  // store internal state
  this._committing = false;
  this._actions = Object.create(null);
  this._mutations = Object.create(null);
  this._wrappedGetters = Object.create(null);
  this._modules = new ModuleCollection(options);
  this._modulesNamespaceMap = Object.create(null);
  this._subscribers = [];
  this._watcherVM = new Vue();

  // bind commit and dispatch to self
  var store = this;
  var ref = this;
  var dispatch = ref.dispatch;
  var commit = ref.commit;
  this.dispatch = function boundDispatch (type, payload) {
    return dispatch.call(store, type, payload)
  };
  this.commit = function boundCommit (type, payload, options) {
    return commit.call(store, type, payload, options)
  };

  // strict mode
  this.strict = strict;

  // init root module.
  // this also recursively registers all sub-modules
  // and collects all module getters inside this._wrappedGetters
  installModule(this, state, [], this._modules.root);

  // initialize the store vm, which is responsible for the reactivity
  // (also registers _wrappedGetters as computed properties)
  resetStoreVM(this, state);

  // apply plugins
  plugins.concat(devtoolPlugin).forEach(function (plugin) { return plugin(this$1); });
};

var prototypeAccessors = { state: {} };

prototypeAccessors.state.get = function () {
  return this._vm._data.$$state
};

prototypeAccessors.state.set = function (v) {
  assert(false, "Use store.replaceState() to explicit replace store state.");
};

Store.prototype.commit = function commit (_type, _payload, _options) {
    var this$1 = this;

  // check object-style commit
  var ref = unifyObjectStyle(_type, _payload, _options);
    var type = ref.type;
    var payload = ref.payload;
    var options = ref.options;

  var mutation = { type: type, payload: payload };
  var entry = this._mutations[type];
  if (!entry) {
    console.error(("[vuex] unknown mutation type: " + type));
    return
  }
  this._withCommit(function () {
    entry.forEach(function commitIterator (handler) {
      handler(payload);
    });
  });
  this._subscribers.forEach(function (sub) { return sub(mutation, this$1.state); });

  if (options && options.silent) {
    console.warn(
      "[vuex] mutation type: " + type + ". Silent option has been removed. " +
      'Use the filter functionality in the vue-devtools'
    );
  }
};

Store.prototype.dispatch = function dispatch (_type, _payload) {
  // check object-style dispatch
  var ref = unifyObjectStyle(_type, _payload);
    var type = ref.type;
    var payload = ref.payload;

  var entry = this._actions[type];
  if (!entry) {
    console.error(("[vuex] unknown action type: " + type));
    return
  }
  return entry.length > 1
    ? Promise.all(entry.map(function (handler) { return handler(payload); }))
    : entry[0](payload)
};

Store.prototype.subscribe = function subscribe (fn) {
  var subs = this._subscribers;
  if (subs.indexOf(fn) < 0) {
    subs.push(fn);
  }
  return function () {
    var i = subs.indexOf(fn);
    if (i > -1) {
      subs.splice(i, 1);
    }
  }
};

Store.prototype.watch = function watch (getter, cb, options) {
    var this$1 = this;

  assert(typeof getter === 'function', "store.watch only accepts a function.");
  return this._watcherVM.$watch(function () { return getter(this$1.state, this$1.getters); }, cb, options)
};

Store.prototype.replaceState = function replaceState (state) {
    var this$1 = this;

  this._withCommit(function () {
    this$1._vm._data.$$state = state;
  });
};

Store.prototype.registerModule = function registerModule (path, rawModule) {
  if (typeof path === 'string') { path = [path]; }
  assert(Array.isArray(path), "module path must be a string or an Array.");
  this._modules.register(path, rawModule);
  installModule(this, this.state, path, this._modules.get(path));
  // reset store to update getters...
  resetStoreVM(this, this.state);
};

Store.prototype.unregisterModule = function unregisterModule (path) {
    var this$1 = this;

  if (typeof path === 'string') { path = [path]; }
  assert(Array.isArray(path), "module path must be a string or an Array.");
  this._modules.unregister(path);
  this._withCommit(function () {
    var parentState = getNestedState(this$1.state, path.slice(0, -1));
    Vue.delete(parentState, path[path.length - 1]);
  });
  resetStore(this);
};

Store.prototype.hotUpdate = function hotUpdate (newOptions) {
  this._modules.update(newOptions);
  resetStore(this, true);
};

Store.prototype._withCommit = function _withCommit (fn) {
  var committing = this._committing;
  this._committing = true;
  fn();
  this._committing = committing;
};

Object.defineProperties( Store.prototype, prototypeAccessors );

function resetStore (store, hot) {
  store._actions = Object.create(null);
  store._mutations = Object.create(null);
  store._wrappedGetters = Object.create(null);
  store._modulesNamespaceMap = Object.create(null);
  var state = store.state;
  // init all modules
  installModule(store, state, [], store._modules.root, true);
  // reset vm
  resetStoreVM(store, state, hot);
}

function resetStoreVM (store, state, hot) {
  var oldVm = store._vm;

  // bind store public getters
  store.getters = {};
  var wrappedGetters = store._wrappedGetters;
  var computed = {};
  forEachValue(wrappedGetters, function (fn, key) {
    // use computed to leverage its lazy-caching mechanism
    computed[key] = function () { return fn(store); };
    Object.defineProperty(store.getters, key, {
      get: function () { return store._vm[key]; },
      enumerable: true // for local getters
    });
  });

  // use a Vue instance to store the state tree
  // suppress warnings just in case the user has added
  // some funky global mixins
  var silent = Vue.config.silent;
  Vue.config.silent = true;
  store._vm = new Vue({
    data: {
      $$state: state
    },
    computed: computed
  });
  Vue.config.silent = silent;

  // enable strict mode for new vm
  if (store.strict) {
    enableStrictMode(store);
  }

  if (oldVm) {
    if (hot) {
      // dispatch changes in all subscribed watchers
      // to force getter re-evaluation for hot reloading.
      store._withCommit(function () {
        oldVm._data.$$state = null;
      });
    }
    Vue.nextTick(function () { return oldVm.$destroy(); });
  }
}

function installModule (store, rootState, path, module, hot) {
  var isRoot = !path.length;
  var namespace = store._modules.getNamespace(path);

  // register in namespace map
  if (namespace) {
    store._modulesNamespaceMap[namespace] = module;
  }

  // set state
  if (!isRoot && !hot) {
    var parentState = getNestedState(rootState, path.slice(0, -1));
    var moduleName = path[path.length - 1];
    store._withCommit(function () {
      Vue.set(parentState, moduleName, module.state);
    });
  }

  var local = module.context = makeLocalContext(store, namespace, path);

  module.forEachMutation(function (mutation, key) {
    var namespacedType = namespace + key;
    registerMutation(store, namespacedType, mutation, local);
  });

  module.forEachAction(function (action, key) {
    var namespacedType = namespace + key;
    registerAction(store, namespacedType, action, local);
  });

  module.forEachGetter(function (getter, key) {
    var namespacedType = namespace + key;
    registerGetter(store, namespacedType, getter, local);
  });

  module.forEachChild(function (child, key) {
    installModule(store, rootState, path.concat(key), child, hot);
  });
}

/**
 * make localized dispatch, commit, getters and state
 * if there is no namespace, just use root ones
 */
function makeLocalContext (store, namespace, path) {
  var noNamespace = namespace === '';

  var local = {
    dispatch: noNamespace ? store.dispatch : function (_type, _payload, _options) {
      var args = unifyObjectStyle(_type, _payload, _options);
      var payload = args.payload;
      var options = args.options;
      var type = args.type;

      if (!options || !options.root) {
        type = namespace + type;
        if (!store._actions[type]) {
          console.error(("[vuex] unknown local action type: " + (args.type) + ", global type: " + type));
          return
        }
      }

      return store.dispatch(type, payload)
    },

    commit: noNamespace ? store.commit : function (_type, _payload, _options) {
      var args = unifyObjectStyle(_type, _payload, _options);
      var payload = args.payload;
      var options = args.options;
      var type = args.type;

      if (!options || !options.root) {
        type = namespace + type;
        if (!store._mutations[type]) {
          console.error(("[vuex] unknown local mutation type: " + (args.type) + ", global type: " + type));
          return
        }
      }

      store.commit(type, payload, options);
    }
  };

  // getters and state object must be gotten lazily
  // because they will be changed by vm update
  Object.defineProperties(local, {
    getters: {
      get: noNamespace
        ? function () { return store.getters; }
        : function () { return makeLocalGetters(store, namespace); }
    },
    state: {
      get: function () { return getNestedState(store.state, path); }
    }
  });

  return local
}

function makeLocalGetters (store, namespace) {
  var gettersProxy = {};

  var splitPos = namespace.length;
  Object.keys(store.getters).forEach(function (type) {
    // skip if the target getter is not match this namespace
    if (type.slice(0, splitPos) !== namespace) { return }

    // extract local getter type
    var localType = type.slice(splitPos);

    // Add a port to the getters proxy.
    // Define as getter property because
    // we do not want to evaluate the getters in this time.
    Object.defineProperty(gettersProxy, localType, {
      get: function () { return store.getters[type]; },
      enumerable: true
    });
  });

  return gettersProxy
}

function registerMutation (store, type, handler, local) {
  var entry = store._mutations[type] || (store._mutations[type] = []);
  entry.push(function wrappedMutationHandler (payload) {
    handler(local.state, payload);
  });
}

function registerAction (store, type, handler, local) {
  var entry = store._actions[type] || (store._actions[type] = []);
  entry.push(function wrappedActionHandler (payload, cb) {
    var res = handler({
      dispatch: local.dispatch,
      commit: local.commit,
      getters: local.getters,
      state: local.state,
      rootGetters: store.getters,
      rootState: store.state
    }, payload, cb);
    if (!isPromise(res)) {
      res = Promise.resolve(res);
    }
    if (store._devtoolHook) {
      return res.catch(function (err) {
        store._devtoolHook.emit('vuex:error', err);
        throw err
      })
    } else {
      return res
    }
  });
}

function registerGetter (store, type, rawGetter, local) {
  if (store._wrappedGetters[type]) {
    console.error(("[vuex] duplicate getter key: " + type));
    return
  }
  store._wrappedGetters[type] = function wrappedGetter (store) {
    return rawGetter(
      local.state, // local state
      local.getters, // local getters
      store.state, // root state
      store.getters // root getters
    )
  };
}

function enableStrictMode (store) {
  store._vm.$watch(function () { return this._data.$$state }, function () {
    assert(store._committing, "Do not mutate vuex store state outside mutation handlers.");
  }, { deep: true, sync: true });
}

function getNestedState (state, path) {
  return path.length
    ? path.reduce(function (state, key) { return state[key]; }, state)
    : state
}

function unifyObjectStyle (type, payload, options) {
  if (isObject(type) && type.type) {
    options = payload;
    payload = type;
    type = type.type;
  }

  assert(typeof type === 'string', ("Expects string as the type, but found " + (typeof type) + "."));

  return { type: type, payload: payload, options: options }
}

function install (_Vue) {
  if (Vue) {
    console.error(
      '[vuex] already installed. Vue.use(Vuex) should be called only once.'
    );
    return
  }
  Vue = _Vue;
  applyMixin(Vue);
}

// auto install in dist mode
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue);
}

var mapState = normalizeNamespace(function (namespace, states) {
  var res = {};
  normalizeMap(states).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    res[key] = function mappedState () {
      var state = this.$store.state;
      var getters = this.$store.getters;
      if (namespace) {
        var module = getModuleByNamespace(this.$store, 'mapState', namespace);
        if (!module) {
          return
        }
        state = module.context.state;
        getters = module.context.getters;
      }
      return typeof val === 'function'
        ? val.call(this, state, getters)
        : state[val]
    };
    // mark vuex getter for devtools
    res[key].vuex = true;
  });
  return res
});

var mapMutations = normalizeNamespace(function (namespace, mutations) {
  var res = {};
  normalizeMap(mutations).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedMutation () {
      var args = [], len = arguments.length;
      while ( len-- ) args[ len ] = arguments[ len ];

      if (namespace && !getModuleByNamespace(this.$store, 'mapMutations', namespace)) {
        return
      }
      return this.$store.commit.apply(this.$store, [val].concat(args))
    };
  });
  return res
});

var mapGetters = normalizeNamespace(function (namespace, getters) {
  var res = {};
  normalizeMap(getters).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedGetter () {
      if (namespace && !getModuleByNamespace(this.$store, 'mapGetters', namespace)) {
        return
      }
      if (!(val in this.$store.getters)) {
        console.error(("[vuex] unknown getter: " + val));
        return
      }
      return this.$store.getters[val]
    };
    // mark vuex getter for devtools
    res[key].vuex = true;
  });
  return res
});

var mapActions = normalizeNamespace(function (namespace, actions) {
  var res = {};
  normalizeMap(actions).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedAction () {
      var args = [], len = arguments.length;
      while ( len-- ) args[ len ] = arguments[ len ];

      if (namespace && !getModuleByNamespace(this.$store, 'mapActions', namespace)) {
        return
      }
      return this.$store.dispatch.apply(this.$store, [val].concat(args))
    };
  });
  return res
});

function normalizeMap (map) {
  return Array.isArray(map)
    ? map.map(function (key) { return ({ key: key, val: key }); })
    : Object.keys(map).map(function (key) { return ({ key: key, val: map[key] }); })
}

function normalizeNamespace (fn) {
  return function (namespace, map) {
    if (typeof namespace !== 'string') {
      map = namespace;
      namespace = '';
    } else if (namespace.charAt(namespace.length - 1) !== '/') {
      namespace += '/';
    }
    return fn(namespace, map)
  }
}

function getModuleByNamespace (store, helper, namespace) {
  var module = store._modulesNamespaceMap[namespace];
  if (!module) {
    console.error(("[vuex] module namespace not found in " + helper + "(): " + namespace));
  }
  return module
}

var index_esm = {
  Store: Store,
  install: install,
  version: '2.2.1',
  mapState: mapState,
  mapMutations: mapMutations,
  mapGetters: mapGetters,
  mapActions: mapActions
};

/* harmony default export */ __webpack_exports__["default"] = (index_esm);


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(77)
)

/* script */
__vue_exports__ = __webpack_require__(39)

/* template */
var __vue_template__ = __webpack_require__(101)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-7cc6cda4"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(69)
)

/* script */
__vue_exports__ = __webpack_require__(40)

/* template */
var __vue_template__ = __webpack_require__(93)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\kstfb.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-406ddf24"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(62)
)

/* script */
__vue_exports__ = __webpack_require__(41)

/* template */
var __vue_template__ = __webpack_require__(85)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\kstfb_mryl.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-05797a63"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(65)
)

/* script */
__vue_exports__ = __webpack_require__(43)

/* template */
var __vue_template__ = __webpack_require__(88)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\sxb.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-20979b0e"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(74)
)

/* script */
__vue_exports__ = __webpack_require__(44)

/* template */
var __vue_template__ = __webpack_require__(98)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\wdrw_mryl.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-5b84f2a1"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(63)
)

/* script */
__vue_exports__ = __webpack_require__(45)

/* template */
var __vue_template__ = __webpack_require__(86)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\xxbg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-113a8419"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(83)
)

/* script */
__vue_exports__ = __webpack_require__(46)

/* template */
var __vue_template__ = __webpack_require__(107)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydbg_rwbg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-e3f04316"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(79)
)

/* script */
__vue_exports__ = __webpack_require__(47)

/* template */
var __vue_template__ = __webpack_require__(103)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydbg_rwxq.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-97c131ae"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(81)
)

/* script */
__vue_exports__ = __webpack_require__(48)

/* template */
var __vue_template__ = __webpack_require__(105)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydbg_wdcg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-abe79cc8"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(75)
)

/* script */
__vue_exports__ = __webpack_require__(49)

/* template */
var __vue_template__ = __webpack_require__(99)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-734e11e4"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(72)
)

/* script */
__vue_exports__ = __webpack_require__(50)

/* template */
var __vue_template__ = __webpack_require__(96)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_cgsb.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-4dde9744"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(78)
)

/* script */
__vue_exports__ = __webpack_require__(51)

/* template */
var __vue_template__ = __webpack_require__(102)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_ckxq.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-93f585e8"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(66)
)

/* script */
__vue_exports__ = __webpack_require__(52)

/* template */
var __vue_template__ = __webpack_require__(90)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_jh.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-34dcbb29"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(76)
)

/* script */
__vue_exports__ = __webpack_require__(53)

/* template */
var __vue_template__ = __webpack_require__(100)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_kscg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-77ddcdf7"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(70)
)

/* script */
__vue_exports__ = __webpack_require__(55)

/* template */
var __vue_template__ = __webpack_require__(94)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_tj.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-46096061"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(68)
)

/* script */
__vue_exports__ = __webpack_require__(56)

/* template */
var __vue_template__ = __webpack_require__(92)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_wdcg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-3cee6f9c"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(82)
)

/* script */
__vue_exports__ = __webpack_require__(57)

/* template */
var __vue_template__ = __webpack_require__(106)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_wddhg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-cb05c24a"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(80)
)

/* script */
__vue_exports__ = __webpack_require__(58)

/* template */
var __vue_template__ = __webpack_require__(104)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_xdhg.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-a55050d4"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(71)
)

/* script */
__vue_exports__ = __webpack_require__(59)

/* template */
var __vue_template__ = __webpack_require__(95)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydrw_xq.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-4d3f6764"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(84)
)

/* script */
__vue_exports__ = __webpack_require__(60)

/* template */
var __vue_template__ = __webpack_require__(108)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydzx.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-fdfa2346"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(73)
)

/* script */
__vue_exports__ = __webpack_require__(61)

/* template */
var __vue_template__ = __webpack_require__(97)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "F:\\weex-yqyd_sx0509\\weex-yqyd_sx0509\\src\\components\\ydzx_xq.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-57440c4b"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
//
//
//
//
//
//

exports.default = {
  methods: {
    back: function back() {
      this.$router.go(-1);
    }
  }
};

/***/ }),
/* 39 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');

// import foot from './footer.vue'
exports.default = {
	// components: {
	// 	'foot': foot
	// },
	data: function data() {
		return {
			imageList: [{ src: '../static/images/pic_01.png' }, { src: '../static/images/W020130321296490618267.jpg' }, { src: '../static/images/158_150401141239_1.jpeg' }],
			//每日之星
			s_list: [
				/*{src: '../static/images/head_04.png', name: '姜熙建', id: '../static/images/pic_hg.png'},*/
			],
			rw_list: [
				/*{href: '/kstfb_mr',title: '每日一练', time: '03-27 23:59',new: true},
    {title: '每周一测', time: '2017-03-31 23:59',new: true},
    {href: '/ydrw/ckjh',title: '阅读任务', time: '05-30 23:59',new: true,name:'张老师'}*/
			],
			// 功能模块列表
			/*p_list: [
   	{href: 'tsg',title: '听书馆', src: '../../static/images/ico_dsg.png', flag: false},
   	{href: 'hdzq',title: '活动专区', src: '../../static/images/ico_hdzq.png', flag: false}
   /*],*/
			jyzx_list: []
			/*{title: '全民阅读不是一句口号', img:'../static/images/158_150401141239_1.jpeg', href:'/ydzx/xq',see:'3542',name:'一起阅读'},*/

			//	loginA:'10099521'
		};
	},

	created: function created() {
		var loginA;
		var self = this;
		storage.getItem('username', function (e) {
			//从缓存中取userId
			loginA = e.data;
			_Utils2.default.fetch({
				url: '/app/information/?page=1&pageSize=4',
				method: 'POST',
				type: 'json',
				data: '',
				success: function success(ret) {
					// const modal = weex.requireModule('modal');
					var data = ret.data.result;
					self.jyzx_list = eval(data);
				}
			});
			_Utils2.default.fetch({
				url: '/app/student/index/task?loginAccount=' + loginA,
				method: 'POST',
				type: 'json',
				data: '',
				success: function success(ret) {
					// const modal = weex.requireModule('modal');
					var data = ret.data.result;
					self.rw_list = eval(data);
				}
			});
			_Utils2.default.fetch({
				url: '/app/student/dailystar?loginAccount=' + loginA + '&pageSize=4',
				method: 'POST',
				type: 'json',
				data: '',
				success: function success(ret) {
					var data = ret.data.result;
					self.s_list = eval(data);
				}
			});
		});
	},
	methods: {
		jump: function jump(path) {
			this.$router.push(path);
		}
	}
};

/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
	data: function data() {
		return {
			willShow: false,
			willShow2: false,
			mid: '',
			kstfb_list: [
				// {title: '每日一练', time: '05-05 23:59',new: true,state:'0',stateName:'前 往',href: '/kstfb/mr'},
				// {title: '每日一练', time: '10-08 23:59',new: false,state:'2',stateName:'已完成',href: '/kstfb/mr'},
				// //{title: '每月一测', time: '02-27 23:59',new: false,state:'2',stateName:'已完成',href: '/kstfb/mr' },
				// {title: '每日一练', time: '01-03 23:59',new: false,state:'1',stateName:'补做',href: '/kstfb/mr'}
			],
			loginA: '10099521'
		};
	},

	created: function created() {
		var self = this;
		_Utils2.default.fetch({
			url: '/app/student/exercise/list?loginAccount=' + self.loginA + '&type=1' + '&page=1' + '&pageSize=10',
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				console.log(ret.data.result);
				var datas = ret.data.result;
				self.kstfb_list = eval(datas);
			}
		});
	},
	// filters:{
	// 	stateName:function(v){
	// 		if(v==0){
	// 			return '前 往';
	// 		}else if(v==1){
	// 			return '已完成';
	// 		}else if(v==2){
	// 			return '补 做';
	// 		}
	// 	}
	// },
	methods: {
		fn: function fn() {
			if (this.willShow == true) {
				this.willShow = false;
			} else {
				this.willShow = true;
			}
		},
		show: function show() {
			if (this.willShow2 == true) {
				this.willShow2 = false;
			} else {
				this.willShow2 = true;
			}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		openKstfb: function openKstfb() {
			this.$router.push('/kstfb');
		},
		openYdrw: function openYdrw() {
			this.$router.push('/ydrw');
		},
		onpeKstfb: function onpeKstfb() {},
		mryl: function mryl(v) {
			// console.log(this.kstfb_list[v].id);
			this.$router.push('/wdrw_mryl?id=' + this.kstfb_list[v].id);
		}
	}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
	data: function data() {
		return {
			// 标题
			title: '',
			// 内容
			content: '',
			// 题目
			tm: [],
			q_list: [{ id: 1, title: '请问下面哪一项是正确', ans: 'A', cnt: '问题解析……',
				answer: [{ option: '选项一', ans: 'A' }, { option: '选项二', ans: 'B' }, { option: '选项三', ans: 'C' }, { option: '选项四', ans: 'D' }]
			}, { id: 2, title: '1954年重大事件', ans: 'B',
				answer: [{ option: '123', ans: 'A' }, { option: '321', ans: 'B' }, { option: '131', ans: 'C' }, { option: '212', ans: 'D' }]
			}, { id: 3, title: '秦国哪一年建立', ans: 'D',
				answer: [{ option: '111', ans: 'A' }, { option: '222', ans: 'B' }, { option: '333', ans: 'C' }, { option: '000', ans: 'D' }]
			}]

		};
	},

	methods: {
		last: function last(i, x, el) {
			if (i == x) {} else {}
		},
		goback: function goback() {
			this.$router.go(-1);
		}
	},
	directives: {

		/*// 解析按钮自定义点击事件指令
  click: {
  	inserted: function(el){
  		el.onclick=function(){
  			// 获取解析内容
  			var nextNode=this.parentNode.parentNode.getElementsByTagName('div')[1];
  			// 显示解析内容
  			nextNode.style.display='block';
  		}
  	},
  
  },
  // 收起解析
  hidden: {
  	inserted: function(el){
  		// el.
  		el.onclick=function(){
  			this.parentNode.parentNode.style.display='none';
  		}
  	}
  }*/
	}
};

/***/ }),
/* 42 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
	data: function data() {
		return {
			// 正确数量
			yCount: 0,
			// 错误数量
			nCount: 0,
			// 正确率
			zql: 0,
			// 闯关结果
			state: '',
			// 星星平分
			stars1: false,
			stars2: false,
			stars3: false,
			Tnum: ''
		};
	},

	created: function created() {
		this.yCount = this.$route.query.zq;
		this.nCount = this.$route.query.cw;
		this.zql = this.$route.query.zql;
		this.Tnum = this.$route.query.Tnum;

		console.log(this.zql);
		if (this.zql <= 29) {
			this.state = '闯关失败';
		} else if (this.zql <= 59) {
			this.state = '闯关失败';
			this.stars1 = true;
		} else if (this.zql <= 79) {
			this.state = '闯关成功';
			this.stars1 = true;
			this.stars2 = true;
		} else {
			this.state = '闯关成功';
			this.stars1 = true;
			this.stars2 = true;
			this.stars3 = true;
		}
	},
	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},

		_goto: function _goto(path) {
			this.$router.push(path);
		}
	}
};

/***/ }),
/* 43 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			tylelist: [{ name: '全班排行榜', bj: '#d9e8fd', color: '#6fa1e8', id: 1 }, { name: '全校排行榜', bj: '#fff', color: '#666', id: 2 }],
			mine: {
				score: '', //
				words: '',
				index: '',
				loginAccount: '',
				avatar: '',
				ability: '',
				stuname: ''
			},
			list: [
				/*	{id: '1', src: '../static/images/head_01.png', name: '欧阳锋芒锋芒', des: '状元', rank: 'A', grade: '100000'},*/
			],
			tabsId: 0,
			tabsIndex: 0,
			Account: '',
			page: 1,
			flag: false,
			showLoading: 'hide'

		};
	},

	methods: {
		cut: function cut(index, id) {
			//debugger
			this.tabsId = id;
			this.tabsIndex = index;
			var self = this;
			for (var i in self.tylelist) {
				self.tylelist[i].bj = '';
				self.tylelist[i].color = '#666';
				if (i == this.tabsIndex) {
					self.tylelist[this.tabsIndex].bj = '#d9e8fd';
					self.tylelist[this.tabsIndex].color = "#6fa1e8";
				}
			}
			if (!this.flag) {
				this.list = [];
				this.page = 1;
			}
			//console.log(njId);
			_Utils2.default.fetch({
				url: '/app/student/rank?loginAccount=' + self.Account + '&page=' + self.page + '&pageSize=10&type=' + self.tabsId,
				method: 'POST',
				type: 'json',
				success: function success(ret) {
					var data = ret.data.result || [];
					if (!self.flag && self.tabsId == 1) {
						self.mine = eval(data.mine);
					}
					if (ret.data.status == 200 && data.info.length != 0) {
						for (var _i = 0; _i < data.info.length; _i++) {
							self.list.push(data.info[_i]);
						}
					} else if (ret.data.status == 404 && data.info.length == 0) {
						modal.toast({ message: '已到底部', duration: 1 });
					}
					self.flag = false;
					self.showLoading = 'hide';
				}
			});
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		onloading: function onloading(event) {
			var self = this;
			self.page += 1;
			self.flag = true;
			// modal.toast({ message: 'loading', duration: 1 })
			self.showLoading = 'show';
			self.cut(self.tabsIndex, self.tabsId);
		}
	},
	created: function created() {
		var self = this;
		storage.getItem('username', function (e) {
			//从缓存中取userId
			self.Account = e.data;
			self.cut(0, 1);
		});
	}

};

/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			// 标题
			title: '',
			// 内容
			content: '',
			// 题目
			tm: [],
			// 成绩
			grade: '',
			q_list: [],
			cont: '',
			index: 0,
			que: '',
			activeName: 9,
			id: '',
			no: '',
			bgColor: '#fafafa',
			loginA: '10099521'
		};
	},

	created: function created() {
		// this._isZq;
		console.log(this.$route.query.id);
		var self = this;
		_Utils2.default.fetch({
			url: '/app/student/exercise/' + self.$route.query.id + '?loginAccount=' + self.loginA,
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				if (ret.data.status == 200) {
					console.log('---', ret.data.result.question);
					// self.grade=ret.data.result.rank;
					self.id = ret.data.result.id;
					self.no = ret.data.result.no;
					self.cont = ret.data.result.content;
					self.q_list = ret.data.result.question;
					console.log(self.q_list);
				}
			}
		});
	},
	methods: {
		last: function last(i, x, el) {
			if (i == x) {} else {}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		optj: function optj() {
			this.$router.push('/ydrw_tj?id=' + this.$route.query.id);
		},

		_isZq: function _isZq(answer, answers, v, n, id) {
			console.log('test', id);

			console.log('+++++++', answer);
			this.index++;
			this.selected = v;
			if (answers.length <= 4) {
				if (this.index == 1) {
					this.$set(answer, 'bgColor', '#ccc');
					var self = this;
					_Utils2.default.fetch({
						url: '/app/student/exercise/record?questionId=' + id + '&chooseAnswer=' + answers[v].answerOption + '&isRight=' + answers[v].isRight + '&exerciseId=' + self.$route.query.id + '&no=' + n + '&loginAccount=' + self.loginA,
						method: 'POST',
						type: 'json',
						// data :,
						success: function success(ret) {
							if (ret.data.status == 200) {
								console.log(ret.data.status);
							}
						}
					});
				} else {
					return;
				}
			} else {
				this.$set(answer, 'bgColor', '#ccc');
				var self = this;
				_Utils2.default.fetch({
					url: '/app/student/exercise/record?questionId=' + id + '&chooseAnswer=' + answers[v].answerOption + '&isRight=' + answers[v].isRight + '&exerciseId=' + self.$route.query.id + '&no=' + n + '&loginAccount=' + self.loginA,
					method: 'POST',
					type: 'json',
					// data :,
					success: function success(ret) {
						if (ret.data.status == 200) {
							console.log(ret.data.status);
						}
					}
				});
			}
		},

		// 提交答案
		subimt: function subimt() {
			var self = this;
			_Utils2.default.fetch({
				url: '/app/student/exercise/finish?loginAccount=' + this.loginA + '&taskId=' + this.id,
				method: 'POST',
				type: 'json',
				// data :'&taskId='+this.id,
				success: function success(ret) {
					if (ret.data.status == 200) {
						console.log(ret.data.status);
					}
				}
			});
		}
	}
};

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			page: 1,
			showLoading: 'hide',
			willShow: false,
			willShow2: false,
			typeId: '',
			stateId: '',
			typeName: '全部类型',
			stateName: '全部状态',
			stateList: [{ name: '全部状态', bj: '#d9e8fd', color: '#6fa1e8', id: '' }, { name: '未查阅', bj: '', color: '#666', id: 0 }, { name: '已查阅', bj: '', color: '#666', id: 1 }],
			typeList: [{ name: '全部类型', bj: '#d9e8fd', color: '#6fa1e8', id: '' }, { name: '阅读任务', bj: '', color: '#666', id: 1 }, { name: '每日一练', bj: '', color: '#666', id: 2 }],
			rw_list: [],
			loginA: '10099521'
		};
	},

	methods: {
		fn: function fn() {
			if (this.willShow == true) {
				this.willShow = false;
			} else {
				this.willShow = true;
			}
		},
		show: function show() {
			if (this.willShow2 == true) {
				this.willShow2 = false;
			} else {
				this.willShow2 = true;
			}
		},
		optType: function optType(id, name) {
			this.typeId = id;
			this.typeName = name;
			this.willShow = false;
			this.dataLoad();
			var self = this;
			for (var i in self.typeList) {
				self.typeList[i].bj = '';
				self.typeList[i].color = '#666';
				if (i == id) {
					self.typeList[id].bj = '#d9e8fd';
					self.typeList[id].color = "#6fa1e8";
				}
			}
		},
		optState: function optState(id, name) {
			this.stateId = id;
			this.stateName = name;
			this.willShow2 = false;
			this.dataLoad();
			var self = this;
			for (var i in self.stateList) {
				self.stateList[i].bj = '';
				self.stateList[i].color = '#666';
				if (i == id) {
					self.stateList[id].bj = '#d9e8fd';
					self.stateList[id].color = "#6fa1e8";
				}
			}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		openRwxq: function openRwxq(type, id, name) {
			// console.log(name);
			var arr = [];
			arr.push(name);
			arr.push(id);
			if (type == 1) {
				this.$router.push('/ydbg/rwbg?nid=' + arr);
			}
			if (type == 2) {
				this.$router.push('/ydrw/mryl?id=' + id);
			}
		},
		dataLoad: function dataLoad() {
			var self = this;
			this.rw_list = [];
			_Utils2.default.fetch({
				url: '/app/student/report/?loginAccount=' + self.loginA + '&page=' + self.page + '&pageSize=10' + '&type=' + self.typeId + '&state=' + self.stateId,
				method: 'POST',
				type: 'json',
				data: '',
				success: function success(ret) {
					if (ret.data.status == 200) {
						console.log(ret.data.result);
						self.rw_list = ret.data.result;
					}
				}
			});
		},
		onloading: function onloading(event) {
			var self = this;
			self.page += 1;
			//self.flag=true;
			// modal.toast({ message: 'loading', duration: 1 })
			self.showLoading = 'show';
			self.dataLoad();
		}
	},
	created: function created() {
		this.dataLoad();
	},
	filters: {
		tName: function tName(val) {
			if (val == 1) {
				return '阅读任务';
			}
			if (val == 2) {
				return '每日一练';
			}
		}
	}
};

/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var sjevent = weex.requireModule('SJevent'); //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


exports.default = {
	data: function data() {
		return {
			ydId: '',
			ydBook: [],
			bookId: '',
			aid: '',
			aName: '',
			cid: '',
			loginA: '10099521',
			voiceUrl: '',
			VoicePic: '../static/images/xs_pic_yy.png',
			checkYes: '../static/images/xs_pic_1.gif',
			checkNo: '../static/images/xs_pic_yy.png'
		};
	},

	created: function created() {
		var self = this;
		if (self.$route.query.nid) {
			var arrs = this.$route.query.nid.split(",");
			for (var i = 0; i < arrs.length; i++) {
				this.aName = arrs[0];
				self.ydId = arrs[1];
			}
		} else {
			self.ydId = self.$route.query.id;
		}
		//阅读计划
		console.log(self.ydId);

		_Utils2.default.fetch({
			url: '/app/student/report/plan/' + self.ydId + '?loginAccount=' + self.loginA,
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				if (ret.data.status == 200) {
					console.log('-*-/-/-/', ret.data.result);
					self.ydBook = ret.data.result.bookInfo;
					self.aid = ret.data.result.id;
					// self.aid = ret.data.result.id;
					// console.log(ret.data.result.id);
					// self.rw_list=ret.data.result;
				}
			}
		});
		//每日一练
	},
	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},

		_goto: function _goto(v) {
			// console.log(this.ydBook);
			var arr = [];
			this.cid = this.ydBook[v].bookId;
			console.log('test', this.cid);
			arr.push(this.ydBook[v].bookName);
			arr.push(this.ydBook[v].bookId);
			arr.push(this.aid);
			this.$router.push('/ydrw/cgsb?bId=' + arr);
		},
		playVoice: function playVoice(n, v) {

			console.log(n[v].feeling.voice);
			var self = this;
			self.sjevent.PlayVoice(n[v].feeling.voice, function (ret) {
				if (self.VoicePic == self.checkYes && ret == 'ok') {
					self.VoicePic = self.checkNo;
				} else {
					self.VoicePic = self.checkYes;
				}
			});
		},
		ckxq: function ckxq() {
			this.$router.push('/ydrw/ckxq?cid=' + this.ydId + '&loginA=' + this.loginA);
		}
	}
};

/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			b_syn: true,
			lt: false,
			book: {/*src: '../static/images/book02.png', btime: '2017-01-01', etime: '2017-01-02', bname: '飞来的伤心梅', bath: '张琴声', bpre: '北京邮电',isbn:'9787547705063'*/},
			wwc: '',
			zrw: '',
			loginA: '',
			rwId: '',
			bId: '',
			rwtype: 0,
			bName: '',
			feeling: '',
			test: ''

		};
	},

	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		openFx: function openFx() {
			this.$router.push('/ydrw_xq/ydrw_xq_hare');
		},
		opendhfx: function opendhfx(path) {
			this.$router.push(path);
		},
		openwdfx: function openwdfx(path) {
			this.$router.push(path);
		},
		openkscg: function openkscg() {
			this.$router.push('/ydrw/kscg?bid=' + this.bId + '&bname=' + this.bName + '&pid=' + this.rwId);
		},
		openwdcg: function openwdcg() {
			this.$router.push('/ydrw/wdcg');
		}
	},
	created: function created() {

		var self = this;
		this.zrw = self.$route.query.access;
		self.feeling = self.$route.query.feelingFinish;
		self.test = self.$route.query.testFinish;

		this.loginA = self.$route.query.loginAccount;
		this.rwId = self.$route.query.planId;
		this.bId = self.$route.query.bookId;
		this.rwtype = self.$route.query.type;
		this.bName = self.$route.query.bookName;

		/* console.log('任务id'+self.rwId);
  console.log('学生账号'+self.loginA);
  console.log('书本id'+self.$route.query.bookId);
  console.log('类型'+self.$route.query.type);
  console.log('书名'+self.$route.query.bookName);
   console.log(self.wwc);
   console.log(self.zrw);*/
		// console.log(self.$route.query.bookId);
		_Utils2.default.fetch({
			url: '/app/book/' + self.$route.query.bookId + '?type=' + self.$route.query.type,
			method: 'GET',
			type: 'json',
			//data:'loginAccount=12&token=123123&id=1',
			success: function success(ret) {
				var books = ret.data.result;
				self.book = eval(books);
				//console.log(self.book);
			}
		});
	}

};

/***/ }),
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			b_syn: true,
			lt: false,
			book: {/*src: '../static/images/book02.png', btime: '2017-01-01', etime: '2017-01-02', bname: '飞来的伤心梅', bath: '张琴声', bpre: '北京邮电',isbn:'9787547705063'*/},
			wwc: '',
			zrw: '',
			loginA: '',
			rwId: '',
			bId: '',
			rwtype: 0,
			bName: '',
			feeling: '',
			test: ''

		};
	},

	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		openFx: function openFx() {
			this.$router.push('/ydrw_xq/ydrw_xq_hare');
		},
		opendhfx: function opendhfx(path) {
			this.$router.push(path);
		},
		openwdfx: function openwdfx(path) {
			this.$router.push(path);
		},
		openkscg: function openkscg() {
			this.$router.push('/ydrw/kscg?bid=' + this.bId + '&bname=' + this.bName + '&pid=' + this.rwId);
		},
		openwdcg: function openwdcg() {
			this.$router.push('/ydrw/wdcg');
		}
	},
	created: function created() {

		var self = this;
		this.zrw = self.$route.query.access;
		self.feeling = self.$route.query.feelingFinish;
		self.test = self.$route.query.testFinish;

		this.loginA = self.$route.query.loginAccount;
		this.rwId = self.$route.query.planId;
		this.bId = self.$route.query.bookId;
		this.rwtype = self.$route.query.type;
		this.bName = self.$route.query.bookName;

		/* console.log('任务id'+self.rwId);
  console.log('学生账号'+self.loginA);
  console.log('书本id'+self.$route.query.bookId);
  console.log('类型'+self.$route.query.type);
  console.log('书名'+self.$route.query.bookName);
   console.log(self.wwc);
   console.log(self.zrw);*/
		// console.log(self.$route.query.bookId);
		_Utils2.default.fetch({
			url: '/app/book/' + self.$route.query.bookId + '?type=' + self.$route.query.type,
			method: 'GET',
			type: 'json',
			//data:'loginAccount=12&token=123123&id=1',
			success: function success(ret) {
				var books = ret.data.result;
				self.book = eval(books);
				//console.log(self.book);
			}
		});
	}

};

/***/ }),
/* 49 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			page: 1,
			showLoading: 'hide',
			flag: false,
			willShow: false,
			willShow2: false,
			typeId: '',
			stateId: '',
			typeName: '全部类型',
			stateName: '全部状态',
			stateList: [{ name: '全部状态', bj: '#d9e8fd', color: '#6fa1e8', id: '' }, { name: '未完成', bj: '', color: '#666', id: 0 }, { name: '已过期', bj: '', color: '#666', id: 2 }, { name: '已完成', bj: '', color: '#666', id: 1 }],
			typeList: [{ name: '全部类型', bj: '#d9e8fd', color: '#6fa1e8', id: '' }, { name: '老师任务', bj: '', color: '#666', id: 1 }, { name: '专家任务', bj: '', color: '#666', id: 2 }],
			rw_list: [
				/*	{title: '阅读任务：五一读书任务', time: '05-05 23:59',new: true,name:'张老师',state:'0',stateName:'前 往'},
    	{title: '阅读任务：元旦读书任务', time: '01-03 23:59',new: false,name:'张老师',state:'1',stateName:'补做'}*/
			],
			loginA: ''
		};
	},

	methods: {
		fn: function fn() {
			if (this.willShow == true) {
				this.willShow = false;
			} else {
				this.willShow = true;
			}
		},
		show: function show() {
			if (this.willShow2 == true) {
				this.willShow2 = false;
			} else {
				this.willShow2 = true;
			}
		},
		optType: function optType(id, name) {
			this.typeId = id;
			this.typeName = name;
			this.willShow = false;
			this.dataLoad();
			var self = this;
			for (var i in self.typeList) {
				self.typeList[i].bj = '';
				self.typeList[i].color = '#666';
				if (i == id) {
					self.typeList[id].bj = '#d9e8fd';
					self.typeList[id].color = "#6fa1e8";
				}
			}
		},
		optState: function optState(id, name) {
			this.stateId = id;
			this.stateName = name;
			this.willShow2 = false;
			this.dataLoad();
			var self = this;
			for (var i in self.stateList) {
				self.stateList[i].bj = '';
				self.stateList[i].color = '#666';
				if (i == id) {
					self.stateList[id].bj = '#d9e8fd';
					self.stateList[id].color = "#6fa1e8";
				}
			}
		},
		goback: function goback() {
			//this.$router.go(-1);
			this.$router.push('/index');
		},
		onpeKstfb: function onpeKstfb() {
			this.$router.push('/kstfb');
		},
		openYdrw: function openYdrw() {
			this.$router.push('/ydrw');
		},
		openRwxq: function openRwxq(path) {
			//this.$router.push('/ydrw/ckjh');
			this.$router.push(path);
		},
		dataLoad: function dataLoad() {

			if (!this.flag) {
				this.rw_list = [];
				this.page = 1;
			}

			var self = this;
			//this.rw_list=[];
			storage.getItem('username', function (e) {
				//从缓存中取userId
				self.loginA = e.data;
				//console.log('-------------'+self.loginA);
				_Utils2.default.fetch({
					url: '/app/student/mytask?loginAccount=' + self.loginA + '&page=' + self.page + '&pageSize=8' + '&type=' + self.typeId + '&state=' + self.stateId,
					method: 'POST',
					type: 'json',
					data: '',
					success: function success(ret) {
						var datas = ret.data.result;
						//self.rw_list=eval(datas);
						if (ret.data.status == 200) {
							for (var i = 0; i < datas.length; i++) {
								self.rw_list.push(datas[i]);
							}
						} else if (ret.data.status == 404) {
							modal.toast({ message: '已到底部', duration: 1 });
						}
						self.flag = false;
						self.showLoading = 'hide';
					}
				});
			});
		},
		onloading: function onloading(event) {
			this.page += 1;
			this.flag = true;
			// modal.toast({ message: 'loading', duration: 1 })
			this.showLoading = 'show';
			this.dataLoad();
		}
	},
	created: function created() {
		this.dataLoad();
	},
	filters: {
		sname: function sname(v) {
			if (v == 0) {
				return '前 往';
			} else if (v == 1) {
				return '已完成';
			} else if (v == 2) {
				return '补 做';
			}
		}
	}
};

/***/ }),
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
	data: function data() {
		return {
			q_list: [],
			aa: true,
			bNname: '',
			bid: '',
			pid: '',
			msg: '',
			cont: '',
			id: '',
			ss: false,
			list: [],
			arr: [],
			bgColor: '#fafafa',
			dui: 0,
			cuo: 0,
			zql: 0,
			loginA: '10099521'
		};
	},

	created: function created() {
		this.aa = false;
		var arrs = this.$route.query.bId.split(",");
		for (var i = 0; i < arrs.length; i++) {
			this.bNname = arrs[0];
			this.bid = arrs[1];
			this.pid = arrs[2];
		}
		var self = this;
		_Utils2.default.fetch({
			url: '/app/student/report/plan/record?planId=' + self.pid + '&bookId=' + self.bid + '&loginAccount=' + self.loginA,
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				console.log(ret);
				if (ret.data.status == 200) {
					self.q_list = ret.data.result;
					var arrs = {};
					self.list = [];
					for (var i = 0; i < self.q_list.length; i++) {
						if (self.q_list[i].isRight == 1) {
							self.dui++;
						} else {
							self.cuo++;
						}
						self.zql = parseInt(self.dui / (self.dui + self.cuo) * 100);
						self.$set(self.q_list[i], 'isShow', false);
						self.$set(self.q_list[i], 'ans', self.arr.join(''));
						for (var j = 0; j < self.q_list[i].answer.length; j++) {
							if (self.q_list[i].chooseAnswer == self.q_list[i].answer[j].answerOption && self.q_list[i].isRight == 1) {
								self.$set(self.q_list[i].answer[j], 'bgColor', '#36c681');
							}
							if (self.q_list[i].isRight != 1 && self.q_list[i].chooseAnswer == self.q_list[i].answer[j].answerOption) {
								self.$set(self.q_list[i].answer[j], 'bgColor', '#ffb5b6');
							}
						}
						// self.$set(self.q_list[i],'ishide',false);
					}

					console.log('list', self.q_list);
				} else if (ret.status) {
					console.log(ret.data.message);self.msg = ret.data.message;
				}
			}
		});
	},
	methods: {
		last: function last(i, x, el) {
			if (i == x) {} else {}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		_goto: function _goto() {

			this.$router.go(-1);
		},
		show: function show(ans, v) {
			console.log('index-------', v);
			this.arr = [];
			for (var i = 0; i < ans.length; i++) {
				if (ans[i].rightOption == 1) {
					// console.log('-----------',ans[i].rightOption);
					this.arr.push(ans[i].answerOption);
				}
			}
			this.q_list[v].ans = this.arr.join(' ');
			this.q_list[v].isShow = true;
			console.log(this.list);

			this.id = true;
		},
		hidden: function hidden(v) {
			this.q_list[v].isShow = false;
		}
	},
	directives: {

		/*// 解析按钮自定义点击事件指令
  click: {
  	inserted: function(el){
  		el.onclick=function(){
  			// 获取解析内容
  			var nextNode=this.parentNode.parentNode.getElementsByTagName('div')[1];
  			// 显示解析内容
  			nextNode.style.display='block';
  		}
  	},
  
  },
  // 收起解析
  hidden: {
  	inserted: function(el){
  		// el.
  		el.onclick=function(){
  			this.parentNode.parentNode.style.display='none';
  		}
  	}
  }*/
	}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 51 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			requirementsVoice: "",
			beginDate: "",
			reason: "",
			loginAccount: "",
			requirements: "",
			readState: '',
			access: "",
			teacher: {
				TEA_NAME: "",
				SCHOOL_NAME: "",
				HEAD_URL: "",
				SCHOOL_ID: ""
			},
			books: [
				/* {
        toast: 0,
        planBookId: "fa9eaf6de8cf4b259d9877e3c581b2e9",
        bookGrade: "五年级",
        author: "林汉达",
        isbn: "9787500791362",
        pic: "http://yqyd-image.img-cn-hangzhou.aliyuncs.com/6141_50.jpg",
        type: 1,
        press: "中国少年儿童出版社",
        readCount: 4278,
        bookName: "中国历史故事集",
        bookType: "儿童故事",
        desc: null
      }*/
			],
			endDate: "",
			planName: "",
			voiceReason: "",
			loginA: '',
			rwId: ''
		};
	},

	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		openYdxq: function openYdxq(path) {
			this.$router.push(path);
		}
	},
	created: function created() {
		var self = this;
		console.log('ssssss', self.$route.query.cid);
		self.loginA = self.$route.query.loginA;
		self.rwId = self.$route.query.cid;
		/*console.log('任务id'+self.rwId);
  console.log('学生账号'+self.loginA);*/

		_Utils2.default.fetch({
			url: '/app/plan/review?planId=' + self.$route.query.cid + '&loginAccount=' + this.loginA,
			method: 'POST',
			type: 'json',
			//data:'loginAccount=12&token=123123&id=1',
			success: function success(ret) {
				console.log(ret);
				var retdata = ret.data.result;
				self.beginDate = retdata.beginDate;
				//console.log(self.beginDate);
				self.requirementsVoice = retdata.requirementsVoice;
				self.beginDate = retdata.beginDate;
				self.reason = retdata.reason;
				self.loginAccount = retdata.loginAccount;
				self.requirements = retdata.requirements;
				self.readState = retdata.readState;
				self.access = retdata.access;
				self.endDate = retdata.endDate;
				self.planName = retdata.planName;
				self.voiceReason = retdata.voiceReason;
				self.teacher = eval(retdata.teacher);
				self.books = eval(retdata.books);
			}
		});
	},
	filters: {
		types: function types(v) {
			if (v == '1') {
				return '做测评';
			} else if (v == '2') {
				return '读后分享';
			} else if (v == '1,2') {
				return '做测评,读后分享';
			}
		},
		ydName: function ydName(n) {
			if (n == 1) {
				return '完成阅读';
			}
		}
	}
};

/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			VoicePic: '../static/images/xs_pic_yy.png',
			checkYes: '../static/images/xs_pic_1.gif',
			checkNo: '../static/images/xs_pic_yy.png',

			requirementsVoice: '',
			reason: '',
			loginAccount: '',
			requirements: '',
			readState: 0,
			access: '',
			endDate: '',
			reasonDuration: 0,
			requirementsDuration: 0,
			planName: '',
			beginDate: '',
			teacher: {
				TEA_NAME: '',
				SCHOOL_NAME: '',
				QQ: '',
				PHONE: '',
				SEX: '',
				HEAD_URL: '',
				EMAIL: '',
				SCHOOL_ID: ''
			},
			books: [],
			voiceReason: '',
			loginA: '',
			rwId: ''
		};
	},

	methods: {
		goback: function goback() {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.StopVoice();
			}
			this.$router.go(-1);
		},
		openYdxq: function openYdxq(path) {
			this.$router.push(path);
		},
		playVoice: function playVoice(url) {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PlayVoice(url, function (ret) {
					if (this.VoicePic == this.checkYes && ret == 'ok') {
						this.VoicePic = this.checkNo;
					} else {
						this.VoicePic = this.checkYes;
					}
				});
			}
		},
		playTvoice: function playTvoice(url) {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PlayVoice(url, function (ret) {
					if (this.VoicePic == this.checkYes && ret == 'ok') {
						this.VoicePic = this.checkNo;
					} else {
						this.VoicePic = this.checkYes;
					}
				});
			}
		}
	},
	created: function created() {
		var self = this;
		this.rwId = self.$route.query.planId;
		storage.getItem('username', function (e) {
			//从缓存中取userId
			self.loginA = e.data;
			//console.log('-------------'+self.loginA);
			_Utils2.default.fetch({
				url: '/app/plan/review?planId=' + self.$route.query.planId + '&loginAccount=' + self.loginA,
				method: 'POST',
				type: 'json',
				success: function success(ret) {
					// debugger  
					var retdata = ret.data.result;
					self.requirementsVoice = retdata.requirementsVoice;
					self.beginDate = retdata.beginDate;
					self.reason = retdata.reason;
					self.loginAccount = retdata.loginAccount;
					self.requirements = retdata.requirements;
					self.readState = retdata.readState;
					self.access = retdata.access;
					self.endDate = retdata.endDate;
					self.planName = retdata.planName;
					self.voiceReason = retdata.voiceReason;
					self.reasonDuration = retdata.reasonDuration;
					self.requirementsDuration = retdata.requirementsDuration;

					self.teacher = eval(retdata.teacher);
					self.books = eval(retdata.books);
				}
			});
		});
	},
	filters: {
		types: function types(v) {
			if (v == '1') {
				return '做测评';
			} else if (v == '2') {
				return '读后分享';
			} else if (v == '1,2') {
				return '做测评,读后分享';
			} else if (v == '2,1') {
				return '读后分享,做测评';
			}
		},
		ydName: function ydName(n, x) {
			if (n == 1 && x == 1) {
				return '完成阅读';
			}
		}
	}
};

/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			message: '',
			win: false,
			flag: false,
			// 题目
			tm: {},
			// 答案
			an_list: [],
			// 当前第几题
			index: 1,
			// 已选择
			c_flag: false,
			// 正确答案
			c_ans: '',
			// 答案是否正确
			a_flag: false,
			// 结束
			e_flag: false,
			// 题目数量
			length: 0,
			// 选项是否是图片
			pic_flag: false,
			// 书名
			bName: '',
			// 答题正确数量
			yCount: 0,
			// 答题错误数量
			nCount: 0,
			//判断是否单选还是多谢
			type: '',
			loninA: '10099521'
		};
	},

	created: function created() {
		this.bName = this.$route.query.bname;
		// 初始化题目
		var self = this;

		_Utils2.default.fetch({
			url: '/app/student/question/' + self.$route.query.bid + '?index=' + self.index + '&loginAccount=' + self.loninA,
			method: 'POST',
			type: 'json',
			//data:'loginAccount=12&token=123123&id=1',
			success: function success(ret) {
				if (ret.data.status == 200) {
					console.log('1-3', ret.data.result.question.type);
					self.type = ret.data.result.question.type;
					self.tm = ret.data.result.question;
					self.an_list = ret.data.result.answer;
					self.length = ret.data.result.total;
					for (var i = 0; i < self.an_list.length; i++) {
						if (self.an_list[i].rightOption == 1) {
							self.c_ans = self.an_list[i].answerOption;
							break;
						}
					}
					if (ret.data.result.picAnswer == 1) {
						self.pic_flag = true;
					} else {
						self.pic_flag = false;
					}
				}
			}
		});
		// var self=this;
		// document.addEventListener("touchmove",function(event){
		// 	if(self.flag){
		// 		event.preventDefault();
		// 		document.documentElement.style.overflow='hidden';
		// 	};
		// },{ passive: false });
	},
	methods: {
		_close: function _close() {
			this.flag = false;
		},
		_show: function _show() {
			this.flag = true;
		},
		goback: function goback() {
			this.$router.go(-1);
		},

		// 选择
		_chose: function _chose(i, flag, option) {
			var self = this;
			_Utils2.default.fetch({
				url: '/app/student/answer/record?bookId=' + self.$route.query.bid + '&index=' + self.index + '&loginAccount=' + self.loninA + '&planId=' + self.$route.query.pid + '&questionId=' + self.tm.questionId + '&chooseOption=' + option + '&right=' + flag,
				method: 'POST',
				type: 'json',
				success: function success(ret) {
					if (ret.data.status == 200) {}
				}
			});
			if (this.c_flag) {
				return false;
			} else {
				if (flag == 1) {
					this.yCount += 1;
					this.an_list[i].flag = 'y';
				} else {
					this.nCount += 1;
					this.an_list[i].flag = 'n';
					this.a_flag = true;
				}
				if (this.index == this.length) {
					this.e_flag = true;
					this.c_flag = false;
				} else {
					this.c_flag = true;
				}
			}
		},
		// 下一题
		_next: function _next() {
			var self = this;
			if (this.index != this.length) {
				// 关闭解析
				this.a_flag = false;
				// 隐藏下一题
				this.c_flag = false;
				this.index += 1;
				this.an_list = [];
				this.tm = {};
				_Utils2.default.fetch({
					url: '/app/student/question/' + self.$route.query.bid + '?index=' + self.index + '&loginAccount=' + self.loninA,
					method: 'POST',
					type: 'json',
					//data:'loginAccount=12&token=123123&id=1',
					success: function success(ret) {
						if (ret.data.status == 200) {
							console.log('1-4', ret.data.result.question.type);
							self.type = ret.data.result.question.type;
							self.tm = ret.data.result.question;
							self.an_list = ret.data.result.answer;
							self.length = ret.data.result.total;
							for (var i = 0; i < self.an_list.length; i++) {
								if (self.an_list[i].rightOption == 1) {
									self.c_ans = self.an_list[i].answerOption;
									break;
								}
							}
						}
					}
				});
			}
		},
		_end: function _end() {
			this.$router.push('/ydrw/cgjg?zq=' + this.yCount + '&cw=' + this.nCount + '&zql=' + parseInt(this.yCount / this.length * 100));
		}
	}
};

/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			// 标题
			title: '',
			// 内容
			content: '',
			// 题目
			tm: [],
			// 成绩
			grade: '',
			q_list: [],
			cont: '',
			index: 0,
			que: '',
			activeName: 9,
			id: '',
			no: '',
			a: 0,
			Right: '',
			list: [],
			arr: [],
			bgColor: '#fafafa',
			loginA: '10099521'
		};
	},

	created: function created() {
		// this._isZq;
		console.log(this.$route.query.id);
		var self = this;
		_Utils2.default.fetch({
			url: '/app/student/report/exercise/record/' + self.$route.query.id + '?loginAccount=' + self.loginA,
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				if (ret.data.status == 200) {
					console.log('---', ret.data.result.records);
					self.cont = ret.data.result.exercise.content;
					self.q_list = ret.data.result.records;

					var arrs = {};
					self.list = [];
					for (var i = 0; i < self.q_list.length; i++) {
						self.$set(self.q_list[i], 'isShow', false);
						for (var j = 0; j < self.q_list[i].question.answers.length; j++) {
							console.log('test', self.q_list[i].question.answers[j].answerOption);
							if (self.q_list[i].chooseAnswer == self.q_list[i].question.answers[j].answerOption && self.q_list[i].isRight == 1) {
								self.$set(self.q_list[i].question.answers[j], 'bgColor', '#36c681');
							}
							if (self.q_list[i].isRight != 1 && self.q_list[i].chooseAnswer == self.q_list[i].question.answers[j].answerOption) {
								self.$set(self.q_list[i].question.answers[j], 'bgColor', '#ffb5b6');
							}
							// self.$set(self.q_list[i].question.answers[j],'bgColor','#ccc');
						}
					}
					console.log('list', self.q_list);
				}
			}
		});
	},
	methods: {
		last: function last(i, x, el) {
			if (i == x) {} else {}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		optj: function optj() {
			this.$router.push('/kstfb');
		},

		_isZq: function _isZq(answer, answers, v, n) {
			this.index++;
		},
		_wtjx: function _wtjx(ans, v) {
			console.log('-----', ans.answers);
			for (var i = 0; i < ans.answers.length; i++) {
				if (ans.answers[i].isRight == 1) {
					this.arr.push(ans.answers[i].answerOption);
				}
			}
			this.Right = this.arr.join(',');
			this.q_list[v].isShow = true;
			console.log('add', this.arr.join(','));
		},
		hidden: function hidden(v) {
			this.q_list[v].isShow = false;
		},

		// 提交答案
		subimt: function subimt() {
			var self = this;
			// kwz.fetch({
			// 	url : '/app/student/exercise/finish?loginAccount=10099521'+'&taskId='+this.id,
			// 	method:'POST',
			// 	type:'json',
			// 	// data :'&taskId='+this.id,
			// 	success : function(ret){
			// 		if(ret.data.status==200){
			// 			console.log(ret.data.status);
			// 		}
			// 	}
			// })
		}
	}
};

/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			// 标题
			title: '',
			// 内容
			content: '',
			// 题目
			tm: [],
			// 成绩
			grade: '',
			q_list: [],
			cont: '',
			index: 0,
			que: '',
			activeName: 9,
			id: '',
			no: '',
			a: 0,
			Right: '',
			list: [],
			arr: [],
			bgColor: '#fafafa',
			tNum: '',
			dui: 0,
			cuo: 0,
			loginA: '10099521'
		};
	},

	created: function created() {
		// this._isZq;
		// console.log(this.$route.query.id);
		var self = this;
		_Utils2.default.fetch({
			url: '/app/student/report/exercise/record/' + self.$route.query.id + '?loginAccount=' + self.loginA,
			method: 'POST',
			type: 'json',
			data: '',
			success: function success(ret) {
				if (ret.data.status == 200) {
					console.log('---', ret.data.result.records);
					self.tNum = ret.data.result.records.length;
					self.cont = ret.data.result.exercise.content;
					self.q_list = ret.data.result.records;

					var arrs = {};
					self.list = [];
					for (var i = 0; i < self.q_list.length; i++) {
						if (self.q_list[i].isRight == 1) {
							self.dui++;
						} else {
							self.cuo++;
						}
						self.$set(self.q_list[i], 'isShow', false);
						for (var j = 0; j < self.q_list[i].question.answers.length; j++) {
							// console.log('test',self.q_list[i].question.answers[j].answerOption);
							if (self.q_list[i].chooseAnswer == self.q_list[i].question.answers[j].answerOption && self.q_list[i].isRight == 1) {
								self.$set(self.q_list[i].question.answers[j], 'bgColor', '#36c681');
							}
							if (self.q_list[i].isRight != 1 && self.q_list[i].chooseAnswer == self.q_list[i].question.answers[j].answerOption) {
								self.$set(self.q_list[i].question.answers[j], 'bgColor', '#ffb5b6');
							}
						}
					}
					console.log('list', self.q_list);
				}
			}
		});
	},
	methods: {
		last: function last(i, x, el) {
			if (i == x) {} else {}
		},
		goback: function goback() {
			this.$router.go(-1);
		},
		optj: function optj() {
			this.$router.push('/kstfb_tjh?Tnum=' + this.tNum + '&zq=' + this.dui + '&cw=' + this.cuo + '&zql=' + parseInt(this.dui / this.tNum * 100));
			console.log(this.tNum, this.dui, this.cuo);
		},

		_isZq: function _isZq(answer, answers, v, n) {
			this.index++;
		},
		_wtjx: function _wtjx(ans, v) {
			console.log('-----', ans.answers);
			for (var i = 0; i < ans.answers.length; i++) {
				if (ans.answers[i].isRight == 1) {
					this.arr.push(ans.answers[i].answerOption);
				}
			}
			this.Right = this.arr.join(',');
			this.q_list[v].isShow = true;
			console.log('add', this.arr.join(','));
		},
		hidden: function hidden(v) {
			this.q_list[v].isShow = false;
		},

		// 提交答案
		subimt: function subimt() {
			var self = this;
			// kwz.fetch({
			// 	url : '/app/student/exercise/finish?loginAccount=10099521'+'&taskId='+this.id,
			// 	method:'POST',
			// 	type:'json',
			// 	// data :'&taskId='+this.id,
			// 	success : function(ret){
			// 		if(ret.data.status==200){
			// 			console.log(ret.data.status);
			// 		}
			// 	}
			// })
		}
	}
};

/***/ }),
/* 56 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			q_list: [],
			aa: true,
			bNname: '',
			bid: '',
			pid: '',
			msg: '',
			cont: '',
			id: '',
			ss: false,
			list: [],
			arr: [],
			bgColor: '#fafafa',
			dui: 0,
			cuo: 0,
			zql: 0,
			loginA: ''
		};
	},

	created: function created() {
		this.aa = false;
		var self = this;
		self.bNname = self.$route.query.bname;
		self.bid = self.$route.query.bid;
		self.pid = self.$route.query.pid;

		storage.getItem('username', function (e) {
			//从缓存中取userId
			self.loginA = e.data;
			_Utils2.default.fetch({
				url: '/app/student/report/plan/record?planId=' + self.pid + '&bookId=' + self.bid + '&loginAccount=' + self.loginA,
				method: 'POST',
				type: 'json',
				data: '',
				success: function success(ret) {
					// console.log(ret.data);	
					if (ret.data.status == 200) {
						// console.log(ret.data.result)
						self.q_list = ret.data.result;
						var arrs = {};
						self.list = [];
						for (var i = 0; i < self.q_list.length; i++) {
							if (self.q_list[i].isRight == 1) {
								self.dui++;
							} else {
								self.cuo++;
							}
							self.zql = parseInt(self.dui / (self.dui + self.cuo) * 100);
							self.$set(self.q_list[i], 'isShow', false);
							self.$set(self.q_list[i], 'ans', self.arr.join(''));
							for (var j = 0; j < self.q_list[i].answer.length; j++) {
								if (self.q_list[i].chooseAnswer == self.q_list[i].answer[j].answerOption && self.q_list[i].isRight == 1) {
									self.$set(self.q_list[i].answer[j], 'bgColor', '#36c681');
								}
								if (self.q_list[i].isRight != 1 && self.q_list[i].chooseAnswer == self.q_list[i].answer[j].answerOption) {
									self.$set(self.q_list[i].answer[j], 'bgColor', '#ffb5b6');
								}
							}
						}

						// console.log('list',self.q_list);
					} else if (ret.status) {
						// console.log(ret.data.message);
						self.msg = ret.data.message;
					}
				}
			});
		});
	},
	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		_goto: function _goto(path) {
			console.log(this.bNname);
			this.$router.push(path);
		},
		show: function show(ans, v) {
			this.arr = [];
			for (var i = 0; i < ans.length; i++) {
				if (ans[i].rightOption == 1) {
					this.arr.push(ans[i].answerOption);
				}
			}
			this.q_list[v].ans = this.arr.join(' ');
			this.q_list[v].isShow = true;
			// console.log(this.list);
			this.id = true;
		},
		hidden: function hidden(v) {
			this.q_list[v].isShow = false;
		}
	}
};

/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			VoicePic: '../static/images/xs_pic_yy.png',
			checkYes: '../static/images/xs_pic_1.gif',
			checkNo: '../static/images/xs_pic_yy.png',
			redList: {},
			piclist: [],
			loginA: ''
		};
	},

	methods: {
		goback: function goback() {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.StopVoice();
			}
			this.$router.go(-1);
		},
		playVoice: function playVoice(url) {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PlayVoice(url, function (ret) {
					if (this.VoicePic == this.checkYes && ret == 'ok') {
						this.VoicePic = this.checkNo;
					} else {
						this.VoicePic = this.checkYes;
					}
				});
			}
		},
		playTvoice: function playTvoice(url) {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PlayVoice(url, function (ret) {
					if (this.VoicePic == this.checkYes && ret == 'ok') {
						this.VoicePic = this.checkNo;
					} else {
						this.VoicePic = this.checkYes;
					}
				});
			}
		},
		enlargePic: function enlargePic(lPic, index) {
			var sjevent = weex.requireModule('SJevent');
			sjevent.showPhoto(lPic, index);
		}
	},
	created: function created() {
		var self = this;
		//this.zrw=self.$route.query.access;
		//self.wwc=self.$route.query.finish;

		//console.log(self.wwc);
		// console.log(self.zrw);
		storage.getItem('username', function (e) {
			//从缓存中取userId
			self.loginA = e.data;
			_Utils2.default.fetch({
				url: '/app/student/feeling/myfeeling?loginAccount=' + self.loginA + '&planId=' + self.$route.query.planId + '&bookId=' + self.$route.query.bookId,
				method: 'POST',
				type: 'json',
				success: function success(ret) {
					var red = ret.data.result;
					self.piclist = red.img.split(',');
					//console.log('------------------'+self.piclist);
					self.redList = eval(red);
				}
			});
		});
	}
};

/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			//imageList:{ src: '../static/images/pic_01.png'}
			//listPic:{imageUrl:'../static/images/add_pic.png',imageUrl:'../static/images/add_pic.png'},
			VoicePic: '../static/images/xs_pic_yy.png',
			checkYes: '../static/images/xs_pic_1.gif',
			checkNo: '../static/images/xs_pic_yy.png',
			content: '',
			voiceUrl: '',
			imageUrl: [],
			loginA: '',
			rwId: '',
			bId: '',
			rwtype: 0,
			bName: '',
			duration: ''
		};
	},

	methods: {
		goback: function goback() {
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.StopVoice();
			}

			this.$router.go(-1);
		},

		openPic: function openPic() {
			var self = this;
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PostSigalImg(function (ret) {
					self.imageUrl = ret.url.split(',');
					modal.toast({
						message: ret.url,
						duration: 0.3
					});
				});
			}
		},
		openVoice: function openVoice() {
			var self = this;
			var sjevent = weex.requireModule('SJevent');
			if (sjevent) {
				sjevent.PostSigalVoice(function (ret) {
					self.voiceUrl = ret.url;
					self.duration = ret.duration;
					/* modal.toast({
     message: ret.url+ret.duration,
     duration: 0.3
     })*/
				});
			}
		},
		playVoice: function playVoice(url) {
			var sjevent = weex.requireModule('SJevent');
			sjevent.PlayVoice(url, function (ret) {
				if (this.VoicePic == this.checkYes && ret == 'ok') {
					this.VoicePic = this.checkNo;
				} else {
					this.VoicePic = this.checkYes;
				}
			});
		},
		enlargePic: function enlargePic(lPic, index) {
			var sjevent = weex.requireModule('SJevent');
			sjevent.showPhoto(lPic, index);
		},
		confirm: function confirm() {
			var self = this;
			self.rwId = self.$route.query.planId;
			self.bId = self.$route.query.bookId;
			self.rwtype = self.$route.query.bookType;
			self.bName = self.$route.query.title;

			if (self.content != '' || self.duration != '' || self.imageUrl.length != 0) {
				storage.getItem('username', function (e) {
					//从缓存中取userId
					self.loginA = e.data;
					_Utils2.default.fetch({
						url: '/app/student/feeling/add?planId=' + self.rwId + '&bookId=' + self.bId + '&loginAccount=' + self.loginA + '&bookType=' + self.rwtype + '&title=' + self.bName + '&content=' + self.content + '&voiceUrl=' + self.voiceUrl + '&imageUrl=' + self.imageUrl + '&duration=' + self.duration,
						method: 'POST',
						type: 'json',
						data: '',
						success: function success(ret) {
							console.log(ret.data);
							modal.toast({
								message: '发布成功',
								duration: 3
							});
							self.$router.replace('/index');
						},
						error: function error(ret) {
							//alert(ret)
						}

					});
				});
			} else {
				modal.alert({
					message: '请说一说或者写一写，分享您的读后感',
					okTitle: '好的'
				}, function () {});
			}
		}
	}
};

/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			b_syn: true,
			lt: false,
			book: {/*src: '../static/images/book02.png', btime: '2017-01-01', etime: '2017-01-02', bname: '飞来的伤心梅', bath: '张琴声', bpre: '北京邮电',isbn:'9787547705063'*/},
			wwc: '',
			zrw: '',
			loginA: '',
			rwId: '',
			bId: '',
			rwtype: 0,
			bName: '',
			feeling: '',
			test: '',
			guideId: ''
		};
	},

	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		jump: function jump(path) {
			this.$router.push(path);
		}
	},
	computed: {
		pcShow: function pcShow() {
			if ((this.zrw == '2' || this.zrw == '1,2' || this.zrw == '2,1') && this.feeling == '0') {
				return true;
			} else {
				return false;
			}
		},
		cgShow: function cgShow() {
			if ((this.zrw == '1' || this.zrw == '1,2' || this.zrw == '2,1') && this.test == '0') {
				return true;
			} else {
				return false;
			}
		},
		pc: function pc() {
			if ((this.zrw == '2' || this.zrw == '1,2' || this.zrw == '2,1') && this.feeling == '1') {
				return true;
			} else {
				return false;
			}
		},
		cg: function cg() {
			if ((this.zrw == '1' || this.zrw == '1,2' || this.zrw == '2,1') && this.test == '1') {
				return true;
			} else {
				return false;
			}
		}
	},

	created: function created() {

		var self = this;
		this.zrw = this.$route.query.access;
		this.feeling = this.$route.query.feelingFinish || '0';
		this.test = this.$route.query.testFinish || '0';

		self.rwId = self.$route.query.planId;
		self.bId = self.$route.query.bookId;
		self.rwtype = self.$route.query.type;
		self.bName = self.$route.query.bookName;
		storage.getItem('username', function (e) {
			//从缓存中取userId
			self.loginA = e.data;
			_Utils2.default.fetch({
				url: '/app/book/' + self.$route.query.bookId + '?type=' + self.$route.query.type,
				method: 'GET',
				type: 'json',
				success: function success(ret) {
					var books = ret.data.result;
					self.book = books; //eval(books);
					self.guideId = books.guideId; //eval(books.guideId);
					//console.log(self.book);
				}
			});
		});
	},
	filters: {
		filterHTMLs: function filterHTMLs(val) {
			if (val) {
				// debugger
				var newVal = val.replace(/<p>/g, '').replace(/<\/p>/g, '').replace(/<br\/>/g, '');
				console.log(newVal);
				return newVal;
			}
		}
	}

};

/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			// 阅读资讯列表
			page: 1,
			showLoading: 'hide',
			ydzx_list: [
				/*{title: '全民阅读不是一句口号', href: '/ydzx/xq', time: '2017-04-10',img:'../static/images/158_150401141239_1.jpeg',text:'有人说，腹有诗书气自华，也有人说，读书改变命运。阅读对人成长的影响是巨大的，一本好书往往能改变人的一生。而一个民族的精神境界，在很大程度上取决于全民族的阅读水平。 '},
    {title: '阅读+对话丨 陈晖：以文学阅读促进儿童全面发展', href: '/ydzx/xq', time: '2017-02-04',img:'../static/images/W020130321296490618267.jpg',text:'在2014年的全国儿童创作出版会议，作协副主席李敬泽先生发表了一个总结的讲话，叫做“儿童文学的再准备”。李敬泽先生指出：儿童文学从来就不仅仅是“文学”，它体现着一个国家、一个民族最深刻、最基本的价值取向和文化关切。要使儿童文学建立在对儿童生活和心灵的可靠知识与精微分析的基础上，使其价值取向和文化关切建立在全社会的充分共识之上。'}*/
			]
		};
	},

	name: 'ydzx',
	created: function created() {
		this.dataLoad();
	},
	methods: {
		goback: function goback() {
			this.$router.go(-1);
		},
		onpeNew: function onpeNew(path) {
			this.$router.push(path);
		},
		dataLoad: function dataLoad() {
			var self = this;
			_Utils2.default.fetch({
				url: '/app/information/?page=' + self.page + '&pageSize=10',
				method: 'POST',
				type: 'json',
				success: function success(ret) {
					var data = ret.data.result;
					if (ret.data.status == 200) {
						for (var i = 0; i < data.length; i++) {
							self.ydzx_list.push(data[i]);
						}
					} else if (ret.data.status == 404) {
						modal.toast({ message: '已到底部', duration: 1 });
					}
					self.showLoading = 'hide';
					// console.log(self.jyzx_list);
				}
			});
		},
		onloading: function onloading(event) {
			var self = this;
			self.page += 1;
			self.showLoading = 'show';
			self.dataLoad();
		}
	}
};

/***/ }),
/* 61 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _Utils = __webpack_require__(0);

var _Utils2 = _interopRequireDefault(_Utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var storage = weex.requireModule('storage');
var modal = weex.requireModule('modal');
exports.default = {
	data: function data() {
		return {
			// 活动列表
			ydzxXq: {}
		};
	},

	name: 'ydzx_xq',
	methods: {
		goback: function goback() {
			this.$router.go(-1);
		}
	},
	created: function created() {
		var self = this;
		console.log(self.$route.query.id);
		_Utils2.default.fetch({
			url: '/app/information/' + self.$route.query.id,
			method: 'POST',
			type: 'json',
			//data:'loginAccount=12&token=123123&id=1',
			success: function success(ret) {
				var datas = ret.data.result;
				self.ydzxXq = eval(datas);
			}
		});
	}
};

/***/ }),
/* 62 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "state": {
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid",
    "padding": 20
  },
  "s_tit": {
    "fontSize": 30,
    "color": "#777777"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "height": 88,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "flexDirection": "row",
    "alignItems": "center",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "yes": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "no": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff",
    "fontSize": 34
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "display": "none",
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  }
}

/***/ }),
/* 63 */
/***/ (function(module, exports) {

module.exports = {
  "loading": {
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "indicator": {
    "color": "#888888",
    "fontSize": 42,
    "paddingTop": 20,
    "paddingBottom": 20,
    "textAlign": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "type": {
    "height": 85,
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1,
    "borderTopStyle": "solid",
    "borderTopColor": "#e6e6e6",
    "borderTopWidth": 1
  },
  "type_li": {
    "position": "relative",
    "flex": 1,
    "paddingLeft": 40,
    "paddingRight": 40,
    "marginTop": 8,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "type_img": {
    "width": 35,
    "height": 18
  },
  "type_li1": {
    "position": "absolute",
    "right": 0,
    "zIndex": 99,
    "backgroundColor": "#ffffff",
    "width": 50,
    "fontSize": 30,
    "height": 284,
    "lineHeight": 70,
    "flexDirection": "column",
    "alignItems": "center",
    "justifyContent": "center",
    "textIndent": 0.3,
    "borderStyle": "solid",
    "borderColor": "#e6e6e6",
    "borderWidth": 1,
    "color": "#666666"
  },
  "type_li2": {
    "height": 214,
    "left": 0
  },
  "type_ling": {
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1
  },
  "s_class": {
    "position": "fixed",
    "zIndex": 9999,
    "top": 175,
    "width": 375,
    "padding": 5,
    "height": 270,
    "borderColor": "#e6e6e6",
    "borderStyle": "solid",
    "borderWidth": 1,
    "backgroundColor": "#ffffff"
  },
  "s_class_opt": {
    "width": 375,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-start",
    "paddingLeft": 20,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "type_yes": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "jrrw": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "paddingTop": 30,
    "paddingBottom": 30
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 0.1
  },
  "jrrw_list": {
    "overflow": "hidden",
    "position": "relative",
    "zIndex": 2,
    "width": 710,
    "marginLeft": 20,
    "marginRight": 20,
    "backgroundColor": "#f1f6fd",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 12,
    "marginBottom": 20
  },
  "newR": {
    "position": "absolute",
    "top": -2,
    "right": -2,
    "width": 75,
    "height": 75
  },
  "jrrw_info": {
    "width": 540,
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30
  },
  "rw_title": {
    "borderLeftStyle": "solid",
    "borderLeftWidth": 20,
    "borderLeftColor": "#70a1e8",
    "paddingLeft": 10,
    "fontSize": 34,
    "lineHeight": 38,
    "color": "#70a1e8"
  },
  "c_bg": {
    "position": "absolute",
    "top": 35,
    "right": 0,
    "bottom": 0,
    "width": 150,
    "textAlign": "center"
  },
  "c_bg_btn": {
    "textAlign": "left",
    "marginTop": 30,
    "paddingBottom": 5,
    "fontSize": 60,
    "color": "#fb6652",
    "marginBottom": 10
  },
  "bj0": {
    "backgroundColor": "#f1f6fd"
  },
  "bj1": {
    "backgroundColor": "#ffffff",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd"
  },
  "bj2": {
    "backgroundColor": "#ffffff"
  },
  "j_l_g": {
    "flex": 1,
    "marginTop": 10,
    "marginRight": 20,
    "marginBottom": 10,
    "marginLeft": 20,
    "paddingTop": 20,
    "paddingRight": 10,
    "paddingBottom": 20,
    "paddingLeft": 10,
    "borderTopStyle": "solid",
    "borderTopWidth": 2,
    "borderTopColor": "#e7e7e7",
    "flexDirection": "row"
  },
  "dhg": {
    "fontSize": 32,
    "color": "#70a1e8",
    "marginRight": 25
  },
  "ct": {
    "color": "#777777"
  },
  "rwType": {
    "fontSize": 34,
    "color": "#808080"
  }
}

/***/ }),
/* 64 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "contents": {
    "position": "relative",
    "top": 88,
    "bottom": 0,
    "backgroundColor": "#ffffff"
  },
  "success": {
    "flex": 1,
    "alignItems": "center",
    "paddingTop": 100,
    "paddingRight": 20,
    "paddingBottom": 50,
    "paddingLeft": 20,
    "height": 200
  },
  "succ_btn": {
    "backgroundColor": "#f0f0f0",
    "height": 100,
    "width": 350,
    "borderRadius": 10,
    "alignItems": "center",
    "justifyContent": "center"
  },
  "stars_list": {
    "marginTop": -180,
    "position": "relative",
    "flexDirection": "row",
    "alignItems": "flex-end"
  },
  "stars": {
    "width": 70,
    "height": 70
  },
  "stars1": {
    "width": 100,
    "height": 100,
    "marginRight": 15,
    "marginLeft": 15
  },
  "topic": {
    "fontSize": 34,
    "paddingTop": 30,
    "paddingBottom": 30,
    "flexDirection": "row",
    "alignItems": "flex-end",
    "justifyContent": "center"
  },
  "topic_num": {
    "color": "#ff6600",
    "fontSize": 50,
    "fontWeight": "bold"
  },
  "integral": {
    "fontSize": 32,
    "paddingBottom": 50,
    "flexDirection": "row",
    "justifyContent": "center"
  },
  "footes": {
    "position": "absolute",
    "bottom": 0,
    "width": 750,
    "height": 98,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "alignItems": "center"
  },
  "footes_btn": {
    "flex": 1,
    "marginLeft": 30,
    "marginRight": 30,
    "height": 65,
    "justifyContent": "center",
    "alignItems": "center",
    "borderWidth": 1,
    "borderColor": "#ffffff",
    "borderStyle": "solid",
    "fontSize": 36,
    "borderRadius": 30,
    "color": "#70a1e8",
    "backgroundColor": "#ffffff"
  }
}

/***/ }),
/* 65 */
/***/ (function(module, exports) {

module.exports = {
  "loading": {
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "indicator": {
    "color": "#888888",
    "fontSize": 42,
    "paddingTop": 20,
    "paddingBottom": 20,
    "textAlign": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "tab": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 70,
    "marginLeft": 60,
    "marginRight": 60,
    "marginTop": 19,
    "marginBottom": 19,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 40,
    "backgroundColor": "#ffffff"
  },
  "tab_li": {
    "width": 315,
    "textAlign": "center",
    "borderRadius": 40,
    "height": 70,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "info_tit": {
    "width": 750,
    "height": 70,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#f6f6f6"
  },
  "info_tit_list": {
    "fontSize": 32,
    "color": "#666666",
    "flexDirection": "row",
    "textAlign": "center"
  },
  "info_l_s": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "height": 120,
    "borderBottomColor": "#f1f1f1",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid"
  },
  "info_flex1": {
    "flex": 1,
    "flexDirection": "row",
    "justifyContent": "center"
  },
  "info_flex2": {
    "flex": 2,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "info_flex3": {
    "flex": 3,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "pic_ph": {
    "width": 43,
    "height": 53
  },
  "o_nbr": {
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "height": 52,
    "fontSize": 32,
    "color": "#808080"
  },
  "s_tx": {
    "float": "left",
    "marginLeft": 10,
    "marginRight": 20,
    "width": 60,
    "height": 60,
    "borderRadius": 100
  },
  "s_name": {
    "marginTop": 7,
    "fontSize": 32,
    "color": "#1a1a1a",
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "designation": {
    "marginTop": 11,
    "fontSize": 30,
    "color": "#ffb700"
  },
  "s_rank": {
    "flex": 2,
    "fontSize": 40,
    "color": "#ff6600",
    "textAlign": "center",
    "lineHeight": 80
  },
  "s_grade": {
    "color": "#ff6600",
    "fontSize": 40
  }
}

/***/ }),
/* 66 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "goback_r": {
    "position": "absolute",
    "top": 22,
    "right": 25,
    "width": 44,
    "height": 44
  },
  "goback_r1": {
    "position": "absolute",
    "top": 22,
    "right": 100,
    "width": 44,
    "height": 44
  },
  "ydrw_zt": {
    "width": 750,
    "height": 300,
    "position": "relative"
  },
  "ydrw_zt_title": {
    "width": 750,
    "position": "absolute",
    "top": 0,
    "left": 0,
    "paddingTop": 40,
    "paddingBottom": 40,
    "color": "#ffffff"
  },
  "ydrw_ztName": {
    "fontSize": 45,
    "lineHeight": 160,
    "letterSpacing": 12,
    "fontWeight": "bold",
    "textAlign": "center",
    "color": "#ffffff"
  },
  "ydry_ztlist": {
    "fontSize": 32,
    "textAlign": "center",
    "color": "#ffffff"
  },
  "ydrw_lump": {
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "margin": 20,
    "marginBottom": 0,
    "paddingBottom": 20
  },
  "fontsz": {
    "fontSize": 34
  },
  "bookName": {
    "fontSize": 36,
    "width": 750,
    "height": 88,
    "backgroundColor": "#e7f1ff",
    "flexDirection": "row",
    "alignItems": "center",
    "textIndent": 20,
    "color": "#666666"
  },
  "book_lump": {
    "width": 750,
    "height": 290,
    "backgroundColor": "#ffffff",
    "position": "relative",
    "paddingRight": 30,
    "paddingLeft": 30,
    "paddingTop": 25,
    "paddingBottom": 25,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#ececec",
    "borderBottomWidth": 1,
    "flexDirection": "row"
  },
  "jrrw_title": {
    "flexDirection": "row",
    "height": 35,
    "marginTop": 25,
    "marginBottom": 25
  },
  "ydjy_l": {
    "width": 190,
    "height": 240,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "book_lump_img": {
    "width": 190,
    "height": 240
  },
  "book_lump_info": {
    "width": 500,
    "paddingLeft": 30
  },
  "ydjy_state": {
    "position": "absolute",
    "top": 20,
    "left": 0,
    "backgroundColor": "#ff6600",
    "color": "#ffffff",
    "fontSize": 26,
    "paddingRight": 15,
    "paddingLeft": 15,
    "paddingTop": 5,
    "paddingBottom": 5
  },
  "ydrw_lump_comt": {
    "marginBottom": 20,
    "marginTop": 20,
    "lineHeight": 40,
    "fontSize": 32,
    "color": "#666666"
  },
  "lstj": {
    "width": 750,
    "fontSize": 32,
    "flexDirection": "row"
  },
  "lstj_l": {
    "flexDirection": "column",
    "width": 180,
    "paddingRight": 30,
    "paddingLeft": 30,
    "paddingTop": 20,
    "paddingBottom": 20
  },
  "lstj_lpic": {
    "width": 120,
    "height": 120,
    "borderRadius": 60,
    "position": "relative"
  },
  "lstj_lname": {
    "backgroundColor": "#70a1e8",
    "fontSize": 32,
    "padding": 10,
    "paddingTop": 5,
    "paddingBottom": 5,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center",
    "position": "relative",
    "left": 0,
    "bottom": 20
  },
  "b_list": {
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 47,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "yerw_yd": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 120
  },
  "ydrw_btn": {
    "backgroundColor": "#ff6600",
    "fontSize": 36,
    "paddingRight": 120,
    "paddingLeft": 120,
    "paddingTop": 20,
    "paddingBottom": 20,
    "borderRadius": 8,
    "color": "#ffffff"
  },
  "px_smq": {
    "width": 750,
    "flexDirection": "row",
    "padding": 20,
    "backgroundColor": "#f0f0f0"
  },
  "ewm_name": {
    "flex": 3,
    "flexDirection": "column",
    "alignItems": "center",
    "padding": 20
  },
  "pic_ewm": {
    "width": 145,
    "height": 145,
    "marginRight": 20,
    "marginLeft": 10
  },
  "footes": {
    "width": 750,
    "height": 98,
    "position": "fixed",
    "bottom": 0,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "footes_btn": {
    "width": 450,
    "height": 80,
    "borderStyle": "solid",
    "borderColor": "#ffffff",
    "borderWidth": 1,
    "fontSize": 36,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center",
    "lineHeight": 70
  },
  "voice": {
    "width": 270,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "borderStyle": "solid",
    "borderColor": "#e8e8e8",
    "borderWidth": 1,
    "borderRadius": 8,
    "backgroundColor": "#f5f5f5",
    "marginTop": 20,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "pic_yy": {
    "width": 20,
    "height": 30
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "ts_type": {
    "flexDirection": "row",
    "alignItems": "center"
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "paddingBottom": 4,
    "paddingTop": 4,
    "paddingLeft": 15,
    "paddingRight": 15,
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "record": {
    "flexDirection": "row",
    "height": 45,
    "lineHeight": 45,
    "fontSize": 30,
    "color": "#999999",
    "fontFamily": "微软雅黑"
  },
  "record_li": {
    "flex": 1,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "pic_see": {
    "width": 33,
    "height": 22
  },
  "pic_praise": {
    "width": 28,
    "height": 23
  }
}

/***/ }),
/* 67 */
/***/ (function(module, exports) {

module.exports = {
  "tj": {
    "width": 750,
    "height": 200,
    "borderWidth": 1,
    "borderColor": "#e7e7e7",
    "borderStyle": "solid"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "title": {
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30,
    "flexDirection": "row",
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "t_l": {
    "flex": 2
  },
  "t_r": {
    "flex": 1,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderLeftWidth": 1,
    "borderLeftColor": "#e7e7e7",
    "borderLeftStyle": "solid"
  },
  "state": {
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid",
    "padding": 30
  },
  "s_tit": {
    "fontSize": 32,
    "color": "#777777",
    "marginBottom": 10,
    "fontWeight": "900"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "height": 88,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "flexDirection": "row",
    "alignItems": "center",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "answer_y": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "answer_n": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff"
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  },
  "hid": {
    "justifyContent": "center",
    "alignItems": "center"
  }
}

/***/ }),
/* 68 */
/***/ (function(module, exports) {

module.exports = {
  "msg": {
    "width": 750,
    "height": 100,
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "state": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88,
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "width": 750,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "yes": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "no": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff",
    "fontSize": 34
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  },
  "hid": {
    "justifyContent": "center",
    "alignItems": "center"
  }
}

/***/ }),
/* 69 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "paddingBottom": 30
  },
  "tab": {
    "height": 70,
    "marginTop": 12,
    "marginBottom": 12,
    "marginRight": 60,
    "marginLeft": 60,
    "borderWidth": 2,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 35,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "tab_li": {
    "flex": 1,
    "verticalAlign": "middle",
    "height": 70,
    "lineHeight": 65,
    "textAlign": "center",
    "fontSize": 34,
    "color": "#666666"
  },
  "tab_yes": {
    "backgroundColor": "#d9e8fd",
    "borderRadius": 35,
    "color": "#6fa1e8"
  },
  "type": {
    "height": 85,
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1,
    "borderTopStyle": "solid",
    "borderTopColor": "#e6e6e6",
    "borderTopWidth": 1
  },
  "type_li": {
    "position": "relative",
    "flex": 1,
    "paddingLeft": 40,
    "paddingRight": 40,
    "marginTop": 8,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "type_img": {
    "width": 35,
    "height": 18
  },
  "type_li1": {
    "position": "absolute",
    "right": 0,
    "zIndex": 99,
    "backgroundColor": "#ffffff",
    "width": 50,
    "fontSize": 30,
    "height": 284,
    "lineHeight": 70,
    "flexDirection": "column",
    "alignItems": "center",
    "justifyContent": "center",
    "textIndent": 0.3,
    "borderStyle": "solid",
    "borderColor": "#e6e6e6",
    "borderWidth": 1,
    "color": "#666666"
  },
  "type_li2": {
    "height": 214,
    "left": 0
  },
  "type_ling": {
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1
  },
  "type_yes": {
    "backgroundColor": "#70a1e8",
    "color": "#ffffff"
  },
  "jrrw": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "paddingTop": 30,
    "paddingBottom": 30
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 10
  },
  "jrrw_list": {
    "overflow": "hidden",
    "position": "relative",
    "zIndex": 2,
    "width": 710,
    "marginLeft": 20,
    "marginRight": 20,
    "backgroundColor": "#f1f6fd",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 12,
    "marginBottom": 20
  },
  "newR": {
    "position": "absolute",
    "top": -2,
    "right": -2,
    "width": 75,
    "height": 75
  },
  "jrrw_info": {
    "width": 540,
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30
  },
  "rw_title": {
    "fontSize": 34,
    "lineHeight": 38,
    "color": "#666666"
  },
  "rw_time": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 40,
    "marginTop": 5
  },
  "c_bg": {
    "position": "absolute",
    "top": 35,
    "right": 0,
    "bottom": 0,
    "width": 150,
    "textAlign": "center"
  },
  "c_bg_btn": {
    "textAlign": "center",
    "width": 110,
    "paddingTop": 5,
    "paddingBottom": 5,
    "fontSize": 28,
    "borderRadius": 8,
    "backgroundColor": "#70a1e8",
    "color": "#ffffff",
    "marginBottom": 10
  },
  "bj0": {
    "backgroundColor": "#f1f6fd"
  },
  "bj1": {
    "backgroundColor": "#ffffff",
    "border": "#d9e8fd 0.01rem solid"
  },
  "bj2": {
    "backgroundColor": "#ffffff"
  },
  "c_bg_btn2": {
    "backgroundColor": "#ffffff",
    "color": "#70a1e8",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#70a1e8"
  },
  "c_bg_btn1": {
    "border": "#e6e6e6 1px solid",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e6e6e6",
    "backgroundColor": "#ffffff",
    "color": "#999999"
  },
  "stateName": {
    "position": "absolute",
    "bottom": -20,
    "fontSize": 22,
    "color": "#ff6600",
    "left": 0
  }
}

/***/ }),
/* 70 */
/***/ (function(module, exports) {

module.exports = {
  "tj": {
    "width": 750,
    "height": 200,
    "borderWidth": 1,
    "borderColor": "#e7e7e7",
    "borderStyle": "solid"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "title": {
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30,
    "flexDirection": "row",
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "t_l": {
    "flex": 2
  },
  "t_r": {
    "flex": 1,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderLeftWidth": 1,
    "borderLeftColor": "#e7e7e7",
    "borderLeftStyle": "solid"
  },
  "state": {
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid",
    "padding": 30
  },
  "s_tit": {
    "fontSize": 32,
    "color": "#777777",
    "marginBottom": 10,
    "fontWeight": "900"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "height": 88,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "flexDirection": "row",
    "alignItems": "center",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "answer_y": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "answer_n": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff"
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  },
  "hid": {
    "justifyContent": "center",
    "alignItems": "center"
  }
}

/***/ }),
/* 71 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "width": 500,
    "textAlign": "center",
    "marginLeft": 120,
    "marginRight": 120,
    "textOverflow": "ellipsis",
    "lines": 1,
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "goback_r": {
    "position": "absolute",
    "top": 22,
    "right": 25,
    "width": 44,
    "height": 44
  },
  "goback_r1": {
    "position": "absolute",
    "top": 22,
    "right": 100,
    "width": 37,
    "height": 37
  },
  "book": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 24,
    "paddingTop": 24,
    "paddingRight": 30,
    "paddingLeft": 30,
    "overflow": "hidden",
    "backgroundColor": "#ffffff"
  },
  "b_img": {
    "width": 180,
    "height": 220,
    "marginRight": 30
  },
  "b_info": {
    "width": 500
  },
  "b_main": {
    "fontSize": 34,
    "color": "#666666",
    "marginTop": 20,
    "lineHeight": 40
  },
  "b_list": {
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 55,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "b_name": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#e7f1ff",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "b_name1": {
    "width": 750,
    "color": "#666666",
    "fontSize": 36,
    "textOverflow": "ellipsis",
    "lines": 1,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "b_syn": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "marginBottom": 110
  },
  "b_cnt": {
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 60,
    "paddingTop": 60,
    "paddingRight": 30,
    "paddingLeft": 30
  },
  "b_title": {
    "textAlign": "left",
    "fontSize": 32,
    "color": "#79a8ec"
  },
  "jyzx": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#ffffff",
    "marginTop": 20,
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "borderStyle": "solid",
    "paddingRight": 20,
    "paddingLeft": 20,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "jyzx_title": {
    "color": "#70a1e8",
    "fontSize": 36
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "paddingRight": 15,
    "paddingLeft": 15,
    "paddingTop": 4,
    "paddingBottom": 4,
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "footes": {
    "position": "absolute",
    "zIndex": 9999,
    "left": 0,
    "bottom": 0,
    "width": 750,
    "height": 98,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "footes_btn": {
    "flex": 2,
    "height": 70,
    "lineHeight": 60,
    "marginLeft": 15,
    "marginRight": 15,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderColor": "#ffffff",
    "borderWidth": 1,
    "borderStyle": "solid",
    "fontSize": 34,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center"
  }
}

/***/ }),
/* 72 */
/***/ (function(module, exports) {

module.exports = {
  "msg": {
    "width": 750,
    "height": 100,
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "state": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88,
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "width": 750,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "yes": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "no": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff",
    "fontSize": 34
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  },
  "hid": {
    "justifyContent": "center",
    "alignItems": "center"
  }
}

/***/ }),
/* 73 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "width": 550,
    "marginLeft": 100,
    "marginRight": 100,
    "textOverflow": "ellipsis",
    "lines": 1,
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "send": {
    "position": "absolute",
    "width": 42,
    "height": 42,
    "top": 22,
    "right": 27
  },
  "ydzx_main": {
    "width": 750
  },
  "ydzx_l": {
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750
  },
  "ydzx_img": {
    "width": 690,
    "height": 360,
    "marginLeft": 30,
    "marginRight": 30
  },
  "ydzx_text": {
    "padding": 30,
    "marginBottom": 120,
    "backgroundColor": "#ffffff"
  },
  "ydzx_titil": {
    "color": "#333333",
    "fontSize": 40,
    "lineHeight": 80,
    "overflow": "hidden",
    "textOverflow": "ellipsis",
    "whiteSpace": "nowrap",
    "lines": 2
  },
  "ydzx_time": {
    "color": "#b7b7b7",
    "fontSize": 35,
    "lineHeight": 60,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  }
}

/***/ }),
/* 74 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "backgroundColor": "#ffffff",
    "paddingBottom": 120
  },
  "title": {
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30,
    "flexDirection": "row",
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "t_l": {
    "flex": 2
  },
  "t_r": {
    "flex": 1,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderLeftWidth": 1,
    "borderLeftColor": "#e7e7e7",
    "borderLeftStyle": "solid"
  },
  "state": {
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid",
    "padding": 30
  },
  "s_tit": {
    "fontSize": 32,
    "color": "#777777",
    "marginBottom": 10,
    "fontWeight": "900"
  },
  "count": {
    "fontSize": 34,
    "color": "#666666"
  },
  "question": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "height": 88,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "fontSize": 34,
    "color": "#808080",
    "flexDirection": "row",
    "alignItems": "center",
    "padding": 20,
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7"
  },
  "answer_y": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "answer_n": {
    "backgroundColor": "#f66c6c",
    "color": "#ffffff"
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "marginBottom": 20,
    "backgroundColor": "#ffffff"
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "display": "none",
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "width": 750,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  }
}

/***/ }),
/* 75 */
/***/ (function(module, exports) {

module.exports = {
  "loading": {
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "indicator": {
    "color": "#888888",
    "fontSize": 42,
    "paddingTop": 20,
    "paddingBottom": 20,
    "textAlign": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "tab": {
    "height": 70,
    "marginTop": 19,
    "marginBottom": 19,
    "marginRight": 60,
    "marginLeft": 60,
    "borderWidth": 2,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 35,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "tab_li": {
    "flex": 1,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 70
  },
  "tab_yes": {
    "backgroundColor": "#d9e8fd",
    "borderRadius": 35,
    "color": "#6fa1e8",
    "fontSize": 34
  },
  "type": {
    "height": 85,
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1,
    "borderTopStyle": "solid",
    "borderTopColor": "#e6e6e6",
    "borderTopWidth": 1
  },
  "type_li": {
    "position": "relative",
    "flex": 1,
    "paddingLeft": 40,
    "paddingRight": 40,
    "marginTop": 8,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "type_img": {
    "width": 35,
    "height": 18
  },
  "type_li1": {
    "position": "absolute",
    "right": 0,
    "zIndex": 99,
    "backgroundColor": "#ffffff",
    "width": 50,
    "fontSize": 30,
    "height": 284,
    "lineHeight": 70,
    "flexDirection": "column",
    "alignItems": "center",
    "justifyContent": "center",
    "textIndent": 0.3,
    "borderStyle": "solid",
    "borderColor": "#e6e6e6",
    "borderWidth": 1,
    "color": "#666666"
  },
  "type_li2": {
    "height": 214,
    "left": 0
  },
  "type_ling": {
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1
  },
  "s_class": {
    "position": "fixed",
    "zIndex": 9999,
    "top": 280,
    "width": 375,
    "padding": 5,
    "height": 270,
    "borderColor": "#e6e6e6",
    "borderStyle": "solid",
    "borderWidth": 1,
    "backgroundColor": "#ffffff"
  },
  "s_class_opt": {
    "width": 375,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-start",
    "paddingLeft": 20,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "type_yes": {
    "backgroundColor": "#40cc8b",
    "color": "#ffffff"
  },
  "jrrw": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "paddingTop": 30,
    "paddingBottom": 30
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 0.1
  },
  "jrrw_list": {
    "overflow": "hidden",
    "position": "relative",
    "zIndex": 2,
    "width": 710,
    "marginLeft": 20,
    "marginRight": 20,
    "backgroundColor": "#f1f6fd",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd",
    "borderRadius": 12,
    "marginBottom": 20
  },
  "newR": {
    "position": "absolute",
    "top": -2,
    "right": -2,
    "width": 75,
    "height": 75
  },
  "jrrw_info": {
    "width": 540,
    "paddingTop": 20,
    "paddingBottom": 20,
    "paddingLeft": 30,
    "paddingRight": 30
  },
  "rw_title": {
    "fontSize": 34,
    "lineHeight": 38,
    "color": "#666666"
  },
  "rw_time": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 40,
    "marginTop": 5
  },
  "c_bg": {
    "position": "absolute",
    "top": 35,
    "right": 0,
    "bottom": 0,
    "width": 150,
    "textAlign": "center"
  },
  "c_bg_btn": {
    "textAlign": "center",
    "width": 110,
    "paddingTop": 5,
    "paddingBottom": 5,
    "fontSize": 28,
    "borderRadius": 8,
    "backgroundColor": "#70a1e8",
    "color": "#ffffff",
    "marginBottom": 10
  },
  "prompt": {
    "position": "absolute",
    "bottom": -20,
    "fontSize": 22,
    "color": "#ff6600",
    "left": 0
  },
  "bj0": {
    "backgroundColor": "#f1f6fd"
  },
  "bj1": {
    "backgroundColor": "#ffffff",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#d9e8fd"
  },
  "bj2": {
    "backgroundColor": "#ffffff"
  },
  "c_bg_btn2": {
    "backgroundColor": "#ffffff",
    "color": "#70a1e8",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#70a1e8"
  },
  "c_bg_btn1": {
    "border": "#e6e6e6 1px solid",
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#e6e6e6",
    "backgroundColor": "#ffffff",
    "color": "#999999"
  }
}

/***/ }),
/* 76 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "state": {
    "borderBottom": "1rem solid #e7e7e7",
    "backgroundColor": "#ffffff",
    "width": 750,
    "padding": 28,
    "fontSize": 32,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88,
    "borderBottomWidth": 1,
    "borderBottomColor": "#e7e7e7",
    "borderBottomStyle": "solid"
  },
  "section": {
    "color": "#79a8ec"
  },
  "count": {
    "flex": 1,
    "color": "#666666",
    "textAlign": "center",
    "fontSize": 34
  },
  "t_title": {
    "overflow": "hidden",
    "backgroundColor": "#ffffff"
  },
  "content": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "marginBottom:last-child": 1,
    "paddingRight": 25,
    "paddingLeft": 25,
    "paddingTop": 30,
    "paddingBottom": 120
  },
  "title": {
    "fontSize": 40,
    "color": "#333333",
    "float": "left"
  },
  "diff": {
    "float": "left",
    "lineHeight": 36,
    "fontSize": 32,
    "color": "#999999",
    "marginLeft": 32,
    "verticalAlign": "text-bottom"
  },
  "topic": {
    "width": 100,
    "fontSize": 35,
    "color": "#333333"
  },
  "jg": {
    "position": "absolute",
    "top": 0,
    "bottom": 0,
    "zIndex": 9999999,
    "width": 100,
    "backgroundColor": "rgba(0,0,0,0.8)"
  },
  "wins": {
    "display": "none",
    "position": "absolute",
    "top": 50,
    "left": 50,
    "zIndex": 999,
    "marginTop": -27,
    "marginLeft": -32,
    "width": 645,
    "height": 540,
    "backgroundColor": "#ffffff",
    "borderRadius": 35
  },
  "loser": {
    "display": "none",
    "position": "absolute",
    "top": 50,
    "left": 50,
    "zIndex": 999,
    "marginTop": -27,
    "marginLeft": -32,
    "width": 645,
    "height": 540,
    "backgroundColor": "#ffffff",
    "borderRadius": 35
  },
  "img_t": {
    "display": "block",
    "position": "absolute",
    "left": 50,
    "marginLeft": -27,
    "top": -11,
    "width": 541,
    "height": 231
  },
  "jb": {
    "display": "block",
    "width": 80,
    "marginTop": 60,
    "marginBottom": 60
  },
  "l_jb": {
    "display": "block",
    "width": 80,
    "marginTop": 30,
    "marginBottom": 60
  },
  "l_p": {
    "width": 100,
    "fontSize": 35,
    "color": "#999999",
    "textAlign": "center"
  },
  "bj": {
    "display": "none",
    "width": 100,
    "position": "absolute",
    "top": 0,
    "left": 0,
    "zIndex": 9
  },
  "l_tle": {
    "width": 100,
    "fontSize": 50,
    "color": "#869ab5",
    "fontWeight": "700",
    "textAlign": "center",
    "marginTop": 50
  },
  "show": {
    "display": "block"
  },
  "question": {
    "flex": 1,
    "fontSize": 36,
    "color": "#333333"
  },
  "answer": {
    "marginTop": 25,
    "flexDirection": "column"
  },
  "a_list": {
    "flex": 1,
    "flexDirection": "row",
    "alignItems": "center",
    "backgroundColor": "#fafafa",
    "borderRadius": 30,
    "borderWidth": 2,
    "borderStyle": "solid",
    "borderColor": "#555555",
    "padding": 20
  },
  "a_c": {
    "flex": 0.4,
    "color": "#808080",
    "fontSize": 34
  },
  "a_t": {
    "flex": 3,
    "color": "#808080",
    "fontSize": 34
  },
  "pic_t": {
    "flex": 1,
    "width": 200,
    "height": 200
  },
  "a_l_y": {
    "backgroundColor": "#40cc8b"
  },
  "a_l_n": {
    "backgroundColor": "#f66c6c"
  },
  "a_t_y": {
    "color": "#ffffff",
    "fontSize": 34
  },
  "a_t_n": {
    "color": "#ffffff",
    "fontSize": 34
  },
  "footer": {
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "width": 750,
    "height": 100,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "backgroundColor": "#6fa1e8"
  },
  "list": {
    "flex": 1,
    "marginBottom": 20,
    "backgroundColor": "#ffffff",
    "borderTopWidth": 1,
    "borderTopStyle": "solid",
    "borderTopColor": "#e7e7e7",
    "marginTop": 20
  },
  "t_q": {
    "width": 750,
    "height": 140,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "flex-end",
    "padding": 30,
    "borderBottomWidth": 1,
    "borderRightStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "t_q_btn": {
    "flexDirection": "row",
    "justifyContent": "space-between",
    "alignItems": "center",
    "width": 200,
    "height": 80,
    "paddingRight": 30,
    "paddingLeft": 30,
    "borderRadius": 50,
    "marginRight": 40,
    "backgroundColor": "#70a1e8"
  },
  "j_cnt": {
    "paddingTop": 26,
    "paddingBottom": 26,
    "paddingLeft": 30,
    "paddingRight": 30,
    "flex": 1,
    "borderBottomColor": "#e6e6e6",
    "borderBottomStyle": "solid",
    "borderBottomWidth": 1
  },
  "ans": {
    "fontSize": 33,
    "color": "#808080"
  },
  "tips": {
    "fontSize": 40,
    "color": "#79a8ec",
    "marginTop": 40
  },
  "contents": {
    "fontSize": 33,
    "color": "#343434",
    "lineHeight": 50,
    "marginTop": 25
  },
  "next": {
    "flex": 1,
    "margin": 20,
    "marginTop": 50,
    "borderRadius": 20,
    "height": 100,
    "backgroundColor": "#6fa1e8",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "n_t": {
    "color": "#ffffff",
    "fontSize": 35
  }
}

/***/ }),
/* 77 */
/***/ (function(module, exports) {

module.exports = {
  "image": {
    "width": 750,
    "height": 320
  },
  "slider": {
    "width": 750,
    "height": 320
  },
  "frame": {
    "width": 750,
    "height": 320,
    "position": "relative"
  },
  "indicator": {
    "width": 700,
    "height": 20,
    "itemColor": "#ccc",
    "itemSelectedColor": "#f60",
    "itemSize": 12,
    "position": "absolute",
    "bottom": 20,
    "left": 20
  },
  "content": {
    "marginBottom": 20,
    "height": 200,
    "backgroundColor": "#ffffff",
    "flexDirection": "row"
  },
  "cont_fx": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 200,
    "zIndex": 99
  },
  "fx_img": {
    "width": 20,
    "height": 36
  },
  "panel": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 650,
    "height": 200
  },
  "panel_li": {
    "alignItems": "center",
    "justifyContent": "center",
    "width": 170
  },
  "icon_1": {
    "position": "absolute",
    "top": 0,
    "right": 40,
    "display": "inline-block",
    "width": 17,
    "height": 17,
    "backgroundColor": "#ff6666",
    "borderRadius": 100
  },
  "icon_0": {
    "width": 88,
    "height": 88
  },
  "type_title": {
    "marginTop": 16,
    "fontSize": 30,
    "color": "#838383"
  },
  "fx_key": {
    "width": 50,
    "height": 200,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "jrrw": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "position": "relative"
  },
  "jrrw_title": {
    "flexDirection": "row",
    "height": 35,
    "marginTop": 25,
    "marginBottom": 25,
    "paddingLeft": 20
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "jrrw_list": {
    "width": 700,
    "height": 130,
    "marginLeft": 24,
    "backgroundColor": "#f1f6fd",
    "borderStyle": "solid",
    "borderWidth": 1,
    "borderColor": "#d9e8fd",
    "borderRadius": 12,
    "marginBottom": 20,
    "padding": 30,
    "paddingTop": 10,
    "paddingBottom": 10
  },
  "newR": {
    "position": "absolute",
    "right": 0,
    "top": 0,
    "width": 75,
    "height": 75
  },
  "jrrw_info": {
    "width": 540
  },
  "rw_title": {
    "flexDirection": "row",
    "height": 55,
    "alignItems": "center"
  },
  "rw_time": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "width": 450,
    "height": 60
  },
  "c_bg": {
    "position": "absolute",
    "right": 40,
    "width": 100,
    "textAlign": "center",
    "top": 50,
    "height": 45,
    "lineHeight": 45,
    "fontSize": 28,
    "borderRadius": 8,
    "backgroundColor": "#70a1e8",
    "color": "#ffffff"
  },
  "mrzx": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "marginTop": 20,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "backgroundColor": "#ffffff"
  },
  "mrzx_l": {
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88,
    "padding": 30
  },
  "mrzx_title": {
    "fontSize": 36,
    "lineHeight": 45,
    "color": "#70a1e8"
  },
  "mrzx_r": {
    "height": 180,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "mrzx_list": {
    "width": 140,
    "height": 180,
    "alignItems": "center",
    "justifyContent": "center"
  },
  "mrzx_list_tx": {
    "width": 101,
    "height": 101,
    "borderRadius": 55,
    "position": "relative",
    "zIndex": 8
  },
  "name": {
    "fontSize": 28,
    "color": "#666666",
    "marginTop": 15
  },
  "pic_hg": {
    "width": 45,
    "height": 45,
    "position": "absolute",
    "left": 45,
    "top": -5,
    "zIndex": 99
  },
  "jyzx": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#ffffff",
    "marginTop": 20,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "fontSize": 36,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "yjzc_more": {
    "fontSize": 28,
    "color": "#838383"
  },
  "yjzc_more_img": {
    "width": 16,
    "height": 26,
    "marginLeft": 10
  },
  "jyzx_list": {
    "backgroundColor": "#ffffff",
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "jyzx_l": {
    "width": 180,
    "height": 118,
    "borderRadius": 3,
    "margin": 20
  },
  "jyzx_r": {
    "flex": 3,
    "marginLeft": 20,
    "marginRight": 10
  },
  "jyzx_titil": {
    "color": "#333333",
    "fontSize": 34,
    "lineHeight": 60,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "record": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 60
  },
  "type_name": {
    "backgroundColor": "#70a1e8",
    "color": "#ffffff",
    "paddingLeft": 8,
    "paddingRight": 8,
    "paddingTop": 4,
    "paddingBottom": 4,
    "borderRadius": 5
  },
  "openYdzx": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "paddingRight": 20
  }
}

/***/ }),
/* 78 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "goback_r": {
    "position": "absolute",
    "top": 22,
    "right": 25,
    "width": 44,
    "height": 44
  },
  "goback_r1": {
    "position": "absolute",
    "top": 22,
    "right": 100,
    "width": 44,
    "height": 44
  },
  "ydrw_zt": {
    "width": 750,
    "height": 300,
    "position": "relative"
  },
  "ydrw_zt_title": {
    "width": 750,
    "position": "absolute",
    "top": 0,
    "left": 0,
    "paddingTop": 40,
    "paddingBottom": 40,
    "color": "#ffffff"
  },
  "ydrw_ztName": {
    "fontSize": 45,
    "lineHeight": 160,
    "letterSpacing": 12,
    "fontWeight": "bold",
    "textAlign": "center",
    "color": "#ffffff"
  },
  "ydry_ztlist": {
    "fontSize": 32,
    "textAlign": "center",
    "color": "#ffffff"
  },
  "ydrw_lump": {
    "borderBottomColor": "#e6e6e6",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "margin": 20,
    "marginBottom": 0,
    "paddingBottom": 20
  },
  "fontsz": {
    "fontSize": 34
  },
  "bookName": {
    "fontSize": 36,
    "width": 750,
    "height": 88,
    "backgroundColor": "#e7f1ff",
    "flexDirection": "row",
    "alignItems": "center",
    "textIndent": 20,
    "color": "#666666"
  },
  "book_lump": {
    "width": 750,
    "height": 290,
    "backgroundColor": "#ffffff",
    "position": "relative",
    "paddingRight": 30,
    "paddingLeft": 30,
    "paddingTop": 25,
    "paddingBottom": 25,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#ececec",
    "borderBottomWidth": 1,
    "flexDirection": "row"
  },
  "jrrw_title": {
    "flexDirection": "row",
    "height": 35,
    "marginTop": 25,
    "marginBottom": 25
  },
  "ydjy_l": {
    "width": 190,
    "height": 240,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "book_lump_img": {
    "width": 190,
    "height": 240
  },
  "book_lump_info": {
    "width": 500,
    "paddingLeft": 30
  },
  "ydjy_state": {
    "position": "absolute",
    "top": 20,
    "left": 0,
    "backgroundColor": "#ff6600",
    "color": "#ffffff",
    "fontSize": 26,
    "paddingRight": 15,
    "paddingLeft": 15,
    "paddingTop": 5,
    "paddingBottom": 5
  },
  "ydrw_lump_comt": {
    "marginBottom": 20,
    "marginTop": 20,
    "lineHeight": 40,
    "fontSize": 32,
    "color": "#666666"
  },
  "lstj": {
    "width": 750,
    "fontSize": 32,
    "flexDirection": "row"
  },
  "lstj_l": {
    "flexDirection": "column",
    "width": 180,
    "paddingRight": 30,
    "paddingLeft": 30,
    "paddingTop": 20,
    "paddingBottom": 20
  },
  "lstj_lpic": {
    "width": 120,
    "height": 120,
    "borderRadius": 60,
    "position": "relative"
  },
  "lstj_lname": {
    "backgroundColor": "#70a1e8",
    "fontSize": 32,
    "padding": 10,
    "paddingTop": 5,
    "paddingBottom": 5,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center",
    "position": "relative",
    "left": 0,
    "bottom": 20
  },
  "b_list": {
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 47,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "yerw_yd": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 120
  },
  "ydrw_btn": {
    "backgroundColor": "#ff6600",
    "fontSize": 36,
    "paddingRight": 120,
    "paddingLeft": 120,
    "paddingTop": 20,
    "paddingBottom": 20,
    "borderRadius": 8,
    "color": "#ffffff"
  },
  "px_smq": {
    "width": 750,
    "fontSize": 32,
    "flexDirection": "row",
    "padding": 20,
    "backgroundColor": "#f0f0f0",
    "marginBottom": 100
  },
  "ewm_name": {
    "fontSize": 28,
    "marginRight": 20,
    "lineHeight": 60,
    "alignItems": "center",
    "paddingTop": 20
  },
  "pic_ewm": {
    "width": 120,
    "height": 120,
    "marginRight": 30
  },
  "footes": {
    "width": 750,
    "height": 98,
    "position": "fixed",
    "bottom": 0,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "footes_btn": {
    "width": 450,
    "height": 80,
    "borderStyle": "solid",
    "borderColor": "#ffffff",
    "borderWidth": 1,
    "fontSize": 36,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center",
    "lineHeight": 70
  },
  "voice": {
    "width": 270,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "borderStyle": "solid",
    "borderColor": "#e8e8e8",
    "borderWidth": 1,
    "borderRadius": 8,
    "backgroundColor": "#f5f5f5",
    "marginTop": 20,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "pic_yy": {
    "width": 20,
    "height": 30
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "ts_type": {
    "flexDirection": "row",
    "alignItems": "center"
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "paddingBottom": 4,
    "paddingTop": 4,
    "paddingLeft": 15,
    "paddingRight": 15,
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "record": {
    "flexDirection": "row",
    "height": 45,
    "lineHeight": 45,
    "fontSize": 30,
    "color": "#999999",
    "fontFamily": "微软雅黑"
  },
  "record_li": {
    "flex": 1,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "pic_see": {
    "width": 33,
    "height": 22
  },
  "pic_praise": {
    "width": 28,
    "height": 23
  }
}

/***/ }),
/* 79 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "width": 500,
    "textAlign": "center",
    "marginLeft": 120,
    "marginRight": 120,
    "textOverflow": "ellipsis",
    "lines": 1,
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "goback_r": {
    "position": "absolute",
    "top": 22,
    "right": 25,
    "width": 44,
    "height": 44
  },
  "goback_r1": {
    "position": "absolute",
    "top": 22,
    "right": 100,
    "width": 37,
    "height": 37
  },
  "book": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 24,
    "paddingTop": 24,
    "paddingRight": 30,
    "paddingLeft": 30,
    "overflow": "hidden",
    "backgroundColor": "#ffffff"
  },
  "b_img": {
    "width": 180,
    "height": 220,
    "marginRight": 30
  },
  "b_info": {
    "width": 500
  },
  "b_main": {
    "fontSize": 34,
    "color": "#666666",
    "marginTop": 20,
    "lineHeight": 40
  },
  "b_list": {
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 55,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "b_name": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#e7f1ff",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "b_name1": {
    "width": 750,
    "color": "#666666",
    "fontSize": 36,
    "textOverflow": "ellipsis",
    "lines": 1,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "b_syn": {
    "width": 750,
    "backgroundColor": "#ffffff"
  },
  "b_cnt": {
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 60,
    "paddingTop": 60,
    "paddingRight": 30,
    "paddingLeft": 30
  },
  "b_title": {
    "textAlign": "left",
    "fontSize": 32,
    "color": "#79a8ec"
  },
  "jyzx": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#ffffff",
    "marginTop": 20,
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "borderStyle": "solid",
    "paddingRight": 20,
    "paddingLeft": 20,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "jyzx_title": {
    "color": "#70a1e8",
    "fontSize": 36
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "paddingRight": 15,
    "paddingLeft": 15,
    "paddingTop": 4,
    "paddingBottom": 4,
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "ico_22": {
    "width": 40,
    "height": 40
  },
  "ico_23": {
    "width": 33,
    "height": 40
  },
  "ico_24": {
    "width": 37,
    "height": 37
  },
  "footes": {
    "position": "fixed",
    "bottom": 0,
    "width": 750,
    "height": 98,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "footes_btn": {
    "flex": 2,
    "height": 70,
    "lineHeight": 60,
    "marginLeft": 15,
    "marginRight": 15,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderColor": "#ffffff",
    "borderWidth": 1,
    "borderStyle": "solid",
    "fontSize": 34,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center"
  }
}

/***/ }),
/* 80 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "content": {
    "width": 750,
    "padding": 30
  },
  "textarea": {
    "width": 700,
    "height": 417,
    "lines": 8,
    "borderColor": "#e6e6e6",
    "borderStyle": "solid",
    "borderWidth": 1,
    "padding": 24,
    "fontSize": 34,
    "marginTop": 30,
    "borderRadius": 10
  },
  "panel": {
    "width": 700,
    "height": 88,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "marginTop": 30,
    "marginBottom": 47,
    "paddingRight": 20
  },
  "photoList": {
    "flexDirection": "row",
    "flexWrap": "wrap",
    "marginTop": 20
  },
  "photo": {
    "width": 52,
    "height": 52,
    "marginLeft": 60
  },
  "voicePic": {
    "flexDirection": "row",
    "justifyContent": "flex-start",
    "width": 41,
    "height": 52,
    "marginLeft": 60
  },
  "confirm": {
    "width": 700,
    "height": 100,
    "textAlign": "center",
    "fontSize": 39,
    "color": "#ffffff",
    "fontWeight": "500",
    "borderRadius": 10,
    "backgroundColor": "#79a8ec",
    "lineHeight": 100,
    "border": 0
  },
  "voice": {
    "width": 270,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "borderStyle": "solid",
    "borderColor": "#e8e8e8",
    "borderWidth": 1,
    "borderRadius": 8,
    "backgroundColor": "#f5f5f5",
    "marginTop": 20,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "pic_yy": {
    "width": 20,
    "height": 30
  }
}

/***/ }),
/* 81 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "width": 500,
    "textAlign": "center",
    "marginLeft": 120,
    "marginRight": 120,
    "textOverflow": "ellipsis",
    "lines": 1,
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 22,
    "left": 25,
    "width": 44,
    "height": 44
  },
  "goback_r": {
    "position": "absolute",
    "top": 22,
    "right": 25,
    "width": 44,
    "height": 44
  },
  "goback_r1": {
    "position": "absolute",
    "top": 22,
    "right": 100,
    "width": 37,
    "height": 37
  },
  "book": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 24,
    "paddingTop": 24,
    "paddingRight": 30,
    "paddingLeft": 30,
    "overflow": "hidden",
    "backgroundColor": "#ffffff"
  },
  "b_img": {
    "width": 180,
    "height": 220,
    "marginRight": 30
  },
  "b_info": {
    "width": 500
  },
  "b_main": {
    "fontSize": 34,
    "color": "#666666",
    "marginTop": 20,
    "lineHeight": 40
  },
  "b_list": {
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 55,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "b_name": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#e7f1ff",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "b_name1": {
    "width": 750,
    "color": "#666666",
    "fontSize": 36,
    "textOverflow": "ellipsis",
    "lines": 1,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "b_syn": {
    "width": 750,
    "backgroundColor": "#ffffff"
  },
  "b_cnt": {
    "width": 750,
    "borderBottomWidth": 1,
    "borderBottomColor": "#f1f1ee",
    "borderBottomStyle": "solid",
    "paddingBottom": 60,
    "paddingTop": 60,
    "paddingRight": 30,
    "paddingLeft": 30
  },
  "b_title": {
    "textAlign": "left",
    "fontSize": 32,
    "color": "#79a8ec"
  },
  "jyzx": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#ffffff",
    "marginTop": 20,
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "borderStyle": "solid",
    "paddingRight": 20,
    "paddingLeft": 20,
    "flexDirection": "row",
    "alignItems": "center"
  },
  "jyzx_title": {
    "color": "#70a1e8",
    "fontSize": 36
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "paddingRight": 15,
    "paddingLeft": 15,
    "paddingTop": 4,
    "paddingBottom": 4,
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "ico_22": {
    "width": 40,
    "height": 40
  },
  "ico_23": {
    "width": 33,
    "height": 40
  },
  "ico_24": {
    "width": 37,
    "height": 37
  },
  "footes": {
    "position": "fixed",
    "bottom": 0,
    "width": 750,
    "height": 98,
    "backgroundColor": "#70a1e8",
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "footes_btn": {
    "flex": 2,
    "height": 70,
    "lineHeight": 60,
    "marginLeft": 15,
    "marginRight": 15,
    "flexDirection": "row",
    "justifyContent": "center",
    "alignItems": "center",
    "borderColor": "#ffffff",
    "borderWidth": 1,
    "borderStyle": "solid",
    "fontSize": 34,
    "borderRadius": 8,
    "color": "#ffffff",
    "textAlign": "center"
  }
}

/***/ }),
/* 82 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "main": {
    "backgroundColor": "#ffffff",
    "width": 750
  },
  "title": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "width": 750,
    "height": 90,
    "backgroundColor": "#ffffff"
  },
  "content": {
    "width": 750,
    "paddingLeft": 35,
    "paddingBottom": 40,
    "paddingRight": 35,
    "backgroundColor": "#ffffff",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "grade": {
    "textAlign": "right",
    "fontSize": 46,
    "color": "#f66c6c",
    "fontWeight": "600",
    "marginTop": 80,
    "marginRight": 40
  },
  "tlt": {
    "width": 750,
    "padding": 30,
    "fontSize": 36,
    "color": "#999999",
    "backgroundColor": "#f3f3ef"
  },
  "remark": {
    "width": 750,
    "padding": 30,
    "flexDirection": "row"
  },
  "remark_pic": {
    "width": 80,
    "height": 80,
    "borderRadius": 40,
    "float": "left",
    "marginRight": 20
  },
  "name": {
    "marginRight": 25,
    "float": "left",
    "fontSize": 35,
    "color": "#79a8ec",
    "height": 80,
    "lineHeight": 80
  },
  "photoList": {
    "flexDirection": "row",
    "flexWrap": "wrap",
    "marginTop": 20
  },
  "voice": {
    "width": 270,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "borderStyle": "solid",
    "borderColor": "#e8e8e8",
    "borderWidth": 1,
    "borderRadius": 8,
    "backgroundColor": "#f5f5f5",
    "marginTop": 20,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "pic_yy": {
    "width": 20,
    "height": 30
  },
  "r_info": {
    "fontSize": 34,
    "color": "#333333",
    "marginTop": 20
  }
}

/***/ }),
/* 83 */
/***/ (function(module, exports) {

module.exports = {
  "header": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "content": {
    "width": 750,
    "paddingTop": 27,
    "paddingBottom": 27,
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "title_img": {
    "borderLeftWidth": 10,
    "borderLeftStyle": "solid",
    "borderLeftColor": "#70a1e8",
    "fontSize": 40,
    "color": "#333333",
    "paddingLeft": 10
  },
  "cnt": {
    "backgroundColor": "#ffffff",
    "alignItems": "stretch"
  },
  "top": {
    "flexDirection": "row"
  },
  "t_l": {
    "flex": 2
  },
  "time": {
    "color": "#808080",
    "fontSize": 33,
    "marginTop": 15
  },
  "t_tle": {
    "fontSize": 40,
    "color": "#333333"
  },
  "t_r": {
    "flex": 1,
    "alignItems": "center",
    "flexDirection": "row"
  },
  "t_r_i": {
    "width": 17,
    "height": 24
  },
  "topic": {
    "flex": 2,
    "borderLeftWidth": 1,
    "borderLeftColor": "#e7e7e7",
    "borderLeftStyle": "solid",
    "color": "#79a8ec",
    "textAlign": "center",
    "lineHeight": 105,
    "fontSize": 32
  },
  "acc": {
    "flex": 1,
    "marginTop": 20,
    "borderWidth": 1,
    "borderColor": "#e7f1ff",
    "borderStyle": "solid",
    "borderRadius": 5
  },
  "book_name": {
    "flex": 1,
    "height": 75,
    "justifyContent": "center",
    "fontWeight": "bold",
    "fontSize": 34,
    "backgroundColor": "#e7f1ff",
    "color": "#666666",
    "borderTopLeftRadius": 5,
    "borderTopRightRadius": 5
  },
  "book_main": {
    "flex": 1
  },
  "book_type": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "paddingTop": 30,
    "paddingRight": 20,
    "paddingBottom": 10,
    "paddingLeft": 20,
    "fontSize": 34
  },
  "b_t_g": {
    "flexDirection": "row",
    "alignItems": "center"
  },
  "cg_btn": {
    "backgroundColor": "#6fa1e8",
    "color": "#ffffff",
    "fontSize": 28,
    "paddingTop": 10,
    "paddingRight": 15,
    "paddingBottom": 10,
    "paddingLeft": 15,
    "borderRadius": 8
  },
  "book_type1": {
    "flex": 1,
    "marginLeft": 20,
    "marginRight": 20,
    "height": 88,
    "alignItems": "center",
    "fontSize": 30,
    "color": "#666666",
    "flexDirection": "row",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#e7e7e7"
  },
  "b_t_c": {
    "flex": 1,
    "justifyContent": "center",
    "flexDirection": "row"
  },
  "remark": {
    "flex": 1,
    "flexDirection": "row",
    "paddingRight": 30,
    "paddingBottom": 27,
    "paddingLeft": 30
  },
  "r_info": {
    "fontSize": 32,
    "color": "#666666",
    "float": "left",
    "marginTop": 20
  },
  "title": {
    "height": 75,
    "alignItems": "flex-start",
    "justifyContent": "center",
    "paddingLeft": 20,
    "fontSize": 33
  },
  "lstj_l": {
    "flexDirection": "column",
    "width": 150
  },
  "lstj_lpic": {
    "width": 100,
    "height": 100,
    "borderRadius": 50
  },
  "lstj_lname": {
    "position": "relative",
    "backgroundColor": "#70a1e8",
    "fontSize": 24,
    "width": 110,
    "paddingTop": 6,
    "paddingBottom": 6,
    "borderRadius": 5,
    "color": "#ffffff",
    "top": -25,
    "left": -8,
    "textAlign": "center",
    "overflow": "hidden",
    "textOverflow": "ellipsis",
    "whiteSpace": "nowrap"
  },
  "ydrw_lump_comt": {
    "flex": 3,
    "fontSize": 28,
    "marginTop": 20,
    "marginBottom": 20,
    "lineHeight": 40,
    "color": "#666666"
  },
  "yy_btn": {
    "height": 70,
    "borderWidth": 1,
    "borderStyle": "solid",
    "borderColor": "#666666",
    "borderRadius": 10,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "paddingLeft": 20,
    "paddingRight": 20
  },
  "yy_t": {
    "color": "#79a8ec",
    "fontSize": 30
  },
  "yy_i": {
    "width": 70,
    "height": 60
  }
}

/***/ }),
/* 84 */
/***/ (function(module, exports) {

module.exports = {
  "loading": {
    "width": 750,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "indicator": {
    "color": "#888888",
    "fontSize": 42,
    "paddingTop": 20,
    "paddingBottom": 20,
    "textAlign": "center"
  },
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "top": 25,
    "left": 25,
    "width": 37,
    "height": 37
  },
  "ydzx_main": {
    "width": 750,
    "backgroundColor": "#f0f0f0",
    "marginBottom": 30
  },
  "ydzx_list": {
    "marginLeft": 20,
    "marginRight": 20,
    "marginTop": 30,
    "borderStyle": "solid",
    "borderWidth": 1,
    "borderColor": "#e7e7e7",
    "backgroundColor": "#ffffff"
  },
  "ydzx_l": {
    "padding": 20
  },
  "ydzx_img": {
    "width": 630,
    "height": 320,
    "marginLeft": 30,
    "marginRight": 30,
    "marginTop": 3,
    "marginBottom": 3
  },
  "ydzx_text": {
    "fontSize": 30,
    "color": "#999999",
    "paddingLeft": 30,
    "paddingRight": 30,
    "paddingBottom": 10,
    "paddingTop": 10,
    "lineHeight": 45,
    "backgroundColor": "#ffffff",
    "overflow": "hidden",
    "textOverflow": "ellipsis",
    "lines": 2
  },
  "ydzx_more": {
    "width": 18,
    "height": 30
  },
  "ydzx_titil": {
    "color": "#333333",
    "fontSize": 36,
    "lineHeight": 60,
    "overflow": "hidden",
    "textOverflow": "ellipsis",
    "whiteSpace": "nowrap"
  },
  "ydzx_time": {
    "color": "#b7b7b7",
    "fontSize": 34
  },
  "ydzx_f": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 85,
    "marginLeft": 20,
    "marginRight": 20,
    "borderTopWidth": 1,
    "borderColor": "#e7e7e7",
    "borderStyle": "solid"
  }
}

/***/ }),
/* 85 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("每日一练")])]), _c('scroller', [_vm._m(0), _c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(q.id) + "." + _vm._s(q.title) + "(    )")])]), _vm._l((q.answer), function(a) {
      return _c('div', {
        staticClass: ["answer"]
      }, [_c('text', {
        staticStyle: {
          fontSize: "34px",
          color: "#666"
        },
        on: {
          "click": function($event) {
            _vm.last(q.ans, a.ans)
          }
        }
      }, [_vm._v(_vm._s(a.ans) + "、" + _vm._s(a.option))])])
    }), _c('div', {
      staticClass: ["list"]
    }, [_vm._m(1, true), _c('div', {
      staticClass: ["j_cnt"]
    }, [_c('text', {
      staticClass: ["ans"]
    }, [_vm._v("正确答案是" + _vm._s(q.ans) + "，你的答案是" + _vm._s(q.answer.ans))]), (q.answer.ans) ? _c('text', [_vm._v("回答错误")]) : _vm._e(), (!q.answer.ans) ? _c('text', [_vm._v("回答正确")]) : _vm._e(), _c('text', {
      staticClass: ["tips"]
    }, [_vm._v("解析")]), _c('text', {
      staticClass: ["contents"]
    }, [_vm._v(_vm._s(q.cnt))]), _c('div', {
      on: {
        "click": _vm.hidden
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "34px"
      }
    }, [_vm._v("收起")])])])])], 2)
  }))]), _vm._m(2)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["s_tit"]
  }, [_vm._v("读短文并按要求作答")]), _c('text', {
    staticClass: ["count"]
  }, [_vm._v("答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%答对7道，答错3道，正确率：70%")])])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["t_q"]
  }, [_c('div', {
    staticClass: ["t_q_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#fff"
    }
  }, [_vm._v("解析")]), _c('image', {
    staticStyle: {
      width: "29px",
      height: "36px"
    },
    attrs: {
      "src": "../static/images/pic_jx.png"
    }
  })])])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["footer"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    }
  }, [_vm._v("提交")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 86 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("学习报告")])]), _c('div', {
    staticClass: ["type"]
  }, [_c('div', {
    staticClass: ["type_li"],
    staticStyle: {
      borderRightColor: "#e6e6e6",
      borderRightStyle: "solid",
      borderRightWidth: "1px"
    },
    on: {
      "click": _vm.fn
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.typeName))]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })]), _c('div', {
    staticClass: ["type_li"],
    on: {
      "click": _vm.show
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.stateName))]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })])]), (_vm.willShow) ? _c('div', {
    staticClass: ["s_class"],
    staticStyle: {
      left: "0px",
      height: "260px"
    }
  }, [_c('scroller', _vm._l((_vm.typeList), function(type) {
    return _c('div', {
      staticClass: ["s_class_opt"],
      style: {
        backgroundColor: type.bj,
        color: type.color
      },
      on: {
        "click": function($event) {
          _vm.optType(type.id, type.name)
        }
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "34px"
      },
      style: {
        backgroundColor: type.bj,
        color: type.color
      }
    }, [_vm._v(_vm._s(type.name))])])
  }))]) : _vm._e(), (_vm.willShow2) ? _c('div', {
    staticClass: ["s_class"],
    staticStyle: {
      left: "375px",
      height: "330px"
    }
  }, [_c('scroller', _vm._l((_vm.stateList), function(state) {
    return _c('div', {
      staticClass: ["s_class_opt"],
      style: {
        backgroundColor: state.bj,
        color: state.color
      },
      on: {
        "click": function($event) {
          _vm.optState(state.id, state.name)
        }
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "34px"
      },
      style: {
        backgroundColor: state.bj,
        color: state.color
      }
    }, [_vm._v(_vm._s(state.name))])])
  }))]) : _vm._e(), _c('scroller', [_c('div', {
    staticClass: ["jrrw"]
  }, _vm._l((_vm.rw_list), function(rw) {
    return _c('div', {
      staticClass: ["jrrw_list"],
      class: ['bj' + rw.state],
      on: {
        "click": function($event) {
          _vm.openRwxq(rw.type, rw.id, rw.name)
        }
      }
    }, [_c('div', {
      staticClass: ["jrrw_info"]
    }, [_c('text', {
      staticClass: ["rw_title"]
    }, [_vm._v(_vm._s(_vm._f("tName")(rw.type)))]), (rw.type == 1) ? _c('text', {
      staticClass: ["rwType"],
      staticStyle: {
        marginTop: "20px"
      }
    }, [_vm._v("阅读任务：" + _vm._s(rw.name))]) : _vm._e(), (rw.type == 2) ? _c('text', {
      staticClass: ["rwType"],
      staticStyle: {
        marginTop: "20px"
      }
    }, [_vm._v("阅读任务：" + _vm._s(rw.title))]) : _vm._e(), _c('text', {
      staticClass: ["rwType"],
      staticStyle: {
        marginTop: "10px"
      }
    }, [_vm._v(_vm._s(rw.teaName) + " 老师")]), _c('text', {
      staticClass: ["rwType"],
      staticStyle: {
        marginTop: "10px"
      }
    }, [_vm._v("截止：" + _vm._s(rw.endDate))])]), _c('div', {
      staticClass: ["c_bg"]
    }, [_c('text', {
      staticClass: ["c_bg_btn"]
    }, [_vm._v(_vm._s(rw.ability))])]), (rw.isNew) ? _c('image', {
      staticClass: ["newR"],
      attrs: {
        "src": "../static/images/xs_pic_newR.png"
      }
    }) : _vm._e(), _c('div', {
      staticClass: ["j_l_g"]
    }, [(rw.score) ? _c('text', {
      staticClass: ["dhg"]
    }, [_vm._v("读后感：" + _vm._s(rw.score) + "分")]) : _vm._e(), _c('text', {
      staticClass: ["dhg"]
    }, [_vm._v("闯关正确率" + _vm._s(rw.percent) + "%")]), _c('text', {
      staticClass: ["dhg", "ct"]
    }, [_vm._v("错题" + _vm._s(rw.wrongCount) + "题")])])])
  })), _c('loading', {
    staticClass: ["loading"],
    attrs: {
      "display": _vm.showLoading
    },
    on: {
      "loading": _vm.onloading
    }
  }, [_c('text', {
    staticClass: ["indicator"]
  }, [_vm._v("加载更多 ...")])])], 1)])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 87 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fff"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("完成结果")])]), _c('div', {
    staticClass: ["contents"]
  }, [_c('div', {
    staticClass: ["success"]
  }, [_c('div', {
    staticClass: ["succ_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.state))])]), _c('div', {
    staticClass: ["stars_list"]
  }, [(_vm.stars1) ? _c('image', {
    staticClass: ["stars"],
    attrs: {
      "src": "../static/images/Stars.png"
    }
  }) : _vm._e(), (!_vm.stars1) ? _c('image', {
    staticClass: ["stars"],
    attrs: {
      "src": "../static/images/Stars_off.png"
    }
  }) : _vm._e(), (_vm.stars2) ? _c('image', {
    staticClass: ["stars1"],
    attrs: {
      "src": "../static/images/Stars.png"
    }
  }) : _vm._e(), (!_vm.stars2) ? _c('image', {
    staticClass: ["stars1"],
    attrs: {
      "src": "../static/images/Stars_off.png"
    }
  }) : _vm._e(), (_vm.stars3) ? _c('image', {
    staticClass: ["stars"],
    attrs: {
      "src": "../static/images/Stars_off.png"
    }
  }) : _vm._e(), (!_vm.stars3) ? _c('image', {
    staticClass: ["stars"],
    attrs: {
      "src": "../static/images/Stars_off.png"
    }
  }) : _vm._e()])]), _c('div', {
    staticClass: ["topic"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px"
    }
  }, [_vm._v("答对")]), _c('text', {
    staticClass: ["topic_num"]
  }, [_vm._v(" " + _vm._s(_vm.yCount) + " ")]), _c('text', {
    staticStyle: {
      fontSize: "36px"
    }
  }, [_vm._v("题，答错")]), _c('text', {
    staticClass: ["topic_num"]
  }, [_vm._v(" " + _vm._s(_vm.nCount) + " ")]), _c('text', {
    staticStyle: {
      fontSize: "36px"
    }
  }, [_vm._v("题，正确率" + _vm._s(_vm.zql) + "%")])]), _vm._m(0)]), _c('div', {
    staticClass: ["footes"]
  }, [_c('div', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm._goto("")
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "30px",
      color: "#555555"
    }
  }, [_vm._v("重做错题")])]), _c('div', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm._goto("/kstfb")
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "30px",
      color: "#555555"
    }
  }, [_vm._v("结束闯关")])])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["integral"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "30px"
    }
  }, [_vm._v("奖励积分：10分")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 88 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("书香榜")])]), _c('div', {
    staticClass: ["tab"]
  }, _vm._l((_vm.tylelist), function(tab, index) {
    return _c('div', {
      staticClass: ["tab_li"],
      style: {
        backgroundColor: tab.bj,
        color: tab.color
      },
      on: {
        "click": function($event) {
          _vm.cut(index, tab.id)
        }
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      },
      style: {
        backgroundColor: tab.bj,
        color: tab.color
      }
    }, [_vm._v(_vm._s(tab.name))])])
  })), _vm._m(0), _c('div', {
    staticClass: ["info_l_s"],
    staticStyle: {
      backgroundColor: "#d9e8fd",
      marginBottom: "20px"
    }
  }, [_c('div', {
    staticClass: ["info_flex1"]
  }, [_c('text', {
    staticClass: ["o_nbr"]
  }, [_vm._v(_vm._s(_vm.mine.index))])]), _c('div', {
    staticClass: ["info_flex3"]
  }, [_c('image', {
    staticClass: ["s_tx"],
    attrs: {
      "src": _vm.mine.avatar
    }
  }), _c('div', {
    staticStyle: {
      flexDirection: "column"
    }
  }, [_c('text', {
    staticClass: ["s_name"]
  }, [_vm._v(_vm._s(_vm.mine.stuname))]), _c('text', {
    staticClass: ["designation"]
  }, [_vm._v(_vm._s(_vm.mine.level))])])]), _c('text', {
    staticClass: ["s_rank"]
  }, [_vm._v(_vm._s(_vm.mine.score))]), _c('div', {
    staticClass: ["info_flex2"]
  }, [_c('text', {
    staticClass: ["s_grade"]
  }, [_vm._v(_vm._s(_vm.mine.words))]), _c('text', {
    staticStyle: {
      fontSize: "28px"
    }
  }, [_vm._v("万")])])]), _c('scroller', [_vm._l((_vm.list), function(l, i) {
    return _c('div', {
      staticClass: ["info_l_s"],
      staticStyle: {
        backgroundColor: "#fff"
      }
    }, [_c('div', {
      staticClass: ["info_flex1"]
    }, [(i > 2) ? _c('text', {
      staticClass: ["o_nbr"]
    }, [_vm._v(_vm._s(i + 1))]) : _vm._e(), (i == 0) ? _c('image', {
      staticClass: ["pic_ph"],
      attrs: {
        "src": "../static/images/pic_gold.png"
      }
    }) : _vm._e(), (i == 1) ? _c('image', {
      staticClass: ["pic_ph"],
      attrs: {
        "src": "../static/images/pic_silver.png"
      }
    }) : _vm._e(), (i == 2) ? _c('image', {
      staticClass: ["pic_ph"],
      attrs: {
        "src": "../static/images/pic_copper.png"
      }
    }) : _vm._e()]), _c('div', {
      staticClass: ["info_flex3"]
    }, [_c('image', {
      staticClass: ["s_tx"],
      attrs: {
        "src": l.avatar
      }
    }), _c('div', {
      staticStyle: {
        flexDirection: "column"
      }
    }, [_c('text', {
      staticClass: ["s_name"]
    }, [_vm._v(_vm._s(l.stuname))]), _c('text', {
      staticClass: ["designation"]
    }, [_vm._v(_vm._s(l.level))])])]), _c('text', {
      staticClass: ["s_rank"]
    }, [_vm._v(_vm._s(l.score))]), _c('div', {
      staticClass: ["info_flex2"]
    }, [_c('text', {
      staticClass: ["s_grade"]
    }, [_vm._v(_vm._s(l.words))]), _c('text', {
      staticStyle: {
        fontSize: "28px"
      }
    }, [_vm._v("万")])])])
  }), _c('loading', {
    staticClass: ["loading"],
    attrs: {
      "display": _vm.showLoading
    },
    on: {
      "loading": _vm.onloading
    }
  }, [_c('text', {
    staticClass: ["indicator"]
  }, [_vm._v("加载更多 ...")])])], 2)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["info_tit"]
  }, [_c('text', {
    staticClass: ["info_tit_list"],
    staticStyle: {
      flex: "1"
    }
  }, [_vm._v("排名")]), _c('text', {
    staticClass: ["info_tit_list"],
    staticStyle: {
      flex: "3"
    }
  }, [_vm._v("姓名")]), _c('text', {
    staticClass: ["info_tit_list"],
    staticStyle: {
      flex: "2"
    }
  }, [_vm._v("积分")]), _c('text', {
    staticClass: ["info_tit_list"],
    staticStyle: {
      flex: "2"
    }
  }, [_vm._v("阅读量(万)")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 89 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    on: {
      "androidback": _vm.back
    }
  }, [_c('router-view', {
    staticStyle: {
      flex: "1"
    }
  })], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 90 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("任务详情")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_heart01.png"
    }
  })]), _c('scroller', [_c('div', [_c('image', {
    staticClass: ["ydrw_zt"],
    attrs: {
      "src": "../static/images/bj.png"
    }
  }), _c('div', {
    staticClass: ["ydrw_zt_title"]
  }, [_c('text', {
    staticClass: ["ydrw_ztName"]
  }, [_vm._v(_vm._s(_vm.planName))]), _c('text', {
    staticClass: ["ydry_ztlist"]
  }, [_vm._v(_vm._s(_vm.teacher.TEA_NAME) + "|" + _vm._s(_vm.teacher.SCHOOL_NAME))])])]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("任务时间：" + _vm._s(_vm.beginDate) + "至" + _vm._s(_vm.endDate))])], 1)]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("老师推荐语")])], 1), _c('div', {
    staticClass: ["lstj"]
  }, [_c('div', {
    staticClass: ["lstj_l"]
  }, [_c('image', {
    staticClass: ["lstj_lpic"],
    attrs: {
      "src": _vm.teacher.HEAD_URL
    }
  }), _c('text', {
    staticClass: ["lstj_lname"]
  }, [_vm._v(_vm._s(_vm.teacher.TEA_NAME))])]), _c('div', {
    staticStyle: {
      flex: "3",
      marginRight: "20px"
    }
  }, [_c('text', {
    staticClass: ["ydrw_lump_comt"]
  }, [_vm._v(_vm._s(_vm.reason))]), (_vm.reasonDuration) ? _c('div', {
    staticClass: ["voice"],
    on: {
      "click": function($event) {
        _vm.playVoice(_vm.voiceReason)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.reasonDuration) + "'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": _vm.VoicePic
    }
  })]) : _vm._e()])])]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("考核心方式： " + _vm._s(_vm._f("types")(_vm.access)))])], 1)]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("阅读要求")])], 1), _c('div', [_c('text', {
    staticClass: ["ydrw_lump_comt"]
  }, [_vm._v(_vm._s(_vm.requirements))]), (_vm.requirementsDuration) ? _c('div', {
    staticClass: ["voice"],
    on: {
      "click": function($event) {
        _vm.playTvoice(_vm.requirementsVoice)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.requirementsDuration) + "'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": _vm.VoicePic
    }
  })]) : _vm._e()])]), _c('div', {
    staticClass: ["jrrw_title"],
    staticStyle: {
      marginLeft: "20px",
      marginRight: "20px",
      marginBottom: "20px"
    }
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("本期图书:共" + _vm._s(_vm.books.length) + "本")])], 1), _vm._l((_vm.books), function(book, index) {
    return _c('div', [_c('div', {
      staticClass: ["bookName"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "32px"
      }
    }, [_vm._v("  图书" + _vm._s(index + 1))]), _vm._v("    "), _c('text', {
      staticStyle: {
        flex: "3",
        fontSize: "34px",
        textOverflow: "ellipsis",
        lines: "1"
      }
    }, [_vm._v(_vm._s(book.bookName))])]), _c('div', {
      staticClass: ["book_lump"]
    }, [_c('div', {
      staticClass: ["ydjy_l"]
    }, [_c('image', {
      staticClass: ["book_lump_img"],
      staticStyle: {
        position: "relative"
      },
      attrs: {
        "src": book.pic
      }
    }), (!book.testFinish == 0 && !book.feelingFinish == 0) ? _c('text', {
      staticClass: ["ydjy_state"]
    }, [_vm._v(_vm._s(_vm._f("ydName")(book.testFinish, book.feelingFinish)))]) : _vm._e()]), _c('div', {
      staticClass: ["book_lump_info"]
    }, [_c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("作者：" + _vm._s(book.author) + "/著 ")]), _c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("出版社：" + _vm._s(book.press))]), _c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("ISBN:" + _vm._s(book.isbn))]), _c('div', {
      staticClass: ["ts_type"]
    }, [_c('text', {
      staticClass: ["ts_label"]
    }, [_vm._v(_vm._s(book.bookType))])]), _c('div', {
      staticClass: ["record"]
    }, [_c('div', {
      staticClass: ["record_li"]
    }, [_c('image', {
      staticClass: ["pic_see"],
      attrs: {
        "src": "../static/images/ico_see.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(book.readCount))])]), _c('div', {
      staticClass: ["record_li"]
    }, [_c('image', {
      staticClass: ["pic_praise"],
      attrs: {
        "src": "../static/images/ico_heart.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(book.toast))])])])])]), _c('div', {
      staticClass: ["ydrw_lump"]
    }, [_c('div', {
      staticClass: ["jrrw_title"]
    }, [_c('span', {
      staticClass: ["title_img"]
    }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
      staticClass: ["fontsz"]
    }, [_vm._v("内容简介")])], 1), _c('text', {
      staticClass: ["ydrw_lump_comt"],
      staticStyle: {
        textOverflow: "ellipsis",
        lines: "5"
      }
    }, [_vm._v(_vm._s(book.desc))])]), _c('div', {
      staticClass: ["yerw_yd"]
    }, [_c('text', {
      staticClass: ["ydrw_btn"],
      on: {
        "click": function($event) {
          _vm.openYdxq('/ydrw_xq?bookId=' + book.bookId + '&type=' + book.type + '&access=' + _vm.access + '&feelingFinish=' + book.feelingFinish + '&testFinish=' + book.testFinish + '&bookName=' + book.bookName + '&planId=' + _vm.rwId)
        }
      }
    }, [_vm._v("开始阅读")])])])
  }), _vm._m(0)], 2)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["px_smq"]
  }, [_c('image', {
    staticClass: ["pic_ewm"],
    attrs: {
      "src": "../static/images/yyyd_gzh.jpg"
    }
  }), _c('div', {
    staticClass: ["ewm_name"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "32px",
      color: "#666",
      lineHeight: "50px"
    }
  }, [_vm._v("长按识别二维码，关注[一起阅读]")]), _c('text', {
    staticStyle: {
      fontSize: "32px",
      color: "#666",
      lineHeight: "50px"
    }
  }, [_vm._v("免费获取 200本精品图书阅读内容")])])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 91 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("每日一练")])]), _c('scroller', [_c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["s_tit"]
  }, [_vm._v("读短文并按要求作答")]), _c('text', {
    staticClass: ["count"]
  }, [_vm._v("    " + _vm._s(_vm.cont))])]), _c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q, v) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(q.no) + "." + _vm._s(q.question.content))])]), _vm._l((q.question.answers), function(a, v) {
      return _c('div', {
        key: v,
        staticClass: ["answer"],
        style: {
          backgroundColor: a.bgColor
        },
        on: {
          "click": function($event) {
            _vm._isZq(a, q.answers, v, q.no)
          }
        }
      }, [_c('div', [_c('text', _vm._b({
        staticStyle: {
          fontSize: "34px",
          color: "#666"
        }
      }, 'text', _vm.que), [_vm._v(_vm._s(a.answerOption) + "、" + _vm._s(a.answerContent))])])])
    }), _c('div', {
      staticClass: ["list"]
    }, [_c('div', {
      staticClass: ["t_q"],
      on: {
        "click": function($event) {
          _vm._wtjx(q.question, v)
        }
      }
    }, [_vm._m(0, true)]), (q.isShow) ? _c('div', {
      key: v,
      staticClass: ["j_cnt"]
    }, [_c('text', {
      staticClass: ["ans"]
    }, [_vm._v("正确答案是  " + _vm._s(_vm.Right) + "，你的答案是  " + _vm._s(q.chooseAnswer) + "\n\t\t\t\t\t\t")]), _c('text', {
      staticClass: ["tips"]
    }, [_vm._v("解析")]), _c('text', {
      staticClass: ["contents"]
    }, [_vm._v("暂无解析")]), _c('div', {
      staticClass: ["hid"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "40px",
        backgroundColor: "#70a1e8",
        padding: "10px",
        borderRadius: "20px",
        color: "#fff"
      },
      on: {
        "click": function($event) {
          _vm.hidden(v)
        }
      }
    }, [_vm._v("收起")])])]) : _vm._e()])], 2)
  }))]), _c('div', {
    staticClass: ["footer"],
    on: {
      "click": _vm.subimt
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    },
    on: {
      "click": function($event) {
        _vm.optj()
      }
    }
  }, [_vm._v("重新练习")])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["t_q_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#fff"
    }
  }, [_vm._v("解析")]), _c('image', {
    staticStyle: {
      width: "29px",
      height: "36px"
    },
    attrs: {
      "src": "../static/images/pic_jx.png"
    }
  })])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 92 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v(_vm._s(_vm.bNname))])]), (_vm.q_list.length == 0) ? _c('div', {
    staticClass: ["msg"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "50px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.msg))])]) : _vm._e(), (_vm.q_list.length != 0) ? _c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["count"]
  }, [_vm._v("答对" + _vm._s(_vm.dui) + "道，答错" + _vm._s(_vm.cuo) + "道，正确率：" + _vm._s(_vm.zql) + "%")])]) : _vm._e(), _c('scroller', [_c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q, v) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666",
        marginRight: "35px"
      }
    }, [_vm._v(_vm._s(q.no) + "." + _vm._s(q.question.qname) + "(    )")])]), _vm._l((q.answer), function(a) {
      return _c('div', {
        staticClass: ["answer"],
        style: {
          backgroundColor: a.bgColor
        }
      }, [_c('text', {
        staticStyle: {
          fontSize: "34px",
          color: "#666",
          marginRight: "10px"
        }
      }, [_vm._v(_vm._s(a.answerOption) + "、" + _vm._s(a.content))])])
    }), _c('div', {
      staticClass: ["list"]
    }, [_c('div', {
      staticClass: ["t_q"],
      on: {
        "click": function($event) {
          _vm.show(q.answer, v)
        }
      }
    }, [_vm._m(0, true)]), (q.isShow) ? _c('div', {
      key: v,
      staticClass: ["j_cnt"]
    }, [_c('text', {
      staticClass: ["ans"]
    }, [_vm._v("正确答案是  " + _vm._s(q.ans) + "，你的答案是  " + _vm._s(q.chooseAnswer) + "\n\t\t\t\t\t\t")]), _c('text', {
      staticClass: ["tips"]
    }, [_vm._v("解析")]), _c('text', {
      staticClass: ["contents"]
    }, [_vm._v("暂无解析")]), _c('div', {
      staticClass: ["hid"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        borderStyle: "solid",
        color: "#666",
        padding: "10px",
        borderRadius: "5px",
        paddingLeft: "30px",
        paddingRight: "30px"
      },
      on: {
        "click": function($event) {
          _vm.hidden(v)
        }
      }
    }, [_vm._v("收起")])])]) : _vm._e()])], 2)
  }))]), _c('div', {
    staticClass: ["footer"],
    on: {
      "click": function($event) {
        _vm._goto('/ydrw/kscg?bid=' + _vm.bid + '&bname=' + _vm.bNname)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    }
  }, [_vm._v("重新闯关")])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["t_q_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#fff"
    }
  }, [_vm._v("解析")]), _c('image', {
    staticStyle: {
      width: "29px",
      height: "36px"
    },
    attrs: {
      "src": "../static/images/pic_jx.png"
    }
  })])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 93 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("我的任务")])]), _c('div', {
    staticClass: ["tab"]
  }, [_c('text', {
    staticClass: ["tab_li"],
    on: {
      "click": _vm.openYdrw
    }
  }, [_vm._v("阅读任务")]), _c('text', {
    staticClass: ["tab_li", "tab_yes"],
    on: {
      "click": _vm.onpeKstfb
    }
  }, [_vm._v("考试提分宝")])]), _c('div', {
    staticClass: ["type"]
  }, [_c('div', {
    staticClass: ["type_li"],
    staticStyle: {
      borderRightColor: "#e6e6e6",
      borderRightStyle: "solid",
      borderRightWidth: "1px"
    },
    on: {
      "click": function($event) {
        _vm.fn()
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "32px",
      color: "#666"
    }
  }, [_vm._v("类型")]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })]), _c('div', {
    staticClass: ["type_li"],
    on: {
      "click": function($event) {
        _vm.show()
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "32px",
      color: "#666"
    }
  }, [_vm._v("状态")]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })])]), _c('scroller', [_c('div', {
    staticClass: ["jrrw"]
  }, _vm._l((_vm.kstfb_list), function(kstfb, v) {
    return _c('div', {
      staticClass: ["jrrw_list"],
      class: ['bj' + kstfb.state],
      on: {
        "click": function($event) {
          _vm.mryl(v)
        }
      }
    }, [_c('div', {
      staticClass: ["jrrw_info"]
    }, [_c('text', {
      staticClass: ["rw_title"]
    }, [_vm._v("每日一练")]), _c('div', {
      staticClass: ["rw_time"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v("截止：" + _vm._s(kstfb.endDate))])])]), _c('div', {
      staticClass: ["c_bg"]
    }, [(kstfb.state == 0) ? _c('text', {
      staticClass: ["c_bg_btn"],
      class: ['c_bg_btn' + kstfb.state]
    }, [_vm._v("前往")]) : _vm._e(), (kstfb.state == 1) ? _c('text', {
      staticClass: ["c_bg_btn"],
      class: ['c_bg_btn' + kstfb.state]
    }, [_vm._v("已完成")]) : _vm._e(), (kstfb.stateName == '补做') ? _c('text', {
      staticClass: ["stateName"]
    }, [_vm._v("逾期未提交")]) : _vm._e()]), (kstfb.state == 0) ? _c('image', {
      staticClass: ["newR"],
      attrs: {
        "src": "../static/images/xs_pic_newR.png"
      }
    }) : _vm._e()])
  }))])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 94 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("每日一练")])]), _c('scroller', [_c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["s_tit"]
  }, [_vm._v("读短文并按要求作答")]), _c('text', {
    staticClass: ["count"]
  }, [_vm._v("    " + _vm._s(_vm.cont))])]), _c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q, v) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(q.no) + "." + _vm._s(q.question.content))])]), _vm._l((q.question.answers), function(a, v) {
      return _c('div', {
        key: v,
        staticClass: ["answer"],
        style: {
          backgroundColor: a.bgColor
        },
        on: {
          "click": function($event) {
            _vm._isZq(a, q.answers, v, q.no)
          }
        }
      }, [_c('div', [_c('text', _vm._b({
        staticStyle: {
          fontSize: "34px",
          color: "#666"
        }
      }, 'text', _vm.que), [_vm._v(_vm._s(a.answerOption) + "、" + _vm._s(a.answerContent))])])])
    }), (q.isRight != 1) ? _c('div', {
      staticClass: ["list"]
    }, [_c('div', {
      staticClass: ["t_q"],
      on: {
        "click": function($event) {
          _vm._wtjx(q.question, v)
        }
      }
    }, [_vm._m(0, true)]), (q.isShow) ? _c('div', {
      key: v,
      staticClass: ["j_cnt"]
    }, [_c('text', {
      staticClass: ["ans"]
    }, [_vm._v("正确答案是  " + _vm._s(_vm.Right) + "，你的答案是  " + _vm._s(q.chooseAnswer) + "\n\t\t\t\t\t\t")]), _c('text', {
      staticClass: ["tips"]
    }, [_vm._v("解析")]), _c('text', {
      staticClass: ["contents"]
    }, [_vm._v("暂无解析")]), _c('div', {
      staticClass: ["hid"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "40px",
        backgroundColor: "#70a1e8",
        padding: "10px",
        borderRadius: "20px",
        color: "#fff"
      },
      on: {
        "click": function($event) {
          _vm.hidden(v)
        }
      }
    }, [_vm._v("收起")])])]) : _vm._e()]) : _vm._e()], 2)
  }))]), _c('div', {
    staticClass: ["footer"],
    on: {
      "click": _vm.subimt
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    },
    on: {
      "click": function($event) {
        _vm.optj()
      }
    }
  }, [_vm._v("完成")])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["t_q_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#fff"
    }
  }, [_vm._v("解析")]), _c('image', {
    staticStyle: {
      width: "29px",
      height: "36px"
    },
    attrs: {
      "src": "../static/images/pic_jx.png"
    }
  })])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 95 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fafafa"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("阅读任务详情")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_heart01.png"
    }
  })]), _c('scroller', [_c('div', {
    staticClass: ["b_name"]
  }, [_c('text', {
    staticClass: ["b_name1"]
  }, [_vm._v(_vm._s(_vm.book.bookName))])]), _c('div', {
    staticClass: ["book"]
  }, [_c('image', {
    staticClass: ["b_img"],
    attrs: {
      "src": _vm.book.bookPic
    }
  }), _c('div', {
    staticClass: ["b_info"]
  }, [_c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("作者：" + _vm._s(_vm.book.author) + "/著 ")]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("出版社：" + _vm._s(_vm.book.press))]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("ISBN: " + _vm._s(_vm.book.isbn) + " ")]), _c('div', {
    staticStyle: {
      flexDirection: "row",
      justifyContent: "left",
      alignItems: "center"
    }
  }, [_c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v(_vm._s(_vm.book.bookType))])])])]), _vm._m(0), _c('div', {
    staticClass: ["b_syn"]
  }, [_c('video', {
    staticStyle: {
      width: "750px",
      height: "400px"
    },
    attrs: {
      "src": _vm.book.guideVoice,
      "playStatus": "play",
      "controls": "controls"
    }
  }), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("编辑推荐")]), _c('text', {
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm._f("filterHTMLs")(_vm.book.editorIntro)))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("内容简介")]), _c('text', {
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm._f("filterHTMLs")(_vm.book.desc)))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("作者简介")]), _c('text', {
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm._f("filterHTMLs")(_vm.book.authorDesc)))])])], 1)]), _c('div', {
    staticClass: ["footes"]
  }, [(_vm.pcShow) ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.jump('/ydrw/xdhg?planId=' + _vm.rwId + '&bookId=' + _vm.bId + '&bookType=' + _vm.rwtype + '&title=' + _vm.bName)
      }
    }
  }, [_vm._v("读后分享")]) : _vm._e(), (_vm.cgShow) ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.jump('/ydrw/kscg?bid=' + _vm.bId + '&bname=' + _vm.bName + '&pid=' + _vm.rwId)
      }
    }
  }, [_vm._v("开始闯关")]) : _vm._e(), (_vm.pc) ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.jump('/ydrw/wddhg?planId=' + _vm.rwId + '&bookId=' + _vm.bId)
      }
    }
  }, [_vm._v("我的分享")]) : _vm._e(), (_vm.cg) ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.jump('/ydrw/wdcg?bid=' + _vm.bId + '&bname=' + _vm.bName + '&pid=' + _vm.rwId)
      }
    }
  }, [_vm._v("我的闯关")]) : _vm._e()])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["jyzx"]
  }, [_c('text', {
    staticClass: ["jyzx_title"]
  }, [_vm._v("专家导读")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 96 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v(_vm._s(_vm.bNname))])]), (_vm.q_list.length == 0) ? _c('div', {
    staticClass: ["msg"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "50px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.msg))])]) : _vm._e(), (_vm.q_list.length != 0) ? _c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["count"]
  }, [_vm._v("答对" + _vm._s(_vm.dui) + "道，答错" + _vm._s(_vm.cuo) + "道，正确率：" + _vm._s(_vm.zql) + "%")])]) : _vm._e(), _c('scroller', [_c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q, v) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666",
        marginRight: "35px"
      }
    }, [_vm._v(_vm._s(q.no) + "." + _vm._s(q.question.qname) + "(    )")])]), _vm._l((q.answer), function(a) {
      return _c('div', {
        staticClass: ["answer"],
        style: {
          backgroundColor: a.bgColor
        }
      }, [_c('text', {
        staticStyle: {
          fontSize: "34px",
          color: "#666",
          marginRight: "10px"
        },
        on: {
          "click": function($event) {
            _vm.last(q.ans, a.ans)
          }
        }
      }, [_vm._v(_vm._s(a.answerOption) + "、" + _vm._s(a.content))])])
    }), _c('div', {
      staticClass: ["list"]
    }, [_c('div', {
      staticClass: ["t_q"],
      on: {
        "click": function($event) {
          _vm.show(q.answer, v)
        }
      }
    }, [_vm._m(0, true)]), (q.isShow) ? _c('div', {
      key: v,
      staticClass: ["j_cnt"]
    }, [_c('text', {
      staticClass: ["ans"]
    }, [_vm._v("正确答案是  " + _vm._s(q.ans) + "，你的答案是  " + _vm._s(q.chooseAnswer) + "\n\t\t\t\t\t\t")]), _c('text', {
      staticClass: ["tips"]
    }, [_vm._v("解析")]), _c('text', {
      staticClass: ["contents"]
    }, [_vm._v("暂无解析")]), _c('div', {
      staticClass: ["hid"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "40px",
        backgroundColor: "#70a1e8",
        padding: "10px",
        borderRadius: "20px",
        color: "#fff"
      },
      on: {
        "click": function($event) {
          _vm.hidden(v)
        }
      }
    }, [_vm._v("收起")])])]) : _vm._e()])], 2)
  }))]), _c('div', {
    staticClass: ["footer"],
    on: {
      "click": function($event) {
        _vm._goto("")
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    }
  }, [_vm._v("重新闯关")])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["t_q_btn"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#fff"
    }
  }, [_vm._v("解析")]), _c('image', {
    staticStyle: {
      width: "29px",
      height: "36px"
    },
    attrs: {
      "src": "../static/images/pic_jx.png"
    }
  })])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 97 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fff",
      fontFamily: "黑体"
    },
    attrs: {
      "id": "ydzx"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v(_vm._s(_vm.ydzxXq.title))]), _c('image', {
    staticClass: ["send"],
    attrs: {
      "src": "../static/images/ico_2101.png"
    }
  })]), _c('scroller', [_c('div', {
    staticClass: ["ydzx_main"]
  }, [_c('div', {
    staticClass: ["ydzx_l"]
  }, [_c('text', {
    staticClass: ["ydzx_titil"]
  }, [_vm._v(_vm._s(_vm.ydzxXq.title))]), _c('div', {
    staticClass: ["ydzx_time"]
  }, [_c('text', {
    staticStyle: {
      color: "#666",
      fontSize: "30px"
    }
  }, [_vm._v(_vm._s(_vm.ydzxXq.createDate))]), _c('text', {
    staticStyle: {
      color: "#6fa1e8",
      fontSize: "30px"
    }
  }, [_vm._v(_vm._s(_vm.ydzxXq.tags))])])]), (_vm.ydzxXq.image) ? _c('image', {
    staticClass: ["ydzx_img"],
    attrs: {
      "src": _vm.ydzxXq.image
    }
  }) : _vm._e(), _c('div', {
    staticClass: ["ydzx_text"]
  }, [_c('text', {
    staticStyle: {
      color: "#666",
      fontSize: "36px",
      textAlign: "justify",
      lineHeight: "45px"
    }
  }, [_vm._v(_vm._s(_vm.ydzxXq.content))])])])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 98 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("每日一练")])]), _c('scroller', [_c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["s_tit"]
  }, [_vm._v("读短文并按要求作答")]), _c('text', {
    staticClass: ["count"]
  }, [_vm._v("    " + _vm._s(_vm.cont))])]), _c('div', {
    staticClass: ["content"]
  }, _vm._l((_vm.q_list), function(q) {
    return _c('div', [_c('div', {
      staticClass: ["question"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(q.no) + "." + _vm._s(q.content))])]), _vm._l((q.answers), function(a, v) {
      return _c('div', {
        key: v,
        staticClass: ["answer"],
        style: {
          backgroundColor: a.bgColor
        },
        on: {
          "click": function($event) {
            _vm._isZq(a, q.answers, v, q.no, q.id)
          }
        }
      }, [_c('text', {
        staticStyle: {
          fontSize: "34px",
          color: "#666"
        }
      }, [_vm._v(_vm._s(a.answerOption) + "、" + _vm._s(a.answerContent))])])
    })], 2)
  }))]), _c('div', {
    staticClass: ["footer"],
    on: {
      "click": _vm.subimt
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "39px",
      color: "#fff"
    },
    on: {
      "click": function($event) {
        _vm.optj()
      }
    }
  }, [_vm._v("提交")])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 99 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("我的任务")])]), _c('div', {
    staticClass: ["tab"]
  }, [_c('div', {
    staticClass: ["tab_li", "tab_yes"],
    on: {
      "click": _vm.openYdrw
    }
  }, [_c('text', {
    staticClass: ["tab_yes"]
  }, [_vm._v("阅读任务")])]), _c('div', {
    staticClass: ["tab_li"],
    on: {
      "click": _vm.onpeKstfb
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#666"
    }
  }, [_vm._v("考试提分宝")])])]), _c('div', {
    staticClass: ["type"]
  }, [_c('div', {
    staticClass: ["type_li"],
    staticStyle: {
      borderRightColor: "#e6e6e6",
      borderRightStyle: "solid",
      borderRightWidth: "1px"
    },
    on: {
      "click": _vm.fn
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.typeName))]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })]), _c('div', {
    staticClass: ["type_li"],
    on: {
      "click": _vm.show
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.stateName))]), _c('image', {
    staticClass: ["type_img"],
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })])]), (_vm.willShow) ? _c('div', {
    staticClass: ["s_class"],
    staticStyle: {
      left: "0px",
      height: "260px"
    }
  }, [_c('scroller', _vm._l((_vm.typeList), function(type) {
    return _c('div', {
      staticClass: ["s_class_opt"],
      style: {
        backgroundColor: type.bj,
        color: type.color
      },
      on: {
        "click": function($event) {
          _vm.optType(type.id, type.name)
        }
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "34px"
      },
      style: {
        backgroundColor: type.bj,
        color: type.color
      }
    }, [_vm._v(_vm._s(type.name))])])
  }))]) : _vm._e(), (_vm.willShow2) ? _c('div', {
    staticClass: ["s_class"],
    staticStyle: {
      left: "375px",
      height: "330px"
    }
  }, [_c('scroller', _vm._l((_vm.stateList), function(state) {
    return _c('div', {
      staticClass: ["s_class_opt"],
      style: {
        backgroundColor: state.bj,
        color: state.color
      },
      on: {
        "click": function($event) {
          _vm.optState(state.id, state.name)
        }
      }
    }, [_c('text', {
      staticStyle: {
        fontSize: "34px"
      },
      style: {
        backgroundColor: state.bj,
        color: state.color
      }
    }, [_vm._v(_vm._s(state.name))])])
  }))]) : _vm._e(), _c('scroller', [_c('div', {
    staticClass: ["jrrw"]
  }, _vm._l((_vm.rw_list), function(rw) {
    return _c('div', {
      staticClass: ["jrrw_list"],
      class: ['bj' + rw.state],
      on: {
        "click": function($event) {
          _vm.openRwxq('/ydrw/ckjh?planId=' + rw.planId + '&type=' + _vm.typeId)
        }
      }
    }, [_c('div', {
      staticClass: ["jrrw_info"]
    }, [_c('text', {
      staticClass: ["rw_title"]
    }, [_vm._v(_vm._s(rw.planName))]), _c('div', {
      staticClass: ["rw_time"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v("截止：" + _vm._s(rw.endDate))]), _c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v(_vm._s(rw.teaName))])])]), _c('div', {
      staticClass: ["c_bg"]
    }, [_c('text', {
      staticClass: ["c_bg_btn"],
      class: ['c_bg_btn' + rw.state]
    }, [_vm._v(_vm._s(_vm._f("sname")(rw.state)))]), (rw.state == '2') ? _c('text', {
      staticClass: ["prompt"]
    }, [_vm._v("逾期未提交")]) : _vm._e()]), (rw.new) ? _c('image', {
      staticClass: ["newR"],
      attrs: {
        "src": "../static/images/xs_pic_newR.png"
      }
    }) : _vm._e()])
  })), _c('loading', {
    staticClass: ["loading"],
    attrs: {
      "display": _vm.showLoading
    },
    on: {
      "loading": _vm.onloading
    }
  }, [_c('text', {
    staticClass: ["indicator"]
  }, [_vm._v("加载更多 ...")])])], 1)])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 100 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v(_vm._s(_vm.bName))])]), _c('div', {
    staticClass: ["state"]
  }, [_c('text', {
    staticClass: ["count"]
  }, [_vm._v(_vm._s(_vm.index) + "/" + _vm._s(_vm.length))])]), _c('scroller', [_c('div', {
    staticClass: ["content"]
  }, [_c('div', [_c('div', {
    staticClass: ["question"]
  }, [(_vm.type == 1) ? _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.index) + "." + _vm._s(_vm.tm.qname) + "(    )")]) : _vm._e(), (_vm.type == 2) ? _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#666"
    }
  }, [_vm._v(_vm._s(_vm.index) + "." + _vm._s(_vm.tm.qname) + "(    )")]) : _vm._e()]), _vm._l((_vm.an_list), function(a, i) {
    return _c('div', {
      staticClass: ["answer"]
    }, [_c('div', {
      staticClass: ["a_list"],
      class: ['a_l_' + a.flag],
      on: {
        "click": function($event) {
          _vm._chose(i, a.rightOption, a.answerOption)
        }
      }
    }, [_c('text', {
      staticClass: ["a_c"],
      class: ['a_t_' + a.flag]
    }, [_vm._v(_vm._s(a.answerOption) + "、")]), (!_vm.pic_flag) ? _c('text', {
      staticClass: ["a_t"],
      class: ['a_t_' + a.flag]
    }, [_vm._v(_vm._s(a.content))]) : _vm._e(), (_vm.pic_flag) ? _c('image', {
      staticClass: ["pic_t"],
      attrs: {
        "src": a.picUrl
      }
    }) : _vm._e()])])
  })], 2), (_vm.a_flag) ? _c('div', {
    staticClass: ["list"]
  }, [_c('div', {
    staticClass: ["j_cnt"]
  }, [_c('text', {
    staticClass: ["ans"]
  }, [_vm._v("正确答案是" + _vm._s(_vm.c_ans))]), _c('text', {
    staticClass: ["tips"]
  }, [_vm._v("解析")]), _c('text', {
    staticClass: ["contents"]
  })])]) : _vm._e(), (_vm.c_flag) ? _c('div', {
    staticClass: ["next"],
    on: {
      "click": _vm._next
    }
  }, [_c('text', {
    staticClass: ["n_t"]
  }, [_vm._v("下一题")])]) : _vm._e(), (_vm.e_flag) ? _c('div', {
    staticClass: ["next"],
    on: {
      "click": _vm._end
    }
  }, [_c('text', {
    staticClass: ["n_t"]
  }, [_vm._v("完成")])]) : _vm._e()])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 101 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('scroller', {
    staticStyle: {
      backgroundColor: "#f0f0f0",
      marginBottom: "100px"
    }
  }, [_c('slider', {
    staticClass: ["slider"],
    attrs: {
      "interval": "3000",
      "autoPlay": "true"
    }
  }, [_vm._l((_vm.imageList), function(img) {
    return _c('div', {
      staticClass: ["frame"]
    }, [_c('image', {
      staticClass: ["image"],
      attrs: {
        "src": img.src
      }
    })])
  }), _c('indicator', {
    staticClass: ["indicator"]
  })], 2), _c('div', {
    staticClass: ["content"]
  }, [_vm._m(0), _c('scroller', {
    staticClass: ["panel"],
    attrs: {
      "scrollDirection": "horizontal",
      "showScrollbar": "false"
    }
  }, [_c('div', {
    staticClass: ["panel_li"],
    on: {
      "click": function($event) {
        _vm.jump('/ydrw')
      }
    }
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/pic_ydrw.png"
    }
  }), _c('div', {
    staticClass: ["icon_1"]
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("阅读任务")])]), _c('div', {
    staticClass: ["panel_li"],
    on: {
      "click": function($event) {
        _vm.jump('/kstfb')
      }
    }
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_kstfb.png"
    }
  }), _c('div', {
    staticClass: ["icon_1"]
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("考试提分宝")])]), _c('div', {
    staticClass: ["panel_li"],
    on: {
      "click": function($event) {
        _vm.jump('/ydbg')
      }
    }
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/pic_ydbg.png"
    }
  }), _c('div', {
    staticClass: ["icon_1"]
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("学习报告")])]), _c('div', {
    staticClass: ["panel_li"],
    on: {
      "click": function($event) {
        _vm.jump('/ydzx')
      }
    }
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_ydzx.png"
    }
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("阅读资讯")])]), _c('div', {
    staticClass: ["panel_li"],
    on: {
      "click": function($event) {
        _vm.jump('/sxb')
      }
    }
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_phb.png"
    }
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("书香榜")])])]), _vm._m(1)]), _c('div', {
    staticClass: ["jrrw"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#70a1e8"
    }
  }, [_vm._v("今日任务")])], 1), _vm._l((_vm.rw_list), function(rw) {
    return _c('div', {
      staticClass: ["jrrw_list"],
      on: {
        "click": function($event) {
          _vm.jump('/ydrw/ckjh?planId=' + rw.planId + '&type=' + rw.type)
        }
      }
    }, [_c('div', {
      staticClass: ["jrrw_info"]
    }, [_c('div', {
      staticClass: ["rw_title"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(rw.planName))])]), _c('div', {
      staticClass: ["rw_time"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v("截止：" + _vm._s(rw.endDate))]), _c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v(_vm._s(rw.teaName))])])]), _c('text', {
      staticClass: ["c_bg"]
    }, [_vm._v("前 往")]), _c('image', {
      staticClass: ["newR"],
      attrs: {
        "src": "../static/images/xs_pic_newR.png"
      }
    })])
  })], 2), _c('div', {
    staticClass: ["mrzx"]
  }, [_vm._m(2), _c('div', {
    staticClass: ["mrzx_r"]
  }, _vm._l((_vm.s_list), function(s, index) {
    return _c('div', {
      staticClass: ["mrzx_list"]
    }, [_c('image', {
      staticClass: ["mrzx_list_tx"],
      attrs: {
        "src": s.avatar
      }
    }), _c('text', {
      staticClass: ["name"]
    }, [_vm._v(_vm._s(s.stuName))]), (index == 0) ? _c('image', {
      staticClass: ["pic_hg"],
      attrs: {
        "src": "../static/images/pic_hg.png"
      }
    }) : _vm._e()])
  }))]), _c('div', [_c('div', {
    staticClass: ["jyzx"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#70a1e8"
    }
  }, [_vm._v("教育资讯")])], 1), _c('div', {
    staticClass: ["openYdzx"],
    on: {
      "click": function($event) {
        _vm.jump('/ydzx')
      }
    }
  }, [_c('text', {
    staticClass: ["yjzc_more"]
  }, [_vm._v("更多")]), _c('image', {
    staticClass: ["yjzc_more_img"],
    attrs: {
      "src": "../static/images/wd_09.png"
    }
  })])]), _vm._l((_vm.jyzx_list), function(jyzx) {
    return _c('div', [_c('div', {
      staticClass: ["jyzx_list"],
      on: {
        "click": function($event) {
          _vm.jump('/ydzx/xq?id=' + jyzx.id)
        }
      }
    }, [_c('div', {
      staticClass: ["jyzx_r"]
    }, [_c('text', {
      staticClass: ["jyzx_titil"]
    }, [_vm._v(" " + _vm._s(jyzx.title))]), _c('div', {
      staticClass: ["record"]
    }, [_c('div', [(jyzx.tags) ? _c('text', {
      staticClass: ["type_name"]
    }, [_vm._v(_vm._s(jyzx.tags))]) : _vm._e()]), _c('div', [(jyzx.see) ? _c('text', {
      staticStyle: {
        fontSize: "28px",
        color: "#828282"
      }
    }, [_vm._v(_vm._s(jyzx.see) + "阅读")]) : _vm._e()])])]), _c('image', {
      staticClass: ["jyzx_l"],
      attrs: {
        "src": jyzx.image
      }
    })])])
  })], 2)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["fx_key"]
  }, [_c('image', {
    staticClass: ["fx_img"],
    attrs: {
      "src": "../static/images/xs_pic_more02.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["fx_key"]
  }, [_c('image', {
    staticClass: ["fx_img"],
    attrs: {
      "src": "../static/images/xs_pic_left.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["mrzx_l"]
  }, [_c('text', {
    staticClass: ["mrzx_title"]
  }, [_vm._v("每日")]), _c('text', {
    staticClass: ["mrzx_title"]
  }, [_vm._v("之星")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 102 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("任务详情")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_heart01.png"
    }
  }), _c('image', {
    staticClass: ["goback_r1"],
    attrs: {
      "src": "../static/images/ico_2101.png"
    }
  })]), _c('scroller', [_c('div', [_c('image', {
    staticClass: ["ydrw_zt"],
    attrs: {
      "src": "../static/images/bj.png"
    }
  }), _c('div', {
    staticClass: ["ydrw_zt_title"]
  }, [_c('text', {
    staticClass: ["ydrw_ztName"]
  }, [_vm._v(_vm._s(_vm.planName))]), _c('text', {
    staticClass: ["ydry_ztlist"]
  }, [_vm._v(_vm._s(_vm.teacher.TEA_NAME) + "|" + _vm._s(_vm.teacher.SCHOOL_NAME))])])]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("任务时间：" + _vm._s(_vm.beginDate) + "至" + _vm._s(_vm.endDate))])], 1)]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("老师推荐语")])], 1), _c('div', {
    staticClass: ["lstj"]
  }, [_c('div', {
    staticClass: ["lstj_l"]
  }, [_c('image', {
    staticClass: ["lstj_lpic"],
    attrs: {
      "src": _vm.teacher.HEAD_URL
    }
  }), _c('text', {
    staticClass: ["lstj_lname"]
  }, [_vm._v(_vm._s(_vm.teacher.TEA_NAME))])]), _c('div', {
    staticStyle: {
      flex: "3",
      marginRight: "20px"
    }
  }, [_c('text', {
    staticClass: ["ydrw_lump_comt"]
  }, [_vm._v(_vm._s(_vm.reason))]), _vm._m(0)])])]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("考核心方式： " + _vm._s(_vm._f("types")(_vm.access)))])], 1)]), _c('div', {
    staticClass: ["ydrw_lump"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("阅读要求")])], 1), _c('div', [_c('text', {
    staticClass: ["ydrw_lump_comt"]
  }, [_vm._v(_vm._s(_vm.requirements))]), _vm._m(1)])]), _c('div', {
    staticClass: ["jrrw_title"],
    staticStyle: {
      marginLeft: "20px",
      marginRight: "20px",
      marginBottom: "20px"
    }
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticClass: ["fontsz"]
  }, [_vm._v("本期图书:共" + _vm._s(_vm.books.length) + "本")])], 1), _vm._l((_vm.books), function(book, index) {
    return _c('div', [_c('div', {
      staticClass: ["bookName"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "32px"
      }
    }, [_vm._v("  图书" + _vm._s(index + 1))]), _vm._v("    "), _c('text', {
      staticStyle: {
        flex: "3",
        fontSize: "34px",
        textOverflow: "ellipsis",
        lines: "1"
      }
    }, [_vm._v(_vm._s(book.bookName))])]), _c('div', {
      staticClass: ["book_lump"]
    }, [_c('div', {
      staticClass: ["ydjy_l"]
    }, [_c('image', {
      staticClass: ["book_lump_img"],
      staticStyle: {
        position: "relative"
      },
      attrs: {
        "src": book.pic
      }
    }), (!book.finish == 0) ? _c('text', {
      staticClass: ["ydjy_state"]
    }, [_vm._v(_vm._s(_vm._f("ydName")(book.finish)))]) : _vm._e()]), _c('div', {
      staticClass: ["book_lump_info"]
    }, [_c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("作者：" + _vm._s(book.author) + "/著 ")]), _c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("出版社：" + _vm._s(book.press))]), _c('text', {
      staticClass: ["b_list"]
    }, [_vm._v("ISBN:" + _vm._s(book.isbn))]), _c('div', {
      staticClass: ["ts_type"]
    }, [_c('text', {
      staticClass: ["ts_label"]
    }, [_vm._v(_vm._s(book.bookType))])]), _c('div', {
      staticClass: ["record"]
    }, [_c('div', {
      staticClass: ["record_li"]
    }, [_c('image', {
      staticClass: ["pic_see"],
      attrs: {
        "src": "../static/images/ico_see.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(book.readCount))])]), _c('div', {
      staticClass: ["record_li"]
    }, [_c('image', {
      staticClass: ["pic_praise"],
      attrs: {
        "src": "../static/images/ico_heart.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(book.toast))])])])])]), _c('div', {
      staticClass: ["ydrw_lump"]
    }, [_c('div', {
      staticClass: ["jrrw_title"]
    }, [_c('span', {
      staticClass: ["title_img"]
    }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
      staticClass: ["fontsz"]
    }, [_vm._v("内容简介")])], 1), _c('text', {
      directives: [{
        name: "html",
        rawName: "v-html",
        value: (book.desc),
        expression: "book.desc"
      }],
      staticClass: ["ydrw_lump_comt"],
      staticStyle: {
        textOverflow: "ellipsis",
        lines: "5"
      }
    }, [_vm._v(_vm._s(book.desc))])]), _c('div', {
      staticClass: ["yerw_yd"]
    }, [_c('text', {
      staticClass: ["ydrw_btn"],
      on: {
        "click": function($event) {
          _vm.openYdxq('/ydbg_wdcg?bookId=' + book.bookId + '&type=' + book.type + '&access=' + _vm.access + '&feelingFinish=' + book.feelingFinish + '&testFinish=' + book.testFinish + '&bookName=' + book.bookName + '&loginAccount=' + _vm.loginA + '&planId=' + _vm.rwId)
        }
      }
    }, [_vm._v("开始阅读")])])])
  }), _vm._m(2), _vm._l((_vm.books), function(book, index) {
    return _c('div', {
      staticClass: ["footes"]
    }, [_c('text', {
      staticClass: ["footes_btn"],
      on: {
        "click": function($event) {
          _vm.openYdxq('/ydbg_wdcg?bookId=' + book.bookId + '&type=' + book.type + '&access=' + _vm.access + '&feelingFinish=' + book.feelingFinish + '&testFinish=' + book.testFinish + '&bookName=' + book.bookName + '&loginAccount=' + _vm.loginA + '&planId=' + _vm.rwId)
        }
      }
    }, [_vm._v("开始阅读")])])
  })], 2)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["voice"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v("8'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": "../static/images/xs_pic_yy.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["voice"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v("8'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": "../static/images/xs_pic_yy.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["px_smq"]
  }, [_c('image', {
    staticClass: ["pic_ewm"],
    attrs: {
      "src": "../static/images/ewm.png"
    }
  }), _c('div', {
    staticClass: ["ewm_name"]
  }, [_c('text', [_vm._v("长按识别二维码，关注[一起阅读]公众号")]), _c('text', [_vm._v("免费获取 200本精品图书阅读内容")])])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 103 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fafafa"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("阅读任务详情")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_heart01.png"
    }
  }), _c('image', {
    staticClass: ["goback_r1"],
    attrs: {
      "src": "../static/images/ico_2101.png"
    },
    on: {
      "click": _vm.openFx
    }
  })]), _c('scroller', [_c('div', {
    staticClass: ["b_name"]
  }, [_c('text', {
    staticClass: ["b_name1"]
  }, [_vm._v(_vm._s(_vm.book.bookName))])]), _c('div', {
    staticClass: ["book"]
  }, [_c('image', {
    staticClass: ["b_img"],
    attrs: {
      "src": _vm.book.guideImage
    }
  }), _c('div', {
    staticClass: ["b_info"]
  }, [_c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("作者：" + _vm._s(_vm.book.author) + "/著 ")]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("出版社：" + _vm._s(_vm.book.press))]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("ISBN: " + _vm._s(_vm.book.isbn) + " ")]), _c('div', {
    staticStyle: {
      flexDirection: "row",
      justifyContent: "left",
      alignItems: "center"
    }
  }, [_c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v(_vm._s(_vm.book.bookType))])])])]), _vm._m(0), _c('div', {
    staticClass: ["b_syn"]
  }, [_c('video', {
    staticStyle: {
      width: "750px",
      height: "400px"
    },
    attrs: {
      "src": _vm.book.guideVoice,
      "controls": "controls"
    }
  }), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("编辑推荐")]), _c('text', {
    directives: [{
      name: "html",
      rawName: "v-html",
      value: (_vm.book.editorIntro),
      expression: "book.editorIntro"
    }],
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.editorIntro))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("内容简介")]), _c('text', {
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.desc))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("作者简介")]), _c('text', {
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.authorDesc))])])], 1)]), _c('div', {
    staticClass: ["footes"]
  }, [(_vm.feeling == 0 && _vm.zrw == '2' || _vm.zrw == '1,2') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.opendhfx('/ydrw/xdhg?planId=' + _vm.rwId + '&bookId=' + _vm.bId + '&bookType=' + _vm.rwtype + '&title=' + _vm.bName)
      }
    }
  }, [_vm._v("读后分享")]) : _vm._e(), (_vm.test == 0 && _vm.zrw == '1' || _vm.zrw == '1,2') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": _vm.openkscg
    }
  }, [_vm._v("开始闯关")]) : _vm._e(), (_vm.feeling == 1 && _vm.zrw == '2') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.openwdfx('/ydrw/wddhg?loginAccount=' + _vm.loginA + '&planId=' + _vm.rwId + '&bookId=' + _vm.bId)
      }
    }
  }, [_vm._v("我的分享")]) : _vm._e(), (_vm.test == 1 && _vm.zrw == '1') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": _vm.openwdcg
    }
  }, [_vm._v("我的闯关")]) : _vm._e()])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["jyzx"]
  }, [_c('text', {
    staticClass: ["jyzx_title"]
  }, [_vm._v("专家导读")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 104 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fafafa"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("读后分享")])]), _c('scroller', [_c('div', {
    staticClass: ["content"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      lineHeight: "45px",
      color: "#666"
    }
  }, [_vm._v("请说一说或者写一写，分享您的读后感：")]), _c('textarea', {
    staticClass: ["textarea"],
    attrs: {
      "placeholder": "请输入读后感",
      "value": (_vm.content)
    },
    on: {
      "input": function($event) {
        _vm.content = $event.target.attr.value
      }
    }
  }), _c('div', {
    staticClass: ["photoList"]
  }, [_vm._l((_vm.imageUrl), function(lPic, index) {
    return _c('div', {
      staticStyle: {
        padding: "10px"
      },
      on: {
        "click": function($event) {
          _vm.enlargePic(lPic, index)
        }
      }
    }, [_c('image', {
      staticStyle: {
        width: "152px",
        height: "150px"
      },
      attrs: {
        "src": lPic
      }
    })])
  }), (_vm.imageUrl.length != 8) ? _c('div', {
    staticStyle: {
      padding: "10px"
    },
    on: {
      "click": _vm.openPic
    }
  }, [_c('image', {
    staticStyle: {
      width: "152px",
      height: "152px"
    },
    attrs: {
      "src": "../static/images/add_pic.png"
    }
  })]) : _vm._e()], 2), _c('div', {
    staticClass: ["panel"]
  }, [_c('div', [(_vm.duration) ? _c('div', {
    staticClass: ["voice"],
    on: {
      "click": function($event) {
        _vm.playVoice(_vm.voiceUrl)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.duration) + "'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": _vm.VoicePic
    }
  })]) : _vm._e()]), _c('image', {
    staticClass: ["voicePic"],
    attrs: {
      "src": "../static/images/ico_02.png"
    },
    on: {
      "click": _vm.openVoice
    }
  })]), _c('text', {
    staticClass: ["confirm"],
    on: {
      "click": _vm.confirm
    }
  }, [_vm._v("提 交")])], 1)])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 105 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fafafa"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("阅读任务详情")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_heart01.png"
    }
  }), _c('image', {
    staticClass: ["goback_r1"],
    attrs: {
      "src": "../static/images/ico_2101.png"
    },
    on: {
      "click": _vm.openFx
    }
  })]), _c('scroller', [_c('div', {
    staticClass: ["b_name"]
  }, [_c('text', {
    staticClass: ["b_name1"]
  }, [_vm._v(_vm._s(_vm.book.bookName))])]), _c('div', {
    staticClass: ["book"]
  }, [_c('image', {
    staticClass: ["b_img"],
    attrs: {
      "src": _vm.book.guideImage
    }
  }), _c('div', {
    staticClass: ["b_info"]
  }, [_c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("作者：" + _vm._s(_vm.book.author) + "/著 ")]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("出版社：" + _vm._s(_vm.book.press))]), _c('text', {
    staticClass: ["b_list"]
  }, [_vm._v("ISBN: " + _vm._s(_vm.book.isbn) + " ")]), _c('div', {
    staticStyle: {
      flexDirection: "row",
      justifyContent: "left",
      alignItems: "center"
    }
  }, [_c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v(_vm._s(_vm.book.bookType))])])])]), _vm._m(0), _c('div', {
    staticClass: ["b_syn"]
  }, [_c('video', {
    staticStyle: {
      width: "750px",
      height: "400px"
    },
    attrs: {
      "src": _vm.book.guideVoice,
      "controls": "controls"
    }
  }), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("编辑推荐")]), _c('text', {
    directives: [{
      name: "html",
      rawName: "v-html",
      value: (_vm.book.editorIntro),
      expression: "book.editorIntro"
    }],
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.editorIntro))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("内容简介")]), _c('text', {
    directives: [{
      name: "html",
      rawName: "v-html",
      value: (_vm.book.desc),
      expression: "book.desc"
    }],
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.desc))])]), _c('div', {
    staticClass: ["b_cnt"]
  }, [_c('text', {
    staticClass: ["b_title"]
  }, [_vm._v("作者简介")]), _c('text', {
    directives: [{
      name: "html",
      rawName: "v-html",
      value: (_vm.book.authorDesc),
      expression: "book.authorDesc"
    }],
    staticClass: ["b_main"]
  }, [_vm._v(_vm._s(_vm.book.authorDesc))])])], 1)]), _c('div', {
    staticClass: ["footes"]
  }, [_c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.opendhfx('/ydrw/xdhg?planId=' + _vm.rwId + '&bookId=' + _vm.bId + '&bookType=' + _vm.rwtype + '&title=' + _vm.bName)
      }
    }
  }, [_vm._v("读后分享")]), _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": _vm.openkscg
    }
  }, [_vm._v("开始闯关")]), (_vm.feeling == 1 && _vm.zrw == '2') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": function($event) {
        _vm.openwdfx('/ydrw/wddhg?loginAccount=' + _vm.loginA + '&planId=' + _vm.rwId + '&bookId=' + _vm.bId)
      }
    }
  }, [_vm._v("我的分享")]) : _vm._e(), (_vm.test == 1 && _vm.zrw == '1') ? _c('text', {
    staticClass: ["footes_btn"],
    on: {
      "click": _vm.openwdcg
    }
  }, [_vm._v("我的闯关")]) : _vm._e()])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["jyzx"]
  }, [_c('text', {
    staticClass: ["jyzx_title"]
  }, [_vm._v("专家导读")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 106 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fafafa"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("我的读后感")])]), _c('scroller', [_c('div', {
    staticClass: ["main"]
  }, [_c('div', {
    staticClass: ["title"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "40px",
      color: "#333333"
    }
  }, [_vm._v(_vm._s(_vm.redList.title))])]), _c('div', {
    staticClass: ["content"]
  }, [_c('text', {
    staticStyle: {
      fontSize: "35px",
      color: "#666666"
    }
  }, [_vm._v(_vm._s(_vm.redList.content))]), (_vm.redList.duration) ? _c('div', {
    staticClass: ["voice"],
    on: {
      "click": function($event) {
        _vm.playVoice(_vm.redList.voice)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.redList.duration) + "'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": _vm.VoicePic
    }
  })]) : _vm._e(), _c('div', {
    staticClass: ["photoList"]
  }, _vm._l((_vm.piclist), function(lPic, index) {
    return _c('div', {
      staticStyle: {
        padding: "10px"
      },
      on: {
        "click": function($event) {
          _vm.enlargePic(lPic, index)
        }
      }
    }, [_c('image', {
      staticStyle: {
        width: "152px",
        height: "150px"
      },
      attrs: {
        "src": lPic
      }
    })])
  })), (_vm.redList.score) ? _c('text', {
    staticClass: ["grade"]
  }, [_vm._v(_vm._s(_vm.redList.score) + "分")]) : _vm._e()]), _c('text', {
    staticClass: ["tlt"]
  }, [_vm._v("老师评语")]), _c('div', {
    staticClass: ["remark"]
  }, [_c('div', {
    staticStyle: {
      flex: "1"
    }
  }, [_c('image', {
    staticClass: ["remark_pic"],
    attrs: {
      "src": _vm.redList.avatar
    }
  }), _c('text', {
    staticClass: ["name"]
  }, [_vm._v(_vm._s(_vm.redList.teaName))])]), _c('div', {
    staticStyle: {
      flex: "3"
    }
  }, [(_vm.redList.checkDuration) ? _c('div', {
    staticClass: ["voice"],
    on: {
      "click": function($event) {
        _vm.playTvoice(_vm.redList.checkVoice)
      }
    }
  }, [_c('text', {
    staticStyle: {
      fontSize: "34px",
      color: "#6fa1e8"
    }
  }, [_vm._v(_vm._s(_vm.redList.checkDuration) + "'")]), _c('image', {
    staticClass: ["pic_yy"],
    attrs: {
      "src": _vm.VoicePic
    }
  })]) : _vm._e(), _c('text', {
    staticClass: ["r_info"]
  }, [_vm._v(_vm._s(_vm.redList.checkContent))])])])])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 107 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["header"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("阅读报告")])]), _c('scroller', [_c('div', {
    staticClass: ["content"]
  }, [_c('div', {
    staticClass: ["cnt"]
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('div', {
    staticClass: ["t_l"]
  }, [_c('text', {
    staticClass: ["t_tle"]
  }, [_vm._v("阅读任务:" + _vm._s(_vm.aName))]), _c('text', {
    staticClass: ["time"]
  }, [_vm._v("2017-01-01")])]), _c('div', {
    staticClass: ["t_r"]
  }, [_c('text', {
    staticClass: ["topic"],
    on: {
      "click": _vm.ckxq
    }
  }, [_vm._v("查看详情")]), _c('image', {
    staticClass: ["t_r_i"],
    attrs: {
      "src": "../static/images/xs_pic_more.png"
    }
  })])]), _vm._l((_vm.ydBook), function(book, v) {
    return _c('div', {
      staticClass: ["acc"]
    }, [_c('div', {
      staticClass: ["book_name"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "35px"
      }
    }, [_vm._v(" 图书 " + _vm._s(v + 1) + "：  " + _vm._s(book.bookName))])]), _c('div', {
      staticClass: ["book_main"]
    }, [_c('div', {
      staticClass: ["book_type"]
    }, [_c('div', {
      staticClass: ["b_t_g"]
    }, [_c('text', {
      staticClass: ["title_img"]
    }, [_vm._v("读后感(分)：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "40px"
      }
    }, [_vm._v(_vm._s(book.score.class))])])]), _c('div', {
      staticClass: ["book_type1"]
    }, [_c('div', {
      staticClass: ["b_t_c"]
    }, [_c('text', {
      staticStyle: {
        color: "#777777",
        fontSize: "30px"
      }
    }, [_vm._v("全班平均：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "30px"
      }
    }, [_vm._v(_vm._s(book.score.mine))])]), _c('div', {
      staticClass: ["b_t_c"]
    }, [_c('text', {
      staticStyle: {
        color: "#777777",
        fontSize: "30px"
      }
    }, [_vm._v("全班最高：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "30px"
      }
    }, [_vm._v(_vm._s(book.score.top))])])]), _c('div', {
      staticClass: ["book_type"]
    }, [_c('div', {
      staticClass: ["b_t_g"]
    }, [_c('text', {
      staticClass: ["title_img"]
    }, [_vm._v("闯关正确率：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "40px"
      }
    }, [_vm._v(_vm._s(book.percent.class))])]), _c('div', {
      on: {
        "click": function($event) {
          _vm._goto(v)
        }
      }
    }, [_c('text', {
      staticClass: ["cg_btn"]
    }, [_vm._v("查看详情")])])]), _c('div', {
      staticClass: ["book_type1"]
    }, [_c('div', {
      staticClass: ["b_t_c"]
    }, [_c('text', {
      staticStyle: {
        color: "#777777",
        fontSize: "30px"
      }
    }, [_vm._v("全班平均：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "30px"
      }
    }, [_vm._v(_vm._s(book.percent.mine))])]), _c('div', {
      staticClass: ["b_t_c"]
    }, [_c('text', {
      staticStyle: {
        color: "#777777",
        fontSize: "30px"
      }
    }, [_vm._v("全班最高：")]), _c('text', {
      staticStyle: {
        color: "#f60",
        fontSize: "30px"
      }
    }, [_vm._v(_vm._s(book.percent.top))])])]), _vm._m(0, true), _c('div', {
      staticClass: ["remark"]
    }, [_c('div', {
      staticClass: ["lstj_l"]
    }, [_c('image', {
      staticClass: ["lstj_lpic"],
      attrs: {
        "src": book.feeling.avatar
      }
    }), _c('text', {
      staticClass: ["lstj_lname"]
    }, [_vm._v(_vm._s(book.feeling.teaName))])]), _c('div', {
      staticClass: ["ydrw_lump_comt"]
    }, [_c('div', {
      staticClass: ["yy_btn"],
      on: {
        "click": function($event) {
          _vm.playVoice(_vm.ydBook, v)
        }
      }
    }, [_c('image', {
      staticClass: ["yy_i"],
      attrs: {
        "src": "../static/images/pic_yy.png"
      }
    })]), _c('text', {
      staticClass: ["r_info"]
    }, [_vm._v(_vm._s(book.feeling.content))])])])])])
  })], 2)])])])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["title"]
  }, [_c('text', {
    staticClass: ["title_img"]
  }, [_vm._v("老师点评读后感")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 108 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    attrs: {
      "id": "ydzx"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("阅读资讯")])]), _c('scroller', [_c('div', {
    staticClass: ["ydzx_main"]
  }, _vm._l((_vm.ydzx_list), function(ydzx) {
    return _c('div', {
      staticClass: ["ydzx_list"],
      on: {
        "click": function($event) {
          _vm.onpeNew('/ydzx/xq?id=' + ydzx.id)
        }
      }
    }, [_c('div', {
      staticClass: ["ydzx_l"]
    }, [_c('text', {
      staticClass: ["ydzx_titil"]
    }, [_vm._v(_vm._s(ydzx.title))]), _c('text', {
      staticClass: ["ydzx_time"]
    }, [_vm._v(_vm._s(ydzx.tags))])]), (ydzx.image) ? _c('image', {
      staticClass: ["ydzx_img"],
      attrs: {
        "src": ydzx.image
      }
    }) : _vm._e(), _c('text', {
      staticClass: ["ydzx_text"]
    }, [_vm._v(_vm._s(ydzx.shortContent))]), _vm._m(0, true)])
  })), _c('loading', {
    staticClass: ["loading"],
    attrs: {
      "display": _vm.showLoading
    },
    on: {
      "loading": _vm.onloading
    }
  }, [_c('text', {
    staticClass: ["indicator"]
  }, [_vm._v("加载更多 ...")])])], 1)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["ydzx_f"]
  }, [_c('text', {
    staticStyle: {
      color: "#4d4d4d",
      fontSize: "34px"
    }
  }, [_vm._v("阅读全文")]), _c('image', {
    staticClass: ["ydzx_more"],
    attrs: {
      "src": "../static/images/xs_pic_more.png"
    }
  })])
}]}
module.exports.render._withStripped = true

/***/ })
/******/ ]);