package fr.univlille.info.J2.main.application.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.univlille.info.J2.main.management.Maze;

public class SaveLoadSystemGames {
	
	private SaveLoadSystemGames() {};

	public static final String GAMES_DIRECTORY = "res/saves/games/";
	public final static String DEFAULT_NAME_FOR_GAME_SAVE = "GameSaveName";

	// Méthode pour sauvegarder un objet dans un fichier
    public static void saveGame(Maze maze, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            oos.writeObject(maze);
        }
    }

    // Méthode pour charger un objet depuis un fichier
    public static Maze loadGame(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            return (Maze) ois.readObject();
        }
    }

    //Exemple d'utilisation
    /*public static void main(String[] args) {
        // Exemple d'utilisation
        Maze saved = new Maze();
        String cheminFichier = "saveTest";

        // Sauvegarder l'objet
        try {
            saveGame(saved, cheminFichier);
            System.out.println("Objet sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Charger l'objet
        try {
            Maze loaded = loadGame(cheminFichier);
            System.out.println("Objet chargé avec succès.");
            System.out.println(loaded.toString());
            for(int h=0; h<loaded.walls.length; h++) {
        		System.out.print('[');
        		System.out.print(loaded.walls[h][0]);
            	for(int l=1; l<loaded.walls[h].length; l++) {
            		System.out.print(',');
            		System.out.print(loaded.walls[h][l]);
            	}
        		System.out.println(']');
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}