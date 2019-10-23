package dao.custom.impl;


import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CustomerDAO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

class CustomerDAOImplTest {

    CustomerDAO customerDAO= DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    public static void main(String[] args) throws Exception {
        new CustomerDAOImplTest().searchCustomers();
    }

    void searchCustomers() throws Exception {
        List<Customer> customers =  customerDAO.searchCustomers("%C%");
        customers.forEach(System.out::print);
    }
}