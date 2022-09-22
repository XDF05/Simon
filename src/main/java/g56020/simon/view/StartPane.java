package g56020.simon.view;

import g56020.simon.designPattern.Observable;
import g56020.simon.designPattern.Observer;
import g56020.simon.model.Model;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartPane extends VBox implements Observer {

    private final Slider sSpeed;
    private final Button bLongest;
    private final Button bStart;
    private final Button bLast;
    private final CheckBox cbSoundToggle;

    public StartPane(Model model) {
        model.addObserver(this);
        //set title a title to the StartPane
        Label title = new Label("Simon");
        title.setFont(new Font(30));
        //creates Slider and label with slider's value (updates it dynamically)
        sSpeed = new Slider(0.5, 2, 1);
        sSpeed.setShowTickLabels(true);
        sSpeed.setShowTickMarks(true);

        Label lSpeed = new Label("Speed");
        lSpeed.textProperty().bind(Bindings.format("%.1f", sSpeed.valueProperty())); //updates the value of lSpeed according to the slider's value.

        //create Buttons, adds them into a ButtonBar
        bLongest = new Button("Longest");
        bStart = new Button("Start");
        bLast = new Button("Last");
        ButtonBar btnBar = new ButtonBar();
        btnBar.getButtons().addAll(bLongest, bStart, bLast);

        //creates a CheckBox
        cbSoundToggle = new CheckBox("Silent mode");

        //Set startPane position to the center
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE); //Adapts startPane's size according to its elements

        //Set the spacing between nodes in startPane
        title.setPadding(new Insets(0, 0, 30, 0));
        lSpeed.setPadding(new Insets(0, 0, 30, 0));
        cbSoundToggle.setPadding(new Insets(30, 0, 0, 0));

        //add nodes to the startPane
        this.getChildren().addAll(title, sSpeed, lSpeed, btnBar, cbSoundToggle);

        //Set a background color and padding to the startPane
        this.setStyle("-fx-background-color: white");
        this.setPadding(new Insets(15));
    }

    public double getSliderValue() {
        return sSpeed.getValue();
    }

    public Button getBLongest() {
        return bLongest;
    }

    public Button getBStart() {
        return bStart;
    }

    public Button getBLast() {
        return bLast;
    }

    public CheckBox getCbSoundToggle() {
        return cbSoundToggle;
    }

    /**
     * creates an alert dialog with a given title and header text.
     *
     * @param s given title / header text
     */
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(s);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }


    @Override
    public void update(Observable observable, Object args) {
        if (args.getClass().equals(String.class)
                && (args.equals("TIME_IS_OVER") || args.equals("GAME_OVER"))) {
            showAlert((String) args);
        }
    }
}
