<%@ page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.*"%>

<% ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
  PageMaker pm = (PageMaker)request.getAttribute("pm");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <h1 style = "text-align:center;">게시판 리스트</h1><hr>
    <form name = "frm" action = "<%=request.getContextPath() %>/board/boardList.do" method = "post">
        <table style = "width:800px; text-align:right">
            <tr>
                <td style = "width:620px;">
                    <select name = "searchType">
                        <option value = "subject">제목</option>
                        <option value = "writer">작성자</option>
                    </select>
                </td>
                <td>
                    <input type = "text" name = "keyword" size = "10">
                </td>
                <td>
                    <input type = "submit" name = "submit" value = "검색">
                </td>
            </tr>
        </table>
    </form>
    
    <table
        border=1 style="border-collapse: collapse; width : 800px;">
        <tr style="color: green;">
            <td>bidx번호</td>
            <td>제목</td>
            <td>작성자</td>
            <td>작성일</td>
        </tr>
        <% for(BoardVo bv : alist){ %>
        <tr>
            <td><%=bv.getBidx()%></td>
            <!-- a태그에 각 글들의 내용을 볼 수 있는 페이지 작성 후 a태그 완성 -->
            <td><a href = "<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx()%>"<%=bv.getSubject() %>>
                <%for(int i = 1; i <= bv.getLevel_(); i++){
                    out.println("&nbsp; &nbsp;");
                    if(i == bv.getLevel_()){
                     out.println("ㄴ");   
                    }
                }
                %>
                <%=bv.getSubject()%>
            </a></td>
            <td><%=bv.getWriter()%></td>
            <td><%=bv.getWriteday()%></td>
        </tr>
        <%
        }
        %>
    </table>
    <table style="width:800px; text-align:center;">
        <tr>
            <td style="width:200px; text-align:right;">
                <% 
                String keyword = pm.getScri().getKeyword();
                String searchType = pm.getScri().getSearchType();
                if(pm.isPrev() == true) out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+(pm.getStartPage()-1)+"&keyword="+keyword+"&searchType="+searchType+"'>◀</a>"); %>
            </td>
            <td>
                <%
                   for(int i = pm.getStartPage(); i <= pm.getEndPage(); i++){
                       out.println("<a href='"+request.getContextPath()+"/board/boardList.do?page="+i+"&keyword="+keyword+"&searchType="+searchType+"'>"+i+"</a>");
                   }
                %>
            </td>
            <td style="width:200px; text-align:left;">
                <% if(pm.isNext() && pm.getEndPage() > 0) out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+(pm.getEndPage()+1)+"&keyword="+keyword+"&searchType="+searchType+"'>▶</a>"); %>
            </td>
        </tr>
    </table>
</body>
</html>