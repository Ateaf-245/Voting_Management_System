package com.example.votingsystem.Databases;

public class CandidateData {

    Integer candidate_Id;
    String firstName;
    String lastName;
    String phone;
    String location;
    String party;
    String image;
    Integer votes;


    public CandidateData(Integer candidate_Id, String firstName, String lastName, String phone, String location, String party, String image) {
        this.candidate_Id = candidate_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.location = location;
        this.party = party;
        this.image = image;
    }

    public CandidateData(Integer candidate_Id,String firstName, String lastName, String location, String party, String image,Integer votes) {
        this.candidate_Id = candidate_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.party = party;
        this.image = image;
        this.votes = votes;
    }

    public Integer getCandidate_Id() {
        return candidate_Id;
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

    public Integer getVotes() {
        return votes;
    }

    public String getParty() {
        return party;
    }
    public String getImage() {
        return image;
    }



}
