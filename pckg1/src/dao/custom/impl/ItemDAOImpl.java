package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<Item> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item");
        List<Item> items = new ArrayList<>();
        while (rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getInt(5),rst.getDouble(6),rst.getDouble(7)));
        }
        return items;
    }

    @Override
    public Item find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE itemId=?",s);
        if(rst.next()){
            return  new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getInt(5),rst.getDouble(6),rst.getDouble(7));
        }
        return null;
    }

    @Override
    public boolean save(Item entity) throws Exception {
        return CrudUtil.execute("INSERT INTO item VALUES (?,?,?,?,?,?,?)",entity.getItemId(),entity.getCategoryId(),entity.getBrand(),entity.getDescription(),entity.getQtyOnHand(),entity.getBuyPrice(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Item entity) throws Exception {
        return CrudUtil.execute("UPDATE item SET categoryId=?,brand=?,description=?,qtyOnHand=?,buyPrice=?,unitPrice=? WHERE  itemId=?",entity.getCategoryId(),entity.getBrand(),entity.getDescription(),entity.getQtyOnHand(),entity.getBuyPrice(),entity.getUnitPrice(),entity.getItemId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM item WHERE itemId=?",s);
    }

    @Override
    public String getLastItemId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Item> searchItems(String text) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item\n" +
                "WHERE itemId LIKE ? OR categoryId LIKE ? OR\n" +
                "brand LIKE ? OR description LIKE ? OR qtyOnHand LIKE ? OR buyPrice LIKE ? OR unitPrice LIKE ?",text,text,text,text,text,text,text);
        List<Item> items = new ArrayList<>();
        while(rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getString(3),
                    rst.getString(4),rst.getInt(5),rst.getDouble(6),rst.getDouble(7)
            ));
        }
        return items;
    }
}
