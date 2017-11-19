/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.AuctionListing;
import exception.AuctionListingNotFoundException;
import exception.BalanceNotEnoughException;
import exception.CustomerNotFoundException;
import java.util.List;


public interface AuctionListingEntityControllerLocal {

    public AuctionListing persistNewAuctionListing(AuctionListing a);

    public List<AuctionListing> retrieveAllAuctionListings();

    public AuctionListing retrieveAuctionListingById(Long id) throws AuctionListingNotFoundException;

    public AuctionListing doUpdateAuctionListing(AuctionListing a);

    public void deleteAuctionListing(Long id) throws AuctionListingNotFoundException;

    public void disableAuctionListing(Long id) throws AuctionListingNotFoundException;

    public void checkAuctionListingEndDate();
    
    public List<AuctionListing> retrieveAuctionListingsBelowExpectedPrice();

    public void assignOwnerManully(Long id) throws AuctionListingNotFoundException;
    
    public AuctionListing doPlaceNewBid(Long cId, Long aId) throws CustomerNotFoundException, AuctionListingNotFoundException, BalanceNotEnoughException;
    
}
