<template>
		<div>
			<!--api-->
		<el-upload 
		
		  :action="isPro? 'img/uploadImg/'+userName:'/api/zhouBaoSpring/img/uploadImg/'+userName"
		  list-type="picture-card"
		  :on-preview="handlePictureCardPreview"
		  :on-remove="handleRemove"
		  :on-success="handleSuccess"
			name="imgMultipartFile"
		  :multiple="false">
		  <i class="el-icon-plus"></i>
		</el-upload>
		<el-dialog :visible.sync="dialogVisible">
		  <img width="100%" :src="dialogImageUrl" alt="">
		</el-dialog>
		</div>
	
</template>

<script>
	 export default {
		 name:'UploadImg',
		data() {
		  return {
			//fileList: [{name: 'food.jpeg', url: 'http://127.0.0.1/img/666/Img43116218%E5%AE%98%E6%96%B9spring%20%E6%A1%86%E6%9E%B6%E6%96%87%E6%A1%A3.jpg'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
			fileList:[],
			dialogImageUrl: '',
			dialogVisible: false,
			
			//用于区分开发和生产环境，自动解决跨域问题
			isPro: Object.is(process.env.NODE_ENV, 'production')
		  };
		},
		
		props:{
			userName:String,
			callback:''
		},
		methods: {
			handleSuccess(response){
				this.$message({
				  message: '恭喜你，图片上传成功！',
				  type: 'success'
				});
				if(this.callback)this.callback(response);
				//console.log(response);
				//console.log(this.userName);
				//console.log(this.callback);
			},
			 handleRemove(file, fileList) {
				console.log(file, fileList);
			  },
			  handlePictureCardPreview(file) {
				this.dialogImageUrl = file.url;
				this.dialogVisible = true;
				//alert(file.url);
			  }
			}
	  }
</script>

<style>
</style>
