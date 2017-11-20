/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Bid;
import Entity.Customer;
import exception.CustomerNotFoundException;
import exception.InvalidLoginCredentialException;
import java.util.List;

public interface CustomerEntityControllerLocal {

    public Customer persistNewCustomer(Customer c);

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public void updateCustomer(Customer customer);

    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException;

    public List<Bid> retrieveBids(Long id);

}
