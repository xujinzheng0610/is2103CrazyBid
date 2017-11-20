/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal (TemporalType.TIMESTAMP)
    private Date bidTime;
    
    @Column(nullable = false, columnDefinition = "DECIMAL(18,4)")
    private BigDecimal bidAmount;
    
    @ManyToOne(cascade = {CascadeType.ALL}) 
    private AuctionListing auctionListing;
    
    @ManyToOne(cascade = {CascadeType.ALL}) 
    private Customer customer;

    public Bid() {
    }

    public Bid(Date bidTime, BigDecimal bidAmount, AuctionListing auctionListing, Customer customer) {
        this.bidTime = bidTime;
        this.bidAmount = bidAmount;
        this.auctionListing = auctionListing;
        this.customer = customer;
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
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Bid[ id=" + id + " ]";
    }

    /**
     * @return the bidTime
     */
    public Date getBidTime() {
        return bidTime;
    }

    /**
     * @param bidTime the bidTime to set
     */
    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    /**
     * @return the auctionListing
     */
    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    /**
     * @param auctionListing the auctionListing to set
     */
    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
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
     * @return the bidAmount
     */
    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    /**
     * @param bidAmount the bidAmount to set
     */
    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }


    
}
