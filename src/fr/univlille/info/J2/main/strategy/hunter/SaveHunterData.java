package fr.univlille.info.J2.main.strategy.hunter;

import java.io.Serializable;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe représente les données à sauvegarder pour le chasseur dans le jeu Monster Hunt.
 * Les données incluent des informations sur le gameplay, les traces laissées par le chasseur, ainsi que sa position dans le labyrinthe.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveHunterData implements Serializable{
	private static final long serialVersionUID = -8756361376167378970L;
	
	/**
     * Les données liées au gameplay du chasseur.
     */
	private GameplayHunterData gameplay;
	
	/**
     * Les traces laissées par le chasseur dans le labyrinthe.
     */
	private int[][] traces;
	
	/**
     * La ligne (row) actuelle où se trouve le chasseur dans le labyrinthe.
     */
	private int row;
	
	/**
     * La colonne (col) actuelle où se trouve le chasseur dans le labyrinthe.
     */
	private int col;
	
	/**
     * Constructeur de la classe SaveHunterData.
     *
     * @param gameplay Les données liées au gameplay du chasseur.
     * @param traces   Les traces laissées par le chasseur dans le labyrinthe.
     * @param row      La ligne actuelle où se trouve le chasseur.
     * @param col      La colonne actuelle où se trouve le chasseur.
     */
	public SaveHunterData(GameplayHunterData gameplay, int[][] traces, int row, int col) {
		this.gameplay = gameplay;
		this.traces = traces;
		this.row = row;
		this.col = col;
	}
	
	/**
     * Renvoie le numéro de version de la classe pour la sérialisation.
     *
     * @return Le numéro de version de la classe.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
     * Renvoie les données liées au gameplay du chasseur.
     *
     * @return Les données liées au gameplay du chasseur.
     */
	public GameplayHunterData getGameplay() {
		return gameplay;
	}
	
	/**
     * Renvoie les traces laissées par le chasseur dans le labyrinthe.
     *
     * @return Les traces laissées par le chasseur.
     */
	public int[][] getTraces() {
		return traces;
	}
	
	/**
     * Renvoie la ligne actuelle où se trouve le chasseur.
     *
     * @return La ligne actuelle du chasseur dans le labyrinthe.
     */
	public int getRow() {
		return row;
	}
	
	/**
     * Renvoie la colonne actuelle où se trouve le chasseur.
     *
     * @return La colonne actuelle du chasseur dans le labyrinthe.
     */
	public int getCol() {
		return col;
	}

	/**
     * Renvoie la portée du bonus du chasseur.
     *
     * @return La portée du bonus du chasseur.
     */
	public int getBonusRange() {
		return this.gameplay.getBonusRange();
	}

	/**
     * Renvoie le niveau d'intelligence artificielle (IA) du chasseur.
     *
     * @return Le niveau d'IA du chasseur.
     */
	public String getIA() {
		return this.gameplay.getIA();
	}

	/**
     * Renvoie le nom du chasseur.
     *
     * @return Le nom du chasseur.
     */
	public String getName() {
		return this.gameplay.getName();
	}

	/**
     * Modifie les traces laissées par le chasseur dans le labyrinthe.
     *
     * @param traces Les nouvelles traces laissées par le chasseur.
     */
	protected void setTraces(int[][] traces) {
		this.traces = traces;
	}

	/**
     * Modifie la ligne actuelle où se trouve le chasseur dans le labyrinthe.
     *
     * @param row La nouvelle ligne du chasseur.
     */
	protected void setRow(int row) {
		this.row = row;
	}

	/**
     * Modifie la colonne actuelle où se trouve le chasseur dans le labyrinthe.
     *
     * @param col La nouvelle colonne du chasseur.
     */
	protected void setCol(int col) {
		this.col = col;
	}
	
	/**
     * Place une trace à la coordonnée spécifiée dans le labyrinthe.
     *
     * @param c     La coordonnée où placer la trace.
     * @param trace La valeur de la trace.
     */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	
	/**
     * Place une trace à la position spécifiée dans le labyrinthe.
     *
     * @param row   La ligne où placer la trace.
     * @param col   La colonne où placer la trace.
     * @param trace La valeur de la trace.
     */
	public void setTrace(int row, int col, int trace) {
		this.traces[row][col]=trace;
	}
	
	
}
