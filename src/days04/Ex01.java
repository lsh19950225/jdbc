package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.cotroller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author User1
 * [jdbc] 게시판 구현 ( 모델2방식 중 MVC패턴 )
 * 1. 패키지 선언
 * 2. http://taeyo.net/ 참조
 */
public class Ex01 {

	public static void main(String[] args) {
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		controller.boardStart();
		
		/*
		 * 1. 패키지 선언
		 * 	days04.board
		 * 	days04.board.cotroller
		 * 	days04.board.service
		 * 	days04.board.persistence - DAO
		 * 	days04.board.domain - DTO, VO
		 * 
		 * 2. http://taeyo.net/ 참조
		 * create sequence seq_tblcstVSBoard
			nocache;
			-- Sequence SEQ_TBLCSTVSBOARD이(가) 생성되었습니다.
			-- oracle 수정 - 테이블 생성
			create table tbl_cstVSBoard (
			  seq number not null primary key,
			  writer varchar2(20) not null,
			  pwd varchar2(20) not null,
			  email varchar2(100),
			  title varchar2(200) not null,
			  writedate date default sysdate,
			  readed number default 0,
			  tag number(1) not null, -- mode 예약어 x
			  content clob
			);
			-- Table TBL_CSTVSBOARD이(가) 생성되었습니다.
		
		3. 더미 데이터 생성
		BEGIN
		   FOR i IN 1..150 LOOP
		       INSERT INTO tbl_cstVSBoard ( seq,  writer, pwd, email, title, tag,  content) 
		       VALUES ( SEQ_TBLCSTVSBOARD.NEXTVAL, '홍길동' || MOD(i,10), '1234'
		       , '홍길동' || MOD(i,10) || '@sist.co.kr', '더미...'  || i, 0, '더미...' || i );
		   END LOOP;
		   COMMIT;
		END;
		--
		SELECT * 
		FROM tbl_cstVSBoard;
		--
		BEGIN
		   update tbl_cstVSBoard
		   set writer = '박준용'
		   where mod(seq,15) = 4;
		   COMMIT;
		END;
		--
		BEGIN
		   update tbl_cstVSBoard
		   set title = '게시판 구현'
		   where mod(seq,15) in (3,5,8);
		   COMMIT;
		END;
		
		4. days04.board.domain.BoardDTO.java
		 */

	} // main

} // class
