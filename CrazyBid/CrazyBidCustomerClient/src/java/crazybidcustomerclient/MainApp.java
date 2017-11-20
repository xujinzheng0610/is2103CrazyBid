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
import Entity.TopUpTransaction;
import ejb.session.stateless.AddressEntityControllerRemote;
import ejb.session.stateless.AuctionListingEntityControllerRemote;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.TopUpTransactionControllerRemote;
import exception.CustomerNotFoundException;
import exception.InvalidLoginCredentialException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chester
 */
public class MainApp {

    private CustomerEntityControllerRemote customerEntityControllerRemote;
    private AddressEntityControllerRemote addressEntityControllerRemote;
    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;
    private TopUpTransactionControllerRemote topUpTransactionControllerRemote;
    private AuctionListingEntityControllerRemote auctionListingEntityControllerRemote;

    private CustomerProfileModule customerProfileModule;
    private CustomerCreditModule customerCreditModule;
    private CustomerBidModule customerBidModule;

    private Customer currentCustomer;

    public MainApp() {
    }

    public MainApp(CustomerEntityControllerRemote customerEntityControllerRemote, AddressEntityControllerRemote addressEntityControllerRemote, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote, TopUpTransactionControllerRemote topUpTransactionControllerRemote, AuctionListingEntityControllerRemote auctionListingEntityControllerRemote) {
        this.customerEntityControllerRemote = customerEntityControllerRemote;
        this.addressEntityControllerRemote = addressEntityControllerRemote;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
        this.topUpTransactionControllerRemote = topUpTransactionControllerRemote;
        this.auctionListingEntityControllerRemote = auctionListingEntityControllerRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** Welcome to CrazyBid.com! ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        customerProfileModule = new CustomerProfileModule(currentCustomer, customerEntityControllerRemote, addressEntityControllerRemote);
                        customerCreditModule = new CustomerCreditModule(currentCustomer, customerEntityControllerRemote, creditPackageEntityControllerRemote, topUpTransactionControllerRemote);
                        customerBidModule = new CustomerBidModule(currentCustomer, auctionListingEntityControllerRemote, customerEntityControllerRemote);
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                    }
                } else if (response == 2) {
                    doRegister();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 3) {
                break;
            }
        }
    }

    public void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** CrazyBid :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        while (username.length() == 0) {
            //to validate empty input
            System.out.println("Please enter your username!");
            System.out.print("Enter username> ");
            username = scanner.nextLine().trim();
        }
        
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        while (password.length() == 0) { //to validate empty input
            System.out.println("Please enter your password");
            System.out.print("Enter password> ");
            password = scanner.nextLine().trim();
        }

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentCustomer = customerEntityControllerRemote.customerLogin(username, password);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                throw new InvalidLoginCredentialException();
            }
        } else {
            System.out.println("Invalid login credential!");
        }
    }

        public void doRegister() {
        Scanner scanner = new Scanner(System.in);
        Customer newCustomer = new Customer();
        String input;

        System.out.println("*** CrazyBid :: Membership Registration ***\n");

        System.out.print("Enter First Name> ");
        input = scanner.nextLine().trim();
        newCustomer.setFirstName(input);

        System.out.print("Enter Last Name> ");
        input = scanner.nextLine().trim();
        newCustomer.setLastName(input);
        while (true) {
            System.out.print("Enter Username> ");
            input = scanner.nextLine().trim();
            if (input.length() == 0) { //to validate empty input
                System.out.println("Please enter a username!");
            } else {
                try {
                    Customer c = customerEntityControllerRemote.retrieveCustomerByUsername(input);
                    System.out.println("Username is already taken! Please try again!");
                } catch (CustomerNotFoundException ex) {
                    newCustomer.setUserName(input);
                    break;
                }
            }
        }

        System.out.print("Enter Password> ");
        input = scanner.nextLine().trim();

        while (input.length() == 0) { //to validate empty input
            System.out.println("Please enter a password!");
            System.out.print("Enter Password> ");
            input = scanner.nextLine().trim();
        }

        newCustomer.setPassword(input);

        while (true) {
            System.out.print("Enter Email> ");
            input = scanner.nextLine().trim();
            if (input.length() == 0) {
                System.out.println("Please enter an email!");
            } else {
                try {
                    Customer c = customerEntityControllerRemote.retrieveCustomerByEmail(input);
                    System.out.println("Email is already taken! Please try again!");
                } catch (CustomerNotFoundException ex) {
                    newCustomer.setEmail(input);
                    break;
                }
            }
        }

        System.out.print("Enter Phone Number> ");
        input = scanner.nextLine().trim();
        newCustomer.setPhoneNumber(input);

        BigDecimal balance = new BigDecimal("0.0000"); 
        newCustomer.setCreditBalance(balance);

        newCustomer.setPremium(false);

      
        List<Address> addressList = new ArrayList<>();
        List<Bid> bidList = new ArrayList<>();
        List<AuctionListing> productList = new ArrayList<>();
        List<TopUpTransaction> topUpList = new ArrayList<>();

        newCustomer.setAddressList(addressList);
        newCustomer.setBidList(bidList);
        newCustomer.setProductList(productList);
        newCustomer.setTopUpList(topUpList);

        newCustomer = customerEntityControllerRemote.persistNewCustomer(newCustomer);
        System.out.println("\nRegistration successful!: " + newCustomer.getUserName() + "\n");
    }

    public void menuMain() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("*** CrazyBid.com Member Page ***\n");
            System.out.println("You are logged in as: " + currentCustomer.getUserName());
            System.out.println("1: Profile Related Operations");
            System.out.println("2: Credit Related Operations");
            System.out.println("3: Auction Listing Related Operations");
            System.out.println("4: Logout\n");

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    customerProfileModule.menuProfileOperation();
                } else if (response == 2) {
                    customerCreditModule.menuCreditModule();
                } else if (response == 3) {
                    customerBidModule.menuBidModule();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid Option, please try again!");
                }
            }
            if (response == 4) {
                break;
            }

        }
    }

}
