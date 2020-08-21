<template>
	<el-container style="height: 500px; " ref="viewerPage">
		<!-- <el-aside width="85%">


			<div style="position: fixed;right:80%;top: 10%;z-index: 10;">

				<el-button type="primary" v-if="user.localeCompare('123')==0" icon="el-icon-edit" @click="dialogPingjiaVisible = true">评价</el-button>
			</div>
			<router-view />
		</el-aside>
		<el-main>
			<MenuList></MenuList>
		</el-main> -->

		<el-aside width="15%">

				<MenuList></MenuList>

			</el-aside>
			<el-main>
				<div style="position: fixed;right:80%;top: 10%;z-index: 10;">

					<el-button type="primary" v-if="user.localeCompare('123')==0" icon="el-icon-edit" @click="dialogPingjiaVisible = true">评价</el-button>
				</div>
				<router-view  />
			</el-main>
		<el-dialog title="周报评价" :visible.sync="dialogPingjiaVisible">
			<h2 style="display: inline-block;">{{zhoubao.userName}} {{timeFormat(zhoubao.time)}}周报</h2>
			<br />
			<el-input type="textarea" style="color: #000000;font-size: xx-large;" :autosize="{ minRows: 4, maxRows: 8}"
			 placeholder="请填写评价内容" v-model="zhoubao.pingjia">
			</el-input>

			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogPingjiaVisible = false">取 消</el-button>
				<el-button type="primary" @click="dialogPingjiaVisible = false;savePingjia()">确 定</el-button>
			</div>
		</el-dialog>
	</el-container>

</template>

<script>
	import NewAdminTimeLine from './NewAdminTimeLine'
	import MenuList from './MenuList'
	const isPro = Object.is(process.env.NODE_ENV, 'production')
	export default {
		name: 'Viewer',
		data() {
			return {
				isPro: Object.is(process.env.NODE_ENV, 'production'),
				loading: true,
				dialogPingjiaVisible: false,
				zhoubao: '',
				user: '',
				clientHeight: ''
			}
		},
		mounted: function() {

			this.zhoubao = this.$route.params.zhoubao;
			this.user = this.Cookies.get('userName');

			// 获取浏览器可视区域高度
			this.clientHeight = `${document.documentElement.clientHeight}`
			//document.body.clientWidth;
			//console.log(self.clientHeight);
			window.onresize = function temp() {
				this.clientHeight = `${document.documentElement.clientHeight}`;
			};

		},
		props: {},
		watch: {
			'$route'(to, from) {
				// 对路由变化作出响应...
				//console.log(this.dialogPingjiaVisible)
				this.zhoubao = to.params.zhoubao;
			},
			// 如果 `clientHeight` 发生改变，这个函数就会运行
			clientHeight: function() {
				this.changeFixed(this.clientHeight)
			}
		},
		components: {
			NewAdminTimeLine,
			MenuList
		},
		methods: {
			//单击保存周报后执行的动作
			savePingjia: function(callback) {
				var self = this;
				this.axios({
					url: '/zhoubao/updateZhoubao',
					method: 'post',
					//发送格式为json
					data: self.zhoubao,
				}).then(function(response) {

					self.$message({
						message: '评价成功',
						type: 'success'
					});
				});
			},
			changeFixed(clientHeight) { //动态修改样式
				// console.log(clientHeight);
				//console.log(this.$refs.viewerPage.$el.style.height);
				this.$refs.viewerPage.$el.style.height = clientHeight - 70 + 'px';
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
		}



	}
</script>

<style>
	.el-scrollbar__wrap {
		overflow-x: hidden;
	}
</style>
