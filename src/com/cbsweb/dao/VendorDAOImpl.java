package com.cbsweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.sqlite.SQLiteConnection;

import com.cbsweb.rest.data.Vendor;

public class VendorDAOImpl {
	
	static VendorDAOImpl dao = null;
	Connection c = null;
	
	private VendorDAOImpl() {
		this.init();
	}
	
	public static VendorDAOImpl getInstance() {
		if(dao == null) {
			dao = new VendorDAOImpl();
		}
		return dao;
	}
	
	public Connection getConnection() {
		if(c == null)
			try {
				c = DriverManager.getConnection("jdbc:sqlite:E:/cbs.db");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return c;
	}
	
	public void init() {
		Connection c = null;
		Statement stmt = null;
		try {
		  Class.forName("org.sqlite.JDBC");
		  
		  /*stmt = c.createStatement();
		  String sql = "DROP TABLE VENDOR";
		  stmt.executeUpdate(sql);*/
	      
		  c = getConnection();
		  stmt = c.createStatement();
		  String sql = "CREATE TABLE VENDOR " +
	                   "(" +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " EMAIL          TEXT	NOT NULL, " + 
	                   " PHNO        	TEXT 	NOT NULL, " + 
	                   " PWD			TEXT    NOT NULL,"  + 
	                   " PRIMARY KEY(EMAIL,PHNO))"; 
	      stmt.executeUpdate(sql);	      
		} catch ( Exception e ) {
		  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		} finally {
			try {
				stmt.close();				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Opened database/table successfully");
	}
	
	public Vendor getVendor(String loginid) {
		Connection c = null;
	    PreparedStatement stmt = null;
	    Vendor vendor = null;
	    try {
	      c = getConnection();
	      c.setAutoCommit(false);
	      System.out.println("Opened daaatabase successfully");

	      stmt = c.prepareStatement("SELECT * FROM VENDOR WHERE email=?");
	      stmt.setString(1, loginid);
	      ResultSet rs = stmt.executeQuery();
	      if (!rs.next()) {
		      stmt = c.prepareStatement("SELECT * FROM VENDOR WHERE phno=?");
		      stmt.setString(1, loginid);
		      rs = stmt.executeQuery();
	      } else {
	    	  stmt = c.prepareStatement("SELECT * FROM VENDOR WHERE email=?");
		      stmt.setString(1, loginid);
		      rs = stmt.executeQuery();
	      }
	      
	      if (rs.next()) {
	         //int id = rs.getInt("id");
	         String name = rs.getString("name");
	         String email = rs.getString("email");
	         String phno = rs.getString("phno");
	         String pwd = rs.getString("pwd");
	         vendor = new Vendor(name, email, phno, pwd);
	      }
	      rs.close();
	      
	      System.out.println("Got vendor successfully");
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    } finally {	    	
		    try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    return vendor;
	  
	
	}
	
	public boolean loginVendor(String loginid, String pwd) {
		Vendor vendor = getVendor(loginid);
		if(vendor != null) {
			String passwd = vendor.getPwd();
			if(passwd != null && passwd.equals(pwd)) 
				return true;
		}
		return false;
	}
	
	public boolean registerVendor(Vendor vendor) {
		Connection c = null;
	    Statement stmt = null;
	    try {
	      c = getConnection();
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "INSERT INTO VENDOR (NAME,EMAIL,PHNO,PWD) " +
	                   "VALUES ("
	                   + "'" + vendor.getName() + "',"
	                   + "'" + vendor.getEmail() + "',"
	                   + "'" + vendor.getPhno() + "',"
	                   + "'" + vendor.getPwd() + "'"
	                   + ");"; 
	      stmt.executeUpdate(sql);

	      System.out.println("Registered vendor successfully. " + vendor);
	      return true;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return false;
	    } finally {
	    	try {
				stmt.close();
				c.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	      
		      
	    }
	}
	
}
