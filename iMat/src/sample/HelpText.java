package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelpText extends AnchorPane {

    private Controller parentController;

    public HelpText(Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("help-text.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        this.parentController = controller;
    }

    @FXML
    private void toStore(){
        parentController.store();
    }
}
