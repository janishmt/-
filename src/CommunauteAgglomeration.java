package projetS5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class CommunauteAgglomeration {
    private boolean[][] matriceAdjacence;
    private ArrayList<Character> indexVilles;
    private boolean[] zonesDeRecharge;

    /**
     * Constructeur de la classe CommunauteAgglomeration.
     *
     * @param nombreVilles Le nombre initial de villes dans la communauté.
     */
    public CommunauteAgglomeration(int nombreVilles) {
        matriceAdjacence = new boolean[nombreVilles][nombreVilles];
        zonesDeRecharge = new boolean[nombreVilles];
        indexVilles = new ArrayList<>();
    }

    /**
     * Ajoute une nouvelle ville à la communauté.
     *
     * @param ville La lettre représentant la nouvelle ville.
     */
    public void ajouterVille(char ville) {
        indexVilles.add(ville);
    }

    /**
     * Ajoute une route entre deux villes dans la communauté.
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
     * Ajoute une zone de recharge à une ville spécifiée.
     *
     * @param ville La ville à laquelle ajouter une zone de recharge.
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
     * Retire la zone de recharge d'une ville spécifiée.
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
     * Affiche l'état actuel de la communauté, y compris la matrice d'adjacence
     * et les zones de recharge.
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
     * Charge une communauté d'agglomération à partir d'un fichier texte.
     *
     * @param file Le fichier à partir duquel charger la communauté d'agglomération.
     * @return La communauté d'agglomération chargée.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     */



    public void optimiserBornesAvecContrainte(int k) {
        int i = 0;
        int scoreCourant = nombreTotalBornes();

        while (i < k) {
            char ville = choisirVilleAleatoire();

            boolean avaitBorne = aZoneDeRecharge(ville);

            if (avaitBorne) {
                retirerZoneDeRecharge(ville);
            } else {
                if (!auMoinsUnVoisinAvecBorne(ville)) {
                    ajouterZoneDeRecharge(ville);
                }
            }

            int nouveauScore = nombreTotalBornes();

            if (nouveauScore < scoreCourant) {
                i = 0;
                scoreCourant = nouveauScore;
            } else {
                i++;
            }
        }
    }

    public boolean auMoinsUnVoisinAvecBorne(char ville) {
        int index = indexVilles.indexOf(ville);
        for (int i = 0; i < matriceAdjacence.length; i++) {
            if (matriceAdjacence[index][i] && zonesDeRecharge[i]) {
                return true;
            }
        }
        return false;
    }

    public int nombreTotalBornes() {
        int total = 0;
        for (boolean zoneDeRecharge : zonesDeRecharge) {
            if (zoneDeRecharge) {
                total++;
            }
        }
        return total;
    }
    
    public static CommunauteAgglomeration chargerDepuisFichier(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int nombreVilles = Integer.parseInt(scanner.nextLine().trim());
        CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);

        for (int i = 0; i < nombreVilles; i++) {
            char ville = scanner.nextLine().charAt(0);
            ca.ajouterVille(ville);
        }

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            char villeA = line[0].charAt(0);
            char villeB = line[1].charAt(0);
            ca.ajouterRoute(villeA, villeB);
        }

        return ca;
    }
}
