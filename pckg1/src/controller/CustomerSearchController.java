package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.CustomerBO;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.CustomerTM;

import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerSearchController {

    public TableView<CustomerTM> tbleCustomer;
    public JFXTextField txtSearch;
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

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    tbleCustomer.getItems().clear();
                    if(txtSearch.getText().isEmpty()){
                        initialize();
                        return;
                    }
                    String text = txtSearch.getText();
                    String searchText="%"+txtSearch.getText()+"%";
                    List<CustomerDTO> search =  customerBO.searchCustomer(searchText);
                    ObservableList<CustomerTM> table = tbleCustomer.getItems();
                    for (CustomerDTO customerDTO : search) {
                        table.add(new CustomerTM(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getContact()));
                    }
                    tbleCustomer.refresh();
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                    Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);

                }
            }
        });

    }
}
