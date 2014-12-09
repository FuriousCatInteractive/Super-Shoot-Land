package Tools;

import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import Entities.KeyBinding;
import Entities.Player;

/**
 * @Class KeyboardActions
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe contenant des méthodes vérifiant si une action est effectuée
 *
 */
public class KeyboardActions {

	/**
	 * Méthode qui retourne le joueur dont le binding correspond à une touche pressée
	 * @param event
	 * @param checkPressed
	 * @return player
	 */
	public static int getPlayerKey(Event event)
	{
		int player = -1;

		event.asKeyEvent();

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			for(Keyboard.Key key : kb.getKeyNames())
			{
				if(Keyboard.isKeyPressed(key))
				{
					player = kb.getPlayerNumber();
					System.out.println("Key pressed : "+key.name()+", player = "+player);
				}
			}

		}


		return player;
	}

	/**
	 * Méthode permettant de vérifier qu'une touche de mouvement est relâchée
	 * @param event
	 * @param key
	 * @return
	 */
	public static boolean movementKeyReleased(Event e, Player p)
	{
		KeyEvent keyEvent =  e.asKeyEvent();

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				if(kb.getActionName().equals(Const.A_MOVE_LEFT) ||kb.getActionName().equals(Const.A_MOVE_RIGHT))
				{
					//On regarde si une des touches de ces mouvements n'est pas pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(key == keyEvent.key)
						{
							System.err.println("Player "+playerNumber+ " KEY RELEASED : "+key.name());
							return true;
						}
					}
				}
			}

		}


		/*if(event.type == Event.Type.KEY_RELEASED)
		{
			KeyEvent keyev =  event.asKeyEvent();


			if(keyev.key == Keyboard.)
			return true;
		} */

		return false;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche de saut
	 * @return res
	 */
	public static boolean isJumping()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant au saut
			if(kb.getActionName().equals(Const.A_JUMP))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("Jump key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche de deplacement gauche
	 * @return res
	 */
	public static boolean isMovingLeft()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant au deplacement gauche
			if(kb.getActionName().equals(Const.A_MOVE_LEFT))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("MOVE_LEFT key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche de deplacement droit
	 * @return res
	 */
	public static boolean isMovingRight()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant au deplacement droit
			if(kb.getActionName().equals(Const.A_MOVE_RIGHT))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("MOVE_RIGHT key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}
	
	/**
	 * Méthode verifiant si on appuie sur la touche bas
	 * @return res
	 */
	public static boolean isMovingDown()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant au deplacement bas
			if(kb.getActionName().equals(Const.A_MOVE_DOWN))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("MOVE_DOWN key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}
	
	/**
	 * Méthode verifiant si on appuie sur la touche haut
	 * @return res
	 */
	public static boolean isMovingUp()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant au deplacement haut
			if(kb.getActionName().equals(Const.A_MOVE_UP))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("MOVE_UPkey is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche d'attaque
	 * @return res
	 */
	public static boolean isAttacking()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant a une attaque
			if(kb.getActionName().equals(Const.A_ATTACK))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("ATTACK key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * FONCTIONS AVEC PARAMETRES (joueur)
	 */
	
	/**
	 * Méthode verifiant si on appuie sur la touche de saut
	 * @return res
	 */
	public static boolean isJumping(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant au saut
				if(kb.getActionName().equals(Const.A_JUMP))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("Jump key is pressed");
							res = true;
						}
					}
				}
			}	
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche de deplacement gauche
	 * @return res
	 */
	public static boolean isMovingLeft(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant au deplacement gauche
				if(kb.getActionName().equals(Const.A_MOVE_LEFT))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("MOVE_LEFT key is pressed");
							res = true;
						}
					}
				}
			}
			
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche de deplacement droit
	 * @return res
	 */
	public static boolean isMovingRight(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant au deplacement droit
				if(kb.getActionName().equals(Const.A_MOVE_RIGHT))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("MOVE_RIGHT key is pressed");
							res = true;
						}
					}
				}
			}
			
		}

		return res;
	}
	
	/**
	 * Méthode verifiant si on appuie sur la touche bas
	 * @return res
	 */
	public static boolean isMovingDown(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant au deplacement bas
				if(kb.getActionName().equals(Const.A_MOVE_DOWN))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("MOVE_DOWN key is pressed");
							res = true;
						}
					}
				}
			}
			
		}

		return res;
	}
	
	/**
	 * Méthode verifiant si on appuie sur la touche haut
	 * @return res
	 */
	public static boolean isMovingUp(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant au deplacement haut
				if(kb.getActionName().equals(Const.A_MOVE_UP))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("MOVE_UPkey is pressed");
							res = true;
						}
					}
				}
			}
			
		}

		return res;
	}

	/**
	 * Méthode verifiant si on appuie sur la touche d'attaque
	 * @return res
	 */
	public static boolean isAttacking(Player p)
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			int playerNumber = kb.getPlayerNumber();

			if(p.getPlayerNumber() == playerNumber)
			{
				//On cherche le keybinding correspondant a une attaque
				if(kb.getActionName().equals(Const.A_ATTACK))
				{
					//On cherche si une des touches du keybinding est pressée
					for(Keyboard.Key key : kb.getKeyNames())
					{
						if(Keyboard.isKeyPressed(key))
						{
							System.out.println("ATTACK key is pressed");
							res = true;
						}
					}
				}
			}
			
		}

		return res;
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * Méthode verifiant si on appuie sur la touche pour quitter le jeu
	 * @return res
	 */
	public static boolean quitKeyPressed()
	{
		boolean res = false;

		for(KeyBinding kb : GameConfig.getKeyBindings())
		{
			//On cherche le keybinding correspondant à quitter
			if(kb.getActionName().equals(Const.A_QUIT))
			{
				//On cherche si une des touches du keybinding est pressée
				for(Keyboard.Key key : kb.getKeyNames())
				{
					if(Keyboard.isKeyPressed(key))
					{
						System.out.println("QUIT key is pressed");
						res = true;
					}
				}
			}
		}

		return res;
	}

}
