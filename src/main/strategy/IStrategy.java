package main.strategy;

import main.maze.cells.ICoordinate;
/**
 * 
 * Cette interface permet l�inter-op�rabilit� entre les IA impl�ment�es dans les diff�rents projets.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public interface IStrategy {
	/**
	 * M�thode consistant � choisir la coordonn�e qui correspond au coup pour la strat�gie.
	 * @return La coordonn�e choisi par la strat�gie.
	 */
	public ICoordinate play();
	/**
	 *  M�thode permettant d'actualiser les connaissances de la strat�gie afin 
	 *  qu'elle puisse faire ses choix en cons�quence (dans le cas d'une strat�gie plus r�fl�chie que random).
	 */
	public void update();
}
