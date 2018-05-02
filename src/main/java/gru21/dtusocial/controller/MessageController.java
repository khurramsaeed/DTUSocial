/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gru21.dtusocial.controller;

import grp21.dtusocial.service.data.PersistenceException;
import grp21.dtusocial.service.data.dto.Message;
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
