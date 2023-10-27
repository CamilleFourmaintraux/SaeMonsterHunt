package main.strategy.monster;

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
public interface IMonsterStrategy extends IStrategy{
	/**
	 * 
	 * @param walls
	 */
	public void initialize(boolean walls[][]);
}
