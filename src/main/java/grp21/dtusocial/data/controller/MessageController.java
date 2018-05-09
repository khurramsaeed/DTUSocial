package grp21.dtusocial.data.controller;

import grp21.dtusocial.data.PersistenceException;
import grp21.dtusocial.data.dto.Message;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 1234
 */
public interface MessageController {
     Message sendMessage(Message message) throws PersistenceException, UnknownHostException;
     List<Message> getAllMessages() throws PersistenceException;
     List<Message> getPersonalMessages(Map<String, Object> fields) throws PersistenceException;
}
