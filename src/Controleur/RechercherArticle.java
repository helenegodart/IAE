package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import Vue.BarreProgression;
import model.Article;

public class RechercherArticle implements ActionListener{

	private JTextField rechercheField;
	private Article article;
	private Thread t;
	
	public RechercherArticle(Article article, JTextField rechercheField){
		this.rechercheField = rechercheField;
		this.article = article;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (rechercheField.getText() != "") {
			article.setNomRecherche(rechercheField.getText());
			t = new Thread(new Traitement());
			new BarreProgression();
			t.start();
		}
	}
	
	class Traitement implements Runnable{   

		public void run(){

			for(int val = 0; val <= 500; val++){
				BarreProgression.bar.setValue(val);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}   

	}

}
