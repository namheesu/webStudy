package kr.or.ddit.member.servlet11;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.utils.PopulateUtils;
import kr.or.ddit.utils.ValidateUtils;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberUpdate.do")
public class MemberUpdateControllerServlet extends HttpServlet{
	private MemberService service = new MemberServiceImpl();
	// 현재 로그인한 사용자의 기본정보를 초기값으로 가지고 있는 수정 양식 제공 = form제공
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// session에서 초기값 가져오기
		MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
		MemberVO member = service.retrieveMember(authMember.getMemId());
		req.setAttribute("member", member);
		
		String logicalViewName = "member/memberEdit";
		req.getRequestDispatcher("/" + logicalViewName + ".miles").forward(req, resp);
	}
	
	// POST 파라미터로 수정 절차 진행
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		PopulateUtils.populate(member, parameterMap);	// 아이디는 비어있음 => 비어있는 id를 session에서 꺼내옴
		MemberVO authMember =  (MemberVO) req.getSession().getAttribute("authMember"); 
		member.setMemId(authMember.getMemId());
		
		

		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid =  ValidateUtils.validate(member, errors, UpdateGroup.class);
		
		String logicalViewName = null;
		if(errors.isEmpty()) {
			//3. 수정 로직 호출
				ServiceResult result = service.modifyMember(member);
				String message = null;
				switch (result) {
				case INVALIDPASSWORD:
					logicalViewName = "member/memberEdit";
					message = "인증 실패";
					break;
				case OK: 	// 수정 성공
					logicalViewName = "redirect:/mypage";
					break;
				default:	
					logicalViewName = "member/memberEdit";
					message = "서버 오류";
					break;
				}
				
				req.setAttribute("message", message);
			
		} else { // 검증에 통과하지 못함, 
			logicalViewName = "member/memberEdit";
		}
		
		
//		5. 해당 뷰로 이동.(경우에 따라)
		if(logicalViewName.startsWith("redirect:")) {
			String redirectViewPath = req.getContextPath() + logicalViewName.substring("redirect:".length());
			resp.sendRedirect(redirectViewPath);
			
		} else {
			req.getRequestDispatcher("/" + logicalViewName + ".miles").forward(req, resp);
		}
		
	}
	
}
