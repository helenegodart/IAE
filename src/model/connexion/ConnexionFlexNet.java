package model.connexion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConnexionFlexNet {
	
	private XMLOutputter sortie;
	private ArrayList<String> tables;
	private Connection con;
	private String texte, connexionUrl;
	
	public ConnexionFlexNet() {
		String login = "tpflexnet";
        String password = "tpflexnet";
		tables = new ArrayList<String>();
		texte = "";
		connexionUrl = "jdbc:sqlserver://100.74.39.207:1433"
    			+ ";userName="+login+";password="+password+";";
		sortie = new XMLOutputter(Format.getPrettyFormat());
		connect();
		
//		try {
//			ecrireFichierData();
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	private void connect() {
        try{
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	con = DriverManager.getConnection(connexionUrl);
          //interaction avec la base
        	
        	DatabaseMetaData md = con.getMetaData();
        	ResultSet rs = md.getTables(null, null, "%", null);
        	while (rs.next()) {
        	  tables.add(rs.getString(3));
        	}
        	
        }
        catch(SQLException sqle){
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
           sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void test() throws SQLException {
		//Initialisation du document XML
		Element racine = new Element("racine");
		Document doc = new Document(racine);
		
		//Requête
		ResultSet res = null;
		Statement stmt = con.createStatement();
		res = stmt.executeQuery("select * from dbo.ADDRESS");
		int nbColonnes = res.getMetaData().getColumnCount();
		System.out.println("nb de col : "+nbColonnes);
		Element table = new Element("table");
		table.setAttribute("nom", "dbo.ADDRESS");
		int numeroLigne = 1;
		while(res.next()) {
			Element ligne = new Element("ligne");
			ligne.setAttribute("numero", numeroLigne+"");
			for (int i = 1; i <= nbColonnes; i++) {
				Element colonne = new Element("colonne");
				colonne.setAttribute("nomColonne", res.getMetaData().getColumnName(i));
				colonne.setText(String.valueOf(res.getObject(i)));
				ligne.addContent(colonne);
			}
			table.addContent(ligne);
		}
		racine.addContent(table);
		
		
	}

	public void ecrireFichierData(String dirPath) throws SQLException {
		File dir = new File(dirPath);
		dir.mkdir();
		
		String sql = "";
		ResultSet res = null;
		int nbColonnes = 0;
		Statement stmt = null;
		
		PreparedStatement test = null;
		
		int avancement = 0;
		boolean ok = true;
		for (String string : tables) {
			Element racine = new Element("racine");
			Document doc = new Document(racine);
//			System.out.println("ok : "+ok+", con : "+con.toString());
			ok = true;
			System.out.println((new Double(avancement*100))/(new Double(tables.size()))+" % : "+string);
			avancement++;
			 try {
				 test = con.prepareStatement("select * from "+string);
				 try {
						test.executeQuery();
					} catch (OutOfMemoryError e) {
						ok = false;
						System.err.println("Out of Memory error 1");
//						con = DriverManager.getConnection(connexionUrl);
						System.gc();
					}
			} catch (SQLException e) {
				ok = false;
				e.printStackTrace();
			}
			 
			if (ok) {
//				System.out.println("test ok");
				stmt = con.createStatement();
				sql = "select * from "+string;
				try {
					res = stmt.executeQuery(sql);
				} catch (OutOfMemoryError e) {
					ok = false;
					System.err.println("Out of Memory error 1");
//					con = DriverManager.getConnection(connexionUrl);
					System.gc();
				}
				
				if (ok) {
//					System.out.println("select ok");
					nbColonnes = res.getMetaData().getColumnCount();

					Element table = new Element("table");
					table.setAttribute("nom", string);
					int numeroLigne = 1;
					
					while(res.next()) {
						Element ligne = new Element("ligne");
						ligne.setAttribute("numero", numeroLigne+"");
						for (int i = 1; i <= nbColonnes; i++) {
							Element colonne = new Element("colonne");
							colonne.setAttribute("nomColonne", res.getMetaData().getColumnName(i));
							colonne.setText(String.valueOf(res.getObject(i)));
							ligne.addContent(colonne);
						}
						numeroLigne++;
						table.addContent(ligne);
					}
//					System.out.println("fin du while");
					racine.addContent(table);
//					System.out.println("add content");
//					stmt.clearBatch();
//					stmt.close();
//					test.clearParameters();
//					test.clearWarnings();
//					test.clearBatch();
//					test.close();
//					res.close();
					
					try {
//						System.out.println("try enregistre");
						enregistre(doc, new File(dir.getAbsoluteFile()+"\\"+string+".xml"));
					} catch (IOException e) {

					}
				}
				else
					ok = true;
			}
			else ok = true;
		}
	}
	
	private void enregistre(Document doc, File fichier) throws IOException {
		      //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
		      //avec en argument le nom du fichier pour effectuer la sérialisation.
//		System.out.println("écriture du fichier : "+fichier.getName());
		      sortie.output(doc, new FileOutputStream(fichier));
	}

	public ArrayList<String> getTables() {
		return tables;
	}

	public Connection getCon() {
		return con;
	}
}
