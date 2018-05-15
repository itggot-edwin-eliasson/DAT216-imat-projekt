package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.*;

import java.awt.*;
import java.io.IOException;

public class ProductListItem extends AnchorPane {
    Controller parentController;
    Product product;
    double amount = 1;

    @FXML private Label productName;
    @FXML private Label productInfo;
    @FXML private ImageView productImage;
    @FXML private TextField productAmount;

    public ProductListItem(Product product, Controller controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.product = product;
        this.parentController = controller;

        productName.setText(product.getName());
        productInfo.setText(product.getPrice() + product.getUnit());
        productImage.setImage(parentController.dh.getFXImage(product));
        productAmount.setText((int)amount + "");

    }

    @FXML public void addToCart(){
        parentController.addToShoppingCart(product, amount);
    }

    @FXML protected void incAmount(){
        if(amount < 1000)
            amount += 1;
            productAmount.setText((int)amount + "");
    }

    @FXML protected void decAmount(){
        if(amount > 1)
            amount -= 1;
            productAmount.setText((int)amount + "");
    }
}
