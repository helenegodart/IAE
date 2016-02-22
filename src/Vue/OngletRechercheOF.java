package Vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Controleur.RechercherOF;
import model.OrdreFabrication;

@SuppressWarnings("serial")
public class OngletRechercheOF extends JPanel {

	public OngletRechercheOF(OrdreFabrication of) {

		setLayout(new BorderLayout());
		CommunRecherche commun_recherche = new CommunRecherche(of);
		add(commun_recherche, BorderLayout.CENTER);
		commun_recherche.creeRecherche(new RechercherOF());

	}

}
