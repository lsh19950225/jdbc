package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author User1
 * [jdbc] emp 테이블의 모든 사원 정보 조회
 */
public class Ex01_03 {

	public static void main(String[] args) {

		// 1. jdbc 드라이버 로딩 - Class.forName()
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = " SELECT * "
				+ " FROM emp ";
		int empno;

		int mgr;
		String ename;
		String job;
		// String hiredate; // 가능
		// Date hiredate; // 가능
		LocalDateTime hiredate; // 가능, 가공안해도됨 가장 좋음.
		double sal;
		double comm;
		int deptno;

		try {
			Class.forName(className);
			// 2. Connection 객체 - DriverManager
			conn = DriverManager.getConnection(url, user, password);
			// 3. 작업(CRUD) - Statement 객체
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) { // rs.next() : boolean
				empno = rs.getInt("empno"); // 1도 가능
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime(); // %s / %tF
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");

				// Date : %s
				// 널 처리 하지 않았는데 0 나옴 // 널이 나왔으면 좋겠다 : 과제
				System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n"
						,empno,ename,job,mgr,hiredate,sal,comm,deptno);
			} // while

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. Connection 객체 닫기 - close()
			try {
				rs.close(); // 닫는 순서도 지켜주기
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	} // main

} // class
