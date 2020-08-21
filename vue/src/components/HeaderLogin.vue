<template>
	<div class="Header">
			<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="true" @select="handleSelect">
				<el-menu-item ><i class="el-icon-s-home"></i>每周工作汇报系统</el-menu-item>
				<el-menu-item ><i class="el-icon-data-analysis"></i>周报阅览</el-menu-item>
			</el-menu>

	</div>
</template>

<script>
	/*
import HelloWorld from "./HelloWorld.vue"
import banner from "./banner.vue"
* */
	export default {
		name: 'HeaderLogin',
		props: {
			// msg: String
			//activeIndex:String
		},
		data() {
			return {
				//activeIndex: '/HomePage',
				//userName: ''
			};
		},

		mounted: function() {
			//this.userName = this.Cookies.get('userName')

		},
		methods: {
			handleSelect(key, keyPath) {
				console.log(key, keyPath);
			}
		},
		//引用其他的组件必须要在这里声明
		/*
	components: {
		HelloWorld,
		banner
  },*/
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<!--测试发现只有/deep/有效-->
<style scoped>
	/*//覆盖element ui的一种方式
	.Header /deep/ .el-header /deep/ .el-menu-demo /deep/ .el-submenu /deep/ .el-submenu__title{
			
			color: #42B983;
			font-size:2em;
		}
	.Header /deep/ .el-header /deep/ .el-menu-demo /deep/ .el-menu-item {
			
			color: #42B983;
			font-size:2em;
	}*/
	.el-menu.el-menu--horizontal {
		border-bottom: none;
		box-shadow: 0 0 black;
	}
</style>
