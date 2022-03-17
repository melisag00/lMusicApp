package com.example.lmusic;

public class User {

    public String imageUrl;
    public String cvUrl;
    public String userBio;
    public String userName;
    public String userEmail;
    public String userPassword;
    public String userAddress;
    public String userPhone;
    public String userBirth;
    public String userInstruments;
    public String userRole;


    public User(){
    }

    public User(String userName, String userEmail, String userPassword, String userAddress, String userPhone, String userBirth, String userInstruments) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
        this.userInstruments = userInstruments;

    }

    public User(String imageUrl, String cvUrl, String userBio, String userName, String userEmail, String userPassword, String userAddress, String userPhone, String userBirth, String userInstruments, String userRole) {
        this.imageUrl = imageUrl;
        this.cvUrl = cvUrl;
        this.userBio = userBio;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
        this.userInstruments = userInstruments;
        this.userRole = userRole;
    }
}
