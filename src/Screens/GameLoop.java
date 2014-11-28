package Screens;

import Entities.Player;
import Graphics.EntityTexture;
import Tools.Const;
import Tools.KeyboardActions;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
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


    private final int mainMenu = 1;
    private final int exit = -1;

    int[] state = IDLE;
    boolean direction = true;

    float yorigin=0;////////////////////////////////////////////////////////////////////
    private Sprite marioClone;
    
    public GameLoop() {
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

        // Les variables de la troisieme methode
        //Variables méthode 2:
        double g = 9.81;
        double pi = 3.14;
        int v_init = 2;
        int angle_init = (int) (pi/3);
        float t = 0;
        double v_x = Math.cos(angle_init)*v_init;
        double v_y = Math.sin(angle_init)*v_init;
        FloatRect posMarioRel = new FloatRect(0,0,0,0);
        boolean up =true;

        ((Sprite)screenObject.get(screenObject.size()-1)).setPosition(App.getSize().x/2, App.getSize().y/2);
        marioClone = ((Sprite)screenObject.get(screenObject.size()-1));
        startMusic("res/sound/tower.ogg");

        while (Running) {

            int returnValue = eventManager(App);
            if (returnValue <= 50)
                return returnValue;


            //posMarioAbs.x = 200;
            //  posMarioAbs.y = 300-(mario->h);

            //On calcule la valeur relative de y:
            float posMarioRelleft=0f;//(float)(v_x*t);
            float posMarioReltop=(int)((v_y*t)-((g*t*t)/2000));

            //On calcule maintenant les valeurs absolues
            ((Sprite)screenObject.get(screenObject.size()-1)).move(posMarioRelleft,-posMarioReltop);


            if(state==JUMP) {
               // System.out.println("up="+up+" t="+t);
                if (up)
                    t += 0.7f;
                else
                    t -= 0.7f;
            }

            // FIN EVOLUTION
            //Avec en bonus une petite mise a 0 des coordonnees lorsque mario s'en va trop loin :)
            if(yorigin-((Sprite)(screenObject.get(screenObject.size()-1))).getGlobalBounds().top>50) {
                //t = 0.1f;
                up=false;
            }
            else if(isGrounded()) {
                //t = 0.1f;
                t=0;
                up=true;
                state=IDLE;
            }

            App.clear(Color.RED);
            for (int i = screenObject.size() - 1; i > -1; i--) {
                if (screenObject.get(i) instanceof Sprite) {
                    /*updateTexture((Sprite) screenObject.get(i), decideState(state), direction);
                    if(decideState(SHOOT)==SHOOT[SHOOT.length-1] && state==SHOOT){
                        state=IDLE;
                    } */
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
        float vitesse = App.getSize().x/200;
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

            if ((KeyboardActions.isMovingLeft())) {
                ((Sprite)screenObject.get(screenObject.size()-1)).move(-vitesse,0);
                state=WALK;
                direction=LEFT;

            }
            else if ((KeyboardActions.isAttacking())) {
                state=SHOOT;
                //TODO projectiles 
                shootParticle();
                
            }
            else if (KeyboardActions.isJumping()) {
                state=JUMP;
                yorigin=  ((Sprite)screenObject.get(screenObject.size()-1)).getGlobalBounds().top;
            }

            else if ((KeyboardActions.isMovingRight())) {
                ((Sprite)screenObject.get(screenObject.size()-1)).move(vitesse,0);
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
    
    /**
     * Méthode pour tirer des projectiles
     */
    public void shootParticle()
    {
    	Texture particuleTex = new Texture();
    	Sprite sprite = new Sprite();
    	try {
    		particuleTex .loadFromFile(Paths.get(Const.TEX_PARTICLE_PATH));
    		particuleTex .setSmooth(true);
			sprite.setTexture(particuleTex);
			sprite.setPosition(marioClone.getPosition());
			screenObject.add(sprite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(direction == LEFT)
    	{
    		
    	}
    	
    	else if(direction == RIGHT)
    	{
    		
    	}
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


