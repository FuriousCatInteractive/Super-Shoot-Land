package Entities;

import static Graphics.EntityTexture.decideState;
import static Graphics.EntityTexture.updateTexture;

/**
 * Created by Corentin on 28/11/2014.
 */
public class MovingEntity extends GameEntity {

    protected float vitesseX;
    protected float vitesseY;
    public static float vitesse = 4.0f;

    public MovingEntity(){
        vitesseX=0;
        vitesseY=0;
    }

    public float getVitesseX() {
        return vitesseX;
    }

    public void setVitesseX(float vitesseX) {
        this.vitesseX = vitesseX;
    }

    public float getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(float vitesseY) {
        this.vitesseY = vitesseY;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }



}
