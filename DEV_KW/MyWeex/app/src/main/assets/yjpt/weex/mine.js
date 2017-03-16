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

	__webpack_require__(166)
	var __weex_template__ = __webpack_require__(286)
	var __weex_style__ = __webpack_require__(287)
	var __weex_script__ = __webpack_require__(288)

	__weex_define__('@weex-component/1821e1b0dd0e4f4c1a6f4f95e4638b7d', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/1821e1b0dd0e4f4c1a6f4f95e4638b7d',undefined,undefined)

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
/* 16 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(17);
	__webpack_require__(21);
	__webpack_require__(25);
	__webpack_require__(29);
	__webpack_require__(33);
	__webpack_require__(37);
	__webpack_require__(78);
	__webpack_require__(82);
	__webpack_require__(86);
	__webpack_require__(90);
	__webpack_require__(91);


/***/ },
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(18)
	var __weex_style__ = __webpack_require__(19)
	var __weex_script__ = __webpack_require__(20)

	__weex_define__('@weex-component/wxc-button', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 18 */
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
/* 19 */
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
/* 20 */
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
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(22)
	var __weex_style__ = __webpack_require__(23)
	var __weex_script__ = __webpack_require__(24)

	__weex_define__('@weex-component/wxc-hn', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 22 */
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
/* 23 */
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
/* 24 */
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
/* 25 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(26)
	var __weex_style__ = __webpack_require__(27)
	var __weex_script__ = __webpack_require__(28)

	__weex_define__('@weex-component/wxc-list-item', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 26 */
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
/* 27 */
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
/* 28 */
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
/* 29 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(30)
	var __weex_style__ = __webpack_require__(31)
	var __weex_script__ = __webpack_require__(32)

	__weex_define__('@weex-component/wxc-panel', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 30 */
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
/* 31 */
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
/* 32 */
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
/* 33 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(34)
	var __weex_style__ = __webpack_require__(35)
	var __weex_script__ = __webpack_require__(36)

	__weex_define__('@weex-component/wxc-tip', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 34 */
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
/* 35 */
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
/* 36 */
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
/* 37 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(38)
	var __weex_style__ = __webpack_require__(39)
	var __weex_script__ = __webpack_require__(40)

	__weex_define__('@weex-component/wxc-countdown', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 38 */
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
/* 39 */
/***/ function(module, exports) {

	module.exports = {
	  "wrap": {
	    "overflow": "hidden"
	  }
	}

/***/ },
/* 40 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _assign = __webpack_require__(41);

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
/* 41 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(42), __esModule: true };

/***/ },
/* 42 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(43);
	module.exports = __webpack_require__(46).Object.assign;

/***/ },
/* 43 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.1 Object.assign(target, source)
	var $export = __webpack_require__(44);

	$export($export.S + $export.F, 'Object', {assign: __webpack_require__(59)});

/***/ },
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
/* 59 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.2.1 Object.assign(target, source, ...)
	var getKeys  = __webpack_require__(60)
	  , gOPS     = __webpack_require__(75)
	  , pIE      = __webpack_require__(76)
	  , toObject = __webpack_require__(77)
	  , IObject  = __webpack_require__(64)
	  , $assign  = Object.assign;

	// should work with symbols and should have deterministic property order (V8 bug)
	module.exports = !$assign || __webpack_require__(55)(function(){
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
/* 78 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(79)
	var __weex_style__ = __webpack_require__(80)
	var __weex_script__ = __webpack_require__(81)

	__weex_define__('@weex-component/wxc-marquee', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 79 */
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
/* 80 */
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
/* 81 */
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
/* 82 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(83)
	var __weex_style__ = __webpack_require__(84)
	var __weex_script__ = __webpack_require__(85)

	__weex_define__('@weex-component/wxc-navbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 83 */
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
	        "color": function () {return this.rightItemColor}
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
	      "style": {
	        "padding": 100,
	        "position": "absolute",
	        "right": 28,
	        "bottom": 0
	      },
	      "events": {
	        "click": "onclickrightitem"
	      },
	      "children": [
	        {
	          "type": "image",
	          "classList": [
	            "right-image"
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
	        "padding": 100,
	        "position": "absolute",
	        "left": 0,
	        "bottom": 0
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
/* 84 */
/***/ function(module, exports) {

	module.exports = {
	  "container": {
	    "flexDirection": "row",
	    "position": "fixed",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "width": 750,
	    "zIndex": 999999
	  },
	  "right-text": {
	    "position": "absolute",
	    "bottom": 28,
	    "right": 32,
	    "textAlign": "right",
	    "fontSize": 32,
	    "fontFamily": "'Open Sans', sans-serif"
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
	    "left": 28,
	    "width": 50,
	    "height": 50
	  },
	  "right-image": {
	    "position": "absolute",
	    "bottom": 20,
	    "right": 28,
	    "width": 50,
	    "height": 50
	  }
	}

/***/ },
/* 85 */
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
/* 86 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(82)
	var __weex_template__ = __webpack_require__(87)
	var __weex_style__ = __webpack_require__(88)
	var __weex_script__ = __webpack_require__(89)

	__weex_define__('@weex-component/wxc-navpage', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 87 */
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
/* 88 */
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
/* 89 */
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
/* 90 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(91)
	var __weex_template__ = __webpack_require__(95)
	var __weex_style__ = __webpack_require__(96)
	var __weex_script__ = __webpack_require__(97)

	__weex_define__('@weex-component/wxc-tabbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 91 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(92)
	var __weex_style__ = __webpack_require__(93)
	var __weex_script__ = __webpack_require__(94)

	__weex_define__('@weex-component/wxc-tabitem', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 92 */
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
/* 93 */
/***/ function(module, exports) {

	module.exports = {
	  "container": {
	    "flex": 1,
	    "flexDirection": "column",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "height": 110,
	    "borderRightWidth": 1,
	    "borderRightColor": "#ffffff",
	    "borderRightStyle": "solid",
	    "borderTop": "none",
	    "borderBottom": "none"
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
	    "fontSize": 28
	  }
	}

/***/ },
/* 94 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    index: 0,
	    title: '',
	    titleColor: '#fff',
	    icon: '',
	    backgroundColor: '#fff'
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
/* 95 */
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
	            "titleColor": function () {return this.titleColor},
	            "backgroundColor": function () {return this.bgColor}
	          }
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 96 */
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
	    "height": 110
	  }
	}

/***/ },
/* 97 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    tabItems: [],
	    selectedIndex: 0,
	    selectedColor: '#00cc99',
	    unselectedColor: '#ffffff',
	    selectedBgColor: '#dedede',
	    unselectedBgColor: '#00cc99'
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
	          tabItem.bgColor = this.selectedBgColor;
	          tabItem.visibility = 'visible';
	        } else {
	          tabItem.icon = tabItem.image;
	          tabItem.titleColor = this.unselectedColor;
	          tabItem.bgColor = this.unselectedBgColor;
	          tabItem.visibility = 'hidden';
	        }
	      }
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ },
/* 98 */
/***/ function(module, exports) {

	
	var Utils = {
	  dir : 'yjpt',
	  //ip : 'http://127.0.0.1:12580/',
	  // ip : 'http://192.168.8.114:12680/',
	  // ip : 'http://192.168.8.6:8180/',
	   ip : 'http://www.yuertong.com/',

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
/* 143 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(144), __esModule: true };

/***/ },
/* 144 */
/***/ function(module, exports, __webpack_require__) {

	var core  = __webpack_require__(46)
	  , $JSON = core.JSON || (core.JSON = {stringify: JSON.stringify});
	module.exports = function stringify(it){ // eslint-disable-line no-unused-vars
	  return $JSON.stringify.apply($JSON, arguments);
	};

/***/ },
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
/* 166 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(167)
	var __weex_style__ = __webpack_require__(168)
	var __weex_script__ = __webpack_require__(169)

	__weex_define__('@weex-component/wxc-citypicker', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 167 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "city_picker"
	  ],
	  "children": [
	    {
	      "type": "scroller",
	      "classList": [
	        "scroll_wrap",
	        "province_scroller"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "city_wrap"
	          ],
	          "repeat": function () {return this.cityData},
	          "style": {
	            "backgroundColor": function () {return this.backgroundColor}
	          },
	          "children": [
	            {
	              "type": "div",
	              "classList": [
	                "province"
	              ],
	              "id": function () {return 'province' + (this.$index)},
	              "events": {
	                "click": function ($event) {this.selecteProvince(this.name,this.$index,$event)}
	              },
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "name",
	                    "province_name"
	                  ],
	                  "style": {
	                    "color": function () {return this.color}
	                  },
	                  "attr": {
	                    "value": function () {return this.name}
	                  }
	                }
	              ]
	            }
	          ]
	        }
	      ]
	    },
	    {
	      "type": "scroller",
	      "classList": [
	        "city_scroller"
	      ],
	      "shown": function () {return this.cities.length>0},
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "cities"
	          ],
	          "repeat": function () {return this.cities},
	          "style": {
	            "backgroundColor": function () {return this.backgroundColor}
	          },
	          "events": {
	            "click": function ($event) {this.selecteCity(this.name,this.$index,$event)}
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "name",
	                "province_name"
	              ],
	              "style": {
	                "color": function () {return this.color}
	              },
	              "attr": {
	                "value": function () {return this.name}
	              }
	            }
	          ]
	        }
	      ]
	    },
	    {
	      "type": "scroller",
	      "classList": [
	        "area_scroller"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "area",
	            "cities"
	          ],
	          "repeat": function () {return this.areaList},
	          "style": {
	            "backgroundColor": function () {return this.backgroundColor}
	          },
	          "events": {
	            "click": function ($event) {this.selecteArea(this.name,this.$index,$event)}
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "name",
	                "province_name"
	              ],
	              "style": {
	                "color": function () {return this.color}
	              },
	              "attr": {
	                "value": function () {return this.name}
	              }
	            }
	          ]
	        }
	      ]
	    },
	    {
	      "type": "div",
	      "classList": [
	        "picker_opt"
	      ],
	      "events": {
	        "click": "pickerCancle"
	      },
	      "children": [
	        {
	          "type": "text",
	          "classList": [
	            "picker_btn"
	          ],
	          "attr": {
	            "value": "取消"
	          }
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 168 */
/***/ function(module, exports) {

	module.exports = {
	  "city_picker": {
	    "position": "fixed",
	    "left": 0,
	    "bottom": 0,
	    "top": 0,
	    "right": 0,
	    "width": 750,
	    "zIndex": 99999,
	    "backgroundColor": "rgba(0,0,0,0.7)",
	    "flexDirection": "row",
	    "paddingTop": 130,
	    "paddingBottom": 110
	  },
	  "city_wrap": {
	    "flexDirection": "row"
	  },
	  "name": {
	    "fontSize": 36
	  },
	  "cities": {
	    "padding": 30,
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#dddddd",
	    "borderBottomStyle": "solid",
	    "borderRightWidth": 1,
	    "borderRightColor": "#dddddd",
	    "borderRightStyle": "solid",
	    "alignItems": "center"
	  },
	  "city_list": {
	    "flexDirection": "column",
	    "flexWrap": "wrap"
	  },
	  "province": {
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#dddddd",
	    "borderBottomStyle": "solid",
	    "borderRightWidth": 1,
	    "borderRightColor": "#dddddd",
	    "borderRightStyle": "solid",
	    "padding": 30,
	    "width": 250,
	    "textAlign": "center",
	    "boxSizing": "border-box",
	    "alignItems": "center"
	  },
	  "scroll_wrap": {
	    "padding": 0,
	    "overflowX": "hidden",
	    "overflowY": "scroll"
	  },
	  "city_scroller": {
	    "padding": 0,
	    "overflowX": "hidden",
	    "width": 250,
	    "overflowY": "scroll"
	  },
	  "area_scroller": {
	    "padding": 0,
	    "overflowX": "hidden",
	    "textAlign": "left",
	    "flex": 1,
	    "overflowY": "scroll"
	  },
	  "picker_opt": {
	    "position": "absolute",
	    "left": 0,
	    "bottom": 0,
	    "right": 0,
	    "height": 110,
	    "width": 750,
	    "color": "#ffffff",
	    "backgroundColor": "#ffffff",
	    "zIndex": 1000000
	  },
	  "picker_btn": {
	    "fontSize": 38,
	    "textAlign": "center",
	    "padding": 30,
	    "borderWidth": 1,
	    "borderColor": "#00cc99",
	    "borderStyle": "solid",
	    "color": "#000000"
	  }
	}

/***/ },
/* 169 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _stringify = __webpack_require__(143);

	var _stringify2 = _interopRequireDefault(_stringify);

	var _typeof2 = __webpack_require__(102);

	var _typeof3 = _interopRequireDefault(_typeof2);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	__webpack_require__(16);
	var dom = __weex_require__('@weex-module/dom');
	var storage = __weex_require__('@weex-module/storage');
	var modal = __weex_require__('@weex-module/modal');
	var Utils = __webpack_require__(98);
	module.exports = {
	                  data: function () {return {
	                                    navBarHeight: 130,
	                                    dir: 'yjpt',
	                                    userId: '',
	                                    leftItemImg: 'yjpt/images/sys.png',
	                                    cityData: [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "北京市",
	                                                      "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "北京市", "area": ["东城区", "西城区", "崇文区", "宣武区", "朝阳区", "丰台区", "石景山区", "海淀区", "门头沟区", "房山区", "通州区", "顺义区", "昌平区", "大兴区", "平谷区", "怀柔区", "密云县", "延庆县"]
	                                                      }]
	                                    }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "天津市", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "天津市", "area": ["和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区", "西青区", "津南区", "北辰区", "武清区", "宝坻区", "宁河县", "静海县", "蓟  县"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "河北省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "石家庄市", "area": ["长安区", "桥东区", "桥西区", "新华区", "郊  区", "井陉矿区", "井陉县", "正定县", "栾城县", "行唐县", "灵寿县", "高邑县", "深泽县", "赞皇县", "无极县", "平山县", "元氏县", "赵  县", "辛集市", "藁", "晋州市", "新乐市", "鹿泉市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "唐山市", "area": ["路南区", "路北区", "古冶区", "开平区", "新  区", "丰润县", "滦  县", "滦南县", "乐亭县", "迁西县", "玉田县", "唐海县", "遵化市", "丰南市", "迁安市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "秦皇岛市", "area": ["海港区", "山海关区", "北戴河区", "青龙满族自治县", "昌黎县", "抚宁县", "卢龙县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "邯郸市", "area": ["邯山区", "丛台区", "复兴区", "峰峰矿区", "邯郸县", "临漳县", "成安县", "大名县", "涉  县", "磁  县", "肥乡县", "永年县", "邱  县", "鸡泽县", "广平县", "馆陶县", "魏  县", "曲周县", "武安市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "邢台市", "area": ["桥东区", "桥西区", "邢台县", "临城县", "内丘县", "柏乡县", "隆尧县", "任  县", "南和县", "宁晋县", "巨鹿县", "新河县", "广宗县", "平乡县", "威  县", "清河县", "临西县", "南宫市", "沙河市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "保定市", "area": ["新市区", "北市区", "南市区", "满城县", "清苑县", "涞水县", "阜平县", "徐水县", "定兴县", "唐  县", "高阳县", "容城县", "涞源县", "望都县", "安新县", "易  县", "曲阳县", "蠡  县", "顺平县", "博野", "雄县", "涿州市", "定州市", "安国市", "高碑店市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "张家口市", "area": ["桥东区", "桥西区", "宣化区", "下花园区", "宣化县", "张北县", "康保县", "沽源县", "尚义县", "蔚  县", "阳原县", "怀安县", "万全县", "怀来县", "涿鹿县", "赤城县", "崇礼县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "承德市", "area": ["双桥区", "双滦区", "鹰手营子矿区", "承德县", "兴隆县", "平泉县", "滦平县", "隆化县", "丰宁满族自治县", "宽城满族自治县", "围场满族蒙古族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "沧州市", "area": ["新华区", "运河区", "沧县", "青  县", "东光县", "海兴县", "盐山县", "肃宁县", "南皮县", "吴桥县", "献  县", "孟村回族自治县", "泊头市", "任丘市", "黄骅市", "河间市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "廊坊市", "area": ["安次区", "固安县", "永清县", "香河县", "大城县", "文安县", "大厂回族自治县", "霸州市", "三河市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "衡水市", "area": ["桃城区", "枣强县", "武邑县", "武强县", "饶阳县", "安平县", "故城县", "景  县", "阜城县", "冀州市", "深州市"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "山西省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "太原市", "area": ["小店区", "迎泽区", "杏花岭区", "尖草坪区", "万柏林区", "晋源区", "清徐县", "阳曲县", "娄烦县", "古交市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "大同市", "area": ["城区", "矿区", "南郊区", "新荣区", "阳高县", "天镇县", "广灵县", "灵丘县", "浑源县", "左云县", "大同县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阳泉市", "area": ["城区", "矿区", "郊区", "平定县", "盂县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "长治市", "area": ["城区", "郊区", "长治县", "襄垣县", "屯留县", "平顺县", "黎城县", "壶关县", "长子县", "武乡县", "沁  县", "沁源县", "潞城市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "晋城市", "area": ["城区", "沁水县", "阳城县", "陵川县", "泽州县", "高平市"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "朔州市", "area": ["朔城区", "平鲁区", "山阴县", "应县", "右玉县", "怀仁县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "忻州市", "area": ["忻府区", "原平市", "定襄县", "五台县", "代县", "繁峙县", "宁武县", "静乐县", "神池县", "五寨县", "岢岚县", "河曲县", "保德县", "偏关县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吕梁市", "area": ["离石区", "孝义市", "汾阳市", "文水县", "交城县", "兴县", "临县", "柳林县", "石楼县", "岚  县", "方山县", "中阳县", "交口县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "晋中市", "area": ["榆次市", "介休市", "榆社县", "左权县", "和顺县", "昔阳县", "寿阳县", "太谷县", "祁  县", "平遥县", "灵石县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "临汾市", "area": ["临汾市", "侯马市", "霍州市", "曲沃县", "翼城县", "襄汾县", "洪洞县", "古县", "安泽县", "浮山县", "吉县", "乡宁县", "蒲县", "大宁县", "永和县", "隰县", "汾西县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "运城市", "area": ["运城市", "永济市", "河津市", "芮城县", "临猗县", "万荣县", "新绛县", "稷山县", "闻喜县", "夏县", "绛县", "平陆县", "垣曲县"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "内蒙古自治区", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "呼和浩特市", "area": ["新城区", "回民区", "玉泉区", "郊  区", "土默特左旗", "托克托县", "和林格尔县", "清水河县", "武川县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "包头市", "area": ["东河区", "昆都伦区", "青山区", "石拐矿区", "白云矿区", "郊区", "土默特右旗", "固阳县", "达尔罕茂明安联合旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "乌海市", "area": ["海勃湾区", "海南区", "乌达区"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "赤峰市", "area": ["红山区", "元宝山区", "松山区", "阿鲁科尔沁旗", "巴林左旗", "巴林右旗", "林西县", "克什克腾旗", "翁牛特旗", "喀喇沁旗", "宁城县", "敖汉旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "呼伦贝尔市", "area": ["海拉尔市", "满洲里市", "扎兰屯市", "牙克石市", "根河市", "额尔古纳市", "阿荣旗", "莫力达瓦达斡尔族自治旗", "鄂伦春自治旗", "鄂温克族自治旗", "新巴尔虎右旗", "新巴尔虎左旗", "陈巴尔虎旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "兴安盟市", "area": ["乌兰浩特市", "阿尔山市", "科尔沁右翼前旗", "科尔沁右翼中旗", "扎赉特旗", "突泉县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "通辽市", "area": ["科尔沁区", "霍林郭勒市", "科尔沁左翼中旗", "科尔沁左翼后旗", "开鲁县", "库伦旗", "奈曼旗", "扎鲁特旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "锡林郭勒盟市", "area": ["二连浩特市", "锡林浩特市", "阿巴嘎旗", "苏尼特左旗", "苏尼特右旗", "东乌珠穆沁旗", "西乌珠穆沁旗", "太仆寺旗", "镶黄旗", "正镶白旗", "正蓝旗", "多伦县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "乌兰察布盟市", "area": ["集宁市", "丰镇市", "卓资县", "化德县", "商都县", "兴和县", "凉城县", "察哈尔右翼前旗", "察哈尔右翼中旗", "察哈尔右翼后旗", "四子王旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "伊克昭盟市", "area": ["东胜市", "达拉特旗", "准格尔旗", "鄂托克前旗", "鄂托克旗", "杭锦旗", "乌审旗", "伊金霍洛旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "巴彦淖尔盟市", "area": ["临河市", "五原县", "磴口县", "乌拉特前旗", "乌拉特中旗", "乌拉特后旗", "杭锦后旗"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阿拉善盟市", "area": ["阿拉善左旗", "阿拉善右旗", "额济纳旗"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "辽宁省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "沈阳市", "area": ["沈河区", "皇姑区", "和平区", "大东区", "铁西区", "苏家屯区", "东陵区", "于洪区", "新民市", "法库县", "辽中县", "康平县", "新城子区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "大连市", "area": ["西岗区", "中山区", "沙河口区", "甘井子区", "旅顺口区", "金州区", "瓦房店市", "普兰店市", "庄河市", "长海县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鞍山市", "area": ["铁东区", "铁西区", "立山区", "千山区", "海城市", "台安县", "岫岩满族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "抚顺市", "area": ["顺城区", "新抚区", "东洲区", "望花区", "抚顺县", "清原满族自治县", "新宾满族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "本溪市", "area": ["平山区", "明山区", "溪湖区", "南芬区", "本溪满族自治县", "桓仁满族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "丹东市", "area": ["振兴区", "元宝区", "振安区", "东港市", "凤城市", "宽甸满族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "锦州市", "area": ["太和区", "古塔区", "凌河区", "凌海市", "黑山县", "义县", "北宁市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "营口市", "area": ["站前区", "西市区", "鲅鱼圈区", "老边区", "大石桥市", "盖州市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阜新市", "area": ["海州区", "新邱区", "太平区", "清河门区", "细河区", "彰武县", "阜新蒙古族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "辽阳市", "area": ["白塔区", "文圣区", "宏伟区", "太子河区", "弓长岭区", "灯塔市", "辽阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "盘锦市", "area": ["双台子区", "兴隆台区", "盘山县", "大洼县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "铁岭市", "area": ["银州区", "清河区", "调兵山市", "开原市", "铁岭县", "昌图县", "西丰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "朝阳市", "area": ["双塔区", "龙城区", "凌源市", "北票市", "朝阳县", "建平县", "喀喇沁左翼蒙古族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "葫芦岛市", "area": ["龙港区", "南票区", "连山区", "兴城市", "绥中县", "建昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吉林省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "长春市", "area": ["朝阳区", "宽城区", "二道区", "南关区", "绿园区", "双阳区", "九台市", "榆树市", "德惠市", "农安县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吉林市", "area": ["船营区", "昌邑区", "龙潭区", "丰满区", "舒兰市", "桦甸市", "蛟河市", "磐石市", "永吉县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "四平市", "area": ["铁西区", "铁东区", "公主岭市", "双辽市", "梨树县", "伊通满族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "辽源市", "area": ["龙山区", "西安区", "东辽县", "东丰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "通化市", "area": ["东昌区", "二道江区", "梅河口市", "集安市", "通化县", "辉南县", "柳河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "白山市", "area": ["八道江区", "江源区", "临江市", "靖宇县", "抚松县", "长白朝鲜族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "松原市", "area": ["宁江区", "乾安县", "长岭县", "扶余县", "前郭尔罗斯蒙古族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "白城市", "area": ["洮北区", "大安市", "洮南市", "镇赉县", "通榆县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "延边朝鲜族自治州", "area": ["延吉市", "图们市", "敦化市", "龙井市", "珲春市", "和龙市", "安图县", "汪清县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黑龙江省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "哈尔滨市", "area": ["松北区", "道里区", "南岗区", "平房区", "香坊区", "道外区", "呼兰区", "阿城区", "双城市", "尚志市", "五常市", "宾县", "方正县", "通河县", "巴彦县", "延寿县", "木兰县", "依兰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "齐齐哈尔市", "area": ["龙沙区", "昂昂溪区", "铁锋区", "建华区", "富拉尔基区", "碾子山区", "梅里斯达斡尔族区", "讷河市", "富裕县", "拜泉县", "甘南县", "依安县", "克山县", "泰来县", "克东县", "龙江县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鹤岗市", "area": ["兴山区", "工农区", "南山区", "兴安区", "向阳区", "东山区", "萝北县", "绥滨县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "双鸭山市", "area": ["尖山区", "岭东区", "四方台区", "宝山区", "集贤县", "宝清县", "友谊县", "饶河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鸡西市", "area": ["鸡冠区", "恒山区", "城子河区", "滴道区", "梨树区", "麻山区", "密山市", "虎林市", "鸡东县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "大庆市", "area": ["萨尔图区", "红岗区", "龙凤区", "让胡路区", "大同区", "林甸县", "肇州县", "肇源县", "杜尔伯特蒙古族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "伊春市", "area": ["伊春区", "带岭区", "南岔区", "金山屯区", "西林区", "美溪区", "乌马河区", "翠峦区", "友好区", "上甘岭区", "五营区", "红星区", "新青区", "汤旺河区", "乌伊岭区", "铁力市", "嘉荫县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "牡丹江市", "area": ["爱民区", "东安区", "阳明区", "西安区", "绥芬河市", "宁安市", "海林市", "穆棱市", "林口县", "东宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "佳木斯市", "area": ["向阳区", "前进区", "东风区", "郊区", "同江市", "富锦市", "桦川县", "抚远县", "桦南县", "汤原县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "七台河市", "area": ["桃山区", "新兴区", "茄子河区", "勃利县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黑河市", "area": ["爱辉区", "北安市", "五大连池市", "逊克县", "嫩江县", "孙吴县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "绥化市", "area": ["北林区", "安达市", "肇东市", "海伦市", "绥棱县", "兰西县", "明水县", "青冈县", "庆安县", "望奎县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "大兴安岭地区", "area": ["呼玛县", "塔河县", "漠河县", "大兴安岭辖区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "上海市", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "上海市", "area": ["黄浦区", "卢湾区", "徐汇区", "长宁区", "静安区", "普陀区", "闸北区", "虹口区", "杨浦区", "宝山区", "闵行区", "嘉定区", "松江区", "金山区", "青浦区", "南汇区", "奉贤区", "浦东新区", "崇明县", "其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "江苏省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南京市", "area": ["玄武区", "白下区", "秦淮区", "建邺区", "鼓楼区", "下关区", "栖霞区", "雨花台区", "浦口区", "江宁区", "六合区", "溧水县", "高淳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "苏州市", "area": ["金阊区", "平江区", "沧浪区", "虎丘区", "吴中区", "相城区", "常熟市", "张家港市", "昆山市", "吴江市", "太仓市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "无锡市", "area": ["崇安区", "南长区", "北塘区", "滨湖区", "锡山区", "惠山区", "江阴市", "宜兴市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "常州市", "area": ["钟楼区", "天宁区", "戚墅堰区", "新北区", "武进区", "金坛市", "溧阳市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "镇江市", "area": ["京口区", "润州区", "丹徒区", "丹阳市", "扬中市", "句容市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南通市", "area": ["崇川区", "港闸区", "通州市", "如皋市", "海门市", "启东市", "海安县", "如东县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "泰州市", "area": ["海陵区", "高港区", "姜堰市", "泰兴市", "靖江市", "兴化市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "扬州市", "area": ["广陵区", "维扬区", "邗江区", "江都市", "仪征市", "高邮市", "宝应县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "盐城市", "area": ["亭湖区", "盐都区", "大丰市", "东台市", "建湖县", "射阳县", "阜宁县", "滨海县", "响水县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "连云港市", "area": ["新浦区", "海州区", "连云区", "东海县", "灌云县", "赣榆县", "灌南县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "徐州市", "area": ["云龙区", "鼓楼区", "九里区", "泉山区", "贾汪区", "邳州市", "新沂市", "铜山县", "睢宁县", "沛县", "丰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "淮安市", "area": ["清河区", "清浦区", "楚州区", "淮阴区", "涟水县", "洪泽县", "金湖县", "盱眙县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宿迁市", "area": ["宿城区", "宿豫区", "沭阳县", "泗阳县", "泗洪县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "浙江省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "杭州市", "area": ["拱墅区", "西湖区", "上城区", "下城区", "江干区", "滨江区", "余杭区", "萧山区", "建德市", "富阳市", "临安市", "桐庐县", "淳安县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宁波市", "area": ["海曙区", "江东区", "江北区", "镇海区", "北仑区", "鄞州区", "余姚市", "慈溪市", "奉化市", "宁海县", "象山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "温州市", "area": ["鹿城区", "龙湾区", "瓯海区", "瑞安市", "乐清市", "永嘉县", "洞头县", "平阳县", "苍南县", "文成县", "泰顺县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "嘉兴市", "area": ["秀城区", "秀洲区", "海宁市", "平湖市", "桐乡市", "嘉善县", "海盐县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湖州市", "area": ["吴兴区", "南浔区", "长兴县", "德清县", "安吉县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "绍兴市", "area": ["越城区", "诸暨市", "上虞市", "嵊州市", "绍兴县", "新昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "金华市", "area": ["婺城区", "金东区", "兰溪市", "义乌市", "东阳市", "永康市", "武义县", "浦江县", "磐安县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "衢州市", "area": ["柯城区", "衢江区", "江山市", "龙游县", "常山县", "开化县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "舟山市", "area": ["定海区", "普陀区", "岱山县", "嵊泗县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "台州市", "area": ["椒江区", "黄岩区", "路桥区", "临海市", "温岭市", "玉环县", "天台县", "仙居县", "三门县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "丽水市", "area": ["莲都区", "龙泉市", "缙云县", "青田县", "云和县", "遂昌县", "松阳县", "庆元县", "景宁畲族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "安徽省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "合肥市", "area": ["庐阳区", "瑶海区", "蜀山区", "包河区", "长丰县", "肥东县", "肥西县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "芜湖市", "area": ["镜湖区", "弋江区", "鸠江区", "三山区", "芜湖县", "南陵县", "繁昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "蚌埠市", "area": ["蚌山区", "龙子湖区", "禹会区", "淮上区", "怀远县", "固镇县", "五河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "淮南市", "area": ["田家庵区", "大通区", "谢家集区", "八公山区", "潘集区", "凤台县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "马鞍山市", "area": ["雨山区", "花山区", "金家庄区", "当涂县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "淮北市", "area": ["相山区", "杜集区", "烈山区", "濉溪县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "铜陵市", "area": ["铜官山区", "狮子山区", "郊区", "铜陵县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "安庆市", "area": ["迎江区", "大观区", "宜秀区", "桐城市", "宿松县", "枞阳县", "太湖县", "怀宁县", "岳西县", "望江县", "潜山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黄山市", "area": ["屯溪区", "黄山区", "徽州区", "休宁县", "歙县", "祁门县", "黟县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "滁州市", "area": ["琅琊区", "南谯区", "天长市", "明光市", "全椒县", "来安县", "定远县", "凤阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阜阳市", "area": ["颍州区", "颍东区", "颍泉区", "界首市", "临泉县", "颍上县", "阜南县", "太和县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宿州市", "area": ["埇桥区", "萧县", "泗县", "砀山县", "灵璧县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "巢湖市", "area": ["居巢区", "含山县", "无为县", "庐江县", "和县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "六安市", "area": ["金安区", "裕安区", "寿县", "霍山县", "霍邱县", "舒城县", "金寨县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "亳州市", "area": ["谯城区", "利辛县", "涡阳县", "蒙城县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "池州市", "area": ["贵池区", "东至县", "石台县", "青阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宣城市", "area": ["宣州区", "宁国市", "广德县", "郎溪县", "泾县", "旌德县", "绩溪县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "福建省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "福州市", "area": ["鼓楼区", "台江区", "仓山区", "马尾区", "晋安区", "福清市", "长乐市", "闽侯县", "闽清县", "永泰县", "连江县", "罗源县", "平潭县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "厦门市", "area": ["思明区", "海沧区", "湖里区", "集美区", "同安区", "翔安区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "莆田市", "area": ["城厢区", "涵江区", "荔城区", "秀屿区", "仙游县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "三明市", "area": ["梅列区", "三元区", "永安市", "明溪县", "将乐县", "大田县", "宁化县", "建宁县", "沙县", "尤溪县", "清流县", "泰宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "泉州市", "area": ["鲤城区", "丰泽区", "洛江区", "泉港区", "石狮市", "晋江市", "南安市", "惠安县", "永春县", "安溪县", "德化县", "金门县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "漳州市", "area": ["芗城区", "龙文区", "龙海市", "平和县", "南靖县", "诏安县", "漳浦县", "华安县", "东山县", "长泰县", "云霄县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南平市", "area": ["延平区", "建瓯市", "邵武市", "武夷山市", "建阳市", "松溪县", "光泽县", "顺昌县", "浦城县", "政和县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "龙岩市", "area": ["新罗区", "漳平市", "长汀县", "武平县", "上杭县", "永定县", "连城县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宁德市", "area": ["蕉城区", "福安市", "福鼎市", "寿宁县", "霞浦县", "柘荣县", "屏南县", "古田县", "周宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "江西省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南昌市", "area": ["东湖区", "西湖区", "青云谱区", "湾里区", "青山湖区", "新建县", "南昌县", "进贤县", "安义县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "景德镇市", "area": ["珠山区", "昌江区", "乐平市", "浮梁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "萍乡市", "area": ["安源区", "湘东区", "莲花县", "上栗县", "芦溪县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "九江市", "area": ["浔阳区", "庐山区", "瑞昌市", "九江县", "星子县", "武宁县", "彭泽县", "永修县", "修水县", "湖口县", "德安县", "都昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "新余市", "area": ["渝水区", "分宜县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鹰潭市", "area": ["月湖区", "贵溪市", "余江县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "赣州市", "area": ["章贡区", "瑞金市", "南康市", "石城县", "安远县", "赣县", "宁都县", "寻乌县", "兴国县", "定南县", "上犹县", "于都县", "龙南县", "崇义县", "信丰县", "全南县", "大余县", "会昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吉安市", "area": ["吉州区", "青原区", "井冈山市", "吉安县", "永丰县", "永新县", "新干县", "泰和县", "峡江县", "遂川县", "安福县", "吉水县", "万安县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宜春市", "area": ["袁州区", "丰城市", "樟树市", "高安市", "铜鼓县", "靖安县", "宜丰县", "奉新县", "万载县", "上高县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "抚州市", "area": ["临川区", "南丰县", "乐安县", "金溪县", "南城县", "东乡县", "资溪县", "宜黄县", "广昌县", "黎川县", "崇仁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "上饶市", "area": ["信州区", "德兴市", "上饶县", "广丰县", "鄱阳县", "婺源县", "铅山县", "余干县", "横峰县", "弋阳县", "玉山县", "万年县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "山东省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "济南市", "area": ["市中区", "历下区", "天桥区", "槐荫区", "历城区", "长清区", "章丘市", "平阴县", "济阳县", "商河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "青岛市", "area": ["市南区", "市北区", "城阳区", "四方区", "李沧区", "黄岛区", "崂山区", "胶南市", "胶州市", "平度市", "莱西市", "即墨市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "淄博市", "area": ["张店区", "临淄区", "淄川区", "博山区", "周村区", "桓台县", "高青县", "沂源县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "枣庄市", "area": ["市中区", "山亭区", "峄城区", "台儿庄区", "薛城区", "滕州市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "东营市", "area": ["东营区", "河口区", "垦利县", "广饶县", "利津县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "烟台市", "area": ["芝罘区", "福山区", "牟平区", "莱山区", "龙口市", "莱阳市", "莱州市", "招远市", "蓬莱市", "栖霞市", "海阳市", "长岛县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "潍坊市", "area": ["潍城区", "寒亭区", "坊子区", "奎文区", "青州市", "诸城市", "寿光市", "安丘市", "高密市", "昌邑市", "昌乐县", "临朐县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "济宁市", "area": ["市中区", "任城区", "曲阜市", "兖州市", "邹城市", "鱼台县", "金乡县", "嘉祥县", "微山县", "汶上县", "泗水县", "梁山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "泰安市", "area": ["泰山区", "岱岳区", "新泰市", "肥城市", "宁阳县", "东平县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "威海市", "area": ["环翠区", "乳山市", "文登市", "荣成市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "日照市", "area": ["东港区", "岚山区", "五莲县", "莒县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "莱芜市", "area": ["莱城区", "钢城区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "临沂市", "area": ["兰山区", "罗庄区", "河东区", "沂南县", "郯城县", "沂水县", "苍山县", "费县", "平邑县", "莒南县", "蒙阴县", "临沭县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "德州市", "area": ["德城区", "乐陵市", "禹城市", "陵县", "宁津县", "齐河县", "武城县", "庆云县", "平原县", "夏津县", "临邑县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "聊城市", "area": ["东昌府区", "临清市", "高唐县", "阳谷县", "茌平县", "莘县", "东阿县", "冠县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "滨州市", "area": ["滨城区", "邹平县", "沾化县", "惠民县", "博兴县", "阳信县", "无棣县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "菏泽市", "area": ["牡丹区", "鄄城县", "单县", "郓城县", "曹县", "定陶县", "巨野县", "东明县", "成武县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "河南省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "郑州市", "area": ["中原区", "金水区", "二七区", "管城回族区", "上街区", "惠济区", "巩义市", "新郑市", "新密市", "登封市", "荥阳市", "中牟县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "开封市", "area": ["鼓楼区", "龙亭区", "顺河回族区", "禹王台区", "金明区", "开封县", "尉氏县", "兰考县", "杞县", "通许县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "洛阳市", "area": ["西工区", "老城区", "涧西区", "瀍河回族区", "洛龙区", "吉利区", "偃师市", "孟津县", "汝阳县", "伊川县", "洛宁县", "嵩县", "宜阳县", "新安县", "栾川县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "平顶山市", "area": ["新华区", "卫东区", "湛河区", "石龙区", "汝州市", "舞钢市", "宝丰县", "叶县", "郏县", "鲁山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "安阳市", "area": ["北关区", "文峰区", "殷都区", "龙安区", "林州市", "安阳县", "滑县", "内黄县", "汤阴县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鹤壁市", "area": ["淇滨区", "山城区", "鹤山区", "浚县", "淇县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "新乡市", "area": ["卫滨区", "红旗区", "凤泉区", "牧野区", "卫辉市", "辉县市", "新乡县", "获嘉县", "原阳县", "长垣县", "封丘县", "延津县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "焦作市", "area": ["解放区", "中站区", "马村区", "山阳区", "沁阳市", "孟州市", "修武县", "温县", "武陟县", "博爱县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "濮阳市", "area": ["华龙区", "濮阳县", "南乐县", "台前县", "清丰县", "范县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "许昌市", "area": ["魏都区", "禹州市", "长葛市", "许昌县", "鄢陵县", "襄城县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "漯河市", "area": ["源汇区", "郾城区", "召陵区", "临颍县", "舞阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "三门峡市", "area": ["湖滨区", "义马市", "灵宝市", "渑池县", "卢氏县", "陕县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南阳市", "area": ["卧龙区", "宛城区", "邓州市", "桐柏县", "方城县", "淅川县", "镇平县", "唐河县", "南召县", "内乡县", "新野县", "社旗县", "西峡县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "商丘市", "area": ["梁园区", "睢阳区", "永城市", "宁陵县", "虞城县", "民权县", "夏邑县", "柘城县", "睢县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "信阳市", "area": ["浉河区", "平桥区", "潢川县", "淮滨县", "息县", "新县", "商城县", "固始县", "罗山县", "光山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "周口市", "area": ["川汇区", "项城市", "商水县", "淮阳县", "太康县", "鹿邑县", "西华县", "扶沟县", "沈丘县", "郸城县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "驻马店市", "area": ["驿城区", "确山县", "新蔡县", "上蔡县", "西平县", "泌阳县", "平舆县", "汝南县", "遂平县", "正阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "焦作市", "area": ["济源市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湖北省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "武汉市", "area": ["江岸区", "武昌区", "江汉区", "硚口区", "汉阳区", "青山区", "洪山区", "东西湖区", "汉南区", "蔡甸区", "江夏区", "黄陂区", "新洲区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黄石市", "area": ["黄石港区", "西塞山区", "下陆区", "铁山区", "大冶市", "阳新县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "十堰市", "area": ["张湾区", "茅箭区", "丹江口市", "郧县", "竹山县", "房县", "郧西县", "竹溪县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "荆州市", "area": ["沙市区", "荆州区", "洪湖市", "石首市", "松滋市", "监利县", "公安县", "江陵县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宜昌市", "area": ["西陵区", "伍家岗区", "点军区", "猇亭区", "夷陵区", "宜都市", "当阳市", "枝江市", "秭归县", "远安县", "兴山县", "五峰土家族自治县", "长阳土家族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "襄樊市", "area": ["襄城区", "樊城区", "襄阳区", "老河口市", "枣阳市", "宜城市", "南漳县", "谷城县", "保康县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "鄂州市", "area": ["鄂城区", "华容区", "梁子湖区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "荆门市", "area": ["东宝区", "掇刀区", "钟祥市", "京山县", "沙洋县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "孝感市", "area": ["孝南区", "应城市", "安陆市", "汉川市", "云梦县", "大悟县", "孝昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黄冈市", "area": ["黄州区", "麻城市", "武穴市", "红安县", "罗田县", "浠水县", "蕲春县", "黄梅县", "英山县", "团风县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "咸宁市", "area": ["咸安区", "赤壁市", "嘉鱼县", "通山县", "崇阳县", "通城县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "随州市", "area": ["曾都区", "广水市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "恩施土家族苗族自治州", "area": ["恩施市", "利川市", "建始县", "来凤县", "巴东县", "鹤峰县", "宣恩县", "咸丰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "仙桃市", "area": ["仙桃"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "天门市", "area": ["天门"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "潜江市", "area": ["潜江"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "神农架林区", "area": ["神农架林区"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湖南省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "长沙市", "area": ["岳麓区", "芙蓉区", "天心区", "开福区", "雨花区", "浏阳市", "长沙县", "望城县", "宁乡县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "株洲市", "area": ["天元区", "荷塘区", "芦淞区", "石峰区", "醴陵市", "株洲县", "炎陵县", "茶陵县", "攸县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湘潭市", "area": ["岳塘区", "雨湖区", "湘乡市", "韶山市", "湘潭县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "衡阳市", "area": ["雁峰区", "珠晖区", "石鼓区", "蒸湘区", "南岳区", "耒阳市", "常宁市", "衡阳县", "衡东县", "衡山县", "衡南县", "祁东县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "邵阳市", "area": ["双清区", "大祥区", "北塔区", "武冈市", "邵东县", "洞口县", "新邵县", "绥宁县", "新宁县", "邵阳县", "隆回县", "城步苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "岳阳市", "area": ["岳阳楼区", "云溪区", "君山区", "临湘市", "汨罗市", "岳阳县", "湘阴县", "平江县", "华容县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "常德市", "area": ["武陵区", "鼎城区", "津市市", "澧县", "临澧县", "桃源县", "汉寿县", "安乡县", "石门县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "张家界市", "area": ["永定区", "武陵源区", "慈利县", "桑植县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "益阳市", "area": ["赫山区", "资阳区", "沅江市", "桃江县", "南县", "安化县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "郴州市", "area": ["北湖区", "苏仙区", "资兴市", "宜章县", "汝城县", "安仁县", "嘉禾县", "临武县", "桂东县", "永兴县", "桂阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "永州市", "area": ["冷水滩区", "零陵区", "祁阳县", "蓝山县", "宁远县", "新田县", "东安县", "江永县", "道县", "双牌县", "江华瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "怀化市", "area": ["鹤城区", "洪江市", "会同县", "沅陵县", "辰溪县", "溆浦县", "中方县", "新晃侗族自治县", "芷江侗族自治县", "通道侗族自治县", "靖州苗族侗族自治县", "麻阳苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "娄底市", "area": ["娄星区", "冷水江市", "涟源市", "新化县", "双峰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湘西土家族苗族自治州", "area": ["吉首市", "古丈县", "龙山县", "永顺县", "凤凰县", "泸溪县", "保靖县", "花垣县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "广东省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "广州市", "area": ["越秀区", "荔湾区", "海珠区", "天河区", "白云区", "黄埔区", "番禺区", "花都区", "南沙区", "萝岗区", "增城市", "从化市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "深圳市", "area": ["福田区", "罗湖区", "南山区", "宝安区", "龙岗区", "盐田区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "东莞市", "area": ["莞城", "常平", "塘厦", "塘厦", "塘厦", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "中山市", "area": ["中山"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "潮州市", "area": ["湘桥区", "潮安县", "饶平县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "揭阳市", "area": ["榕城区", "揭东县", "揭西县", "惠来县", "普宁市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "云浮市", "area": ["云城区", "新兴县", "郁南县", "云安县", "罗定市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "珠海市", "area": ["香洲区", "斗门区", "金湾区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "汕头市", "area": ["金平区", "濠江区", "龙湖区", "潮阳区", "潮南区", "澄海区", "南澳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "韶关市", "area": ["浈江区", "武江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "佛山市", "area": ["禅城区", "南海区", "顺德区", "三水区", "高明区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "江门市", "area": ["蓬江区", "江海区", "新会区", "恩平市", "台山市", "开平市", "鹤山市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "湛江市", "area": ["赤坎区", "霞山区", "坡头区", "麻章区", "吴川市", "廉江市", "雷州市", "遂溪县", "徐闻县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "茂名", "area": ["茂南区", "茂港区", "化州市", "信宜市", "高州市", "电白县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "肇庆市", "area": ["端州区", "鼎湖区", "高要市", "四会市", "广宁县", "怀集县", "封开县", "德庆县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "惠州市", "area": ["惠城区", "惠阳区", "博罗县", "惠东县", "龙门县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "梅州市", "area": ["梅江区", "兴宁市", "梅县", "大埔县", "丰顺县", "五华县", "平远县", "蕉岭县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "汕尾市", "area": ["城区", "陆丰市", "海丰县", "陆河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "河源市", "area": ["源城区", "紫金县", "龙川县", "连平县", "和平县", "东源县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阳江市", "area": ["江城区", "阳春市", "阳西县", "阳东县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "清远市", "area": ["清城区", "英德市", "连州市", "佛冈县", "阳山县", "清新县", "连山壮族瑶族自治县", "连南瑶族自治县", "其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "广西壮族自治区", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南宁市", "area": ["青秀区", "兴宁区", "西乡塘区", "良庆区", "江南区", "邕宁区", "武鸣县", "隆安县", "马山县", "上林县", "宾阳县", "横县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "柳州市", "area": ["城中区", "鱼峰区", "柳北区", "柳南区", "柳江县", "柳城县", "鹿寨县", "融安县", "融水苗族自治县", "三江侗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "桂林市", "area": ["象山区", "秀峰区", "叠彩区", "七星区", "雁山区", "阳朔县", "临桂县", "灵川县", "全州县", "平乐县", "兴安县", "灌阳县", "荔浦县", "资源县", "永福县", "龙胜各族自治县", "恭城瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "梧州市", "area": ["万秀区", "蝶山区", "长洲区", "岑溪市", "苍梧县", "藤县", "蒙山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "北海市", "area": ["海城区", "银海区", "铁山港区", "合浦县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "防城港市", "area": ["港口区", "防城区", "东兴市", "上思县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "钦州市", "area": ["钦南区", "钦北区", "灵山县", "浦北县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "贵港市", "area": ["港北区", "港南区", "覃塘区", "桂平市", "平南县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "玉林市", "area": ["玉州区", "北流市", "容县", "陆川县", "博白县", "兴业县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "百色市", "area": ["右江区", "凌云县", "平果县", "西林县", "乐业县", "德保县", "田林县", "田阳县", "靖西县", "田东县", "那坡县", "隆林各族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "贺州市", "area": ["八步区", "钟山县", "昭平县", "富川瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "河池市", "area": ["金城江区", "宜州市", "天峨县", "凤山县", "南丹县", "东兰县", "都安瑶族自治县", "罗城仫佬族自治县", "巴马瑶族自治县", "环江毛南族自治县", "大化瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "来宾市", "area": ["兴宾区", "合山市", "象州县", "武宣县", "忻城县", "金秀瑶族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "崇左市", "area": ["江州区", "凭祥市", "宁明县", "扶绥县", "龙州县", "大新县", "天等县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海南省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海口市", "area": ["龙华区", "秀英区", "琼山区", "美兰区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "三亚市", "area": ["三亚市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "五指山市", "area": ["五指山"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "琼海市", "area": ["琼海"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "儋州市", "area": ["儋州"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "文昌市", "area": ["文昌"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "万宁市", "area": ["万宁"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "东方市", "area": ["东方"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "澄迈县", "area": ["澄迈县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "定安县", "area": ["定安县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "屯昌县", "area": ["屯昌县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "临高县", "area": ["临高县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "白沙黎族自治县", "area": ["白沙黎族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "昌江黎族自治县", "area": ["昌江黎族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "乐东黎族自治县", "area": ["乐东黎族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "陵水黎族自治县", "area": ["陵水黎族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "保亭黎族苗族自治县", "area": ["保亭黎族苗族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "琼中黎族苗族自治县", "area": ["琼中黎族苗族自治县"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "重庆市", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "重庆市", "area": ["渝中区", "大渡口区", "江北区", "南岸区", "北碚区", "渝北区", "巴南区", "长寿区", "双桥区", "沙坪坝区", "万盛区", "万州区", "涪陵区", "黔江区", "永川区", "合川区", "江津区", "九龙坡区", "南川区", "綦江县", "潼南县", "荣昌县", "璧山县", "大足县", "铜梁县", "梁平县", "开县", "忠县", "城口县", "垫江县", "武隆县", "丰都县", "奉节县", "云阳县", "巫溪县", "巫山县", "石柱土家族自治县", "秀山土家族苗族自治县", "酉阳土家族苗族自治县", "彭水苗族土家族自治县", "其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "四川省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "成都市", "area": ["青羊区", "锦江区", "金牛区", "武侯区", "成华区", "龙泉驿区", "青白江区", "新都区", "温江区", "都江堰市", "彭州市", "邛崃市", "崇州市", "金堂县", "郫县", "新津县", "双流县", "蒲江县", "大邑县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "自贡市", "area": ["大安区", "自流井区", "贡井区", "沿滩区", "荣县", "富顺县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "攀枝花市", "area": ["仁和区", "米易县", "盐边县", "东区", "西区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "泸州市", "area": ["江阳区", "纳溪区", "龙马潭区", "泸县", "合江县", "叙永县", "古蔺县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "德阳市", "area": ["旌阳区", "广汉市", "什邡市", "绵竹市", "罗江县", "中江县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "绵阳市", "area": ["涪城区", "游仙区", "江油市", "盐亭县", "三台县", "平武县", "安县", "梓潼县", "北川羌族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "广元市", "area": ["元坝区", "朝天区", "青川县", "旺苍县", "剑阁县", "苍溪县", "市中区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "遂宁市", "area": ["船山区", "安居区", "射洪县", "蓬溪县", "大英县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "内江市", "area": ["市中区", "东兴区", "资中县", "隆昌县", "威远县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "乐山市", "area": ["市中区", "五通桥区", "沙湾区", "金口河区", "峨眉山市", "夹江县", "井研县", "犍为县", "沐川县", "马边彝族自治县", "峨边彝族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "南充市", "area": ["顺庆区", "高坪区", "嘉陵区", "阆中市", "营山县", "蓬安县", "仪陇县", "南部县", "西充县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "眉山市", "area": ["东坡区", "仁寿县", "彭山县", "洪雅县", "丹棱县", "青神县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宜宾市", "area": ["翠屏区", "宜宾县", "兴文县", "南溪县", "珙县", "长宁县", "高县", "江安县", "筠连县", "屏山县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "广安市", "area": ["广安区", "华蓥市", "岳池县", "邻水县", "武胜县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "达州", "area": ["通川区", "万源市", "达县", "渠县", "宣汉县", "开江县", "大竹县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "雅安市", "area": ["雨城区", "芦山县", "石棉县", "名山县", "天全县", "荥经县", "宝兴县", "汉源县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "巴中市", "area": ["巴州区", "南江县", "平昌县", "通江县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "资阳市", "area": ["雁江区", "简阳市", "安岳县", "乐至县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阿坝藏族羌族自治州", "area": ["马尔康县", "九寨沟县", "红原县", "汶川县", "阿坝县", "理县", "若尔盖县", "小金县", "黑水县", "金川县", "松潘县", "壤塘县", "茂县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "甘孜藏族自治州", "area": ["康定县", "丹巴县", "炉霍县", "九龙县", "甘孜县", "雅江县", "新龙县", "道孚县", "白玉县", "理塘县", "德格县", "乡城县", "石渠县", "稻城县", "色达县", "巴塘县", "泸定县", "得荣县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "凉山彝族自治州", "area": ["西昌市", "美姑县", "昭觉县", "金阳县", "甘洛县", "布拖县", "雷波县", "普格县", "宁南县", "喜德县", "会东县", "越西县", "会理县", "盐源县", "德昌县", "冕宁县", "木里藏族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "贵州省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "贵阳市", "area": ["南明区", "云岩区", "花溪区", "乌当区", "白云区", "小河区", "清镇市", "开阳县", "修文县", "息烽县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "六盘水市", "area": ["钟山区", "水城县", "盘县", "六枝特区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "遵义市", "area": ["红花岗区", "汇川区", "赤水市", "仁怀市", "遵义县", "绥阳县", "桐梓县", "习水县", "凤冈县", "正安县", "余庆县", "湄潭县", "道真仡佬族苗族自治县", "务川仡佬族苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "安顺市", "area": ["西秀区", "普定县", "平坝县", "镇宁布依族苗族自治县", "紫云苗族布依族自治县", "关岭布依族苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "铜仁地区", "area": ["铜仁市", "德江县", "江口县", "思南县", "石阡县", "玉屏侗族自治县", "松桃苗族自治县", "印江土家族苗族自治县", "沿河土家族自治县", "万山特区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "毕节地区", "area": ["毕节市", "黔西县", "大方县", "织金县", "金沙县", "赫章县", "纳雍县", "威宁彝族回族苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黔西南布依族苗族自治州", "area": ["兴义市", "望谟县", "兴仁县", "普安县", "册亨县", "晴隆县", "贞丰县", "安龙县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黔东南苗族侗族自治州", "area": ["凯里市", "施秉县", "从江县", "锦屏县", "镇远县", "麻江县", "台江县", "天柱县", "黄平县", "榕江县", "剑河县", "三穗县", "雷山县", "黎平县", "岑巩县", "丹寨县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黔南布依族苗族自治州", "area": ["都匀市", "福泉市", "贵定县", "惠水县", "罗甸县", "瓮安县", "荔波县", "龙里县", "平塘县", "长顺县", "独山县", "三都水族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "云南省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "昆明市", "area": ["盘龙区", "五华区", "官渡区", "西山区", "东川区", "安宁市", "呈贡县", "晋宁县", "富民县", "宜良县", "嵩明县", "石林彝族自治县", "禄劝彝族苗族自治县", "寻甸回族彝族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "曲靖市", "area": ["麒麟区", "宣威市", "马龙县", "沾益县", "富源县", "罗平县", "师宗县", "陆良县", "会泽县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "玉溪市", "area": ["红塔区", "江川县", "澄江县", "通海县", "华宁县", "易门县", "峨山彝族自治县", "新平彝族傣族自治县", "元江哈尼族彝族傣族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "保山市", "area": ["隆阳区", "施甸县", "腾冲县", "龙陵县", "昌宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "昭通市", "area": ["昭阳区", "鲁甸县", "巧家县", "盐津县", "大关县", "永善县", "绥江县", "镇雄县", "彝良县", "威信县", "水富县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "丽江市", "area": ["古城区", "永胜县", "华坪县", "玉龙纳西族自治县", "宁蒗彝族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "普洱市", "area": ["思茅区", "普洱哈尼族彝族自治县", "墨江哈尼族自治县", "景东彝族自治县", "景谷傣族彝族自治县", "镇沅彝族哈尼族拉祜族自治县", "江城哈尼族彝族自治县", "孟连傣族拉祜族佤族自治县", "澜沧拉祜族自治县", "西盟佤族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "临沧市", "area": ["临翔区", "凤庆县", "云县", "永德县", "镇康县", "双江拉祜族佤族布朗族傣族自治县", "耿马傣族佤族自治县", "沧源佤族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "德宏傣族景颇族自治州", "area": ["潞西市", "瑞丽市", "梁河县", "盈江县", "陇川县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "怒江傈僳族自治州", "area": ["泸水县", "福贡县", "贡山独龙族怒族自治县", "兰坪白族普米族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "迪庆藏族自治州", "area": ["香格里拉县", "德钦县", "维西傈僳族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "大理白族自治州", "area": ["大理市", "祥云县", "宾川县", "弥渡县", "永平县", "云龙县", "洱源县", "剑川县", "鹤庆县", "漾濞彝族自治县", "南涧彝族自治县", "巍山彝族回族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "楚雄彝族自治州", "area": ["楚雄市", "双柏县", "牟定县", "南华县", "姚安县", "大姚县", "永仁县", "元谋县", "武定县", "禄丰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "红河哈尼族彝族自治州", "area": ["蒙自县", "个旧市", "开远市", "绿春县", "建水县", "石屏县", "弥勒县", "泸西县", "元阳县", "红河县", "金平苗族瑶族傣族自治县", "河口瑶族自治县", "屏边苗族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "文山壮族苗族自治州", "area": ["文山县", "砚山县", "西畴县", "麻栗坡县", "马关县", "丘北县", "广南县", "富宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "西双版纳傣族自治州", "area": ["景洪市", "勐海县", "勐腊县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "西藏自治区", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "拉萨市", "area": ["城关区", "林周县", "当雄县", "尼木县", "曲水县", "堆龙德庆县", "达孜县", "墨竹工卡县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "那曲地区", "area": ["那曲县", "嘉黎县", "比如县", "聂荣县", "安多县", "申扎县", "索县", "班戈县", "巴青县", "尼玛县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "昌都地区", "area": ["昌都县", "江达县", "贡觉县", "类乌齐县", "丁青县", "察雅县", "八宿县", "左贡县", "芒康县", "洛隆县", "边坝县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "林芝地区", "area": ["林芝县", "工布江达县", "米林县", "墨脱县", "波密县", "察隅县", "朗县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "山南地区", "area": ["乃东县", "扎囊县", "贡嘎县", "桑日县", "琼结县", "曲松县", "措美县", "洛扎县", "加查县", "隆子县", "错那县", "浪卡子县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "日喀则地区", "area": ["日喀则市", "南木林县", "江孜县", "定日县", "萨迦县", "拉孜县", "昂仁县", "谢通门县", "白朗县", "仁布县", "康马县", "定结县", "仲巴县", "亚东县", "吉隆县", "聂拉木县", "萨嘎县", "岗巴县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阿里地区", "area": ["噶尔县", "普兰县", "札达县", "日土县", "革吉县", "改则县", "措勤县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "陕西省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "西安市", "area": ["莲湖区", "新城区", "碑林区", "雁塔区", "灞桥区", "未央区", "阎良区", "临潼区", "长安区", "高陵县", "蓝田县", "户县", "周至县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "铜川市", "area": ["耀州区", "王益区", "印台区", "宜君县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宝鸡市", "area": ["渭滨区", "金台区", "陈仓区", "岐山县", "凤翔县", "陇县", "太白县", "麟游县", "扶风县", "千阳县", "眉县", "凤县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "咸阳市", "area": ["秦都区", "渭城区", "杨陵区", "兴平市", "礼泉县", "泾阳县", "永寿县", "三原县", "彬县", "旬邑县", "长武县", "乾县", "武功县", "淳化县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "渭南市", "area": ["临渭区", "韩城市", "华阴市", "蒲城县", "潼关县", "白水县", "澄城县", "华县", "合阳县", "富平县", "大荔县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "延安市", "area": ["宝塔区", "安塞县", "洛川县", "子长县", "黄陵县", "延川县", "富县", "延长县", "甘泉县", "宜川县", "志丹县", "黄龙县", "吴起县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "汉中市", "area": ["汉台区", "留坝县", "镇巴县", "城固县", "南郑县", "洋县", "宁强县", "佛坪县", "勉县", "西乡县", "略阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "榆林市", "area": ["榆阳区", "清涧县", "绥德县", "神木县", "佳县", "府谷县", "子洲县", "靖边县", "横山县", "米脂县", "吴堡县", "定边县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "安康市", "area": ["汉滨区", "紫阳县", "岚皋县", "旬阳县", "镇坪县", "平利县", "石泉县", "宁陕县", "白河县", "汉阴县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "商洛市", "area": ["商州区", "镇安县", "山阳县", "洛南县", "商南县", "丹凤县", "柞水县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "甘肃省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "兰州市", "area": ["城关区", "七里河区", "西固区", "安宁区", "红古区", "永登县", "皋兰县", "榆中县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "嘉峪关市", "area": ["嘉峪关市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "金昌市", "area": ["金川区", "永昌县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "白银市", "area": ["白银区", "平川区", "靖远县", "会宁县", "景泰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "天水市", "area": ["清水县", "秦安县", "甘谷县", "武山县", "张家川回族自治县", "北道区", "秦城区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "武威市", "area": ["凉州区", "民勤县", "古浪县", "天祝藏族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "酒泉市", "area": ["肃州区", "玉门市", "敦煌市", "金塔县", "肃北蒙古族自治县", "阿克塞哈萨克族自治县", "安西县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "张掖市", "area": ["甘州区", "民乐县", "临泽县", "高台县", "山丹县", "肃南裕固族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "庆阳市", "area": ["西峰区", "庆城县", "环县", "华池县", "合水县", "正宁县", "宁县", "镇原县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "平凉市", "area": ["崆峒区", "泾川县", "灵台县", "崇信县", "华亭县", "庄浪县", "静宁县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "定西市", "area": ["安定区", "通渭县", "临洮县", "漳县", "岷县", "渭源县", "陇西县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "陇南市", "area": ["武都区", "成县", "宕昌县", "康县", "文县", "西和县", "礼县", "两当县", "徽县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "临夏回族自治州", "area": ["临夏市", "临夏县", "康乐县", "永靖县", "广河县", "和政县", "东乡族自治县", "积石山保安族东乡族撒拉族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "甘南藏族自治州", "area": ["合作市", "临潭县", "卓尼县", "舟曲县", "迭部县", "玛曲县", "碌曲县", "夏河县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "青海省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "西宁市", "area": ["城中区", "城东区", "城西区", "城北区", "湟源县", "湟中县", "大通回族土族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海东地区", "area": ["平安县", "乐都县", "民和回族土族自治县", "互助土族自治县", "化隆回族自治县", "循化撒拉族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海北藏族自治州", "area": ["海晏县", "祁连县", "刚察县", "门源回族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海南藏族自治州", "area": ["共和县", "同德县", "贵德县", "兴海县", "贵南县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "黄南藏族自治州", "area": ["同仁县", "尖扎县", "泽库县", "河南蒙古族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "果洛藏族自治州", "area": ["玛沁县", "班玛县", "甘德县", "达日县", "久治县", "玛多县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "玉树藏族自治州", "area": ["玉树县", "杂多县", "称多县", "治多县", "囊谦县", "曲麻莱县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "海西蒙古族藏族自治州", "area": ["德令哈市", "格尔木市", "乌兰县", "都兰县", "天峻县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "宁夏回族自治区", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "银川市", "area": ["兴庆区", "西夏区", "金凤区", "灵武市", "永宁县", "贺兰县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "石嘴山市", "area": ["大武口区", "惠农区", "平罗县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吴忠市", "area": ["利通区", "青铜峡市", "盐池县", "同心县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "固原市", "area": ["原州区", "西吉县", "隆德县", "泾源县", "彭阳县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "中卫市", "area": ["沙坡头区", "中宁县", "海原县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "新疆维吾尔族自治区", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "乌鲁木齐市", "area": ["天山区", "沙依巴克区", "新市区", "水磨沟区", "头屯河区", "达坂城区", "东山区", "乌鲁木齐县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "克拉玛依市", "area": ["克拉玛依区", "独山子区", "白碱滩区", "乌尔禾区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "吐鲁番地区", "area": ["吐鲁番市", "托克逊县", "鄯善县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "哈密地区", "area": ["哈密市", "伊吾县", "巴里坤哈萨克自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "和田地区", "area": ["和田市", "和田县", "洛浦县", "民丰县", "皮山县", "策勒县", "于田县", "墨玉县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阿克苏地区", "area": ["阿克苏市", "温宿县", "沙雅县", "拜城县", "阿瓦提县", "库车县", "柯坪县", "新和县", "乌什县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "喀什地区", "area": ["喀什市", "巴楚县", "泽普县", "伽师县", "叶城县", "岳普湖县", "疏勒县", "麦盖提县", "英吉沙县", "莎车县", "疏附县", "塔什库尔干塔吉克自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "克孜勒苏柯尔克孜自治州", "area": ["阿图什市", "阿合奇县", "乌恰县", "阿克陶县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "巴音郭楞蒙古自治州", "area": ["库尔勒市", "和静县", "尉犁县", "和硕县", "且末县", "博湖县", "轮台县", "若羌县", "焉耆回族自治县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "昌吉回族自治州", "area": ["昌吉市", "阜康市", "奇台县", "玛纳斯县", "吉木萨尔县", "呼图壁县", "木垒哈萨克自治县", "米泉市", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "博尔塔拉蒙古自治州", "area": ["博乐市", "精河县", "温泉县", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "石河子市", "area": ["石河子"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "阿拉尔市", "area": ["阿拉尔"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "图木舒克市", "area": ["图木舒克"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "五家渠市", "area": ["五家渠"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "伊犁哈萨克自治州", "area": ["伊宁市", "奎屯市", "伊宁县", "特克斯县", "尼勒克县", "昭苏县", "新源县", "霍城县", "巩留县", "察布查尔锡伯自治县", "塔城地区", "阿勒泰地区", "其他"] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "其他", "area": ["其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "台湾省", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "台湾省", "area": ["台北市", "高雄市", "台北县", "桃园县", "新竹县", "苗栗县", "台中县", "彰化县", "南投县", "云林县", "嘉义县", "台南县", "高雄县", "屏东县", "宜兰县", "花莲县", "台东县", "澎湖县", "基隆市", "新竹市", "台中市", "嘉义市", "台南市", "其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "澳门", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "澳门", "area": ["花地玛堂区", "圣安多尼堂区", "大堂区", "望德堂区", "风顺堂区", "嘉模堂区", "圣方济各堂区", "路凼", "其他"] }] }, { 'color': '#000000', 'backgroundColor': '#ffffff', "name": "香港", "city": [{ 'color': '#000000', 'backgroundColor': '#ffffff', "name": "香港", "area": ["中西区", "湾仔区", "东区", "南区", "深水埗区", "油尖旺区", "九龙城区", "黄大仙区", "观塘区", "北区", "大埔区", "沙田区", "西贡区", "元朗区", "屯门区", "荃湾区", "葵青区", "离岛区", "其他"] }] }],
	                                    cities: [],
	                                    area: [],
	                                    areaList: [],
	                                    address: ''
	                  }},
	                  created: function created() {
	                                    var bundleUrl = this.$getConfig().bundleUrl;
	                                    console.log('hit', bundleUrl);
	                                    var nativeBase;
	                                    var isAndroidAssets = bundleUrl.indexOf('file:///mnt/sdcard/') >= 0;
	                                    var isiOSAssets = bundleUrl.indexOf('file:///') >= 0;
	                                    if (isAndroidAssets) {
	                                                      nativeBase = bundleUrl;
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

	                                    this.leftItemImg = Utils.changeImg(this.leftItemImg);

	                                    var self = this;

	                                    storage.getItem('userId', function (e) {
	                                                      self.userId = e.data;
	                                    });

	                                    console.log('llll' + self.cities.length);
	                  },
	                  methods: {
	                                    selecteProvince: function selecteProvince(name, index) {
	                                                      var self = this;
	                                                      console.log(self.cityData[index]);
	                                                      for (var i in self.cityData) {
	                                                                        self.cityData[i].backgroundColor = "#ffffff";
	                                                                        self.cityData[i].color = "#000000";
	                                                                        if (self.cityData[i].name == name) {
	                                                                                          if (self.cityData[i].city.length == 1) {
	                                                                                                            self.cities = [];
	                                                                                                            self.areaList = [];

	                                                                                                            self.area = JSON.parse((0, _stringify2.default)(self.cityData[i].city[0].area));
	                                                                                                            for (var i = 0; i < self.area.length; i++) {
	                                                                                                                              var q = {};
	                                                                                                                              q.name = self.area[i];
	                                                                                                                              q.backgroundColor = '#ffffff';
	                                                                                                                              q.color = '#000000';
	                                                                                                                              self.areaList.push(q);
	                                                                                                            }
	                                                                                          } else {
	                                                                                                            self.area = [];
	                                                                                                            self.cities = [];
	                                                                                                            self.areaList = [];

	                                                                                                            self.cities = JSON.parse((0, _stringify2.default)(self.cityData[i].city));
	                                                                                          }
	                                                                        }
	                                                      }
	                                                      self.cityData[index].backgroundColor = "#00cc99";
	                                                      self.cityData[index].color = "#ffffff";
	                                                      storage.setItem('province', name, function () {});
	                                    },
	                                    selecteCity: function selecteCity(name, index) {
	                                                      var self = this;
	                                                      self.areaList = [];
	                                                      if (self.cities.length > 0) {
	                                                                        for (var i in self.cities) {
	                                                                                          self.cities[i].backgroundColor = "#ffffff";
	                                                                                          self.cities[i].color = "#000000";
	                                                                                          if (i == index) {
	                                                                                                            for (var j in self.cities[i].area) {
	                                                                                                                              var q = {};
	                                                                                                                              q.name = self.area[j];
	                                                                                                                              q.backgroundColor = '#ffffff';
	                                                                                                                              q.color = '#000000';
	                                                                                                                              self.areaList.push(q);
	                                                                                                            }
	                                                                                          }
	                                                                        }
	                                                                        self.cities[index].backgroundColor = "#00cc99";
	                                                                        self.cities[index].color = "#ffffff";
	                                                                        storage.setItem('city', name, function () {});
	                                                      }
	                                    },
	                                    selecteArea: function selecteArea(name, index) {
	                                                      var self = this;
	                                                      self.address = self.address + '*' + name;
	                                                      for (var i in self.areaList) {
	                                                                        self.areaList[i].backgroundColor = "#ffffff";
	                                                                        self.areaList[i].color = "#000000";
	                                                      }
	                                                      self.areaList[index].backgroundColor = "#00cc99";

	                                                      self.areaList[index].color = "#ffffff";

	                                                      storage.getItem('province', function (re) {
	                                                                        self.address = re.data + '*';
	                                                                        storage.getItem('city', function (e) {
	                                                                                          console.log('city:' + e.data);
	                                                                                          if (e.data != 'undefined') {
	                                                                                                            self.address = re.data + '*' + e.data + '*' + name;
	                                                                                          } else {
	                                                                                                            self.address = re.data + '*' + re.data + '*' + name;
	                                                                                          }
	                                                                                          self.$dispatch('isShowCity', false);
	                                                                                          self.$dispatch('address', self.address.replace(/\s+/g, ""));
	                                                                        });
	                                                      });
	                                    },
	                                    pickerCancle: function pickerCancle() {
	                                                      this.$dispatch('isShowCity', false);
	                                    }
	                  }
	};}
	/* generated by weex-loader */


/***/ },
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
/* 225 */,
/* 226 */,
/* 227 */,
/* 228 */,
/* 229 */,
/* 230 */,
/* 231 */,
/* 232 */,
/* 233 */,
/* 234 */,
/* 235 */,
/* 236 */,
/* 237 */,
/* 238 */,
/* 239 */,
/* 240 */,
/* 241 */,
/* 242 */,
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */,
/* 261 */,
/* 262 */,
/* 263 */,
/* 264 */,
/* 265 */,
/* 266 */,
/* 267 */,
/* 268 */,
/* 269 */,
/* 270 */,
/* 271 */,
/* 272 */,
/* 273 */,
/* 274 */,
/* 275 */,
/* 276 */,
/* 277 */,
/* 278 */,
/* 279 */,
/* 280 */,
/* 281 */,
/* 282 */,
/* 283 */,
/* 284 */,
/* 285 */,
/* 286 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "wxc-navpage",
	  "attr": {
	    "dataRole": "none",
	    "height": function () {return this.navBarHeight},
	    "backgroundColor": "#00cc99",
	    "title": "我",
	    "titleColor": "white",
	    "rightItemSrc": function () {return this.images.rightItemImg}
	  },
	  "children": [
	    {
	      "type": "div",
	      "classList": [
	        "head_img"
	      ],
	      "children": [
	        {
	          "type": "text",
	          "classList": [
	            "mine_title"
	          ],
	          "attr": {
	            "value": "头像"
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "mine_text"
	          ],
	          "events": {
	            "click": "updateUserImg"
	          },
	          "children": [
	            {
	              "type": "image",
	              "classList": [
	                "my_img"
	              ],
	              "attr": {
	                "src": function () {return this.images.userImg}
	              }
	            },
	            {
	              "type": "image",
	              "classList": [
	                "arrow_icon",
	                "head_arrow"
	              ],
	              "attr": {
	                "src": function () {return this.images.arrowRight}
	              }
	            }
	          ]
	        }
	      ]
	    },
	    {
	      "type": "div",
	      "classList": [
	        "mine_settings"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "items"
	          ],
	          "style": {
	            "borderBottomStyle": function () {return this.borderBottom}
	          },
	          "repeat": function () {return this.myList2},
	          "events": {
	            "click": function ($event) {this.redirect(this.url,this.id,$event)}
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "mine_title"
	              ],
	              "attr": {
	                "value": function () {return this.option}
	              }
	            },
	            {
	              "type": "div",
	              "classList": [
	                "mine_text"
	              ],
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "setting"
	                  ],
	                  "attr": {
	                    "value": function () {return this.value}
	                  }
	                },
	                {
	                  "type": "image",
	                  "classList": [
	                    "arrow_icon"
	                  ],
	                  "attr": {
	                    "src": function () {return this.images.arrowRight}
	                  }
	                }
	              ]
	            }
	          ]
	        }
	      ]
	    },
	    {
	      "type": "wxc-citypicker",
	      "classList": [
	        "city_picker"
	      ],
	      "shown": function () {return this.isShowCity}
	    }
	  ]
	}

/***/ },
/* 287 */
/***/ function(module, exports) {

	module.exports = {
	  "content": {
	    "backgroundColor": "#eeeeee",
	    "color": "#333333",
	    "fontFamily": "Microsoft YaHei",
	    "fontSize": 30,
	    "overflow": "hidden"
	  },
	  "head_img": {
	    "flexDirection": "row",
	    "padding": 20,
	    "marginBottom": 20,
	    "backgroundColor": "#ffffff",
	    "alignItems": "center",
	    "paddingLeft": 30
	  },
	  "mine_settings": {
	    "flexDirection": "column",
	    "marginBottom": 20
	  },
	  "items": {
	    "flexDirection": "row",
	    "padding": 30,
	    "paddingTop": 35,
	    "paddingBottom": 35,
	    "backgroundColor": "#ffffff",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#cccccc",
	    "boxSizing": "border-box",
	    "alignItems": "center",
	    "display": "flex"
	  },
	  "mine_title": {
	    "flex": 1,
	    "width": 100,
	    "fontSize": 36,
	    "paddingLeft": 20
	  },
	  "mine_text": {
	    "flexDirection": "row",
	    "alignItems": "center",
	    "fontSize": 36
	  },
	  "my_img": {
	    "height": 95,
	    "width": 95,
	    "borderRadius": 100
	  },
	  "arrow_icon": {
	    "height": 33,
	    "width": 20,
	    "marginLeft": 40,
	    "opacity": 0.6,
	    "marginRight": 15
	  },
	  "head_arrow": {
	    "marginRight": 25
	  },
	  "setting": {
	    "fontSize": 36,
	    "width": 450,
	    "textAlign": "right",
	    "whiteSpace": "nowrap"
	  }
	}

/***/ },
/* 288 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _stringify = __webpack_require__(143);

	var _stringify2 = _interopRequireDefault(_stringify);

	var _typeof2 = __webpack_require__(102);

	var _typeof3 = _interopRequireDefault(_typeof2);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	__webpack_require__(16);
	var Utils = __webpack_require__(98);
	var modal = __weex_require__('@weex-module/modal');
	var storage = __weex_require__('@weex-module/storage');
	var SJevent = __weex_require__('@weex-module/SJevent');
	module.exports = {
	    data: function () {return {
	        navBarHeight: 130,
	        show0: true,
	        show1: false,
	        show2: false,
	        dir: 'yjpt',
	        myList: [{ id: 'userName', option: '姓名', value: '彭尼玛', url: 'name-setting', borderBottom: 'solid' }, { id: 'userSex', option: '性别', value: '未设置', url: 'gender-setting', borderBottom: 'solid' }, { id: 'userRegion', option: '地区', value: '', url: 'region-setting', borderBottom: 'none' }],
	        myList2: [{ id: 'userName', option: '姓名', value: '彭尼玛', url: 'name-setting', borderBottom: 'solid' }, { id: 'userSex', option: '性别', value: '未设置', url: 'gender-setting', borderBottom: 'solid' }, { id: 'userRegion', option: '地区', value: '', url: 'region-setting', borderBottom: 'none' }],
	        images: {
	            arrowRight: 'yjpt/images/arrow_right.png',
	            userImg: 'yjpt/images/IMG_df.png',
	            rightItemImg: 'yjpt/images/setting.png'
	        },
	        loginUrl: 'login',
	        settingUrl: 'setting',
	        isShowCity: false,
	        userId: '',
	        address: ''
	    }},
	    created: function created() {
	        Utils.changeImg(this.images, ['arrowRight', 'userImg', 'rightItemImg']);
	        var bundleUrl = this.$getConfig().bundleUrl;
	        console.log('hit', bundleUrl);
	        var nativeBase;
	        var isAndroidAssets = bundleUrl.indexOf('file:///mnt/sdcard/') >= 0;
	        var isiOSAssets = bundleUrl.indexOf('file:///') >= 0;
	        if (isAndroidAssets) {
	            nativeBase = bundleUrl;
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

	        for (var i = 0; i < this.myList.length; i++) {
	            var myList = this.myList[i];
	            if (myList.url) {
	                myList.url = base + myList.url + '.js';
	            }
	        }

	        this.loginUrl = base + this.loginUrl + '.js';
	        this.settingUrl = base + this.settingUrl + '.js';

	        this.$on('naviBar.rightItem.click', function () {
	            Utils.navigate.push(this, this.settingUrl, 'true');
	        });

	        var self = this;

	        storage.getItem('userId', function (e) {
	            self.userId = e.data;
	            Utils.fetch({
	                url: '/userInfo/myInfo',
	                data: 'userId=' + self.userId,
	                type: 'json',
	                method: 'POST',
	                success: function success(ret) {
	                    var _data = ret.data;
	                    var obj = _data;
	                    if (obj.retcode == '1') {
	                        if (obj.userInfo.photo != null && obj.userInfo.photo != '') {
	                            self.images.userImg = Utils.ip + self.dir + obj.userInfo.photo;
	                        } else {
	                            self.images.userImg = self.images.userImg;
	                        }

	                        for (var i = 0; i < self.myList.length; i++) {
	                            var myList = self.myList[i];
	                            if (myList.id == 'userName') {
	                                myList.value = obj.userInfo.realname == null ? '未设置' : obj.userInfo.realname;
	                            }
	                            if (myList.id == 'userSex') {
	                                myList.value = (obj.userInfo.sex == null ? '未设置' : obj.userInfo.sex) == 1 ? '男' : '女';
	                            }
	                            if (myList.id == 'userRegion') {
	                                myList.value = obj.userInfo.county_id == null ? '未设置' : obj.userInfo.county_id;
	                            }
	                        }

	                        self.myList2 = JSON.parse((0, _stringify2.default)(self.myList));

	                        self.myList2[2].value = self.myList2[2].value.replace(/\*/g, '').replace(/\s+/g, "");

	                        storage.setItem('sex', obj.userInfo.sex, function () {});
	                        storage.setItem('userName', obj.userInfo.realname, function () {});
	                        storage.setItem('address', self.myList[2].value, function () {});
	                    } else {
	                        modal.toast({
	                            message: '网络异常，请稍后重试',
	                            duration: '1'
	                        });
	                    }
	                }
	            });
	        });

	        self.$on('address', function (e) {
	            console.log('mine:' + e.detail);
	            self.address = e.detail;
	            self.myList2[2].value = e.detail.replace(/\*/g, "");
	            storage.setItem('address', e.detail, function () {});

	            Utils.fetch({
	                url: '/userInfo/updateUserInfo',
	                data: 'userId=' + self.userId + '&countyId=' + self.address,
	                type: 'json',
	                method: 'POST',
	                success: function success(ret) {
	                    var _data = ret.data;
	                    var obj = _data;
	                    if (obj.retcode == '1') {
	                        modal.toast({
	                            message: '修改成功',
	                            duration: '1'
	                        });
	                    } else {
	                        modal.alert({
	                            message: '修改失败，请稍后重试',
	                            okTitle: '好的'
	                        });
	                    }
	                }
	            });
	        });
	        self.$on('isShowCity', function (e) {
	            self.isShowCity = e.detail;
	        });
	    },
	    methods: {
	        redirect: function redirect(url, id) {
	            var self = this;
	            if (id == 'userRegion') {
	                self.isShowCity = true;
	                console.log('userRegion');
	            } else {
	                Utils.navigate.push(this, url, 'true');;
	            }
	        },
	        updateUserImg: function updateUserImg() {
	            var self = this;
	            storage.getItem('jsessionid', function (e) {
	                var jsessionid = e.data;
	                var paramJson = {
	                    userId: self.userId,
	                    jsessionid: jsessionid
	                };
	                paramJson = (0, _stringify2.default)(paramJson);
	                if (SJevent.PostSigalImg) {
	                    SJevent.PostSigalImg(paramJson, function (res) {
	                        if (res == 0) {
	                            modal.toast({
	                                message: '图片上传失败请重新上传！',
	                                duration: '1'
	                            }, function () {});
	                        } else {
	                            var path = res.path;
	                            self.images.userImg = Utils.ip + 'yjpt' + path;
	                        }
	                    });
	                } else {
	                    modal.alert({
	                        message: '请在App中操作',
	                        okTitle: '好的'
	                    }, function () {});
	                }
	            });
	        }
	    }
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);