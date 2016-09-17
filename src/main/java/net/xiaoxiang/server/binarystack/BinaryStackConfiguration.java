package net.xiaoxiang.server.binarystack;

import java.nio.charset.Charset;

/**
 * Created by fred on 16/8/24.
 */
public class BinaryStackConfiguration {
    /**
     * 服务器IP
     */
    public final static String REMOTE_HOST =  "127.0.0.1";

    /**
     * 服务端监听端口
     */
    public final static int REMOTE_PORT = 8080;

    /**
     * 客户端类型,Server发起的报文，这块填写127
     */
    public final static byte CLIENT_TYPE = 127;

    /**
     * 客户端版本号,Server发起的报文，这块填写127
     */
    public final static byte CLIENT_VERSION = 127;

    /**
     * 客户端版本号
     */
    public final static byte PROTOCOL_VERSION = 1;

    /**
     * 应答超时时间
     */
    public final static int CALLBACK_TIMEOUT = 5000;


    /**
     * BPMessage中的Body编码
     */
    public final static Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 处理请求类报文线程池的线程数量
     * 作Server端，一般设置为处理器的核数比较合理
     */
    public final static int HANDLER_THREADPOOL_COUNT = 8;

    /**
     * 处理应答报文线程池的线程数量
     * 作为客户端，一般一个线程就够了
     */
    public final static int CALLBACK_THREADPOOL_COUNT = 1;

    /**
     * 处理发送请求类报文线程池的线程数量
     * 作为客户端，一般一个线程就够了
     */
    public final static int NIO_WORKER_THREADPOOL_COUNT = 1;

}
