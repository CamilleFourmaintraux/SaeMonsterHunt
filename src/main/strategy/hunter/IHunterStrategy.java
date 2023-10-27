package main.strategy.hunter;

import main.strategy.IStrategy;
/**
 * 
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public interface IHunterStrategy extends IStrategy{
	/**
	 * 
	 * @param nbrRows
	 * @param nbrCols
	 */
	public void initialize(int nbrRows, int nbrCols);
}
