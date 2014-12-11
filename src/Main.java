
import Screens.*;
import Tools.GameConfig;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.VideoMode;

import java.awt.*;


/**
 * @Class Main
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe main
 *
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("début du jeu");

    	//Initialisation de la configuration de jeu
    	GameConfig.configKeyBindings();



        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int WINDOW_H = (int)tailleEcran.getHeight();
        int WINDOW_W =  (int)tailleEcran.getWidth();

        RenderWindow window1 = new RenderWindow(new VideoMode(WINDOW_W,WINDOW_H), "Projet POO",-1);//-1=fullscreen
        window1.setFramerateLimit(60);
        window1.setKeyRepeatEnabled(true);

        // création d'une vue à partir de la zone rectangulaire du monde 2D à voir
        View view1 = new View(new FloatRect(0, 0, WINDOW_W, WINDOW_H));
        window1.setView(view1);

        //Applications variables
        cScreen[] Screens = new cScreen[7];
        int screen = 0;
        Screens[0] = new BootSplash();
        Screens[1] = new MainMenu();
        Screens[4] = new SelectMode();
        Screens[5] = new SelectPersoLocal();
        Screens[3] = new SelectPerso();
        Screens[2] = new GameLoop();

        while (screen >= 0) {
            screen = Screens[screen].Run(window1);
        }
        System.out.println("fin du jeu");
    }
}
