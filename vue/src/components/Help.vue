<template>
	<div>
		<el-collapse v-model="activeNames" >
			<el-collapse-item title="概述" name="1">
				<div>
					<h3>周报上传时间说明</h3>
					<p> 每周周报 须在<strong>该周周二1点之后至次周周一23点之前</strong>这个时间段进行提交</p>
					<p>超出时间段周报会计为下一周</p>
					<p>请<strong>避免在边界时间</strong>（比如周二凌晨0点左右和下周一晚上23点左右）上传周报</p>
					<h3>可选两种周报</h3>
					<ul>
						<li>
							<h3>上传pdf周报</h3>
							<p>将写好的周报文件转为pdf进行上传</p>
						</li>
						<li>
							<h3>在线写周报</h3>
							<p>使用网站提供的“在线写周报功能”编写当周周报</p>
						</li>
					</ul>
					<h3>遇到问题请联系管理员,微信：yyb663</h3>
				</div>
			</el-collapse-item>
			<el-collapse-item title="上传pdf周报" name="2">
				<div>
					<h3>每周只能上传一个pdf文件</h3>
				</div>
				<div>只需要在上传周报界面选择pdf上传即可</div>
				<div>目前暂不支持其它格式的文件上传 对周报的删除操作会在后续进行更新</div>
			</el-collapse-item>
			<el-collapse-item title="在线写周报" name="3">
				<div>
					<h3>步骤 1 上传图片</h3>
					<p>上传周报图片每张图片可代表一个练习 上传后可进行编辑 (不需要图片可省略)</p>
					<h3>步骤 2 填写表格 和图片详情</h3>
					<p>填写周报表格,可根据重要性单击单元格切换样式,如果第一步已上传图片可选择将对应练习关联起来</p>
					<h3>步骤 3 填写总结 保存周报</h3>
					<p>总结填写前,周报不会发送到老师端,但依然可以保存,一般在每周末填写总结 保存后周报会同步到老师端</p>

				</div>
			</el-collapse-item>
		</el-collapse>
	</div>
</template>

<script>
	export default {
		name: 'Help',
		data() {
			return {
				activeNames: ['1']
			}
		},
	}
</script>

<style scoped>
	div {
		width: 96%;
		margin: auto;
	}
</style>
