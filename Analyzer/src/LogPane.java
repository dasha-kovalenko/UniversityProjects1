import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class LogPane extends JTextPane {
	public static final long serialVersionUID = 1;
	
	public boolean getScrollableTracksViewportWidth(){
		return false;
	}

	public LogPane() { 
		super();
		setBackground(Color.WHITE);
		setFont(new Font("Courier New", Font.PLAIN, 14));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setEditable(false);
	}
	public void setSize(Dimension d)

	   {

	    if(d.width <= super.getSize().width)

	    {

	     d.width = super.getSize().width;

	    }

	    super.setSize(d);

	   }

	 




	public void appendStyledString(String str, Color c) {
		setEditable(true);
		Document doc = getDocument();
		StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
	    setCaretPosition(doc.getLength()); 
	    setCharacterAttributes(aset, false);
	   
	    try {
			doc.insertString(doc.getLength(), str, aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	    setEditable(false);
	}
}

