package com.example.ideafood;

public class Account {
    String username, password, level, email;

    public Account() {
    }

    public Account(String username, String password, String level, String email) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.email = email;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
