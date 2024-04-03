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

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.others.advice.OthersControllerAdvice;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.utils.PopulateUtils;
import kr.or.ddit.utils.ValidateUtils;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/prod/prodUpdate.do")
public class ProdUpdateControllerServlet extends HttpServlet{
	private ProdService service = new ProdServiceImpl();
	private OthersControllerAdvice advice = new OthersControllerAdvice();
	
	// UI제공
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String prodId = req.getParameter("what");
		if(StringUtils.isBlank(prodId)) {
			resp.sendError(400, "필수 파라미터 누락");
			return;
		}
		ProdVO prod = service.retrieveProd(prodId);
		req.setAttribute("prod", prod);
		String logicalViewName = "prod/prodEdit";
		req.getRequestDispatcher("/" + logicalViewName + ".miles").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		PopulateUtils.populate(prod, parameterMap);
		
		log.info("===>\n{}", prod);
		
		// 검증작업
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		ValidateUtils.validate(prod,errors, UpdateGroup.class);
		
		String logicalViewName = null;
		if(errors.isEmpty()) {
			ServiceResult result = service.modifyProd(prod); 
			String message = null;
			switch(result) {
			case OK:
				logicalViewName = "redirect:/prod/prodView.do?what=" + prod.getProdId();
				break;
			case FAIL :
				logicalViewName = "prod/prodEdit";
				message = "서버 오류";
				break;
			}
		} else {
			logicalViewName = "prod/prodEdit";
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
