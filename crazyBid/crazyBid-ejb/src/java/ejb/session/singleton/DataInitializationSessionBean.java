/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;


import Entity.Staff;
import ejb.session.stateless.staffEntityControllerLocal;
import enumeration.StaffAccessRight;
import exception.StaffNotFoundException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;


/**
 *
 * @author User
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private staffEntityControllerLocal staffEntityControllerLocal;

    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public DataInitializationSessionBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            staffEntityControllerLocal.retrieveStaffByUsername("manager");
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        }
    }
    
    private void initializeData(){
        staffEntityControllerLocal.persistNewStaff(new Staff("eric","xu","manager","password",StaffAccessRight.ADMIN));
    }
   
}