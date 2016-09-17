package net.xiaoxiang.server.binarystack;

import net.xiaoxiang.server.binarystack.entity.ResponseInformation;
import net.xiaoxiang.server.binarystack.message.BPMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 客户端请求分发类
 * Created by fred on 16/9/3.
 */
public class HandlerDispatcher {

    private static HandlerDispatcher instance = new HandlerDispatcher();
    private static Object lock = new Object();
    private ExecutorService fixThreadPool = Executors.newFixedThreadPool(BinaryStackConfiguration.HANDLER_THREADPOOL_COUNT);
    private static final Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);

    /**
     * 单例模式
     */
    public static HandlerDispatcher getInstance(){
        if(null == instance){
            synchronized (lock){
                if(null == instance){
                    instance = new HandlerDispatcher();
                }
            }
        }

        return instance;
    }

    private HandlerDispatcher(){}


    /**
     * 根据CmdNumber到BeanResourceManager找到对应的handler和request的class，将通知对象分发到对应的handler中
     * @param tx
     */
    public void execute(final Transaction tx){
        fixThreadPool.execute(new Runnable() {
            public void run() {
                try {

                    /**
                     * 从BeanResourceManager找到对应的handler和对应的Request class
                     * 没找到说明初始化有问题，抛exception，返回500错误
                     */
                    BPMessage request = tx.getRequest();
                    Handler handler = BeanResourceManager.getHandler(request.getHeader().getCmd());
                    Class requestClass = BeanResourceManager.getRequest(request.getHeader().getCmd());

                    if(null ==  handler || null == requestClass){
                        throw new Exception("Please initial BeanResourceManager first");
                    }

                    Gson gson = new Gson();
                    handler.handle(tx , gson.fromJson(new String(request.getBody(), BinaryStackConfiguration.CHARSET), requestClass));
                }
                catch (Exception ex){
                    logger.error("invoke callback meets error" , ex);
                    tx.sendResponse(new ResponseInformation(500, ex.getMessage()));
                }
            }
        });
    }
}


