package by.aig.clientrmi;

import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.List;
import java.util.*;
import javax.swing.table.AbstractTableModel;

import by.aig.common.FootballDataBase;
import by.aig.common.Match;



public class ClientFrame extends JFrame {

	private String host;
	private String port;
	private JFrame frame = this;
	
	private JComboBox teamBox;

	private JComboBox startDayBox;
	private JComboBox startMonthBox;
	private JComboBox startYearBox;
	
	private JComboBox endDayBox;
	private JComboBox endMonthBox;
	private JComboBox endYearBox;
	
	private JMenuItem insertItem;
	private JMenuItem deleteItem;
	
	private JButton showStatisticsButton;
	private JButton showAllButton;
	private JButton deleteButton;
	
	private JTable table;

	private MatchTableModel tableModel = new MatchTableModel();
	private static final Vector<String> TEAMS = new Vector<String>();
	
	public ClientFrame(String hostString, String portString) {
		this.host = hostString;
		this.port = portString;
		ArrayList<Match>ms = new ArrayList<Match>();
		/*try {
			//showDataBase();
			//selectMatches();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			JOptionPane.showMessageDialog(null, "Server not found");
		}*/
		FootballDataBase footballDB;
		try {
			footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
					this.port+"/server.footballdb");
			//ms = (ArrayList<Match>)footballDB.showDataBase();
			String team = new String("arsenal");/*(String) teamBox.getSelectedItem();*/
			String startDateString = new String("2012-01-01");/*String.format("%s-%s-%s", startYear,startMonth,startDay);*/
			Date startDate = Date.valueOf(startDateString);
			String endDateString = new String("2012-03-30");/*String.format("%s-%s-%s", endYear,endMonth,endDay);*/
			Date endDate = Date.valueOf(endDateString);
			//ms = (ArrayList<Match>) footballDB.selectMatches(team,startDate,endDate);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			JOptionPane.showMessageDialog(null, "Server not found");
		}
		//System.out.println(ms.toString());
		/*for(Match m:ms){
			if(!TEAMS.contains(m.getTeam1()))
				TEAMS.add(m.getTeam1());
			if(!TEAMS.contains(m.getTeam2()))
				TEAMS.add(m.getTeam2());
		}*/

		createAndShowGUI();
		addListeners();
	}

	private void createAndShowGUI() {
		frame.setTitle("Football Statistics");
		frame.setSize(new Dimension(500,700));
		frame.setLocation(10, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		Container contains = frame.getContentPane();
		
        JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 220, 238));
		JMenu file = new JMenu("File");
		file.setForeground(new Color(139,0,139));
		file.setFont(new Font("Consolas", Font.ITALIC, 14));
		insertItem = new JMenuItem("Insert");
		deleteItem = new JMenuItem("Delete");
		deleteItem.setBackground(Color.CYAN);
		insertItem.setBackground(Color.CYAN);
		file.add(insertItem);
		file.add(deleteItem);
		menuBar.add(file);
		setJMenuBar(menuBar);
		
		teamBox = new JComboBox(TEAMS);
		teamBox.setBorder(BorderFactory.createTitledBorder("Choose Football Team"));
		JPanel teamPanel = new JPanel();
		teamPanel.add(teamBox);
		contains.setLayout(new BorderLayout());
		contains.add(teamPanel, BorderLayout.NORTH);
		
		Vector<String>days = new Vector<String>();
		Vector<String>months = new Vector<String>();
		Vector<String>years = new Vector<String>();
		years.add("2010");
		years.add("2011");
		years.add("2012");
		for(int i = 0; i < 31; i++) {
			if(i<12){
				if(i<9){
					days.add(String.format("0%d", i+1));
					months.add(String.format("0%d", i+1));
				}
				else {
					days.add(String.format("%d", i+1));
					months.add(String.format("%d", i+1));
				}
			}else days.add(String.format("%d", i+1));
		}
		startDayBox = new JComboBox(days);
		startMonthBox = new JComboBox(months);
		startYearBox = new JComboBox(years);
		endDayBox = new JComboBox(days);
		endMonthBox = new JComboBox(months);
		endYearBox = new JComboBox(years);
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(255, 220, 238));
		startPanel.setBorder(BorderFactory.createTitledBorder("Choose Time Period"));
		startPanel.add(startDayBox);
		startPanel.add(startMonthBox);
		startPanel.add(startYearBox);
		JLabel label = new JLabel("-");
		startPanel.add(label);
		startPanel.add(endDayBox);
		startPanel.add(endMonthBox);
		startPanel.add(endYearBox);
		
		tableModel = new MatchTableModel();
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
	    table.setRowHeight(45);
	    JScrollPane centerPane = new JScrollPane(table);
	    startPanel.add(centerPane, BorderLayout.CENTER);

		showStatisticsButton = new JButton("Show Statistics",new ImageIcon("football1.jpg"));
		showStatisticsButton.setBackground(new Color(0,255,0));
		showStatisticsButton.setForeground(new Color(255,0,255));
		showAllButton = new JButton("Show All",new ImageIcon("all1.jpg"));
		showAllButton.setBackground(new Color(0,255,0));
		showAllButton.setForeground(new Color(255,0,255));
		deleteButton = new JButton("Delete Match",new ImageIcon("delete3.jpg"));
		deleteButton.setBackground(new Color(0,255,0));
		deleteButton.setForeground(new Color(255,0,255));

		JPanel butPanel = new JPanel();
		butPanel.add(showStatisticsButton);
		butPanel.add(showAllButton);
		butPanel.add(deleteButton);
		
		contains.add(startPanel,BorderLayout.CENTER);
		contains.add(butPanel,BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	private void addListeners() {
		showStatisticsButton.addActionListener(new ButtonListener());
		showAllButton.addActionListener(new ButtonListener());
		deleteButton.addActionListener(new ButtonListener());
		insertItem.addActionListener(new ItemListener());
		deleteItem.addActionListener(new ItemListener());
	}
	private class ItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JMenuItem item = (JMenuItem)event.getSource();
			if(item.equals(insertItem)){
				/*try {
					insertMatch();
					
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame, "Remote Exception");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					JOptionPane.showMessageDialog(null, "Server wasn't found");
				}*/
			}else
				if(item.equals(deleteItem)){
					/*try {
						deleteMatches();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NotBoundException e) {
						JOptionPane.showMessageDialog(null, "Server wasn't found");
					}*/
				}
		}

		private void deleteMatches() throws MalformedURLException, RemoteException, NotBoundException {
			FootballDataBase footdb = (FootballDataBase) Naming
								.lookup("rmi://" + host + ":" + port+"/server.footballdb");
			DeleteDialog dlg = new DeleteDialog(frame,"Delete Match Dialog",true, footdb.showDataBase());
			dlg.setSize(500,300);
			dlg.setLocation(frame.getX(), frame.getY());
			dlg.setVisible(true);
			if(dlg.isRet())
				footdb.deleteMatch(dlg.getTeam1String(),
								   dlg.getTeam2String(),
								   dlg.getCountString(),
								   dlg.getDate()
								   );
				JOptionPane.showMessageDialog(frame, "To update table push the \"Show All\" Button");

		}

		private void insertMatch() throws RemoteException, NotBoundException, MalformedURLException {
			FootballDataBase footdb = (FootballDataBase) Naming
					.lookup("rmi://" + host + ":" + port+"/server.footballdb");
			InsertDialog dlg = new InsertDialog(frame,"Insert Match Dialog",true);
			dlg.setSize(500,300);
			dlg.setLocation(frame.getX(), frame.getY());
			dlg.setVisible(true);
			if(dlg.isRet()){
				footdb.insertMatch(dlg.getTeam1String(),
								   dlg.getTeam2String(),
								   dlg.getCountString(),
								   dlg.getDate()
								   );
			}
			updateTeamComboBox(dlg.getTeam1String(),dlg.getTeam2String());
		}

		private void updateTeamComboBox(String t1,String t2) {
			if(!TEAMS.contains(t1)){
				TEAMS.add(t1);
				//teamBox.addItem(t1);
			}
			if(!TEAMS.contains(t2)){
				TEAMS.add(t2);
				//teamBox.addItem(t2);
			}
		}
		
	}
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton) event.getSource();
			if(button.equals(showStatisticsButton)){
				try {
					selectMatches();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					JOptionPane.showMessageDialog(null, "Server not found");
				}
				
			}else{
				if(button.equals(showAllButton)){
					try {
						showDataBase();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NotBoundException e) {
						JOptionPane.showMessageDialog(null, "Server not found");
					}
				}else{
					if(button.equals(deleteButton)){
						int sel = table.getSelectedRow();
						if(sel != -1){
		        			 try {
								FootballDataBase footdb = (FootballDataBase) Naming
									.lookup("rmi://" + host + ":" + port+"/server.footballdb");
								Match m =  tableModel.matches.get(sel);
								String t1 = m.getTeam1();
								String t2 = m.getTeam2();
								footdb.deleteMatch(t1, t2,m.getCount(), m.getMatchdate());
			        			tableModel.matches.remove(sel);
			        			int k = 0;int l = 0;
			        			for(Match mch: tableModel.matches){
			        				if(mch.getTeam1().equals(t1)||mch.getTeam2().equals(t1)){
			        					k = 1;
			        				}
			        				if(mch.getTeam1().equals(t2)||mch.getTeam2().equals(t2)){
			        					l = 1;
			        				}
			        			}
			        			if(k == 0) teamBox.removeItem(t1);
			        			if(l == 0) teamBox.removeItem(t2);
			        			tableModel.fireTableDataChanged();

		        			 } catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (RemoteException e) {
								e.printStackTrace();
							} catch (NotBoundException e) {
								JOptionPane.showMessageDialog(null, "Server not found");
							}
		        		 }
		        		 else {
		        			 JOptionPane.showMessageDialog(frame, "Select a Row!");
		        		 }

					}
				}
			}
		}
		
	}

	private void showDataBase() throws MalformedURLException, RemoteException, NotBoundException{
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
				this.port+"/server.footballdb");
		//setResultTableModel(footballDB.showDataBase());

	}
	private void selectMatches() throws RemoteException, NotBoundException, MalformedURLException {
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
																	this.port+"/server.footballdb");

		String team = new String("arsenal");/*(String) teamBox.getSelectedItem();*/
		/**String startDay = (String) startDayBox.getSelectedItem();
		String startMonth = (String) startMonthBox.getSelectedItem();
		String startYear = (String) startYearBox.getSelectedItem();*/
		String startDateString = new String("2012-01-01");/*String.format("%s-%s-%s", startYear,startMonth,startDay);*/
		Date startDate = Date.valueOf(startDateString);
		/**String endDay = (String) endDayBox.getSelectedItem();
		String endMonth = (String) endMonthBox.getSelectedItem();
		String endYear = (String) endYearBox.getSelectedItem();*/
		String endDateString = new String("2012-03-30");/*String.format("%s-%s-%s", endYear,endMonth,endDay);*/
		Date endDate = Date.valueOf(endDateString);
		List <Match>m = new ArrayList<Match>();
		m = footballDB.selectMatches(team,startDate,endDate);
		//setResultTableModel(m);
		//setResultTableModel(footballDB.selectMatches(team,startDate,endDate));
	}
	
	private void setResultTableModel(List<Match> list) {
		tableModel.matches.clear();
		for (Match m : list){
			tableModel.addRow(m);
		}
		tableModel.fireTableDataChanged();
	}
}

	class MatchTableModel extends AbstractTableModel
	{
		ArrayList<Match> matches = new ArrayList<Match>();
		private String[] columnNames = {"Team1","Team2","Count","Date of the match"};
		public MatchTableModel(){
			matches = new ArrayList<Match>();
		}
		public void addRow(Match m){
	        matches.add(m);
	        fireTableStructureChanged();
	    }
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		@Override
		public int getRowCount() {
			return matches.size();
		}
		@Override
		public Object getValueAt(int row, int col) {
			if(col==0) return matches.get(row).getTeam1();
			else{
				if(col==1) return matches.get(row).getTeam2();
				else{
					if(col==2) return matches.get(row).getCount();
					else return matches.get(row).getMatchdate();
				}
			}
		}	
		
	
		public String getColumnName(int col) {
	        return columnNames[col];
		}
	    public Class getColumnClass(int c) {
		    return getValueAt(0, c).getClass();
		}
	    public boolean isCellEditable(int row, int col) {
	    	return false;
	    }
	    public void setValueAt(Object value, int row, int col) {
	       Match m = matches.get(row);
	       switch(col){
	       		case 0: m.setTeam1((String)value);
	       		case 1: m.setTeam2((String)value);
	       		case 2: m.setCount((String)value);
	       		case 3: m.setMatchdate((Date)value);
	       }
	       matches.set(row, m);
	       fireTableCellUpdated(row, col);
	    }
	}
