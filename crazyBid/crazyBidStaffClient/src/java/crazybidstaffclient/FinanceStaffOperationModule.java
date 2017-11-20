/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.CreditPackage;
import Entity.Staff;
import Entity.TopUpTransaction;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import enumeration.StaffAccessRight;
import exception.InvalidAccessRightException;
import exception.PackageNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class FinanceStaffOperationModule {

    private Staff currentStaff;

    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;

    public FinanceStaffOperationModule() {
    }

    public FinanceStaffOperationModule(Staff currentStaff, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote) {
        this.currentStaff = currentStaff;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
    }

    public void menuFinanceStaffOperation() throws InvalidAccessRightException {
        if (currentStaff.getAccessRight() != StaffAccessRight.FINANCE) {
            throw new InvalidAccessRightException("You don't have FINANCE rights to access the finance operation module.");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Bid : Finance Operation ***\n");
            System.out.println("1: Create Credit Package");
            System.out.println("2: View Credit Package Details");
//            System.out.println("3: Update Credit Package");
//            System.out.println("4: Delete Credit Package");
            System.out.println("3: View All Credit Packages");
            System.out.println("---------------------------------");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 4) {
                    break;
                } else if (response == 1) {
                    createCreditPackage();
                } else if (response == 2) {
                    viewCreditPackageDetails();
                } else if (response == 3) {
                    viewAllCreditPackages();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 4) {
                break;
            }
        }
    }

    public void createCreditPackage() {
        Scanner scanner = new Scanner(System.in);
        CreditPackage newCreditPackage = new CreditPackage();

        System.out.println("*** Crazy Bid:: Finance Operation :: Create New Credit Package ***\n");

        while (true) {
            System.out.print("Enter Amount> ");
//            Double a = scanner.nextDouble();
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();
            try {
                creditPackageEntityControllerRemote.retrievePackageByAmount(amount);
                //validate if package with the same amount has been created
                System.out.println("This Package has been created. Please Try again! ");
                System.out.println("1: continue ");
                System.out.println("2: break ");
                int ans = scanner.nextInt();
                scanner.nextLine();
                if (ans == 2) {
                    break;
                }
            } catch (PackageNotFoundException ex) {
                newCreditPackage.setAmount(amount);
                System.out.print("Enter Package Name> ");
                newCreditPackage.setPackageName(scanner.nextLine().trim());

                newCreditPackage.setSoldAmount(0);
                newCreditPackage.setStatus(Boolean.TRUE);
                List<TopUpTransaction> topUpList = new ArrayList<>();
                newCreditPackage.setTransactionList(topUpList);

                newCreditPackage = creditPackageEntityControllerRemote.persistNewCreditPackage(newCreditPackage);
                System.out.println("New Credit Package" + newCreditPackage.getPackageName() + " with value " + newCreditPackage.getAmount() + " created successfully!: \n");
                break;
            }
        }
    }

    public void viewCreditPackageDetails() { //view details of the credit package
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: System Finance :: View Credit Package Details ***\n");
        System.out.print("Enter Credit Package Amount> ");
        BigDecimal amount = scanner.nextBigDecimal();

        try {
            CreditPackage c = creditPackageEntityControllerRemote.retrievePackageByAmount(amount);
            System.out.printf("%10s%20s%25s%25s%16s\n", "Package ID", "Package Name", "Package amount", "Package sold", "Status");
            System.out.printf("%5s%25s%20s%25s%20s\n", c.getId(), c.getPackageName(), c.getAmount().toString(), c.getSoldAmount(), c.getStatus().toString());
            System.out.println("---------------------------------------------------------------------------------------------------\n");
            System.out.println("1: Update Credt Package");
            System.out.println("2: Delete Credit Package");
            System.out.println("3: Back\n");
            System.out.print("> ");

            while (response < 1 || response > 3) {
                response = scanner.nextInt();
                if (response == 1) {
                    doUpdateCreditPackage(c);
                } else if (response == 2) {
                    doDeleteCreditPackage(c);
                } else if (response == 3) {

                } else {
                    System.out.println("Option is invalid, please try again!");
                }
            }
        } catch (PackageNotFoundException ex) {
            System.out.println("An error has occurred while retrieving credit package: " + ex.getMessage() + "\n");
        }
    }

    public void viewAllCreditPackages() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid :: Finance System  :: View All Credit Packages ***\n");

        List<CreditPackage> cList = creditPackageEntityControllerRemote.retrieveAllCreditPackages();
        System.out.printf("%10s%20s%25s%25s%16s\n", "Package ID", "Package Name", "Package Amount", "Package Sold", "Status");

        for (CreditPackage c : cList) {
            System.out.printf("%5s%25s%20s%25s%20s\n", c.getId(), c.getPackageName(), c.getAmount().toString(), c.getSoldAmount(), c.getStatus().toString());
        }

        System.out.println("---------------------------------------------------------------------------------------------------\n");
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    public void doUpdateCreditPackage(CreditPackage c) { //update credit package details
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Crazy Bid :: System Finance :: View Credit Package Details :: Update Package ***\n");
        System.out.print("Enter Package Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            c.setPackageName(input);
        }
        while (true) {
            System.out.print("Select Status (0: No Change, 1: ACTIVE, 2: INACTIVE)> ");
            Integer status = scanner.nextInt();

            if (status == 0) {
                break;
            } else if (status == 1) {
                c.setStatus(Boolean.TRUE);
                break;
            } else if (status == 2) {
                c.setStatus(Boolean.FALSE);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        creditPackageEntityControllerRemote.updateCreditPackage(c);
        System.out.println("Credit Package has been updated successfully!\n");
    }

    public void doDeleteCreditPackage(CreditPackage c) {
        Scanner scanner = new Scanner(System.in);

        if (c.getSoldAmount() > 0) { //if customers have purchased this package, staff will not be able to delete package but only DISABLE
            System.out.println("This credit package has been purchased and therefore cannot be deleted!\n");
        } else {
            String input;
            System.out.println("*** Crazy Bid :: System Finance :: View Credit package Details :: Delete Package ***\n");
            System.out.printf("Confirm Delete Package %s (Package ID: %s) (Enter 'Y' to Delete)> ", c.getPackageName(), c.getAmount().toString());
            input = scanner.nextLine().trim();

            if (input.equals("Y") || input.equals("y")) {
                c.setStatus(false);
                creditPackageEntityControllerRemote.updateCreditPackage(c);
                System.out.println("Credit package has been disabled successfully!\n");
            } else {
                System.out.println("Package NOT disabled!\n");
            }
        }
    }

}
