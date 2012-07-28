import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;

import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.text.DateFormatter;


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
	private JList resultList; 
	private DefaultListModel listModel;

	
	private JButton showStatisticsButton;
	private JButton showAllButton;
	
	
	
	private static final String[] TEAMS = { "Manchster-United",
											"Liverpool", 
											"Arsenal", 
											"Oldham Athletic",
											"Sunderland", 
											"Any" 
										  };
	
	private ComboBoxModel boxModel;
	


	
	public ClientFrame(String hostString, String portString) {
		this.host = hostString;
		this.port = portString;
		createAndShowGUI();
		addListeners();
	}



	private void createAndShowGUI() {
		// TODO Auto-generated method stub
		frame.setTitle("Football Statistics");
		frame.setSize(new Dimension(500,500));
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
		
		listModel = new DefaultListModel();
		resultList = new JList(listModel);
		resultList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane centerPane = new JScrollPane(resultList);
		startPanel.add(centerPane, BorderLayout.CENTER);

	//	selectPanel.add(outputPanel, BorderLayout.CENTER);


		

		showStatisticsButton = new JButton("Show Statistics");
		showStatisticsButton.setBackground(new Color(0,255,0));
		showStatisticsButton.setForeground(new Color(255,0,255));
		showAllButton = new JButton("Show All");
		showAllButton.setBackground(new Color(0,255,0));
		showAllButton.setForeground(new Color(255,0,255));


	//	showStatisticsButton.setIcon(new ImageIcon("final_soccerbal.jpg"));
		JPanel butPanel = new JPanel();
		butPanel.add(showStatisticsButton);
		butPanel.add(showAllButton);
		
		
		contains.add(startPanel,BorderLayout.CENTER);
		contains.add(butPanel,BorderLayout.SOUTH);
		
		
		frame.setVisible(true);
		
	}

	
	private void addListeners() {
		showStatisticsButton.addActionListener(new ButtonListener());
		showAllButton.addActionListener(new ButtonListener());
		insertItem.addActionListener(new ItemListener());
		deleteItem.addActionListener(new ItemListener());
	}
	private class ItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			JMenuItem item = (JMenuItem)event.getSource();
			if(item.equals(insertItem)){
				try {
					insertMatch();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				if(item.equals(deleteItem)){
					try {
						deleteMatches();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}

		private void deleteMatches() throws MalformedURLException, RemoteException, NotBoundException {
			// TODO Auto-generated method stub
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

		}

		private void insertMatch() throws RemoteException, NotBoundException, MalformedURLException {
			// TODO Auto-generated method stub
		
			/*Registry registry = LocateRegistry.getRegistry(host);*/
			FootballDataBase footdb = (FootballDataBase) Naming
					.lookup("rmi://" + host + ":" + port+"/server.footballdb");
			InsertDialog dlg = new InsertDialog(frame,"Insert Match Dialog",true);
			dlg.setSize(500,300);
			dlg.setLocation(frame.getX(), frame.getY());
			dlg.setVisible(true);
			if(dlg.isRet())
				footdb.insertMatch(dlg.getTeam1String(),
								   dlg.getTeam2String(),
								   dlg.getCountString(),
								   dlg.getDate()
								   );
		
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
					e.printStackTrace();
				}
				
			}else{
				if(button.equals(showAllButton)){
					try {
						showDataBase();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	private void showDataBase() throws MalformedURLException, RemoteException, NotBoundException{
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
				this.port+"/server.footballdb");
		setResultListModel(footballDB.showDataBase());

	}
	private void selectMatches() throws RemoteException, NotBoundException, MalformedURLException {
		//Registry registry = LocateRegistry.getRegistry();
		FootballDataBase footballDB = (FootballDataBase) Naming.lookup("rmi://" + this.host + ":" + 
																	this.port+"/server.footballdb");
	//	System.out.println(this.host);
	//	System.out.println(this.port);
	//	System.out.println(footballDB);
		String team = (String) teamBox.getSelectedItem();
	//	System.out.println(team);
		String startDay = (String) startDayBox.getSelectedItem();
		String startMonth = (String) startMonthBox.getSelectedItem();
		String startYear = (String) startYearBox.getSelectedItem();
		String startDateString = String.format("%s-%s-%s", startYear,startMonth,startDay);
		Date startDate = Date.valueOf(startDateString);
	//	System.out.println(startDate.toString());
		String endDay = (String) endDayBox.getSelectedItem();
		String endMonth = (String) endMonthBox.getSelectedItem();
		String endYear = (String) endYearBox.getSelectedItem();
		String endDateString = String.format("%s-%s-%s", endYear,endMonth,endDay);
		Date endDate = Date.valueOf(endDateString);
	//	System.out.println(endDate.toString());
		
	//	System.out.println(footballDB.selectMatches(team,startDate,endDate).size());
		setResultListModel(footballDB.selectMatches(team,startDate,endDate));
	}
	
	
	//!!!!!!!!!
	private void setResultListModel(List<Match> list) {
		System.out.println(list.toString());
		listModel.clear();
		for (Match m : list)
			listModel.addElement(m);
		resultList.updateUI();
		
	}

	
}


/**	System.out.println("Date, with the default formatting: " + startDate);
String startDateString1 = dateFormat.format(startDate);
System.out.println("Date in format yyyy-MM-dd: " + startDateString1);

// Converting to String again, using an alternative format
DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd"); 
String startDateString2 = dateFormat2.format(startDate);
System.out.println("Date in format yyyy-MM-dd: " + startDateString2);
DateFormat formatter; 
Date date = new Date(); 
formatter = new SimpleDateFormat("yyyy-MM-dd");
date = (Date)formatter.parseObject(startDateString2);
System.out.println(date.toString());
*/

/*} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}*/


