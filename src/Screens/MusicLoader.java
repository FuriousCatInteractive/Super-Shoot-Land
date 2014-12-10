package Screens;

/**
 * Created by Corentin on 07/12/2014.
 */
public class MusicLoader extends Thread {

    /**
     * thread en parrélèle qui charge les musiques grosses
     * (qq mégas)
     */
    public void run(){
        System.out.println("début loadmusic");
        cScreen.musicBackground = cScreen.loadMusic("res/sound/theme-ssb.ogg" );
        cScreen.musicStage1=cScreen.loadMusic("res/sound/battlefield.ogg");
        cScreen.getReady=cScreen.loadMusic("res/sound/getready.ogg");
        cScreen.gameOver = cScreen.loadMusic("res/sound/Wha-Wha.ogg");
        cScreen.victory = cScreen.loadMusic("res/sound/FF7_victory.ogg");
        cScreen.shoot = cScreen.loadMusic("res/sound/shoot.ogg");
        cScreen.pick = cScreen.loadMusic("res/sound/pick.ogg");
        cScreen.select= cScreen.loadMusic("res/sound/select.ogg");
        cScreen.hit = cScreen.loadMusic("res/sound/hit2.ogg");
        cScreen.jump = cScreen.loadMusic("res/sound/jump2.ogg");
        System.out.println("fin loadmusic");
    }
}
