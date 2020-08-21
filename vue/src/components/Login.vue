<template>
	<div style="width: 100%;height:100%;padding:8% 0% 17% 0%;background-image: url('https://open.saintic.com/api/bingPic/'); transform: rotateY(180deg)">
		<el-card class="box-card" style="background-color:rgba(255,255,255,1); transform: rotateY(180deg)">
			<div v-if="login" >
				<div style="font-size: 1.2rem;text-align:center;color:#303133;width:100%;margin: 8% auto;font-weight: 600;">每周工作汇报管理系统</div>
				<el-input v-model="userName" placeholder="请输入用户名" style="width: 84%;margin: 2% 8%;"></el-input>
				<el-input placeholder="请输入密码" v-model="userPwd" show-password style="width: 84%;margin: 2% 8%;"></el-input>
				<el-button type="primary" :loading="loadState" style="width: 84%;margin: 6% 8% 4% 8%;" v-on:click="yanzheng">登录</el-button>

				<el-link type="info" @click="login=!login" style="font-size: 0.9rem;left:8%;margin: 0.2%;font-weight:500;">没有账号？点击注册</el-link>
			</div>

			<div v-if="!login">
				<div v-if="!admin">
					<div style="font-size: 1.2rem;text-align:center; #303133;width:100%;margin: 4% auto;font-weight: 600;">用户注册</div>

					<el-input v-model="newUser" placeholder="请输入真实姓名" style="width: 84%;margin: 2% 8%;" @change="existUserName()" ref="newUser"></el-input>
					<el-input placeholder="请输入密码" v-model="newPwd" show-password style="width: 84%;margin: 2% 8%;"></el-input>
					<el-input placeholder="请确认密码" v-model="twicePwd" show-password style="width: 84%;margin: 2% 8%;"></el-input>
					<el-input v-model="professor" placeholder="请输入预设暗号进行身份验证" style="width: 84%;margin: 2% 8%;"></el-input>
					<el-select v-model="type" placeholder="硕士还是博士？" style="width: 84%;margin: 2% 8%;">
						<el-option label="硕士" :value="0">
						</el-option>
						<el-option label="博士" :value="2">
						</el-option>
					</el-select>
					<el-date-picker v-model="year" type="year" placeholder="请选择入学年份" style="width: 84%;margin: 2% 8%;"></el-date-picker>
					<el-button type="primary" :loading="loadState" style="width: 84%;margin: 6% 8% 4% 8%;" v-on:click="zhuce">注册</el-button>
				</div>
				<div v-if="admin">
					<div style="font-size: 1.2rem;text-align:center; #303133;width:100%;margin: 4% auto;font-weight: 600;">管理员注册</div>
					<el-input v-model="newUser" placeholder="请输入用户名" style="width: 84%;margin: 2% 8%;" @change="existUserName()" ref="newUser"></el-input>
					<el-input placeholder="请输入密码" v-model="newPwd" show-password style="width: 84%;margin: 2% 8%;"></el-input>
					<el-input placeholder="请确认密码" v-model="twicePwd" show-password style="width: 84%;margin: 2% 8%;"></el-input>
					<el-input v-model="professor" placeholder="请设置暗号用于用户注册验证" style="width: 84%;margin: 2% 8%;"></el-input>
					<el-button type="primary" :loading="loadState" style="width: 84%;margin: 6% 8% 4% 8%;" v-on:click="zhuce">注册管理员</el-button>
					<!--2019年6月23日 管理员注册功能尚未完成-->
				</div>
				<el-link type="info" @click="login=!login;admin=false;" style="font-size: 0.9rem;left:8%;margin: 0.2%;font-weight:500;">返回登录</el-link>
				<el-link type="info" v-if="!admin" @click="admin=!admin" style="font-size: 0.9rem;left:52%;margin: 0.2%;font-weight:500;">注册管理员</el-link>

			</div>

		</el-card>
	</div>
</template>

<script>
	export default {
		name: 'Login',
		data() {
			return {
				userName: '',
				userPwd: '',
				loadState: false,
				login: true,
				admin: false,
				newUser: '',
				newPwd: '',
				twicePwd: '',
				professor: '',
				type: '',
				year: '',
				bingImg:'',
			}
		},
		mounted:function(){
			this.getBingImgUrl();
		},
		methods: {
			yanzheng: function(event) {
				this.loadState = true;


				//ajax部分 从数据库更新对应的英雄

				var self = this;
				this.axios({
					url: '/user/yanzheng/', //这个api后台本来就有
					method: 'get',
					auth: {
						//unescape(encodeURIComponent(str)
						username: unescape(encodeURIComponent(this.userName)),
						password: unescape(encodeURIComponent(this.userPwd))
						// username: this.userName,
						// password: this.userPwd
					},
					headers: {
						'Content-Type': 'application/json'
					}
				}).then(function(response) {
					//alert(response.data);
					console.log(response);
					if (response.status == 401) {
						//alert("登录失败！");
						self.$message({
							showClose: true,
							message: '用户名或密码错误',
							type: 'error'
						});
						//alert("用户名或密码错误");
						self.loadState = false;
					} else if (response.data.type == 1) {
						//alert("登录成功！");
						self.$notify({
							title: '欢迎阅览周报~',
							type: 'success'
						});
						//location.assign(ans);
						self.$router.push('AdminHomePage')
					}
					//不是管理员就是正常用户
					else if (response.data.type == 0||response.data.type == 2) {
						//alert("登录成功！");
						self.$notify({
							title: '成功',
							message: '已成功登录系统！',
							type: 'success'
						});
						//location.assign(ans);
						self.$router.push('HomePage')
					}
					//alert(response.data);
					else {
						//alert("登录失败！");
						self.$message({
							showClose: true,
							message: '用户名或密码错误',
							type: 'error'
						});
						//alert("用户名或密码错误");
						self.loadState = false;
					}

				});


				//调用Cookies存储用户名 方便跟踪用户状态
				this.Cookies.set('userName', this.userName, {
					expires: 1
				});

			},
			existUserName: function() {
				var self = this;
				//从后台中提取本周总结 -1在后端会被识别为本周
				this.axios.get("existUserName/" + self.newUser).then(function(response) {
					//alert(response.data.length);
					if (response.data == 1) {
						self.$message({
							showClose: true,
							message: '用户名已存在，请重新输入~',
							type: 'error'
						});
						self.newUser = '';
						self.$refs.newUser.focus();
					}
				});
			},
			zhuce: function() {
				this.loadState = true;
				if (!this.newUser || !this.newPwd) {
					this.$message({
						showClose: true,
						message: '用户名或密码不能为空~',
						type: 'error'
					});

					this.loadState = false;
					return;
				}
				if (this.newPwd != this.twicePwd) {
					this.$message({
						showClose: true,
						message: '两次密码不相同，请重新输入~',
						type: 'error'
					});
					this.loadState = false;
					return;
				}
				if (this.professor.localeCompare("蒋泽军") != 0 && this.professor.localeCompare("王丽芳") != 0) {
					this.$message({
						showClose: true,
						message: '导师姓名错误，身份验证失败~',
						type: 'error'
					});

					this.loadState = false;
					return;
				}
				if (this.type.length == 0) {
					this.$message({
						showClose: true,
						message: '未选择身份~',
						type: 'error'
					});

					this.loadState = false;
					return;
				}
				if (!this.year) {
					this.$message({
						showClose: true,
						message: '未选择入学年份~',
						type: 'error'
					});

					this.loadState = false;
					return;
				}
				//alert("测试");
				var json = {
					userName: this.newUser,
					userPwd: this.newPwd,
					type: this.type,
					year: this.year.getFullYear()
				};
				var self = this;
				//alert(json.year);
				this.axios({
					url: 'zhuce/',
					method: 'post',
					//发送格式为json
					data: json,
					headers: {
						'Content-Type': 'application/json'
					}
				}).then(function(response) {

					//alert(response.data);
					if (response.data == 0) {
						self.$message({
							showClose: true,
							message: '注册失败，请联系管理员',
							type: 'error'
						});
						//alert("用户名或密码错误");
						self.loadState = false;
					} else if (response.data == 1) {
						self.$message({
							showClose: true,
							message: '恭喜' + self.newUser + '注册成功,请登录',
							type: 'success'
						});
						self.loadState = false;
						self.userName = self.newUser;
						self.login = !self.login //切换为登录界面
					}

				});

			},
			
			getBingImgUrl:function(){
				var self = this;
				//从后台中提取本周总结 -1在后端会被识别为本周
				this.axios.get("https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1").then(function(response) {
					self.bingImg="https://cn.bing.com/"+response.images[0].url;
				});
				
			}
		}
	}
</script>

<style scoped>
	.box-card {
		position: relative;
		width: 30%;
		left: 15%;
		height: 100%;
	}

	/deep/ el-row {
		margin: 10%;
	}
</style>
