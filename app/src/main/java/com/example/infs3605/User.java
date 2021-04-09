package com.example.infs3605;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String name, email, password, language, type,blood;
    public Long id;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(String name, String email, String password, String language, String type, String blood, Long id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.language = language;
        this.type = type;
        this.blood = blood;
        this.id = id;
    }

    public static ArrayList<User> getStaticUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Mina Faiz","Hurstville Red Cross","With family","Chinese","No Help Requested","A+ (A Postive)",Long.valueOf(101)));
        users.add(new User("Zaed Faiz","Chatswoord Red Cross","With family","English","No Help requested","A+ (A Postive)",Long.valueOf(102)));
        users.add(new User("Rafid Mahmood","Unknown","Unknown","English","Help requested","C+ (A Postive)",Long.valueOf(103)));
        return users;
    }

    public static User getUser(String name) {
        ArrayList<User> users = getStaticUsers();
        for (final User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return users.get(users.size() - 1);
    }


}