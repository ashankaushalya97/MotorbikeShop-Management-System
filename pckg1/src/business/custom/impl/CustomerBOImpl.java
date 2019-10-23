package business.custom.impl;

import business.custom.CustomerBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customer) throws Exception {
        return customerDAO.save(new Customer(customer.getCustomerId(),customer.getName(),customer.getContact()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws Exception {
        return customerDAO.update(new Customer(customer.getCustomerId(),customer.getName(),customer.getContact()));
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        List<Customer> all = customerDAO.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : all) {
            customerDTOS.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getContact()));
        }
        return customerDTOS;
    }

    @Override
    public String getLastCustomerId() throws Exception {
        return customerDAO.getLastCustomerId();
    }

    @Override
    public List<CustomerDTO> searchCustomer(String text) throws Exception {
        List<Customer> search = customerDAO.searchCustomers(text);
        List<CustomerDTO> customers = new ArrayList<>();
        for (Customer customer : search) {
            customers.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getContact()));
        }
        return customers;
    }
}
