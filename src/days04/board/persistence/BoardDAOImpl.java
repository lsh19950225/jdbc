package days04.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.doit.domain.DeptVO;

import days04.board.domain.BoardDTO;



public class BoardDAOImpl implements BoardDAO {
	
	private Connection conn = null; // = DBConn.getConnection(); x
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// 생성자 DI : alt + shift + s
	public BoardDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}
	
	// Setter DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() {
		return conn;
	}
	
	@Override
	public ArrayList<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException {
		
		long seq;
		String title, writer, email;
		Date writedate;
		int readed;
		
		ArrayList<BoardDTO> list = null;
		
		String sql = " select * "
				+ "from ( "
				+ "select rownum no, t.* "
				+ "from ( "
				+ "    select seq, title, writer, email, writedate, readed "
				+ "    from tbl_cstVSBoard "
				+ "    order by seq desc "
				+ ") t "
				+ ") b "
				+ "where no between ? and ? ";
		
		// 부서조회() START
		BoardDTO dto = null;
		int start = (currentPage-1)*numberPerPage + 1;
		int end = start + numberPerPage - 1;
		int totalRecords = getTotalRecords();
		if ( end > totalRecords ) {
			end = totalRecords;
		}

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				list = new ArrayList<BoardDTO>();
				do {

					seq = rs.getLong("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.email(email)
							.writedate(writedate)
							.readed(readed)
							.build();
					
					list.add(dto);

				} while (rs.next());

			} // if

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
		// 부서조회() END
		
		return list;
	}

	@Override
	public ArrayList<BoardDTO> select() throws SQLException {
		
		long seq;
		String title, writer, email;
		Date writedate;
		int readed;
		
		ArrayList<BoardDTO> list = null;
		
		String sql = " select seq, title, writer, email, writedate, readed "
				+ " from tbl_cstVSBoard "
				+ " order by seq desc ";
		
		// 부서조회() START
		BoardDTO dto = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				list = new ArrayList<BoardDTO>();
				do {

					seq = rs.getLong("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.email(email)
							.writedate(writedate)
							.readed(readed)
							.build();
					
					list.add(dto);

				} while (rs.next());

			} // if

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
		// 부서조회() END
		
		return list;
	}

	@Override
	public int getTotalRecords() throws SQLException {
		
		int totalRecords = 0;      
        String sql = "SELECT COUNT(*) FROM tbl_cstvsboard";
        this.pstmt = this.conn.prepareStatement(sql);
        this.rs =  this.pstmt.executeQuery();      
        if( this.rs.next() ) totalRecords = rs.getInt(1);      
        this.rs.close();
        this.pstmt.close();            
        return totalRecords;
		
	}

	@Override
	public int getTotalPages(int numberPerPage) throws SQLException {
		
		int totalPages = 0;      
        String sql = "SELECT CEIL(COUNT(*)/?) FROM tbl_cstvsboard";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setInt(1, numberPerPage);
        this.rs =  this.pstmt.executeQuery();      
        if( this.rs.next() ) totalPages = rs.getInt(1);      
        this.rs.close();
        this.pstmt.close();            
        return totalPages;
        
	}

	@Override
	public int insert(BoardDTO dto) throws SQLException {
		
		String sql = " INSERT INTO tbl_cstvsboard "
	               + " (seq, writer, pwd, email, title, tag, content ) "
	               + " VALUES "
	               + " (seq_tblcstvsboard.NEXTVAL, ?, ?, ?, ?, ?, ? ) ";
		
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, dto.getWriter());
		this.pstmt.setString(2, dto.getPwd());
		this.pstmt.setString(3, dto.getEmail());
		this.pstmt.setString(4, dto.getTitle());
		this.pstmt.setInt(5, dto.getTag());
		this.pstmt.setString(6, dto.getContent());
		rowCount = this.pstmt.executeUpdate();
		
		return rowCount;
	}

	@Override
	public int increaseReaded(long seq) throws SQLException {
		
		String sql = " update tbl_cstvsboard "
	               + " set readed = readed + 1 "
	               + " where seq = ? ";
		
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();
		
		return rowCount;
		
	}

	@Override
	public BoardDTO view(long seq) throws SQLException {
		
		String title, writer, email, content;
		Date writedate;
		int readed;
		
		ArrayList<BoardDTO> list = null;
		
		String sql = " select seq, title, writer, email, writedate, readed, content "
				+ " from tbl_cstVSBoard "
				+ " where seq = ? ";
		
		// 부서조회() START
		BoardDTO dto = null;

		try {
			pstmt = conn.prepareStatement(sql);
			this.pstmt.setLong(1, seq);
			rs = pstmt.executeQuery();

			if (rs.next()) {


					seq = rs.getLong("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					content = rs.getString("content");
					dto = new BoardDTO().builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.email(email)
							.writedate(writedate)
							.readed(readed)
							.content(content)
							.build();

			} // if

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
		// 부서조회() END
		
		return dto;
		
	}

	@Override
	public int delete(long seq) throws SQLException {
		
		String sql = " delete "
	               + " from tbl_cstvsboard "
	               + " where seq = ? ";
		
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();
		
		return rowCount;

	} // delete

	@Override
	public int update(BoardDTO dto) throws SQLException {
		
		String sql = " update tbl_cstvsboard "
	               + " set title = ?, content = ? "
	               + " where seq = ? ";
		
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, dto.getTitle());
		this.pstmt.setString(2, dto.getContent());
		this.pstmt.setLong(3, dto.getSeq());
		rowCount = this.pstmt.executeUpdate();
		
		return rowCount;
		
	}


	
	

} // class
