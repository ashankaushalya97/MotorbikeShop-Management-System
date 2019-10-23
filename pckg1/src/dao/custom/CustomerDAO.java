package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String getLastCustomerId()throws Exception;

    List<Customer> searchCustomers(String text)throws Exception;
}
