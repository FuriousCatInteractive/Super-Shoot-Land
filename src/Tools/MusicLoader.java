package Tools;

import Screens.cScreen;

/**
 * @Class MusicLoader
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe gérant le chargement de sons sous forme d'un Thread
 *
 */
public class MusicLoader extends Thread {


    /**
     * thread en parallèle qui charge les musiques de grosse taille
     * (quelques mégas)
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
