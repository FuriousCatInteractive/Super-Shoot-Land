package Entities;

import java.util.ArrayList;

import org.jsfml.window.Keyboard;

/**
* @Class KeyBinding
* @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
* 
* Représente une configuration de touche
*
*/
public class KeyBinding {
	
	private String actionName; //action declenchee par une/des touche(s)
	private int playerNumber; //joueur associé à l'action
	private ArrayList<Keyboard.Key> keyNames; //liste des touches correspondant a l'action

	/**
	 * Constructeur de KeyBinding
	 * @param actionName
	 */
	public KeyBinding(String actionName) {
		this.actionName = actionName;
		this.keyNames = new ArrayList<Keyboard.Key>();
	}

	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public ArrayList<Keyboard.Key> getKeyNames() {
		return keyNames;
	}

	public void setKeyNames(ArrayList<Keyboard.Key> keyNames) {
		this.keyNames = keyNames;
	}


	public int getPlayerNumber() {
		return playerNumber;
	}


	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
}
