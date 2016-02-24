package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import interfaces.Searchable;

public class Mapping {

	private Document map, sortie;
	private ArrayList<Element> tables;
	private ArrayList<String> tableNames, data;
	private HashMap<String, Integer> colonneNames;
	
	public Mapping(Searchable searchable) {
		sortie = new Document(new Element("flexnet"));
		tables = new ArrayList<Element>();
		tableNames = new ArrayList<String>();
		colonneNames = searchable.getModel().getColonneNamesIndex();
		data = searchable.getModel().getData();
	}
	
	public boolean map() throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		map = sax.build(new File("mapping_Test.xml"));
		
		List<Element> entry = map.getRootElement().getChildren(); 
		
		String flex = "", flexTable = "", flexCol = "";
		for (Element element : entry) {
			flex = element.getAttributeValue("flex");
			flexTable = flex.substring(0, flex.indexOf("."));
			flexCol = flex.substring(flex.indexOf(".")+1);
			//Si on a déjà utilisé la table
			if (tableNames.contains(flexTable)) {
				for (Element e : tables) {
					if (e.getAttributeValue("nom").equals(flexTable)) {
						Element el = new Element("colonne");
						el.setAttribute("nom", flexCol);
						el.setText(data.get(colonneNames.get(flexCol)));
						e.addContent(el);
					}
				}
			}
			//Si c'est la première fois
			else {
				Element e = new Element("table");
				e.setAttribute("nom", flexTable);
				Element el = new Element("colonne");
				el.setAttribute("nom", flexCol);
				el.setText(data.get(colonneNames.get(flexCol)));
				e.addContent(el);
				tableNames.add(flexTable);
				tables.add(e);
			}
		}
		return true;
	}
}
