package zhoubao.data;

public class SpitterNotFoundException extends RuntimeException {
	private String spitterUserName;
	public  SpitterNotFoundException(String spitterUserName) {
		this.spitterUserName=spitterUserName;
	}
	public String getSpitterUserName() {
		return spitterUserName;
	}
}
