<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>서버에서 클라이언트로 전송되는 응답 형태 : HttpServletResponse</h4>
<pre>
	1. Response Line : status code
		status code : 요청 처리의 성공/실패 여부를 표현할 수 있는 세자리 숫자.
		
		1) 100 ~ : Http 프로토콜(Connect-Less, State-Less) / 
				ING...(아직 연결중,, 아직 상태가 진행중..) EX) 채팅.. , WebSocket 프로토콜에서 사용되고, 주로 양방향 실시간 통신이나 push 메세지 서비스에 활용됨.
				
		2) 200 ~ : OK(<%=HttpServletResponse.SC_OK %>)		
		3) 300 ~ : response body 가 없기 때문에 클라이언트가 응답을 받은 후 추가 액션을 행하게 됨.
			304 : Not Modified (<%=HttpServletResponse.SC_NOT_MODIFIED %>), 캐시 영역을 검색
			302/307 : Moved, 자원의 새로운 위치를 대상으로 새로운 요청을 전송함.----- => sendredirect?
			(브라우저는 정적자원을 받으면 캐싱(자기한테 저장하다)하는 성향이있다)
			응답을 받으면 클라이언트의 저장소에 저장하고 .. (네트워크 부하 발생 안함. 속도 느려지지 않음)
			r1을 서버가 갖고 있고 캐싱해놓음 , 내용이 변경되지 않았으면 300번대 (304->해당 내용에 대한 캐시를 찾기위해 저장소를 뒤질때)내보냄 -> body가 없음 
			302.. response의 body없음. 
		
		<!-- 클라이언트쪽에서 에러 -->
		4) 400 ~ : client side error	<!-- 클라이언트에 문제가 생겼다면 이유를 알려줘야함. 그 응답을 받고 수정을 해야 하기 때문에 400번대 에러는 자세히 알려줌 -->
			400	: <%=HttpServletResponse.SC_BAD_REQUEST %> : 요청이 잘못되었다. 필수파라미터 누락, 요청데이터의 타입 문제, 요청 데이터 길이가 너무 길때...(요청 검증시 의도적으로 전송)
			401/403 : <%=HttpServletResponse.SC_UNAUTHORIZED %> / <%=HttpServletResponse.SC_FORBIDDEN %> : 보안처리를 위해 사용 / 401:로그인페이지, 403:같은 직원일지라도 맡은 역할에 따라 접근권한이 생김
			404 : <%=HttpServletResponse.SC_NOT_FOUND %> : 클라이언트가 요청하는 정보가 없을때
			
			405 : <%=HttpServletResponse.SC_METHOD_NOT_ALLOWED %> : 서버사이드의 해당 콜백 메소드가 생성되지 않았을 때 ==> 모든 콜백메소드의 에러
			
			406 : <%=HttpServletResponse.SC_NOT_ACCEPTABLE %> : response-content랑 연관.........
				request Accept header (response Content-Type) 헤더를 처리할 수 없음. (=답장을 쓰지 못하겠다)
			415 : <%=HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE %> : 
				request Content-Type 헤더를 처리할 수 없음. (=네가 보낸 편지를 읽지 못하겠다.)
			
		5) 500 ~ : server side error	<!-- 대부분의 에러는 500, 인터넷 서버 에러 : 서버의 구체적인 에러를 나누지 않기 때문에/ -->
	
	2. Response Header (name/value)
		Content-*
		Content-Length : response body의 길이
		Content-Type : response body content's MIME
		
		1) Content-Type :response content MIME
		
		2) Cache-Control : 캐시 데이터 제어
		3) Refresh : auto-request
		
		4) Location : 자원의 위치가 새로운 곳으로 이동한 경우, 새로운 위치 정보를 제공하는 헤더(sendRedirect)
		
		
		
	3. Response Body(Content Body, Message Body) : 응답 컨텐츠 영역
</pre>
</body>
</html>