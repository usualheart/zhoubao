<template>
	<div>

		<el-container>
			<el-aside width="200px">
				<el-menu style="position: fixed;" default-active="1" class="el-menu-vertical-demo">

					<el-menu-item index="5" @click="dialogHelpVisible = true">
						<i class="el-icon-s-help"></i>
						<span slot="title">使用帮助</span>
					</el-menu-item>
					<el-menu-item index="1" @click="dialogDetailVisible = true">
						<i class="el-icon-s-help"></i>
						<span slot="title">添加片段</span>
					</el-menu-item>


					<el-menu-item index="4" @click="showZhoubao">
						<i class="el-icon-monitor"></i>
						<span slot="title">预览效果</span>
					</el-menu-item>
					<el-menu-item index="2" @click="SaveAll">
						<i class="el-icon-upload"></i>
						<span slot="title">保存周报</span>
					</el-menu-item>
				</el-menu>
			</el-aside>
			<el-main>
				<TableEdit ref="TableEdit" v-bind:details="details"></TableEdit>

				<div id="概述">
					<h2>总结：</h2>
					<el-input type="textarea" style="color: #000000;font-size: large;"  :autosize="{ minRows: 4, maxRows: 8}" placeholder="请写总结,总结未填写之前不会提交到老师端" v-model="summary.content">
					</el-input>
					<br/><br/>
					<!-- <textarea rows="3" placeholder="请写总结,总结未填写之前不会提交到老师端" v-model="summary.content"></textarea> -->
				</div>
				
				<DetailEdit  :isPro="isPro" v-for="(detail,index) in details"  v-bind:key=detail.id v-bind:detail="detail" v-bind:index="index+1"></DetailEdit>

				<el-dialog title="新建详细周报片段" :visible.sync="dialogDetailVisible">
					
					<!-- <img v-bind:src="isPro?detailOne.imgSrc:'/api/zhouBaoSpring/'+detailOne.imgSrc"/>
					 -->
					 <DetailEdit :isPro="isPro"  v-bind:detail="detailOne" ></DetailEdit>
					 
				
					<div slot="footer" class="dialog-footer">
						<el-button @click="dialogDetailVisible = false">取 消</el-button>
						<el-button type="primary" @click="dialogDetailVisible = false;makeDetail()">确 定</el-button>
					</div>
				</el-dialog>


				<!--使用帮助对话框-->
				<el-dialog title="在线写周报帮助" :visible.sync="dialogHelpVisible">
					<div style="width: 80%;margin: auto;">
						<h3>步骤 1 上传图片</h3>
						<p>上传周报图片每张图片可代表一个练习 上传后可进行编辑 (不需要图片可省略)</p>
						<h3>步骤 2 填写表格 和图片详情</h3>
						<p>填写周报表格,可根据重要性单击单元格切换样式,如果第一步已上传图片可选择将对应练习关联起来</p>
						<h3>步骤 3 填写总结 保存周报</h3>
						<p>总结填写前,周报不会发送到老师端,但依然可以保存,一般在每周末填写总结 保存后周报会同步到老师端</p>
					</div>
				</el-dialog>
			</el-main>
		</el-container>


		<!--回到顶部-->

	</div>
</template>

<script>
	import TableEdit from './TableEdit'
	import DetailEdit from './DetailEdit'
	import UploadImg from './UploadImg'
	export default {
		name: 'HtmlEdit',
		data() {
			return {
				details: [],
				detailOne: {
					id: "-1",
					userName: this.Cookies.get('userName'),
					head: "",
					content: "",
					imgSrc: "",
					time: "",
					week: ""
				},
				summary: {
					id: "-1",
					userName: "",
					content: "",
					time: "",
					week: ""
				},
				userName: '',
				week: '',
				dialogTableVisible: false,
				dialogHelpVisible: false,
				dialogDetailVisible: false,
				
				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production')
			}
		},
		components: {
			TableEdit,
			DetailEdit,
			UploadImg
		},
		mounted: function() {
			
				//console.log(this);
			//从Cookies中获取用户名
			this.userName = this.Cookies.get('userName');
			this.week = this.getWeekCount(new Date());
			//刷新周报数据
			this.shuaxin();
		},
		methods: {
			//从后台获取周报数据
			shuaxin: function() { //进行图片路径的获取

				//从后台提取总结
				this.getSummary();
				//从后台中提取本周表格数据 -1在后端会被识别为本周
				//alert('test');
				//从后台中提取本周(周二到下周周一之间)的周报片段(只要上传图片一定会有)
				this.getDetail();
			},
			getSummary: function() {
				var self = this;
				//从后台中提取本周总结 -1在后端会被识别为本周
				this.axios.get("/htmlZhoubao/getSummary/" + self.userName + "/" + "-1").then(function(response) {
					//alert(response.data.length);
					if (response.data.length != 0)
						self.summary = response.data[0];
				});
			},
			SaveSummary: function() {
				if (this.summary.id == "-1" && this.summary.content) {
					/*
					if(this.summary.content.length>450){
						this.$message({
						  message: '总结太长，无法保存！',
						  type: 'error'
						});
						return;
					}*/
					//对总结的数据进行相应的完善
					this.summary.userName = this.userName;

					//将summary中的数据插入到数据库中
					var self = this;
					this.axios({
						url: '/htmlZhoubao/saveSummary/',
						method: 'post',
						//发送格式为json
						data: JSON.stringify(self.summary),
						headers: {
							'Content-Type': 'application/json'
						}
					}).then(function(response) {
						self.summary = response.data; //更新id
						//alert(self.summary);
					});

					//将本次周报总索引插入到数据库中,建立本周周报索引
					this.axios.get("/zhoubao/makeHtmlZhoubao/" + self.userName);
				} else {
					//更新到数据库

					var self = this;
					this.axios({
						url: '/htmlZhoubao/updateSummary/',
						method: 'post',
						//发送格式为json
						data: JSON.stringify(self.summary),
						headers: {
							'Content-Type': 'application/json'
						}
					});
				}
			},
			//添加周报片段
			makeDetail: function() {
				//解决数组变动检测的两种方法
				//方法一：Vue.set
				//Vue.set(self.sectionitems[i], j, self.sectionAll[m++]);//zheli bunneg shiyong 
				// 方法二：Array.prototype.splice
				//self.sectionitems[i].splice(j, 1, self.sectionAll[m++]);
				//self.sectionitems[i][j]=self.sectionAll[m++];//Vue不能检测这种数组的变动 会影响列表渲染的刷新！
				this.details.push(this.detailOne);
				console.log(this.details);
				this.detailOne = {
					id: "-1",
					userName: this.Cookies.get('userName'),
					head: "",
					content: "",
					imgSrc: "",
					time: "",
					week: ""
				};
				this.SaveDatail();
			},
			getDetail: function() { //进行图片路径的获取
				var self = this;
				//ajax部分
				//alert("detail")
				//从后台中提取本周(周二到下周周一之间)的周报片段(只要上传图片一定会有)
				this.axios.get("/htmlZhoubao/getDetail/" + self.userName + "/" + -1).then(function(response) {

					//只有数据库中有周报 才对数据进行更新
					if (response.data.length != 0)
						self.details = response.data;
					//else
					//说明没有图片记录首次进入提醒上传图片
					//self.showUpImg=true;

					//alert(self.details[0]);
				});
			},
			//单击保存周报后执行的动作
			//单击保存周报后执行的动作
			SaveDatail: function(callback) {
				var self = this;
				//如果是首次无图片的情况
				//alert(self.details[0].id.localeCompare("-1"));
				/*
				if(self.details[0].id=="-1"){
					self.details[0].userName=this.userName;
					self.details[0].week=this.week;
					self.details[0].time=new Date();
				}*/
				//将details中的数据插入到数据库中
				//在后端会根据id是否为“-1”来判断是插入还是更新
				//只有details非空时才进行保存
				if (self.details.length != 0) {
					var self = this;
					this.axios({
						url: '/htmlZhoubao/saveDetail/',
						method: 'post',
						//发送格式为json
						data: JSON.stringify(self.details),
						headers: {
							'Content-Type': 'application/json'
						}
					}).then(function(response) {
							
						if(response){
							self.details=response.data;
						callback;
						}
				});
				}
			},
			SaveAll: function() {
				var self = this;
				//保存周报详情（会自动判断更新还是保存）
				this.SaveDatail();
				//更新总结中的数据
				this.SaveSummary();
				//更新表格中的数据
				this.$refs.TableEdit.editDone();
				this.$message({
					message: '恭喜你，周报保存成功',
					type: 'success'
				});
				//刷新片段数据
				//this.getDetail();
			},
			showZhoubao: function() {
				this.$router.push({
					name: 'HtmlViewer',
					params: {
						userName: this.userName,
						week: this.week,
					}
				});
			},
			//计算日期是本学期开学第几周,参数是计算的日期
			getWeekCount: function(insertDate) {
				//通过当前日期 计算第几周
				//设置开学日期，用于计算第几周
				var startDay = new Date();
				startDay.setFullYear(2019, 1, 26); //介于 0 ~ 11 之间。用本地时间表示。相当于设置为2月26

				//时间差 计算相当于第几周
				var ms = insertDate.getTime() - startDay.getTime() + 3000; //多增加3秒钟时间 用来解决边界问题
				var x = 1000 * 60 * 60 * 24;
				var DayCount = ms / x; //天数
				var weekCount = parseInt(DayCount / 7) + 1; //计算第几周
				return weekCount;
			}


		}

	}
</script>


<style scoped>
	div#概述 {
		width: 90%;
		margin: auto;
	}

	div#概述 textarea {
		display: block;
		width: 100%;
		height: auto;
		font-size: 1.8em;
		margin-bottom: 1%;
		border: 1px solid lightgrey;
		box-shadow: 2px 2px 3px #dddddd;
		font-weight: 500;
	}
</style>
