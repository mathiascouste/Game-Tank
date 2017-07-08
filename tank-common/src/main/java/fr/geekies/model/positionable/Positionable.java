package fr.geekies.model.positionable;

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = Mine.class, name = "Mine"),
    @JsonSubTypes.Type(value = Missile.class, name = "Missile"),
    @JsonSubTypes.Type(value = Tank.class, name = "Tank") })
public abstract class Positionable {

  private static final AtomicLong counter = new AtomicLong();

  private long id;

  private double xPos;

  private double yPos;

  @JsonIgnore
  private double speed;

  @JsonIgnore
  private double altitude;

  private double angle;

  @JsonIgnore
  private double width;

  @JsonIgnore
  private double height;

  public Positionable(double width, double height, double altitude, double speed) {
    this.id = counter.incrementAndGet();
    this.width = width;
    this.height = height;
    this.altitude = altitude;
    this.speed = speed;
    this.xPos = 0;
    this.yPos = 0;
    this.angle = 0;
  }

  public void forward() {
    this.setxPos(this.getxPos() + this.getSpeed() * Math.cos(this.getAngle()));
    this.setyPos(this.getyPos() + this.getSpeed() * Math.sin(this.getAngle()));
  }

  public void backward() {
    this.setxPos(this.getxPos() - 0.5 * this.getSpeed() * Math.cos(this.getAngle()));
    this.setyPos(this.getyPos() - 0.5 * this.getSpeed() * Math.sin(this.getAngle()));
  }

  public void turn(int i) {
    double angleDifference = 0.6 * i * Math.PI / 100;
    this.setAngle(this.getAngle() + angleDifference);
  }

  public double distanceTo(Positionable positionable) {
    return Math.sqrt(Math.pow(this.xPos - positionable.xPos, 2) + Math.pow(this.yPos - positionable.yPos, 2));
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getxPos() {
    return xPos;
  }

  public void setxPos(double xPos) {
    this.xPos = xPos;
  }

  public double getyPos() {
    return yPos;
  }

  public void setyPos(double yPos) {
    this.yPos = yPos;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getAltitude() {
    return altitude;
  }

  public void setAltitude(double altitude) {
    this.altitude = altitude;
  }
}
