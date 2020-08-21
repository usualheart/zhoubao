<template>
	<div style="height: 300px;">
	  <el-steps align-center :active="active" finish-status="success">
		<el-step title="步骤 1 上传图片"  description="上传周报图片每张图片可代表一个练习 上传后可进行编辑 (不需要图片可省略)"></el-step>
		<el-step title="步骤 2 填写表格 和图片详情" description="填写周报表格,可根据重要性单击单元格切换样式,如果第一步已上传图片可选择将对应练习关联起来"></el-step>
		<el-step title="步骤 3 填写总结 保存周报" description="总结填写前,周报不会发送到老师端,但依然可以保存,一般在每周末填写总结 保存后周报会同步到老师端 "></el-step>
	  </el-steps>
	  <el-button style="width:6.25rem; margin:5% 44%;" @click="next">下一步</el-button>
	</div>
</template>

<script>
	export default{
		name:ZhoubaoStep,
		data(){
			return{
				active:0
			}
		},
		methods:{
			next: function (){
							if (this.active++ > 2) this.active = 0;
			 }
		}
	}
</script>

<style>
</style>
