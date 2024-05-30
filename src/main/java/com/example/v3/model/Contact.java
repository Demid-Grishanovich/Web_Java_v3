package com.example.v3.model;

public class Contact {
    private int id;
    private int userId;
    private String name;
    private String phone;
    private String photoUrl;

    /**
     * Default constructor.
     */
    public Contact() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Constructor to initialize a new contact with user ID, name, phone number, and photo URL.
     * @param userId the ID of the user associated with this contact
     * @param name the name of the contact
     * @param phone the phone number of the contact
     * @param photoUrl the URL of the contact's photo
     */
    public Contact(int userId, String name, String phone, String photoUrl) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }

    // Getters and setters for all fields

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public Contact(int id, int userId, String name, String phone, String photoUrl) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}