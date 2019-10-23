package business.custom.impl;


import business.BOFactory;
import business.BOTypes;
import business.custom.DeliveryBO;
import business.custom.OrderBO;
import dto.DeliveryDTO;

import java.util.List;

class DeliveryBOImplTest {
    DeliveryBO deliveryBO = BOFactory.getInstance().getBO(BOTypes.DELIVERY);
    public static void main(String[] args) {
        try {
            new DeliveryBOImplTest().findAllDeliveries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void saveDelivery() {

        boolean result = false;
        try {
            result= deliveryBO.saveDelivery(new DeliveryDTO("D001","OR001","Gampaha","Not delivered"));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateDelivery() {
    }

    void deleteDelivery() {
    }

    void findAllDeliveries() throws Exception {
        List<DeliveryDTO> allDelivery = deliveryBO.findAllDeliveries();
        allDelivery.forEach(System.out::print);
    }

    void getLastDeliveryId() {
    }
}