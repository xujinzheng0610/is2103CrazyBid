/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author User
 */
public class AuctionListingNotFoundException extends Exception{

    public AuctionListingNotFoundException() {
    }

    public AuctionListingNotFoundException(String message) {
        super(message);
    }
    
}
