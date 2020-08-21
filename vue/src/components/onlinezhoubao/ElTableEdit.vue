<template>
  <!--一周表格编辑部分-->
  <div>
	<table >
		<tr>
			<th v-on:click="editDone" style="color:steelblue;padding:1%;">保存表格</th>
			<th>上午</th>
			<th>下午</th>
			<th>晚上</th>
		</tr>
		<tr v-for="(item,index) in sectionitems">
			<th>{{weekName[index]}}</th>
			<td v-for="section in item" v-bind:class="section.class"  v-on:click.self="editCell(section)">
				<textarea v-model="section.head" style="width:80%;height:70%;font-size:1.3em;"></textarea>
				<select v-model="select2" @change='editDetailId(section)'>
					<!--提供一个禁用的选项来解决只有一个选项的问题-->
					<option disabled value="">请选择</option>
					<!--值绑定 绑定在非字符串或布尔值上 需要用v-bind-->
					<option  v-for="(detail,index) in details" v-bind:value="[detail,index]">练习{{index+1}} {{detail.head}}</option>
				</select>	
			</td>
		</tr>
	</table>
	
	<el-calendar :range="['2019-05-27', '2019-06-02']">
	  <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
	  <template slot="dateCell" slot-scope="{date, data}">
	  		<p :class="data.isSelected ? 'is-selected' : ''">
	  		  {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
			  
	  		</p>
			<div>
				<tr>
					<td v-if="'0' ? 'true':'false'" >上午</td>
					<td v-bind:class="sectionitems[weekNo(date)][0].class"  v-on:click.self="editCell(sectionitems[weekNo(date)][0])">
						<textarea v-model="sectionitems[weekNo(date)][0].head" style="width:80%;height:70%;font-size:1.3em;"></textarea>
						<select v-model="select2" @change='editDetailId(sectionitems[weekNo(date)][0])'>
							<!--提供一个禁用的选项来解决只有一个选项的问题-->
							<option disabled value="">请选择</option>
							<!--值绑定 绑定在非字符串或布尔值上 需要用v-bind-->
							<option  v-for="(detail,index) in details" v-bind:value="[detail,index]">练习{{index+1}} {{detail.head}}</option>
						</select>	
					</td>
				</tr>
				<tr>
					<td>下午</td>
					<td v-bind:class="sectionitems[weekNo(date)][1].class"  v-on:click.self="editCell(sectionitems[weekNo(date)][1])">
						<textarea v-model="sectionitems[weekNo(date)][1].head" style="width:80%;height:70%;font-size:1.3em;"></textarea>
						<select v-model="select2" @change='editDetailId(sectionitems[weekNo(date)][1])'>
							<!--提供一个禁用的选项来解决只有一个选项的问题-->
							<option disabled value="">请选择</option>
							<!--值绑定 绑定在非字符串或布尔值上 需要用v-bind-->
							<option  v-for="(detail,index) in details" v-bind:value="[detail,index]">练习{{index+1}} {{detail.head}}</option>
						</select>	
					</td>
				</tr>
				<tr>
					<td>晚上</td>
					<td v-bind:class="sectionitems[weekNo(date)][2].class"  v-on:click.self="editCell(sectionitems[weekNo(date)][2])">
						<textarea v-model="sectionitems[weekNo(date)][2].head" style="width:80%;height:70%;font-size:1.3em;"></textarea>
						<select v-model="select2" @change='editDetailId(sectionitems[weekNo(date)][2])'>
							<!--提供一个禁用的选项来解决只有一个选项的问题-->
							<option disabled value="">请选择</option>
							<!--值绑定 绑定在非字符串或布尔值上 需要用v-bind-->
							<option  v-for="(detail,index) in details" v-bind:value="[detail,index]">练习{{index+1}} {{detail.head}}</option>
						</select>	
					</td>
				</tr>
			</div>
	  </template>
	</el-calendar>
	</div>
</template>

<script>
export default {
  name: 'TableEdit',
  data () {
    return {
				ids:[],//用于存储插入数据库后返回的id
				weekName:["周一","周二","周三","周四","周五","周六","周日"],
				select2:"",
				sectionAll:"",
				userName:[],
				sectionitems:[[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],
				[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],
				[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}],
				[{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""},
				{id:"-1",userName:"",no:"0",head:"上课",class:"other",detailid:"0",time:"",week:""}]],//json数组 用于存储每周概览表的数据
    }
  },
	props:{
		details:Array,
	},
	computed:{
		
	},
	mounted:function(){
			this.userName=this.Cookies.get('userName');
			this.getSection();
	},
	methods:{
		weekNo:function(date){
			//alert("123");
			//var date=new Date();
			return (date.getDay()+6)%7;
			//return 1+date;
		},
		getSection:function(){ //进行图片路径的获取
				var self = this;
				//ajax部分 
				//从后台中提取本周表格数据 -1在后端会被识别为本周
				this.axios.get("/getSection/"+self.userName+"/"+"-1").then(function(response) {
					if(response.data.length!=0){//当数据库中有数据的时候才对前端进行
						self.sectionAll=response.data; 
					//	alert(self.sectionAll[0].head);
						//把sectionALL中数据存储到sectionitems中
						var m=0;
						for(var i=0;i<self.sectionitems.length;i++){
							for(var j=0;j<self.sectionitems[i].length;j++){
								//解决数组变动检测的两种方法
								//方法一：Vue.set
								//Vue.set(self.sectionitems[i], j, self.sectionAll[m++]);//zheli bunneg shiyong 
								// 方法二：Array.prototype.splice
								self.sectionitems[i].splice(j, 1, self.sectionAll[m++]);
								//self.sectionitems[i][j]=self.sectionAll[m++];//Vue不能检测这种数组的变动 会影响列表渲染的刷新！
							}
						}
					}	
				}); 
			},
		editDone:function(){
				if(this.sectionitems[0][0].id=="-1"){
					//对表格的数据进行相应的完善 并添加到sectionALL中
					this.sectionAll=[];
					var k=0;
					for(var i=0;i<this.sectionitems.length;i++){
						for(var j=0;j<this.sectionitems[i].length;j++){
							this.sectionitems[i][j].userName=this.userName;
							this.sectionitems[i][j].no=k++;//记录no 作为排序的依据
							this.sectionAll.push(this.sectionitems[i][j]);
						}
					}
					//alert(JSON.stringify(this.sectionAll));
					//将sectionAll中的数据插入到数据库中 并把数据库中自动生成的id更新到前端数据中
					var self = this;
					this.axios({
						url: '/saveSection/',
						method:'post',
						 //发送格式为json
						data:JSON.stringify(self.sectionAll),
						headers:{
								'Content-Type': 'application/json'
						}
					}).then(function(response) {
						self.ids= response.data; //此时还是字符串
						//alert(self.ids[0]);
						//获取到的id更新到sectionitems数组
						var l=0;
						for(var i=0;i<self.sectionitems.length;i++){
							for(var j=0;j<self.sectionitems[i].length;j++){
								self.sectionitems[i][j].id=self.ids[l++];
								
							}
						}
						
					});
				}else{
					//需要将表格数据重新装入到sectionAll
					this.sectionAll=[];
					for(var i=0;i<this.sectionitems.length;i++){
						for(var j=0;j<this.sectionitems[i].length;j++){
							this.sectionAll.push(this.sectionitems[i][j]);
						}
					}
					//将sectionAll中的数据更新到数据库中
					var self = this;
					this.axios({
						url: '/updateSection/',
						method:'post',
						 //发送格式为json
						data:JSON.stringify(self.sectionAll),
						headers:{
								'Content-Type': 'application/json'
						}
					});
				}
				alert("表格已保存")
			},
			editCell:function(section){
				//alert(section.head);
				//在文字和其他的类之间进行切换 练习不会被切换为文字或者其它
				var str=section.class;
				if(section.detailid==0){
					if(str.localeCompare("other")==0)
						section.class="wenzi";
					else if(str.localeCompare("wenzi")==0)
						section.class="other";
					else if(str.localeCompare("lianxi")==0)
						section.class="wenzi";
				}else{
					if(str.localeCompare("other")==0)
						section.class="lianxi";
					else if(str.localeCompare("wenzi")==0)
						section.class="lianxi";
				}
			},
			editDetailId:function(section){
				//alert(this.select2[1]);
				section.detailid=this.select2[0].id;
				section.head="练习"+(this.select2[1]+1)+" "+this.select2[0].head;
				//section.head="练习"+(section.detailid)+this.details[section.detailid-1].head;
				section.class="lianxi";
				this.select2="";
				//alert(this.select2);
			},
	}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	table{
	width:90%;
	margin:auto;
	border-collapse:collapse;
	}
	tr{
	border-bottom:solid lightgrey 1px;
	}
	td{
	height:35px;
	text-align:center;
	max-width:40%;
	border:none;
	}
	th{
	width:1%;
	}
	input[type="text"]{border:none;}

	td.lianxi a{text-decoration:none;color:white;}
	td.lianxi{background-color:steelblue;}

	td.wenzi{background-color:#E8E8E8;}
	td.wenzi a{text-decoration:none;color:black;}

	td.other{background-color:white;}
	td.other a{text-decoration:none;color:grey;}
	td.other textarea{color:lightgrey;}
	
	.is-selected {
    color: #1989FA;
  }
	/deep/ .el-calendar-day{
	  height: 100%;
  }
</style>
