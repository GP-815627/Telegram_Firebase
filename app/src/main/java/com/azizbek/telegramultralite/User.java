package com.azizbek.telegramultralite;

public class User {

    String profilimage;
    String username;
    String surname;
    String useremail;
    String uploadkey;

    public User() {
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUploadkey() {
        return uploadkey;
    }

    public void setUploadkey(String uploadkey) {
        this.uploadkey = uploadkey;
    }

    public String getProfilimage() {
        return profilimage;
    }

    public void setProfilimage(String profilimage) {
        this.profilimage = profilimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
