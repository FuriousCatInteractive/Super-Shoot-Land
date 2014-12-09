package Screens;

import Entities.Player;
import Tools.*;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

/**
 * @Class SelectPerso
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe pour le menu de choix des personnages
 *
 */
public class SelectPerso extends cScreen implements iMenu{

    private Vector2i pos;
    private int menu;
    private int nb_choix_menu;

    private int AppX;
    private int AppY;

    public static int persoSelect;

    /**
     * Constructeur
     */
    public SelectPerso() {
        pos = new Vector2i(0,0);
        menu = 0;
        nb_choix_menu = 4;
        persoSelect=1;
    }

    /**
     * Créer et charge les éléments du screen
     * @param App
     */
    private void loadScreenObjects(RenderWindow App){
        loadFont( "res/font/Volter__28Goldfish_29.ttf");
        AppX = App.getSize().x;
        AppY = App.getSize().y;
        int taille_Font_base = (int)(0.06*AppY);
       // int offsetX =AppX/3-20;
       // int offsety = AppY/20;

        int hauteur_min = AppY/8;
        int largeur_min = AppX/8;
        int ecart_min = AppX/30;
        int origin_y_min = AppY/5+ecart_min;
        int origin_x_min = AppX/2-largeur_min/2;

        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+0*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+1*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+2*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+3*(ecart_min+hauteur_min),taille_Font_base);


        loadImage("res/img/miniature_pikachu.png",origin_x_min, origin_y_min, largeur_min,hauteur_min );
        loadImage("res/img/miniature_mario.png",origin_x_min, origin_y_min+1*(ecart_min+hauteur_min), largeur_min,hauteur_min );
        loadImage("res/img/miniature_link.png",origin_x_min, origin_y_min+2*(ecart_min+hauteur_min), largeur_min,hauteur_min );
        loadImage("res/img/miniature_megaman.png",origin_x_min, origin_y_min+3*(ecart_min+hauteur_min), largeur_min,hauteur_min );

        loadText("Select your Character", AppX/2,AppY/15,(int)1.6*taille_Font_base);
        ((Text)screenObject.get(screenObject.size()-1)).setColor(Const.light_green);

        loadText("Furious Cat Interactive - 2014",AppX/2, AppY-AppY/20, (int) (taille_Font_base/1.5));
        ((Text)screenObject.get(screenObject.size()-1)).setColor(Const.light_green);

        newRect(AppX, AppY/5, 0 ,0, Const.dark_green);
        newRect(AppX, 10, 0 ,AppY/5, Const.light_green);
        newRect(AppX, AppY/11, 0 ,10*AppY/11, Const.dark_green);
        newRect(AppX, 10, 0 ,10*AppY/11-10, Const.light_green);
    }

    /**
     * Méthode principale qui fait l'update
     * @param App
     * @return
     */
    public int Run(RenderWindow App){

      //  musicBackground.play();

        loadScreenObjects(App);

        boolean Running = true;

        loadImagePerso(persoSelect);

        while (Running)
        {
            int returnValue = eventManager(App);
            if(returnValue<=50)
                return  returnValue;

            menuSelectionne(menu);

            screenObject.remove(screenObject.size()-1);
            loadImagePerso(menu+1);


            App.clear(Const.background_green);
            for(int i=screenObject.size()-1 ;i>-1;i--) {
                App.draw(screenObject.get(i));
            }
            App.display();
        }

        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    /**
     * appelle le keybord et le mouse manager
     * @param App
     * @return
     */
    public int eventManager(RenderWindow App){
        //Verifying events
        for (Event event : App.pollEvents())
        {
            // Window closed
            if (event.type == Type.CLOSED)
            {
                screenObject.clear();
                return (Const.exit);
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

    /**
     * Regarde les événements liés à la souris
     * @param event
     * @param App
     * @return
     */
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
            persoSelect=menu+1;
            event.asMouseEvent();
           // musicBackground.stop();
            screenObject.clear();
            return choixValide();
        }

        //si on ne quitte pas cet écran
        return 100;
    }

    /**
     * Regarde les évenements liés au clavier
     * @param event
     * @param App
     * @return
     */
    public int keyboardManager(Event event, RenderWindow App){
        //Key pressed
        if (event.type == Event.Type.KEY_PRESSED){
            event.asKeyEvent();

            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)){
                screenObject.clear();
                return  Const.mainMenu;
            }


            if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)){
                menu++;
                if(menu>nb_choix_menu-1)
                    menu = 0;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
                menu--;
                if(menu<0)
                    menu = nb_choix_menu-1;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
                    persoSelect=menu+1;
                   // musicBackground.stop();
                    screenObject.clear();
                    return choixValide();

            }
        }
        //si on ne quitte pas cet écran
        return 100;
    }

    /**
     * Highlight si selectionné
     * @param numero
     */
    public void menuSelectionne(int numero){
        for(int i =0; i<nb_choix_menu;i++)        {
            if( i==menu)
                ((Text)screenObject.get(i)).setColor(Const.light_green);
            else
                ((Text)screenObject.get(i)).setColor(Const.dark_green);
        }
    }

    /**
     * Si clic ou entrée on change de screen
     * @return
     */
    public int choixValide(){
        int   returnvalue = Const.play;
        return returnvalue;
    }

    /**
     * Charge le perso coorespondant au num
     * sélectionnéà l'écran précédent
     */
    public void loadImagePerso(int num)        {
        int x = AppX/15;
        int y = AppY/3;
        int w = AppX/4;
        int h = AppY/2;
        switch (num) {
            case Player.PIKACHU:
                loadImage("res/img/max_pikachu.png", x, y, w, h);
                break;
            case Player.MARIO:
                loadImage("res/img/max_mario.png",x, y, w, h);
                break;
            case Player.LINK:
                loadImage("res/img/max_link.png", x, y, w, h);
                break;
            case Player.MEGAMAN:
                loadImage("res/img/max_megaman.png", x, y, w, h);
                break;
            default:
                loadImage("res/img/max_pikachu.png", x, y, w, h);
                break;
        }
    }
}
