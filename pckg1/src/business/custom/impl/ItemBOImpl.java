package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO item) throws Exception {
        return itemDAO.save(new Item(item.getItemId(),item.getCategoryId(),item.getBrand(),item.getDescription(),item.getQtyOnHand(),item.getBuyPrice(),item.getUnitPrice()));
    }

    @Override
    public boolean updateItem(ItemDTO item) throws Exception {
        return itemDAO.update(new Item(item.getItemId(),item.getCategoryId(),item.getBrand(),item.getDescription(),item.getQtyOnHand(),item.getBuyPrice(),item.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(String id) throws Exception {
        return itemDAO.delete(id);
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        List<Item> all = itemDAO.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : all) {
            itemDTOS.add(new ItemDTO(item.getItemId(),item.getCategoryId(),item.getBrand(),item.getDescription(),item.getQtyOnHand(),item.getBuyPrice(),item.getUnitPrice()));
        }
        return itemDTOS;
    }

    @Override
    public String getLastItemId() throws Exception {
        return itemDAO.getLastItemId();
    }

    @Override
    public List<ItemDTO> searchItems(String text) throws Exception {
        List<Item> search = itemDAO.searchItems(text);
        List<ItemDTO> items = new ArrayList<>();
        for (Item item : search) {
            items.add(new ItemDTO(item.getItemId(),item.getCategoryId(),item.getBrand(),item.getDescription(),item.getQtyOnHand(),item.getBuyPrice(),item.getUnitPrice()));
        }
        return items;
    }
}
