package projetS5;

import java.io.*;
import java.util.Scanner;

/**
 * Classe principale du programme, gérant le menu principal et l'interaction avec l'utilisateur.
 */
public class Main {
    public static void main(String[] args) {

        GestionEntreesSorties.creerNouveauFichierTexte("monNouveauFichier.txt");

        Scanner scanner = new Scanner(System.in);
        CommunauteAgglomeration ca = null;

        if (args.length != 1) {
            System.out.println("Veuillez fournir le chemin vers le fichier texte représentant la communauté d'agglomération.");
            scanner.close();
            return;
        }
        
        try {
            // Charger la communauté d'agglomération depuis le fichier au démarrage
            ca = GestionEntreesSorties.chargerDepuisFichier(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier spécifié n'a pas été trouvé.");
            e.printStackTrace();
            scanner.close();
            return;
        }

        int choix;
        do {
            /*Afficher le menu*/ 
            System.out.println("Menu:");
            System.out.println("1) Resoudre manuellement");
            System.out.println("2) Resoudre automatiquement");
            System.out.println("3) Sauvegarder");
            System.out.println("4) Fin");

            /*  Lire le choix de l'utilisateur*/
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            // Exécuter l'action en fonction du choix de l'utilisateur
            switch (choix) {
                case 1:
                
                Menu.gererRoutes(scanner, ca);
                
                    
                    System.out.println("Initialisation avec la solution naive (une zone de recharge dans chaque ville).");
                    
                    ca.fullBornedeRecharge();
                
                Menu.gererZonesDeRecharge(scanner, ca);
                ca.afficherEtat();
                break;                
                
                case 2:
                    // Résoudre automatiquement
                    // Appelez votre algorithme ici
                    break;
                case 3:
                    
                    System.out.print("Entrez le chemin vers le fichier de sauvegarde : ");
                    String cheminSauvegarde = scanner.nextLine();
                    try {
                        GestionEntreesSorties.sauvegarderDansFichier( new File(cheminSauvegarde),ca);
                        System.out.println("Communaute d'agglomeration sauvegardee avec succes.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde de la communaute d'agglomeration dans le fichier.");
                        e.printStackTrace(); // À adapter selon les besoins de gestion des erreurs
                    }
                    break;
                case 4:
                    // Fin
                    System.out.println("Programme termine.");
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer un nombre entre 1 et 4.");
                    break;
            }
        } while (choix != 4);

        scanner.close();
    }
}
