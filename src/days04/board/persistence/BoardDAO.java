package days04.board.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.board.domain.BoardDTO;

public interface BoardDAO {
	
	// 1. 페이징 처리 x + 게시글 목록 추상메서드 선언
	ArrayList<BoardDTO> select() throws SQLException; // 예외 처리를 떠넘긴다.
	
	// 1-2. 페이징 처리 o + 게시글 목록 추상메서드 선언
	ArrayList<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException; // 예외 처리를 떠넘긴다.

	// 1-3. 총레코드수
	int getTotalRecords() throws SQLException;
	// 1-4. 총페이지수
	int getTotalPages(int numberPerPage) throws SQLException;
	
	// 2. 게시글 쓰기(새글)
	int insert(BoardDTO dto) throws SQLException;
	
	// 3. 조회수 증가
	int increaseReaded(long seq) throws SQLException;
	// 3-2. 상세보기
	BoardDTO view(long seq) throws SQLException;
	
	// 4. 게시글 삭제
	int delete(long seq) throws SQLException;
	
	// 5. 게시글 수정 - 제목, 내용, 이메일
	int update(BoardDTO dto) throws SQLException;
	
	// 6. 게시글 검색 페이징처리 x
	ArrayList<BoardDTO> search(String searchCondition, String searchWord) throws SQLException;

	// 6-1. 게시글 검색 페이징처리 o
	ArrayList<BoardDTO> search(String searchCondition, String searchWord, int currentPage, int numberPerPage) throws SQLException;
	
	// 검색할 때의 총 페이지
	int getTotalPages(int numberPerPage, String searchCondition, String searchWord) throws SQLException;

} // interface
