package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainFormController {
    public AnchorPane ArpStage;
    public static Stage stage;

    public void initialize(){

        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Home.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnHome_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Home.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnCustomer_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Customer.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }
    public void btnInventory_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Inventory.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnOrder_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Order.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }
    public void btnDelivery_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Delivery.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnSearch_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Search.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void fade(Parent root){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof Circle){
            Circle icon = (Circle) event.getSource();
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {

        if (event.getSource() instanceof Circle){
            Circle icon = (Circle) event.getSource();


            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);


        }
    }
    public void btnClose_OnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }
    public void btnMinimize_OnClicked(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void btnReports_OnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Reports.fxml"));
            ArpStage.getChildren().clear();
            ArpStage.getChildren().add(root);
            fade(root);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }
}
