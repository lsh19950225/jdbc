package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.doit.domain.EmpVO;

import com.util.DBConn;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author User1
 * [jdbc] emp 테이블의 모든 사원 정보 조회
 * 				org.doit.domain 패키지
 * 				ㄴ EmpVO.java	(이름 뒤에 VO가 붙으면 Value Object) : 값을 저장하는
 * 				ArrayList<EmpVO> list
 * 				dispEmp() 출력함수 선언
 * 
 * 				com.util 패키지
 * 				ㄴ DBConn.java
 * 					ㄴ Connection getConnection() 메서드 구현
 * 					ㄴ Connection getConnection() 메서드 구현
 * 					ㄴ Connection getConnection() 메서드 구현
 * 					ㄴ close() 메서드 구현
 */
public class Ex01_04 {

	public static void main(String[] args) {

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

		ArrayList<EmpVO> list = new ArrayList<>(); // 객체 생성
		EmpVO vo = null;

		try {
			// 1. + 2. == com.util.DBConn.getConnection()
			conn = DBConn.getConnection();
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

				vo = new EmpVO(empno,ename,job,mgr,hiredate,sal,comm,deptno);

				list.add(vo);

				// Date : %s
				// 널 처리 하지 않았는데 0 나옴 // 널이 나왔으면 좋겠다 : 과제
				/*

				 */
			} // while

			dispEmp(list);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. Connection 객체 닫기 - close()
			try {
				rs.close(); // 닫는 순서도 지켜주기
				stmt.close();
				// conn.close(); // 중요
				DBConn.close(); // 바꿔주기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	} // main

	public static void dispEmp(ArrayList<EmpVO> list) {
		if (list.size() == 0) {
			System.out.println("사원이 존재하지 않습니다.");
			return;
		}

		// 사원 정보 출력
		// ㄴ. 밑과 같은 코딩
		list.forEach(vo->{
			System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n"
					,vo.getEmpno(),vo.getEname(),vo.getJob(),vo.getMgr(),vo.getHiredate()
					,vo.getSal(),vo.getComm(),vo.getDeptno());
		});

		/*
		// ㄱ. 기억하기
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo =  ir.next();
			// System.out.println(vo.toString());
			System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n"
					,vo.getEmpno(),vo.getEname(),vo.getJob(),vo.getMgr(),vo.getHiredate()
					,vo.getSal(),vo.getComm(),vo.getDeptno());
		} // while
		 */

	}

} // class
