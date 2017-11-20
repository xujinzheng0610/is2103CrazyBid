/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import Entity.Customer;
import exception.AddressNotFoundException;
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
@Local(AddressEntityControllerLocal.class)
@Remote(AddressEntityControllerRemote.class)

public class AddressEntityController implements AddressEntityControllerRemote, AddressEntityControllerLocal {

    @PersistenceContext(unitName = "CrazyBid-ejbPU")
    private EntityManager em;

    @Override
    public Address createNewAddress(Address newAddress) { //persist new address

        em.persist(newAddress);
        em.flush();
        em.refresh(newAddress);

        Customer c = newAddress.getCustomer();
        c.getAddressList().add(newAddress);

        return newAddress;
    }

    @Override
    public void updateAddress(Address newAddress) { //update address
        em.merge(newAddress);
    }

    @Override
    public Address retrieveAddressByAddressId(Long id) throws AddressNotFoundException { //retrieve by ID
        Address a = em.find(Address.class, id);

        if (a != null) {
            return a;
        } else {
            throw new AddressNotFoundException("Address ID " + id + " does not exist!");
        }
    }

    @Override
    public void deleteAddress(Long id) throws AddressNotFoundException { // delete address
        Address aToRemove = retrieveAddressByAddressId(id);
        if (aToRemove != null) {
            if (aToRemove.getProductList().isEmpty()) {
                Customer c = aToRemove.getCustomer();
                for (Address a : c.getAddressList()) {
                    if (a.getId().compareTo(id) == 0) {
                        c.getAddressList().remove(a);
                        break;
                    }
                }
                em.remove(aToRemove);
            }else{
                aToRemove.setStatus(Boolean.FALSE);
            }
        } else {
            throw new AddressNotFoundException("Address ID " + id + " does not exist!");
        }

    }

    @Override
    public List<Address> retrieveAllAddresses() { //retrieve all addresses
        Query query = em.createQuery("SELECT a FROM Address a");
        return query.getResultList();
    }

}
