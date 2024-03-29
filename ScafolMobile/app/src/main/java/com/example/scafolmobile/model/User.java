package com.example.scafolmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("dinas_id")
    private String dinasId;
    @SerializedName("bi_id")
    private String biId;
    @SerializedName("nama")
    private String nama;
    @SerializedName("username")
    private String username;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("bagian")
    private String bagian;
    @SerializedName("role")
    private String role;
    @SerializedName("isactive")
    private String isactive;

    public User(String userId, String dinasId, String biId, String nama, String username, String telephone,
                    String bagian, String role, String isactive){
        this.userId = userId;
        this.dinasId = dinasId;
        this.biId = biId;
        this.userId = userId;
        this.nama = nama;
        this.username = username;
        this.telephone = telephone;
        this.bagian = bagian;
        this.role = role;
        this.isactive = isactive;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", dinasId='" + dinasId + '\'' +
                ", biId='" + biId + '\'' +
                ", nama='" + nama + '\'' +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", bagian='" + bagian + '\'' +
                ", role='" + role + '\'' +
                ", isactive='" + isactive + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDinasId() {
        return dinasId;
    }

    public void setDinasId(String dinasId) {
        this.dinasId = dinasId;
    }

    public String getBiId() {
        return biId;
    }

    public void setBiId(String biId) {
        this.biId = biId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
