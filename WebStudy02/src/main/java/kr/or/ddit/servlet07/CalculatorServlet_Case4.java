package kr.or.ddit.servlet07;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.enumpkg.OperatorType;
import kr.or.ddit.vo.CalculatorVO;

@WebServlet("/07/case4/calculator.do")
public class CalculatorServlet_Case4 extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int status = validate(req);		// 코드 실행 후 200이거나 200이 아님		// 콜바이레퍼런스(객체의 주소를 넘겨줌) , 콜바이벨류(값을 복사해서 넘겨줌)
		
		if(status == HttpServletResponse.SC_OK) { 
			String view = "/WEB-INF/views/07/calculateView.jsp";
			req.getRequestDispatcher(view).forward(req, resp);
		} else {	// 문제가 생김.. status에 의해 에러종류 내보냄
			resp.sendError(status);
		}
	}

	private int validate(HttpServletRequest req) {
		
		int status = HttpServletResponse.SC_OK;
			
		try(
			InputStream is =  req.getInputStream();
		){
			// 역직렬화, 언마샬링
			ObjectMapper objectMapper = new ObjectMapper();
			CalculatorVO calVO = objectMapper.readValue(is, CalculatorVO.class);
			
			req.setAttribute("calculator", calVO);
		} catch(Exception e) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}

		return status;
	}
}
