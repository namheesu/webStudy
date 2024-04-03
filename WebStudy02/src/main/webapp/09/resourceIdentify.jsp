<%@page import="kr.or.ddit.servlet08.ServerTimeServlet"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>자원의 종류와 종류에 따른 식별 방법</h4>
<pre>
	자원의 위치와 접근 방법에 따라 3가지 분류.(경로 표기 방식에 따라 나눔)
	1. file system resource	: 파일시스템상의 절대경로(물리경로)를 사용해서 접근 가능.
		ex) D:\01.medias\img\bp01.jpg		 <!-- 윈도우 : \ , 리눅스 : / -> OS에 따라 경로표시 달라짐 -->
		<%
			File fsRes = new File("D:\\01.medias\\img\\dog06.jpg");
			out.println(fsRes.length());
		%>
	2. web resource			: URL이라는 논리주소의 형태로 접근 가능.	/ servlet context가 필요
		ex) https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
		ex) http://localhost/WebStudy01/resources/images/dog06.jpg
									   /resources/images/dog06.jpg
									   
		<%
			String logicalPath = "/resources/images/dog06.jpg";			// 서버사이드?path이기 때문에 contextPath 없음
			String realPath = application.getRealPath(logicalPath);	// d드라이브부터 , 이클립스 톰캣 경로
			out.println("real path :" + realPath);
			File webRes = new File(realPath);
			out.println(webRes.length());
			
		%>
	3. classpath resource	: classpath 이후의 경로부터 시작되는 qualified name의 형태로 접근 가능.
		ex) kr/or/ddit/images/dog06.jpg
		<!-- ClassLoader 
			1.클래스 = 붕어빵틀
			클래스로더 = 
			2.인스턴스 = 붕어빵틀에서 구워진 붕어빵
			3.힙 메모리 공간 = 
		-->
		<%
			//A a = new A();
//			logicalPath = "kr/or/ddit/images/dog06.jpg";
//			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//			ClassLoader classLoader = ServerTimeServlet.class.getClassLoader();
//			URL url = classLoader.getResource(logicalPath);				// 특정위치에 존재하지 않기 때문에 자신의 최상위 경로부터 
	
			logicalPath = "/kr/or/ddit/images/dog06.jpg";
			URL url = ServerTimeServlet.class.getResource(logicalPath);	// 자원을 찾을 때 자기 위치를 기준으로 찾음 . 
			out.println(url);
			File cpRes = new File(url.toURI());
			out.println(cpRes.length());
		%>
</pre>
<h4>URI vs URL</h4>
URI (Uniform Resource Identifier) : 자원의 식별방법 : ex)위치를 통해서 식별, 웹자원
URL (Uniform Resource Locator) : 자원을 식별할 때 위치를 기준으로 식별하는 방식. :
URN (Uniform Resouce Naming) : 자원을 식별할 때 명명된 이름으로 식별하는 방식. : 사용안함
URC (Uniform Resouce content) : 자원을 식별할 때 해당 자원이 가진 컨텐츠의 특성으로 식별하는 방식. : ex) 안경쓴 사람,사용안함

URL - 절대경로 
protocol://ip[domain]:port/context/depth.../resourceName

client side : context path를 포함한 경로 형태.
server side : context path 이후의 경로만 사용함.

<img src="http://localhost/WebStudy01/resources/images/dog06.jpg" />	<!-- 절대경로 -->
<img src="//localhost/WebStudy01/resources/images/dog06.jpg" />		<!-- 프로토콜 생략 -->
<img src="<%=request.getContextPath() %>resources/images/dog06.jpg" />		<!--localhost 생략,  절대경로를 만들어 WebStudy01생략-->

상대경로 : 현재 문서의 출처를 기준으로 자원의 위치가 상대적으로 표현됨.
<img src="../resources/images/dog06.jpg" />
</body>
</html>