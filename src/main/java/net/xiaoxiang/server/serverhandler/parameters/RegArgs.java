package net.xiaoxiang.server.serverhandler.parameters;

/**
 * Created by fred on 16/9/4.
 */
public class RegArgs {

    int userId = 0;

    String password = "";


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
