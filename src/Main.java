import Entities.KeyBinding;
import Screens.*;
import Tools.Const;
import Tools.GameConfig;
import Tools.XmlKeyBindingsParser;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.VideoMode;








//pour connaitre la taille de la fenetre
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by coco on 14-11-16.
 */
public class Main {

    public static void main(String[] args) {
    	
    	//Initialisation de la configuration de jeu
    	GameConfig.configKeyBindings();
    	

        System.out.println("début du jeu");

        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int WINDOW_H = 700;//(int)tailleEcran.getHeight();
        int WINDOW_W = 700;//(int)tailleEcran.getWidth();

        RenderWindow window1 = new RenderWindow(new VideoMode(WINDOW_W,WINDOW_H), "Projet POO"/*,-1*/);//-1=fullscreen
        window1.setFramerateLimit(60);
        window1.setKeyRepeatEnabled(true);

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
