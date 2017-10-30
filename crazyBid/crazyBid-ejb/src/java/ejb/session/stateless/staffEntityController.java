/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Staff;
import exception.InvalidLoginCredentialException;
import exception.StaffNotFoundException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local( staffEntityControllerLocal.class)
@Remote( staffEntityControllerRemote.class)

public class staffEntityController implements staffEntityControllerRemote, staffEntityControllerLocal {

    @PersistenceContext(unitName = "crazyBid-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Staff persistNewStaff(Staff s)
    {
        em.persist(s);
        em.flush();
        em.refresh(s);
        
        return s;
    }
    
    @Override
    public Staff retrieveStaffByUsername (String username) throws StaffNotFoundException{

        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (Staff)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }
    
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException{
        try{
            Staff s = retrieveStaffByUsername(username);
            if(s.getPassword().equals(password)){
                return s;
            }
            else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
}
