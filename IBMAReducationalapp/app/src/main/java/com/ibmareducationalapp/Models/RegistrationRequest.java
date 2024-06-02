package com.ibmareducationalapp.Models;

public class RegistrationRequest {
    private String name;
    private String academicSubject;
    private String email;
    private String password; // Raw password for registration

    public RegistrationRequest(){}
    public RegistrationRequest(String name, String academicSubject, String email, String password) {
        this.name = name;
        this.academicSubject = academicSubject;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademicSubject() {
        return academicSubject;
    }

    public void setAcademicSubject(String academicSubject) {
        this.academicSubject = academicSubject;
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

}
