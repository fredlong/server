package net.xiaoxiang.server.serverhandler.handler;

import net.xiaoxiang.server.binarystack.Handler;
import net.xiaoxiang.server.binarystack.Transaction;
import net.xiaoxiang.server.binarystack.util.BPException;
import net.xiaoxiang.server.serverhandler.ResponseInformationManager;
import net.xiaoxiang.server.serverhandler.parameters.RegArgs;
import net.xiaoxiang.server.serverhandler.parameters.RegResponse;
import java.util.UUID;

/**
 * 处理用户发送的登录信令
 * Created by fred on 16/9/4.
 */
public class RegHandler implements Handler {
    public void handle(Transaction tx , Object requestObject) throws BPException {
        if(!(requestObject instanceof  RegArgs)){
            throw new BPException(ResponseInformationManager.REQUEST_FORMAT_ERROR);
        }

        try {
            RegArgs request = (RegArgs) requestObject;
            if(request.getUserId() == 10000003 && request.getPassword().equals("123456")){
                String credential = UUID.randomUUID().toString();
                PresenceHandler.getInstance().addUser(request.getUserId() , tx , credential);
                RegResponse response = new RegResponse();
                response.setCrediential(credential);
                tx.sendResponse(ResponseInformationManager.OK, response);

                System.out.println("User " + request.getUserId() + " login successfully");
            }
        }
        catch (Exception ex){
            throw new BPException(500 , ex.getMessage() , ex);
        }
    }
}
