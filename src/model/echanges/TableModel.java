package model.echanges;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 
 * @author cottin4u
 * TableModel a pour but de remplir une JTable repr�sentant la recherche dans plusieurs Table d'une base de donn�es.
 * Cette classe pourra �tre utilis�e pour les articles et les ordres de fabrication, ou encore autre chose.
 * 
 * sqlFile est le fichier sur lequel cette classe construira les requ�tes pour interroger les tables
 * sqlDoc est la repr�sentation de ce document au format XML
 * colonnesNames est une HashMap permettant de stocker l'indice de la colonne et le nom de celle-ci
 * data est un tableau n*m stockant les r�sultats de la requ�te
 * databaseName est le nom de la base de donn�e dans laquelle effectuer les recherches
 * nomRecherche est le nom recherch� sur lequel s'effectue la condition
 * colonneRecherche est la colonne sur laquelle s'effectue la condition
 * tableRecherche est la table sur laquelle s'effectue la condition
 */
public class TableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File sqlFile;
	private Document sqlDoc;
	private HashMap<Integer, String> colonneNames;
	private ArrayList<ArrayList<String>> data;
	private String databaseName, nomRecherche, colonneRecherche, tableRecherche;
	private Connection con;
	
	public TableModel(String sqlFile, String databaseName, Connection con, String tableRecherche, String colonneRecherche, String nomRecherche) {
		this.sqlFile = new File(sqlFile);
		this.databaseName = databaseName;
		this.nomRecherche = nomRecherche;
		this.colonneRecherche = colonneRecherche;
		this.tableRecherche = tableRecherche;
		this.con = con;
		SAXBuilder sax = new SAXBuilder();
		try {
			sqlDoc = sax.build(this.sqlFile);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		colonneNames = new HashMap<Integer, String>();
		data = new ArrayList<ArrayList<String>>();
		
		//Remplissage de la table si une recherche est demand�e <=> si nomRecherche != null
		if (nomRecherche != null) {
			try {
				executeRequetes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	private void executeRequetes() throws SQLException {
		String sql = "SELECT ";
		List<Element> tables = sqlDoc.getRootElement().getChildren();
		Statement stmt = con.createStatement();
		String nomTable = null;
		Element condition=null;
		//Pour chaque table
		for (Element table : tables) {
			nomTable = databaseName+"."+table.getAttributeValue("nom");
			/**
			 * Cr�ation de la requ�te
			 */
			//Si on ne s�lectionne pas toutes les colonnes <=> pas de select *
			if (table.getAttribute("all") == null) {
				//On s�lectionne toutes les colonnes qui nous int�ressent
				for (Element colonne : table.getChildren("colonne")) {
					sql += colonne.getAttributeValue("nom")+", ";
				}
				//On enl�ve la derni�re virgule
				sql = sql.substring(0, sql.length()-2);
			}
			//Sinon, si select *
			else
				sql += "*";
			//On rajoute la table
			sql += " FROM "+nomTable;
			
			//On rajoute les conditions de jointures si il y en a
			sql += " WHERE ";
			condition = table.getChild("condition");
			//Si il y a une condition de jointure
			if (!condition.getText().equals("")) 
				sql += condition.getText() + " AND ";
			sql += tableRecherche+"."+colonneRecherche+" LIKE '"+nomRecherche+"'";
			//On finalise la requ�te
			sql += ";";
			
			/**
			 * Ex�cution et ajout des donn�es
			 */
			//On ex�cute
			
			System.out.println("SQL : "+sql);
			ResultSet res = stmt.executeQuery(sql);
			
			//On r�cup�re les r�sultats, qu'on ajoute dans les colonnes
			int nbColonnes = res.getMetaData().getColumnCount();
			//Ajout des noms de colonnes
			for (int i = 1; i <= nbColonnes; i++) {
				colonneNames.put(colonneNames.size(), nomTable+"."+res.getMetaData().getColumnLabel(i));
			}
			//Ajout des donn�es
			while (res.next()) {
				ArrayList<String> ligne = new ArrayList<String>();
				for (int i = 1; i <= nbColonnes; i++) {
					ligne.add(res.getObject(i).toString());
				} 
				data.add(ligne);
			}
			res.close();
		}//Fin du for sur les tables
	}
	
	@Override
	public int getColumnCount() {
		return colonneNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
	public HashMap<Integer, String> getColonneNames() {
		return colonneNames;
	}
	
	public String getColumnName(int columnIndex) {
	    return colonneNames.get(columnIndex).substring(databaseName.length()+1);
	}

	public String getNomRecherche() {
		return nomRecherche;
	}

	public void setNomRecherche(String nomRecherche) {
		data.clear();
		colonneNames.clear();
		this.nomRecherche = nomRecherche;
		if (nomRecherche!= null) {
			try {
				executeRequetes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			fireTableDataChanged();
		}
	}
	
	public ArrayList<String> getData() {
		return data.get(0);
	}

}
