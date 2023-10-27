package main.maze.cells;
/**
 * L'interface ICellEvent d�finit les m�thodes n�cessaires pour obtenir des informations sur un �v�nement de cellule.
 */
public interface ICellEvent {
	 /**
     * R�cup�re l'�tat de la cellule associ�e � cet �v�nement.
     *
     * @return L'�tat de la cellule (parmi les valeurs de l'�num�ration CellInfo).
     */
	public CellInfo getState();
	/**
     * R�cup�re le num�ro du tour auquel cet �v�nement s'est produit.
     *
     * @return Le num�ro du tour associ� � l'�v�nement de la cellule.
     */
	public int getTurn();
	/**
     * R�cup�re les coordonn�es de la cellule associ�e � cet �v�nement.
     *
     * @return Les coordonn�es (ICoordinate) de la cellule.
     */
	public ICoordinate getCoord();
	/**
     * �num�ration des diff�rents �tats possibles d'une cellule.
     */
	public enum CellInfo {
		EMPTY, WALL, MONSTER, HUNTER, EXIT
	}
	
}
