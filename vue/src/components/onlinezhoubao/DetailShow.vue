<template>
	<!--里边这块相当于组件的模板-->
	<div class="Detail" v-bind:id="detail.id">
		<h3>练习{{index}} {{detail.head}}</h3>
		<!--<span style="float:right;font-size:250%;margin-right:20%">&times;</span>-->
		<p>{{detail.content}}</p>
		<img v-bind:src="isPro?detail.imgSrc:'api/zhouBaoSpring/'+detail.imgSrc" />
	</div>
</template>

<script>
	export default {
		name: 'DetailShow',
		data() {
			return {
				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production')

			}
		},
		props: {
			detail: Object,
			index: '',
		}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	img {
		max-width: 100%;
		max-height: 700px;
	}

	div.Detail {
		z-index: 100;
		top: 1%;
		background-color: white;
		width: 90%;
		margin: auto;
	}
</style>
