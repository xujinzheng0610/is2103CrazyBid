/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Staff;
import exception.InvalidLoginCredentialException;
import exception.StaffNotFoundException;
import java.util.List;

public interface StaffEntityControllerLocal {

    public Staff persistNewStaff(Staff s);

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException;

    public Staff retrieveStaffByStaffId(Long id) throws StaffNotFoundException;

    public void updateStaff(Staff s);

    public void deleteStaff(Long id) throws StaffNotFoundException ;

    public List<Staff> retrieveAllStaffs();
    
}
