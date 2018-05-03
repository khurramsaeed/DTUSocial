package grp21.dtusocial.service;
import grp21.dtusocial.service.data.dto.Message;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khurram Saeed Malik
 * This class is deprecated because we store data in mongodb now
 * No need to save data in java objects
 */
@Deprecated
public class ChatService {

    private List<Message> messageList = new ArrayList<>();
    //MorphiaHandler handler = MorphiaHandler.morphiaHandler;
    private static ChatService instance = new ChatService();
    
    public static ChatService getInstance() {
        return instance;
    }
    
    public List<Message> getChatById(String userId, String senderId) {
        if (messageList.isEmpty()) return null;
        List<Message> messages = new ArrayList<>();
        
        for (Message message : messageList) {
            if (message.getUserId().equals(userId) && message.getInteractorId().equals(senderId) ||
                message.getUserId().equals(senderId) && message.getInteractorId().equals(userId)) {
                    messages.add(message);
            }
        }
        // null is used for error handling
        return messages;
        
    }
    
    public void sendMessage(Message message) {
        messageList.add(message);
       //return handler.readPersonalChat(userId, senderId);
       }
 
    public List<Message> getMessages() {
        return messageList;
    }
    
}
