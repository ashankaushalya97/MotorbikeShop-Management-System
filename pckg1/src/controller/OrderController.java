package controller;

import DB.DBConnection;
import business.BOFactory;
import business.BOTypes;
import business.custom.CategoryBO;
import business.custom.CustomerBO;
import business.custom.ItemBO;
import business.custom.OrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.OrderTM;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {
    public JFXButton btnSave;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtID;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXButton btnPlaceOrder;
    public JFXComboBox<String> comboCustomer;
    public JFXComboBox<String> comboItemCategory;
    public JFXComboBox<String> comboItem;
    public JFXTextField txtQty;
    public Label labTotal;
    public JFXTextField txtDate;
    public List<CustomerDTO> allCustomers;
    public List<CategoryDTO> categories;
    public List<ItemDTO> allItems;
    public TableView<OrderTM> tbleOrders;
    public JFXButton btnNew;
    OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);
    ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);
    CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
    CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
    double total=0;
    boolean itemCheck=true;

    public void initialize(){

        tbleOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tbleOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        tbleOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tbleOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tbleOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tbleOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));

        generateID();
        //load customer
        try {
            allCustomers=customerBO.findAllCustomers();
            ObservableList cmbCustomer= comboCustomer.getItems();
            for (CustomerDTO allCustomer : allCustomers) {
                cmbCustomer.add(allCustomer.getName());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
        txtID.setEditable(false);
        txtDate.setEditable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setEditable(false);
        comboItemCategory.setDisable(true);
        comboItem.setDisable(true);
        txtDescription.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQty.setDisable(true);
        btnNew.setDisable(true);

        //load category
        try {
            ObservableList<String> cmb = comboItemCategory.getItems();
            categories =  categoryBO.getCategories();
            for (CategoryDTO category : categories) {
                cmb.add(category.getDescription());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }

        //load items
        try {
            allItems=itemBO.findAllItems();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }

        comboItemCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    comboItem.getItems().clear();
                    ObservableList<String> cmbItems = comboItem.getItems();
                    String selectedItem = comboItemCategory.getSelectionModel().getSelectedItem();
                    String selectedCategory=null;
                    for (CategoryDTO category : categories) {
                        if(selectedItem.equals(category.getDescription())){
                            selectedCategory=category.getCategoryId();
                        }
                    }
                    for (ItemDTO allItem : allItems) {
                        if(selectedCategory.equals(allItem.getCategoryId())){
                            cmbItems.add(allItem.getBrand());
                        }
                    }

                }catch (Exception e){
                    return;
                }

            }
        });
        comboItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    String selectedItem = comboItem.getSelectionModel().getSelectedItem();

                    for (ItemDTO allItem : allItems) {
                        if(selectedItem.equals(allItem.getBrand())){
                            if(itemCheck){
                                if(allItem.getQtyOnHand()<=0){
                                    new Alert(Alert.AlertType.ERROR,"Out of stock!").show();
                                    return;
                                }
                            }
                            txtDescription.setText(allItem.getDescription());
                            txtQtyOnHand.setText(String.valueOf(allItem.getQtyOnHand()));
                            txtUnitPrice.setText(String.valueOf(allItem.getUnitPrice()));
                            return;
                        }
                    }
                }catch (Exception e){
                    return;
                }
            }
        });

        comboCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    String selectedItem = comboCustomer.getSelectionModel().getSelectedItem();
                    if(selectedItem.isEmpty() || selectedItem.equals(null)){
                        return;
                    }
                    comboCustomer.setDisable(true);
                    comboItemCategory.setDisable(false);
                    comboItem.setDisable(false);
                    txtDescription.setDisable(false);
                    txtQtyOnHand.setDisable(false);
                    txtUnitPrice.setDisable(false);
                    txtQty.setDisable(false);
                    btnNew.setDisable(false);
                }catch (Exception e){
                    return;
                }
            }
        });

        tbleOrders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderTM> observable, OrderTM oldValue, OrderTM newValue) {
                try {
                    OrderTM selectedItem = tbleOrders.getSelectionModel().getSelectedItem();
                    comboItemCategory.getSelectionModel().select(selectedItem.getCategory());
                    comboItemCategory.setDisable(true);
                    itemCheck=false;
                    comboItem.getSelectionModel().select(selectedItem.getBrand());
                    comboItem.setDisable(true);
                    itemCheck=true;
                    txtQty.setText(String.valueOf(selectedItem.getQty()));
                    btnSave.setText("Update");

                }catch (Exception e){
                    return;
                }
            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(!(!txtID.getText().isEmpty() && (!txtDate.getText().isEmpty()) && (!comboCustomer.getSelectionModel().isEmpty())
            && (!comboItemCategory.getSelectionModel().isEmpty()) && (!comboItem.getSelectionModel().isEmpty())
                && (!txtDescription.getText().isEmpty()) && (!txtQtyOnHand.getText().isEmpty())
                && (!txtUnitPrice.getText().isEmpty()) && txtQty.getText().matches("(^[1-9][0-9]+$)|(^[1-9])")
        )){
            new Alert(Alert.AlertType.ERROR,"Invalid input!").show();
            return;
        }
        ObservableList<OrderTM> table =tbleOrders.getItems();
        String itemId=null;
        double unitPrice=0;
        int qty= Integer.parseInt(txtQty.getText());

        if(btnSave.getText().equals("Add to cart")){
            for (ItemDTO allItem : allItems) {
                if(txtDescription.getText().equals(allItem.getDescription())){
                    if(qty>allItem.getQtyOnHand()){
                        new Alert(Alert.AlertType.ERROR,"Out of stock!").show();
                        btnNew_OnAction(actionEvent);
                        return;
                    }
                    itemId=allItem.getItemId();
                    unitPrice=allItem.getUnitPrice();
                    allItem.setQtyOnHand(allItem.getQtyOnHand()-qty);
                }
            }
            double subtotal=unitPrice*qty;
            for (int i = 0; i <table.size() ; i++) {
                if(itemId.equals(table.get(i).getItemCode())){
                    total=total-table.get(i).getTotal();
                    table.get(i).setQty(table.get(i).getQty()+qty);
                    table.get(i).setTotal(table.get(i).getQty()*unitPrice);
                    total+=table.get(i).getTotal();

                    labTotal.setText(String.valueOf(total));
                    btnNew_OnAction(actionEvent);
                    tbleOrders.refresh();

                    return;
                }
            }

            table.add(new OrderTM(itemId,comboItemCategory.getSelectionModel().getSelectedItem()
                    ,comboItem.getSelectionModel().getSelectedItem(),txtDescription.getText(),Integer.parseInt(txtQty.getText()),subtotal));

            total+=subtotal;
            labTotal.setText(String.valueOf(total));

            btnNew_OnAction(actionEvent);

        }else{
            for (ItemDTO allItem : allItems) {
                if(allItem.getItemId().equals(tbleOrders.getSelectionModel().getSelectedItem().getItemCode())){
                    System.out.println(allItem.getQtyOnHand());

                    if(qty>allItem.getQtyOnHand()){
                        new Alert(Alert.AlertType.ERROR,"Out of stock!").show();
                        btnNew_OnAction(actionEvent);
                        return;
                    }
                    itemId=allItem.getItemId();
                    unitPrice=allItem.getUnitPrice();
                    allItem.setQtyOnHand((allItem.getQtyOnHand()+tbleOrders.getSelectionModel().getSelectedItem().getQty())-qty);
                    System.out.println(allItem.getQtyOnHand());
                }
            }

            for (OrderTM orderTM : table) {
                if(orderTM.getItemCode().equals(tbleOrders.getSelectionModel().getSelectedItem().getItemCode())){
                    orderTM.setQty(qty);
                    orderTM.setTotal(qty*unitPrice);
                }
            }
            total();
            tbleOrders.refresh();
            btnNew_OnAction(actionEvent);
        }

    }
    public void total(){
        double total=0;
        for (OrderTM item : tbleOrders.getItems()) {
            total+=item.getTotal();
        }
        labTotal.setText(String.valueOf(total));
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        ObservableList<OrderTM> table = tbleOrders.getItems();
        for (ItemDTO allItem : allItems) {
            if(tbleOrders.getSelectionModel().getSelectedItem().getItemCode().equals(allItem.getItemId())){
                allItem.setQtyOnHand(tbleOrders.getSelectionModel().getSelectedItem().getQty()+allItem.getQtyOnHand());
            }
        }

        table.remove(tbleOrders.getSelectionModel().getSelectedItem());

        tbleOrders.refresh();
        btnNew_OnAction(actionEvent);
        new Alert(Alert.AlertType.INFORMATION,"Record successfully deleted!").show();
    }

    public void btnNew_OnAction(ActionEvent actionEvent) {

        comboItemCategory.getSelectionModel().clearSelection();
        comboItem.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtQty.clear();

        btnSave.setText("Add to cart");
        comboItemCategory.setDisable(false);
        comboItem.setDisable(false);
        itemCheck=true;

    }
    public void generateID(){
        int maxId = 0;
        txtDate.setText(LocalDate.now() + "");

        String lastCustomerId = null;
        try {
            lastCustomerId = orderBO.getLastOrderId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("OR", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OR00" + maxId;
            } else if (maxId < 100) {
                id = "OR0" + maxId;
            } else {
                id = "OR" + maxId;
            }
            txtID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        List<OrderDetailDTO> orderDetails = new ArrayList<>();
        for (OrderTM item : tbleOrders.getItems()) {
            for (ItemDTO allItem : allItems) {
                if(item.getItemCode().equals(allItem.getItemId())){
                    orderDetails.add(new OrderDetailDTO(item.getItemCode(),item.getQty(),allItem.getUnitPrice()));
                }
            }
        }
        orderDetails.forEach(System.out::println);

        try {
            Date date = Date.valueOf(txtDate.getText());
            String customerId = null;
            for (CustomerDTO allCustomer : allCustomers) {
                if(allCustomer.getName().equals(comboCustomer.getSelectionModel().getSelectedItem())){
                    customerId=allCustomer.getCustomerId();
                }
            }
            System.out.println(customerId);
            boolean result = orderBO.placeOrder(new OrderDTO(txtID.getText(),date,customerId,orderDetails));
            System.out.println(result);
            if(!result){
                throw new RuntimeException("Something went wrong!");
            }

//            for (OrderTM item : tbleOrders.getItems()) {
//                for (ItemDTO allItem : allItems) {
//                    if(allItem.getItemId().equals(item.getItemCode())){
//                        itemBO.updateItem(allItem);
//                    }
//                }
//            }
            new Alert(Alert.AlertType.INFORMATION,"Order succeffuly added.").show();

            JasperReport mainjasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/placeOrder.jasper"));
            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/placeOrder.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("orderId",txtID.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(mainjasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
            btnClear_OnAction(actionEvent);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
        comboCustomer.setDisable(false);
        tbleOrders.getItems().clear();
        comboCustomer.getSelectionModel().clearSelection();
        comboItemCategory.getSelectionModel().clearSelection();
        comboItem.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtQty.clear();

        comboItemCategory.setDisable(true);
        comboItem.setDisable(true);
        txtDescription.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQty.setDisable(true);

        btnSave.setText("Add to cart");
        labTotal.setText("0");

        //load items
        try {
            allItems=itemBO.findAllItems();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            Logger.getLogger("lk.ijse.dep.rcr").log(Level.SEVERE, null,e);
        }
        itemCheck=true;
    }
}
