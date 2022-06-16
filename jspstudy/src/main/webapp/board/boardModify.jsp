<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="jspstudy.domain.*"%>
<%if(session.getAttribute("midx") == null){
    out.println("<script>alert('로그인 하세요'); location.href = '"+request.getContextPath()+"/member/memberLogin.do'</script>");
}
%>
<%BoardVo bv = (BoardVo)request.getAttribute("bv"); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
      function check(){
          var fm = document.frm;
          
          fm.action = "<%=request.getContextPath()%>/board/boardModifyAction.do";
          fm.method = "post";
          fm.submit();
          return;
      }
    </script>
</head>
<body>
<h1 style = "text-align:center;">수정하기</h1>
<hr>
    <form name = "frm">
        <input type = "hidden" name = "bidx" value = "<%=bv.getBidx()%>">
        <table border = 1 style = "width : 800px;">
            <tr>
                <td style = "width : 100px;">제목</td>
                <td><input type = "text" name = "subject" size = "80" value = "<%=bv.getSubject()%>"></td>
            </tr>
            <tr>
                <td>내용</td>
                <td><textarea name = "content" placeholder = "내용을 입력해주세요" cols = "80" rows = "10"><%=bv.getContent()%>
                </textarea></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td><input type = "text" name = "writer" size = "80" value = "<%=bv.getWriter()%>"></td>
            </tr>
            <tr>
                <td colspan = "2" style = "text-align : right;">
                    <input type = "button" name = "btn" value = "확인" onclick = "check();">
                    <input type = "reset" name = "reset" value = "리셋">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>