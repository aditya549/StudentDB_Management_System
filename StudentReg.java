package com.cubic.dbproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentReg
 */
@WebServlet("/StudentReg")
public class StudentReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
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
		String sname=req.getParameter("sname");
		String spass=req.getParameter("spass");
		int tmark=Integer.parseInt(req.getParameter("tmarks"));
		int hmark=Integer.parseInt(req.getParameter("hmarks"));
		int emark=Integer.parseInt(req.getParameter("emarks"));
		int somark=Integer.parseInt(req.getParameter("somarks"));
		int smark=Integer.parseInt(req.getParameter("smarks"));
		int mmark=Integer.parseInt(req.getParameter("mmarks"));
		
		int total=tmark+hmark+emark+somark+smark+mmark;
		double percent=(total/6);
		try {
			PreparedStatement pst1 = con.prepareStatement("insert into Studentdetails values(?,?,?,?,?,?,?,?,?,?)");
		pst1.setString(1, sname);
		pst1.setString(2, spass);
		pst1.setInt(3, tmark);
		pst1.setInt(4, hmark);
		pst1.setInt(5, emark);
		pst1.setInt(6, somark);
		pst1.setInt(7, smark);
		pst1.setInt(8, mmark);
		pst1.setInt(9, total);
		pst1.setDouble(10, percent);
		int i=pst1.executeUpdate();
		if(i==0) {
			out.println("Student Record Not Inserted");
		}
		else
			out.println("Student Records Are Inserted Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
