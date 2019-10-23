package business.custom.impl;

import business.custom.DeliveryBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.DeliveryDAO;
import dto.DeliveryDTO;
import entity.Delivery;
import entity.DeliveryPK;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {
    DeliveryDAO deliveryDAO = DAOFactory.getInstance().getDAO(DAOTypes.DELIVERY);
    @Override
    public boolean saveDelivery(DeliveryDTO delivery) throws Exception {
        return deliveryDAO.save(new Delivery(delivery.getDeliveryId(),delivery.getOrderId(),delivery.getAddress(),delivery.getStates()));
    }

    @Override
    public boolean updateDelivery(DeliveryDTO delivery) throws Exception {
        return deliveryDAO.update(new Delivery(delivery.getDeliveryId(),delivery.getOrderId(),delivery.getAddress(),delivery.getStates()));
    }

    @Override
    public boolean deleteDelivery(String deliveryId, String orderId) throws Exception {
        return deliveryDAO.delete(new DeliveryPK(deliveryId,orderId));
    }

    @Override
    public List<DeliveryDTO> findAllDeliveries() throws Exception {
        List<Delivery> all = deliveryDAO.findAll();
        List<DeliveryDTO> deliveryDTOS = new ArrayList<>();
        for (Delivery delivery : all) {
            deliveryDTOS.add(new DeliveryDTO(delivery.getDeliveryPK().getDeliveryId(),delivery.getDeliveryPK().getOrderId(),delivery.getAddress(),delivery.getStates()));
        }
        return deliveryDTOS;
    }

    @Override
    public String getLastDeliveryId() throws Exception {
        return deliveryDAO.getLastDeliveryId();
    }

    @Override
    public List<String> getOrderIds() throws Exception {
        List<Delivery> all = deliveryDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Delivery delivery : all) {
            ids.add(delivery.getDeliveryPK().getOrderId());
        }
        return ids;
    }

    @Override
    public List<DeliveryDTO> searchDelivery(String text) throws Exception {
        List<Delivery> search = deliveryDAO.searchDelivery(text);
        List<DeliveryDTO> deliveries = new ArrayList<>();
        for (Delivery delivery : search) {
            deliveries.add(new DeliveryDTO(delivery.getDeliveryPK().getDeliveryId(),delivery.getDeliveryPK().getOrderId(),delivery.getAddress(),delivery.getStates()));
        }

        return deliveries;
    }
}
