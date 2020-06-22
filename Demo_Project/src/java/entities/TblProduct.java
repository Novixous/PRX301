/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
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
            query = "SELECT t FROM TblProduct t WHERE t.productId = :productID")
    ,
     @NamedQuery(name = "TblProduct.findProductByProductName",
            query = "SELECT t FROM TblProduct t WHERE t.productName = :productName")
    ,
     @NamedQuery(name = "TblProduct.findProductByPrice",
            query = "SELECT t FROM TblProduct t WHERE t.price = :price")
    ,
     @NamedQuery(name = "TblProduct.findProductByThumbnail",
            query = "SELECT t FROM TblProduct t WHERE t.thumbnail = :thumbnail")
})
public class TblProduct {

}
