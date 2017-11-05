/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidcustomerclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author Chester
 */
public class Main {

    @EJB(name = "CustomerEntityControllerRemote")
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
