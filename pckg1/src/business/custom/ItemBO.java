package business.custom;

import business.SuperBO;
import dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO{

    boolean saveItem(ItemDTO item)throws Exception;

    boolean updateItem(ItemDTO item)throws Exception;

    boolean deleteItem(String id)throws Exception;

    List<ItemDTO> findAllItems()throws Exception;

    String getLastItemId()throws Exception;

    List<ItemDTO> searchItems(String text)throws Exception;

}
