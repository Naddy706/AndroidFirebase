package com.example.firebaseexample;

public class register {

    String email,token;

    public register(String email, String token) {

        this.email = email;
        this.token = token;
    }

    public register() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
