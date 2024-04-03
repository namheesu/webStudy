package kr.or.ddit.prod.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.others.advice.OthersControllerAdvice;
import kr.or.ddit.others.dao.OthersDAOImpl;
import kr.or.ddit.others.dao.OthersDAO;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.utils.PopulateUtils;
import kr.or.ddit.utils.ValidateUtils;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/prod/prodInsert.do")
public class ProdInsertControllerServlet extends HttpServlet{
	private ProdService service = new ProdServiceImpl();
	
	private OthersControllerAdvice advice = new OthersControllerAdvice();
	
	// view layer로 연결하기 위한 컨트롤러
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		advice.addLprodList(req);
		advice.addBuyerList(req);
		String logicalViewName = "prod/prodForm";
		req.getRequestDispatcher("/" +logicalViewName + ".miles").forward(req, resp);
	}
	
	// form을 통해 전송된 데이터에 대한 처리를 위한 컨트롤러(검증 포함(작성중인))
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 4개의 모델 모두 가져감
		advice.addLprodList(req);
		advice.addBuyerList(req);
		
		// 1. 특수문자 디코딩설정
		// 2. PROD_ID를 뺀 19개의 파라미터 받고 -> Command Object로 캡슐화(ProdVO)
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		PopulateUtils.populate(prod, parameterMap);
		
		log.info("===>\n{}", prod);
		
		// 검증작업
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		ValidateUtils.validate(prod,errors, InsertGroup.class);
		
		String logicalViewName = null;
		if(errors.isEmpty()) {
			ServiceResult result = service.createProd(prod);
			String message = null;
			switch(result) {
			case OK:
				logicalViewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
				break;
			case FAIL :
				logicalViewName = "prod/prodForm";
				break;
			}
		} else {
			logicalViewName = "prod/prodForm";
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
