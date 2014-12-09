package Screens;

import Entities.Player;
import Tools.Const;
import Tools.KeyboardActions;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

/**
 * Created by coco on 14-11-16.
 */
public class SelectPersoLocal extends cScreen{

    private Vector2i pos;
    private int menu;
    private int menu2;
    private int nb_choix_menu;

    private int AppX;
    private int AppY;

    public static int persoSelect1;
    public static int persoSelect2;

    private boolean ok1;
    private boolean ok2;

    public SelectPersoLocal() {
        pos = new Vector2i(0,0);
        menu = 0;
        menu2=0;
        nb_choix_menu = 4;
        persoSelect1=1;
        persoSelect2=1;
    }

    /**
     * créer et charge les éléments du screen
     * @param App
     */
    private void loadScreenObjects(RenderWindow App){
        loadFont( "res/font/Volter__28Goldfish_29.ttf");
        AppX = App.getSize().x;
        AppY = App.getSize().y;
        int taille_Font_base = (int)(0.06*AppY);
        int offsetX =AppX/3-20;
        int offsety = AppY/20;

        int hauteur_min = AppY/8;
        int largeur_min = AppX/8;
        int ecart_min = AppX/30;
        int origin_y_min = AppY/5+ecart_min;
        int origin_x_min = AppX/2-largeur_min/2;

        loadText(">",origin_x_min-20, origin_y_min+20+0*(ecart_min+hauteur_min),taille_Font_base);
        loadText(">",origin_x_min-20, origin_y_min+20+1*(ecart_min+hauteur_min),taille_Font_base);
        loadText(">",origin_x_min-20, origin_y_min+20+2*(ecart_min+hauteur_min),taille_Font_base);
        loadText(">",origin_x_min-20, origin_y_min+20+3*(ecart_min+hauteur_min),taille_Font_base);

        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+0*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+1*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+2*(ecart_min+hauteur_min),taille_Font_base);
        loadText("<",origin_x_min+largeur_min+20, origin_y_min+20+3*(ecart_min+hauteur_min),taille_Font_base);


        loadImage("res/img/miniature_pikachu.png",origin_x_min, origin_y_min, largeur_min,hauteur_min );
        loadImage("res/img/miniature_mario.png",origin_x_min, origin_y_min+1*(ecart_min+hauteur_min), largeur_min,hauteur_min );
        loadImage("res/img/miniature_link.png",origin_x_min, origin_y_min+2*(ecart_min+hauteur_min), largeur_min,hauteur_min );
        loadImage("res/img/miniature_megaman.png",origin_x_min, origin_y_min+3*(ecart_min+hauteur_min), largeur_min,hauteur_min );

        loadText("Select your Character", AppX/2,AppY/15,(int)1.6*taille_Font_base);
        ((Text)screenObject.get(screenObject.size()-1)).setColor(light_green);

        loadText("Furious Cat Interactive - 2014",AppX/2, AppY-AppY/20, (int) (taille_Font_base/1.5));
        ((Text)screenObject.get(screenObject.size()-1)).setColor(light_green);

        newRect(AppX, AppY/5, 0 ,0, dark_green);
        newRect(AppX, 10, 0 ,AppY/5, light_green);
        newRect(AppX, AppY/11, 0 ,10*AppY/11, dark_green);
        newRect(AppX, 10, 0 ,10*AppY/11-10, light_green);


    }

    /**
     * méthode principale qui fait l'update
     * @param App
     * @return
     */
    public int Run(RenderWindow App){

       // musicBackground.play();

        loadScreenObjects(App);

        boolean Running = true;

        loadImagePerso(menu+1, 1);
        loadImagePerso(menu2+1, 2);

        ok1=false;
        ok2=false;

        while (Running)
        {


            int returnValue = eventManager(App);
            if(returnValue<=50)
                return  returnValue;

            menuSelectionne(menu, 1);
            menuSelectionne(menu2, 2);


            screenObject.remove(screenObject.size()-1);
            loadImagePerso(menu+1,1);

            screenObject.remove(screenObject.size()-2);
            loadImagePerso(menu2+1,2);


            App.clear(background_green);
            for(int i=screenObject.size()-1 ;i>-1;i--) {
                App.draw(screenObject.get(i));
            }
            App.display();
        }

        //Never reaching this point normally, but just in case, exit the application
        return (-1);
    }

    /**
     * appelle le keybord et le mouse manager
     * @param App
     * @return
     */
    public int eventManager(RenderWindow App){
        //Verifying events
        for (Event event : App.pollEvents())
        {
            // Window closed
            if (event.type == event.type.CLOSED)
            {
                screenObject.clear();
                return (exit);
            }

            /*int returnValueMouse = mouseManager(event, App);
            if(returnValueMouse<=50)
                return  returnValueMouse;*/

            int returnValueKeyboard = keyboardManager(event, App);
            if(returnValueKeyboard <=50)
                return  returnValueKeyboard ;
        }

        //si on ne quitte pa s cet écran
        return 100;
    }

    /**
     * regarde les événements liés à la souris
     * @param event
     * @param App
     * @return
     */
   /*public int mouseManager(Event event, RenderWindow App){
        if (event.type == Event.Type.MOUSE_MOVED) {
            event.asMouseEvent();
            pos = Mouse.getPosition(App);
            for (int i = 0; i < nb_choix_menu; i++)
                if ( ((Text)screenObject.get(i)).getGlobalBounds().contains((float) pos.x, (float) pos.y))
                    menu = i;
        }

        //click de la souris
        if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
            persoSelect1=menu+1;
            event.asMouseEvent();
           // musicBackground.stop();
            screenObject.clear();
            return choixValide();
        }

        //si on ne quitte pas cet écran
        return 100;
    }*/

    /**
     * regarde les évenements liés au clavier
     * @param event
     * @param App
     * @return
     */
    public int keyboardManager(Event event, RenderWindow App){
        //Key pressed
        if (event.type == Event.Type.KEY_PRESSED){
            event.asKeyEvent();
            
            int activePlayer = KeyboardActions.getPlayerKey(event);
			
			switch(activePlayer)
			{
				case Const.PLAYER1:

		            if (KeyboardActions.quitKeyPressed()){
		                screenObject.clear();
		                return  mainMenu;
		            }

		            if (KeyboardActions.isMovingDown()){
		            	cScreen.select.play();
		                menu++;
		                if(menu>nb_choix_menu-1)
		                    menu = 0;
		            }

		            if (KeyboardActions.isMovingUp()) {
		            	cScreen.select.play();
		                menu--;
		                if(menu<0)
		                    menu = nb_choix_menu-1;
		            }

		            //touche d'attaque pour valider les choix
		            if (KeyboardActions.isAttacking()) {
		            	cScreen.pick.play();
		                ok1=true;
		            }
		            
					break;
					
				case Const.PLAYER2:
					if (KeyboardActions.isMovingDown()){
						cScreen.select.play();
		                menu2++;
		                if(menu2>nb_choix_menu-1)
		                    menu2 = 0;
		            }

		            if (KeyboardActions.isMovingUp()) {
		            	cScreen.select.play();
		                menu2--;
		                if(menu2<0)
		                    menu2 = nb_choix_menu-1;
		            }

		            if (KeyboardActions.isAttacking()) {
		            	cScreen.pick.play();
		                ok2=true;
		            }
		            
					break;
					
					default:
						break;	
			} 
			
			if(ok1 && ok2)
			{
				 persoSelect1=menu+1;
                 persoSelect2=menu2+1;
                 cScreen.musicBackground.stop();
                 cScreen.getReady.play();
                 
                 try {
					Thread.sleep((long) cScreen.getReady.getBuffer().getDuration().asSeconds()+500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                 
                 screenObject.clear();
                 return choixValide();
			}
			
			
        }
        
        
        
        //si on ne quitte pas cet écran
        return 100;
    }

    /**
     * hilight si selectionné
     * @param num
     * @param identifiant
     */
    public void menuSelectionne(int num, int identifiant){
        for(int i =0; i<nb_choix_menu;i++)        {
            int it=i+(identifiant-1)*nb_choix_menu;
           // System.out.println(it);
            if( i==num)
                ((Text)screenObject.get(it)).setColor(light_green);
            else
                ((Text)screenObject.get(it)).setColor(dark_green);
        }
    }

    /**
     * si clic ou entrée on change de screen
     * @return
     */
    public int choixValide(){
        int   returnvalue = play;
        return returnvalue;
    }

    /**
     * charge le perso coorespondant au num
     * sélectionnéà l'écran précédent
     */
    public void loadImagePerso(int num, int identifiant)        {
        int x;
        int y = AppY/3;
        int w = AppX/4;
        int h = AppY/2;
        if (identifiant==2)
            x = AppX-AppX/15-w;
        else
            x=AppX/15;
        switch (num) {
            case Player.PIKACHU:
                loadImage("res/img/max_pikachu.png", x, y, w, h);
                break;
            case Player.MARIO:
                loadImage("res/img/max_mario.png",x, y, w, h);
                break;
            case Player.LINK:
                loadImage("res/img/max_link.png", x, y, w, h);
                break;
            case Player.MEGAMAN:
                loadImage("res/img/max_megaman.png", x, y, w, h);
                break;
            default:
                loadImage("res/img/max_pikachu.png", x, y, w, h);
                break;
        }
    }
}
