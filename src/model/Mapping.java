package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import interfaces.Searchable;

public class Mapping {

	private Document map, sortie;
	private ArrayList<Element> tables;
	private ArrayList<String> tableNames, colonnesUsed, data;
	private HashMap<String, Integer> colonneNames;
	
	public Mapping(Searchable searchable) {
		sortie = new Document(new Element("flexnet"));
		tables = new ArrayList<Element>();
		tableNames = new ArrayList<String>();
		colonneNames = searchable.getModel().getColonneNamesIndex();
		colonnesUsed = new ArrayList<String>();
		data = searchable.getModel().getData();
	}
	
	public boolean map() throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		map = sax.build(new File("mapping_Test.xml"));
		
		List<Element> entry = map.getRootElement().getChildren(); 
		
		String flex = "", flexTable = "", flexCol = "",
				sage = "", sageTable = "", sageCol = "";
		for (Element element : entry) {
			flex = element.getAttributeValue("flex");
			flexTable = flex.substring(0, flex.indexOf("."));
			flexCol = flex.substring(flex.indexOf(".")+1);
			
			sage = element.getAttributeValue("sage");
			sageTable = sage.substring(0, sage.indexOf("."));
			sageCol = sage.substring(sage.indexOf(".")+1);
			
			//Si on a déjà utilisé la table
			if (tableNames.contains(flexTable)) {
				for (Element e : tables) {
					if (e.getAttributeValue("nom").equals(flexTable) && !colonnesUsed.contains(flexCol)) {
						Element el = new Element("colonne");
						el.setAttribute("nom", flexCol);
						el.setText(data.get(colonneNames.get(sageCol)));
						e.addContent(el);
						colonnesUsed.add(flexCol);
					}
				}
			}
			//Si c'est la première fois
			else {
				Element e = new Element("table");
				e.setAttribute("nom", flexTable);
				Element el = new Element("colonne");
				el.setAttribute("nom", flexCol);
				el.setText(data.get(colonneNames.get(sageCol)));
				e.addContent(el);
				tableNames.add(flexTable);
				colonnesUsed.add(flexCol);
				tables.add(e);
			}
		}
		
		for (Element element : tables) {
			sortie.getRootElement().addContent(element);
		}
		
		
		try {
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(this.sortie, new FileOutputStream(new File("sortie.xml")));
		   }
		   catch (java.io.IOException e){
			   e.printStackTrace();
		   }
		
		JOptionPane.showMessageDialog(null, "Le fichier \"sortie.xml\" a été généré correctement", "Mapping terminé !", JOptionPane.INFORMATION_MESSAGE, null);
		return true;
	}
}
