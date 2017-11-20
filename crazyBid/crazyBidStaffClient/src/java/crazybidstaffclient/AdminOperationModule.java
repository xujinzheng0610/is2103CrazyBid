/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.AuctionListing;
import Entity.Staff;
import enumeration.StaffAccessRight;
import exception.InvalidAccessRightException;
import exception.StaffNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ejb.session.stateless.StaffEntityControllerRemote;

/**
 *
 * @author User
 */
public class AdminOperationModule {

    private Staff currentStaff;

    private StaffEntityControllerRemote staffEntityControllerRemote;

    public AdminOperationModule() {
    }

    public AdminOperationModule(Staff currentStaff, StaffEntityControllerRemote staffEntityControllerRemote) {
        this.currentStaff = currentStaff;
        this.staffEntityControllerRemote = staffEntityControllerRemote;
    }

    public void menuAdminOperation() throws InvalidAccessRightException, StaffNotFoundException {
        if (currentStaff.getAccessRight() != StaffAccessRight.ADMIN) {
            throw new InvalidAccessRightException("You don't have ADMIN rights to access the administrator system!");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Bid : Administrator System ***\n");
            System.out.println("1: Create New Staff");
            System.out.println("2: View Employee Details");
            System.out.println("3: View All Employees");
            System.out.println("4: Back\n");
            System.out.println("-----------------------");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 4) {
                    break;
                } else if (response == 1) {
                    createNewStaff();
                } else if (response == 2) {
                    viewStaffDetails();
                } else if (response == 3) {
                    viewAllStaffs();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 4) {
                break;
            }
        }
    }

    public void createNewStaff() throws StaffNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Staff newStaff = new Staff();

        System.out.println("*** Crazy Bid:: Administrator System :: Create New Staff ***\n");

        while (true) {
            System.out.print("Enter Username> ");
            String username = scanner.nextLine().trim();
            while (username.length() == 0) { //validate empty input
                System.out.println("Please enter a username!");
                System.out.print("Enter Username> ");
                username = scanner.nextLine().trim();
            }
            try {
                staffEntityControllerRemote.retrieveStaffByUsername(username);
                System.out.print("This Username has been used! Please Try again!");
                System.out.println("1: Retry ");
                System.out.println("2: Back ");
                int ans = scanner.nextInt();
                scanner.nextLine();
                if (ans == 2) {
                    break;
                }
            } catch (StaffNotFoundException ex) {
                newStaff.setUserName(username);
                System.out.print("Enter First Name> ");
                newStaff.setFirstName(scanner.nextLine().trim());
                System.out.print("Enter Last Name> ");
                newStaff.setLastName(scanner.nextLine().trim());
                System.out.print("Enter Password> ");
                String password = scanner.nextLine().trim();
                while (password.length() == 0) {
                    System.out.println("Please enter a password!");
                    System.out.print("Enter password> ");
                    password = scanner.nextLine().trim();
                }
                newStaff.setPassword(password);
                
                while (true) {
                    System.out.print("Select Access Right (1: ADMIN, 2: FINANCE, 3:SALES)> ");
                    Integer accessRightInt = scanner.nextInt();
                    scanner.nextLine();
                    if (accessRightInt >= 1 && accessRightInt <= 3) {
                        newStaff.setAccessRight(StaffAccessRight.values()[accessRightInt - 1]);
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
                newStaff = staffEntityControllerRemote.persistNewStaff(newStaff);
                System.out.println("New " + newStaff.getAccessRight() + " Staff " + newStaff.getUserName() + " created successfully!: \n");
                break;
            }
        }
    }

    public void viewStaffDetails() { //to view details of staff
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: Administrator System :: View Staff Details ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();

        try {
            Staff s = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%20s%20s%20s%15s%20s\n", "Staff ID", "First Name", "Last Name", "Access Right", "Username", "Password");
            System.out.printf("%8s%20s%20s%15s%20s%20s\n", s.getStaffId().toString(), s.getFirstName(), s.getLastName(), s.getAccessRight().toString(), s.getUserName(), s.getPassword());
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1: Update Staff");
            System.out.println("2: Delete Staff");
            System.out.println("3: Back\n");
            System.out.print("> ");

            while (response < 1 || response > 3) {
                response = scanner.nextInt();
                if (response == 1) {
                    doUpdateStaff(s);
                } else if (response == 2) {
                    doDeleteStaff(s);
                } else if (response == 3) {

                } else {
                    System.out.println("Option is invalid, please try again!");
                }
            }
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }
    }

    public void viewAllStaffs() { //to view all details of all staff
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid :: Administrator System :: View All Staffs ***\n");

        List<Staff> sList = staffEntityControllerRemote.retrieveAllStaffs();
        System.out.printf("%8s%20s%20s%15s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Access Right", "Username", "Password");

        for (Staff s : sList) {
            System.out.printf("%8s%20s%20s%15s%20s%20s\n", s.getStaffId().toString(), s.getFirstName(), s.getLastName(), s.getAccessRight().toString(), s.getUserName(), s.getPassword());
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    public void doUpdateStaff(Staff s) { //to update staff details
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Crazy Bid :: Administrator System :: View Staff Details :: Update Staff ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            s.setFirstName(input);
        }

        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            s.setLastName(input);
        }

        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            s.setUserName(input);
        }

        while (true) {
            System.out.print("Select Access Right (0: No Change, 1: ADMIN, 2: FINANCE  3:SALES)> ");
            Integer accessRightInt = scanner.nextInt();

            if (accessRightInt >= 1 && accessRightInt <= 2) {
                s.setAccessRight(StaffAccessRight.values()[accessRightInt - 1]);
                break;
            } else if (accessRightInt == 0) {
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        scanner.nextLine();

        System.out.print("Enter Password (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            s.setPassword(input);
        }

        staffEntityControllerRemote.updateStaff(s);
        System.out.println("Staff updated successfully!\n");
    }

    public void doDeleteStaff(Staff s) { //to delete staff
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Crazy Bid :: Administrator System :: View Staff Details :: Delete Staff ***\n");
        System.out.printf("Confirm Delete Staff %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", s.getFirstName(), s.getLastName(), s.getStaffId());
        input = scanner.nextLine().trim();

        if (input.equals("Y") || input.equals("y")) {
            try {
                staffEntityControllerRemote.deleteStaff(s.getStaffId());
                System.out.println("Staff deleted successfully!\n");
            } catch (StaffNotFoundException ex) {
                System.out.println("An error has occurred while deleting staff: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Staff NOT deleted!\n");
        }
    }

}
