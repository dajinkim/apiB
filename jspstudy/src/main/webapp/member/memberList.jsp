<%@ page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.*"%>
<%
//MemberDao md = new MemberDao();

//ArrayList<MemberVo> alist = md.memberSelectAll();
ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");
//out.println(alist.get(0).getMembername() + "<br>");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    if(session.getAttribute("midx") != null){
        out.println("회원 아이디 : "+session.getAttribute("memberId"));
        out.println("회원 이름 : "+session.getAttribute("memberName"));
        
        out.println("<a href = '"+request.getContextPath()+"/member/memberLogoutAction.do'>로그아웃</a>");
    }
%>
    <h1 style = "text-align:center;">회원 목록 만들기</h1><hr>
    <table
        border=1 style="border-collapse: collapse; margin-left: auto; margin-right: auto;">
        <tr style="color: green;">
            <td>midx번호</td>
            <td>제목</td>
            <td>전화번호</td>
            <td>작성일</td>
        </tr>
        <%
        for (MemberVo mv : alist) {
        %>
        <tr>
            <td><%=mv.getMidx()%></td>
            <td><%=mv.getMembername()%></td>
            <td><%=mv.getMemberphone()%></td>
            <td><%=mv.getWriteday()%></td>
        </tr>
        <%
        }
        %>
    </table>
</body>
</html>