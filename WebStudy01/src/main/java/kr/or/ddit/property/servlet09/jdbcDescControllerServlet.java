package kr.or.ddit.property.servlet09;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.property.service.PropertyService;
import kr.or.ddit.property.service.PropertyServiceImpl;
import kr.or.ddit.vo.PropertyVO;

/**
 * 1. Model1 에서 Model2로 리팩토링
 * 2. 기존 property 패키지의 계층 구조 활용.
 * 3. 최종 뷰에서 layout.jsp에 있는 페이지 모듈화 구조 활용.
 *
 */
@WebServlet("/12/jdbcDesc.do")
public class jdbcDescControllerServlet extends HttpServlet{
	private PropertyService service = new PropertyServiceImpl();
	// 모델 1 ->> 모델 2
	// 레이어드 전체 구조 그대로 사용
	// view에서 부트스트랩 사용 table에 class로 디자인이 로드, 위에는 css, 아래는 jsp, includee
	// layout 사용해서 부트스트랩의 css사용
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PropertyVO> propList = service.retrieveProperties();
		req.setAttribute("propList", propList);
		req.setAttribute("contentPage", "/WEB-INF/views/12/jdbcDesc.jsp");
		
		String view = "/WEB-INF/views/layout.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
}
