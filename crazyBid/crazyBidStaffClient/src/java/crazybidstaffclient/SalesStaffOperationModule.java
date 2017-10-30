/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.Staff;
import enumeration.StaffAccessRight;
import exception.InvalidAccessRightException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class SalesStaffOperationModule {
    private Staff currentStaff;

    public SalesStaffOperationModule() {
    }

    public SalesStaffOperationModule(Staff currentStaff) {
        this.currentStaff = currentStaff;
    }

    public void menuSalesStaffOperation() throws InvalidAccessRightException {
        if (currentStaff.getAccessRight() != StaffAccessRight.SALES) {
            throw new InvalidAccessRightException("You don't have SALES rights to access the sales operation module.");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Bid : Admin Operation ***\n");
            System.out.println("1: Create Auction Listing");
            System.out.println("2: View Auction Listing Details");
//            System.out.println("3: Update Auction Listing");
//            System.out.println("4: Delete Auction Listing");
            System.out.println("3: View All Auction Listings");
            System.out.println("4: View All Auction Listings with bids but below reserve price");
            System.out.println("-----------------------");
            System.out.println("5: Back\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 5) {
                    break;
                } else if (response == 1) {
                    createAuctionListing();
                } else if (response == 2) {
                    viewAuctionListingDetails();
                } else if (response == 3) {
                    viewAuctionListings();
                } else if (response == 4) {
                    viewAuctionListingsBelow();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }
    
    public void createAuctionListing(){}
    public void viewAuctionListingDetails(){}
    public void viewAuctionListings(){}
    public void viewAuctionListingsBelow(){}
    
    
    
}
