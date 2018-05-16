package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavMenuListItem extends AnchorPane {

    String menuName;
    Controller parentController;

    @FXML private Label navName;

    public NavMenuListItem(String menuName, Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("navmenu-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.menuName = menuName;
        this.parentController = controller;

        navName.setText(menuName);
    }

    @FXML
    private void goTo(){
        System.out.println(menuName);
        parentController.goTo(menuName);
    }
}
