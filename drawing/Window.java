package drawing;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class Window extends JFrame implements Observer {

	private Canvas canvas;
	private StatusBar statusbar;
	private ToolBar toolbar;
	private JScrollPane pane;

	public Window(DrawingModel model) {
		this.setTitle("A2Basic");
		this.setSize(800, 400);

		// Create the top component in the JFrame with a border layout
		JPanel panel = new JPanel(new BorderLayout());

		// Creates the ToolBar component
		toolbar = new ToolBar(model);

		// Creates the StatusBar component
		statusbar = new StatusBar(model);
		// Creates the Canvas component
		canvas = new Canvas(model);

		// Add all the components to their respective position
		pane = new JScrollPane(canvas);
		pane.setViewportView(canvas);
		

		panel.add(toolbar, BorderLayout.NORTH);
		panel.add(pane, BorderLayout.CENTER);
		
		
		panel.add(statusbar, BorderLayout.SOUTH);

		//panel.add(canvas, BorderLayout.CENTER);
		
		this.add(panel);
		// this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// The update from the Observer interface. Executes when setChanged and
	// notifyObservers is called
	@Override
	public void update(Observable o, Object arg) {
		canvas.repaint();
		statusbar.repaint();
		toolbar.repaint();
		//pane.repaint();

	}

}
