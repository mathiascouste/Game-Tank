package fr.geekies;

import fr.geekies.connexion.ServerConnexion;
import fr.geekies.view.Frame;

public class TankClient {

    public static void main(String[] args) throws InterruptedException {

        ServerConnexion server = new ServerConnexion();
        if (server.connect()) {
            server.startReceiving();
        }

        new Frame(server);
    }
}
