package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class EffacerRecherche implements MouseListener{

	private JTextField recherche;
	
	public EffacerRecherche(JTextField recherche) {

		this.recherche = recherche;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if(recherche.getText().equals("Rechercher un élément")){
			recherche.setText("");
		} else {
		}

	}

}
