<template>
	<div style="margin-top:10rpx;">
		<van-panel use-footer-slot >
			<view slot="header">
				<!--使用header slot进行自定义-->
				<van-row>
					<van-col span="12" offset="1">
						<image :src="avatarUrl" style="width: 70rpx;height:70rpx;margin: 7rpx;"></image> <text style="display: inline-block;bottom: 25rpx;position: relative;">{{userName}}</text>

					</van-col>
					<van-col span="7" offset="4">
						<text style="font-size: small;color:lightgray;display:inline-block;top:16rpx;position: relative;">{{timeFormat}}</text>
					</van-col>
				</van-row>
			</view>
			<view slot="footer">
				<!--使用footer slot进行自定义-->
				<van-row gutter="20">
					<van-col span="12" offset="3">
						<image v-for="(dianzan,index) in dianzans" :key="index" :src="dianzan.avatarUrl" style="width: 50rpx;height:50rpx;"></image>
					</van-col>
					<van-col span="3" offset="18" @click="dianzan">
						<van-icon :name="state ? 'like' :'like-o'"  :color="state ? '#f47983':'grey'" size="1.2em"/>
					</van-col>
					
					
					<!--
					<van-col span="3">
						<van-icon name="chat-o" />
					</van-col>
					* 
					* 
					* -->
				</van-row>
			</view>
			<van-row>
				<van-col span="17" offset="4">
					<view style="margin:2% 0%;">{{content}}</view>
				</van-col>
			</van-row>

		</van-panel>
	</div>
</template>

<script>
	export default {
		name: 'LoveItem',
		data() {
			return {
				
				dianzans: '',
				state: false,//用来表示是否已经点过赞
			}
		},
		props: {
			//biaobai: '',
			id: '',
			userName: '',
			avatarUrl: '',
			content: '',
			create_time: '',
		},
		computed: {
			timeFormat: function() {
				//将过去的时间表示为 易于看懂的格式
				var create_time = new Date(this.create_time);
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
		},
		watch:{
			dianzans:function(){
				this.state=this.dianzanState();
			},
		},
		mounted: function() {
			this.getDianzansById();
		},
		methods: {
			//进行点赞
			dianzan: function() {
				if (this.state){
					wx.showToast({
						title: '已经点过了',
						icon: 'success'
					});
					return;
				}
				var self = this;
				this.qcloud.request({
					url:this.config.service.host+'/weapp/dianzan/'+ self.id,
					//url: 'https://weapp.yuyongbo.cn/weapp/dianzan/' + self.id,
					method: 'POST',
					success: function(response) {
						//showSuccess(response.data[0]);
						console.log(response);
						//this.getDianzan();
						wx.showToast({
							title: '点赞成功',
							icon: 'success'
						});
						self.state=true;
						self.getDianzansById();
					},
					fail: function(err) {
						console.log(err);
					}
				});
			},
			//async 表示是异步函数 //获取所有点赞数据
			getDianzansById: function() {
				var self = this;
			
				this.qcloud.request({
					url:this.config.service.host+'/weapp/dianzan/'+ self.id,
					//url: 'https://weapp.yuyongbo.cn/weapp/dianzan/' + this.id,
					success: function(response) {
						self.dianzans = response.data.data;
						
					},
					fail: function(err) {
						console.log(err);
					}
				});
			},
			dianzanState: function() {
				
				if (null ==this.dianzans)return;
				
				const session = this.qcloud.Session.get();
				//如果用户已经登录
				if(session){
					
					var openId=session.userinfo.openId;
					//wx.showToast({title: openId+'状态检查',icon: 'success'});
					if (null!=openId) { //刚开始的时候globalData可能还是null（没有授权）
						for (var i = 0; i < this.dianzans.length; i++) {
							if (this.dianzans[i].openId.localeCompare(openId) == 0) {
								return true;
							}
						}
					}
				}
				
				return false;
			},
		}

	}
</script>

<style>

</style>
