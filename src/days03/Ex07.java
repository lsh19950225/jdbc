package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author User1
 * [jdbc] select dept
 */
public class Ex07 {

	public static void main(String[] args) {

		String sql = "{call up_selectdept(?)}";

		Connection conn = null;
		CallableStatement cstmt = null;

		conn = DBConn.getConnection();
		ResultSet rs = null;
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR); // out : 출력용
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1); // 변환
			
			int deptno;
			String dname, loc;
			
			while (rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n",deptno,dname,loc);
			} // while
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DBConn.close();

	} // main

} // class

//-- dept 테이블의 모든 부서 정보를 조회하는 저장 프로시저
//create or replace procedure up_selectdept
//(
//    pdeptcursor out sys_refcursor
//)
//is
//begin
//    open pdeptcursor for
//        select *
//        from dept;
//-- exception
//--    when other then
//--    raise ap_e)
//end;