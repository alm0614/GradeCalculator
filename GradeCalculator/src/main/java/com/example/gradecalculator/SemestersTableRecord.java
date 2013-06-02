package com.example.gradecalculator;

/**
 * Created by Andrew on 5/20/13.
 */
public class SemestersTableRecord {
  private Integer id;
  private Integer sequence;
  private String name;
  private Double gpa;
  private Integer credits;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getGpa() {
    return gpa;
  }

  public void setGpa(double gpa) {
    this.gpa = gpa;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  @Override
  public String toString() {
    return name;// + " Credits: " + credits + " GPA: " + gpa;
  }
}
