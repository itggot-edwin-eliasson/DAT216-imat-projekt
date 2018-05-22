package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class CategoryListItem extends AnchorPane {

    ProductCategory category;
    Controller parentController;
    Translator tr = new Translator();
    private boolean selected = false;

    @FXML private Label categoryName;
    @FXML private AnchorPane background;

    public CategoryListItem(ProductCategory category, Controller controller) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category-listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.category = category;
        this.parentController = controller;

        categoryName.setText(tr.translate(category));
    }

    public void selectedBackground(){
        background.setStyle("-fx-background-color: #d0d0d0");
    }

    public void unselectedBackground(){
        background.setStyle(null);
    }

    @FXML
    private void searchCategory(){
        if(!selected) {
            selected = true;
            parentController.getCategory(category, tr.translate(category));
        } else {
            selected = false;
            unselectedBackground();
            parentController.getCategory();
        }
    }
}
