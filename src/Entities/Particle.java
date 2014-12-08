package Entities;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Graphics.EntityTexture;
import Screens.GameLoop;
import Screens.cScreen;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import Tools.Const;


/**
 * @Class Particle
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe représentant une particule (tir)
 *
 */
public class Particle {

	private Sprite sprite;
	private float xPos, yPos;
	private int moveXDirection; //direction de la particule
	private float speed; //vitesse
	private Player owner;
	private long emitTime, lifeTime; //temps au début, durée de vie de la particule
	private boolean isExpired; //la particule doit être détruite
	private IntRect hitbox;

	/**
	 * Constructeur de particle
	 * @param owner
	 * @param sprite
	 * @param xPos
	 * @param yPos
	 */
	public Particle(Player owner, Sprite sprite, float xPos, float yPos) {
		super();
		this.owner = owner;
		this.sprite = sprite;
		this.xPos = xPos;
		this.yPos = yPos;
		sprite.setPosition(xPos, yPos);
		sprite.setScale(Const.PARTICLE_SCALE_X, Const.PARTICLE_SCALE_Y);
		setSpeed(Const.PARTICLE_SPEED);
		setLifeTime(Const.PARTICLE_LIFETIME);
		emitTime = System.currentTimeMillis();
		hitbox = new IntRect((FloatRect)sprite.getGlobalBounds());
	}

    /**
     * Constructeur de particle
     * @param owner
     * @param sprite
     */
	public Particle(Player owner, Sprite sprite) {
		super();
		this.owner = owner;
		this.sprite = sprite;
		sprite.setScale(Const.PARTICLE_SCALE_X, Const.PARTICLE_SCALE_Y);
		setSpeed(Const.PARTICLE_SPEED);
		setLifeTime(Const.PARTICLE_LIFETIME);
		emitTime = System.currentTimeMillis();
		hitbox = new IntRect((FloatRect)sprite.getGlobalBounds());
	}
	
	public void setPosition(float x, float y)
	{
		xPos = x;
		yPos = y;
		sprite.setPosition(xPos,yPos);
	}

    /**
     * Méthode pour changer la position de la particule
     */
	public void move()
	{
		long curTime = System.currentTimeMillis();

		//System.out.println("EMIT TIME = "+emitTime+ ", CURTIME = "+curTime + ", LIFETIME NS ="+lifeTime);
		//Tant que la durée de vie n'est pas atteinte on déplace la particule
		if(curTime - emitTime <= lifeTime)
		{
			xPos += speed * moveXDirection;
			sprite.setPosition(xPos, yPos);
			hitbox = new IntRect(sprite.getGlobalBounds());
		}

		//Si durée de vie atteinte, la particule est à détruire
		else
		{
			isExpired = true;
		}

	}

	public boolean collided(ArrayList<Drawable> array)
	{
		for (int i = array.size()-1; i > -1; i--) 
		{
			if (array.get(i) instanceof GameEntity) 
			{
				if(array.get(i) != owner)
				{
					if ((this.hitbox.intersection(((GameEntity) array.get(i)).getHitbox())) != null) 
					{
						System.out.println("collision particule");
                        if(array.get(i) instanceof Player) {
                            ((Player) array.get(i)).lostPV(10);
                            // ((Player) array.get(i)).state= EntityTexture.JUMP;
                            cScreen.hit.play();
                        }
						return true;
					}	
				}
				
			}
		}

		return false;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}



	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public float getXPos() {
		return xPos;
	}

	public void setYPos(float xPos) {
		this.xPos = xPos;
	}

	public float getYPos() {
		return yPos;
	}


	public int getMoveXDirection() {
		return moveXDirection;
	}

	public void setMoveXDirection(int moveXDirection) {
		this.moveXDirection = moveXDirection;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public long getEmitTime() {
		return emitTime;
	}

	public void setEmitTime(long emitTime) {
		this.emitTime = emitTime;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public IntRect getHitbox() {
		return hitbox;
	}

	public void setHitbox(IntRect hitbox) {
		this.hitbox = hitbox;
	}
	
	



}

