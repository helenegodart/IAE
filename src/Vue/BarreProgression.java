package Vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class BarreProgression extends JFrame{
	
	private JProgressBar bar;

	public BarreProgression(){      
		
		setSize(300, 80);
		setTitle("Traitement en cours");
		setLocationRelativeTo(null);      

		setBar(new JProgressBar());
		getBar().setMaximum(500);
		getBar().setMinimum(0);
		getBar().setStringPainted(true);

		getContentPane().add(getBar(), BorderLayout.CENTER);
        
		setVisible(true);      

	}

	public JProgressBar getBar() {
		return bar;
	}

	public void setBar(JProgressBar bar) {
		this.bar = bar;
	}
	
}
