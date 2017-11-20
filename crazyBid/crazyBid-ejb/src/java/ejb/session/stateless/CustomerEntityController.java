/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import Entity.AuctionListing;
import Entity.Bid;
import Entity.Customer;
import exception.CustomerNotFoundException;
import exception.InvalidLoginCredentialException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
@Local(CustomerEntityControllerLocal.class)
@Remote(CustomerEntityControllerRemote.class)

public class CustomerEntityController implements CustomerEntityControllerRemote, CustomerEntityControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    @Override
    public Customer persistNewCustomer(Customer c) { // persist new cusstomer

        em.persist(c);
        em.flush();
        em.refresh(c);

        return c;
    }

    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException { //retrive by username

        Query query = em.createQuery("SELECT s FROM Customer s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            Customer c = (Customer) query.getSingleResult();
            List<AuctionListing> aList = c.getProductList();
            for (AuctionListing a : aList) {
                a.getAddress();
                a.getBidList();
                
            }
            for(Address a: c.getAddressList()){
                a.getCountry();
            }
            return c;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }

    @Override
    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException {//retrieve by email

        Query query = em.createQuery("SELECT s FROM Customer s WHERE s.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            Customer c = (Customer) query.getSingleResult();
            List<AuctionListing> aList = c.getProductList();
            for (AuctionListing a : aList) {
                a.getAddress();
                a.getBidList();
            }
            return c;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + email + " does not exist!");
        }
    }

    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException { //to log in
        try {
            Customer c = retrieveCustomerByUsername(username);
            if (c.getPassword().equals(password)) {
                return c;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateCustomer(Customer customer) {//to update customer
        em.merge(customer);
    }

    @Override
    public List<Bid> retrieveBids(Long id) { // to retrive Bids completed by the customer
        Customer c = em.find(Customer.class, id);

        List<Bid> bList = c.getBidList();
        for (Bid b : bList) {
            b.getAuctionListing().getProduct();

        }
        return bList;

    }

}
