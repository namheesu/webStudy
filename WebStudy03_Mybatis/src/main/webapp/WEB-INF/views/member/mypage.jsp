<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	<!-- 커스텀태그만드는 법 , 1.1버전으로-->
	
<c:if test="${not empty sessionScope.message}">	<!--  ne null and message.length() gt 0 -->
	<span class="text-danger">${sessionScope.message }</span>
	<c:remove scope="session" var="message" />
</c:if>	

<%-- 위의 c:remove로 대체
	session.removeAttribute("message");	// flash attribute
--%>

<table class="table table-bordered">
	<!-- 
	1. index.jsp 에서 로그인 성공 시 mypage.jsp로 이동
	2. 로그인한 회원의 정보(이름, 아이디, 비밀번호, 이메일, 전화번호) 출력
 -->
	<tr>
		<th>회원아이디</th>
		<td>${requestScope.member.memId }</td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td>${member.memPass}</td>
	</tr>
	<tr>
		<th>이름</th>
		<td>${member.memName}</td>
	</tr>
	<tr>
		<th>주민번호1</th>
		<td>${member.memRegno1}</td>
	</tr>
	<tr>
		<th>주민번호2</th>
		<td>${member.memRegno2}</td>
	</tr>
	<tr>
		<th>생일</th>
		<td>${member.memBir}</td>
	</tr>
	<tr>
		<th>우편번호</th>
		<td>${member.memZip}</td>
	</tr>
	<tr>
		<th>주소1</th>
		<td>${member.memAdd1}</td>
	</tr>
	<tr>
		<th>주소2</th>
		<td>${member.memAdd2}</td>
	</tr>
	<tr>
		<th>집전번</th>
		<td>${member.memHometel}</td>
	</tr>
	<tr>
		<th>회사전번</th>
		<td>${member.memComtel}</td>
	</tr>
	<tr>
		<th>휴대폰</th>
		<td>${member.memHp}</td>
	</tr>
	<tr>
		<th>이메일</th>
		<td>${member.memMail}</td>
	</tr>
	<tr>
		<th>직업</th>
		<td>${member.memJob}</td>
	</tr>
	<tr>
		<th>취미</th>
		<td>${member.memLike}</td>
	</tr>
	<tr>
		<th>기념일</th>
		<td>${member.memMemorial}</td>
	</tr>
	<tr>
		<th>기념일자</th>
		<td>${member.memMemorialday}</td>
	</tr>
	<tr>
		<th>마일리지</th>
		<td>${member.memMileage}</td>
	</tr>
	<tr>
		<th>탈퇴여부</th>
		<td>${member.memDelete}</td>
	</tr>
	<tr>
		<td colspan="2">
			<button class="btn btn-danger" id="delBtn">탈퇴</button>
			<a class="btn btn-primary" href='<c:url value="/member/memberUpdate.do" />'>개인정보수정</a>
		</td>
	</tr>
</table>
<c:set var="cartList" value="${member.cartList }" />
<c:if test="${not empty cartList }">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>상품명</th>
				<th>판매가</th>
				<th>마일리지</th>
			</tr>
		</thead>
		<tbody>
<c:forEach items="${cartList }" var="cart">
			<tr>
				<td>
					<c:url value="/prod/prodView.do" var="viewUrl">
               			<c:param name="what" value="${cart.prod.prodId }" />
               			<c:param name="param" value="paramvalue" />
               		</c:url>
					<a href="${viewUrl }">${cart.prod.prodName }</a>
				</td>
				<td>${cart.prod.prodPrice }</td>
				<td>${cart.prod.prodMileage }</td>
			</tr>
</c:forEach>
		</tbody>
	</table>
</c:if>
<table>

</table>
<form name="deleteForm" method="post" action="${pageContext.request.contextPath }/member/memberDelete.do">
	<input type="hidden" name="password"	/>
</form>
<script>
	$(delBtn).on("click", function(){
		let password = prompt("비밀번호");
		if(password.length>0){
			document.deleteForm.password.value = password;
			$(document.deleteForm).submit();
		}
	});
</script>