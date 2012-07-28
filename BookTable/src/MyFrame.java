import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

class Book implements Serializable {
	String name,author;
	int year;
	Book (String a, String n, int y){author=a; name=n; year=y;}
}
public class MyFrame extends JFrame implements ActionListener{
	JTable table;
	DataModel dataModel;
	ArrayList <Book> books;
	JButton saveButton, loadButton;
	MyFrame() {
		books = new ArrayList <Book>();
		books.add(new Book("hahaGorky","Matj",1987));
		//books.add(new Book("Marks","Kapital",1975));

		dataModel = new DataModel();
		table = new JTable(dataModel);
		add(table, BorderLayout.CENTER);
		
		saveButton = new JButton ("Save");
		saveButton.addActionListener(this);
		add(saveButton, BorderLayout.SOUTH);
		loadButton = new JButton ("Load");
		loadButton.addActionListener(this);
		add(loadButton, BorderLayout.NORTH);
	}
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 300, 300);
		frame.setVisible(true);
	}

class DataModel extends AbstractTableModel {
	public int getColumnCount() {
		return 3;
	}
	public int getRowCount() {
		return books.size();
	}
	public Object getValueAt(int row, int col) {
		switch (col)
		{
		case 0: return books.get(row).author;
		case 1: return books.get(row).name; 
		case 2: return books.get(row).year; 
		}
		return null;
	}
	public void setValueAt(Object val, int row, int col) {
		switch (col)
		{
		case 0: books.get(row).author = (String)val; break;
		case 1: books.get(row).name = (String)val; break;
		case 2: books.get(row).year = Integer.parseInt((String)val); break; 
		}
		this.fireTableDataChanged();
	}
	public boolean isCellEditable(int row, int col) {
		return true;
	}
}

public void actionPerformed(ActionEvent ae) {
	if (ae.getSource() == saveButton) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("arch.dat"));
			oos.write(books.size());
			for (int i=0; i<books.size(); ++i)
				oos.writeObject(books.get(i));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	else {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("arch.dat"));
			int n = ois.read();
			books.clear();
			for (int i=0; i<n; ++i)
				try {
					books.add((Book)ois.readObject());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				dataModel.fireTableDataChanged();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
}