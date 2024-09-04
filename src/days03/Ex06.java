package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author User1
 * [jdbc] 로그인(인증) / 인가
 * 아이디/비밀번호 입력
 * [로그인][회원가입]
 *   ***
 *   emp / empno(id) / ename(pwd)
 */
public class Ex06 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// emp 테이블의 empno(ID) 가정
		System.out.print("로그인할 ID(empno)/PWD(ename) 입력 ? ");
		int id = scanner.nextInt(); // 7369			9999
		String pwd = scanner.next();
		
		String sql = "{call up_login(?,?,?)}";

		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1; // 값 의미없음.

		conn = DBConn.getConnection();

		try {
			cstmt = conn.prepareCall(sql);
			// in ?, out ?
			cstmt.setInt(1, id);
			cstmt.setString(2, pwd);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER); // out : 출력용
			cstmt.executeQuery();
			check = cstmt.getInt(3);
			if (check == 0) {
				System.out.println("로그인 성공!!!");
			} else if (check == 1) {
				System.out.println("아이디 존재하지만 비밀번호가 잘못된다.");
			} else if (check == -1) {
				System.out.println("존재하지 않는 아이디 입니다.");
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

		DBConn.close();

	} // main

} // class

//create or replace procedure up_login
//(
//    pid in emp.empno%type
//    , ppwd in emp.ename%type 
//    , pcheck out number -- 0(로그인 성공)/1(id 존재, pwd x)/-1(id존재 x)
//)
//is
//    vpwd emp.ename%type;
//begin
//    select count(*) into pcheck
//    from emp
//    where empno = pid;
//    
//    if pcheck = 1 then -- id 존재
//        select ename into vpwd
//        from emp
//        where empno = pid;
//        
//        if vpwd = ppwd then -- id 존재 o, pwd 일치
//            pcheck := 0;
//        else -- id 존재 o, pwd x
//            pcheck := 1;
//        end if;
//    else -- id 존재 x
//        pcheck := 1;
//    end if;
//-- exception
//--    when other then
//--    raise ap_e)
//end;