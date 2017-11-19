/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import Entity.Address;
import Entity.AuctionListing;
import Entity.Bid;
import Entity.Customer;
import ejb.session.stateless.AuctionListingEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import exception.AuctionListingNotFoundException;
import exception.BalanceNotEnoughException;
import exception.CustomerNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author User
 */
public class CustomerBidModule {

    private Customer currentCustomer;

    private AuctionListingEntityControllerRemote auctionListingEntityControllerRemote;
    private CustomerEntityControllerRemote customerEntityControllerRemote;

    public CustomerBidModule() {
    }

    public CustomerBidModule(Customer currentCustomer, AuctionListingEntityControllerRemote auctionListingEntityControllerRemote, CustomerEntityControllerRemote customerEntityControllerRemote) {
        this.currentCustomer = currentCustomer;
        this.auctionListingEntityControllerRemote = auctionListingEntityControllerRemote;
        this.customerEntityControllerRemote = customerEntityControllerRemote;
    }

    public void menuBidModule() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** CrazyBid :: Customer Bid Module ***\n");
            System.out.println("1: View All Auction Listings");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Browse Won Auction Listing");
            System.out.println("4: Back\n");
            System.out.println("---------------------------");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doViewAllAuctionListings();
                } else if (response == 2) {
                    doViewAuctionListingDetails();
                } else if (response == 3) {
                    doBrowseWonAuctionListing();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 4) {
                break;
            }
        }
    }

    public void doViewAllAuctionListings() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid ::Customer Bid System :: View All Auction Listings ***\n");

        List<AuctionListing> aList = auctionListingEntityControllerRemote.retrieveAllAuctionListings();
        System.out.printf("%5s%20s%25s%25s%30s\n", "Product ID", "Product Name", "Starting Price", "Current Price", "End time");

        for (AuctionListing a : aList) {
            if (a.getStatus()) {
                Date d = new Date();
                if (a.getStartDate().compareTo(d) <= 0 && a.getEndDate().compareTo(d) >= 0) {
                    String currentPrice = "";
                    List<Bid> bidList = a.getBidList();
                    if (bidList.isEmpty()) {
                        currentPrice = a.getStartingPrice().toString();
                    } else {
                        currentPrice = bidList.get(bidList.size() - 1).getBidAmount().toString();
                    }
                    System.out.printf("%5s%20s%25s%25s%50s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
                            currentPrice, a.getEndDate());

                }
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    public void doViewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: Customer Bid System :: View Auction Listing Details ***\n");
        System.out.print("Enter Auction Listing ID> ");
        Long id = scanner.nextLong();

        try {
            AuctionListing a = auctionListingEntityControllerRemote.retrieveAuctionListingById(id);
            if (a.getStatus()) {
                Date d = new Date();
                if (a.getStartDate().compareTo(d) <= 0 && a.getEndDate().compareTo(d) >= 0) {
                    System.out.printf("%5s%20s%25s%25s%30s%30s\n", "Product ID", "Product Name", "Starting Price", "Current Price", "Current Leading Customer", "End time");
                    String currentPrice = "";
                    String currentLeadingBid = "Pending";
                    List<Bid> bidList = a.getBidList();
                    if (bidList.isEmpty()) {
                        currentPrice = a.getStartingPrice().toString();
                    } else {
                        currentPrice = bidList.get(bidList.size() - 1).getBidAmount().toString();
                        currentLeadingBid = bidList.get(bidList.size() - 1).getCustomer().getUserName();
                    }
                    System.out.printf("%5s%20s%25s%25s%25s%50s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
                            currentPrice, currentLeadingBid, a.getEndDate());

                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

                    while (response < 1 || response > 3) {
                        System.out.println("1: Place New Bid");
                        System.out.println("2: Refresh Auction Listing Bids");
                        System.out.println("3: Back\n");
                        System.out.print("> ");
                        response = scanner.nextInt();
                        if (response == 2) {
                            doRefreshAuctionListingBids(a);
                        } else if (response == 1) {
                            if (a.getStatus()) {
                                a.getBidList();
                                try {
                                    a = auctionListingEntityControllerRemote.doPlaceNewBid(currentCustomer.getCustomerId(), a.getId());
                                    bidList = a.getBidList();
                                    Bid b = bidList.get(bidList.size() - 1);

                                    System.out.println("You have succussfully bid this auction listing with price: " + b.getBidAmount().toString());
                                } catch (CustomerNotFoundException | AuctionListingNotFoundException | BalanceNotEnoughException ex) {
                                    System.out.println("Error occurs with Info: " + ex.getMessage() + " !");
                                }
                            } else {
                                System.out.println("This listing has expired!");
                            }
                        } else if (response == 3) {

                        } else {
                            System.out.println("Option is invalid, please try again!");
                        }
                    }
                } else {
                    System.out.println("This listing is not available!");
                }
            } else {
                System.out.println("This listing has expired!");
            }
        } catch (AuctionListingNotFoundException ex) {
            System.out.println("An error has occurred while retrieving auction listing: " + ex.getMessage() + "\n");
        }
    }

    public void doBrowseWonAuctionListing() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CrazyBid :: Customer Bid System :: Browse Won Auction Listing ***\n");
        try {
            currentCustomer = customerEntityControllerRemote.retrieveCustomerByUsername(currentCustomer.getUserName());
            List<AuctionListing> aList = currentCustomer.getProductList();
            System.out.printf("%5s%20s%25s%80s\n", "Product ID", "Product Name", "Won Price", "Address");
            for (AuctionListing a : aList) {
                String address = "";
                if (a.getAddress() == null) {
                    address = "Pending";
                } else {
                    Address add = a.getAddress();
                    address = add.getStreetAddress() + ", " + add.getCity() + ", " + add.getCountry() + ", " + add.getPostalCode() + ", " + add.getPhoneNumber() + ".";
                }
                String wonPrice = "";
                List<Bid> bidList = a.getBidList();
                Bid b = bidList.get(bidList.size() - 1);
                wonPrice = b.getBidAmount().toString();
                System.out.printf("%5s%20s%25s%80s\n", a.getId(), a.getProduct(), wonPrice, address);
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            }
        } catch (CustomerNotFoundException ex) {
            System.out.println("Error occurs with Info: " + ex.getMessage() + " !");
        }
        Integer response = 0;
        while (response < 1 || response > 2) {
            System.out.println("1: Select Delivery Address For Won Auction Listing");
            System.out.println("2: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();
            if (response == 1) {
                doSelectAddressForWonAuctionListing();
            } else if (response == 2) {

            } else {
                System.out.println("Option is invalid, please try again!");
            }
        }
    }

    public void doRefreshAuctionListingBids(AuctionListing a) {
        //similar to view auction listing details
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        try {
            a = auctionListingEntityControllerRemote.retrieveAuctionListingById(a.getId());
            if (a.getStatus()) {
                Date d = new Date();
                if (a.getStartDate().compareTo(d) <= 0 && a.getEndDate().compareTo(d) >= 0) {
                    System.out.printf("%5s%20s%25s%25s%30s%30s\n", "Product ID", "Product Name", "Starting Price", "Current Price", "Current Leading Customer", "End time");
                    String currentPrice = "";
                    String currentLeadingBid = "Pending";
                    List<Bid> bidList = a.getBidList();
                    if (bidList.isEmpty()) {
                        currentPrice = a.getStartingPrice().toString();
                    } else {
                        currentPrice = bidList.get(bidList.size() - 1).getBidAmount().toString();
                        currentLeadingBid = bidList.get(bidList.size() - 1).getCustomer().getUserName();
                    }
                    System.out.printf("%5s%20s%25s%25s%30s%30s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
                            currentPrice, currentLeadingBid, a.getEndDate());

                    while (response < 1 || response > 3) {
                        System.out.println("------------------------");
                        System.out.println("1: Place New Bid");
                        System.out.println("2: Refresh Auction Listing Bids");
                        System.out.println("3: Back\n");
                        System.out.print("> ");
                        response = scanner.nextInt();
                        if (response == 2) {
                            doRefreshAuctionListingBids(a);
                        } else if (response == 1) {
                            if (a.getStatus()) {
                                a.getBidList();
                                try {
                                    a = auctionListingEntityControllerRemote.doPlaceNewBid(currentCustomer.getCustomerId(), a.getId());
                                } catch (CustomerNotFoundException | AuctionListingNotFoundException | BalanceNotEnoughException ex) {
                                    System.out.println("Error occurs with Info: " + ex.getMessage() + " !");
                                }
                            } else {
                                System.out.println("Sorry, this auctionListing has expired!");
                            }
                        } else if (response == 3) {

                        } else {
                            System.out.println("Option is invalid, please try again!");
                        }
                    }
                } else {
                    System.out.println("Sorry, this auctionListing is not available!");
                }
            } else {
                System.out.println("Sorry, this auctionListing has expired!");
            }
        } catch (AuctionListingNotFoundException ex) {
            System.out.println("An error has occurred while retrieving auction listing: " + ex.getMessage() + "\n");
        }
    }

    public void doSelectAddressForWonAuctionListing() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CrazyBid :: Customer Bid System :: Browse Won Auction Listing :: Select Address For Won Auction Listing ***\n");
        try {
            currentCustomer = customerEntityControllerRemote.retrieveCustomerByUsername(currentCustomer.getUserName());
            System.out.print("Plese key in the id of auction listing> ");
            Long id = scanner.nextLong();
            try {
                AuctionListing auctionListing = auctionListingEntityControllerRemote.retrieveAuctionListingById(id);
                if (auctionListing.getAddress() != null) {
                    System.out.println("You have assign address for this auction listing already!");
                } else {
                    List<Address> aList = currentCustomer.getAddressList();
                    System.out.println("available addresses:");
                    System.out.println("________________________________________________________");
                    System.out.printf("%5s%20s%20s%40s%10s%20s\n", "Address ID", "Counrty", "City", "Street", "Postal Code", "Phone Number");
                    for (Address a : aList) {
                        if (a.getStatus()) {
                            System.out.printf("%5s%20s%20s%40s%10s%20s\n", a.getId(), a.getCountry(), a.getCity(), a.getStreetAddress(), a.getPostalCode(), a.getPhoneNumber());
                        }
                    }
                    while (true) {
                        System.out.print("\n Please key in address id> ");
                        Long aId = scanner.nextLong();
                        Address address = new Address();
                        for (Address a : aList) {
                            if (a.getId().equals(aId)) {
                                address = a;
                            }
                        }
                        if (address != null && address.getStatus()) {
                            auctionListing.setAddress(address);
                            auctionListing = auctionListingEntityControllerRemote.doUpdateAuctionListing(auctionListing);
                            System.out.println("You have succussfully assign address with id: " + auctionListing.getAddress().getId() + " for this auction listing");
                            break;
                        } else {
                            System.out.println("The address id is invalid!");
                            Integer response = 0;
                            System.out.println("Key in 1 to quit this action.");
                            System.out.println("Key in any other number to continue");
                            response = scanner.nextInt();
                            if (response == 1) {
                                break;
                            }
                        }
                    }
                }

            } catch (AuctionListingNotFoundException ex) {
                System.out.println("Error occurs with Info: " + ex.getMessage() + "!");
            }
        } catch (CustomerNotFoundException ex) {
            System.out.println("Error occurs with Info: " + ex.getMessage() + " !");
        }

    }
}
