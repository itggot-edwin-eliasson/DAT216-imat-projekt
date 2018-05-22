package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML private ImageView favoriteImage;

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
        productAmount.setText(amount + "");
        Image img;
        if(parentController.dh.isFavorite(product)){
            img = new Image(ProductListItem.class.getResourceAsStream("resources/baseline_favorite_black_18dp.png"));
            favoriteImage.setImage(img);
        } else {
            img = new Image(ProductListItem.class.getResourceAsStream("resources/baseline_favorite_border_black_18dp.png"));
            favoriteImage.setImage(img);
        }
        setTextField();

    }

    public void setAmount(double amount){
        this.amount = amount;
        productAmount.setText(this.amount + "");
    }

    private void setTextField(){
        productAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    if(!productAmount.getText().isEmpty()) {
                        amount = Double.valueOf(productAmount.getText().replaceAll(",", "."));
                    } else {
                        amount = 1.0;
                    }
                }
            }
        });
        productAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("((\\\\d*)|(\\\\d+\\.\\\\d*))")) {
                    productAmount.setText(newValue.replaceAll("((\\\\d*)|(\\\\d+\\.\\\\d*))", ""));
                }
                if(productAmount.getText().length() > 6){
                    productAmount.setText(productAmount.getText().substring(0, 6));
                }
            }
        });
    }

    @FXML public void addToCart(){
        parentController.addToShoppingCart(product, amount);
    }

    @FXML private void incAmount(){
        if(amount < 1000) {
            if (product.getUnitSuffix().equals("kg")) {
                amount += 0.1;
            } else {
                amount += 1;
            }
            productAmount.setText(String.format("%n%.2f", amount));
        }
    }

    @FXML private void decAmount() {
        if (product.getUnitSuffix().equals("kg")) {
            if (amount > 0.1)
                amount -= 0.1;
        } else {
            if (amount > 1)
                amount -= 1;
        }
        productAmount.setText(String.format("%n%.2f", amount));
    }

    @FXML private void setFavorite(){
        Image img;
        if(parentController.dh.isFavorite(product)){
            img = new Image(ProductListItem.class.getResourceAsStream("resources/baseline_favorite_border_black_18dp.png"));
            favoriteImage.setImage(img);
            parentController.removeFavorite(product);
        } else {
            img = new Image(ProductListItem.class.getResourceAsStream("resources/baseline_favorite_black_18dp.png"));
            favoriteImage.setImage(img);
            parentController.setFavrite(product);
        }
    }
}
