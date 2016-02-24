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
		}
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}
