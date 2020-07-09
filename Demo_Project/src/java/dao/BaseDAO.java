/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author katherinecao
 */
public class BaseDAO<T, PK extends Serializable> {
    protected Class<T> entityClass;
    
    public BaseDAO(){
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass=(Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }
    
   
    
}
