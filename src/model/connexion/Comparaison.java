package model.connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;




public class Comparaison {

	private File dir1, dir2, result;
	private ArrayList<String> tables, diffs;
	private SAXBuilder sax;


	public Comparaison(ArrayList<String> tables, String dir1, String dir2, String result) {
		this.tables = tables;
		this.dir1 = new File(dir1);
		this.dir2 = new File(dir2);
		this.diffs = new ArrayList<String>();
		this.result = new File(result);
		this.sax = new SAXBuilder();
		
//		test();
	}

	private void test() {
		BufferedReader br1, br2; 
		String s1, s2;
		boolean differents = false;

		try {
			br1 = new BufferedReader(new FileReader(new File("ALERT_DETAIL.xml")));
			br2 = new BufferedReader(new FileReader(new File("ALERT_DETAIL1.xml")));

			while ((s1 = br1.readLine()) != null && (s2 = br2.readLine()) != null && !differents) {
				differents = !s1.equals(s2);
			}

			if (differents) {
				System.out.println("Différent !");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void compareSimple() {
		BufferedReader br1 = null, br2 = null; 
		File f1, f2;
		String s1, s2;
		boolean differents = false;

		BufferedWriter bw;
		
		try {
			for (String string : tables) {
				f1 = new File(dir1.getAbsolutePath()+"\\"+string+".xml");
				f2 = new File(dir2.getAbsolutePath()+"\\"+string+".xml");
				
				if (f1.exists() && f2.exists()) {
					br1 = new BufferedReader(new FileReader(f1));
					br2 = new BufferedReader(new FileReader(f2));

					while ((s1 = br1.readLine()) != null && (s2 = br2.readLine()) != null && !differents) {
						differents = !s1.equals(s2);
					}

					if (differents) diffs.add(string);
				}
			}
			
			br1.close();
			br2.close();
			
			bw = new BufferedWriter(new FileWriter(result));
			
			for (String string : diffs) {
				bw.write(string+"\r\n");
			}
			
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
