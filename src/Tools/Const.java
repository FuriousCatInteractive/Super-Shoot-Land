package Tools;

/**
 * @Class Const
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe contenant des constantes globales
 *
 */
public class Const {

	//Chemins d'accès
	public final static String XML_BINDINGS_PATH = "src/Assets/bindings.xml";
	public final static String IMG_PATH = "res/img/";
	
	//Joueurs
	public final static int PLAYER1 = 1;
	public final static int PLAYER2 = 2;
	public final static int PLAYER3 = 3;
	public final static int PLAYER4 = 4;

	//Personnages
	public final static String MARIO = "mario";
	public final static String PIKACHU = "pikachu";
	public final static String LINK = "link";
	public final static String KIRBY = "kirby";
	public final static String MEGAMAN = "megaman";

	//Ensemble des actions (saut, mouvement...) référencés pour les key bindings
	public final static String A_MOVE_LEFT = "MOVE_LEFT";
	public final static String A_MOVE_RIGHT = "MOVE_RIGHT";
	public final static String A_ATTACK = "ATTACK";
	public final static String A_JUMP = "JUMP";
	public final static String A_QUIT = "QUIT";

	//Valeurs float

	//Paramètres particules
	public final static float PARTICLE_SCALE_X = 2f;
	public final static float PARTICLE_SCALE_Y = 2f;
	public final static int PARTICLE_MOVE_X_LEFT = -1;
	public final static int PARTICLE_MOVE_X_RIGHT = 1;
	public final static float PARTICLE_SPEED = 6f; //pixels
	public final static long PARTICLE_LIFETIME = 1000; //millisecondes


}
