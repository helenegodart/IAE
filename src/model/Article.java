package model;

import interfaces.Searchable;

import java.sql.Connection;
import java.util.Observable;

import javax.swing.JTable;

import model.echanges.TableModel;

public class Article extends Observable implements Searchable{

	private TableModel model;
	private JTable table;
	private String nomArticle;
	@SuppressWarnings("unused")
	private Connection con;
	
	public Article(Connection con, String nomArticle) {
		this.nomArticle = nomArticle;
		this.con = con;
		model = new TableModel("articles_optimises.xml", "IAE3ASIE", con, "ITMMASTER", "ITMREF_0", nomArticle);
		table = new JTable(model);
		
	}

	public JTable getTable() {
		return table;
	}

	public String getNomRecherche() {
		return nomArticle;
	}

	public void setNomRecherche(String nomArticle) {
		this.nomArticle = nomArticle;
		model.setNomRecherche(nomArticle);
		table.setModel(model);
		table.revalidate();
		setChanged();
		notifyObservers();
	}

	public TableModel getModel() {
		return model;
	}
}
