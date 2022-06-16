package jspstudy.dbconn;


import java.sql.Connection;

public class Dbconn {

	//private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String url = "jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";
	//private String user = "system";
	private String user = "root";
	private String password = "1234";

	public Connection getConnection() {
		Connection conn = null;
		
		try {

			// ����� ����̹� �߿� ��� ������ Ŭ���� ã�Ƽ� ����
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			// ���� ������ ���ؼ� ���� ��ü�� �������� conn�� ��´�.
			conn = java.sql.DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}