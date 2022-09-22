package g56020.simon;

import g56020.simon.controller.Controller;
import g56020.simon.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var model = new Model();
        var controller = new Controller(model, primaryStage);
    }
}
