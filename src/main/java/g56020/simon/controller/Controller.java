package g56020.simon.controller;

import g56020.simon.model.Model;
import g56020.simon.view.MainView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {
    private final Model model;
    private final MainView view;

    public Controller(Model model, Stage stage) {
        this.model = model;
        this.view = new MainView(this, this.model);
        this.view.start(stage);
    }

    /**
     * Starts a color sequence, passes the coloredRectangles and speed to the model
     *
     * @param speed retrieved speed from the view
     */
    public void start(double speed) {
        model.start(speed);
    }

    /**
     * Opens a dialog box and shows the user's best score
     */
    public void longest() {
        model.longest();
    }

    /**
     * Opens a dialog box and shows the user's last score
     */
    public void last() {
        model.last();
    }

    /**
     * Passes to click from the View to the Model
     */
    public void click(Color color) {
        model.click(color);
    }
}
