package Entities;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;

import static Graphics.EntityTexture.IDLE;
import static Graphics.EntityTexture.decideState;
import static Graphics.EntityTexture.updateTexture;

/**
 * Created by coco on 14-11-20.
 */
public class Player extends MovingEntity{

    public int[] state = IDLE;

    public Player(){

    }



}
