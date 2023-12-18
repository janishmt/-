package projetS5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommunauteAgglomeration {
    private boolean[][] matriceAdjacence;
    private ArrayList<Character> indexVilles;
    private boolean[] zonesDeRecharge;

    public CommunauteAgglomeration(int nombreVilles) {
        matriceAdjacence = new boolean[nombreVilles][nombreVilles];
        zonesDeRecharge = new boolean[nombreVilles];
        indexVilles = new ArrayList<>();
    }

    public void ajouterVille(char ville) {
        indexVilles.add(ville);
    }

    public void ajouterRoute(char villeA, char villeB) {
        int indexA = indexVilles.indexOf(villeA);
        int indexB = indexVilles.indexOf(villeB);
        matriceAdjacence[indexA][indexB] = true;
        matriceAdjacence[indexB][indexA] = true;
    }

    public void ajouterZoneDeRecharge(char ville) {
        int indexVille = indexVilles.indexOf(ville);

        if (!zonesDeRecharge[indexVille]) {
            zonesDeRecharge[indexVille] = true;
        } else {
            System.out.println("Il existe déjà une borne dans cette ville");
        }
    }

    public void retirerZoneDeRecharge(char ville) {
        int indexVille = indexVilles.indexOf(ville);
        if (zonesDeRecharge[indexVille]) {
            zonesDeRecharge[indexVille] = false;
        } else {
            System.out.println("Il n'existe pas de borne dans cette ville");
        }
    }

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
   
    public static CommunauteAgglomeration chargerDepuisFichier(File file) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(file)) {
        int nombreVilles = Integer.parseInt(scanner.nextLine().trim());
        CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);

        for (int i = 0; i < nombreVilles; i++) {
            char ville = scanner.nextLine().charAt(0);
            ca.ajouterVille(ville);
        }

        // Chercher le champ "Routes"
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("Routes")) {
                break; // On a trouvé le champ "Routes", on peut commencer à lire les routes
            }
        }

        // Lire les routes
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("Recharges")) {
                break; // On a trouvé le champ "Recharges", on peut commencer à lire les bornes de recharge
            }

            String[] parts = line.split(" ");
            char villeA = parts[0].charAt(0);
            char villeB = parts[1].charAt(0);
            ca.ajouterRoute(villeA, villeB);
        }

        // Initialiser avec la solution naïve si nécessaire
        if (!ca.estAccessible()) {
            System.out.println("La communauté actuelle ne respecte pas la contrainte d'accessibilité.");
            System.out.println("Initialisation avec la solution naïve (une zone de recharge dans chaque ville).");
            ca.fullBornedeRecharge();
        }

        // Lire les bornes de recharge
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split(" ");
            for (String ville : parts) {
                char lettreVille = ville.charAt(0);
                ca.ajouterZoneDeRecharge(lettreVille);
            }
        }

        return ca;

    } catch (FileNotFoundException e) {
        // Gérer l'exception ici ou la propager si nécessaire
        throw e;
    }
}

    
    
    public void sauvegarderDansFichier(File fichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            // Écrire les déclarations des villes
            for (char ville : indexVilles) {
                writer.write("ville(" + ville + ").");
                writer.newLine();
            }

            // Écrire les déclarations des routes
            for (int i = 0; i < matriceAdjacence.length; i++) {
                for (int j = i + 1; j < matriceAdjacence.length; j++) {
                    if (matriceAdjacence[i][j]) {
                        writer.write("route(" + indexVilles.get(i) + "," + indexVilles.get(j) + ").");
                        writer.newLine();
                    }
                }
            }

            // Écrire les déclarations des zones de recharge
            for (int i = 0; i < zonesDeRecharge.length; i++) {
                if (zonesDeRecharge[i]) {
                    writer.write("recharge(" + indexVilles.get(i) + ").");
                    writer.newLine();
                }
            }

            System.out.println("Communauté d'agglomération sauvegardée avec succès.");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde de la communauté d'agglomération dans le fichier.");
            throw e;
        }
    }

    
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
    
    public void fullBornedeRecharge() {
        for (int i = 0; i < zonesDeRecharge.length; i++) {
            zonesDeRecharge[i] = true;
        }
    }

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

    
