webpackJsonp([25],{1067:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("x-header",{attrs:{"left-options":{backText:""}}},[e._v("《原创新课堂》9上数学北师（深圳专版）")]),e._v(" "),o("div",{staticClass:"content"},[o("ul",e._l(e.periods,function(t){return o("li",{on:{click:function(t){e.topic()}}},[o("div",{staticClass:"mg period"},[o("p",{staticClass:"period_1"},[e._v(e._s(t.name))]),e._v(" "),o("p",{staticClass:"period_2"},[e._v(e._s(e.book)+" "+e._s(t.chapter))])]),e._v(" "),o("div",{staticClass:"number"},[o("p",[e._v(e._s(t.num))])])])}))])],1)},staticRenderFns:[]}},730:function(e,t,o){o(998);var a=o(18)(o(914),o(1067),"data-v-366e3a50",null);e.exports=a.exports},914:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=o(283),i=o.n(a);t.default={components:{XHeader:i.a},data:function(){return{book:"原创北师深圳专版",periods:[{name:"第1课时菱形的性质",chapter:"第1章特殊的平行四边形",num:"No.040171053001"},{name:"第2课时菱形的判定",chapter:"第1章特殊的平行四边形",num:"No.040171053002"}],data:[]}},mounted:function(){var e=this,t=this;t.$teacher.getChapter(t,1).then(function(t){console.log(t.data),e.data=t.data})},methods:{topic:function(){this.$router.push("/topic")}}}},953:function(e,t,o){t=e.exports=o(705)(),t.push([e.i,"[data-v-366e3a50]{font-size:1.5rem}.mg[data-v-366e3a50]{width:100%;margin-left:1rem;margin-right:1rem}.content ul[data-v-366e3a50]{background-color:#fff}.content li[data-v-366e3a50]{display:-webkit-box;display:-webkit-flex;display:flex;padding:.5rem 0;border-bottom:1px solid #dcdcdc}.content li .period[data-v-366e3a50]{-webkit-box-flex:0.6;-webkit-flex:0.6;flex:0.6;overflow:hidden;line-height:2.5rem}.content li .period .period_1[data-v-366e3a50]{color:#666}.content li .period .period_2[data-v-366e3a50]{font-size:1rem;color:#bfbfc8;white-space:nowrap;text-overflow:ellipsis;overflow:hidden}.content li .number[data-v-366e3a50]{-webkit-box-flex:0.4;-webkit-flex:0.4;flex:0.4;color:#bfbfc8;position:relative}.content li .number p[data-v-366e3a50]{font-size:1rem;position:absolute;bottom:.5rem}",""])},998:function(e,t,o){var a=o(953);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);o(706)("3d42c36a",a,!0)}});