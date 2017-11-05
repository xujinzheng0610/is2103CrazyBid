/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.Staff;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import exception.InvalidAccessRightException;
import exception.InvalidLoginCredentialException;
import exception.StaffNotFoundException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class MainApp {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;
    
    private AdminOperationModule adminOperationModule;
    private SalesStaffOperationModule salesStaffOperationModule;
    private FinanceStaffOperationModule financeStaffOperationModule;

    private Staff currentStaff;

    public MainApp() {
    }

    public MainApp(StaffEntityControllerRemote staffEntityControllerRemote, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
    }

    
    

    public void runApp() throws StaffNotFoundException {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** Welcome to Retail Core Banking System ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        adminOperationModule = new AdminOperationModule(currentStaff, staffEntityControllerRemote);
                        salesStaffOperationModule = new SalesStaffOperationModule(currentStaff);
                        financeStaffOperationModule = new FinanceStaffOperationModule(currentStaff,creditPackageEntityControllerRemote);

                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 2) {
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
                currentStaff = staffEntityControllerRemote.staffLogin(username, password);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                throw new InvalidLoginCredentialException();
            }
        } else {
            System.out.println("Invalid login credential!");
        }
    }

    public void menuMain() throws StaffNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("*** Retail Core Banking System  ***\n");
            System.out.println("You are login as " + currentStaff.getFirstName() + " " + currentStaff.getLastName() + " with " + currentStaff.getAccessRight().toString() + " rights\n");
            System.out.println("1: Admin Related");
            System.out.println("2: Sales Related");
            System.out.println("3: Finance Related");
            System.out.println("4: Change Password");
            System.out.println("5: Logout\n");

            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        adminOperationModule.menuAdminOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }

                } else if (response == 2) {
                    try {
                        salesStaffOperationModule.menuSalesStaffOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    try {
                        financeStaffOperationModule.menuFinanceStaffOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 4){
                    changePassword(currentStaff);
                }
                if (response == 5) {
                    break;
                }
            }
            if (response == 5) {
                    break;
            }
            
        }
    }
    
    public void changePassword( Staff currentStaff){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Crazy Bid:: Main Menu:: Change Password ***\n");
        while(true){
            System.out.print("Enter current password> ");
            if(scanner.nextLine().trim().equals(currentStaff.getPassword())){
                while(true){
                    System.out.print("Enter new password> ");
                    String newPassword = scanner.nextLine().trim();
                    System.out.print("Confirm new password> ");
                    if(scanner.nextLine().trim().equals(newPassword)){
                        currentStaff.setPassword(newPassword);
                        staffEntityControllerRemote.updateStaff(currentStaff);
                        System.out.println("You have successfully changed your password to "+ currentStaff.getPassword());
                        break;
                    }else{
                        System.out.println("Passwords are inconsistent! Please try again!> ");
                        System.out.println("1: continue ");
                        System.out.println("2: break ");
                        int ans = scanner.nextInt();
                        scanner.nextLine();
                        if(ans == 2)
                            break;
                    }
                }
                break;
            }else{
                System.out.println("Password is incorrect! Please try again!");
                System.out.println("1: continue ");
                System.out.println("2: break ");
                int ans = scanner.nextInt();
                scanner.nextLine();
                if(ans == 2)
                    break;
            }
        }
    }
}

    
    

