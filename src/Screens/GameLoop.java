package Screens;

import Entities.Player;
import Graphics.EntityTexture;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop extends cScreen {

    private Vector2i pos;
    private int menu;
    private int nb_choix_menu;

    private final int mainMenu = 1;
    private final int exit = -1;

    int[] state = IDLE;
    boolean direction = true;

    public GameLoop() {
        pos = new Vector2i(0, 0);
        menu = 0;
        nb_choix_menu = 3;
    }

    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        int AppX = App.getSize().x;
        int AppY = App.getSize().y;
        loadText("play", App.getSize().x / 2, 40, 2 * taille_Font_base);
        loadSpriteSheet("res/img/mario-spritesheet.png", 0.007f*AppY);
    }

    public int Run(RenderWindow App) {

        loadScreenObjects(App);

        boolean Running = true;

        startMusic("res/sound/tower.ogg");

        while (Running) {

            int returnValue = eventManager(App);
            if (returnValue <= 50)
                return returnValue;

            App.clear(Color.RED);
            for (int i = screenObject.size() - 1; i > -1; i--) {
                if (screenObject.get(i) instanceof Sprite) {
                    updateTexture((Sprite) screenObject.get(i), decideState(state), direction);
                   if(decideState(SHOOT)==SHOOT[SHOOT.length-1] && state==SHOOT){
                        state=IDLE;
                    }
                    System.out.println("state "+decideState(state)+" "+ EntityTexture.dureeeAnimation);
                    //((Sprite) screenObject.get(i)).setPosition(App.getSize().x / 2, App.getSize().y / 2);
                }
                App.draw(screenObject.get(i));
            }
            App.display();
        }

        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    public int eventManager(RenderWindow App) {
        //Verifying events
        for (Event event : App.pollEvents()) {
            // Window closed
            if (event.type == event.type.CLOSED) {
                return (exit);
            }
            int returnValueKeyboard = keyboardManager(event, App);
            if (returnValueKeyboard <= 50)
                return returnValueKeyboard;
        }

        //si on ne quitte pa s cet écran
        return 100;
    }


    public int keyboardManager(Event event, RenderWindow App) {
        //Key pressed
        if (event.type == Event.Type.KEY_PRESSED) {
            event.asKeyEvent();

            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                sound.stop();
                return mainMenu;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
                state=WALK;
                direction=LEFT;
            }
           else if (Keyboard.isKeyPressed(Keyboard.Key.V)) {
                state=SHOOT;
            }
            else if (Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
                state=JUMP;
            }

            else if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
                state=WALK;
                direction=RIGHT;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
            }
        }
        else if(event.type == Event.Type.KEY_RELEASED){
            if(state==WALK)
                state=IDLE;



        }
        //si on ne quitte pas cet écran
        return 100;
    }

}


