package controller;

import DB.DBConnection;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class HomeController {

    public AnchorPane root;

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the DB Backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showSaveDialog(this.root.getScene().getWindow());
        if (file != null) {

            // Now, we have to backup the DB...
            // Long running task == We have to backup
            this.root.getScene().setCursor(Cursor.WAIT);

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    String[] commands;
                    if (DBConnection.password.length() > 0){
                        commands = new String[]{"mysqldump", "-h", DBConnection.host, "-u", DBConnection.username,
                                "-p" + DBConnection.password,"--port",DBConnection.port, DBConnection.db, "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }else{
                        commands = new String[]{"mysqldump", "-h", DBConnection.host, "-u", DBConnection.username, "--port",DBConnection.port,
                                DBConnection.db, "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }

                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Wadea Kachal");
                    } else {
                        return null;
                    }
                }
            };

            task.setOnSucceeded(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION,"Backup process has been done successfully").show();
            });

            task.setOnFailed(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR,"Failed to back up. Contact DEEPO").show();
            });

            new Thread(task).start();
        }
    }

    public void btnRestore_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Let's restore the backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showOpenDialog(this.root.getScene().getWindow());
        if (file != null) {

            String[] commands;
            if (DBConnection.password.length() > 0){
                commands = new String[]{"mysql", "-h", DBConnection.host, "-u", DBConnection.username,
                        "-p" + DBConnection.password,"--port",DBConnection.port, DBConnection.db, "-e", "source " + file.getAbsolutePath()};
            }else{
                commands = new String[]{"mysql", "-h", DBConnection.host, "-u", DBConnection.username,"--port",DBConnection.port,
                        DBConnection.db, "-e", "source " + file.getAbsolutePath()};
            }

            // Long running task == Restore
            this.root.getScene().setCursor(Cursor.WAIT);

            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Wadea Kachal");
                    } else {
                        return null;
                    }
                }
            };

            task.setOnSucceeded(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION, "Restore process has been successfully done").show();
            });
            task.setOnFailed(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR, "Failed to restore the backup. Contact DEPPO").show();
            } );

            new Thread(task).start();
        }
    }
}
