import DB.DBConnection;
import controller.LoginController;
import controller.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppInitializer extends Application {

    public static void main(String[] args) {

        launch(args);

        try {
            System.out.println("Shutting down the connection.");
            DBConnection.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage){

        try{
            // Let's setup the root logger
            Logger rootLogger = Logger.getLogger("");
            FileHandler fileHandler = new FileHandler("error.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            rootLogger.addHandler(fileHandler);

            DBConnection.getInstance().getConnection();

            URL resource = this.getClass().getResource("/view/Login.fxml");
            Parent root = FXMLLoader.load(resource);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
            primaryStage.show();
            //  MainFormController.stage=primaryStage;
            LoginController.stage=primaryStage;

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }

}
