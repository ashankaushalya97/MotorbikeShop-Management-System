package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.DeliveryBO;
import business.custom.OrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.DeliveryDTO;
import dto.OrderDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.DeliveryTM;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryController {


    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public TableView<DeliveryTM> tblDelivery;
    public JFXTextField txtID;
    public JFXComboBox<String> comboOrderID;
    public JFXComboBox<String> comboStates;
    OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);
    DeliveryBO deliveryBO = BOFactory.getInstance().getBO(BOTypes.DELIVERY);
    public boolean checkOrderID=true;

    public void initialize(){
        tblDelivery.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        tblDelivery.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblDelivery.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblDelivery.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("states"));

        //load table
        try {
            ObservableList<DeliveryTM> table =tblDelivery.getItems();
            List<DeliveryDTO> allDeliveries = deliveryBO.findAllDeliveries();
            for (DeliveryDTO allDelivery : allDeliveries) {
                table.add(new DeliveryTM(allDelivery.getDeliveryId(),allDelivery.getOrderId(),allDelivery.getAddress(),allDelivery.getStates()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }

        //load orderID
        ObservableList<String> comboOrder  = comboOrderID.getItems();
        try {
            List<String> OrderID = orderBO.getAllOrderIDs();
            for (String s : OrderID) {
                comboOrder.add(s);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);

        }

        //setup states
        ObservableList<String> comboDelivery = comboStates.getItems();
        comboDelivery.add("Not Delivered");
        comboDelivery.add("Delivered");

        txtID.setDisable(true);
        comboOrderID.setDisable(true);
        txtAddress.setDisable(true);
        comboStates.setDisable(true);
        txtID.setEditable(false);

        comboOrderID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<String> ids = new ArrayList<>();
                try {
                    ids = deliveryBO.getOrderIds();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                    Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
                }
                try {
                    String selectedItem = comboOrderID.getSelectionModel().getSelectedItem();
                    if(checkOrderID==true){
                        for (String id : ids) {
                            if(id.equals(selectedItem)){
                                new Alert(Alert.AlertType.ERROR,"This orderId already have a delivery!").show();
                                comboOrderID.getSelectionModel().clearSelection();
                                return;
                            }
                        }
                    }
                }catch (Exception e){
                    return;
                }
            }
        });

        tblDelivery.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeliveryTM>() {
            @Override
            public void changed(ObservableValue<? extends DeliveryTM> observable, DeliveryTM oldValue, DeliveryTM newValue) {
                try{
                    comboOrderID.setDisable(true);
                    txtID.setDisable(false);
                    txtAddress.setDisable(false);
                    comboStates.setDisable(false);

                    DeliveryTM selectedItem = tblDelivery.getSelectionModel().getSelectedItem();
                    checkOrderID=false;
                    comboOrderID.getSelectionModel().select(selectedItem.getOrderId());
                    checkOrderID=true;
                    txtID.setText(selectedItem.getDeliveryId());
                    txtAddress.setText(selectedItem.getAddress());
                    comboStates.getSelectionModel().select(selectedItem.getStates());

                    btnSave.setText("Update");
                }catch (Exception e){
                    return;
                }
            }
        });


    }

    public void btnNew_OnAction(ActionEvent actionEvent) {

        btnSave.setText("Save");
        txtID.setDisable(false);
        comboOrderID.setDisable(false);
        txtAddress.setDisable(false);
        comboStates.setDisable(false);

        comboOrderID.getSelectionModel().clearSelection();
        txtAddress.clear();
        comboStates.getSelectionModel().select(0);

        int maxId = 0;
        String lastCustomerId = null;
        try {
            lastCustomerId = deliveryBO.getLastDeliveryId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("D", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "D00" + maxId;
            } else if (maxId < 100) {
                id = "D0" + maxId;
            } else {
                id = "D" + maxId;
            }
            txtID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(!((!comboOrderID.getSelectionModel().isEmpty()) && (!txtID.getText().isEmpty())
            && (!txtAddress.getText().isEmpty()) && (!comboStates.getSelectionModel().isEmpty())
        )){
            new Alert(Alert.AlertType.ERROR,"Invalid inputs!").show();
            return;
        }
        ObservableList<DeliveryTM> table =tblDelivery.getItems();
        boolean result = false;
        if(btnSave.getText().equals("Save")){
            try {
                result=deliveryBO.saveDelivery(new DeliveryDTO(txtID.getText(),comboOrderID.getSelectionModel().getSelectedItem(),
                        txtAddress.getText(),comboStates.getSelectionModel().getSelectedItem()
                ));
                if(!result){
                    throw new RuntimeException("Something went wrong!");
                }
                table.add(new DeliveryTM(txtID.getText(),comboOrderID.getSelectionModel().getSelectedItem(),txtAddress.getText()
                ,comboStates.getSelectionModel().getSelectedItem()));
                tblDelivery.refresh();
                btnNew_OnAction(actionEvent);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
            }

        }else{
            try {
                result =  deliveryBO.updateDelivery(new DeliveryDTO(txtID.getText(),comboOrderID.getSelectionModel().getSelectedItem(),txtAddress.getText(),comboStates.getSelectionModel().getSelectedItem()));
                if(!result){
                    throw new RuntimeException("Something went wrong!");
                }
                tblDelivery.getItems().clear();
                comboOrderID.getItems().clear();
                comboStates.getItems().clear();
                initialize();
                new Alert(Alert.AlertType.INFORMATION,"Record successfully updated!").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
            }
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        DeliveryTM selectedItem = tblDelivery.getSelectionModel().getSelectedItem();
        try {
            boolean result= deliveryBO.deleteDelivery(selectedItem.getDeliveryId(),selectedItem.getOrderId());
            if(!result){
                throw new RuntimeException("Something went wrong!");
            }
            tblDelivery.getItems().clear();
            comboOrderID.getItems().clear();
            comboStates.getItems().clear();
            initialize();
            new Alert(Alert.AlertType.INFORMATION,"Record successfully deleted!").show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
