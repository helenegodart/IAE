package model.connexion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.IllegalDataException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class DataWriter {

	private File dir;
	private Connection con;
	private ArrayList<String> tables;
	private XMLOutputter sortie;
	private ArrayList<String> exceptions;

	public DataWriter(String dir, Connection con, ArrayList<String> tables) {
		this.dir = new File(dir);
		this.con = con;
		this.tables = tables;

		sortie = new XMLOutputter(Format.getPrettyFormat());

		initExceptions();
	}

	public void ecrire() throws SQLException {
		dir.mkdir();

		String sql = "";
		ResultSet res = null;
		int nbColonnes = 0;
		Statement stmt = null;

		PreparedStatement test = null;

		int avancement = 0;
		boolean ok = true;
		for (String string : tables) {
			if (!exceptions.contains(string)) {
				Element racine = new Element("racine");
				Document doc = new Document(racine);
				//				System.out.println("ok : "+ok+", con : "+con.toString());
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
						//							con = DriverManager.getConnection(connexionUrl);
						System.gc();
					}
				} catch (SQLServerException e) {
					// Ne rien afficher (table sur lesquelles on ne peut requeter)
					ok = false;
					System.err.println("ServerException : "+string);

				} catch (SQLException e) {
					ok = false;
					e.printStackTrace();
				} 

				if (ok) {
					//					System.out.println("test ok");
					stmt = con.createStatement();
					sql = "select * from "+string;
					try {
						res = stmt.executeQuery(sql);

					} catch (SQLServerException e) {
						// Ne rien afficher (table sur lesquelles on ne peut requeter)
						ok = false;
					} catch (OutOfMemoryError e) {
						ok = false;
						System.err.println("Out of Memory error 1");
						//						con = DriverManager.getConnection(connexionUrl);
						System.gc();
					}

					if (ok) {
						//						System.out.println("select ok");
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
								try {
									colonne.setText(String.valueOf(res.getObject(i).toString()));
								} catch (IllegalDataException e) {
									colonne.setText("!ERROR!");
								}
								ligne.addContent(colonne);
							}
							numeroLigne++;
							table.addContent(ligne);
						}
						//						System.out.println("fin du while");
						racine.addContent(table);
						//						System.out.println("add content");
						//						stmt.clearBatch();
						//						stmt.close();
						//						test.clearParameters();
						//						test.clearWarnings();
						//						test.clearBatch();
						//						test.close();
						//						res.close();

						try {
							//							System.out.println("try enregistre");
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
	}

	private void enregistre(Document doc, File fichier) throws IOException {
		//Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
		//avec en argument le nom du fichier pour effectuer la sérialisation.
		//		System.out.println("écriture du fichier : "+fichier.getName());
		sortie.output(doc, new FileOutputStream(fichier));
	}

	private void initExceptions() {
		exceptions = new ArrayList<String>();

		exceptions.add("VueSage");
	}

}
