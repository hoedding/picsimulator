package picsim.mvc.view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BreakpointCellRenderer extends JLabel implements ListCellRenderer<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7082125848079406969L;
	
	public List<Integer> bplist = new ArrayList<>();

    private JList<String> ss;

	public BreakpointCellRenderer(JList<String> s) {
		
		setOpaque(true);

        ss = s;
		
		
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());

		 

        if (bplist.contains(index)) setBackground(Color.RED);

        else if (isSelected) setBackground(Color.CYAN);

        else setBackground(Color.WHITE);

 

        return this;
	}
	public void setThings(List<Integer> s) {

        bplist = s;

        

        ss.repaint();

    }
}
