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

	var __weex_template__ = __webpack_require__(380)
	var __weex_style__ = __webpack_require__(381)
	var __weex_script__ = __webpack_require__(382)

	__weex_define__('@weex-component/a0ccb4b0ad543d2fbae4748add627507', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/a0ccb4b0ad543d2fbae4748add627507',undefined,undefined)

/***/ },

/***/ 380:
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "item"
	  ],
	  "events": {
	    "click": function ($event) {this.openlistPage(this.url,$event)}
	  },
	  "children": [
	    {
	      "type": "image",
	      "classList": [
	        "pic_thumb"
	      ],
	      "attr": {
	        "src": function () {return this.pictureUrl}
	      }
	    },
	    {
	      "type": "div",
	      "classList": [
	        "title"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "style": {
	            "height": 50,
	            "flexDirection": "row"
	          },
	          "children": [
	            {
	              "type": "text",
	              "style": {
	                "color": "#000000",
	                "fontSize": 38
	              },
	              "attr": {
	                "value": function () {return this.title}
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "operating"
	              ],
	              "shown": function () {return this.operatId&&this.operatId>0},
	              "events": {
	                "click": function ($event) {this.operatPage(this.operatUrl,$event)}
	              },
	              "attr": {
	                "value": function () {return this.operating}
	              },
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "operatId"
	                  ],
	                  "attr": {
	                    "value": function () {return this.operatId}
	                  }
	                }
	              ]
	            },
	            {
	              "type": "text",
	              "classList": [
	                "operating"
	              ],
	              "shown": function () {return this.noticeId&&this.noticeId>0},
	              "events": {
	                "click": function ($event) {this.noticePage(this.noticeUrl,$event)}
	              },
	              "attr": {
	                "value": function () {return this.notice}
	              },
	              "children": [
	                {
	                  "type": "text",
	                  "classList": [
	                    "operatId"
	                  ],
	                  "attr": {
	                    "value": function () {return this.noticeId}
	                  }
	                }
	              ]
	            },
	            {
	              "type": "text",
	              "style": {
	                "flex": 1,
	                "textAlign": "right"
	              },
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": function () {return this.date}
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "style": {
	            "width": 600,
	            "height": 50,
	            "flexDirection": "row"
	          },
	          "children": [
	            {
	              "type": "text",
	              "style": {
	                "flex": 1
	              },
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": function () {return this.news_content}
	              }
	            },
	            {
	              "type": "text",
	              "shown": function () {return this.size>0},
	              "classList": [
	                "size"
	              ],
	              "attr": {
	                "value": function () {return this.size}
	              }
	            }
	          ]
	        }
	      ]
	    }
	  ]
	}

/***/ },

/***/ 381:
/***/ function(module, exports) {

	module.exports = {
	  "item": {
	    "width": 750,
	    "backgroundColor": "#FFFFFF",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#c0c0c0",
	    "height": 140,
	    "padding": 15,
	    "flexDirection": "row"
	  },
	  "pic_thumb": {
	    "width": 90,
	    "height": 100,
	    "flex": 1,
	    "marginRight": 20,
	    "borderRadius": 50
	  },
	  "title": {
	    "textAlign": "left",
	    "fontSize": 34,
	    "paddingTop": 8,
	    "color": "#666666",
	    "fontFamily": "微软雅黑",
	    "textOverflow": "ellipsis",
	    "lines": 1
	  },
	  "title_name": {
	    "width": 750,
	    "height": 80,
	    "color": "#000000",
	    "lineHeight": 80,
	    "fontSize": 32,
	    "textIndent": 20,
	    "flexDirection": "row",
	    "fontFamily": "微软雅黑"
	  },
	  "more": {
	    "width": 40,
	    "height": 80,
	    "lineHeight": 80,
	    "flex": 1,
	    "fontSize": 28,
	    "textAlign": "right",
	    "color": "#b8b8b8",
	    "paddingRight": 20
	  },
	  "operating": {
	    "fontSize": 26,
	    "backgroundColor": "#00CC99",
	    "padding": 6,
	    "height": 40,
	    "letterSpacing": 8,
	    "borderRadius": 3,
	    "color": "#ffffff",
	    "margin": 15,
	    "marginTop": 0
	  },
	  "operatId": {
	    "backgroundColor": "#F74C31",
	    "color": "#ffffff",
	    "width": 35,
	    "height": 35,
	    "left": 40,
	    "borderRadius": 25,
	    "fontSize": 26,
	    "textAlign": "center",
	    "top": -26
	  },
	  "size": {
	    "width": 40,
	    "height": 40,
	    "lineHeight": 38,
	    "color": "#ffffff",
	    "fontSize": 26,
	    "paddingLeft": 14,
	    "paddingTop": -4,
	    "backgroundColor": "#F74C31",
	    "borderRadius": 25
	  }
	}

/***/ },

/***/ 382:
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	module.exports = {
	  methods: {
	    openlistPage: function openlistPage(url) {
	      Utils.navigate.push(this, url, 'true');;
	    },
	    operatPage: function operatPage(url) {
	      Utils.navigate.push(this, url, 'true');;
	    },
	    noticePage: function noticePage(url) {
	      Utils.navigate.push(this, url, 'true');;
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ }

/******/ });