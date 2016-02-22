package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.BarreProgression;

public class RechercherArticle implements ActionListener{

	private Thread t;
	private BarreProgression barre;
	private int val;
	
	public RechercherArticle(){

	

	}

	class Traitement implements Runnable{   

		public void run(){
			
			val = barre.getBar().getMinimum();
			
			while(val <= barre.getBar().getMaximum()){
				barre.getBar().setValue(val);
				val++;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			barre.dispose();
			
		}   

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		t = new Thread(new Traitement());
		barre = new BarreProgression();
		t.start();    

	}

}
