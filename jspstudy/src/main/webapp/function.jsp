<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.*"%>

<%!public int insertMember(Connection conn, String memberId, String memberPwd, String memberName, String memberGender,
			String memberAddr, String memberJumin, String memberphone, String hobby, String memberEmail, String ip) {

		int value = 0;
		PreparedStatement pstmt = null;

		String sql = "insert into b_member(MIDX,MEMBERID,MEMBERPWD,membername,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP)"
				+ "values(midx_b_seq.nextval,?,?,?,?,?,?,?,?,?,?)";

		//구문을 실행하고 리턴값으로 실행되었으면 1 , 아니면 0 을 value 변수에 담는다.
		try {

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
			//Statement stmt = conn.createStatement();
			//value = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

/*	public ArrayList<MemberVo1> memberSelectAll(Connection conn) {

		//memberVo 객체를 담는 arrayList 클래스를 객체 생성한다.
		ArrayList<MemberVo1> alist = new ArrayList<MemberVo1>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		//쿼리구문을 문자열로 만들어 놓는다 
		String sql = "select * from b_member where delyn = 'N' order by midx desc";

		//연결 객체에 있는 prepareStatement 메소드를 실행해서 sql 문자열을 담아 구문 객체를 만든다 
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				//반복 할때마다 객체 생성 
				MemberVo1 mv = new MemberVo1();

				//rs에 담아놓은 컬럼값들을 mv에 옮겨 담는다
				mv.setMidx(rs.getInt("midx"));
				mv.setMembername(rs.getString("memberName"));
				mv.setMemberphone(rs.getString("memberphone"));
				mv.setWriteday(rs.getString("writeday"));

				//alist에 값을 담은 객체를 추가한다.
				alist.add(mv);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alist;
	}*/
%>
