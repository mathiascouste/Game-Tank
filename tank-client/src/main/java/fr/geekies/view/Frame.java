package fr.geekies.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import fr.geekies.Config;
import fr.geekies.connexion.ServerConnexion;
import fr.geekies.utils.ImageLoader;
import fr.geekies.utils.KeyboardHandler;

public class Frame extends JFrame {
    private static final long serialVersionUID = 5919331288692754557L;

    private ServerConnexion server;

    private KeyboardHandler keyboardHandler;

    private Panel pan;

    public Frame(ServerConnexion server) {
        this.server = server;
        this.keyboardHandler = new KeyboardHandler(this.server);

        this.pan = new Panel(Config.AREA_WIDTH, Config.AREA_HEIGHT);

        this.setTitle("Animation");
        this.setSize(Config.AREA_WIDTH, Config.AREA_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(pan);
        this.setVisible(true);
        this.addKeyListener(new KeyboardListener());

        ImageLoader.init();

        go();
    }

    private void go() {
        for (;;) {
            pan.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class KeyboardListener implements KeyListener {
        // Boolean value: false is nothing, true is pressed
        Map<Integer, Boolean> antiFlickering = new HashMap<>();

        @Override
        public void keyPressed(KeyEvent event) {
            Boolean state = antiFlickering.get(event.getKeyCode());
            if (state == null) {
                antiFlickering.put(event.getKeyCode(), true);
                state = false;
            }
            if (!state) {
                antiFlickering.put(event.getKeyCode(), true);
                keyboardHandler.press(event.getKeyCode());
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            antiFlickering.put(event.getKeyCode(), false);
            keyboardHandler.release(event.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent event) {
        }
    }
}
