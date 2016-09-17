package net.xiaoxiang.server.serverhandler.handler;

import net.xiaoxiang.server.binarystack.util.BPException;
import net.xiaoxiang.server.binarystack.Handler;
import net.xiaoxiang.server.binarystack.Transaction;
import net.xiaoxiang.server.serverhandler.ResponseInformationManager;
import net.xiaoxiang.server.serverhandler.parameters.SendMessageArgs;
import net.xiaoxiang.server.serverhandler.parameters.SendMessageResponse;

import java.util.UUID;

/**
 * 处理用户发送消息信令
 * Created by fred on 16/9/10.
 */
public class SendMessageHandler implements Handler{
    public void handle(Transaction tx , Object requestObject) throws BPException {
        if(!(requestObject instanceof SendMessageArgs)){
            throw new BPException(400 , "Request arg format error");
        }

        try {
            SendMessageArgs request = (SendMessageArgs) requestObject;
            System.out.println("User " + tx.getRequest().getHeader().getUserId() + " send a message to " + request.getTargetUserId() + "with content:" + request.getMessageContent());
            SendMessageResponse response = new SendMessageResponse();
            response.setMessageId(UUID.randomUUID().toString());
            tx.sendResponse(ResponseInformationManager.OK, response);
        }
        catch (Exception ex){
            throw new BPException(500 , ex.getMessage() , ex);
        }
    }
}
