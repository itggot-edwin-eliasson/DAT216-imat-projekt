package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    IMatDataHandler dh = IMatDataHandler.getInstance();
    ProductCategory[] pc = ProductCategory.values();

    @FXML private FlowPane categoryMenu;
    @FXML private FlowPane productListFlowPane;

    private Map<String, ProductListItem> productListItemMap = new HashMap<String, ProductListItem>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Product product : dh.getProducts()){
            ProductListItem item = new ProductListItem(product, this);
            productListItemMap.put(product.getName(), item);
        }
        updateProductList();
        setCategory();
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

    private void setCategory() {
        categoryMenu.getChildren().clear();
        for(int i = 0; i < pc.length; i++){
            CategoryListItem item = new CategoryListItem(pc[i], this);
            categoryMenu.getChildren().add(item);
        }
    }


}