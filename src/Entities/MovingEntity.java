package Entities;


/**
 * Created by Corentin on 28/11/2014.
 */
public class MovingEntity extends GameEntity {

    protected float vitesseX;
    protected float vitesseY;
    public static float vitesse = 4.0f;

    public float getImage_h() {
        return image_h;
    }

    public void setImage_h(float image_h) {
        this.image_h = image_h;
    }

    public float getImage_w() {
        return image_w;
    }

    public void setImage_w(float image_w) {
        this.image_w = image_w;
    }

    protected float image_h=0;
    protected float image_w=0;

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
        MovingEntity.vitesse = vitesse;
    }



}
