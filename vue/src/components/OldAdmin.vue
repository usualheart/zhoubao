<template>
	<div >
		<div class="timeline">
		  <div v-bind:class="zhoubaoWeeks[0].class" v-for="zhoubaoWeeks in zhoubaoAll" v-bind:key="zhoubaoWeeks[0].id">
			<div class="content" style="border: 1px solid #E60000;">
			  <h2>第{{zhoubaoWeeks[0].week}}周</h2>
			  <p>{{zhoubaoWeeks[0].time.getFullYear()}}年{{zhoubaoWeeks[0].time.getMonth()+1}}月{{zhoubaoWeeks[0].time.getDate()}}日</p>
			  <div v-for="(zhoubao,index) in zhoubaoWeeks " style="display:inline-block;width:25%;text-align:center;" v-on:click="ShowModal(zhoubao)">
				  <img  v-bind:src="zhoubao.filePath.localeCompare('-1')==0?'../static/html5.png':'../static/pdf.png'"/><br/>
				  <a style="color:black;">{{zhoubao.userName}}</a><br/>
			  </div>
			</div>
		  </div>
		</div>
	</div>
</template>
<script>
	export default{
		name:'OldAdmin',
		data(){
			return{
				zhoubaoWeeks:[],
				zhoubaoAll:[],
				showModal:false,
				watchZhoubao:"",
				watchPath:"",
			}
		},
		mounted: function(){
			//刷新周报数据
			this.shuaxin();
		},
		 methods: {
			//ajax部分 从后台中提取数据
			shuaxin:function(){ //进行文件路径的获取
				var self = this;
				this.axios.get("/getAll/").then(function(response) {
					var tmps=[];
					tmps= response.data; //
					
					//把tmps按周划分为数组 装进zhoubaoAll数组
					var week=tmps[0].week;
					var j=0;
					for(var i=0;i<tmps.length;i++){
						if(tmps[i].week==week)
							self.zhoubaoWeeks.push(tmps[i]);
						else {
							//装入zhoubaoAll前在zhoubaoWeeks中设置相应的时间线类：左边或者右边
							if(j%2==0)self.zhoubaoWeeks[0].class="container left";
							else self.zhoubaoWeeks[0].class="container right";
							//对时间格式进行调整
							var datetime=new Date(self.zhoubaoWeeks[0].time);
							self.zhoubaoWeeks[0].time=datetime;
							//把zhoubaoWeeks装入zhoubaoAll数组
							self.zhoubaoAll.push(self.zhoubaoWeeks);
							j++;
							self.zhoubaoWeeks=[];
							week=tmps[i].week;
							self.zhoubaoWeeks.push(tmps[i]);
						}
						
					}
					//装入zhoubaoAll前在zhoubaoWeeks中设置相应的时间线类：左边或者右边
					if(j%2==0)self.zhoubaoWeeks[0]["class"]="container left";
					else self.zhoubaoWeeks[0]["class"]="container right";
					//对时间格式进行调整
					var datetime=new Date(self.zhoubaoWeeks[0].time);
					self.zhoubaoWeeks[0].time=datetime;
					self.zhoubaoAll.push(self.zhoubaoWeeks);
				})     
			},
			
		}
	}
</script>

<style scoped>
	* {
  box-sizing: border-box;
}

/* Set a background color */
body {
  //background-color: #474e5d;
  font-family: Helvetica, sans-serif;
}

/* The actual timeline (the vertical ruler) */
.timeline {
  position: relative;
  max-width: 1200px;
  margin: 0 auto;
}

/* The actual timeline (the vertical ruler) */
.timeline::after {
  content: '';
  position: absolute;
  width: 6px;
  background-color: white;
  top: 0;
  bottom: 0;
  left: 50%;
  margin-left: -3px;
}

/* Container around content */
.container {
  padding: 10px 40px;
  position: relative;
  background-color: inherit;
  width: 50%;
  color:black;
  
}

/* The circles on the timeline */
.container::after {
  content: '';
  position: absolute;
  width: 25px;
  height: 25px;
  right: -17px;
  background-color: white;
  border: 4px solid #FF9F55;
  top: 15px;
  border-radius: 50%;
  z-index: 1;
}

/* Place the container to the left */
.left {
  left: 0;
}

/* Place the container to the right */
.right {
  left: 50%;
}

/* Add arrows to the left container (pointing right) */
.left::before {
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  right: 30px;
  border: medium solid white;
  border-width: 10px 0 10px 10px;
  border-color: transparent transparent transparent white;
}

/* Add arrows to the right container (pointing left) */
.right::before {
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  left: 30px;
  border: medium solid white;
  border-width: 10px 10px 10px 0;
  border-color: transparent white transparent transparent;
}

/* Fix the circle for containers on the right side */
.right::after {
  left: -16px;
}

/* The actual content */
.content {
  padding: 20px 30px;
  background-color: white;
  position: relative;
  border-radius: 6px;
}

/* Media queries - Responsive timeline on screens less than 600px wide */
@media screen and (max-width: 600px) {
/* Place the timelime to the left */
  .timeline::after {
    left: 31px;
  }

/* Full-width containers */
  .container {
    width: 100%;
    padding-left: 70px;
    padding-right: 25px;
  }

/* Make sure that all arrows are pointing leftwards */
  .container::before {
    left: 60px;
    border: medium solid white;
    border-width: 10px 10px 10px 0;
    border-color: transparent white transparent transparent;
  }

/* Make sure all circles are at the same spot */
  .left::after, .right::after {
    left: 15px;
  }

/* Make all right containers behave like the left ones */
  .right {
    left: 0%;
  }
}
</style>
