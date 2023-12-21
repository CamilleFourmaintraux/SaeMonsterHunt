package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

public class SaveMonsterData implements Serializable{
	private static final long serialVersionUID = 522923773838437211L;
	
	/**
	 * La grille des parties déjà explorés du labyrinthe.
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
	 * La ligne de la coordonnée du monstre à laquelle il se trouve.
	 */
	private int row;
	
	/**
	 * La colonne de la coordonnée du monstre à laquelle il se trouve.
	 */
	private int col;
	
	/**
	 * Constructueur de la sauvegarde des données du Monstre.
	 * 
	 * @param gameplay 		Données de gameplay du Monstre.
	 * @param explored		Tableau des case découverte du labyrinthe.
	 * @param walls			Tableau des murs du labyrinthe.
	 * @param row			Ligne du labyrinthe à laquelle se trouve le monstre.
	 * @param col			Colonne du labyrinthe à laquelle se trouve le monstre.
	 */
	public SaveMonsterData(GameplayMonsterData gameplay, boolean[][] explored, boolean[][] walls, int row, int col) {
		this.gameplay = gameplay;
		this.explored = explored;
		this.walls = walls;
		this.row = row;
		this.col = col;
	}

	/**
	 * Récupère les données de gameplay du monstre.
	 * 
	 * @return Données de gameplay du monstre sous un objet GameplayMonsterData.
	 */
	public GameplayMonsterData getGameplay() {
		return gameplay;
	}

	/**
	 * Récupère le tableau des cases déjà explorées par le monstre du labyrinthe.
	 * 
	 * @return Tableau des cases déjà exploréers par le monstre.
	 */
	public boolean[][] getExplored() {
		return explored;
	}

	/**
	 * Récupère le tableau des murs du labyrinthe.
	 * 
	 * @return Tableau des murs du labyrinthe.
	 */
	public boolean[][] getWalls() {
		return walls;
	}

	/**
	 * Récupère la ligne du labyrinthe à laquelle se trouve le monstre.
	 * 
	 * @return la ligne du labyrinthe.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Récupère la colonne du labyrinthe à laquelle se trouve le monstre.
	 * 
	 * @return la colonne du labyrinthe.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Définit le tableau de sauvegarde des cases explorées par le monstre avec le tableau des cases déjà explorées actuel.
	 * 
	 * @param explored
	 */
	protected void setExplored(boolean[][] explored) {
		this.explored = explored;
	}

	/**
	 * Définit le tableau de sauvegarde des murs du labyrinthe avec le talbeau des murs de la partie actuelle.
	 * 
	 * @param walls
	 */
	protected void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	/**
	 * Définit la sauvegarde de la position vertical du monstre à partir de la position actuelle de celui-ci.
	 * 
	 * @param row position vertical du monstre.
	 */
	protected void setRow(int row) {
		this.row = row;
	}

	/**
	 * Définit la sauvegarde de la position horizontal du monstre à partir de la position actuelle de celui-ci.
	 * 
	 * @param col position horizontal du monstre.
	 */
	protected void setCol(int col) {
		this.col = col;
	}

	/**
	 * Récupère si le monstre a une vision limitée ou non pour la sauvegarde.
	 * 
	 * @return Le boolean du brouillard actif ou non.
	 */
	public boolean isVisionLimited() {
		return this.gameplay.isVisionLimited();
	}

	/**
	 * Obtient la portée de vision du monstre.
     * La portée de vision représente la distance maximale à laquelle le monstre peut voir.
     * 
	 * @return La portée de vision du monstre.
	 */
	public int getVisionRange() {
		return this.gameplay.getVisionRange();
	}

	/**
	 * Obtient la portée de déplacement du monstre.
     * La portée de déplacement représente la distance maximale que le monstre peut se déplacer en un tour.
     * 
	 * @return La portée de déplacement du monstre.
	 */
	public int getMovingRange() {
		return this.gameplay.getMovingRange();
	}

	/**
	 * Récupère le niveau de l'IA à partir des données de gameplay du monstre actuel.
	 * 
	 * @return le niveau de l'IA de la partie.
	 */
	public String getIA() {
		return this.gameplay.getIA();
	}

	/**
	 * Récupère le nom du joueur de la partie actuel.
	 * 
	 * @return le nom du joueur de la partie.
	 */
	public String getName() {
		return this.gameplay.getName();
	}
}
