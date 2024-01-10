package fr.univlille.info.J2.main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe représente une stratégie de monstre sans intelligence artificielle.
 * Le monstre ne prend aucune décision et reste inactif pendant le jeu.
 * Utilisé lorsque le monstre ne nécessite pas de comportement spécifique.
 * Implémente l'interface IMonsterStrategy.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class NoIAMonster implements IMonsterStrategy{

	/**
     * Cette méthode est appelée lorsque c'est le tour du monstre de jouer.
     * Dans ce cas, le monstre ne fait rien et retourne null.
     *
     * @return null, car le monstre ne prend aucune action.
     */
	@Override
	public ICoordinate play() { return null; }

	/**
     * Cette méthode est appelée lorsqu'un événement de cellule se produit.
     * Pour cette stratégie, aucune mise à jour n'est nécessaire, car le monstre n'a pas d'intelligence artificielle.
     *
     * @param arg0 L'événement de cellule associé à la mise à jour.
     */
	@Override
	public void update(ICellEvent arg0) { /*No ia, so no update*/ }

	/**
     * Cette méthode est appelée lors de l'initialisation de la stratégie de monstre.
     * Comme le monstre n'a pas d'intelligence artificielle, aucune initialisation n'est nécessaire.
     *
     * @param arg0 Le tableau de boolean représentant les murs du labyrinthe.
     */
	@Override
	public void initialize(boolean[][] arg0) { /*No ia, so no initialization*/ }

}
