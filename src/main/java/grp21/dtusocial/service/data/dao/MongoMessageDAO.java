/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grp21.dtusocial.service.data.dao;
import grp21.dtusocial.service.data.dao.MongoBaseDAO;
import grp21.dtusocial.service.data.dao.MessageDAO;
import grp21.dtusocial.service.data.dto.Message;

/**
 *
 * @author 1234
 */
public class MongoMessageDAO extends MongoBaseDAO<Message> implements MessageDAO {

    public MongoMessageDAO() {
        super(Message.class);
    }

  
  
}
