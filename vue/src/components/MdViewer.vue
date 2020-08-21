<template>
	<div v-loading="loading">
		
		<!--'v-highlight'是自定义指令 用于解决在单文件组建中使用highlight的问题-->
		<h1 style="text-align: center;">{{zhoubao.filePath.split("-").reverse()[0]}}</h1>
		<div v-html="compiledMarkdown" class="markdown-body" v-highlight>
			
		</div>
	</div>
</template>

<script>
	import _ from 'lodash' //两种方法都对
	import marked from 'marked'
	export default{
		name:'MdViewer',
		data(){
			return{
				mdStr:'',
				html: '',
				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production'),
				loading: true
			}
		},
		props:{
			zhoubao:Object,
		},
		mounted:function(){
			this.getMdStr(this.zhoubao.filePath);
		},
		watch: {
			
			zhoubao:function(){
				this.loading=true;
				this.getMdStr(this.zhoubao.filePath);
			}
		
		},
		computed: {
			compiledMarkdown: function() {
				this.html = marked(this.mdStr, {
					sanitize: true
				});
				if (this.html) this.textArea = false;
				
				
				//alert(this.html);
				return this.html;
			}
		},
		methods:{
			
			getMdStr: function(filePath) {
				var self = this;
				this.axios({
					url: filePath,
					method: 'get',
					// headers: {
					 	'Content-Type': 'application/octet-stream'
					// }
				}).then(function(response) {
					//alert(response);
					self.mdStr = response.data;
					self.$nextTick(function() {
						// DOM 现在更新了
						// `this` 绑定到当前实例
						//关闭加载进度条
						self.loading = false;
					})
				});
			},
			/*
			handleOnload:function(){
				//alert("加载成功");
				var self=this;
				setTimeout(() => {
				  self.loading=false;
				}, 700);
				//this.loading=false;
			}*/
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
	
		
		h1{margin:0.3%;}
	embed{
		width: 100%;
		height: 49rem;
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
