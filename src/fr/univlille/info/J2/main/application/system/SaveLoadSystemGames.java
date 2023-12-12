package fr.univlille.info.J2.main.application.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.univlille.info.J2.main.management.Maze;

/**
 * La classe SaveLoadSystemGames fournit des méthodes statiques pour sauvegarder et charger des objets de type Maze
 * en utilisant la sérialisation Java. Elle permet de stocker des instances de la classe Maze dans des fichiers
 * pour les sauvegarder et les restaurer ultérieurement.
 */
public class SaveLoadSystemGames {
	
	private SaveLoadSystemGames() {};

	/**
     * Le répertoire par défaut où seront sauvegardés les fichiers de jeux.
     */
	public static final String GAMES_DIRECTORY = "res/saves/games/";
	
	/**
     * Nom par défaut pour la sauvegarde d'un jeu.
     */
	public final static String DEFAULT_NAME_FOR_GAME_SAVE = "default_save_name";

	/**
     * Sauvegarde un objet de type Maze dans un fichier.
     *
     * @param maze     L'objet Maze à sauvegarder.
     * @param fileName Le nom du fichier dans lequel sauvegarder l'objet.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la sauvegarde.
     */
    public static void saveGame(Maze maze, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            oos.writeObject(maze);
        }
    }

    /**
     * Charge un objet de type Maze depuis un fichier.
     *
     * @param fileName Le nom du fichier à partir duquel charger l'objet.
     * @return L'objet Maze chargé depuis le fichier.
     * @throws IOException            Si une erreur d'entrée/sortie se produit lors du chargement.
     * @throws ClassNotFoundException Si la classe Maze n'est pas trouvée lors du chargement.
     */
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