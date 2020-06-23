/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblProduct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utilities.DBUtilities;

/**
 *
 * @author katherinecao
 */
public class ProductDAO extends BaseDAO<TblProduct, Long> {

    private ProductDAO() {

    }
    private static ProductDAO instance;
    private final static Object LOCK = new Object();

    public static ProductDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductDAO();
            }
        }
        return instance;
    }

    public TblProduct getProductBy(String productName, String categoryId, String domain) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<TblProduct> result = em.createNamedQuery("TblProduct.findByNameAndCategoryId", TblProduct.class)
                    .setParameter("productName", productName)
                    .setParameter("categoryID", categoryId)
                    .setParameter("resourceDomain", domain)
                    .getResultList();
            transaction.commit();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public synchronized void saveProductWhenCrawling(TblProduct product) {
        TblProduct existedProduct = getInstance().getProductBy(product.getProductName(), product.getCategoryID(), product.getResouceDomain());
        TblProduct result;
        if (existedProduct == null) {
            result = create(product);
        } else{
            result = update(product);
        }
    }
}
