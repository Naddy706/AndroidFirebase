package com.example.firebaseexample;

import java.util.Arrays;

public class User {

    String Name;
    String Email;
    String Password;
    String Gender;
    String Country;
    String Language;
    String Date;
    String Time;
    String Uri;

    public User(String name, String email, String password, String gender, String country, String language, String date, String time, String uri) {
        Name = name;
        Email = email;
        Password = password;
        Gender = gender;
        Country = country;
        Language = language;
        Date = date;
        Time = time;
        Uri = uri;
    }


    public User(){


    }


    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLanguage() {

       return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }




}
