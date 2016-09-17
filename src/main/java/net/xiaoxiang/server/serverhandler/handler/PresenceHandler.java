package net.xiaoxiang.server.serverhandler.handler;

import net.xiaoxiang.server.binarystack.util.Action;
import net.xiaoxiang.server.binarystack.entity.CommonResponse;
import net.xiaoxiang.server.binarystack.Transaction;
import net.xiaoxiang.server.serverhandler.ResponseInformationManager;


import java.util.HashMap;

/**
 * 在线用户管理类
 * Created by fred on 16/9/10.
 */
public class PresenceHandler {
    private static PresenceHandler instance = new PresenceHandler();
    private static Object lock = new Object();


    /**
     * 使用单例模式
     * @return
     */
    public static PresenceHandler getInstance(){
        if(null == instance){
            synchronized (lock){
                if(null == instance){
                    instance = new PresenceHandler();
                }
            }
        }
        return instance;
    }

    private PresenceHandler(){}
    private HashMap<Integer , Endpoint> onlineUsers = new HashMap<Integer, Endpoint>();

    /**
     * 用户登录后，将用户的连接信息放入缓存
     * @param userId
     * @param tx
     * @param credential
     */
    public void addUser(int userId , Transaction tx , String credential){
        onlineUsers.put(userId , new Endpoint(tx , credential));
    }

    public void sendNotify(int userId , short cmd , Object entity , Action<CommonResponse> action){
        Endpoint endpoint = onlineUsers.get(userId);
        if(null != endpoint){
            endpoint.getTransaction().sendNotify(cmd , entity , action);
        }
        else{

            if(null != action){
                action.run(new CommonResponse(ResponseInformationManager.USER_NOT_ONLINE));
            }
        }

    }

    public void sendNotify(int userId , short cmd , Object entity){
        sendNotify(cmd, cmd , entity , null);
    }

    class Endpoint {
        Transaction context;
        String credential;

        Endpoint(Transaction context , String credential){
            this.context = context;
            this.credential = credential;
        }

        public Transaction getTransaction() {
            return context;
        }

        public String getCredential() {
            return credential;
        }
    }

}
