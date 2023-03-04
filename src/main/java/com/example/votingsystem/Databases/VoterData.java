package com.example.votingsystem.Databases;

public class VoterData {

    private Integer voterID;
    private String firstName;
    private String lastName;
    private String phone;
    private String location;
    private String image;

    private Integer verification;

    public VoterData(Integer voterID, String firstName, String lastName, String phone, String location, String image, Integer verification) {
        this.voterID = voterID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.location = location;
        this.image = image;
        this.verification = verification;
    }

    public VoterData(Integer voterID, String firstName, String lastName, String phone, String location) {
        this.voterID = voterID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.location = location;
    }

    public Integer getVoterID() {
        return voterID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public Integer getVerification() {
        return verification;
    }

}
