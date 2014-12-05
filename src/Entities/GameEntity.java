package Entities;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

/**
 * Created by Corentin on 28/11/2014.
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
