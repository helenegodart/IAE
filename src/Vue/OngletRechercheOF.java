package Vue;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import Controleur.RechercherOF;
import model.OrdreFabrication;

@SuppressWarnings("serial")
public class OngletRechercheOF extends JPanel {

	private OrdreFabrication of; 
	private CommunRecherche communRecherche;

	public OngletRechercheOF(OrdreFabrication of) {
		this.of = of;
		communRecherche = new CommunRecherche(of);
		this.setLayout(new BorderLayout());
		this.add(communRecherche, BorderLayout.CENTER);
		communRecherche.creeRecherche(new RechercherOF());
	}
	
	public void setOF(OrdreFabrication of) {
		this.of = of;
		communRecherche.setSearchable(of);
		this.revalidate();
	}
	
	public CommunRecherche getCommunRecherche() {
		return communRecherche;
	}

}
