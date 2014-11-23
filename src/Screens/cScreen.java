package Screens;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.w3c.dom.css.Rect;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static Graphics.EntityTexture.loadTexture;

/**
 * Created by coco on 14-11-16.
 */
public class cScreen {
    public static final Color dark_green = new Color(35,67,49, 255);
    public static final Color background_green = new Color(83,122,62, 255);
    public static final Color logo_green = new Color(167,186, 74, 255);
    public static final Color light_green = new Color(211,226,154, 255);

    protected Sound sound;
    protected Font fontScreen;
    protected ArrayList<Drawable> screenObject = new ArrayList();

    public cScreen() {
        fontScreen = new Font();
    }

    public int Run(RenderWindow App){ return  0;}

    protected void loadFont(String path){
        try {
            fontScreen.loadFromFile(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Failed to load the font:");
            e.printStackTrace();
        }
    }
    protected void loadText(String text, int posX, int posY, int taille){
        Text text1= new Text();
        text1.setFont(fontScreen);
        text1.setCharacterSize(taille);
        text1.setString(text);

        text1.setOrigin(text1.getLocalBounds().width/2, text1.getGlobalBounds().height/2);
        text1.setPosition(posX, posY);
        screenObject.add(text1);
    }

    protected void loadImage(String path, int posX, int posY){
        Sprite background = new Sprite();
        try {
            Texture maTexture = new Texture();
            maTexture.loadFromFile(Paths.get(path)); // on charge la texture qui se trouve dans notre dossier assets
            background.setTexture(maTexture); // on applique la texture à notre sprite
        } catch (IOException e) {
            System.err.println("Failed to load the image:");
            e.printStackTrace();
        }
        background.setPosition(posX, posY);
        screenObject.add(background);
    }

    protected void newRect(int weight, int height, int posX, int posY,Color color){
        RectangleShape rect;
        rect = new RectangleShape(new Vector2f(weight, height));
        rect.setFillColor(color);
        rect.setPosition(posX, posY);
        screenObject.add(rect);
    }

    protected void startMusic(String path)
    {
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
        sound = new Sound();
        sound.setBuffer(soundBuffer);
        sound.play();
        //sound.setVolume(40.0f);
    }

   public void loadSpriteSheet(String path, float scale){
       Sprite mario = new Sprite();
       mario.setTexture(loadTexture(path));
       mario.setScale(scale,scale);
       screenObject.add(mario);
   }
}
