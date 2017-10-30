/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.Staff;
import ejb.session.stateless.staffEntityControllerRemote;
import enumeration.StaffAccessRight;
import exception.StaffNotFoundException;
import javax.ejb.EJB;

/**
 *
 * @author User
 */
public class Main {

    @EJB
    private static staffEntityControllerRemote staffEntityControllerRemote;

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws StaffNotFoundException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(staffEntityControllerRemote);
         
        mainApp.runApp();  
    }
    
}
