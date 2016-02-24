package Vue;

import javax.swing.JTabbedPane;

import model.Article;
import model.OrdreFabrication;

@SuppressWarnings("serial")
public class ListeOnglets extends JTabbedPane {

	OngletRechercheArticle onglet1;
	OngletRechercheOF onglet2;
	
	public ListeOnglets(int top, Article article, OrdreFabrication of) {
		
		setUI(new BarreOngletsPerso());
		
		onglet1 = new OngletRechercheArticle(article);
		addTab("Recherche par Article", onglet1);
		
		onglet2 = new OngletRechercheOF(of);
		addTab("Recherche par Ordre de Fabrication", onglet2);
		
		OngletMapping onglet3 = new OngletMapping();
		addTab("Mapping", onglet3);
		
		OngletOntologie onglet4 = new OngletOntologie();
		addTab("Ontologie", onglet4);
		
	}
	
	public void setArticle(Article article) {
		onglet1.setArticle(article);
		this.revalidate();
	}
	
	public void setOF(OrdreFabrication of) {
		onglet2.setOF(of);
		this.revalidate();
	}
	
	public OngletRechercheArticle getOngletArticle() {
		return onglet1;
	}
	
	public OngletRechercheOF getOngletOF() {
		return onglet2;
	}
	
}
