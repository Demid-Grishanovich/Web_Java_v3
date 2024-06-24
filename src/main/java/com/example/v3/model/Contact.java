package com.example.v3.model;

public class Contact {
    private int id;
    private int userId;
    private String name;
    private String phone;
    private byte[] photo;

    // Конструкторы
    public Contact() {
    }

    public Contact(int userId, String name, String phone, byte[] photo) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public Contact(int id, int userId, String name, String phone, byte[] photo) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
