<template>
	<div>
		<el-row :gutter="20" type="flex" justify="center">
			<el-col :span="18">
				<!--api-->
				<el-upload class="upload-demo" name="pdfMdMultipartFile" :drag="true" :action="isPro?'/zhoubao/uploadPdfMd/'+userName:'/api/zhouBaoSpring/zhoubao/uploadPdfMd/'+userName"
				 :multiple="false" accept=".md,application/pdf" :on-success="success" :before-upload="handleBeforeUpload">

					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip"><i class="el-icon-warning-outline"></i> 支持文件：Markdown(*.md)或pdf(*.pdf) <img
						 src="static/markdown80.png" style="width:50px;" />
						<img src="static/pdf.png" style="width:50px;" /></div>
				</el-upload>
			</el-col>
		</el-row>


	</div>
</template>

<script>
	export default {
		name: 'UploadPdf',
		data() {
			return {
				userName: '',

				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production')
			}
		},
		mounted: function() {
			this.userName = this.Cookies.get('userName');
		},
		methods: {
			success: function() {
				this.$notify({
					title: '周报上传成功！',
					type: 'success'
				});
				this.$router.push('HomePage');
			},
			handleBeforeUpload: function(file) {
				//alert(file.name);
				var fileName = file.name;
				var shuzu = fileName.split(".");
				var extName = shuzu[shuzu.length - 1].toLowerCase();
				//alert(extName);
				if (extName.localeCompare("pdf") != 0 && extName.localeCompare("md") != 0) {
					this.$message({
						message: '上传失败，请上传pdf或Markdow(*.md)文件！',
						type: 'error'
					});
					return false;

				} else {
					return true;
				}
			}
		}
	}
</script>

<style scoped>
	/deep/ .el-upload-dragger {
		display: block;
		height: 15.25rem;
		width: 50rem;
		margin: auto;

	}

	/deep/ .el-upload__tip {
		width: 100%;
		font-size: 1.125rem;
	}
</style>
