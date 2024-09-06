package days04.board.vo;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

public class pagingVO {

	public int currentPage = 1;
	public int start;
	public int end;
	public boolean prev;
	public boolean next;
	
	public pagingVO(int currentPage, int numberPerPage, int numberOfPageBlock) {
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		
		try {
			int totalPages = dao.getTotalPages(numberPerPage);
			
			start = (currentPage - 1)/numberOfPageBlock*numberOfPageBlock + 1;
			end = start + numberOfPageBlock - 1;
			if (end>totalPages) end = totalPages;
			if (start != 1) this.prev = true;
			if (end != totalPages) this.next = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public pagingVO(int currentPage, int numberPerPage, int numberOfPageBlock, String searchCondition,
			String searchWord) {
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		
		try {
			int totalPages = dao.getTotalPages(numberPerPage, searchCondition, searchWord);
			
			start = (currentPage - 1)/numberOfPageBlock*numberOfPageBlock + 1;
			end = start + numberOfPageBlock - 1;
			if (end>totalPages) end = totalPages;
			if (start != 1) this.prev = true;
			if (end != totalPages) this.next = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
} // class
