import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


class FlowerTableModel extends AbstractTableModel{
	private String[] columnNames = {"Picture",
									"Name of the flower",
									"Temperature",
									"Its origin"};
	public ArrayList<Flower> cont;	
	public FlowerTableModel(){
		cont = new ArrayList<Flower>();
	}
	public void addRow(Flower f){
        cont.add(f);
        fireTableStructureChanged();
    }	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return cont.size();
	}
	public String getColumnName(int col) {
        return columnNames[col];
    }
	@Override
	public Object getValueAt(int row, int col) {
		if(col==0) return cont.get(row).getPicture();
		else{
			if(col==1) return cont.get(row).getName();
			else{
				if(col==2) return cont.get(row).getTemperature();
				else return cont.get(row).getOrigin();
			}
		}
	}
	 public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
   public boolean isCellEditable(int row, int col) {
    	return true;
    }
    public void setValueAt(Object value, int row, int col) {
       Flower f=cont.get(row);
       switch(col){
       		case 0: f.setPicture((String)value);
       		case 1: f.setName((String)value);
       		case 2: f.setTemperature((Integer)value);
       		case 3: f.setOrigin((String)value);
       }
       cont.set(row, f);
       fireTableCellUpdated(row, col);
    }
}

public class TestCollection extends JFrame implements ActionListener{
	private FlowerTableModel model;
	private JTable table;
	
	public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Add new flower"))
        {
            MyDialog dial = new MyDialog(this, "Flowers!", true);
            dial.setSize(600,180);
            dial.setLocation(this.getX(), this.getY());
            dial.setVisible(true);
            if(dial.isRet()){
                 String path=dial.getP1();
                 String name=dial.getP2();
                 Integer t=new Integer(dial.getP3());
                 String c=dial.getP4();
         		 Flower f=new Flower(path,name,t,c);
                 model.addRow(f);
            }
        }
        else {
        	 if(e.getActionCommand().equals("Delete flower")){
        		 int sel=table.getSelectedRow();
        		 if(sel!=-1){
        			 model.cont.remove(sel);
        			 model.fireTableDataChanged();
        		 }
        		 else {
        			 JOptionPane.showMessageDialog(this, "Select a Row!");
        		 }
        	 }
        	 //сериализация
        	 else {
            	 if(e.getActionCommand().equals("Save into File")){
                     try {
                    	 
                    	String fname = new String("");
                    	JFileChooser fc = new JFileChooser();
                    	int res = fc.showDialog(this,"Save into File"); 
                 		if (res == JFileChooser.APPROVE_OPTION) {  
                		      File file = fc.getSelectedFile();
                		      fname=file.getName();
                		      JOptionPane.showMessageDialog(this, fname);
                 		}
                 		StringTokenizer st = new StringTokenizer(fname, ".");
        				String [] strArr = new String[2];
        				int m = 0;
        				while (st.hasMoreTokens()){
        					strArr[m] = st.nextToken();
        					System.out.println(strArr[m]);
        					m++;
        				}
        				if (strArr[1].equals("xml")){
        					FileWriter fw = new FileWriter(fname);
        					fw.write("<?xml version=\"1.0\" encoding=\"Windows-1251\"?>");
        					fw.write("\n");
        					for(int i=0; i<model.cont.size(); i++){
        						fw.write("<name>"+model.cont.get(i).getName()+"</name>"+"\n");
        						fw.write("<picture>"+model.cont.get(i).getPicture()+"</picture>"+"\n");
        						fw.write("<temperature>"+model.cont.get(i).getTemperature()+"</temperature>"+"\n");
        						fw.write("<origin>"+model.cont.get(i).getOrigin()+"</origin>"+"\n");
        						fw.write("\n");
        					}
        					fw.close();
        				} else {
	                     	ObjectOutputStream os = 
	                     		new ObjectOutputStream(
	                     				new FileOutputStream(fname));
	                     	os.write(model.cont.size());
	                     	for(int i=0; i<model.cont.size(); ++i){
	                     		os.writeObject(model.cont.get(i));
	                     	}
	                     	os.flush();
	                     	os.close();
        				}
                     }
                     catch (IOException exc){
                     	System.err.println(exc);
                     }
            	 }
            	 //десериализация
            	 else {
                	 if(e.getActionCommand().equals("Open File")){
             	        try{
             	        	String fname = new String("");
             	        	JFileChooser fc = new JFileChooser();
             	        	int res = fc.showDialog(this,"Open File"); 
                    		if (res == JFileChooser.APPROVE_OPTION) {  
                   		      File file = fc.getSelectedFile();
                   		      fname=file.getName();
                   		      JOptionPane.showMessageDialog(this, fname);
                    		//}
	             	        	StringTokenizer st = new StringTokenizer(fname, ".");
	            				String [] strArr = new String[2];
	            				int m = 0;
	            				while (st.hasMoreTokens()){
	            					strArr[m] = st.nextToken();
	            					System.out.println(strArr[m]);
	            					m++;
	            				}
	            				if (strArr[1].equals("xml")){
	            					ReadXMLFile reader = new ReadXMLFile(fname);
	            					model.cont.clear();
	            					model.cont = reader.getFls();
	            					//for(int i=0;i<model.cont.size();i++)
	            						//System.out.println(model.cont.get(i).getName());
	            					model.fireTableDataChanged();
	            					
	            				} else {
	            					ObjectInputStream is = 
		             	        		new ObjectInputStream(
		             	        				new FileInputStream(fname));
	            					int n = is.read();
	                 	        	model.cont.clear();
	                 	        	for (int i=0; i<n; ++i)
	                 					try {
	                 						model.cont.add((Flower)is.readObject());
	                 					} catch (ClassNotFoundException exc) {
	                 						exc.printStackTrace();
	                 					}
	                 					model.fireTableDataChanged();
	            				}
	              	        	//ArrayList<Flower> tst= (ArrayList<Flower>) is.readObject();
	             	        	//model.cont = (ArrayList<Flower>)tst.clone();
	             	        	//model.fireTableDataChanged();
	            	        	//is.close();
	             	        	//System.out.println(model.cont.size());
             	        }

             	        }
             	       
             	        catch(FileNotFoundException fe){
             	        	System.err.println(fe);
             	        	System.err.println("File is not found");
             	        }
             	        catch(IOException ie){
             	        	System.err.println(ie);
             	        	System.err.println("Error of access");
             	        }
                	 }
                }
            }
        }
    }

	 public TestCollection(String name){
		 super(name);
		 model=new FlowerTableModel();
		 String path;
		 String fname;
		 Integer t;
		 String orig;
		 BufferedReader br;
		 Flower f;
		 table = new JTable(model);
		 try {
			br = new BufferedReader(new FileReader("in.txt"));
			Integer n=Integer.parseInt(br.readLine());
			for(int i=0; i<n; ++i){
				path=br.readLine();
				fname=br.readLine();
				t=Integer.parseInt(br.readLine());
				orig=br.readLine();
				f=new Flower(path,fname,t,orig);
				model.addRow(f);
			}
		 }
		 catch (FileNotFoundException e1) {
			e1.printStackTrace();
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }	 
		 
	     table.setFillsViewportHeight(true);
	     table.setRowHeight(90);
		 JScrollPane sp = new JScrollPane(table);
	     JPanel panel = new JPanel();
	     getContentPane().setLayout(new BorderLayout(50, 20));
	     getContentPane().add(sp, BorderLayout.CENTER);
	     JPanel pan = new JPanel(new GridLayout(1, 2, 50, 0));
	     JButton addFlower = new JButton("Add new flower");
	     addFlower.addActionListener(this);
	     pan.add(addFlower);
	     JButton delFlower = new JButton("Delete flower");
	     delFlower.addActionListener(this);
	     pan.add(delFlower);
	     JButton openFile = new JButton("Open File");
	     openFile.addActionListener(this);
	     pan.add(openFile);
	     JButton saveFile = new JButton("Save into File");
	     saveFile.addActionListener(this);
	     pan.add(saveFile);
	     getContentPane().add(pan, BorderLayout.SOUTH);
		}

	public static void main(String[] args) {
		TestCollection app = new TestCollection("Greenhouse");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(600, 540);
        app.setLocation(200, 180);        
		SplashDialog dlg = new SplashDialog(app,"Welcome!");
		dlg.setVisible(true);
		app.setVisible(true);        
     }
}
