package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{


        ResourceBundle bundle = java.util.ResourceBundle.getBundle("sample/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("imat.fxml"), bundle);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Poiret+One");

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
