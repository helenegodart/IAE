package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Connecteur;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {

	static String titre;

	//Menu
	private JMenuBar barre_menu; 
	private JMenu fichiers;
	private JMenuItem sauvegarde; 


	//Composants
	private JPanel conteneur; 
	private ListeOnglets onglets; 
	private Connecteur connecteur;

	public Fenetre(Connecteur connecteur){
		this.connecteur = connecteur;
		titre = "IAE";
		
		//Format de la fenêtre
		setMinimumSize(new Dimension(600, 700));
		setTitle(titre);
		setLocationRelativeTo(null);  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Initialisation
		barre_menu = new JMenuBar();
		fichiers = new JMenu("Fichier");
		sauvegarde = new JMenuItem("Sauvegarder sous forme de fichier");
		conteneur = new JPanel();
		onglets = new ListeOnglets(SwingConstants.TOP, connecteur.getArticle(), connecteur.getOf());

		// Menu
		barre_menu.add(fichiers);		
		// Sous-menu
		fichiers.add(sauvegarde);

		//TODO checker
		//fichiers.addActionListener(new Sauvegarder());

		// Ajout de la barre de menu
		setJMenuBar(barre_menu);

		conteneur.setLayout(new BorderLayout());
		conteneur.add(onglets);

		setContentPane(conteneur);
		pack();
		setVisible(true);

	}

}
