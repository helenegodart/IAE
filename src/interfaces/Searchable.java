package interfaces;

import javax.swing.JTable;

import model.echanges.TableModel;

public interface Searchable {

	public String getNomRecherche();
	public void setNomRecherche(String nomRecherche);
	public JTable getTable();
	public TableModel getModel();
}
