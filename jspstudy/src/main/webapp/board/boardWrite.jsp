<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   if(session.getAttribute("midx") == null){
	   session.setAttribute("saveUrl", request.getRequestURI());
       out.println("<script>alert('로그인 하세요'); location.href = '"+request.getContextPath()+"/member/memberLogin.do'</script>");   
   }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
    function check(){
    //alert("테스트입니다.");
        var fm = document.frm;

        if(fm.subject.value == "")
        {
            alert("제목 입력하세요");
            fm.subject.focus();
            return;
        }   
        else if (fm.content.value == "")
        {
            alert("내용을 입력하세요.");
            fm.content.focus();
            return;
        }
        else if (fm.writer.value == "")
        {
            alert("작성자를 입력하세요.");
            fm.writer.focus();
            return;
        }
        
        //가상경로 사용 
        fm.action = "<%=request.getContextPath()%>/board/boardWriteAction.do";
        fm.enctype = "multipart/form-data";
        fm.method = "post";
        fm.submit();
        return;
    }
    </script>
</head>
<body>
<h1 style = "text-align:center;">글쓰기</h1>
<hr>
    <form name = "frm">
        <table border = 1 style = "width : 800px;">
            <tr>
                <td style = "width : 100px;">제목</td>
                <td><input type = "text" name = "subject" size = "80"></td>
            </tr>
            <tr>
                <td>내용</td>
                <td><textarea name = "content" placeholder = "내용을 입력해주세요" cols = "80" rows = "10">
                </textarea></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td><input type = "text" name = "writer" size = "80" value = "<%=session.getAttribute("memberName")%>" readonly = "readonly"></td>
            </tr>
            <tr>
                <td>파일 업로드</td>
                <td><input type = "file" name = "filename" ></td>
            </tr>
            <tr>
                <td colspan = "2">
                    <input type = "button" name = "btn" value = "확인" onclick = "check();">
                    <input type = "reset" name = "reset" value = "리셋">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>