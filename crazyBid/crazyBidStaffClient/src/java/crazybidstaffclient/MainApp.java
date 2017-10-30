/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazybidstaffclient;

import Entity.Staff;
import ejb.session.stateless.staffEntityControllerRemote;
import exception.StaffNotFoundException;


/**
 *
 * @author User
 */
public class MainApp {
    private staffEntityControllerRemote staffEntityControllerRemote;
    
    public MainApp() {
    }

    public MainApp(staffEntityControllerRemote staffEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
    }
    
    public void runApp() throws StaffNotFoundException{
        Staff s = new Staff();
        s = staffEntityControllerRemote.retrieveStaffByUsername("manager");
        
        System.out.println(s.getStaffId() + " has been created!");
           
    }
    
    
}
