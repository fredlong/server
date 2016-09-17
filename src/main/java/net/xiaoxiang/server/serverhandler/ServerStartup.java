package net.xiaoxiang.server.serverhandler;

import net.xiaoxiang.server.binarystack.BeanResourceManager;
import net.xiaoxiang.server.binarystack.CallbackManager;
import net.xiaoxiang.server.binarystack.NetworkManager;
import net.xiaoxiang.server.binarystack.entity.BeanResource;
import net.xiaoxiang.server.binarystack.entity.CommonResponse;
import net.xiaoxiang.server.binarystack.util.Action;
import net.xiaoxiang.server.serverhandler.handler.NewVersionNotifyHandler;
import net.xiaoxiang.server.serverhandler.handler.PresenceHandler;
import net.xiaoxiang.server.serverhandler.handler.SendMessageHandler;
import net.xiaoxiang.server.serverhandler.handler.RegHandler;
import net.xiaoxiang.server.serverhandler.parameters.NewVersionRequest;
import net.xiaoxiang.server.serverhandler.parameters.RegArgs;
import net.xiaoxiang.server.serverhandler.parameters.SendMessageArgs;
import org.apache.log4j.BasicConfigurator;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by fred on 16/8/29.
 */
public class ServerStartup {
    public static void main(String[] args)throws  Exception{
        BasicConfigurator.configure();

        //初始化信令资源
        BeanResourceManager.addHandlerBean(CommandNumber.REG , RegHandler.class, RegArgs.class);
        BeanResourceManager.addHandlerBean(CommandNumber.SEND_MESSAGE , SendMessageHandler.class, SendMessageArgs.class);
        BeanResourceManager.addNotifyBean(CommandNumber.NOTIFY_NEW_VERSION , null);

        //启动服务
        new Thread(NetworkManager.getInstance()).start();
        //启动回调函数检查线程
        new Thread(CallbackManager.getInstance()).start();


        final AtomicBoolean isSend = new AtomicBoolean(false);
        while(!isSend.get()){
            NewVersionNotifyHandler.sendVersionNotifyHandler(10000003 , "10.0.2", new Date().getTime(), new Action<CommonResponse>() {
                public void run(CommonResponse response) {
                    System.out.println("Notify send result code is " + response.getResultCode());
                    System.out.println("Notify send result message is " + response.getMessage());

                    if (response.getResultCode() == 200) {
                        isSend.set(true);
                    }
                }
            });

            Thread.sleep(3000);
        }

    }
}
