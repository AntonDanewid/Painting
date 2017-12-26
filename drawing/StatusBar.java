package drawing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class StatusBar extends JPanel {

	private DrawingModel model;
	private int nbr;
	private JLabel counter;
	private JLabel selection;

	//The statusbar for the GUI. 
	public StatusBar(DrawingModel model) {
		this.model = model;


		this.setPreferredSize(new Dimension(800, 40));
		 counter = new JLabel(nbr + " Strokes");
		selection = new JLabel();
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(counter, 0);
		this.add(selection, 1);
		

	}
	
	@Override
	public void paintComponent(Graphics g) {
		counter.setText(model.nbrOfShapes() + " Strokes");
		if(model.isSelected()) {
			selection.setText(", " + model.getCurrent().npoints() + " points, Scale: " + model.getCurrent().scale + ", rotation: " + model.getCurrent().getRotation());
		} else {
			selection.setText("");
		}
		
		super.paintComponent(g);
	}

}
