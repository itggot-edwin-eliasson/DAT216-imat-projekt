package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.w3c.dom.Text;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Flow;

public class Controller implements Initializable {

    IMatDataHandler dh = IMatDataHandler.getInstance();
    ProductCategory[] pc = ProductCategory.values();
    ShoppingCart sc = dh.getShoppingCart();
    Translator tr = new Translator();
    User user;
    Customer customer = dh.getCustomer();
    CreditCard creditCard = dh.getCreditCard();
    private String username;
    private String password;
    private String search;
    private Order tmpOrder;

    @FXML private FlowPane categoryMenu;
    @FXML private FlowPane productListFlowPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private FlowPane shoppingCartPreview;
    @FXML private ScrollPane listView;
    @FXML private AnchorPane registerPane;
    @FXML private AnchorPane storePane;
    @FXML private AnchorPane registerStart;
    @FXML private AnchorPane registerEnd;
    @FXML private AnchorPane registerRegisterPane;
    @FXML private AnchorPane registerEditInformation;
    @FXML private Label totalPrice;
    @FXML private FlowPane navMenu;
    @FXML private FlowPane orderHistoryFlowPane;
    @FXML private AnchorPane orderHistoryPane;
    @FXML private SplitPane storeSplitPane;
    @FXML private AnchorPane productInfo;
    @FXML private Label productInfoName;
    @FXML private ImageView productInfoImage;
    @FXML private AnchorPane registerLogin;
    @FXML private AnchorPane paymentPane;
    @FXML private AnchorPane confirmPane;
    @FXML private AnchorPane orderConfirmed;
    @FXML private Label shoppingCartPrice;
    @FXML private Label confirmPanePrice;
    @FXML private AnchorPane favoritesPane;
    @FXML private FlowPane favoritesFlowPane;
    @FXML private Label orderLabel;
    @FXML private AnchorPane orderPane;
    @FXML private FlowPane orderFlowPane;
    @FXML private TextField searchBarField;

    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private TextField streetField;
    @FXML private TextField postNumberField;
    @FXML private TextField cityField;
    @FXML private TextField phoneField;
    @FXML private TextField mobilePhoneField;
    @FXML private TextField e_mailField;
    @FXML private TextField cardNumberField;
    @FXML private TextField cvvField;
    @FXML private DatePicker date;
    @FXML private ComboBox time;

    @FXML private Label firstNameLabel;
    @FXML private Label surnameLabel;
    @FXML private Label streetLabel;
    @FXML private Label postNumberLabel;
    @FXML private Label cityLabel;
    @FXML private Label phoneLabel;
    @FXML private Label mobilePhoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label cardNumberLabel;
    @FXML private Label cvvLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;

    @FXML private TextField usernameLoginRegister;
    @FXML private PasswordField passwordLoginRegister;
    @FXML private TextField usernameRegisterRegister;
    @FXML private PasswordField passwordRegisterRegister;
    @FXML private Button loginButton;



    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Product product : dh.getProducts()){
            ProductListItem item = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), item);
        }

        updateProductList();
        updateFavoritesList();
        setCategory();
        setNavMenu();
        textFieldSetup();
        registerStart.toFront();
        storePane.toFront();
        listView.toFront();
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

    private void updateProductList(List<Product> products) {
        productListFlowPane.getChildren().clear();
        ProductListItem item;
        for (int i = 0; i < products.size(); i++) {
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
        item = new NavMenuListItem("Favoriter", this);
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

    private void updateOrderItemList(Order order){
        List<ShoppingItem> items = order.getItems();
        orderFlowPane.getChildren().clear();
        ProductListItem item;
        for(int i = 0; i < items.size(); i++){
            item = productListItemMap.get(items.get(i).getProduct().getName());
            item.amount = items.get(i).getAmount();
            orderFlowPane.getChildren().add(item);
        }
    }

    private void updateShoppingList(){
        List<ShoppingItem> shoppingList = sc.getItems();
        shoppingCartFlowPane.getChildren().clear();
        for(int i = 0; i < shoppingList.size(); i++){
            ShoppingCartListItem item = new ShoppingCartListItem(shoppingList.get(i), this);
            shoppingCartFlowPane.getChildren().add(item);
        }
        shoppingCartPrice.setText(sc.getTotal() + " kr");
        confirmPanePrice.setText(sc.getTotal() + " kr");
    }

    private void updateShoppingView(){
        List<ShoppingItem> shoppingList = sc.getItems();
        shoppingCartPreview.getChildren().clear();
        for(int i = 0; i < shoppingList.size(); i++){
            ShoppingCartListItem item = new ShoppingCartListItem(shoppingList.get(i), this);
            shoppingCartPreview.getChildren().add(item);
        }
        totalPrice.setText(sc.getTotal() + " kr");
        confirmPanePrice.setText(sc.getTotal() + " kr");

    }

    private void updateFavoritesList(){
        List<Product> favoritesList = dh.favorites();
        favoritesFlowPane.getChildren().clear();
        ProductListItem item;
        for(int i = 0; i < favoritesList.size(); i++){
            item = productListItemMap.get(favoritesList.get(i).getName());
            favoritesFlowPane.getChildren().add(item);
        }
    }

    private void textFieldSetup(){
        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setFirstName(firstNameField.getText());

                }
            }
        });
        surnameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setLastName(surnameField.getText());

                }
            }
        });
        streetField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setAddress(firstNameField.getText());

                }
            }
        });
        postNumberField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setPostCode(postNumberField.getText());
                }
            }
        });
        cityField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setPostAddress(firstNameField.getText());
                }
            }
        });
        phoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setPhoneNumber(phoneField.getText());
                }
            }
        });
        mobilePhoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setMobilePhoneNumber(mobilePhoneField.getText());
                }
            }
        });
        e_mailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setEmail(e_mailField.getText());
                }
            }
        });
        cardNumberField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else {
                    creditCard.setCardNumber(cardNumberField.getText());
                }
            }
        });
        cvvField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    creditCard.setVerificationCode(Integer.valueOf(cvvField.getText()));

                }
            }
        });
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = searchBarField.getText();
            search();
            listView.toFront();
        });
    }

    private void usernameLoginRegister(){
        usernameLoginRegister.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    username = usernameLoginRegister.getText();

                }

            }
        });
    }

    private void passwordLoginRegister(){
        passwordLoginRegister.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    password = passwordLoginRegister.getText();

                }

            }
        });
    }

    private void usernameRegisterRegister(){
        usernameRegisterRegister.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    username = usernameRegisterRegister.getText();

                }

            }
        });
    }

    private void passwordRegisterRegister(){
        passwordRegisterRegister.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    password = passwordRegisterRegister.getText();

                }

            }
        });
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
                updateProductList();
                storePane.toFront();
                listView.toFront();
                break;
            case "Favoriter":
                favoritesPane.toFront();
                updateFavoritesList();
                break;

            case "Orderhistorik":
                setOrderHistory();
                storePane.toFront();
                orderHistoryPane.toFront();
                break;
        }
    }

    public void setFavrite(Product product){
        dh.addFavorite(product);
    }

    public void removeFavorite(Product product){
        dh.removeFavorite(product);
    }

    public void showProductInfo(Product product){
        productInfo.toFront();
        productInfoName.setText(product.getName());
        productInfoImage.setImage(dh.getFXImage(product));
    }

    public void goToOrder(Order order){
        orderLabel.setText("Order: " + order.getDate());
        tmpOrder = order;
        updateOrderItemList(order);
        orderPane.toFront();
    }

    private void registerEnd(){
        registerEnd.toFront();
    }

    @FXML
    private void selectListPane(){
        listView.toFront();
    }

    @FXML
    private void toRegister(){
        System.out.println(dh.isCustomerComplete());
        if(sc.getItems().size() != 0 && dh.isCustomerComplete()) {
            firstNameField.setText(customer.getFirstName());
            surnameField.setText(customer.getLastName());
            streetField.setText(customer.getAddress());
            postNumberField.setText(customer.getPostCode());
            cityField.setText(customer.getPostAddress());
            phoneField.setText(customer.getPhoneNumber());
            mobilePhoneField.setText(customer.getMobilePhoneNumber());
            e_mailField.setText(customer.getEmail());
            registerPane.toFront();
            registerStart.toFront();
        } else if(sc.getItems().size() != 0){
            registerPane.toFront();
            registerStart.toFront();
        }
    }

    @FXML
    private void toStore(){
        storePane.toFront();
        listView.toFront();
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
    private void toRegisterLogin(){
        registerLogin.toFront();
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
    private void placeOrder() {
        if (sc.getItems().size() > 0){
            dh.placeOrder();
            updateShoppingView();
            updateShoppingList();
            dh.shutDown();
        }
    }

    @FXML
    private void loginFromRegister(){
        User tmpUser = dh.getUser();
        if(username.equals(tmpUser.getUserName())){
            if(password.equals(tmpUser.getPassword())){
                user = tmpUser;
                user.setPassword(password);
                user.setUserName(username);
                loginButton.setText("Logga ut");
                registerEnd.toFront();
            }else{
                passwordLoginRegister.focusedProperty();
                //passwordLoginError.setText("Wrong password");
            }
        }else{
            usernameLoginRegister.focusedProperty();
            //usernameLoginError.setText("Wrong username");
        }
    }

    @FXML
    private void registerFromRegister(){
        if (!username.equals("") && !password.equals("")) {
            user = dh.getUser();
            user.setUserName(username);
            user.setPassword(password);
            registerEnd.toFront();
        }
    }

    @FXML
    private void backFromRegisterLogin(){
        registerLogin.toBack();
    }

    @FXML
    private void backFromRegisterRegister(){
        registerRegisterPane.toBack();
    }

    @FXML
    private void backFromRegisterInformation(){
        registerEditInformation.toBack();
    }

    @FXML
    private void exitProductInfo(){
        productInfo.toBack();
    }

    @FXML
    private void backFromRegister(){
        registerPane.toBack();
    }

    @FXML
    private void toPayment(){
        if(dh.isCustomerComplete()) {
            paymentPane.toFront();
        }
    }

    @FXML
    private void backFromPayment(){
        paymentPane.toBack();;
    }

    @FXML
    private void toConfirm(){
        confirmPane.toFront();
        firstNameLabel.setText(customer.getFirstName());
        surnameLabel.setText(customer.getLastName());
        streetLabel.setText(customer.getAddress());
        postNumberLabel.setText(customer.getPostCode());
        cityLabel.setText(customer.getPostAddress());
        phoneLabel.setText(customer.getPhoneNumber());
        mobilePhoneLabel.setText(customer.getMobilePhoneNumber());
        emailLabel.setText(customer.getEmail());
        cardNumberLabel.setText(creditCard.getCardNumber());
        cvvLabel.setText(creditCard.getVerificationCode() + "");
    }

    @FXML
    private void backFromConfirm(){
        confirmPane.toBack();
    }

    @FXML
    private void toOrderConfirmed(){
        orderConfirmed.toFront();
        placeOrder();
    }

    @FXML
    private void search(){
        updateProductList(dh.findProducts(search));
        listView.toFront();
    }

    @FXML
    private void keyPressed(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                search();
                break;
        }
    }

    @FXML
    private void backFromOrder(){
        orderPane.toBack();
    }

    @FXML
    private void addFullOrderToShoppingCart(){
        List<ShoppingItem> items = tmpOrder.getItems();
        for(int i = 0; i < items.size(); i++){
            if(!sc.getItems().contains(items.get(i))){
                sc.addItem(items.get(i));
            }
        }
        updateShoppingList();
    }
}