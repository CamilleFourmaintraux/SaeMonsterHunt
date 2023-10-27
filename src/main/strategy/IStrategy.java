package main.strategy;

import main.maze.cells.ICoordinate;
/**
 * 
 * Cette interface permet l’inter-opérabilité entre les IA implémentées dans les différents projets.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public interface IStrategy {
	/**
	 * Méthode consistant à choisir la coordonnée qui correspond au coup pour la stratégie.
	 * @return La coordonnée choisi par la stratégie.
	 */
	public ICoordinate play();
	/**
	 *  Méthode permettant d'actualiser les connaissances de la stratégie afin 
	 *  qu'elle puisse faire ses choix en conséquence (dans le cas d'une stratégie plus réfléchie que random).
	 */
	public void update();
}
