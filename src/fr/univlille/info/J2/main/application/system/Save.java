package fr.univlille.info.J2.main.application.system;

import java.io.Serializable;

import fr.univlille.info.J2.main.management.SaveManagementData;
import fr.univlille.info.J2.main.management.SaveMazeData;
import fr.univlille.info.J2.main.management.exit.SaveExitData;
import fr.univlille.info.J2.main.strategy.hunter.SaveHunterData;
import fr.univlille.info.J2.main.strategy.monster.SaveMonsterData;


/**
 * La classe Save représente un objet de sauvegarde contenant les données nécessaires
 * pour restaurer l'état d'une partie du jeu Monster Hunt. Cette classe implémente
 * l'interface Serializable, permettant l'enregistrement et la récupération de l'objet.
 * 
 * Chaque instance de la classe Save contient des données spécifiques à différents aspects
 * du jeu, tels que la gestion des données, le labyrinthe, la sortie, le monstre et le chasseur.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Save implements Serializable{
	/**
	 * Le numéro de série de la classe.
	 */
	private static final long serialVersionUID = -1006454071348557007L;
	/**
	 * Les données de gestion à sauvegarder.
	 */
	private SaveManagementData data_man;
	/**
	 * Les données du labyrinthe à sauvegarder.
	 */
	private SaveMazeData data_maze;
	/**
	 * Les données de la sortie à sauvegarder.
	 */
	private SaveExitData data_exit;
	/**
	 * Les données du monstre à sauvegarder.
	 */
	private SaveMonsterData data_monster;
	/**
	 * Les données du chasseur à sauvegarder.
	 */
	private SaveHunterData data_hunter;
	
	/**
     * Constructeur de la classe Save.
     *
     * @param data_man    	Les données de gestion à sauvegarder.
     * @param data_maze   	Les données du labyrinthe à sauvegarder.
     * @param data_exit   	Les données de la sortie à sauvegarder.
     * @param data_monster 	Les données du monstre à sauvegarder.
     * @param data_hunter  	Les données du chasseur à sauvegarder.
     */
	public Save(SaveManagementData data_man, SaveMazeData data_maze, SaveExitData data_exit, SaveMonsterData data_monster,
			SaveHunterData data_hunter) {
		this.data_man = data_man;
		this.data_maze = data_maze;
		this.data_exit = data_exit;
		this.data_monster = data_monster;
		this.data_hunter = data_hunter;
	}

	/**
     * Obtient la version de la classe pour la sérialisation.
     *
     * @return Le numéro de série de la classe.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
     * Obtient les données de gestion du jeu.
     *
     * @return Les données de gestion du jeu.
     */
	public SaveManagementData getData_management() {
		return data_man;
	}

	/**
     * Obtient les données du labyrinthe.
     *
     * @return Les données du labyrinthe.
     */
	public SaveMazeData getData_maze() {
		return data_maze;
	}

	/**
     * Obtient les données de la sortie.
     *
     * @return Les données de la sortie.
     */
	public SaveExitData getData_exit() {
		return data_exit;
	}

	/**
     * Obtient les données du monstre.
     *
     * @return Les données du monstre.
     */
	public SaveMonsterData getData_monster() {
		return data_monster;
	}

	/**
     * Obtient les données du chasseur.
     *
     * @return Les données du chasseur.
     */
	public SaveHunterData getData_hunter() {
		return data_hunter;
	}
}

