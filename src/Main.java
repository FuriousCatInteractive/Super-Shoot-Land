import Screens.*;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.VideoMode;

//pour connaitre la taille de la fenetre
import java.awt.*;

/**
 * Created by coco on 14-11-16.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("début du jeu");

        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int WINDOW_H = (int)tailleEcran.getHeight();
        int WINDOW_W = (int)tailleEcran.getWidth();

        RenderWindow window1 = new RenderWindow(new VideoMode(WINDOW_W,WINDOW_H), "Projet POO",-1);//-1=fullscreen
        window1.setFramerateLimit(60);
       // window1.setKeyRepeatEnabled(true);

        // création d'une vue à partir de la zone rectangulaire du monde 2D à voir
        View view1 = new View(new FloatRect(0, 0, WINDOW_W, WINDOW_H));
        window1.setView(view1);

        //Applications variables
        cScreen[] Screens = new cScreen[3];
        int screen = 0;
        Screens[0] = new BootSplash();
        Screens[1] = new MainMenu();
        Screens[2] = new GameLoop();

        while (screen >= 0) {
            screen = Screens[screen].Run(window1);
        }
        System.out.println("fin du jeu");
    }
}
