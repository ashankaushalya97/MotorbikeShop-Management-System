package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.ItemBO;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ItemTM;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventorySearchController {
    public JFXTextField txtSearch;
    public TableView<ItemTM> tbleInventory;
    ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);

    public void initialize(){

        //load table
        tbleInventory.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tbleInventory.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        tbleInventory.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tbleInventory.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tbleInventory.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tbleInventory.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        tbleInventory.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        ObservableList<ItemTM> table = tbleInventory.getItems();
        try {
            List<ItemDTO> all = itemBO.findAllItems();
            for (ItemDTO itemDTO : all) {
                table.add(new ItemTM(itemDTO.getItemId(),itemDTO.getCategoryId(),itemDTO.getBrand(),
                        itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getBuyPrice(),itemDTO.getUnitPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.pos").log(Level.SEVERE, null,e);
        }

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try{
                    tbleInventory.getItems().clear();
                    if(txtSearch.getText().isEmpty()){
                        initialize();
                        return;
                    }
                    String text = txtSearch.getText();
                    String searchText="%"+txtSearch.getText()+"%";
                    List<ItemDTO> search = itemBO.searchItems(searchText);
                    ObservableList<ItemTM> table = tbleInventory.getItems();
                    for (ItemDTO itemDTO : search) {
                        table.add(new ItemTM(itemDTO.getItemId(),itemDTO.getCategoryId(),itemDTO.getBrand(),
                                itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getBuyPrice(),itemDTO.getUnitPrice()));
                    }
                    tbleInventory.refresh();
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                    Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);

                }
            }
        });

    }
}
