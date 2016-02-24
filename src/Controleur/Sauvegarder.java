package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.jdom2.JDOMException;

import interfaces.Searchable;
import model.Mapping;

public class Sauvegarder implements ActionListener {
	
	private Searchable searchable;
	private Mapping mapping;
	
	public Sauvegarder(Searchable searchable){
		this.searchable = searchable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			mapping = new Mapping(searchable);
			mapping.map();
		} catch (JDOMException | IOException e1) {
			e1.printStackTrace();
		}
	}
}
