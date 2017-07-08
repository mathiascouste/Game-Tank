package fr.geekies.model.positionable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Weapon extends Positionable {

  @JsonIgnore
  private int range;

  @JsonIgnore
  private int dammage;

  private Positionable origin;

  public Weapon(double width, double height, double altitude, double speed, int range, int dammage,
      Positionable origin) {
    super(width, height, altitude, speed);
    this.range = range;
    this.dammage = dammage;
    this.origin = origin;
  }

  public int getRange() {
    return range;
  }

  public void setRange(int range) {
    this.range = range;
  }

  public int getDammage() {
    return dammage;
  }

  public void setDammage(int dammage) {
    this.dammage = dammage;
  }

  public Positionable getOrigin() {
    return origin;
  }

  public void setOrigin(Positionable origin) {
    this.origin = origin;
  }
}
