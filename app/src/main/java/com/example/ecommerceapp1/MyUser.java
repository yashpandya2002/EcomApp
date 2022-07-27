package com.example.ecommerceapp1;

public class MyUser {
    String email;
    String password;
    String authToken;
    String firstName;

    public MyUser(String email, String password, String authToken, String firstName) {
        this.email = email;
        this.password = password;
        this.authToken = authToken;
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
