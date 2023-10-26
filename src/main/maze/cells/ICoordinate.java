package main.maze.cells;

public interface ICoordinate {
	/**
     * Renvoie le numéro de ligne de cette coordonnée.
     *
     * @return Le numéro de ligne.
     */
	public int getRow();
	/**
     * Renvoie le numéro de colonne de cette coordonnée.
     *
     * @return Le numéro de colonne.
     */
	public int getCol();
}
