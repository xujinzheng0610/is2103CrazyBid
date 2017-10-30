/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Staff;
import exception.StaffNotFoundException;

public interface staffEntityControllerLocal {

    public Staff persistNewStaff(Staff s);

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;
    
}
