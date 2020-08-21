<template>
	<!--里边这块相当于组件的模板-->
	<div class="Detail" v-if="!(detail.id==-2)">

		<span class="head">片段{{index}} </span>
		<el-input v-model="detail.head" style="width: 20%;color: #000000;font-size: large;font-weight: 1000;" placeholder="请输入标题"></el-input>
		<el-button-group v-if="!(detail.id==-1)">
			<!-- <el-button type="info" icon="el-icon-edit" plain></el-button> -->
			<el-popover placement="top" width="160" v-model="visible">
				<p>确定删除这个片段以及包含的图片吗？</p>
				<div style="text-align: right; margin: 0">
					<el-button size="mini" type="text" @click="visible = false">取消</el-button>
					<el-button type="primary" size="mini" @click="visible = false;delDetail(detail.id);delImg(detail.imgSrc)">确定</el-button>
				</div>
				<el-button slot="reference" icon="el-icon-delete">删除</el-button>

			</el-popover>
		</el-button-group>
		<br />

		<!-- <span class="head">片段{{index}} </span><input class="head" type="text" placeholder="请输入标题" v-model="detail.head" />
		<br />
		<textarea placeholder="请在这里进行编辑" rows="5" cols="20" wrap="hard" v-model="detail.content"></textarea> -->


		<el-input style="margin: 1% 0%;" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入内容" v-model="detail.content">
		</el-input>

		<img v-if="detail.imgSrc" v-bind:src="isPro?detail.imgSrc:'/api/zhouBaoSpring/'+detail.imgSrc" @click="openDelImg(detail.imgSrc)" />
		<div v-if="!detail.imgSrc" style="width: 100%;margin:0% 0%">
			<UploadImg :userName="detail.userName" :callback="afterUploadImg"></UploadImg>
		</div>


	</div>
</template>

<script>
	import UploadImg from './UploadImg'
	export default {
		name: 'DetailEdit',
		data() {
			return {
				//details:[{id:"-1",userName:"",head:"",content:"",imgSrc:"",time:"",week:""},],
				visible: false,
			}
		},
		props: {
			detail: Object,
			index: '',
			isPro: '' //用于区分开发和生产环境，自动解决跨域问题
		},
		components: {
			UploadImg
		},
		methods: {
			openDelImg(filePath) {
				this.$confirm('此操作将永久删除该图片, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.delImg(filePath);

				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},

			afterUploadImg: function(response) {
				this.detail.imgSrc = response;
				//存储详情
				this.$parent.$parent.$parent.SaveDatail(); //因为el-main（element相关组件的原因）
			},
			delDetail: function(id) {
				//console.log(this);
				var self = this;
				//删除detail
				this.axios({
					url: '/htmlZhoubao/delDetail/' + id,
					method: 'get',
				}).then(function(response) {
					self.$message({
						type: 'success',
						message: '删除成功!'
					});
					// //self.detail = ''; //响应字段设置为空//会报错

					//console.log(self.$parent.$parent.$parent);
					// self.$emit("getDetail");
					self.$parent.$parent.$parent.getDetail(); //因为el-main（element相关组件的原因）
				});

			},
			//执行删除的函数
			delImg: function(filePath) {
				if(!filePath)return;
				var self = this;
				//确认则准备开始删除文件
				this.axios({
					url: '/img/delImg',
					method: 'post',
					//发送格式为json
					data: {
						"filePath": filePath
					},
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(function(response) {
					self.$message({
						type: 'success',
						message: '删除成功!'
					});
					self.detail.imgSrc = ''; //响应字段设置为空
				//	
				});
			},

		}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	div.Detail textarea {
		display: block;
		width: 100%;
		height: auto;
		font-size: 1.8em;
		margin-bottom: 1%;
		border: 1px solid lightgrey;
		box-shadow: 2px 2px 3px #dddddd;
		font-weight: 500;
	}

	img {
		max-width: 100%;
		max-height: 800px;
	}

	div.Detail {
		z-index: 100;
		top: 1%;
		background-color: white;
		width: 90%;
		margin: 1% auto;
	}

	input.head {
		font-size: 1.3em;
		display: inline;
		margin: 2% 0%;
		font-weight: bold;
	}

	span.head {
		font-size: 1.3em;
		display: inline;
		margin: 2% 0%;
		font-weight: bold;
		position:
	}
</style>
