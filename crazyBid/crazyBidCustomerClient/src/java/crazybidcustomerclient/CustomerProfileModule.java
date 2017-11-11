/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import Entity.Address;
import Entity.AuctionListing;
import Entity.Customer;
import ejb.session.stateless.AddressEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import exception.AddressNotFoundException;
import exception.CustomerNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chester
 */
public class CustomerProfileModule {

    private Customer currentCustomer;
    private CustomerEntityControllerRemote customerEntityControllerRemote;
    private AddressEntityControllerRemote addressEntityControllerRemote;

    public CustomerProfileModule() {
    }

    public CustomerProfileModule(Customer currentCustomer, CustomerEntityControllerRemote customerEntityControllerRemote, AddressEntityControllerRemote addressEntityControllerRemote) {
        this.currentCustomer = currentCustomer;
        this.customerEntityControllerRemote = customerEntityControllerRemote;
        this.addressEntityControllerRemote = addressEntityControllerRemote;
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
            System.out.println("5: View All Addresses");
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doViewCustomerProfile();
                } else if (response == 2) {
                    doUpdateProfile(currentCustomer);
                } else if (response == 3) {
                    doCreateNewAddress();
                } else if (response == 4) {
                    doViewAddressDetails();
                } else if (response == 5) {
                    doViewAllAddresses();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 6) {
                break;
            }
        }
    }

    private void doViewCustomerProfile() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: View Customer Profile ***\n");
        System.out.print("Enter username > ");
        String username = currentCustomer.getUserName();

        try {
            customerEntityControllerRemote.retrieveCustomerByUsername(username);
            System.out.printf("%8s%15s%15s%15s%15s%20s%20s%20s\n", "First Name", "Last Name", "Username", "Password", "Email", "Phone Number", "Credit Balance", "Premium Status");
            System.out.printf("%22s%20s%12s%18s%15s%15s%18s%22s\n", currentCustomer.getFirstName(), currentCustomer.getLastName(), currentCustomer.getUserName(), currentCustomer.getPassword(), currentCustomer.getEmail(), currentCustomer.getPhoneNumber(), currentCustomer.getCreditBalance(), currentCustomer.getPremium());
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1: Update Profile");
            System.out.println("2: Back\n");
            System.out.print("> ");

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doUpdateProfile(currentCustomer);
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

    public void doCreateNewAddress() {
        Scanner scanner = new Scanner(System.in);
        Address newAddress = new Address();

        System.out.println("*** CrazyBid :: Add New Address ***\n");


        System.out.print("Enter Street Address> ");
        newAddress.setStreetAddress(scanner.nextLine().trim());

        System.out.print("Enter Country> ");
        newAddress.setCountry(scanner.nextLine().trim());

        System.out.print("Enter City> ");
        newAddress.setCity(scanner.nextLine().trim());

        System.out.print("Enter Postal Code> ");
        newAddress.setPostalCode(scanner.nextLine().trim());

        System.out.print("Enter Phone Number> ");
        newAddress.setPhoneNumber(scanner.nextLine().trim());

        newAddress.setCustomer(currentCustomer);
        newAddress.setStatus(Boolean.TRUE);

        List<AuctionListing> productList = new ArrayList<>();
        newAddress.setProductList(productList);

        addressEntityControllerRemote.createNewAddress(newAddress);

        System.out.println("Address added successfully!:\n");
    }

    public void doViewAddressDetails() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: View Address Details ***\n");
        System.out.print("Please key in address id> ");
        Long addressId = scanner.nextLong();

        try {
            Address a = addressEntityControllerRemote.retrieveAddressByAddressId(addressId);
            System.out.printf("%8s%20s%20s%20s%20s%20s%5s\n", "Address Id", "Street Address", "Country", "City", "Postal Code", "Phone Number", "Status");
            System.out.printf("%8s%20s%20s%20s%20s%20s%5s\n", a.getId(), a.getStreetAddress(), a.getCountry(), a.getCity(), a.getPostalCode(), a.getPhoneNumber(), a.getStatus().toString());
            System.out.println("\n------------------------");
            System.out.println("1: Update Address");
            System.out.println("2: Delete Address");
            System.out.println("3: Back\n");
            while (response < 1 || response > 3) {

                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doUpdateAddress(a);
                } else if (response == 2) {
                    doDeleteAddress(a);
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

        } catch (AddressNotFoundException ex) {
            System.out.println("Customer is not found!");
        }

    }

    public void doUpdateAddress(Address address) {

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** CrazyBid :: Update Address ***\n");


        System.out.print("Enter Street Address (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setStreetAddress(input);
        }

        System.out.print("Enter Country (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setCountry(input);
        }

        System.out.print("Enter City (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setCity(input);
        }

        System.out.print("Enter Postal Code (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setPostalCode(input);
        }

        System.out.print("Enter Phone Number (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setPhoneNumber(input);
        }

        addressEntityControllerRemote.updateAddress(address);
        System.out.println("Address updated successfully!\n");

    }

    public void doDeleteAddress(Address a) throws AddressNotFoundException { //must disassociate with winning bid, and marked as disabled, customer cannot use in th future
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Crazy Bid :: Delete Address ***\n");
        System.out.printf("Confirm Delete Address %s (Enter 'Y' to Delete)> ", a.getId());
        input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                addressEntityControllerRemote.deleteAddress(a.getId());
                System.out.println("Staff deleted successfully!\n");
            } catch (AddressNotFoundException ex) {
                System.out.println("An error has occurred while deleting Address: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Address NOT deleted!\n");
        }
    }

    public void doViewAllAddresses() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid :: View All Addresses ***\n");

        List<Address> addressList = addressEntityControllerRemote.retrieveAllAddresses();
        System.out.printf("%8s%20s%20s%20s%20s%20s%5s\n", "Address Id", "Street Address", "Country", "City", "Postal Code", "Phone Number", "Status");

        for (Address a : addressList) {
            System.out.printf("%8s%20s%20s%20s%20s%20s%5s\n", a.getId(), a.getStreetAddress(), a.getCountry(), a.getCity(), a.getPostalCode(), a.getPhoneNumber(), a.getStatus().toString());
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
}
