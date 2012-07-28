package by.kdv.clientrmi;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class InsertDialog extends JDialog implements ActionListener{
	
	//private ClientFrameBundle clientFrameBundle;
	
	//private JTextField team1Field;
	//private JTextField team2Field;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea team1Area;
	private JTextArea team2Area;
	
	private JComboBox team1CountBox;
	private JComboBox team2CountBox;
	private JButton insertButton;
	
	private JComboBox dayBox;
	private JComboBox monthBox;
	private JComboBox yearBox;
	private Date date;
	
	private String team1String;
	private String team2String;
	private String countString;

	
    private boolean ret;
    
 

	public InsertDialog(JFrame frame, String title, boolean modal,String[]words) {
		// TODO Auto-generated constructor stub
		super(frame,title,modal);
		
		//clientFrameBundle = clientFrBundle;

        setLayout(new GridLayout(5,1));
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2,1));
        
        
        team1Area = new JTextArea(40,60);
        team1Area.setFont(new Font("Comic Sans MS",Font.PLAIN, 14));
        team1Area.setForeground(new Color(139,0,139));
        team1Area.setBorder(BorderFactory.createTitledBorder(words[0]));
        team1Area.setEditable(true);
        team1Area.setBackground(new Color(152, 251, 152));
                       
        team2Area = new JTextArea(40,60);
        team2Area.setFont(new Font("Comic Sans MS",Font.PLAIN, 14));
        team2Area.setForeground(new Color(139,0,139));
        team2Area.setBorder(BorderFactory.createTitledBorder(words[1]));
        team2Area.setEditable(true);
        team2Area.setBackground(new Color(0, 255, 127));
        
        fieldPanel.add(team1Area);
        fieldPanel.add(team2Area);
        
        Container contents = this.getContentPane();
        contents.add(team1Area);
        contents.add(team2Area);
        
		Vector<String>days = new Vector<String>();
		Vector<String>months = new Vector<String>();
		Vector<String>years = new Vector<String>();
		Vector<String>counts = new Vector<String>();
		years.add("2010");
		years.add("2011");
		years.add("2012");
		for(int i = 0; i < 31; i++) {
			if(i<12){
				if(i<9){
					counts.add(String.format("%d", i+1));
					days.add(String.format("0%d", i+1));
					months.add(String.format("0%d", i+1));
				}
				else {
					counts.add(String.format("%d", i+1));
					days.add(String.format("%d", i+1));
					months.add(String.format("%d", i+1));
				}
			}else days.add(String.format("%d", i+1));
		}
		dayBox = new JComboBox(days);
		monthBox = new JComboBox(months);
		yearBox = new JComboBox(years);
		team1CountBox = new JComboBox(counts);
		team2CountBox = new JComboBox(counts);
		
		JPanel countPanel = new JPanel();
		countPanel.setBackground(new Color(154, 255, 154));
		countPanel.setBorder(BorderFactory.createTitledBorder(words[2]));
		countPanel.add(team1CountBox);
		JLabel label = new JLabel(":");
		countPanel.add(label);
		countPanel.add(team2CountBox);
		contents.add(countPanel);
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(0, 238, 118));
		startPanel.setBorder(BorderFactory.createTitledBorder(words[3]));
		startPanel.add(dayBox);
		startPanel.add(monthBox);
		startPanel.add(yearBox);
		contents.add(startPanel);
		
		insertButton = new JButton(words[4]);
		insertButton.addActionListener(this);
		JPanel butPanel = new JPanel();
		butPanel.setBackground(new Color(144, 238, 144));
		butPanel.add(insertButton);
		contents.add(butPanel);

	}



	@Override
	public void actionPerformed(ActionEvent ev) {
		JButton button = (JButton) ev.getSource();
		if(button.equals(insertButton)){
			team1String = team1Area.getText().toLowerCase().toString();
			team2String = team2Area.getText().toLowerCase().toString();
			
			String count1 = (String) team1CountBox.getSelectedItem();
			String count2 = (String) team2CountBox.getSelectedItem();
			countString = String.format("%s:%s", count1,count2);

			String day = (String) dayBox.getSelectedItem();
			String month = (String) monthBox.getSelectedItem();
			String year = (String) yearBox.getSelectedItem();
			String dateString = String.format("%s-%s-%s", year,month,day);
			date = Date.valueOf(dateString);
			ret = true;
            this.setVisible(false);

		}else{
            ret = false;
            this.setVisible(false);
        }

	}

	public Date getDate() {
		return date;
	}

	public String getTeam1String() {
		return team1String;
	}

	public String getTeam2String() {
		return team2String;
	}

	public String getCountString() {
		return countString;
	}

	public boolean isRet() {
		return ret;
	}
}
