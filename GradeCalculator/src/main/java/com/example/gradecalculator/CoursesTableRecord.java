package com.example.gradecalculator;

/**
 * Created by Andrew on 5/20/13.
 */
public class CoursesTableRecord {
  private int id;
  private int semester_id;
  private String name;
  private Integer credits;
  private Double grade;

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSemester_id() {
    return semester_id;
  }

  public void setSemester_id(int semester_id) {
    this.semester_id = semester_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCredits() {
    return credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  public Double getGrade() {
    return grade;
  }

  public void setGrade(Double grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return name;
  }
}
