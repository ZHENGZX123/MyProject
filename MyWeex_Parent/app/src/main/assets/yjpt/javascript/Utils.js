var Utils = {
    // dir : 'yjpt',
  	dir : 'yjpts',
    // ip : 'http://192.168.8.206:8180/',
     // ip : 'http://192.168.8.114:8888/',
    // ip : 'http://127.0.0.1:8888/',
    ip : 'http://www.yuertong.com/',   //本地不用

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
                          subObj[imgNameArr[j]] = Utils.ip +Utils.dir+ '/'+osubOjb;
                      }
                        
                    }
                  }
                  obj[imgNameArr[j]] = Utils.ip +Utils.dir+ '/'+ obj[imgNameArr[j]];
                  
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
                arr[imgNameArr[i]] = Utils.ip + Utils.dir +  '/'+arr[imgNameArr[i]] ;
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
                   // data : eval("(" + response.data + ")")  // 用于手机 端
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