package net.xiaoxiang.server.serverhandler;

import net.xiaoxiang.server.binarystack.entity.ResponseInformation;

/**
 * Created by fred on 16/9/11.
 */
public class ResponseInformationManager {
    public static final ResponseInformation OK = new ResponseInformation(200 , "OK");
    public static final ResponseInformation USER_NOT_ONLINE =  new ResponseInformation(410 , "User not online , can not send notify");
    public static final ResponseInformation REQUEST_FORMAT_ERROR =  new ResponseInformation(400 , "Request arg format error");

}
