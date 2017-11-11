/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import Entity.TopUpTransaction;
import exception.CustomerNotFoundException;
import exception.PackageNotFoundException;
import java.util.List;

public interface TopUpTransactionControllerRemote {

    public List<TopUpTransaction> retrieveAllTransactions(Long cId) throws CustomerNotFoundException;
    
    public TopUpTransaction addNewTransaction(Long cId, Long pId) throws CustomerNotFoundException, PackageNotFoundException;
}
