package model;

import interfaces.Searchable;

import java.sql.Connection;
import java.util.Observable;

import javax.swing.JTable;

import model.echanges.TableModel;

public class OrdreFabrication extends Observable implements Searchable {

	private TableModel model;
	private JTable table;
	private String nomOF;
	@SuppressWarnings("unused")
	private Connection con;
	
	public OrdreFabrication(Connection con, String nomOF) {
		this.nomOF = nomOF;
		this.con = con;
		//TODO : Mettre le bon nom de database
		//TODO : changer le nom de l'of (null pour l'instant)
		model = new TableModel("of.xml", "IAE3ASIE", con, "ITMMASTER", "ITMREF_0", null);
		table = new JTable(model);
	}

	public JTable getTable() {
		return table;
	}

	@Override
	public String getNomRecherche() {
		return nomOF;
	}

	@Override
	public void setNomRecherche(String nomRecherche) {
		this.nomOF = nomRecherche;
		model.setNomRecherche(nomOF);
		table.setModel(model);
	}

	@Override
	public TableModel getModel() {
		return model;
	}
}
