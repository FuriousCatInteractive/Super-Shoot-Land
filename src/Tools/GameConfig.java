package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import Entities.KeyBinding;

/**
* @Class GameConfig
* @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
* 
* Configuration des paramètres de jeu
*
*/
public class GameConfig {
	private static ArrayList<KeyBinding> keyBindings; //configuration clavier (liste des bindings de touches)

	/**
	 * Méthode qui va créer la configuration des touches clavier
	 */
	public static void configKeyBindings()
	{

    	//Parsing du fichier bindings.xml
    	InputStream keyBindingsFile;
    	
    	try 
		{
    		keyBindingsFile = new FileInputStream(new File(Const.XML_BINDINGS_PATH));
        	keyBindings = XmlKeyBindingsParser.parseXmlInputStream(keyBindingsFile);
		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<KeyBinding> getKeyBindings() {
		return keyBindings;
	}

	
}
