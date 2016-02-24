package model;

import java.sql.SQLException;

import Vue.Fenetre;
import model.connexion.Comparaison;
import model.connexion.ConnexionDemoFra;
import model.connexion.ConnexionSage;

public class Connecteur implements Runnable{
	
	private Article article;
	private OrdreFabrication of;
	

	public Connecteur() {

	}
	
	public void run() {
//		ConnexionDemoFra demoFra = new ConnexionDemoFra();
//		ConnexionFlexNet flex = new ConnexionFlexNet();
		ConnexionSage sage = new ConnexionSage();
		
		article = new Article(sage.getCon(), null);
		of = new OrdreFabrication(sage.getCon(), "Nom de l'of");	
	}

	public static void main(String[] args) {
		Connecteur connecteur = new Connecteur();
		Thread t = new Thread(connecteur);
		t.start();
		Fenetre fen = new Fenetre(connecteur);
		try {
			t.join();
			System.out.println(connecteur.getArticle().toString());
			fen.setConnecteur(connecteur);
			fen.revalidate();
			fen.getOnglets().getOngletArticle().getCommunRecherche().getZone_recherche().setEnabled(true);
			fen.getOnglets().getOngletOF().getCommunRecherche().getZone_recherche().setEnabled(true);
			fen.revalidate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public Article getArticle() {
		return article;
	}

	public OrdreFabrication getOf() {
		return of;
	}
}
