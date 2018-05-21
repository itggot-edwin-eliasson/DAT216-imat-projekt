package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartListItem extends AnchorPane {

    ShoppingItem shoppingItem;
    Controller parentController;

    @FXML private Label productName;
    @FXML private TextField itemAmount;
    @FXML private ImageView itemImage;

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
        itemAmount.setText(String.format("%n%.2f", shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
        itemImage.setImage(parentController.dh.getFXImage(shoppingItem.getProduct()));
    }

    @FXML
    private void removeItem(){
        parentController.removeCheck(shoppingItem);
    }

    @FXML private void incAmount(){
        double amount = shoppingItem.getAmount();
        if(amount < 1000) {
            if (shoppingItem.getProduct().getUnitSuffix().equals("kg")) {
                amount += 0.1;
            } else {
                amount += 1;
            }
            shoppingItem.setAmount(amount);
            itemAmount.setText(String.format("%n%.2f", amount) + " " + shoppingItem.getProduct().getUnitSuffix());
        }
    }

    @FXML private void decAmount() {
        double amount = shoppingItem.getAmount();
        if (shoppingItem.getProduct().getUnitSuffix().equals("kg")) {
            if (amount > 0.1)
                amount -= 0.1;
        } else {
            if (amount > 1)
                amount -= 1;
        }
        shoppingItem.setAmount(amount);
        itemAmount.setText(String.format("%n%.2f", amount) + " " + shoppingItem.getProduct().getUnitSuffix());
    }
}
