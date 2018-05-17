package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    String username;
    String password;
    String firstName;
    String surname;
    String street;
    String streetNumber;
    String postNumber;
    String city;
    String country;
    String phone;
    String mobilePhone;
    String email;
    String cardNumber;
    String cvv;

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
    @FXML private AnchorPane loginPane;
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

    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private TextField streetField;
    @FXML private TextField streetNumberField;
    @FXML private TextField postNumberField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField phoneField;
    @FXML private TextField mobilePhoneField;
    @FXML private TextField e_mailField;
    @FXML private TextField cardNumberField;
    @FXML private TextField cvvField;
    @FXML private DatePicker date;
    @FXML private ComboBox time;

    @FXML private Label usernameLoginError;
    @FXML private Label passwordLoginError;
    @FXML private TextField usernameLoginDialogField;
    @FXML private PasswordField passwordLoginDialogField;
    @FXML private AnchorPane loginDialog;
    @FXML private AnchorPane registerDialog;
    @FXML private TextField usernameRegisterDialogField;
    @FXML private PasswordField passwordRegisterDialogField;
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
        setCategory();
        setNavMenu();
        usernameLogin();
        passwordLogin();
        usernameRegister();
        passwordRegister();
        usernameRegisterRegister();
        passwordRegisterRegister();
        usernameLoginRegister();
        passwordLoginRegister();
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

    private void textFieldSetup(){
        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    firstName = firstNameField.getText();

                }
            }
        });
        surnameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    surname = surnameField.getText();

                }
            }
        });
        streetField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    street = streetField.getText();

                }
            }
        });
        streetNumberField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    streetNumber = streetNumberField.getText();

                }
            }
        });
        postNumberField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    postNumber = postNumberField.getText();

                }
            }
        });
        cityField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    city = cityField.getText();

                }
            }
        });
        countryField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    country = countryField.getText();

                }
            }
        });
        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    firstName = firstNameField.getText();

                }
            }
        });
    }

    private void usernameLogin(){
        usernameLoginDialogField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    username = usernameLoginDialogField.getText();

                }

            }
        });
    }

    private void passwordLogin(){
        passwordLoginDialogField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    password = passwordLoginDialogField.getText();

                }

            }
        });
    }

    private void usernameRegister(){
        usernameRegisterDialogField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    username = usernameRegisterDialogField.getText();

                }

            }
        });
    }

    private void passwordRegister(){
        passwordRegisterDialogField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    password = passwordRegisterDialogField.getText();

                }

            }
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
                break;

            case "Orderhistorik":
                setOrderHistory();
                storePane.toFront();
                orderHistoryPane.toFront();
                break;
        }
    }

    public void showProductInfo(Product product){
        productInfo.toFront();
        productInfoName.setText(product.getName());
        productInfoImage.setImage(dh.getFXImage(product));
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
        if(sc.getItems().size() != 0)
            registerPane.toFront();
            registerStart.toFront();
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
        }
    }

    @FXML
    private void login(){
        User tmpUser = dh.getUser();
        if(username.equals(tmpUser.getUserName())){
            if(password.equals(tmpUser.getPassword())){
                user = tmpUser;
                user.setPassword(password);
                user.setUserName(username);
                userName.setText(user.getUserName());
                loginButton.setText("Logga ut");
                loginPane.toBack();
            }else{
                passwordLoginDialogField.focusedProperty();
                passwordLoginError.setText("Wrong password");
            }
        }else{
            usernameLoginDialogField.focusedProperty();
            usernameLoginError.setText("Wrong username");
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
                userName.setText(user.getUserName());
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
    private void register(){
        if (!username.equals("") && !password.equals("")) {
            user = dh.getUser();
            user.setUserName(username);
            user.setPassword(password);
            loginButton.setText("Logga ut");
            loginPane.toBack();
            userName.setText(user.getUserName());
        }
    }

    @FXML
    private void registerFromRegister(){
        if (!username.equals("") && !password.equals("")) {
            user = dh.getUser();
            user.setUserName(username);
            user.setPassword(password);
            registerEnd.toFront();
            userName.setText(user.getUserName());
        }
    }

    @FXML
    private void toLogin(){
        System.out.println(loginButton.getText());
        if(loginButton.getText().equals("Logga in")) {
            usernameLoginError.setText("");
            passwordLoginError.setText("");
            loginPane.toFront();
            loginDialog.toFront();
            registerEnd();
        }else{
            userName.setText("");
            user = null;
            loginButton.setText("Logga in");
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
    private void toRegisterWindow(){
        registerDialog.toFront();
    }

    @FXML
    private void exitProductInfo(){
        productInfo.toBack();
    }

    @FXML
    private void closeLogin(){
        loginPane.toBack();
    }

    @FXML
    private void backFromRegister(){
        registerPane.toBack();
    }

    @FXML
    private void toPayment(){
        paymentPane.toFront();
    }

    @FXML
    private void backFromPayment(){
        paymentPane.toBack();;
    }

    @FXML
    private void toConfirm(){
        confirmPane.toFront();
    }

    @FXML
    private void backFromConfirm(){
        confirmPane.toBack();
    }

    @FXML
    private void toOrderConfirmed(){
        orderConfirmed.toFront();
    }
}