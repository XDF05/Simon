package g56020.simon.view;

import g56020.simon.controller.Controller;
import g56020.simon.model.Model;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class MainView {

    private final Model model;
    private final Controller controller;

    private final static int SCENE_WIDTH = 600;
    private final static int SCENE_HEIGHT = 600;

    List<ColoredRectangle> coloredRectangles;

    public MainView(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    /**
     * Creates the main windows, with every element inside it.
     *
     * @param primaryStage primary stage from the window
     */
    public void start(Stage primaryStage) {
        RootPane root = new RootPane(model);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        coloredRectangles = root.getColoredRectangles();
        //sets the height and width of each colored rectangle according to the scene's height / width
        coloredRectangles.forEach((coloredRectangle) -> {
            coloredRectangle.heightProperty().bind(Bindings.divide(scene.heightProperty(), 2));
            coloredRectangle.widthProperty().bind(Bindings.divide(scene.widthProperty(), 2));
        });

        //startButton pressed
        root.getBStart().setOnAction(e -> {
            double speed = root.getSliderValue();
            controller.start(speed);
        });
        //longestButton pressed
        root.getBLongest().setOnAction(e -> controller.longest());
        //longestButton pressed
        root.getBLast().setOnAction(e -> controller.last());

        /*
         * Verifies each time a user presses on a colored Rectangle whether it follows the right sequence,
         * if not, shows the starting menu again.
         */
        coloredRectangles.forEach((coloredRectangle) -> coloredRectangle.setOnMouseClicked(e -> controller.click((Color) coloredRectangle.getFill())));

        primaryStage.setTitle("g56020 - Simon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}