package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

public class SaveMonsterData implements Serializable{
	private static final long serialVersionUID = 522923773838437211L;
	
	/**
	 * La grille des parties déjà explorés du labyrinthe
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
	 * Les coordonnées du monstre.
	 */
	private int row;
	private int col;
	
	public SaveMonsterData(GameplayMonsterData gameplay, boolean[][] explored, boolean[][] walls, int row, int col) {
		this.gameplay = gameplay;
		this.explored = explored;
		this.walls = walls;
		this.row = row;
		this.col = col;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GameplayMonsterData getGameplay() {
		return gameplay;
	}

	public boolean[][] getExplored() {
		return explored;
	}

	public boolean[][] getWalls() {
		return walls;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	protected void setExplored(boolean[][] explored) {
		this.explored = explored;
	}

	protected void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	protected void setRow(int row) {
		this.row = row;
	}

	protected void setCol(int col) {
		this.col = col;
	}

	public boolean isVisionLimited() {
		return this.gameplay.isVisionLimited();
	}

	public int getVisionRange() {
		return this.gameplay.getVisionRange();
	}

	public int getMovingRange() {
		return this.gameplay.getMovingRange();
	}

	public String getIA() {
		return this.gameplay.getIA();
	}

	public String getName() {
		return this.gameplay.getName();
	}
	
	
	
}
