package controller;

import business.BOFactory;
import business.BOTypes;
import business.custom.CustomerBO;
import business.custom.DeliveryBO;
import business.custom.ItemBO;
import dto.CustomerDTO;
import dto.DeliveryDTO;
import dto.ItemDTO;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jdk.internal.dynalink.linker.LinkerServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CustomerTM;
import util.DeliveryTM;
import util.ItemTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsController {

    public AnchorPane reportsPane;
    CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
    ItemBO itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);
    DeliveryBO deliveryBO = BOFactory.getInstance().getBO(BOTypes.DELIVERY);

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof Pane){
            Pane icon = (Pane) event.getSource();
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {

        if (event.getSource() instanceof Pane){
            Pane icon = (Pane) event.getSource();


            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.GRAY);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }
    @FXML
    private void navigate(MouseEvent event) throws Exception {
        if (event.getSource() instanceof Pane) {
            Pane icon = (Pane) event.getSource();


            switch (icon.getId()) {
                case "customerPane":
                    ObservableList<CustomerTM> loadCustomers = FXCollections.observableArrayList();
                    try {
                        List<CustomerDTO> customers = customerBO.findAllCustomers();
                        for (CustomerDTO customer : customers) {
                            loadCustomers.add(new CustomerTM(customer.getCustomerId(),customer.getName(),customer.getContact()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/customer.jasper"));

                    Map<String,Object> params = new HashMap<>();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,new JRBeanCollectionDataSource(loadCustomers));
                    JasperViewer.viewReport(jasperPrint,false);
                    break;

                case "inventoryPane":
                    try {
                        List<ItemDTO> items = itemBO.findAllItems();
                        ObservableList<ItemTM> loadItems = FXCollections.observableArrayList();
                        for (ItemDTO item : items) {
                            loadItems.add(new ItemTM(item.getItemId(),item.getCategoryId(),item.getBrand(),item.getDescription()
                            ,item.getQtyOnHand(),item.getBuyPrice(),item.getUnitPrice()
                            ));
                        }
                        jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/item.jasper"));
                        Map<String,Object> params2 = new HashMap<>();
                        jasperPrint = JasperFillManager.fillReport(jasperReport,params2,new JRBeanCollectionDataSource(loadItems));
                        JasperViewer.viewReport(jasperPrint,false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case "ordersPane":

                    break;
                case "deliveryPane":
                    try {
                        List<DeliveryDTO> delivery = deliveryBO.findAllDeliveries();
                        ObservableList<DeliveryTM> loaddeliveries = FXCollections.observableArrayList();
                        for (DeliveryDTO deliveryDTO : delivery) {
                            loaddeliveries.add(new DeliveryTM(deliveryDTO.getDeliveryId(),deliveryDTO.getOrderId(),deliveryDTO.getAddress(),deliveryDTO.getStates()));
                        }
                        jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/delivery.jasper"));
                        Map<String,Object> params2 = new HashMap<>();
                        jasperPrint = JasperFillManager.fillReport(jasperReport,params2,new JRBeanCollectionDataSource(loaddeliveries));
                        JasperViewer.viewReport(jasperPrint,false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
