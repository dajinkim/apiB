<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="jspstudy.domain.*"%>
<%BoardVo bv = (BoardVo)request.getAttribute("bv"); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1 style = "text-align:center;">수정하기</h1>
<hr>
    <form name = "frm">
        <input type = "hidden" name = "bidx" value = "<%=bv.getBidx()%>">
        <table border = 1 style = "width : 800px;">
            <tr>
                <td colspan = "2" style = "width : 100px;">delete ?</td>
            </tr>
            <tr>
                <td colspan = "2" style = "text-align : right;">
                    <input type = "button" name = "btn" value = "확인" onclick = "location.href='<%=request.getContextPath()%>/board/boardDeleteAction.do?bidx=<%=bv.getBidx()%>'">
                    <input type = "reset" name = "reset" value = "취소" onclick = "location.href='<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx()%>'">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>