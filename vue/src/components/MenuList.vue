<template>

	<div class="boxFinding" style="width: 90%;margin:0% 5%;" v-loading="loading">
		<div style="width: 42%;" v-for="(zhoubaoWeeks,index1) in zhoubaoAll" v-bind:key="zhoubaoWeeks[0].id" ref="allPeople">

			<h2>{{timeFormat(zhoubaoWeeks[1][0][0].time) }}<!-- {{ ' 第'+getWeekCount(new Date(zhoubaoWeeks[1][0][0].time))+'周'}} -->
			</h2>

			<div>

				<h4 v-if="zhoubaoWeeks[0].length">博士</h4>
				<div style="cursor:pointer;" v-for="(zhoubao1,index2) in zhoubaoWeeks[0]" v-bind:key="zhoubao.id">
					<el-link style="font-size: large;margin: 2% 0%;" :type="elLinkType[zhoubao.indexOf(zhoubao1)]" v-on:click="viewer(zhoubao1);changeMyType(zhoubao1)">{{zhoubao1.userName}}</el-link>
				</div>
			</div>
			<div>
				<div v-for="(shuoshiYear,index2) in zhoubaoWeeks[1]" v-bind:key="shuoshiYear[0].id">
					<h4>硕士{{shuoshiYear[0].year}}级</h4>
					<div style="cursor:pointer;" v-for="(zhoubao1,index3) in shuoshiYear" v-bind:key="zhoubao1.id">
						<el-link style="font-size:large;margin: 2% 0%;" :type="elLinkType[zhoubao.indexOf(zhoubao1)]" v-on:click="viewer(zhoubao1);changeMyType(zhoubao1)">{{zhoubao1.userName}}</el-link>
					</div>
				</div>
			</div>

		</div>

	</div>





</template>
<script>
	import global_ from './Global'
	export default {
		name: 'NewAdminTimeLine',
		data() {
			return {
				zhoubaoWeeks: [],
				zhoubao: [],
				zhoubaoAll: [],
				loading: true,
				imgSrc: ["../static/html5.png", "../static/pdf.png", "../static/markdown80.png"],
				elLinkType: []
			}
		},
		mounted: function() {
			//刷新周报数据
			this.shuaxin();

		},
		watch: {
			zhoubao: function() {


				/****************
				 * 制作需要展示的数组
				 * 步骤1:将所有周报按周分组 
				 * ***************/
				//console.log(this.zhoubao)
				//把this.zhoubao按周划分为数组 装进zhoubaoAll数组
				var start = 0;
				for (var i = 0; i < this.zhoubao.length; i++) {
					if (this.zhoubao[start].week != this.zhoubao[i].week) {
						this.zhoubaoAll.push(this.makeWeek(this.zhoubao.slice(start, i)));
						start = i;
					}
				}
				//边界处理 复制剩余元素
				this.zhoubaoAll.push(this.makeWeek(this.zhoubao.slice(start)));

				this.$nextTick(function() {
					// DOM 现在更新了
					// `this` 绑定到当前实例
					//关闭加载进度条
					this.loading = false;
				})
				
				
				
				//修改elLinkType数组 全部为default
				this.elLinkType = this.zhoubao.map(a => "default");
				var ids=this.zhoubao.map(a=> a.id);
				var index=ids.indexOf(this.$route.params.zhoubao.id);
				this.elLinkType.splice(index, 1, "primary");
				// this.changeMyType(this.$route.params.zhoubao);

			},
		},
		methods: {

			changeMyType: function(zhoubao) {
				var index = this.zhoubao.indexOf(zhoubao); //找到元素在数组中的位置 这样就可以定位vue组件
				// this.elLinkType[index] = "primary";
				// Array.prototype.splice
				//下边的方法可以让vue检测到数组变动
				this.elLinkType.splice(index, 1, "primary")
			},
			// makeType: function(zhoubao) {
			// 	// console.log(this.$refs.allPeople[index1]);
			// 	//var result = this.zhoubao.map(a=> a.id);//把数组种某个属性映射为新的数组
			// 	//console.log(result);
			// 	//console.log(this.zhoubao.indexOf(zhoubao));
			// 	var index=this.zhoubao.indexOf(zhoubao);//找到元素在数组中的位置 这样就可以定位vue组件
			// 	//console.log(this.$children[index]);
			// 	this.$children[index].type='primary';
			// 	//通过DOM元素几乎无法修改 由于嵌套了很多次
			// 	//console.log(this.$refs.allPeople[index1].children[2].children[index2].children[index3+1].children[0]);
			// },
			//ajax部分 从后台中提取数据
			shuaxin: function() { //进行文件路径的获取
				var self = this;
				//alert('123');
				this.axios.get("/zhoubao/getRecentWeeks/" + 5).then(function(response) {
					self.zhoubao = response.data; //
				})

			},
			makeWeek: function(oneWeekAll) {
				var ans = [];
				ans.push(this.boshi(oneWeekAll));
				ans[1] = this.shuoshi(oneWeekAll);
				return ans;
			},

			getWeekCount: global_.getWeekCount,
			//判断周报文件类型
			fileType: function(filePath) {
				if (filePath.localeCompare('-1') == 0) return 0; //表示是section式的网页周报
				else {
					var strs = filePath.split(".");
					var houzhui = strs[strs.length - 1].toLowerCase();
					if (houzhui.localeCompare("pdf") == 0) return 1; //pdf文件
					else if (houzhui.localeCompare("md") == 0) return 2; //markdown文件
				}
			},
			//route to PdfViewer or HtmlViewer
			viewer: function(zhoubao) {
				var id = zhoubao.id;
				//如果是片段式网页周报
				if (zhoubao.filePath.localeCompare("-1") == 0) {

					this.$router.push({
						name: 'HtmlViewerAll',
						params: {
							userName: zhoubao.userName,
							week: zhoubao.week,
							zhoubao: zhoubao
						}
					});
				} else { //如果不是片段式网页周报 则进行如下处理：pdf或者markdown文件
					var strs = zhoubao.filePath.split(".");
					var houzhui = strs[strs.length - 1].toLowerCase();
					if (houzhui.localeCompare("pdf") == 0) {

						this.$router.push({

							//path: `/Viewer/pdf/`,
							name: 'pdfViewerAll',
							params: {
								zhoubao,

								id
							}
						});
					} else if (houzhui.localeCompare("md") == 0) {
						this.$router.push({
							name: 'MdViewerAll',
							params: {
								zhoubao,

								id
							}
						});
					}
				}
			},
			timeFormat: function(date) {
				//alert(date);
				var now = new Date();
				date = new Date(date);
				var yueri = (date.getMonth() + 1) + "月" + date.getDate() + "日";
				if (date.getFullYear() == now.getFullYear())
					return yueri;
				else return date.getFullYear() + "年" + yueri;

			},

			//用于对每周周报分类呈现的一些方法
			boshi: function(zhoubaoWeeks) {
				//console.log(zhoubaoWeeks);
				for (var i = 0; i < zhoubaoWeeks.length; i++) {
					if (zhoubaoWeeks[i].type == 0) {
						return zhoubaoWeeks.slice(0, i);
					}
				}
			},
			shuoshi: function(zhoubaoWeeks) {
				//寻找表示硕士的数据的起点
				var start = 0;
				for (var i = 0; i < zhoubaoWeeks.length; i++) {
					if (zhoubaoWeeks[i].type == 0) {
						start = i;
						break;
					}
				}
				//开始按照年份进行划分
				var start = start;
				var ans = [];
				for (var i = start; i < zhoubaoWeeks.length; i++) {
					if (zhoubaoWeeks[i].year != zhoubaoWeeks[start].year) {
						ans.push(zhoubaoWeeks.slice(start, i));
						start = i;
					}
				}
				ans.push(zhoubaoWeeks.slice(start)); //复制最后一段数组

				return ans;
			},


		}
	}
</script>

<style scoped>
	.boxFinding {
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;
		align-items: center;
		justify-content: left;
		margin: 1% 0%;
	}

	span {
		font-size: x-large;
	}

	h4 {
		margin: 10% 0% 5% 0%;
	}

	h2 {
		margin: 10% 0% 0% 0%;
	}
</style>
