/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;


import ejb.session.stateless.AuctionListingEntityControllerRemote;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import exception.StaffNotFoundException;
import javax.ejb.EJB;

/**
 *
 * @author User
 */
public class Main {

    @EJB
    private static AuctionListingEntityControllerRemote auctionListingEntityControllerRemote;

    @EJB
    private static StaffEntityControllerRemote staffEntityControllerRemote;

    @EJB
    private static CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;

    
   
    

    
    
    /**
     * @param args the command line arguments
     * @throws exception.StaffNotFoundException
     */
    public static void main(String[] args) throws StaffNotFoundException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(staffEntityControllerRemote, creditPackageEntityControllerRemote, auctionListingEntityControllerRemote);
         
        mainApp.runApp();  
    }
    
}
