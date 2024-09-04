package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author User1
 *	[jdbc] 트랜잭션 처리
 *	논리적인 작업 단위			모두 완료, 모두 취소
 *	예) 계좌 이체
 *		1) A update
 *		2) B update			commit, rollback
 *
 *	begin						try
 *		1) update				insert	50	o
 *		2) update				insert	50	x
 *				:
 *		commit;			commit;
 *	exception				catch
 *		when ??? then
 *			rollback;		rollback;
 *	end						}
 */
public class Ex02 {

	public static void main(String[] args) {

		String sql = " insert into dept values (?,?,?) ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;

		try {
			// 1, 2
			conn = DBConn.getConnection();
			conn.setAutoCommit(false); // 자동 커밋 x
			pstmt = conn.prepareStatement(sql);

			// ㄱ.
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");

			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("첫 번째 부서 추가 성공!!!");
			}

			// ㄴ. 에러발생
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("두 번째 부서 추가 성공!!!");
			}

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		// 4
		DBConn.close();

	} // main

} // class
