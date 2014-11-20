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
public class MainMenu extends cScreen implements iMenu{

    private Vector2i pos;
    private int menu;
    private int nb_choix_menu;

    private final int play = -1;
    private final int config = -1;
    private final int exit = -1;

    public MainMenu() {
        pos = new Vector2i(0,0);
        menu = 0;
        nb_choix_menu = 3;
    }

    private void loadScreenObjects(RenderWindow App){
        loadFont( "res/font/Volter__28Goldfish_29.ttf");

        int taille_Font_base = 60;
        int offsetX =App.getSize().x/8;
        int offsety = App.getSize().y/13;
        loadText("Play", 2*App.getSize().x/3+offsetX,App.getSize().y/3+offsety,taille_Font_base);
        loadText("Config", App.getSize().x/2+offsetX,App.getSize().y/2+offsety,taille_Font_base);
        loadText("Exit", App.getSize().x/3+offsetX,2*App.getSize().y/3+offsety,taille_Font_base);

        loadText("Titre", App.getSize().x/2,40,2*taille_Font_base);
        ((Text)screenObject.get(3)).setColor(light_green);
        loadText("Furious Cat Interactive ",App.getSize().x/2, App.getSize().y-25,20);
        ((Text)screenObject.get(4)).setColor(light_green);
        newRect(App.getSize().x, App.getSize().y/5, 0 ,0, dark_green);
        newRect(App.getSize().x, App.getSize().y/11, 0 ,10*App.getSize().y/11, dark_green);
        loadImage("res/img/logo.png",App.getSize().x/6,App.getSize().y/4);
        ((Sprite)screenObject.get(7)).setScale(0.75f,0.75f);
    }

    public int Run(RenderWindow App){

        loadScreenObjects(App);

        boolean Running = true;

        startMusic("res/sound/tower.ogg");


        while (Running)
        {
            int returnValue = eventManager(App);
            if(returnValue<=50)
                return  returnValue;

            menuSelectionne(menu);

            App.clear(background_green);
            for(int i=screenObject.size()-1 ;i>-1;i--) {
                App.draw(screenObject.get(i));
            }
            App.display();
        }

        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    public int eventManager(RenderWindow App){
        //Verifying events
        for (Event event : App.pollEvents())
        {
            // Window closed
            if (event.type == event.type.CLOSED)
            {
                return (exit);
            }

            int returnValueMouse = mouseManager(event, App);
            if(returnValueMouse<=50)
                return  returnValueMouse;

            int returnValueKeyboard = keyboardManager(event, App);
            if(returnValueKeyboard <=50)
                return  returnValueKeyboard ;
        }

        //si on ne quitte pa s cet écran
        return 100;
    }

    public int mouseManager(Event event, RenderWindow App){
        if (event.type == Event.Type.MOUSE_MOVED) {
            event.asMouseEvent();
            pos = Mouse.getPosition(App);
            for (int i = 0; i < nb_choix_menu; i++)
                if ( ((Text)screenObject.get(i)).getGlobalBounds().contains((float) pos.x, (float) pos.y))
                    menu = i;
        }

        //click de la souris
        if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
            event.asMouseEvent();
            sound.stop();
            return choixValide();
        }

        //si on ne quitte pas cet écran
        return 100;
    }

    public int keyboardManager(Event event, RenderWindow App){
        //Key pressed
        if (event.type == Event.Type.KEY_PRESSED){
            event.asKeyEvent();

            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
                return  exit;

            if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)){
                menu++;
                if(menu>nb_choix_menu-1)
                    menu = 0;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
                menu--;
                if(menu<0)
                    menu = 2;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
                sound.stop();
                return choixValide();
            }
        }
        //si on ne quitte pas cet écran
        return 100;
    }

    public void menuSelectionne(int numero){
        for(int i =0; i<nb_choix_menu;i++)        {
            if(i==numero)
                ((Text)screenObject.get(i)).setColor(light_green);
            else
                ((Text)screenObject.get(i)).setColor(dark_green);
        }
    }

    public int choixValide(){
        int returnvalue =100;
        switch (menu){
            case 0:
                returnvalue = play;
                break;
            case 1:
                returnvalue = config;
                break;
            case 2:
                returnvalue = exit;
                break;
        }
        return returnvalue;
    }
}
