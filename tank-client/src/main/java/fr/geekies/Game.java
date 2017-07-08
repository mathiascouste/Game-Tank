package fr.geekies;

import java.util.ArrayList;
import java.util.List;

import fr.geekies.model.positionable.Positionable;

public class Game {
    private static List<Positionable> positionables = new ArrayList<Positionable>();

    public Game() {
    }

    public static List<Positionable> getPositionables() {
        return positionables;
    }

    public static void setPositionables(List<Positionable> positionables) {
        Game.positionables = positionables;
    }
}
