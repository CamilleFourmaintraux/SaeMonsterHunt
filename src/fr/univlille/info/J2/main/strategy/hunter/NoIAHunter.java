package fr.univlille.info.J2.main.strategy.hunter;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe implémente l'interface IHunterStrategy pour représenter une stratégie de chasseur sans intelligence artificielle.
 * Le chasseur ne prend pas de décisions stratégiques et retourne null pour l'action de jeu.
 * Il n'effectue aucune mise à jour ou initialisation, car il n'a pas d'intelligence artificielle.
 * Il est utilisé comme une stratégie de chasseur simple sans comportement complexe.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class NoIAHunter implements IHunterStrategy{
	/**
     * Retourne null car le chasseur n'a pas de stratégie et ne prend pas de décision pour l'action de jeu.
     * 
     * @return null car aucune action n'est définie.
     */
	@Override
	public ICoordinate play() {return null;}
	
	/**
     * Aucune mise à jour n'est effectuée car le chasseur n'a pas d'intelligence artificielle.
     * 
     * @param arg0 Événement de cellule, mais pas utilisé dans cette stratégie.
     */
	@Override
	public void update(ICellEvent arg0) { /* No IA, so no update */ }
	
	/**
     * Aucune initialisation n'est effectuée car le chasseur n'a pas d'intelligence artificielle.
     * 
     * @param arg0 Largeur de la grille, mais pas utilisée dans cette stratégie.
     * @param arg1 Hauteur de la grille, mais pas utilisée dans cette stratégie.
     */
	@Override
	public void initialize(int arg0, int arg1) { /* No IA, so no initialization */  }
}
