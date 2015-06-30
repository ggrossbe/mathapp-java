package com.client.math;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.client.math.MathClientServlet.Oper;
import com.mathapp.utilities.JsonHelperForMathApp;
import com.mathapp.utilities.MathResponseData;
import com.mathapp.utilities.ServletUtilities;

/**
 * Servlet implementation class MathDotNetServlet
 */
public class MathDotNetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MathDotNetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public enum Oper{
    	add,subtract,divide,multiply,stall,error;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		MathWSClient wsClient = new MathWSClient();
		
		switch (Oper.valueOf(request.getParameter("operation"))){
		case add: request.setAttribute("oper", "+");
		break;
		case multiply: request.setAttribute("oper", "x");
		}
		
		//request.setAttribute("operation", request.getParameter("operation"));
	    request.setAttribute("v1", request.getParameter("value1"));
	    request.setAttribute("v2", request.getParameter("value2"));
	    
	    String responseString = wsClient.getMathDotNetResult(request.getParameter("operation"),
				ServletUtilities.getIntParameter(request, "value1", 0),
				ServletUtilities.getIntParameter(request, "value2", 0));
	    
//		XmlHelperForMathApp xmlHelperForMathApp = new XmlHelperForMathApp();
		JsonHelperForMathApp jsonHelperForMathApp = new JsonHelperForMathApp();

		MathResponseData responseData = jsonHelperForMathApp.getMathResponse(responseString);
		
//		System.out.println("Result is: " + responseData.getResult());
//		System.out.println("Status is: " + responseData.getStatus());
	    
	    
	    request.setAttribute("result", responseData.getResult());
	    request.setAttribute("status", responseData.getStatus());
	    
	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/operation.jsp");
	    rd.forward(request, response);
	}
	    
	    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
