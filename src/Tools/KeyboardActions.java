package Tools;

import org.jsfml.window.Keyboard;

import Entities.KeyBinding;

/**
 * @Class KeyboardActions
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe contenant des méthodes vérifiant si une action est effectuée
 *
 */
public class KeyboardActions {
	
	//TODO remplacer toutes les méthodes copiées collées par une seule qui vérifie l'action effectuée
	//(pour faire plus propre et moins de copier coller)

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
