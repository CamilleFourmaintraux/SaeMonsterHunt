package main.maze.cells;
/**
 * L'interface ICellEvent définit les méthodes nécessaires pour obtenir des informations sur un événement de cellule.
 */
public interface ICellEvent {
	 /**
     * Récupère l'état de la cellule associée à cet événement.
     *
     * @return L'état de la cellule (parmi les valeurs de l'énumération CellInfo).
     */
	public CellInfo getState();
	/**
     * Récupère le numéro du tour auquel cet événement s'est produit.
     *
     * @return Le numéro du tour associé à l'événement de la cellule.
     */
	public int getTurn();
	/**
     * Récupère les coordonnées de la cellule associée à cet événement.
     *
     * @return Les coordonnées (ICoordinate) de la cellule.
     */
	public ICoordinate getCoord();
	/**
     * Énumération des différents états possibles d'une cellule.
     */
	public enum CellInfo {
		EMPTY, WALL, MONSTER, HUNTER, EXIT
	}
	
}
