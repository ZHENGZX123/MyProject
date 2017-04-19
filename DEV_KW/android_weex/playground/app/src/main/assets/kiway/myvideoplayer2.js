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

	__weex_define__('@weex-component/84e941fd8ee157bc015e403590212e66', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_module__.exports.template = __weex_template__

	})

	__weex_bootstrap__('@weex-component/84e941fd8ee157bc015e403590212e66',undefined,undefined)

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "children": [
	    {
	      "type": "my_videoplayer2",
	      "attr": {
//	        "src":"http://qiubai-video.qiushibaike.com/91B2TEYP9D300XXH_3g.mp4"
//            "src": "http://g.tbcdn.cn/ali-wireless-h5/res/0.0.6/toy.mp4",
	        "url": "[\"file:///mnt/sdcard/www/video/test.mp4\",\"file:///mnt/sdcard/www/video/test1.mp4\"]"
//	      "url": "file:///mnt/sdcard/www/video/test1.mp4"
	      },

	      "style": {
          	        "width": 750,
          	        "height": 800
          	      }
	    }
	  ]
	}

/***/ }
/******/ ]);