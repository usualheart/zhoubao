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

import com.fasterxml.jackson.annotation.JsonProperty;

import zhoubao.controller.WeekNo;

@Entity
@Table(name = "sectionitem")
public class SectionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 2, max = 16)
	@Column(name = "userName")
	private String userName;

	@Column(name = "no")
	private Integer no;
	@Column(name = "head")
	private String head;
	
	@JsonProperty(value="class")
	@Column(name = "class")
	private String cssClass;

	@Column(name = "detailid")
	private Integer detailid;

	@Column(name = "time")
	private Date time;

	@Column(name = "week")
	private Integer week;

	public SectionItem(Integer id, String userName, Integer no, String head, String cssClass, Integer detailid,
			Date time, Integer week) {
		super();
		this.id = id;
		this.userName = userName;
		this.no = no;
		this.head = head;
		this.cssClass = cssClass;
		this.detailid = detailid;
		this.time = time;
		this.week = week;
	}

	public SectionItem() {
	}

	public SectionItem(String userName, Integer no, String head, String cssClass, Integer detailid) {
		super();
		this.userName = userName;
		this.no = no;
		this.head = head;
		this.cssClass = cssClass;
		this.detailid = detailid;
		this.time = new Date();
		this.week = WeekNo.getWeekCount();
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

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Integer getDetailid() {
		return detailid;
	}

	public void setDetailid(Integer detailid) {
		this.detailid = detailid;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cssClass == null) ? 0 : cssClass.hashCode());
		result = prime * result + ((detailid == null) ? 0 : detailid.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((no == null) ? 0 : no.hashCode());
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
		SectionItem other = (SectionItem) obj;
		if (cssClass == null) {
			if (other.cssClass != null)
				return false;
		} else if (!cssClass.equals(other.cssClass))
			return false;
		if (detailid == null) {
			if (other.detailid != null)
				return false;
		} else if (!detailid.equals(other.detailid))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (no == null) {
			if (other.no != null)
				return false;
		} else if (!no.equals(other.no))
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
		return "SectionItem [id=" + id + ", userName=" + userName + ", no=" + no + ", head=" + head + ", cssClass="
				+ cssClass + ", detailid=" + detailid + ", time=" + time + ", week=" + week + "]";
	}
	
	

}
