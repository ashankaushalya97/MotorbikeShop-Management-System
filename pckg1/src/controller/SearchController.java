package controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SearchController {
    public AnchorPane searchPane;

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof Pane){
            Pane icon = (Pane) event.getSource();
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {

        if (event.getSource() instanceof Pane){
            Pane icon = (Pane) event.getSource();


            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.GRAY);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);


        }
    }
    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof Pane) {
            Pane icon = (Pane) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "customerPane":
                    root = FXMLLoader.load(this.getClass().getResource("/view/CustomerSearch.fxml"));
                    break;
                case "inventoryPane":
                    root = FXMLLoader.load(this.getClass().getResource("/view/InventorySearch.fxml"));
                    break;
                case "ordersPane":
                    root = FXMLLoader.load(this.getClass().getResource("/view/OrderSearch.fxml"));
                    break;
                case "deliveryPane":
                    root = FXMLLoader.load(this.getClass().getResource("/view/DeliverySearch.fxml"));
                    break;
            }

            if (root != null) {
                searchPane.getChildren().clear();
                searchPane.getChildren().add(root);

                TranslateTransition tt = new TranslateTransition(Duration.millis(500),searchPane);
                tt.setFromX(searchPane.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }


}
