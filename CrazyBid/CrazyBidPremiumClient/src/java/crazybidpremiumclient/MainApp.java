/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidpremiumclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import exception.InvalidLoginCredentialException;
import java.util.Scanner;
import Entity.Customer;

/**
 *
 * @author User
 */
public class MainApp {

    private CustomerEntityControllerRemote customerEntityControllerRemote;


    private PremiumCustomerModule premiumCustomerModule;

    private Customer newCustomer;

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
            System.out.println("1: Login as premium customer");
            System.out.println("2: Register as premium customer");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        premiumCustomerModule = new PremiumCustomerModule();
                        premiumCustomerModule.doLogin();
                    } catch (InvalidLoginCredentialException ex) {
                    }
                } else if (response == 2) {
                    try {
                        doRegister();
                    } catch (InvalidLoginCredentialException ex) {
                    }
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


    public void doRegister() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** CrazyBiz :: Register as premium customer ***\n");
        System.out.println("Please log in using your crazy bid account.");
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

        if (username.length() > 0 && password.length() > 0) {
            try {
                newCustomer = customerEntityControllerRemote.customerLogin(username, password);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                throw new InvalidLoginCredentialException();
            }
        } else {
            System.out.println("Invalid login credential!");
        }

        System.out.print("Enter Y to confirm the registration> ");
        String input = scanner.nextLine().trim();
        if (input.equals("Y")) {
            newCustomer.setPremium(Boolean.TRUE);
            customerEntityControllerRemote.updateCustomer(newCustomer);
            System.out.println("You have become the premium customer in AI.SG!");
        } else {
            System.out.println("Sign up failed!");
        }
    }


}
