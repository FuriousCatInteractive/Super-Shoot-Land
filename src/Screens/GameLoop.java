package Screens;

import Entities.GameEntity;
import Entities.Player;
import Graphics.EntityTexture;
import InputGameLoop.InputMananger;
import Level.Level;
import Tools.KeyboardActions;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop extends cScreen {

    public final static int mainMenu = 1;
    public final static int exit = -1;
    public static int returnValue=1000;

    public static Player p1;
    public Level lvltest ;

    public static int AppX;
    public static int AppY;

    private InputMananger inputGL;

    public final static int Running=1;
    public final static int Pause=0;
    public static int gameState;

    public GameLoop() {
        gameState=Running;

    }

    /**
     * load les éléments qui seront à afficher
     * @param App
     */
    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
        int taille_Font_base = 40;
        AppX = App.getSize().x;
        AppY = App.getSize().y;

        loadImage("res/img/stage1.png",0,0, AppX,AppY);
        ((GameEntity)screenObject.get(screenObject.size()-1)).setHitbox(new IntRect(0,0,0,0));

        loadPerso(SelectPerso.persoSelect);
        lvltest = new Level(20,(AppX/AppY)*20);
        loadLevelToGame(App);

    }

    /**
     * charge le perso coorespondant au num
     * sélectionnéà l'écran précédent
     */
    public void loadPerso(int num){

        switch (num) {
            case Player.PIKACHU:
                p1 = loadPlayer("res/img/pikachu-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MARIO:
                p1 = loadPlayer("res/img/mario-spritesheet.png", 0.007f * AppY);
                break;
            case Player.LINK:
                p1 = loadPlayer("res/img/link-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MEGAMAN:
                p1 = loadPlayer("res/img/megaman-spritesheet.png", 0.007f * AppY);
                break;
            default:
                p1 = loadPlayer("res/img/pikachu-spritesheet.png", 0.007f * AppY);
                break;
        }
        p1.setPerso(num);

    }

    /**
     * boucle de jeu principale
     * @param App
     * @return
     */
    public int Run(RenderWindow App) {
        gameState=Running;
        loadScreenObjects(App);
        inputGL= new InputMananger(App);

        p1.playerReset();

        musicStage1.play();
        inputGL.start();
        // Thread threaPlayer1 = new Thread(p1);
        // threaPlayer1.start();

        while (gameState == Running) {

            //returnvalue mis à jour par thread input
            inputGL.run();
            if (returnValue <= 50)
                return returnValue;

            p1.run();
            if(p1.isDead())
                messageDefaite();
            afficher(App);
        }
        while(gameState == Pause){

            inputGL.run();
            if(returnValue <= 50)
                return mainMenu;
            afficher(App);
        }
        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    /**
     * si on change de screen
     * @param nextMenu
     * @return
     */
    public static int retourne(int nextMenu){
        musicStage1.stop();
        screenObject.clear();
        return nextMenu;
    }

    /**
     * l'affichage en tant que tel
     * @param App
     */
    public void afficher(RenderWindow App){
        App.clear(Color.RED);


        for (int i = 0; i <  screenObject.size() ; i++) {
            App.draw(screenObject.get(i));
            //if(screenObject.get(i) instanceof GameEntity)
            //    afficherHitbox(App, (GameEntity) screenObject.get(i));
        }
        App.draw(p1);
       // afficherHitbox(App,p1);
        App.display();
    }

    private void afficherHitbox(RenderWindow App, GameEntity entity){
        IntRect temp = new IntRect(0,0,0,0);
        temp= entity.getHitbox();
        RectangleShape rect = new RectangleShape(new Vector2f(temp.width, temp.height) );
        rect.setPosition(temp.left, temp.top);
        rect.setFillColor(Color.BLUE);
        App.draw(rect);
    }

    public  void loadLevelToGame(RenderWindow App){
        int h_block = AppY/lvltest.getHauteur();
        int w_block = AppX/lvltest.getLargeur();
        for(int i =0;i<lvltest.getHauteur();i++) {
            for (int j = 0; j < lvltest.getLargeur(); j++) {
                if(lvltest.mapBinaire[i][j]==1)
                    loadImage("res/img/block.png", j*w_block,i*h_block, w_block, h_block);
            }
        }
    }

    private  int  messageDefaite(){
        musicStage1.stop();

        gameOver.play();
        newRect(AppX, AppY/4, 0 ,AppY/2-AppY/8, dark_green);
        loadText("Game Over!", AppX / 2, AppY/2-20, 40);
        loadText("(Appuyez sur une \"ESC\" pour revenir au menu)", AppX / 2, AppY/2+20, 20);
        gameState=Pause;

        return 2;

    }
}

