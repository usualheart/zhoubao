<template>
	<div>
		<div v-loading="loading">
			<h1 style="margin: 1.5% auto;width: 20rem;">{{userName}}的周报</h1>
			<TableShow v-bind:sectionitems="sectionitems" v-if="!loading"></TableShow>
			<div id="概述" v-if="!loading">
				<h3>总结</h3>
				<p>{{summary.content}}</p>
			</div>
			<DetailShow v-for="(detail,index) in details" v-bind:key="detail.id" v-bind:detail="detail" v-bind:index="index+1"></DetailShow>
		</div>
		<!-- <div >
			<AllTimeLine/>
		</div> -->
	</div>
</template>

<script>
	import TableShow from './TableShow'
	import DetailShow from './DetailShow'
	import AllTimeLine from '../quan/AllTimeLine'
	export default {
		name: 'HtmlViewer',
		data() {
			return {
				details: [],
				//sectionitems:[[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"lianxi",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}]],//json数组 用于存储每周概览表的数据
				sectionitems: [],
				sectionAll: [],
				summary: '',
				loading: true

			}
		},
		props: {
			userName: String,
			week: '',
			zhoubao:''
		},
		components: {
			TableShow,
			DetailShow,
			AllTimeLine
		},
		mounted: function() {
			//从Cookies中获取用户名
			//this.userName=this.Cookies.get('watchName');
			//this.week=this.Cookies.get('watchWeek');
			//this.week=14;
			//this.userName='666';
			//刷新周报数据
			this.shuaxin();

		},
		watch: {
			'$route'(to, from) {
				// 对路由变化作出响应...
				this.loading=true;
				this.sectionitems=[];
				this.sectionAll=[];
				this.details=[];
				this.summary='';
				this.shuaxin();
			}
		},
		methods: {
			//从后台获取周报数据
			shuaxin: function() { //进行图片路径的获取
				var self = this;
				//ajax部分 
				//从后台中提取本周表格详情数据 -1在后端会被识别为本周
				this.axios.get("/htmlZhoubao/getSection/" + self.userName + "/" + self.week).then(function(response) {
					self.sectionAll = response.data;
					//alert(self.sectionAll);

					//把sectionALL中数据存储到sectionitems中
					var m = 0;
					for (var i = 0; i < 7; i++) {
						var tmpDay = [];
						for (var j = 0; j < 3; j++) {
							//解决数组变动检测的两种方法
							//方法一：Vue.set
							//Vue.set(self.sectionitems[i], j, self.sectionAll[m++]);
							// 方法二：Array.prototype.splice
							tmpDay.push(self.sectionAll[m++]);
							//self.sectionitems[i].splice(j, 1, self.sectionAll[m++]);
							//self.sectionitems[i][j]=self.sectionAll[m++];//Vue不能检测这种数组的变动 会影响列表渲染的刷新！
						}
						self.sectionitems.push(tmpDay);
					}
					self.$nextTick(function() {
						// DOM 现在更新了
						// `this` 绑定到当前实例
						//关闭加载进度条
						self.loading = false;
					})

				});
				this.getSummary();
				//ajax部分 从后台中提取周报片段数据
				this.axios.get("/htmlZhoubao/getDetail/" + self.userName + "/" + self.week).then(function(response) {
					self.details = response.data;


				})
			},
			getSummary: function() {
				var self = this;
				//从后台中提取本周总结 -1在后端会被识别为本周
				this.axios.get("/htmlZhoubao/getSummary/" + self.userName + "/" + self.week).then(function(response) {
					if (response.data[0] != null)
						self.summary = response.data[0];
					//alert(self.summary);

				});
			},
		}

	}
</script>


<style scoped>
	div#概述 {
		width: 90%;
		margin: auto;
	}
</style>
