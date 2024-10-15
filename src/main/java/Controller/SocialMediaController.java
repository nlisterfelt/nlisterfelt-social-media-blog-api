package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. 
 * The endpoints you will need can be found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the 
     * startAPI() method, as the test suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.get("/messages/{account_id}", this::getMessagesByAccountIDHandler);
        app.post("/messages", this::createMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/register", this::createRegisterHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request 
     * and response.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    private void getMessageByIDHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(message_id);
        if(message!=null){
            context.json(message);
        }
    }
    private void getMessagesByAccountIDHandler(Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountID(account_id);
        if(messages!=null){
            context.json(messages);
        }
    }
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
        } else {
            context.status(400);
        }
    }
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_int"));
        String message_text = context.formParam("message_text");
        Message message = messageService.updateMessage(message_id, message_text);
        if(message!=null){
            context.json(message);
        } else {
            context.status(400);
        }
    }
    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account checkAccount = accountService.checkAccount(account);
        if(checkAccount!=null){
            context.json(mapper.writeValueAsString(checkAccount));
        } else {
            context.status(401);
        }
    }
    private void createRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        } else {
            context.status(400);
        }
    }
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        Message deletedMessage = messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));
        if(deletedMessage!=null){
            context.json(deletedMessage);
        } else {
            context.status(200);
        }
    }
}