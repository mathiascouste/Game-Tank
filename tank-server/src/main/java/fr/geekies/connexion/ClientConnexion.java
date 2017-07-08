package fr.geekies.connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.geekies.TankServer;
import fr.geekies.events.Event;
import fr.geekies.model.Game;
import fr.geekies.model.TankController;

public class ClientConnexion implements Runnable {

    private Socket socket;

    private TankServer tankServer;

    private TankController tankController;

    private Game game;

    private PrintWriter out;

    private BufferedReader in;

    public ClientConnexion(Socket socket, TankServer tankServer, Game game) {
        this.socket = socket;
        this.tankServer = tankServer;
        this.game = game;
        try {
            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addTankToGame(this.game);
    }

    private void addTankToGame(Game game) {
        this.tankController = game.addATank();
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            while (true) {
                String eventString = this.in.readLine();
                Event event = mapper.readValue(eventString, Event.class);
                this.tankController.handleEvent(event);
            }
        } catch (IOException e) {
            this.tankServer.removeClient(this);
            System.out.println("Client left the game");
        }
    }

    public void sendGameState(String gameState) {
        this.out.println(gameState);
        this.out.flush();
    }

    public TankController getTankController() {
        return this.tankController;
    }
}
