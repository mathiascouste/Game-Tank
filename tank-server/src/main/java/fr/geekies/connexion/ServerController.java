package fr.geekies.connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.geekies.TankServer;

public class ServerController implements Runnable {
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_TOTAL = "total";
    private TankServer mainClass;

    private BufferedReader in;

    private String command = "";

    private Thread thread;

    public ServerController(TankServer mainClass) {
        this.mainClass = mainClass;
        in = new BufferedReader(new InputStreamReader(System.in));
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while ((command = in.readLine()) != null) {
                switch (command.toLowerCase()) {
                case COMMAND_QUIT:
                    System.exit(0);
                    break;
                case COMMAND_TOTAL:
                    System.out.println("Number of connected clients: " + this.mainClass.getNbClients());
                    break;
                default:
                    System.out.println("*** Error : unsupported command ***");
                }
                System.out.flush();
            }
        } catch (IOException e) {
        }
    }
}
