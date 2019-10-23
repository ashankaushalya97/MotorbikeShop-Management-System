package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.CustomerTM;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController {

    public JFXTextField txtID;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public TableView<CustomerTM> tbleCustomer;
    public JFXButton btnSave;
    CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);

    public void initialize(){
        tbleCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tbleCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tbleCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));

        ObservableList<CustomerTM> customers = tbleCustomer.getItems();
        customers.clear();
        try {
            List<CustomerDTO> all = customerBO.findAllCustomers();
            for (CustomerDTO customerDTO : all) {
                customers.add(new CustomerTM(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getContact()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }

        btnSave.setText("Save");
        txtID.setDisable(true);
        txtName.setDisable(true);
        txtContact.setDisable(true);

        tbleCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                try {
                    CustomerTM selectedItem = tbleCustomer.getSelectionModel().getSelectedItem();
                    txtID.setText(selectedItem.getCustomerId());
                    txtName.setText(selectedItem.getName());
                    txtContact.setText(selectedItem.getContact());
                    btnSave.setText("Update");
                }catch (Exception e){
                    return;
                }

            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        ObservableList<CustomerTM> table = tbleCustomer.getItems();
        if(!(txtName.getText().matches("[A-Za-z][A-Za-z. ]+")&& txtContact.getText().matches("^[0-9]{3}-[0-9]{7}$") && (!(txtID.getText().isEmpty())))){
            new Alert(Alert.AlertType.ERROR,"Invalid input!").show();
            return;
        }
        boolean result=false;
        if(btnSave.getText().equals("Save")){
            try {
                result= customerBO.saveCustomer(new CustomerDTO(txtID.getText(),txtName.getText(),txtContact.getText()));
                if(!result){
                    throw new RuntimeException("Something went wrong");
                }
                table.add(new CustomerTM(txtID.getText(),txtName.getText(),txtContact.getText()));
                btnNew_OnAction(actionEvent);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
            }
        }else{
            try {
                result= customerBO.updateCustomer(new CustomerDTO(txtID.getText(),txtName.getText(),txtContact.getText()));
                if(!result){
                    throw new RuntimeException("Something went wrong!");
                }
                initialize();
                btnNew_OnAction(actionEvent);
                new Alert(Alert.AlertType.INFORMATION,"User successfully updated!").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
            }

        }


    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        try {
           boolean result = customerBO.deleteCustomer(txtID.getText());
           if(!result){
               throw new RuntimeException("Something went wrong!");
           }
            new Alert(Alert.AlertType.INFORMATION,"User successfully deleted!").show();
           ObservableList<CustomerTM> table = tbleCustomer.getItems();
            for (CustomerTM customerTM : table) {
                if(txtID.getText().equals(customerTM.getCustomerId())){
                    table.remove(customerTM);
                    btnNew_OnAction(actionEvent);
                    return;
                }
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnNew_OnAction(ActionEvent actionEvent) {

        txtID.setDisable(false);
        txtName.setDisable(false);
        txtContact.setDisable(false);

        txtName.clear();
        txtContact.clear();
        btnSave.setText("Save");
        tbleCustomer.getSelectionModel().clearSelection();
        int maxId = 0;

        String lastCustomerId = null;
        try {
            lastCustomerId = customerBO.getLastCustomerId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            txtID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }
}
