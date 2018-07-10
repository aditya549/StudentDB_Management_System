package com.cubic.dbproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	@Override
	public void init(ServletConfig config) throws ServletException {
	System.out.println("Initialization of Servlet");
			try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "reddy", "reddy");
			}catch (Exception e) {
				System.out.println(e);
			}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String sname=req.getParameter("aname");
		String spass=req.getParameter("apass");
		out.println("<h1 style='text-align:center; border:dotted;'>Welcome To Student Display</h1>");
		HashMap<String, String> hm=new HashMap<>();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("select * from studentdetails");
		ResultSet rs=pst.executeQuery();
		while(rs.next()) {
			hm.put(rs.getString(1),rs.getString(2));
		}
		String pass=hm.get(sname);
		if(hm.containsKey(sname)&&pass.equals(spass)) {
			PreparedStatement pst1=con.prepareStatement("select * from studentdetails where sname=? and spassword=?");
			pst1.setString(1, sname);
			pst1.setString(2, spass);
			ResultSet rs1=pst1.executeQuery();
			out.println("<table align='center' border='2'>");
			while(rs1.next()) {
				
				out.println("<th>Subject</th>");
				out.println("<th>Marks</th>");
				out.println("<h4 style='text-align:center'>Student Name :" +rs1.getString("sname")+"</h4>");
			out.println("<tr><td>Student Telugu Marks:</td><td>"+rs1.getString("telugu")+"</td></tr>");
			out.println("<tr><td>Student Hindi Marks  :</td><td> "+rs1.getString("hindi")+"</td></tr>");
			
			out.println("<tr><td>Student English Marks: </td><td>"+rs1.getString("english")+"</td></tr>");
			
			out.println("<tr><td>Student social Marks : </td><td>"+rs1.getString("social")+"</td></tr>");
			
			out.println("<tr><td>Student science Marks: </td><td>"+rs1.getString("science")+"</td></tr>");
			
			out.println("<tr><td>Student maths Marks  : </td><td>"+rs1.getString("maths")+"</td></tr>");
			
			out.println("<tr><th>Total Marks :</th><th>"+rs1.getString("total")+"</th></tr>");
			out.println("<tr><th>Percentage  :</th><th>"+rs1.getString("percent")+"</th></tr>");
			}
			out.println("</table>");
		}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
		@Override
		public void destroy() {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
}