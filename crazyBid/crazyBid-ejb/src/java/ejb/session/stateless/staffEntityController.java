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
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(StaffEntityControllerLocal.class)
@Remote(StaffEntityControllerRemote.class)

public class StaffEntityController implements StaffEntityControllerRemote, StaffEntityControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Staff persistNewStaff(Staff s) { 
        em.persist(s);
        em.flush();
        em.refresh(s);

        return s;
    }

    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException { //retrieve by username

        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Staff) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public Staff retrieveStaffByStaffId(Long id) throws StaffNotFoundException { //retrieve by ID
        Staff s = em.find(Staff.class, id);

        if (s != null) {
            return s;
        } else {
            throw new StaffNotFoundException("Staff ID " + id + " does not exist!");
        }
    }

    @Override
    public List<Staff> retrieveAllStaffs(){ //retrieve every staff
        Query query = em.createQuery("SELECT s FROM Staff s");
        return query.getResultList();
    }
    
    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException { //to log in
        try {
            Staff s = retrieveStaffByUsername(username);
            if (s.getPassword().equals(password)) {
                return s;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateStaff(Staff s) { //update staff 
        em.merge(s);
    }

    @Override
    public void deleteStaff(Long id) throws StaffNotFoundException { //to delete staff
        Staff sToRemove = retrieveStaffByStaffId(id);
        if (sToRemove != null) {
            em.remove(sToRemove);
        } else {
            throw new StaffNotFoundException("Staff ID " + id + " does not exist!");
        }
        
        
    }


}
