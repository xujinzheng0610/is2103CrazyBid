/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import Entity.Customer;
import Entity.TopUpTransaction;
import exception.CustomerNotFoundException;
import exception.PackageNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Chester
 */
@Stateless
@Local(TopUpTransactionControllerLocal.class)
@Remote(TopUpTransactionControllerRemote.class)

public class TopUpTransactionController implements TopUpTransactionControllerRemote, TopUpTransactionControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    @Override
    public List<TopUpTransaction> retrieveAllTransactions(Long cId) throws CustomerNotFoundException {
        Customer c = em.find(Customer.class, cId);
        if (c != null) {
            List<TopUpTransaction> tList = c.getTopUpList();
            for (TopUpTransaction t : tList) {
                t.getCreditPackage().getAmount();
                t.getCreditPackage().getPackageName();
            }
            return tList;
        } else {
            throw new CustomerNotFoundException("This customer id is invalid!");
        }

    }

    @Override
    public TopUpTransaction addNewTransaction(Long cId, Long pId) throws CustomerNotFoundException, PackageNotFoundException {
        Customer c = em.find(Customer.class, cId);
        CreditPackage p = em.find(CreditPackage.class, pId);

        if (c != null) {
            if (p != null) {
                TopUpTransaction t = new TopUpTransaction();
                t.setCreatedOn(new Date());
                t.setCreditPackage(p);
                p.getTransactionList().add(t);
                p.setSoldAmount(p.getSoldAmount() + 1);
                t.setCustomer(c);
                c.getTopUpList().add(t);
                c.setCreditBalance(c.getCreditBalance().add(p.getAmount()));
                em.persist(t);
                em.flush();
                em.refresh(t);
                t.getCreditPackage();
                return t;
            } else {
                throw new PackageNotFoundException("This Credit Package Id is Invalid!");
            }
        } else {
            throw new CustomerNotFoundException("This Customer Id is Invalid!");
        }
    }
}
