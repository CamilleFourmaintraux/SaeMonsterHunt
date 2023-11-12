package fr.univlille.info.J2.main.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SaveLoadSystem {
	public static final String SAVE_DIRECTORY = "./res/mazes/";
	public static int mazeSave_counter = 0;
	public static int gameSave_counter = 0;
	
	public static void reset_mazeSave_counter() {
		mazeSave_counter=0;
	}
	
	public static void reset_gameSave_counter() {
		gameSave_counter=0;
	}

    // Méthode pour sauvegarder un tableau boolean[][] dans un fichier
    public static void saveMap(boolean[][] map, String saveName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_DIRECTORY+saveName+mazeSave_counter+".dat"))) {
            mazeSave_counter++;
            oos.writeObject(map);
        }
    }

    // Méthode pour charger un tableau boolean[][] depuis un fichier
    public static boolean[][] loadMap(String saveName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_DIRECTORY+saveName))) {
            return (boolean[][]) ois.readObject();
        }
    }
    
    //Exemple de fonctionnement de saveMap et loadMap
    public static void main(String[] args) {
        // Exemple d'utilisation
        boolean[][] tableauASauvegarder = { { true, false, true }, { false, true, false } };
        String cheminFichier = "testSave.dat";

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
