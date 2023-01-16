import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;

public class ElectronicStoreView extends Pane {
    //Model
    private ElectronicStore model;
    //Text fields
    private TextField salesText, revenueText, perSaleText;
    //List views
    private ListView<String> storeStockList, currentCartList, mostPopularItemsList;
    //Label
    private Label currentCartLabel;
    //Button pane
    private StoreButtonPane buttonPane;

    //Get methods
    public ListView<String> getStoreStockList() {return storeStockList;}
    public ListView<String> getCurrentCartList() {return currentCartList;}
    public ListView<String> getMostPopularItemsList() {return mostPopularItemsList;}

    public TextField getSalesText() {return salesText;}
    public TextField getRevenueText() {return revenueText;}
    public TextField getPerSaleText() {return perSaleText;}

    public Label getCurrentCartLabel() {return currentCartLabel;}

    public StoreButtonPane getButtonPane() {return buttonPane;}

    public ElectronicStoreView(ElectronicStore iModel) {
        model = iModel;

        //Create the labels
        Label storeSummaryLabel = new Label("Store Summary:");
        storeSummaryLabel.relocate(10,20);
        storeSummaryLabel.setAlignment(Pos.CENTER);
        storeSummaryLabel.setPrefSize(180,0);

        Label numberOfSalesLabel = new Label("# Sales:");
        numberOfSalesLabel.relocate(10,55);

        Label revenueLabel = new Label("Revenue:");
        revenueLabel.relocate(10, 105);

        Label perSaleLabel = new Label("$ / Sale:");
        perSaleLabel.relocate(10, 155);

        Label mostPopularItemsLabel = new Label("Most Popular Items:");
        mostPopularItemsLabel.relocate(10, 200);

        Label storeStockLabel = new Label("Store Stock:");
        storeStockLabel.relocate(200, 20);
        storeStockLabel.setAlignment(Pos.CENTER);
        storeStockLabel.setPrefSize(280,0);

        currentCartLabel = new Label("Current Cart:");
        currentCartLabel.relocate(500, 20);
        currentCartLabel.setAlignment(Pos.CENTER);
        currentCartLabel.setPrefSize(280,0);

        //Create TextFields
        salesText = new TextField();
        salesText.relocate(80,50);
        salesText.setPrefSize(100, 30);

        revenueText = new TextField();
        revenueText.relocate(80,100);
        revenueText.setPrefSize(100, 30);

        perSaleText = new TextField();
        perSaleText.relocate(80, 150);
        perSaleText.setPrefSize(100,30);

        //Create ListViews
        mostPopularItemsList = new ListView<String>();
        mostPopularItemsList.relocate(10, 220);
        mostPopularItemsList.setPrefSize(170, 100);

        storeStockList = new ListView<String>();
        storeStockList.relocate(200,50);
        storeStockList.setPrefSize(280, 270);

        currentCartList = new ListView<String>();
        currentCartList.relocate(500, 50);
        currentCartList.setPrefSize(280,270);

        //Create Button
        buttonPane = new StoreButtonPane();
        buttonPane.relocate(10,335);

        //Add all the components to the Pane
        getChildren().addAll(storeSummaryLabel, revenueLabel, numberOfSalesLabel, perSaleLabel,
                mostPopularItemsLabel, storeStockLabel, currentCartLabel,
                salesText, revenueText, perSaleText,
                mostPopularItemsList, storeStockList, currentCartList,
                buttonPane
        );

    }

    public void update() {
        //Set products in store's stock;
        storeStockList.setItems(FXCollections.observableArrayList(model.getDisplayProducts()));

        //Add to current cart
        currentCartList.setItems(FXCollections.observableArrayList(model.getDisplayCart2()));

        //Display popularity list
        mostPopularItemsList.setItems(FXCollections.observableArrayList(model.getPopularityList()));

        //Whether something is selected or not in the store stock
        int selectedStockIndex = storeStockList.getSelectionModel().getSelectedIndex();
        //If selected, enable add button
        if(selectedStockIndex >= 0){
            getButtonPane().getAddButton().setDisable(false);
        //If not selected, disable add button
        }else{
            getButtonPane().getAddButton().setDisable(true);
        }

        //Whether something is selected or not in the cart
        int selectedCartIndex = currentCartList.getSelectionModel().getSelectedIndex();
        //If selected, enable remove button
        if(selectedCartIndex >= 0){
            getButtonPane().getRemoveButton().setDisable(false);
            //If not selected, disable remove button
        }else{
            getButtonPane().getRemoveButton().setDisable(true);
        }

        //Enable purchase button if there are 1 or more products in cart list
        if(model.getDisplayCart().size() > 0){
            getButtonPane().getPurchaseButton().setDisable(false);
        }else{
            getButtonPane().getPurchaseButton().setDisable(true);
        }

        //Set the textFields
        salesText.setText(Integer.toString(model.getNumberOfSales()));
        revenueText.setText(String.format("%.2f", model.getRevenue()));
        if(model.getRevenue() == 0){
            perSaleText.setText("N/A");
        }else{
            perSaleText.setText(String.format("%.2f", (model.getRevenue()) / model.getNumberOfSales()));
        }

        //Set current cart label text
        currentCartLabel.setText("Current Cart: " + String.format("%.2f", model.getCartTotal()));

    }
}
