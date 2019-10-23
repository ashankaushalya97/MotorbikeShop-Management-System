package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.CategoryBO;
import business.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CategoryDTO;
import dto.ItemDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ItemTM;

import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryController {

    public JFXTextField txtBrand;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<ItemTM> tbleInventory;
    public JFXTextField txtID;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXComboBox comboCategory;
    public JFXTextField txtBuyPrice;
    List<CategoryDTO> categories;
    ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);
    CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
    private static DecimalFormat df = new DecimalFormat("#.##");

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

        //load catogories
        try {
            ObservableList<String> cmb = comboCategory.getItems();
            categories =  categoryBO.getCategories();
            for (CategoryDTO category : categories) {
                cmb.add(category.getDescription());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
        txtID.setEditable(false);
        txtID.setDisable(true);
        txtBrand.setDisable(true);
        txtDescription.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtBuyPrice.setDisable(true);
        txtUnitPrice.setDisable(true);
        comboCategory.setDisable(true);

        tbleInventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                try {
                    ItemTM selectedItem = tbleInventory.getSelectionModel().getSelectedItem();

                    txtID.setDisable(false);
                    txtID.setText(selectedItem.getItemId());
                    txtBrand.setDisable(false);;
                    txtBrand.setText(selectedItem.getBrand());
                    txtDescription.setDisable(false);
                    txtDescription.setText(selectedItem.getDescription());
                    txtQtyOnHand.setDisable(false);
                    txtQtyOnHand.setText(String.valueOf(selectedItem.getQtyOnHand()));
                    txtBuyPrice.setDisable(false);
//                    System.out.println(String.format("%.2f",String.valueOf(selectedItem.getBuyPrice())));
                    txtBuyPrice.setText(String.valueOf(df.format(selectedItem.getBuyPrice())));
                    txtUnitPrice.setDisable(false);
                    txtUnitPrice.setText(String.valueOf(df.format(selectedItem.getUnitPrice())));
                    comboCategory.setDisable(false);
                    ObservableList<String> cmb = comboCategory.getItems();
                    System.out.println(selectedItem.getCategoryId());
                    String catId = null;
                    for (CategoryDTO category : categories) {
                        if(selectedItem.getCategoryId().equals(category.getCategoryId())){
                            comboCategory.getSelectionModel().select(category.getDescription());
                        }
                    }
                    btnSave.setText("Update");

                }catch (Exception e){
                    return;
                }

            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(!(txtQtyOnHand.getText().matches("^\\d+$") && txtBuyPrice.getText().matches("(^[0-9]+\\.[0-9]{2}$)|(^[0-9]+$)")
                && txtUnitPrice.getText().matches("(^[0-9]+\\.[0-9]{2}$)|(^[0-9]+$)")
                && (!txtID.getText().isEmpty()) && (txtDescription.getText().matches("^[\\w \\.&]+$"))
                && (txtBrand.getText().matches("^[\\w \\.&]+$"))
                && (!comboCategory.getSelectionModel().isEmpty()))
        ){
            new Alert(Alert.AlertType.ERROR,"Invalid input!").show();
            return;
        }
        boolean result=false;
        ObservableList<ItemTM> table = tbleInventory.getItems();
        String categoryId = null;

        if(btnSave.getText().equals("Save")){

            try {
                for (CategoryDTO category : categories) {
                    if(comboCategory.getSelectionModel().getSelectedItem().equals(category.getDescription())){
                        categoryId=category.getCategoryId();
                    }
                }

                result = itemBO.saveItem(new ItemDTO(txtID.getText(),categoryId,txtBrand.getText()
                        ,txtDescription.getText(),Integer.parseInt(txtQtyOnHand.getText()),Double.parseDouble(txtBuyPrice.getText())
                        ,Double.parseDouble(txtUnitPrice.getText())));
                if(!result){
                    throw new RuntimeException("Something went wrong!");
                }
                btnNew_OnAction(actionEvent);
                tbleInventory.getItems().clear();
                initialize();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
            }

        }else{
            try {
                for (CategoryDTO category : categories) {
                    if(comboCategory.getSelectionModel().getSelectedItem().equals(category.getDescription())){
                        categoryId=category.getCategoryId();
                    }
                }
                result= itemBO.updateItem(new ItemDTO(txtID.getText(),categoryId,txtBrand.getText()
                        ,txtDescription.getText(),Integer.parseInt(txtQtyOnHand.getText()),Double.parseDouble(txtBuyPrice.getText())
                        ,Double.parseDouble(txtUnitPrice.getText())));
                if(!result){
                    throw new RuntimeException("Something went wrong!");
                }
                tbleInventory.getItems().clear();
                comboCategory.getItems().clear();
                initialize();
                btnNew_OnAction(actionEvent);
                new Alert(Alert.AlertType.INFORMATION,"Item successfully updated!").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
            }
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        try {
            boolean result= itemBO.deleteItem(txtID.getText());
            if(!result){
                throw new RuntimeException("Something went wrong!");
            }
            tbleInventory.getItems().clear();
            comboCategory.getItems().clear();
            initialize();
            btnNew_OnAction(actionEvent);
            new Alert(Alert.AlertType.INFORMATION,"Item successfully deleted!").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnNew_OnAction(ActionEvent actionEvent) {

        txtID.setDisable(false);
        txtBrand.setDisable(false);
        txtDescription.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtBuyPrice.setDisable(false);
        txtUnitPrice.setDisable(false);
        comboCategory.setDisable(false);

        txtID.clear();
        txtBrand.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtBuyPrice.clear();
        txtUnitPrice.clear();
        comboCategory.getSelectionModel().clearSelection();
        tbleInventory.getSelectionModel().clearSelection();

        btnSave.setText("Save");
        int maxId = 0;

        String lastCustomerId = null;
        try {
            lastCustomerId = itemBO.getLastItemId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("I", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            txtID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }
}
