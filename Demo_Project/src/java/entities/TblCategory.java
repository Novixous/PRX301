/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Tbl_Category", catalog = "NTPGamingGear", schema = "dbo")
@XmlType(name = "", propOrder = { //"categoryName"
})
@XmlRootElement(name = "Category", namespace = "www.category.vn")
@NamedQueries({
    @NamedQuery(name = "TblCategory.findAll", query = "SELECT t FROM TblCategory t")
    ,
    @NamedQuery(name = "TblCategory.findByCategoryId", query = "SELECT t FROM TblCategory t WHERE t.categoryId = :categoryId")
    ,
    @NamedQuery(name = "TblCategory.findByCategoryName", query = "SELECT t FROM TblCategory t WHERE t.categoryName = :categoryName")
})

public class TblCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CategoryId", nullable = false, length = 250)
    @XmlAttribute(name = "CategoryId", required = true)
    private String categoryId;

    @Column(name = "CategoryName", length = 250)
    @XmlElement(name = "CategoryName", required = true, namespace = "www.category.vn")
    private String categoryName;

    public TblCategory() {
    }

    public TblCategory(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public TblCategory(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "entities.TblCategory[ categoryId=" + categoryId + " ]";
    }

}
