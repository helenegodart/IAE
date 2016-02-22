package model;

import java.sql.SQLException;

import Vue.Fenetre;
import model.connexion.Comparaison;
import model.connexion.ConnexionDemoFra;
import model.connexion.ConnexionSage;

public class Connecteur {
	
	private Article article;
	private OrdreFabrication of;
	

	public Connecteur() {
//		ConnexionDemoFra demoFra = new ConnexionDemoFra();
		ConnexionSage sage = new ConnexionSage();
		
//		ConnexionFlexNet flex = new ConnexionFlexNet();
		
//		DataWriter dw = new DataWriter("C:\\Users\\cottin4u\\Desktop\\FlexNet_dir1", flex.getCon(), flex.getTables());
//		DataWriter dw = new DataWriter("C:\\Users\\cottin4u\\Desktop\\Sage_dir2", demoFra.getCon(), demoFra.getTables());
	
//		try {
//			dw.ecrire();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		
		
//		Comparaison comp = new Comparaison(demoFra.getTables(), "C:\\Users\\cottin4u\\Desktop\\Sage_dir1", 
//				"C:\\Users\\cottin4u\\Desktop\\Sage_dir2", "diff_sage1.txt");
//		
//		System.out.println("Running...");
//		comp.compareSimple();
//		System.out.println("Finish");
		
		article = new Article(sage.getCon(), null);
		of = new OrdreFabrication(sage.getCon(), "Nom de l'of");
	}

	public static void main(String[] args) {
		Connecteur connecteur = new Connecteur();
		new Fenetre(connecteur);
	}
	
	public Article getArticle() {
		return article;
	}

	public OrdreFabrication getOf() {
		return of;
	}
}
