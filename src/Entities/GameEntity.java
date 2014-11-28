package Entities;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;

/**
 * Created by Corentin on 28/11/2014.
 */
public class GameEntity extends Sprite {

    protected FloatRect hitbox;


    public GameEntity(){
        hitbox = new FloatRect(this.getTextureRect());
    }

}
