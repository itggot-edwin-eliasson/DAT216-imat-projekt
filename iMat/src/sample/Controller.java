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
    private LocalDate deliveryDate = null;
    private String deliveryTime = "";
    private ShoppingItem tmpItem;

    @FXML private FlowPane categoryMenu;
    @FXML private FlowPane productListFlowPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private AnchorPane listView;
    @FXML private AnchorPane registerPane;
    @FXML private AnchorPane storePane;
    @FXML private FlowPane navMenu;
    @FXML private FlowPane orderHistoryFlowPane;
    @FXML private AnchorPane orderHistoryPane;
    @FXML private SplitPane storeSplitPane;
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
    @FXML private Label categoryTitle;
    @FXML private AnchorPane startPane;

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
    @FXML private Label confirmedTimeLabel;

    @FXML private Label firstNameErrorMessage;
    @FXML private Label lastNameErrorMessage;
    @FXML private Label adressErrorMessage;
    @FXML private Label postNumberErrorMessage;
    @FXML private Label cityErrorMessage;
    @FXML private Label phoneErrorMessage;
    @FXML private Label mobilePhoneErrorMessage;
    @FXML private Label emailErrorMessage;
    @FXML private Label cardHolderErrorMessage;
    @FXML private Label cardTypeErrorMessage;
    @FXML private Label dateErrorMessage;
    @FXML private Label cardNumberErrorMessage;
    @FXML private Label cvvErrorMessage;
    @FXML private Label deliveryDateErrorMessage;
    @FXML private Label deliveryTimeErrorMessage;


    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();
    private Map<String, CategoryListItem> categoryListItemMap = new HashMap<String, CategoryListItem>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Product product : dh.getProducts()){
            ProductListItem item = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), item);
        }

        for(ProductCategory p : pc){
            CategoryListItem item = new CategoryListItem(p, this);
            categoryListItemMap.put(tr.translate(p), item);
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
        storePane.toFront();
        startPane.toFront();
    }

    private void updateProductList(){
        productListFlowPane.getChildren().clear();
        List<Product> products = dh.getProducts();
        ProductListItem item;
        for(int i = 0; i < products.size(); i++){
            item = productListItemMap.get(products.get(i).getName());
            item.setAmount(1.0);
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
        CategoryListItem item;
        for(int i = 0; i < pc.length; i++){
            item = categoryListItemMap.get(tr.translate(pc[i]));
            item.unselectedBackground();
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
            item.setAmount(items.get(i).getAmount());
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
            item.setAmount(1.0);
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
                    firstNameErrorMessage.setText("");
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
                    lastNameErrorMessage.setText("");
                }
            }
        });
        streetField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setAddress(streetField.getText());
                    adressErrorMessage.setText("");
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
                    postNumberErrorMessage.setText("");
                }
            }
        });
        postNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (postNumberField.getText().length() > 6){
                    postNumberField.setText(postNumberField.getText().substring(0, 6));
                }
            }
        });
        cityField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else{
                    customer.setPostAddress(cityField.getText());
                    cityErrorMessage.setText("");
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
                    phoneErrorMessage.setText("");
                }
            }
        });
        phoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneField.setText(newValue.replaceAll("[^\\d]", ""));
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
                    mobilePhoneErrorMessage.setText("");
                }
            }
        });
        mobilePhoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    mobilePhoneField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        e_mailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                } else {
                    if (e_mailField.getText().contains("@")){
                        customer.setEmail(e_mailField.getText());
                        emailErrorMessage.setText("");
                    }else{
                        emailErrorMessage.setText("Ogiltig mailaddress");
                    }
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
                    cardHolderErrorMessage.setText("");
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
                    cardNumberErrorMessage.setText("");
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
                if(cardNumberField.getText().length() > 16){
                    cardNumberField.setText(cardNumberField.getText().substring(0, 16));
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
                    cvvErrorMessage.setText("");
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
                if(cvvField.getText().length() > 3){
                    cvvField.setText(cvvField.getText().substring(0, 3));
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
                cardTypeErrorMessage.setText("");
            }
        });
    }

    private void setCardMonthComboBox(){
        cardMonth.getItems().setAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        cardMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                creditCard.setValidMonth(Integer.valueOf(newValue.toString()));
                dateErrorMessage.setText("");
            }
        });
    }

    private void setCardYearComboBox(){
        cardYear.getItems().setAll("2018", "2019", "2020", "2021", "2022", "2023", "2024");
        cardYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                creditCard.setValidYear(Integer.valueOf(newValue.toString()));
                dateErrorMessage.setText("");
            }
        });
    }

    private void setDatePicker(){
        date.valueProperty().addListener((ov, oldValue, newValue) -> {
            deliveryDate = (newValue);
            deliveryDateErrorMessage.setText("");
        });
    }

    private void setCheckBox(){
        timeAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if(new_val) {
                    deliveryTime = timeAllDay.getText();
                    deliveryTimeErrorMessage.setText("");
                } else {
                    if (timeEight.isSelected() && timeOne.isSelected() && timeFour.isSelected()) {
                        deliveryTime = "08.00 - 20.00";
                    } else if (timeEight.isSelected() && timeOne.isSelected()) {
                        deliveryTime = "08.00 - 16.00";
                    } else if (timeEight.isSelected() && timeFour.isSelected()) {
                        deliveryTime = "08.00 - 12.00\n16.00 - 20.00";
                    } else if (timeOne.isSelected() && timeFour.isSelected()){
                        deliveryTime = "12.00 - 20.00";
                    } else if (timeEight.isSelected()){
                        deliveryTime = timeEight.getText();
                    } else if (timeOne.isSelected()){
                        deliveryTime = timeOne.getText();
                    } else if (timeFour.isSelected()){
                        deliveryTime = timeFour.getText();
                    } else {
                        deliveryTime = "";
                    }
                }
            }
        });
        timeEight.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if(new_val) {
                    if (timeAllDay.isSelected()){
                        //do nothing
                    } else if (timeOne.isSelected() && timeFour.isSelected()){
                        deliveryTime = "08.00 - 20.00";
                    } else if (timeOne.isSelected()){
                        deliveryTime = "08.00 - 16.00";
                    } else if (timeFour.isSelected()){
                        deliveryTime = "08.00 - 12.00\n16.00 - 20.00";
                    } else {
                        deliveryTime = timeEight.getText();
                    }
                    deliveryTimeErrorMessage.setText("");
                } else {
                    if (timeAllDay.isSelected()){
                        deliveryTime = timeAllDay.getText();
                    } else if(timeOne.isSelected() && timeFour.isSelected()){
                        deliveryTime = "12.00 - 20.00";
                    } else if (timeOne.isSelected()){
                        deliveryTime = timeOne.getText();
                    } else if (timeFour.isSelected()){
                        deliveryTime = timeFour.getText();
                    } else {
                        deliveryTime = "";
                    }
                }
            }
        });
        timeOne.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if(new_val) {
                    if (timeAllDay.isSelected()){
                        //do nothing
                    } else if (timeEight.isSelected() && timeFour.isSelected()){
                        deliveryTime = "08.00 - 20.00";
                    } else if (timeEight.isSelected()){
                        deliveryTime = "08.00 - 16.00";
                    } else if (timeFour.isSelected()){
                        deliveryTime = "12.00 - 20.00";
                    } else {
                        deliveryTime = timeOne.getText();
                    }
                    deliveryTimeErrorMessage.setText("");
                } else {
                    if (timeAllDay.isSelected()){
                        deliveryTime = timeAllDay.getText();
                    } else if (timeEight.isSelected() && timeFour.isSelected()) {
                        deliveryTime = "08.00 - 12.00\n16.00 - 20.00";
                    } else if (timeEight.isSelected()){
                        deliveryTime = timeEight.getText();
                    } else  if (timeFour.isSelected()){
                        deliveryTime = timeFour.getText();
                    } else {
                        deliveryTime = "";
                    }
                }
            }
        });
        timeFour.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if(new_val) {
                    if (timeAllDay.isSelected()){
                        //do nothing
                    } else if (timeOne.isSelected() && timeEight.isSelected()){
                        deliveryTime = "08.00 - 20.00";
                    } else if (timeOne.isSelected()){
                        deliveryTime = "12.00 - 20.00";
                    } else if (timeEight.isSelected()){
                        deliveryTime = "08.00 - 12.00\n16.00 - 20.00";
                    } else {
                        deliveryTime = timeFour.getText();
                    }
                    deliveryTimeErrorMessage.setText("");
                } else {
                    if (timeAllDay.isSelected()){
                        deliveryTime = timeAllDay.getText();
                    } else if (timeEight.isSelected() && timeOne.isSelected()) {
                        deliveryTime = "08.00 - 16.00";
                    } else if (timeEight.isSelected()){
                        deliveryTime = timeEight.getText();
                    } else if (timeOne.isSelected()){
                        deliveryTime = timeOne.getText();
                    } else {
                        deliveryTime = "";
                    }
                }
            }
        });
    }

    public void addToShoppingCart(Product product, double amount){
        boolean contains = false;
        for(int i = 0; i < sc.getItems().size(); i++){
            if(product.getName().equals(sc.getItems().get(i).getProduct().getName())){
                contains = true;
                sc.getItems().get(i).setAmount(amount);
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

    public void updateTotalPrice(){
        shoppingCartPrice.setText(String.format("%n%.2f", sc.getTotal()) + " kr");
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

    public void getCategory(ProductCategory category, String categoryName){
        setCategory();
        categoryListItemMap.get(categoryName).selectedBackground();
        listView.toFront();
        updateProductList(dh.getProducts(category));
        categoryTitle.setText("Vald kategori: " + categoryName);
    }

    public void getCategory(){
        updateProductList();
        categoryTitle.setText("Vald kategori: Alla");
    }

    public void goTo(String navMenuName){
        switch (navMenuName){
            case "Startsida":
                updateProductList();
                setCategory();
                categoryTitle.setText("Vald kategori: Alla");
                startPane.toFront();
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
        } else if(sc.getItems().size() != 0){
            registerPane.toFront();
        }
    }

    @FXML
    private void toStore(){
        emptyCartButton.setDisable(false);
        toRegisterButton.setDisable(false);
        storePane.toFront();
        listView.toFront();
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
    private void toPayment(){
        if(e_mailField.getText().contains("@")) {
            if (dh.isCustomerComplete()) {
                paymentPane.toFront();
            } else {
                if (firstNameField.getText().isEmpty()) {
                    firstNameErrorMessage.setText("Fyll i fältet");
                }
                if (surnameField.getText().isEmpty()) {
                    lastNameErrorMessage.setText("Fyll i fältet");
                }
                if (streetField.getText().isEmpty()) {
                    adressErrorMessage.setText("Fyll i fältet");
                }
                if (postNumberField.getText().isEmpty()) {
                    postNumberErrorMessage.setText("Fyll i fältet");
                }
                if (cityField.getText().isEmpty()) {
                    cityErrorMessage.setText("Fyll i fältet");
                }
                if (phoneField.getText().isEmpty()) {
                    phoneErrorMessage.setText("Fyll i fältet");
                }
                if (mobilePhoneField.getText().isEmpty()) {
                    mobilePhoneErrorMessage.setText("Fyll i fältet");
                }
                if (e_mailField.getText().isEmpty()) {
                    emailErrorMessage.setText("Fyll i fältet");
                }
            }
        } else {
            emailErrorMessage.setText("Ogiltig mailaddress");
        }
    }

    @FXML
    private void backFromRegister(){
        registerPane.toBack();
    }

    @FXML
    private void backFromPayment(){
        paymentPane.toBack();
    }

    @FXML
    private void toConfirm(){
        boolean check = true;
        if(cardHolderField.getText().isEmpty()){
            cardHolderErrorMessage.setText("Fyll i fältet");
            check = false;
        }
        if(cardType.getSelectionModel().isEmpty()){
            cardTypeErrorMessage.setText("Välj ett alternativ");
            check = false;
        }
        if(cardMonth.getSelectionModel().isEmpty() || cardYear.getSelectionModel().isEmpty()){
            dateErrorMessage.setText("Välj ett alternativ");
            check = false;
        }
        if(cardNumberField.getText().isEmpty()){
            cardNumberErrorMessage.setText("Fyll i fältet");
            check = false;
        }
        if(cardNumberField.getText().length() < 16){
            cardNumberErrorMessage.setText("Fel kortnummer");
            check = false;
        }
        if(cvvField.getText().isEmpty()){
            cvvErrorMessage.setText("Fyll i fältet");
            check = false;
        }
        if(cvvField.getText().length() < 3){
            cvvErrorMessage.setText("Fel kod");
            check = false;
        }
        if(deliveryDate == null){
            deliveryDateErrorMessage.setText("Välj ett datum");
            check = false;
        } else if(deliveryDate.isBefore(LocalDate.now())){
            deliveryDateErrorMessage.setText("Ogiltigt\nleveransdatum");
            check = false;
        }
        if(deliveryTime.isEmpty()){
            deliveryTimeErrorMessage.setText("Välj en tid");
            check = false;
        }
        if(check){
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
    }

    @FXML
    private void backFromConfirm(){
        toRegisterButton.setDisable(false);
        emptyCartButton.setDisable(false);
        confirmPane.toBack();
    }

    @FXML
    private void toOrderConfirmed(){
        if(!(sc.getItems().size() == 0)) {
            orderConfirmed.toFront();
            confirmedTimeLabel.setText("Din order levereras:\n" + deliveryDate.toString() + "\n" + deliveryTime);
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
            for(int j = 0; j < sc.getItems().size(); j++) {
                if (!items.get(i).getProduct().getName().equals(sc.getItems().get(j).getProduct().getName())) {
                    sc.addItem(items.get(i));
                } else {
                    sc.getItems().get(j).setAmount(items.get(i).getAmount());
                }
            }
        }
        updateShoppingList();
    }

    @FXML
    private void deleteCheck(){
        if(sc.getItems().size() > 0) {
            emptyShoppingCartPane.toFront();
        }
    }

    @FXML
    private void deleteShoppingCart(){
        removeAllItems();
        emptyShoppingCartPane.toBack();
    }

    private void removeAllItems(){
        if(sc.getItems().size() > 0) {
            for (int i = sc.getItems().size() - 1; i >= 0; i--) {
                sc.removeItem(sc.getItems().get(i));
            }
        }
        updateShoppingList();
    }

    @FXML
    private void toHelpPane(){
        helpPane.toFront();
    }
}