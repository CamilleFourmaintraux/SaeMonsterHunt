package main.maze.cells;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe repr�sentant un �v�nement de cellule.
 *
 * Elle impl�mente l'interface ICellEvent et permet de stocker des informations
 * sur un �v�nement survenu dans une cellule d'un labyrinthe.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class CellEvent implements ICellEvent{
	/**
	 * Les coordonn�es de la cellule o� l'�v�nement a eu lieu.
	 */
	ICoordinate coord;
	/**
	 * Le num�ro du tour auquel l'evenement a eu lieu.
	 */
	int turn;
	/**
	 * L'�tat de la cellule actuellement.
	 */
	CellInfo state;
	
	/**
	 * Constructeur, cr�e un �v�nement de cellule.
     *
     * @param coord Les coordonn�es de la cellule o� l'�v�nement a eu lieu.
     * @param turn Le num�ro du tour auquel l'�v�nement a eu lieu.
	 */
	public CellEvent(ICoordinate coord, int turn) {
		this.coord = coord;
		this.state = null;
		this.turn = turn;
	}
	
	/**
     * Obtient l'�tat (CellInfo) de la cellule au moment de l'�v�nement.
     *
     * @return un objet CellInfo indiquant l'�tat de la cellule.
     */
	@Override
	public CellInfo getState() {
		return this.state;
	}
	
	 /**
     * Obtient le num�ro du tour auquel l'�v�nement a eu lieu.
     *
     * @return Le num�ro du tour de l'�v�nement.
     */
	@Override
	public int getTurn() {
		return this.turn;
	}
	
	/**
    * Obtient les coordonn�es de la cellule o� l'�v�nement a eu lieu.
    *
    * @return Les coordonn�es de la cellule.
    */
	@Override
	public ICoordinate getCoord() {
		return this.coord;
	}
}
