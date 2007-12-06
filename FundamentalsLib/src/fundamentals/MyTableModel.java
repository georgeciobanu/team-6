/*
 * 	Table model
 *
 *      This code was taken from http://forum.java.sun.com/thread.jspa?threadID=5242581&tstart=1
 *
 */
 
package fundamentals;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;
 
 
public class MyTableModel extends AbstractTableModel{
	
	private Vector<Vector> data;
	private Vector<String> colNames;
	
	public MyTableModel(){
		this.colNames = new Vector<String>();
		this.data = new Vector<Vector>();
	}
	
	public MyTableModel(Vector colnames){
		this.colNames = colnames;
		this.data = new Vector<Vector>();
	}
	
	/*
	 * 	Reset all the table data including its structure
	 */
	public void resetTable(){
		this.colNames.removeAllElements();
		this.data.removeAllElements();
	}
	
	/*
	 * 	Sets the table column names;
	 */
	public void setColumnNames(Vector<String> colNames){
		this.colNames = colNames;
		this.fireTableStructureChanged();
	}
	
	/*
	 * 	Adds a row given its Vector elements
	 */
	public void addRow(Vector data){
		this.data.add(data);
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}
	
	/*
	 * 	Removes a row given its index
	 */	
	public void removeRowAt(int row){
		this.data.removeElementAt(row);
	}
	
	/*
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount(){
		return this.colNames.size();
	}
	
	/*
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int colNum){
		return this.colNames.get(colNum);
	}
	
	/*
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount(){
		return this.data.size();
	}
	
	/*
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col){
		Vector value = this.data.get(row);
		return value.get(col);
	}
 
}