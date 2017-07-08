package fr.geekies.model;

import java.util.ArrayList;
import java.util.List;

import fr.geekies.model.positionable.Positionable;

public class GameState {
  public List<Positionable> positionables;

  public GameState() {
    this.positionables = new ArrayList<Positionable>();
  }
}
