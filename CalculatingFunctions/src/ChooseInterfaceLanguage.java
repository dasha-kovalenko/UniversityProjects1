import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ChooseInterfaceLanguage extends JDialog{

	public int language;
	public JDialog dlg = this;
	private boolean ret;
	public ChooseInterfaceLanguage(JFrame parent, String title, Boolean modal){
		super(parent, title,modal);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		language = -1;
		Container content = this.getContentPane();
		//panel.setBackground(new Color(255,245,238));
		JLabel label = new JLabel("Choose Language");
		content.setLayout(new BorderLayout());
		content.add(label, BorderLayout.NORTH);
		final JRadioButton englishButton = new JRadioButton("English");
		final JRadioButton frenchButton = new JRadioButton("French");
		JPanel panel1 = new JPanel();
		panel1.add(englishButton);
		panel1.add(frenchButton);
		content.add(panel1,BorderLayout.CENTER);
		JPanel panel = new JPanel();
		JButton buttonOK = new JButton("OK");
		panel.add(buttonOK);
		content.add(panel,BorderLayout.SOUTH);

		buttonOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getActionCommand().equals("buttonOK")){
					if(englishButton.isSelected())
						language = 0;
					else if(frenchButton.isSelected())
						language = 1;
					ret = true;
					dlg.setVisible(false);
				}
			}
			
		});
	}

    public boolean isRet(){return ret;}

	public int getLanguage(){
		return this.language;
	}
	/**public void setLanguage(int l){
		language = l;
	}*/
}


class SplashDialog extends JDialog implements ActionListener {
	private JButton ok;
	public SplashDialog(JFrame fr, String title) {
		super(fr,title,true);
		JPanel p=new JPanel();
		ImageIcon img = new ImageIcon("welcome_1.jpg");
		JLabel l = new JLabel(img);
		p.add(l);	
		ok = new JButton("OK");		
		p.add(ok);
		this.add(p);
		ok.addActionListener(this);
		this.setLocation(200, 200);
		this.setSize(270,195);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
	
}

