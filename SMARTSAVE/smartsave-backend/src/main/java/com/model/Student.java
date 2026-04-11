package com.model;

public class Student {

    private Long id;
    private String firstName;
    private String major;
    private String emails;

    public Student() {
    }

    public Student(Long id, String firstName, String major, String emails) {
        this.id = id;
        this.firstName = firstName;
        this.major = major;
        this.emails = emails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}