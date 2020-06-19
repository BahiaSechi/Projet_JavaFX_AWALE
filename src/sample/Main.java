package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Awale Game");
        primaryStage.setScene(new Scene(root, 1360, 750));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("img/icon.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
