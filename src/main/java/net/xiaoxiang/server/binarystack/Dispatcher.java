package net.xiaoxiang.server.binarystack;

import net.xiaoxiang.server.binarystack.message.BPMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报文分发总类
 * Created by fred on 16/8/20.
 */
public class Dispatcher  extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BPMessage) {
            BPMessage bpMessage = (BPMessage) msg;
            if(bpMessage.isResponse()){
                CallbackManager.getInstance().execute(bpMessage);
            }
            else{
                Transaction tx = new Transaction(bpMessage , ctx);
                HandlerDispatcher.getInstance().execute(tx);
            }
        }

    }

    /**
     * 网络层出现异常的时候，记录日志
     * @param ctx
     * @param cause
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("invoke disconnect failed", cause);
    }
}