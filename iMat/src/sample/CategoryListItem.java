package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryListItem extends AnchorPane {

    String category;
    Controller parentController;

    @FXML private Label categoryName;

    public CategoryListItem(String category, Controller controller) {

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

        categoryName.setText(category);
    }
}
