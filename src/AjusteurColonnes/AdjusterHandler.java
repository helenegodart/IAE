package AjusteurColonnes;

import java.awt.FontMetrics;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class AdjusterHandler implements TableModelListener, Runnable {

	private JTable table;

	public AdjusterHandler(JTable table){
		this.table = table;
		table.getModel().addTableModelListener(this);
		adjustColumnPreferredWidths(table);
	}

	public void tableChanged(TableModelEvent e){
		SwingUtilities.invokeLater(this);
	}

	public void run(){
		adjustColumnPreferredWidths(table);
	}


	private void adjustColumnPreferredWidths(JTable table){
		FontMetrics fm = table.getFontMetrics(table.getFont());
        for (int i = 0 ; i < table.getColumnCount() ; i++){
            int max = 0;
            for (int j = 0 ; j < table.getRowCount() ; j++){
               int taille = fm.stringWidth((String)table.getValueAt(j,i));
               if (taille > max)
                    max = taille;
            }
            String nom = (String)table.getColumnModel().getColumn(i).getIdentifier();
            int taille = fm.stringWidth(nom);
            if (taille > max)
                   max = taille;
           table.getColumnModel().getColumn(i).setPreferredWidth(max+25);
        }
	}
}