<template>
	<div id="app">
		<router-view name="header" />
<div style="width: 79%;margin: auto;">
					<router-view name="main0" />
				</div>
				<div style="width: 100%;margin: auto;">
					<router-view name="main1" />
				</div>

				<!--网页底部-->
				<router-view name="footer" />



	</div>
</template>

<script>
	export default {
		name: 'App',
		data() {
			return {

				clientHeight: '',

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
		

		methods: {

			
		},

	}
</script>

<style>
</style>
