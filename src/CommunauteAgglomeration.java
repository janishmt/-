package projetS5;


import java.util.ArrayList;
import java.util.Scanner;
/**
 * La classe CommunauteAgglomeration représente la communauté d'agglomération avec ses villes, routes et zones de recharge.
 */

public class CommunauteAgglomeration {
   
    private boolean[][] matriceAdjacence;
    private ArrayList<Character> indexVilles;
    private boolean[] zonesDeRecharge;

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
     * @param villeA La première ville de la route.
     * @param villeB La deuxième ville de la route.
     */

    public void ajouterRoute(char villeA, char villeB) {
        int indexA = indexVilles.indexOf(villeA);
        int indexB = indexVilles.indexOf(villeB);
        matriceAdjacence[indexA][indexB] = true;
        matriceAdjacence[indexB][indexA] = true;
    }

    /**
     * Vérifie si une route existe entre deux villes spécifiées dans la communauté d'agglomération.
     *
     * @param villeA L'indice de la première ville.
     * @param villeB L'indice de la deuxième ville.
     * @return true si une route existe, sinon false.
     */

    public boolean existeRoute(int villeA, int villeB) {
        return matriceAdjacence[villeA][villeB];
    }

     /**
     * Ajoute une zone de recharge à une ville spécifiée dans la communauté d'agglomération.
     *
     * @param ville La lettre représentant la ville à laquelle ajouter la zone de recharge.
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
     * @param ville La lettre représentant la ville de laquelle retirer la zone de recharge.
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
     * Vérifie si une ville spécifiée possède une zone de recharge.
     *
     * @param ville La lettre représentant la ville à vérifier.
     * @return true si la ville possède une zone de recharge, sinon false.
     */

    public boolean possedeZoneDeRecharge(char ville) {
        int indexVille = indexVilles.indexOf(ville);
        return zonesDeRecharge[indexVille];
    }

    /**
     * Renvoie le nombre total de villes dans la communauté d'agglomération.
     *
     * @return Le nombre de villes.
     */
    public int getNombreVilles (){
        return matriceAdjacence.length;
    }
    
    /**
     * Renvoie la liste des lettres représentant les villes dans la communauté d'agglomération.
     *
     * @return La liste des lettres des villes.
     */
    
    public ArrayList<Character> getIndexVilles() {
        return indexVilles;
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
     * Vérifie si la communauté d'agglomération respecte la contrainte d'accessibilité.
     *
     * @return true si la contrainte est respectée, sinon false.
     */
    
    public boolean estAccessible() {
        for (int i = 0; i < matriceAdjacence.length; i++) {
            if (!zonesDeRecharge[i]) {
                boolean villeAccessible = false;
                for (int j = 0; j < matriceAdjacence.length; j++) {
                    if (matriceAdjacence[i][j] && zonesDeRecharge[j]) {
                        villeAccessible = true;
                        break;
                    }
                }
                if (!villeAccessible) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Vérifie si une ville spécifiée dans la communauté d'agglomération respecte la contrainte d'accessibilité.
     *
     * @param villeIndex L'indice de la ville à vérifier.
     * @return true si la contrainte est respectée, sinon false.
     */
    public boolean estAccessible(int villeIndex) {
        if (zonesDeRecharge[villeIndex]) {
            return true;
        }

        for (int i = 0; i < matriceAdjacence[villeIndex].length; i++) {
            if (matriceAdjacence[villeIndex][i] && zonesDeRecharge[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initialise toutes les villes de la communauté d'agglomération avec des zones de recharge.
     */
    public void fullBornedeRecharge() {
        for (int i = 0; i < zonesDeRecharge.length; i++) {
            zonesDeRecharge[i] = true;
        }
    }

    /**
     * Initialise une communauté d'agglomération en demandant à l'utilisateur de spécifier le nombre de villes,
     * les noms des villes, les routes entre les villes et les zones de recharge.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @return La communauté d'agglomération nouvellement créée.
     */
    public static CommunauteAgglomeration initialiserCommunaute(Scanner scanner) {
    System.out.print("Entrez le nombre de villes : ");
    int nombreVilles = scanner.nextInt();
    scanner.nextLine(); // Consommer la nouvelle ligne

    CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);

    for (int i = 0; i < nombreVilles; i++) {
        char ville = scanner.nextLine().charAt(0);
        ca.ajouterVille(ville);
    }

    System.out.println("Entrez les routes (chaque route sur une nouvelle ligne, format : A B) : ");
    while (true) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            break; // Fin de la saisie des routes
        }

        String[] parts = line.split(" ");
        char villeA = parts[0].charAt(0);
        char villeB = parts[1].charAt(0);
        ca.ajouterRoute(villeA, villeB);
    }

    // Lire les bornes de recharge
    System.out.println("Entrez les bornes de recharge (séparées par des espaces) : ");
    String bornesRecharge = scanner.nextLine().trim();
    for (int i = 0; i < bornesRecharge.length(); i++) {
        char lettreVille = bornesRecharge.charAt(i);
        ca.ajouterZoneDeRecharge(lettreVille);
    }
    return ca;
}
public boolean[][] getMatriceAdjacence() {
    return matriceAdjacence;
}

public boolean[] getZonesDeRecharge() {
    return zonesDeRecharge;
}
public char getVilleAtIndex(int index) {
    return indexVilles.get(index);
}    
}

    
