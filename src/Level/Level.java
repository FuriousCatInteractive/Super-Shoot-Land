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
             remplirLigne(i, 1);

        //ensuite on supprime un certain pourcentage de lignes
        Random hasard = new Random();
       // hasard.nextInt(hauteur);
        remplirLigne(0,0);
        remplirLigne(1,0);
        for(int i =2;i<(hauteur*90)/100;i++) 
                remplirLigne(hasard.nextInt(hauteur),0);

        verifLigneAdjacentes();

        for(int i =0;i<hauteur;i++) {

            if(mapBinaire[i][0]==1)
                for(int j = 0;j<hasard.nextInt((largeur*60)/100)+1; j++)
                    mapBinaire[i][hasard.nextInt(largeur)]=0;
            mapBinaire[i][0]=0;
            mapBinaire[i][largeur-1]=0;
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
        for(int j =0; j<largeur;j++){
            mapBinaire[numLigne][j]=val;
        }
    }

    /**
     * vérifit et suppimme si deux lignes côté à côtes sont pleines
     */
    private void verifLigneAdjacentes(){
        int compteur=1;
        for(int i=2;i<hauteur-1;i++){
            if(mapBinaire[i+1][4]==1){
                compteur=1;
                remplirLigne(i,0);
            }
            else{
                compteur++;
                System.out.println(compteur);
                if(compteur==3){
                    System.out.println("---"+i);
                    remplirLigne(i-1,1);
                }
            }
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
