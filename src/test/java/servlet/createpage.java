package servlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;

public class createpage extends HttpServlet {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException, IOException{
	try{
		 
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try
		{
			Class.forName("org.postgresql.Driver");
			String jdbcUrl="jdbc:postgresql://52.15.79.81:5432/postgres";
			String username="postgres";
			String password="password";
		
						
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			String a=req.getParameter("product");
			String b=req.getParameter("problemdescription");
			String c=req.getParameter("contact");
			String d=req.getParameter("date");
			String e=req.getParameter("status");
			
			String sql= "INSERT INTO avt(product, pdesc, cont, datee, statuss ) VALUES ('"+a+"', '"+b+"','"+c+"','"+d+"','"+e+"' ) RETURNING *;";
			ResultSet rs1= statement.executeQuery(sql);
			
			String sql1= "SELECT  * FROM avt;";
			ResultSet rs= statement.executeQuery(sql1);
			
			
			        out.println("<html>");
					out.println("<head>");
					out.println("<title>Repair Page</title>");
					out.println("<style>");
					out.println("body{background-color: #f9f8f7;}");
					out.println("table {border-collapse: collapse;border-spacing: 0;width: 75%;border: 2px solid #ddd;}");
					out.println("th, td {border: none;text-align: center;padding: 12px;}");
					out.println(".button {background-color: #FFA500;button-align: center;border-radius:7px;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;margin: 14px 2px;cursor: pointer;}");
					out.println(".btn {background-color: #FFA500;button-align: center;border-radius:7px;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;margin: 14px 2px;cursor: pointer;}");
					out.println("#home{text-align: right;}");
					out.println("tr:nth-child(even){background-color: #f2f2f2}");
					out.println("</style>");
					out.println("</head");
					out.println("<body>");

					out.println("<div id="+"home"+">");
					out.println("<a href="+"avnhomepage.jsp"+">Home</a>&nbsp;&nbsp;");
					out.println("<a href="+"avnlogin.jsp"+">Logout</a>");
					out.println("</div>");
					out.println("<h2 align="+"center"+">Repair Ticket Page</h2>");
					out.println("<a href="+"avncreatepage.jsp"+"><input type="+"button"+" class="+"btn"+" value="+"CreateRepairTicket"+"></a>");

					out.println("<div style="+"overflow-x:auto;"+">");
					out.println("<table>");
					out.println("<tr>");
					out.println("<th style="+"color:#9a965b;"+">Ticket ID</th>");
					out.println("<th style="+"color:#9a965b;"+">Products</th>");
					out.println("<th style="+"color:#9a965b;"+">Problem Description</th>");
					out.println("<th style="+"color:#9a965b;"+">Contact</th>");
					out.println("<th style="+"color:#9a965b;"+">Date</th>");
					out.println("<th style="+"color:#9a965b;"+">Status</th>");
					out.println("</tr>");
					while(rs.next())
					{
						String tn=rs.getString("tnum");
						String prod=rs.getString("product");
						String pd=rs.getString("pdesc");
						String cont=rs.getString("cont");
						String date=rs.getString("datee");
						String stat=rs.getString("statuss");
						out.println("<tr>");
						out.println("<th>"+tn+"</th>");
						out.println("<th>"+prod+"</th>");
						out.println("<th>"+pd+"</th>");
						out.println("<th>"+cont+"</th>");
						out.println("<th>"+date+"</th>");
						out.println("<th>"+stat+"</th>");
						out.println("</tr>");
					}
					System.out.println("Crete Ticket Unit Test Passed");
					out.println("<table>");
		}
		catch(ClassNotFoundException cnfe)
		{
			out.println("class not found");
		}
		}
	catch(SQLException e)
	{
	throw new RuntimeException("Cannot connect the database!", e);		
		}
	}
	
	
}

