/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import Entity.CreditPackage;
import Entity.Customer;
import Entity.TopUpTransaction;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.TopUpTransactionControllerRemote;
import exception.CustomerNotFoundException;
import exception.PackageNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CustomerCreditModule {

    private Customer currentCustomer;
    private CustomerEntityControllerRemote customerEntityControllerRemote;
    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;
    private TopUpTransactionControllerRemote topUpTransactionControllerRemote;

    public CustomerCreditModule() {
    }

    public CustomerCreditModule(Customer currentCustomer, CustomerEntityControllerRemote customerEntityControllerRemote, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote, TopUpTransactionControllerRemote topUpTransactionControllerRemote) {
        this.currentCustomer = currentCustomer;
        this.customerEntityControllerRemote = customerEntityControllerRemote;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
        this.topUpTransactionControllerRemote = topUpTransactionControllerRemote;
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
                } else if (response == 3) {
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CrazyBid :: View Credit Balance ***\n");
        try {
            currentCustomer = customerEntityControllerRemote.retrieveCustomerByUsername(currentCustomer.getUserName());
            System.out.println("Credit Balance: " + currentCustomer.getCreditBalance());
            System.out.println("------------------------");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (CustomerNotFoundException ex) {
            System.out.println("Error occurs with info: " + ex.getMessage() + " !");
        }
    }

    public void doViewTransactionHistory() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid :: View Transaction History ***\n");
        try {
            List<TopUpTransaction> tList = topUpTransactionControllerRemote.retrieveAllTransactions(currentCustomer.getCustomerId());
            System.out.printf("%8s%47s%10s\n", "Date", "Package Name", "Amount");
            for (TopUpTransaction t : tList) {
                System.out.printf("%8s%25s%10s\n", t.getCreatedOn(), t.getCreditPackage().getPackageName(), t.getCreditPackage().getAmount().toString());
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (CustomerNotFoundException ex) {
            System.out.println("Error occur with Info: " + ex.getMessage() + "! ");
        }
    }

    public void doPurchaseCredit() {
        Scanner scanner = new Scanner(System.in);

        //display and choose credit package
        System.out.println("*** CrazyBid :: Purchase Credit Package ***\n");
        List<CreditPackage> cList = creditPackageEntityControllerRemote.retrieveAllCreditPackages();

        System.out.printf("%20s%20s%20s\n", "PackageID", "Package Name", "Amount");

        for (CreditPackage c : cList) {
            System.out.printf("%20s%20s%20s\n", c.getId(), c.getPackageName(), c.getAmount());
        }

        System.out.println("-------------------------------------------------------------------------------------");

        System.out.print("Key in the ID of credit package to purchase: \n");
        Long id = scanner.nextLong();
        try {
            TopUpTransaction t = topUpTransactionControllerRemote.addNewTransaction(currentCustomer.getCustomerId(), id);
            System.out.println("You have successfully purchased " + t.getCreditPackage().getAmount() + " credits on " + t.getCreatedOn());
            try {
                currentCustomer = customerEntityControllerRemote.retrieveCustomerByUsername(currentCustomer.getUserName());
                System.out.println("Your current credit balance is " + currentCustomer.getCreditBalance().toString());
                System.out.println("");
            } catch (CustomerNotFoundException ex) {
                System.out.println("Error occurs with info: " + ex.getMessage() + " !");
            }
        } catch (PackageNotFoundException | CustomerNotFoundException ex) {
            System.out.println("error occured with info: " + ex.getMessage() + " !");
        }

    }

}
