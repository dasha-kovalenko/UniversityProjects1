import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
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
	
/*	public MyFrame(int[] counts){
		for(int i = 0; i < counts.length; i++){
			results.add(new Result(i, counts[i]));
		}
	}*/
	MyFrame(int[] counts){
		
		results = new ArrayList<Result>();
		for(int i = 0; i < counts.length; i++){
			results.add(new Result(i+1, counts[i]));
		}
		

		dataModel = new DataModel();
		table = new JTable(dataModel);
		add(table, BorderLayout.CENTER);
		
	}
	public static void main(String[] args) {
		int [] counts = {100,5,1,100,99,10};
		MyFrame frame = new MyFrame(counts);
		frame.setTitle("Simple Results");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 500);
		frame.setVisible(true);
	}

class DataModel extends AbstractTableModel {
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
			case 1: return "Amount of inputs";
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