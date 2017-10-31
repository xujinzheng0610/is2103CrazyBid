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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true)
    private String userName;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 16, nullable = false)
    private String phoneNumber;
    @Column(length = 32, nullable = false, unique = true)
    private String email;
    @Column(length = 32, nullable = false)
    private Long creditBalance;
    @Column(nullable = false)
    private Boolean premium;
    
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "customer")
    private List<Address> addressList;
//
//    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "customer")
//    private List<Bid> bidList;
    
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "owner")
    private List<AuctionListing> productList;
    
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "customer")
    private List<CreditTransaction> topUpList;
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getCustomerId() == null && other.getCustomerId() != null) || (this.getCustomerId() != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Customer[ id=" + getCustomerId() + " ]";
    }

    /**
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the creditBalance
     */
    public Long getCreditBalance() {
        return creditBalance;
    }

    /**
     * @param creditBalance the creditBalance to set
     */
    public void setCreditBalance(Long creditBalance) {
        this.creditBalance = creditBalance;
    }

    /**
     * @return the premium
     */
    public Boolean getPremium() {
        return premium;
    }

    /**
     * @param premium the premium to set
     */
    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    /**
     * @return the addressList
     */
    public List<Address> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }



    /**
     * @return the bidList
     */
//    public List<Bid> getBidList() {
//        return bidList;
//    }
//
//    /**
//     * @param bidList the bidList to set
//     */
//    public void setBidList(List<Bid> bidList) {
//        this.bidList = bidList;
//    }

    /**
     * @return the topUpList
     */
    public List<CreditTransaction> getTopUpList() {
        return topUpList;
    }

    /**
     * @param topUpList the topUpList to set
     */
    public void setTopUpList(List<CreditTransaction> topUpList) {
        this.topUpList = topUpList;
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
