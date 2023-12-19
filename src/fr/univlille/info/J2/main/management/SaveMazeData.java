package fr.univlille.info.J2.main.management;

import java.io.Serializable;

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
	
	
	public SaveMazeData(boolean[][] walls, int[][] traces, int turn, boolean isMonsterTurn) {
		this.walls = walls;
		this.traces = traces;
		this.turn = turn;
		this.isMonsterTurn = isMonsterTurn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean[][] getWalls() {
		return walls;
	}

	public int[][] getTraces() {
		return traces;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isMonsterTurn() {
		return isMonsterTurn;
	}

	protected void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	protected void setTraces(int[][] traces) {
		this.traces = traces;
	}

	protected void setTurn(int turn) {
		this.turn = turn;
	}
	
	protected void incrementTurn() {
		this.turn++;
	}

	protected void setMonsterTurn(boolean isMonsterTurn) {
		this.isMonsterTurn = isMonsterTurn;
	}
	
}
