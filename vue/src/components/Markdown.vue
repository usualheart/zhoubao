<template>
	<div id="editor" v-loading="loading">


		<div class="boxFinding">
			<el-button type="primary" v-if="buttonPrimary" @click="confirmSaveMarkdown">保存本页Markdown</el-button>
			<el-button type="success" v-if="!buttonPrimary">已上传</el-button>

			<el-button @click="input=''">从零开始编写</el-button>
			<el-button @click="getMdStr('../static/周报模板.md')">使用模板编写</el-button>
			<el-button type="success" @click="upMd()">从文件上传Markdown</el-button>
		</div>
		<br />
		<el-row :gutter="6" type="flex" justify="left">
			<el-col :span="10">
				<textarea id="editor" :value="input" ref="textArea" @input="update" v-if="true"></textarea>
			</el-col>
			<el-col :span="14">
				<!--'v-highlight'是自定义指令 用于解决在单文件组建中使用highlight的问题-->
				<div v-html="compiledMarkdown" class="markdown-body" v-highlight></div>
			</el-col>
		</el-row>
	</div>
</template>
<script type="text/x-template" id="hello-world-template">
	<p>Hello hello hello</p>
</script>


<script>
	//let _ = require('lodash')
	import _ from 'lodash' //两种方法都对
	import marked from 'marked'
	export default {
		name: "Markdown",
		data() {
			return {
				userName: '',
				input: '',
				html: '',
				textArea: 'true',
				buttonPrimary: true,
				loading:true
				//blogHtml:'../assets/hello.html',//内嵌网页不安全 也未能突破
			}
		},
		computed: {
			compiledMarkdown: function() {
				this.html = marked(this.input, {
					sanitize: true
				});
				if (this.html) this.textArea = false;
				//alert(this.html);
				
				return this.html;
				
			}
		},
		mounted: function() {
			this.userName = this.Cookies.get('userName');
			this.getMdStr("/static/周报markdown介绍.md");
		},
		methods: {
			update: _.debounce(function(e) {
				//this.input = e.target.value;
				this.input = this.$refs.textArea.value;
			}, 300),
			getMdStr: function(filePath) {
				var self = this;
				this.axios({
					url: filePath,
					method: 'get',
					headers: {
						'Content-Type': 'application/json'
					}
				}).then(function(response) {
					//alert(response);
					self.input = response.data;
					self.$nextTick(function() {
						// DOM 现在更新了
						// `this` 绑定到当前实例
						//关闭加载进度条
						self.loading = false;
					})
				});
			},
			saveMarkdown: function() {
				var self = this;
				var markdownJson = {
					content: this.input
				};
				this.axios({
					url: '/zhoubao/saveMarkdown/' + self.userName,
					method: 'post',
					//发送格式为json
					data: markdownJson, //JSON.stringify(
					headers: {
						'Content-Type': 'application/json'
					}
				}).then(function(response) {
					self.$notify({
						title: "新建成功",
						message: 'Markdown周报新建成功！',
						type: 'success'
					});
					self.$router.push('HomePage');
				});
				self.buttonPrimary = false
			},
			confirmSaveMarkdown: function() {
				this.$confirm('此操作会上传Markdown周报, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '再编辑一下',
					type: 'warning'
				}).then(() => {
					this.saveMarkdown();
				}).catch(() => {
					this.$message({
						type: 'info',
						title: '取消',
						message: '已取消'
					});
				});
			},
			upMd: function() {
				this.$router.push('UploadPdf');
			}
		},
		directives: {
			//局部自定义 用于解决在单文件组建中使用highlight的问题 
			'highlight': function(el) {
				let blocks = el.querySelectorAll('pre code');
				blocks.forEach((block) => {
					hljs.highlightBlock(block)
				})
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
		margin: 0.5% 0%;
	}


	div#editor {
		height: 100%;
	}

	textarea#editor {
		width: 100%;
		height: 90%;
		font-size: 1.2em;
		border: 1px solid lightgrey;
		padding: 2%;
	}




	.markdown-body {
		box-sizing: border-box;
		min-width: 200px;
		max-width: 980px;
		margin: 0 auto;
		padding: 45px;

	}

	@media (max-width: 767px) {
		.markdown-body {
			padding: 15px;
		}
	}
</style>
