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
import exception.BalanceNotEnoughException;
import exception.CustomerNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
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

    @Resource
    TimerService timerService;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public AuctionListing persistNewAuctionListing(AuctionListing a) { //persist auction listing
        em.persist(a);
        em.flush();
        em.refresh(a);

        return a;
    }

    @Override
    public List<AuctionListing> retrieveAllAuctionListings() {//retrieve all listings
        Query query = em.createQuery("SELECT a FROM AuctionListing a");
        List<AuctionListing> aList = query.getResultList();
        for (AuctionListing a : aList) {
            a.getBidList();
            a.getStatus();
            for (Bid b : a.getBidList()) {
                b.getBidAmount();

            }
        }
        return aList;
    }

    @Override
    public List<AuctionListing> retrieveAuctionListingsBelowExpectedPrice() { //retrive listings with Bids but below reserved price
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.owner.customerId = 1");
        List<AuctionListing> list = query.getResultList();
        for (AuctionListing a : list) {
            a.getBidList();
            a.getBidList().size();
        }
        return list;
    }

    @Override
    public AuctionListing retrieveAuctionListingById(Long id) throws AuctionListingNotFoundException { //retrive by ID
        AuctionListing a = em.find(AuctionListing.class, id);

        if (a != null) {
            a.getBidList().size();
            a.getAddress();
            return a;
        } else {
            throw new AuctionListingNotFoundException("Auction ID " + id + " does not exist!");
        }
    }

    @Override
    public AuctionListing doUpdateAuctionListing(AuctionListing a) {//update auction listing
        em.merge(a);
        a.getAddress();
        return a;
    }

    @Override
    public void deleteAuctionListing(Long id) throws AuctionListingNotFoundException {//delete auction listing
        AuctionListing a = retrieveAuctionListingById(id);
        if (a != null) {
            em.remove(a);
        } else {
            throw new AuctionListingNotFoundException("the auction listing " + id + " can't be found!");
        }
    }

    @Override
    public void disableAuctionListing(Long id) throws AuctionListingNotFoundException { //to disable the auction listing so that it will not be able to accept bids
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
    public void checkAuctionListingEndDate() { //for EJB timer to check if listing has expired.
        Date d = new Date();
        System.out.println("now the date is " + d.toString());
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.status = TRUE");
        List<AuctionListing> list = query.getResultList();
        for (AuctionListing a : list) {
            if (a.getEndDate().compareTo(d) <= 0) {   //endDate is smaller,listing has expired
                a.setStatus(Boolean.FALSE);
                System.out.println("product with id " + a.getId() + " has closed.");
                if (a.getBidList().isEmpty()) {
                    System.out.println("nobody ever bid this product");
                    //if listing receives no bid
                } else {
                    System.out.println("this product has bids");
                    List<Bid> bidList = a.getBidList();
                    Bid b = bidList.get(bidList.size() - 1);
                    Customer c = b.getCustomer();
                    if (b.getBidAmount().compareTo(a.getExpectedPrice()) >= 0) {
                        //if listing has bids and winning bid is greater than or equals to reserve price
                        System.out.println("customer " + c.getCustomerId() + " has succcessfully bid this product");
                        c.getProductList().add(a);
                        a.setOwner(c);
                    } else {
                        //send to salesStaffs to further decide when listing has bids but below reserve price
                        System.out.println("this product will be sent to sales staff to further decide");
                        Long id = Long.parseLong("1");
                        Customer systemCustomer = em.find(Customer.class, id);
                        a.setOwner(systemCustomer);

                    }
                }

            }
        }
    }

    @Override
    public void assignOwnerManully(Long id) throws AuctionListingNotFoundException { //to assign winning bid to customer manually
        AuctionListing a = em.find(AuctionListing.class, id);
        if (a != null) {
            List<Bid> bidList = a.getBidList();
            Bid b = bidList.get(bidList.size() - 1);
            Customer c = b.getCustomer();
            a.setOwner(c);
            c.getProductList().add(a);
        } else {
            throw new AuctionListingNotFoundException("This Auction Listing id is invalid!");
        }
    }

    @Override
    public AuctionListing doPlaceNewBid(Long cId, Long aId) throws CustomerNotFoundException, AuctionListingNotFoundException, BalanceNotEnoughException {
        //for customer to place a new bid on a listing
        Customer c = em.find(Customer.class, cId);
        AuctionListing a = em.find(AuctionListing.class, aId);
        if (c != null) {
            if (a != null) {
                float price = 0;
                List<Bid> bidList = a.getBidList();
                if (bidList.isEmpty()) {
                    price = a.getStartingPrice().floatValue();
                } else {
                    Bid b = bidList.get(bidList.size() - 1);
                    price = b.getBidAmount().floatValue();
                }
                //different increments fo different prices 
                if (price > 0 && price < 1) {
                    price += 0.05;
                } else if (price >= 1 && price < 5) {
                    price += 0.25;
                } else if (price >= 5 && price < 25) {
                    price += 0.50;
                } else if (price >= 25 && price < 100) {
                    price += 1.00;
                } else if (price >= 100 && price < 250) {
                    price += 2.50;
                } else if (price >= 250 && price < 500) {
                    price += 5.00;
                } else if (price >= 500 && price < 1000) {
                    price += 10.00;
                } else if (price >= 1000 && price < 2500) {
                    price += 25.00;
                } else if (price >= 2500 && price < 5000) {
                    price += 50.00;
                } else if (price >= 5000) {
                    price += 100.00;
                }

                //to check if user has enough credits to place bid
                if (c.getCreditBalance().compareTo(BigDecimal.valueOf(price).setScale(4, RoundingMode.UP)) < 0) {
                    throw new BalanceNotEnoughException("This customer has not enough balance");
                } else {
                    if (!bidList.isEmpty()) {
                        Bid b = bidList.get(bidList.size() - 1);
                        Customer oldCustomer = b.getCustomer();
                        oldCustomer.setCreditBalance(oldCustomer.getCreditBalance().add(b.getBidAmount()));

                        BigDecimal amount = b.getBidAmount();
                        Bid refundBid = new Bid(new Date(), amount.setScale(4, RoundingMode.UP), a, c);
                        em.persist(refundBid);
                        refundBid.setBidAmount(amount.negate());
                        oldCustomer.getBidList().add(refundBid);
                        //refund previous bid to previous customer
                    }
                    //assign latest bid to latest customer
                    Bid newBid = new Bid(new Date(), BigDecimal.valueOf(price).setScale(4, RoundingMode.UP), a, c);
                    System.out.println("******** value of Price:  " + price);
                    em.persist(newBid);
                    c.getBidList().add(newBid);
                    BigDecimal newBalance = c.getCreditBalance().subtract(BigDecimal.valueOf(price).setScale(4, RoundingMode.UP));
                    c.setCreditBalance(newBalance);
                    a.getBidList().add(newBid);
                    a.getBidList().size();
                    return a;
                }
            } else {
                throw new AuctionListingNotFoundException("This auction listing id is invalid!");
            }
        } else {
            throw new CustomerNotFoundException("This Customer id is invalid!");
        }
    }

    @Override
    public void newConfigureSniping(Long cId, Long aId, BigDecimal expectingPrice) throws CustomerNotFoundException, AuctionListingNotFoundException, BalanceNotEnoughException {
        Customer c = em.find(Customer.class, cId);
        AuctionListing a = em.find(AuctionListing.class, aId);
        if (c != null) {
            if (a != null) {
                float price = 0;
                List<Bid> bidList = a.getBidList();
                if (bidList.isEmpty()) {
                    price = a.getStartingPrice().floatValue();
                } else {
                    Bid b = bidList.get(bidList.size() - 1);
                    price = b.getBidAmount().floatValue();
                }
                //different increments fo different prices 
                if (price > 0 && price < 1) {
                    price += 0.05;
                } else if (price >= 1 && price < 5) {
                    price += 0.25;
                } else if (price >= 5 && price < 25) {
                    price += 0.50;
                } else if (price >= 25 && price < 100) {
                    price += 1.00;
                } else if (price >= 100 && price < 250) {
                    price += 2.50;
                } else if (price >= 250 && price < 500) {
                    price += 5.00;
                } else if (price >= 500 && price < 1000) {
                    price += 10.00;
                } else if (price >= 1000 && price < 2500) {
                    price += 25.00;
                } else if (price >= 2500 && price < 5000) {
                    price += 50.00;
                } else if (price >= 5000) {
                    price += 100.00;
                }

                //to check if user has enough credits to place bid
                if (c.getCreditBalance().compareTo(BigDecimal.valueOf(price).setScale(4, RoundingMode.UP)) < 0) {
                    throw new BalanceNotEnoughException("This customer has not enough balance");
                } else if (expectingPrice.compareTo(BigDecimal.valueOf(price).setScale(4, RoundingMode.UP)) < 0) {
                    System.out.println("Has exceeded the expected price, give up.");
                } else {
                    if (!bidList.isEmpty()) {
                        Bid b = bidList.get(bidList.size() - 1);
                        Customer oldCustomer = b.getCustomer();
                        oldCustomer.setCreditBalance(oldCustomer.getCreditBalance().add(b.getBidAmount()));

                        BigDecimal amount = b.getBidAmount();
                        Bid refundBid = new Bid(new Date(), amount.setScale(4, RoundingMode.UP), a, c);
                        em.persist(refundBid);
                        refundBid.setBidAmount(amount.negate());
                        oldCustomer.getBidList().add(refundBid);
                        //refund previous bid to previous customer
                    }
                    //assign latest bid to latest customer
                    Bid newBid = new Bid(new Date(), BigDecimal.valueOf(price).setScale(4, RoundingMode.UP), a, c);
                    System.out.println("******** value of Price:  " + price);
                    em.persist(newBid);
                    c.getBidList().add(newBid);
                    BigDecimal newBalance = c.getCreditBalance().subtract(BigDecimal.valueOf(price).setScale(4, RoundingMode.UP));
                    c.setCreditBalance(newBalance);
                    a.getBidList().add(newBid);
                    a.getBidList().size();

                }

            } else {
                throw new AuctionListingNotFoundException("This auction listing id is invalid!");
            }
        } else {
            throw new CustomerNotFoundException("This customer id is invalid!");
        }
    }

}
