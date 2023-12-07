package fr.univlille.info.J2.main.management.cells;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe représentant un événement de cellule.
 *
 * Elle implémente l'interface ICellEvent et permet de stocker des informations
 * sur un événement survenu dans une cellule d'un labyrinthe.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class CellEvent implements ICellEvent{
	/**
	 * Les coordonnées de la cellule ou l'événement a eu lieu.
	 */
	ICoordinate coord;
	/**
	 * Le numéro du tour auquel l'evenement a eu lieu.
	 */
	int turn;
	/**
	 * L'état de la cellule actuellement.
	 */
	CellInfo state;

	/**
	 * Constructeur, crée un événement de cellule.
     *
     * @param coord 	Les coordonnées de la cellule ou l'événement a eu lieu.
     * @param turn 		Le numéro du tour auquel l'événement a eu lieu.
     * @param state 	L'état de la cellule actuel.
	 */
	public CellEvent(ICoordinate coord, int turn, CellInfo state) {
		this.coord = coord;
		this.state = state;
		this.turn = turn;
	}

	/**
     * Obtient l'état (CellInfo) de la cellule au moment de l'événement.
     *
     * @return un objet CellInfo indiquant l'état de la cellule.
     */
	@Override
	public CellInfo getState() {
		return this.state;
	}

	 /**
     * Obtient le numéro du tour auquel l'événement a eu lieu.
     *
     * @return Le numéro du tour de l'événement.
     */
	@Override
	public int getTurn() {
		return this.turn;
	}

	/**
    * Obtient les coordonnées de la cellule ou l'événement a eu lieu.
    *
    * @return Les coordonnées de la cellule.
    */
	@Override
	public ICoordinate getCoord() {
		return this.coord;
	}
}
