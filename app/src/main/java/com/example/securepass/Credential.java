package com.example.securepass;

public class Credential {

    private int id;
    private String title;
    private String username;
    private String password;
    private String url;

    public Credential(){
    }

    public Credential(String title, String username, String password, String url){
        this.title = title;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Credential(int id, String title, String username, String password, String url){
        this.id = id;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
