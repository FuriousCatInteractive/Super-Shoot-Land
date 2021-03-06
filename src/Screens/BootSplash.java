package Screens;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import Tools.MusicLoader;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @Class BootSplash
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe gérant l'écran de démarrage
 *
 */
public class BootSplash extends cScreen{

    private Sprite cat;
    private Texture maTexture;
    private float ratio;
    private float alpha;
    private boolean ok;
    private boolean ok2;

    private Sound miaou;


    /**
     * Initialise tout les champs
     * @param fenetre
     */
    public void BootSplashInit(RenderWindow fenetre){


        maTexture = new Texture(); // déclaration d'une texture
        cat = new Sprite();

        try {
            maTexture.loadFromFile(Paths.get("res/img/logo-furious-cat-interactive.png")); // on charge la texture qui se trouve dans notre dossier assets
        } catch (IOException e) {
            e.printStackTrace();
        }

        cat.setTexture(maTexture); // on applique la texture à notre sprite
        ratio = (float)fenetre.getSize().y/(maTexture.getSize().y*2);
        cat.setScale(ratio, ratio);

        float posX = fenetre.getSize().x/2;
        float posY = fenetre.getSize().y/2;

        cat.setOrigin(maTexture.getSize().x / 2, maTexture.getSize().y / 2);
        cat.setPosition(posX, posY);

        alpha=0;
        ok = false;
        ok2=false;

        miaou = loadMusic("res/sound/Meow.ogg");
    }

    /**
     * Méthode principale appellé par le main
     * Fait le rafraichissement
     * @param App
     * @return
     */
    public int Run(RenderWindow App){

        boolean Running = true;
        BootSplashInit(App);
        long debut_bootsplash = System.currentTimeMillis();
        int duree=5000;


     /*   for(MusicLoader it : allMusics){
            it.start();
        }*/
        MusicLoader loader = new MusicLoader();
        loader.start();

        while (System.currentTimeMillis()-debut_bootsplash<duree && Running)
        {
            //Verifying events
            for (Event event : App.pollEvents())
            {
                // Window closed
                if (event.type == Type.CLOSED)
                    return (-1);

                //if(event.type == Event.Type.MOUSE_BUTTON_RELEASED)
                   // return 1;

                //Key pressed
                if (event.type == Event.Type.KEY_PRESSED)
                {
                    event.asKeyEvent();
                    if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
                        return (-1);
                  //  else
                       // return 1;
                }
            }

            update(cat);
            App.draw(cat);
            App.display();

            //Clearing screen
            App.clear();
        }

        //Never reaching this point normally, but just in case, exit the application
        System.out.println("bootsplash finit");

            /*for(MusicLoader it : allMusics){
                try {
                it.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        try {
            loader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (1);
    }

    /**
     * update le sprite chat
     * @param cat
     */
    public void update(Sprite cat)
    {
        if(alpha<=255 && !ok)
            alpha+=2f;
        else if(alpha>=0)
        {
            ok = true;
            alpha-=2f;
        }
        cat.setColor(new Color(255, 255, 255, (int) alpha)); // half transparent

        if(cat.getRotation()<360)
        {
            cat.rotate(3f);
            ok2 = true;
        }
        else if(ok2==true)
        {
            //this.startMusic("res/sound/Meow.ogg", );
            miaou.play();
            ok2=false;
        }
    }

}
