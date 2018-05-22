package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML private Label itemPrice;

    public ShoppingCartListItem(ShoppingItem shoppingItem, Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shoppingcart-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTextField();

        this.shoppingItem = shoppingItem;
        this.parentController = controller;

        productName.setText(shoppingItem.getProduct().getName());
        itemAmount.setText(String.format("%n%.2f", shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
        itemImage.setImage(parentController.dh.getFXImage(shoppingItem.getProduct()));
        String s = String.format("%n%.2f",shoppingItem.getTotal()) + "kr";
        itemPrice.setText(s.substring(1));
    }

    private void setTextField(){
        itemAmount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    if(!itemAmount.getText().isEmpty()) {
                        shoppingItem.setAmount(Double.valueOf(itemAmount.getText().substring(0, 4).replaceAll(",", ".")));
                    } else {
                        shoppingItem.setAmount(1.0);

                    }
                    String s = String.format("%n%.2f", shoppingItem.getTotal()) + "kr";
                    itemPrice.setText(s.substring(1));
                    parentController.updateTotalPrice();
                }
            }
        });
        itemAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("((\\\\d*)|(\\\\d+\\.\\\\d*))")) {
                    itemAmount.setText(newValue.replaceAll("((\\\\d*)|(\\\\d+\\.\\\\d*))", ""));
                }
                if(itemAmount.getText().length() > 9){
                    itemAmount.setText(itemAmount.getText().substring(0, 9));
                }
            }
        });
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
            parentController.updateTotalPrice();
            String s = String.format("%n%.2f",shoppingItem.getTotal()) + "kr";
            itemPrice.setText(s.substring(1));
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
        parentController.updateTotalPrice();
        String s = String.format("%n%.2f",shoppingItem.getTotal()) + "kr";
        itemPrice.setText(s.substring(1));
    }
}
