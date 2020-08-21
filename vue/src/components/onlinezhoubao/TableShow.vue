<template>
	<!--一周表格展示部分-->
	<table v-if="showTable">
		<tr>
			<th></th>
			<th>上午</th>
			<th>下午</th>
			<th>晚上</th>
		</tr>
		<tr v-for="(item,index) in sectionitems">
			<th>{{weekName[index]}}</th>
			<td v-for="section in item" v-bind:class="section.class" >
			<a v-bind:href="'#'+section.detailid">{{section.head}}</a>
			</td>
		</tr>
	</table>
</template>

<script>
	export default{
	  name:'TableShow',
	  data() {
	    return {
	      currentDate: new Date(),
		  weekName:["周一","周二","周三","周四","周五","周六","周日"],
		  showTable:true,
	    };
	  },
	  props:{
		  sectionitems:Array,
	  },
	  methods:{
	  }
	}
</script>

<style scoped>
	table{
	width:90%;
	margin:auto;
	border-collapse:collapse;
	font-size:1em;
	}
	tr{
	border-bottom:solid lightgrey 1px;
	}
	td{
	height:55px;
	text-align:center;
	max-width:30%;
	border:solid lightgrey 1px;
	}
	td.lianxi a{text-decoration:none;color:white;}
	td.lianxi{background-color:#409EFF;}

	td.wenzi{background-color:#E8E8E8;}
	td.wenzi a{text-decoration:none;color:black;}

	td.other{background-color:white;}
	td.other a{text-decoration:none;color:grey;}
	td.other textarea{color:lightgrey;}
</style>
