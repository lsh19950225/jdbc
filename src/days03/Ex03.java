package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author User1
 *	[jdbc] 자바 리플렉션(reflection)
 *				ㄴ 반사, 상, 반영
 *			jdbc 리플렉션 ? 결과물(rs)에 대한 정보를 추출해서 사용하는 기술
 *		예) scott 소유하고 있는 모든 테이블 목록 출력(조회)
 *			테이블명을 선택(입력) 받아서 그 선택된 테이블의 정보를 조회
 */
public class Ex03 {

	public static void main(String[] args) {

		String sql = " select table_name "
				+ " from tabs ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> tnList = new ArrayList<>();
		String tableName = null;

		// 1, 2
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tableName = rs.getString("table_name");
				tnList.add(tableName);
			} // while
			System.out.println(tnList.toString());
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

		//
		Scanner scanner = new Scanner(System.in);
		System.out.print("테이블명을 입력 ? ");
		tableName = scanner.next();
		//
		// sql = " select * from "; // 불가능
		sql = " select * from " + tableName;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// rs.getMetaData() : rs 결과 객체로 부터 컬럼의 수, 컬럼 타입, 컬럼 속성
			ResultSetMetaData rsmd = rs.getMetaData();
			// 컬럼수
			int columnCount = rsmd.getColumnCount(); // 컬럼수 반환
			System.out.println("the number of column : " + columnCount);
			
			System.out.println("-".repeat(columnCount * 7));
			
			/*
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnName(i+1);
				int columnType = rsmd.getColumnType(i+1);
				String columnTypeName = rsmd.getColumnTypeName(i+1);
				int p = rsmd.getPrecision(i+1); // 타입 크기
				int s = rsmd.getScale(i+1);
				if (columnType == 2) {
					System.out.printf("%s\t%d\t%s(%d,%d)\n", columnName,columnType,columnTypeName,p,s);
				} else if (columnType == 12) {
					System.out.printf("%s\t%d\t%s(%d)\n", columnName,columnType,columnTypeName,p);
				} else {
					System.out.printf("%s\t%d\t%s\n", columnName,columnType,columnTypeName);
				}
			}
			*/
			
			for (int i = 1; i <= columnCount; i++) { // 참고
				String columnName = rsmd.getColumnName(i);
				System.out.printf("%s\t",columnName);
			} // for
			System.out.println();
			System.out.println("-".repeat(columnCount * 7));
			
			while (rs.next()) {
				for (int i = 1; i < args.length; i++) {
					int columnType = rsmd.getColumnType(i);
					
					if (columnType == 2) { // number
						int s = rsmd.getScale(i);
					if (s == 0) {
						System.out.printf("%d\t",rs.getInt(i));
					} else {
						System.out.printf("%.2f\t",rs.getDouble(i));
					}
				} else if (columnType == 12) { // varchar2, nvarchar2, char ..
					System.out.printf("%s\t",rs.getString(i));
				} else if (columnType == 93) { // date
					System.out.printf("%tF\t",rs.getDate(i));
				}
				
				} // for
				
				System.out.println(); // 개행
					
			} // while
			
			System.out.println("-".repeat(columnCount * 7));
			
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

		// 4
		DBConn.close();

	} // main

} // class
