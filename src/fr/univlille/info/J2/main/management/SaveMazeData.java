package fr.univlille.info.J2.main.management;

import java.io.Serializable;

/**
 * Cette classe représente les données d'un labyrinthe à sauvegarder.
 * Elle implémente l'interface Serializable pour permettre la sérialisation.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveMazeData implements Serializable{
	
	private static final long serialVersionUID = -6244168822609852642L;
	
	/**
	 * Tableau de boolean représentant les murs plein ou non du labyrinthe (false=mur true=pas de mur).
	 */
	private boolean[][] walls;
	
	/**
	 * Tableau d'entier stockant les numéros de tours ou le monstre est déjà passé.
	 */
	private int[][] traces;
	
	/**
	 * Le numéro du tour actuel.
	 */
	private int turn;
	
	/**
	 * Boolean qui permet de savoir si c'est le tour du monstre ou non.
	 */
	private boolean isMonsterTurn;
	
	/**
     * Constructeur de la classe SaveMazeData.
     *
     * @param walls         Tableau de boolean représentant les murs du labyrinthe.
     * @param traces        Tableau d'entiers représentant les numéros de tours où le monstre est passé.
     * @param turn          Le numéro du tour actuel.
     * @param isMonsterTurn Boolean indiquant si c'est le tour du monstre.
     */
	public SaveMazeData(boolean[][] walls, int[][] traces, int turn, boolean isMonsterTurn) {
		this.walls = walls;
		this.traces = traces;
		this.turn = turn;
		this.isMonsterTurn = isMonsterTurn;
	}

	/**
     * Retourne le serialVersionUID de la classe.
     *
     * @return Le serialVersionUID de la classe.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * Retourne le tableau de boolean représentant les murs du labyrinthe.
     *
     * @return Le tableau de boolean représentant les murs du labyrinthe.
     */
	public boolean[][] getWalls() {
		return walls;
	}

	/**
     * Retourne le tableau d'entiers représentant les numéros de tours où le monstre est passé.
     *
     * @return Le tableau d'entiers représentant les numéros de tours où le monstre est passé.
     */
	public int[][] getTraces() {
		return traces;
	}

	/**
     * Retourne le numéro du tour actuel.
     *
     * @return Le numéro du tour actuel.
     */
	public int getTurn() {
		return turn;
	}

	/**
     * Indique si c'est le tour du monstre.
     *
     * @return true si c'est le tour du monstre, sinon false.
     */
	public boolean isMonsterTurn() {
		return isMonsterTurn;
	}

	/**
     * Modifie le tableau de boolean représentant les murs du labyrinthe.
     *
     * @param walls Le nouveau tableau de boolean représentant les murs du labyrinthe.
     */
	protected void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	/**
     * Modifie le tableau d'entiers représentant les numéros de tours où le monstre est passé.
     *
     * @param traces Le nouveau tableau d'entiers représentant les numéros de tours où le monstre est passé.
     */
	protected void setTraces(int[][] traces) {
		this.traces = traces;
	}

	/**
     * Modifie le numéro du tour actuel.
     *
     * @param turn Le nouveau numéro du tour actuel.
     */
	protected void setTurn(int turn) {
		this.turn = turn;
	}
	
	/**
     * Incrémente le numéro du tour actuel.
     */
	protected void incrementTurn() {
		this.turn++;
	}

	/**
     * Modifie l'indicateur indiquant si c'est le tour du monstre.
     *
     * @param isMonsterTurn Nouvelle valeur de l'indicateur.
     */
	protected void setMonsterTurn(boolean isMonsterTurn) {
		this.isMonsterTurn = isMonsterTurn;
	}
	
}
