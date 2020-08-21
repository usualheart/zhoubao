<template>
	<div class="block" v-loading="loading">
		<el-timeline style="width: 70%;margin: auto;">
			<el-timeline-item v-for="zhoubao in zhoubaos" v-bind:key="zhoubao.id" :timestamp="'第'+getWeekCount(new Date(zhoubao.time))+'周'" placement="top">
				<div v-on:click="viewer(zhoubao)">
					<el-card shadow="hover">
						<el-row :gutter="20" type="flex" justify="center">
							<el-col :span="12">
								<div style="cursor:pointer;">
									<h2 style="font-weight:600;">{{timeFormat(new Date(zhoubao.time))}}</h2>
									<h3 style="font-weight:500;"><i class="el-icon-reading"></i> {{zhoubao.filePath.localeCompare("-1") ? zhoubao.filePath.split("/").reverse()[0]:"html在线周报" }}</h3>
									
									<h2 style="font-weight:500;color: #409EFF;" v-if="zhoubao.pingjia">老师评价:{{zhoubao.pingjia}}</h2>
									<el-button @click.stop="delPdfMd(zhoubao)" v-if="fileType(zhoubao.filePath)">删除</el-button>
								</div>
							</el-col>
							<el-col :span="5">
								<div class="boxFinding">
									<img :src="imgSrc[fileType(zhoubao.filePath)] " style="margin: 1%;" />

								</div>
							</el-col>
							<el-col :span="3">
							</el-col>
						</el-row>
					</el-card>
				</div>
			</el-timeline-item>
		</el-timeline>
	</div>
</template>
<script>
	import global_ from './Global'
	export default {
		name: 'TimeLine',
		data() {
			return {
				zhoubaos: [],
				loading:true,
				imgSrc: ["../static/html5.png", "../static/pdf.png", "../static/markdown80.png"],
				file: ["html周报", "pdf周报", "Markdown周报"],
			}
		},
		mounted: function() {
			//this.weekNow = this.$data;
			//alert(JSON.stringify(this.$root));
			//从Cookies中获取用户名
			this.userName = this.Cookies.get('userName');
			//刷新周报数据
			this.shuaxin();

		},
		methods: {
			//ajax部分 从后台中提取数据
			shuaxin: function() { //进行
				var self = this;
				self.axios.get("/zhoubao/getSomeone/" + self.userName).then(function(response) {
					self.zhoubaos = response.data;
					
					self.$nextTick(function() {
						// DOM 现在更新了
						//关闭加载进度条
						self.loading = false;
					})
					
					//当还没有周报的时候
					if (self.zhoubaos.length == 0 && (self.userName.localeCompare("123") != 0)) {
						self.$notify({
							title: '注意',
							message: '首次使用，先来看看帮助吧！',
							position: 'top-left',
							type: "warning",
							offset: 100,
							onClick: self.$router.push('Help') //这个是跳转到帮助界面
						});
					} else if (self.userName.localeCompare("123") == 0) {
						self.$message({
							showClose: true,
							message: '这里是上传周报的界面，只能看到用户自己的周报',
							type: 'success'
						});
					}
					//alert(self.zhoubaos[0].id);
				})
			},
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
			timeFormat: function(date) {
				//alert(date);
				var now = new Date();
				var yueri = (date.getMonth() + 1) + "月" + date.getDate() + "日";
				if (date.getFullYear() == now.getFullYear())
					return yueri;
				else return date.getFullYear() + "年" + yueri;

			},
			getWeekCount:global_.getWeekCount,
			//删除pdf周报文件或者markdown文件
			delPdfMd: function(zhoubao) {
				var self = this;
				var d = confirm("是否要删除" + zhoubao.filePath.split("/").reverse()[0] + "?");
				//如果确认要删除，则执行删除动作
				if (d) {
					
					this.axios({
						url: '/zhoubao/delPdfMd/'+zhoubao.id,
						method: 'get',
						//发送格式为json
						headers: {
							'Content-Type': 'application/json'
						}
					}).then(function(response) {
						self.$notify({
							title: '删除成功',
							message: '已成功删除文件',
							type: 'success'
						});
						self.shuaxin();
					});
				}
			},

			//route to PdfViewer or HtmlViewer
			viewer: function(zhoubao) {
				//如果是片段式网页周报
				if (zhoubao.filePath.localeCompare("-1") == 0) {
					if (this.Cookies.get('userName').localeCompare("123") == 0)
						this.$router.push({
							name: 'AdminHtmlViewer',
							params: {
								userName: zhoubao.userName,
								week: zhoubao.week,
							}
						});
					else
						this.$router.push({
							name: 'HtmlViewer',
							params: {
								userName: zhoubao.userName,
								week: zhoubao.week,
							}
						});
				} else { //如果不是片段式网页周报 则进行如下处理：pdf或者markdown文件

					var houzhui = zhoubao.filePath.split(".").reverse()[0].toLowerCase();
					if (houzhui.localeCompare("pdf") == 0) {
						if (this.Cookies.get('userName').localeCompare("123") == 0)
							this.$router.push({
								name: 'AdminPdfViewer',
								params: {
									zhoubao
								}
							});
						else
							this.$router.push({
								name: 'PdfViewer',
								params: {
									zhoubao
								}
							});
					} else if (houzhui.localeCompare("md") == 0) {
						this.$router.push({
							name: 'MdViewer',
							params: {
								zhoubao
							}
						});
					}
				}
				///调用Cookies存储watched用户名 方便跟踪用户状态
				//this.Cookies.set('watchedUser', this.userName, {expires: 1});
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
		margin: 4%;
	}
</style>
