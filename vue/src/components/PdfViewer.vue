<template>
	<div style="
		" :style="{ 'height':clientHeight-130+'px','text-align':'center'}"ref="homePage">
		<h1>{{zhoubao.filePath.split("/").reverse()[0]}}</h1>
		<!--<iframe src="static/pdf.pdf" v-on:load="handleOnload" />-->
		<!--<iframe src="http://127.0.1:3000/pdf/18年寒假的图书.pdf" />-->
		<!-- <embed :src="src" /> -->
		<iframe :src="(isPro? zhoubao.filePath:'api/zhouBaoSpring/'+zhoubao.filePath)+'#zoom=110'" seamless></iframe>

	</div>
</template>

<script>
	const isPro = Object.is(process.env.NODE_ENV, 'production')
	export default {
		name: 'PdfViewer',
		data() {
			return {
				src: '',
				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production'),
				loading: true,
				clientHeight:''
			}
		},
		mounted() {

			// 获取浏览器可视区域高度
			this.clientHeight = `${document.documentElement.clientHeight}`
			//document.body.clientWidth;
			//console.log(self.clientHeight);
			window.onresize = function temp() {
				this.clientHeight = `${document.documentElement.clientHeight}`;
			};
		},
		props: {
			zhoubao: Object,
			id: ''
		},
		watch: {
			'$route'(to, from) {
				// 对路由变化作出响应...
				// console.log(to);
				//  console.log(this);
			},
			// 如果 `clientHeight` 发生改变，这个函数就会运行
			clientHeight: function() {
			//	this.changeFixed(this.clientHeight)
			}

		},
		


	}
</script>

<style scoped>
	h1 {
		margin: 0.3%;
	}

	iframe {
		width: 85%;
		height: 95%;
	}
</style>
