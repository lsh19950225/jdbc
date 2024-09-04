package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Ex07_03 {

	public static void main(String[] args) {
		
		// up_insertdept
		// 새로운 부서 추가 1조
		Scanner scanner = new Scanner(System.in);
		System.out.println("부서번호,부서명,지역명 입력 ? ");
		int deptno = scanner.nextInt();
		String dname = scanner.next();
		String loc = scanner.next();
		
		String sql = "{call up_insertdept(?,?,?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		int pdeptno = 50;
		String pdname = null;
		String ploc = null;

		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pdeptno);
			cstmt.setString(2, pdname);
			cstmt.setString(3, ploc);

			rowCount = cstmt.executeUpdate();

			if (rowCount == 1) {
				System.out.println("부서 추가 성공!!!");
			} else {
				System.out.println("부서 추가 실패!!!");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
