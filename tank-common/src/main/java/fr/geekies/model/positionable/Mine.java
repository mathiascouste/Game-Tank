package fr.geekies.model.positionable;

public class Mine extends Weapon {
  public Mine() {
    this(null);
  }

  public Mine(Positionable origin) {
    super(20, 20, 0, 0, 20, 75, origin);
  }
}
