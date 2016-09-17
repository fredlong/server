package net.xiaoxiang.server.binarystack;

import net.xiaoxiang.server.binarystack.message.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 网络连接管理类
 * 启动服务器监听
 * 负责发送请求
 * Created by fred on 16/8/20.
 */
public class NetworkManager implements Runnable{
    private static NetworkManager netWork = new NetworkManager();
    private static Object lock = new Object();
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);

    /**
     * 使用单例模式
     */
    public static NetworkManager getInstance(){
        if(null == netWork){
            synchronized (lock){
                if(null == netWork){
                    netWork = new NetworkManager();
                }
            }
        }
        return netWork;
    }

    private NetworkManager(){}


    /**
     * 启动服务
     * @throws InterruptedException
     */
    public void  runAsServer() throws InterruptedException {
        /**
         * 管理channal的线程池
         * 一般监听一段端口，一个线程就可以搞定，默认就可以了
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /**
         * 负责网络io的group
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup(BinaryStackConfiguration.NIO_WORKER_THREADPOOL_COUNT);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)

                 //设置TCP的参数，这里设置了套接字的最大连接个数。
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE , true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast("encoder", new BPEncoder())
                                .addLast("decoder", new BPDecoder())
                                .addLast("handler", new Dispatcher());

                    }
                });


        ChannelFuture future = bootstrap.bind(BinaryStackConfiguration.REMOTE_HOST , BinaryStackConfiguration.REMOTE_PORT).sync();


        if(future.isSuccess()){
            logger.info("Success to run as server");
        }
        else{
            logger.info("Run failed ");
        }
    }

    public void run() {
        try {
            runAsServer();
        } catch (Exception ex){
            logger.error("server failed to start" , ex);
        }
    }
}