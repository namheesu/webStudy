package kr.or.ddit.servlet07;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.enumpkg.MediaType;
import kr.or.ddit.enumpkg.OperatorType;
import kr.or.ddit.vo.CalculatorVO;

/**
 * 응답을 JSON으로 전송하기 위해, Marshalling, Serialization이 필요함.
 *
 */
@WebServlet("/07/case3/calculator.do")
public class CalculatorServlet_Case3 extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int status = validate(req);		// 코드 실행 후 200이거나 200이 아님		// 콜바이레퍼런스(객체의 주소를 넘겨줌) , 콜바이벨류(값을 복사해서 넘겨줌)
		String accept = req.getHeader("accept");
		
		if(status == HttpServletResponse.SC_OK) { 
			String view = null;
			if(accept.contains("json")) {
				view = "/WEB-INF/views/07/calculateView.jsp";
			} else {
				req.getRequestDispatcher(view).forward(req, resp);
			}
			
		} else {	// 문제가 생김.. status에 의해 에러종류 내보냄
			resp.sendError(status);
		}
	}

	private int validate(HttpServletRequest req) {
		String left = req.getParameter("left");
		String rigth = req.getParameter("right");
		String operator = req.getParameter("operator");
		int status = HttpServletResponse.SC_OK;
			
		try {
			double leftOp = Double.parseDouble(left);
			double rightOp = Double.parseDouble(rigth);
			OperatorType operatorType = OperatorType.valueOf(operator);
			
			CalculatorVO calVO = new CalculatorVO(leftOp, rightOp, operatorType);
			req.setAttribute("calculator", calVO);
		} catch(Exception e) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}

		return status;
	}
}
