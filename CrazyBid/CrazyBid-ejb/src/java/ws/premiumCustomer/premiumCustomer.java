/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.premiumCustomer;

import Entity.AuctionListing;
import Entity.Bid;
import Entity.Customer;
import ejb.session.stateless.AuctionListingEntityControllerLocal;
import ejb.session.stateless.CustomerEntityControllerLocal;
import exception.AuctionListingNotFoundException;
import exception.BalanceNotEnoughException;
import exception.CustomerNotFoundException;
import exception.InvalidLoginCredentialException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@WebService(serviceName = "premiumCustomer")
@Stateless()
public class premiumCustomer {

    @EJB
    private AuctionListingEntityControllerLocal auctionListingEntityController;

    @EJB
    private CustomerEntityControllerLocal customerEntityController;

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    /**
     * This is a sample web service operation
     *
     * @param username
     * @param password
     */
    @WebMethod(operationName = "customerLogin")
    public Customer customerLogin(@WebParam(name = "username") String username,
            @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        try {
            Customer c = customerEntityController.customerLogin(username, password);
            em.detach(c);
            if (c.getPremium()) {
                c.setAddressList(null);
                c.setBidList(null);
                
                c.setTopUpList(null);
                
                List<AuctionListing> aList =c.getProductList();
                for(AuctionListing a: aList){
     
                    a.setOwner(null);
                }

                return c;
            } else {
                throw new InvalidLoginCredentialException("You are not premium customer");
            }
        } catch (InvalidLoginCredentialException ex) {
            throw new InvalidLoginCredentialException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "retrieveCustomerByUsername")
    public Customer retrieveCustomerByUsername(@WebParam(name = "username") String username) throws CustomerNotFoundException {

        try {
            Customer c = customerEntityController.retrieveCustomerByUsername(username);
            em.detach(c);
            c.setAddressList(null);
            c.setBidList(null);
            c.setTopUpList(null);
            for (AuctionListing a : c.getProductList()) {
                a.setOwner(null);
                a.getAddress().setCustomer(null);
            }
            return c;
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "retrieveAllAuctionListings")
    public List<AuctionListing> retrieveAllAuctionListings() {
        List<AuctionListing> aList = auctionListingEntityController.retrieveAllAuctionListings();
        em.detach(aList);
        for (AuctionListing a : aList) {
            a.setAddress(null);
            a.setOwner(null);
            for (Bid b : a.getBidList()) {
                b.setAuctionListing(null);
                b.setCustomer(null);
            }

        }
        return aList;
    }

    @WebMethod(operationName = "retrieveAuctionListingById")
    public AuctionListing retrieveAuctionListingById(@WebParam(name = "aId") Long aId) throws AuctionListingNotFoundException {
        try {
            AuctionListing a = auctionListingEntityController.retrieveAuctionListingById(aId);
            em.detach(a);
            a.setAddress(null);
            a.setOwner(null);
            for (Bid b : a.getBidList()) {
                b.setAuctionListing(null);
            }

            return a;
        } catch (AuctionListingNotFoundException ex) {
            throw new AuctionListingNotFoundException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "newConfigureSniping")
    public void newConfigureSniping(Long cId, Long aId, BigDecimal expectingPrice) {
        try {
            auctionListingEntityController.newConfigureSniping(cId, aId, expectingPrice);
        } catch (CustomerNotFoundException | AuctionListingNotFoundException | BalanceNotEnoughException ex) {
            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
        }
    }

}
