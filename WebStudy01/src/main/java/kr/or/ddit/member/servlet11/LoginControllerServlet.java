package kr.or.ddit.member.servlet11;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.AuthenticateService;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/login/loginProcess.do")
public class LoginControllerServlet extends HttpServlet{
	private AuthenticateService service = new AuthenticateServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");
		MemberVO inputData = new MemberVO();	// service에 보낼 수 있게 vo사용
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		boolean valid = validate(inputData);	// 검증 
		String logicalViewName = null;
		String message = null;
		HttpSession session = req.getSession();		// request redirect가 아닌 sendRedirect를 사용, stateLess가 아닌 stateFull을 사용 , 로그인 한후 사용자 이름 없어지지 않음
		if(valid) {
			if(service.authenticate(inputData)) {
				if(session.isNew()) {
					message = "브라우저의 설정 오류, 쿠키 정보를 확인하세요.";
					logicalViewName = "/login/loginForm.jsp";
				}else {
					logicalViewName = "/";	// 로그인 성공 시 웰컴페이지로 이동
					session.setAttribute("authId", inputData.getMemId());
					int maxAge = Optional.ofNullable(req.getParameter("rememberMe"))
										.map(rv->60*60*24*3)
										.orElse(0);
					Cookie rememberMeCookie = new Cookie("rememberMe", inputData.getMemId());
					rememberMeCookie.setMaxAge(maxAge);
					rememberMeCookie.setPath(req.getContextPath());
					resp.addCookie(rememberMeCookie);
				}
			} else {
				message = "아이디 혹은 비밀번호 오류.";
				logicalViewName = "/login/loginForm.jsp";
			}
		} else {	
//			resp.sendError(400);
			message = "아이디 혹은 비밀번호 누락.";
			logicalViewName = "/login/loginForm.jsp";
		}
		session.setAttribute("message", message);
		resp.sendRedirect(req.getContextPath() + logicalViewName);	// 중복코드를 처리하기 위해
	}	
		// 파라미터 확보 2개
		// 2개로 로그인처리해야 하니깐 그에 따른 검증 , 400번 
		// 성공시 service를 통해 인증여부 파악 
		// 인증 통과시 콘솔에 띄우기 , 실패 시 콘솔에 띄우기

	private boolean validate(MemberVO inputData) {
		boolean valid = true;
		if(StringUtils.isBlank(inputData.getMemId())) {
			valid = false;
		}
		if(StringUtils.isBlank(inputData.getMemPass())) {
			valid = false;
		}
		return valid;
	}

}
