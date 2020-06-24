package zhoubao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekNo {
	//计算当前日期是本学期开学第几周
	public static int getWeekCount() {
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDay=new Date();
		try {
			startDay = sdf.parse("2019/02/26 00:00:00");
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		long betweenMs=now.getTime()-startDay.getTime();
		int weekCount=(int) (betweenMs/(1000*60*60*24*7))+1;
		
		return weekCount;
	}
	/*测试
	 * public static void main(String []args) { System.out.println(getWeekCount());
	 * }
	 */
}
