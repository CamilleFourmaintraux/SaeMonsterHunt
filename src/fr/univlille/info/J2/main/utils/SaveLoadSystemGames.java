package fr.univlille.info.J2.main.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.univlille.info.J2.main.management.Maze;

public class SaveLoadSystemGames {

	public static final String GAMES_DIRECTORY = "res/saves/games/";
	public final static String DEFAULT_NAME_FOR_GAME_SAVE = "GameSaveName";

	// Méthode pour sauvegarder un objet dans un fichier
    public static void saveGame(Maze maze, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAMES_DIRECTORY+fileName))) {
            oos.writeObject(maze);
        }
    }

    // Méthode pour charger un objet depuis un fichier
    public static Maze loadGame(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAMES_DIRECTORY+fileName))) {
            return (Maze) ois.readObject();
        }
    }

    //Exemple d'utilisation
    public static void main(String[] args) {
        // Exemple d'utilisation
        Maze objetASauvegarder = new Maze();
        String cheminFichier = "saveTest.obj";

        // Sauvegarder l'objet
        try {
            saveGame(objetASauvegarder, cheminFichier);
            System.out.println("Objet sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Charger l'objet
        try {
            Maze game = loadGame(cheminFichier);
            System.out.println("Objet chargé avec succès.");
            System.out.println(game.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}