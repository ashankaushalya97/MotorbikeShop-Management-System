package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.OrderBO;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.ls.LSInput;
import util.SearchOrderTM;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderSearchController {
    public TableView<SearchOrderTM> tblOrder;
    public JFXTextField txtSearch;
    OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);

    public void initialize(){

        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            List<OrderDTO2> orders = orderBO.getOrderInfo();
            ObservableList<SearchOrderTM> table = tblOrder.getItems();
            for (OrderDTO2 order : orders) {
                table.add(new SearchOrderTM(order.getOrderId(),order.getDate(),order.getCustomerId(),order.getName(),order.getTotal()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               try {
                tblOrder.getItems().clear();
                if(txtSearch.getText().isEmpty()){
                    initialize();
                    return;
                }
                String text = txtSearch.getText();
                String searchText="%"+txtSearch.getText()+"%";
                List<OrderDTO2> orders = orderBO.searchOrder(searchText);
                ObservableList<SearchOrderTM> table = tblOrder.getItems();
                   for (OrderDTO2 order : orders) {
                       table.add(new SearchOrderTM(order.getOrderId(),order.getDate(),order.getCustomerId(),order.getName(),order.getTotal()));
                   }

                tblOrder.refresh();
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);

            }
            }
        });

    }
}
