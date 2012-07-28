import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

class MyDialog extends JDialog implements ActionListener
{
    /*private Integer p1;
    private String p2;
    private String p3;
    public String p4;*/
	private String p1;
	private String p2;
	private Integer p3;
	private String p4;
    //private JTextField edit1;
    private JTextField edit2;
    private JTextField edit3;
    private JTextField edit4;
    private JButton browseBut;
    private boolean ret;
    public MyDialog(JFrame parent, String title, boolean modal)
    {
        super(parent,title,modal);
        setLayout(new GridLayout(5,2));
        //edit1 = new JTextField();
        edit2 = new JTextField();
        edit3 = new JTextField();
        edit4 = new JTextField();
        browseBut = new JButton("Browse...");
        browseBut.addActionListener(this);
        JButton button1 = new JButton("OK");
        button1.addActionListener(this);
        JButton button2 = new JButton("Cancel");
        button2.addActionListener(this);
        JPanel p=new JPanel();
        add(new JLabel("Enter path to flower image: "));
        p.add(browseBut);
        add(p);
        add(new JLabel("Enter its name: "));
        add(edit2);
        add(new JLabel("Enter temperature: "));
        add(edit3);
        add(new JLabel("Enter the origin of the flower: "));
        add(edit4);
        JPanel p1=new JPanel();
        p1.add(button1);
        p1.add(button2);
        add(p1);
    }
    public String getP1() {return p1;}
    public String getP2() {return p2;}
    public Integer getP3(){return p3;}
    public String getP4() {return p4;}
    public boolean isRet(){return ret;}
    public void actionPerformed(ActionEvent e){
    	if(e.getSource().equals(browseBut)){
    		JFileChooser fopen = new JFileChooser();
    		int res = fopen.showDialog(this,"Open File"); 
    		if (res == JFileChooser.APPROVE_OPTION) {  
   		      File file = fopen.getSelectedFile();
   		      p1=file.getPath();
    		}
    	} else{
	        if(e.getActionCommand().equals("OK")){
	            try{
	            	
	            	//p1=edit1.getText();
	            	p2=edit2.getText();
	            	p3=Integer.parseInt(edit3.getText());
	            	if(p3 < 0000000 || p3 > 100000) throw new NumberFormatException();
	            	p4=edit4.getText();
	            ret = true;
	            this.setVisible(false);
	            }
	            catch(NumberFormatException ex){
	                JOptionPane.showMessageDialog(this, "Error!", "NumberFormatException!", 0);
	                ret = false;
	            }
	        }
    	
	        else{
	            ret = false;
	            this.setVisible(false);
	        }
    	}
    }
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
