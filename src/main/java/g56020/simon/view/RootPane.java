package g56020.simon.view;

import g56020.simon.designPattern.Observable;
import g56020.simon.designPattern.Observer;
import g56020.simon.model.Model;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class RootPane extends StackPane implements Observer {

    private final List<ColoredRectangle> coloredRectangles;
    private final StartPane startPane;

    public RootPane(Model model) {
        model.addObserver(this);

        coloredRectangles = new ArrayList<>();
        startPane = new StartPane(model);

        //Create and add each colored rectangle to coloredRectangles
        ColoredRectangle green = new ColoredRectangle("green");
        ColoredRectangle red = new ColoredRectangle("red");
        ColoredRectangle yellow = new ColoredRectangle("yellow");
        ColoredRectangle blue = new ColoredRectangle("blue");
        coloredRectangles.add(green);
        coloredRectangles.add(red);
        coloredRectangles.add(yellow);
        coloredRectangles.add(blue);

        //Positions each colored Button
        setAlignment(green, Pos.TOP_LEFT);
        setAlignment(red, Pos.TOP_RIGHT);
        setAlignment(yellow, Pos.BOTTOM_LEFT);
        setAlignment(blue, Pos.BOTTOM_RIGHT);

        //adds every element to the main pane
        this.getChildren().addAll(green, red, yellow, blue);
        this.getChildren().add(startPane);
    }

    public List<ColoredRectangle> getColoredRectangles() {
        return this.coloredRectangles;
    }

    public VBox getStartPane() {
        return this.startPane;
    }

    public double getSliderValue() {
        return startPane.getSliderValue();
    }

    public Button getBLongest() {
        return startPane.getBLongest();
    }

    public Button getBStart() {
        return startPane.getBStart();
    }

    public Button getBLast() {
        return startPane.getBLast();
    }

    public CheckBox getCbSoundToggle() {
        return startPane.getCbSoundToggle();
    }

    @Override
    public void update(Observable observable, Object args) {
        if (args.getClass().equals(Color.class)) {
            Color color = (Color) args;
            coloredRectangles.forEach((coloredRectangle -> {
                if (coloredRectangle.getFill().equals(color)) {
                    coloredRectangle.illuminate();
                    if (!getCbSoundToggle().isSelected()) {
                        try {
                            SoundMaker.playSound(coloredRectangle);
                        } catch (MidiUnavailableException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));
        }
    }
}
