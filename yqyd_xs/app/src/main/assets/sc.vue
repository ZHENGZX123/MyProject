<template>
	<div id="sc" style=" background-color: #fff;">
		<div class="top">
			<image src="../static/images/pic_ss.png" class="goback" @click="goback" />
			<text class="top_name">书库</text>
			<image class="goback_r" src="../static/images/ico_18.png" @click="scan()" />
		</div>
		<div class="nav">
			<div class="nav_li" v-for='(item,index) in typeData' @click='light(index,item.id)' >
				<text :style="{ backgroundColor: item.bj, color: item.color}" class="nav_name" >{{item.name}}</text>
			</div>
		</div>
		<div class="nav1"  >
			<div class="nav1_li"  @click="gradese">
				<text class="fontSize">{{allGrade}}</text>
				<image src="../static/images/ico_17.png" style=" width:35px; height: 18px; " ></image>
			</div>
			<div  class="nav1_li"  @click="subj">
				<text class="fontSize">{{allSubject}}</text>
				<image src="../static/images/ico_17.png" style=" width:35px; height: 18px;  " ></image>
			</div>
		</div>
		<div class="s_class" v-if="willShow"  style=" left: 0px; ">
			<scroller >
				<div class="s_class_opt"  @click="optGrade('', $event)" >
					<text style=" font-size: 34px; " >全部年级</text>
				</div>
				<div class="s_class_opt" v-for='nj in grade'  @click="optGrade(nj.name,$event)" ><text style=" font-size: 34px; color: #666; " >{{nj.name}}</text></div>
			</scroller>
		</div>
		
		<div class="s_class"  v-if="willShow2" style=" left: 375px; ">
			<scroller>
				<div class="s_class_opt" @click="optSubject('', '全部书目', $event)" >
					<text style="font-size: 34px; ">全部书目</text>
				</div>
				<div class="s_class_opt" v-for='subj in subject'  @click="optSubject(subj.id,subj.name,$event)" >
					<text style="font-size: 34px; color: #666;">{{subj.name}}</text>
				</div>
			</scroller>
		</div>
		<!-- <foot></foot> -->
		<scroller>
			<div class="ydjy_list" v-for="sc in sc_list">
				<div class="ydjy_l">
					<img :src="sc.pic" class="ydjy_lPic" style=" position: relative; " ></img>
					<!--<text v-if="sc.state" class="ydjy_state"  >{{sc.state}}</text>-->
					<div class="play" @click="onpeScxq('/sk/xq?id='+sc.id+'&type='+tabsId)"></div>
					<image :src="sc.tabImg" class="ico_22" />
				</div>
				
				<div class="ydjy_r">
					<text class="hdzq_titil" @click="onpeScxq('/sk/xq?id='+sc.id+'&type='+tabsId)" >{{sc.name}}</text>
					<div class="author"><text class="fontSize">{{sc.author}}</text><text style="font-size: 30px; color: #666;">著</text></div>
					<div class="b_list"><text class="fontSize">ISBN:</text><text style="font-size: 32px; color: #666; ">{{sc.isbn}}</text></div>
					<div style="flex-direction: row; justify-content: left; align-items: center;">
						<text class="ts_label">{{sc.bookType}}</text>
					</div>	

					<div class="record" >
						<div class="record_li">
							<image src="../static/images/ico_see.png" class="pic_see" ></image>&nbsp;&nbsp;<text>{{sc.view}}</text>
						</div>
						<div class="record_li" @click="praise(sc,tabsId)">
							<image :src="checkYes" class="pic_praise" v-if="sc.isZan==1" ></image ><image :src="checkes" class="pic_praise" v-if="sc.isZan==0" ></image >&nbsp;&nbsp;<text>{{sc.toast}}</text>
						</div>
					</div>
				</div>
			</div>
			<loading class="loading" @loading="onloading" :display="showLoading">
			    <text class="indicator">加载更多 ...</text>
			</loading>
	    </scroller>
	</div>
</template>
<script>
	// import f from './footer.vue'
	const storage = weex.requireModule('storage');
    const modal = weex.requireModule('modal');
	import kwz from '../../static/js/Utils.js'
	const animation = weex.requireModule('animation');
	export default {
		data(){
			return {
				checkes:'../static/images/ico_heart.png',
				checkYes:'../static/images/ico_heart_yes.png',
				allSubject:'全部书目',
				allGrade:'全部年级',
				willShow:false,
				willShow2:false,
				statusId: '0', 
            	grade: [],
            	subject: [],
            	isShow:false,
            	flagSrc: '../static/images/Selected.png',
            	arrowSrc: '../static/images/down.png',
				// 阅读教研 列表
				sc_list: [
					/*{title: '飞来的伤心梅', img:'../static/images/book01.png', tabImg:'../static/images/pic_pause.png',href: '/sk/xq',see:'4万',praise:'200' ,state:'完成阅读', isbn: '9787547705063'}*/
				],
				typeData:[
					{name:'书城',bj:'#d9e8fd',color:'#6fa1e8',id:1},
					{name:'老师推荐',bj:'',color:'#666',id:2},
					{name:'专家推荐',bj:'',color:'#666',id:1}
				],
				flag:0,
				subjId:'',
				njName:'',
				tabsId:0,
				tabsIndex:0,
				page: 1,
				schoolId:'',
				flag: false,
				showLoading: 'hide',
				Account:''
			}
		},
		name: 'sc',
		methods: {
			gradese: function(id,name){
			    if(this.willShow==true){
			        this.willShow=false;
			    }else{
			        this.willShow=true;
			    }
			    var self = this
			    kwz.fetch({
			    	url : '/app/book/grade',
			    	method:'POST',
			    	type:'json',
			    	success : function(ret){
			    		var grades = ret.data.grade
			    		self.grade=eval(grades);	
			    	}
			    })
	       	},
	        subj:function(id,name){
			    if(this.willShow2==true){
			        this.willShow2=false;
			    }else{
			        this.willShow2=true;
			    }
			    var self = this
			     kwz.fetch({
			    	url : '/app/book/module',
			    	method:'POST',
			    	type:'json',
			    	success : function(ret){
			    		var subj = ret.data.modules
			    		self.subject=eval(subj);
			    	}
			    })
		    },
	        optGrade:function(name){
			    this.njName=name;
			    this.allGrade=name;
			    if(this.allGrade==""){
			    	this.allGrade="全部年级"
			    }
	        	this.willShow=false;
			    this.light(this.tabsIndex,this.tabsId);
	        },
	        optSubject:function(id,name){
	        	this.subjId=id;
	        	this.allSubject=name;
	        	this.willShow2=false;
			    this.light(this.tabsIndex,this.tabsId);
	        },
			light:function(index,id){
				// debugger
				this.tabsId=id;
				this.tabsIndex=index;
                
				if(!this.flag){
					this.sc_list=[];
		    		this.page=1;
				}
				var self = this;

				for(var i in self.typeData){
					self.typeData[i].bj = '';
                    self.typeData[i].color = '#666';
					if(i==index){
						self.typeData[index].bj = '#d9e8fd';
						self.typeData[index].color="#6fa1e8";
					}
				}
				if(this.tabsId==1){
					this.schoolId="";
					kwz.fetch({
					    	url : '/app/book/list?loginAccount='+self.Account+'&moduleId='+self.subjId+'&grade='+self.njName+'&type='+self.tabsId+'&page='+self.page+'&pageSize=8&schoolCode='+self.schoolId,
					    	method:'POST',
					    	type:'json',
					    	success : function(ret){
					    		var datas = ret.data.books||[];
									if(ret.data.statusCode==200 && datas.length!=0){
						    			for(let i=0;i<datas.length;i++){
							    			self.sc_list.push(datas[i]);
							    		}
						    		}else if(ret.data.statusCode==404 && datas.length == 0 ){
						    			 modal.toast({ message: '已到底部', duration: 1 })
						    		}
						    		self.flag=false;
						    		self.showLoading = 'hide';
					    	}

				    	})
				}else{
				    storage.getItem('schoolCode',function(e){  //从缓存中取userId
		            	self.schoolId = e.data;
		            	kwz.fetch({
					    	url : '/app/book/list?loginAccount='+self.Account+'&moduleId='+self.subjId+'&grade='+self.njName+'&type='+self.tabsId+'&page='+self.page+'&pageSize=8&schoolCode='+self.schoolId,
					    	method:'POST',
					    	type:'json',
					    	success : function(ret){
					    		var datas = ret.data.books||[];
									if(ret.data.statusCode==200 && datas.length!=0){
						    			for(let i=0;i<datas.length;i++){
							    			self.sc_list.push(datas[i]);
							    		}
						    		}else if(ret.data.statusCode==404 && datas.length == 0 ){
						    			 modal.toast({ message: '已到底部', duration: 1 })
						    		}
						    		self.flag=false;
						    		self.showLoading = 'hide';
					    	}

				    	})
		            });
				}
		    },
		    praise:function(sc,tabsId){
		    	if(sc.isZan==0){
		    		var self=this;
						storage.getItem('username',function(e){  //从缓存中取userId
				             self.Account = e.data;
					    	 kwz.fetch({
						    	url : '/app/book/zan?bookId='+sc.id+'&loginAccount='+self.Account +'&bookType='+tabsId,
						    	method:'POST',
						    	type:'json',
						    	success : function(ret){
						    		sc.isZan = 1
									sc.toast = sc.toast+1;
						    	}
					    	})
				    	})

		    	}else{
		    		var self=this;
						storage.getItem('username',function(e){  //从缓存中取userId
				             self.Account = e.data;
					    	 kwz.fetch({
						    	url : '/app/book/zan?bookId='+sc.id+'&loginAccount='+self.Account +'&bookType='+tabsId,
						    	method:'POST',
						    	type:'json',
						    	success : function(ret){
									sc.toast = sc.toast-1;
									sc.isZan = 0
						    	}
					    	})
				    	})
		    	}
		    },
		    goback: function () {
		      this.$router.push('/sk/sk_search');
		    },
		    scan:function(){
		    	var self=this;
		    	 var sjevent = weex.requireModule('SJevent');
		    	if(sjevent) {
		    		sjevent.QRScan(function(isbn){
		    			self.$router.push('/sk/sm_search?isbn='+isbn);
		    		})
		    	}
		    },
		    onpeScxq:function(path){
		      this.$router.push(path);
		    },
		    onloading (event) {
		    	this.page+=1;
		    	this.flag=true;
		       // modal.toast({ message: 'loading', duration: 1 })
		        this.showLoading = 'show';
		        this.light(this.tabsIndex,this.tabsId);
		    }
		},
		// components: {
		// 	'foot': f, // 添加底部导航组件
		// },
		created : function(){
			var self =this;
		    storage.getItem('username',function(e){  //从缓存中取userId
		        self.Account = e.data;
		   		self.light(0,1);  
		   	}); 
		}
	}
</script>

<style scoped>
	/*头部*/



	
	.loading {
  		width: 750px;
    	flex-direction: row;
   	 	align-items: center;
    	justify-content: center;
 	}
  	.indicator {
   		color: #888888;
	    font-size: 42px;
	    padding-top: 20px;
	    padding-bottom: 20px;
	    text-align: center;
 	 }
	.top{ 
		width: 750px; 
		height: 88px; 
		background-color: #6fa1e8; 
		flex-direction: row; 
		align-items: center; 
		justify-content: center; 
	}
	.top_name{
		font-size: 36px;
		color: #fff;	
	}
	.goback{
		position: absolute;
		top:22px;
		left: 25px;
		width: 44px;
		height: 44px;
	}
	.goback_r{
		position: absolute;
		top:22px;
		right: 25px;
		width: 44px;
		height: 44px;
	}
	.nav{
		width: 750px; 
		height: 88px; 
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-color: #e7e7e7;
		border-bottom-width: 1px;
		border-style: solid;
		background-color: #fff;
	}
	.navYes{
		 background-color: #5f94df; 
		 color: #fff;
		 padding: 1px; 
		 padding-right: 25px;
		 padding-left: 25px;
		 border-radius: 8px;
	}
	.nav_li{
		flex: 1;
		align-items: center;
		justify-content: center;
	}
	.nav1{
		width: 750px; 
		height: 88px;	
		border-color: #e7e7e7;
		border-bottom-width: 1px;
		border-style: solid;
		flex-direction: row; 
		align-items: center; 
		justify-content: left;
		position: relative;		
	}
	.nav1_li{
		position: relative;
		 flex: 1; 
		 flex-direction: row; 
		 align-items: center; 
		 justify-content: space-between; 
		 border-right-color: #e7e7e7; 
		 border-right-width: 1px; 
		 border-right-style: solid; 
		 padding-left: 20px; 
		 padding-right: 20px;	
	}
    .nav_name{
    	font-size: 34px; 
    	border-radius: 5px;  
    	color: #666;	
    	padding-left: 20px; 
    	padding-top: 8px; 
    	padding-bottom: 8px;
		padding-right: 20px;
	}
	.s_class{
		position: fixed;
	    z-index: 9999;
	    top: 264px;
		padding-left: 36px;
		padding-right: 36px;
		width: 375px;
		height: 500px;
		border-color: #e6e6e6;
		border-style: solid;
		border-width: 1px;
		background-color: #fff;

	}
	.s_class_opt{
		color: #666;
		font-size: 32px;
		width: 302px;
		height: 80px;
		flex-direction:row;
		align-items: center;
		border-bottom-color: #e6e6e6;
		border-bottom-style: solid;
		border-bottom-width: 1px;
	}
	.ydjy_list{
		width: 750px;  
		height: 290px; 
		flex-direction: row;
		align-items: center;
		background-color: #fff; 
		border-color: #ececec;
		border-bottom-width: 1px;
		border-style: solid;
		position: relative;
		z-index: 1;
	}
	.ydjy_l{
		width: 240px; 
		height:240px; 
		flex-direction: row;
		align-items: center;
		justify-content: center;
	}
	.ydjy_lPic{
		width: 190px;
		height: 240px; 
	}
	.ydjy_r{
		height: 240px; 
		width: 455px; 
	}
	.play{
		position: absolute; 
		background-color: #000;  
		width: 191px; 
		height:240px;   
		top:0; 
		left: 22px; 
		z-index: 88; 
		display: flex; 
		align-items: center; 
		justify-content: center; 
		filter:alpha(opacity=30);  /*支持 IE 浏览器*/
		-moz-opacity:0.30; /*支持 FireFox 浏览器*/
		opacity:0.30;  /*支持 Chrome, Opera, Safari 等浏览器*/	
	}
	.record{
		width: 455px; 
		height: 50px;
		font-size: 30px; 
		flex-direction: row;
		color: #999; 
	}
	.ico_22{
		width: 60px;
		height: 60px;
		position:absolute; 
		top:100px; 
		left:85px; 
		z-index:99;	
	}
	.b_list{
		flex-direction: row;
		font-size: 30px; 
		color: #666; 
		line-height: 55px;
	}
	.record_li{
		flex: 1; 
		flex-direction: row; 
		align-items: center; 
		justify-content: left;
	}
	.pic_see{
		width: 38px; 
		height: 27px; 
	}
	.pic_praise{
		width: 32px; 
		height: 28px; 
	}
	.hdzq_titil{
		flex-direction: row;
		color:#333; 
		font-size:36px; 
		height:60px;
		text-overflow:ellipsis; 
		lines:1;
	}
	.ts_label{
		font-size: 26px; 
		background-color:#d9e8fd; 
		color: #666; 
		padding-top: 4px;
		padding-bottom: 4px; 
		padding-left: 15px;
		padding-right: 15px;
		margin: 10px; 
		margin-left: 0; 
		border-radius: 5px;
	}
	.ydjy_state{
		 background-color: #f60;
		 color:#fff; 
		 font-size: 22px; 
		 position:absolute; 
		 left:22px;
		 top:10px;
		 padding-right: 15px;
		 padding-left: 15px;
		 padding-top: 8px;
		 padding-bottom: 8px;
		 z-index: 89;
		 border-top-right-radius:20px;
	     border-bottom-right-radius:20px;
	}
	.author{
		flex-direction: row;
		line-height: 40px;
	}
    .fontSize{
    	font-size: 32px;
    	color: #666;
    }
</style>