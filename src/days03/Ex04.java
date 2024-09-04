package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Ex04 {

	public static void main(String[] args) {

		/*
		  						[실행결과]
				 ACCOUNTING(3)
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				 RESEARCH(3)
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				 SALES(6)
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				   empno ename hiredate pay
				 OPERATIONS(1)
				   empno ename hiredate pay
				 NULL(1)
				   empno ename hiredate pay
		 */

		String deptSql = " select d.deptno, dname, count(empno) cnt "
				+ " from dept d full outer join emp e on d.deptno = e.deptno "
				+ " group by dname, d.deptno "
				+ " order by d.deptno asc ";

		String empSql = " select empno, ename, hiredate, sal+nvl(comm,0) pay "
				+ " from emp "
				+ " where deptno = ? "; // deptno is null (기억)

		Connection conn = null;
		PreparedStatement deptPstmt = null, empPstmt = null;
		ResultSet deptRs = null, empRs = null;
		DeptVO dvo = null;
		EmpVO evo = null;
		ArrayList<EmpVO> empList = null;

		LinkedHashMap<DeptVO, ArrayList<EmpVO>> lhMap = new LinkedHashMap<>(); // MVC패턴

		conn = DBConn.getConnection();

		int deptno, cnt;
		String dname;

		int empno;
		String ename;
		LocalDateTime hiredate;
		double pay;

		try {
			deptPstmt = conn.prepareStatement(deptSql);
			deptRs = deptPstmt.executeQuery();

			while (deptRs.next()) {
				empList = null;
				deptno = deptRs.getInt("deptno");
				dname = deptRs.getString("dname");
				cnt = deptRs.getInt("cnt");

				dvo = new DeptVO(deptno, dname, null, cnt); // loc : select에 없어서 null

				// System.out.printf("%s(%d명)\n",dvo.getDname(),dvo.getCnt());

				// 해당 부서사원 조회 START
				// deptno = null empSql 설정
				if (deptno == 0) {
					empSql = " select empno, ename, hiredate, sal+nvl(comm,0) pay "
							+ " from emp "
							+ " where deptno is null ";
				}
				empPstmt = conn.prepareStatement(empSql);
				if (deptno != 0) empPstmt.setInt(1, deptno);
				empRs = empPstmt.executeQuery();
				if (empRs.next()) {
					empList = new ArrayList<EmpVO>();
					do {	
						empno = empRs.getInt("empno");
						ename = empRs.getString("ename");
						hiredate = empRs.getTimestamp("hiredate").toLocalDateTime();
						pay = empRs.getDouble("pay");

						evo = new EmpVO().builder()
								.empno(empno)
								.ename(ename)
								.hiredate(hiredate)
								.sal(pay)
								.build();
						empList.add(evo);
					} while (empRs.next());

				} // if

				// 해당 부서사원 조회 END
				// Map		k,v 엔트리 추가
				lhMap.put(dvo, empList);

			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				deptRs.close();
				deptPstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DBConn.close();

		dispLHMap(lhMap);

	} // main

	private static void dispLHMap(LinkedHashMap<DeptVO, ArrayList<EmpVO>> lhMap) {

		Set<Entry<DeptVO, ArrayList<EmpVO>>> eset = lhMap.entrySet();
		Iterator<Entry<DeptVO, ArrayList<EmpVO>>> eir = eset.iterator();
		while (eir.hasNext()) {
			Entry<DeptVO, ArrayList<EmpVO>> entry = eir.next();
			DeptVO dvo = entry.getKey();
			
			System.out.printf("%s(%d명)\n",dvo.getDname(),dvo.getCnt());
			
			ArrayList<EmpVO> empList = entry.getValue();
			
			if (empList == null) {
				System.out.println("\t 해당 부서원은 존재 x");
				continue;
			}
			
			Iterator<EmpVO> ir = empList.iterator();
			while (ir.hasNext()) {
				EmpVO evo = ir.next();
				System.out.printf("%d\t%s\t%tF\t%.2f\n",evo.getEmpno(),evo.getEname(),evo.getHiredate(),evo.getSal());
			} // while
			
		}

	}

} // class
