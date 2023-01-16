import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ElectronicStoreApp extends Application {
    //Create model
    private ElectronicStore model;
    private ElectronicStoreView view;

    public void start(Stage primaryStage) {

        model = ElectronicStore.createStore();

        Pane aPane = new Pane();
        view = new ElectronicStoreView(model);

        //Create the view
        ElectronicStoreView view = new ElectronicStoreView(model);
        aPane.getChildren().add(view);

        primaryStage.setTitle("Electronic Store Application - Watts Up Electronics");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane, 800, 400));
        primaryStage.show();

        view.update();

        //Event when add button is pressed
        view.getButtonPane().getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Get index of the product
                int indexStockItem = view.getStoreStockList().getSelectionModel().getSelectedIndex();

                if(indexStockItem >= 0) {
                    Product[] productList = model.getStockList();
                    Product selectedProduct = productList[indexStockItem];
                    model.addToCart(selectedProduct, indexStockItem);
                }
                view.update();
            }
        });

        //Event when remove button is pressed
        view.getButtonPane().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Get index of the product
                int indexCartItem = view.getCurrentCartList().getSelectionModel().getSelectedIndex();

                if(indexCartItem >= 0) {
                    ArrayList<Product> cartList = model.getCurrentCart();
                    Product selectedCartProduct = cartList.get(indexCartItem);

                    model.removeFromCart(selectedCartProduct);
                }
                view.update();

            }
        });

        //Even when purchase button is pressed
        view.getButtonPane().getPurchaseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Product> purchaseList = model.getDisplayCart();

                //Sale goes up by one
                model.saleMade();

                //Call purchase method for each product
                for(Product p : purchaseList){
                    model.purchase(p);
                }
                view.update();
            }
        });

        //Event when reset button is pressed
        view.getButtonPane().getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                start(primaryStage);
            }
        });

        //Event when stock list is selected
        view.getStoreStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.update();
            }
        });

        //Event when cart list is selected
        view.getCurrentCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.update();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}