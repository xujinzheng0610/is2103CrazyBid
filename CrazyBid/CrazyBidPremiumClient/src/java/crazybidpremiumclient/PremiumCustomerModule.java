/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidpremiumclient;

import static com.sun.xml.bind.util.CalendarConv.formatter;
import exception.InvalidLoginCredentialException;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import ws.premium.Address;
import ws.premium.AuctionListing;
import ws.premium.AuctionListingNotFoundException_Exception;
import ws.premium.Bid;
import ws.premium.Customer;
import ws.premium.CustomerNotFoundException_Exception;
import ws.premium.InvalidLoginCredentialException_Exception;

/**
 *
 * @author User
 */
public class PremiumCustomerModule {

    private Customer currentCustomer;

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** AI.SG :: Premium Customer Module ***\n");
            System.out.println("1: Remote View Credit Balance");
            System.out.println("2: Remote View All Auction Listings");
            System.out.println("3: Remote View Auction Listing Details");
            System.out.println("4: Remote Browse Won Auction Listing");
            System.out.println("5: Log out\n");
            System.out.println("---------------------------");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    remoteViewCreditBalance();
                } else if (response == 2) {
                    remoteViewAllAuctionListings();
                } else if (response == 3) {
                    remoteViewAuctionListingDetails();
                } else if (response == 4) {
                    remoteBrowseWonAuctionListing();
                } else if (response == 5) {
                    doLogout();
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }

    public void doLogout() {
        
    }

    public void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** AI.SG :: Premium Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        while (username.length() == 0) {
            System.out.println("Please enter your username!");
            System.out.print("Enter username> ");
            username = scanner.nextLine().trim();
        }
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        while (password.length() == 0) {
            System.out.println("Please enter your password");
            System.out.print("Enter password> ");
            password = scanner.nextLine().trim();
        }

        try {
            currentCustomer = customerLogin(username, password);
            System.out.println("Login successful!\n");
            menu();
        } catch (InvalidLoginCredentialException_Exception ex) {
            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
        }
    }

    public void remoteViewCreditBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** AI.SG :: View Credit Balance ***\n");
        try {
            currentCustomer = retrieveCustomerByUsername(currentCustomer.getUserName());
            System.out.println("Credit Balance: " + currentCustomer.getCreditBalance());
            System.out.println("------------------------");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (CustomerNotFoundException_Exception ex) {
            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
        }

    }

    public void remoteViewAllAuctionListings() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("*** AI.SG :: Remote View All Auction Listings ***\n");
//        List<AuctionListing> aList = retrieveAllAuctionListings();
//        System.out.printf("%5s%20s%25s%25s%30s\n", "Product ID", "Product Name", "Starting Price", "Current Price", "End time");
//
//        for (AuctionListing a : aList) {
//            if (a.isStatus()) {
//                Date d = new Date();
//                if (a.getStartDate().toGregorianCalendar().getTime().compareTo(d) <= 0 && a.getEndDate().toGregorianCalendar().getTime().compareTo(d) >= 0) {
//                    String currentPrice = "";
//                    List<Bid> bidList = a.getBidList();
//                    if (bidList.isEmpty()) {
//                        currentPrice = a.getStartingPrice().toString();
//                    } else {
//                        currentPrice = bidList.get(bidList.size() - 1).getBidAmount().toString();
//                    }
//                    System.out.printf("%5s%20s%25s%25s%50s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
//                            currentPrice, a.getEndDate());
//                }
//            }
//        }
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.print("Press any key to continue...> ");
//        scanner.nextLine();
    }

    public void remoteViewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
//        Integer response = 0;
//
//        System.out.println("*** AI.SG :: Remote View Auction Listing Details ***\n");
//        System.out.print("Enter Auction Listing ID> ");
//        Long id = scanner.nextLong();
//        try {
//            AuctionListing a = retrieveAuctionListingById(id);
//            if (a.isStatus()) {
//                Date d = new Date();
//                if (a.getStartDate().toGregorianCalendar().getTime().compareTo(d) <= 0 && a.getEndDate().toGregorianCalendar().getTime().compareTo(d) >= 0) {
//                    System.out.printf("%5s%20s%25s%25s%30s\n", "Product ID", "Product Name", "Starting Price", "Current Price", "End time");
//                    String currentPrice = "";
//
//                    List<Bid> bidList = a.getBidList();
//                    if (bidList.isEmpty()) {
//                        currentPrice = a.getStartingPrice().toString();
//                    } else {
//                        currentPrice = bidList.get(bidList.size() - 1).getBidAmount().toString();
//                    }
//                    System.out.printf("%5s%20s%25s%25s%50s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
//                            currentPrice, a.getEndDate());
//
//                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
//                    while (response < 1 || response > 3) {
//                        System.out.println("1: Configure Proxy Bidding");
//                        System.out.println("2: Configure Sniping");
//                        System.out.println("3: Back\n");
//                        System.out.print("> ");
//                        response = scanner.nextInt();
//                        if (response == 1) {
//                            doConfigureProxyBidding(a);
//                        } else if (response == 2) {
//                            doConfigureSniping(a);
//                        } else if (response == 3) {
//                            break;
//                        } else {
//                            System.out.println("Option is invalid, please try again!");
//                        }
//                    }
//                } else {
//                    System.out.println("This listing is not available!");
//                }
//            } else {
//                System.out.println("This listing has expired!");
//            }
//
//        } catch (AuctionListingNotFoundException_Exception ex) {
//            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
//        }
    }

    public void remoteBrowseWonAuctionListing() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("*** AI.SG :: Remote Browse Won Auction Listing ***\n");
//        try {
//            currentCustomer = retrieveCustomerByUsername(currentCustomer.getUserName());
//            List<AuctionListing> aList = currentCustomer.getProductList();
//            System.out.printf("%5s%20s%25s%80s\n", "Product ID", "Product Name",  "Address");
//            for (AuctionListing a : aList) {
//                String address = "";
//                if (a.getAddress() == null) {
//                    address = "Pending";
//                } else {
//                    Address add = a.getAddress();
//                    address = add.getStreetAddress() + ", " + add.getCity() + ", " + add.getCountry() + ", " + add.getPostalCode() + ", " + add.getPhoneNumber() + ".";
//                }
//
//                System.out.printf("%5s%20s%25s%80s\n", a.getId(), a.getProduct(), address);
//            }
//            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//            System.out.print("Press any key to continue...> ");
//            scanner.nextLine();
//
//        } catch (CustomerNotFoundException_Exception ex) {
//            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
//        }
    }

    public void doConfigureProxyBidding(AuctionListing a) {
    }

    public void doConfigureSniping(AuctionListing a) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("*** AI.SG :: Remote View Auction Listing Details :: Configure Sniping ***\n");
//        System.out.println("The end date of this auction listing is " + a.getEndDate().toGregorianCalendar().getTime());
//        Date bidDate = new Date();
//        while (true) {
//            System.out.println("Please key in your bidding time at least 1 minute before the end time.");
//            System.out.print("Enter in format (yyyy-MM-dd HH:mm:00)> ");
//            String endDateStr = scanner.nextLine().trim();
//            ParsePosition pos = new ParsePosition(0);
//            bidDate = formatter.parse(endDateStr, pos);
//            if (bidDate.compareTo(a.getEndDate().toGregorianCalendar().getTime()) < 0) {
//                break;
//            } else {
//                System.out.println("The time your key in is invalid!");
//            }
//        }
//        System.out.print("Please key in your expected price> ");
//        BigDecimal price = scanner.nextBigDecimal();
//        //only customer login can use the snipping function, so put the timer into frontend.
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                newConfigureSniping(currentCustomer.getCustomerId(), a.getId(), price);
//            }
//        }, bidDate);

    }

    private static ws.premium.Customer customerLogin(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ws.premium.PremiumCustomer_Service service = new ws.premium.PremiumCustomer_Service();
        ws.premium.PremiumCustomer port = service.getPremiumCustomerPort();
        return port.customerLogin(username, password);
    }

    private static Customer retrieveCustomerByUsername(java.lang.String username) throws CustomerNotFoundException_Exception {
        ws.premium.PremiumCustomer_Service service = new ws.premium.PremiumCustomer_Service();
        ws.premium.PremiumCustomer port = service.getPremiumCustomerPort();
        return port.retrieveCustomerByUsername(username);
    }

    private static java.util.List<ws.premium.AuctionListing> retrieveAllAuctionListings() {
        ws.premium.PremiumCustomer_Service service = new ws.premium.PremiumCustomer_Service();
        ws.premium.PremiumCustomer port = service.getPremiumCustomerPort();
        return port.retrieveAllAuctionListings();
    }

    private static AuctionListing retrieveAuctionListingById(java.lang.Long aId) throws AuctionListingNotFoundException_Exception {
        ws.premium.PremiumCustomer_Service service = new ws.premium.PremiumCustomer_Service();
        ws.premium.PremiumCustomer port = service.getPremiumCustomerPort();
        return port.retrieveAuctionListingById(aId);
    }

    private static void newConfigureSniping(java.lang.Long arg0, java.lang.Long arg1, java.math.BigDecimal arg2) {
        ws.premium.PremiumCustomer_Service service = new ws.premium.PremiumCustomer_Service();
        ws.premium.PremiumCustomer port = service.getPremiumCustomerPort();
        port.newConfigureSniping(arg0, arg1, arg2);
    }

}
