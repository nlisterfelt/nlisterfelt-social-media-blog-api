package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }
    public List<Message> getAllMessagesByAccountID(int account_id){
        return messageDAO.getAllMessagesByAccountID(account_id);
    }
    public Message addMessage(Message message){
        if(messageDAO.getMessageByID(message.message_id)==null && message.message_text!=null && message.message_text.length()>=255){
            return messageDAO.addMessage(message);
        }
        return null;
    }
    public Message updateMessage(int message_id, String message_text){
        Message message = messageDAO.getMessageByID(message_id);
        if(message!=null && message_text.length()>0 && message_text.length()<=255){
            messageDAO.updateMessage(message_id, message_text);
        }
        return null;
    }
    public Message deleteMessage(int message_id){
        if(messageDAO.getMessageByID(message_id)!=null){
            return messageDAO.deleteMessage(message_id);
        }
        return null;
    }
}
