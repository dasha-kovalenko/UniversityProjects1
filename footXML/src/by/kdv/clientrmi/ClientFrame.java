package by.kdv.clientrmi;

import java.awt.*;

import javax.swing.*;

import java.rmi.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.sql.Date;
import java.util.List;
import java.util.*;

import javax.swing.table.AbstractTableModel;

import by.kdv.common.FootballDataBase;
import by.kdv.common.Match;

public class ClientFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientFrameBundle clientFrameBundle;

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
	
	private JMenu file;
	private JMenu language;
	private JMenuItem insertItem;
	private JCheckBoxMenuItem englishItem;
	private JCheckBoxMenuItem russianItem;
	
	private JButton showStatisticsButton;
	private JButton showAllButton;
	private JButton deleteButton;
	
	private JTable table;

	private String[] columnNames = {"Team1","Team2","Count","Date of the match"};
	private MatchTableModel tableModel = new MatchTableModel(columnNames);

	public String[] wordsForInsertDialog = {"Enter the name of the first team:",
											"Enter the name of the second team:",
											"Set count: ",
											"Choose date of the match: ",
											"Insert"};

	private JPanel startPanel;
	private JPanel datePanel;
	private JPanel butPanel;
	private JPanel teamPanel;
	private static final Vector<String> TEAMS = new Vector<String>();
	
	public ClientFrame(String hostString, String portString) {
		frame.addWindowListener(new WindowListener());
		this.host = hostString;
		this.port = portString;
		clientFrameBundle = ClientFrameBundle.getInstance();
		
		ArrayList<Match> ms = new ArrayList<Match>();
		FootballDataBase footballDB;
		try {
			footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
					this.port+"/server.footballdb");
			ms = (ArrayList<Match>)footballDB.showDataBase();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
			JOptionPane.showMessageDialog(frame, "Remote Exception", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (NotBoundException e) {
			JOptionPane.showMessageDialog(frame, "Server not found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "Socket Exception", "Error", JOptionPane.ERROR_MESSAGE);
		}
		if(!ms.isEmpty()){
			for(Match m:ms){
				if(!TEAMS.contains(m.getTeam1()))
					TEAMS.add(m.getTeam1());
				if(!TEAMS.contains(m.getTeam2()))
					TEAMS.add(m.getTeam2());
			}
		}
		createAndShowGUI();
		addListeners();
	}

	private void createAndShowGUI() {
		frame.setTitle("Football Statistics");
		frame.setPreferredSize(new Dimension(700,600));
		//frame.setLocationRelativeTo(frame.getRootPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Container contents = frame.getContentPane();
        JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 220, 238));
		initialization();
		addComponents();
		englishItem.setSelected(true);
		menuBar.add(file);
		setJMenuBar(menuBar);
		teamBox.setBorder(BorderFactory.createTitledBorder("Choose Football Team"));
		teamPanel = new JPanel();
		teamPanel.add(teamBox);
		contents.setLayout(new BorderLayout());
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
		
		
		//GregorianCalendar g  = new GregorianCalendar();
		//String date;
		//date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(g.getTime());
		//String day = date.substring(0, 2);
		//String month = date.substring(3,5);
		
		//System.out.println(year);
		endYearBox = new JComboBox(years);

		startPanel.setLayout(new BorderLayout());
		startPanel.add(teamPanel,BorderLayout.NORTH);
		JPanel boxDatePanel = new JPanel();
		boxDatePanel.setLayout(new BorderLayout());
		datePanel.setBorder(BorderFactory.createTitledBorder("Choose Time Period"));
		datePanel.add(startDayBox);
		datePanel.add(startMonthBox);
		datePanel.add(startYearBox);
		JLabel label = new JLabel("-");
		datePanel.add(label);
		datePanel.add(endDayBox);
		datePanel.add(endMonthBox);
		datePanel.add(endYearBox);
		boxDatePanel.add(datePanel,BorderLayout.CENTER);
		teamPanel.add(datePanel);
		startPanel.add(boxDatePanel,BorderLayout.WEST);
		tableModel = new MatchTableModel(columnNames);
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
	    table.setRowHeight(25);
	    JScrollPane centerPane = new JScrollPane(table);
	    startPanel.add(centerPane, BorderLayout.CENTER);
		setColors();
		contents.add(startPanel,BorderLayout.CENTER);
		contents.add(butPanel,BorderLayout.SOUTH);
		pack();
		frame.setVisible(true);
	}
	private void addComponents() {
		// TODO Auto-generated method stub
		language.add(englishItem);
		language.add(russianItem);
		file.add(insertItem);
		file.add(language);
	    butPanel.add(showStatisticsButton);
		butPanel.add(showAllButton);
		butPanel.add(deleteButton);

	}

	private void initialization() {
		file = new JMenu("File");
		insertItem = new JMenuItem("Insert");
		language = new JMenu("Language");
		englishItem = new JCheckBoxMenuItem("English");
		russianItem = new JCheckBoxMenuItem("Russian");
		teamBox = new JComboBox(TEAMS);
		startPanel = new JPanel();
		datePanel = new JPanel();
		showStatisticsButton = new JButton("Show Statistics",new ImageIcon("football1.jpg"));
		showAllButton = new JButton("Show All",new ImageIcon("all1.jpg"));
		deleteButton = new JButton("Delete Match",new ImageIcon("delete3.jpg"));
		butPanel = new JPanel();
	}

	private void setColors(){
		startPanel.setBackground(new Color(255, 220, 238));
		datePanel.setBackground(new Color(190,190,190));
		butPanel.setBackground(new Color(190,190,190));
		teamPanel.setBackground(new Color(190,190,190));
		file.setForeground(new Color(139,0,139));
		file.setFont(new Font("Consolas", Font.ITALIC, 14));
		insertItem.setBackground(Color.cyan);
		language.setBackground(Color.cyan);
		englishItem.setBackground(Color.YELLOW);
		russianItem.setBackground(Color.YELLOW);
		showStatisticsButton.setBackground(new Color(0,255,0));
		showStatisticsButton.setForeground(new Color(255,0,255));
		showAllButton.setBackground(new Color(0,255,0));
		showAllButton.setForeground(new Color(255,0,255));
		deleteButton.setBackground(new Color(0,255,0));
		deleteButton.setForeground(new Color(255,0,255));
	}
	
	private void addListeners() {
		showStatisticsButton.addActionListener(new ButtonListener());
		showAllButton.addActionListener(new ButtonListener());
		deleteButton.addActionListener(new ButtonListener());
		insertItem.addActionListener(new ItemListener());
		englishItem.addActionListener(new ItemListener());
		russianItem.addActionListener(new ItemListener());
	}
	private class ItemListener implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent event) {
			JMenuItem item = (JMenuItem)event.getSource();
			if(item.equals(insertItem)){
				try {
					insertMatch();
					
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame, "Remote Exception");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					JOptionPane.showMessageDialog(null, "Server wasn't found");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(item.equals(englishItem)){
				russianItem.setSelected(false);
				clientFrameBundle.setLocale(Locale.ENGLISH);
				try {
					refreshUILanguage();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(item.equals(russianItem)){
				englishItem.setSelected(false);
				clientFrameBundle.setLocale(new Locale("ru"));
				try {
					refreshUILanguage();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}


		private void refreshUILanguage() throws UnsupportedEncodingException {
			frame.setTitle(clientFrameBundle.getString("title"));
			file.setText(clientFrameBundle.getString("menuFile"));
			insertItem.setText(clientFrameBundle.getString("insertItem"));
			wordsForInsertDialog [0] = clientFrameBundle.getString("team1TitleBorder");
			wordsForInsertDialog [1] = clientFrameBundle.getString("team2TitleBorder");
			wordsForInsertDialog [2] = clientFrameBundle.getString("countTitleBorder");
			wordsForInsertDialog [3] = clientFrameBundle.getString("dateTitleBorder");
			wordsForInsertDialog [4] = clientFrameBundle.getString("insertButtonTitle");
			language.setText(clientFrameBundle.getString("menuLanguage"));
			englishItem.setText(clientFrameBundle.getString("englishItem"));
			russianItem.setText(clientFrameBundle.getString("russianItem"));
			teamBox.setBorder(BorderFactory.createTitledBorder(clientFrameBundle.getString("teamBoxTitleBorder")));
			datePanel.setBorder(BorderFactory.createTitledBorder(clientFrameBundle.getString("datePanelTitleBorder")));
			String[] wordsForTableModel = {clientFrameBundle.getString("column1Name"),
										   clientFrameBundle.getString("column2Name"),
										   clientFrameBundle.getString("column3Name"),
										   clientFrameBundle.getString("column4Name")};
			tableModel.columnNames = wordsForTableModel;
			tableModel.fireTableStructureChanged();
			showStatisticsButton.setText(clientFrameBundle.getString("showStatisticsButton"));
			showAllButton.setText(clientFrameBundle.getString("showAllButton"));
			deleteButton.setText(clientFrameBundle.getString("deleteButton"));
		}

		private void insertMatch() throws RemoteException, NotBoundException, MalformedURLException, UnsupportedEncodingException {
			FootballDataBase footdb = (FootballDataBase) Naming
					.lookup("rmi://" + host + ":" + port+"/server.footballdb");
			InsertDialog dlg = new InsertDialog(frame,clientFrameBundle.getString("insertTitle"),true,wordsForInsertDialog);
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
			}
			if(!TEAMS.contains(t2)){
				TEAMS.add(t2);
			}
		}
		
	}
	
	private class WindowListener implements java.awt.event.WindowListener{

		@Override
		public void windowActivated(WindowEvent we) {
			
		}

		@Override
		public void windowClosed(WindowEvent we) {
			
		}

		@Override
		public void windowClosing(WindowEvent we) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent we) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent we) {
			
		}

		@Override
		public void windowIconified(WindowEvent we) {
			
		}

		@Override
		public void windowOpened(WindowEvent we) {
			//System.out.println("q!");
		}
		
	}
	
//////class ButtonListener	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton) event.getSource();
			if(button.equals(showStatisticsButton)){
				try {
					selectMatches();
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame, "Remote Exception");
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
						JOptionPane.showMessageDialog(frame, "Remote Exception");
					} catch (NotBoundException e) {
						JOptionPane.showMessageDialog(null, "Server not found");
					}
				}else{
					if(button.equals(deleteButton)){
						try {
							deleteMatches();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(frame, "Remote Exception");
						} catch (NotBoundException e) {
							JOptionPane.showMessageDialog(null, "Server not found");
						}
					}
	        	}
		    }
		}
	}		

	private void showDataBase() throws MalformedURLException, RemoteException, NotBoundException{
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
				this.port+"/server.footballdb");
		try {
			setResultTableModel(footballDB.showDataBase(/*frame*/));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Socket Exception");
		}

	}
	private void deleteMatches() throws MalformedURLException, RemoteException, NotBoundException {
		int sel = table.getSelectedRow();
		if(sel != -1){
			Match m =  tableModel.matches.get(sel);
			FootballDataBase footdb = (FootballDataBase) Naming
				.lookup("rmi://" + host + ":" + port+"/server.footballdb");
			String t1 = m.getTeam1();
			String t2 = m.getTeam2();
			footdb.deleteMatch(m.getId());
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

		}
		else {
			JOptionPane.showMessageDialog(frame, "Select a Row!");
	 	}
	}
	private void selectMatches() throws RemoteException, NotBoundException, MalformedURLException {
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
																	this.port+"/server.footballdb");

		String team = (String) teamBox.getSelectedItem();
		String startDay = (String) startDayBox.getSelectedItem();
		String startMonth = (String) startMonthBox.getSelectedItem();
		String startYear = (String) startYearBox.getSelectedItem();
		String startDateString = String.format("%s-%s-%s", startYear,startMonth,startDay);
		Date startDate = Date.valueOf(startDateString);
		String endDay = (String) endDayBox.getSelectedItem();
		String endMonth = (String) endMonthBox.getSelectedItem();
		String endYear = (String) endYearBox.getSelectedItem();
		String endDateString = String.format("%s-%s-%s", endYear,endMonth,endDay);
		Date endDate = Date.valueOf(endDateString);
		List <Match>m = new ArrayList<Match>();
		m = footballDB.selectMatches(/*frame, */team,startDate,endDate);
		setResultTableModel(m);
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
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ArrayList<Match> matches = new ArrayList<Match>();
		String[] columnNames = {"Team1","Team2","Count","Date of the match"};
		
		public MatchTableModel(String[] words){
			columnNames = words;
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
	    public Class<? extends Object> getColumnClass(int c) {
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
