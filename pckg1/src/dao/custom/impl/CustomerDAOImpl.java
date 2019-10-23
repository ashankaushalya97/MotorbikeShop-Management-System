package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer");
        List<Customer> customers= new ArrayList<>();
        while(rst.next()){
            customers.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return customers;
    }

    @Override
    public Customer find(String s) throws Exception {
        ResultSet rst  = CrudUtil.execute("SELECT * from customer where customerId=?", s);

        if(rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3));
        }
        return null;
    }

    @Override
    public boolean save(Customer entity) throws Exception {
        return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?)",entity.getCustomerId(),entity.getName(),entity.getContact());
    }

    @Override
    public boolean update(Customer entity) throws Exception {
        return CrudUtil.execute("UPDATE customer SET name=?,contact=? WHERE customerId=?",entity.getName(),entity.getContact(),entity.getCustomerId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM customer WHERE customerId=?",s);
    }

    @Override
    public String getLastCustomerId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1");
        String id=null;
        if(rst.next()){
            id=rst.getString(1);
        }
        return id;
    }

    @Override
    public List<Customer> searchCustomers(String text) throws Exception {
        List<Customer> search= new ArrayList<>();
        ResultSet rst=CrudUtil.execute("SELECT * FROM customer WHERE customerId like ? or name like ? or contact like ?",text,text,text);
        while (rst.next()){
            search.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return search;
    }
}
