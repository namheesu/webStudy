<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main class="container-fluid">
<h4>웰컴 페이지 : 누적방문자수 : ${userCount }명</h4>
<c:set var="memberWrapper" value="${pageContext.request.userPrincipal }" />
<c:if test="${empty memberWrapper }">
	<a href="${pageContext.request.contextPath }/login/loginForm.jsp">로그인</a>
	<a href="${pageContext.request.contextPath }/member/memberInsert.do">회원가입</a>
</c:if>
<c:if test="${not empty memberWrapper }">
	<c:set var="authMember" value="${memberWrapper.realUser }"/>
	<a href="${pageContext.request.contextPath }/mypage">${authMember.memName }[${authMember.memRole }]</a> 
	<form method="post" id="logoutForm" action="${pageContext.request.contextPath }/login/logOut.do"></form>
	<a href="javascript:;" data-log-out="#logoutForm">로그아웃</a>
</c:if>
<h4>엔터프라이즈 프레임워크(Spring) 사용</h4>
<dl>
	<dt>Spring-context(DI container)</dt>
	<dd>
		Spring DI 컨테이너를 기반으로 어플리케이션 내부에서 의존적 코드에 의해 발생하는 결합력을 낮춤.
	</dd>
	<dt>Spring-ORM, Spring-mybatis</dt>
	<dd>
		매퍼 프레임워크인 마이바티스와 스프링을 연동하고, 매퍼 스캐너를 기반으로 전역적으로 관리되는 매퍼 프록시를 자동 생성함. 
	</dd>
	<dt>Spring-AOP, AspectjWeaver</dt>
	<dd>
		AOP 개발 방법론을 기반으로 한 선언적 프로그래밍 기법으로 트랜잭션 관리나 로깅과 같은 CCC 으로 인해 발생하는 중복을 해결함.
	</dd>
	<dt>Spring-webmvc</dt>
	<dd>
		웹상의 요청을 Model2+MVC 아키텍처 패턴을 기반으로 처리하기 위해 Front-Controller Pattern 인 DispatcherServlet 을 엔트리 포인트로 한 스프링의 MVC 프로그래밍 모델을 사용함.  
	</dd>
</dl>
</main>
<script src="${pageContext.request.contextPath}/resources/js/app/index.js"></script>

