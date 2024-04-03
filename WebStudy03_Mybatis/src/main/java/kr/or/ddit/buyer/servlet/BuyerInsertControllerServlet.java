package kr.or.ddit.buyer.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.others.advice.OthersControllerAdvice;
import kr.or.ddit.utils.PopulateUtils;
import kr.or.ddit.utils.ValidateUtils;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.BuyerVO;

@WebServlet("/buyer/buyerInsert.do")
public class BuyerInsertControllerServlet extends HttpServlet{
	private BuyerService service = new BuyerServiceImpl();
	
	private OthersControllerAdvice advice = new OthersControllerAdvice();
	
	// view layer 로 연결하기 위한 컨트롤러
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		advice.addLprodList(req);
		
		String logicalViewName = "buyer/buyerForm";
		req.getRequestDispatcher("/"+logicalViewName+".miles").forward(req, resp);
	}
	
	// form 을 통해 전송된 데이터에 대한 처리를 위한 컨트롤러
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		advice.addLprodList(req);
		
//		1.특수문자 디코딩 설정
//		2. 18개의 파라미터를 받고 -> Command Object 로 캡슐화(BuyerVO)
		BuyerVO buyer = new BuyerVO();
		req.setAttribute("buyer", buyer);
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		PopulateUtils.populate(buyer, parameterMap);
		
		// 2-1. 검증 작업 : 통과, 불통
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		ValidateUtils.validate(buyer, errors, InsertGroup.class);
		
		String logicalViewName = null;
		if(errors.isEmpty()) {
	//		3. 등록 로직 호출
			service.createBuyer(buyer);
	//		4. 로직의 실행 결과에 따른 뷰 선택
			logicalViewName = "redirect:/buyer/buyerView.do?what="+buyer.getBuyerId(); // Post-Redirect-Get
		}else {
			logicalViewName = "buyer/buyerForm";
		}
		
		
//		5. 해당 뷰로 이동.
		if(logicalViewName.startsWith("redirect:")) {
			String redirectViewPath = req.getContextPath() + logicalViewName.substring("redirect:".length());
			resp.sendRedirect(redirectViewPath);
		}else {
			req.getRequestDispatcher("/"+logicalViewName+".miles").forward(req, resp);
		}
		
	}
}	
