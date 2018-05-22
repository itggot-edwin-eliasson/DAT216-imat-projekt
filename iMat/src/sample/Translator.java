package sample;

import se.chalmers.cse.dat216.project.ProductCategory;

import static se.chalmers.cse.dat216.project.ProductCategory.*;

public class Translator {

    public Translator() {

    }

    public String translate(ProductCategory category){
        String categoryName = "";
        switch (category) {
            case POD:
                categoryName = "Baljväxter";
                break;
            case BREAD:
                categoryName = "Bröd";
                break;
            case BERRY:
                categoryName = "Bär";
                break;
            case CITRUS_FRUIT:
                categoryName = "Citrus frukter";
                break;
            case HOT_DRINKS:
                categoryName = "Drycker varma";
                break;
            case COLD_DRINKS:
                categoryName = "Drycker kalla";
                break;
            case EXOTIC_FRUIT:
                categoryName = "Exotiska frukter";
                break;
            case FISH:
                categoryName = "Fisk";
                break;
            case VEGETABLE_FRUIT:
                categoryName = "Grönsaksfrukter";
                break;
            case CABBAGE:
                categoryName = "Kål";
                break;
            case MEAT:
                categoryName = "Kött";
                break;
            case DAIRIES:
                categoryName = "Mejeri";
                break;
            case MELONS:
                categoryName = "Meloner";
                break;
            case FLOUR_SUGAR_SALT:
                categoryName = "Mjöl, socker och salt";
                break;
            case NUTS_AND_SEEDS:
                categoryName = "Nötter och frön";
                break;
            case PASTA:
                categoryName = "Pasta";
                break;
            case POTATO_RICE:
                categoryName = "Potatis och ris";
                break;
            case ROOT_VEGETABLE:
                categoryName = "Rootfrukter";
                break;
            case FRUIT:
                categoryName = "Stenfrukter";
                break;
            case SWEET:
                categoryName = "Sötsaker";
                break;
            case HERB:
                categoryName = "Örter";
                break;

        }
        return categoryName;
    }
}
