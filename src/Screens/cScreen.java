package Screens;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by coco on 14-11-16.
 */
public class cScreen {

    protected Sound sound;
    protected Font fontScreen;
    protected ArrayList<Text> texts;

    public cScreen() {
        fontScreen = new Font();
        texts = new ArrayList();
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
        texts.add(text1);
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
}
