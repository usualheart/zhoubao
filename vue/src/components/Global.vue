
<script>
	//计算日期是本学期开学第几周
	function getWeekCount(insertDate){
	  // insertDate.setFullYear(2019,9,2);//介于 0 ~ 11 之间。用本地时间表示。相当于设置为2月26
	  // insertDate.setHours(23,59,59,0);
	  //alert(insertDate);
	 // var monthBefore=insertDate.getMonth()-1;
	//  insertDate.setMonth(1);
	   //设置开学日期，用于计算第几周
	   var OneDay=new Date();
	   var TwoDay=new Date();
	   //根据时间设置开学时间
	   OneDay.setFullYear(2019,1,26);//介于 0 ~ 11 之间。用本地时间表示。相当于设置为2月26
	   OneDay.setHours(0,0,0,0);
	   TwoDay.setFullYear(2019,7,27);//介于 0 ~ 11 之间。用本地时间表示。相当于设置为2月26
	   TwoDay.setHours(0,0,0,0);
	   
	   if(insertDate.getTime()>TwoDay.getTime()) {var startDay=TwoDay;}
	   else {var startDay=OneDay;
	   }
	   //时间差 计算相当于第几周
	   var ms=insertDate.getTime()-startDay.getTime();
	   var x=1000*60*60*24;
	   var weekCount=parseInt(ms/x/7)+1;//计算第几周
	   return weekCount;
	}
export default
{
  getWeekCount
}
</script>

