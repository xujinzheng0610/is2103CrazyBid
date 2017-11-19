/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
public class CreditPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32, nullable = false)
    private String packageName;
    @Column(nullable = false, unique = true, scale = 4)
    private BigDecimal amount;
    @Column(nullable = false)
    private int soldAmount;
    @Column(nullable = false)
    private Boolean status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "creditPackage")
    private List<TopUpTransaction> transactionList;

    public CreditPackage() {
    }

    public CreditPackage(String packageName, BigDecimal amount, int soldAmount, Boolean status, List<TopUpTransaction> transactionList) {
        this.packageName = packageName;
        this.amount = amount;
        this.soldAmount = soldAmount;
        this.status = status;
        this.transactionList = transactionList;
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
        if (!(object instanceof CreditPackage)) {
            return false;
        }
        CreditPackage other = (CreditPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CreditPackage[ id=" + id + " ]";
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the transactionList
     */
    public List<TopUpTransaction> getTransactionList() {
        return transactionList;
    }

    /**
     * @param transactionList the transactionList to set
     */
    public void setTransactionList(List<TopUpTransaction> transactionList) {
        this.transactionList = transactionList;
    }

    /**
     * @return the soldAmount
     */
    public int getSoldAmount() {
        return soldAmount;
    }

    /**
     * @param soldAmount the soldAmount to set
     */
    public void setSoldAmount(int soldAmount) {
        this.soldAmount = soldAmount;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

}
