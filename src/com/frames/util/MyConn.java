package com.frames.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MyConn {
	
	static String jdbcDriver= "oracle.jdbc.driver.OracleDriver";

	static String dbURL = "";
	static String MyUser="ecc";
	static String MyPassword="ecc";
	 
	 Statement stmt;
	static  Connection conn = null;
	 ResultSet rs = null;
	 PreparedStatement ps = null;
	 CallableStatement cs;
	 static{
		 InputStream in = MyConn.class.getClassLoader().getResourceAsStream("jdbc.properties");  
		   Properties p = new Properties();
		   try {
			   p.load(in);
			   dbURL= p.getProperty("jdbc.url");
			   MyUser= p.getProperty("jdbc.username");
			   MyPassword= p.getProperty("jdbc.password");
		   } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		   } 
		   
	 }
	  
	   public MyConn()
	     {
		   
		  
		   try 
	        {
		   		Class.forName(jdbcDriver);
		   		conn = DriverManager.getConnection(dbURL,MyUser,MyPassword);
		   		
	        }
		  catch(ClassNotFoundException e)
	        {
	        	System.err.println("MyConn(): " + e.getMessage());
	        	
	        }
		  catch(SQLException e)
	        {System.err.println("MyConn (): " + e.getMessage());
	        }
	     }
	   
	  
	  
	   public String getCardNumber(String value)
	   {
		   String IPString = "";
		   if (conn != null)
		   {
			  try
			  {
				  ps = conn.prepareStatement("select cn from cardnumber where ip = ?");
				  ps.setString(1, value);
				  rs = ps.executeQuery();
				  if (rs.next())
				  {
					 IPString = rs.getString("cn");
					 DelCardNumber(value);
				  }
			  }
			  catch(SQLException ex)
			  {
				  System.err.println(ex.getMessage());
			  }
		   }
		   return IPString;
		   
	   }
	   
	   public void DelCardNumber(String value)
	   {
		   if (conn != null)
		   {
			   try
			   {
				   ps = conn.prepareStatement("delete from cardnumber where ip = ?");
				   ps.setString(1, value);
				   ps.executeUpdate();
			   }
			   catch(SQLException ex)
			   {
				   System.err.println(ex.getMessage());
			   }
		   }
	   }
	   
	
	   public ResultSet executeQuery(String sql) 
	     {rs = null;
	      try
	        {
	         Statement stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);

	        }
	      catch(SQLException ex) 
	        {
	        	System.err.println("aq.executeQuery:"+ex.getMessage());
				
			}
	      return rs;
	     }

	  
	   public String executeUpdate(String sql) 
	     {rs = null; 
	   try { 
	       Statement stmt = conn.createStatement(); 
	       stmt.executeUpdate(sql); 
	       stmt.executeUpdate("commit");
	       stmt.close();
	       } 
	   catch(SQLException ex) { 
		   System.err.println("executeQuery: " + ex.getMessage());
		   return "0";
			}
	       return("executeUpdate ok"); 

	   }


	   
	   public CallableStatement executeProcedure(String procedure) 
	     {
	      try
	        {
	         cs = conn.prepareCall(procedure);

	        }
	      catch(SQLException ex) 
	        {System.err.println("aq.executeQuery:"+ex.getMessage());
			}
	      return cs;
	     }

	   	
		public void close() throws Exception {
			if (stmt != null)  {
				stmt.close();
				stmt = null;
			}
			conn.close();
			conn = null;
		}
		/**
		 * @return conn
		 */
		public Connection getConn() {
			return conn;
		}
		
		public void setConn(Connection conn) {
			this.conn = conn;
		}

}
