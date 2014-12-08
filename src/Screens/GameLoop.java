package Screens;

import Entities.GameEntity;
import Entities.Player;
import Graphics.EntityTexture;
import InputGameLoop.InputMananger;
import Level.Level;
import Tools.Const;
import Tools.KeyboardActions;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import javax.print.attribute.standard.PrinterLocation;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

import static Graphics.EntityTexture.*;

/**
 * Created by coco on 14-11-16.
 */
public class GameLoop extends cScreen {

    public static int returnValue=1000;

    public static Player p1;
    public static Player p2;
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

        if(SelectMode.local){
            System.out.println("play locally");
            p1 = loadPerso(SelectPersoLocal.persoSelect1);
            p2 = loadPerso(SelectPersoLocal.persoSelect2);
        }
        else {
            p1 = loadPerso(SelectPerso.persoSelect);
        }



        lvltest = new Level(20,(AppX/AppY)*20);
        loadLevelToGame(App);

    }

    /**
     * charge le perso coorespondant au num
     * sélectionnéà l'écran précédent
     */
    public Player loadPerso(int num){
        Player p;
        switch (num) {
            case Player.PIKACHU:
                p = loadPlayer("res/img/pikachu-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MARIO:
                p = loadPlayer("res/img/mario-spritesheet.png", 0.007f * AppY);
                break;
            case Player.LINK:
                p = loadPlayer("res/img/link-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MEGAMAN:
                p = loadPlayer("res/img/megaman-spritesheet.png", 0.007f * AppY);
                break;
            default:
                p = loadPlayer("res/img/pikachu-spritesheet.png", 0.007f * AppY);
                break;
        }
        p.setPerso(num);
        return p;

    }

    /**
     * boucle de jeu principale
     * @param App
     * @return
     */
    public int Run(RenderWindow App) {
        musicBackground.stop();
        gameState=Running;
        loadScreenObjects(App);
        inputGL= new InputMananger(App);

        p1.playerReset();
        p1.setPlayerNumber(Const.PLAYER1);
        screenObject.add(p1);
       
        p2.playerReset();   
        p2.setPlayerNumber(Const.PLAYER2);
        screenObject.add(p2);
        
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
            p2.run();

            updateList();

            if(p1.isDead())
                messageVictoire(p2);
            else if (p2.isDead())
                messageVictoire(p1);


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
       // afficherHitbox(App,p1);
        App.display();
    }

    /**
     * affiche la hitbox donnée en bleu
     * @param App
     * @param entity
     */
    private void afficherHitbox(RenderWindow App, GameEntity entity){
        IntRect temp = new IntRect(0,0,0,0);
        temp= entity.getHitbox();
        RectangleShape rect = new RectangleShape(new Vector2f(temp.width, temp.height) );
        rect.setPosition(temp.left, temp.top);
        rect.setFillColor(Color.BLUE);
        App.draw(rect);
    }

    /**
     * charge la map binaire en screenobjects avec
     * texture et hitbox
     * @param App
     */
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

    /**
     * affiche petit message et met le jeu en pause
     * petite musique en bonus =)
     * @return
     */
    private  int  messageDefaite(){
        musicStage1.stop();

        gameOver.play();
        newRect(AppX, AppY/4, 0 ,AppY/2-AppY/8, dark_green);
        loadText("Game Over!", AppX / 2, AppY/2-20, 40);
        loadText("(Appuyez sur une \"ESC\" pour revenir au menu)", AppX / 2, AppY/2+20, 20);
        gameState=Pause;

        return 2;

    }

    /**
     * affiche petit message et met le jeu en pause
     * petite musique en bonus =)
     * @return
     */
    private  int  messageVictoire(Player p){
        musicStage1.stop();

        victory.play();
        newRect(AppX, AppY/4, 0 ,AppY/2-AppY/8, dark_green);
        if(p ==p1)
        loadText("Player 1 win!!!", AppX / 2, AppY/2-20, 40);
        else
            loadText("Player 2 win!!!", AppX / 2, AppY/2-20, 40);
        loadText("(Appuyez sur une \"ESC\" pour revenir au menu)", AppX / 2, AppY/2+20, 20);
        gameState=Pause;

        return 2;

    }

    private void updateList(){
        for (int i =screenObject.size() - 1; i > -1; i--) {
            if (screenObject.get(i) instanceof Player || screenObject.get(i) instanceof Text) {
                screenObject.remove(i);
            }
        }
        loadText("P1: "+p1.getHP(), AppX/8,10,AppY/25);
        loadText("P2: "+p2.getHP(), 7*AppX/8,10,AppY/25);
        ((Text)screenObject.get(screenObject.size()-1)).setColor(dark_green);
        ((Text)screenObject.get(screenObject.size()-2)).setColor(dark_green);
        screenObject.add(p1);
        screenObject.add(p2);

    }

}

