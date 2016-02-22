package Vue;

import javax.swing.JTabbedPane;

import model.Article;
import model.OrdreFabrication;

@SuppressWarnings("serial")
public class ListeOnglets extends JTabbedPane {

	public ListeOnglets(int top, Article article, OrdreFabrication of) {
		
		setUI(new BarreOngletsPerso());
		
		OngletRechercheArticle onglet1 = new OngletRechercheArticle(article);
		addTab("Recherche par Article", onglet1);
		
		OngletRechercheOF onglet2 = new OngletRechercheOF(of);
		addTab("Recherche par Ordre de Fabrication", onglet2);
		
		OngletMapping onglet3 = new OngletMapping();
		addTab("Mapping", onglet3);
		
		OngletOntologie onglet4 = new OngletOntologie();
		addTab("Ontologie", onglet4);
		
	}
	
}
