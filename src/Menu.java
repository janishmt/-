package projetS5;
import java.util.Scanner;


/**
 * La classe Menu gère les interactions avec l'utilisateur pour configurer la communauté d'agglomération.
 */
public class Menu {

	/**
     * Initialise une communauté d'agglomération en demandant à l'utilisateur de spécifier le nombre de villes
     * et les noms des villes.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @return La communauté d'agglomération nouvellement créée.
     */
    public static CommunauteAgglomeration initialiserCommunaute(Scanner scanner) {
        int nombreVilles;
        do {
            System.out.println("Entrez le nombre de villes (max 26):");
            nombreVilles = scanner.nextInt();
            if (nombreVilles <= 0 || nombreVilles > 26) {
                System.out.println("Le nombre de villes doit être supérieur à 0 et inférieur à 26");
            }
            scanner.nextLine(); // Consommer la nouvelle ligne
        } while (nombreVilles <= 0 || nombreVilles > 26);

        CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);

        for (int i = 0; i < nombreVilles; i++) {
            System.out.println("Veuillez entrer la ville numéro " + (i + 1));
            char ville = scanner.nextLine().toUpperCase().charAt(0);
            ca.ajouterVille(ville);
        }

        return ca;
    }
    /**
     * Gère l'ajout de routes entre les villes de la communauté d'agglomération.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @param ca La communauté d'agglomération à modifier.
     */
    public static void gererRoutes(Scanner scanner, CommunauteAgglomeration ca) {
        boolean finRoutes = false;
        while (!finRoutes) {
            System.out.println("[1] Ajouter une route");
            System.out.println("[2] Terminer l'ajout de routes");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    ajouterRoute(scanner, ca);
                    break;
                case 2:
                    finRoutes = true;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer 1 ou 2.");
                    break;
            }
        }
    }

    /**
     * Ajoute une route entre deux villes de la communauté d'agglomération.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @param ca La communauté d'agglomération à modifier.
     */
    private static void ajouterRoute(Scanner scanner, CommunauteAgglomeration ca) {
        System.out.println("Entrez la première ville (lettre):");
        char villeA = scanner.nextLine().toUpperCase().charAt(0);

        System.out.println("Entrez la deuxième ville (lettre):");
        char villeB = scanner.nextLine().toUpperCase().charAt(0);

        ca.ajouterRoute(villeA, villeB);
    }

    /**
     * Gère l'ajout et la suppression des zones de recharge dans la communauté d'agglomération.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @param ca La communauté d'agglomération à modifier.
     */
    public static void gererZonesDeRecharge(Scanner scanner, CommunauteAgglomeration ca) {
        boolean finZones = false;
        while (!finZones) {
            System.out.println("[1] Ajouter une zone de recharge");
            System.out.println("[2] Retirer une zone de recharge");
            System.out.println("[3] Terminer la configuration");
            System.out.print("Votre choix : ");
            int action = scanner.nextInt();
            scanner.nextLine(); 

            switch (action) {
                case 1:
                    ajouterZoneDeRecharge(scanner, ca);
                    break;
                case 2:
                    retirerZoneDeRecharge(scanner, ca);
                    break;
                case 3:
                    finZones = true;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer 1, 2 ou 3.");
                    break;
            }
        }
    }

    /**
     * Ajoute une zone de recharge à une ville spécifiée dans la communauté d'agglomération.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @param ca La communauté d'agglomération à modifier.
     */
    private static void ajouterZoneDeRecharge(Scanner scanner, CommunauteAgglomeration ca) {
        System.out.println("Entrez la ville pour ajouter la zone de recharge:");
        char villeAjouter = scanner.nextLine().toUpperCase().charAt(0);
        ca.ajouterZoneDeRecharge(villeAjouter);
    }

    /**
     * Retire la zone de recharge d'une ville spécifiée dans la communauté d'agglomération.
     *
     * @param scanner Scanner pour lire l'entrée utilisateur.
     * @param ca La communauté d'agglomération à modifier.
     */
    private static void retirerZoneDeRecharge(Scanner scanner, CommunauteAgglomeration ca) {
        System.out.println("Entrez la ville pour retirer la zone de recharge:");
        char villeRetirer = scanner.nextLine().toUpperCase().charAt(0);
        ca.retirerZoneDeRecharge(villeRetirer);
    }
}

