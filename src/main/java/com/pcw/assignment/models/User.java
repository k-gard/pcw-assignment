package com.pcw.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
  @GenericGenerator(name = "seq", strategy="increment")
  private int id;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false,unique = true)
  private String userName;
  @Column(nullable = false)

  private String password;
  @Column(nullable = false)
  private String role;


  public User(String firstName, String lastName, String role,String userName,String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.userName = userName;
    this.password = password;
  }
  public User(JsonObject jsonObject){
   this.firstName = jsonObject.getString("firstName");
   this.lastName = jsonObject.getString("firstName");
   this.role= jsonObject.getString("role");
   this.userName = jsonObject.getString("userName");;
   this.password = jsonObject.getString("password");;}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public User() {
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "{" +"\n"+
            "\"id\": " + id +" ," +
            "\"firstName\":" + "\"" + firstName +"\"" + "," +
            "\"lastName\":" +"\"" +  lastName +"\"" + "," +
            "\"role\":" +"\"" +  role +"\"" +
            "}";
  }
}
