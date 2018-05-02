/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gru21.dtusocial.controller;

import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dao.MessageDAO;
import grp21.dtusocial.service.data.dao.MongoMessageDAO;
import grp21.dtusocial.service.data.dao.MongoTodoDAO;
import grp21.dtusocial.service.data.dto.Message;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 1234
 */
public class MessageControllerImpl implements MessageController{
    MessageDAO messageDAO = new MongoMessageDAO();
    
    @Override
    public Message sendMessage(Message message) throws PersistenceException, UnknownHostException {
        return messageDAO.save(message);
    }

    @Override
    public List<Message> getAllMessages() throws PersistenceException {
        return messageDAO.getAll();
    }

    @Override
    public List<Message> getPersonalMessages(Map<String, Object> fields) throws PersistenceException {
        return messageDAO.findByFields(fields);
    }
    
}
