package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.SearchCriteria;

public class BoardDao {

	private Connection conn;
	private PreparedStatement pstmt = null;
	

	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	public int deleteBoard(int bidx) {
		
		int value = 0;

		String sql = "UPDATE b_board SET delyn = 'Y', writeday = now() WHERE bidx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
		
	}
	
	public int updateBoard(String subject, String content, String writer, int bidx) {
		
		int value = 0;
		String sql = "UPDATE B_BOARD SET subject = ?, content = ?, writer = ? writeday = now() WHERE bidx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setInt(4, bidx);
			
			value = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return value;
	}
	
	public int insertBoard(String subject, String content, String writer, String ip, int midx, String fileName) {
		
		int value = 0;
		String sql = "INSERT INTO B_BOARD(originbidx, depth, level_,subject, content, writer, ip, midx, filename)"
				 //+"VALUES(select auto_increment, 0, 0, ?, ?, ?, ?, ?, ?)";
				 +"select max(bidx) + 1, 0, 0, ?, ?, ?, ?, ?, ? from b_board";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, ip);
			pstmt.setInt(5, midx);
			pstmt.setString(6, fileName);
			
			value = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
		//memberVo ��ü�� ��� arrayList Ŭ������ ��ü �����Ѵ�.
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
		
		ResultSet rs = null;
		String str = "";
		
		if(scri.getSearchType().equals("subject")) {
			str = "and subject like ?";
		}
		else {
			str = "and writer like ?";
		}
		
		//���������� ���ڿ��� ����� ���´� 
		//String sql = "select * from b_board where delyn = 'N' order by originbidx desc, depth asc";
		
		/*String sql = "SELECT * FROM("
				+ "SELECT ROWNUM AS rnum, A.* FROM("
				+ "select * from b_board where delyn = 'N' "+str+" order by originbidx desc, depth ASC) A"
				+ ") B WHERE rnum BETWEEN ? AND ?";*/
		String sql = "select * from b_board where delyn = 'N'"+str+"order by originbidx desc, depth asc limit ?,?";;
		
		//���� ��ü�� �ִ� prepareStatement �޼ҵ带 �����ؼ� sql ���ڿ��� ��� ���� ��ü�� ����� 
		try{
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, "%"+scri.getKeyword()+"%");
		    pstmt.setInt(2, (scri.getPage()-1)*15 + 1);
		    pstmt.setInt(3, scri.getPage()*15);

		    rs = pstmt.executeQuery();
		    
			while(rs.next()){
				//�ݺ� �Ҷ����� ��ü ���� 
				BoardVo bv = new BoardVo();
				
				//rs�� ��Ƴ��� �÷������� mv�� �Ű� ��´�
				bv.setBidx(rs.getInt("bidx"));
				bv.setSubject(rs.getString("subject"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				bv.setLevel_(rs.getInt("level_"));
				
				//alist�� ���� ���� ��ü�� �߰��Ѵ�.
				alist.add(bv);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try {
				conn.close();
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		return alist;
	}
	
	public BoardVo boardSelectOne(int bidx) {
		
		BoardVo bv = null;
		ResultSet rs = null;
		String sql = "select * from b_board where bidx = ?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bv = new BoardVo();
				
				bv.setBidx(rs.getInt("bidx"));
				bv.setOriginbidx(rs.getInt("originbidx"));
				bv.setDepth(rs.getInt("depth"));
				bv.setLevel_(rs.getInt("level_"));
				bv.setSubject(rs.getString("subject"));
				bv.setContent(rs.getString("content"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				bv.setFilename(rs.getString("filename"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bv;
	}
	
	public int replyBoard(BoardVo bv) {
		
		int value = 0;
		
		String sql1 = "update b_board set depth = depth + 1 where originbidx = ? and depth > ?";
		
		String sql2 = "INSERT INTO B_BOARD(bidx, originbidx, depth, level_,subject, content, writer, ip, midx)"
				 +"VALUES(BIDX_B_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn.setAutoCommit(false);  //�ڵ� Ŀ�Ա�� ��
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth());
			//value = pstmt.executeUpdate();
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth() + 1);
			pstmt.setInt(3, bv.getLevel_() + 1);
			pstmt.setString(4, bv.getSubject());
			pstmt.setString(5, bv.getContent());
			pstmt.setString(6, bv.getWriter());
			pstmt.setString(7, bv.getIp());
			pstmt.setInt(8, bv.getMidx());
			value = pstmt.executeUpdate();
			
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	public int boardTotal(SearchCriteria scri) {
		int cnt = 0;
		ResultSet rs = null;
		String str = "";

		if(scri.getSearchType().equals("subject")) {
			str = "and subject like ?";
		}
		else {
			str = "and writer like ?";
		}
		
		String sql = "select count(*) as cnt from b_board where delyn = 'N'" +str+"";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}
	
}