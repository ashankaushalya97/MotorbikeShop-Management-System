package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.DeliveryBO;
import com.jfoenix.controls.JFXTextField;
import dto.DeliveryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.DeliveryTM;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliverySearchController {
    public TableView<DeliveryTM> tblDelivery;
    public JFXTextField txtSearch;
    DeliveryBO deliveryBO = BOFactory.getInstance().getBO(BOTypes.DELIVERY);
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

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    tblDelivery.getItems().clear();
                    if(txtSearch.getText().isEmpty()){
                        initialize();
                        return;
                    }
                    String searchText="%"+txtSearch.getText()+"%";
                    List<DeliveryDTO> search = deliveryBO.searchDelivery(searchText);
                    ObservableList<DeliveryTM> table = tblDelivery.getItems();
                    for (DeliveryDTO deliveryDTO : search) {
                        table.add(new DeliveryTM(deliveryDTO.getDeliveryId(),deliveryDTO.getOrderId(),deliveryDTO.getAddress(),deliveryDTO.getStates()));
                    }
                    tblDelivery.refresh();
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                    Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);

                }
            }
        });
    }
}
