package Screens;

import Entities.Player;
import Graphics.EntityTexture;
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


    private final int mainMenu = 1;
    private final int exit = -1;

    // int[] state = IDLE;
    boolean direction = true;
    Player p1;

    float yorigin=0;////////////////////////////////////////////////////////////////////
    private boolean enaMoveFinJUMP;


    public GameLoop() {
    }

    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        int AppX = App.getSize().x;
        int AppY = App.getSize().y;
        loadText("play", App.getSize().x / 2, 40, 2 * taille_Font_base);
        loadSpriteSheet("res/img/pikachu-spritesheet.png", 0.007f*AppY);
        p1 = new Player();
        p1 = (Player)screenObject.get(screenObject.size()-1);
    }

    public int Run(RenderWindow App) {

      //  System.out.println("--------------------->test");fg
//fgfds
        loadScreenObjects(App);

        boolean Running = true;

        // Les variables de la troisieme methode
        //Variables méthode 2:
        double g = 9.81;
        double pi = 3.14;
        int v_init = 2;
        int angle_init = (int) (pi/3);
        float t = 0;
        double v_x = Math.cos(angle_init)*v_init;
        double v_y = Math.sin(angle_init)*v_init;
        boolean up =true;

        ((Sprite)screenObject.get(screenObject.size()-1)).setPosition(App.getSize().x/2, App.getSize().y/2);

        startMusic("res/sound/tower.ogg");

        while (Running) {

            p1 = ((Player)screenObject.get(screenObject.size()-1));

            int returnValue = eventManager(App);
            if (returnValue <= 50)
                return returnValue;

            //On calcule la valeur relative de y:
            p1.setVitesseY((float) (-1.0f*((v_y*t)-((g*t*t)/2000))));
            //On calcule maintenant les valeurs absolues
            p1.move(p1.getVitesseX(),1.0f*p1.getVitesseY());
            //System.out.println("-->"+p1.getVitesseY()+" state "+p1.state[0]);

            if(p1.state==JUMP) {
                //  System.out.println("up="+up+" t="+t);
                if (up)
                    t += 0.70f;
                else
                    t -= 0.7f;
            }


            // FIN EVOLUTION
            //Avec en bonus une petite mise a 0 des coordonnees lorsque mario s'en va trop loin :)
            if(yorigin-p1.getGlobalBounds().top>4*p1.getLocalBounds().height) {
                //t = 0.1f;

                up=false;
            }
            else if(isGrounded()) {
                //t = 0.1f;
                t=0;
                up=true;

                if(/*p1.getVitesseX()!=0 &&*/ enaMoveFinJUMP==true)
                    p1.state=WALK;
                else{
                    p1.setVitesseX(0);
                    p1.state=IDLE;
                }


            }

            App.clear(Color.RED);
            updateTexture(p1);
            screenObject.set(screenObject.size()-1, p1);
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
            if (event.type == Event.Type.CLOSED) {
                screenObject.clear();
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
        float vitesse = App.getSize().x/150;
        //Sprite mario =  (Sprite)screenObject.get(screenObject.size()-1);
        //Key pressed

        //TODO fichier de config xml pour binder les touches du clavier

        if (event.type == Event.Type.KEY_PRESSED) {
            event.asKeyEvent();

            if (KeyboardActions.quitKeyPressed()) {
                sound.stop();
                screenObject.clear();
                return mainMenu;
            }


            if ((KeyboardActions.isAttacking())) {
                if(p1.state!=JUMP)
                {
                    p1.state=SHOOT;
                }

               // System.out.println("shhot "+p1.state[0]);
            }
            else if (KeyboardActions.isJumping()) {
                yorigin=  p1.getGlobalBounds().top;
                if(p1.state==WALK)
                {
                    // System.out.println(p1.getVitesseX());
                    p1.setVitesseX(p1.getVitesseX());
                }
                p1.state=JUMP;
                enaMoveFinJUMP=false;
            }
            if ((KeyboardActions.isMovingLeft())) {
                p1.setVitesseX(-vitesse);
                direction=LEFT;
                if(p1.state!=JUMP)
                    p1.state=WALK;
                else if(p1.state==JUMP){
                    //System.out.println("continue saut");
                    enaMoveFinJUMP=true;
                }
            }
            else if ((KeyboardActions.isMovingRight())) {
                p1.setVitesseX(vitesse);
                direction=RIGHT;
                if(p1.state!=JUMP)
                    p1.state=WALK;
                else if(p1.state==JUMP){
                   // System.out.println("continue saut");
                    enaMoveFinJUMP=true;
                }
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
            }
        }

        else if(event.type == Event.Type.KEY_RELEASED) {
           org.jsfml.window.event.KeyEvent keyev =  event.asKeyEvent();// == Keyboard.Key.SPACE;

            if (p1.state == WALK){
                p1.state = IDLE;
                p1.setVitesseX(0);
            }
            else if ((p1.state == JUMP && (keyev.key == Keyboard.Key.LEFT || keyev.key == Keyboard.Key.RIGHT) )) {
               // System.out.println("-------------->jey left relachée");
                enaMoveFinJUMP=false;
            }

        }
        //si on ne quitte pas cet écran
        return 100;
    }

    public boolean isGrounded(){
        float diff =400-p1.getGlobalBounds().top;
        if(diff<0){
            p1.move(0, diff);
            return true;
        }

        else
            return false;
    }

}


