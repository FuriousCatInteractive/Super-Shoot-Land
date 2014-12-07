package Level;

import org.jsfml.graphics.RenderWindow;

import java.util.Random;

/**
 * Created by Corentin on 29/11/2014.
 */
public class Level {

    public int[][] mapBinaire;

   private int largeur, hauteur;

    /**
     * constructeur par défault 10 lignes 10 colonnes
     */
    public Level(){
        this(10,10);
    }

    /**
     * constructeur utilisé
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
     * rempli une map de 0 et de 1 pour les plateformes
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
        int amplitude = 3;
        for(int i =2;i<hauteur-1;i+=hasard.nextInt(1)+3) {
            for (int j = 0; j<hasard.nextInt(3)+1; j++){
                int pos = hasard.nextInt(largeur - 2 * amplitude) + amplitude;
                createPlateforme(i, 1, pos - hasard.nextInt(amplitude)-1, pos + hasard.nextInt(amplitude)+1);
            }
        }
    }

    /**
     * affiche la map binaire
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
     * rempli la ligne de la valeur
     * @param numLigne
     * @param val
     */
    private void remplirLigne(int numLigne, int val){
           createPlateforme(numLigne,val,0, largeur);
    }

    /**
     * créer une plateforme
     * @param numLigne
     * @param val
     * @param début
     * @param fin
     */
    private void createPlateforme(int numLigne, int val, int début, int fin){
        for(int j =début; j<fin;j++){
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
