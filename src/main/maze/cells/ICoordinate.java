package main.maze.cells;

public interface ICoordinate {
	/**
     * Renvoie le num�ro de ligne de cette coordonn�e.
     *
     * @return Le num�ro de ligne.
     */
	public int getRow();
	/**
     * Renvoie le num�ro de colonne de cette coordonn�e.
     *
     * @return Le num�ro de colonne.
     */
	public int getCol();
}
