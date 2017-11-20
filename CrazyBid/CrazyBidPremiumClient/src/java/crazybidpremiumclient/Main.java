/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidpremiumclient;

import ejb.session.stateless.AddressEntityControllerRemote;
import ejb.session.stateless.AuctionListingEntityControllerRemote;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.TopUpTransactionControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author User
 */
public class Main {


    @EJB
    private static CustomerEntityControllerRemote customerEntityControllerRemote;



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(customerEntityControllerRemote);
        mainApp.runApp();
    }

}
