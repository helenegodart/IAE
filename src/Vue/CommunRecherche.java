package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import AjusteurColonnes.AdjusterHandler;
import Controleur.Sauvegarder;
import interfaces.Searchable;

@SuppressWarnings("serial")
public class CommunRecherche extends JPanel{
	
	private Tableau resultats; 
	private JTable tableau; 
	private JScrollPane scroll;
	private AdjusterHandler taille_tableau;
	
	private JPanel conteneur_recherche;
	private JButton rechercher;
	private JButton actualiser;
	private JButton envoi;
	private JTextField zone_recherche;
	private JPanel conteneur_bouton;
	private JPanel conteneur_barre;
	
	
	public CommunRecherche(Searchable searchable) {
		
		//Initialisation
		tableau = new JTable(searchable.getModel());
		scroll = new JScrollPane(tableau, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		conteneur_recherche = new JPanel();
		rechercher = new JButton("Rechercher");
		actualiser = new JButton("Actualiser");
		envoi = new JButton("Sauvegarder dans un fichier");
		zone_recherche = new JTextField("Rechercher un élément");
		conteneur_bouton = new JPanel();
		conteneur_barre = new JPanel();
		
		//Paramètres du tableau
		taille_tableau = new AdjusterHandler(tableau);
		taille_tableau.run();
		tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableau.getTableHeader().setReorderingAllowed(false);
		
		//Taille des éléments
		zone_recherche.setPreferredSize(new Dimension(150, 30));
		conteneur_recherche.setPreferredSize(new Dimension(100, 50));
	
		// BorderLayout
		setLayout(new BorderLayout());
		add(conteneur_recherche, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(envoi,  BorderLayout.SOUTH);
		
		envoi.addActionListener(new Sauvegarder());

		// BoxLayout inclus dans le BorderLayout
		conteneur_recherche.setLayout(new BoxLayout(conteneur_recherche, BoxLayout.LINE_AXIS));
		conteneur_recherche.add(conteneur_barre);
		conteneur_recherche.add(conteneur_bouton);
		
		conteneur_bouton.setLayout(new BoxLayout(conteneur_bouton, BoxLayout.LINE_AXIS));
		conteneur_bouton.add(rechercher);
		conteneur_bouton.add(actualiser);
		
		conteneur_barre.setLayout(new FlowLayout());
		conteneur_barre.add(zone_recherche);
	}


	public void creeRecherche(ActionListener action_listener) {
		
		rechercher.addActionListener(action_listener);		
		
	}

}
