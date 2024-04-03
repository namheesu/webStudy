<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
// map형태의 저장데이터 형식을 사용함 - case1과 case2의 공통점
	LocalDateTime processTime = LocalDateTime.now();
	// case 1 - state less
	request.setAttribute("processTime", processTime);
	// case 2 - state full(session)
	session.setAttribute("processTime", processTime);
	// case 3 - state full(cookie)
	Cookie customCookie = new Cookie("processTime", processTime.toString());	// 쿠키는 문자열만 저장되고, 세션은 타입제한이 없다
	response.addCookie(customCookie);
%>
<a href="afterCase1.jsp">이후 발생하는 새로운 요청(request scope)</a><br>
<a href="afterCase2.jsp">이후 발생하는 새로운 요청(session scope)</a><br>
<a href="afterCase3.jsp">이후 발생하는 새로운 요청(Cookie)</a>
</body>
</html>