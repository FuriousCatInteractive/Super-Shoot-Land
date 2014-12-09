package InputGameLoop;

import Screens.GameLoop;
import Screens.cScreen;
import Tools.Const;
import Tools.KeyboardActions;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

import static Graphics.EntityTexture.*;

/**
 * @Class InputManager
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe gérant les entrées clavier
 *
 */
public class InputManager extends Thread {

	private RenderWindow App;

	public InputManager(RenderWindow App){
		this.App = App;
	}

	/**
	 * Méthode qui fait l'update (dans un thread)
	 */
	public void run() {
		GameLoop.returnValue =  eventManager(App);
		//System.out.println("ret="+GameLoop.returnValue);
	}

	/**
	 * Appel mouse et keyboard manager
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

		//si on ne quitte pas cet écran
		return 100;
	}

	/**
	 * Gère les inputs clavier
	 * @param event
	 * @param App
	 * @return
	 */
	public int keyboardManager(Event event, RenderWindow App) {


		if (event.type == Event.Type.KEY_PRESSED) {
			event.asKeyEvent();

			if (KeyboardActions.quitKeyPressed()) {
				return GameLoop.retourne(Const.mainMenu);
			}

			if ((KeyboardActions.isAttacking(GameLoop.p1))) {
				System.out.println("PLAYER 1 SHOT");
				cScreen.shoot.play();
				GameLoop.p1.PlayerShoot();
			}
			else if (KeyboardActions.isJumping(GameLoop.p1)) {
				GameLoop.p1.PlayerJump();
			}
			if ((KeyboardActions.isMovingLeft(GameLoop.p1))) {
				GameLoop.p1.PlayerWalk(LEFT);
			}
			else if ((KeyboardActions.isMovingRight(GameLoop.p1))) {
				GameLoop.p1.PlayerWalk(RIGHT);
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
				GameLoop.p2.PlayerJump();
			}
			if ((KeyboardActions.isMovingLeft(GameLoop.p2))) {
				GameLoop.p2.PlayerWalk(LEFT);
			}
			else if ((KeyboardActions.isMovingRight(GameLoop.p2))) {
				GameLoop.p2.PlayerWalk(RIGHT);
			}
		}

		else if(event.type == Event.Type.KEY_RELEASED) {

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
