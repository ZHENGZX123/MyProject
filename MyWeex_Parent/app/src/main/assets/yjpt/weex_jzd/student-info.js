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

	var __weex_template__ = __webpack_require__(241)
	var __weex_style__ = __webpack_require__(242)
	var __weex_script__ = __webpack_require__(243)

	__weex_define__('@weex-component/d5128c79cc5a109fb34ce3c67e046e4f', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/d5128c79cc5a109fb34ce3c67e046e4f',undefined,undefined)

/***/ },
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(5);
	__webpack_require__(9);
	__webpack_require__(13);
	__webpack_require__(17);
	__webpack_require__(21);
	__webpack_require__(25);
	__webpack_require__(66);
	__webpack_require__(70);
	__webpack_require__(74);
	__webpack_require__(78);
	__webpack_require__(79);


/***/ },
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(6)
	var __weex_style__ = __webpack_require__(7)
	var __weex_script__ = __webpack_require__(8)

	__weex_define__('@weex-component/wxc-button', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 6 */
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
/* 7 */
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
/* 8 */
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
/* 9 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(10)
	var __weex_style__ = __webpack_require__(11)
	var __weex_script__ = __webpack_require__(12)

	__weex_define__('@weex-component/wxc-hn', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 10 */
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
/* 11 */
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
/* 12 */
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
/* 13 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(14)
	var __weex_style__ = __webpack_require__(15)
	var __weex_script__ = __webpack_require__(16)

	__weex_define__('@weex-component/wxc-list-item', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 14 */
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
/* 15 */
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
/* 16 */
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
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(18)
	var __weex_style__ = __webpack_require__(19)
	var __weex_script__ = __webpack_require__(20)

	__weex_define__('@weex-component/wxc-panel', [], function(__weex_require__, __weex_exports__, __weex_module__) {

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
/* 19 */
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
/* 20 */
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
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(22)
	var __weex_style__ = __webpack_require__(23)
	var __weex_script__ = __webpack_require__(24)

	__weex_define__('@weex-component/wxc-tip', [], function(__weex_require__, __weex_exports__, __weex_module__) {

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
/* 23 */
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
/* 24 */
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
/* 25 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(26)
	var __weex_style__ = __webpack_require__(27)
	var __weex_script__ = __webpack_require__(28)

	__weex_define__('@weex-component/wxc-countdown', [], function(__weex_require__, __weex_exports__, __weex_module__) {

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
/* 27 */
/***/ function(module, exports) {

	module.exports = {
	  "wrap": {
	    "overflow": "hidden"
	  }
	}

/***/ },
/* 28 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _assign = __webpack_require__(29);

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
/* 29 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(30), __esModule: true };

/***/ },
/* 30 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(31);
	module.exports = __webpack_require__(34).Object.assign;

/***/ },
/* 31 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.1 Object.assign(target, source)
	var $export = __webpack_require__(32);

	$export($export.S + $export.F, 'Object', {assign: __webpack_require__(47)});

/***/ },
/* 32 */
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(33)
	  , core      = __webpack_require__(34)
	  , ctx       = __webpack_require__(35)
	  , hide      = __webpack_require__(37)
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
/* 33 */
/***/ function(module, exports) {

	// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
	var global = module.exports = typeof window != 'undefined' && window.Math == Math
	  ? window : typeof self != 'undefined' && self.Math == Math ? self : Function('return this')();
	if(typeof __g == 'number')__g = global; // eslint-disable-line no-undef

/***/ },
/* 34 */
/***/ function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ },
/* 35 */
/***/ function(module, exports, __webpack_require__) {

	// optional / simple context binding
	var aFunction = __webpack_require__(36);
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
/* 36 */
/***/ function(module, exports) {

	module.exports = function(it){
	  if(typeof it != 'function')throw TypeError(it + ' is not a function!');
	  return it;
	};

/***/ },
/* 37 */
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(38)
	  , createDesc = __webpack_require__(46);
	module.exports = __webpack_require__(42) ? function(object, key, value){
	  return dP.f(object, key, createDesc(1, value));
	} : function(object, key, value){
	  object[key] = value;
	  return object;
	};

/***/ },
/* 38 */
/***/ function(module, exports, __webpack_require__) {

	var anObject       = __webpack_require__(39)
	  , IE8_DOM_DEFINE = __webpack_require__(41)
	  , toPrimitive    = __webpack_require__(45)
	  , dP             = Object.defineProperty;

	exports.f = __webpack_require__(42) ? Object.defineProperty : function defineProperty(O, P, Attributes){
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
/* 39 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(40);
	module.exports = function(it){
	  if(!isObject(it))throw TypeError(it + ' is not an object!');
	  return it;
	};

/***/ },
/* 40 */
/***/ function(module, exports) {

	module.exports = function(it){
	  return typeof it === 'object' ? it !== null : typeof it === 'function';
	};

/***/ },
/* 41 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = !__webpack_require__(42) && !__webpack_require__(43)(function(){
	  return Object.defineProperty(__webpack_require__(44)('div'), 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 42 */
/***/ function(module, exports, __webpack_require__) {

	// Thank's IE8 for his funny defineProperty
	module.exports = !__webpack_require__(43)(function(){
	  return Object.defineProperty({}, 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 43 */
/***/ function(module, exports) {

	module.exports = function(exec){
	  try {
	    return !!exec();
	  } catch(e){
	    return true;
	  }
	};

/***/ },
/* 44 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(40)
	  , document = __webpack_require__(33).document
	  // in old IE typeof document.createElement is 'object'
	  , is = isObject(document) && isObject(document.createElement);
	module.exports = function(it){
	  return is ? document.createElement(it) : {};
	};

/***/ },
/* 45 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.1 ToPrimitive(input [, PreferredType])
	var isObject = __webpack_require__(40);
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
/* 46 */
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
/* 47 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.2.1 Object.assign(target, source, ...)
	var getKeys  = __webpack_require__(48)
	  , gOPS     = __webpack_require__(63)
	  , pIE      = __webpack_require__(64)
	  , toObject = __webpack_require__(65)
	  , IObject  = __webpack_require__(52)
	  , $assign  = Object.assign;

	// should work with symbols and should have deterministic property order (V8 bug)
	module.exports = !$assign || __webpack_require__(43)(function(){
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
/* 48 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 / 15.2.3.14 Object.keys(O)
	var $keys       = __webpack_require__(49)
	  , enumBugKeys = __webpack_require__(62);

	module.exports = Object.keys || function keys(O){
	  return $keys(O, enumBugKeys);
	};

/***/ },
/* 49 */
/***/ function(module, exports, __webpack_require__) {

	var has          = __webpack_require__(50)
	  , toIObject    = __webpack_require__(51)
	  , arrayIndexOf = __webpack_require__(55)(false)
	  , IE_PROTO     = __webpack_require__(59)('IE_PROTO');

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
/* 50 */
/***/ function(module, exports) {

	var hasOwnProperty = {}.hasOwnProperty;
	module.exports = function(it, key){
	  return hasOwnProperty.call(it, key);
	};

/***/ },
/* 51 */
/***/ function(module, exports, __webpack_require__) {

	// to indexed object, toObject with fallback for non-array-like ES3 strings
	var IObject = __webpack_require__(52)
	  , defined = __webpack_require__(54);
	module.exports = function(it){
	  return IObject(defined(it));
	};

/***/ },
/* 52 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for non-array-like ES3 and non-enumerable old V8 strings
	var cof = __webpack_require__(53);
	module.exports = Object('z').propertyIsEnumerable(0) ? Object : function(it){
	  return cof(it) == 'String' ? it.split('') : Object(it);
	};

/***/ },
/* 53 */
/***/ function(module, exports) {

	var toString = {}.toString;

	module.exports = function(it){
	  return toString.call(it).slice(8, -1);
	};

/***/ },
/* 54 */
/***/ function(module, exports) {

	// 7.2.1 RequireObjectCoercible(argument)
	module.exports = function(it){
	  if(it == undefined)throw TypeError("Can't call method on  " + it);
	  return it;
	};

/***/ },
/* 55 */
/***/ function(module, exports, __webpack_require__) {

	// false -> Array#indexOf
	// true  -> Array#includes
	var toIObject = __webpack_require__(51)
	  , toLength  = __webpack_require__(56)
	  , toIndex   = __webpack_require__(58);
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
/* 56 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.15 ToLength
	var toInteger = __webpack_require__(57)
	  , min       = Math.min;
	module.exports = function(it){
	  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
	};

/***/ },
/* 57 */
/***/ function(module, exports) {

	// 7.1.4 ToInteger
	var ceil  = Math.ceil
	  , floor = Math.floor;
	module.exports = function(it){
	  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
	};

/***/ },
/* 58 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(57)
	  , max       = Math.max
	  , min       = Math.min;
	module.exports = function(index, length){
	  index = toInteger(index);
	  return index < 0 ? max(index + length, 0) : min(index, length);
	};

/***/ },
/* 59 */
/***/ function(module, exports, __webpack_require__) {

	var shared = __webpack_require__(60)('keys')
	  , uid    = __webpack_require__(61);
	module.exports = function(key){
	  return shared[key] || (shared[key] = uid(key));
	};

/***/ },
/* 60 */
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(33)
	  , SHARED = '__core-js_shared__'
	  , store  = global[SHARED] || (global[SHARED] = {});
	module.exports = function(key){
	  return store[key] || (store[key] = {});
	};

/***/ },
/* 61 */
/***/ function(module, exports) {

	var id = 0
	  , px = Math.random();
	module.exports = function(key){
	  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
	};

/***/ },
/* 62 */
/***/ function(module, exports) {

	// IE 8- don't enum bug keys
	module.exports = (
	  'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'
	).split(',');

/***/ },
/* 63 */
/***/ function(module, exports) {

	exports.f = Object.getOwnPropertySymbols;

/***/ },
/* 64 */
/***/ function(module, exports) {

	exports.f = {}.propertyIsEnumerable;

/***/ },
/* 65 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.13 ToObject(argument)
	var defined = __webpack_require__(54);
	module.exports = function(it){
	  return Object(defined(it));
	};

/***/ },
/* 66 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(67)
	var __weex_style__ = __webpack_require__(68)
	var __weex_script__ = __webpack_require__(69)

	__weex_define__('@weex-component/wxc-marquee', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 67 */
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
/* 68 */
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
/* 69 */
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
/* 70 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(71)
	var __weex_style__ = __webpack_require__(72)
	var __weex_script__ = __webpack_require__(73)

	__weex_define__('@weex-component/wxc-navbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 71 */
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
/* 72 */
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
/* 73 */
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
/* 74 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(70)
	var __weex_template__ = __webpack_require__(75)
	var __weex_style__ = __webpack_require__(76)
	var __weex_script__ = __webpack_require__(77)

	__weex_define__('@weex-component/wxc-navpage', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 75 */
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
/* 76 */
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
/* 77 */
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
/* 78 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(79)
	var __weex_template__ = __webpack_require__(83)
	var __weex_style__ = __webpack_require__(84)
	var __weex_script__ = __webpack_require__(85)

	__weex_define__('@weex-component/wxc-tabbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 79 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(80)
	var __weex_style__ = __webpack_require__(81)
	var __weex_script__ = __webpack_require__(82)

	__weex_define__('@weex-component/wxc-tabitem', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})


/***/ },
/* 80 */
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
/* 81 */
/***/ function(module, exports) {

	module.exports = {
	  "container": {
	    "flex": 1,
	    "flexDirection": "column",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "height": 100
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
	    "width": 50,
	    "height": 50
	  },
	  "tab-text": {
	    "marginTop": 8,
	    "textAlign": "center",
	    "fontSize": 20
	  }
	}

/***/ },
/* 82 */
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
/* 83 */
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
/* 84 */
/***/ function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "width": 750,
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 10
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
	    "bottom": 5,
	    "left": 0,
	    "right": 0,
	    "height": 100
	  }
	}

/***/ },
/* 85 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    tabItems: [],
	    selectedIndex: 0,
	    selectedColor: '#00cc99',
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
/* 86 */
/***/ function(module, exports) {

	var Utils = {
	  	dir : 'yjpts',
	    // ip : 'http://192.168.8.206:8180/',
	    // ip : 'http://192.168.8.114:8088/',
	    // ip : 'http://127.0.0.1:8088/',
	    ip : 'http://www.yuertong.com/',

	    setOpenUrl : function(context,arr){
	      var bundleUrl = context.bundleUrl;
	      bundleUrl = new String(bundleUrl);
	      var nativeBase;
	      var isAndroidAssets = bundleUrl.indexOf('file:///mnt/sdcard/') >= 0;

	      var isiOSAssets = bundleUrl.indexOf('file:///') >= 0 ;//&& bundleUrl.indexOf('WeexDemo.app') > 0;
	      
	      if (isAndroidAssets) {
	        nativeBase = 'file://assets/';
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
	                          subObj[imgNameArr[j]] = Utils.ip +Utils.dir+'/'+ osubOjb;
	                      }
	                        
	                    }
	                  }
	                  obj[imgNameArr[j]] = Utils.ip +Utils.dir+'/'+ obj[imgNameArr[j]];
	                  
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
	                arr[imgNameArr[i]] = Utils.ip+Utils.dir+'/' + arr[imgNameArr[i]] ;
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

	        stream.fetch({
	            headers : options.headers || headers,
	            method : options.method || method,
	            url : requestUrl +　options.url,
	            type : options.dataType || type,
	            body : options.data
	        }, function(response) {
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
/* 98 */,
/* 99 */,
/* 100 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(101), __esModule: true };

/***/ },
/* 101 */
/***/ function(module, exports, __webpack_require__) {

	var core  = __webpack_require__(34)
	  , $JSON = core.JSON || (core.JSON = {stringify: JSON.stringify});
	module.exports = function stringify(it){ // eslint-disable-line no-unused-vars
	  return $JSON.stringify.apply($JSON, arguments);
	};

/***/ },
/* 102 */,
/* 103 */,
/* 104 */,
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */,
/* 110 */,
/* 111 */,
/* 112 */,
/* 113 */,
/* 114 */,
/* 115 */,
/* 116 */,
/* 117 */,
/* 118 */,
/* 119 */,
/* 120 */,
/* 121 */,
/* 122 */,
/* 123 */,
/* 124 */,
/* 125 */,
/* 126 */,
/* 127 */,
/* 128 */,
/* 129 */,
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
/* 241 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "wxc-navpage",
	  "attr": {
	    "dataRole": "none",
	    "height": function () {return this.navBarHeight},
	    "backgroundColor": "#00cc99",
	    "title": "我的宝贝",
	    "titleColor": "white",
	    "leftItemSrc": function () {return this.headLeft},
	    "rightItemColor": "white",
	    "rightItemTitle": "完成"
	  },
	  "events": {
	    "clickleftitem": "onclickleftitem",
	    "clickrightitem": "onclickrightitem"
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
	            "mycake1"
	          ],
	          "children": [
	            {
	              "type": "image",
	              "classList": [
	                "pic_thumb"
	              ],
	              "attr": {
	                "src": function () {return this.myBaby.avatar==null?this.myPortraitUrl:this.myBaby.avatar}
	              },
	              "events": {
	                "click": "QRScan"
	              }
	            },
	            {
	              "type": "div",
	              "classList": [
	                "mycake"
	              ],
	              "events": {
	                "click": "toggleMenu"
	              },
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "name"
	                  ],
	                  "attr": {
	                    "value": "性别"
	                  }
	                },
	                {
	                  "type": "text",
	                  "classList": [
	                    "sex"
	                  ],
	                  "style": {
	                    "fontSize": 36
	                  },
	                  "attr": {
	                    "value": function () {return this.myBaby.gender?this.myBaby.gender:'未知'}
	                  }
	                },
	                {
	                  "type": "image",
	                  "classList": [
	                    "rightli"
	                  ],
	                  "attr": {
	                    "src": function () {return this.arrow}
	                  },
	                  "style": {
	                    "width": 22,
	                    "height": 35
	                  }
	                }
	              ]
	            },
	            {
	              "type": "div",
	              "classList": [
	                "mycake"
	              ],
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "name"
	                  ],
	                  "attr": {
	                    "value": "生日"
	                  }
	                },
	                {
	                  "type": "input",
	                  "attr": {
	                    "type": "date",
	                    "autofocus": "false",
	                    "value": function () {return this.myBaby.birthday?this.myBaby.birthday:'未知'}
	                  },
	                  "classList": [
	                    "input"
	                  ],
	                  "events": {
	                    "change": "datePicker"
	                  }
	                },
	                {
	                  "type": "image",
	                  "classList": [
	                    "rightli"
	                  ],
	                  "attr": {
	                    "src": function () {return this.arrow}
	                  },
	                  "style": {
	                    "width": 22,
	                    "height": 35
	                  }
	                }
	              ]
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "pop_menu"
	          ],
	          "shown": function () {return this.isShowMenu==1},
	          "children": [
	            {
	              "type": "div",
	              "classList": [
	                "menu_sex"
	              ],
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "pop_list"
	                  ],
	                  "events": {
	                    "click": function ($event) {this.setGender('男',$event)}
	                  },
	                  "attr": {
	                    "value": "男"
	                  }
	                },
	                {
	                  "type": "text",
	                  "classList": [
	                    "pop_list"
	                  ],
	                  "events": {
	                    "click": function ($event) {this.setGender('女',$event)}
	                  },
	                  "attr": {
	                    "value": "女"
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
/* 242 */
/***/ function(module, exports) {

	module.exports = {
	  "input": {
	    "fontSize": 36,
	    "height": 80,
	    "width": 300,
	    "textAlign": "right",
	    "marginRight": 80
	  },
	  "pop_menu": {
	    "position": "fixed",
	    "left": 0,
	    "right": 0,
	    "bottom": 0,
	    "top": 0,
	    "backgroundColor": "rgba(0,0,0,0.3)"
	  },
	  "menu_sex": {
	    "height": 200,
	    "backgroundColor": "#ffffff",
	    "position": "absolute",
	    "right": 0,
	    "bottom": 0,
	    "left": 0
	  },
	  "pop_list": {
	    "padding": 30,
	    "textAlign": "center",
	    "fontSize": 36,
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#cccccc",
	    "borderBottomStyle": "solid"
	  },
	  "content": {
	    "color": "#353535",
	    "backgroundColor": "#ffffff",
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0
	  },
	  "blank_img": {
	    "height": 35,
	    "width": 22
	  },
	  "mycake1": {
	    "width": 750,
	    "backgroundColor": "#ffffff",
	    "paddingTop": 50,
	    "alignItems": "center",
	    "position": "relative"
	  },
	  "mycake": {
	    "flexDirection": "row",
	    "width": 750,
	    "height": 110,
	    "backgroundColor": "#ffffff",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#e5e5e5",
	    "display": "flex",
	    "alignItems": "center",
	    "justifyContent": "space-between",
	    "paddingLeft": 15,
	    "paddingRight": 30
	  },
	  "name": {
	    "fontSize": 36,
	    "fontFamily": "微软雅黑",
	    "paddingTop": 10,
	    "textAlign": "center"
	  },
	  "mycake_li": {
	    "height": 110,
	    "borderTopWidth": 1,
	    "borderTopColor": "#e5e5e5"
	  },
	  "rightName": {
	    "flex": 2,
	    "paddingRight": 20,
	    "textAlign": "right",
	    "fontFamily": "微软雅黑",
	    "fontSize": 30
	  },
	  "nameType": {
	    "fontSize": 36,
	    "paddingLeft": 30,
	    "textAlign": "left"
	  },
	  "ewm": {
	    "width": 80,
	    "height": 80,
	    "marginTop": 13,
	    "marginLeft": 10
	  },
	  "mycake_liImg": {
	    "width": 80,
	    "height": 80,
	    "margin": 15
	  },
	  "title": {
	    "flexDirection": "row",
	    "display": "flex",
	    "alignItems": "center",
	    "height": 80,
	    "fontFamily": "微软雅黑"
	  },
	  "title1": {
	    "flex": 1,
	    "marginLeft": 30,
	    "fontSize": 36
	  },
	  "title2": {
	    "flex": 3,
	    "color": "#666666",
	    "fontSize": 26
	  },
	  "class": {
	    "flex": 2,
	    "textIndent": 20,
	    "fontFamily": "微软雅黑"
	  },
	  "mycake_liName": {
	    "flex": 2,
	    "paddingLeft": 20,
	    "color": "#666666",
	    "fontSize": 26
	  },
	  "direction": {
	    "width": 90,
	    "fontSize": 60,
	    "fontWeight": "bold",
	    "textAlign": "center",
	    "color": "#cccccc"
	  },
	  "pic_thumb": {
	    "width": 200,
	    "height": 200,
	    "borderRadius": 100
	  },
	  "text": {
	    "width": 260,
	    "textAlign": "right",
	    "color": "#666666",
	    "fontSize": 26,
	    "fontFamily": "微软雅黑",
	    "paddingRight": 40
	  },
	  "sex": {
	    "paddingLeft": 36,
	    "textIndent": 20,
	    "fontFamily": "微软雅黑",
	    "marginRight": 80
	  },
	  "sexPic": {
	    "width": 40,
	    "height": 40,
	    "margin": 35
	  },
	  "rightli": {
	    "position": "absolute",
	    "right": 20,
	    "top": 40
	  }
	}

/***/ },
/* 243 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _stringify = __webpack_require__(100);

	var _stringify2 = _interopRequireDefault(_stringify);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	__webpack_require__(4);
	var Utils = __webpack_require__(86);
	var modal = __weex_require__('@weex-module/modal');
	var storage = __weex_require__('@weex-module/storage');
	var SJevent = __weex_require__('@weex-module/SJevent');
	var globalEvent = __weex_require__('@weex-module/globalEvent');
	var datePicker = __weex_require__('@weex-module/Date-Picker');
	var picker = __weex_require__('@weex-module/picker');
	module.exports = {
	    data: function () {return {
	        dir: 'yjpt',
	        navBarHeight: 130,
	        name: '',
	        sex: '',
	        isShowMenu: 0,
	        date: '',
	        myPortraitUrl: Utils.ip + Utils.dir + '/' + 'yjpt/images/IMG_df.png',
	        arrow: Utils.ip + Utils.dir + '/' + 'yjpt/images/arrows_right.png',
	        sexPic: '',

	        myBaby: {},
	        classId: '',
	        userId: '',
	        jsessionid: ''
	    }},
	    created: function created() {
	        var self = this;
	        self.headLeft = Utils.ip + Utils.dir + '/' + 'yjpt/images/id_right_bg.png';

	        self.$on('naviBar.leftItem.click', function (e) {
	            Utils.navigate.pop(self, 'true');
	        });

	        self.$on('naviBar.rightItem.click', function (e) {

	            if (self.myBaby.gender == '未知' || self.myBaby.birthday == '') {
	                return;
	            }

	            var gender;
	            if (self.myBaby.gender == '男') {
	                gender = 1;
	            } else {
	                gender = 2;
	            }

	            Utils.fetch({
	                url: '/app/student/' + self.myBaby.userId,
	                data: 'gender=' + gender + '&birthday=' + self.myBaby.birthday,
	                dataType: 'JSON',
	                method: 'put',
	                success: function success(res) {
	                    res.data = eval('(' + res.data + ')');
	                    if (res.data.StatusCode == '200') {
	                        modal.toast({
	                            message: '修改成功',
	                            duration: '0.5'
	                        });
	                        var obj = {};
	                        obj.gender = gender;
	                        obj.birthday = self.myBaby.birthday;

	                        var Hulk = new BroadcastChannel('studentInfo');
	                        Hulk.postMessage((0, _stringify2.default)(obj));

	                        setTimeout(function () {
	                            Utils.navigate.pop(self, 'true');
	                        }, 500);
	                    }
	                }
	            });
	        });

	        storage.getItem('myBaby', function (e) {
	            if (e.data != 'undefined') {
	                self.myBaby = JSON.parse(e.data);
	                if (self.myBaby.birthday) {
	                    self.myBaby.birthday = self.myBaby.birthday.split('T')[0];
	                }
	                Utils.fetch({
	                    url: '/app/student/' + self.myBaby.id,
	                    method: 'get',
	                    dataType: 'json',
	                    success: function success(res) {
	                        if (res.data.StatusCode == '200') {
	                            self.myBaby = res.data.data.student;
	                            if (self.myBaby.gender == '1') {
	                                self.myBaby.gender = '男';
	                            } else if (self.myBaby.gender == '2') {
	                                self.myBaby.gender = '女';
	                            }

	                            if (self.myBaby.birthday) {
	                                self.myBaby.birthday = self.myBaby.birthday.split("T")[0];
	                            }
	                        }
	                    }
	                });
	            }
	        });

	        storage.getItem('userId', function (e) {
	            if (e.data != 'undefined') {
	                self.userId = e.data;
	            }
	        });

	        storage.getItem('jsessionid', function (e) {
	            if (e.data != 'undefined') {
	                self.jsessionid = e.data;
	            }
	        });
	    },
	    methods: {
	        QRScan: function QRScan() {
	            var self = this;
	            if (SJevent.PostSigalImg) {
	                var paramJson = {
	                    userId: self.userId,
	                    url: Utils.ip + Utils.dir + '/course/file',
	                    jsessionid: self.jsessionid
	                };
	                paramJson = (0, _stringify2.default)(paramJson);

	                SJevent.PostSigalImg(paramJson, function (res) {
	                    if (res == 0) {
	                        modal.toast({
	                            message: '上传失败，请稍后重试！',
	                            duration: '1'
	                        }, function () {});
	                    } else {
	                        var path = res.path;
	                        self.myBaby.avatar = res.path;
	                    }
	                });
	            } else {
	                modal.toast({
	                    message: '请在App中操作'
	                });
	            }
	        },
	        setGender: function setGender(gender) {
	            var self = this;
	            self.isShowMenu = !self.isShowMenu;
	            self.myBaby.gender = gender;
	        },
	        toggleMenu: function toggleMenu() {
	            var self = this;
	            self.isShowMenu = !self.isShowMenu;
	        },
	        setBirthday: function setBirthday() {
	            var self = this;
	            self.isShowMenu = !self.isShowMenu;
	        },
	        confirmDate: function confirmDate() {
	            var self = this;
	            self.isShowMenu = !self.isShowMenu;
	        },
	        datePicker: function datePicker(event) {
	            var self = this;
	            self.myBaby.birthday = event.value;
	        }
	    }

	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);