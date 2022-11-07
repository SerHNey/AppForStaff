package com.example.appforstaff;

import android.graphics.Bitmap;

public class Staff {
     String id,name, phone, email;
    Bitmap image;
    public  Staff(String name, String phone, String email, String id, Bitmap image ){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }
    public  Staff(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
