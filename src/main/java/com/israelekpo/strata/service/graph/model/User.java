package com.israelekpo.strata.service.graph.model;

public class User {

  public static final String ID = "id";
  public static final String FNAME = "fname";
  public static final String LNAME = "lname";

  private String id;

  private String fname;

  private String lname;

  public User() {

  }

  public User(String id, String fname, String lname) {

    this.id = id;
    this.fname = fname;
    this.lname = lname;
  }

  public String getId() {
    return id;
  }

  public void setId(String object) {
    this.id = object;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }
}
