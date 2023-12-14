package fr.univlille.info.J2.main.application.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.exit.SaveExitData;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.SaveHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.SaveMonsterData;
import fr.univlille.info.J2.main.utils.menuConception.Theme;

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
    public static void saveGame(Save save, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            oos.writeObject(save);
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
    public static Save loadGame(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            return (Save) ois.readObject();
        }
    }

    //Exemple d'utilisation
    public static void main(String[] args) {
        // Exemple d'utilisation
    	SaveMazeData smad = new SaveMazeData(Maze.DEFAULT_MAP, new int[10][10], 4, false, Theme.THEME_DUNGEON);
    	SaveExitData sexd = new SaveExitData(8, 8);
    	SaveMonsterData smod = new SaveMonsterData(new GameplayMonsterData("Monster","Player", false, 1, 1), new boolean[10][10], Maze.DEFAULT_MAP, 1, 8);
    	SaveHunterData shud = new SaveHunterData(new GameplayHunterData("Hunter","Player", 0), new int[10][10], 8, 1);
        Save saved = new Save(smad, sexd, smod, shud);
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
            Save loaded = loadGame(cheminFichier);
            System.out.print("Objet chargé avec succès :");
            System.out.println(loaded.toString());
            System.out.println(loaded.getData_monster().getRow());
            System.out.println(loaded.getData_monster().getCol());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}