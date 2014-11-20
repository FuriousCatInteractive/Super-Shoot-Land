package Screens;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop extends cScreen {

    private Vector2i pos;
    private int menu;
    private int nb_choix_menu;

    private final int play = -1;
    private final int config = -1;
    private final int exit = -1;

    public GameLoop() {
        pos = new Vector2i(0, 0);
        menu = 0;
        nb_choix_menu = 3;
    }

    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        loadText("play", App.getSize().x / 2, 40, 2 * taille_Font_base);
    }

    public int Run(RenderWindow App) {

        loadScreenObjects(App);

        boolean Running = true;

        startMusic("res/sound/tower.ogg");


        while (Running) {
            int returnValue = eventManager(App);
            if (returnValue <= 50)
                return returnValue;

            App.clear(background_green);
            for (int i = screenObject.size() - 1; i > -1; i--) {
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

            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
                return exit;

            if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {

            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
            }
        }
        //si on ne quitte pas cet écran
        return 100;
    }
}


