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

    @EJB
    private static CustomerEntityControllerRemote customerEntityController;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MainApp mainApp = new MainApp(customerEntityController);
        mainApp.runApp();  
    }
    
}
