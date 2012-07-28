import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.text.*;

import java.io.*;

 

public class JTextPaneDemo2 extends JFrame

{

 

 public void init() throws Exception

 {

	  final int w = 649;
	
	  final int h = 416;
	
	  JTextPane textPane = new JTextPane();
	
	  textPane = new JTextPane(){
		   public void setSize(Dimension d)
		   {
		    if(d.width <= getParent().getSize().width)
		    {
		        d.width = getParent().getSize().width;
		    }
		    super.setSize(d);
		   }
		   public boolean getScrollableTracksViewportWidth(){
		       return false;
		   }
	  };

 
  JFrame jf = new JFrame("JTextPaneDemo2");

  Container contentPane = jf.getContentPane();

  contentPane.setLayout(null);

  JScrollPane scrollPane = new JScrollPane();

  scrollPane.setBounds(new Rectangle(12, 12, w, h));

  scrollPane.getViewport().add(textPane);

  contentPane.add(scrollPane);

  jf.setSize(700,550);

  jf.setVisible(true);

 }

 

 public static void main(String args[]) throws Exception

 {

	  JTextPaneDemo2 jc = new JTextPaneDemo2();
	
	  jc.addWindowListener(new WindowAdapter()

  {

   public void windowClosing(WindowEvent e)

   {

    System.exit(0);

   }

  });

  jc.init();

 }

 

}