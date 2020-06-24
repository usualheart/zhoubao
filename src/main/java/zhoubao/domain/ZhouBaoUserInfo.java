package zhoubao.domain;

import java.io.Serializable;
import java.util.Date;


public class ZhouBaoUserInfo implements Serializable {
	private Integer id;
	private String userName;
	private String filePath;
	private Date time;
	private Integer week;
	private String pingjia;
	private Integer type;
	private Integer year;

	public ZhouBaoUserInfo() {

	}
	public ZhouBaoUserInfo(Integer id,Integer i1,Integer i2,Integer i3,Integer i4,Integer i5,Integer i6,Integer i7) {
this.id=id;
	}
	public ZhouBaoUserInfo(Integer id, String userName, String filePath, Date time, Integer week, String pingjia,
			Integer type, Integer year) {
		super();
		this.id = id;
		this.userName = userName;
		this.filePath = filePath;
		this.time = time;
		this.week = week;
		this.pingjia = pingjia;
		this.type = type;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getPingjia() {
		return pingjia;
	}

	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "ZhouBaoUserInfo [id=" + id + ", userName=" + userName + ", filePath=" + filePath + ", time=" + time
				+ ", week=" + week + ", pingjia=" + pingjia + ", type=" + type + ", year=" + year + "]";
	}

	
}