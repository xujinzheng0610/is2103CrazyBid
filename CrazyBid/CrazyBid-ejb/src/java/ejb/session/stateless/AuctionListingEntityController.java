/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.AuctionListing;
import Entity.Bid;
import Entity.Customer;
import exception.AuctionListingNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
@Local(AuctionListingEntityControllerLocal.class)
@Remote(AuctionListingEntityControllerRemote.class)
public class AuctionListingEntityController implements AuctionListingEntityControllerRemote, AuctionListingEntityControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public AuctionListing persistNewAuctionListing(AuctionListing a) {
        em.persist(a);
        em.flush();
        em.refresh(a);

        return a;
    }

    @Override
    public List<AuctionListing> retrieveAllAuctionListings() {
        Query query = em.createQuery("SELECT a FROM AuctionListing a");
        return query.getResultList();
    }

    @Override
    public List<AuctionListing> retrieveAuctionListingsBelowExpectedPrice(){
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.owner.customerId = 0");
        List<AuctionListing> list = query.getResultList();
        for(AuctionListing a: list){
            a.getBidList();
        }
        return list;
    }
    
    @Override
    public AuctionListing retrieveAuctionListingById(Long id) throws AuctionListingNotFoundException {
        AuctionListing a = em.find(AuctionListing.class, id);

        if (a != null) {
            a.getBidList().size();
            return a;
        } else {
            throw new AuctionListingNotFoundException("Staff ID " + id + " does not exist!");
        }
    }

    @Override
    public void doUpdateAuctionListing(AuctionListing a) {
        em.merge(a);
    }

    @Override
    public void deleteAuctionListing(Long id) throws AuctionListingNotFoundException {
        AuctionListing a = retrieveAuctionListingById(id);
        if (a != null) {
            em.remove(a);
        } else {
            throw new AuctionListingNotFoundException("the auction listing " + id + " can't be found!");
        }
    }

    @Override
    public void disableAuctionListing(Long id) throws AuctionListingNotFoundException {
        AuctionListing a = retrieveAuctionListingById(id);
        if (a != null) {
            a.setStatus(Boolean.FALSE);
            //refund money
            List<Bid> bidList = a.getBidList();
            Bid b = bidList.get(bidList.size() - 1);
            Customer c = b.getCustomer();
            c.setCreditBalance(b.getBidAmount().add(c.getCreditBalance()));

        } else {
            throw new AuctionListingNotFoundException("the auction listing " + id + " can't be found!");
        }
    }

    @Override
    public void checkAuctionListingEndDate() {
        Date d = new Date();
        System.out.println("now the date is " + d.toString());
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.status = TRUE");
        List<AuctionListing> list = query.getResultList();
        for (AuctionListing a : list) {
            if (a.getEndDate().compareTo(d) <= 0) {   //endDate is smaller, means pass already
                a.setStatus(Boolean.FALSE);
                System.out.println("product with id "+a.getId() + " has closed.");
                if (a.getBidList().isEmpty()) {
                    System.out.println("nobody ever bid this product");
                    //nobody bid this product then close it is enough
                } else {
                    System.out.println("this product has bids");
                    List<Bid> bidList = a.getBidList();
                    Bid b = bidList.get(bidList.size() - 1);
                    Customer c = b.getCustomer();
                    if(b.getBidAmount().compareTo(a.getExpectedPrice()) >=0){
                        //assign product to this customer directly
                        System.out.println("customer " + c.getCustomerId()+ " has succcessfully bid this product");
                        c.getProductList().add(a);
                        a.setOwner(c);
                    }
                    else{
                        //send to salesStaffs to further decide
                        System.out.println("this product will be sent to sales staff to further decide");
                        Customer systemCustomer = em.find(Customer.class, 0);
                        a.setOwner(systemCustomer);

                    }
                }

            }
        }
    }
    
    
    @Override
    public void assignOwnerManully(AuctionListing a){
        List<Bid> bidList = a.getBidList();
        Bid b = bidList.get(bidList.size()-1);
        Customer c = b.getCustomer();
        a.setOwner(c);
        c.getProductList().add(a);
    }
}
