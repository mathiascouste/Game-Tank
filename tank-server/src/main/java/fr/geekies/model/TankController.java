package fr.geekies.model;

import fr.geekies.events.Event;
import fr.geekies.model.positionable.Tank;

public class TankController {
    private Tank tank;

    private boolean fire = false;
    private boolean dropmine = false;
    private boolean forward = false;
    private boolean backward = false;
    private boolean turnleft = false;
    private boolean turnright = false;
    private boolean towerturnleft = false;
    private boolean towerturnright = false;

    public TankController(Tank tank) {
        this.tank = tank;
    }

    public void handleEvent(Event event) {
        switch (event.getType()) {
        case "fire":
            fire = true;
            break;
        case "dropmine":
            dropmine = true;
            break;

        case "forwardstart":
            forward = true;
            backward = false;
            break;
        case "forwardstop":
            forward = false;
            break;
        case "backwardstart":
            backward = true;
            forward = false;
            break;
        case "backwardstop":
            backward = false;
            break;

        case "turnleftstart":
            turnleft = true;
            turnright = false;
            break;
        case "turnleftstop":
            turnleft = false;
            break;
        case "turnrightstart":
            turnright = true;
            turnleft = false;
            break;
        case "turnrightstop":
            turnright = false;
            break;

        case "towerturnleftstart":
            towerturnleft = true;
            towerturnright = false;
            break;
        case "towerturnleftstop":
            towerturnleft = false;
            break;
        case "towerturnrightstart":
            towerturnright = true;
            towerturnleft = false;
            break;
        case "towerturnrightstop":
            towerturnright = false;
            break;
        }

    }

    public void applyAction(Game game) {
        if (fire) {
            game.addPositionable(this.tank.fireMissile());
            fire = false;
        }
        if (dropmine) {
            game.addPositionable(this.tank.dropMine());
            dropmine = false;
        }
        if (forward) {
            this.tank.forward();
        }
        if (backward) {
            this.tank.backward();
        }
        if (turnleft) {
            this.tank.turn(-2);
        }
        if (turnright) {
            this.tank.turn(2);
        }
        if (towerturnleft) {
            this.tank.turnTower(-3);
        }
        if (towerturnright) {
            this.tank.turnTower(3);
        }
    }

    public Tank getTank() {
        return tank;
    }
}
