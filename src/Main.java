
import java.util.Scanner;

/*Methode principale du projet*/
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nombreVilles;
        
        do{
        System.out.println("Entrez le nombre de villes (max 26):");
        nombreVilles = scanner.nextInt();
         scanner.nextLine();
        }while(nombreVilles<=0||nombreVilles>26);
        CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);
        
        for(int i=0;i<nombreVilles;i++){
            System.out.println("veuillez entrer la ville numéro"+ " "+ (i+1));
            char ville=scanner.nextLine().toUpperCase().charAt(0);  /*récupérer la ville tapée par l'utilisateur */
            ca.ajouterVille(ville);
            
        }
      

       
           
          
        
        

        /*  Menu pour ajouter des routes*/
        boolean fin = false;
        
        
        
        while (!fin) {
            System.out.println("[1] Ajouter une route");
            System.out.println("[2] Terminer l'ajout de routes");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le reste de la ligne après le nombre


            switch (choix) {
                case 1:
                    System.out.println("Entrez la première ville (lettre):");
                    char villeA = scanner.nextLine().toUpperCase().charAt(0);

                    System.out.println("Entrez la deuxième ville (lettre):");
                    char villeB = scanner.nextLine().toUpperCase().charAt(0);

                    ca.ajouterRoute(villeA, villeB);
                    break;
                case 2:
                    fin = true;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer 1 ou 2.");
                    break;
            }
        }

        /*  Menu pour gérer les zones de recharge*/
        fin = false;
        while (!fin) {
            System.out.println("[1] Ajouter une zone de recharge");
            System.out.println("[2] Retirer une zone de recharge");
            System.out.println("[3] Terminer la configuration");
            int action = scanner.nextInt();
            scanner.nextLine(); 

            switch (action) {
                case 1:
                    System.out.println("Entrez la ville pour ajouter la zone de recharge:");
                    char villeAjouter = scanner.nextLine().toUpperCase().charAt(0);
                    ca.ajouterZoneDeRecharge(villeAjouter);
                    break;
                case 2:
                    System.out.println("Entrez la ville pour retirer la zone de recharge:");
                    char villeRetirer = scanner.nextLine().toUpperCase().charAt(0);
                    ca.retirerZoneDeRecharge(villeRetirer);
                    break;
                case 3:
                    fin = true;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez entrer 1, 2 ou 3.");
                    break;
            }
        }

        /*  Afficher la matrice d'adjacence montrant les routes existantes ainsi que les villes contenant des zones de recharge*/
        ca.afficherEtat();

        /* Ferme les flux d'entrées-sorties */
        scanner.close();
    }
}