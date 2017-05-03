// { "framework": "Vue" }

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 8);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(22)
)

/* script */
__vue_exports__ = __webpack_require__(18)

/* template */
var __vue_template__ = __webpack_require__(26)
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
__vue_options__.__file = "F:\\weex-yqyd_sx0412\\src\\components\\footer.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-16e8b8b1"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(25)
)

/* script */
__vue_exports__ = __webpack_require__(19)

/* template */
var __vue_template__ = __webpack_require__(29)
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
__vue_options__.__file = "F:\\weex-yqyd_sx0412\\src\\components\\index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-32d456a8"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.host = host;
exports.https = https;
exports.timeAgo = timeAgo;
exports.unescape = unescape;
function host(url) {
  if (!url) return '';
  var host = url.replace(/^https?:\/\//, '').replace(/\/.*$/, '');
  var parts = host.split('.').slice(-3);
  if (parts[0] === 'www') parts.shift();
  return parts.join('.');
}

function https(url) {
  if (WXEnvironment.platform === 'iOS' && typeof url === 'string') {
    return url.replace(/^http\:/, 'https:');
  }
  return '';
}

function timeAgo(time) {
  var between = Date.now() / 1000 - Number(time);
  if (between < 3600) {
    return pluralize(~~(between / 60), ' minute');
  } else if (between < 86400) {
    return pluralize(~~(between / 3600), ' hour');
  } else {
    return pluralize(~~(between / 86400), ' day');
  }
}

function pluralize(time, label) {
  if (time === 1) {
    return time + label;
  }
  return time + label + 's';
}

function unescape(text) {
  var res = text || '';[['<p>', '\n'], ['&amp;', '&'], ['&amp;', '&'], ['&apos;', '\''], ['&#x27;', '\''], ['&#x2F;', '/'], ['&#39;', '\''], ['&#47;', '/'], ['&lt;', '<'], ['&gt;', '>'], ['&nbsp;', ' '], ['&quot;', '"']].forEach(function (pair) {
    res = res.replace(new RegExp(pair[0], 'ig'), pair[1]);
  });

  return res;
}

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = {
  methods: {
    jump: function jump(to) {
      if (this.$router) {
        this.$router.push(to);
      }
    }
  }
};

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _vueRouter = __webpack_require__(13);

var _vueRouter2 = _interopRequireDefault(_vueRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*import StoriesView from './views/StoriesView.vue'
import ArticleView from './views/ArticleView.vue'
import CommentView from './views/CommentView.vue'
import UserView from './views/UserView.vue'*/

Vue.use(_vueRouter2.default); // import Vue from 'vue'


_vueRouter2.default.prototype.goBack = function () {
  this.isBack = true;
  window.history.go(-1);
};
// Story view factory
/*function createStoriesView (type) {
  return {
    name: `${type}-stories-view`,
    render (createElement) {
      return createElement(StoriesView, { props: { type }})
    }
  }
}
*/
exports.default = new _vueRouter2.default({
  // mode: 'abstract',
  routes: [{ path: '/', name: 'index', component: __webpack_require__(1) },
  // // 书库
  { path: '/index', name: 'index', component: __webpack_require__(1) }, { path: '/sc', name: 'sc', component: __webpack_require__(15) },
  // 阅读资讯 详情
  { path: '/ydzx/xq', name: 'ydzx_xq', component: __webpack_require__(16) }]
});

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _vuex = __webpack_require__(14);

var _vuex2 = _interopRequireDefault(_vuex);

var _actions = __webpack_require__(9);

var actions = _interopRequireWildcard(_actions);

var _mutations = __webpack_require__(11);

var mutations = _interopRequireWildcard(_mutations);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// Vuex is auto installed on the web
if (WXEnvironment.platform !== 'Web') {
  Vue.use(_vuex2.default);
} // import Vue from 'vue'


var store = new _vuex2.default.Store({
  actions: actions,
  mutations: mutations,

  state: {
    activeType: null,
    items: {},
    users: {},
    counts: {
      top: 20,
      new: 20,
      show: 15,
      ask: 15,
      job: 15
    },
    lists: {
      top: [],
      new: [],
      show: [],
      ask: [],
      job: []
    }
  },

  getters: {
    // ids of the items that should be currently displayed based on
    // current list type and current pagination
    activeIds: function activeIds(state) {
      var activeType = state.activeType,
          lists = state.lists,
          counts = state.counts;

      return activeType ? lists[activeType].slice(0, counts[activeType]) : [];
    },


    // items that should be currently displayed.
    // this Array may not be fully fetched.
    activeItems: function activeItems(state, getters) {
      return getters.activeIds.map(function (id) {
        return state.items[id];
      }).filter(function (_) {
        return _;
      });
    }
  }
});

exports.default = store;

/***/ }),
/* 6 */
/***/ (function(module, exports) {

exports.sync = function (store, router, options) {
  var moduleName = (options || {}).moduleName || 'route'

  store.registerModule(moduleName, {
    state: cloneRoute(router.currentRoute),
    mutations: {
      'router/ROUTE_CHANGED': function (state, transition) {
        store.state[moduleName] = cloneRoute(transition.to, transition.from)
      }
    }
  })

  var isTimeTraveling = false
  var currentPath

  // sync router on store change
  store.watch(
    function (state) { return state[moduleName] },
    function (route) {
      if (route.fullPath === currentPath) {
        return
      }
      isTimeTraveling = true
      currentPath = route.fullPath
      router.push(route)
    },
    { sync: true }
  )

  // sync store on router navigation
  router.afterEach(function (to, from) {
    if (isTimeTraveling) {
      isTimeTraveling = false
      return
    }
    currentPath = to.fullPath
    store.commit('router/ROUTE_CHANGED', { to: to, from: from })
  })
}

function cloneRoute (to, from) {
  var clone = {
    name: to.name,
    path: to.path,
    hash: to.hash,
    query: to.query,
    params: to.params,
    fullPath: to.fullPath,
    meta: to.meta
  }
  if (from) {
    clone.from = cloneRoute(from)
  }
  return Object.freeze(clone)
}


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* script */
__vue_exports__ = __webpack_require__(17)

/* template */
var __vue_template__ = __webpack_require__(30)
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
__vue_options__.__file = "F:\\weex-yqyd_sx0412\\src\\App.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _App = __webpack_require__(7);

var _App2 = _interopRequireDefault(_App);

var _router = __webpack_require__(4);

var _router2 = _interopRequireDefault(_router);

var _store = __webpack_require__(5);

var _store2 = _interopRequireDefault(_store);

var _vuexRouterSync = __webpack_require__(6);

var _filters = __webpack_require__(2);

var filters = _interopRequireWildcard(_filters);

var _mixins = __webpack_require__(3);

var _mixins2 = _interopRequireDefault(_mixins);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// sync the router with the vuex store.
// this registers `store.state.route`
// import Vue from 'vue'
(0, _vuexRouterSync.sync)(_store2.default, _router2.default);

// register global utility filters.
Object.keys(filters).forEach(function (key) {
  Vue.filter(key, filters[key]);
});

// register global mixins.
Vue.mixin(_mixins2.default);

// create the app instance.
// here we inject the router and store to all child components,
// making them available everywhere as `this.$router` and `this.$store`.
new Vue(Vue.util.extend({ el: '#root', router: _router2.default, store: _store2.default }, _App2.default));

_router2.default.push('/');

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.FETCH_LIST_DATA = FETCH_LIST_DATA;
exports.LOAD_MORE_ITEMS = LOAD_MORE_ITEMS;
exports.ENSURE_ACTIVE_ITEMS = ENSURE_ACTIVE_ITEMS;
exports.FETCH_ITEMS = FETCH_ITEMS;
exports.FETCH_USER = FETCH_USER;

var _fetch = __webpack_require__(10);

var LOAD_MORE_STEP = 10;

// ensure data for rendering given list type
function FETCH_LIST_DATA(_ref, _ref2) {
  var commit = _ref.commit,
      dispatch = _ref.dispatch,
      state = _ref.state;
  var type = _ref2.type;

  commit('SET_ACTIVE_TYPE', { type: type });
  return (0, _fetch.fetchIdsByType)(type).then(function (ids) {
    return commit('SET_LIST', { type: type, ids: ids });
  }).then(function () {
    return dispatch('ENSURE_ACTIVE_ITEMS');
  });
}

// load more items
function LOAD_MORE_ITEMS(_ref3) {
  var dispatch = _ref3.dispatch,
      state = _ref3.state;

  state.counts[state.activeType] += LOAD_MORE_STEP;
  return dispatch('ENSURE_ACTIVE_ITEMS');
}

// ensure all active items are fetched
function ENSURE_ACTIVE_ITEMS(_ref4) {
  var dispatch = _ref4.dispatch,
      getters = _ref4.getters;

  return dispatch('FETCH_ITEMS', {
    ids: getters.activeIds
  });
}

function FETCH_ITEMS(_ref5, _ref6) {
  var commit = _ref5.commit,
      state = _ref5.state;
  var ids = _ref6.ids;

  // only fetch items that we don't already have.
  var newIds = ids.filter(function (id) {
    return !state.items[id];
  });
  return newIds.length ? (0, _fetch.fetchItems)(newIds).then(function (items) {
    return commit('SET_ITEMS', { items: items });
  }) : Promise.resolve();
}

function FETCH_USER(_ref7, _ref8) {
  var commit = _ref7.commit,
      state = _ref7.state;
  var id = _ref8.id;

  return state.users[id] ? Promise.resolve(state.users[id]) : (0, _fetch.fetchUser)(id).then(function (user) {
    return commit('SET_USER', { user: user });
  });
}

/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.fetch = fetch;
exports.fetchIdsByType = fetchIdsByType;
exports.fetchItem = fetchItem;
exports.fetchItems = fetchItems;
exports.fetchUser = fetchUser;
var stream = weex.requireModule('stream');
var baseURL = 'https://hacker-news.firebaseio.com/v0';

function fetch(path) {
  return new Promise(function (resolve, reject) {
    stream.fetch({
      method: 'GET',
      url: baseURL + '/' + path + '.json',
      type: 'json'
    }, function (response) {
      if (response.status == 200) {
        resolve(response.data);
      } else {
        reject(response);
      }
    }, function () {});
  });
}

function fetchIdsByType(type) {
  return fetch(type + 'stories');
}

function fetchItem(id) {
  return fetch('item/' + id);
}

function fetchItems(ids) {
  return Promise.all(ids.map(function (id) {
    return fetchItem(id);
  }));
}

function fetchUser(id) {
  return fetch('user/' + id);
}

/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.SET_ACTIVE_TYPE = SET_ACTIVE_TYPE;
exports.SET_LIST = SET_LIST;
exports.SET_ITEMS = SET_ITEMS;
exports.SET_USER = SET_USER;
function SET_ACTIVE_TYPE(state, _ref) {
  var type = _ref.type;

  state.activeType = type;
}

function SET_LIST(state, _ref2) {
  var type = _ref2.type,
      ids = _ref2.ids;

  state.lists[type] = ids;
}

function SET_ITEMS(state, _ref3) {
  var items = _ref3.items;

  items.forEach(function (item) {
    if (item) {
      Vue.set(state.items, item.id, item);
    }
  });
}

function SET_USER(state, _ref4) {
  var user = _ref4.user;

  Vue.set(state.users, user.id, user);
}

/***/ }),
/* 12 */
/***/ (function(module, exports) {

// shim for using process in browser
var process = module.exports = {};

// cached from whatever global is present so that test runners that stub it
// don't break things.  But we need to wrap it in a try catch in case it is
// wrapped in strict mode code which doesn't define any globals.  It's inside a
// function because try/catches deoptimize in certain engines.

var cachedSetTimeout;
var cachedClearTimeout;

function defaultSetTimout() {
    throw new Error('setTimeout has not been defined');
}
function defaultClearTimeout () {
    throw new Error('clearTimeout has not been defined');
}
(function () {
    try {
        if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
        } else {
            cachedSetTimeout = defaultSetTimout;
        }
    } catch (e) {
        cachedSetTimeout = defaultSetTimout;
    }
    try {
        if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
        } else {
            cachedClearTimeout = defaultClearTimeout;
        }
    } catch (e) {
        cachedClearTimeout = defaultClearTimeout;
    }
} ())
function runTimeout(fun) {
    if (cachedSetTimeout === setTimeout) {
        //normal enviroments in sane situations
        return setTimeout(fun, 0);
    }
    // if setTimeout wasn't available but was latter defined
    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
        cachedSetTimeout = setTimeout;
        return setTimeout(fun, 0);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedSetTimeout(fun, 0);
    } catch(e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
        } catch(e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
        }
    }


}
function runClearTimeout(marker) {
    if (cachedClearTimeout === clearTimeout) {
        //normal enviroments in sane situations
        return clearTimeout(marker);
    }
    // if clearTimeout wasn't available but was latter defined
    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
        cachedClearTimeout = clearTimeout;
        return clearTimeout(marker);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedClearTimeout(marker);
    } catch (e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
        } catch (e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
        }
    }



}
var queue = [];
var draining = false;
var currentQueue;
var queueIndex = -1;

function cleanUpNextTick() {
    if (!draining || !currentQueue) {
        return;
    }
    draining = false;
    if (currentQueue.length) {
        queue = currentQueue.concat(queue);
    } else {
        queueIndex = -1;
    }
    if (queue.length) {
        drainQueue();
    }
}

function drainQueue() {
    if (draining) {
        return;
    }
    var timeout = runTimeout(cleanUpNextTick);
    draining = true;

    var len = queue.length;
    while(len) {
        currentQueue = queue;
        queue = [];
        while (++queueIndex < len) {
            if (currentQueue) {
                currentQueue[queueIndex].run();
            }
        }
        queueIndex = -1;
        len = queue.length;
    }
    currentQueue = null;
    draining = false;
    runClearTimeout(timeout);
}

process.nextTick = function (fun) {
    var args = new Array(arguments.length - 1);
    if (arguments.length > 1) {
        for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
    }
    queue.push(new Item(fun, args));
    if (queue.length === 1 && !draining) {
        runTimeout(drainQueue);
    }
};

// v8 likes predictible objects
function Item(fun, array) {
    this.fun = fun;
    this.array = array;
}
Item.prototype.run = function () {
    this.fun.apply(null, this.array);
};
process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];
process.version = ''; // empty string to avoid regexp issues
process.versions = {};

function noop() {}

process.on = noop;
process.addListener = noop;
process.once = noop;
process.off = noop;
process.removeListener = noop;
process.removeAllListeners = noop;
process.emit = noop;

process.binding = function (name) {
    throw new Error('process.binding is not supported');
};

process.cwd = function () { return '/' };
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};
process.umask = function() { return 0; };


/***/ }),
/* 13 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process) {Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/**
  * vue-router v2.2.1
  * (c) 2017 Evan You
  * @license MIT
  */
/*  */

function assert (condition, message) {
  if (!condition) {
    throw new Error(("[vue-router] " + message))
  }
}

function warn (condition, message) {
  if (!condition) {
    typeof console !== 'undefined' && console.warn(("[vue-router] " + message));
  }
}

var View = {
  name: 'router-view',
  functional: true,
  props: {
    name: {
      type: String,
      default: 'default'
    }
  },
  render: function render (h, ref) {
    var props = ref.props;
    var children = ref.children;
    var parent = ref.parent;
    var data = ref.data;

    data.routerView = true;

    var name = props.name;
    var route = parent.$route;
    var cache = parent._routerViewCache || (parent._routerViewCache = {});

    // determine current view depth, also check to see if the tree
    // has been toggled inactive but kept-alive.
    var depth = 0;
    var inactive = false;
    while (parent) {
      if (parent.$vnode && parent.$vnode.data.routerView) {
        depth++;
      }
      if (parent._inactive) {
        inactive = true;
      }
      parent = parent.$parent;
    }
    data.routerViewDepth = depth;

    // render previous view if the tree is inactive and kept-alive
    if (inactive) {
      return h(cache[name], data, children)
    }

    var matched = route.matched[depth];
    // render empty node if no matched route
    if (!matched) {
      cache[name] = null;
      return h()
    }

    var component = cache[name] = matched.components[name];

    // inject instance registration hooks
    var hooks = data.hook || (data.hook = {});
    hooks.init = function (vnode) {
      matched.instances[name] = vnode.child;
    };
    hooks.prepatch = function (oldVnode, vnode) {
      matched.instances[name] = vnode.child;
    };
    hooks.destroy = function (vnode) {
      if (matched.instances[name] === vnode.child) {
        matched.instances[name] = undefined;
      }
    };

    // resolve props
    data.props = resolveProps(route, matched.props && matched.props[name]);

    return h(component, data, children)
  }
};

function resolveProps (route, config) {
  switch (typeof config) {
    case 'undefined':
      return
    case 'object':
      return config
    case 'function':
      return config(route)
    case 'boolean':
      return config ? route.params : undefined
    default:
      warn(false, ("props in \"" + (route.path) + "\" is a " + (typeof config) + ", expecting an object, function or boolean."));
  }
}

/*  */

var encodeReserveRE = /[!'()*]/g;
var encodeReserveReplacer = function (c) { return '%' + c.charCodeAt(0).toString(16); };
var commaRE = /%2C/g;

// fixed encodeURIComponent which is more comformant to RFC3986:
// - escapes [!'()*]
// - preserve commas
var encode = function (str) { return encodeURIComponent(str)
  .replace(encodeReserveRE, encodeReserveReplacer)
  .replace(commaRE, ','); };

var decode = decodeURIComponent;

function resolveQuery (
  query,
  extraQuery
) {
  if ( extraQuery === void 0 ) extraQuery = {};

  if (query) {
    var parsedQuery;
    try {
      parsedQuery = parseQuery(query);
    } catch (e) {
      process.env.NODE_ENV !== 'production' && warn(false, e.message);
      parsedQuery = {};
    }
    for (var key in extraQuery) {
      parsedQuery[key] = extraQuery[key];
    }
    return parsedQuery
  } else {
    return extraQuery
  }
}

function parseQuery (query) {
  var res = {};

  query = query.trim().replace(/^(\?|#|&)/, '');

  if (!query) {
    return res
  }

  query.split('&').forEach(function (param) {
    var parts = param.replace(/\+/g, ' ').split('=');
    var key = decode(parts.shift());
    var val = parts.length > 0
      ? decode(parts.join('='))
      : null;

    if (res[key] === undefined) {
      res[key] = val;
    } else if (Array.isArray(res[key])) {
      res[key].push(val);
    } else {
      res[key] = [res[key], val];
    }
  });

  return res
}

function stringifyQuery (obj) {
  var res = obj ? Object.keys(obj).map(function (key) {
    var val = obj[key];

    if (val === undefined) {
      return ''
    }

    if (val === null) {
      return encode(key)
    }

    if (Array.isArray(val)) {
      var result = [];
      val.slice().forEach(function (val2) {
        if (val2 === undefined) {
          return
        }
        if (val2 === null) {
          result.push(encode(key));
        } else {
          result.push(encode(key) + '=' + encode(val2));
        }
      });
      return result.join('&')
    }

    return encode(key) + '=' + encode(val)
  }).filter(function (x) { return x.length > 0; }).join('&') : null;
  return res ? ("?" + res) : ''
}

/*  */

var trailingSlashRE = /\/?$/;

function createRoute (
  record,
  location,
  redirectedFrom
) {
  var route = {
    name: location.name || (record && record.name),
    meta: (record && record.meta) || {},
    path: location.path || '/',
    hash: location.hash || '',
    query: location.query || {},
    params: location.params || {},
    fullPath: getFullPath(location),
    matched: record ? formatMatch(record) : []
  };
  if (redirectedFrom) {
    route.redirectedFrom = getFullPath(redirectedFrom);
  }
  return Object.freeze(route)
}

// the starting route that represents the initial state
var START = createRoute(null, {
  path: '/'
});

function formatMatch (record) {
  var res = [];
  while (record) {
    res.unshift(record);
    record = record.parent;
  }
  return res
}

function getFullPath (ref) {
  var path = ref.path;
  var query = ref.query; if ( query === void 0 ) query = {};
  var hash = ref.hash; if ( hash === void 0 ) hash = '';

  return (path || '/') + stringifyQuery(query) + hash
}

function isSameRoute (a, b) {
  if (b === START) {
    return a === b
  } else if (!b) {
    return false
  } else if (a.path && b.path) {
    return (
      a.path.replace(trailingSlashRE, '') === b.path.replace(trailingSlashRE, '') &&
      a.hash === b.hash &&
      isObjectEqual(a.query, b.query)
    )
  } else if (a.name && b.name) {
    return (
      a.name === b.name &&
      a.hash === b.hash &&
      isObjectEqual(a.query, b.query) &&
      isObjectEqual(a.params, b.params)
    )
  } else {
    return false
  }
}

function isObjectEqual (a, b) {
  if ( a === void 0 ) a = {};
  if ( b === void 0 ) b = {};

  var aKeys = Object.keys(a);
  var bKeys = Object.keys(b);
  if (aKeys.length !== bKeys.length) {
    return false
  }
  return aKeys.every(function (key) { return String(a[key]) === String(b[key]); })
}

function isIncludedRoute (current, target) {
  return (
    current.path.replace(trailingSlashRE, '/').indexOf(
      target.path.replace(trailingSlashRE, '/')
    ) === 0 &&
    (!target.hash || current.hash === target.hash) &&
    queryIncludes(current.query, target.query)
  )
}

function queryIncludes (current, target) {
  for (var key in target) {
    if (!(key in current)) {
      return false
    }
  }
  return true
}

/*  */

// work around weird flow bug
var toTypes = [String, Object];
var eventTypes = [String, Array];

var Link = {
  name: 'router-link',
  props: {
    to: {
      type: toTypes,
      required: true
    },
    tag: {
      type: String,
      default: 'a'
    },
    exact: Boolean,
    append: Boolean,
    replace: Boolean,
    activeClass: String,
    event: {
      type: eventTypes,
      default: 'click'
    }
  },
  render: function render (h) {
    var this$1 = this;

    var router = this.$router;
    var current = this.$route;
    var ref = router.resolve(this.to, current, this.append);
    var location = ref.location;
    var route = ref.route;
    var href = ref.href;
    var classes = {};
    var activeClass = this.activeClass || router.options.linkActiveClass || 'router-link-active';
    var compareTarget = location.path ? createRoute(null, location) : route;
    classes[activeClass] = this.exact
      ? isSameRoute(current, compareTarget)
      : isIncludedRoute(current, compareTarget);

    var handler = function (e) {
      if (guardEvent(e)) {
        if (this$1.replace) {
          router.replace(location);
        } else {
          router.push(location);
        }
      }
    };

    var on = { click: guardEvent };
    if (Array.isArray(this.event)) {
      this.event.forEach(function (e) { on[e] = handler; });
    } else {
      on[this.event] = handler;
    }

    var data = {
      class: classes
    };

    if (this.tag === 'a') {
      data.on = on;
      data.attrs = { href: href };
    } else {
      // find the first <a> child and apply listener and href
      var a = findAnchor(this.$slots.default);
      if (a) {
        // in case the <a> is a static node
        a.isStatic = false;
        var extend = _Vue.util.extend;
        var aData = a.data = extend({}, a.data);
        aData.on = on;
        var aAttrs = a.data.attrs = extend({}, a.data.attrs);
        aAttrs.href = href;
      } else {
        // doesn't have <a> child, apply listener to self
        data.on = on;
      }
    }

    return h(this.tag, data, this.$slots.default)
  }
};

function guardEvent (e) {
  // don't redirect with control keys
  if (e.metaKey || e.ctrlKey || e.shiftKey) { return }
  // don't redirect when preventDefault called
  if (e.defaultPrevented) { return }
  // don't redirect on right click
  if (e.button !== undefined && e.button !== 0) { return }
  // don't redirect if `target="_blank"`
  if (e.target && e.target.getAttribute) {
    var target = e.target.getAttribute('target');
    if (/\b_blank\b/i.test(target)) { return }
  }
  // this may be a Weex event which doesn't have this method
  if (e.preventDefault) {
    e.preventDefault();
  }
  return true
}

function findAnchor (children) {
  if (children) {
    var child;
    for (var i = 0; i < children.length; i++) {
      child = children[i];
      if (child.tag === 'a') {
        return child
      }
      if (child.children && (child = findAnchor(child.children))) {
        return child
      }
    }
  }
}

var _Vue;

function install (Vue) {
  if (install.installed) { return }
  install.installed = true;

  _Vue = Vue;

  Object.defineProperty(Vue.prototype, '$router', {
    get: function get () { return this.$root._router }
  });

  Object.defineProperty(Vue.prototype, '$route', {
    get: function get () { return this.$root._route }
  });

  Vue.mixin({
    beforeCreate: function beforeCreate () {
      if (this.$options.router) {
        this._router = this.$options.router;
        this._router.init(this);
        Vue.util.defineReactive(this, '_route', this._router.history.current);
      }
    }
  });

  Vue.component('router-view', View);
  Vue.component('router-link', Link);

  var strats = Vue.config.optionMergeStrategies;
  // use the same hook merging strategy for route hooks
  strats.beforeRouteEnter = strats.beforeRouteLeave = strats.created;
}

/*  */

var inBrowser = typeof window !== 'undefined';

/*  */

function resolvePath (
  relative,
  base,
  append
) {
  if (relative.charAt(0) === '/') {
    return relative
  }

  if (relative.charAt(0) === '?' || relative.charAt(0) === '#') {
    return base + relative
  }

  var stack = base.split('/');

  // remove trailing segment if:
  // - not appending
  // - appending to trailing slash (last segment is empty)
  if (!append || !stack[stack.length - 1]) {
    stack.pop();
  }

  // resolve relative path
  var segments = relative.replace(/^\//, '').split('/');
  for (var i = 0; i < segments.length; i++) {
    var segment = segments[i];
    if (segment === '.') {
      continue
    } else if (segment === '..') {
      stack.pop();
    } else {
      stack.push(segment);
    }
  }

  // ensure leading slash
  if (stack[0] !== '') {
    stack.unshift('');
  }

  return stack.join('/')
}

function parsePath (path) {
  var hash = '';
  var query = '';

  var hashIndex = path.indexOf('#');
  if (hashIndex >= 0) {
    hash = path.slice(hashIndex);
    path = path.slice(0, hashIndex);
  }

  var queryIndex = path.indexOf('?');
  if (queryIndex >= 0) {
    query = path.slice(queryIndex + 1);
    path = path.slice(0, queryIndex);
  }

  return {
    path: path,
    query: query,
    hash: hash
  }
}

function cleanPath (path) {
  return path.replace(/\/\//g, '/')
}

/*  */

function createRouteMap (
  routes,
  oldPathMap,
  oldNameMap
) {
  var pathMap = oldPathMap || Object.create(null);
  var nameMap = oldNameMap || Object.create(null);

  routes.forEach(function (route) {
    addRouteRecord(pathMap, nameMap, route);
  });

  return {
    pathMap: pathMap,
    nameMap: nameMap
  }
}

function addRouteRecord (
  pathMap,
  nameMap,
  route,
  parent,
  matchAs
) {
  var path = route.path;
  var name = route.name;
  if (process.env.NODE_ENV !== 'production') {
    assert(path != null, "\"path\" is required in a route configuration.");
    assert(
      typeof route.component !== 'string',
      "route config \"component\" for path: " + (String(path || name)) + " cannot be a " +
      "string id. Use an actual component instead."
    );
  }

  var record = {
    path: normalizePath(path, parent),
    components: route.components || { default: route.component },
    instances: {},
    name: name,
    parent: parent,
    matchAs: matchAs,
    redirect: route.redirect,
    beforeEnter: route.beforeEnter,
    meta: route.meta || {},
    props: route.props == null
      ? {}
      : route.components
        ? route.props
        : { default: route.props }
  };

  if (route.children) {
    // Warn if route is named and has a default child route.
    // If users navigate to this route by name, the default child will
    // not be rendered (GH Issue #629)
    if (process.env.NODE_ENV !== 'production') {
      if (route.name && route.children.some(function (child) { return /^\/?$/.test(child.path); })) {
        warn(
          false,
          "Named Route '" + (route.name) + "' has a default child route. " +
          "When navigating to this named route (:to=\"{name: '" + (route.name) + "'\"), " +
          "the default child route will not be rendered. Remove the name from " +
          "this route and use the name of the default child route for named " +
          "links instead."
        );
      }
    }
    route.children.forEach(function (child) {
      var childMatchAs = matchAs
        ? cleanPath((matchAs + "/" + (child.path)))
        : undefined;
      addRouteRecord(pathMap, nameMap, child, record, childMatchAs);
    });
  }

  if (route.alias !== undefined) {
    if (Array.isArray(route.alias)) {
      route.alias.forEach(function (alias) {
        var aliasRoute = {
          path: alias,
          children: route.children
        };
        addRouteRecord(pathMap, nameMap, aliasRoute, parent, record.path);
      });
    } else {
      var aliasRoute = {
        path: route.alias,
        children: route.children
      };
      addRouteRecord(pathMap, nameMap, aliasRoute, parent, record.path);
    }
  }

  if (!pathMap[record.path]) {
    pathMap[record.path] = record;
  }

  if (name) {
    if (!nameMap[name]) {
      nameMap[name] = record;
    } else if (process.env.NODE_ENV !== 'production' && !matchAs) {
      warn(
        false,
        "Duplicate named routes definition: " +
        "{ name: \"" + name + "\", path: \"" + (record.path) + "\" }"
      );
    }
  }
}

function normalizePath (path, parent) {
  path = path.replace(/\/$/, '');
  if (path[0] === '/') { return path }
  if (parent == null) { return path }
  return cleanPath(((parent.path) + "/" + path))
}

var index$1 = Array.isArray || function (arr) {
  return Object.prototype.toString.call(arr) == '[object Array]';
};

var isarray = index$1;

/**
 * Expose `pathToRegexp`.
 */
var index = pathToRegexp;
var parse_1 = parse;
var compile_1 = compile;
var tokensToFunction_1 = tokensToFunction;
var tokensToRegExp_1 = tokensToRegExp;

/**
 * The main path matching regexp utility.
 *
 * @type {RegExp}
 */
var PATH_REGEXP = new RegExp([
  // Match escaped characters that would otherwise appear in future matches.
  // This allows the user to escape special characters that won't transform.
  '(\\\\.)',
  // Match Express-style parameters and un-named parameters with a prefix
  // and optional suffixes. Matches appear as:
  //
  // "/:test(\\d+)?" => ["/", "test", "\d+", undefined, "?", undefined]
  // "/route(\\d+)"  => [undefined, undefined, undefined, "\d+", undefined, undefined]
  // "/*"            => ["/", undefined, undefined, undefined, undefined, "*"]
  '([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))'
].join('|'), 'g');

/**
 * Parse a string for the raw tokens.
 *
 * @param  {string}  str
 * @param  {Object=} options
 * @return {!Array}
 */
function parse (str, options) {
  var tokens = [];
  var key = 0;
  var index = 0;
  var path = '';
  var defaultDelimiter = options && options.delimiter || '/';
  var res;

  while ((res = PATH_REGEXP.exec(str)) != null) {
    var m = res[0];
    var escaped = res[1];
    var offset = res.index;
    path += str.slice(index, offset);
    index = offset + m.length;

    // Ignore already escaped sequences.
    if (escaped) {
      path += escaped[1];
      continue
    }

    var next = str[index];
    var prefix = res[2];
    var name = res[3];
    var capture = res[4];
    var group = res[5];
    var modifier = res[6];
    var asterisk = res[7];

    // Push the current path onto the tokens.
    if (path) {
      tokens.push(path);
      path = '';
    }

    var partial = prefix != null && next != null && next !== prefix;
    var repeat = modifier === '+' || modifier === '*';
    var optional = modifier === '?' || modifier === '*';
    var delimiter = res[2] || defaultDelimiter;
    var pattern = capture || group;

    tokens.push({
      name: name || key++,
      prefix: prefix || '',
      delimiter: delimiter,
      optional: optional,
      repeat: repeat,
      partial: partial,
      asterisk: !!asterisk,
      pattern: pattern ? escapeGroup(pattern) : (asterisk ? '.*' : '[^' + escapeString(delimiter) + ']+?')
    });
  }

  // Match any characters still remaining.
  if (index < str.length) {
    path += str.substr(index);
  }

  // If the path exists, push it onto the end.
  if (path) {
    tokens.push(path);
  }

  return tokens
}

/**
 * Compile a string to a template function for the path.
 *
 * @param  {string}             str
 * @param  {Object=}            options
 * @return {!function(Object=, Object=)}
 */
function compile (str, options) {
  return tokensToFunction(parse(str, options))
}

/**
 * Prettier encoding of URI path segments.
 *
 * @param  {string}
 * @return {string}
 */
function encodeURIComponentPretty (str) {
  return encodeURI(str).replace(/[\/?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
  })
}

/**
 * Encode the asterisk parameter. Similar to `pretty`, but allows slashes.
 *
 * @param  {string}
 * @return {string}
 */
function encodeAsterisk (str) {
  return encodeURI(str).replace(/[?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
  })
}

/**
 * Expose a method for transforming tokens into the path function.
 */
function tokensToFunction (tokens) {
  // Compile all the tokens into regexps.
  var matches = new Array(tokens.length);

  // Compile all the patterns before compilation.
  for (var i = 0; i < tokens.length; i++) {
    if (typeof tokens[i] === 'object') {
      matches[i] = new RegExp('^(?:' + tokens[i].pattern + ')$');
    }
  }

  return function (obj, opts) {
    var path = '';
    var data = obj || {};
    var options = opts || {};
    var encode = options.pretty ? encodeURIComponentPretty : encodeURIComponent;

    for (var i = 0; i < tokens.length; i++) {
      var token = tokens[i];

      if (typeof token === 'string') {
        path += token;

        continue
      }

      var value = data[token.name];
      var segment;

      if (value == null) {
        if (token.optional) {
          // Prepend partial segment prefixes.
          if (token.partial) {
            path += token.prefix;
          }

          continue
        } else {
          throw new TypeError('Expected "' + token.name + '" to be defined')
        }
      }

      if (isarray(value)) {
        if (!token.repeat) {
          throw new TypeError('Expected "' + token.name + '" to not repeat, but received `' + JSON.stringify(value) + '`')
        }

        if (value.length === 0) {
          if (token.optional) {
            continue
          } else {
            throw new TypeError('Expected "' + token.name + '" to not be empty')
          }
        }

        for (var j = 0; j < value.length; j++) {
          segment = encode(value[j]);

          if (!matches[i].test(segment)) {
            throw new TypeError('Expected all "' + token.name + '" to match "' + token.pattern + '", but received `' + JSON.stringify(segment) + '`')
          }

          path += (j === 0 ? token.prefix : token.delimiter) + segment;
        }

        continue
      }

      segment = token.asterisk ? encodeAsterisk(value) : encode(value);

      if (!matches[i].test(segment)) {
        throw new TypeError('Expected "' + token.name + '" to match "' + token.pattern + '", but received "' + segment + '"')
      }

      path += token.prefix + segment;
    }

    return path
  }
}

/**
 * Escape a regular expression string.
 *
 * @param  {string} str
 * @return {string}
 */
function escapeString (str) {
  return str.replace(/([.+*?=^!:${}()[\]|\/\\])/g, '\\$1')
}

/**
 * Escape the capturing group by escaping special characters and meaning.
 *
 * @param  {string} group
 * @return {string}
 */
function escapeGroup (group) {
  return group.replace(/([=!:$\/()])/g, '\\$1')
}

/**
 * Attach the keys as a property of the regexp.
 *
 * @param  {!RegExp} re
 * @param  {Array}   keys
 * @return {!RegExp}
 */
function attachKeys (re, keys) {
  re.keys = keys;
  return re
}

/**
 * Get the flags for a regexp from the options.
 *
 * @param  {Object} options
 * @return {string}
 */
function flags (options) {
  return options.sensitive ? '' : 'i'
}

/**
 * Pull out keys from a regexp.
 *
 * @param  {!RegExp} path
 * @param  {!Array}  keys
 * @return {!RegExp}
 */
function regexpToRegexp (path, keys) {
  // Use a negative lookahead to match only capturing groups.
  var groups = path.source.match(/\((?!\?)/g);

  if (groups) {
    for (var i = 0; i < groups.length; i++) {
      keys.push({
        name: i,
        prefix: null,
        delimiter: null,
        optional: false,
        repeat: false,
        partial: false,
        asterisk: false,
        pattern: null
      });
    }
  }

  return attachKeys(path, keys)
}

/**
 * Transform an array into a regexp.
 *
 * @param  {!Array}  path
 * @param  {Array}   keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function arrayToRegexp (path, keys, options) {
  var parts = [];

  for (var i = 0; i < path.length; i++) {
    parts.push(pathToRegexp(path[i], keys, options).source);
  }

  var regexp = new RegExp('(?:' + parts.join('|') + ')', flags(options));

  return attachKeys(regexp, keys)
}

/**
 * Create a path regexp from string input.
 *
 * @param  {string}  path
 * @param  {!Array}  keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function stringToRegexp (path, keys, options) {
  return tokensToRegExp(parse(path, options), keys, options)
}

/**
 * Expose a function for taking tokens and returning a RegExp.
 *
 * @param  {!Array}          tokens
 * @param  {(Array|Object)=} keys
 * @param  {Object=}         options
 * @return {!RegExp}
 */
function tokensToRegExp (tokens, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */ (keys || options);
    keys = [];
  }

  options = options || {};

  var strict = options.strict;
  var end = options.end !== false;
  var route = '';

  // Iterate over the tokens and create our regexp string.
  for (var i = 0; i < tokens.length; i++) {
    var token = tokens[i];

    if (typeof token === 'string') {
      route += escapeString(token);
    } else {
      var prefix = escapeString(token.prefix);
      var capture = '(?:' + token.pattern + ')';

      keys.push(token);

      if (token.repeat) {
        capture += '(?:' + prefix + capture + ')*';
      }

      if (token.optional) {
        if (!token.partial) {
          capture = '(?:' + prefix + '(' + capture + '))?';
        } else {
          capture = prefix + '(' + capture + ')?';
        }
      } else {
        capture = prefix + '(' + capture + ')';
      }

      route += capture;
    }
  }

  var delimiter = escapeString(options.delimiter || '/');
  var endsWithDelimiter = route.slice(-delimiter.length) === delimiter;

  // In non-strict mode we allow a slash at the end of match. If the path to
  // match already ends with a slash, we remove it for consistency. The slash
  // is valid at the end of a path match, not in the middle. This is important
  // in non-ending mode, where "/test/" shouldn't match "/test//route".
  if (!strict) {
    route = (endsWithDelimiter ? route.slice(0, -delimiter.length) : route) + '(?:' + delimiter + '(?=$))?';
  }

  if (end) {
    route += '$';
  } else {
    // In non-ending mode, we need the capturing groups to match as much as
    // possible by using a positive lookahead to the end or next path segment.
    route += strict && endsWithDelimiter ? '' : '(?=' + delimiter + '|$)';
  }

  return attachKeys(new RegExp('^' + route, flags(options)), keys)
}

/**
 * Normalize the given path string, returning a regular expression.
 *
 * An empty array can be passed in for the keys, which will hold the
 * placeholder key descriptions. For example, using `/user/:id`, `keys` will
 * contain `[{ name: 'id', delimiter: '/', optional: false, repeat: false }]`.
 *
 * @param  {(string|RegExp|Array)} path
 * @param  {(Array|Object)=}       keys
 * @param  {Object=}               options
 * @return {!RegExp}
 */
function pathToRegexp (path, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */ (keys || options);
    keys = [];
  }

  options = options || {};

  if (path instanceof RegExp) {
    return regexpToRegexp(path, /** @type {!Array} */ (keys))
  }

  if (isarray(path)) {
    return arrayToRegexp(/** @type {!Array} */ (path), /** @type {!Array} */ (keys), options)
  }

  return stringToRegexp(/** @type {string} */ (path), /** @type {!Array} */ (keys), options)
}

index.parse = parse_1;
index.compile = compile_1;
index.tokensToFunction = tokensToFunction_1;
index.tokensToRegExp = tokensToRegExp_1;

/*  */

var regexpCache = Object.create(null);

function getRouteRegex (path) {
  var hit = regexpCache[path];
  var keys, regexp;

  if (hit) {
    keys = hit.keys;
    regexp = hit.regexp;
  } else {
    keys = [];
    regexp = index(path, keys);
    regexpCache[path] = { keys: keys, regexp: regexp };
  }

  return { keys: keys, regexp: regexp }
}

var regexpCompileCache = Object.create(null);

function fillParams (
  path,
  params,
  routeMsg
) {
  try {
    var filler =
      regexpCompileCache[path] ||
      (regexpCompileCache[path] = index.compile(path));
    return filler(params || {}, { pretty: true })
  } catch (e) {
    if (process.env.NODE_ENV !== 'production') {
      warn(false, ("missing param for " + routeMsg + ": " + (e.message)));
    }
    return ''
  }
}

/*  */

function normalizeLocation (
  raw,
  current,
  append
) {
  var next = typeof raw === 'string' ? { path: raw } : raw;
  // named target
  if (next.name || next._normalized) {
    return next
  }

  // relative params
  if (!next.path && next.params && current) {
    next = assign({}, next);
    next._normalized = true;
    var params = assign(assign({}, current.params), next.params);
    if (current.name) {
      next.name = current.name;
      next.params = params;
    } else if (current.matched) {
      var rawPath = current.matched[current.matched.length - 1].path;
      next.path = fillParams(rawPath, params, ("path " + (current.path)));
    } else if (process.env.NODE_ENV !== 'production') {
      warn(false, "relative params navigation requires a current route.");
    }
    return next
  }

  var parsedPath = parsePath(next.path || '');
  var basePath = (current && current.path) || '/';
  var path = parsedPath.path
    ? resolvePath(parsedPath.path, basePath, append || next.append)
    : (current && current.path) || '/';
  var query = resolveQuery(parsedPath.query, next.query);
  var hash = next.hash || parsedPath.hash;
  if (hash && hash.charAt(0) !== '#') {
    hash = "#" + hash;
  }

  return {
    _normalized: true,
    path: path,
    query: query,
    hash: hash
  }
}

function assign (a, b) {
  for (var key in b) {
    a[key] = b[key];
  }
  return a
}

/*  */

function createMatcher (routes) {
  var ref = createRouteMap(routes);
  var pathMap = ref.pathMap;
  var nameMap = ref.nameMap;

  function addRoutes (routes) {
    createRouteMap(routes, pathMap, nameMap);
  }

  function match (
    raw,
    currentRoute,
    redirectedFrom
  ) {
    var location = normalizeLocation(raw, currentRoute);
    var name = location.name;

    if (name) {
      var record = nameMap[name];
      if (process.env.NODE_ENV !== 'production') {
        warn(record, ("Route with name '" + name + "' does not exist"));
      }
      var paramNames = getRouteRegex(record.path).keys
        .filter(function (key) { return !key.optional; })
        .map(function (key) { return key.name; });

      if (typeof location.params !== 'object') {
        location.params = {};
      }

      if (currentRoute && typeof currentRoute.params === 'object') {
        for (var key in currentRoute.params) {
          if (!(key in location.params) && paramNames.indexOf(key) > -1) {
            location.params[key] = currentRoute.params[key];
          }
        }
      }

      if (record) {
        location.path = fillParams(record.path, location.params, ("named route \"" + name + "\""));
        return _createRoute(record, location, redirectedFrom)
      }
    } else if (location.path) {
      location.params = {};
      for (var path in pathMap) {
        if (matchRoute(path, location.params, location.path)) {
          return _createRoute(pathMap[path], location, redirectedFrom)
        }
      }
    }
    // no match
    return _createRoute(null, location)
  }

  function redirect (
    record,
    location
  ) {
    var originalRedirect = record.redirect;
    var redirect = typeof originalRedirect === 'function'
        ? originalRedirect(createRoute(record, location))
        : originalRedirect;

    if (typeof redirect === 'string') {
      redirect = { path: redirect };
    }

    if (!redirect || typeof redirect !== 'object') {
      process.env.NODE_ENV !== 'production' && warn(
        false, ("invalid redirect option: " + (JSON.stringify(redirect)))
      );
      return _createRoute(null, location)
    }

    var re = redirect;
    var name = re.name;
    var path = re.path;
    var query = location.query;
    var hash = location.hash;
    var params = location.params;
    query = re.hasOwnProperty('query') ? re.query : query;
    hash = re.hasOwnProperty('hash') ? re.hash : hash;
    params = re.hasOwnProperty('params') ? re.params : params;

    if (name) {
      // resolved named direct
      var targetRecord = nameMap[name];
      if (process.env.NODE_ENV !== 'production') {
        assert(targetRecord, ("redirect failed: named route \"" + name + "\" not found."));
      }
      return match({
        _normalized: true,
        name: name,
        query: query,
        hash: hash,
        params: params
      }, undefined, location)
    } else if (path) {
      // 1. resolve relative redirect
      var rawPath = resolveRecordPath(path, record);
      // 2. resolve params
      var resolvedPath = fillParams(rawPath, params, ("redirect route with path \"" + rawPath + "\""));
      // 3. rematch with existing query and hash
      return match({
        _normalized: true,
        path: resolvedPath,
        query: query,
        hash: hash
      }, undefined, location)
    } else {
      warn(false, ("invalid redirect option: " + (JSON.stringify(redirect))));
      return _createRoute(null, location)
    }
  }

  function alias (
    record,
    location,
    matchAs
  ) {
    var aliasedPath = fillParams(matchAs, location.params, ("aliased route with path \"" + matchAs + "\""));
    var aliasedMatch = match({
      _normalized: true,
      path: aliasedPath
    });
    if (aliasedMatch) {
      var matched = aliasedMatch.matched;
      var aliasedRecord = matched[matched.length - 1];
      location.params = aliasedMatch.params;
      return _createRoute(aliasedRecord, location)
    }
    return _createRoute(null, location)
  }

  function _createRoute (
    record,
    location,
    redirectedFrom
  ) {
    if (record && record.redirect) {
      return redirect(record, redirectedFrom || location)
    }
    if (record && record.matchAs) {
      return alias(record, location, record.matchAs)
    }
    return createRoute(record, location, redirectedFrom)
  }

  return {
    match: match,
    addRoutes: addRoutes
  }
}

function matchRoute (
  path,
  params,
  pathname
) {
  var ref = getRouteRegex(path);
  var regexp = ref.regexp;
  var keys = ref.keys;
  var m = pathname.match(regexp);

  if (!m) {
    return false
  } else if (!params) {
    return true
  }

  for (var i = 1, len = m.length; i < len; ++i) {
    var key = keys[i - 1];
    var val = typeof m[i] === 'string' ? decodeURIComponent(m[i]) : m[i];
    if (key) { params[key.name] = val; }
  }

  return true
}

function resolveRecordPath (path, record) {
  return resolvePath(path, record.parent ? record.parent.path : '/', true)
}

/*  */


var positionStore = Object.create(null);

function setupScroll () {
  window.addEventListener('popstate', function (e) {
    saveScrollPosition();
    if (e.state && e.state.key) {
      setStateKey(e.state.key);
    }
  });
}

function handleScroll (
  router,
  to,
  from,
  isPop
) {
  if (!router.app) {
    return
  }

  var behavior = router.options.scrollBehavior;
  if (!behavior) {
    return
  }

  if (process.env.NODE_ENV !== 'production') {
    assert(typeof behavior === 'function', "scrollBehavior must be a function");
  }

  // wait until re-render finishes before scrolling
  router.app.$nextTick(function () {
    var position = getScrollPosition();
    var shouldScroll = behavior(to, from, isPop ? position : null);
    if (!shouldScroll) {
      return
    }
    var isObject = typeof shouldScroll === 'object';
    if (isObject && typeof shouldScroll.selector === 'string') {
      var el = document.querySelector(shouldScroll.selector);
      if (el) {
        position = getElementPosition(el);
      } else if (isValidPosition(shouldScroll)) {
        position = normalizePosition(shouldScroll);
      }
    } else if (isObject && isValidPosition(shouldScroll)) {
      position = normalizePosition(shouldScroll);
    }

    if (position) {
      window.scrollTo(position.x, position.y);
    }
  });
}

function saveScrollPosition () {
  var key = getStateKey();
  if (key) {
    positionStore[key] = {
      x: window.pageXOffset,
      y: window.pageYOffset
    };
  }
}

function getScrollPosition () {
  var key = getStateKey();
  if (key) {
    return positionStore[key]
  }
}

function getElementPosition (el) {
  var docEl = document.documentElement;
  var docRect = docEl.getBoundingClientRect();
  var elRect = el.getBoundingClientRect();
  return {
    x: elRect.left - docRect.left,
    y: elRect.top - docRect.top
  }
}

function isValidPosition (obj) {
  return isNumber(obj.x) || isNumber(obj.y)
}

function normalizePosition (obj) {
  return {
    x: isNumber(obj.x) ? obj.x : window.pageXOffset,
    y: isNumber(obj.y) ? obj.y : window.pageYOffset
  }
}

function isNumber (v) {
  return typeof v === 'number'
}

/*  */

var supportsPushState = inBrowser && (function () {
  var ua = window.navigator.userAgent;

  if (
    (ua.indexOf('Android 2.') !== -1 || ua.indexOf('Android 4.0') !== -1) &&
    ua.indexOf('Mobile Safari') !== -1 &&
    ua.indexOf('Chrome') === -1 &&
    ua.indexOf('Windows Phone') === -1
  ) {
    return false
  }

  return window.history && 'pushState' in window.history
})();

// use User Timing api (if present) for more accurate key precision
var Time = inBrowser && window.performance && window.performance.now
  ? window.performance
  : Date;

var _key = genKey();

function genKey () {
  return Time.now().toFixed(3)
}

function getStateKey () {
  return _key
}

function setStateKey (key) {
  _key = key;
}

function pushState (url, replace) {
  saveScrollPosition();
  // try...catch the pushState call to get around Safari
  // DOM Exception 18 where it limits to 100 pushState calls
  var history = window.history;
  try {
    if (replace) {
      history.replaceState({ key: _key }, '', url);
    } else {
      _key = genKey();
      history.pushState({ key: _key }, '', url);
    }
  } catch (e) {
    window.location[replace ? 'replace' : 'assign'](url);
  }
}

function replaceState (url) {
  pushState(url, true);
}

/*  */

function runQueue (queue, fn, cb) {
  var step = function (index) {
    if (index >= queue.length) {
      cb();
    } else {
      if (queue[index]) {
        fn(queue[index], function () {
          step(index + 1);
        });
      } else {
        step(index + 1);
      }
    }
  };
  step(0);
}

/*  */


var History = function History (router, base) {
  this.router = router;
  this.base = normalizeBase(base);
  // start with a route object that stands for "nowhere"
  this.current = START;
  this.pending = null;
  this.ready = false;
  this.readyCbs = [];
};

History.prototype.listen = function listen (cb) {
  this.cb = cb;
};

History.prototype.onReady = function onReady (cb) {
  if (this.ready) {
    cb();
  } else {
    this.readyCbs.push(cb);
  }
};

History.prototype.transitionTo = function transitionTo (location, onComplete, onAbort) {
    var this$1 = this;

  var route = this.router.match(location, this.current);
  this.confirmTransition(route, function () {
    this$1.updateRoute(route);
    onComplete && onComplete(route);
    this$1.ensureURL();

    // fire ready cbs once
    if (!this$1.ready) {
      this$1.ready = true;
      this$1.readyCbs.forEach(function (cb) {
        cb(route);
      });
    }
  }, onAbort);
};

History.prototype.confirmTransition = function confirmTransition (route, onComplete, onAbort) {
    var this$1 = this;

  var current = this.current;
  var abort = function () { onAbort && onAbort(); };
  if (
    isSameRoute(route, current) &&
    // in the case the route map has been dynamically appended to
    route.matched.length === current.matched.length
  ) {
    this.ensureURL();
    return abort()
  }

  var ref = resolveQueue(this.current.matched, route.matched);
    var updated = ref.updated;
    var deactivated = ref.deactivated;
    var activated = ref.activated;

  var queue = [].concat(
    // in-component leave guards
    extractLeaveGuards(deactivated),
    // global before hooks
    this.router.beforeHooks,
    // in-component update hooks
    extractUpdateHooks(updated),
    // in-config enter guards
    activated.map(function (m) { return m.beforeEnter; }),
    // async components
    resolveAsyncComponents(activated)
  );

  this.pending = route;
  var iterator = function (hook, next) {
    if (this$1.pending !== route) {
      return abort()
    }
    hook(route, current, function (to) {
      if (to === false) {
        // next(false) -> abort navigation, ensure current URL
        this$1.ensureURL(true);
        abort();
      } else if (typeof to === 'string' || typeof to === 'object') {
        // next('/') or next({ path: '/' }) -> redirect
        (typeof to === 'object' && to.replace) ? this$1.replace(to) : this$1.push(to);
        abort();
      } else {
        // confirm transition and pass on the value
        next(to);
      }
    });
  };

  runQueue(queue, iterator, function () {
    var postEnterCbs = [];
    var isValid = function () { return this$1.current === route; };
    var enterGuards = extractEnterGuards(activated, postEnterCbs, isValid);
    // wait until async components are resolved before
    // extracting in-component enter guards
    runQueue(enterGuards, iterator, function () {
      if (this$1.pending !== route) {
        return abort()
      }
      this$1.pending = null;
      onComplete(route);
      if (this$1.router.app) {
        this$1.router.app.$nextTick(function () {
          postEnterCbs.forEach(function (cb) { return cb(); });
        });
      }
    });
  });
};

History.prototype.updateRoute = function updateRoute (route) {
  var prev = this.current;
  this.current = route;
  this.cb && this.cb(route);
  this.router.afterHooks.forEach(function (hook) {
    hook && hook(route, prev);
  });
};

function normalizeBase (base) {
  if (!base) {
    if (inBrowser) {
      // respect <base> tag
      var baseEl = document.querySelector('base');
      base = (baseEl && baseEl.getAttribute('href')) || '/';
    } else {
      base = '/';
    }
  }
  // make sure there's the starting slash
  if (base.charAt(0) !== '/') {
    base = '/' + base;
  }
  // remove trailing slash
  return base.replace(/\/$/, '')
}

function resolveQueue (
  current,
  next
) {
  var i;
  var max = Math.max(current.length, next.length);
  for (i = 0; i < max; i++) {
    if (current[i] !== next[i]) {
      break
    }
  }
  return {
    updated: next.slice(0, i),
    activated: next.slice(i),
    deactivated: current.slice(i)
  }
}

function extractGuards (
  records,
  name,
  bind,
  reverse
) {
  var guards = flatMapComponents(records, function (def, instance, match, key) {
    var guard = extractGuard(def, name);
    if (guard) {
      return Array.isArray(guard)
        ? guard.map(function (guard) { return bind(guard, instance, match, key); })
        : bind(guard, instance, match, key)
    }
  });
  return flatten(reverse ? guards.reverse() : guards)
}

function extractGuard (
  def,
  key
) {
  if (typeof def !== 'function') {
    // extend now so that global mixins are applied.
    def = _Vue.extend(def);
  }
  return def.options[key]
}

function extractLeaveGuards (deactivated) {
  return extractGuards(deactivated, 'beforeRouteLeave', bindGuard, true)
}

function extractUpdateHooks (updated) {
  return extractGuards(updated, 'beforeRouteUpdate', bindGuard)
}

function bindGuard (guard, instance) {
  return function boundRouteGuard () {
    return guard.apply(instance, arguments)
  }
}

function extractEnterGuards (
  activated,
  cbs,
  isValid
) {
  return extractGuards(activated, 'beforeRouteEnter', function (guard, _, match, key) {
    return bindEnterGuard(guard, match, key, cbs, isValid)
  })
}

function bindEnterGuard (
  guard,
  match,
  key,
  cbs,
  isValid
) {
  return function routeEnterGuard (to, from, next) {
    return guard(to, from, function (cb) {
      next(cb);
      if (typeof cb === 'function') {
        cbs.push(function () {
          // #750
          // if a router-view is wrapped with an out-in transition,
          // the instance may not have been registered at this time.
          // we will need to poll for registration until current route
          // is no longer valid.
          poll(cb, match.instances, key, isValid);
        });
      }
    })
  }
}

function poll (
  cb, // somehow flow cannot infer this is a function
  instances,
  key,
  isValid
) {
  if (instances[key]) {
    cb(instances[key]);
  } else if (isValid()) {
    setTimeout(function () {
      poll(cb, instances, key, isValid);
    }, 16);
  }
}

function resolveAsyncComponents (matched) {
  return flatMapComponents(matched, function (def, _, match, key) {
    // if it's a function and doesn't have Vue options attached,
    // assume it's an async component resolve function.
    // we are not using Vue's default async resolving mechanism because
    // we want to halt the navigation until the incoming component has been
    // resolved.
    if (typeof def === 'function' && !def.options) {
      return function (to, from, next) {
        var resolve = once(function (resolvedDef) {
          match.components[key] = resolvedDef;
          next();
        });

        var reject = once(function (reason) {
          warn(false, ("Failed to resolve async component " + key + ": " + reason));
          next(false);
        });

        var res = def(resolve, reject);
        if (res && typeof res.then === 'function') {
          res.then(resolve, reject);
        }
      }
    }
  })
}

function flatMapComponents (
  matched,
  fn
) {
  return flatten(matched.map(function (m) {
    return Object.keys(m.components).map(function (key) { return fn(
      m.components[key],
      m.instances[key],
      m, key
    ); })
  }))
}

function flatten (arr) {
  return Array.prototype.concat.apply([], arr)
}

// in Webpack 2, require.ensure now also returns a Promise
// so the resolve/reject functions may get called an extra time
// if the user uses an arrow function shorthand that happens to
// return that Promise.
function once (fn) {
  var called = false;
  return function () {
    if (called) { return }
    called = true;
    return fn.apply(this, arguments)
  }
}

/*  */


var HTML5History = (function (History$$1) {
  function HTML5History (router, base) {
    var this$1 = this;

    History$$1.call(this, router, base);

    var expectScroll = router.options.scrollBehavior;

    if (expectScroll) {
      setupScroll();
    }

    window.addEventListener('popstate', function (e) {
      this$1.transitionTo(getLocation(this$1.base), function (route) {
        if (expectScroll) {
          handleScroll(router, route, this$1.current, true);
        }
      });
    });
  }

  if ( History$$1 ) HTML5History.__proto__ = History$$1;
  HTML5History.prototype = Object.create( History$$1 && History$$1.prototype );
  HTML5History.prototype.constructor = HTML5History;

  HTML5History.prototype.go = function go (n) {
    window.history.go(n);
  };

  HTML5History.prototype.push = function push (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      pushState(cleanPath(this$1.base + route.fullPath));
      handleScroll(this$1.router, route, this$1.current, false);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HTML5History.prototype.replace = function replace (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      replaceState(cleanPath(this$1.base + route.fullPath));
      handleScroll(this$1.router, route, this$1.current, false);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HTML5History.prototype.ensureURL = function ensureURL (push) {
    if (getLocation(this.base) !== this.current.fullPath) {
      var current = cleanPath(this.base + this.current.fullPath);
      push ? pushState(current) : replaceState(current);
    }
  };

  HTML5History.prototype.getCurrentLocation = function getCurrentLocation () {
    return getLocation(this.base)
  };

  return HTML5History;
}(History));

function getLocation (base) {
  var path = window.location.pathname;
  if (base && path.indexOf(base) === 0) {
    path = path.slice(base.length);
  }
  return (path || '/') + window.location.search + window.location.hash
}

/*  */


var HashHistory = (function (History$$1) {
  function HashHistory (router, base, fallback) {
    History$$1.call(this, router, base);
    // check history fallback deeplinking
    if (fallback && checkFallback(this.base)) {
      return
    }
    ensureSlash();
  }

  if ( History$$1 ) HashHistory.__proto__ = History$$1;
  HashHistory.prototype = Object.create( History$$1 && History$$1.prototype );
  HashHistory.prototype.constructor = HashHistory;

  // this is delayed until the app mounts
  // to avoid the hashchange listener being fired too early
  HashHistory.prototype.setupListeners = function setupListeners () {
    var this$1 = this;

    window.addEventListener('hashchange', function () {
      if (!ensureSlash()) {
        return
      }
      this$1.transitionTo(getHash(), function (route) {
        replaceHash(route.fullPath);
      });
    });
  };

  HashHistory.prototype.push = function push (location, onComplete, onAbort) {
    this.transitionTo(location, function (route) {
      pushHash(route.fullPath);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HashHistory.prototype.replace = function replace (location, onComplete, onAbort) {
    this.transitionTo(location, function (route) {
      replaceHash(route.fullPath);
      onComplete && onComplete(route);
    }, onAbort);
  };

  HashHistory.prototype.go = function go (n) {
    window.history.go(n);
  };

  HashHistory.prototype.ensureURL = function ensureURL (push) {
    var current = this.current.fullPath;
    if (getHash() !== current) {
      push ? pushHash(current) : replaceHash(current);
    }
  };

  HashHistory.prototype.getCurrentLocation = function getCurrentLocation () {
    return getHash()
  };

  return HashHistory;
}(History));

function checkFallback (base) {
  var location = getLocation(base);
  if (!/^\/#/.test(location)) {
    window.location.replace(
      cleanPath(base + '/#' + location)
    );
    return true
  }
}

function ensureSlash () {
  var path = getHash();
  if (path.charAt(0) === '/') {
    return true
  }
  replaceHash('/' + path);
  return false
}

function getHash () {
  // We can't use window.location.hash here because it's not
  // consistent across browsers - Firefox will pre-decode it!
  var href = window.location.href;
  var index = href.indexOf('#');
  return index === -1 ? '' : href.slice(index + 1)
}

function pushHash (path) {
  window.location.hash = path;
}

function replaceHash (path) {
  var i = window.location.href.indexOf('#');
  window.location.replace(
    window.location.href.slice(0, i >= 0 ? i : 0) + '#' + path
  );
}

/*  */


var AbstractHistory = (function (History$$1) {
  function AbstractHistory (router, base) {
    History$$1.call(this, router, base);
    this.stack = [];
    this.index = -1;
  }

  if ( History$$1 ) AbstractHistory.__proto__ = History$$1;
  AbstractHistory.prototype = Object.create( History$$1 && History$$1.prototype );
  AbstractHistory.prototype.constructor = AbstractHistory;

  AbstractHistory.prototype.push = function push (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      this$1.stack = this$1.stack.slice(0, this$1.index + 1).concat(route);
      this$1.index++;
      onComplete && onComplete(route);
    }, onAbort);
  };

  AbstractHistory.prototype.replace = function replace (location, onComplete, onAbort) {
    var this$1 = this;

    this.transitionTo(location, function (route) {
      this$1.stack = this$1.stack.slice(0, this$1.index).concat(route);
      onComplete && onComplete(route);
    }, onAbort);
  };

  AbstractHistory.prototype.go = function go (n) {
    var this$1 = this;

    var targetIndex = this.index + n;
    if (targetIndex < 0 || targetIndex >= this.stack.length) {
      return
    }
    var route = this.stack[targetIndex];
    this.confirmTransition(route, function () {
      this$1.index = targetIndex;
      this$1.updateRoute(route);
    });
  };

  AbstractHistory.prototype.getCurrentLocation = function getCurrentLocation () {
    var current = this.stack[this.stack.length - 1];
    return current ? current.fullPath : '/'
  };

  AbstractHistory.prototype.ensureURL = function ensureURL () {
    // noop
  };

  return AbstractHistory;
}(History));

/*  */

var VueRouter = function VueRouter (options) {
  if ( options === void 0 ) options = {};

  this.app = null;
  this.apps = [];
  this.options = options;
  this.beforeHooks = [];
  this.afterHooks = [];
  this.matcher = createMatcher(options.routes || []);

  var mode = options.mode || 'hash';
  this.fallback = mode === 'history' && !supportsPushState;
  if (this.fallback) {
    mode = 'hash';
  }
  if (!inBrowser) {
    mode = 'abstract';
  }
  this.mode = mode;

  switch (mode) {
    case 'history':
      this.history = new HTML5History(this, options.base);
      break
    case 'hash':
      this.history = new HashHistory(this, options.base, this.fallback);
      break
    case 'abstract':
      this.history = new AbstractHistory(this, options.base);
      break
    default:
      if (process.env.NODE_ENV !== 'production') {
        assert(false, ("invalid mode: " + mode));
      }
  }
};

var prototypeAccessors = { currentRoute: {} };

VueRouter.prototype.match = function match (
  raw,
  current,
  redirectedFrom
) {
  return this.matcher.match(raw, current, redirectedFrom)
};

prototypeAccessors.currentRoute.get = function () {
  return this.history && this.history.current
};

VueRouter.prototype.init = function init (app /* Vue component instance */) {
    var this$1 = this;

  process.env.NODE_ENV !== 'production' && assert(
    install.installed,
    "not installed. Make sure to call `Vue.use(VueRouter)` " +
    "before creating root instance."
  );

  this.apps.push(app);

  // main app already initialized.
  if (this.app) {
    return
  }

  this.app = app;

  var history = this.history;

  if (history instanceof HTML5History) {
    history.transitionTo(history.getCurrentLocation());
  } else if (history instanceof HashHistory) {
    var setupHashListener = function () {
      history.setupListeners();
    };
    history.transitionTo(
      history.getCurrentLocation(),
      setupHashListener,
      setupHashListener
    );
  }

  history.listen(function (route) {
    this$1.apps.forEach(function (app) {
      app._route = route;
    });
  });
};

VueRouter.prototype.beforeEach = function beforeEach (fn) {
  this.beforeHooks.push(fn);
};

VueRouter.prototype.afterEach = function afterEach (fn) {
  this.afterHooks.push(fn);
};

VueRouter.prototype.onReady = function onReady (cb) {
  this.history.onReady(cb);
};

VueRouter.prototype.push = function push (location, onComplete, onAbort) {
  this.history.push(location, onComplete, onAbort);
};

VueRouter.prototype.replace = function replace (location, onComplete, onAbort) {
  this.history.replace(location, onComplete, onAbort);
};

VueRouter.prototype.go = function go (n) {
  this.history.go(n);
};

VueRouter.prototype.back = function back () {
  this.go(-1);
};

VueRouter.prototype.forward = function forward () {
  this.go(1);
};

VueRouter.prototype.getMatchedComponents = function getMatchedComponents (to) {
  var route = to
    ? this.resolve(to).route
    : this.currentRoute;
  if (!route) {
    return []
  }
  return [].concat.apply([], route.matched.map(function (m) {
    return Object.keys(m.components).map(function (key) {
      return m.components[key]
    })
  }))
};

VueRouter.prototype.resolve = function resolve (
  to,
  current,
  append
) {
  var location = normalizeLocation(to, current || this.history.current, append);
  var route = this.match(location, current);
  var fullPath = route.redirectedFrom || route.fullPath;
  var base = this.history.base;
  var href = createHref(base, fullPath, this.mode);
  return {
    location: location,
    route: route,
    href: href,
    // for backwards compat
    normalizedTo: location,
    resolved: route
  }
};

VueRouter.prototype.addRoutes = function addRoutes (routes) {
  this.matcher.addRoutes(routes);
  if (this.history.current !== START) {
    this.history.transitionTo(this.history.getCurrentLocation());
  }
};

Object.defineProperties( VueRouter.prototype, prototypeAccessors );

function createHref (base, fullPath, mode) {
  var path = mode === 'hash' ? '#' + fullPath : fullPath;
  return base ? cleanPath(base + '/' + path) : path
}

VueRouter.install = install;
VueRouter.version = '2.2.1';

if (inBrowser && window.Vue) {
  window.Vue.use(VueRouter);
}

/* harmony default export */ __webpack_exports__["default"] = (VueRouter);

/* WEBPACK VAR INJECTION */}.call(__webpack_exports__, __webpack_require__(12)))

/***/ }),
/* 14 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Store", function() { return Store; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapState", function() { return mapState; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapMutations", function() { return mapMutations; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapGetters", function() { return mapGetters; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "mapActions", function() { return mapActions; });
/**
 * vuex v2.2.1
 * (c) 2017 Evan You
 * @license MIT
 */
var applyMixin = function (Vue) {
  var version = Number(Vue.version.split('.')[0]);

  if (version >= 2) {
    var usesInit = Vue.config._lifecycleHooks.indexOf('init') > -1;
    Vue.mixin(usesInit ? { init: vuexInit } : { beforeCreate: vuexInit });
  } else {
    // override init and inject vuex init procedure
    // for 1.x backwards compatibility.
    var _init = Vue.prototype._init;
    Vue.prototype._init = function (options) {
      if ( options === void 0 ) options = {};

      options.init = options.init
        ? [vuexInit].concat(options.init)
        : vuexInit;
      _init.call(this, options);
    };
  }

  /**
   * Vuex init hook, injected into each instances init hooks list.
   */

  function vuexInit () {
    var options = this.$options;
    // store injection
    if (options.store) {
      this.$store = options.store;
    } else if (options.parent && options.parent.$store) {
      this.$store = options.parent.$store;
    }
  }
};

var devtoolHook =
  typeof window !== 'undefined' &&
  window.__VUE_DEVTOOLS_GLOBAL_HOOK__;

function devtoolPlugin (store) {
  if (!devtoolHook) { return }

  store._devtoolHook = devtoolHook;

  devtoolHook.emit('vuex:init', store);

  devtoolHook.on('vuex:travel-to-state', function (targetState) {
    store.replaceState(targetState);
  });

  store.subscribe(function (mutation, state) {
    devtoolHook.emit('vuex:mutation', mutation, state);
  });
}

/**
 * Get the first item that pass the test
 * by second argument function
 *
 * @param {Array} list
 * @param {Function} f
 * @return {*}
 */
/**
 * Deep copy the given object considering circular structure.
 * This function caches all nested objects and its copies.
 * If it detects circular structure, use cached copy to avoid infinite loop.
 *
 * @param {*} obj
 * @param {Array<Object>} cache
 * @return {*}
 */


/**
 * forEach for object
 */
function forEachValue (obj, fn) {
  Object.keys(obj).forEach(function (key) { return fn(obj[key], key); });
}

function isObject (obj) {
  return obj !== null && typeof obj === 'object'
}

function isPromise (val) {
  return val && typeof val.then === 'function'
}

function assert (condition, msg) {
  if (!condition) { throw new Error(("[vuex] " + msg)) }
}

var Module = function Module (rawModule, runtime) {
  this.runtime = runtime;
  this._children = Object.create(null);
  this._rawModule = rawModule;
};

var prototypeAccessors$1 = { state: {},namespaced: {} };

prototypeAccessors$1.state.get = function () {
  return this._rawModule.state || {}
};

prototypeAccessors$1.namespaced.get = function () {
  return !!this._rawModule.namespaced
};

Module.prototype.addChild = function addChild (key, module) {
  this._children[key] = module;
};

Module.prototype.removeChild = function removeChild (key) {
  delete this._children[key];
};

Module.prototype.getChild = function getChild (key) {
  return this._children[key]
};

Module.prototype.update = function update (rawModule) {
  this._rawModule.namespaced = rawModule.namespaced;
  if (rawModule.actions) {
    this._rawModule.actions = rawModule.actions;
  }
  if (rawModule.mutations) {
    this._rawModule.mutations = rawModule.mutations;
  }
  if (rawModule.getters) {
    this._rawModule.getters = rawModule.getters;
  }
};

Module.prototype.forEachChild = function forEachChild (fn) {
  forEachValue(this._children, fn);
};

Module.prototype.forEachGetter = function forEachGetter (fn) {
  if (this._rawModule.getters) {
    forEachValue(this._rawModule.getters, fn);
  }
};

Module.prototype.forEachAction = function forEachAction (fn) {
  if (this._rawModule.actions) {
    forEachValue(this._rawModule.actions, fn);
  }
};

Module.prototype.forEachMutation = function forEachMutation (fn) {
  if (this._rawModule.mutations) {
    forEachValue(this._rawModule.mutations, fn);
  }
};

Object.defineProperties( Module.prototype, prototypeAccessors$1 );

var ModuleCollection = function ModuleCollection (rawRootModule) {
  var this$1 = this;

  // register root module (Vuex.Store options)
  this.root = new Module(rawRootModule, false);

  // register all nested modules
  if (rawRootModule.modules) {
    forEachValue(rawRootModule.modules, function (rawModule, key) {
      this$1.register([key], rawModule, false);
    });
  }
};

ModuleCollection.prototype.get = function get (path) {
  return path.reduce(function (module, key) {
    return module.getChild(key)
  }, this.root)
};

ModuleCollection.prototype.getNamespace = function getNamespace (path) {
  var module = this.root;
  return path.reduce(function (namespace, key) {
    module = module.getChild(key);
    return namespace + (module.namespaced ? key + '/' : '')
  }, '')
};

ModuleCollection.prototype.update = function update$1 (rawRootModule) {
  update(this.root, rawRootModule);
};

ModuleCollection.prototype.register = function register (path, rawModule, runtime) {
    var this$1 = this;
    if ( runtime === void 0 ) runtime = true;

  var parent = this.get(path.slice(0, -1));
  var newModule = new Module(rawModule, runtime);
  parent.addChild(path[path.length - 1], newModule);

  // register nested modules
  if (rawModule.modules) {
    forEachValue(rawModule.modules, function (rawChildModule, key) {
      this$1.register(path.concat(key), rawChildModule, runtime);
    });
  }
};

ModuleCollection.prototype.unregister = function unregister (path) {
  var parent = this.get(path.slice(0, -1));
  var key = path[path.length - 1];
  if (!parent.getChild(key).runtime) { return }

  parent.removeChild(key);
};

function update (targetModule, newModule) {
  // update target module
  targetModule.update(newModule);

  // update nested modules
  if (newModule.modules) {
    for (var key in newModule.modules) {
      if (!targetModule.getChild(key)) {
        console.warn(
          "[vuex] trying to add a new module '" + key + "' on hot reloading, " +
          'manual reload is needed'
        );
        return
      }
      update(targetModule.getChild(key), newModule.modules[key]);
    }
  }
}

var Vue; // bind on install

var Store = function Store (options) {
  var this$1 = this;
  if ( options === void 0 ) options = {};

  assert(Vue, "must call Vue.use(Vuex) before creating a store instance.");
  assert(typeof Promise !== 'undefined', "vuex requires a Promise polyfill in this browser.");

  var state = options.state; if ( state === void 0 ) state = {};
  var plugins = options.plugins; if ( plugins === void 0 ) plugins = [];
  var strict = options.strict; if ( strict === void 0 ) strict = false;

  // store internal state
  this._committing = false;
  this._actions = Object.create(null);
  this._mutations = Object.create(null);
  this._wrappedGetters = Object.create(null);
  this._modules = new ModuleCollection(options);
  this._modulesNamespaceMap = Object.create(null);
  this._subscribers = [];
  this._watcherVM = new Vue();

  // bind commit and dispatch to self
  var store = this;
  var ref = this;
  var dispatch = ref.dispatch;
  var commit = ref.commit;
  this.dispatch = function boundDispatch (type, payload) {
    return dispatch.call(store, type, payload)
  };
  this.commit = function boundCommit (type, payload, options) {
    return commit.call(store, type, payload, options)
  };

  // strict mode
  this.strict = strict;

  // init root module.
  // this also recursively registers all sub-modules
  // and collects all module getters inside this._wrappedGetters
  installModule(this, state, [], this._modules.root);

  // initialize the store vm, which is responsible for the reactivity
  // (also registers _wrappedGetters as computed properties)
  resetStoreVM(this, state);

  // apply plugins
  plugins.concat(devtoolPlugin).forEach(function (plugin) { return plugin(this$1); });
};

var prototypeAccessors = { state: {} };

prototypeAccessors.state.get = function () {
  return this._vm._data.$$state
};

prototypeAccessors.state.set = function (v) {
  assert(false, "Use store.replaceState() to explicit replace store state.");
};

Store.prototype.commit = function commit (_type, _payload, _options) {
    var this$1 = this;

  // check object-style commit
  var ref = unifyObjectStyle(_type, _payload, _options);
    var type = ref.type;
    var payload = ref.payload;
    var options = ref.options;

  var mutation = { type: type, payload: payload };
  var entry = this._mutations[type];
  if (!entry) {
    console.error(("[vuex] unknown mutation type: " + type));
    return
  }
  this._withCommit(function () {
    entry.forEach(function commitIterator (handler) {
      handler(payload);
    });
  });
  this._subscribers.forEach(function (sub) { return sub(mutation, this$1.state); });

  if (options && options.silent) {
    console.warn(
      "[vuex] mutation type: " + type + ". Silent option has been removed. " +
      'Use the filter functionality in the vue-devtools'
    );
  }
};

Store.prototype.dispatch = function dispatch (_type, _payload) {
  // check object-style dispatch
  var ref = unifyObjectStyle(_type, _payload);
    var type = ref.type;
    var payload = ref.payload;

  var entry = this._actions[type];
  if (!entry) {
    console.error(("[vuex] unknown action type: " + type));
    return
  }
  return entry.length > 1
    ? Promise.all(entry.map(function (handler) { return handler(payload); }))
    : entry[0](payload)
};

Store.prototype.subscribe = function subscribe (fn) {
  var subs = this._subscribers;
  if (subs.indexOf(fn) < 0) {
    subs.push(fn);
  }
  return function () {
    var i = subs.indexOf(fn);
    if (i > -1) {
      subs.splice(i, 1);
    }
  }
};

Store.prototype.watch = function watch (getter, cb, options) {
    var this$1 = this;

  assert(typeof getter === 'function', "store.watch only accepts a function.");
  return this._watcherVM.$watch(function () { return getter(this$1.state, this$1.getters); }, cb, options)
};

Store.prototype.replaceState = function replaceState (state) {
    var this$1 = this;

  this._withCommit(function () {
    this$1._vm._data.$$state = state;
  });
};

Store.prototype.registerModule = function registerModule (path, rawModule) {
  if (typeof path === 'string') { path = [path]; }
  assert(Array.isArray(path), "module path must be a string or an Array.");
  this._modules.register(path, rawModule);
  installModule(this, this.state, path, this._modules.get(path));
  // reset store to update getters...
  resetStoreVM(this, this.state);
};

Store.prototype.unregisterModule = function unregisterModule (path) {
    var this$1 = this;

  if (typeof path === 'string') { path = [path]; }
  assert(Array.isArray(path), "module path must be a string or an Array.");
  this._modules.unregister(path);
  this._withCommit(function () {
    var parentState = getNestedState(this$1.state, path.slice(0, -1));
    Vue.delete(parentState, path[path.length - 1]);
  });
  resetStore(this);
};

Store.prototype.hotUpdate = function hotUpdate (newOptions) {
  this._modules.update(newOptions);
  resetStore(this, true);
};

Store.prototype._withCommit = function _withCommit (fn) {
  var committing = this._committing;
  this._committing = true;
  fn();
  this._committing = committing;
};

Object.defineProperties( Store.prototype, prototypeAccessors );

function resetStore (store, hot) {
  store._actions = Object.create(null);
  store._mutations = Object.create(null);
  store._wrappedGetters = Object.create(null);
  store._modulesNamespaceMap = Object.create(null);
  var state = store.state;
  // init all modules
  installModule(store, state, [], store._modules.root, true);
  // reset vm
  resetStoreVM(store, state, hot);
}

function resetStoreVM (store, state, hot) {
  var oldVm = store._vm;

  // bind store public getters
  store.getters = {};
  var wrappedGetters = store._wrappedGetters;
  var computed = {};
  forEachValue(wrappedGetters, function (fn, key) {
    // use computed to leverage its lazy-caching mechanism
    computed[key] = function () { return fn(store); };
    Object.defineProperty(store.getters, key, {
      get: function () { return store._vm[key]; },
      enumerable: true // for local getters
    });
  });

  // use a Vue instance to store the state tree
  // suppress warnings just in case the user has added
  // some funky global mixins
  var silent = Vue.config.silent;
  Vue.config.silent = true;
  store._vm = new Vue({
    data: {
      $$state: state
    },
    computed: computed
  });
  Vue.config.silent = silent;

  // enable strict mode for new vm
  if (store.strict) {
    enableStrictMode(store);
  }

  if (oldVm) {
    if (hot) {
      // dispatch changes in all subscribed watchers
      // to force getter re-evaluation for hot reloading.
      store._withCommit(function () {
        oldVm._data.$$state = null;
      });
    }
    Vue.nextTick(function () { return oldVm.$destroy(); });
  }
}

function installModule (store, rootState, path, module, hot) {
  var isRoot = !path.length;
  var namespace = store._modules.getNamespace(path);

  // register in namespace map
  if (namespace) {
    store._modulesNamespaceMap[namespace] = module;
  }

  // set state
  if (!isRoot && !hot) {
    var parentState = getNestedState(rootState, path.slice(0, -1));
    var moduleName = path[path.length - 1];
    store._withCommit(function () {
      Vue.set(parentState, moduleName, module.state);
    });
  }

  var local = module.context = makeLocalContext(store, namespace, path);

  module.forEachMutation(function (mutation, key) {
    var namespacedType = namespace + key;
    registerMutation(store, namespacedType, mutation, local);
  });

  module.forEachAction(function (action, key) {
    var namespacedType = namespace + key;
    registerAction(store, namespacedType, action, local);
  });

  module.forEachGetter(function (getter, key) {
    var namespacedType = namespace + key;
    registerGetter(store, namespacedType, getter, local);
  });

  module.forEachChild(function (child, key) {
    installModule(store, rootState, path.concat(key), child, hot);
  });
}

/**
 * make localized dispatch, commit, getters and state
 * if there is no namespace, just use root ones
 */
function makeLocalContext (store, namespace, path) {
  var noNamespace = namespace === '';

  var local = {
    dispatch: noNamespace ? store.dispatch : function (_type, _payload, _options) {
      var args = unifyObjectStyle(_type, _payload, _options);
      var payload = args.payload;
      var options = args.options;
      var type = args.type;

      if (!options || !options.root) {
        type = namespace + type;
        if (!store._actions[type]) {
          console.error(("[vuex] unknown local action type: " + (args.type) + ", global type: " + type));
          return
        }
      }

      return store.dispatch(type, payload)
    },

    commit: noNamespace ? store.commit : function (_type, _payload, _options) {
      var args = unifyObjectStyle(_type, _payload, _options);
      var payload = args.payload;
      var options = args.options;
      var type = args.type;

      if (!options || !options.root) {
        type = namespace + type;
        if (!store._mutations[type]) {
          console.error(("[vuex] unknown local mutation type: " + (args.type) + ", global type: " + type));
          return
        }
      }

      store.commit(type, payload, options);
    }
  };

  // getters and state object must be gotten lazily
  // because they will be changed by vm update
  Object.defineProperties(local, {
    getters: {
      get: noNamespace
        ? function () { return store.getters; }
        : function () { return makeLocalGetters(store, namespace); }
    },
    state: {
      get: function () { return getNestedState(store.state, path); }
    }
  });

  return local
}

function makeLocalGetters (store, namespace) {
  var gettersProxy = {};

  var splitPos = namespace.length;
  Object.keys(store.getters).forEach(function (type) {
    // skip if the target getter is not match this namespace
    if (type.slice(0, splitPos) !== namespace) { return }

    // extract local getter type
    var localType = type.slice(splitPos);

    // Add a port to the getters proxy.
    // Define as getter property because
    // we do not want to evaluate the getters in this time.
    Object.defineProperty(gettersProxy, localType, {
      get: function () { return store.getters[type]; },
      enumerable: true
    });
  });

  return gettersProxy
}

function registerMutation (store, type, handler, local) {
  var entry = store._mutations[type] || (store._mutations[type] = []);
  entry.push(function wrappedMutationHandler (payload) {
    handler(local.state, payload);
  });
}

function registerAction (store, type, handler, local) {
  var entry = store._actions[type] || (store._actions[type] = []);
  entry.push(function wrappedActionHandler (payload, cb) {
    var res = handler({
      dispatch: local.dispatch,
      commit: local.commit,
      getters: local.getters,
      state: local.state,
      rootGetters: store.getters,
      rootState: store.state
    }, payload, cb);
    if (!isPromise(res)) {
      res = Promise.resolve(res);
    }
    if (store._devtoolHook) {
      return res.catch(function (err) {
        store._devtoolHook.emit('vuex:error', err);
        throw err
      })
    } else {
      return res
    }
  });
}

function registerGetter (store, type, rawGetter, local) {
  if (store._wrappedGetters[type]) {
    console.error(("[vuex] duplicate getter key: " + type));
    return
  }
  store._wrappedGetters[type] = function wrappedGetter (store) {
    return rawGetter(
      local.state, // local state
      local.getters, // local getters
      store.state, // root state
      store.getters // root getters
    )
  };
}

function enableStrictMode (store) {
  store._vm.$watch(function () { return this._data.$$state }, function () {
    assert(store._committing, "Do not mutate vuex store state outside mutation handlers.");
  }, { deep: true, sync: true });
}

function getNestedState (state, path) {
  return path.length
    ? path.reduce(function (state, key) { return state[key]; }, state)
    : state
}

function unifyObjectStyle (type, payload, options) {
  if (isObject(type) && type.type) {
    options = payload;
    payload = type;
    type = type.type;
  }

  assert(typeof type === 'string', ("Expects string as the type, but found " + (typeof type) + "."));

  return { type: type, payload: payload, options: options }
}

function install (_Vue) {
  if (Vue) {
    console.error(
      '[vuex] already installed. Vue.use(Vuex) should be called only once.'
    );
    return
  }
  Vue = _Vue;
  applyMixin(Vue);
}

// auto install in dist mode
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue);
}

var mapState = normalizeNamespace(function (namespace, states) {
  var res = {};
  normalizeMap(states).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    res[key] = function mappedState () {
      var state = this.$store.state;
      var getters = this.$store.getters;
      if (namespace) {
        var module = getModuleByNamespace(this.$store, 'mapState', namespace);
        if (!module) {
          return
        }
        state = module.context.state;
        getters = module.context.getters;
      }
      return typeof val === 'function'
        ? val.call(this, state, getters)
        : state[val]
    };
    // mark vuex getter for devtools
    res[key].vuex = true;
  });
  return res
});

var mapMutations = normalizeNamespace(function (namespace, mutations) {
  var res = {};
  normalizeMap(mutations).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedMutation () {
      var args = [], len = arguments.length;
      while ( len-- ) args[ len ] = arguments[ len ];

      if (namespace && !getModuleByNamespace(this.$store, 'mapMutations', namespace)) {
        return
      }
      return this.$store.commit.apply(this.$store, [val].concat(args))
    };
  });
  return res
});

var mapGetters = normalizeNamespace(function (namespace, getters) {
  var res = {};
  normalizeMap(getters).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedGetter () {
      if (namespace && !getModuleByNamespace(this.$store, 'mapGetters', namespace)) {
        return
      }
      if (!(val in this.$store.getters)) {
        console.error(("[vuex] unknown getter: " + val));
        return
      }
      return this.$store.getters[val]
    };
    // mark vuex getter for devtools
    res[key].vuex = true;
  });
  return res
});

var mapActions = normalizeNamespace(function (namespace, actions) {
  var res = {};
  normalizeMap(actions).forEach(function (ref) {
    var key = ref.key;
    var val = ref.val;

    val = namespace + val;
    res[key] = function mappedAction () {
      var args = [], len = arguments.length;
      while ( len-- ) args[ len ] = arguments[ len ];

      if (namespace && !getModuleByNamespace(this.$store, 'mapActions', namespace)) {
        return
      }
      return this.$store.dispatch.apply(this.$store, [val].concat(args))
    };
  });
  return res
});

function normalizeMap (map) {
  return Array.isArray(map)
    ? map.map(function (key) { return ({ key: key, val: key }); })
    : Object.keys(map).map(function (key) { return ({ key: key, val: map[key] }); })
}

function normalizeNamespace (fn) {
  return function (namespace, map) {
    if (typeof namespace !== 'string') {
      map = namespace;
      namespace = '';
    } else if (namespace.charAt(namespace.length - 1) !== '/') {
      namespace += '/';
    }
    return fn(namespace, map)
  }
}

function getModuleByNamespace (store, helper, namespace) {
  var module = store._modulesNamespaceMap[namespace];
  if (!module) {
    console.error(("[vuex] module namespace not found in " + helper + "(): " + namespace));
  }
  return module
}

var index_esm = {
  Store: Store,
  install: install,
  version: '2.2.1',
  mapState: mapState,
  mapMutations: mapMutations,
  mapGetters: mapGetters,
  mapActions: mapActions
};

/* harmony default export */ __webpack_exports__["default"] = (index_esm);


/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(24)
)

/* script */
__vue_exports__ = __webpack_require__(20)

/* template */
var __vue_template__ = __webpack_require__(28)
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
__vue_options__.__file = "F:\\weex-yqyd_sx0412\\src\\components\\sc.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-2c494434"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(23)
)

/* script */
__vue_exports__ = __webpack_require__(21)

/* template */
var __vue_template__ = __webpack_require__(27)
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
__vue_options__.__file = "F:\\weex-yqyd_sx0412\\src\\components\\ydzx_xq.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-22dc6049"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

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

exports.default = {
  methods: {
    back: function back() {
      this.$router.back();
    }
  }
};

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

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

exports.default = {
	data: function data() {
		return {
			/*src: [
   	{href: '/index', value: 'index', title: '首 页',img: '../static/images/xs_pic_sy.png',},
   	{href: '/sc', value: 'sc', title: '书 库',img: '../static/images/pic_sc01.png',},
   	{href: 'lt', value: 'lt', title: '阅读圈'},
   	{href: '/main', value: 'main', title: '我 的',img: '../static/images/pic_w01.png',}
   ],
   flag: 0*/
		};
	},

	computed: {
		bg: function bg() {
			if (this.show == 0) {
				return '/index';
			}
			if (this.show == 1) {
				return '/sc';
			}
			if (this.show == 2) {
				return '/lt';
			}
			if (this.show == 2) {
				return '/main';
			}
		}
	},
	props: ['show']
};

/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _footer = __webpack_require__(0);

var _footer2 = _interopRequireDefault(_footer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
	components: {
		'foot': _footer2.default
	},
	data: function data() {
		return {
			imageList: [{ src: '../static/images/pic_01.png' }, { src: '../static/images/W020130321296490618267.jpg' }, { src: '../static/images/158_150401141239_1.jpeg' }],
			// 班级列表
			s_list: [{ src: '../static/images/head_04.png', name: '姜熙建', id: '../static/images/pic_hg.png' }, { src: '../static/images/head_03.png', name: '宋智孝', id: '' }, { src: '../static/images/head_05.png', name: '夏洛', id: '' }, { src: '../static/images/head_02.png', name: '小白', id: '' }],
			rw_list: [{ href: 'kstfb_mr', title: '每日一题', time: '03-27 23:59', new: true },
			/*{title: '每周一测', time: '2017-03-31 23:59',new: true},*/
			{ href: 'ydrw_jh', title: '阅读任务', time: '05-30 23:59', new: true, name: '张老师' }],

			// 功能模块列表
			/*p_list: [
   	{href: 'ydrw', title: '阅读任务', src: '../static/images/pic_ydrw.png', flag: true},
   	{href: 'kstfb',title: '考试提分宝', src: '../static/images/ico_kstfb.png', flag: false},
   	{href: 'ydbg',title: '学习报告', src: '../static/images/pic_ydbg.png', flag: false},
   	{href: 'ydzx',title: '阅读资讯', src: '../static/images/ico_ydzx.png', flag: false},
   	{href: 'sxb',title: '书香榜', src: '../static/images/ico_phb.png', flag: false},
   	/*{href: 'tsg',title: '听书馆', src: '../../static/images/ico_dsg.png', flag: false},
   	{href: 'hdzq',title: '活动专区', src: '../../static/images/ico_hdzq.png', flag: false}*/
			/*],*/
			jyzx_list: [{ title: '全民阅读不是一句口号', img: '../static/images/158_150401141239_1.jpeg', href: '/ydzx/xq', see: '3542', name: '一起阅读' }, { title: '2017年越秀读书月正式启动 行走+阅读感受广府文化', img: '../static/images/file_1392891646.jpg', href: '/ydzx/xq', see: '324', name: '阅读政策' }, { title: '告诉你幼儿绘本阅读的那些事儿！', img: '../static/images/21091W492-0.jpg', href: '/ydzx/xq', see: '4万', name: '阅读推荐' }, { title: ' 阅读+对话丨 陈晖：以文学阅读促进儿童全面发展', img: '../static/images/W020130321296490618267.jpg', href: '/ydzx/xq', see: '5962', name: '一起阅读' }]
		};
	},

	methods: {
		onchange: function onchange(event) {
			console.log('changed:', event.index);
		},
		jump: function jump() {
			this.$router.push('/ydzx/xq');
		}
	}
}; //
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

/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _footer = __webpack_require__(0);

var _footer2 = _interopRequireDefault(_footer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
	data: function data() {
		return {
			// 阅读教研 列表
			sc_list: [{ title: '飞来的伤心梅', img: '../static/images/book01.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book02.png', tabImg: '../static/images/pic_play.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }, { title: '飞来的伤心梅', img: '../static/images/book04.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book03.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '100', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦狼王梦狼王梦狼王梦狼王梦', img: '../static/images/book02.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }],
			data: [{ name: '书城' }, { name: '老师推荐' }, { name: '专家推荐' }],
			flag: 0,
			r_list: [{ title: '飞来的伤心梅', img: '../static/images/book01.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book02.png', tabImg: '../static/images/pic_play.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }, { title: '飞来的伤心梅', img: '../static/images/book04.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book03.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '100', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦狼王梦狼王梦狼王梦狼王梦', img: '../static/images/book02.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }],
			h_list: [{ title: '飞来的伤心梅', img: '../static/images/book04.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book03.png', href: 'sk_xq', see: '100', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦狼王梦狼王梦狼王梦狼王梦', img: '../static/images/book02.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }],
			c_list: [{ title: '飞来的伤心梅', img: '../static/images/book01.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '4万', praise: '200', state: '完成阅读', isbn: '9787547705063' }, { title: '狼王梦', img: '../static/images/book02.png', tabImg: '../static/images/pic_pause.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }, { title: '狼王梦狼王梦狼王梦狼王梦狼王梦', img: '../static/images/book02.png', href: 'sk_xq', see: '100', state: '', isbn: '9787547705063' }]
		};
	},

	name: 'sc',
	methods: {
		light: function light(i) {
			this.flag = i;
			if (i == 0) {
				this.sc_list = this.r_list;
			}
			if (i == 1) {
				this.sc_list = this.h_list;
			}
			if (i == 2) {
				this.sc_list = this.c_list;
			}
		},
		goback: function goback() {
			this.$router.goBack();
		}
	},
	components: {
		'foot': _footer2.default // 添加底部导航组件
	}
}; //
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

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

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

exports.default = {
	data: function data() {
		return {
			// 活动列表
			ydzx_list: [{ name: '全民阅读不是一句口号', time: '2017-02-04', img: '../static/images/158_150401141239_1.jpeg', text: '      ' }]
		};
	},

	name: 'ydzx_xq',
	methods: {
		goback: function goback() {
			this.$router.goBack();
		}
	}
};

/***/ }),
/* 22 */
/***/ (function(module, exports) {

module.exports = {
  "footer": {
    "width": 750,
    "height": 110,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderTopWidth": 1,
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "zIndex": 999,
    "flexDirection": "row",
    "backgroundColor": "#ffffff"
  },
  "fooot_list": {
    "flex": 1,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "f_list": {
    "flexDirection": "column",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "footer_img": {
    "marginTop": 12,
    "width": 44,
    "height": 44
  }
}

/***/ }),
/* 23 */
/***/ (function(module, exports) {

module.exports = {
  "send": {
    "position": "absolute",
    "display": "block",
    "width": 42,
    "height": 42,
    "top": 22,
    "right": 27,
    "backgroundSize": "100% 100%"
  },
  "ydzx_main": {
    "width": 750,
    "position": "relative",
    "top": 88
  },
  "ydzx_l": {
    "width": 750
  },
  "ydzx_img": {
    "display": "inline-block",
    "width": 690,
    "height": 360,
    "backgroundSize": "100% 100%"
  },
  "ydzx_text": {
    "fontSize": 32,
    "color": "#666666",
    "padding": 30,
    "lineHeight": 45,
    "marginBottom": 120,
    "backgroundColor": "#ffffff"
  },
  "ydzx_titil": {
    "color": "#333333",
    "fontSize": 40,
    "lineHeight": 80,
    "overflow": "hidden",
    "textOverflow": "ellipsis",
    "whiteSpace": "nowrap"
  },
  "ydzx_time": {
    "color": "#b7b7b7",
    "fontSize": 32,
    "lineHeight": 60,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  }
}

/***/ }),
/* 24 */
/***/ (function(module, exports) {

module.exports = {
  "top": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#6fa1e8",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "top_name": {
    "fontSize": 36,
    "color": "#ffffff"
  },
  "goback": {
    "position": "absolute",
    "left": 25,
    "width": 37,
    "height": 37
  },
  "goback_r": {
    "position": "absolute",
    "right": 25,
    "width": 37,
    "height": 37
  },
  "nav": {
    "width": 750,
    "height": 80,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "borderStyle": "solid",
    "backgroundColor": "#ffffff"
  },
  "navYes": {
    "backgroundColor": "#5f94df",
    "color": "#ffffff",
    "borderRadius": 8
  },
  "nav_li": {
    "flex": 1,
    "alignItems": "center",
    "justifyContent": "center",
    "fontSize": 32,
    "color": "#666666"
  },
  "nav1": {
    "width": 750,
    "height": 88,
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "borderStyle": "solid",
    "flexDirection": "row",
    "alignItems": "center"
  },
  "s_class": {
    "fontSize": 0.32,
    "color": "#808080",
    "width": 92,
    "height": 0.8,
    "lineHeight": 0.8,
    "border": 0,
    "backgroundColor": "rgba(255,255,255,0)",
    "backgroundPosition": "center right",
    "backgroundRepeat": "no-repeat",
    "backgroundSize": "0.35rem 0.18px"
  },
  "ydjy_list": {
    "width": 750,
    "height": 290,
    "flexDirection": "row",
    "alignItems": "center",
    "backgroundColor": "#ffffff",
    "borderBottom": "#ececec 1px solid",
    "marginBottom:last-child": 1.9
  },
  "ydjy_l": {
    "width": 190,
    "height": 240,
    "backgroundColor": "#ff6600"
  },
  "ydjy_lPic": {
    "width": 190,
    "height": 240
  },
  "ydjy_r": {
    "height": 240,
    "width": 455
  },
  "play": {
    "position": "absolute",
    "backgroundColor": "#000000",
    "width": 191,
    "height": 240,
    "top": 0,
    "left": 0,
    "zIndex": 88,
    "display": "flex",
    "alignItems": "center",
    "justifyContent": "center",
    "filter": "alpha(opacity=30)",
    "MozOpacity": 0.3,
    "opacity": 0.3
  },
  "record": {
    "width": 455,
    "height": 50,
    "fontSize": 30,
    "flexDirection": "row",
    "color": "#999999"
  },
  "ico_22": {
    "width": 60,
    "height": 60,
    "position": "absolute",
    "top": 140,
    "left": 70,
    "zIndex": 99
  },
  "b_list": {
    "flexDirection": "row",
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 55
  },
  "pic_see": {
    "width": 33,
    "height": 22
  },
  "pic_praise": {
    "width": 28,
    "height": 23
  },
  "hdzq_titil": {
    "flexDirection": "row",
    "color": "#333333",
    "fontSize": 36,
    "height": 60,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "ts_label": {
    "fontSize": 26,
    "backgroundColor": "#d9e8fd",
    "color": "#666666",
    "margin": 10,
    "marginLeft": 0,
    "borderRadius": 5
  },
  "ydjy_state": {
    "backgroundColor": "#ff6600",
    "color": "#ffffff",
    "fontSize": 22,
    "position": "absolute",
    "top": 10,
    "zIndex": 89
  },
  "author": {
    "flexDirection": "row",
    "fontSize": 30,
    "color": "#666666",
    "lineHeight": 40
  }
}

/***/ }),
/* 25 */
/***/ (function(module, exports) {

module.exports = {
  "image": {
    "width": 750,
    "height": 320
  },
  "slider": {
    "width": 750,
    "height": 320
  },
  "frame": {
    "width": 750,
    "height": 320,
    "position": "relative"
  },
  "indicator": {
    "width": 700,
    "height": 20,
    "itemColor": "#ccc",
    "itemSelectedColor": "#f60",
    "itemSize": 12,
    "position": "absolute",
    "bottom": 20,
    "left": 20
  },
  "content": {
    "marginBottom": 20,
    "height": 200,
    "backgroundColor": "#ffffff",
    "flexDirection": "row"
  },
  "cont_fx": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 200,
    "zIndex": 99
  },
  "fx_img": {
    "width": 20,
    "height": 36
  },
  "panel": {
    "flexDirection": "row",
    "alignItems": "center",
    "width": 650,
    "height": 200
  },
  "panel_li": {
    "alignItems": "center",
    "justifyContent": "center",
    "width": 170
  },
  "icon_1": {
    "position": "absolute",
    "top": 0,
    "right": 40,
    "display": "inline-block",
    "width": 17,
    "height": 17,
    "backgroundColor": "#ff6666",
    "borderRadius": 100
  },
  "icon_0": {
    "width": 88,
    "height": 88
  },
  "type_title": {
    "marginTop": 16,
    "fontSize": 30,
    "color": "#838383"
  },
  "jrrw": {
    "width": 750,
    "backgroundColor": "#ffffff",
    "position": "relative"
  },
  "jrrw_title": {
    "flexDirection": "row",
    "height": 35,
    "marginTop": 25,
    "marginBottom": 25,
    "paddingLeft": 20
  },
  "title_img": {
    "backgroundColor": "#70a1e8",
    "marginRight": 1,
    "width": 5
  },
  "jrrw_list": {
    "width": 700,
    "height": 130,
    "marginLeft": 24,
    "backgroundColor": "#f1f6fd",
    "borderStyle": "solid",
    "borderWidth": 1,
    "borderColor": "#d9e8fd",
    "borderRadius": 12,
    "marginBottom": 20,
    "padding": 30,
    "paddingTop": 10,
    "paddingBottom": 10
  },
  "newR": {
    "position": "absolute",
    "right": 0,
    "top": 0,
    "width": 75,
    "height": 75
  },
  "jrrw_info": {
    "width": 540
  },
  "rw_title": {
    "flexDirection": "row",
    "height": 55,
    "alignItems": "center"
  },
  "rw_time": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "width": 450,
    "height": 60
  },
  "c_bg": {
    "position": "absolute",
    "right": 40,
    "width": 100,
    "textAlign": "center",
    "top": 50,
    "height": 45,
    "lineHeight": 45,
    "fontSize": 28,
    "borderRadius": 8,
    "backgroundColor": "#70a1e8",
    "color": "#ffffff"
  },
  "mrzx": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center",
    "marginTop": 20,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "backgroundColor": "#ffffff"
  },
  "mrzx_l": {
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88,
    "padding": 30
  },
  "mrzx_title": {
    "fontSize": 36,
    "lineHeight": 45,
    "color": "#70a1e8"
  },
  "mrzx_r": {
    "height": 180,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "mrzx_list": {
    "width": 140,
    "height": 180,
    "alignItems": "center",
    "justifyContent": "center"
  },
  "mrzx_list_tx": {
    "width": 101,
    "height": 101,
    "borderRadius": 55,
    "position": "relative",
    "zIndex": 8
  },
  "name": {
    "fontSize": 28,
    "color": "#666666",
    "marginTop": 15
  },
  "pic_hg": {
    "width": 45,
    "height": 45,
    "position": "absolute",
    "left": 45,
    "top": -10,
    "zIndex": 99
  },
  "jyzx": {
    "width": 750,
    "height": 88,
    "backgroundColor": "#ffffff",
    "marginTop": 20,
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "fontSize": 36,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "yjzc_more": {
    "fontSize": 28,
    "color": "#838383"
  },
  "yjzc_more_img": {
    "width": 16,
    "height": 26,
    "marginLeft": 10
  },
  "jyzx_list": {
    "backgroundColor": "#ffffff",
    "borderStyle": "solid",
    "borderColor": "#e7e7e7",
    "borderBottomWidth": 1,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between"
  },
  "jyzx_l": {
    "width": 180,
    "height": 118,
    "borderRadius": 3,
    "margin": 20
  },
  "jyzx_r": {
    "flex": 3,
    "marginLeft": 20,
    "marginRight": 10
  },
  "jyzx_titil": {
    "color": "#333333",
    "fontSize": 34,
    "lineHeight": 60,
    "textOverflow": "ellipsis",
    "lines": 1
  },
  "record": {
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "space-between",
    "height": 60
  }
}

/***/ }),
/* 26 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('div', {
    staticClass: ["footer"]
  }, [_c('div', {
    staticClass: ["fooot_list"]
  }, [_c('router-link', {
    staticClass: ["f_list"],
    attrs: {
      "to": "/index"
    }
  }, [_c('image', {
    staticClass: ["footer_img"],
    attrs: {
      "src": "../static/images/xs_pic_sy.png"
    }
  }), _c('text', {
    staticStyle: {
      marginTop: "9px",
      color: "#70a1e8"
    }
  }, [_vm._v("首 页")])])], 1), _c('div', {
    staticClass: ["fooot_list"]
  }, [_c('router-link', {
    staticClass: ["f_list"],
    attrs: {
      "to": "/sc"
    }
  }, [_c('image', {
    staticClass: ["footer_img"],
    attrs: {
      "src": "../static/images/pic_sc01.png"
    }
  }), _c('text', {
    staticStyle: {
      marginTop: "9px",
      color: "#666"
    }
  }, [_vm._v("书 库")])])], 1), _c('div', {
    staticClass: ["fooot_list"]
  }, [_c('router-link', {
    staticClass: ["f_list"],
    attrs: {
      "to": "/main"
    }
  }, [_c('image', {
    staticClass: ["footer_img"],
    attrs: {
      "src": "../static/images/pic_w01.png"
    }
  }), _c('text', {
    staticStyle: {
      marginTop: "9px",
      color: "#666"
    }
  }, [_vm._v("我 的")])])], 1)])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 27 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      backgroundColor: "#fff",
      fontFamily: "黑体"
    },
    attrs: {
      "id": "ydzx"
    }
  }, [_c('header', [_c('div', {
    on: {
      "click": _vm.goback
    }
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../../static/images/xs_pic_fh.png"
    }
  })]), _c('p', [_vm._v("全民阅读不是一句口号")]), _c('a', {
    staticClass: ["send"],
    attrs: {
      "href": "javascript:void(0)"
    }
  })], 1), _vm._l((_vm.ydzx_list), function(ydzxXq) {
    return _c('div', {
      staticClass: ["ydzx_main"]
    }, [_c('div', {
      staticClass: ["ydzx_l"]
    }, [_c('div', {
      staticClass: ["ydzx_titil"]
    }, [_c('text', [_vm._v(_vm._s(ydzxXq.name))])]), _c('div', {
      staticClass: ["ydzx_time"]
    }, [_c('text', [_vm._v(_vm._s(ydzxXq.time))]), _c('text', {
      staticStyle: {
        color: "#6fa1e8"
      }
    }, [_vm._v("一起阅读平台")])])]), (ydzxXq.img) ? _c('image', {
      staticClass: ["ydzx_img"],
      attrs: {
        "src": ydzxXq.img
      }
    }) : _vm._e(), _c('div', {
      staticClass: ["ydzx_text"]
    }, [_vm._v("    有人说，腹有诗书气自华，也有人说，读书改变命运。阅读对人成长的影响是巨大的，一本好书往往能改变人的一生。而一个民族的精神境界，在很大程度上取决于全民族的阅读水平。\n\n\t\t\t    按理说，读书是一件私人化的事情，可是，从《全民阅读“十三五”时期发展规划》到《政府工作报告》中提出要大力推动全民阅读，种种政策信号都在从国家战略高度去强调阅读的重要性，更有专家学者向媒体呼吁，全民阅读应该进入立法时间。\n\n\t\t\t    有人会不解，读一本书还要立法，为什么？实际上，推动全民阅读不只是一句简单的口号，改善国民阅读状况，政府应该起到主导作用，要为民众营造一个良好的读书环境。\n\n\t\t\t "), _c('b', [_vm._v("有书读")]), _vm._v("\n\n\t\t\t    最近，《中国诗词大会》和《朗读者》这类文化节目特别火。看着银幕上的人出口成章、妙语连珠，很多人在羡慕之余不禁抱憾书到用时方恨少。据官方调查数据显示，当前我国国民人均年阅读量为4.58本，也就是说，人们每三个月才会去看一本书，这个指标远远低于许多发达国家甚至是一些发展中国家的水平。\n\n\t\t\t    中央电视台主持人白岩松多次在公开场合呼吁全民阅读。他认为，推动全民阅读政府应该起主导作用。要把全民分成不同的阅读阶层，用立法去保障，根据不同层次需求提供推动阅读的不同方式。\n\n\t\t\t    白岩松所说的立法其实并不是用法律手段强迫人们读书，而是希望政府能够把公民阅读当成一种硬需求，尤其是保障那些想读书却又没有条件读书的人群。他举例说：“巴西的监狱里有一个明确的规定，犯人在规定的时间每读完4本书，写了读后感并且被认可了，可以减刑15天。”白岩松认为，这个事例充分体现了巴西对于阅读的尊重，也说明全民阅读的重点在于“全民”二字，让每个人都有书读是政府部门工作的着力点。                                                                                                            前不久，“书香中国万里行”活动走进河南宝丰，中国出版集团公司前总裁聂震宁作为嘉宾也来到了宝丰县实地调研。让他惊喜的是，一个小小的县城竟然建有24小时公益图书馆，就连一个普通的农村超市都有图书角。他颇为感慨地说：“宝丰县的农民是幸福的，他们有一个很好的阅读环境。也许就在超市一角的图书架里，他们学到了有益的知识，获得了致富灵感。”  \n\n\t\t\t    在我国，全民阅读至少面临双重困境：一是贫穷地区缺乏读书的场所，二是繁华地段反而看不到图书馆和实体书店。聂震宁认为：“我国目前平均45.9万人才拥有一所公共图书馆，这与每1.5公里半径内设置一所公共图书馆、平均2万人左右拥有一所公共图书馆的国际标准相去甚远。除图书馆外，实体书店也是最方便国民阅读和购书的场所。而今，许多实体书店越来越难以为继，政府有必要研究一些对策，譬如对实体书店怎么支持？对图书馆怎么支持？”\n\n\t\t\t    文化部部长雒树刚曾在公开场合提出，我国很多偏远落后地区的图书室还存在内容分配不合理、图书更新慢等问题，让每个公民都有书读，不是简单地建几个图书馆那么简单。政府要运用智慧，花心思为民众营造一个良好的读书环境。\n\n\t\t\t    可喜的是，相关部门已经开始行动。今年初，国家新闻出版广电总局下发通知，明确了2017年全民阅读工作的着力点。其中，着力办好主题阅读活动、着力完善基础阅读设施、着力推动基层群众阅读等内容都体现了政府部门对推动全民阅读的责任担当。\n\n\t\t\t"), _c('B', [_vm._v("读好书")]), _vm._v(" \n\n\t\t\t    北京卫视一档节目《书香北京》曾在微博上推出过“品书香，晒书房”活动，参与者上万人，很多年轻人纷纷秀出书房照片。一位在北京打工的年轻人秀出的照片中，一个狭小的屋内，他和室友睡上下铺，在这样的环境中，却摆了一个简易书架，整齐地排列着翻旧了的书。这位网友说：“我的书房就是这个小书架，这些是我最珍爱的书，也是最苦最累时的精神食粮。因为在读书的时候，我可以看到未来。”  \n\n\t\t\t    很多人并不是没有读书的欲望，他们的问题在于选择太多或者诱惑太多。在这个知识信息大爆炸的时代，从广义上讲，我们每天都在接受着汹涌而来的各类信息，而从狭义上讲，我们的阅读量则变得越来越少了.\n\t\t\t    现在最要紧的是解决大众读什么、怎么读的问题。”作家曹文轩认为，读者是可以培养的。作家不能本能地随从大众阅读的潜意识，而应该对之加以培养和引导。\n\n\t\t\t    从出版方的角度讲，聂震宁更注重书刊的品质。“我们的年出书品种已经够多了，年库存码洋也已经很大，当下要考虑的是如何把书刊做得更好一些。当全民阅读蔚然成风的时候，也就是出版业再次走向辉煌的时刻，书刊的品质是否能优质高效地为全民阅读服务，这是每一个出版人要考虑的问题。”\n\n\t\t\t    国家新闻出版广电总局原副局长邬书林认为， 推动全民阅读就是要让散落在世界各个角落的人群都能享受到阅读的乐趣。“全民阅读已经进入细化分层的阶段，政府部门需要科学研究、分类实施。” \n\n\t\t\t    在这方面，北京可以算是先行者。北京阅读季自开展以来，有针对性地引导不同年龄阶段的人群阅读，提升了阅读活动的实效性。他们在北京市属机关内陆续开展领导干部系列文化讲座、青年公务员读书大讲堂及公务员读书状况调研；针对女性群体的阅读需求，围绕“阅读与城市女性”“家庭阅读”“职业女性阅读”“中外阅读交流”等主题举办了多场读书活动；还推出过亲子阅读“接力跑”等品牌，将一些先进的阅读理念传递到城市的各个角落。\n\n\t\t\t"), _c('B', [_vm._v("善读书")]), _vm._v("   \n\n\t\t\t    良好的阅读习惯要在童年时期养成。邬书林认为，全民阅读要与教育打通，要把阅读方法、阅读习惯嵌入到国民教育中，变成国民教育的基础工作。 \n\t\t\t\n\n\t\t\t    福建省读书援助协会工作人员在调研中发现，在贫困偏远地区，很多学校存在办学软件缺失，尤其是课外阅读图书缺乏的困扰。城市里的孩子也存在阅读缺失的问题。他们虽不缺图书，但是身边充斥着大量电脑游戏和视频动画，影响了他们的阅读时间和阅读品质。\n\n\t\t\t    “政府在推动全民阅读这项工作时最应该规划和保障的是中小学生的阅读。”福建省读书援助协会秘书长洪燕青告诉记者：“中小学阅读应自主阅读还是强制阅读一直存在争议。其实强制读书未必有好效果，关键是要引导孩子更快更好地理解和吸收书本中的知识，用于指导生活实践。” \n\n\t\t\t    在聂震宁看来，读书是要讲究方法的。应妥善处理数字化阅读和传统阅读的关系，科学规划时间，实现取长补短。近年来，有声书渐渐流行，亚马逊语音交互式蓝牙音箱回声在节假日期间大卖、谷歌智能家居设备谷歌家居发展势头也很强劲。“人们可以通过更多的设备收听有声读物，有声书会越来越受欢迎，这是好方向，也是全民阅读下一步的着力点。此类出版内容还有很大拓展空间，出版方应该抓住这个机会。”聂震宁说。\n\n\t\t\t    只要你想读书，总会找到合适的时间和方法。而在浩如烟海的书籍中如何取舍和选择也是大有学问的。作家王立群认为，有几种图书是大众都非常喜欢的：第一是很需要的书，人在生活中遇到的各种各样的问题可以通过读书得到答案，这种书大部分是科学类、知识性的；还有一种是能够提高生活质量、满足审美趣味的书，这样的书大部分是文学艺术方面的书。他认为后者是全民的，对于一个人来说好像空气、阳光、水一样不能缺失。\n\n\t\t\t     “现在很多人读书不求甚解，所以评书、品书引导工作很重要。”王立群认为，应该多举办一些读书讲座、通过媒体的力量多向社会推荐好书。作为读者，也要学会借鉴他人的经验，尤其是一些文化名人、知名学者，从他们的经验之中提升自己的阅读品位。（原文来源：经济日报 作者：姜天骄）\n\n\t\t")], 1)])
  })], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 28 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('scroller', {
    staticStyle: {
      backgroundColor: "#fff",
      paddingBottom: "120px"
    },
    attrs: {
      "id": "sc"
    }
  }, [_c('div', {
    staticClass: ["top"]
  }, [_c('image', {
    staticClass: ["goback"],
    attrs: {
      "src": "../static/images/xs_pic_fh.png"
    },
    on: {
      "click": _vm.goback
    }
  }), _c('text', {
    staticClass: ["top_name"]
  }, [_vm._v("书库")]), _c('image', {
    staticClass: ["goback_r"],
    attrs: {
      "src": "../static/images/ico_18.png"
    }
  })]), _c('div', {
    staticClass: ["nav"]
  }, _vm._l((_vm.data), function(item, index) {
    return _c('div', {
      staticClass: ["nav_li"]
    }, [_c('text', {
      class: {
        'navYes': _vm.flag == index
      },
      on: {
        "click": function($event) {
          _vm.light(index)
        }
      }
    }, [_vm._v(_vm._s(item.name))])])
  })), _vm._m(0), _c('foot'), _vm._l((_vm.sc_list), function(sc) {
    return _c('div', {
      staticClass: ["ydjy_list"]
    }, [_c('div', {
      staticClass: ["ydjy_l"]
    }, [_c('router-link', {
      attrs: {
        "to": {
          path: sc.href
        }
      }
    }, [_c('image', {
      staticClass: ["ydjy_lPic"],
      staticStyle: {
        position: "relative"
      },
      attrs: {
        "src": sc.img
      }
    }), (sc.state) ? _c('div', {
      staticClass: ["ydjy_state"]
    }, [_c('text', {
      staticStyle: {
        color: "#fff"
      }
    }, [_vm._v(_vm._s(sc.state))])]) : _vm._e()]), _c('div', {
      staticClass: ["play"]
    }), _c('router-link', {
      attrs: {
        "to": {
          path: sc.href
        }
      }
    }, [_c('image', {
      staticClass: ["ico_22"],
      attrs: {
        "src": sc.tabImg
      }
    })])], 1), _c('div', {
      staticClass: ["ydjy_r"]
    }, [_c('router-link', {
      attrs: {
        "to": {
          path: sc.href
        }
      }
    }, [_c('div', {
      staticClass: ["hdzq_titil"]
    }, [_c('text', [_vm._v(_vm._s(sc.title))])])]), _vm._m(1, true), _c('div', {
      staticClass: ["b_list"]
    }, [_c('text', [_vm._v("ISBN:")]), _c('text', [_vm._v(_vm._s(sc.isbn))])]), _vm._m(2, true), _c('div', {
      staticClass: ["record"]
    }, [_c('div', {
      staticStyle: {
        flex: "1",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "left"
      }
    }, [_c('image', {
      staticClass: ["pic_see"],
      attrs: {
        "src": "../static/images/ico_see.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(sc.see))])]), _c('div', {
      staticStyle: {
        flex: "1",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "left"
      }
    }, [_c('image', {
      staticClass: ["pic_praise"],
      attrs: {
        "src": "../static/images/ico_heart.png"
      }
    }), _vm._v(" "), _c('text', [_vm._v(_vm._s(sc.praise))])])])], 1)])
  })], 2)
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["nav1"]
  }, [_c('div', {
    staticStyle: {
      flex: "1",
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "space-between",
      borderRightColor: "#e7e7e7",
      borderRightWidth: "1px",
      borderRightStyle: "solid",
      paddingLeft: "20px",
      paddingRight: "20px"
    }
  }, [_c('text', [_vm._v("一年级")]), _c('image', {
    staticStyle: {
      width: "35px",
      height: "18px"
    },
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })]), _c('div', {
    staticStyle: {
      flex: "1",
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "space-between",
      paddingLeft: "20px",
      paddingRight: "20px"
    }
  }, [_c('text', [_vm._v("全部书目")]), _c('image', {
    staticStyle: {
      width: "35px",
      height: "18px"
    },
    attrs: {
      "src": "../static/images/ico_17.png"
    }
  })])])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["author"]
  }, [_c('text', [_vm._v("王蕾")]), _c('text', [_vm._v("著")])])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      flexDirection: "row",
      justifyContent: "left",
      alignItems: "center"
    }
  }, [_c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v("故事")]), _c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v("科普")]), _c('text', {
    staticClass: ["ts_label"]
  }, [_vm._v("美国")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 29 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('scroller', {
    staticStyle: {
      backgroundColor: "#f0f0f0"
    }
  }, [_c('slider', {
    staticClass: ["slider"],
    attrs: {
      "interval": "3000",
      "autoPlay": "true"
    },
    on: {
      "change": _vm.onchange
    }
  }, [_vm._l((_vm.imageList), function(img) {
    return _c('div', {
      staticClass: ["frame"]
    }, [_c('image', {
      staticClass: ["image"],
      attrs: {
        "src": img.src
      }
    })])
  }), _c('indicator', {
    staticClass: ["indicator"]
  })], 2), _c('div', {
    staticClass: ["content"]
  }, [_vm._m(0), _c('scroller', {
    staticClass: ["panel"],
    attrs: {
      "scrollDirection": "horizontal",
      "showScrollbar": "false"
    }
  }, [_c('router-link', {
    attrs: {
      "to": "/ydrw"
    }
  }, [_c('div', {
    staticClass: ["panel_li"]
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/pic_ydrw.png"
    }
  }), _c('div', {
    staticClass: ["icon_1"]
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("阅读任务")])])]), _c('router-link', {
    attrs: {
      "to": "/kstfb"
    }
  }, [_c('div', {
    staticClass: ["panel_li"]
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_kstfb.png"
    }
  }), _c('div', {
    staticClass: ["icon_1"]
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("考试提分宝")])])]), _c('router-link', {
    attrs: {
      "to": "/ydbg"
    }
  }, [_c('div', {
    staticClass: ["panel_li"]
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/pic_ydbg.png"
    }
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("学习报告")])])]), _c('router-link', {
    attrs: {
      "to": "/ydzx"
    }
  }, [_c('div', {
    staticClass: ["panel_li"]
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_ydzx.png"
    }
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("阅读资讯")])])]), _c('router-link', {
    attrs: {
      "to": "/sxb"
    }
  }, [_c('div', {
    staticClass: ["panel_li"]
  }, [_c('image', {
    staticClass: ["icon_0"],
    attrs: {
      "src": "../static/images/ico_phb.png"
    }
  }), _c('text', {
    staticClass: ["type_title"]
  }, [_vm._v("书香榜")])])])], 1), _vm._m(1)]), _c('div', {
    staticClass: ["jrrw"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#70a1e8"
    }
  }, [_vm._v("今日任务")])], 1), _vm._l((_vm.rw_list), function(rw) {
    return _c('div', [_c('router-link', {
      staticClass: ["jrrw_list"],
      attrs: {
        "to": {
          path: rw.href
        }
      }
    }, [_c('div', {
      staticClass: ["jrrw_info"]
    }, [_c('div', {
      staticClass: ["rw_title"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "36px",
        color: "#666"
      }
    }, [_vm._v(_vm._s(rw.title))])]), _c('div', {
      staticClass: ["rw_time"]
    }, [_c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v("截止：" + _vm._s(rw.time))]), _c('text', {
      staticStyle: {
        fontSize: "26px",
        color: "#808080"
      }
    }, [_vm._v(_vm._s(rw.name))])])]), _c('text', {
      staticClass: ["c_bg"]
    }, [_vm._v("前 往")]), _c('image', {
      directives: [{
        name: "show",
        rawName: "v-show",
        value: (rw.new),
        expression: "rw.new"
      }],
      staticClass: ["newR"],
      attrs: {
        "src": "../static/images/xs_pic_newR.png"
      }
    })])], 1)
  })], 2), _c('div', {
    staticClass: ["mrzx"]
  }, [_vm._m(2), _c('div', {
    staticClass: ["mrzx_r"]
  }, _vm._l((_vm.s_list), function(s) {
    return _c('div', {
      staticClass: ["mrzx_list"]
    }, [_c('image', {
      staticClass: ["mrzx_list_tx"],
      attrs: {
        "src": s.src
      }
    }), _c('text', {
      staticClass: ["name"]
    }, [_vm._v(_vm._s(s.name))]), (s.id) ? _c('image', {
      staticClass: ["pic_hg"],
      attrs: {
        "src": s.id
      }
    }) : _vm._e()])
  }))]), _c('div', [_c('div', {
    staticClass: ["jyzx"]
  }, [_c('div', {
    staticClass: ["jrrw_title"]
  }, [_c('span', {
    staticClass: ["title_img"]
  }, [_vm._v(" ")]), _vm._v("  "), _c('text', {
    staticStyle: {
      fontSize: "36px",
      color: "#70a1e8"
    }
  }, [_vm._v("教育资讯")])], 1), _c('router-link', {
    attrs: {
      "to": "/ydzx"
    }
  }, [_c('div', {
    staticStyle: {
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "center",
      paddingRight: "20px"
    }
  }, [_c('text', {
    staticClass: ["yjzc_more"]
  }, [_vm._v("更多")]), _c('image', {
    staticClass: ["yjzc_more_img"],
    attrs: {
      "src": "../static/images/wd_09.png"
    }
  })])])], 1), _vm._l((_vm.jyzx_list), function(jyzx) {
    return _c('div', [_c('div', {
      staticClass: ["jyzx_list"],
      on: {
        "click": _vm.jump
      }
    }, [_c('div', {
      staticClass: ["jyzx_r"]
    }, [_c('text', {
      staticClass: ["jyzx_titil"]
    }, [_vm._v(" " + _vm._s(jyzx.title))]), _c('div', {
      staticClass: ["record"]
    }, [_c('text', {
      staticStyle: {
        backgroundColor: "#70a1e8",
        color: "#fff",
        padding: "4px 8px",
        borderRadius: "5px"
      }
    }, [_vm._v(_vm._s(jyzx.name))]), _c('text', {
      staticStyle: {
        fontSize: "28px",
        color: "#828282"
      }
    }, [_vm._v(_vm._s(jyzx.see) + "阅读")])])]), _c('image', {
      staticClass: ["jyzx_l"],
      attrs: {
        "src": jyzx.img
      }
    })])])
  })], 2), _c('foot', {
    attrs: {
      "show": 0
    }
  })], 1)
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      width: "50px",
      height: "200px",
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "center"
    }
  }, [_c('image', {
    staticClass: ["fx_img"],
    attrs: {
      "src": "../static/images/xs_pic_more02.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      width: "50px",
      height: "200px",
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "center"
    }
  }, [_c('image', {
    staticClass: ["fx_img"],
    attrs: {
      "src": "../static/images/xs_pic_left.png"
    }
  })])
},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["mrzx_l"]
  }, [_c('text', {
    staticClass: ["mrzx_title"]
  }, [_vm._v("每日")]), _c('text', {
    staticClass: ["mrzx_title"]
  }, [_vm._v("之星")])])
}]}
module.exports.render._withStripped = true

/***/ }),
/* 30 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    on: {
      "androidback": _vm.back
    }
  }, [_c('router-view', {
    staticStyle: {
      flex: "1"
    }
  })], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })
/******/ ]);