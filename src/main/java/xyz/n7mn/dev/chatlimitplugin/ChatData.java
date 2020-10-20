package xyz.n7mn.dev.chatlimitplugin;

import java.util.Date;

class ChatData {

    private String userName;
    private Date date;

    public ChatData(){
        userName = null;
        date = null;
    }

    public ChatData(String userName, Date date){
        this.userName = userName;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
