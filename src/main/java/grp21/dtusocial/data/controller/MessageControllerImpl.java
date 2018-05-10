package grp21.dtusocial.data.controller;

import grp21.dtusocial.data.PersistenceException;
import grp21.dtusocial.data.dao.MessageDAO;
import grp21.dtusocial.data.dao.MongoMessageDAO;
import grp21.dtusocial.data.dto.Message;
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
