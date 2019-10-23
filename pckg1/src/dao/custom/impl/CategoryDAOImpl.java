package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CategoryDAO;
import entity.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public List<Category> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category");
        List<Category> categories = new ArrayList<>();
        while (rst.next()){
            categories.add(new Category(rst.getString(1),rst.getString(2)));
        }
        return categories;
    }

    @Override
    public Category find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECt * FROM category WHERE categoryId=?",s);
        if(rst.next()){
            return new Category(rst.getString(1),rst.getString(2));
        }
        return null;
    }

    @Override
    public boolean save(Category entity) throws Exception {
        return CrudUtil.execute("INSERT INTO category VALUES (?,?)",entity.getCategoryId(),entity.getDescription());
    }

    @Override
    public boolean update(Category entity) throws Exception {
        return CrudUtil.execute("UPDATE category SET description=? WHERE categoryId=?",entity.getDescription(),entity.getCategoryId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM category WHERE categoryId=?",s);
    }
}
