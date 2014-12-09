package Entities;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * @Class GameEntity
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe représentant une entité du jeu
 *
 */
public class GameEntity extends Sprite {

    protected IntRect hitbox;

    public GameEntity(){
       //hitbox = new IntRect(this.getTextureRect());
    }


    public IntRect getHitbox() {
        return hitbox;
    }

    public void setHitbox(IntRect hitbox) {
        this.hitbox = hitbox;
    }


}
