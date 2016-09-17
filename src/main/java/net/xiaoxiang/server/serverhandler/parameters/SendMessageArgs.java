package net.xiaoxiang.server.serverhandler.parameters;

/**
 * Created by fred on 16/9/4.
 */
public class SendMessageArgs {
    int targetUserId = 0;
    String messageContent = "";

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
