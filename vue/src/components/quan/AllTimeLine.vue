<template>
	<div class="block" v-loading="false">
		<el-timeline style="width: 70%;margin: auto;">
			<el-timeline-item v-for="(detail,index) in details" v-bind:key="detail.id" timestamp="" placement="top">
				<AllDetail  v-bind:detail="detail" v-bind:index="index+1"></AllDetail>
				
			</el-timeline-item>
		</el-timeline>
	</div>
</template>
<script>
	import AllDetail from '../quan/AllDetail'
	import global_ from '../Global'
	export default {
		name: 'AllTimeLine',
		data() {
			return {
				loading:true,details:'',
			}
		},
		mounted: function() {
			//this.weekNow = this.$data;
			//alert(JSON.stringify(this.$root));
			//从Cookies中获取用户名
			this.userName = this.Cookies.get('userName');
			//刷新周报数据
			this.getDetail();

		},
		components: {
			AllDetail
		},
		methods: {
			getDetail: function() { //进行图片路径的获取
				var self = this;
				//ajax部分
				//alert("detail")
				//从后台中提取本周(周二到下周周一之间)的周报片段(只要上传图片一定会有)
				this.axios.get("/htmlZhoubao/getAllDetail/" + self.userName + "/" + new Date(0)).then(function(response) {
			
					//只有数据库中有周报 才对数据进行更新
					if (response.data.length != 0)
						self.details = response.data;
					//else
					//说明没有图片记录首次进入提醒上传图片
					//self.showUpImg=true;
			
					//alert(self.details[0]);
				});
			},
			timeFormat: function(time) {
				//将过去的时间表示为 易于看懂的格式
				var create_time = new Date(time);
				var now = new Date();
				var ans = create_time.getHours() + ":" + create_time.getMinutes();
				if (create_time.toLocaleDateString().localeCompare(now.toLocaleDateString()) == 0) return ans;
				else {
					var now0 = new Date(now.toLocaleDateString());
					var riqicha = (now0.getTime() - create_time.getTime()) / 1000 / 60 / 60 / 24;
					if (riqicha < 1) ans = '昨天 ' + ans;
					else if (riqicha < 2) ans = '前天 ' + ans;
					else ans = (create_time.getMonth() + 1) + "-" + (create_time.getDate()) + " " + ans;
					if (create_time.getFullYear() != now.getFullYear()) ans = (create_time.getFullYear()) + "-" + ans;
					return ans;
				}
			},
		}
	}
</script>

<style scoped>
	.boxFinding {
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;
		align-items: center;
		margin: 4%;
	}
</style>
