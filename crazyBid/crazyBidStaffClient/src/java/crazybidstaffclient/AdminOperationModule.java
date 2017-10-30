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
public class AdminOperationModule {

    private Staff currentStaff;

    public AdminOperationModule() {
    }

    public AdminOperationModule(Staff currentStaff) {
        this.currentStaff = currentStaff;
    }

    public void menuAdminOperation() throws InvalidAccessRightException {
        if (currentStaff.getAccessRight() != StaffAccessRight.ADMIN) {
            throw new InvalidAccessRightException("You don't have ADMIN rights to access the system administration module.");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Bid : Admin Operation ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View Employee Details");
//            System.out.println("3: Update Employee");
//            System.out.println("4: Delete Employee");
            System.out.println("3: View All Employees");
            System.out.println("-----------------------");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 4) {
                    break;
                } else if (response == 1) {
                    createNewEmployee();
                } else if (response == 2) {
                    viewEmployeeDetails();
                } else if (response == 3) {
                    viewAllEmployees();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 4) {
                break;
            }
        }
    }
    
    public void createNewEmployee(){}
    public void viewEmployeeDetails(){}
    public void viewAllEmployees(){}
}
