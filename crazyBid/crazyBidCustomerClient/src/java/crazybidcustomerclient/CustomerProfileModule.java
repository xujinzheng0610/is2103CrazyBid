/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import Entity.Customer;
import ejb.session.stateless.CustomerEntityControllerRemote;
import exception.CustomerNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Chester
 */
public class CustomerProfileModule {

    private Customer currentCustomer;
    private CustomerEntityControllerRemote customerEntityControllerRemote;

    public CustomerProfileModule() {
    }

    public CustomerProfileModule(Customer currentCustomer, CustomerEntityControllerRemote customerEntityControllerRemote) {
        this.currentCustomer = currentCustomer;
        this.customerEntityControllerRemote = customerEntityControllerRemote;
    }

    public void menuProfileOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** POS System :: Customer Profile Operation ***\n");
            System.out.println("1: View Customer Profile");
            System.out.println("2: Update Customer Profile");
            System.out.println("3: Create Address");
            System.out.println("4: View Address Details");
            System.out.println("5: Back\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doViewCustomerProfile();
                } else if (response == 2) {
                    doUpdateProfile(currentCustomer);
                } else if (response == 3) {
                    doCreateAddress(currentCustomer);
                }  else if (response == 5) {
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

    private void doViewCustomerProfile() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: View Customer Profile ***\n");
        System.out.print("Enter username > ");
        String username = scanner.nextLine();

        try {
            Customer customer = customerEntityControllerRemote.retrieveCustomerByUsername(username);
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s\n", "First Name", "Last Name", "Username", "Password", "Email", "Phone Number", "Credit Balance", "Premium Status");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s\n", customer.getFirstName(), customer.getLastName(), customer.getUserName(), customer.getPassword(), customer.getEmail(), customer.getPhoneNumber(), customer.getCreditBalance(), customer.getPremium());
            System.out.println("------------------------");
            System.out.println("1: Update Profile");
            System.out.println("2: Back\n");
            System.out.print("> ");

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doUpdateProfile(customer);
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

        } catch (CustomerNotFoundException ex) {
            System.out.println("Customer is not found!");
        }
    }

    public void doUpdateProfile(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** CrazyBid :: View Customer Profile :: Update Customer Profile ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setFirstName(input);
        }

        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setLastName(input);
        }

        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setUserName(input);
        }

        System.out.print("Enter Password (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setPassword(input);
        }

        System.out.print("Enter Email (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setEmail(input);
        }

        System.out.print("Enter Phone Number (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            customer.setPhoneNumber(input);
        }

        customerEntityControllerRemote.updateCustomer(customer);
        System.out.println("Customer updated successfully!\n");
    }

    
    public void doCreateAddress(Customer customer){
        
    }
}
