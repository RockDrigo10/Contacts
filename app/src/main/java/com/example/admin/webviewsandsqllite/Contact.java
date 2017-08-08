package com.example.admin.webviewsandsqllite;

/**
 * Created by Admin on 8/7/2017.
 */

public class Contact {
    String id;
    String name;
    String number;
    String email;
    String social;
    byte[] photo;

    public Contact(String id, String name, String number, String email, String social, byte[] photo) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.social = social;
        this.photo = photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getSocial() {
        return social;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
