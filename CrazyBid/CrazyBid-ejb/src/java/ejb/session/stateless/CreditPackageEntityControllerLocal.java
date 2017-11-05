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


public interface CreditPackageEntityControllerLocal {

    public CreditPackage retrievePackageByAmount(BigDecimal amount) throws PackageNotFoundException;

    public CreditPackage persistNewCreditPackage(CreditPackage c);

    public List<CreditPackage> retrieveAllCreditPackages();

    public CreditPackage retrieveCreditPackageById(Long id) throws PackageNotFoundException;

    public void deleteCreditPackage(Long id) throws PackageNotFoundException;

    public void updateCreditPackage(CreditPackage c);
    
}
