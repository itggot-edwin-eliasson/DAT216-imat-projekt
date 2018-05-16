package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class Controller implements Initializable {

    IMatDataHandler dh = IMatDataHandler.getInstance();
    ProductCategory[] pc = ProductCategory.values();
    ShoppingCart sc = dh.getShoppingCart();
    Translator tr = new Translator();
    User user;

    @FXML private FlowPane categoryMenu;
    @FXML private FlowPane productListFlowPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private FlowPane shoppingCartPreview;
    @FXML private ScrollPane listView;
    @FXML private AnchorPane registerPane;
    @FXML private AnchorPane storePane;
    @FXML private Label userName;
    @FXML private AnchorPane registerStart;
    @FXML private AnchorPane registerEnd;
    @FXML private AnchorPane registerRegisterPane;
    @FXML private AnchorPane registerEditInformation;
    @FXML private Label totalPrice;
    @FXML private FlowPane navMenu;
    @FXML private FlowPane orderHistoryFlowPane;
    @FXML private AnchorPane orderHistoryPane;



    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Product product : dh.getProducts()){
            ProductListItem item = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), item);
        }

        updateProductList();
        setCategory();
        setNavMenu();
        if(user == null){
            userName.setText("");
        } else {
            userName.setText(user.getUserName());
        }
        registerStart.toFront();
        storePane.toFront();
    }

    private void updateProductList(){
        productListFlowPane.getChildren().clear();
        List<Product> products = dh.getProducts();
        ProductListItem item;
        for(int i = 0; i < products.size(); i++){
            item = productListItemMap.get(products.get(i).getName());
            productListFlowPane.getChildren().add(item);
        }
    }

    private void updateProductList(List<Product> products){
        productListFlowPane.getChildren().clear();
        ProductListItem item;
        for(int i = 0; i < products.size(); i++){
            item = productListItemMap.get(products.get(i).getName());
            productListFlowPane.getChildren().add(item);
        }
    }

    private void setCategory() {
        categoryMenu.getChildren().clear();
        for(int i = 0; i < pc.length; i++){
            CategoryListItem item = new CategoryListItem(pc[i], this);
            categoryMenu.getChildren().add(item);
        }
    }

    private void setNavMenu(){
        navMenu.getChildren().clear();
        NavMenuListItem item = new NavMenuListItem("Startsida", this);
        navMenu.getChildren().add(item);
        item = new NavMenuListItem("Min sida", this);
        navMenu.getChildren().add(item);
        item = new NavMenuListItem("Orderhistorik", this);
        navMenu.getChildren().add(item);
    }

    private void setOrderHistory(){
        orderHistoryFlowPane.getChildren().clear();
        OrderHistoryListItem item;
        for(int i = 0; i < dh.getOrders().size(); i++){
            item = new OrderHistoryListItem(dh.getOrders().get(i), this);
            orderHistoryFlowPane.getChildren().add(item);
        }
    }

    private void updateShoppingList(){
        List<ShoppingItem> shoppingList = sc.getItems();
        shoppingCartFlowPane.getChildren().clear();
        for(int i = 0; i < shoppingList.size(); i++){
            ShoppingCartListItem item = new ShoppingCartListItem(shoppingList.get(i), this);
            shoppingCartFlowPane.getChildren().add(item);
        }

    }

    private void updateShoppingView(){
        List<ShoppingItem> shoppingList = sc.getItems();
        shoppingCartPreview.getChildren().clear();
        for(int i = 0; i < shoppingList.size(); i++){
            ShoppingCartListItem item = new ShoppingCartListItem(shoppingList.get(i), this);
            shoppingCartPreview.getChildren().add(item);
        }
        totalPrice.setText(sc.getTotal() + " kr");

    }

    public void addToShoppingCart(Product product, double amount){
        boolean contains = false;
        for(int i = 0; i < sc.getItems().size(); i++){
            if(product.getName() == sc.getItems().get(i).getProduct().getName()){
                contains = true;
                break;
            }
        }
        if(!contains) {
            ShoppingItem shoppingItem = new ShoppingItem(product, amount);
            sc.addItem(shoppingItem);
            updateShoppingList();
            updateShoppingView();
        }
    }

    public void removeFromShoppingCart(ShoppingItem item){
        sc.removeItem(item);
        updateShoppingList();
        updateShoppingView();
    }

    public void getCategory(ProductCategory category){
        listView.toFront();
        updateProductList(dh.getProducts(category));
    }

    public void goTo(String navMenuName){
        switch (navMenuName){
            case "Startsida":
                storePane.toFront();
                listView.toFront();
            case "Min sida":

            case "Orderhistorik":
                storePane.toFront();
                orderHistoryPane.toFront();
        }
    }

    @FXML
    private void selectListPane(){
        listView.toFront();
    }

    @FXML
    private void toRegister(){
        registerPane.toFront();
    }

    @FXML
    private void toStore(){
        storePane.toFront();
    }

    @FXML
    private void toRegisterInformation(){
        registerEditInformation.toFront();
    }

    @FXML
    private void toRegisterRegister(){
        registerRegisterPane.toFront();
    }

    @FXML
    private void toRegisterEnd(){
        registerEnd.toFront();
    }

    @FXML
    private void toRegisterStart(){
        registerStart.toFront();
    }

    @FXML
    private void placeOrder(){
        dh.placeOrder();
    }
}