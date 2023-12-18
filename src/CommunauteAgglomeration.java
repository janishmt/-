public class Main {
    public static void main(String[] args) {

        
        Scanner scanner = new Scanner(System.in);
        CommunauteAgglomeration ca = null;

        if (args.length != 1) {
            System.out.println("Veuillez fournir le chemin vers le fichier texte représentant la communauté d'agglomération.");
            scanner.close();
            return;
        }

        try {
            // Charger la communauté d'agglomération depuis le fichier au démarrage
            ca = CommunauteAgglomeration.chargerDepuisFichier(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier spécifié n'a pas été trouvé.");
            e.printStackTrace();
            scanner.close();
            return;
        }

        int choix;
        do {
            // Afficher le menu
            System.out.println("Menu:");
            System.out.println("1) Resoudre manuellement");
            System.out.println("2) Resoudre automatiquement");
            System.out.println("3) Sauvegarder");
            System.out.println("4) Fin");

            // Lire le choix de l'utilisateur
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            // Exécuter l'action en fonction du choix de l'utilisateur
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
                    // Résoudre automatiquement
                    // Appelez votre algorithme ici
                    break;
                case 3:
                    // Sauvegarder
                    System.out.print("Entrez le chemin vers le fichier de sauvegarde : ");
                    String cheminSauvegarde = scanner.nextLine();
                    try {
                        ca.sauvegarderDansFichier(new File(cheminSauvegarde));
                        System.out.println("Communauté d'agglomération sauvegardée avec succès.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde de la communauté d'agglomération dans le fichier.");
                        e.printStackTrace(); // À adapter selon les besoins de gestion des erreurs
                    }
                    break;
                case 4:
                    // Fin
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
