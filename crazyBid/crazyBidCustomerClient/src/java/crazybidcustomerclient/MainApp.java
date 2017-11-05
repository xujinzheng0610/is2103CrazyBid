/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import Entity.Customer;
import ejb.session.stateless.CustomerEntityControllerRemote;
import exception.InvalidLoginCredentialException;
import java.util.Scanner;

/**
 *
 * @author Chester
 */
public class MainApp {

    private CustomerEntityControllerRemote customerEntityControllerRemote;

    private CustomerProfileModule customerProfileModule;

    private CustomerCreditModule customerCreditModule;

    private Customer currentCustomer;

    public MainApp() {
    }

    public MainApp(CustomerEntityControllerRemote customerEntityControllerRemote) {
        this.customerEntityControllerRemote = customerEntityControllerRemote;
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
                        customerProfileModule = new CustomerProfileModule(currentCustomer, customerEntityControllerRemote);
                        customerCreditModule = new CustomerCreditModule(currentCustomer, customerEntityControllerRemote);
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

        System.out.println("*** CrazyBiz.com Staff System :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

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

        System.out.println("*** CrazyBid :: Membership Registration ***\n");
        System.out.print("Enter First Name> ");
        newCustomer.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newCustomer.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        newCustomer.setUserName(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        newCustomer.setPassword(scanner.nextLine().trim());
        System.out.print("Enter Email> ");
        newCustomer.setEmail(scanner.nextLine().trim());
        System.out.print("Enter Phone Number> ");
        newCustomer.setPhoneNumber(scanner.nextLine().trim());
        newCustomer.setCreditBalance(0L);
        newCustomer.setPremium(false);

        //Test registration
//        newCustomer.setBidList(null);
        newCustomer.setTopUpList(null);
        newCustomer.setAddressList(null);
        newCustomer.setProductList(null);

        newCustomer = customerEntityControllerRemote.persistNewCustomer(newCustomer);
        System.out.println("Registration successful!: " + newCustomer.getUserName() + "\n");
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
//            System.out.println("2: Update Customer Profile");
//            System.out.println("3: Create Address");
//            System.out.println("4: View Address Details"); //1:Update Address, 2:Delete Address
//            System.out.println("5: View All Addresses");
//            System.out.println("6: View Credit Balance");
//            System.out.println("7: View Credit Transaction History");
//            System.out.println("8: Purchase Credit Package");
//            System.out.println("9: Browse All Auction Listings");
//            System.out.println("10: View Auction Listing Details"); //1:place new bids, 2: refresh auction listing bids
//            System.out.println("11: View Browse Won Auction Listings"); //1:Select Delivery Address for Won Auction Listing

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    customerProfileModule.menuProfileOperation();
                } else if (response == 2) {
                    customerCreditModule.menuCreditModule();
                } else if (response == 3) {
                    //auctionModule
                }
                if (response == 4) {
                    break;
                }
            }
            if (response == 4) {
                break;
            }

        }
    }

}
