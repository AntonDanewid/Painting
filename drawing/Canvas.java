package drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;



//The Canvas class for the GUI 
public class Canvas extends JPanel {

	private JPanel panel;
	private DrawingModel model;
	private int lastX;
	private int lastY;

	public Canvas(DrawingModel model) {
		this.model = model;

		//panel = new JPanel();
		this.setBackground(Color.WHITE);
		
		//this.add(panel);
		//this.setViewportView(panel);

		//Creates a MouseAdapter (controller) for this view
		MouseAdapter listener = new MouseAdapter() {

			
			@Override
			public void mousePressed(MouseEvent e) {
				model.clicked(e.getX(), e.getY());
				
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (model.getCurrent() != null) {
					model.moveCurrent(e.getX()-lastX, e.getY()-lastY);
					lastX = e.getX();
					lastY = e.getY();
					
					
				} else {
					model.appendStroke(e.getX(), e.getY());

				}
			}

		};
		ComponentAdapter adapter = new ComponentAdapter() {
	        @Override
			public void componentResized(ComponentEvent e) {
	        	updatePerfferedSize();
	        }

		};
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.addComponentListener(adapter);

	}
	
	
	
	private void updatePerfferedSize() {
		int maxX = model.getMaxX();
		int maxY = model.getMaxY();
		if(this.getY() < maxY) {
			this.setPreferredSize(new Dimension(this.getX(), model.getMaxY()));
			getParent().revalidate();
			repaint();
		}
		if(this.getX() < maxX) {
			this.setPreferredSize(new Dimension(model.getMaxX(), this.getY()));
			getParent().revalidate();
			repaint();
		}
		if(this.getX() < maxX && this.getY() < maxY) {
			this.setPreferredSize(new Dimension(model.getMaxX(), model.getMaxY()));
			getParent().revalidate();
			repaint();
		}
		
		
		//this.setPreferredSize(new Dimension(model.getMaxX(), model.getMaxY()));
		//this.setPreferredSize(new Dimension(10000, 10000));

	}
	
	
	//Draw every shape when we repaint the canvas
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(model.getCurrent()!= null) {
			model.getCurrent().setStrokeThickness(6);
			model.getCurrent().setColour(Color.YELLOW);
			model.getCurrent().draw(g2);
			model.getCurrent().setStrokeThickness(2);
			model.getCurrent().setColour(Color.BLACK);
		}
		if (model.getShapes() != null) {
			for (Shape s : model.getShapes()) {
				s.draw(g2);
			}
		}
		
	}
}
