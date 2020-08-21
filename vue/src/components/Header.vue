<template>
	<div class="Header" style="width:98%;margin:auto;">
		<el-row :gutter="20" type="flex" justify="center" style="border-bottom: solid 1px #e6e6e6;">
			<el-col :span="21">
				<!--el-header-->
				<!-- 即便 `true` 是静态的，我们仍然需要 `v-bind` 来告诉 Vue这是一个 JavaScript 表达式而不是一个字符串。-->
				<el-menu v-if="!teacher" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="true" @select="handleSelect">
					<el-menu-item index="/HomePage"><i class="el-icon-s-home"></i>{{userName}}的周报</el-menu-item>
					<el-menu-item index="/SeeAllZhoubao"><i class="el-icon-data-analysis"></i>周报阅览</el-menu-item>
					
					<el-menu-item index="/makeMarkdown"><i class="el-icon-magic-stick
				"></i>Markdown周报</el-menu-item>
					<el-menu-item index="/HtmlEdit"><i class="el-icon-edit"></i>表格动图周报</el-menu-item>
					<el-menu-item index="/UploadPdf"><i class="el-icon-upload2"></i>上传周报</el-menu-item>
<!-- 
					<el-menu-item index="/AllTimeLine"><i class="el-icon-question"></i>All片段</el-menu-item> -->
					<el-menu-item index="/Help"><i class="el-icon-question"></i>帮助</el-menu-item>
					<!--<el-menu-item index="3"><a href="https://www.ele.me" target="_blank">博客管理</a></el-menu-item>-->
				</el-menu>
				
				<el-menu v-if="teacher" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="true" @select="handleSelect" >
					<el-menu-item index="/AdminHomePage"><i class="el-icon-s-home"></i>周报阅览</el-menu-item><!-- 
					<el-menu-item index="/AdminHomePage" ><i class="el-icon-upload2"></i>体验上传系统</el-menu-item> -->
					<!--<el-menu-item index="3"><a href="https://www.ele.me" target="_blank">博客管理</a></el-menu-item>-->
				</el-menu>
			</el-col>
		</el-row>

	</div>
</template>

<script>
	/*
import HelloWorld from "./HelloWorld.vue"
import banner from "./banner.vue"
* */
	export default {
		name: 'Header',
		props: {
			// msg: String
			//activeIndex:String
		},
		data() {
			return {
				activeIndex: '/HomePage',
				userName: '',
				teacher:false
			};
		},

		mounted: function() {
			this.userName = this.Cookies.get('userName');
			this.teacher=this.userName.localeCompare("123")==0;

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
