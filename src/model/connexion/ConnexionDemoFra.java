package model.connexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConnexionDemoFra {
	
	private ArrayList<String> tables;
	private Connection con;
	
	public ConnexionDemoFra() {
		this.tables = new ArrayList<String>();
		
		try {
			connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void connect() throws SQLException {
		String login = "esial";
        String password = "esial";
        
        try{
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	String connectionUrl = "jdbc:sqlserver://100.74.39.201:1433"
        			+ ";userName="+login+";password="+password+";databaseName=DEMOFRA";
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
    	  tables.add("DEMOFRA."+rs.getString(3));
    	}
	}

	public ArrayList<String> getTables() {
		return tables;
	}

	public Connection getCon() {
		return con;
	}
}


