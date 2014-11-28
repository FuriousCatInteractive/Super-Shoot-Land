package Screens;

import Entities.MovingEntity;
import Entities.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop2 extends cScreen {


    private final int mainMenu = 1;
    private final int exit = -1;


    boolean direction = true;
    Player p1;


    float yorigin=0;////////////////////////////////////////////////////////////////////

    public GameLoop2() {
    }

    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        int AppX = App.getSize().x;
        int AppY = App.getSize().y;
        loadText("play", App.getSize().x / 2, 40, 2 * taille_Font_base);
        loadSpriteSheet("res/img/mario-spritesheet.png", 0.007f*AppY);
        p1 = new Player();
        p1 = (Player)screenObject.get(screenObject.size()-1);
    }

    public int Run(RenderWindow App) {

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

        p1.setPosition(App.getSize().x/2, App.getSize().y/2);

        startMusic("res/sound/tower.ogg");

        while (Running) {

            p1 = ((Player)screenObject.get(screenObject.size()-1));

            int returnValue = eventManager(App);
            if (returnValue <= 50)
                return returnValue;



            //On calcule la valeur relative de y:
            float posMarioRelleft=p1.getVitesseX();//(float)(v_x*t);
            float posMarioReltop=(int)((v_y*t)-((g*t*t)/2000));

            //On calcule maintenant les valeurs absolues
            p1.move(p1.getVitesseX(),-posMarioReltop);


            if(p1.state==JUMP) {
               // System.out.println("up="+up+" t="+t);
                if (up)
                    t += 0.7f;
                else
                    t -= 0.7f;
            }

            // FIN EVOLUTION
            //Avec en bonus une petite mise a 0 des coordonnees lorsque mario s'en va trop loin :)
            if(yorigin-p1.getGlobalBounds().top>50) {
                //t = 0.1f;
                up=false;
            }
            else if(isGrounded()) {
                //t = 0.1f;
                t=0;
                up=true;
                p1.state=IDLE;
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

        //Key pressed
        
        //TODO fichier de config xml pour binder les touches du clavier
        
        if (event.type == Event.Type.KEY_PRESSED) {
            event.asKeyEvent();

            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                sound.stop();
                screenObject.clear();
                return mainMenu;
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {

                p1.setVitesseX(-MovingEntity.vitesse);

            }
            else if (Keyboard.isKeyPressed(Keyboard.Key.V)) {
                p1.state=SHOOT;
            }
            else if (Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
                p1.state=JUMP;
                yorigin=  ((Sprite)screenObject.get(screenObject.size()-1)).getGlobalBounds().top;
            }

            else if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
                p1.setVitesseX(MovingEntity.vitesse);

            }

            if (Keyboard.isKeyPressed(Keyboard.Key.RETURN)) {
            }
        }
        else if(event.type == Event.Type.KEY_RELEASED){
            if(p1.state==WALK)
                p1.setVitesseX(0);



        }
        //si on ne quitte pas cet écran
        return 100;
    }

    public boolean isGrounded(){
        float diff = 600-((Sprite)(screenObject.get(screenObject.size()-1))).getGlobalBounds().top;
        if(diff<0){
            ((Sprite)(screenObject.get(screenObject.size()-1))).move(0,diff);
            return true;
        }

        else
            return false;
    }

}


