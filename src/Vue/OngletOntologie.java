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
public class OngletOntologie extends JPanel {

	private CardLayout mapping;
	private JPanel content;
	int indice;
	private JPanel conteneur_bouton;
	private JButton precedent;
	private JButton suivant;
	private JLabel image_ontologie_sage; 
	private JLabel image_ontologie_flexnet;
	private JLabel image_ontologie_globale;
	
	//Liste des noms de nos conteneurs pour la pile de cartes
	private String[] listContent = {"ontologie_sage", "ontologie_flexnets", "ontologie_globale"};

	
	public OngletOntologie(){
		
		mapping = new CardLayout();	
		content = new JPanel();
		indice = 0;
		conteneur_bouton = new JPanel();
		precedent = new JButton("Précédent");
		suivant = new JButton("Suivant");
		
		image_ontologie_sage = new JLabel(new ImageIcon("images/ontologie_sage.png"));
		image_ontologie_flexnet = new JLabel(new ImageIcon("images/ontologie_flexnet.png"));
		image_ontologie_globale = new JLabel(new ImageIcon("images/ontologie_globale.png"));
		
		//On crée trois conteneurs de couleur différente
		JPanel ontologie_sage = new JPanel();
		ontologie_sage.add(image_ontologie_sage);
		JPanel ontologie_flexnets = new JPanel();
		ontologie_flexnets.add(image_ontologie_flexnet);	
		JPanel ontologie_globale = new JPanel();
		ontologie_globale.add(image_ontologie_globale);

		
		
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
		content.add(ontologie_sage, listContent[0]);
		content.add(ontologie_flexnets, listContent[1]);
		content.add(ontologie_globale, listContent[2]);

		this.add(conteneur_bouton, BorderLayout.SOUTH);
		this.add(content, BorderLayout.CENTER);
		
		this.setVisible(true);
	}	
}


