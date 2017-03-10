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

	var __weex_template__ = __webpack_require__(385)
	var __weex_style__ = __webpack_require__(386)
	var __weex_script__ = __webpack_require__(387)

	__weex_define__('@weex-component/ba25ba908d6729bbf37381c3bf213238', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/ba25ba908d6729bbf37381c3bf213238',undefined,undefined)

/***/ },

/***/ 385:
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "children": [
	    {
	      "type": "div",
	      "classList": [
	        "row"
	      ],
	      "repeat": function () {return this.subList},
	      "children": [
	        {
	          "type": "image",
	          "classList": [
	            "pic_thumb"
	          ],
	          "attr": {
	            "src": function () {return this.pictureUrl}
	          },
	          "events": {
	            "click": function ($event) {this.openPage(this.url,$event)}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "title"
	          ],
	          "style": {
	            "width": 600,
	            "flex": 3
	          },
	          "events": {
	            "click": function ($event) {this.openPage(this.url,$event)}
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "Name"
	              ],
	              "attr": {
	                "value": function () {return this.title}
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": function () {return this.news_content}
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "style": {
	            "flex": 1
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": function () {return [this.value=='订阅'?'button1':'button', 'btn']},
	              "attr": {
	                "value": function () {return this.value}
	              }
	            }
	          ]
	        }
	      ]
	    }
	  ]
	}

/***/ },

/***/ 386:
/***/ function(module, exports) {

	module.exports = {
	  "row": {
	    "width": 750,
	    "backgroundColor": "#FFFfff",
	    "justifyContent": "center",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#c0c0c0",
	    "height": 130,
	    "padding": 15,
	    "flexDirection": "row"
	  },
	  "btn": {
	    "width": 140,
	    "marginTop": 18,
	    "paddingTop": 15,
	    "paddingBottom": 15,
	    "border": "none",
	    "borderRadius": 7,
	    "color": "#ffffff",
	    "fontFamily": "微软雅黑",
	    "textAlign": "center"
	  },
	  "button": {
	    "backgroundColor": "#FF6600"
	  },
	  "button1": {
	    "backgroundColor": "#00cc99"
	  },
	  "pic_thumb": {
	    "width": 100,
	    "height": 100,
	    "marginRight": 20,
	    "borderRadius": 50
	  },
	  "Name": {
	    "color": "#000000",
	    "fontSize": 36,
	    "lineHeight": 45
	  },
	  "title": {
	    "textAlign": "left",
	    "lineHeight": 45,
	    "color": "#666666",
	    "fontFamily": "微软雅黑"
	  }
	}

/***/ },

/***/ 387:
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  data: function () {return {
	    loading: false,
	    subList: []
	  }},
	  methods: {
	    openPage: function openPage(url) {
	      Utils.navigate.push(this, url, 'true');;
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ }

/******/ });