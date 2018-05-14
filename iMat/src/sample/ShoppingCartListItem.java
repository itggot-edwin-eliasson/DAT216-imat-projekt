package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartListItem extends AnchorPane {

    ShoppingItem shoppingItem;
    Controller parentController;

    @FXML private Label productName;

    public ShoppingCartListItem(ShoppingItem shoppingItem, Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shoppingcart-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.shoppingItem = shoppingItem;
        this.parentController = controller;

        productName.setText(shoppingItem.getProduct().getName());
    }
}
