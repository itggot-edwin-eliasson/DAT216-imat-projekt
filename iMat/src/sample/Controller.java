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
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
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
    DecimalFormat df = new DecimalFormat("#.00");
    private String username;
    private String password;
    private String search;
    private Order tmpOrder;
    private LocalDate deliveryDate;
    private String deliveryTime = "";
    private ShoppingItem tmpItem;

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
    @FXML private AnchorPane registerLogin;
    @FXML private AnchorPane paymentPane;
    @FXML private AnchorPane confirmPane;
    @FXML private AnchorPane orderConfirmed;
    @FXML private Label shoppingCartPrice;
    @FXML private AnchorPane favoritesPane;
    @FXML private FlowPane favoritesFlowPane;
    @FXML private Label orderLabel;
    @FXML private AnchorPane orderPane;
    @FXML private FlowPane orderFlowPane;
    @FXML private TextField searchBarField;
    @FXML private AnchorPane helpPane;
    @FXML private AnchorPane deleteProductPane;
    @FXML private AnchorPane emptyShoppingCartPane;
    @FXML private Button toRegisterButton;
    @FXML private Button emptyCartButton;

    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private TextField streetField;
    @FXML private TextField postNumberField;
    @FXML private TextField cityField;
    @FXML private TextField phoneField;
    @FXML private TextField mobilePhoneField;
    @FXML private TextField e_mailField;
    @FXML private TextField cardHolderField;
    @FXML private ComboBox cardType;
    @FXML private ComboBox cardMonth;
    @FXML private ComboBox cardYear;
    @FXML private TextField cardNumberField;
    @FXML private TextField cvvField;
    @FXML private DatePicker date;
    @FXML private CheckBox timeAllDay;
    @FXML private CheckBox timeEight;
    @FXML private CheckBox timeOne;
    @FXML private CheckBox timeFour;

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
    @FXML private Label cardTypeLabel;
    @FXML private Label cardValidDateLabel;
    @FXML private Label cardHolderLabel;

    @FXML private TextField usernameLoginRegister;
    @FXML private PasswordField passwordLoginRegister;
    @FXML private TextField usernameRegisterRegister;
    @FXML private PasswordField passwordRegisterRegister;


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
        setCardTypeComboBox();
        setCardMonthComboBox();
        setCardYearComboBox();
        setDatePicker();
        setCheckBox();
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
        item = new NavMenuListItem("Hjälp", this);
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
        String s = String.format("%n%.2f", sc.getTotal()) + " kr";
        shoppingCartPrice.setText(s.substring(1));
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
        cardHolderField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else {
                    creditCard.setHoldersName(cardHolderField.getText());
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
        cardNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardNumberField.setText(newValue.replaceAll("[^\\d]", ""));
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
        cvvField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cvvField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = searchBarField.getText();
            search();
            listView.toFront();
        });
    }

    private void setCardTypeComboBox(){
        cardType.getItems().setAll("Matercard", "Visa", "American Express");
        cardType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditCard.setCardType(newValue);
            }
        });
    }

    private void setCardMonthComboBox(){
        cardMonth.getItems().setAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        cardMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                creditCard.setValidMonth(Integer.valueOf(newValue.toString()));
            }
        });
    }

    private void setCardYearComboBox(){
        cardYear.getItems().setAll("2018", "2019", "2020", "2021", "2022", "2023", "2024");
        cardYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                creditCard.setValidYear(Integer.valueOf(newValue.toString()));
            }
        });
    }

    private void setDatePicker(){
        date.valueProperty().addListener((ov, oldValue, newValue) -> {
            deliveryDate = (newValue);
        });
    }

    private void setCheckBox(){
        timeAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                deliveryTime = timeAllDay.getText();
            }
        });
        timeEight.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!deliveryTime.equals("") && !deliveryTime.equals(timeAllDay.getText())){
                    deliveryTime += "\neller " + timeEight.getText();
                } else if(deliveryTime.equals(timeAllDay.getText())){
                    //do nothing
                } else {
                    deliveryTime = timeEight.getText();
                }
            }
        });
        timeOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!deliveryTime.equals("") && !deliveryTime.equals(timeAllDay.getText())) {
                    deliveryTime += "\neller " + timeOne.getText();
                } else if (deliveryTime.equals(timeAllDay.getText())) {
                    //do nothing
                } else {
                    deliveryTime = timeOne.getText();
                }
            }
        });
        timeFour.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!deliveryTime.equals("") && !deliveryTime.equals(timeAllDay.getText())){
                    deliveryTime += "\neller " + timeFour.getText();
                } else if(deliveryTime.equals(timeAllDay.getText())){
                    //do nothing
                } else {
                    deliveryTime = timeFour.getText();
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
        }
        updateShoppingList();
    }


    public void removeCheck(ShoppingItem item){
        deleteProductPane.toFront();
        tmpItem = item;
    }

    @FXML
    private void removeFromShoppingCart(){
        deleteProductPane.toBack();
        sc.removeItem(tmpItem);
        updateShoppingList();
    }
    @FXML
    private void cancelProductRemoval(){
        deleteProductPane.toBack();
    }

    @FXML
    private void cancelSCRemoval(){
        emptyShoppingCartPane.toBack();
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
            case "Hjälp":
                helpPane.toFront();
                break;
        }
    }

    public void setFavrite(Product product){
        dh.addFavorite(product);
    }

    public void removeFavorite(Product product){
        dh.removeFavorite(product);
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
        firstNameLabel.setText(customer.getFirstName());
        surnameLabel.setText(customer.getLastName());
        streetLabel.setText(customer.getAddress());
        postNumberLabel.setText(customer.getPostCode());
        cityLabel.setText(customer.getPostAddress());
        phoneLabel.setText(customer.getPhoneNumber());
        mobilePhoneLabel.setText(customer.getMobilePhoneNumber());
        emailLabel.setText(customer.getEmail());
        cardNumberLabel.setText(creditCard.getCardNumber());
        cardHolderLabel.setText(creditCard.getHoldersName());
        cardValidDateLabel.setText(creditCard.getValidMonth() + "/" + creditCard.getValidYear());
        cardTypeLabel.setText(creditCard.getCardType());
        cvvLabel.setText(creditCard.getVerificationCode() + "");
        dateLabel.setText(deliveryDate.toString());
        timeLabel.setText(deliveryTime);
        emptyCartButton.setDisable(true);
        toRegisterButton.setDisable(true);
        confirmPane.toFront();
    }

    @FXML
    private void backFromConfirm(){
        confirmPane.toBack();
    }

    @FXML
    private void toOrderConfirmed(){
        if(!(sc.getItems().size() == 0)) {
            orderConfirmed.toFront();
            placeOrder();
        }
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

    @FXML
    private void deleteCheck(){
        emptyShoppingCartPane.toFront();
    }

    @FXML
    private void deleteShoppingCart(){
        removeAllItems();
    }

    private void removeAllItems(){
        if(sc.getItems().size() > 0) {
            for (int i = sc.getItems().size(); i > 0; i++) {
                sc.removeItem(sc.getItems().get(i));
            }
        }
    }
}