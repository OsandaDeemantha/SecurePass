package com.example.securepass;

public class Credential {

    int id;
    String username;
    String password;
    String url;

    public Credential(){
    }

    public Credential(String username, String password, String url){
        this.username = username;
        this.password = password;
        this.url = url;
    }
    public Credential(int id, String username, String password, String url){
        this.id = id;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
