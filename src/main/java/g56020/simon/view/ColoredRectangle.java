package g56020.simon.view;

import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ColoredRectangle extends Rectangle {
    private final double defaultOpacity = 1;

    public ColoredRectangle(String color) {
        this.setId(color);
        this.setFill(Color.web(color));
        this.setOpacity(defaultOpacity);
    }

    /**
     * Illuminates the colored rectangle for a duration of 1 second
     */
    public void illuminate() {
        this.setOpacity(0.5);
        var pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(event -> this.setOpacity(defaultOpacity));
        pause.play();
    }

}
