package Vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Controleur.RechercherArticle;
import model.Article;

@SuppressWarnings("serial")
public class OngletRechercheArticle extends JPanel {

	public OngletRechercheArticle(Article article) {
		
		setLayout(new BorderLayout());
		CommunRecherche commun_recherche = new CommunRecherche(article);
		add(commun_recherche, BorderLayout.CENTER);
		commun_recherche.creeRecherche(new RechercherArticle(article, commun_recherche.getZone_recherche()));
		
	}
	
}
