package fr.univlille.info.J2.main.application.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.univlille.info.J2.main.management.Management;
import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.SaveManagementData;
import fr.univlille.info.J2.main.management.SaveMazeData;
import fr.univlille.info.J2.main.management.exit.SaveExitData;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.SaveHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.SaveMonsterData;
import fr.univlille.info.J2.main.utils.resources.Theme;

/**
 * La classe SaveLoadSystemGames fournit des méthodes statiques pour sauvegarder et charger des objets de type Maze
 * en utilisant la sérialisation Java. Elle permet de stocker des instances de la classe Maze dans des fichiers
 * pour les sauvegarder et les restaurer ultérieurement.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
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
     * Sauvegarde un objet de type Save dans un fichier.
     *
     * @param save      L'objet Save à sauvegarder.
     * @param fileName  Le nom du fichier dans lequel sauvegarder l'objet.
     * 
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
     * @param fileName Le nom du fichier à partir duquel charger l'objet (sans l'extension).
     * 
     * @return L'objet Maze chargé depuis le fichier.
     * 
     * @throws IOException            Si une erreur d'entrée/sortie se produit lors du chargement.
     * @throws ClassNotFoundException Si la classe Maze n'est pas trouvée lors du chargement.
     */
    public static Save loadGame(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAMES_DIRECTORY+fileName+".obj"))) {
            return (Save) ois.readObject();
        }
    }
    
    /**
     * Charge un objet de type Maze depuis un fichier.
     *
     * @param file Le fichier à partir duquel charger l'objet.
     * 
     * @return L'objet Maze chargé depuis le fichier.
     * 
     * @throws IOException            Si une erreur d'entrée/sortie se produit lors du chargement.
     * @throws ClassNotFoundException Si la classe Maze n'est pas trouvée lors du chargement.
     */
    public static Save loadGame(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Save) ois.readObject();
        }
    }
}