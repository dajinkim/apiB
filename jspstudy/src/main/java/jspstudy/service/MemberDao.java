package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;

public class MemberDao {
	
	
	private Connection conn;
	private PreparedStatement pstmt = null;
	
	public MemberDao() {
		
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	public MemberVo memberLogin(String memberId, String memberPwd) {
		
		MemberVo mv = null;
		ResultSet rs = null;
		String sql = "select * from b_member where delyn='N' and memberId=? and memberPwd=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mv = new MemberVo();
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberId"));
				mv.setMembername(rs.getString("memberName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mv;
	}
	
	public int insertMember(String memberId,String memberPwd, String memberName,String memberGender,String memberAddr, String memberJumin,String memberphone,String hobby,String memberEmail, String ip){
		
		int value =0; 
	   
		
		String sql = "insert into b_member(MEMBERID,MEMBERPWD,membername,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP)"
				+"values(?,?,?,?,?,?,?,?,?,?)";
		
		
		//������ �����ϰ� ���ϰ����� ����Ǿ����� 1 , �ƴϸ� 0 �� value ������ ��´�.
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			pstmt.setString(3, memberName);
			pstmt.setString(4, memberGender);
			pstmt.setString(5, memberAddr);
			pstmt.setString(6, memberJumin);
			pstmt.setString(7, memberphone);
			pstmt.setString(8, hobby);
			pstmt.setString(9, memberEmail);
			pstmt.setString(10, ip);
			
			value = pstmt.executeUpdate();

		}catch(Exception e){
		 e.printStackTrace();	
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public ArrayList<MemberVo> memberSelectAll(){
		//memberVo ��ü�� ��� arrayList Ŭ������ ��ü �����Ѵ�.
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		
		ResultSet rs = null;
		
		//���������� ���ڿ��� ����� ���´� 
		String sql = "select * from b_member where delyn = 'N' order by midx desc";
		
		//���� ��ü�� �ִ� prepareStatement �޼ҵ带 �����ؼ� sql ���ڿ��� ��� ���� ��ü�� ����� 
		try{
	    pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			//�ݺ� �Ҷ����� ��ü ���� 
			MemberVo mv = new MemberVo();
			
			//rs�� ��Ƴ��� �÷������� mv�� �Ű� ��´�
			mv.setMidx(rs.getInt("midx"));
			mv.setMembername(rs.getString("memberName"));
			mv.setMemberphone(rs.getString("memberphone"));
			mv.setWriteday(rs.getString("writeday"));
			
			//alist�� ���� ���� ��ü�� �߰��Ѵ�.
			alist.add(mv);
			
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

}
