package fr.univlille.info.J2.main.application.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoadSystemMaps {
	public static final String MAZES_DIRECTORY = "res/saves/mazes/";
	public final static String DEFAULT_NAME_FOR_MAP_SAVE = "default_map_name";

    // Méthode pour sauvegarder un tableau boolean[][] dans un fichier
    public static void saveMap(boolean[][] map, String saveName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MAZES_DIRECTORY+saveName+".dat"))) {
            oos.writeObject(map);
        }
    }

    // Méthode pour charger un tableau boolean[][] depuis un fichier avec le chemin
    public static boolean[][] loadMap(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MAZES_DIRECTORY+fileName+".dat"))) {
            return (boolean[][]) ois.readObject();
        }
    }

 // Méthode pour charger un tableau boolean[][] depuis un fichier
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