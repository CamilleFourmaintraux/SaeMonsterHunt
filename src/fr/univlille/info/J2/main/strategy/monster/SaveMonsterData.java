package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

/**
 * Cette classe représente les données sauvegardées du monstre, comprenant la grille
 * des parties déjà explorées du labyrinthe, les murs, ainsi que les coordonnées du monstre.
 * Ces données peuvent être sérialisées pour la sauvegarde et le chargement du jeu.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveMonsterData implements Serializable{
	private static final long serialVersionUID = 522923773838437211L;
	
	/**
     * Les données de gameplay du monstre.
     */
	private GameplayMonsterData gameplay;
	
	/**
	 * La grille des parties déjà explorés du labyrinthe
	 */
	private boolean[][] explored;
	
	/**
	 * Les murs pour la stratégie
	 */
	private boolean[][] walls;
	
	/**
     * La ligne (y) actuelle où se trouve le monstre dans le labyrinthe.
     */
	private int row;
	
	/**
     * La colonne (x) actuelle où se trouve le monstre dans le labyrinthe.
     */
	private int col;
	
	/**
     * Constructeur de la classe {@code SaveMonsterData}.
     *
     * @param gameplay Les données de gameplay du monstre.
     * @param explored La grille des parties déjà explorées du labyrinthe par le monstre.
     * @param walls Les murs pour la stratégie du monstre.
     * @param row La ligne (y) actuelle où se trouve le monstre dans le labyrinthe.
     * @param col La colonne (x) actuelle où se trouve le monstre dans le labyrinthe.
     */
	public SaveMonsterData(GameplayMonsterData gameplay, boolean[][] explored, boolean[][] walls, int row, int col) {
		this.gameplay = gameplay;
		this.explored = explored;
		this.walls = walls;
		this.row = row;
		this.col = col;
	}

	/**
     * Retourne le numéro de version de la sérialisation.
     *
     * @return Le numéro de version de la sérialisation.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * Retourne les données de gameplay du monstre.
     *
     * @return Les données de gameplay du monstre.
     */
	public GameplayMonsterData getGameplay() {
		return gameplay;
	}

	/**
     * Retourne la grille des parties déjà explorées du labyrinthe par le monstre.
     *
     * @return La grille des parties déjà explorées du labyrinthe par le monstre.
     */
	public boolean[][] getExplored() {
		return explored;
	}

	/**
     * Retourne les murs pour la stratégie du monstre.
     *
     * @return Les murs pour la stratégie du monstre.
     */
	public boolean[][] getWalls() {
		return walls;
	}

	/**
     * Retourne la ligne (y) actuelle où se trouve le monstre dans le labyrinthe.
     *
     * @return La ligne (y) actuelle du monstre.
     */
	public int getRow() {
		return row;
	}

	/**
     * Retourne la colonne (x) actuelle où se trouve le monstre dans le labyrinthe.
     *
     * @return La colonne (x) actuelle du monstre.
     */
	public int getCol() {
		return col;
	}

	/**
     * Modifie la grille des parties déjà explorées du labyrinthe par le monstre.
     *
     * @param explored La nouvelle grille des parties déjà explorées du labyrinthe.
     */
	protected void setExplored(boolean[][] explored) {
		this.explored = explored;
	}

	/**
     * Modifie les murs pour la stratégie du monstre.
     *
     * @param walls Les nouveaux murs pour la stratégie du monstre.
     */
	protected void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	/**
     * Modifie la ligne (y) actuelle où se trouve le monstre dans le labyrinthe.
     *
     * @param row La nouvelle ligne (y) du monstre.
     */
	protected void setRow(int row) {
		this.row = row;
	}

	/**
     * Modifie la colonne (x) actuelle où se trouve le monstre dans le labyrinthe.
     *
     * @param col La nouvelle colonne (x) du monstre.
     */
	protected void setCol(int col) {
		this.col = col;
	}

	/**
     * Indique si la vision du monstre est limitée.
     *
     * @return {@code true} si la vision du monstre est limitée, sinon {@code false}.
     */
	public boolean isVisionLimited() {
		return this.gameplay.isVisionLimited();
	}

	/**
     * Retourne la portée de vision du monstre.
     *
     * @return La portée de vision du monstre.
     */
	public int getVisionRange() {
		return this.gameplay.getVisionRange();
	}

	 /**
     * Retourne la portée de déplacement du monstre.
     *
     * @return La portée de déplacement du monstre.
     */
	public int getMovingRange() {
		return this.gameplay.getMovingRange();
	}

	/**
     * Retourne le niveau d'intelligence artificielle (IA) du monstre.
     *
     * @return Le niveau d'intelligence artificielle (IA) du monstre.
     */
	public String getIA() {
		return this.gameplay.getIA();
	}

	/**
     * Retourne le nom du monstre.
     *
     * @return Le nom du monstre.
     */
	public String getName() {
		return this.gameplay.getName();
	}
}
