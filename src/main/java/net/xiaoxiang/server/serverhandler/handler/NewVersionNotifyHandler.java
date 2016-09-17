package net.xiaoxiang.server.serverhandler.handler;

import net.xiaoxiang.server.binarystack.entity.CommonResponse;
import net.xiaoxiang.server.binarystack.util.Action;
import net.xiaoxiang.server.serverhandler.CommandNumber;
import net.xiaoxiang.server.serverhandler.parameters.NewVersionRequest;

/**
 * 发送新版本通知类
 * Created by fred on 16/8/29.
 */
public class NewVersionNotifyHandler {
    public static void sendVersionNotifyHandler(int userId , String newVersion , long date , Action<CommonResponse> action){
        NewVersionRequest request = new NewVersionRequest();
        request.setNewVersion(newVersion);
        request.setPublishDate(date);
        PresenceHandler.getInstance().sendNotify(userId , CommandNumber.NOTIFY_NEW_VERSION , request , action);

    }
}
