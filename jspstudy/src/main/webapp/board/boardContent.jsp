<%@ page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="jspstudy.domain.*"%>
<%BoardVo bv = (BoardVo)request.getAttribute("bv"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <h1 style = "text-align:center;">게시판 리스트</h1><hr>
    <table border=1 style="border-collapse: collapse; width : 800px;">
        <tr>
            <td>제목 &nbsp; &nbsp; (날짜 : <%=bv.getWriteday().substring(0,10) %>)</td>
            <td><%=bv.getSubject() %></td>
        </tr>
        <tr>
            <td>내용</td>
            <td style = "width : 500px; height : 300px;"><%=bv.getContent() %>
                <img src = "<%=request.getContextPath()%>/images/<%=bv.getFilename()%>">
                <a href = "<%=request.getContextPath()%>/board/fileDownload.do?filename=<%=bv.getFilename()%>"><%=bv.getFilename()%></a>
            </td>
        </tr>
        <tr>
            <td>작성자</td>
            <td><%=bv.getWriter() %></td>
        </tr>
        <tr>
            <td colspan = "2" style = "text-align:right;">
            <input type = "button" value = "수정" name = "modify" onclick = "location.href = '<%=request.getContextPath() %>/board/boardModify.do?bidx=<%=bv.getBidx()%>'">
            <input type = "button" value = "삭제" name = "delete" onclick = "location.href = '<%=request.getContextPath() %>/board/boardDelete.do?bidx=<%=bv.getBidx()%>'">
            <input type = "button" value = "답변" name = "reply" onclick = "location.href = '<%=request.getContextPath() %>/board/boardReply.do?bidx=<%=bv.getBidx()%>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>'">
            <input type = "button" value = "목록" name = "list" onclick = "location.href = '<%=request.getContextPath() %>/board/boardList.do'"></td>
        </tr>
    </table>
</body>
</html>