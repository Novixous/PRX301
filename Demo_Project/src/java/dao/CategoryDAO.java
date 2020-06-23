/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblCategory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import utilities.DBUtilities;

/**
 *
 * @author katherinecao
 */
public class CategoryDAO extends BaseDAO<TblCategory, String>{
    private CategoryDAO(){
        
    }
    private static CategoryDAO instance;
    private final static Object LOCK = new Object();
    
    public static CategoryDAO getInstance(){
        synchronized(LOCK){
            if (instance == null) {
                instance = new CategoryDAO();
            }
        }
        return instance;
    }
    
    public synchronized TblCategory getFirstCategoryByName(String categoryName){
        EntityManager em=DBUtilities.getEntityManager();
        try {
            List<TblCategory> result = em.createNamedQuery("TblCategory.findByCategoryName", TblCategory.class)
                    .setParameter("categoryName", categoryName)
                    .getResultList();
            if (result!=null && !result.isEmpty()) {
                return  result.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE,null,e);
        } finally{
            if (em!=null) {
                em.close();
            }
        }
        return null;
    }
}
