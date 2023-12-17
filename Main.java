package projetS5;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CommunauteAgglomeration ca;

        // Utilisateur spécifie le fichier
        System.out.println("Entrez le chemin vers le fichier de la communauté d'agglomération :");
        String cheminFichier = scanner.nextLine();
        File file = new File(cheminFichier);

        try {
            ca = CommunauteAgglomeration.chargerDepuisFichier(file);
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : Fichier non trouvé.");
            return;
        }

        Menu.gererRoutes(scanner, ca);
        Menu.gererZonesDeRecharge(scanner, ca);

        ca.afficherEtat();
        scanner.close();
    }
}
