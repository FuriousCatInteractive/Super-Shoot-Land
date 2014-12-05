package Entities;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

import static Graphics.EntityTexture.*;
import static Graphics.EntityTexture.JUMP;
import static Graphics.EntityTexture.WALK;

/**
 * Created by coco on 14-11-20.
 */
public class Player extends MovingEntity implements  Runnable{

    // Les variables de la troisieme methode
    //Variables méthode 2:
    public final static double g = 9.81;
    public final static double pi = 3.14;
    public final int v_init = 2;
    public final int angle_init = (int) (pi/3);
    public  float t = 0;
    public double v_x = Math.cos(angle_init)*v_init;
    public double v_y = Math.sin(angle_init)*v_init;
    public boolean up =true;

    public  float yorigin=0;////////////////////////////////////////////////////////////////////
    public boolean enaMoveFinJUMP;

   public boolean direction = true;

   public float vitesse = 4;//App.getSize().x/150;

    public int[] state = IDLE;

    private String perso;

    public Player(){
        hitbox = new IntRect((FloatRect) this.getGlobalBounds());
        perso="mario";
    }

    /**
     * vérifit et met à jour les variables du player
     */
    public void updatePplayerPhysics(){


        //On calcule la valeur relative de y:
         setVitesseY((float) (-1.0f*(( v_y* t)-((Player.g* t* t)/2000))));
        //On calcule maintenant les valeurs absolues
         move( getVitesseX(),1.0f* getVitesseY());
        //System.out.println("-->"+ getVitesseY()+" state "+ state[0]);

        if( state==JUMP) {
            //  System.out.println("up="+up+" t="+t);
            if ( up)
                 t += 0.70f;
            else
                 t -= 0.7f;
        }

        // FIN EVOLUTION
        //Avec en bonus une petite mise a 0 des coordonnees lorsque mario s'en va trop loin :)
        if( yorigin- getGlobalBounds().top>4* getLocalBounds().height) {
            //t = 0.1f;

             up=false;
        }
        else if(isGrounded()) {
            //t = 0.1f;
             t=0;
             up=true;

            if(/* getVitesseX()!=0 &&*/  enaMoveFinJUMP==true)
                 state=WALK;
            else{
                 setVitesseX(0);
                 state=IDLE;
            }
        }
    }

    /**
     * appellée quand touche shoot appuyée
     */
    public void PlayerShoot(){
        if( state!=JUMP)
        {
             state=SHOOT;
        }
    }

    /**
     * appellée quand touche jump appuyée
     */
    public  void PLayerJump() {
         yorigin =  getGlobalBounds().top;
        if ( state == WALK) {
            // System.out.println( getVitesseX());
             setVitesseX( getVitesseX());
        }
         state = JUMP;
         enaMoveFinJUMP = false;
    }

    /**
     * appellée quand touche gauche ou droite appuyée
     */
    public  void PLayerWalk(boolean dir) {
        if(dir==LEFT)
             setVitesseX(- vitesse);
        else
             setVitesseX( vitesse);

         direction = dir;
        if ( state != JUMP)
             state = WALK;
        else if ( state == JUMP) {
            //System.out.println("continue saut");
            enaMoveFinJUMP = true;
        }
    }

    /**
     * appellée quand touche gauche ou droie relachée
     */
    public  void PlayerIdle(){
        if (state == WALK){
            state = IDLE;
            setVitesseX(0);
        }
        enaMoveFinJUMP=false;
    }

    /**
     * vérifit que le joueur est au sol
     */
    public boolean isGrounded(){
        float diff =400-getGlobalBounds().top;
        if(diff<0){
            move(0, diff);
            return true;
        }
        else
            return false;
    }

    /**
     * pour le thread
     */
    @Override
    public void run() {
        updatePplayerPhysics();
        updateTexture(this);
        updateHitbox();
    }

    public String getPerso() {
        return perso;
    }

    public void setPerso(String perso) {
        this.perso = perso;
    }


    public void updateHitbox(){
        if(perso.equals("pikachu")){
            System.out.println("pika!");
            IntRect big =  new IntRect(this.getGlobalBounds());
            hitbox=new IntRect(big.left+big.width/3,       big.top+big.height/3,
                               big.width/4,                (int) (big.height/1.55));
            System.out.println("hitbox="+hitbox);
        }
    }
}
