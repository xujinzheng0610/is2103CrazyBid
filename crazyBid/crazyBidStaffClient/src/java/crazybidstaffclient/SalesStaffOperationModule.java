/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.AuctionListing;
import Entity.Bid;
import Entity.Staff;
import ejb.session.stateless.AuctionListingEntityControllerRemote;
import enumeration.StaffAccessRight;
import exception.AuctionListingNotFoundException;
import exception.InvalidAccessRightException;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class SalesStaffOperationModule {

    private Staff currentStaff;

    private AuctionListingEntityControllerRemote auctionListingEntityControllerRemote;

    public SalesStaffOperationModule() {
    }

    public SalesStaffOperationModule(Staff currentStaff, AuctionListingEntityControllerRemote auctionListingEntityControllerRemote) {
        this.currentStaff = currentStaff;
        this.auctionListingEntityControllerRemote = auctionListingEntityControllerRemote;
    }

    public void menuSalesStaffOperation() throws InvalidAccessRightException {
        if (currentStaff.getAccessRight() != StaffAccessRight.SALES) {
            throw new InvalidAccessRightException("You don't have SALES rights to access the sales system!");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Bid : Sales System ***\n");
            System.out.println("1: Create Auction Listing");
            System.out.println("2: View Auction Listing Details");
//            System.out.println("3: Update Auction Listing");
//            System.out.println("4: Delete Auction Listing");
            System.out.println("3: View All Auction Listings");
            System.out.println("4: View All Auction Listings with bids but below reserve price");
            System.out.println("5: Back\n");
            System.out.println("---------------------------------------------------------------");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 5) {
                    break;
                } else if (response == 1) {
                    createAuctionListing();
                } else if (response == 2) {
                    viewAuctionListingDetails();
                } else if (response == 3) {
                    viewAuctionListings();
                } else if (response == 4) {
                    viewAuctionListingsBelow();
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }

    public void createAuctionListing() {
        Scanner scanner = new Scanner(System.in);
        AuctionListing newA = new AuctionListing();

        System.out.println("*** Crazy Bid:: Sales System :: Create New Auction Listing ***\n");

        System.out.print("Enter Product Name> ");
        newA.setProduct(scanner.nextLine().trim());
        System.out.print("Enter Product Description> ");
        newA.setProductDescription(scanner.nextLine().trim());
        System.out.print("Enter Starting Price> ");
        newA.setStartingPrice(scanner.nextBigDecimal());
        scanner.nextLine();
        System.out.print("Enter Expected Price> ");
        // should check the expected price, which should be equal or bigger than start price
        newA.setExpectedPrice(scanner.nextBigDecimal());
        scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (true) {
            System.out.print("Enter Start Time in format (\"yyyy-MM-dd HH:mm:00\")>" );
            String startDateStr = scanner.nextLine().trim();
            ParsePosition pos = new ParsePosition(0);
            Date startDate = formatter.parse(startDateStr, pos);
            if (startDate == null) {
                System.out.println("the date format is invalid, please try again!");
            } else if (startDate.compareTo(new Date()) <= 0) {
                System.out.println("the start date should be a future time, please try again!");
            } else {
                newA.setStartDate(startDate);
                break;
            }
        }
        while (true) {
            System.out.print("Enter End Time in format (\"yyyy-MM-dd HH:mm:00\")> ");
            String endDateStr = scanner.nextLine().trim();
            ParsePosition pos = new ParsePosition(0);
            Date endDate = formatter.parse(endDateStr, pos);
            if (endDate == null) {
                System.out.println("the date format is invalid, please try again!");
            } else if (endDate.compareTo(newA.getStartDate()) <= 0) {
                System.out.println("the end date is before start date, please try again!");
            } else {
                newA.setEndDate(endDate);
                break;
            }
        }

        List<Bid> bidList = new ArrayList<>();
        newA.setBidList(bidList);
        newA.setStatus(Boolean.TRUE);
        newA.setAddress(null);
        newA.setOwner(null);
        newA = auctionListingEntityControllerRemote.persistNewAuctionListing(newA);

        System.out.println("New Product - " + newA.getProduct() + " with ID " + newA.getId() + " created successfully!: \n");

    }

    public void viewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** CrazyBid :: Sales System :: View Auction Listing Details ***\n");
        System.out.print("Enter Auction Listing ID> ");
        Long id = scanner.nextLong();

        try {
            AuctionListing a = auctionListingEntityControllerRemote.retrieveAuctionListingById(id);
            System.out.printf("%5s%20s%25s%25s%30s%33s%30s\n", "Product ID", "Product Name", "Starting Price", "Expected Price", "Start time", "End time", "Status");
            System.out.printf("%5s%23s%22s%25s%43s%36s%18s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(), a.getExpectedPrice().toString(), a.getStartDate(), a.getEndDate(), a.getStatus().toString());
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (response < 1 || response > 3) {
                System.out.println("1: Update Auction Listing");
                System.out.println("2: Delete Auction Listing");
                System.out.println("3: Back\n");
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doUpdateAuctionListing(a);
                } else if (response == 2) {
                    if (a.getStatus()) {
                        a.getBidList();
                        doDeleteAuctionListing(a);
                    } else {
                        System.out.println("This auction listing is disabled already!");
                    }
                } else if (response == 3) {

                } else {
                    System.out.println("Option is invalid, please try again!");
                }
            }
        } catch (AuctionListingNotFoundException ex) {
            System.out.println("An error has occurred while retrieving auction listing: " + ex.getMessage() + "\n");
        }
    }

    public void viewAuctionListings() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Crazy Bid :: Sales System :: View All Auction Listings ***\n");

        List<AuctionListing> aList = auctionListingEntityControllerRemote.retrieveAllAuctionListings();
        System.out.printf("%5s%20s%25s%25s%30s%33s%30s\n", "Product ID", "Product Name", "Starting Price", "Expected Price", "Start time", "End time", "Status");

        for (AuctionListing a : aList) {
            System.out.printf("%5s%23s%22s%25s%43s%36s%18s\n", a.getId(), a.getProduct(), a.getStartingPrice().toString(),
                    a.getExpectedPrice().toString(), a.getStartDate(), a.getEndDate(), a.getStatus().toString());
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    public void viewAuctionListingsBelow() {
        Scanner scanner = new Scanner(System.in);
        List<AuctionListing> aList = auctionListingEntityControllerRemote.retrieveAuctionListingsBelowExpectedPrice();
        System.out.printf("%5s%15s%25s%25s\n", "Product ID", "Product Name", "Expected Price", "Current Price");

        for (AuctionListing a : aList) {
            List<Bid> bidList = a.getBidList();
            Bid b = bidList.get(bidList.size() - 1);
            System.out.printf("%5s%15s%25s%25s\n", a.getId(), a.getProduct(), a.getExpectedPrice().toString(), b.getBidAmount().toString());
        }
        Integer response = 0;
        while (response < 1 || response > 2) {
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("1: Assign Auction Listing");
            System.out.println("2: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();
            if (response == 1) {
                doAssignAuctionListing();
            } else if (response == 2) {

            } else {
                System.out.println("Option is invalid, please try again!");
            }
        }

    }

    public void doUpdateAuctionListing(AuctionListing a) {
        Scanner scanner = new Scanner(System.in);
        if (a.getStartDate().compareTo(new Date()) <= 0) {
            System.out.println("This product has started bidding, cannot be edited!");
        } else {
            String input;
            System.out.println("*** Crazy Bid :: System Sales :: View Auction Listing Details :: Update Product ***\n");
            System.out.print("Enter Product Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                a.setProduct(input);
            }
            System.out.print("Enter Product Description (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                a.setProductDescription(input);
            }
            System.out.print("Enter Starting Price (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                BigDecimal b = new BigDecimal(input);
                a.setStartingPrice(b);
            }
            System.out.print("Enter Expected Price (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                BigDecimal b = new BigDecimal(input);
                a.setExpectedPrice(b);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (true) {
                System.out.print("Enter Start Time in format (\"yyyy-MM-dd HH:mm:00\")(blank if no change)> ");
                String startDateStr = scanner.nextLine().trim();

                if (startDateStr.length() > 0) {
                    ParsePosition pos = new ParsePosition(0);
                    Date startDate = formatter.parse(startDateStr, pos);
                    if (startDate == null) {
                        System.out.println("the date format is invalid, please try again!");
                    } else if (startDate.compareTo(new Date()) <= 0) {
                        System.out.println("the start date should be a future time, please try again!");
                    } else {
                        a.setStartDate(startDate);
                        break;
                    }
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Enter End Time in format (\"yyyy-MM-dd HH:mm:00\")(blank if no change)> ");
                String endDateStr = scanner.nextLine().trim();

                if (endDateStr.length() > 0) {
                    ParsePosition pos = new ParsePosition(0);
                    Date endDate = formatter.parse(endDateStr, pos);
                    if (endDate == null) {
                        System.out.println("the date format is invalid, please try again!");
                    } else if (endDate.compareTo(a.getStartDate()) <= 0) {
                        System.out.println("the end date is before start date, please try again!");
                    } else {
                        a.setEndDate(endDate);
                        break;
                    }
                } else {
                    break;
                }
            }
            auctionListingEntityControllerRemote.doUpdateAuctionListing(a);
            System.out.println(a.getProduct() + " has been updated successfully!");

        }
    }

    public void doDeleteAuctionListing(AuctionListing a) throws AuctionListingNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("*** Crazy Bid :: Sales System :: View Auction Listing Details :: Delete Auction Listing ***\n");
        System.out.printf("Confirm Delete Auction Listing %s (Package ID: %s) (Enter 'Y' to Delete)> ", a.getProduct(), a.getId());
        input = scanner.nextLine().trim();
        if (input.equals("Y") || input.equals("y")) {
            if (a.getBidList().isEmpty()) {
                auctionListingEntityControllerRemote.deleteAuctionListing(a.getId());
                System.out.println("Auction Listing has been deleted successfully!\n");
            } else {
                auctionListingEntityControllerRemote.disableAuctionListing(a.getId());
                System.out.println("Auction Listing has been disabled successfully!\n");
            }
        } else {
            System.out.println("Auction Listing is NOT disabled!\n");
        }

    }

    public void doAssignAuctionListing() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Crazy Bid :: Sales System :: View Auction Listing Below Expected Price :: Assign Bid ***\n");
        System.out.print("Please enter the product ID> ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        try {
            AuctionListing a = auctionListingEntityControllerRemote.retrieveAuctionListingById(id);
            System.out.print("Confirm Assign Product -" + a.getProduct()+ "(Product ID: "+a.getId()+") (Enter 'Y' to Confirm)> " );
            String input = scanner.nextLine().trim();
            if (input.equals("Y")) {
                a.getBidList();
                auctionListingEntityControllerRemote.assignOwnerManully(a.getId());
            } else {
                System.out.println("Auction Listing NOT assigned!\n");
            }
        } catch (AuctionListingNotFoundException ex) {
            System.out.println("Error is occuring with info: " + ex.getMessage() + " !");
        }
    }
}
