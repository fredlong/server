package net.xiaoxiang.server.binarystack;

import net.xiaoxiang.server.binarystack.entity.CommonResponse;
import net.xiaoxiang.server.binarystack.entity.ResponseInformation;
import net.xiaoxiang.server.binarystack.message.BPMessage;
import net.xiaoxiang.server.binarystack.message.BPMessageFactory;
import net.xiaoxiang.server.binarystack.util.Action;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;

/**
 * 事务上下文类
 * 每一次请求的channel，Request都会放在这个类里，并被传递到handler中
 * handler调用tx方法返回应答
 * Created by fred on 16/9/3.
 */
public class Transaction {
    BPMessage request;
    ChannelHandlerContext context;

    Transaction(BPMessage request , ChannelHandlerContext context){
        this.request = request ;
        this.context = context;
    }

    public BPMessage getRequest() {
        return request;
    }

    /**
     * 返回应答给客户端
     * @param responseInformation
     * 应答的应答码和应答描述信息
     * @param entity
     * 应答的业务数据
     */
    public void sendResponse(ResponseInformation responseInformation , Object entity){
        context.channel().writeAndFlush(BPMessageFactory.generateBPMessage(request, responseInformation.getResultCode(), responseInformation.getMessage(), entity));
    }

    public void sendResponse(ResponseInformation responseInformation){
        sendResponse(responseInformation , null);
    }


    /**
     * 发送通知
     * @param cmd
     * 信令编码
     * @param entity
     * 通知内容
     * @param action
     * 回调函数
     */
    public void sendNotify(short cmd , Object entity , Action<CommonResponse> action){
        Gson gson = new Gson();
        BPMessage notify = BPMessageFactory.generateBPMessage(request.getHeader().getUserId() , cmd , null , gson.toJson(entity).getBytes(BinaryStackConfiguration.CHARSET));
        if(null != action){
            CallbackManager.getInstance().addCallBack(request.getHeader().getSeq() , action);
        }
        context.channel().writeAndFlush(notify);
    }
}
