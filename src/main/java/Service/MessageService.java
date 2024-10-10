package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public Message addMessage(Message message){
        if(message.message_text!=null && message.message_text.length()>=255){
            return messageDAO.addMessage(message);
        }
        return null;
    }
    
}
