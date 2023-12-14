package fr.univlille.info.J2.main.strategy.hunter;

import java.io.Serializable;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class SaveHunterData implements Serializable{
	private static final long serialVersionUID = -8756361376167378970L;
	
	/**
	 * Les données sur les spécifications du gameplay du chasseur
	 */
	private GameplayHunterData gameplay;
	
	/**
     * Tableau pour stocker les traces laissées par le chasseur dans le labyrinthe.
	 */
	private int[][] traces;
	
	/**
	 * Les coordonnées du dernier tir du chasseur.
	 */
	private int row;
	private int col;
	
	public SaveHunterData(GameplayHunterData gameplay, int[][] traces, int row, int col) {
		this.gameplay = gameplay;
		this.traces = traces;
		this.row = row;
		this.col = col;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public GameplayHunterData getGameplay() {
		return gameplay;
	}
	public int[][] getTraces() {
		return traces;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

	public int getBonusRange() {
		return this.gameplay.getBonusRange();
	}

	public String getIA() {
		return this.gameplay.getIA();
	}

	public String getName() {
		return this.gameplay.getName();
	}

	protected void setTraces(int[][] traces) {
		this.traces = traces;
	}

	protected void setRow(int row) {
		this.row = row;
	}

	protected void setCol(int col) {
		this.col = col;
	}
	/**
     * Définit la trace laissée par le chasseur à des coordonnées spécifiques.
     *
     * @param c     Les coordonnées où la trace doit être définie.
     * @param trace La valeur de la trace.
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	public void setTrace(int row, int col, int trace) {
		this.traces[row][col]=trace;
	}
	
	
}
