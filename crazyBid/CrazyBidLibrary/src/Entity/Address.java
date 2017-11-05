/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String addressContent;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "address")
    private List<AuctionListing> productList;

    public Address() {
    }

    public Address(String addressContent, Customer customer, List<AuctionListing> productList) {
        this.addressContent = addressContent;
        this.customer = customer;
        this.productList = productList;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Address[ id=" + id + " ]";
    }

    /**
     * @return the addressContent
     */
    public String getAddressContent() {
        return addressContent;
    }

    /**
     * @param addressContent the addressContent to set
     */
    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
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
     * @return the productList
     */
    public List<AuctionListing> getProductList() {
        return productList;
    }

    /**
     * @param productList the productList to set
     */
    public void setProductList(List<AuctionListing> productList) {
        this.productList = productList;
    }
    
}
