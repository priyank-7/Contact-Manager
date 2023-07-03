package com.smart.smartcontectmanager.Entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contect {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    
    @NotNull(message = "Name field is required")
    private String name;
    
    private String secondName;
    
    private String work;
    
    private String email;
    
    @NotNull(message = "Phone field is required")
    @Length(min = 7, max = 15, message = "Phone number should be between 7 to 15 characters")
    private String phone;
    
    private String image;
    
    @Column(length = 1000)
    private String description;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getcId() {
        return cId;
    }
    public Contect() {
    }
    public Contect(int cId, String name, String secondName, String work, String email, String phone, String image,
            String description) {
        this.cId = cId;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.description = description;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    // @Override
    // public String toString() {
    //     return "Contect [cId=" + cId + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", email="
    //             + email + ", phone=" + phone + ", image=" + image + ", description=" + description + ", user=" + user
    //             + "]";
    // }
}
