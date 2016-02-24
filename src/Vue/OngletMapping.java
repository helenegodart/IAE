package Vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OngletMapping extends JPanel {

	private CardLayout mapping;
	private JPanel content;
	int indice;
	private JPanel conteneur_bouton;
	private JButton precedent;
	private JButton suivant;
	private JLabel image_mapping_articles; 
	private JLabel image_mapping_nomenclatures;
	private JLabel image_mapping_xml;
	
	//Liste des noms de nos conteneurs pour la pile de cartes
	private String[] listContent = {"mapping_articles", "mapping_nomenclatures", "mapping_xml"};

	
	public OngletMapping(){
		
		mapping = new CardLayout();	
		content = new JPanel();
		indice = 0;
		conteneur_bouton = new JPanel();
		precedent = new JButton("Précédent");
		suivant = new JButton("Suivant");
		
		image_mapping_articles = new JLabel(new ImageIcon("images/mapping_articles.png"));
		image_mapping_nomenclatures = new JLabel(new ImageIcon("images/mapping_nomenclatures.png"));
		image_mapping_xml = new JLabel(new ImageIcon("images/mapping_xml.png"));
		
		//On crée trois conteneurs de couleur différente
		JPanel mapping_articles = new JPanel();
		mapping_articles.add(image_mapping_articles);
		JPanel mapping_nomenclatures = new JPanel();
		mapping_nomenclatures.add(image_mapping_nomenclatures);	
		JPanel mapping_xml = new JPanel();
		mapping_xml.add(image_mapping_xml);

		
		
		//BorderLayout inclus dans le CardLayout 
		setLayout(new BorderLayout());
		add(conteneur_bouton, BorderLayout.SOUTH);
		
		//BoxLayout inclus dans le BorderLayout
		conteneur_bouton.setLayout(new BoxLayout(conteneur_bouton, BoxLayout.LINE_AXIS));
		conteneur_bouton.add(precedent);
		conteneur_bouton.add(suivant);
	
		//Définition de l'action du bouton precedent
		suivant.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//Via cette instruction, on passe au prochain conteneur de la pile
				mapping.next(content);
			}
		});
		
		//Définition de l'action du bouton suivant
		precedent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//Via cette instruction, on passe au prochain conteneur de la pile
				mapping.previous(content);
			}
		});
		
		//On définit le layout
		content.setLayout(mapping);
		//On ajoute les cartes à la pile avec un nom pour les retrouver
		content.add(mapping_articles, listContent[0]);
		content.add(mapping_nomenclatures, listContent[1]);
		content.add(mapping_xml, listContent[2]);

		this.add(conteneur_bouton, BorderLayout.SOUTH);
		this.add(content, BorderLayout.CENTER);
		
		this.setVisible(true);
	}	
}

