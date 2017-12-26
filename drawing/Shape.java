package drawing;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import javax.vecmath.*;

// simple shape model class
class Shape {
	// shape points
	ArrayList<Point2d> points;

	
	
	
	private int maxY;
	private int maX; 
	private int minX;
	private int minY; 
	
	private Point2d center;
	private AffineTransform a = new AffineTransform(); 
	

	
	
	
	public void clearPoints() {
		points = new ArrayList<Point2d>();
		pointsChanged = true;
		
	}

	// add a point to end of shape
	public void addPoint(Point2d p) {
		if (points == null)
			clearPoints();
		points.add(p);
		pointsChanged = true;

	}

	// add a point to end of shape
	public void addPoint(double x, double y) {
		addPoint(new Point2d(x, y));
		
	}

	public int npoints() {
		return points.size();
	}

	private int rotation = 0;

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int i) {
		a.rotate(i- rotation);
		rotation = i;
	}

	// shape is polyline or polygon
	Boolean isClosed = false;

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	// if polygon is filled or not
	Boolean isFilled = false;

	public Boolean getIsFilled() {
		return isFilled;
	}

	public void setIsFilled(Boolean isFilled) {
		this.isFilled = isFilled;
	}

	// drawing attributes
	Color colour = Color.BLACK;
	float strokeThickness = 2.0f;

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public float getStrokeThickness() {
		return strokeThickness;
	}

	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}

	// shape's transform
	// quick hack, get and set would be better
	float scale = 1.0f;
	// some optimization to cache points for drawing
	Boolean pointsChanged = false; // dirty bit
	int[] xpoints, ypoints;
	int npoints = 0;

	void cachePointsArray() {
		xpoints = new int[points.size()];
		ypoints = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			xpoints[i] = (int) points.get(i).x;
			ypoints[i] = (int) points.get(i).y;
		}
		npoints = points.size();
		pointsChanged = false;
	}

	// let the shape draw itself
	// (note this isn't good separation of shape View from shape Model)
	public void draw(Graphics2D g2) {
		// don't draw if points are empty (not shape)
		if (points == null)
			return;
		// see if we need to update the cache
		if (pointsChanged)
			cachePointsArray();
		// save the current g2 transform matrix
		AffineTransform M = g2.getTransform();
		// multiply in this shape's transform
		// (uniform scale)
				
		
		boundingBox();
		//g2.setTransform(a);
		
		g2.translate(center.getX(), center.getY());

		g2.rotate(Math.toRadians(rotation));
		g2.scale(scale, scale);
		g2.translate(-center.getX(), -center.getY());
		
		
		a = g2.getTransform();
		// call drawing functions
		g2.setColor(colour);
		if (isFilled) {
			g2.fillPolygon(xpoints, ypoints, npoints);
		} else {
			// can adjust stroke size using scale
			g2.setStroke(new BasicStroke(strokeThickness / scale));
			if (isClosed)
				g2.drawPolygon(xpoints, ypoints, npoints);
			else
				g2.drawPolyline(xpoints, ypoints, npoints);
		}
		// reset the transform to what it was before we drew the shape
		g2.setTransform(M);
	}

	// let shape handle its own hit testing
	// (x,y) is the point to test against
	// (x,y) needs to be in same coordinate frame as shape, you could add
	// a panel-to-shape transform as an extra parameter to this function
	// (note this isn't good separation of shape Controller from shape Model)
	
	
	
	public void transform(int x, int y) {
		a.translate(x, y);
		
	}
	
	
	
	
	public boolean hittest(double x, double y) {
		if (points != null) {
			
			
//			Point2D a = new Point2D();
//			a.transform(ptSrc, ptDst);
//			a.trans
//			
			
			
			Point2d M = new Point2d(x,y);
			for (int i = 0; i < points.size(); i++) {
				if (i < points.size()-1) {
					Point2d P0 = points.get(i);
					Point2d P1 = points.get(i + 1);

					double d2 = Line2D.ptSegDist(P0.x, P0.y, P1.x, P1.y, M.x, M.y);

					if (d2 < 5 + strokeThickness) {
						
						return true;
					}
				}
			}

		}

		return false;
	}
	
	//Calculates the bounding box for this shape 
	private void boundingBox() {
		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;

		maX = Integer.MIN_VALUE;
		maxY = Integer.MIN_VALUE;
	
		
		for(Point2d p: points) {
			if(p.getX() > maX) {
				maX = (int) p.getX();
			}
			if(p.getY() > maxY) {
				maxY = (int) p.getY();
			}
			
			if(p.getX() < minX) {
				minX = (int) p.getX();
			}
			
			if(p.getY() < minY) {
				minY = (int) p.getY();
			}
			
		}
		center = new Point2d(minX + (maX- minX)/2, minY + (maxY - minY)/ 2);
		a.translate(center.x, center.y);

	}
	
	
}