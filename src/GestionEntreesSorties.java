package projetS5;

import java.io.*;
import java.util.Scanner;

/**
 * Classe utilitaire pour gérer les entrées/sorties, y compris le chargement et la sauvegarde de la communauté d'agglomération.
 */

public class GestionEntreesSorties {

    /**
     * Charge une communauté d'agglomération depuis un fichier texte.
     *
     * @param file Le fichier texte à partir duquel charger la communauté d'agglomération.
     * @return La communauté d'agglomération chargée.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     * @throws IllegalArgumentException Si le fichier est vide ou si le nombre de villes spécifié est incorrect.
     */
    public static CommunauteAgglomeration chargerDepuisFichier(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Le fichier est vide.");
            }

            int nombreVilles = Integer.parseInt(scanner.nextLine().trim());
            CommunauteAgglomeration ca = new CommunauteAgglomeration(nombreVilles);

            for (int i = 0; i < nombreVilles; i++) {
                if (!scanner.hasNextLine()) {
                    throw new IllegalArgumentException("Nombre incorrect de villes spécifié dans le fichier.");
                }

                char ville = scanner.nextLine().charAt(0);
                ca.ajouterVille(ville);
            }

            return ca;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Fichier non trouvé");
        }
    }

    /**
     * Crée un nouveau fichier texte avec un contenu initial.
     *
     * @param nomFichier Le nom du fichier texte à créer.
     */

    public static void creerNouveauFichierTexte(String nomFichier) {
        try {
            FileWriter writer = new FileWriter(nomFichier);
            writer.write("Contenu initial du fichier texte.");
            writer.close();
            System.out.println("Le fichier texte a ete cree avec succes.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier texte : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la communauté d'agglomération dans un fichier texte.
     *
     * @param fichier Le fichier dans lequel sauvegarder la communauté d'agglomération.
     * @param ca La communauté d'agglomération à sauvegarder.
     * @throws IOException En cas d'erreur lors de la sauvegarde dans le fichier.
     */

    public static void sauvegarderDansFichier(File fichier, CommunauteAgglomeration ca) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (char ville : ca.getIndexVilles()) {
                writer.write("ville(" + ville + ").");
                writer.newLine();
            }

            for (int i = 0; i < ca.getMatriceAdjacence().length; i++) {
                for (int j = i + 1; j < ca.getMatriceAdjacence().length; j++) {
                    if (ca.getMatriceAdjacence()[i][j]) {
                        writer.write("route(" + ca.getIndexVilles().get(i) + "," + ca.getIndexVilles().get(j) + ").");
                        writer.newLine();
                    }
                }
            }

            for (int i = 0; i < ca.getZonesDeRecharge().length; i++) {
                if (ca.getZonesDeRecharge()[i]) {
                    writer.write("recharge(" + ca.getIndexVilles().get(i) + ").");
                    writer.newLine();
                }
            }

            System.out.println("Communaute d'agglomeration sauvegardee avec succes.");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde de la communaute d'agglomeration dans le fichier.");
            throw e;
        }
    }
}

