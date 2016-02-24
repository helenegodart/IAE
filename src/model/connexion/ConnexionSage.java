package model.connexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class ConnexionSage {
	
	private ArrayList<String> tables;
	private Connection con;
	private String databaseName;
	
	public ConnexionSage() {
		this.tables = new ArrayList<String>();
		this.databaseName = "sagex3";
		Date debut = new Date();
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Date fin = new Date();
		System.out.println("temps connexion : "+(fin.getTime()-debut.getTime())+"ms");
	}
	
	private void connect() throws SQLException {
		String login = "tpsage";
        String password = "tpsage";
        
        try{
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	String connectionUrl = "jdbc:sqlserver://100.74.39.206\\sagex3"
        			+ ";userName="+login+";password="+password+";databaseName="+databaseName;
        	con = DriverManager.getConnection(connectionUrl);
          
        }
        catch(SQLException sqle){
           sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		DatabaseMetaData md = con.getMetaData();
    	ResultSet rs = md.getTables(null, null, "%", null);
    	while (rs.next()) {
    	  tables.add(databaseName+rs.getString(3));
    	}
	}

	public ArrayList<String> getTables() {
		return tables;
	}

	public Connection getCon() {
		return con;
	}
}


