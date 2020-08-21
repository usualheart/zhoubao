<template>
	<div class="block" v-loading="loading">
		<el-container>
			<el-aside width="200px">Aside</el-aside>
			<el-main>
				<el-timeline style="width: 90%;margin: auto;">
					<div>
						<!--:timestamp="'第'+zhoubaoWeeks[0].week+'周'"-->
						<el-timeline-item placement="top" v-for="(zhoubaoWeeks,index) in zhoubaoAll" v-bind:key="zhoubaoWeeks[0].id"
						 :timestamp="'第'+getWeekCount(new Date(zhoubaoWeeks[1][0][0].time))+'周'">

							<el-card shadow="hover">
								<!--标题-->
								<el-row :gutter="5" type="flex" justify="center">
									<el-col :span="23" :offset=1>
										<div style="position: relative;top:20%;font-size: x-large;font-weight:700;">
											<span style="display: inline-block;">{{timeFormat(zhoubaoWeeks[1][0][0].time)}}</span>
										</div>
									</el-col>

								</el-row>

								<el-row :gutter="5" type="flex" justify="center">
									<el-col :span="3" :offset=1>

										<div style="position: relative;top:30%;">
											<h3 v-if="zhoubaoWeeks[0].length">博士：</h3>
										</div>
									</el-col>
									<el-col :span="20">
										<div class="boxFinding">

											<div style="display:inline-block;text-align:center;cursor:pointer;" v-for="(zhoubao,index) in zhoubaoWeeks[0]"
											 v-bind:key="zhoubao.id" v-on:click="viewer(zhoubao)">
												<img v-bind:src="imgSrc[fileType(zhoubao.filePath)]" /><br />
												<a style="color:black;">{{zhoubao.userName}}</a><br />
											</div>
										</div>
									</el-col>
								</el-row>
								<el-row :gutter="5" type="flex" justify="center" v-for="shuoshiYear in zhoubaoWeeks[1]" v-bind:key="shuoshiYear[0].id">
									<el-col :span="3" :offset=1>
										<div style="position: relative;top:20%;">
											<h4>硕士{{shuoshiYear[0].year}}级：</h4>
										</div>
									</el-col>
									<el-col :span="20">
										<div class="boxFinding">

											<div style="display:inline-block;text-align:center;cursor:pointer;" v-for="(zhoubao,index) in shuoshiYear"
											 v-bind:key="zhoubao.id" v-on:click="viewer(zhoubao)">
												<img v-bind:src="imgSrc[fileType(zhoubao.filePath)]" /><br />
												<a style="color:black;">{{zhoubao.userName}}</a><br />
											</div>
										</div>

									</el-col>
								</el-row>

							</el-card>
						</el-timeline-item>
					</div>
				</el-timeline>

			</el-main>
		</el-container>



	</div>
</template>
<script>
	import global_ from './Global'
	export default {
		name: 'NewAdminView',
		data() {
			return {
				zhoubaoWeeks: [],
				zhoubao: [],
				zhoubaoAll: [],
				loading: true,
				imgSrc: ["../static/html5.png", "../static/pdf.png", "../static/markdown80.png"]
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


			}
		},
		methods: {
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
				//如果是片段式网页周报
				if (zhoubao.filePath.localeCompare("-1") == 0) {
					
						this.$router.push({
							name: 'HtmlViewerAll',
							params: {
								userName: zhoubao.userName,
								week: zhoubao.week,
							}
						});
				} else { //如果不是片段式网页周报 则进行如下处理：pdf或者markdown文件
					var strs = zhoubao.filePath.split(".");
					var houzhui = strs[strs.length - 1].toLowerCase();
					if (houzhui.localeCompare("pdf") == 0) {
						
							this.$router.push({
								name: 'PdfViewerAll',
								params: {
									zhoubao
								}
							});
					} else if (houzhui.localeCompare("md") == 0) {
						this.$router.push({
							name: 'MdViewerAll',
							params: {
								zhoubao
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
			/*
			 *分页功能
			 */
			getPage: function() {
				var start = self.currentPage * self.pageSize;
				var tmp = self.videos;
				alert(tmp);
				self.currentVideos = tmp.slice(start, start + 5);
			},
			nextPage: function() {
				if (this.dataShow < this.pageNum - 1)
					this.dataShow++;
			},
			shangyiPage: function() {
				this.dataShow--;
			},
			firstPage: function() {
				this.dataShow = 0;
			},
			lastPage: function() {
				this.dataShow = this.pageNum - 1;
			},
			locatePage: function(page) {
				this.dataShow = page;
			},

		}
	}
</script>

<style scoped>
	.boxFinding {
		display: flex;
		flex-wrap: wrap;
		flex-direction: row;
		align-items: center;
		justify-content: left;
		margin: 1% 0%;
	}
</style>
