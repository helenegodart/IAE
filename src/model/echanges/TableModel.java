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

import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;

/**
 * 
 * @author cottin4u
 * TableModel a pour but de remplir une JTable représentant la recherche dans plusieurs Table d'une base de données.
 * Cette classe pourra être utilisée pour les articles et les ordres de fabrication, ou encore autre chose.
 * 
 * sqlFile est le fichier sur lequel cette classe construira les requêtes pour interroger les tables
 * sqlDoc est la représentation de ce document au format XML
 * colonnesNames est une HashMap permettant de stocker l'indice de la colonne et le nom de celle-ci
 * data est un tableau n*m stockant les résultats de la requête
 * databaseName est le nom de la base de donnée dans laquelle effectuer les recherches
 * nomRecherche est le nom recherché sur lequel s'effectue la condition
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
	private HashMap<String, Integer> colonneNamesIndex;
	private ArrayList<ArrayList<String>> data;
	private String databaseName, nomRecherche, colonneRecherche, tableRecherche;
	private Connection con;
	private ArrayList<String> caracteresAEviter;
	private String[] lettres = {"b", "c", "d", "e", "f", "g", "h", "i", "j"};
	private HashMap<String, String> lettreNom;
	
	public TableModel(String sqlFile, String databaseName, Connection con, String tableRecherche, String colonneRecherche, String nomRecherche) {
		this.sqlFile = new File(sqlFile);
		this.databaseName = databaseName;
		this.nomRecherche = nomRecherche;
		this.colonneRecherche = colonneRecherche;
		this.tableRecherche = tableRecherche;
		this.con = con;
		this.caracteresAEviter = new ArrayList<String>();
		this.lettreNom = new HashMap<String, String>();
		initialiseCaracteres();
		SAXBuilder sax = new SAXBuilder();
		try {
			sqlDoc = sax.build(this.sqlFile);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		colonneNames = new HashMap<Integer, String>();
		colonneNamesIndex = new HashMap<String, Integer>();
		data = new ArrayList<ArrayList<String>>();
		
		//Remplissage de la table si une recherche est demandée <=> si nomRecherche != null
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
			 * Création de la requête
			 */
			//Si on ne sélectionne pas toutes les colonnes <=> pas de select *
			if (table.getAttribute("all") == null) {
				//On sélectionne toutes les colonnes qui nous intéressent
				for (Element colonne : table.getChildren("colonne")) {
					sql += "a."+colonne.getAttributeValue("nom")+", ";
				}
				//On enlève la dernière virgule
				sql = sql.substring(0, sql.length()-2);
			}
			//Sinon, si select *
			else
				sql += "*";
			//On rajoute la table
			sql += " FROM "+ajoutNomTables(table);
			
			//On rajoute les conditions de jointures si il y en a
			sql += " WHERE ";
			condition = table.getChild("condition");
			//Si il y a une condition de jointure
			if (!condition.getText().equals("")) {
				String texte = condition.getText();
				for (String string : lettreNom.keySet()) {
					if (texte.contains(string)) {
						texte = texte.replace(string, lettreNom.get(string));
					}
				}
				sql += texte + " AND ";
			}
			sql += lettreNom.get(tableRecherche.substring(tableRecherche.indexOf(".")+1))+"."+colonneRecherche+" LIKE '"+nomRecherche+"'";
			//On finalise la requête
			sql += ";";
			
			/**
			 * Exécution et ajout des données
			 */
			//On exécute
			
			System.out.println("SQL : "+sql);
			ResultSet res = stmt.executeQuery(sql);
			
			//On récupère les résultats, qu'on ajoute dans les colonnes
			int nbColonnes = res.getMetaData().getColumnCount();
			//Ajout des noms de colonnes
			for (int i = 1; i <= nbColonnes; i++) {
				colonneNames.put(colonneNames.size(), nomTable+"."+res.getMetaData().getColumnLabel(i));
				colonneNamesIndex.put(res.getMetaData().getColumnLabel(i), colonneNamesIndex.size());
			}
			//Ajout des données
			int nbLignes = 0;
			while (res.next()) {
				nbLignes++;
				ArrayList<String> ligne = new ArrayList<String>();
				//Initialisation
				for (int i = 0; i < colonneNames.size(); i++) {
					ligne.add("");
				}
				for (int i = 1; i <= nbColonnes; i++) {
					ligne.set(colonneNamesIndex.get(res.getMetaData().getColumnName(i)),res.getObject(i).toString());
				} 
				data.add(ligne);
			}
			if (nbLignes == 0) {
				ArrayList<String> ligne = new ArrayList<String>();
				for (int i = 0; i < colonneNames.size(); i++) {
					ligne.add("");
				}
				data.add(ligne);
			}
			res.close();
			sql = "SELECT ";
		}//Fin du for sur les tables
		//Traitement de data pour merger les résultats dans une seule ligne
		ArrayList<String> r = new ArrayList<String>();
		int index = 0;
		for (ArrayList<String> list : data) {
			for (int i = 0; i < list.size(); i++) {
				if (i >= index) {
					r.add(index, list.get(i));
					index++;
				}
			}
		}
		data.clear();
		data.add(r);
		System.out.println(data.toString());
	}
	
	private String ajoutNomTables(Element table) {
		Element condition = table.getChild("condition");
		if (!condition.getText().equals("")) {
			String texte = condition.getText();
			String[] tab = texte.split("\\.");
			String retour = "";
			int j = 0;
			String car = "";
//			System.out.println(texte+", tab[i] : "+tab.length);
			for (int i = 0; i < tab.length-1; i++) {
				while (tab[i].length()-j-1 >= 0 && 
						!caracteresAEviter.contains(car)) {
					car = tab[i].substring(tab[i].length()-j-1, tab[i].length()-j);
					j++;
				}
				j--;
				if (caracteresAEviter.contains(car)) j--;
				if (table.getAttributeValue("nom").equals(tab[i].substring(tab[i].length()-j-1))){
					lettreNom.put(tab[i].substring(tab[i].length()-j-1), "a");
					retour += ", "+databaseName+"."+tab[i].substring(tab[i].length()-j-1)+" a";
				}
				else {
					lettreNom.put(tab[i].substring(tab[i].length()-j-1), lettres[i]);
					retour += ", "+databaseName+"."+tab[i].substring(tab[i].length()-j-1)+" "+lettres[i];
				}
				j=0;
			}
			retour = retour.substring(2);
			return retour;
		}
		else{
			lettreNom.put(table.getAttributeValue("nom"), "a");
			return databaseName+"."+table.getAttributeValue("nom")+" a";
		}
	}
	
	private void initialiseCaracteres() {
		caracteresAEviter.add(" ");
		caracteresAEviter.add("<");
		caracteresAEviter.add(">");
		caracteresAEviter.add("=");
		caracteresAEviter.add("!");
		caracteresAEviter.add(",");
		caracteresAEviter.add(";");
		
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
