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

	var __weex_template__ = __webpack_require__(1)
	var __weex_script__ = __webpack_require__(2)

	__weex_define__('@weex-component/5af13fc8aebe3df97dbdf8bd8cbb21c6', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	})

	__weex_bootstrap__('@weex-component/5af13fc8aebe3df97dbdf8bd8cbb21c6',undefined,undefined)

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "children": [
	    {
	      "type": "my_checkbox",
	      "attr": {
	        "setchecked": "true",
	        "settext": "clickme"
	      },
	      "events":{
            "test": "test"
	      },
	      "style": {
	        "width": 400,
	        "height": 200
	      }
	    }
	  ]
	}
/***/ },
/* 2 */
/***/ function(module, exports) {
	module.exports = function(module, exports, __weex_require__){"use strict";
	module.exports = {
	  methods: {
	    test: function test(aaa) {
 var modal = __weex_require__('@weex-module/modal');
                    modal.toast({
                                message: aaa,
                                duration: 2
                              });
__weex_require__('@weex-module/my_log').showLog("test", aaa);
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);