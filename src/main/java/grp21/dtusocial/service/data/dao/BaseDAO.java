/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service.data.dao;

import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dto.BaseDTO;
import grp21.dtusocial.service.data.dto.Todo;
import gru21.dtusocial.interfaces.ValidException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 1234
 */
public interface BaseDAO <T extends BaseDTO>{
    T save(T element) throws PersistenceException, UnknownHostException;
    List<T> saveMultiple(List<T> elements) throws PersistenceException;

    List<T> getAll() throws PersistenceException;
    T get(String id) throws PersistenceException, ValidException;
    List<T> multiGet(Collection<String> ids) throws PersistenceException, ValidException;

    List<T> findByField(String fieldName, String value) throws PersistenceException;
    List<T> findByFields(Map<String, Object> fields) throws PersistenceException;

    int findByFieldAndUpdateField(String findField, Object findFieldValue, String updateField, Object newValue) throws PersistenceException;

    Boolean delete(String oid) throws PersistenceException, ValidException;
    
    Boolean deleteById(String fieldName, String objectId) throws PersistenceException, ValidException;
    
    Boolean updateTodo(Todo todo) throws PersistenceException, UnknownHostException;
    


}
