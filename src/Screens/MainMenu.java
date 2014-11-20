package Screens;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

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

    public int Run(RenderWindow App){

        loadFont( "res/font/KGBlankSpaceSketch.ttf");

        int taille_Font_base = 40;

        loadText("Play", App.getSize().x/2,App.getSize().y/2-50,taille_Font_base);
        loadText("Config", App.getSize().x/2,App.getSize().y/2,taille_Font_base);
        loadText("Exit", App.getSize().x/2,App.getSize().y/2+50,taille_Font_base);

        loadText("Titre", App.getSize().x/2,40,2*taille_Font_base);
        loadText("Furious Cat Interactive ",App.getSize().x/2, App.getSize().y-25,20);

        boolean Running = true;

        startMusic("res/sound/king.it.ogg");


        while (Running)
        {
            int returnValue = eventManager(App);
            if(returnValue<=50)
                return  returnValue;

            menuSelectionne(menu);

            App.clear();
            for(int i=0 ;i<texts.size();i++)
                App.draw(texts.get(i));
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
                if (texts.get(i).getGlobalBounds().contains((float) pos.x, (float) pos.y))
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
                texts.get(i).setColor(Color.BLUE);
            else
                texts.get(i).setColor(Color.WHITE);
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
