package Vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Controleur.RechercherArticle;
import model.Article;

@SuppressWarnings("serial")
public class OngletRechercheArticle extends JPanel {

	private Article article;
	private CommunRecherche communRecherche;
	private RechercherArticle rechArticle;
	
	public OngletRechercheArticle(Article article) {
		this.article = article;
		communRecherche = new CommunRecherche(article);
		this.setLayout(new BorderLayout());
		this.add(communRecherche, BorderLayout.CENTER);
		rechArticle = new RechercherArticle(article, communRecherche.getZone_recherche());
		communRecherche.creeRecherche(rechArticle);
	}
	
	public void setArticle(Article article) {
		this.article = article;
		communRecherche.setSearchable(article);
		rechArticle.setArticle(article);
		this.revalidate();
	}
	
	public CommunRecherche getCommunRecherche() {
		return communRecherche;
	}
}
