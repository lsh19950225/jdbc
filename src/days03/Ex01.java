package days03;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author User1
 *	[jdbc]
 */
public class Ex01 {

	public static String [] menu = { "추가", "수정", "삭제", "조회", "검색", "종료" };
	public static int selectedNumber;
	public static Connection conn;
	public static Scanner scanner = new Scanner(System.in);
	public static PreparedStatement pstmt = null;

	public static void main(String[] args) {

		conn = DBConn.getConnection();

		do {
			메뉴출력();
			메뉴선택();
			메뉴처리();         
		} while (true);

	} // main

	private static void 메뉴처리() {

		switch (selectedNumber) {
		case 1:  // 추가
			부서추가();
			break;
		case 2:  // 수정
			부서수정();
			break;
		case 3:  // 삭제
			부서삭제();
			break;
		case 4: // 조회
			부서조회();
			break;
		case 5:// 검색
			부서검색();
			break;
		case 6: // 종료
			프로그램종료();
			break;
		default:
			break;
		} // switch

		일시정지();

	}

	private static void 일시정지() {

		System.out.print("엔터치면 계속합니다.");
		try {
			System.in.read();
			System.in.skip( System.in.available() );
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void 프로그램종료() {

		// 1. DB 닫기
		DBConn.close();
		// 1-2. 스캐너 닫기
		scanner.close();
		// 2. 종료 메시지 출력
		System.out.println("프로그램 종료!!!");
		// 3. 
		System.exit(-1);

	}

	private static void 부서검색() {
		
		// SELECT *
		// FROM dept
		// WHERE REGEXP_LIKE (?, ?, 'i');
		// WHERE REGEXP_LIKE (loc, 'e', 'i');
		// 테이블명, 컬럼명 등등	?로 대체할 수 없다.
		
		// 검색조건 입력 ? 1'b'(부서번호)/2'n'(부서명)/3'l'(지역명)	"nl"
		// 검색어 입력
		String SearchCondition; // 검색 조건
		String SearchWord; // 검색어

		System.out.print("검색조건, 검색어 입력하세요 ? ");
		SearchCondition = scanner.nextLine();
		SearchWord = scanner.nextLine();

		// 부서조회() 모든 코딩 복사 + 붙이기
		ArrayList<DeptVO> list = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String sql = " select * "
				+ " from dept "
				+ " where ";

		int deptno;
		String dname, loc;

		DeptVO vo = null;

		//
		if (SearchCondition.equals("b")) { // 부서번호 검색
			sql += " deptno = ? ";
		} else if (SearchCondition.equals("n")) { // 부서명 검색
			sql += " REGEXP_LIKE ( dname, ?, 'i') ";
		} else if (SearchCondition.equals("nl")) { // 지역명 검색
			sql += " REGEXP_LIKE ( dname, ?, 'i') OR REGEXP_LIKE ( loc, ?, 'i') ";
		}

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, SearchWord);
			if (SearchCondition.equals("nl")) {
				pstmt.setString(2, SearchWord);
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {

				list = new ArrayList<DeptVO>();
				do {

					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc, 0);
					list.add(vo);

				} while (rs.next());

			} // if

			부서출력(list);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void 부서조회() {

		ArrayList<DeptVO> list = null;
		ResultSet rs = null;

		String sql = " select * "
				+ " from dept "
				+ " where deptno > 0 ";

		int deptno;
		String dname, loc;

		DeptVO vo = null;

		try {
			// stmt = conn.createStatement();
			// rs = stmt.executeQuery(sql);
			pstmt = conn.prepareStatement(sql); // 미리 쿼리를 준다.
			rs = pstmt.executeQuery();

			if (rs.next()) {

				list = new ArrayList<DeptVO>();
				do {

					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc, 0);
					list.add(vo);

				} while (rs.next());

			} // if

			부서출력(list);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void 부서출력(ArrayList<DeptVO> list) {

		Iterator<DeptVO> ir = list.iterator();
		System.out.println("-".repeat(30));
		System.out.printf("deptno\tdname\tloc\n");
		System.out.println("-".repeat(30));
		while (ir.hasNext()) {
			DeptVO vo =  ir.next();
			System.out.printf("%d\t%s\t%s\n"
					, vo.getDeptno(), vo.getDname(), vo.getLoc());
		}
		System.out.println("-".repeat(30));

	}

	private static void 부서삭제() {

		String deptnos ;
		System.out.print("> 삭제할 부서번호를 입력 ? ");
		deptnos = scanner.nextLine();  // "50   , 60"

		String regex = "\\s*,\\s*";
		String [] deptnoArr = deptnos.split(regex);


		String sql = "DELETE FROM dept "
				+ " WHERE deptno IN ( ";
		for (int i = 0; i < deptnoArr.length; i++) {
			sql +="? , ";
		} // 
		// 마지막 붙은 , 제거
		sql = sql.substring(0, sql.length()-2);
		sql +=  " )";
		System.out.println(sql);
		try {
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, deptnos);
			for (int i = 0; i < deptnoArr.length; i++) {
				pstmt.setString(i+1, deptnoArr[i]);
			}
			int rowCount = pstmt.executeUpdate();
			if( rowCount >= 1 ) {
				System.out.println(" 부서 삭제 성공!!!");
			}else {
				System.out.println(" 삭제할 부서 존재 X !!!");
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}



	}

	private static void 부서수정() {

		// 1. 수정할 부서번호를 입력
		// 2. 실제 부서정보 읽어와서 출력
		// 3. 수정할 부서명, 지역명 입력받아서 수정(update)

		ResultSet rs = null;
		DeptVO vo = null;

		System.out.print("> 수정할 부서번호 입력하세요 ? ");
		int deptno = scanner.nextInt();
		String sql = "SELECT * "
				+ " FROM dept "
				+ " WHERE deptno= ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();

			if( ! rs.next() ) {
				System.out.println(" 수정할 부서가 존재하지 않습니다.");
				return ;
			} // if

			// 
			String odname = rs.getString("dname");
			String oloc = rs.getString("loc");

			System.out.println(" Original DNAME : " + odname);
			System.out.println(" Original LOC : " + oloc);
		} catch (SQLException e) {         
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}   

		// dname=>홍길동, loc=>서울 엔터
		// loc=>서울 엔터
		// dname=>홍길동 엔터
		System.out.print("> 수정할 부서명, 지역명 입력하세요 ? ");
		String dname = scanner.next();
		String loc = scanner.next();

		sql = " UPDATE dept "
				+ " SET dname=?, loc=? "
				+ " WHERE deptno=? ";


		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dname);
			pstmt.setString(2, loc);
			pstmt.setInt(3, deptno);

			int rowCount = pstmt.executeUpdate();

			if( rowCount == 1 ) {
				System.out.println(" 부서 수정 성공!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void 부서추가() {

		System.out.print("부서번호, 부서명, 지역명 입력하세요 ? ");
		int deptno = scanner.nextInt();
		String dname = scanner.next();
		String loc = scanner.next();

		String sql = "INSERT INTO dept ( deptno, dname, loc ) "
				+ "VALUES (?,?,?)"; // 날짜, 문자열도 '' 없이 ?로 표시한다.

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);

			int rowCount = pstmt.executeUpdate(); // 매개변수 x

			if (rowCount == 1) {
				System.out.println("부서 추가 성공!!!");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void 메뉴선택() {

		try{
			System.out.print("> 메뉴 선택하세요 ? ");
			selectedNumber = scanner.nextInt();
			scanner.nextLine(); // \r\n 제거 작업
		} catch (Exception e) {      }

	}

	private static void 메뉴출력() {

		System.out.printf("[메뉴]\n");
		for (int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\n", i+1, menu[i]);
		}

	}

} // class
