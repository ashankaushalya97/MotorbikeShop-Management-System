package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.LoginBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.LoginDTO;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class LoginController {
    public JFXTextField txtUsername;
    public static Stage stage;
    public AnchorPane loginPane;
    public JFXPasswordField txtPassword;
    LoginBO loginBO = BOFactory.getInstance().getBO(BOTypes.LOGIN);

    public void btnLogin_OnAction(ActionEvent actionEvent)  {
        if(txtUsername.getText().equals(null) || txtPassword.getText().equals(null) || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            System.out.println("empty fields");
            new Alert(Alert.AlertType.ERROR,"Please enter username and password!").show();
            return;
        }
        try{
            if(loginBO.authentication(new LoginDTO(txtUsername.getText(),txtPassword.getText()))){

                Scene subScene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml")));
                Stage primaryStage = (Stage) this.loginPane.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();
                MainFormController.stage=primaryStage;

                TranslateTransition tt = new TranslateTransition(Duration.millis(500), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }else{
                System.out.println("Something went wrong");
                new Alert(Alert.AlertType.ERROR,"Invalid username or password!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }

        txtPassword.clear();
        txtUsername.clear();
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

}
