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
public class FinanceStaffOperationModule {

    private Staff currentStaff;

    public FinanceStaffOperationModule() {
    }

    public FinanceStaffOperationModule(Staff currentStaff) {
        this.currentStaff = currentStaff;
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
            System.out.println("-----------------------");
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
    
    public void createCreditPackage(){}
    public void viewCreditPackageDetails(){}
    public void viewAllCreditPackages(){}
    
    
}
