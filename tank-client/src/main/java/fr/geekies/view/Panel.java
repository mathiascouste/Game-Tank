package fr.geekies.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.geekies.Config;
import fr.geekies.Game;
import fr.geekies.model.positionable.Mine;
import fr.geekies.model.positionable.Missile;
import fr.geekies.model.positionable.Positionable;
import fr.geekies.model.positionable.Tank;
import fr.geekies.utils.ImageLoader;

public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;

  private int height, width;

  public Panel(int l, int h) {
    super();
    this.height = h;
    this.width = l;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;

    g2d.drawImage(ImageLoader.getImage("background"), 0, 0, this.width, this.height, this);
    Game.getPositionables().stream().sorted((p1, p2) -> Double.compare(p1.getAltitude(), p2.getAltitude()))
        .forEachOrdered(p -> {
          paintPositionable(p, g2d);
        });
  }

  private void paintPositionable(Positionable p, Graphics2D g2d) {
    if (p instanceof Tank) {
      paintTank((Tank)p, g2d);
    } else if (p instanceof Missile) {
      paintMissile((Missile)p, g2d);
    } else if (p instanceof Mine) {
      paintMine((Mine)p, g2d);
    }
  }

  private void paintTank(Tank tank, Graphics2D g) {
    g.setColor(Color.red);
    g.rotate(tank.getAngle(), tank.getxPos(), tank.getyPos());
    g.drawImage(ImageLoader.getImage("tankcorps"), (int)(tank.getxPos() - tank.getHeight() / 2),
        (int)(tank.getyPos() - tank.getWidth() / 2), (int)tank.getHeight(), (int)tank.getWidth(), this);
    g.rotate(-tank.getAngle(), tank.getxPos(), tank.getyPos());

    g.setColor(Color.blue);
    g.rotate(tank.getTowerAngle(), tank.getxPos(), tank.getyPos());
    g.drawImage(ImageLoader.getImage("tourelle"), ((int)tank.getxPos() - tank.getTowerLenght() / 2 + 10),
        ((int)tank.getyPos() - tank.getTowerWidth() / 2 - 1), tank.getTowerLenght(), tank.getTowerWidth(), this);

    g.rotate(-tank.getTowerAngle(), tank.getxPos(), tank.getyPos());

    double lifeRatio = (double)tank.getLife() / (double)Config.TANK_MAX_LIFE;
    g.setColor(Color.black);
    g.drawRect((int)tank.getxPos() - 11, (int)tank.getyPos() - 3, 22, 6);
    g.setColor(Color.red);
    g.fillRect((int)tank.getxPos() - 10, (int)tank.getyPos() - 2, 20, 4);
    g.setColor(Color.green);
    g.fillRect((int)tank.getxPos() - 10, (int)tank.getyPos() - 2, (int)(20 * lifeRatio), 4);
  }

  private void paintMissile(Missile missile, Graphics2D g) {
    g.rotate(missile.getAngle(), missile.getxPos(), missile.getyPos());

    g.drawImage(ImageLoader.getImage("missile"), (int)(missile.getxPos() - missile.getHeight() / 2),
        (int)(missile.getyPos() - missile.getWidth() / 2), (int)missile.getHeight(), (int)missile.getWidth(), this);

    g.rotate(-missile.getAngle(), missile.getxPos(), missile.getyPos());
  }

  private void paintMine(Mine mine, Graphics2D g) {
    g.rotate(mine.getAngle(), mine.getxPos(), mine.getyPos());

    g.drawImage(ImageLoader.getImage("mine"), (int)(mine.getxPos() - mine.getHeight() / 2),
        (int)(mine.getyPos() - mine.getWidth() / 2), (int)mine.getHeight(), (int)mine.getWidth(), this);

    g.rotate(-mine.getAngle(), mine.getxPos(), mine.getyPos());
  }
}
