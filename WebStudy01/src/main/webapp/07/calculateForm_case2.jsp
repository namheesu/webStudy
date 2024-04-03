<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="kr.or.ddit.enumpkg.OperatorType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath() %>/resources/js/app/07/calculateForm_case2.js?<%=System.currentTimeMillis()%>"></script>
</head>
<body>


<!-- 
	- enctype : form data가 서버로 제출될 때 해당 데이터가 인코딩되는 방법 
	- 현재 웹 애플리케이션의 컨텍스트 경로를 가져오는 메소드 
	- form의 name태그 : 폼이 제출된 후 서버에서 form data를 참조하기 위해 사용, 자바스크립트에서 요소를 참조하기 위해 사용
-->
<!--enctype: 파라미터가 전송될수 있도록 파라미터를 잡아줌 -->
<form id="calForm" method="post" enctype="application/x-www-form-urlencoded" action="<%=request.getContextPath() %>/07/case2/calculator.do"> 
	<input type="number" name="left" placeholder="좌측피연산자">
	<select name="operator">
		<%=
			Stream.of(OperatorType.values())			// enum의 모든값을 Stream으로 변환 후 
			// map()함수 사용해서 각 enum값에 대해 변환 수행 , enum상수의 이름인 o를 문자열로 반환, enum값의 getSign()반환
				.map(o->String.format("<option value='%s' label='%c' />", o.name(), o.getSign()))	
				.collect(Collectors.joining("\n"))
		%>
	</select>
	<input type="number" name="right" placeholder="우측피연산자">
	<button type="submit">=</button>
</form>

<div id="resultArea"></div>

</body>
</html>