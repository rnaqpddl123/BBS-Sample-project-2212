package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.Board;

public class BoardDao {
	public static Connection getConnection() {
		Connection conn;
		try {
			//Servers/contex.xml에 Resource에 정보를 입력해놔야함
			Context initcontext = new InitialContext();
			DataSource ds = (DataSource) initcontext.lookup("java:comp/env/jdbc/project");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	// 게시판
	public List<Board> listusers(String field, String query, int page) {
		Connection conn = getConnection();
		int offset = (page - 1) *10;
		String sql = "SELECT b.bid, b.uid, b.title, b.modtime,"
				+ " b.viewCount, b.replycount, u.uname FROM board AS b"
				+ "	JOIN users AS u"
				+ "	ON b.uid=u.uid"
				+ "	WHERE b.isDeleted=0 AND "+ field + " LIKE ?"
				+ "	ORDER BY bid DESC"
				+ "	LIMIT 10"
				+ "	OFFSET ?;";
		List<Board> list = new ArrayList<>();
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%" + query + "%");
			pStmt.setInt(2, offset);
			
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setBid(rs.getInt(1));
				b.setUid(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setModTime(LocalDateTime.parse(rs.getString(4).replace(" ", "T")));
				b.setViewCount(rs.getInt(5));
				b.setReplyCount(rs.getInt(6));
				b.setUname(rs.getString(7));
				list.add(b);
			}
			rs.close(); pStmt.close(); conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 게시글 개수
	public int getBoardCount() {
		Connection conn = getConnection();
		String sql = "SELECT COUNT(title) FROM board WHERE isDeleted=0";
		int count = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	// 게시글 작성
	public void insert(Board b) {
		Connection conn = getConnection();
		String sql = "INSERT INTO board(uid, title, content, files) VALUES (?, ? , ?, ?);";
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, b.getUid());
			pStmt.setString(2, b.getTitle());
			pStmt.setString(3, b.getContent());
			pStmt.setString(4, b.getFiles());
			
			pStmt.executeUpdate();
			pStmt.close(); conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 게시글 보기
	public Board getBoardDetail(int bid) {
		Connection conn = getConnection();
		String sql = "SELECT b.bid, b.uid, b.title, b.content, b.modtime,"
				+ " b.viewCount, b.replycount, b.files, u.uname FROM board AS b"
				+ "	JOIN users AS u"
				+ "	ON b.uid=u.uid"
				+ "	WHERE b.bid=?";
		Board b = new Board();
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bid);
			
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				b.setBid(rs.getInt(1));
				b.setUid(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setContent(rs.getString(4));
				b.setModTime(LocalDateTime.parse(rs.getString(5).replace(" ", "T")));
				b.setViewCount(rs.getInt(6));
				b.setReplyCount(rs.getInt(7));
				b.setFiles(rs.getString(8));
				b.setUname(rs.getString(9));
			}
			rs.close(); pStmt.close(); conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
