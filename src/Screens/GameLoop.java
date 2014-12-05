package Screens;

import Entities.Player;
import Graphics.EntityTexture;
import InputGameLoop.InputMananger;
import Tools.KeyboardActions;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop extends cScreen {

    public final static int mainMenu = 1;
    public final static int exit = -1;
    public static int returnValue=1000;

    public static Player p1;

    private InputMananger inputGL;

    public GameLoop() {
    }

    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        int AppX = App.getSize().x;
        int AppY = App.getSize().y;
        loadText("play", App.getSize().x / 2, 40, 2 * taille_Font_base);
        p1 = loadPlayer("res/img/pikachu-spritesheet.png", 0.007f*AppY);
    }

    public int Run(RenderWindow App) {

        loadScreenObjects(App);
        inputGL= new InputMananger(App);

        boolean Running = true;

       p1.setPosition(App.getSize().x / 2, App.getSize().y / 2);

        startMusic("res/sound/tower.ogg");
        inputGL.start();
        Thread threaPlayer1 = new Thread(p1);
        threaPlayer1.start();

        while (Running) {

           //returnvalue mis à jour par thread input
            inputGL.run();
            if (returnValue <= 50)
                return returnValue;

            p1.run();

            App.clear(Color.RED);
            App.draw(p1);
            for (int i = screenObject.size() - 1; i > -1; i--) {
                App.draw(screenObject.get(i));
            }
            App.display();
        }

        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    public static int retourne(int nextMenu){
        sound.stop();
        screenObject.clear();
        return nextMenu;
    }
}

