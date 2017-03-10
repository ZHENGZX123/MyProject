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

	var __weex_template__ = __webpack_require__(377)
	var __weex_style__ = __webpack_require__(378)
	var __weex_script__ = __webpack_require__(379)

	__weex_define__('@weex-component/a27fe2741583c29e624ea1aef55bd537', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/a27fe2741583c29e624ea1aef55bd537',undefined,undefined)

/***/ },

/***/ 377:
/***/ function(module, exports) {

	module.exports = {
	  "type": "scroller",
	  "classList": [
	    "content"
	  ],
	  "children": [
	    {
	      "type": "div",
	      "classList": [
	        "row"
	      ],
	      "events": {
	        "click": function ($event) {this.openinfoPage(this.url,$event)}
	      },
	      "repeat": function () {return this.infoList},
	      "children": [
	        {
	          "type": "text",
	          "classList": [
	            "title_name"
	          ],
	          "attr": {
	            "value": function () {return this.infoTitle}
	          }
	        },
	        {
	          "type": "text",
	          "classList": [
	            "lead"
	          ],
	          "attr": {
	            "value": function () {return this.articleLead}
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
	              "repeat": function () {return this.img},
	              "attr": {
	                "src": function () {return this.picUrl}
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "type"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "type_li"
	              ],
	              "style": {
	                "color": "#0099CC"
	              },
	              "attr": {
	                "value": function () {return this.infoType}
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "type_li"
	              ],
	              "attr": {
	                "value": function () {return (this.number) + '人关注'}
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "type_li"
	              ],
	              "style": {
	                "textAlign": "right"
	              },
	              "attr": {
	                "value": function () {return this.time}
	              }
	            }
	          ]
	        }
	      ]
	    }
	  ]
	}

/***/ },

/***/ 378:
/***/ function(module, exports) {

	module.exports = {
	  "content": {
	    "color": "#353535",
	    "position": "absolute",
	    "top": 0,
	    "left": 0,
	    "right": 0,
	    "bottom": 0
	  },
	  "row": {
	    "borderBottomColor": "#e5e5e5",
	    "borderBottomWidth": 1,
	    "marginBottom": 5,
	    "fontFamily": "微软雅黑"
	  },
	  "title_name": {
	    "width": 750,
	    "color": "#000000",
	    "padding": 20,
	    "fontWeight": "bold",
	    "fontSize": 38,
	    "textOverflow": "ellipsis",
	    "lines": 1
	  },
	  "lead": {
	    "width": 750,
	    "color": "#444444",
	    "paddingLeft": 20,
	    "paddingRight": 20,
	    "fontSize": 34,
	    "textOverflow": "ellipsis",
	    "lines": 1
	  },
	  "pic_list": {
	    "flexDirection": "row",
	    "flexWrap": "wrap",
	    "padding": 10,
	    "width": 750
	  },
	  "pic_li": {
	    "height": 150,
	    "width": 220,
	    "margin": 10
	  },
	  "type": {
	    "flexDirection": "row",
	    "flexFlow": "wrap",
	    "width": 730,
	    "padding": 20,
	    "paddingTop": 0
	  },
	  "type_li": {
	    "flex": 1,
	    "fontSize": 30
	  }
	}

/***/ },

/***/ 379:
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    loading: false,
	    infoList: []
	  }},
	  methods: {
	    openinfoPage: function openinfoPage(url) {
	      Utils.navigate.push(this, url, 'true');;
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ }

/******/ });