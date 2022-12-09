package com.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/employee")
public class EmployeeDetails extends HttpServlet{

	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","");
		return con;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	EmployeeDetails em=new EmployeeDetails();	
	Connection con=null;
	Statement stmt=null;
	
	String query=null;
	String id=req.getParameter("id");
	String fname = req.getParameter("fname");
	String Dob=req.getParameter("Dob");
	
	String email=req.getParameter("email");
	
	String contact=req.getParameter("contact");
	
	System.out.println(id+","+fname +","+Dob+","+email+","+contact);
	
	try {
		con=em.getConnection();
	} catch (ClassNotFoundException | SQLException e) {
	
		e.printStackTrace();
	}
	

	
	query="INSERT INTO emp(id,fname,Dob,email,contact) VALUES('"+id+"','"+fname+"','"+Dob+"','"+email+"',"+contact+");";
	System.out.println(query);		
	
	try {
		stmt = con.createStatement();
		stmt.executeUpdate(query);
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	try {
		con=em.getConnection();
		stmt =con.createStatement();
		query="select * from emp";
		
		ResultSet rs = stmt.executeQuery(query);
		
		resp.setContentType("text/html");
		
		PrintWriter pvalue=  resp.getWriter();
		
		pvalue.println("<h1><p>Employee Data inserted Successfully</p></h1>");
		
		
		
		
		while(rs.next())
		{
			pvalue.print("<h1>"+rs.getString(1)+","+
					rs.getString(2)+","+rs.getString(3)+","+
					rs.getString(4)+","+rs.getString(5)+"</h1>");
			
			System.out.println();
		}
		
		
		pvalue.flush();
	
	
	} catch (Exception e) {
		
		e.printStackTrace();
	} 
	
	System.out.println("Done");
	
	}	
	
}
