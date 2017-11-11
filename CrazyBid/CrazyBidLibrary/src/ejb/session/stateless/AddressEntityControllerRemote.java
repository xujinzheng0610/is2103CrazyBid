/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import exception.AddressNotFoundException;
import java.util.List;

public interface AddressEntityControllerRemote {
    public Address createNewAddress(Address newAddress);

    public void updateAddress(Address newAddress);

    public void deleteAddress(Long id) throws AddressNotFoundException;

    public Address retrieveAddressByAddressId(Long id) throws AddressNotFoundException;

    public List<Address> retrieveAllAddresses();
}
