package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "users", schema = "public")
@NamedQueries(

        {
            @NamedQuery(name = "userByUsername", query = "select u from UserEntity u where u.userName= :userName"),
            @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email= :email"),
            //@NamedQuery(name = "deleteById", query = "delete from UserEntity u where u.id= :id"),
            @NamedQuery(name = "userById", query = "select u from UserEntity u where u.id= :id"),
            @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid")
           // @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid= :uuid")

        }
)
public class UserEntity implements Serializable {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UUID")
    @Size(max = 64)
    private String uuid;


    @Column(name = "FIRSTNAME")
    @NotNull
    @Size(max = 200)
    private String firstName;

    @Column(name = "LASTNAME")
    @NotNull
    @Size(max = 200)
    private String lastName;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 64)
    private String userName;

    @Column(name = "EMAIL")
    @NotNull
    private String email;



    @Column(name = "PASSWORD")
    @NotNull
    @Size(max = 50)
    private String password;

    @Column(name = "SALT")
    @Size(max = 200)
    private String salt;

    @Column(name = "COUNTRY")
    @NotNull
    private String country;

    @Column(name = "ABOUTME")
    private String aboutMe;

    @Column(name = "DOB")
    @NotNull
    private String dob;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "CONTACTNUMBER")
    private String contactNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<QuestionEntity> questions = new ArrayList<>();

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

}
