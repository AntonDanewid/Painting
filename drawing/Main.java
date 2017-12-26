package drawing;

import javax.vecmath.Point2d;

public class Main {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		Window w = new Window(model);
		model.addObserver(w);
		
		
	}

}
