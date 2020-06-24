package zhoubao.data;

//���ִ������Ҳ�����Ӧ���û� �������һ��
public class MyError {
	private int code;
	private String message;

	public MyError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
