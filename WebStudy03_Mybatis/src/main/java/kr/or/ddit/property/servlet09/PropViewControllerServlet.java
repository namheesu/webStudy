package kr.or.ddit.property.servlet09;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.property.service.PropertyService;
import kr.or.ddit.property.service.PropertyServiceImpl;

@WebServlet("/09/propView.do")
public class PropViewControllerServlet extends HttpServlet{
	private PropertyService service = new PropertyServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String logicalViewName = "09/propView";
		req.getRequestDispatcher("/" + logicalViewName + ".miles").forward(req, resp);
		
	}
}
