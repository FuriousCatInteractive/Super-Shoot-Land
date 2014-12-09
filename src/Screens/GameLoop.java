package Screens;

import Entities.GameEntity;
import Entities.Player;
import InputGameLoop.InputManager;
import Level.Level;
import Tools.Const;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * @Class GameLoop
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe gérant la boucle de jeu principale
 *
 */
public class GameLoop extends cScreen {

    public static int returnValue=1000;

    public static Player p1;
    public static Player p2;
    public Level lvltest ;

    public static int AppX;
    public static int AppY;

    private InputManager inputGL;

    public final static int Running=1;
    public final static int Pause=0;
    public static int gameState;

    public GameLoop() {
        gameState=Running;

    }

    /**
     * Load les éléments qui seront à afficher
     * @param App
     */
    private void loadScreenObjects(RenderWindow App) {
        loadFont("res/font/Volter__28Goldfish_29.ttf");
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

        loadText("P1  ", AppX/16,15,AppY/25);
        loadText("  P2", 15*AppX/16,15,AppY/25);
        ((Text)screenObject.get(screenObject.size()-1)).setColor(Const.dark_green);
        ((Text)screenObject.get(screenObject.size()-2)).setColor(Const.dark_green);

        lvltest = new Level(20,(AppX/AppY)*20);
        loadLevelToGame(App);
    }

    /**
     * Charge le perso coorespondant au num
     * sélectionnéà l'écran précédent
     */
    public Player loadPerso(int num){
        Player p;
        switch (num) {
            case Player.PIKACHU:
                p = loadPlayer(Const.IMG_PATH+"pikachu-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MARIO:
                p = loadPlayer(Const.IMG_PATH+"mario-spritesheet.png", 0.007f * AppY);
                break;
            case Player.LINK:
                p = loadPlayer(Const.IMG_PATH+"link-spritesheet.png", 0.007f * AppY);
                break;
            case Player.MEGAMAN:
                p = loadPlayer(Const.IMG_PATH+"megaman-spritesheet.png", 0.007f * AppY);
                break;
            default:
                p = loadPlayer(Const.IMG_PATH+"pikachu-spritesheet.png", 0.007f * AppY);
                break;
        }
        p.setPerso(num);
        return p;

    }

    /**
     * Boucle de jeu principale
     * @param App
     * @return
     */
    public int Run(RenderWindow App) {

        musicBackground.stop();
        gameState=Running;
        loadScreenObjects(App);
        inputGL= new InputManager(App);

        p1.setPlayerNumber(Const.PLAYER1);
        p1.playerReset();
        screenObject.add(p1);

        p2.setPlayerNumber(Const.PLAYER2);
        p2.playerReset();
        screenObject.add(p2);
        
        musicStage1.play();
        musicStage1.setLoop(true);
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
                return Const.mainMenu;
            afficher(App);
        }
        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    /**
     * Changement d'écran
     * @param nextMenu
     * @return
     */
    public static int retourne(int nextMenu){
        musicStage1.stop();
        screenObject.clear();
        return nextMenu;
    }

    /**
     * Affichage des éléments
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
     * Affiche la hitbox donnée en bleu (pour le debug)
     * @param App
     * @param entity
     */
    @SuppressWarnings("unused")
	private void afficherHitbox(RenderWindow App, GameEntity entity){
        IntRect temp = new IntRect(0,0,0,0);
        temp= entity.getHitbox();
        RectangleShape rect = new RectangleShape(new Vector2f(temp.width, temp.height) );
        rect.setPosition(temp.left, temp.top);
        rect.setFillColor(Color.BLUE);
        App.draw(rect);
    }

    /**
     * Charge la map binaire en screenobjects avec
     * texture et hitbox
     * @param App
     */
    public  void loadLevelToGame(RenderWindow App){
        int h_block = AppY/lvltest.getHauteur();
        int w_block = AppX/lvltest.getLargeur();
        for(int i =0;i<lvltest.getHauteur();i++) {
            for (int j = 0; j < lvltest.getLargeur(); j++) {
                if(lvltest.mapBinaire[i][j]==1)
                    loadImage(Const.IMG_PATH+"block.png", j*w_block,i*h_block, w_block, h_block);
            }
        }
    }

    /**
     * Affiche petit message et met le jeu en pause
     * @return
     */
    @SuppressWarnings("unused")
	private  int  messageDefaite(){
        musicStage1.stop();

        gameOver.play();
        newRect(AppX, AppY/4, 0 ,AppY/2-AppY/8, Const.dark_green);
        loadText("Game Over!", AppX / 2, AppY/2-20, 40);
        loadText("(Appuyez sur une \"ESC\" pour revenir au menu)", AppX / 2, AppY/2+20, 20);
        gameState=Pause;

        return 2;

    }

    /**
     * Affiche petit message et met le jeu en pause
     * petite musique en bonus =)
     * @return
     */
    private  int  messageVictoire(Player p){
        musicStage1.stop();
        musicBackground.setLoop(true);
        victory.play();
        newRect(AppX, AppY/4, 0 ,AppY/2-AppY/8, Const.dark_green);
        if(p ==p1)
        loadText("Player 1 win!!!", AppX / 2, AppY/2-20, 40);
        else
            loadText("Player 2 win!!!", AppX / 2, AppY/2-20, 40);
        loadText("(Appuyez sur une \"ESC\" pour revenir au menu)", AppX / 2, AppY/2+20, 20);
        gameState=Pause;

        return 2;

    }

    /**
     * Méthode qui supprime les screenobjects et les réaffiche
     */
    private void updateList(){
        for (int i =screenObject.size() - 1; i > -1; i--) {
            if (screenObject.get(i) instanceof Player
                   // || screenObject.get(i) instanceof Text
                    || screenObject.get(i) instanceof RectangleShape ) {
                screenObject.remove(i);
            }
        }
       int y=15;
        int largeur=AppX/4;
        int hauteur=AppY/30;
        newRect(p1.getHP()*largeur/100, hauteur, AppX/12 , (int) (y/1.5), Const.dark_green);
        newRect(p2.getHP()*largeur/100, hauteur, 5*AppX/6+AppX/12+largeur/100-p2.getHP()*largeur/100-AppX/100, (int) (y/1.5), Const.dark_green);
        screenObject.add(p1);
        screenObject.add(p2);

    }

}

