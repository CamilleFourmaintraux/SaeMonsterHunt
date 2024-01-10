package fr.univlille.info.J2.main.application.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Cette classe fournit des méthodes pour sauvegarder et charger des tableaux boolean[][] (map) dans des fichiers.
 * Elle utilise la sérialisation d'objets pour accomplir ces opérations.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveLoadSystemMaps {
	
    /** 
     * Répertoire par défaut pour sauvegarder les labyrinthes. 
     */
	public static final String MAZES_DIRECTORY = "res/saves/mazes/";
	
	/** 
	 * Nom par défaut pour la sauvegarde des labyrinthes. 
	 */
	public final static String DEFAULT_NAME_FOR_MAP_SAVE = "default_map_name";

	/**
     * Sauvegarde un tableau boolean[][] dans un fichier avec le nom spécifié.
     *
     * @param map       Le tableau boolean[][] à sauvegarder.
     * @param saveName  Le nom du fichier de sauvegarde (sans extension).
     * 
     * @throws IOException  En cas d'erreur lors de l'écriture du fichier.
     */
    public static void saveMap(boolean[][] map, String saveName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MAZES_DIRECTORY+saveName+".dat"))) {
            oos.writeObject(map);
        }
    }

    /**
     * Charge un tableau boolean[][] depuis un fichier en utilisant le nom du fichier.
     *
     * @param fileName  Le nom du fichier de sauvegarde (sans extension).
     * @return          Le tableau boolean[][] chargé depuis le fichier.
     * 
     * @throws IOException            En cas d'erreur lors de la lecture du fichier.
     * @throws ClassNotFoundException Si la classe du tableau n'a pas été trouvée.
     */
    public static boolean[][] loadMap(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MAZES_DIRECTORY+fileName+".dat"))) {
            return (boolean[][]) ois.readObject();
        }
    }

    /**
     * Charge un tableau boolean[][] depuis un fichier en utilisant un objet de type File.
     *
     * @param file  L'objet File représentant le fichier à charger.
     * @return      Le tableau boolean[][] chargé depuis le fichier.
     * 
     * @throws IOException            En cas d'erreur lors de la lecture du fichier.
     * @throws ClassNotFoundException Si la classe du tableau n'a pas été trouvée.
     */
    public static boolean[][] loadMap(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (boolean[][]) ois.readObject();
        }
    }

    //Exemple de fonctionnement de saveMap et loadMap
   
    public static void main(String[] args) {
        // Exemple d'utilisation
        boolean[][] tableauASauvegarder = { { true, false, true }, { false, true, false } };
        String cheminFichier = "testSave";

        // Sauvegarder le tableau
        try {
            saveMap(tableauASauvegarder, cheminFichier);
            System.out.println("Tableau sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Charger le tableau
        try {
            boolean[][] tableauCharge = loadMap(cheminFichier);
            System.out.println("Tableau chargé avec succès.");
            for(int h=0; h<tableauCharge.length; h++) {
        		System.out.print('[');
        		System.out.print(tableauCharge[h][0]);
            	for(int l=1; l<tableauCharge[h].length; l++) {
            		System.out.print(',');
            		System.out.print(tableauCharge[h][l]);
            	}
        		System.out.println(']');
            }
        	System.out.println();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}