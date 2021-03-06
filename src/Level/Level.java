package Level;

import java.util.Random;

/**
 * @Class Level
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Classe représentant un niveau
 *
 */
public class Level {

    public int[][] mapBinaire;

   private int largeur, hauteur;

    /**
     * Constructeur par défaut 10 lignes 10 colonnes
     */
    public Level(){
        this(10,10);
    }

    /**
     * Constructeur utilisé
     * @param larg
     * @param haut
     */
    public Level(int haut, int larg){
        largeur=larg;
        hauteur=haut;
        generateMap();
        afficheMapBinaire();
    }

    /**
     * Remplit une map de 0 et de 1 pour les plateformes
     * @return
     */
    private void generateMap(){
        mapBinaire = new int[hauteur][largeur];

        //on commence en remplissant de 1
        for(int i =0;i<hauteur;i++)
             remplirLigne(i, 0);

        //ensuite on supprime un certain pourcentage de lignes
        Random hasard = new Random();
       // hasard.nextInt(hauteur);
        remplirLigne(0,0);
        remplirLigne(1,0);
        remplirLigne(2,0);
        int amplitude = 3;
        for(int i =3;i<hauteur-1;i+=hasard.nextInt(1)+3) {
            for (int j = 0; j<hasard.nextInt(3)+1; j++){
                int pos = hasard.nextInt(largeur - 2 * amplitude) + amplitude;
                createPlateforme(i, 1, pos - hasard.nextInt(amplitude)-1, pos + hasard.nextInt(amplitude)+1);
            }
        }
    }

    /**
     * Affiche la map binaire
     */
    private void afficheMapBinaire(){
        for(int i =0;i<hauteur;i++){
            for(int j =0;j<largeur;j++){
                System.out.print(mapBinaire[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Remplit la ligne de la valeur
     * @param numLigne
     * @param val
     */
    private void remplirLigne(int numLigne, int val){
           createPlateforme(numLigne,val,0, largeur);
    }

    /**
     * Créer une plateforme
     * @param numLigne
     * @param val
     * @param debut
     * @param fin
     */
    private void createPlateforme(int numLigne, int val, int debut, int fin){
        for(int j =debut; j<fin;j++){
            mapBinaire[numLigne][j]=val;
        }
    }



    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
