/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import exception.PackageNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
@Local(CreditPackageEntityControllerLocal.class)
@Remote(CreditPackageEntityControllerRemote.class)
public class CreditPackageEntityController implements CreditPackageEntityControllerRemote, CreditPackageEntityControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public CreditPackage retrievePackageByAmount(BigDecimal amount) throws PackageNotFoundException {
        Query query = em.createQuery("SELECT c FROM CreditPackage c WHERE c.amount = :inAmount");
        query.setParameter("inAmount", amount);

        try {
            return (CreditPackage) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PackageNotFoundException("Credit Package with value " + amount + " does not exist!");
        }
    }

    @Override
    public CreditPackage retrieveCreditPackageById(Long id) throws PackageNotFoundException {
        CreditPackage c = em.find(CreditPackage.class, id);

        if (c != null) {
            return c;
        } else {
            throw new PackageNotFoundException(" Credit Package ID " + id + " does not exist!");
        }
    }

    @Override
    public CreditPackage persistNewCreditPackage(CreditPackage c) {
        em.persist(c);
        em.flush();
        em.refresh(c);
        return c;
    }

    @Override
    public List<CreditPackage> retrieveAllCreditPackages() {
        Query query = em.createQuery("SELECT c FROM CreditPackage c");
        return query.getResultList();
    }

    @Override
    public void deleteCreditPackage(Long id) throws PackageNotFoundException {
        CreditPackage c = retrieveCreditPackageById(id);
        if (c != null) {
            em.remove(c);
        } else {
            throw new PackageNotFoundException(" Credit Package ID " + id + " does not exist!");
        }
    }
    
    @Override
    public void updateCreditPackage(CreditPackage c){
        em.merge(c);
    }
}
