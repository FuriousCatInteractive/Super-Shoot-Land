package Entities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.*;

import Screens.GameLoop;
import Tools.Const;
import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-20.
 */
public class Player extends MovingEntity implements  Runnable {

    // Les variables de la troisieme methode
    //Variables méthode 2:
    public final static double g = 9.81;
    public final static double pi = 3.14;
    public final int v_init = 2;
    public final int angle_init = (int) (pi / 3);
    public float t = 0;
    public double v_x = Math.cos(angle_init) * v_init;
    public double v_y = Math.sin(angle_init) * v_init;
    public boolean up = false;
    public boolean down = false;

    public float yorigin = 0;////////////////////////////////////////////////////////////////////
    public boolean enaMoveFinJUMP;

    public boolean direction = true;

    public float vitesse = 4;//App.getSize().x/150;

    public int[] state = IDLE;

    private String perso;

    private boolean isCollied;
    private boolean isGrounded;

    private final int HP_Max=100;
    private int HP;

    private ArrayList<Particle> particles; //particules tirées par le joueur

    public Player() {
        hitbox = new IntRect((FloatRect) this.getGlobalBounds());
        perso = "mario";
        particles = new ArrayList<Particle>();
        isCollied = false;
        isGrounded = false;
        HP=HP_Max;
    }

    /**
     * vérifit et met à jour les variables du player
     */
    public void updatePplayerPhysics() {

        //On calcule la valeur relative de y:
        setVitesseY((float) (-1.0f * ((v_y * t) - ((Player.g * t * t) / 2000))));
        //On calcule maintenant les valeurs absolues
        movePerso(getVitesseX(), 1.0f * getVitesseY());
        //System.out.println("-->"+ getVitesseY()+" state "+ state[0]);

        if (state == JUMP /*|| */) {
            //  System.out.println("up="+up+" t="+t);
            if (up) {
                t += 0.70f;
                if (yorigin - getGlobalBounds().top > 4 * getLocalBounds().height) {
                    down = true;
                    up = false;
                }
            } else if (!up ) {
                t -= 0.7f;
                down = true;
                if (isGrounded) {
                    t = 0;
                    up = false;
                    down = false;
                    if (/* getVitesseX()!=0 &&*/  enaMoveFinJUMP == true)
                        state = WALK;
                    else {
                        setVitesseX(0);
                        state = IDLE;
                    }
                }
            }
        }
        else if(!isGrounded(GameLoop.screenObject)) {
            state=JUMP;
            enaMoveFinJUMP = false;
            isGrounded = false;
            up = false;
            down = false;
        }
    }

    /**
     * appellée quand touche shoot appuyée
     */
    public void PlayerShoot() {
        if (state != JUMP) {

            state = SHOOT;
            Texture tex = new Texture();
            Sprite s = new Sprite();

            try {

                //FIXME particule selon le personnage ?
                tex.loadFromFile(Paths.get("res/img/logo.png"));
                tex.setSmooth(true);
                s.setTexture(tex);
                Particle particle = new Particle(this, s, this.getGlobalBounds().left, this.getGlobalBounds().top);


                if (direction == RIGHT) {
                    particle.setXPos(this.getGlobalBounds().left + this.getGlobalBounds().width);
                    particle.setMoveXDirection(Const.PARTICLE_MOVE_X_RIGHT);

                } else {
                    particle.setMoveXDirection(Const.PARTICLE_MOVE_X_LEFT);
                }

                particles.add(particle);
                GameLoop.screenObject.add(particle.getSprite());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * appellée quand touche jump appuyée
     */
    public void PLayerJump() {
        if(isGrounded) {
            yorigin = getGlobalBounds().top;
            if (state == WALK) {
                // System.out.println( getVitesseX());
                setVitesseX(getVitesseX());
            }
            state = JUMP;
            enaMoveFinJUMP = false;
            isGrounded = false;
            up = true;
            down = false;
        }
    }

    /**
     * appellée quand touche gauche ou droite appuyée
     */
    public void PLayerWalk(boolean dir) {
        if (isCollied == false) {
            if (dir == LEFT)
                setVitesseX(-vitesse);
            else
                setVitesseX(vitesse);

            direction = dir;
            if (state != JUMP)
                state = WALK;
            else if (state == JUMP) {
                //System.out.println("continue saut");
                enaMoveFinJUMP = true;
            }
        }
    }

    /**
     * appellée quand touche gauche ou droie relachée
     */
    public void PlayerIdle() {
        if (state == WALK) {
            state = IDLE;
            setVitesseX(0);
        }
        enaMoveFinJUMP = false;
    }


    /**
     * Méthode pour déplacer les particules tirées
     */
    public void moveParticles() {
        moveLoop:
        for (Particle p : particles) {
            //Si durée de vie atteinte ou collision
            if (p.isExpired() || p.collided(GameLoop.screenObject)) {
                particles.remove(p);
                GameLoop.screenObject.remove(p.getSprite());
                break moveLoop;
            } else {
                p.move();
            }
        }
    }

    /**
     * pour le thread
     */
    @Override
    public void run() {
        // while (GameLoop.gameState==GameLoop.Running) {
        moveParticles();
        updatePplayerPhysics();
        verifOutOfTableau();
        synchronized (GameLoop.screenObject) {
            if (verifCollision(GameLoop.screenObject) != 0) {
                updateTexture(this);
                updateHitbox();
            } else {
                //movePerso(-vitesseX,-vitesseY);
                //vitesseY = -vitesseY;
                // up=false;
            }
        }
        //  }
    }

    public String getPerso() {
        return perso;
    }

    public void setPerso(String perso) {
        this.perso = perso;
    }

    /**
     * déplace la hitbox en même temps que le perso
     */
    public void updateHitbox() {
        if (perso.equals("pikachu")) {
            //System.out.println("pika!");
            IntRect big = new IntRect(this.getGlobalBounds());
            int x = big.left + big.width / 2 - big.width / 8;
            int y = big.top + big.height - (int) (big.height / 1.55);
            int w = big.width / 4;
            int h = (int) (big.height / 1.55);
            hitbox = new IntRect(x, y, w, h);

            //System.out.println("hitbox="+hitbox);
        }
    }

    /**
     * vérifit les collisions avec le reste des éléments du jeu
     *
     * @param array
     */
    public int verifCollision(ArrayList<Drawable> array) {
        IntRect hitboxTemp = new IntRect(hitbox.left/*+(int)vitesseX*/, hitbox.top + (int) vitesseY, hitbox.width, hitbox.height);
        for (int i = array.size() - 1; i > -1; i--) {
            if (array.get(i) instanceof GameEntity) {
                IntRect res = (hitboxTemp.intersection(((GameEntity) array.get(i)).getHitbox()));
                if (res != null) {


                    //vérifit si les pieds sur terre
                    int lim = hitboxTemp.top + hitboxTemp.height;

                    int resbottom = res.top + res.height;
                    // System.out.println("top="+hitboxTemp.top +  " bottom res=" + resbottom +" "+down);

                 /*   if (hitboxTemp.top <= resbottom && up ) {

                        System.out.println("headed ");
                        int diff = hitbox.top - resbottom;
                        movePerso(0, -diff+1);
                        vitesseY=0;
                        up=false;
                        t=0;
                    }*/

                    //System.out.println("vy=" + vitesseY);
                    if (res.top <= lim && res.top > hitbox.top && vitesseY >= -1.0) {
                        int diff = hitbox.top + hitbox.height - res.top;
                        movePerso(0, -diff - 2);
                        System.out.println("groundeed " + diff);
                        isGrounded = true;
                        down = false;
                    } else if (state != JUMP) {
                        vitesseX = 0;
                        movePerso(res.width * (direction ? -1 : 1), 0);
                    }

                    return 0;
                }
            }
        }

        //System.out.println(" a pa collision");
        return 10;
    }

    public void movePerso(float dx, float dy) {
        super.move(dx, dy);
        hitbox = new IntRect(hitbox.left + (int) dx, hitbox.top + (int) dy, hitbox.width, hitbox.height);
    }

    private boolean isGrounded(ArrayList<Drawable> array) {
        // System.out.println("test");
        IntRect hitboxTemp = new IntRect(hitbox.left/*+(int)vitesseX*/, hitbox.top +15, hitbox.width, hitbox.height);
        for (int i = array.size() - 1; i > -1; i--) {
            if (array.get(i) instanceof GameEntity) {
                IntRect res = (hitboxTemp.intersection(((GameEntity) array.get(i)).getHitbox()));
                if (res != null) {
                    //  System.out.println("test gronded");
                    return true;
                }
            }
        }
        return false;
    }

    private void verifOutOfTableau(){
        if(hitbox.top>=GameLoop.AppY){
            HP=0;
            System.out.println("player dead");
        }
    }

    public boolean isDead(){
        if(HP<=0)
            return true;
        else
            return  false;
    }

    public void playerReset(){
        HP=100;
        state=IDLE;
        setPosition(GameLoop.AppX/2,GameLoop.AppY/2 );
    }
}
