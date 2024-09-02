package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleDriver;

public class Ex02 {

	public static void main(String[] args) {
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // type4 : 가장 많이 쓰인다.
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null; // 일처리하는 배달의 민족.
		ResultSet rs = null; // 결과물
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD작업
			
			// select : query
			// insert, update, delete : update
			
			String sql = " SELECT * "
						+ "FROM dept";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select
			
			int deptno;
			String dname, loc;
			
			while (rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n",deptno,dname,loc);
			}
			// System.out.println(conn);
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
