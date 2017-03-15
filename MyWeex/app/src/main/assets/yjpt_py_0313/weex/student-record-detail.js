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
/******/ ({

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(365)
	var __weex_style__ = __webpack_require__(366)
	var __weex_script__ = __webpack_require__(367)

	__weex_define__('@weex-component/7e77ec2242bc64f3dd84c74317102d8a', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/7e77ec2242bc64f3dd84c74317102d8a',undefined,undefined)

/***/ },

/***/ 16:
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

/***/ 17:
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

/***/ 18:
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

/***/ 19:
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

/***/ 20:
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

/***/ 21:
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

/***/ 22:
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

/***/ 23:
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

/***/ 24:
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

/***/ 25:
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

/***/ 26:
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

/***/ 27:
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

/***/ 28:
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

/***/ 29:
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

/***/ 30:
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

/***/ 31:
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

/***/ 32:
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

/***/ 33:
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

/***/ 34:
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

/***/ 35:
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

/***/ 36:
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

/***/ 37:
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

/***/ 38:
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

/***/ 39:
/***/ function(module, exports) {

	module.exports = {
	  "wrap": {
	    "overflow": "hidden"
	  }
	}

/***/ },

/***/ 40:
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

/***/ 41:
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(42), __esModule: true };

/***/ },

/***/ 42:
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(43);
	module.exports = __webpack_require__(46).Object.assign;

/***/ },

/***/ 43:
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.1 Object.assign(target, source)
	var $export = __webpack_require__(44);

	$export($export.S + $export.F, 'Object', {assign: __webpack_require__(59)});

/***/ },

/***/ 44:
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

/***/ 45:
/***/ function(module, exports) {

	// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
	var global = module.exports = typeof window != 'undefined' && window.Math == Math
	  ? window : typeof self != 'undefined' && self.Math == Math ? self : Function('return this')();
	if(typeof __g == 'number')__g = global; // eslint-disable-line no-undef

/***/ },

/***/ 46:
/***/ function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ },

/***/ 47:
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

/***/ 48:
/***/ function(module, exports) {

	module.exports = function(it){
	  if(typeof it != 'function')throw TypeError(it + ' is not a function!');
	  return it;
	};

/***/ },

/***/ 49:
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

/***/ 50:
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

/***/ 51:
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(52);
	module.exports = function(it){
	  if(!isObject(it))throw TypeError(it + ' is not an object!');
	  return it;
	};

/***/ },

/***/ 52:
/***/ function(module, exports) {

	module.exports = function(it){
	  return typeof it === 'object' ? it !== null : typeof it === 'function';
	};

/***/ },

/***/ 53:
/***/ function(module, exports, __webpack_require__) {

	module.exports = !__webpack_require__(54) && !__webpack_require__(55)(function(){
	  return Object.defineProperty(__webpack_require__(56)('div'), 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },

/***/ 54:
/***/ function(module, exports, __webpack_require__) {

	// Thank's IE8 for his funny defineProperty
	module.exports = !__webpack_require__(55)(function(){
	  return Object.defineProperty({}, 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },

/***/ 55:
/***/ function(module, exports) {

	module.exports = function(exec){
	  try {
	    return !!exec();
	  } catch(e){
	    return true;
	  }
	};

/***/ },

/***/ 56:
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(52)
	  , document = __webpack_require__(45).document
	  // in old IE typeof document.createElement is 'object'
	  , is = isObject(document) && isObject(document.createElement);
	module.exports = function(it){
	  return is ? document.createElement(it) : {};
	};

/***/ },

/***/ 57:
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

/***/ 58:
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

/***/ 59:
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

/***/ 60:
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 / 15.2.3.14 Object.keys(O)
	var $keys       = __webpack_require__(61)
	  , enumBugKeys = __webpack_require__(74);

	module.exports = Object.keys || function keys(O){
	  return $keys(O, enumBugKeys);
	};

/***/ },

/***/ 61:
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

/***/ 62:
/***/ function(module, exports) {

	var hasOwnProperty = {}.hasOwnProperty;
	module.exports = function(it, key){
	  return hasOwnProperty.call(it, key);
	};

/***/ },

/***/ 63:
/***/ function(module, exports, __webpack_require__) {

	// to indexed object, toObject with fallback for non-array-like ES3 strings
	var IObject = __webpack_require__(64)
	  , defined = __webpack_require__(66);
	module.exports = function(it){
	  return IObject(defined(it));
	};

/***/ },

/***/ 64:
/***/ function(module, exports, __webpack_require__) {

	// fallback for non-array-like ES3 and non-enumerable old V8 strings
	var cof = __webpack_require__(65);
	module.exports = Object('z').propertyIsEnumerable(0) ? Object : function(it){
	  return cof(it) == 'String' ? it.split('') : Object(it);
	};

/***/ },

/***/ 65:
/***/ function(module, exports) {

	var toString = {}.toString;

	module.exports = function(it){
	  return toString.call(it).slice(8, -1);
	};

/***/ },

/***/ 66:
/***/ function(module, exports) {

	// 7.2.1 RequireObjectCoercible(argument)
	module.exports = function(it){
	  if(it == undefined)throw TypeError("Can't call method on  " + it);
	  return it;
	};

/***/ },

/***/ 67:
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

/***/ 68:
/***/ function(module, exports, __webpack_require__) {

	// 7.1.15 ToLength
	var toInteger = __webpack_require__(69)
	  , min       = Math.min;
	module.exports = function(it){
	  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
	};

/***/ },

/***/ 69:
/***/ function(module, exports) {

	// 7.1.4 ToInteger
	var ceil  = Math.ceil
	  , floor = Math.floor;
	module.exports = function(it){
	  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
	};

/***/ },

/***/ 70:
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(69)
	  , max       = Math.max
	  , min       = Math.min;
	module.exports = function(index, length){
	  index = toInteger(index);
	  return index < 0 ? max(index + length, 0) : min(index, length);
	};

/***/ },

/***/ 71:
/***/ function(module, exports, __webpack_require__) {

	var shared = __webpack_require__(72)('keys')
	  , uid    = __webpack_require__(73);
	module.exports = function(key){
	  return shared[key] || (shared[key] = uid(key));
	};

/***/ },

/***/ 72:
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(45)
	  , SHARED = '__core-js_shared__'
	  , store  = global[SHARED] || (global[SHARED] = {});
	module.exports = function(key){
	  return store[key] || (store[key] = {});
	};

/***/ },

/***/ 73:
/***/ function(module, exports) {

	var id = 0
	  , px = Math.random();
	module.exports = function(key){
	  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
	};

/***/ },

/***/ 74:
/***/ function(module, exports) {

	// IE 8- don't enum bug keys
	module.exports = (
	  'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'
	).split(',');

/***/ },

/***/ 75:
/***/ function(module, exports) {

	exports.f = Object.getOwnPropertySymbols;

/***/ },

/***/ 76:
/***/ function(module, exports) {

	exports.f = {}.propertyIsEnumerable;

/***/ },

/***/ 77:
/***/ function(module, exports, __webpack_require__) {

	// 7.1.13 ToObject(argument)
	var defined = __webpack_require__(66);
	module.exports = function(it){
	  return Object(defined(it));
	};

/***/ },

/***/ 78:
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

/***/ 79:
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

/***/ 80:
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

/***/ 81:
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

/***/ 82:
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

/***/ 83:
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

/***/ 84:
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

/***/ 85:
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

/***/ 86:
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

/***/ 87:
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

/***/ 88:
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

/***/ 89:
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

/***/ 90:
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

/***/ 91:
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

/***/ 92:
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

/***/ 93:
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

/***/ 94:
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

/***/ 95:
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

/***/ 96:
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

/***/ 97:
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

/***/ 98:
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

/***/ 143:
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(144), __esModule: true };

/***/ },

/***/ 144:
/***/ function(module, exports, __webpack_require__) {

	var core  = __webpack_require__(46)
	  , $JSON = core.JSON || (core.JSON = {stringify: JSON.stringify});
	module.exports = function stringify(it){ // eslint-disable-line no-unused-vars
	  return $JSON.stringify.apply($JSON, arguments);
	};

/***/ },

/***/ 365:
/***/ function(module, exports) {

	module.exports = {
	  "type": "wxc-navpage",
	  "attr": {
	    "dataRole": "none",
	    "height": function () {return this.navBarHeight},
	    "backgroundColor": "#00cc99",
	    "title": "成长足迹",
	    "titleColor": "white",
	    "leftItemSrc": function () {return this.headImg}
	  },
	  "events": {
	    "clickleftitem": "onclickleftitem"
	  },
	  "children": [
	    {
	      "type": "scroller",
	      "children": [
	        {
	          "type": "refresh",
	          "classList": [
	            "refresh"
	          ],
	          "events": {
	            "refresh": "handleRefresh"
	          },
	          "attr": {
	            "display": function () {return this.displayRefresh}
	          },
	          "style": {
	            "flexDirection": "row"
	          },
	          "children": [
	            {
	              "type": "loading-indicator"
	            },
	            {
	              "type": "text",
	              "style": {
	                "marginLeft": 36,
	                "color": "#eeeeee"
	              },
	              "attr": {
	                "value": "loading..."
	              }
	            }
	          ]
	        },
	        {
	          "type": "image",
	          "style": {
	            "width": 750,
	            "height": 340
	          },
	          "attr": {
	            "src": function () {return this.headbj}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "infoCcke"
	          ],
	          "children": [
	            {
	              "type": "div",
	              "classList": [
	                "padd_LR30"
	              ],
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "infoName"
	                  ],
	                  "attr": {
	                    "value": function () {return this.name}
	                  }
	                }
	              ]
	            },
	            {
	              "type": "image",
	              "classList": [
	                "infoPic"
	              ],
	              "attr": {
	                "src": function () {return this.photo}
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "style": {
	            "flexDirection": "row"
	          },
	          "children": [
	            {
	              "type": "div",
	              "classList": [
	                "release"
	              ],
	              "children": [
	                {
	                  "type": "image",
	                  "classList": [
	                    "releasePic"
	                  ],
	                  "attr": {
	                    "src": function () {return this.Camera}
	                  }
	                }
	              ]
	            },
	            {
	              "type": "div",
	              "classList": [
	                "relContent"
	              ],
	              "children": [
	                {
	                  "type": "div",
	                  "style": {
	                    "flexDirection": "row"
	                  },
	                  "children": [
	                    {
	                      "type": "text",
	                      "classList": [
	                        "relContent1"
	                      ],
	                      "attr": {
	                        "value": "记录了"
	                      }
	                    },
	                    {
	                      "type": "text",
	                      "classList": [
	                        "relContent2"
	                      ],
	                      "attr": {
	                        "value": function () {return this.num}
	                      }
	                    },
	                    {
	                      "type": "text",
	                      "classList": [
	                        "relContent1"
	                      ],
	                      "attr": {
	                        "value": "天的时光"
	                      }
	                    }
	                  ]
	                }
	              ]
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "cake"
	          ],
	          "repeat": function () {return this.growthJListCopy},
	          "id": function () {return this.id},
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "cakeTime"
	              ],
	              "attr": {
	                "value": function () {return this.create_time}
	              }
	            },
	            {
	              "type": "div",
	              "classList": [
	                "circle"
	              ]
	            },
	            {
	              "type": "div",
	              "classList": [
	                "cake_right"
	              ],
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "cakeContent"
	                  ],
	                  "attr": {
	                    "value": function () {return this.content}
	                  }
	                },
	                {
	                  "type": "div",
	                  "classList": [
	                    "pic_list"
	                  ],
	                  "children": [
	                    {
	                      "type": "image",
	                      "classList": [
	                        "pic_li"
	                      ],
	                      "repeat": function () {return this.imagList},
	                      "attr": {
	                        "src": function () {return this.image_path}
	                      }
	                    }
	                  ]
	                }
	              ]
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "shown": function () {return this.growthJListCopy.length<=0},
	          "children": [
	            {
	              "type": "text",
	              "style": {
	                "padding": 50,
	                "textAlign": "center"
	              },
	              "attr": {
	                "value": "暂无数据..."
	              }
	            }
	          ]
	        },
	        {
	          "type": "loading",
	          "classList": [
	            "loading"
	          ],
	          "events": {
	            "loading": "handleLoading"
	          },
	          "attr": {
	            "display": function () {return this.displayLoading}
	          },
	          "style": {
	            "background": "#fff"
	          }
	        }
	      ]
	    }
	  ]
	}

/***/ },

/***/ 366:
/***/ function(module, exports) {

	module.exports = {
	  "infoCcke": {
	    "flexDirection": "row",
	    "position": "relative",
	    "top": -60
	  },
	  "padd_LR30": {
	    "paddingLeft": 30,
	    "paddingRight": 30
	  },
	  "infoPic": {
	    "width": 130,
	    "height": 130,
	    "borderRadius": 65
	  },
	  "infoName": {
	    "fontSize": 50,
	    "fontWeight": "bold",
	    "color": "#444444"
	  },
	  "release": {
	    "borderColor": "#00cc99",
	    "borderWidth": 2,
	    "padding": 20,
	    "paddingBottom": 25,
	    "paddingTop": 25,
	    "borderRadius": 65,
	    "position": "relative",
	    "left": 115
	  },
	  "releasePic": {
	    "width": 50,
	    "height": 40
	  },
	  "relContent": {
	    "marginLeft": 150,
	    "flex": 3,
	    "paddingTop": 20
	  },
	  "relContent1": {
	    "fontSize": 40,
	    "color": "#00cc99",
	    "fontFamily": "微软雅黑"
	  },
	  "relContent2": {
	    "fontSize": 68,
	    "marginTop": -26,
	    "color": "#00cc99",
	    "fontFamily": "微软雅黑",
	    "paddingLeft": 12,
	    "paddingRight": 12
	  },
	  "cake": {
	    "width": 750,
	    "justifyContent": "center",
	    "flexDirection": "row"
	  },
	  "cakeTime": {
	    "flex": 1,
	    "width": 180,
	    "marginRight": 20,
	    "borderRightWidth": 1,
	    "borderRightColor": "#00cc99",
	    "padding": 15,
	    "color": "#00cc99",
	    "fontSize": 30,
	    "textAlign": "center",
	    "fontFamily": "微软雅黑"
	  },
	  "circle": {
	    "width": 26,
	    "height": 26,
	    "borderColor": "#00cc99",
	    "borderWidth": 2,
	    "borderRadius": 13,
	    "position": "absolute",
	    "left": 148,
	    "zIndex": 99,
	    "top": 24,
	    "backgroundColor": "#ffffff"
	  },
	  "cake_right": {
	    "width": 570,
	    "textAlign": "left",
	    "fontSize": 30,
	    "color": "#666666",
	    "fontFamily": "微软雅黑",
	    "padding": 15
	  },
	  "cakeContent": {
	    "fontSize": 30,
	    "color": "#444444",
	    "lineHeight": 45,
	    "paddingBottom": 10
	  },
	  "pic_list": {
	    "flexDirection": "row",
	    "flexWrap": "wrap"
	  },
	  "pic_li": {
	    "height": 120,
	    "width": 155,
	    "margin": 10,
	    "backgroundSize": "100% auto"
	  }
	}

/***/ },

/***/ 367:
/***/ function(module, exports, __webpack_require__) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var _stringify = __webpack_require__(143);

	var _stringify2 = _interopRequireDefault(_stringify);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	__webpack_require__(16);
	var Utils = __webpack_require__(98);
	var modal = __weex_require__('@weex-module/modal');
	var storage = __weex_require__('@weex-module/storage');
	module.exports = {
	    data: function () {return {
	        loading: false,
	        headImg: '',
	        praiseImg: '',
	        commentImg: '',
	        navBarHeight: 130,
	        num: 0,
	        name: '张老师',
	        dir: 'yjpt',
	        itemList: [{
	            content: '今日8点33分著名表演艺术家葛存壮因脑梗引发心脏衰竭去世。',
	            img: [{ picUrl: 'yjpt/images/index_17.jpg' }, { picUrl: 'yjpt/images/index_07.jpg' }, { picUrl: 'yjpt/images/index_24.jpg' }, { picUrl: 'yjpt/images/index_24.jpg' }],
	            time: '07/27 15:06'
	        }, {
	            content: '今日8点33分著名表演艺术家葛存壮因脑梗引发心脏衰竭去世。',
	            img: [{ picUrl: 'yjpt/images/index_17.jpg' }, { picUrl: 'yjpt/images/index_07.jpg' }],
	            time: '07/26 10:38'
	        }, {
	            photo: 'yjpt/images/IMG_df.png',
	            content: '今日8点33分著名表演艺术家葛存壮因脑梗引发心脏衰竭去世。',
	            img: [{ picUrl: 'yjpt/images/index_17.jpg' }, { picUrl: 'yjpt/images/index_07.jpg' }, { picUrl: 'yjpt/images/index_24.jpg' }],
	            time: '07/25 15:23'
	        }],
	        childId: '',
	        pageNo: 1,
	        pageSize: 10,
	        growthJList: [],
	        growthJListCopy: [],
	        photo: '',
	        displayRefresh: 'show',
	        displayLoading: 'show',
	        jsessionid: ''
	    }},
	    created: function created() {
	        this.headbj = Utils.ip + 'yjpt/images/5472600.jpg';
	        this.headImg = Utils.ip + 'yjpt/images/id_right_bg.png';
	        this.Camera = Utils.ip + 'yjpt/images/Camera03.png';
	        this.$on('naviBar.leftItem.click', function (e) {
	            Utils.navigate.pop(this, 'true');
	        });

	        Utils.changeImg(this.itemList, ['photo', 'picUrl'], 'img');

	        var self = this;

	        storage.getItem('jsessionid', function (e) {
	            if (e.data) {
	                self.jsessionid = e.data;
	            }
	        });

	        storage.getItem('childId', function (e) {
	            self.childId = e.data;
	            self.loading = true;
	            Utils.fetch({
	                url: '/growth/getPGrowthList',
	                data: 'childId=' + self.childId + '&pageNo=' + self.pageNo + '&pageSize=' + self.pageSize,
	                type: 'json',
	                method: 'POST',
	                success: function success(ret) {
	                    if (ret.data.retcode == '1') {
	                        self.loading = false;
	                        self.growthJList = JSON.parse((0, _stringify2.default)(ret.data.growthJList));
	                        self.growthJListCopy = JSON.parse((0, _stringify2.default)(self.growthJList));
	                        for (var i = 0; i < self.growthJListCopy.length; i++) {
	                            self.getPostTime(i, self.growthJListCopy[i].create_time);
	                            for (var j = 0; j < self.growthJListCopy[i].imagList.length; j++) {
	                                self.growthJListCopy[i].imagList[j].image_path = Utils.ip + self.dir + self.growthJListCopy[i].imagList[j].image_path + '?jsessionid=' + self.jsessionid;
	                            }
	                        }

	                        self.getDayCounts();
	                    } else {
	                        modal.toast({
	                            message: '网络故障，请稍后重试！',
	                            duration: '1'
	                        });
	                    }
	                },
	                complete: function complete(res) {
	                    if (res.status == '200') {
	                        self.loading = true;
	                    } else {
	                        self.loading = false;
	                    }
	                }
	            });
	        });

	        storage.getItem('childName', function (e) {
	            self.name = e.data;
	        });

	        storage.getItem("childPhoto", function (e) {
	            self.photo = e.data;
	            console.log(e.data);
	        });
	    },
	    methods: {
	        getPostTime: function getPostTime(i, time) {
	            var self = this;
	            var t = self.growthJListCopy[i].create_time;
	            var date = t.split(" ")[0];
	            var time = t.split(" ")[1];

	            var d1 = date.split('-').slice(1, 3).join('/');
	            var t1 = time.split(':').slice(0, 2).join(':');

	            self.growthJListCopy[i].create_time = d1 + ' ' + t1;
	        },

	        handleLoading: function handleLoading(e) {
	            var self = this;
	            self.displayLoading = 'show', modal.toast({
	                message: 'loading...'
	            });
	            self.getFormerData();
	            self.$scrollTo(self.growthJListCopy[self.growthJListCopy.length - 1].id);
	        },

	        handleRefresh: function handleRefresh(e) {
	            var self = this;
	            self.displayRefresh = 'show';
	            self.getLatestData();
	        },

	        getFormerData: function getFormerData() {
	            var self = this;
	            self.pageNo += 1;
	            Utils.fetch({
	                url: '/growth/getPGrowthList',
	                data: 'childId=' + self.childId + '&pageNo=' + self.pageNo + '&pageSize=' + self.pageSize,
	                type: 'json',
	                method: 'POST',
	                success: function success(ret) {
	                    if (ret.data.retcode == '1') {
	                        self.growthJList = self.growthJList.concat(JSON.parse((0, _stringify2.default)(ret.data.growthJList)));
	                        self.growthJListCopy = JSON.parse((0, _stringify2.default)(self.growthJList));
	                        for (var i = 0; i < self.growthJListCopy.length; i++) {
	                            self.getPostTime(i, self.growthJListCopy[i].create_time);
	                            for (var j in self.growthJListCopy[i].imagList) {
	                                self.growthJListCopy[i].imagList[j].image_path = Utils.ip + self.dir + self.growthJListCopy[i].imagList[j].image_path + '?jsessionid=' + self.jsessionid;
	                            }
	                        }
	                        self.displayLoading = 'hide';
	                        self.getDayCounts();
	                    } else {
	                        modal.toast({
	                            message: '网络故障，请稍后重试！',
	                            duration: '1'
	                        });
	                    }
	                }
	            });
	        },

	        getLatestData: function getLatestData() {
	            var self = this;
	            self.pageNo = 1;
	            Utils.fetch({
	                url: '/growth/getPGrowthList',
	                data: 'childId=' + self.childId + '&pageNo=' + self.pageNo + '&pageSize=' + self.pageSize,
	                type: 'json',
	                method: 'POST',
	                success: function success(ret) {
	                    if (ret.data.retcode == '1') {
	                        self.growthJList = JSON.parse((0, _stringify2.default)(ret.data.growthJList));
	                        self.growthJListCopy = JSON.parse((0, _stringify2.default)(self.growthJList));
	                        for (var i = 0; i < self.growthJListCopy.length; i++) {
	                            self.getPostTime(i, self.growthJListCopy[i].create_time);

	                            for (var j = 0; j < self.growthJListCopy[i].imagList.length; j++) {
	                                self.growthJListCopy[i].imagList[j].image_path = Utils.ip + self.dir + self.growthJListCopy[i].imagList[j].image_path + '?jsessionid=' + self.jsessionid;
	                            }
	                        }

	                        self.displayRefresh = 'hide';
	                        self.getDayCounts();
	                    } else {
	                        modal.toast({
	                            message: '网络故障，请稍后重试！',
	                            duration: '1'
	                        });
	                    }
	                }
	            });
	        },

	        getDayCounts: function getDayCounts() {
	            var self = this;
	            var days = [];
	            for (var i in self.growthJList) {
	                days.push(self.growthJList[i].create_time.split(" ")[0]);
	            }

	            var res = [];
	            var json = {};

	            for (var i = 0; i < days.length; i++) {
	                if (!json[days[i]]) {
	                    res.push(days[i]);
	                    json[days[i]] = 1;
	                }
	            }
	            self.num = res.length;
	        }
	    }
	};}
	/* generated by weex-loader */


/***/ }

/******/ });