package projetS5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * La classe Main est le point d'entrée du programme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommunauteAgglomeration ca;

        String cheminFichier;

        /*  Vérifiez si le chemin du fichier a été passé en argument; sinon, demandez-le*/
        if (args.length == 1) {
            cheminFichier = args[0];
        } else {
            System.out.println("Veuillez fournir le chemin vers le fichier texte représentant la communauté d'agglomération : ");
            cheminFichier = scanner.nextLine();
        }

        /* charger la communauté d'agglomération à l'aide du fichier texte*/
        try {
            ca = GestionFichier.chargerDepuisFichier(new File(cheminFichier));
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier spécifié n'a pas été trouvé.");
            e.printStackTrace();
            scanner.close();
            return;
        }

        int choix;
        do {
            /*  Afficher le menu*/
            System.out.println("Menu:");
            System.out.println("1) Resoudre manuellement");
            System.out.println("2) Resoudre automatiquement");
            System.out.println("3) Sauvegarder");
            System.out.println("4) Fin");

            /*  Lire le choix de l'utilisateur*/
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            /*Exécuter l'action en fonction du choix de l'utilisateur*/
            switch (choix) {
                case 1:
                    if (ca == null) {
                        ca = CommunauteAgglomeration.initialiserCommunaute(scanner);
                    }

                    if (!ca.estAccessible()) {
                        System.out.println("La communauté actuelle ne respecte pas la contrainte d'accessibilité.");
                        System.out.println("Initialisation avec la solution naïve (une zone de recharge dans chaque ville).");
                        ca.fullBornedeRecharge();
                    }

                    Menu.gererRoutes(scanner, ca);
                    Menu.gererZonesDeRecharge(scanner, ca);
                    ca.afficherEtat();
                    break;
                case 2:
                    
                    if (ca == null) {
                        ca = CommunauteAgglomeration.initialiserCommunaute(scanner);
                    }

                    AlgorithmeCouvertureSommet algo = new AlgorithmeCouvertureSommet(ca);
                    algo.executer();
                    ca.afficherEtat();

                    break;
                case 3:
                    /*sauvegarder la communauté d'agglomération dans le fichier texte*/
                    System.out.print("Entrez le chemin vers le fichier de sauvegarde : ");
                    String cheminSauvegarde = scanner.nextLine();
                    try {
                        GestionFichier.sauvegarderDansFichier(new File(cheminSauvegarde), ca);
                        System.out.println("Communauté d'agglomération sauvegardée avec succès.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde de la communauté d'agglomération dans le fichier.");
                        e.printStackTrace(); 
                    }
                    break;
                case 4:
                    
                    System.out.println("Programme terminé.");
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer un nombre entre 1 et 4.");
                    break;
            }
        } while (choix != 4);

        scanner.close();
    }
}


3
