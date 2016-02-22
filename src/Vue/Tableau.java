package Vue;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class Tableau extends AbstractTableModel {

	private final Object[][] donnees;
	private final String[] entetes = { "Prénom", "Nom", "Couleur favorite", "Homme", "Sport" };

	public Tableau() {
		super();

		donnees = new Object[][] {
			{ "Johnathan", "Sykes", "Rouge", "Homme", "TENNIS" },
			{ "Nicolas", "Van de Kampf", "Noir", "Homme", "FOOTBALL" },
			{ "Damien", "Cuthbert", "Cyan", "Homme", "RIEN" },
			{ "Corinne", "Valance", "Bleu", "Femme", "NATATION" },
			{ "Emilie", "Schrödinger", "Magenta", "Femme", "FOOTBALL" },
			{ "Delphine", "Duke", "Jaune", "Femme", "TENNIS" },
			{ "Eric", "Trump", "Rose", "Homme", "FOOTBALL" }, 
		};

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entetes.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return donnees.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return donnees[rowIndex][columnIndex];
	}
	
	public String getColumnName(int columnIndex) {
	    return entetes[columnIndex];
	}

}
