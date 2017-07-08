package fr.geekies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.geekies.Config;
import fr.geekies.connexion.ClientConnexion;
import fr.geekies.model.positionable.Positionable;
import fr.geekies.model.positionable.Tank;
import fr.geekies.model.positionable.Weapon;

public class Game implements Runnable {
    private GameState gameState;

    private static final long TIMESTEP = 25;

    private List<ClientConnexion> clientConnexions;

    private List<TankController> tankControllers;

    public Game(List<ClientConnexion> clientConnexions) {
        this.clientConnexions = clientConnexions;
        this.gameState = new GameState();
        this.tankControllers = new ArrayList<>();
    }

    public void addPositionable(Positionable positionable) {
        this.gameState.positionables.add(positionable);
    }

    public void removePositionable(Positionable positionable) {
        this.gameState.positionables.remove(positionable);
    }

    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();

            this.runStep();

            this.sendGameState();

            long elapsed = System.currentTimeMillis() - start;
            long toSleep = TIMESTEP - elapsed;
            if (toSleep >= 0) {
                try {
                    Thread.sleep(toSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runStep() {
        List<Weapon> weapons = this.gameState.positionables.stream()
                .filter(positionable -> positionable instanceof Weapon)
                .map(positionable -> (Weapon) positionable)
                .collect(Collectors.toList());
        List<Tank> tanks = this.gameState.positionables.stream()
                .filter(positionable -> positionable instanceof Tank)
                .map(positionable -> (Tank) positionable)
                .collect(Collectors.toList());

        this.executeTanksActions();
        this.executeWeaponsActions(weapons);
        this.executeInteractions(tanks, weapons);
        this.removeOutOfBoundWeapons(weapons);
    }

    private void executeTanksActions() {
        for (TankController controller : this.tankControllers) {
            controller.applyAction(this);
        }
    }

    private void executeWeaponsActions(List<Weapon> weapons) {
        for (Weapon weapon : weapons) {
            weapon.forward();
        }
    }

    private void executeInteractions(List<Tank> tanks, List<Weapon> weapons) {
        Set<Positionable> tanksToDestroy = new HashSet<>();
        for (Weapon weapon : weapons) {
            for (Tank tank : tanks) {
                if (weapon.getOrigin() != tank && weapon.getRange() >= weapon.distanceTo(tank)) {
                    tank.applyDammage(weapon.getDammage());
                    tanksToDestroy.add(weapon);
                    if (!tank.isAlive()) {
                        tanksToDestroy.add(tank);
                    }
                }
            }
        }

        Set<TankController> controllersToDestroy = new HashSet<>();
        tanksToDestroy.stream().filter(p -> p instanceof Tank).forEach(p -> {
            this.tankControllers.forEach(c -> {
                if (c.getTank() == p) {
                    controllersToDestroy.add(c);
                }
            });
        });

        this.gameState.positionables.removeAll(tanksToDestroy);
        controllersToDestroy.forEach(c -> this.removeController(c));
    }

    private void removeOutOfBoundWeapons(List<Weapon> weapons) {
        Set<Positionable> toDestroy = new HashSet<>();
        for (Weapon weapon : weapons) {
            double x = weapon.getxPos();
            double y = weapon.getyPos();
            if (x < 0 || y < 0 || x > Config.AREA_WIDTH || y > Config.AREA_HEIGHT) {
                toDestroy.add(weapon);
            }
        }
        this.gameState.positionables.removeAll(toDestroy);
    }

    private void sendGameState() {
        String gameState = this.getGameStateAsString();
        for (ClientConnexion client : this.clientConnexions) {
            client.sendGameState(gameState);
        }
    }

    private String getGameStateAsString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this.gameState);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public TankController addATank() {
        Tank tank = new Tank();
        tank.setxPos(tank.getHeight());
        tank.setyPos(tank.getWidth());
        this.addPositionable(tank);

        TankController controller = new TankController(tank);
        this.tankControllers.add(controller);

        return controller;
    }

    public void removeController(TankController tankController) {
        this.tankControllers.remove(tankController);
    }
}
