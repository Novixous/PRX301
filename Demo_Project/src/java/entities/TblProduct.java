/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "TblProduct", catalog = "NTPGamingGear", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = { //    "productName",
//    "price",
//    "thumbnail"
})
@XmlSeeAlso({
    TblProduct.class
})
@XmlRootElement(name = "ProductType", namespace = "www.product.vn")
@NamedQueries({
    @NamedQuery(name = "TblProduct.findAll",
            query = "SELECT t FROM TblProduct t")
    ,
    @NamedQuery(name = "TblProduct.findProductByProductID",
            query = "SELECT t FROM TblProduct t WHERE t.productID = :productID")
    ,
     @NamedQuery(name = "TblProduct.findProductByProductName",
            query = "SELECT t FROM TblProduct t WHERE t.productName = :productName")
    ,
     @NamedQuery(name = "TblProduct.findProductByPrice",
            query = "SELECT t FROM TblProduct t WHERE t.price = :price")
    ,
     @NamedQuery(name = "TblProduct.findProductByThumbnail",
            query = "SELECT t FROM TblProduct t WHERE t.thumbnail = :thumbnail")
    ,
     @NamedQuery(name = "TblProduct.findByCategoryId",
            query = "SELECT t FROM TblProduct t WHERE t.categoryID = :categoryID")
    ,
    
     @NamedQuery(name = "TblProduct.findByIsActive",
            query = "SELECT t FROM TblProduct t WHERE t.isActive = :isActive")
    ,
     @NamedQuery(name = "TblProduct.findByNameAndCategoryId",
            query = "SELECT t FROM TblProduct t WHERE lower(t.productName) "
            + "LIKE lower(:productName) AND t.categoryID = :categoryID "
            + "AND t.resourceDomain = :resourceDomain")
    ,
     @NamedQuery(name = "TblProduct.findTrendingProducts",
            query = "SELECT t FROM TblProduct t ORDER BY t.productID")
    ,
     @NamedQuery(name = "TblProduct.countAllRecordsInCategory",
            query = "SELECT count(t.productID) FROm TblProduct t "
            + "WHERE t.categoryID = :categoryID")
    , @NamedQuery(name = "TblProduct.searchLikeProductName",
            query = "SELECT t FROM TblProduct t WHERE lower(t.productName) "
            + "LIKE lower(:productName)")})
public class TblProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    @XmlAttribute(name = "ProductID", required = true)
    private Long productID;

    @XmlElement(name = "ProductName", namespace = "www.product.vn", required = true)
    @Column(name = "ProductName", length = 250)
    private String productName;

    @XmlElement(name = "Price", namespace = "www.product.vn", required = true)
    @Column(name = "Price")
    private BigInteger price;

    @XmlElement(name = "Thumbnail", namespace = "www.product.vn", required = true)
    @Column(name = "Thumbnail", length = 250)
    private String thumbnail;

    @Column(name = "CategoryID", length = 250)
    @XmlAttribute(name = "CategoryID", required = true)
    private String categoryID;

    @Column(name = "IsActive")
    @XmlAttribute(name = "IsActive")
    private boolean isActive;

    @Column(name = "ResourceDomain", length = 250)
    @XmlTransient
    private String resouceDomain;

    public TblProduct(Long productID, String productName, BigInteger price, String thumbnail, String categoryID, boolean isActive, String resouceDomain) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.isActive = isActive;
        this.resouceDomain = resouceDomain;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public String getResouceDomain() {
        return resouceDomain;
    }

    public void setResouceDomain(String resouceDomain) {
        this.resouceDomain = resouceDomain;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TblProduct other = (TblProduct) obj;
        if (this.isActive != other.isActive) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.thumbnail, other.thumbnail)) {
            return false;
        }
        if (!Objects.equals(this.categoryID, other.categoryID)) {
            return false;
        }
        if (!Objects.equals(this.resouceDomain, other.resouceDomain)) {
            return false;
        }
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TblProduct{" + "productID=" + productID + ", productName=" + productName + ", price=" + price + ", thumbnail=" + thumbnail + ", categoryID=" + categoryID + ", isActive=" + isActive + ", resouceDomain=" + resouceDomain + '}';
    }

}
