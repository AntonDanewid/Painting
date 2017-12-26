package drawing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

import javax.vecmath.*;

//The model for the program
public class DrawingModel extends Observable {

	private Shape current = null;
	private ArrayList<Shape> shapes;

	public DrawingModel() {
		shapes = new ArrayList<Shape>();
	}

	// Sets the scale for the currently selected model
	public void setScale(float d) {
		if (current == null) {
			return;
		}
		current.scale = d;
		notifyObservers();
		setChanged();

	}

	// Sets current shape if the click hits a shape
	public void clicked(int x, int y) {
		// if(current != null) {
		// for(int i = 0; i < shapes.size(); i++) {
		// shapes.get(i).setStrokeThickness(2);
		// shapes.get(i).setColour(Color.BLACK);
		// }
		// }
		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).hittest(x, y)) {
				current = shapes.get(i);
				setChanged();
				notifyObservers();
				return;
			}

		}
		current = null;
		Shape shape = new Shape();
		shape.addPoint(new Point2d(x, y));
		shapes.add(shape);
		setChanged();
		notifyObservers();

	}

	// Adds a a stroke to a shape
	public void appendStroke(int x, int y) {
		shapes.get(shapes.size() - 1).addPoint(x, y);
		notifyObservers();
		setChanged();

	}

	// Rotates the currently selected stroke(from clicked(int x, int y)
	public void rotateCurrent(int i) {
		if (current != null) {
			current.setRotation(i);
			setChanged();
			notifyObservers();
		}
	}

	// Returns the list of shapes
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	// Returns the number of shapes in the model
	public int nbrOfShapes() {
		return shapes.size();

	}

	// Removes the currently selected stroke
	public void deleteCurrent() {
		if (current != null) {
			shapes.remove(current);
			current = null;
			setChanged();
			notifyObservers();
		}
	}

	// Returns true if a shape is selected, false otherwise
	public boolean isSelected() {
		if (current == null) {
			return false;
		}
		return true;
	}

	// Returns the currently selected shape
	public Shape getCurrent() {
		return current;
	}

	// Moves the currently selected shape
	public void moveCurrent(int x, int y) {
		if (current != null) {
			current.transform(x, y);
			setChanged();
			notifyObservers();
		}
	}

	public int getMaxY() {
		int yMax = Integer.MIN_VALUE;
		for (Shape s : shapes) {
			for(Point2d p: s.points) {
				if(p.y > yMax) {
					yMax = (int) p.y;
				}
			}
		}
		return yMax;
	}

	public int getMaxX() {
		int xMax = Integer.MIN_VALUE;
		for (Shape s : shapes) {
			for(Point2d p: s.points) {
				if(p.x > xMax) {
					xMax = (int) p.x;
				}
			}
		}
		return xMax;
	}

}
