package Screens;

import Tools.Const;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import Tools.KeyboardActions;


/**
 * @Class MainMenu
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe du menu principal
 *
 */
public class MainMenu extends cScreen implements iMenu{

    private Vector2i pos;
    private int menu;
    private int nb_choix_menu;

    /**
     * constructeur par défaut
     */
    public MainMenu() {
        pos = new Vector2i(0,0);
        menu = 0;
        nb_choix_menu = 3;
    }

    /**
     * charge tous les éléments du screen
     * @param App
     */
    private void loadScreenObjects(RenderWindow App){
        loadFont( "res/font/Volter__28Goldfish_29.ttf");
        int AppX = App.getSize().x;
        int AppY = App.getSize().y;
        int taille_Font_base = (int)(0.09*AppY);
        int offsetX =AppX/4;
        int offsety = AppY/20;
        loadText("Play", AppX/2+offsetX,AppY/3+offsety,taille_Font_base);
        loadText("Config", AppX/2+offsetX,AppY/2+offsety,taille_Font_base);
        loadText("Exit", AppX/2+offsetX,2*AppY/3+offsety,taille_Font_base);

        loadText("Super Shoot Land", AppX/2,AppY/15,(int)1.1*taille_Font_base);
        ((Text)screenObject.get(3)).setColor(Const.light_green);
        loadText("Furious Cat Interactive - 2014",AppX/2, AppY-AppY/20,taille_Font_base/2);
        ((Text)screenObject.get(4)).setColor(Const.light_green);
        newRect(AppX, AppY/5, 0 ,0, Const.dark_green);
        newRect(AppX, 10, 0 ,AppY/5, Const.light_green);
        newRect(AppX, AppY/11, 0 ,10*AppY/11, Const.dark_green);
        newRect(AppX, 10, 0 ,10*AppY/11-10, Const.light_green);
        loadImage("res/img/logo.png",(int)((float)AppX/12),AppY/3);
       // System.out.println(500/(float)AppX);
        ((Sprite)screenObject.get(screenObject.size()-1)).setScale(0.0017f*AppY,0.0017f*AppY);
    }

    /**
     * méthode principale qui fait l'update
     * @param App
     * @return
     */
    public int Run(RenderWindow App){


        musicBackground.play();
        musicBackground.setLoop(true);

        loadScreenObjects(App);

        boolean Running = true;

        while (Running)
        {
            int returnValue = eventManager(App);
            if(returnValue<=50)
                return  returnValue;

            menuSelectionne(menu);

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
     * appel keyboard et mouse manager
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
     * regarde les clics et les mouvements de la souris
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
            event.asMouseEvent();
            cScreen.pick.play();
            screenObject.clear();
            return choixValide();
        }

        //si on ne quitte pas cet écran
        return 100;
    }

    /**
     * regarde les touche appuyées
     * @param event
     * @param App
     * @return
     */
    public int keyboardManager(Event event, RenderWindow App){
        //Key pressed
        if (event.type == Event.Type.KEY_PRESSED){
            event.asKeyEvent();

            if (KeyboardActions.quitKeyPressed()){
                screenObject.clear();
                return  Const.exit;
            }


            if (KeyboardActions.isMovingDown()){
                menu++;
                cScreen.select.play();
                if(menu>nb_choix_menu-1)
                    menu = 0;
            }

            if (KeyboardActions.isMovingUp()) {
                menu--;
                cScreen.select.play();
                if(menu<0)
                    menu = 2;
            }

            if (KeyboardActions.isAttacking()) {
               // musicBackground.stop();
                cScreen.pick.play();
                screenObject.clear();
                return choixValide();
            }
        }
        //si on ne quitte pas cet écran
        return 100;
    }

    /**
     * hilight du menu selectionné
     * @param numero
     */
    public void menuSelectionne(int numero){
        for(int i =0; i<nb_choix_menu;i++)        {
            if(i==numero)
                ((Text)screenObject.get(i)).setColor(Const.light_green);
            else
                ((Text)screenObject.get(i)).setColor(Const.dark_green);
        }
    }

    /**
     * si un choit est validé on regarde si on change de screen
     */
    public int choixValide(){
        int returnvalue =100;
        switch (menu){
            case 0:
                returnvalue = Const.selectMode;
                break;
            case 1:
                returnvalue = Const.config;
                break;
            case 2:
                returnvalue = Const.exit;
                break;
        }
        return returnvalue;
    }
}
