/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(180)
	var __weex_style__ = __webpack_require__(181)
	var __weex_script__ = __webpack_require__(182)

	__weex_define__('@weex-component/428679602c56b1c6b4286026eb8fde0d', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/428679602c56b1c6b4286026eb8fde0d',undefined,undefined)

/***/ },
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */,
/* 29 */,
/* 30 */,
/* 31 */,
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(45)
	  , core      = __webpack_require__(46)
	  , ctx       = __webpack_require__(47)
	  , hide      = __webpack_require__(49)
	  , PROTOTYPE = 'prototype';

	var $export = function(type, name, source){
	  var IS_FORCED = type & $export.F
	    , IS_GLOBAL = type & $export.G
	    , IS_STATIC = type & $export.S
	    , IS_PROTO  = type & $export.P
	    , IS_BIND   = type & $export.B
	    , IS_WRAP   = type & $export.W
	    , exports   = IS_GLOBAL ? core : core[name] || (core[name] = {})
	    , expProto  = exports[PROTOTYPE]
	    , target    = IS_GLOBAL ? global : IS_STATIC ? global[name] : (global[name] || {})[PROTOTYPE]
	    , key, own, out;
	  if(IS_GLOBAL)source = name;
	  for(key in source){
	    // contains in native
	    own = !IS_FORCED && target && target[key] !== undefined;
	    if(own && key in exports)continue;
	    // export native or passed
	    out = own ? target[key] : source[key];
	    // prevent global pollution for namespaces
	    exports[key] = IS_GLOBAL && typeof target[key] != 'function' ? source[key]
	    // bind timers to global for call from export context
	    : IS_BIND && own ? ctx(out, global)
	    // wrap global constructors for prevent change them in library
	    : IS_WRAP && target[key] == out ? (function(C){
	      var F = function(a, b, c){
	        if(this instanceof C){
	          switch(arguments.length){
	            case 0: return new C;
	            case 1: return new C(a);
	            case 2: return new C(a, b);
	          } return new C(a, b, c);
	        } return C.apply(this, arguments);
	      };
	      F[PROTOTYPE] = C[PROTOTYPE];
	      return F;
	    // make static versions for prototype methods
	    })(out) : IS_PROTO && typeof out == 'function' ? ctx(Function.call, out) : out;
	    // export proto methods to core.%CONSTRUCTOR%.methods.%NAME%
	    if(IS_PROTO){
	      (exports.virtual || (exports.virtual = {}))[key] = out;
	      // export proto methods to core.%CONSTRUCTOR%.prototype.%NAME%
	      if(type & $export.R && expProto && !expProto[key])hide(expProto, key, out);
	    }
	  }
	};
	// type bitmap
	$export.F = 1;   // forced
	$export.G = 2;   // global
	$export.S = 4;   // static
	$export.P = 8;   // proto
	$export.B = 16;  // bind
	$export.W = 32;  // wrap
	$export.U = 64;  // safe
	$export.R = 128; // real proto method for `library` 
	module.exports = $export;

/***/ },
/* 45 */
/***/ function(module, exports) {

	// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
	var global = module.exports = typeof window != 'undefined' && window.Math == Math
	  ? window : typeof self != 'undefined' && self.Math == Math ? self : Function('return this')();
	if(typeof __g == 'number')__g = global; // eslint-disable-line no-undef

/***/ },
/* 46 */
/***/ function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ },
/* 47 */
/***/ function(module, exports, __webpack_require__) {

	// optional / simple context binding
	var aFunction = __webpack_require__(48);
	module.exports = function(fn, that, length){
	  aFunction(fn);
	  if(that === undefined)return fn;
	  switch(length){
	    case 1: return function(a){
	      return fn.call(that, a);
	    };
	    case 2: return function(a, b){
	      return fn.call(that, a, b);
	    };
	    case 3: return function(a, b, c){
	      return fn.call(that, a, b, c);
	    };
	  }
	  return function(/* ...args */){
	    return fn.apply(that, arguments);
	  };
	};

/***/ },
/* 48 */
/***/ function(module, exports) {

	module.exports = function(it){
	  if(typeof it != 'function')throw TypeError(it + ' is not a function!');
	  return it;
	};

/***/ },
/* 49 */
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(50)
	  , createDesc = __webpack_require__(58);
	module.exports = __webpack_require__(54) ? function(object, key, value){
	  return dP.f(object, key, createDesc(1, value));
	} : function(object, key, value){
	  object[key] = value;
	  return object;
	};

/***/ },
/* 50 */
/***/ function(module, exports, __webpack_require__) {

	var anObject       = __webpack_require__(51)
	  , IE8_DOM_DEFINE = __webpack_require__(53)
	  , toPrimitive    = __webpack_require__(57)
	  , dP             = Object.defineProperty;

	exports.f = __webpack_require__(54) ? Object.defineProperty : function defineProperty(O, P, Attributes){
	  anObject(O);
	  P = toPrimitive(P, true);
	  anObject(Attributes);
	  if(IE8_DOM_DEFINE)try {
	    return dP(O, P, Attributes);
	  } catch(e){ /* empty */ }
	  if('get' in Attributes || 'set' in Attributes)throw TypeError('Accessors not supported!');
	  if('value' in Attributes)O[P] = Attributes.value;
	  return O;
	};

/***/ },
/* 51 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(52);
	module.exports = function(it){
	  if(!isObject(it))throw TypeError(it + ' is not an object!');
	  return it;
	};

/***/ },
/* 52 */
/***/ function(module, exports) {

	module.exports = function(it){
	  return typeof it === 'object' ? it !== null : typeof it === 'function';
	};

/***/ },
/* 53 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = !__webpack_require__(54) && !__webpack_require__(55)(function(){
	  return Object.defineProperty(__webpack_require__(56)('div'), 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 54 */
/***/ function(module, exports, __webpack_require__) {

	// Thank's IE8 for his funny defineProperty
	module.exports = !__webpack_require__(55)(function(){
	  return Object.defineProperty({}, 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 55 */
/***/ function(module, exports) {

	module.exports = function(exec){
	  try {
	    return !!exec();
	  } catch(e){
	    return true;
	  }
	};

/***/ },
/* 56 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(52)
	  , document = __webpack_require__(45).document
	  // in old IE typeof document.createElement is 'object'
	  , is = isObject(document) && isObject(document.createElement);
	module.exports = function(it){
	  return is ? document.createElement(it) : {};
	};

/***/ },
/* 57 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.1 ToPrimitive(input [, PreferredType])
	var isObject = __webpack_require__(52);
	// instead of the ES6 spec version, we didn't implement @@toPrimitive case
	// and the second argument - flag - preferred type is a string
	module.exports = function(it, S){
	  if(!isObject(it))return it;
	  var fn, val;
	  if(S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it)))return val;
	  if(typeof (fn = it.valueOf) == 'function' && !isObject(val = fn.call(it)))return val;
	  if(!S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it)))return val;
	  throw TypeError("Can't convert object to primitive value");
	};

/***/ },
/* 58 */
/***/ function(module, exports) {

	module.exports = function(bitmap, value){
	  return {
	    enumerable  : !(bitmap & 1),
	    configurable: !(bitmap & 2),
	    writable    : !(bitmap & 4),
	    value       : value
	  };
	};

/***/ },
/* 59 */,
/* 60 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 / 15.2.3.14 Object.keys(O)
	var $keys       = __webpack_require__(61)
	  , enumBugKeys = __webpack_require__(74);

	module.exports = Object.keys || function keys(O){
	  return $keys(O, enumBugKeys);
	};

/***/ },
/* 61 */
/***/ function(module, exports, __webpack_require__) {

	var has          = __webpack_require__(62)
	  , toIObject    = __webpack_require__(63)
	  , arrayIndexOf = __webpack_require__(67)(false)
	  , IE_PROTO     = __webpack_require__(71)('IE_PROTO');

	module.exports = function(object, names){
	  var O      = toIObject(object)
	    , i      = 0
	    , result = []
	    , key;
	  for(key in O)if(key != IE_PROTO)has(O, key) && result.push(key);
	  // Don't enum bug & hidden keys
	  while(names.length > i)if(has(O, key = names[i++])){
	    ~arrayIndexOf(result, key) || result.push(key);
	  }
	  return result;
	};

/***/ },
/* 62 */
/***/ function(module, exports) {

	var hasOwnProperty = {}.hasOwnProperty;
	module.exports = function(it, key){
	  return hasOwnProperty.call(it, key);
	};

/***/ },
/* 63 */
/***/ function(module, exports, __webpack_require__) {

	// to indexed object, toObject with fallback for non-array-like ES3 strings
	var IObject = __webpack_require__(64)
	  , defined = __webpack_require__(66);
	module.exports = function(it){
	  return IObject(defined(it));
	};

/***/ },
/* 64 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for non-array-like ES3 and non-enumerable old V8 strings
	var cof = __webpack_require__(65);
	module.exports = Object('z').propertyIsEnumerable(0) ? Object : function(it){
	  return cof(it) == 'String' ? it.split('') : Object(it);
	};

/***/ },
/* 65 */
/***/ function(module, exports) {

	var toString = {}.toString;

	module.exports = function(it){
	  return toString.call(it).slice(8, -1);
	};

/***/ },
/* 66 */
/***/ function(module, exports) {

	// 7.2.1 RequireObjectCoercible(argument)
	module.exports = function(it){
	  if(it == undefined)throw TypeError("Can't call method on  " + it);
	  return it;
	};

/***/ },
/* 67 */
/***/ function(module, exports, __webpack_require__) {

	// false -> Array#indexOf
	// true  -> Array#includes
	var toIObject = __webpack_require__(63)
	  , toLength  = __webpack_require__(68)
	  , toIndex   = __webpack_require__(70);
	module.exports = function(IS_INCLUDES){
	  return function($this, el, fromIndex){
	    var O      = toIObject($this)
	      , length = toLength(O.length)
	      , index  = toIndex(fromIndex, length)
	      , value;
	    // Array#includes uses SameValueZero equality algorithm
	    if(IS_INCLUDES && el != el)while(length > index){
	      value = O[index++];
	      if(value != value)return true;
	    // Array#toIndex ignores holes, Array#includes - not
	    } else for(;length > index; index++)if(IS_INCLUDES || index in O){
	      if(O[index] === el)return IS_INCLUDES || index || 0;
	    } return !IS_INCLUDES && -1;
	  };
	};

/***/ },
/* 68 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.15 ToLength
	var toInteger = __webpack_require__(69)
	  , min       = Math.min;
	module.exports = function(it){
	  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
	};

/***/ },
/* 69 */
/***/ function(module, exports) {

	// 7.1.4 ToInteger
	var ceil  = Math.ceil
	  , floor = Math.floor;
	module.exports = function(it){
	  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
	};

/***/ },
/* 70 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(69)
	  , max       = Math.max
	  , min       = Math.min;
	module.exports = function(index, length){
	  index = toInteger(index);
	  return index < 0 ? max(index + length, 0) : min(index, length);
	};

/***/ },
/* 71 */
/***/ function(module, exports, __webpack_require__) {

	var shared = __webpack_require__(72)('keys')
	  , uid    = __webpack_require__(73);
	module.exports = function(key){
	  return shared[key] || (shared[key] = uid(key));
	};

/***/ },
/* 72 */
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(45)
	  , SHARED = '__core-js_shared__'
	  , store  = global[SHARED] || (global[SHARED] = {});
	module.exports = function(key){
	  return store[key] || (store[key] = {});
	};

/***/ },
/* 73 */
/***/ function(module, exports) {

	var id = 0
	  , px = Math.random();
	module.exports = function(key){
	  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
	};

/***/ },
/* 74 */
/***/ function(module, exports) {

	// IE 8- don't enum bug keys
	module.exports = (
	  'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'
	).split(',');

/***/ },
/* 75 */
/***/ function(module, exports) {

	exports.f = Object.getOwnPropertySymbols;

/***/ },
/* 76 */
/***/ function(module, exports) {

	exports.f = {}.propertyIsEnumerable;

/***/ },
/* 77 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.13 ToObject(argument)
	var defined = __webpack_require__(66);
	module.exports = function(it){
	  return Object(defined(it));
	};

/***/ },
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */,
/* 94 */,
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */
/***/ function(module, exports) {

	
	var Utils = {
	  dir : 'yjpt',
	  ip : 'http://127.0.0.1:12580/',
	  // ip : 'http://192.168.8.114:12680/',
	  // ip : 'http://192.168.8.6:8180/',
	  // ip : 'http://www.yuertong.com/',

	setOpenUrl : function(context,arr){
	  var bundleUrl = context.bundleUrl;
	  bundleUrl = new String(bundleUrl);
	  var nativeBase;
	  var isAndroidAssets = bundleUrl.indexOf('file://assets/') >= 0;

	  var isiOSAssets = bundleUrl.indexOf('file:///') >= 0 ;//&& bundleUrl.indexOf('WeexDemo.app') > 0;
	  if (isAndroidAssets) {
	    nativeBase = 'file://assets/yjpt/weex/';
	  }
	  else if (isiOSAssets) {
	    // file:///var/mobile/Containers/Bundle/Application/{id}/WeexDemo.app/
	    // file:///Users/{user}/Library/Developer/CoreSimulator/Devices/{id}/data/Containers/Bundle/Application/{id}/WeexDemo.app/
	    nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
	  }
	  else {
	      var host = '192.168.8.114:12680';
	      var matches = /\/\/([^\/]+?)\//.exec(context.bundleUrl);
	      if (matches && matches.length >= 2) {
	          host = matches[1];
	      }
	      nativeBase = 'http://' + host + '/' + Utils.dir + '/weex/';
	  }
	  var h5Base = './index.html?page=./' + Utils.dir + '/weex/';
	  // in Native
	  var base = nativeBase;
	  if (typeof window === 'object') {
	    // in Browser or WebView
	    base = h5Base;
	  }

	  if(Object.prototype.toString.call(arr) === '[object Array]'){//参数是数组类型[{url:'showcase/new-fashion/sub-type'},{url:'showcase/new-fashion/sub-type'}]
	      for (var i=0;i<arr.length;i++) {
	          var obj = arr[i];
	          if (obj.url) {
	              obj.url = base + obj.url + '.js';
	          }
	      }
	  }else if(Object.prototype.toString.call(arr) === '[object String]'){//参数是String类型 'showcase/new-fashion/sub-type'
	   if (arr) {
	          arr = base + arr + '.js';
	      }
	  }else if(Object.prototype.toString.call(arr) === '[object Object]'){//参数是对象类型{url:'showcase/new-fashion/sub-type'}
	      if (arr.url) {
	          arr.url = base + arr.url + '.js';
	      }
	  }
	  return arr;
	},

	/***
	* 改变图片路径
	* arr: 需要改变图片路径的参数
	* imgNameArr:(可选;必须是数组) 对象的key
	*/
	changeImg : function(arr,imgNameArr,subArrName){//{picUrl:'aa.jpg',url:'b.jpg'} ['picUrl','url']
	    if(Object.prototype.toString.call(arr) === '[object Array]'){
	        for(var j =0;j<imgNameArr.length;j++){
	          for (var i=0;i<arr.length;i++) {
	              var obj = arr[i];
	              if(subArrName){
	                var subObj = obj[subArrName];
	                var osubOjb ;
	                for(var k=0;k<subObj.length;k++){
	                  osubOjb = subObj[k];
	                  osubOjb[imgNameArr[j]] = Utils.ip + osubOjb[imgNameArr[j]];
	                }
	              }
	              obj[imgNameArr[j]] = Utils.ip + obj[imgNameArr[j]];
	          }
	        }
	      }else if(Object.prototype.toString.call(arr) === '[object String]'){
	          arr = Utils.ip + arr;
	      }else if(Object.prototype.toString.call(arr) === '[object Object]'){
	          if(Object.prototype.toString.call(imgNameArr) !== '[object Array]'){
	            console.log("必须是数组");
	            return;
	          }
	          for(var i=0;i<imgNameArr.length;i++){
	            arr[imgNameArr[i]] = Utils.ip + arr[imgNameArr[i]] ;
	          }
	      }
	    return arr;
	},

	navigate : {
	  /***
	  * 打开一个新页面 
	  * @params obj 当前页面作业域(传参数时为this)
	  * @params url 页面的地址
	  * @params animate 是否显示动画；值为'true'/'false'
	  * @params callback 回调函数
	  */


	  push : function(obj,url,animate,callback){
	    var params = {
	      'url': url,
	      'animated' : animate,
	    }    
	    var navigator = __weex_require__('@weex-module/navigator');
	    navigator.push(params, function(e) {
	        //callback
	        /* if(typeof callback == 'function')
	         callback();*/
	    });
	    
	  },
	  /***
	  * 关闭当前页面 
	  * @params obj 当前页面作业域(传参数时为this)
	  * @params animate 是否显示动画；值为'true'/'false'
	  * @params callback 回调函数
	  */
	  pop : function(obj,animate,callback){
	    var params = {
	        'animated' : 'true',
	    }
	    // navigator.pop(params, function(e) {
	    //     //callback
	    //     if(typeof callback == 'function')
	    //     callback();
	    // });
		var navigator = __weex_require__('@weex-module/navigator');
	    navigator.pop(params, function(e) {
	        //callback
	        /*if(typeof callback == 'function')
	        callback();*/
	    });
	  }
	},



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
	fetch : function(options){
	    if(!options){
	      console.log("options is not null");
	      return;
	    }
	    if(!options.url || options.url  == ""){
	      console.log("url is not null");
	      return;
	    }
	    var method = "GET";
	    var type = "text";
	    var stream = __weex_require__('@weex-module/stream');
	    var modal = __weex_require__('@weex-module/modal');
	    var headers = {'Content-Type' : 'application/x-www-form-urlencoded'};
	   // var requestUrl = Utils.ip + 'yjpts';
	    var requestUrl = Utils.ip + 'yjpt';
	    // var requestUrl = 'http://192.168.8.19:8080/yjpts';
	    // modal.alert({
	    //           message:JSON.stringify(options),
	    //           okTitle:'好的'
	    //       },function(){
	    //               // self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));
	    //       });

	    stream.fetch({
	        headers : options.headers || headers,
	        method : options.method || method,
	        url : requestUrl +　options.url,
	        type : options.dataType || type,
	        body : options.data
	    }, function(response) {
	          //请求结束
	          // modal.alert({
	          //     message:JSON.stringify(response),
	          //     okTitle:'好的'
	          // },function(){
	          //         // self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));
	          // });
	            //      self.$openURL(Utils.setOpenUrl(self.$getConfig(),'login'));

	          //debugger
	          //console.log("response----"+JSON.stringify(response));
	          if(!response.ok || response.ok == 0){
	            modal.toast({
	              'message': '网络故障，请稍后再试！', 
	              'duration': 1
	            });
	          }else{
	            if(options.success && typeof options.success == 'function'){
	              var data = {
	                status : response.status,
	                headers : response.headers,
	                data : eval("(" + response.data + ")")  // 用于手机 端
	                // data :response.data //  用于PC端
	              }
	              options.success(data);
	            }
	          }
	    },function(response){
	        if(options.complete && typeof options.complete == 'function'){
	          var data = {
	              readyState : response.readyState,
	              status : response.status
	            }
	          options.complete(data);
	        }
	    })//fetch end
	},
	//去除数组中的相同元素[1,1,1,2,3] --->[1,2,3]
	unique : function(arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	    return result;
	},

	/**输出几天前、几分钟前的内容*/
	time : function(beginData){
	  var result;
	  var v = beginData.replace(/-/g,"/");
	  var s = new Date(v);
	  var minute = 1000 * 60;
	  var hour = minute * 60;
	  var day = hour * 24;
	  var halfamonth = day * 15;
	  var month = day * 30;
	  var now = new Date().getTime();
	  var diffValue = now - s.getTime();
	  var monthC =diffValue/month;
	  var weekC =diffValue/(7*day);
	  var dayC =diffValue/day;
	  var hourC =diffValue/hour;
	  var minC =diffValue/minute;
	  if(monthC>12){
	     var s2 = new Date(v);              
	     result = s2.getFullYear()+"年" + (s2.getMonth()+1)+"月"+s2.getDate()+"日"; 
	  }else if(monthC>=1){
	     result= parseInt(monthC) + "个月前";
	  }else if(weekC>=1){
	     result = parseInt(weekC) + "周前";
	  }else if(dayC>=1){
	     result = parseInt(dayC) +"天前";
	  }else if(hourC>=1){
	     result = parseInt(hourC) +"小时前";
	  }else if(minC>=1){
	     result= parseInt(minC) +"分钟前";
	  }else
	   result="刚刚";
	  return result;
	}
	}

	module.exports = Utils;

/***/ },
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	exports.__esModule = true;

	var _iterator = __webpack_require__(103);

	var _iterator2 = _interopRequireDefault(_iterator);

	var _symbol = __webpack_require__(123);

	var _symbol2 = _interopRequireDefault(_symbol);

	var _typeof = typeof _symbol2.default === "function" && typeof _iterator2.default === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof _symbol2.default === "function" && obj.constructor === _symbol2.default && obj !== _symbol2.default.prototype ? "symbol" : typeof obj; };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = typeof _symbol2.default === "function" && _typeof(_iterator2.default) === "symbol" ? function (obj) {
	  return typeof obj === "undefined" ? "undefined" : _typeof(obj);
	} : function (obj) {
	  return obj && typeof _symbol2.default === "function" && obj.constructor === _symbol2.default && obj !== _symbol2.default.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof(obj);
	};

/***/ },
/* 103 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(104), __esModule: true };

/***/ },
/* 104 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(105);
	__webpack_require__(118);
	module.exports = __webpack_require__(122).f('iterator');

/***/ },
/* 105 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $at  = __webpack_require__(106)(true);

	// 21.1.3.27 String.prototype[@@iterator]()
	__webpack_require__(107)(String, 'String', function(iterated){
	  this._t = String(iterated); // target
	  this._i = 0;                // next index
	// 21.1.5.2.1 %StringIteratorPrototype%.next()
	}, function(){
	  var O     = this._t
	    , index = this._i
	    , point;
	  if(index >= O.length)return {value: undefined, done: true};
	  point = $at(O, index);
	  this._i += point.length;
	  return {value: point, done: false};
	});

/***/ },
/* 106 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(69)
	  , defined   = __webpack_require__(66);
	// true  -> String#at
	// false -> String#codePointAt
	module.exports = function(TO_STRING){
	  return function(that, pos){
	    var s = String(defined(that))
	      , i = toInteger(pos)
	      , l = s.length
	      , a, b;
	    if(i < 0 || i >= l)return TO_STRING ? '' : undefined;
	    a = s.charCodeAt(i);
	    return a < 0xd800 || a > 0xdbff || i + 1 === l || (b = s.charCodeAt(i + 1)) < 0xdc00 || b > 0xdfff
	      ? TO_STRING ? s.charAt(i) : a
	      : TO_STRING ? s.slice(i, i + 2) : (a - 0xd800 << 10) + (b - 0xdc00) + 0x10000;
	  };
	};

/***/ },
/* 107 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var LIBRARY        = __webpack_require__(108)
	  , $export        = __webpack_require__(44)
	  , redefine       = __webpack_require__(109)
	  , hide           = __webpack_require__(49)
	  , has            = __webpack_require__(62)
	  , Iterators      = __webpack_require__(110)
	  , $iterCreate    = __webpack_require__(111)
	  , setToStringTag = __webpack_require__(115)
	  , getPrototypeOf = __webpack_require__(117)
	  , ITERATOR       = __webpack_require__(116)('iterator')
	  , BUGGY          = !([].keys && 'next' in [].keys()) // Safari has buggy iterators w/o `next`
	  , FF_ITERATOR    = '@@iterator'
	  , KEYS           = 'keys'
	  , VALUES         = 'values';

	var returnThis = function(){ return this; };

	module.exports = function(Base, NAME, Constructor, next, DEFAULT, IS_SET, FORCED){
	  $iterCreate(Constructor, NAME, next);
	  var getMethod = function(kind){
	    if(!BUGGY && kind in proto)return proto[kind];
	    switch(kind){
	      case KEYS: return function keys(){ return new Constructor(this, kind); };
	      case VALUES: return function values(){ return new Constructor(this, kind); };
	    } return function entries(){ return new Constructor(this, kind); };
	  };
	  var TAG        = NAME + ' Iterator'
	    , DEF_VALUES = DEFAULT == VALUES
	    , VALUES_BUG = false
	    , proto      = Base.prototype
	    , $native    = proto[ITERATOR] || proto[FF_ITERATOR] || DEFAULT && proto[DEFAULT]
	    , $default   = $native || getMethod(DEFAULT)
	    , $entries   = DEFAULT ? !DEF_VALUES ? $default : getMethod('entries') : undefined
	    , $anyNative = NAME == 'Array' ? proto.entries || $native : $native
	    , methods, key, IteratorPrototype;
	  // Fix native
	  if($anyNative){
	    IteratorPrototype = getPrototypeOf($anyNative.call(new Base));
	    if(IteratorPrototype !== Object.prototype){
	      // Set @@toStringTag to native iterators
	      setToStringTag(IteratorPrototype, TAG, true);
	      // fix for some old engines
	      if(!LIBRARY && !has(IteratorPrototype, ITERATOR))hide(IteratorPrototype, ITERATOR, returnThis);
	    }
	  }
	  // fix Array#{values, @@iterator}.name in V8 / FF
	  if(DEF_VALUES && $native && $native.name !== VALUES){
	    VALUES_BUG = true;
	    $default = function values(){ return $native.call(this); };
	  }
	  // Define iterator
	  if((!LIBRARY || FORCED) && (BUGGY || VALUES_BUG || !proto[ITERATOR])){
	    hide(proto, ITERATOR, $default);
	  }
	  // Plug for library
	  Iterators[NAME] = $default;
	  Iterators[TAG]  = returnThis;
	  if(DEFAULT){
	    methods = {
	      values:  DEF_VALUES ? $default : getMethod(VALUES),
	      keys:    IS_SET     ? $default : getMethod(KEYS),
	      entries: $entries
	    };
	    if(FORCED)for(key in methods){
	      if(!(key in proto))redefine(proto, key, methods[key]);
	    } else $export($export.P + $export.F * (BUGGY || VALUES_BUG), NAME, methods);
	  }
	  return methods;
	};

/***/ },
/* 108 */
/***/ function(module, exports) {

	module.exports = true;

/***/ },
/* 109 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(49);

/***/ },
/* 110 */
/***/ function(module, exports) {

	module.exports = {};

/***/ },
/* 111 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var create         = __webpack_require__(112)
	  , descriptor     = __webpack_require__(58)
	  , setToStringTag = __webpack_require__(115)
	  , IteratorPrototype = {};

	// 25.1.2.1.1 %IteratorPrototype%[@@iterator]()
	__webpack_require__(49)(IteratorPrototype, __webpack_require__(116)('iterator'), function(){ return this; });

	module.exports = function(Constructor, NAME, next){
	  Constructor.prototype = create(IteratorPrototype, {next: descriptor(1, next)});
	  setToStringTag(Constructor, NAME + ' Iterator');
	};

/***/ },
/* 112 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.2 / 15.2.3.5 Object.create(O [, Properties])
	var anObject    = __webpack_require__(51)
	  , dPs         = __webpack_require__(113)
	  , enumBugKeys = __webpack_require__(74)
	  , IE_PROTO    = __webpack_require__(71)('IE_PROTO')
	  , Empty       = function(){ /* empty */ }
	  , PROTOTYPE   = 'prototype';

	// Create object with fake `null` prototype: use iframe Object with cleared prototype
	var createDict = function(){
	  // Thrash, waste and sodomy: IE GC bug
	  var iframe = __webpack_require__(56)('iframe')
	    , i      = enumBugKeys.length
	    , lt     = '<'
	    , gt     = '>'
	    , iframeDocument;
	  iframe.style.display = 'none';
	  __webpack_require__(114).appendChild(iframe);
	  iframe.src = 'javascript:'; // eslint-disable-line no-script-url
	  // createDict = iframe.contentWindow.Object;
	  // html.removeChild(iframe);
	  iframeDocument = iframe.contentWindow.document;
	  iframeDocument.open();
	  iframeDocument.write(lt + 'script' + gt + 'document.F=Object' + lt + '/script' + gt);
	  iframeDocument.close();
	  createDict = iframeDocument.F;
	  while(i--)delete createDict[PROTOTYPE][enumBugKeys[i]];
	  return createDict();
	};

	module.exports = Object.create || function create(O, Properties){
	  var result;
	  if(O !== null){
	    Empty[PROTOTYPE] = anObject(O);
	    result = new Empty;
	    Empty[PROTOTYPE] = null;
	    // add "__proto__" for Object.getPrototypeOf polyfill
	    result[IE_PROTO] = O;
	  } else result = createDict();
	  return Properties === undefined ? result : dPs(result, Properties);
	};


/***/ },
/* 113 */
/***/ function(module, exports, __webpack_require__) {

	var dP       = __webpack_require__(50)
	  , anObject = __webpack_require__(51)
	  , getKeys  = __webpack_require__(60);

	module.exports = __webpack_require__(54) ? Object.defineProperties : function defineProperties(O, Properties){
	  anObject(O);
	  var keys   = getKeys(Properties)
	    , length = keys.length
	    , i = 0
	    , P;
	  while(length > i)dP.f(O, P = keys[i++], Properties[P]);
	  return O;
	};

/***/ },
/* 114 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(45).document && document.documentElement;

/***/ },
/* 115 */
/***/ function(module, exports, __webpack_require__) {

	var def = __webpack_require__(50).f
	  , has = __webpack_require__(62)
	  , TAG = __webpack_require__(116)('toStringTag');

	module.exports = function(it, tag, stat){
	  if(it && !has(it = stat ? it : it.prototype, TAG))def(it, TAG, {configurable: true, value: tag});
	};

/***/ },
/* 116 */
/***/ function(module, exports, __webpack_require__) {

	var store      = __webpack_require__(72)('wks')
	  , uid        = __webpack_require__(73)
	  , Symbol     = __webpack_require__(45).Symbol
	  , USE_SYMBOL = typeof Symbol == 'function';

	var $exports = module.exports = function(name){
	  return store[name] || (store[name] =
	    USE_SYMBOL && Symbol[name] || (USE_SYMBOL ? Symbol : uid)('Symbol.' + name));
	};

	$exports.store = store;

/***/ },
/* 117 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.9 / 15.2.3.2 Object.getPrototypeOf(O)
	var has         = __webpack_require__(62)
	  , toObject    = __webpack_require__(77)
	  , IE_PROTO    = __webpack_require__(71)('IE_PROTO')
	  , ObjectProto = Object.prototype;

	module.exports = Object.getPrototypeOf || function(O){
	  O = toObject(O);
	  if(has(O, IE_PROTO))return O[IE_PROTO];
	  if(typeof O.constructor == 'function' && O instanceof O.constructor){
	    return O.constructor.prototype;
	  } return O instanceof Object ? ObjectProto : null;
	};

/***/ },
/* 118 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(119);
	var global        = __webpack_require__(45)
	  , hide          = __webpack_require__(49)
	  , Iterators     = __webpack_require__(110)
	  , TO_STRING_TAG = __webpack_require__(116)('toStringTag');

	for(var collections = ['NodeList', 'DOMTokenList', 'MediaList', 'StyleSheetList', 'CSSRuleList'], i = 0; i < 5; i++){
	  var NAME       = collections[i]
	    , Collection = global[NAME]
	    , proto      = Collection && Collection.prototype;
	  if(proto && !proto[TO_STRING_TAG])hide(proto, TO_STRING_TAG, NAME);
	  Iterators[NAME] = Iterators.Array;
	}

/***/ },
/* 119 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var addToUnscopables = __webpack_require__(120)
	  , step             = __webpack_require__(121)
	  , Iterators        = __webpack_require__(110)
	  , toIObject        = __webpack_require__(63);

	// 22.1.3.4 Array.prototype.entries()
	// 22.1.3.13 Array.prototype.keys()
	// 22.1.3.29 Array.prototype.values()
	// 22.1.3.30 Array.prototype[@@iterator]()
	module.exports = __webpack_require__(107)(Array, 'Array', function(iterated, kind){
	  this._t = toIObject(iterated); // target
	  this._i = 0;                   // next index
	  this._k = kind;                // kind
	// 22.1.5.2.1 %ArrayIteratorPrototype%.next()
	}, function(){
	  var O     = this._t
	    , kind  = this._k
	    , index = this._i++;
	  if(!O || index >= O.length){
	    this._t = undefined;
	    return step(1);
	  }
	  if(kind == 'keys'  )return step(0, index);
	  if(kind == 'values')return step(0, O[index]);
	  return step(0, [index, O[index]]);
	}, 'values');

	// argumentsList[@@iterator] is %ArrayProto_values% (9.4.4.6, 9.4.4.7)
	Iterators.Arguments = Iterators.Array;

	addToUnscopables('keys');
	addToUnscopables('values');
	addToUnscopables('entries');

/***/ },
/* 120 */
/***/ function(module, exports) {

	module.exports = function(){ /* empty */ };

/***/ },
/* 121 */
/***/ function(module, exports) {

	module.exports = function(done, value){
	  return {value: value, done: !!done};
	};

/***/ },
/* 122 */
/***/ function(module, exports, __webpack_require__) {

	exports.f = __webpack_require__(116);

/***/ },
/* 123 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(124), __esModule: true };

/***/ },
/* 124 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(125);
	__webpack_require__(134);
	__webpack_require__(135);
	__webpack_require__(136);
	module.exports = __webpack_require__(46).Symbol;

/***/ },
/* 125 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// ECMAScript 6 symbols shim
	var global         = __webpack_require__(45)
	  , has            = __webpack_require__(62)
	  , DESCRIPTORS    = __webpack_require__(54)
	  , $export        = __webpack_require__(44)
	  , redefine       = __webpack_require__(109)
	  , META           = __webpack_require__(126).KEY
	  , $fails         = __webpack_require__(55)
	  , shared         = __webpack_require__(72)
	  , setToStringTag = __webpack_require__(115)
	  , uid            = __webpack_require__(73)
	  , wks            = __webpack_require__(116)
	  , wksExt         = __webpack_require__(122)
	  , wksDefine      = __webpack_require__(127)
	  , keyOf          = __webpack_require__(128)
	  , enumKeys       = __webpack_require__(129)
	  , isArray        = __webpack_require__(130)
	  , anObject       = __webpack_require__(51)
	  , toIObject      = __webpack_require__(63)
	  , toPrimitive    = __webpack_require__(57)
	  , createDesc     = __webpack_require__(58)
	  , _create        = __webpack_require__(112)
	  , gOPNExt        = __webpack_require__(131)
	  , $GOPD          = __webpack_require__(133)
	  , $DP            = __webpack_require__(50)
	  , $keys          = __webpack_require__(60)
	  , gOPD           = $GOPD.f
	  , dP             = $DP.f
	  , gOPN           = gOPNExt.f
	  , $Symbol        = global.Symbol
	  , $JSON          = global.JSON
	  , _stringify     = $JSON && $JSON.stringify
	  , PROTOTYPE      = 'prototype'
	  , HIDDEN         = wks('_hidden')
	  , TO_PRIMITIVE   = wks('toPrimitive')
	  , isEnum         = {}.propertyIsEnumerable
	  , SymbolRegistry = shared('symbol-registry')
	  , AllSymbols     = shared('symbols')
	  , OPSymbols      = shared('op-symbols')
	  , ObjectProto    = Object[PROTOTYPE]
	  , USE_NATIVE     = typeof $Symbol == 'function'
	  , QObject        = global.QObject;
	// Don't use setters in Qt Script, https://github.com/zloirock/core-js/issues/173
	var setter = !QObject || !QObject[PROTOTYPE] || !QObject[PROTOTYPE].findChild;

	// fallback for old Android, https://code.google.com/p/v8/issues/detail?id=687
	var setSymbolDesc = DESCRIPTORS && $fails(function(){
	  return _create(dP({}, 'a', {
	    get: function(){ return dP(this, 'a', {value: 7}).a; }
	  })).a != 7;
	}) ? function(it, key, D){
	  var protoDesc = gOPD(ObjectProto, key);
	  if(protoDesc)delete ObjectProto[key];
	  dP(it, key, D);
	  if(protoDesc && it !== ObjectProto)dP(ObjectProto, key, protoDesc);
	} : dP;

	var wrap = function(tag){
	  var sym = AllSymbols[tag] = _create($Symbol[PROTOTYPE]);
	  sym._k = tag;
	  return sym;
	};

	var isSymbol = USE_NATIVE && typeof $Symbol.iterator == 'symbol' ? function(it){
	  return typeof it == 'symbol';
	} : function(it){
	  return it instanceof $Symbol;
	};

	var $defineProperty = function defineProperty(it, key, D){
	  if(it === ObjectProto)$defineProperty(OPSymbols, key, D);
	  anObject(it);
	  key = toPrimitive(key, true);
	  anObject(D);
	  if(has(AllSymbols, key)){
	    if(!D.enumerable){
	      if(!has(it, HIDDEN))dP(it, HIDDEN, createDesc(1, {}));
	      it[HIDDEN][key] = true;
	    } else {
	      if(has(it, HIDDEN) && it[HIDDEN][key])it[HIDDEN][key] = false;
	      D = _create(D, {enumerable: createDesc(0, false)});
	    } return setSymbolDesc(it, key, D);
	  } return dP(it, key, D);
	};
	var $defineProperties = function defineProperties(it, P){
	  anObject(it);
	  var keys = enumKeys(P = toIObject(P))
	    , i    = 0
	    , l = keys.length
	    , key;
	  while(l > i)$defineProperty(it, key = keys[i++], P[key]);
	  return it;
	};
	var $create = function create(it, P){
	  return P === undefined ? _create(it) : $defineProperties(_create(it), P);
	};
	var $propertyIsEnumerable = function propertyIsEnumerable(key){
	  var E = isEnum.call(this, key = toPrimitive(key, true));
	  if(this === ObjectProto && has(AllSymbols, key) && !has(OPSymbols, key))return false;
	  return E || !has(this, key) || !has(AllSymbols, key) || has(this, HIDDEN) && this[HIDDEN][key] ? E : true;
	};
	var $getOwnPropertyDescriptor = function getOwnPropertyDescriptor(it, key){
	  it  = toIObject(it);
	  key = toPrimitive(key, true);
	  if(it === ObjectProto && has(AllSymbols, key) && !has(OPSymbols, key))return;
	  var D = gOPD(it, key);
	  if(D && has(AllSymbols, key) && !(has(it, HIDDEN) && it[HIDDEN][key]))D.enumerable = true;
	  return D;
	};
	var $getOwnPropertyNames = function getOwnPropertyNames(it){
	  var names  = gOPN(toIObject(it))
	    , result = []
	    , i      = 0
	    , key;
	  while(names.length > i){
	    if(!has(AllSymbols, key = names[i++]) && key != HIDDEN && key != META)result.push(key);
	  } return result;
	};
	var $getOwnPropertySymbols = function getOwnPropertySymbols(it){
	  var IS_OP  = it === ObjectProto
	    , names  = gOPN(IS_OP ? OPSymbols : toIObject(it))
	    , result = []
	    , i      = 0
	    , key;
	  while(names.length > i){
	    if(has(AllSymbols, key = names[i++]) && (IS_OP ? has(ObjectProto, key) : true))result.push(AllSymbols[key]);
	  } return result;
	};

	// 19.4.1.1 Symbol([description])
	if(!USE_NATIVE){
	  $Symbol = function Symbol(){
	    if(this instanceof $Symbol)throw TypeError('Symbol is not a constructor!');
	    var tag = uid(arguments.length > 0 ? arguments[0] : undefined);
	    var $set = function(value){
	      if(this === ObjectProto)$set.call(OPSymbols, value);
	      if(has(this, HIDDEN) && has(this[HIDDEN], tag))this[HIDDEN][tag] = false;
	      setSymbolDesc(this, tag, createDesc(1, value));
	    };
	    if(DESCRIPTORS && setter)setSymbolDesc(ObjectProto, tag, {configurable: true, set: $set});
	    return wrap(tag);
	  };
	  redefine($Symbol[PROTOTYPE], 'toString', function toString(){
	    return this._k;
	  });

	  $GOPD.f = $getOwnPropertyDescriptor;
	  $DP.f   = $defineProperty;
	  __webpack_require__(132).f = gOPNExt.f = $getOwnPropertyNames;
	  __webpack_require__(76).f  = $propertyIsEnumerable;
	  __webpack_require__(75).f = $getOwnPropertySymbols;

	  if(DESCRIPTORS && !__webpack_require__(108)){
	    redefine(ObjectProto, 'propertyIsEnumerable', $propertyIsEnumerable, true);
	  }

	  wksExt.f = function(name){
	    return wrap(wks(name));
	  }
	}

	$export($export.G + $export.W + $export.F * !USE_NATIVE, {Symbol: $Symbol});

	for(var symbols = (
	  // 19.4.2.2, 19.4.2.3, 19.4.2.4, 19.4.2.6, 19.4.2.8, 19.4.2.9, 19.4.2.10, 19.4.2.11, 19.4.2.12, 19.4.2.13, 19.4.2.14
	  'hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables'
	).split(','), i = 0; symbols.length > i; )wks(symbols[i++]);

	for(var symbols = $keys(wks.store), i = 0; symbols.length > i; )wksDefine(symbols[i++]);

	$export($export.S + $export.F * !USE_NATIVE, 'Symbol', {
	  // 19.4.2.1 Symbol.for(key)
	  'for': function(key){
	    return has(SymbolRegistry, key += '')
	      ? SymbolRegistry[key]
	      : SymbolRegistry[key] = $Symbol(key);
	  },
	  // 19.4.2.5 Symbol.keyFor(sym)
	  keyFor: function keyFor(key){
	    if(isSymbol(key))return keyOf(SymbolRegistry, key);
	    throw TypeError(key + ' is not a symbol!');
	  },
	  useSetter: function(){ setter = true; },
	  useSimple: function(){ setter = false; }
	});

	$export($export.S + $export.F * !USE_NATIVE, 'Object', {
	  // 19.1.2.2 Object.create(O [, Properties])
	  create: $create,
	  // 19.1.2.4 Object.defineProperty(O, P, Attributes)
	  defineProperty: $defineProperty,
	  // 19.1.2.3 Object.defineProperties(O, Properties)
	  defineProperties: $defineProperties,
	  // 19.1.2.6 Object.getOwnPropertyDescriptor(O, P)
	  getOwnPropertyDescriptor: $getOwnPropertyDescriptor,
	  // 19.1.2.7 Object.getOwnPropertyNames(O)
	  getOwnPropertyNames: $getOwnPropertyNames,
	  // 19.1.2.8 Object.getOwnPropertySymbols(O)
	  getOwnPropertySymbols: $getOwnPropertySymbols
	});

	// 24.3.2 JSON.stringify(value [, replacer [, space]])
	$JSON && $export($export.S + $export.F * (!USE_NATIVE || $fails(function(){
	  var S = $Symbol();
	  // MS Edge converts symbol values to JSON as {}
	  // WebKit converts symbol values to JSON as null
	  // V8 throws on boxed symbols
	  return _stringify([S]) != '[null]' || _stringify({a: S}) != '{}' || _stringify(Object(S)) != '{}';
	})), 'JSON', {
	  stringify: function stringify(it){
	    if(it === undefined || isSymbol(it))return; // IE8 returns string on undefined
	    var args = [it]
	      , i    = 1
	      , replacer, $replacer;
	    while(arguments.length > i)args.push(arguments[i++]);
	    replacer = args[1];
	    if(typeof replacer == 'function')$replacer = replacer;
	    if($replacer || !isArray(replacer))replacer = function(key, value){
	      if($replacer)value = $replacer.call(this, key, value);
	      if(!isSymbol(value))return value;
	    };
	    args[1] = replacer;
	    return _stringify.apply($JSON, args);
	  }
	});

	// 19.4.3.4 Symbol.prototype[@@toPrimitive](hint)
	$Symbol[PROTOTYPE][TO_PRIMITIVE] || __webpack_require__(49)($Symbol[PROTOTYPE], TO_PRIMITIVE, $Symbol[PROTOTYPE].valueOf);
	// 19.4.3.5 Symbol.prototype[@@toStringTag]
	setToStringTag($Symbol, 'Symbol');
	// 20.2.1.9 Math[@@toStringTag]
	setToStringTag(Math, 'Math', true);
	// 24.3.3 JSON[@@toStringTag]
	setToStringTag(global.JSON, 'JSON', true);

/***/ },
/* 126 */
/***/ function(module, exports, __webpack_require__) {

	var META     = __webpack_require__(73)('meta')
	  , isObject = __webpack_require__(52)
	  , has      = __webpack_require__(62)
	  , setDesc  = __webpack_require__(50).f
	  , id       = 0;
	var isExtensible = Object.isExtensible || function(){
	  return true;
	};
	var FREEZE = !__webpack_require__(55)(function(){
	  return isExtensible(Object.preventExtensions({}));
	});
	var setMeta = function(it){
	  setDesc(it, META, {value: {
	    i: 'O' + ++id, // object ID
	    w: {}          // weak collections IDs
	  }});
	};
	var fastKey = function(it, create){
	  // return primitive with prefix
	  if(!isObject(it))return typeof it == 'symbol' ? it : (typeof it == 'string' ? 'S' : 'P') + it;
	  if(!has(it, META)){
	    // can't set metadata to uncaught frozen object
	    if(!isExtensible(it))return 'F';
	    // not necessary to add metadata
	    if(!create)return 'E';
	    // add missing metadata
	    setMeta(it);
	  // return object ID
	  } return it[META].i;
	};
	var getWeak = function(it, create){
	  if(!has(it, META)){
	    // can't set metadata to uncaught frozen object
	    if(!isExtensible(it))return true;
	    // not necessary to add metadata
	    if(!create)return false;
	    // add missing metadata
	    setMeta(it);
	  // return hash weak collections IDs
	  } return it[META].w;
	};
	// add metadata on freeze-family methods calling
	var onFreeze = function(it){
	  if(FREEZE && meta.NEED && isExtensible(it) && !has(it, META))setMeta(it);
	  return it;
	};
	var meta = module.exports = {
	  KEY:      META,
	  NEED:     false,
	  fastKey:  fastKey,
	  getWeak:  getWeak,
	  onFreeze: onFreeze
	};

/***/ },
/* 127 */
/***/ function(module, exports, __webpack_require__) {

	var global         = __webpack_require__(45)
	  , core           = __webpack_require__(46)
	  , LIBRARY        = __webpack_require__(108)
	  , wksExt         = __webpack_require__(122)
	  , defineProperty = __webpack_require__(50).f;
	module.exports = function(name){
	  var $Symbol = core.Symbol || (core.Symbol = LIBRARY ? {} : global.Symbol || {});
	  if(name.charAt(0) != '_' && !(name in $Symbol))defineProperty($Symbol, name, {value: wksExt.f(name)});
	};

/***/ },
/* 128 */
/***/ function(module, exports, __webpack_require__) {

	var getKeys   = __webpack_require__(60)
	  , toIObject = __webpack_require__(63);
	module.exports = function(object, el){
	  var O      = toIObject(object)
	    , keys   = getKeys(O)
	    , length = keys.length
	    , index  = 0
	    , key;
	  while(length > index)if(O[key = keys[index++]] === el)return key;
	};

/***/ },
/* 129 */
/***/ function(module, exports, __webpack_require__) {

	// all enumerable object keys, includes symbols
	var getKeys = __webpack_require__(60)
	  , gOPS    = __webpack_require__(75)
	  , pIE     = __webpack_require__(76);
	module.exports = function(it){
	  var result     = getKeys(it)
	    , getSymbols = gOPS.f;
	  if(getSymbols){
	    var symbols = getSymbols(it)
	      , isEnum  = pIE.f
	      , i       = 0
	      , key;
	    while(symbols.length > i)if(isEnum.call(it, key = symbols[i++]))result.push(key);
	  } return result;
	};

/***/ },
/* 130 */
/***/ function(module, exports, __webpack_require__) {

	// 7.2.2 IsArray(argument)
	var cof = __webpack_require__(65);
	module.exports = Array.isArray || function isArray(arg){
	  return cof(arg) == 'Array';
	};

/***/ },
/* 131 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for IE11 buggy Object.getOwnPropertyNames with iframe and window
	var toIObject = __webpack_require__(63)
	  , gOPN      = __webpack_require__(132).f
	  , toString  = {}.toString;

	var windowNames = typeof window == 'object' && window && Object.getOwnPropertyNames
	  ? Object.getOwnPropertyNames(window) : [];

	var getWindowNames = function(it){
	  try {
	    return gOPN(it);
	  } catch(e){
	    return windowNames.slice();
	  }
	};

	module.exports.f = function getOwnPropertyNames(it){
	  return windowNames && toString.call(it) == '[object Window]' ? getWindowNames(it) : gOPN(toIObject(it));
	};


/***/ },
/* 132 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.7 / 15.2.3.4 Object.getOwnPropertyNames(O)
	var $keys      = __webpack_require__(61)
	  , hiddenKeys = __webpack_require__(74).concat('length', 'prototype');

	exports.f = Object.getOwnPropertyNames || function getOwnPropertyNames(O){
	  return $keys(O, hiddenKeys);
	};

/***/ },
/* 133 */
/***/ function(module, exports, __webpack_require__) {

	var pIE            = __webpack_require__(76)
	  , createDesc     = __webpack_require__(58)
	  , toIObject      = __webpack_require__(63)
	  , toPrimitive    = __webpack_require__(57)
	  , has            = __webpack_require__(62)
	  , IE8_DOM_DEFINE = __webpack_require__(53)
	  , gOPD           = Object.getOwnPropertyDescriptor;

	exports.f = __webpack_require__(54) ? gOPD : function getOwnPropertyDescriptor(O, P){
	  O = toIObject(O);
	  P = toPrimitive(P, true);
	  if(IE8_DOM_DEFINE)try {
	    return gOPD(O, P);
	  } catch(e){ /* empty */ }
	  if(has(O, P))return createDesc(!pIE.f.call(O, P), O[P]);
	};

/***/ },
/* 134 */
/***/ function(module, exports) {

	

/***/ },
/* 135 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(127)('asyncIterator');

/***/ },
/* 136 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(127)('observable');

/***/ },
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "class_list"
	  ],
	  "style": {
	    "position": "absolute",
	    "zIndex": 999999999999999,
	    "left": 0,
	    "bottom": 0,
	    "right": 0,
	    "top": 0,
	    "backgroundColor": "rgba(0,0,0,0.7)"
	  },
	  "children": [
	    {
	      "type": "div",
	      "events": {
	        "click": "confirm"
	      },
	      "style": {
	        "flexDirection": "row",
	        "padding": 30,
	        "alignItems": "center",
	        "backgroundColor": "#ebebeb",
	        "borderBottomWidth": 2,
	        "borderBottomStyle": "solid",
	        "borderBottomColor": "#dddddd",
	        "paddingTop": 40,
	        "paddingBottom": 40,
	        "fontSize": 34,
	        "position": "absolute",
	        "left": 0,
	        "right": 0,
	        "top": 0,
	        "fontWeight": "bold",
	        "zIndex": 9999999
	      },
	      "children": [
	        {
	          "type": "text",
	          "style": {
	            "flex": 3,
	            "textAlign": "left"
	          },
	          "attr": {
	            "value": "请选择班级"
	          }
	        },
	        {
	          "type": "text",
	          "style": {
	            "flex": 1,
	            "textAlign": "right",
	            "padding": 30,
	            "paddingTop": 6,
	            "paddingBottom": 6
	          },
	          "attr": {
	            "value": "确定"
	          }
	        }
	      ]
	    },
	    {
	      "type": "div",
	      "style": {
	        "position": "absolute",
	        "left": 0,
	        "right": 0,
	        "top": 130,
	        "bottom": 0
	      },
	      "children": [
	        {
	          "type": "scroller",
	          "children": [
	            {
	              "type": "div",
	              "classList": [
	                "schools"
	              ],
	              "repeat": function () {return this.classList},
	              "events": {
	                "click": function ($event) {this.selectClass(this.$index,$event)}
	              },
	              "id": "sb$index",
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "school_name"
	                  ],
	                  "attr": {
	                    "value": function () {return (this.class_name) + ' (' + (this.grade_id) + ')'}
	                  }
	                },
	                {
	                  "type": "image",
	                  "classList": [
	                    "blank_img"
	                  ],
	                  "attr": {
	                    "src": function () {return this.imgUrl}
	                  }
	                }
	              ]
	            }
	          ]
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 181 */
/***/ function(module, exports) {

	module.exports = {
	  "schools": {
	    "flexDirection": "row",
	    "padding": 30,
	    "alignItems": "center",
	    "backgroundColor": "#ffffff",
	    "borderBottomWidth": 1,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dddddd",
	    "paddingTop": 40,
	    "paddingBottom": 40,
	    "fontSize": 34
	  },
	  "blank_img": {
	    "height": 60,
	    "width": 60
	  },
	  "school_name": {
	    "flex": 4,
	    "fontSize": 34
	  }
	}

/***/ },
/* 182 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _typeof2 = __webpack_require__(102);

	var _typeof3 = _interopRequireDefault(_typeof2);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var Utils = __webpack_require__(98);
	var modal = __weex_require__('@weex-module/modal');
	var storage = __weex_require__('@weex-module/storage');
	module.exports = {
	    data: function () {return {
	        classList: [],
	        images: {
	            leftItemImg: 'yjpt/images/id_right_bg.png',
	            qrImg: 'yjpt/images/qr_img.png',
	            arrowRight: 'yjpt/images/arrow_right2.png',
	            blankRadio: 'yjpt/images/blank-checkbox.png',
	            selectRadio: 'yjpt/images/select-checkbox.png'
	        },
	        classIds: [],
	        showClass: 0,
	        class: []
	    }},
	    created: function created() {
	        Utils.changeImg(this.images, ['leftItemImg', 'qrImg', 'arrowRight', 'blankRadio', 'selectRadio']);
	        this.$on('naviBar.leftItem.click', function (e) {
	            Utils.navigate.pop(this, 'true');
	        });

	        var bundleUrl = this.$getConfig().bundleUrl;
	        console.log('hit', bundleUrl);
	        var nativeBase;
	        var isAndroidAssets = bundleUrl.indexOf('file://assets/') >= 0;
	        var isiOSAssets = bundleUrl.indexOf('file:///') >= 0;
	        if (isAndroidAssets) {
	            nativeBase = 'file://assets/yjpt/weex/';
	        } else if (isiOSAssets) {
	            nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
	        } else {
	            var host = 'localhost:12580';
	            var matches = /\/\/([^\/]+?)\//.exec(this.$getConfig().bundleUrl);
	            if (matches && matches.length >= 2) {
	                host = matches[1];
	            }
	            nativeBase = 'http://' + host + '/' + this.dir + '/weex/';
	        }
	        var h5Base = './index.html?page=./' + this.dir + '/weex/';

	        var base = nativeBase;
	        if ((typeof window === 'undefined' ? 'undefined' : (0, _typeof3.default)(window)) === 'object') {
	            base = h5Base;
	        }

	        var self = this;
	        storage.getItem('classList', function (e) {
	            self.classList = JSON.parse(e.data);
	            self.showClass = 1;
	        });
	    },

	    methods: {
	        selectClass: function selectClass(index) {
	            var self = this;

	            if (self.classList[index].imgUrl == self.images.blankRadio) {
	                self.classList[index].imgUrl = self.images.selectRadio;
	            } else {
	                self.classList[index].imgUrl = self.images.blankRadio;
	            }
	        },
	        confirm: function confirm() {
	            var self = this;
	            self.showClass = 0;
	            self.classIds.splice(0, self.classIds.length);
	            self.class.splice(0, self.class.length);
	            for (var i = 0; i < self.classList.length; i++) {
	                if (self.classList[i].imgUrl == self.images.selectRadio) {
	                    self.classIds.push(self.classList[i].id);
	                    self.class.push(self.classList[i].class_name);
	                }
	            }

	            self.$dispatch('showClass', 0);
	            if (self.classIds.length > 0) {
	                self.$dispatch('classIds', self.classIds);
	                self.$dispatch('class', self.class);
	            }
	        }
	    }
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);