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

	var __weex_template__ = __webpack_require__(225)
	var __weex_style__ = __webpack_require__(226)
	var __weex_script__ = __webpack_require__(227)

	__weex_define__('@weex-component/4425ddeb8b5fee137f60d75398875f75', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/4425ddeb8b5fee137f60d75398875f75',undefined,undefined)

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
/* 17 */
/***/ function(module, exports) {

	// 7.1.4 ToInteger
	var ceil  = Math.ceil
	  , floor = Math.floor;
	module.exports = function(it){
	  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
	};

/***/ },
/* 18 */
/***/ function(module, exports) {

	// 7.2.1 RequireObjectCoercible(argument)
	module.exports = function(it){
	  if(it == undefined)throw TypeError("Can't call method on  " + it);
	  return it;
	};

/***/ },
/* 19 */,
/* 20 */,
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(22)
	  , core      = __webpack_require__(23)
	  , ctx       = __webpack_require__(24)
	  , hide      = __webpack_require__(26)
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
/* 22 */
/***/ function(module, exports) {

	// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
	var global = module.exports = typeof window != 'undefined' && window.Math == Math
	  ? window : typeof self != 'undefined' && self.Math == Math ? self : Function('return this')();
	if(typeof __g == 'number')__g = global; // eslint-disable-line no-undef

/***/ },
/* 23 */
/***/ function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ },
/* 24 */
/***/ function(module, exports, __webpack_require__) {

	// optional / simple context binding
	var aFunction = __webpack_require__(25);
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
/* 25 */
/***/ function(module, exports) {

	module.exports = function(it){
	  if(typeof it != 'function')throw TypeError(it + ' is not a function!');
	  return it;
	};

/***/ },
/* 26 */
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(27)
	  , createDesc = __webpack_require__(35);
	module.exports = __webpack_require__(31) ? function(object, key, value){
	  return dP.f(object, key, createDesc(1, value));
	} : function(object, key, value){
	  object[key] = value;
	  return object;
	};

/***/ },
/* 27 */
/***/ function(module, exports, __webpack_require__) {

	var anObject       = __webpack_require__(28)
	  , IE8_DOM_DEFINE = __webpack_require__(30)
	  , toPrimitive    = __webpack_require__(34)
	  , dP             = Object.defineProperty;

	exports.f = __webpack_require__(31) ? Object.defineProperty : function defineProperty(O, P, Attributes){
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
/* 28 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(29);
	module.exports = function(it){
	  if(!isObject(it))throw TypeError(it + ' is not an object!');
	  return it;
	};

/***/ },
/* 29 */
/***/ function(module, exports) {

	module.exports = function(it){
	  return typeof it === 'object' ? it !== null : typeof it === 'function';
	};

/***/ },
/* 30 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = !__webpack_require__(31) && !__webpack_require__(32)(function(){
	  return Object.defineProperty(__webpack_require__(33)('div'), 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 31 */
/***/ function(module, exports, __webpack_require__) {

	// Thank's IE8 for his funny defineProperty
	module.exports = !__webpack_require__(32)(function(){
	  return Object.defineProperty({}, 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 32 */
/***/ function(module, exports) {

	module.exports = function(exec){
	  try {
	    return !!exec();
	  } catch(e){
	    return true;
	  }
	};

/***/ },
/* 33 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(29)
	  , document = __webpack_require__(22).document
	  // in old IE typeof document.createElement is 'object'
	  , is = isObject(document) && isObject(document.createElement);
	module.exports = function(it){
	  return is ? document.createElement(it) : {};
	};

/***/ },
/* 34 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.1 ToPrimitive(input [, PreferredType])
	var isObject = __webpack_require__(29);
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
/* 35 */
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
/* 36 */,
/* 37 */
/***/ function(module, exports) {

	var hasOwnProperty = {}.hasOwnProperty;
	module.exports = function(it, key){
	  return hasOwnProperty.call(it, key);
	};

/***/ },
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 / 15.2.3.14 Object.keys(O)
	var $keys       = __webpack_require__(43)
	  , enumBugKeys = __webpack_require__(53);

	module.exports = Object.keys || function keys(O){
	  return $keys(O, enumBugKeys);
	};

/***/ },
/* 43 */
/***/ function(module, exports, __webpack_require__) {

	var has          = __webpack_require__(37)
	  , toIObject    = __webpack_require__(44)
	  , arrayIndexOf = __webpack_require__(47)(false)
	  , IE_PROTO     = __webpack_require__(50)('IE_PROTO');

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
/* 44 */
/***/ function(module, exports, __webpack_require__) {

	// to indexed object, toObject with fallback for non-array-like ES3 strings
	var IObject = __webpack_require__(45)
	  , defined = __webpack_require__(18);
	module.exports = function(it){
	  return IObject(defined(it));
	};

/***/ },
/* 45 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for non-array-like ES3 and non-enumerable old V8 strings
	var cof = __webpack_require__(46);
	module.exports = Object('z').propertyIsEnumerable(0) ? Object : function(it){
	  return cof(it) == 'String' ? it.split('') : Object(it);
	};

/***/ },
/* 46 */
/***/ function(module, exports) {

	var toString = {}.toString;

	module.exports = function(it){
	  return toString.call(it).slice(8, -1);
	};

/***/ },
/* 47 */
/***/ function(module, exports, __webpack_require__) {

	// false -> Array#indexOf
	// true  -> Array#includes
	var toIObject = __webpack_require__(44)
	  , toLength  = __webpack_require__(48)
	  , toIndex   = __webpack_require__(49);
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
/* 48 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.15 ToLength
	var toInteger = __webpack_require__(17)
	  , min       = Math.min;
	module.exports = function(it){
	  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
	};

/***/ },
/* 49 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(17)
	  , max       = Math.max
	  , min       = Math.min;
	module.exports = function(index, length){
	  index = toInteger(index);
	  return index < 0 ? max(index + length, 0) : min(index, length);
	};

/***/ },
/* 50 */
/***/ function(module, exports, __webpack_require__) {

	var shared = __webpack_require__(51)('keys')
	  , uid    = __webpack_require__(52);
	module.exports = function(key){
	  return shared[key] || (shared[key] = uid(key));
	};

/***/ },
/* 51 */
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(22)
	  , SHARED = '__core-js_shared__'
	  , store  = global[SHARED] || (global[SHARED] = {});
	module.exports = function(key){
	  return store[key] || (store[key] = {});
	};

/***/ },
/* 52 */
/***/ function(module, exports) {

	var id = 0
	  , px = Math.random();
	module.exports = function(key){
	  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
	};

/***/ },
/* 53 */
/***/ function(module, exports) {

	// IE 8- don't enum bug keys
	module.exports = (
	  'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'
	).split(',');

/***/ },
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.13 ToObject(argument)
	var defined = __webpack_require__(18);
	module.exports = function(it){
	  return Object(defined(it));
	};

/***/ },
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */,
/* 68 */,
/* 69 */,
/* 70 */,
/* 71 */
/***/ function(module, exports) {

	exports.f = Object.getOwnPropertySymbols;

/***/ },
/* 72 */
/***/ function(module, exports) {

	exports.f = {}.propertyIsEnumerable;

/***/ },
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(81);
	__webpack_require__(85);
	__webpack_require__(89);
	__webpack_require__(93);
	__webpack_require__(97);
	__webpack_require__(101);
	__webpack_require__(109);
	__webpack_require__(113);
	__webpack_require__(117);
	__webpack_require__(121);
	__webpack_require__(122);


/***/ },
/* 81 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(82)
	var __weex_style__ = __webpack_require__(83)
	var __weex_script__ = __webpack_require__(84)

	__weex_define__('@weex-component/wxc-button', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 82 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": function () {return ['btn', 'btn-' + (this.type), 'btn-sz-' + (this.size)]},
	  "children": [
	    {
	      "type": "text",
	      "classList": function () {return ['btn-txt', 'btn-txt-' + (this.type), 'btn-txt-sz-' + (this.size)]},
	      "attr": {
	        "value": function () {return this.value}
	      }
	    }
	  ]
	}

/***/ },
/* 83 */
/***/ function(module, exports) {

	module.exports = {
	  "btn": {
	    "marginBottom": 0,
	    "alignItems": "center",
	    "justifyContent": "center",
	    "borderWidth": 1,
	    "borderStyle": "solid",
	    "borderColor": "#333333"
	  },
	  "btn-default": {
	    "color": "rgb(51,51,51)"
	  },
	  "btn-primary": {
	    "backgroundColor": "rgb(40,96,144)",
	    "borderColor": "rgb(40,96,144)"
	  },
	  "btn-success": {
	    "backgroundColor": "rgb(92,184,92)",
	    "borderColor": "rgb(76,174,76)"
	  },
	  "btn-info": {
	    "backgroundColor": "rgb(91,192,222)",
	    "borderColor": "rgb(70,184,218)"
	  },
	  "btn-warning": {
	    "backgroundColor": "rgb(240,173,78)",
	    "borderColor": "rgb(238,162,54)"
	  },
	  "btn-danger": {
	    "backgroundColor": "rgb(217,83,79)",
	    "borderColor": "rgb(212,63,58)"
	  },
	  "btn-link": {
	    "borderColor": "rgba(0,0,0,0)",
	    "borderRadius": 0
	  },
	  "btn-txt-default": {
	    "color": "rgb(51,51,51)"
	  },
	  "btn-txt-primary": {
	    "color": "rgb(255,255,255)"
	  },
	  "btn-txt-success": {
	    "color": "rgb(255,255,255)"
	  },
	  "btn-txt-info": {
	    "color": "rgb(255,255,255)"
	  },
	  "btn-txt-warning": {
	    "color": "rgb(255,255,255)"
	  },
	  "btn-txt-danger": {
	    "color": "rgb(255,255,255)"
	  },
	  "btn-txt-link": {
	    "color": "rgb(51,122,183)"
	  },
	  "btn-sz-large": {
	    "width": 300,
	    "height": 100,
	    "paddingTop": 25,
	    "paddingBottom": 25,
	    "paddingLeft": 40,
	    "paddingRight": 40,
	    "borderRadius": 15
	  },
	  "btn-sz-middle": {
	    "width": 240,
	    "height": 80,
	    "paddingTop": 15,
	    "paddingBottom": 15,
	    "paddingLeft": 30,
	    "paddingRight": 30,
	    "borderRadius": 10
	  },
	  "btn-sz-small": {
	    "width": 170,
	    "height": 60,
	    "paddingTop": 12,
	    "paddingBottom": 12,
	    "paddingLeft": 25,
	    "paddingRight": 25,
	    "borderRadius": 7
	  },
	  "btn-txt-sz-large": {
	    "fontSize": 45
	  },
	  "btn-txt-sz-middle": {
	    "fontSize": 35
	  },
	  "btn-txt-sz-small": {
	    "fontSize": 30
	  }
	}

/***/ },
/* 84 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    type: 'default',
	    size: 'large',
	    value: ''
	  }},
	  methods: {}
	};}
	/* generated by weex-loader */


/***/ },
/* 85 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(86)
	var __weex_style__ = __webpack_require__(87)
	var __weex_script__ = __webpack_require__(88)

	__weex_define__('@weex-component/wxc-hn', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 86 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": function () {return ['h' + (this.level)]},
	  "style": {
	    "justifyContent": "center"
	  },
	  "children": [
	    {
	      "type": "text",
	      "classList": function () {return ['txt-h' + (this.level)]},
	      "attr": {
	        "value": function () {return this.value}
	      }
	    }
	  ]
	}

/***/ },
/* 87 */
/***/ function(module, exports) {

	module.exports = {
	  "h1": {
	    "height": 110,
	    "paddingTop": 20,
	    "paddingBottom": 20
	  },
	  "h2": {
	    "height": 110,
	    "paddingTop": 20,
	    "paddingBottom": 20
	  },
	  "h3": {
	    "height": 110,
	    "paddingTop": 20,
	    "paddingBottom": 20
	  },
	  "txt-h1": {
	    "fontSize": 70
	  },
	  "txt-h2": {
	    "fontSize": 52
	  },
	  "txt-h3": {
	    "fontSize": 42
	  }
	}

/***/ },
/* 88 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    level: 1,
	    value: ''
	  }},
	  methods: {}
	};}
	/* generated by weex-loader */


/***/ },
/* 89 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(90)
	var __weex_style__ = __webpack_require__(91)
	var __weex_script__ = __webpack_require__(92)

	__weex_define__('@weex-component/wxc-list-item', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 90 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "item"
	  ],
	  "events": {
	    "touchstart": "touchstart",
	    "touchend": "touchend"
	  },
	  "style": {
	    "backgroundColor": function () {return this.bgColor}
	  },
	  "children": [
	    {
	      "type": "content"
	    }
	  ]
	}

/***/ },
/* 91 */
/***/ function(module, exports) {

	module.exports = {
	  "item": {
	    "paddingTop": 25,
	    "paddingBottom": 25,
	    "paddingLeft": 35,
	    "paddingRight": 35,
	    "height": 160,
	    "justifyContent": "center",
	    "borderBottomWidth": 1,
	    "borderColor": "#dddddd"
	  }
	}

/***/ },
/* 92 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    bgColor: '#ffffff'
	  }},
	  methods: {
	    touchstart: function touchstart() {},
	    touchend: function touchend() {}
	  }
	};}
	/* generated by weex-loader */


/***/ },
/* 93 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(94)
	var __weex_style__ = __webpack_require__(95)
	var __weex_script__ = __webpack_require__(96)

	__weex_define__('@weex-component/wxc-panel', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 94 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": function () {return ['panel', 'panel-' + (this.type)]},
	  "style": {
	    "borderWidth": function () {return this.border}
	  },
	  "children": [
	    {
	      "type": "text",
	      "classList": function () {return ['panel-header', 'panel-header-' + (this.type)]},
	      "style": {
	        "paddingTop": function () {return this.paddingHead},
	        "paddingBottom": function () {return this.paddingHead},
	        "paddingLeft": function () {return this.paddingHead*1.5},
	        "paddingRight": function () {return this.paddingHead*1.5}
	      },
	      "attr": {
	        "value": function () {return this.title}
	      }
	    },
	    {
	      "type": "div",
	      "classList": function () {return ['panel-body', 'panel-body-' + (this.type)]},
	      "style": {
	        "paddingTop": function () {return this.paddingBody},
	        "paddingBottom": function () {return this.paddingBody},
	        "paddingLeft": function () {return this.paddingBody*1.5},
	        "paddingRight": function () {return this.paddingBody*1.5}
	      },
	      "children": [
	        {
	          "type": "content"
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 95 */
/***/ function(module, exports) {

	module.exports = {
	  "panel": {
	    "marginBottom": 20,
	    "backgroundColor": "#ffffff",
	    "borderColor": "#dddddd",
	    "borderWidth": 1
	  },
	  "panel-primary": {
	    "borderColor": "rgb(40,96,144)"
	  },
	  "panel-success": {
	    "borderColor": "rgb(76,174,76)"
	  },
	  "panel-info": {
	    "borderColor": "rgb(70,184,218)"
	  },
	  "panel-warning": {
	    "borderColor": "rgb(238,162,54)"
	  },
	  "panel-danger": {
	    "borderColor": "rgb(212,63,58)"
	  },
	  "panel-header": {
	    "backgroundColor": "#f5f5f5",
	    "fontSize": 40,
	    "color": "#333333"
	  },
	  "panel-header-primary": {
	    "backgroundColor": "rgb(40,96,144)",
	    "color": "#ffffff"
	  },
	  "panel-header-success": {
	    "backgroundColor": "rgb(92,184,92)",
	    "color": "#ffffff"
	  },
	  "panel-header-info": {
	    "backgroundColor": "rgb(91,192,222)",
	    "color": "#ffffff"
	  },
	  "panel-header-warning": {
	    "backgroundColor": "rgb(240,173,78)",
	    "color": "#ffffff"
	  },
	  "panel-header-danger": {
	    "backgroundColor": "rgb(217,83,79)",
	    "color": "#ffffff"
	  },
	  "panel-body": {}
	}

/***/ },
/* 96 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    type: 'default',
	    title: '',
	    paddingBody: 20,
	    paddingHead: 20,
	    dataClass: '',
	    border: 0
	  }},
	  ready: function ready() {}
	};}
	/* generated by weex-loader */


/***/ },
/* 97 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(98)
	var __weex_style__ = __webpack_require__(99)
	var __weex_script__ = __webpack_require__(100)

	__weex_define__('@weex-component/wxc-tip', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 98 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": function () {return ['tip', 'tip-' + (this.type)]},
	  "children": [
	    {
	      "type": "text",
	      "classList": function () {return ['tip-txt', 'tip-txt-' + (this.type)]},
	      "attr": {
	        "value": function () {return this.value}
	      }
	    }
	  ]
	}

/***/ },
/* 99 */
/***/ function(module, exports) {

	module.exports = {
	  "tip": {
	    "paddingLeft": 36,
	    "paddingRight": 36,
	    "paddingTop": 36,
	    "paddingBottom": 36,
	    "borderRadius": 10
	  },
	  "tip-txt": {
	    "fontSize": 28
	  },
	  "tip-success": {
	    "backgroundColor": "#dff0d8",
	    "borderColor": "#d6e9c6"
	  },
	  "tip-txt-success": {
	    "color": "#3c763d"
	  },
	  "tip-info": {
	    "backgroundColor": "#d9edf7",
	    "borderColor": "#bce8f1"
	  },
	  "tip-txt-info": {
	    "color": "#31708f"
	  },
	  "tip-warning": {
	    "backgroundColor": "#fcf8e3",
	    "borderColor": "#faebcc"
	  },
	  "tip-txt-warning": {
	    "color": "#8a6d3b"
	  },
	  "tip-danger": {
	    "backgroundColor": "#f2dede",
	    "borderColor": "#ebccd1"
	  },
	  "tip-txt-danger": {
	    "color": "#a94442"
	  }
	}

/***/ },
/* 100 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    type: 'success',
	    value: ''
	  }}
	};}
	/* generated by weex-loader */


/***/ },
/* 101 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(102)
	var __weex_style__ = __webpack_require__(103)
	var __weex_script__ = __webpack_require__(104)

	__weex_define__('@weex-component/wxc-countdown', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 102 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "style": {
	    "overflow": "hidden",
	    "flexDirection": "row"
	  },
	  "events": {
	    "appear": "appeared",
	    "disappear": "disappeared"
	  },
	  "children": [
	    {
	      "type": "content"
	    }
	  ]
	}

/***/ },
/* 103 */
/***/ function(module, exports) {

	module.exports = {
	  "wrap": {
	    "overflow": "hidden"
	  }
	}

/***/ },
/* 104 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _assign = __webpack_require__(105);

	var _assign2 = _interopRequireDefault(_assign);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	module.exports = {
	    data: function () {return {
	        now: 0,
	        remain: 0,
	        time: {
	            elapse: 0,
	            D: '0',
	            DD: '0',
	            h: '0',
	            hh: '00',
	            H: '0',
	            HH: '0',
	            m: '0',
	            mm: '00',
	            M: '0',
	            MM: '0',
	            s: '0',
	            ss: '00',
	            S: '0',
	            SS: '0'
	        },
	        outofview: false
	    }},
	    ready: function ready() {
	        if (this.remain <= 0) {
	            return;
	        }

	        this.now = Date.now();
	        this.nextTick();
	    },
	    methods: {
	        nextTick: function nextTick() {
	            if (this.outofview) {
	                setTimeout(this.nextTick.bind(this), 1000);
	            } else {
	                this.time.elapse = parseInt((Date.now() - this.now) / 1000);

	                if (this.calc()) {
	                    this.$emit('tick', (0, _assign2.default)({}, this.time));
	                    setTimeout(this.nextTick.bind(this), 1000);
	                } else {
	                    this.$emit('alarm', (0, _assign2.default)({}, this.time));
	                }
	                this._app.updateActions();
	            }
	        },
	        format: function format(str) {
	            if (str.length >= 2) {
	                return str;
	            } else {
	                return '0' + str;
	            }
	        },
	        calc: function calc() {
	            var remain = this.remain - this.time.elapse;
	            if (remain < 0) {
	                remain = 0;
	            }
	            this.time.D = String(parseInt(remain / 86400));
	            this.time.DD = this.format(this.time.D);
	            this.time.h = String(parseInt((remain - parseInt(this.time.D) * 86400) / 3600));
	            this.time.hh = this.format(this.time.h);
	            this.time.H = String(parseInt(remain / 3600));
	            this.time.HH = this.format(this.time.H);
	            this.time.m = String(parseInt((remain - parseInt(this.time.H) * 3600) / 60));
	            this.time.mm = this.format(this.time.m);
	            this.time.M = String(parseInt(remain / 60));
	            this.time.MM = this.format(this.time.M);
	            this.time.s = String(remain - parseInt(this.time.M) * 60);
	            this.time.ss = this.format(this.time.s);
	            this.time.S = String(remain);
	            this.time.SS = this.format(this.time.S);

	            return remain > 0;
	        },
	        appeared: function appeared() {
	            this.outofview = false;
	        },
	        disappeared: function disappeared() {
	            this.outofview = true;
	        }
	    }
	};}
	/* generated by weex-loader */


/***/ },
/* 105 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(106), __esModule: true };

/***/ },
/* 106 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(107);
	module.exports = __webpack_require__(23).Object.assign;

/***/ },
/* 107 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.1 Object.assign(target, source)
	var $export = __webpack_require__(21);

	$export($export.S + $export.F, 'Object', {assign: __webpack_require__(108)});

/***/ },
/* 108 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.2.1 Object.assign(target, source, ...)
	var getKeys  = __webpack_require__(42)
	  , gOPS     = __webpack_require__(71)
	  , pIE      = __webpack_require__(72)
	  , toObject = __webpack_require__(58)
	  , IObject  = __webpack_require__(45)
	  , $assign  = Object.assign;

	// should work with symbols and should have deterministic property order (V8 bug)
	module.exports = !$assign || __webpack_require__(32)(function(){
	  var A = {}
	    , B = {}
	    , S = Symbol()
	    , K = 'abcdefghijklmnopqrst';
	  A[S] = 7;
	  K.split('').forEach(function(k){ B[k] = k; });
	  return $assign({}, A)[S] != 7 || Object.keys($assign({}, B)).join('') != K;
	}) ? function assign(target, source){ // eslint-disable-line no-unused-vars
	  var T     = toObject(target)
	    , aLen  = arguments.length
	    , index = 1
	    , getSymbols = gOPS.f
	    , isEnum     = pIE.f;
	  while(aLen > index){
	    var S      = IObject(arguments[index++])
	      , keys   = getSymbols ? getKeys(S).concat(getSymbols(S)) : getKeys(S)
	      , length = keys.length
	      , j      = 0
	      , key;
	    while(length > j)if(isEnum.call(S, key = keys[j++]))T[key] = S[key];
	  } return T;
	} : $assign;

/***/ },
/* 109 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(110)
	var __weex_style__ = __webpack_require__(111)
	var __weex_script__ = __webpack_require__(112)

	__weex_define__('@weex-component/wxc-marquee', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 110 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "wrap"
	  ],
	  "events": {
	    "appear": "appeared",
	    "disappear": "disappeared"
	  },
	  "children": [
	    {
	      "type": "div",
	      "id": "anim",
	      "classList": [
	        "anim"
	      ],
	      "children": [
	        {
	          "type": "content"
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 111 */
/***/ function(module, exports) {

	module.exports = {
	  "wrap": {
	    "overflow": "hidden",
	    "position": "relative"
	  },
	  "anim": {
	    "flexDirection": "column",
	    "position": "absolute",
	    "transform": "translateY(0) translateZ(0)"
	  }
	}

/***/ },
/* 112 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	    data: function () {return {
	        step: 0,
	        count: 0,
	        index: 1,
	        duration: 0,
	        interval: 0,
	        outofview: false
	    }},
	    ready: function ready() {
	        if (this.interval > 0 && this.step > 0 && this.duration > 0) {
	            this.nextTick();
	        }
	    },
	    methods: {
	        nextTick: function nextTick() {
	            var self = this;
	            if (this.outofview) {
	                setTimeout(self.nextTick.bind(self), self.interval);
	            } else {
	                setTimeout(function () {
	                    self.animation(self.nextTick.bind(self));
	                }, self.interval);
	            }
	        },
	        animation: function animation(cb) {
	            var self = this;
	            var offset = -self.step * self.index;
	            var $animation = __weex_require__('@weex-module/animation');
	            $animation.transition(this.$el('anim'), {
	                styles: {
	                    transform: 'translateY(' + String(offset) + 'px) translateZ(0)'
	                },
	                timingFunction: 'ease',
	                duration: self.duration
	            }, function () {
	                self.index = (self.index + 1) % self.count;
	                self.$emit('change', {
	                    index: self.index,
	                    count: self.count
	                });
	                cb && cb();
	            });
	        },
	        appeared: function appeared() {
	            this.outofview = false;
	        },
	        disappeared: function disappeared() {
	            this.outofview = true;
	        }
	    }
	};}
	/* generated by weex-loader */


/***/ },
/* 113 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(114)
	var __weex_style__ = __webpack_require__(115)
	var __weex_script__ = __webpack_require__(116)

	__weex_define__('@weex-component/wxc-navbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 114 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "container"
	  ],
	  "style": {
	    "height": function () {return this.height},
	    "backgroundColor": function () {return this.backgroundColor}
	  },
	  "attr": {
	    "dataRole": function () {return this.dataRole}
	  },
	  "children": [
	    {
	      "type": "text",
	      "classList": [
	        "right-text"
	      ],
	      "style": {
	        "color": "#ffffff",
	        "padding": 60,
	        "paddingRight": 20
	      },
	      "attr": {
	        "naviItemPosition": "right",
	        "value": function () {return this.rightItemTitle}
	      },
	      "shown": function () {return !this.rightItemSrc},
	      "events": {
	        "click": "onclickrightitem"
	      }
	    },
	    {
	      "type": "div",
	      "classList": [
	        "right-image"
	      ],
	      "events": {
	        "click": "onclickrightitem"
	      },
	      "children": [
	        {
	          "type": "image",
	          "classList": [
	            "right-image2"
	          ],
	          "attr": {
	            "naviItemPosition": "right",
	            "src": function () {return this.rightItemSrc}
	          },
	          "shown": function () {return this.rightItemSrc}
	        }
	      ]
	    },
	    {
	      "type": "text",
	      "classList": [
	        "left-text"
	      ],
	      "style": {
	        "color": function () {return this.leftItemColor}
	      },
	      "attr": {
	        "naviItemPosition": "left",
	        "value": function () {return this.leftItemTitle}
	      },
	      "shown": function () {return !this.leftItemSrc},
	      "events": {
	        "click": "onclickleftitem"
	      }
	    },
	    {
	      "type": "div",
	      "style": {
	        "padding": 60
	      },
	      "events": {
	        "click": "onclickleftitem"
	      },
	      "children": [
	        {
	          "type": "image",
	          "classList": [
	            "left-image"
	          ],
	          "attr": {
	            "naviItemPosition": "left",
	            "src": function () {return this.leftItemSrc}
	          },
	          "shown": function () {return this.leftItemSrc}
	        }
	      ]
	    },
	    {
	      "type": "text",
	      "classList": [
	        "center-text"
	      ],
	      "style": {
	        "color": function () {return this.titleColor}
	      },
	      "attr": {
	        "naviItemPosition": "center",
	        "value": function () {return this.title}
	      }
	    }
	  ]
	}

/***/ },
/* 115 */
/***/ function(module, exports) {

	module.exports = {
	  "container": {
	    "flexDirection": "row",
	    "position": "fixed",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "width": 750
	  },
	  "right-text": {
	    "position": "absolute",
	    "bottom": -30,
	    "right": 0,
	    "textAlign": "right",
	    "fontSize": 32,
	    "fontFamily": "'Open Sans', sans-serif",
	    "padding": 60
	  },
	  "left-text": {
	    "position": "absolute",
	    "bottom": 28,
	    "left": 32,
	    "textAlign": "left",
	    "fontSize": 32,
	    "fontFamily": "'Open Sans', sans-serif"
	  },
	  "center-text": {
	    "position": "absolute",
	    "bottom": 25,
	    "left": 172,
	    "right": 172,
	    "textAlign": "center",
	    "fontSize": 36,
	    "fontWeight": "bold"
	  },
	  "left-image": {
	    "position": "absolute",
	    "bottom": 20,
	    "left": 20,
	    "width": 50,
	    "height": 50,
	    "padding": 20
	  },
	  "right-image2": {
	    "position": "absolute",
	    "bottom": 20,
	    "right": 28,
	    "width": 50,
	    "height": 50,
	    "padding": 20
	  },
	  "right-image": {
	    "position": "absolute",
	    "bottom": 0,
	    "right": 0,
	    "paddingTop": 60,
	    "paddingBottom": 60,
	    "paddingRight": 20,
	    "paddingLeft": 90
	  }
	}

/***/ },
/* 116 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    dataRole: 'navbar',

	    backgroundColor: 'black',

	    height: 88,

	    title: "",

	    titleColor: 'black',

	    rightItemSrc: '',

	    rightItemTitle: '',

	    rightItemColor: 'black',

	    leftItemSrc: '',

	    leftItemTitle: '',

	    leftItemColor: 'black'
	  }},
	  methods: {
	    onclickrightitem: function onclickrightitem(e) {
	      this.$dispatch('naviBar.rightItem.click', {});
	    },
	    onclickleftitem: function onclickleftitem(e) {
	      this.$dispatch('naviBar.leftItem.click', {});
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ },
/* 117 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(113)
	var __weex_template__ = __webpack_require__(118)
	var __weex_style__ = __webpack_require__(119)
	var __weex_script__ = __webpack_require__(120)

	__weex_define__('@weex-component/wxc-navpage', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 118 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "wrapper"
	  ],
	  "children": [
	    {
	      "type": "wxc-navbar",
	      "attr": {
	        "dataRole": function () {return this.dataRole},
	        "height": function () {return this.height},
	        "backgroundColor": function () {return this.backgroundColor},
	        "title": function () {return this.title},
	        "titleColor": function () {return this.titleColor},
	        "leftItemSrc": function () {return this.leftItemSrc},
	        "leftItemTitle": function () {return this.leftItemTitle},
	        "leftItemColor": function () {return this.leftItemColor},
	        "rightItemSrc": function () {return this.rightItemSrc},
	        "rightItemTitle": function () {return this.rightItemTitle},
	        "rightItemColor": function () {return this.rightItemColor}
	      }
	    },
	    {
	      "type": "div",
	      "classList": [
	        "wrapper"
	      ],
	      "style": {
	        "marginTop": function () {return this.height}
	      },
	      "children": [
	        {
	          "type": "content"
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 119 */
/***/ function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0,
	    "width": 750
	  }
	}

/***/ },
/* 120 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    dataRole: 'navbar',
	    backgroundColor: 'black',
	    height: 88,
	    title: "",
	    titleColor: 'black',
	    rightItemSrc: '',
	    rightItemTitle: '',
	    rightItemColor: 'black',
	    leftItemSrc: '',
	    leftItemTitle: '',
	    leftItemColor: 'black'
	  }}
	};}
	/* generated by weex-loader */


/***/ },
/* 121 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(122)
	var __weex_template__ = __webpack_require__(126)
	var __weex_style__ = __webpack_require__(127)
	var __weex_script__ = __webpack_require__(128)

	__weex_define__('@weex-component/wxc-tabbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 122 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(123)
	var __weex_style__ = __webpack_require__(124)
	var __weex_script__ = __webpack_require__(125)

	__weex_define__('@weex-component/wxc-tabitem', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 123 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "container"
	  ],
	  "style": {
	    "backgroundColor": function () {return this.backgroundColor}
	  },
	  "events": {
	    "click": "onclickitem"
	  },
	  "children": [
	    {
	      "type": "image",
	      "classList": [
	        "top-line"
	      ],
	      "attr": {
	        "src": "http://gtms03.alicdn.com/tps/i3/TB1mdsiMpXXXXXpXXXXNw4JIXXX-640-4.png"
	      }
	    },
	    {
	      "type": "image",
	      "classList": [
	        "tab-icon"
	      ],
	      "attr": {
	        "src": function () {return this.icon}
	      }
	    },
	    {
	      "type": "text",
	      "classList": [
	        "tab-text"
	      ],
	      "style": {
	        "color": function () {return this.titleColor}
	      },
	      "attr": {
	        "value": function () {return this.title}
	      }
	    }
	  ]
	}

/***/ },
/* 124 */
/***/ function(module, exports) {

	module.exports = {
	  "container": {
	    "flex": 1,
	    "flexDirection": "column",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "height": 88
	  },
	  "top-line": {
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "height": 2
	  },
	  "tab-icon": {
	    "marginTop": 5,
	    "width": 40,
	    "height": 40
	  },
	  "tab-text": {
	    "marginTop": 5,
	    "textAlign": "center",
	    "fontSize": 20
	  }
	}

/***/ },
/* 125 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    index: 0,
	    title: '',
	    titleColor: '#000000',
	    icon: '',
	    backgroundColor: '#ffffff'
	  }},
	  methods: {
	    onclickitem: function onclickitem(e) {
	      var vm = this;
	      var params = {
	        index: vm.index
	      };
	      vm.$dispatch('tabItem.onClick', params);
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ },
/* 126 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "wrapper"
	  ],
	  "children": [
	    {
	      "type": "embed",
	      "classList": [
	        "content"
	      ],
	      "style": {
	        "visibility": function () {return this.visibility}
	      },
	      "repeat": function () {return this.tabItems},
	      "attr": {
	        "src": function () {return this.src},
	        "type": "weex"
	      }
	    },
	    {
	      "type": "div",
	      "classList": [
	        "tabbar"
	      ],
	      "append": "tree",
	      "children": [
	        {
	          "type": "wxc-tabitem",
	          "repeat": function () {return this.tabItems},
	          "attr": {
	            "index": function () {return this.index},
	            "icon": function () {return this.icon},
	            "title": function () {return this.title},
	            "titleColor": function () {return this.titleColor}
	          }
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 127 */
/***/ function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "width": 750,
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0
	  },
	  "content": {
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0,
	    "marginTop": 0,
	    "marginBottom": 88
	  },
	  "tabbar": {
	    "flexDirection": "row",
	    "position": "fixed",
	    "bottom": 0,
	    "left": 0,
	    "right": 0,
	    "height": 88
	  }
	}

/***/ },
/* 128 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    tabItems: [],
	    selectedIndex: 0,
	    selectedColor: '#ff0000',
	    unselectedColor: '#000000'
	  }},
	  created: function created() {
	    this.selected(this.selectedIndex);

	    this.$on('tabItem.onClick', function (e) {
	      var detail = e.detail;
	      this.selectedIndex = detail.index;
	      this.selected(detail.index);

	      var params = {
	        index: detail.index
	      };
	      this.$dispatch('tabBar.onClick', params);
	    });
	  },
	  methods: {
	    selected: function selected(index) {
	      for (var i = 0; i < this.tabItems.length; i++) {
	        var tabItem = this.tabItems[i];
	        if (i == index) {
	          tabItem.icon = tabItem.selectedImage;
	          tabItem.titleColor = this.selectedColor;
	          tabItem.visibility = 'visible';
	        } else {
	          tabItem.icon = tabItem.image;
	          tabItem.titleColor = this.unselectedColor;
	          tabItem.visibility = 'hidden';
	        }
	      }
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ },
/* 129 */
/***/ function(module, exports) {

	var Utils = {
	    dir : 'yjpt',
	  	// dir : 'yjpts',
	    // ip : 'http://192.168.8.206:8180/',
	     ip : 'http://192.168.8.114:8888/',
	    // ip : 'http://127.0.0.1:8888/',
	    // ip : 'http://www.yuertong.com/',   //本地不用

	    setOpenUrl : function(context,arr){
	      var bundleUrl = context.bundleUrl;
	      bundleUrl = new String(bundleUrl);
	      var nativeBase;
	      var isAndroidAssets = bundleUrl.indexOf('file:///mnt/sdcard/') >= 0;

	      var isiOSAssets = bundleUrl.indexOf('file:///') >= 0 ;//&& bundleUrl.indexOf('WeexDemo.app') > 0;
	      if (isAndroidAssets) {
	          nativeBase = bundleUrl;
	      }
	      else if (isiOSAssets) {
	        // file:///var/mobile/Containers/Bundle/Application/{id}/WeexDemo.app/
	        // file:///Users/{user}/Library/Developer/CoreSimulator/Devices/{id}/data/Containers/Bundle/Application/{id}/WeexDemo.app/
	        nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
	      }
	      else {
	        var host = Utils.ip;
	        var matches = /\/\/([^\/]+?)\//.exec(context.bundleUrl);
	        if (matches && matches.length >= 2) {
	          host = matches[1];
	        }
	        nativeBase = 'http://' + host + '/' + Utils.dir + '/weex_jzd/';
	      }
	      var h5Base = './index.html?page=./' + Utils.dir + '/weex_jzd/';
	      // in Native
	      var base = nativeBase;
	      if (typeof window === 'object') {
	        // in Browser or WebView
	        base = h5Base;
	      }

	      if(Object.prototype.toString.call(arr) === '[object Array]'){//参数是数组类型[{url:'showcase/new-fashion/sub-type'},{url:'showcase/new-fashion/sub-type'}]
	        for (var i in arr) {
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
	            for(var j in imgNameArr){
	              for (var i in arr) {
	                  var obj = arr[i];
	                  if(subArrName){
	                    var subObj = obj[subArrName];
	                    var osubOjb ;
	                    for(var k in subObj){
	                      if(k==imgNameArr[j]){
	                          osubOjb = subObj[k];
	                          subObj[imgNameArr[j]] = Utils.ip +Utils.dir+ '/'+osubOjb;
	                      }
	                        
	                    }
	                  }
	                  obj[imgNameArr[j]] = Utils.ip +Utils.dir+ '/'+ obj[imgNameArr[j]];
	                  
	              }
	            }
	          }else if(Object.prototype.toString.call(arr) === '[object String]'){
	              arr = Utils.ip + arr;
	          }else if(Object.prototype.toString.call(arr) === '[object Object]'){
	              if(Object.prototype.toString.call(imgNameArr) !== '[object Array]'){
	                console.log("必须是数组");
	                return;
	              }
	              for(var i in imgNameArr){
	                arr[imgNameArr[i]] = Utils.ip + Utils.dir +  '/'+arr[imgNameArr[i]] ;
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
	        // var navigator = require('@weex-module/navigator');
	        var params = {
	          'url': url,
	          'animated' : animate,
	        }
	        var vm = obj;    
	        /*navigator.push(params,function(e){
	          if(typeof callback == 'function')
	               callback();
	        })
	    */    vm.$call('navigator','push',params, function () {
	          if(typeof callback == 'function')
	             callback();
	        });
	      },
	      /***
	      * 关闭当前页面 
	      * @params obj 当前页面作业域(传参数时为this)
	      * @params animate 是否显示动画；值为'true'/'false'
	      * @params callback 回调函数
	      */
	      pop : function(obj,animate,callback){
	        // var navigator = require('@weex-module/navigator');
	        var params = {
	          'animated' : animate,
	        }
	        /*navigator.pop(params,function(e){
	            if(typeof callback == 'function')
	                callback();
	        })*/
	         obj.$call('navigator','pop',params, function () {
	           if(typeof callback == 'function')
	             callback();
	         });
	      },
	      /***
	      * 打开一个新页面 (防止在当前滑动到上一页面)
	      * @params obj 当前页面作业域(传参数时为this)
	      * @params url 页面的地址
	      * @params animate 是否显示动画；值为'true'/'false'
	      * @params callback 回调函数
	      */
	      present :  function(obj,url,animate,callback){
	        var params = {
	          'url': url,
	          'animated' : animate,
	        }
	        var vm = obj;   
	        /*navigator.present(params,function(e){
	            if(typeof callback == 'function')
	              callback();
	        })*/
	        vm.$call('navigator','present',params, function () {
	          if(typeof callback == 'function')
	             callback();
	        });
	      }
	    },

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
	    // },



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
	        var requestUrl = Utils.ip + Utils.dir;
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
	                   // data : eval("(" + response.data + ")")  // 用于手机 端
	                    data :response.data //  用于PC端
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
/* 130 */,
/* 131 */,
/* 132 */,
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
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
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */,
/* 223 */,
/* 224 */,
/* 225 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "wxc-navpage",
	  "attr": {
	    "dataRole": "none",
	    "height": function () {return this.navBarHeight},
	    "backgroundColor": "#00cc99",
	    "title": "服务协议",
	    "titleColor": "white",
	    "leftItemSrc": function () {return this.headLeft}
	  },
	  "events": {
	    "clickleftitem": "onclickleftitem"
	  },
	  "children": [
	    {
	      "type": "scroller",
	      "classList": [
	        "content"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "title_name"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 44,
	                "fontWeight": "bold"
	              },
	              "attr": {
	                "value": "软件许可及服务协议"
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "lead"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "重要须知："
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "开维教育在此特别提醒用户认真阅读、充分理解本《软件许可及服务协议》（下称《协议》）--- 用户应认真阅读、充分理解本《协议》中各条款，包括免除或者限制开维教育责任的免责条款及对用户的权利限制条款。请您审慎阅读并选择接受或不接受本《协议》（未成年人应在法定监护人陪同下阅读）。除非您接受本《协议》所有条款，否则您无权下载、安装或使用本软件及其相关服务。您的下载、安装、使用、帐号获取和登录等行为将视为对本《协议》的接受，并同意接受本《协议》各项条款的约束。\n       本《协议》是您（下称“用户”）与深圳开维教育信息技术股份有限公司（下称“开维教育”）及其运营合作单位（下称“合作单位”）之间关于用户下载、安装、使用“开维软件”；注册、使用、管理开维教育帐号；以及使用开维教育相关服务所订立的协议。本《协议》描述开维教育与用户之间关于“软件”许可使用及服务相关方面的权利义务。“用户”是指通过开维教育提供的获取软件授权和帐号注册的途径获得软件产品及号码授权许可以及使用开维教育相关服务的个人或组织。\n\n       本《协议》可由开维教育随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知。用户可重新下载安装本软件或网站查阅最新版协议条款。在开维教育修改《协议》条款后，如果用户不接受修改后的条款，请立即停止使用开维教育提供的软件和服务，用户继续使用开维教育提供的软件和服务将被视为已接受了修改后的协议。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "1.知识产权声明"
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "1.1  本“软件”是由开维教育开发。“软件”的一切版权、商标权、专利权、商业秘密等知识产权，以及与“软件”相关的所有信息内容，包括但不限于：文字表述及其组合、图标、图饰、图表、色彩、界面设计、版面框架、有关数据、印刷材料、或电子文档等均受中华人民共和国著作权法、商标法、专利法、反不正当竞争法和相应的国际条约以及其他知识产权法律法规的保护，除涉及第三方授权的软件或技术外，开维教育享有上述知识产权。\n       1.2  未经开维教育书面同意，用户不得为任何营利性或非营利性的目的自行实施、利用、转让或许可任何三方实施、利用、转让上述知识产权，开维教育保留追究上述未经许可行为的权利。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "2.“软件”授权范围"
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "2.1  用户不得对该软件或者该软件运行过程中释放到任何计算机终端内存中的数据及该软件运行过程中客户端与服务器端的交互数据进行复制、更改、修改、挂接运行或创作任何衍生作品，形式包括但不限于使用插件、外挂或非经授权的第三方工具/服务接入本“软件”和相关系统。\n       2.2  保留权利：未明示授权的其他一切权利仍归开维教育所有，用户使用其他权利时须另外取得开维教育的书面同意。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "3. 开维教育帐号"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "style": {
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "要使用本“软件”，用户需填写并提交个人或组织资料注册开维教育帐号。作为帐号注册过程的一部分，用户必须同意：(1) 按照帐号注册过程的提示信息，提供有关您本人的真实信息（此类信息必须保持最新、完整且准确）；(2) 根据需要维护并更新注册人信息，使之始终保持最新、完整且准确。如果您未满 18 周岁，应在法定监护人陪同下阅读并提交帐号注册。"
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "3.1 用户可以通过注册开维教育帐号使用开维教育提供的各种服务。服务包括但不限于PC端及手持终端软件、幼教平台-园丁、幼教平台-家长等可以通过开维教育帐号登录或使用的服务，开维教育保留对公司未来服务改变和说明的权利。无论用户通过何种方式获得开维教育帐号，均受本协议约束。用户通过开维教育帐号使用开维教育的服务时，须同时遵守各项服务的服务条款。\n       3.2 开维教育帐号的所有权归开维教育所有，用户完成申请注册手续后，获得开维教育帐号的使用权。\n       3.3 开维教育帐号使用权仅属于初始申请注册人，禁止赠与、借用、租用、转让或售卖。如果开维教育发现使用者并非帐号初始注册人，开维教育有权在未经通知的情况下回收该帐号而无需向该帐号使用人承担法律责任，由此带来的包括并不限于用户通讯中断、用户资料等清空等损失由用户自行承担。开维教育禁止用户私下有偿或无偿转让帐号，以免因帐号问题产生纠纷，用户应当自行承担因违反此要求而遭致的任何损失，同时开维教育保留追究上述行为人法律责任的权利。\n       3.4 用户承担开维教育帐号与密码的保管责任，并就其帐号及密码项下之一切活动负全部责任。用户须重视开维教育帐号密码和公开邮箱的密码保护。用户同意如发现他人未经许可使用其帐号时立即通知开维教育。\n       3.5 用户开维教育帐号在丢失或遗忘密码后，须遵照开维教育的申诉途径及时申诉请求找回帐号。用户应提供能增加帐号安全性的个人密码保护资料。用户可以凭初始注册资料及个人密码保护资料填写申诉向开维教育申请找回帐号，开维教育的密码找回机制仅负责识别申诉单上所填资料与系统记录资料的正确性，而无法识别申诉人是否系真正帐号权使用人。对用户因被他人冒名申诉而致的任何损失，开维教育不承担任何责任，用户知晓开维教育帐号及密码保管责任在于用户，开维教育并不承诺开维教育帐号丢失或遗忘密码后用户一定能通过申诉找回帐号。\n       3.6 用户注册开维教育帐号后如果长期不使用，开维教育有权回收帐号，以免造成资源浪费，由此带来的包括并不限于用户通信中断、用户资料、邮件等损失由用户自行承担。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "4.“软件”使用"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "style": {
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "用户在遵守法律及本《协议》的前提下可依本《协议》使用本“软件”。用户无权实施包括但不限于下列行为："
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "4.1 用户通过非开维教育开发、授权或认可的三方兼容软件、系统登录或使用开维教育软件及服务，用户针对开维教育公司的软件和服务使用非开维教育公司开发、授权或认证的插件和外挂；\n       4.2 删除本“软件”及其他副本上所有关于版权的信息、内容；\n       4.3 对本“软件”进行反向工程、反向汇编、反向编译等.\n       4.4 对于本“软件”相关信息等，未经开维教育书面同意，用户擅自实施包括但不限于下列行为：使用、出租、出借、复制、修改、链接、转载、汇编、发表、出版，建立镜像站点、擅自借助本“软件”发展与之有关的衍生产品、作品、服务、插件、外挂、兼容、互联等。\n       4.5 利用本“软件”发表、传送、传播、储存违反国家法律、危害国家安全、祖国统一、社会稳定、公序良俗的内容，或任何不当的、侮辱诽谤的、淫秽的、暴力的及任何违反国家法律法规政策的内容.\n       4.6 利用本“软件”发表、传送、传播、储存侵害他人知识产权、商业秘密权等合法权利的内容；\n       4.7 制造虚假身份以误导、欺骗他人.\n       4.8 利用本“软件”批量发表、传送、传播广告信息及垃圾信息；\n       4.9 传送或散布以其他方式实现传送含有受到知识产权法律保护的图像、相片、软件或其他资料的文件，作为举例（但不限于此）：包括版权或商标法（或隐私权或公开权），除非用户拥有或控制着相应的权利或已得到所有必要的认可.\n       4.10 使用任何包含有通过侵犯商标、版权、专利、商业机密或任何一方的其他专有权利的方式利用本“软件”获得的图像或相片的资料或信息.\n       4.11 任何人进行任何危害计算机网络安全的行为，包括但不限于：使用未经许可的数据或进入未经许可的服务器/帐号；未经允许进入公众计算机网络或者他人计算机系统并删除、修改、增加存储信息；未经许可，企图探查、扫描、测试本“软件”系统或网络的弱点或其它实施破坏网络安全的行为；企图干涉、破坏本“软件”系统或网站的正常运行，故意传播恶意程序或病毒以及其他破坏干扰正常网络信息服务的行为；伪造TCP/IP数据包名称或部分名称.\n       4.12 不得通过修改或伪造软件作品运行中的指令、数据、数据包，增加、删减、变动软件的功能或运行效果，不得将用于上述用途的软件通过信息网络向公众传播或者运营.\n       4.13 将本软件及开维教育提供的服务用于核设施运行、生命维持或其他会使人类及其财产处于危险之中的重大设备。用户明白本软件及开维教育提供的服务并非为以上目的而设计，如果因为软件和服务的原因导致以上操作失败而带来的人员伤亡、严重的财产损失和环境破坏，开维教育将不承担任何责任.\n       4.14 禁止用户制作、发布、传播用于窃取开维教育帐号及他人专属信息、财产、保密信息的软件.\n       4.15 在未经开维教育公司书面明确授权前提下，出售、出租、出借、散布、转移或转授权软件和服务或相关的链接或从使用软件和服务或软件和服务的条款中获利，无论以上使用是否为直接经济或金钱收益.\n       4.16 其他以任何不合法的方式、为任何不合法的目的、或以任何与本协议不一致的方式使用本软件和和开维教育提供的其他服务.\n       4.17 用户若违反上述规定，开维教育有权采取终止、完全或部分中止、限制用户帐号的使用功能.\n       4.18 使用本“软件”必须遵守国家有关法律和政策等，维护国家利益，保护国家安全，并遵守本《协议》。对于用户违法或违反本《协议》的使用而引起的一切责任，由用户负全部责任，一概与开维教育及合作单位无关；导致开维教育及合作单位损失的，开维教育及合作单位有权要求用户赔偿，并有权保留相关记录。而且，对于用户违法或违反本《协议》以及违反了利用本软件和开维教育帐号访问的开维教育或合作单位的其他服务规定的相关服务条款，开维教育有权视用户的行为性质，在不事先通知用户的情况下，采取包括但不限于中断使用许可、停止提供服务、限制使用、回收用户开维教育帐号、法律追究等措施。对利用开维教育帐号进行违法活动、骚扰、欺骗其他用户等行为，开维教育有权回收其帐号。由此带来的包括并不限于用户通信中断、用户资料、邮件和游戏道具丢失等损失由用户自行承担.\n       4.19 本“软件”同大多数互联网软件一样，受包括但不限于用户原因、网络服务质量、社会环境等因素的差异影响，可能受到各种安全问题的侵扰，如他人利用用户的资料，造成现实生活中的骚扰；用户下载安装的其它软件或访问的其他网站中含有“特洛伊木马”等病毒，威胁到用户的计算机信息和数据的安全，继而影响本“软件”的正常使用等等。用户应加强信息安全及使用者资料的保护意识，要注意加强密码保护，以免遭致损失和骚扰.\n       4.20 非经开维教育或开维教育授权开发并正式发布的其它任何由本“软件”衍生的软件均属非法，下载、安装、使用此类软件，将可能导致不可预知的风险，由此产生的一切法律责任与纠纷一概与开维教育无关。用户不得轻易下载、安装、使用此类软件，否则，开维教育有权在不事先通知用户的情况下单方面终止用户开维教育帐号的使用资格。\n\n       用户只能通过开维教育提供的本“软件”及其他合法通道登录使用开维软件。用户不得通过其他未经开维教育授权开发的包括但不限于非法兼容型软件、程序或者其他非开维教育明示许可的方式，登录使用开维教育帐号，否则，开维教育有权在不事先通知用户的情况下单方面终止用户的开维教育帐号的使用资格\n       4.21 用户同意个人隐私信息是指那些能够对用户进行个人辨识或涉及个人通信的信息，包括下列信息：用户的姓名，身份证号，手机号码，IP地址，电子邮件地址信息。而非个人隐私信息是指用户对本软件的操作状态以及使用习惯等一些明确且客观反映在开维教育服务器端的基本记录信息和其他一切个人隐私信息范围外的普通信息。 尊重用户个人隐私信息的私有性是开维教育的一贯制度，开维教育将会采取合理的措施保护用户的个人隐私信息，除法律或有法律赋予权限的政府部门要求或用户同意等原因外，开维教育未经用户同意不向除合作单位以外的第三方公开、 透露用户个人隐私信息。 但是用户在注册时选择或同意，或用户与开维教育及合作单位之间就用户个人隐私信息公开或使用另有约定的除外，同时用户应自行承担因此可能产生的任何风险，开维教育对此不予负责。同时为了运营和改善开维教育的技术和服务，我们将可能会自己收集使用或向第三方提供用户的非个人隐私信息，这将有助于向用户提供更好的用户体验和提高我们的服务质量.\n       4.22 一般而言，开维教育公司基于下列原因需要使用到用户的信息资源：（1）执行软件验证服务；（2）执行软件升级服务；（3）提高用户的使用安全性并提供客户支持；（4）因用户使用开维教育软件特定功能如远程控制等或因用户要求开维教育或合作单位提供特定服务时，开维教育或合作单位则需要把用户的信息提供给与此相关联的第三方；（5）将各种非个人隐私数据用于商业目的，包括但不限于向第三方提供增值服务、广告、定位广告、营销、联合注册其它服务、促销或其它任何活动（统称为“商业活动”）；（6）执行开维教育的《隐私保护声明》，用户可访问开维教育网站查阅该声明；（7）其他有利于用户和开维教育利益的.\n       4.23 本“软件”需要用户共同享用、维护其所提供的利益，用户在此确认同意本“软件”在必要时使用您计算机的处理器和带宽等资源，用作容许其它本“软件”使用者与您通讯联络、分享本“软件”及服务的有限目的。此项同意可能会影响用户的使用感受和带来不可预知的风险。您应认真考虑并做出选择，承担风险.\n       4.24 用户同意本“软件”将会尽其合理努力以保护您的计算机资源及计算机通讯的隐私性和完整性，但是，您承认和同意开维教育不能就此事提供任何保证.\n       4.25 “用户同意”的方式有：（1）通过点击“我同意”或者其他方式接受本《协议》及开维教育发布的其他服务条款；（2）用户通过电子邮件、电话、传真、即时通信等方式所作的口头或书面表示；（3）本《协议》或其他服务声明中有“默认同意”条款，用户对此无异议的；（4）其他开维教育与用户均认可的方式.\n       4.26  开维教育保留在任何时候根据适用法律、法规、法律程序或政府要求的需要而披露任何信息，或由开维教育自主决定全部或部分地编辑、拒绝张贴或删除任何信息或资料的权利.\n       4.27  本“软件”的替换、修改和升级：开维教育保留在任何时候通过为您提供本“软件”替换、修改、升级版本的权利以及为替换、修改或升级收取费用的权利。本“软件”为用户默认开通“升级提示”功能，视用户使用的“软件”版本差异，开维教育提供给用户自行选择是否需要开通此功能。软件新版本发布后，开维教育不保证旧版本软件的继续可用。开维教育保留因业务发展需要，单方面对软件的部分功能效果进行改变或进行限制，用户需承担此风险.\n       4.28  开维教育和/或合作单位将根据市场与技术的发展，向用户提供与本“软件”相关的各种互联网以及移动通信增值服务，包括免费和收费的增值服务。开维教育和/或合作单位保留对相关增值服务收取费用及改变收费标准、方式的权利；如相关服务由免费变更为收费服务，开维教育和/或合作单位将以适当的形式通知，用户可自主选择接受或拒绝收费服务，并保证在使用收费服务时，将按照开维教育和/或合作单位相关收费规定支付费用，如拒付或拖欠费用，开维教育和/或合作单位有权停止服务，并依法追偿损失及赔偿.\n       4.29  开维教育公司有权在服务中或经过服务投放各种广告和宣传信息，该广告可能以系统消息或弹出窗口等形式出现.\n       4.30 本“软件”可能会使用第三方的软件或技术，该等使用均是获得了合法授权的。如因本“软件”使用的第三方软件或技术引发的任何纠纷，由该第三方负责解决，开维教育不承担任何责任。 开维教育不对该第三方软件或技术提供客服支持，若用户需要获取支持，请与该第三方联系。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "5. 法律责任与免责"
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "5.1   利用的许可\n       5.1.1 许可利用您的计算机：为了得到本“软件”所提供的利益，用户在此许可开维教育利用用户计算机的处理器和宽带，用作容许其它开维教育开维软件使用者与用户通讯联络的有限目的。\n       5.1.2 保护用户的计算机（资源）：用户认可开维教育软件将会尽其商业上的合理努力以保护用户的计算机资源及计算机通讯的隐私性和完整性，但是，用户承认和同意开维教育不能就此事提供任何保证.\n       5.1.3 本软件为网络交互型软件，开维教育可以根据用户软件使用状态和行为，为了改善软件服务和体验、完善业务内容，自己或与合作方合作开发新的服务或者调整软件功能.\n       5.2   开维教育特别提请用户注意：开维教育为了保障公司业务发展和调整的自主权，开维教育拥有随时自行修改或中断软件授权而不需通知用户的权利，如有必要，修改或中断会以通告形式公布于开维教育网站重要页面上.\n       5.3   用户违反本《协议》或相关的服务条款的规定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费，用户同意赔偿开维教育与合作公司、关联公司，并使之免受损害。对此，开维教育有权视用户的行为性质，在不事先通知用户的情况下，采取本《协议》第4.18条所述的措施及其他相应的措施.\n       5.4   使用本“软件”由用户自己承担风险，开维教育及合作单位对本“软件”不作任何类型的担保，不论是明示的、默示的或法令的保证和条件，包括但不限于本“软件”的适销性、适用性、无病毒、无疏忽或无技术瑕疵问题、所有权和无侵权的明示或默示担保和条件，对在任何情况下因使用或不能使用本“软件”所产生的直接、间接、偶然、特殊及后续的损害及风险，开维教育及合作单位不承担任何责任.\n       5.5   使用本“软件”涉及到互联网服务，可能会受到各个环节不稳定因素的影响，存在因不可抗力、计算机病毒、黑客攻击、系统不稳定、用户所在位置、用户关机，非法内容信息、骚扰信息屏蔽以及其他任何网络、技术、通信线路、信息安全管理措施等原因造成的服务中断、受阻等不能满足用户要求的风险，用户须明白并自行承担以上风险，用户因此不能发送或接收阅读消息，或接、发错消息，开维教育及合作单位不承担任何责任.\n       5.6   使用本“软件”可能存在来自他人匿名或冒名的含有威胁、诽谤、令人反感或非法内容的信息的风险，用户须明白并自行承担以上风险，开维教育及合作单位不对任何有关信息真实性、适用性、合法性承担责任.\n       5.7   用户因第三方如电信部门的通讯线路故障、技术问题、网络、电脑故障、系统不稳定性及其他各种不可抗力原因而遭受的一切损失，开维教育及合作单位不承担责任.\n       5.8   因技术故障等不可抗事件影响到服务的正常运行的，开维教育及合作单位承诺在第一时间内与相关单位配合，及时处理进行修复，但用户因此而遭受的一切损失，开维教育及合作单位不承担责任.\n       5.9   用户之间通过本“软件”与其他用户交互，因受误导或欺骗而导致或可能导致的任何心理、生理上的伤害以及经济上的损失，由过错方依法承担所有责任，一概与开维教育及合作单位无关。"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "stress"
	              ],
	              "attr": {
	                "value": "6.    其他条款"
	              }
	            },
	            {
	              "type": "text",
	              "style": {
	                "fontSize": 34,
	                "color": "#444444",
	                "lineHeight": 45
	              },
	              "attr": {
	                "value": "6.1   协议的完整性：本《协议》以及第三方授权软件或技术的使用协议和许可条款，共同构成了本“软件”及其支持服务的完整协议.\n       6.2   本《协议》所定的任何条款的部分或全部无效者，不影响其它条款的效力.       \n       6.3   本《协议》的所有标题仅仅是为了醒目及阅读方便，本身并无实际涵义，不能作为本《协议》涵义解释之依据.\n       6.4   本《协议》签订地为深圳。本《协议》的解释、效力及纠纷的解决，适用于中华人民共和国法律。若用户和开维教育之间发生任何纠纷或争议，首先应友好协商解决，协商不成的，用户在此完全同意将纠纷或争议提交开维教育所在地即深圳市有管辖权的人民法院管辖。\n       本《协议》版权由开维教育所有，开维教育保留一切解释权利。开维教育等本文中提及的软件和服务名称为开维教育的注册商标或商标，受法律保护。"
	              }
	            },
	            {
	              "type": "div",
	              "style": {
	                "height": 80,
	                "display": "flex",
	                "alignItems": "center",
	                "justifyContent": "center"
	              },
	              "children": [
	                {
	                  "type": "text",
	                  "style": {
	                    "fontSize": 45
	                  },
	                  "attr": {
	                    "value": "开维教育"
	                  }
	                }
	              ]
	            }
	          ]
	        },
	        {
	          "type": "scroller"
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 226 */
/***/ function(module, exports) {

	module.exports = {
	  "content": {
	    "color": "#353535",
	    "backgroundColor": "#ffffff",
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0
	  },
	  "stress": {
	    "textAlign": "left",
	    "fontWeight": "bold",
	    "fontSize": 40,
	    "lineHeight": 70
	  },
	  "title_name": {
	    "width": 750,
	    "height": 100,
	    "color": "#000000",
	    "fontWeight": "bold",
	    "display": "flex",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "textOverflow": "ellipsis",
	    "lines": 1
	  },
	  "lead": {
	    "width": 750,
	    "padding": 25,
	    "paddingTop": 0,
	    "color": "#999999"
	  }
	}

/***/ },
/* 227 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	__webpack_require__(80);
	var Utils = __webpack_require__(129);
	module.exports = {
	    data: function () {return {
	        navBarHeight: 130
	    }},
	    created: function created() {
	        this.headLeft = Utils.ip + 'yjpt/images/id_right_bg.png';
	        this.$on('naviBar.leftItem.click', function (e) {
	            Utils.navigate.pop(this, 'true');
	        });
	    }
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);