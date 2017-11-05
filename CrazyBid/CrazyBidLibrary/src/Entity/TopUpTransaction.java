/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
@Entity
public class TopUpTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private CreditPackage creditPackage;

    public TopUpTransaction() {
    }

    public TopUpTransaction(Date createdOn, Customer customer, CreditPackage creditPackage) {
        this.createdOn = createdOn;
        this.customer = customer;
        this.creditPackage = creditPackage;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TopUpTransaction)) {
            return false;
        }
        TopUpTransaction other = (TopUpTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.TopUpTransaction[ id=" + id + " ]";
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the creditPackage
     */
    public CreditPackage getCreditPackage() {
        return creditPackage;
    }

    /**
     * @param creditPackage the creditPackage to set
     */
    public void setCreditPackage(CreditPackage creditPackage) {
        this.creditPackage = creditPackage;
    }
    
}
