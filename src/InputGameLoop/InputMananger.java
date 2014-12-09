package InputGameLoop;

import Screens.GameLoop;
import Screens.SelectMode;
import Screens.cScreen;
import Tools.Const;
import Tools.KeyboardActions;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import static Graphics.EntityTexture.*;

/**
 * Created by Corentin on 05/12/2014.
 */
public class InputMananger extends Thread {

	private RenderWindow App;

	public InputMananger(RenderWindow App){
		this.App = App;
	}

	/**
	 * méthode qui fait l'update (dans un thread)
	 */
	public void run() {
		GameLoop.returnValue =  eventManager(App);
		//System.out.println("ret="+GameLoop.returnValue);
	}

	/**
	 * appel mouse et keyboard manager
	 * @param App
	 * @return
	 */
	public int eventManager(RenderWindow App) {
		//Verifying events
		for (Event event : App.pollEvents()) {
			// Window closed
			if (event.type == Event.Type.CLOSED) {
				return GameLoop.retourne(-1);
			}
			int returnValueKeyboard = keyboardManager(event, App);
			if (returnValueKeyboard <= 50)
				return returnValueKeyboard;
		}

		//si on ne quitte pa s cet écran
		return 100;
	}

	/**
	 * gère les inputs clavier
	 * @param event
	 * @param App
	 * @return
	 */
	public int keyboardManager(Event event, RenderWindow App) {


		if (event.type == Event.Type.KEY_PRESSED) {
			event.asKeyEvent();
			
			int activePlayer = KeyboardActions.getPlayerKey(event);

					if (KeyboardActions.quitKeyPressed()) {
						return GameLoop.retourne(Const.mainMenu);
					}

					if ((KeyboardActions.isAttacking(GameLoop.p1))) {
						System.out.println("PLAYER 1 SHOT");
						cScreen.shoot.play();
						GameLoop.p1.PlayerShoot();
					}
					else if (KeyboardActions.isJumping(GameLoop.p1)) {
						GameLoop.p1.PLayerJump();
					}
					if ((KeyboardActions.isMovingLeft(GameLoop.p1))) {
						GameLoop.p1.PLayerWalk(LEFT);
					}
					else if ((KeyboardActions.isMovingRight(GameLoop.p1))) {
						GameLoop.p1.PLayerWalk(RIGHT);
					}

					if (KeyboardActions.quitKeyPressed()) {
						return GameLoop.retourne(Const.mainMenu);
					}

					if ((KeyboardActions.isAttacking(GameLoop.p2))) {
						System.out.println("PLAYER 2 SHOT");
						cScreen.shoot.play();
						GameLoop.p2.PlayerShoot();
					}
					else if (KeyboardActions.isJumping(GameLoop.p2)) {
						GameLoop.p2.PLayerJump();
					}
					if ((KeyboardActions.isMovingLeft(GameLoop.p2))) {
						GameLoop.p2.PLayerWalk(LEFT);
					}
					else if ((KeyboardActions.isMovingRight(GameLoop.p2))) {
						GameLoop.p2.PLayerWalk(RIGHT);
					}

			

			/*if (SelectMode.local) {
				if (Keyboard.isKeyPressed(Keyboard.Key.R)) {
					GameLoop.p2.PlayerShoot();
				}
				else if (Keyboard.isKeyPressed(Keyboard.Key.T)) {
					GameLoop.p2.PLayerJump();
				}
				if (Keyboard.isKeyPressed(Keyboard.Key.Q)) {
					GameLoop.p2.PLayerWalk(LEFT);
				}
				else if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
					GameLoop.p2.PLayerWalk(RIGHT);
				}
			} */


		}

		else if(event.type == Event.Type.KEY_RELEASED) {
			
			/*int activePlayer = KeyboardActions.getPlayerKey(event);
			
			switch(activePlayer)
			{
				case Const.PLAYER1:
					if (KeyboardActions.movementKeyReleased(event, GameLoop.p1)) 
					{
						GameLoop.p1.PlayerIdle();
					}
				break;
				
				case Const.PLAYER2:
					if (KeyboardActions.movementKeyReleased(event, GameLoop.p2)) 
					{
						GameLoop.p2.PlayerIdle();
					}
				break;
					
				default:
				break;
				
			} */
			
			if (KeyboardActions.movementKeyReleased(event, GameLoop.p1)) 
			{
				GameLoop.p1.PlayerIdle();
			}
			
			if (KeyboardActions.movementKeyReleased(event, GameLoop.p2)) 
			{
				GameLoop.p2.PlayerIdle();
			}
		}
		//si on ne quitte pas cet écran
		return 100;
	}
}
