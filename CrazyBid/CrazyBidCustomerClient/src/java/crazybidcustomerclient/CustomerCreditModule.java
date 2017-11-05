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

public class CustomerCreditModule {

    private Customer currentCustomer;
    private CustomerEntityControllerRemote customerEntityControllerRemote;

    public CustomerCreditModule() {
    }

    public CustomerCreditModule(Customer currentCustomer, CustomerEntityControllerRemote customerEntityControllerRemote) {
        this.currentCustomer = currentCustomer;
        this.customerEntityControllerRemote = customerEntityControllerRemote;
    }

 

    public void menuCreditModule() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CrazyBid :: Credit Operation ***\n");
            System.out.println("1: View Credit Balance");
            System.out.println("2: View Credit Transaction History");
            System.out.println("3: Purchase Credit Package");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doViewCreditBalance();
                } else if (response == 2) {
                    doViewTransactionHistory();
                } else if (response == 2) {
                    doPurchaseCredit();
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

    public void doViewCreditBalance() {
        System.out.println("view balance");
    }

    public void doViewTransactionHistory() {
        System.out.println("view transaction history");
    }

    public void doPurchaseCredit() {
        System.out.println("purchase credit");

    }

}
