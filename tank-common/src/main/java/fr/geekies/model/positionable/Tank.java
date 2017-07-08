package fr.geekies.model.positionable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.geekies.Config;

public class Tank extends Positionable {

  private int life;

  @JsonIgnore
  private int towerWidth;

  @JsonIgnore
  private int towerLenght;

  private double towerAngle;

  public Tank() {
    super(46, 64, 1, 2);

    this.life = Config.TANK_MAX_LIFE;
    this.towerWidth = 46;
    this.towerLenght = 64;
    this.towerAngle = this.getAngle();
  }

  @Override
  public void turn(int i) {
    super.turn(i);
    double angleDifference = 0.6 * i * Math.PI / 100;
    this.towerAngle += angleDifference;
  }

  public Missile fireMissile() {
    return (Missile)alignWeapon(new Missile(this));
  }

  public Mine dropMine() {
    return (Mine)alignWeapon(new Mine(this));
  }

  private Weapon alignWeapon(Weapon weapon) {
    weapon.setAngle(this.towerAngle);
    weapon.setxPos(this.getxPos());
    weapon.setyPos(this.getyPos());
    return weapon;
  }

  public void turnTower(int i) {
    this.towerAngle += 0.3 * i * Math.PI / 100;

  }

  public void applyDammage(int dammage) {
    this.life -= dammage;
  }

  @JsonIgnore
  public boolean isAlive() {
    return this.life > 0;
  }

  public int getTowerWidth() {
    return towerWidth;
  }

  public void setTowerWidth(int towerWidth) {
    this.towerWidth = towerWidth;
  }

  public int getTowerLenght() {
    return towerLenght;
  }

  public void setTowerLenght(int towerLenght) {
    this.towerLenght = towerLenght;
  }

  public double getTowerAngle() {
    return towerAngle;
  }

  public void setTowerAngle(double towerAngle) {
    this.towerAngle = towerAngle;
  }

  public int getLife() {
    return life;
  }

  public void setLife(int life) {
    this.life = life;
  }
}
