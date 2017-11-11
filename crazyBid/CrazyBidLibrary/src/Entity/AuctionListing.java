/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
@Entity
public class AuctionListing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal (TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;
    @Temporal (TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;
    @Column(length = 32, nullable = false)
    private String product;
    private String productDescription;
    @Column(nullable = false)
    private BigDecimal startingPrice;
    @Column(nullable = false)
    private BigDecimal expectedPrice;
    @Column(nullable = false)
    private Boolean status;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "auctionListing")   
    private List<Bid> bidList;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer owner;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Address address;

    public AuctionListing() {
    }

    public AuctionListing(Date startDate, Date endDate, String product, String productDescription, BigDecimal startingPrice, BigDecimal expectedPrice, Boolean status, List<Bid> bidList, Customer owner, Address address) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.product = product;
        this.productDescription = productDescription;
        this.startingPrice = startingPrice;
        this.expectedPrice = expectedPrice;
        this.status = status;
        this.bidList = bidList;
        this.owner = owner;
        this.address = address;
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
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListing)) {
            return false;
        }
        AuctionListing other = (AuctionListing) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AuctionListing[ id=" + getId() + " ]";
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }



    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return the bidList
     */
    public List<Bid> getBidList() {
        return bidList;
    }

    /**
     * @param bidList the bidList to set
     */
    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    /**
     * @return the owner
     */
    public Customer getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the startingPrice
     */
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    /**
     * @param startingPrice the startingPrice to set
     */
    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    /**
     * @return the expectedPrice
     */
    public BigDecimal getExpectedPrice() {
        return expectedPrice;
    }

    /**
     * @param expectedPrice the expectedPrice to set
     */
    public void setExpectedPrice(BigDecimal expectedPrice) {
        this.expectedPrice = expectedPrice;
    }
    
}
