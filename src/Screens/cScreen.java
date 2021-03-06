package Screens;

import Entities.GameEntity;
import Entities.Player;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static Graphics.EntityTexture.loadTexture;

/**
 * @Class cScreen
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe gérant l'affichage
 *
 */
public class cScreen {


	public static Sound musicBackground;
    public static Sound musicStage1;
    public static Sound getReady;
	public static Sound shoot;
	public static Sound pick;
	public static Sound select;
	public static Sound gameOver;
	public static Sound victory;
    public static Sound hit;
    public static Sound jump;


    protected Font fontScreen;
    public static ArrayList<Drawable> screenObject;

    /**
     * constructeur par défault
     */
    public cScreen() {
    	screenObject = new ArrayList<Drawable>();
        fontScreen = new Font();
    }

    /**
     * Méthode qui sera appelée par le main
     * elle sera réimplémentée dans les classes enfants
     * @param App
     * @return
     */
    public int Run(RenderWindow App){ return  0;}

    /**
     * charge un epolice en mémoire
     * @param path
     */
    protected void loadFont(String path){
        try {
            fontScreen.loadFromFile(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Failed to load the font:");
            e.printStackTrace();
        }
    }

    /**
     * Charge du texte et le met dans le tableau screenobjetcs
     * @param text
     * @param posX
     * @param posY
     * @param taille
     */
    protected void loadText(String text, int posX, int posY, int taille){
        Text text1= new Text();
        text1.setFont(fontScreen);
        text1.setCharacterSize(taille);
        text1.setString(text);

        text1.setOrigin(text1.getLocalBounds().width/2, text1.getGlobalBounds().height/2);
        text1.setPosition(posX, posY);
        screenObject.add(text1);
    }

    /**
     * Charge une image sans scale
     * @param path
     * @param posX
     * @param posY
     */
    protected void loadImage(String path, int posX, int posY){
        loadImage(path, posX, posY,1.0f,1.0f);
    }

    /**
     * Charge une image avec scale et la mets
     * dans le tableau screen objects
     * @param path
     * @param posX
     * @param posY
     * @param sizeX
     * @param sizeY
     */
    protected void loadImage(String path, int posX, int posY, float sizeX, float sizeY){
        GameEntity background = new GameEntity();
        try {
            Texture maTexture = new Texture();
            maTexture.loadFromFile(Paths.get(path)); // on charge la texture qui se trouve dans notre dossier assets
            background.setTexture(maTexture); // on applique la texture à notre sprite
        } catch (IOException e) {
            System.err.println("Failed to load the image:");
            e.printStackTrace();
        }
        background.setScale(sizeX/background.getGlobalBounds().width,sizeY/background.getGlobalBounds().height);
        background.setPosition(posX, posY);
        background.setHitbox(new IntRect(posX,posY,(int)(background.getGlobalBounds().width), (int) background.getGlobalBounds().height));
        screenObject.add(background);
    }

    /**
     * Crée un rect et le met dans le tableau screenobjects
     * @param weight
     * @param height
     * @param posX
     * @param posY
     * @param color
     */
    protected void newRect(int weight, int height, int posX, int posY,Color color){
        RectangleShape rect;
        rect = new RectangleShape(new Vector2f(weight, height));
        rect.setFillColor(color);
        rect.setPosition(posX, posY);
        screenObject.add(rect);
    }

    /**
     * Chargement de musique
     * @param path
     * @return
     */
    public static Sound loadMusic(String path)
    {
        Sound sound1 = new Sound();
        SoundBuffer soundBuffer = new SoundBuffer();
        try {
            soundBuffer.loadFromFile(Paths.get(path));
            //System.out.println("Sound duration: " + soundBuffer.getDuration().asSeconds() + " seconds");
        } catch(IOException ex) {
            //Something went wrong
            System.err.println("Failed to load the sound:");
            ex.printStackTrace();
        }

        //Create a sound and set its buffer
        //sound = new Sound();
        sound1.setBuffer(soundBuffer);
        //sound.play();
        //sound.setVolume(40.0f);
        return sound1;
    }

    /**
     * Charge la texture d'un joueur avec
     * le chemin vers sa spritesheet
     * @param path
     * @param scale
     * @return
     */
   public Player loadPlayer(String path, float scale){
       System.out.println(path);
       Player mario = new Player();
       mario.setTexture(loadTexture(path));
       mario.setImage_h(mario.getLocalBounds ().height);
       mario.setImage_w(mario.getLocalBounds ().width);
       mario.setScale(scale,scale);
       return mario;
   }

}
