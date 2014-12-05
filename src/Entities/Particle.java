package Entities;
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
	private int moveXDirection;
	private float speed;
	private Player owner;

	/**
	 * Constructeur de particle
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
	}
	
	/**
	 * Méthode pour changer la position de la particule
	 * @param newX
	 * @param newY
	 */
	public void movePosition(float newX, float newY)
	{
		xPos = newX;
		yPos = newY;
		sprite.setPosition(newX, newY);
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

	public float getXPos() {
		return xPos;
	}

	public void setYPos(float xPos) {
		this.xPos = xPos;
	}

	public float getYPos() {
		return yPos;
	}

	public void setXPos(float yPos) {
		this.yPos = yPos;
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
	
	
}

