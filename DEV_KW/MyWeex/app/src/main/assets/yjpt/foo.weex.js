// { "framework": "Vue" }
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

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(1)
	)

	/* script */
	__vue_exports__ = __webpack_require__(2)

	/* template */
	var __vue_template__ = __webpack_require__(3)
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
	__vue_options__.__file = "C:\\Users\\Administrator\\demo\\src\\foo.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	for (var name in module) {
	__vue_options__.style[name] = module[name]
	}
	})

	module.exports = __vue_exports__
	module.exports.el = 'true'
	new Vue(module.exports)


/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "header": {
	    "paddingTop": 15,
	    "paddingLeft": 40,
	    "height": 100,
	    "backgroundColor": "rgb(230,230,230)"
	  },
	  "conceal": {
	    "backgroundColor": "#ffffff",
	    "width": 650,
	    "height": 70,
	    "borderRadius": 10,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "text": {
	    "color": "rgb(120,120,120)"
	  },
	  "input": {
	    "marginLeft": 1,
	    "width": 650,
	    "flexDirection": "row"
	  },
	  "int": {
	    "paddingLeft": 5,
	    "width": 550,
	    "height": 70,
	    "marginRight": 20
	  },
	  "in_text": {
	    "marginTop": 10
	  },
	  "nav": {
	    "backgroundColor": "rgb(250,250,250)",
	    "flexDirection": "row",
	    "height": 80
	  },
	  "n_c": {
	    "justifyContent": "center",
	    "alignItems": "center",
	    "flex": 2
	  },
	  "more": {
	    "height": 60,
	    "marginTop": 10,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderLeftStyle": "solid",
	    "borderLeftColor": "rgb(0,0,0)",
	    "borderLeftWidth": 1,
	    "flex": 1
	  },
	  "g": {
	    "color": "#008000"
	  },
	  "selected": {
	    "borderBottomWidth": 4,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#008000"
	  },
	  "list": {
	    "position": "absolute",
	    "top": 185,
	    "left": 0,
	    "right": 0,
	    "backgroundColor": "rgb(240,240,240)"
	  },
	  "row": {
	    "flexDirection": "row",
	    "justifyContent": "flex-start"
	  },
	  "title": {
	    "flex": 1,
	    "height": 70,
	    "justifyContent": "center"
	  },
	  "r_text": {
	    "marginLeft": 20,
	    "width": 150,
	    "height": 50,
	    "borderRadius": 25,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "rgb(180,180,180)",
	    "backgroundColor": "#ffffff",
	    "textAlign": "center",
	    "lineHeight": 50
	  },
	  "content": {
	    "position": "absolute",
	    "top": 180,
	    "left": 0,
	    "right": 0,
	    "bottom": 80
	  },
	  "panel": {
	    "borderBottomWidth": 1,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "rgb(120,120,120)",
	    "padding": 15,
	    "flexDirection": "row"
	  },
	  "img": {
	    "width": 90,
	    "height": 90,
	    "marginRight": 20
	  },
	  "des": {
	    "fontSize": 30
	  },
	  "des1": {
	    "fontSize": 24,
	    "color": "rgb(120,120,120)"
	  },
	  "price": {
	    "color": "#FF0000"
	  },
	  "footer": {
	    "width": 100,
	    "height": 80,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "backgroundColor": "rgb(220,220,220)",
	    "borderTopStyle": "solid",
	    "borderTopWidth": 1,
	    "borderTopColor": "rgb(20,20,20)",
	    "position": "fixed",
	    "left": 0,
	    "bottom": 0,
	    "flexDirection": "row"
	  },
	  "f_t": {
	    "flex": 1,
	    "textAlign": "center"
	  }
	}

/***/ },
/* 2 */
/***/ function(module, exports) {

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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var modal = weex.requireModule("modal");
	exports.default = {
	  data: function data() {
	    return {
	      flag: true, // 假搜索
	      in_flag: false, // 真搜索
	      l_flag: false, // 列表
	      nav: [], // 导航列表
	      list: [// 标签列表
	      [{ id: "0", text: '群推荐', flag: false }, { id: "1", text: '教学教辅', flag: false }, { id: "4", text: '驱蚊器', flag: false }, { id: "5", text: '阿萨德群', flag: false }], [{ id: "6", text: '人弃我取', flag: false }, { id: "7", text: '权威说法', flag: true }, { id: "8", text: '过去玩', flag: false }, { id: "9", text: '啥事', flag: false }], [{ id: "10", text: '驱蚊器无', flag: false }]],
	      d_list: []
	    };
	  },

	  created: function created() {
	    // 添加导航标签
	    this.nav.push({ id: "0", text: '群推荐', flag: true }, { id: "1", text: '教学教辅', flag: false }, { id: "2", text: '校服校装', flag: false });

	    // 添加默认商品列表
	    this.d_list.push({ des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" });
	  },
	  methods: {
	    // 假搜索
	    hidden: function hidden(event) {
	      this.flag = false;
	      this.in_flag = true;
	    },
	    // 真搜索
	    close: function close() {
	      this.flag = true;
	      this.in_flag = false;
	    },
	    // 导航菜单选中
	    doSomething: function doSomething(i) {
	      // 清除列表
	      this.d_list.length = 0;
	      // 隐藏左右的选中样式
	      for (var _i = 0; _i < this.nav.length; _i++) {
	        this.nav[_i].flag = false;
	      }
	      // 选中样式
	      this.nav[i].flag = true;

	      if (this.nav[i].id == '0') {
	        // 添加对应的商品列表
	        this.d_list.push({ des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" });
	      }

	      if (this.nav[i].id == '1') {
	        // 添加对应的商品列表
	        this.d_list.push({ des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" });
	      }
	      if (this.nav[i].id == '6') {
	        // 添加对应的商品列表
	        this.d_list.push({ des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" }, { des: "开维教育液晶电子手写板 魔术黑板8.5吋", src: "http://img.sc.chinaz.com/upload/2014/09/16/2014091613554997.jpg", price: "￥89.08" });
	      }

	      // 关闭下拉菜单
	      this.l_flag = false;
	    },
	    // 显示更多菜单
	    show: function show() {
	      if (this.l_flag == true) {
	        this.l_flag = false;
	      } else {
	        this.l_flag = true;
	      }
	    },
	    // 选择菜单
	    chose: function chose(r, idx) {
	      var flag = true;
	      for (var i = 0; i < this.nav.length; i++) {
	        // 判断标签是否已存在
	        if (this.nav[i].text == this.list[r][idx].text) {
	          // 选中对应的标签
	          this.doSomething(i);
	          // 关闭下拉菜单
	          this.l_flag = false;
	          flag = false;
	          break;
	        };
	        // 不存在
	        flag = true;
	      }
	      if (flag) {
	        // 将选中的标签添加到导航内
	        this.nav.splice(2, 1, this.list[r][idx]);
	        // 选中导航第三个对应的标签
	        this.doSomething(2);
	        // 关闭下拉菜单
	        this.l_flag = false;
	      }
	    }
	  }
	};
	module.exports = exports["default"];

/***/ },
/* 3 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', [_c('div', {
	    staticClass: ["header"]
	  }, [(_vm.flag) ? _c('div', {
	    staticClass: ["conceal"]
	  }, [_c('text', {
	    staticClass: ["text"],
	    on: {
	      "click": _vm.hidden
	    }
	  }, [_vm._v("搜索您需要的内容")])]) : _vm._e(), (_vm.in_flag) ? _c('div', {
	    staticClass: ["input"]
	  }, [_c('input', {
	    staticClass: ["int"],
	    attrs: {
	      "type": "text",
	      "placeholder": "搜索您需要的内容"
	    }
	  }), _c('text', {
	    staticClass: ["in_text"],
	    on: {
	      "click": _vm.close
	    }
	  }, [_vm._v("取消")])]) : _vm._e()]), _c('div', {
	    staticClass: ["nav"]
	  }, [_vm._l((_vm.nav), function(info, i) {
	    return _c('div', {
	      staticClass: ["n_c"],
	      class: {
	        selected: info.flag
	      },
	      on: {
	        "click": function($event) {
	          _vm.doSomething(i, info.id)
	        }
	      }
	    }, [_c('text', {
	      class: {
	        g: info.flag
	      }
	    }, [_vm._v(_vm._s(info.text))])])
	  }), _c('div', {
	    staticClass: ["more"]
	  }, [_c('text', {
	    on: {
	      "click": _vm.show
	    }
	  }, [_vm._v("更多")])])], 2), _c('list', {
	    staticClass: ["content"]
	  }, _vm._l((_vm.d_list), function(d) {
	    return _c('cell', {
	      appendAsTree: true,
	      attrs: {
	        "append": "tree"
	      }
	    }, [_c('div', {
	      staticClass: ["panel"]
	    }, [_c('img', {
	      staticClass: ["img"],
	      attrs: {
	        "src": d.src
	      }
	    }), _c('div', {
	      staticClass: ["data"]
	    }, [_c('text', {
	      staticClass: ["des"]
	    }, [_vm._v(_vm._s(d.des))]), _c('text', {
	      staticClass: ["des1"]
	    }, [_vm._v(_vm._s(d.des))]), _c('text', {
	      staticClass: ["price"]
	    }, [_vm._v(_vm._s(d.price))])])])])
	  })), (_vm.l_flag) ? _c('div', {
	    staticClass: ["list"]
	  }, _vm._l((_vm.list), function(row, r) {
	    return _c('div', {
	      staticClass: ["row"]
	    }, _vm._l((row), function(l, i) {
	      return _c('div', {
	        staticClass: ["title"]
	      }, [_c('text', {
	        staticClass: ["r_text"],
	        on: {
	          "click": function($event) {
	            _vm.chose(r, i)
	          }
	        }
	      }, [_vm._v(_vm._s(l.text))])])
	    }))
	  })) : _vm._e(), _vm._m(0)])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["footer"]
	  }, [_c('text', {
	    staticClass: ["f_t"]
	  }, [_vm._v("家校")]), _c('text', {
	    staticClass: ["f_t"]
	  }, [_vm._v("学习")]), _c('text', {
	    staticClass: ["f_t"]
	  }, [_vm._v("服务")]), _c('text', {
	    staticClass: ["f_t"]
	  }, [_vm._v("我的")])])
	}]}
	module.exports.render._withStripped = true

/***/ }
/******/ ]);