package fr.geekies.model.positionable;

public class Missile extends Weapon {
  public Missile() {
    this(null);
  }

  public Missile(Positionable origin) {
    super(9, 30, 2, 5, 46, 25, origin);
  }
}
