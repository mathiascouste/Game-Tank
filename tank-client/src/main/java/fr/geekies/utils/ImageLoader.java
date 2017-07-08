package fr.geekies.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {

  private static HashMap<String, Image> images = new HashMap<String, Image>();

  public static void addImage(String path, String name) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(ClassLoader.getSystemResource(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    ImageLoader.images.put(name, img);
  }

  public static void init() {
    String tab[] = { "tankcorps.png", "tourelle.png", "missile.png", "background.png", "mine.png" };
    String path = "images/";
    for (int i = 0; i < tab.length; i++) {
      ImageLoader.addImage(path + tab[i], tab[i].split("\\.")[0]);
    }
  }

  public static Image getImage(String name) {
    return ImageLoader.images.get(name);
  }
}
