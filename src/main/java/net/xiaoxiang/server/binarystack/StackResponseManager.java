package net.xiaoxiang.server.binarystack;

import net.xiaoxiang.server.binarystack.entity.ResponseInformation;

/**
 * 给用户返回应答码和应答描述管理类
 * Created by fred on 16/9/11.
 */
public class StackResponseManager {
    //客户端应答超时
    public static final ResponseInformation CALLBACK_EXPIRED = new ResponseInformation(502 , "Server did not response in " + BinaryStackConfiguration.CALLBACK_TIMEOUT + " Second");

    //无法解析接收到的Response
    public static final ResponseInformation FAIL_TO_PARSE_RESPONSE =  new ResponseInformation(506 , "Fail to parse response bytes to CommonResponse object");

    //成功返回
    public static final ResponseInformation OK = new ResponseInformation(200 , "OK");
}
