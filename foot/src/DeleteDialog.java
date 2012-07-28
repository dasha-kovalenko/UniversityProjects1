import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class DeleteDialog extends JDialog implements ActionListener {

	private JComboBox team1Box;
	private JComboBox team2Box;
	private JComboBox countBox;
	private JComboBox dateBox;
	
	private String team1String;
	private String team2String;
	private String countString;
	private Date date;
	
    private boolean ret;

	public DeleteDialog(JFrame frame, String title, boolean modal, List<Match>matches) {
		// TODO Auto-generated constructor stub
		super(frame,title,modal);
		Set<String> teams1 = new TreeSet<String>();
		Set<String> teams2 = new TreeSet<String>();

		List<String>counts = new ArrayList<String>();
		List<String>dates = new ArrayList<String>();
		for(int i = 0; i<matches.size(); i++){
			Match m = matches.get(i);
			teams1.add(m.getTeam1().toString());
			teams2.add(m.getTeam2().toString());
			counts.add(m.getCount().toString());
			dates.add(m.getMatchdate().toString());
		}
        setLayout(new GridLayout(5,1));
        
        JPanel team1Panel = new JPanel();
        team1Panel.setBackground(new Color(152, 251, 152));
        team1Box = new JComboBox(teams1.toArray());
       team1Box.setBorder(BorderFactory.createTitledBorder("Select the name of the team1, you want to delete"));
       team1Panel.add(team1Box);
       JPanel team2Panel = new JPanel();
       team2Panel.setBackground(new Color(0, 255, 127));
       
        team2Box = new JComboBox(teams2.toArray());
        team2Box.setBorder(BorderFactory.createTitledBorder("Select the name of the team2, you want to delete"));
        team2Panel.add(team2Box);
        
        Container contents = this.getContentPane();
        contents.add(team1Panel);
        contents.add(team2Panel);
        
		dateBox = new JComboBox(dates.toArray());
		countBox = new JComboBox(counts.toArray());
		
		JPanel countPanel = new JPanel();
		countPanel.setBackground(new Color(154, 255, 154));
		countPanel.setBorder(BorderFactory.createTitledBorder("Set Count"));
		countPanel.add(countBox);
		contents.add(countPanel);
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(0, 238, 118));
		startPanel.setBorder(BorderFactory.createTitledBorder("Choose Date of the Match"));
		startPanel.add(dateBox);
		contents.add(startPanel);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		JPanel butPanel = new JPanel();
		butPanel.setBackground(new Color(144, 238, 144));
		butPanel.add(deleteButton);
		contents.add(butPanel);

	}



	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getActionCommand().equals("Delete")){
			team1String = team1Box.getSelectedItem().toString().toLowerCase();
			team2String = (String)team2Box.getSelectedItem().toString().toLowerCase();
			countString  = (String) countBox.getSelectedItem();
			String dateString = (String) dateBox.getSelectedItem();
			date = Date.valueOf(dateString);
			
			//Match m = new Match(1,team1String,team2String,countString,date);
			ret = true;
            this.setVisible(false);
			//System.out.println(m.toString());

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
