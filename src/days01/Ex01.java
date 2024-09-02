package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

public class Ex01 {

	public static void main(String[] args) {
		
		System.out.println("Hello World");
		
		// oracle.jdbc.driver.OracleDriver;
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // type4 : 가장 많이 쓰인다.
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD작업
			System.out.println(conn);
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}

	}

}
