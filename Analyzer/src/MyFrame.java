import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

class Result {
	Integer number;
	Integer amount;
	Result(Integer a, Integer b){number = a; amount = b;}
}
public class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	DataModel dataModel;
	ArrayList<Result> results;
	MyFrame frame = this;
	
	public MyFrame(int[] counts){
		
		results = new ArrayList<Result>();
		frame.setTitle("Simple Results");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 300);
		
		for(int i = 0; i < counts.length-1; i++){
			results.add(new Result(i+1, counts[i+1]));
		}

		dataModel = new DataModel();
		table = new JTable(dataModel);
		//JScrollPane scroll = new JScrollPane(table);
		add(table, BorderLayout.CENTER);
		frame.setVisible(true);
	}

class DataModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getColumnCount() {
		return results.size() + 1;
	}
	public int getRowCount() {
		return 2;
	}
	public Object getValueAt(int row, int col) {

		if(col != 0){
			switch(row){
			case 0: {
				return results.get(col-1).number;
			}
			case 1: return results.get(col-1).amount;
			}
			return null;
		}
		else
			switch(row){
			case 0: return "Number of segment";
			case 1: return "Amount of executions";
			}
		return null;
	}
	public void setValueAt(Object val, int row, int col) {
		switch(row){
		case 0: results.get(col-1).number = (Integer)val; break;
		case 1: results.get(col-1).amount = (Integer)val; break;
		}
		this.fireTableDataChanged();
	}
	public boolean isCellEditable(int row, int col) {
		return true;
	}
}


}