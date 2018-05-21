package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavMenuListItem extends AnchorPane {

    String menuName;
    Controller parentController;

    @FXML private Label navName;
    @FXML private ImageView navImage;

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
        Image img;
        switch(menuName){
            case "Startsida":
                img = new Image(ProductListItem.class.getResourceAsStream("resources/outline_home_black_36dp.png"));
                navImage.setImage(img);
                break;
            case "Favoriter":
                img = new Image(ProductListItem.class.getResourceAsStream("resources/outline_favorite_border_black_36dp.png"));
                navImage.setImage(img);
                break;
            case "Orderhistorik":
                img = new Image(ProductListItem.class.getResourceAsStream("resources/outline_query_builder_black_36dp.png"));
                navImage.setImage(img);
                break;
            case "Hjälp":
                img = new Image(ProductListItem.class.getResourceAsStream("resources/outline_help_outline_black_36dp.png"));
                navImage.setImage(img);
                break;
        }
    }

    @FXML
    private void goTo(){
        parentController.goTo(menuName);
    }
}
