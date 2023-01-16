import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class StoreButtonPane extends Pane{
    private Button resetButton, addButton, removeButton, purchaseButton;

    //get methods
    public Button getResetButton() {return resetButton;}
    public Button getAddButton() {return addButton;}
    public Button getRemoveButton() {return removeButton;}
    public Button getPurchaseButton() {return purchaseButton;}

    public StoreButtonPane() {
        Pane innerPane = new Pane();

        //Create buttons
        resetButton = new Button("Reset Store");
        resetButton.setStyle("-fx-font: 12 arial;");
        resetButton.relocate(15,0);
        resetButton.setPrefSize(140, 50);

        addButton = new Button("Add To Cart");
        addButton.setStyle("-fx-font: 12 arial;");
        addButton.relocate(250,0);
        addButton.setPrefSize(140,50);

        removeButton = new Button("Remove From Cart");
        removeButton.setStyle("-fx-font: 12 arial;");
        removeButton.relocate(490,0);
        removeButton.setPrefSize(140,50);

        purchaseButton= new Button("Complete Sale");
        purchaseButton.setStyle("-fx-font: 12 arial;");
        purchaseButton.relocate(630,0);
        purchaseButton.setPrefSize(140,50);

        //Add all buttons to the pane
        innerPane.getChildren().addAll(resetButton, addButton, removeButton, purchaseButton);

        getChildren().addAll(innerPane);
    }
}
