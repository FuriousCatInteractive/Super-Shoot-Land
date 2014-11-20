import Screens.*;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;

import java.awt.*;

//pur connaitre la taille de la fenetre
//import java.awt.*;

/**
 * Created by coco on 14-11-16.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("début du jeu");

        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int WINDOW_H = (int)tailleEcran.getHeight();
        int WINDOW_W= (int)tailleEcran.getWidth();

        RenderWindow window1 = new RenderWindow(new VideoMode(WINDOW_W, WINDOW_H), "Projet POO",-1);//-=fullscreen
        window1.setFramerateLimit(60);
        window1.setKeyRepeatEnabled(true);

        //Applications variables
        cScreen[] Screens = new cScreen[3];
        int screen = 0;
        Screens[0] = new BootSplash();
        Screens[1] = new MainMenu();
        Screens[2] = new GameLoop();

        while (screen >= 0) {
            screen = Screens[screen].Run(window1);
        }
        System.out.println("quitte le jeu proprement");
    }
}
