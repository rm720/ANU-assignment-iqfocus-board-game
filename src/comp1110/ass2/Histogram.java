package comp1110.ass2;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * A very simple class that draws a histogram, for demonstrating efficacy of hash functions.
 */
// Steve Blackburn on lecture
public class Histogram extends Stage {
	static final int WINDOW_WIDTH = 400;
	static final int WINDOW_HEIGHT = 500;
	static final int MARGIN = 10;
	static final int GAP = 2;
	static final int BAR_HEIGHT = WINDOW_HEIGHT - 2 * MARGIN;
	
	Histogram(String name, int[] buckets, Color color) {
		Group group = new Group();
		this.setScene(new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setTitle(name);
		int step = ((WINDOW_WIDTH - (2 * MARGIN)) / buckets.length);
		
		/* find the maximum value */
		int max = 0;
		int total = 0;
		for (int i = 0; i < buckets.length; i++) {
			total += buckets[i];
			if (buckets[i] > max) max = buckets[i];
		}
		/* find the expected */
		float expected = total/buckets.length;
		
		for (int i = 0; i < buckets.length; i++) {
			/* draw a grey bar for expected value */
			int height = ((int) (BAR_HEIGHT * expected))/max;
			Rectangle r = new Rectangle(MARGIN + (i * step), MARGIN + (WINDOW_HEIGHT - (2 * MARGIN)) - height, step - GAP, height);
			r.setFill(Color.GREY);
			group.getChildren().add(r);
			/* now draw a colored bar for actual value */
			height = (BAR_HEIGHT * buckets[i])/max;
			r = new Rectangle(MARGIN + (i * step), MARGIN + (WINDOW_HEIGHT - (2 * MARGIN)) - height, step - GAP, height);
			r.setFill(color);
			group.getChildren().add(r);
		}
	}

}
