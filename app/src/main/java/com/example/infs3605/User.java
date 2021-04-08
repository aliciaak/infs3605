package com.example.infs3605;

public class User {

    public String name, email, password, language, type,blood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public User(){
    }

    public User(String name, String email, String password, String language,String type,String blood) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.language = language;
        this.type = type;
        this.blood = blood;
    }
}