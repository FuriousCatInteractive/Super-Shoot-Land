package Tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsfml.window.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Entities.KeyBinding;

/**
 * @Class XmlKeyBindingsParser
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Parser XML pour la configuration des touches clavier
 *
 */
public class XmlKeyBindingsParser {
	
	/**
	 * Méthode de parsing du fichier XML bindings.xml
	 * @param is
	 * @return waves
	 */
	public static ArrayList<KeyBinding> parseXmlInputStream(InputStream is)
	{
		ArrayList<KeyBinding> keyBindings = null;
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();	
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("bind");
			
			keyBindings = new ArrayList<KeyBinding>();
			
			for(int i=0;i<items.getLength();i++)
			{
				Node item = items.item(i);
				NamedNodeMap attrs = item.getAttributes();
				NodeList childNodes = item.getChildNodes();
				
				//On récupère le type d'action du binding (mouvement, saut ...)
				String type = attrs.getNamedItem("type").getTextContent(); //l'action à effectuer
				String player = attrs.getNamedItem("player").getTextContent(); //joueur associé à l'action
				KeyBinding kb = new KeyBinding(type);
				
				try
				{
					kb.setPlayerNumber(Integer.parseInt(player));
				}
				
				catch(Exception e)
				{
					System.err.println("XML : playerNumber is not an integer");
				}
				
				
				//On récupère chaque nom de touche associé au type d'action
				for(int j=0;j<childNodes.getLength();j++)
				{
					if(childNodes.item(j) instanceof Element)
					{
						Element child = (Element)childNodes.item(j);
						String keyName = child.getAttribute("name"); //touche
						
						
						//On ajoute le KeyBoard.Key correspondant à la valeur texte récupérée
						try
						{
							kb.getKeyNames().add(Keyboard.Key.valueOf(keyName));
						}
						
						catch(IllegalArgumentException e)
						{
							System.err.println("Touche incorrecte : "+Keyboard.Key.valueOf(keyName));
						}
						
					}	
				}
				
				keyBindings.add(kb);
			}
			
			System.out.println("Key bindings :");
			for(KeyBinding k : keyBindings)
			{
				System.out.println(k.getActionName() + k.getKeyNames());
			}
		}
		
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
		
		return keyBindings;
	}

}
