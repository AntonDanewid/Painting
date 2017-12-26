package drawing;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputListener;

public class ToolBar extends JPanel {

	/**
	 * 
	 */
	private DrawingModel model;
	private Button delete;
	private JSlider scale;
	private JSlider rotate;
	private JLabel scaleText;
	private JLabel rotText; 

	@SuppressWarnings("deprecation")
	public ToolBar(DrawingModel model) {
		this.model = model;
		//JToolBar toolbar = new JToolBar();
		//toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setPreferredSize(new Dimension(800, 40));
	
		delete = new Button("Delete");
		this.add(delete);
		this.add(new JLabel("Scale"));		
	    scale = new JSlider(50, 200);
	    this.add(scale);
	    scaleText = new JLabel(Integer.toString(scale.getValue() / 100));
	  
	    this.add(scaleText);
	    
	    this.add(new JLabel("Rotate"));		
	    rotate = new JSlider(-180, 180);
		this.add(rotate);
		rotText = new JLabel(Integer.toString(rotate.getValue()));
		this.add(rotText);
		
		
		
		
		//this.add(toolbar);
		
		

		
		
		MouseAdapter listener =new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	if(e.getSource() == delete) {
            		delete();
       
            
            	}
            	
            }
            @Override
			public void mouseDragged(MouseEvent e) {
            	if(e.getSource() == scale) {
            		scaleChange();
            	} else if(e.getSource() == rotate) {
            		rotateChange();
            }
            scaleText.setText(Double.toString(scale.getValue() / 100));
            rotText.setText(Integer.toString(rotate.getValue()));


        }};
        //Add the listener to all the components
        delete.addMouseListener(listener);
        scale.addMouseListener(listener);
        rotate.addMouseListener(listener);
		scale.addMouseMotionListener(listener);
		rotate.addMouseMotionListener(listener);

		


        
        
	}

	private void delete() {

		model.deleteCurrent();
		
	}

	private void scaleChange() {
		float s = (float) scale.getValue() / 100;
		model.setScale(s);

	}

	private void rotateChange() {
		model.rotateCurrent(rotate.getValue());

	}

	
	//The pain disables the components when a shape is not selected. 
	@Override
	public void paintComponent(Graphics g) {
		if(model.getCurrent() == null) {
			
			this.setEnabled(false);
			delete.setEnabled(false);
			rotate.setEnabled(false);
			scale.setEnabled(false);;
			scaleText.setEnabled(false);
			rotText.setEnabled(false);
		} else { 
			
			
			
			this.setEnabled(true);
			delete.setEnabled(true);
			rotate.setEnabled(true);
			scale.setEnabled(true);;
			scaleText.setEnabled(true);
			rotText.setEnabled(true);
			scaleText.setText(Float.toString(model.getCurrent().scale));
			rotText.setText(Integer.toString(model.getCurrent().getRotation()));
			scale.setValue((int) (model.getCurrent().scale * 100));
			rotate.setValue(model.getCurrent().getRotation());
			
			
			

		}
		super.paintComponent(g);
		
	}

}
