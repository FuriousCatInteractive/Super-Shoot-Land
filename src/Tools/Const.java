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
	
	//Ensemble des actions (saut, mouvement...) référencés pour les key bindings
	public final static String A_MOVE_LEFT = "MOVE_LEFT";
	public final static String A_MOVE_RIGHT = "MOVE_RIGHT";
	public final static String A_ATTACK = "ATTACK";
	public final static String A_JUMP = "JUMP";
	public final static String A_QUIT = "QUIT";
	
	//Valeurs float
	
	 //Paramètres particules
	public final static float PARTICLE_SCALE_X = 0.1f;
	public final static float PARTICLE_SCALE_Y = 0.1f;
	public final static int PARTICLE_MOVE_X_LEFT = -1;
	public final static int PARTICLE_MOVE_X_RIGHT = 1;
	public final static float PARTICLE_SPEED = 0.1f;
	

}
