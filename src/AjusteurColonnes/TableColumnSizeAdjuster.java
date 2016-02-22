package AjusteurColonnes;

import javax.swing.JTable;

import org.w3c.dom.Entity;

public abstract class TableColumnSizeAdjuster implements Entity{

	public void give(Object obj) throws Exception {
		new AdjusterHandler((JTable)obj);
	}
}