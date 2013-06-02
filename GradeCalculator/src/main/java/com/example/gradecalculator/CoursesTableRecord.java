package com.example.gradecalculator;

/**
 * Created by Andrew on 5/20/13.
 */
public class CoursesTableRecord {
  private Integer id;
  private Integer semester_id;
  private String name;
  private Integer credits;
  private Double grade;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSemester_id() {
    return semester_id;
  }

  public void setSemester_id(Integer semester_id) {
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
