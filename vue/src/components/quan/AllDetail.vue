<template>

	<el-card shadow="hover">
		<el-row :gutter="20" type="flex" justify="center">
			<el-col :span="10">
				<div style="cursor:pointer;">
					<h2 style="font-weight:600;">{{detail.userName}}</h2><!-- 
			<h3>练习{{index}} {{detail.head}}</h3> -->
					<h3 style="font-weight:500;color: gray;"> {{timeFormat(detail.time)}}</h3>
				</div>
			</el-col>
			<el-col :span="6">
			<h3>{{detail.head}}</h3>
			</el-col>
			
		</el-row>
		<el-row :gutter="20" type="flex" justify="center">
			<el-col :span="16">
				<p>{{detail.content}}</p>
			</el-col>
		</el-row>
		<el-row :gutter="20" type="flex" justify="center">
			<el-col :span="16">
				<img v-bind:src="isPro?detail.imgSrc:'api/zhouBaoSpring/'+detail.imgSrc" />
			</el-col>
		</el-row>

	</el-card>
</template>

<script>
	export default {
		name: 'AllDetail',
		data() {
			return {
				//用于区分开发和生产环境，自动解决跨域问题
				isPro: Object.is(process.env.NODE_ENV, 'production')
			}
		},
		props: {
			detail: Object,
			index: '',
		},
		methods: {
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	img {
		max-width: 100%;
		max-height: 150px;
	}

	div.Detail {
		z-index: 100;
		top: 1%;
		background-color: white;
		width: 90%;
		margin: auto;
	}

	.boxFinding {
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;
		align-items: center;
		margin: 4%;
	}
</style>
