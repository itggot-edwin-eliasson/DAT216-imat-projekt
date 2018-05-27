package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class OrderHistoryListItem extends AnchorPane {

    Order order;
    Controller parentController;

    @FXML private Label orderDate;
    @FXML private Label orderNumber;
    @FXML private Label orderPrice;

    public OrderHistoryListItem(Order order, Controller controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order-history-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.order = order;
        this.parentController = controller;

        orderDate.setText(this.order.getDate() + "");
        orderNumber.setText(this.order.getOrderNumber() + "");
        double price = 0;
        List<ShoppingItem> items = this.order.getItems();
        for(int i = 0; i < items.size(); i++){
            price += items.get(i).getTotal();
        }
        String s = String.format("%n%.2f", price);
        orderPrice.setText(s.substring(1));
    }

    @FXML
    private void goToOrder(){
        parentController.goToOrder(order);
    }
}
