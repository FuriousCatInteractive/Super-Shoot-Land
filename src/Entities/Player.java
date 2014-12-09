package Entities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import Screens.cScreen;
import org.jsfml.graphics.*;

import Screens.GameLoop;
import Tools.Const;
import static Graphics.EntityTexture.*;

/**
 * @Class Player
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe représentant un joueur
 *
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

    public float yorigin = 0;
    public boolean enaMoveFinJUMP;

    public boolean direction = true;

    public float vitesse = 4;

    public int[] state = IDLE;

    private int perso;

    private boolean isCollied;
    private boolean isGrounded;

    private final int HP_Max=100;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    private int HP;

    public final static int PIKACHU = 1;
    public final static int MARIO = 2;
    public final static int LINK = 3;
    public final static int MEGAMAN = 4;
    
    private int playerNumber;

    private ArrayList<Particle> particles; //particules tirées par le joueur

    public Player() {
        hitbox = new IntRect((FloatRect) this.getGlobalBounds());
        perso = PIKACHU;
        particles = new ArrayList<Particle>();
        isCollied = false;
        isGrounded = false;
        HP=HP_Max;
    }

    /**
     * Méthode qui vérifie et met à jour les variables du player
     */
    public void updatePplayerPhysics() {

        //On calcule la valeur relative de y:
        setVitesseY((float) (-1.0f * ((v_y * t) - ((Player.g * t * t) / 2000))));
        //On calcule maintenant les valeurs absolues
        movePerso(getVitesseX(), 1.0f * getVitesseY());

        if (state == JUMP /*|| */) {
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
     * Méthode appelée quand touche shoot appuyée
     */
    public void PlayerShoot() {
        if (state != JUMP) {

            state = SHOOT;
            Texture tex = new Texture();
            Sprite s = new Sprite();

            try {

                tex.loadFromFile(Paths.get(Const.IMG_PATH+"particle.png"));
                tex.setSmooth(true);
                s.setTexture(tex);
                Particle particle = new Particle(this, s);
                particle.setPosition(getHitbox().left, getHitbox().top+getHitbox().height/6);

                if (direction == RIGHT) {
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
     *Méthode appelée quand touche jump appuyée
     */
    public void PlayerJump() {
        cScreen.jump.play();
        if(isGrounded) {
            yorigin = getGlobalBounds().top;
            if (state == WALK) {
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
     * Méthode appelée quand touche gauche ou droite appuyée
     */
    public void PlayerWalk(boolean dir) {
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
     * Méhode appelée quand touche gauche ou droie relachée
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
     * Méthode run pour le thread
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

    public int getPerso() {
        return perso;
    }

    public void setPerso(int perso) {
        this.perso = perso;
    }

    /**
     * Méthode qui déplace la hitbox en même temps que le perso
     */
    public void updateHitbox() {
        IntRect big = new IntRect(this.getGlobalBounds());
        int x, y, w, h;

        x = big.left + big.width / 2 - big.width / 6;
        y = big.top + big.height - (int) (big.height / 1.75);
        w = big.width / 3;
        h = (int) (big.height / 1.75);
        hitbox = new IntRect(x, y, w, h);


        //System.out.println("hitbox="+hitbox);
    }



    /**
     * Méthode vérifie les collisions avec le reste des éléments du jeu
     *
     * @param array
     */
    public int verifCollision(ArrayList<Drawable> array) {
        IntRect hitboxTemp = new IntRect(hitbox.left/*+(int)vitesseX*/, hitbox.top + (int) vitesseY, hitbox.width, hitbox.height);
        for (int i = array.size() - 1; i > -1; i--) {
            if (array.get(i) instanceof GameEntity && array.get(i) != this) {
                IntRect res = (hitboxTemp.intersection(((GameEntity) array.get(i)).getHitbox()));
                if (res != null) {


                    //vérifit si les pieds sur terre
                    int lim = hitboxTemp.top + hitboxTemp.height;

                    //int resbottom = res.top + res.height;
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

        return 10;
    }

    /**
     * Méthode pour déplacer le personnage
     * @param dx
     * @param dy
     */
    public void movePerso(float dx, float dy) {
        super.move(dx, dy);
        hitbox = new IntRect(hitbox.left + (int) dx, hitbox.top + (int) dy, hitbox.width, hitbox.height);
    }

    /**
     * Méthode verifiant si le joueur est au sol
     * @param array
     * @return
     */
    private boolean isGrounded(ArrayList<Drawable> array) {
        // System.out.println("test");
        IntRect hitboxTemp = new IntRect(hitbox.left/*+(int)vitesseX*/, hitbox.top +15, hitbox.width, hitbox.height);
        for (int i = array.size() - 1; i > -1; i--) {
            if (array.get(i) instanceof GameEntity && array.get(i) != this) {
                IntRect res = (hitboxTemp.intersection(((GameEntity) array.get(i)).getHitbox()));
                if (res != null) {
                    //  System.out.println("test gronded");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode verifiant si le joueur est sorti de l'écran
     */
    private void verifOutOfTableau(){
        if(hitbox.top>=GameLoop.AppY){
            HP=0;
            System.out.println("player dead");
        }
    }

    /**
     * Méthode vérifiant si le joueur est mort
     * @return mort
     */
    public boolean isDead(){
        if(HP<=0)
            return true;
        else
            return  false;
    }

    /**
     * Méthode permettant de reset la position du joueur
     */
    public void playerReset(){
        HP=100;
        state=IDLE;
        setPosition((playerNumber - 1) * GameLoop.AppX / 2 + GameLoop.AppX / 8, 30);

    }

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
    

    public void lostPV(int nbPVperdu){
        HP-=nbPVperdu;
    }
}
