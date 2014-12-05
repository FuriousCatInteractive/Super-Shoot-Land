package Entities;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;


/**
 * @Class Particle
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe représentant une particule (tir)
 *
 */
public class Particle {
	
	private Texture texture;
	private Sprite sprite;
	private float xPos, yPos;
	
	/**
	 * Constructeur de particle
	 * @param texture
	 * @param sprite
	 */
	public Particle(Texture texture, Sprite sprite) {
		super();
		this.texture = texture;
		this.sprite = sprite;
	}
	
	/**
	 * Constructeur de particle
	 * @param texture
	 * @param sprite
	 * @param xPos
	 * @param yPos
	 */
	public Particle(Texture texture, Sprite sprite, float xPos, float yPos) {
		super();
		this.texture = texture;
		this.sprite = sprite;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public float getxPos() {
		return xPos;
	}
	
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	
	public float getyPos() {
		return yPos;
	}
	
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	
	
	
	

}
