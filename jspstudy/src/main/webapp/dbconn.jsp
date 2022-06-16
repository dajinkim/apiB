<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.*"%>
<%

%>
<%
String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
String user = "system";
String password = "1234";

//등록한 드라이버 중에 사용 가능한 클래스 찾아서 생성 
Class.forName("oracle.jdbc.driver.OracleDriver");

//연결 정보를 통해서 연결 객체를 참조변수 conn에 담는다.

Connection conn = java.sql.DriverManager.getConnection(url, user, password);
%>