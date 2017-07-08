package fr.geekies;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.geekies.connexion.ClientConnexion;
import fr.geekies.connexion.ServerController;
import fr.geekies.model.Game;

public class TankServer {
    private static final int MAX_CLIENT = 10;

    private int nbClients = 0;

    private List<ClientConnexion> clientConnexions;

    private int port;

    private Game game;

    public TankServer(int intValue) {
        this.port = intValue;
        this.clientConnexions = new ArrayList<ClientConnexion>();
        this.game = new Game(this.clientConnexions);
    }

    static private void printWelcome(Integer port) {
        System.out.println("--------");
        System.out.println("Starting on port : " + port.toString());
        System.out.println("--------");
        System.out.println("Exit : enter \"quit\"");
        System.out.println("Number of clients : enter \"total\"");
        System.out.println("--------");
    }

    public static void main(String args[]) {
        Integer port;
        if (args.length <= 0) {
            port = new Integer("6666");
        } else {
            port = new Integer(args[0]);
        }

        TankServer serv = new TankServer(port.intValue());
        serv.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            new ServerController(this);
            new Thread(this.game).start();
            serverSocket = new ServerSocket(this.port);
            printWelcome(this.port);
            while (nbClients < MAX_CLIENT) {
                addClient(serverSocket.accept());
                nbClients++;
            }
        } catch (Exception e) {
        }
    }

    private void addClient(Socket socket) {
        ClientConnexion client = new ClientConnexion(socket, this, this.game);
        this.clientConnexions.add(client);
        new Thread(client).start();
    }

    public int getNbClients() {
        return this.nbClients;
    }

    public void removeClient(ClientConnexion clientConnexion) {
        this.clientConnexions.remove(clientConnexion);
        this.game.removeController(clientConnexion.getTankController());
        this.nbClients--;
    }
}
