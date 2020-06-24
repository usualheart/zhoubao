package zhoubao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "zhoubao")
public class ZhouBao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 2, max = 16)
	@Column(name = "userName")
	private String userName;

	@Column(name = "filePath")
	private String filePath;

	@Column(name = "time")
	private Date time;

	@Column(name = "week")
	private Integer week;

	@Column(name = "pingjia")
	private String pingjia;

	public ZhouBao() {

	}
	
	public ZhouBao(String userName, String filePath, Date time, Integer week) {
		super();
		this.id=null;
		this.userName = userName;
		this.filePath = filePath;
		this.time = time;
		this.week = week;
		
	}

	public ZhouBao(Integer id, String userName, String filePath, Date time, Integer week, String pingjia) {
		super();
		this.id = id;
		this.userName = userName;
		this.filePath = filePath;
		this.time = time;
		this.week = week;
		this.pingjia = pingjia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + id;
		result = prime * result + ((pingjia == null) ? 0 : pingjia.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((week == null) ? 0 : week.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZhouBao other = (ZhouBao) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (id != other.id)
			return false;
		if (pingjia == null) {
			if (other.pingjia != null)
				return false;
		} else if (!pingjia.equals(other.pingjia))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (week == null) {
			if (other.week != null)
				return false;
		} else if (!week.equals(other.week))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZhouBao [id=" + id + ", userName=" + userName + ", filePath=" + filePath + ", time=" + time + ", week="
				+ week + ", pingjia=" + pingjia + "]";
	}

}
