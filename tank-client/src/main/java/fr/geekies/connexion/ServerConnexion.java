package fr.geekies.connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.geekies.Game;
import fr.geekies.events.Event;
import fr.geekies.model.GameState;

public class ServerConnexion implements Runnable {
    private int port;

    private String ip;

    private Socket socket;

    private PrintWriter out;

    private BufferedReader in;

    public ServerConnexion() {
        this("127.0.0.1", 6666);
    }

    public ServerConnexion(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public boolean connect() {
        try {
            InetAddress inetAdd = InetAddress.getByName(this.ip);
            this.socket = new Socket(inetAdd, this.port);

            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (socket != null) {
            return this.socket.isConnected();
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();

        String gameStateStr = "";
        while (true) {
            try {
                gameStateStr = this.in.readLine();
                GameState gameState = mapper.readValue(gameStateStr, GameState.class);
                Game.setPositionables(gameState.positionables);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startReceiving() {
        Thread th = new Thread(this);
        th.start();
    }

    public void sendEvent(Event event) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.out.println(mapper.writeValueAsString(event));
            this.out.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
