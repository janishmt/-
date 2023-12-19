package projetS5;



import java.util.ArrayList;


/**
 * Représente une communauté d'agglomération avec des villes, des routes et des zones de recharge.
 */
public class CommunauteAgglomeration {
    private boolean[][] matriceAdjacence;
    private ArrayList<Character> indexVilles;
    private boolean[] zonesDeRecharge;

    /**
     * Constructeur de la communauté d'agglomération.
     *
     * @param nombreVilles Le nombre initial de villes dans la communauté.
     */
    public CommunauteAgglomeration(int nombreVilles) {
        matriceAdjacence = new boolean[nombreVilles][nombreVilles];
        zonesDeRecharge = new boolean[nombreVilles];
        indexVilles = new ArrayList<>();
    }

       /**
     * Ajoute une ville à la communauté d'agglomération.
     *
     * @param ville La lettre représentant la ville à ajouter.
     */
    public void ajouterVille(char ville) {
        indexVilles.add(ville);
    }

    /**
     * Ajoute une route entre deux villes de la communauté d'agglomération.
     *
     * @param villeA La première ville.
     * @param villeB La deuxième ville.
     */

    public void ajouterRoute(char villeA, char villeB) {
        int indexA = indexVilles.indexOf(villeA);
        int indexB = indexVilles.indexOf(villeB);
        matriceAdjacence[indexA][indexB] = true;
        matriceAdjacence[indexB][indexA] = true;
    }


     /**
     * Ajoute une zone de recharge à une ville spécifiée dans la communauté d'agglomération.
     *
     * @param ville La ville à laquelle ajouter la zone de recharge.
     */
    public void ajouterZoneDeRecharge(char ville) {
        int indexVille = indexVilles.indexOf(ville);

        if (!zonesDeRecharge[indexVille]) {
            zonesDeRecharge[indexVille] = true;
        } else {
            System.out.println("Il existe déjà une borne dans cette ville");
        }
    }

    /**
     * Retire la zone de recharge d'une ville spécifiée dans la communauté d'agglomération.
     *
     * @param ville La ville de laquelle retirer la zone de recharge.
     */
    public void retirerZoneDeRecharge(char ville) {
        int indexVille = indexVilles.indexOf(ville);
        if (zonesDeRecharge[indexVille]) {
            zonesDeRecharge[indexVille] = false;
        } else {
            System.out.println("Il n'existe pas de borne dans cette ville");
        }
    }

     /**
     * Affiche l'état de la communauté d'agglomération, y compris la matrice d'adjacence et les zones de recharge.
     */
    public void afficherEtat() {
        System.out.println("Matrice d'adjacence:");
        for (int i = 0; i < matriceAdjacence.length; i++) {
            for (int j = 0; j < matriceAdjacence.length; j++) {
                System.out.print(matriceAdjacence[i][j] ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println("Zones de recharge:");
        for (int i = 0; i < zonesDeRecharge.length; i++) {
            if (zonesDeRecharge[i]) {
                System.out.println(indexVilles.get(i));
            }
        }
        System.out.println();
    }
   

     /**
     * Initialise toutes les zones de recharge de la communauté d'agglomération.
     */
    public void fullBornedeRecharge() {
        for (int i = 0; i < zonesDeRecharge.length; i++) {
            zonesDeRecharge[i] = true;
        }
    }

    

    public boolean[][] getMatriceAdjacence() {
        return matriceAdjacence;
    }

    public boolean[] getZonesDeRecharge() {
        return zonesDeRecharge;
    }
    public ArrayList<Character> getIndexVilles() {
        return indexVilles;
    }
}



    

