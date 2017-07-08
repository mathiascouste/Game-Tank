package fr.geekies.utils;

import fr.geekies.connexion.ServerConnexion;
import fr.geekies.events.BackwardStart;
import fr.geekies.events.BackwardStop;
import fr.geekies.events.DropMine;
import fr.geekies.events.Fire;
import fr.geekies.events.ForwardStart;
import fr.geekies.events.ForwardStop;
import fr.geekies.events.TowerTurnLeftStart;
import fr.geekies.events.TowerTurnLeftStop;
import fr.geekies.events.TowerTurnRightStart;
import fr.geekies.events.TowerTurnRightStop;
import fr.geekies.events.TurnLeftStart;
import fr.geekies.events.TurnLeftStop;
import fr.geekies.events.TurnRightStart;
import fr.geekies.events.TurnRightStop;

public class KeyboardHandler {
    private static final int KEY_FIRE = 90;
    private static final int KEY_TURN_LEFT = 37;
    private static final int KEY_TURN_RIGHT = 39;
    private static final int KEY_FORWARD = 38;
    private static final int KEY_BACKWARD = 40;
    private static final int KEY_ROTATE_LEFT = 65;
    private static final int KEY_ROTATE_RIGHT = 69;
    private static final int KEY_DROP_MINE = 32;

    private ServerConnexion server;

    public KeyboardHandler(ServerConnexion server) {
        this.server = server;
    }

    public void press(int n) {
        switch (n) {
        case KEY_FIRE:
            this.server.sendEvent(new Fire());
            break;
        case KEY_TURN_LEFT:
            this.server.sendEvent(new TurnLeftStart());
            break;
        case KEY_TURN_RIGHT:
            this.server.sendEvent(new TurnRightStart());
            break;
        case KEY_FORWARD:
            this.server.sendEvent(new ForwardStart());
            break;
        case KEY_BACKWARD:
            this.server.sendEvent(new BackwardStart());
            break;
        case KEY_ROTATE_LEFT:
            this.server.sendEvent(new TowerTurnLeftStart());
            break;
        case KEY_ROTATE_RIGHT:
            this.server.sendEvent(new TowerTurnRightStart());
            break;
        case KEY_DROP_MINE:
            this.server.sendEvent(new DropMine());
            break;
        }
    }

    public void release(int n) {
        switch (n) {
        case KEY_TURN_LEFT:
            this.server.sendEvent(new TurnLeftStop());
            break;
        case KEY_TURN_RIGHT:
            this.server.sendEvent(new TurnRightStop());
            break;
        case KEY_FORWARD:
            this.server.sendEvent(new ForwardStop());
            break;
        case KEY_BACKWARD:
            this.server.sendEvent(new BackwardStop());
            break;
        case KEY_ROTATE_LEFT:
            this.server.sendEvent(new TowerTurnLeftStop());
            break;
        case KEY_ROTATE_RIGHT:
            this.server.sendEvent(new TowerTurnRightStop());
            break;
        case KEY_DROP_MINE:
            break;
        }
    }
}
