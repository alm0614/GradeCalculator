package com.example.gradecalculator;

/**
 * Created by Andrew on 5/22/13.
 */
public class GradingScaleTableRecord {
  private String name;
  private Double value;
  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
