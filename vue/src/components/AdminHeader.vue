<template>
	<div class="Header" >
		<el-row :gutter="20" type="flex" justify="center" style="border-bottom: solid 1px #e6e6e6;;">
			<el-col :span="18" >
				<!--el-header-->
				<!-- 即便 `true` 是静态的，我们仍然需要 `v-bind` 来告诉 Vue这是一个 JavaScript 表达式而不是一个字符串。-->
				<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="true" @select="handleSelect" >
					<el-menu-item index="/AdminHomePage"><i class="el-icon-s-home"></i>周报阅览</el-menu-item>
					<el-menu-item index="/HomePage" ><i class="el-icon-upload2"></i>体验上传系统</el-menu-item>
					<!--<el-menu-item index="3"><a href="https://www.ele.me" target="_blank">博客管理</a></el-menu-item>-->
				</el-menu>
			</el-col>
		</el-row>
		
		<!--体验上传系统的弹出框-->
		<!--
		<el-dialog title="请上传周报图片" :visible.sync="dialogUserVisible" >
		  <router-view name="AdminSeeUser"/>
		  <router-view name="AdminSeeUser1"/>
		</el-dialog>
		-->
	</div>
</template>

<script>
/*
import HelloWorld from "./HelloWorld.vue"
import banner from "./banner.vue"
* */
export default {
  name: 'AdminHeader',
  props: {
  },
	data() {
      return {
        activeIndex: '/AdminHomePage',
		//dialogUserVisible:false
      };
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
