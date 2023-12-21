package fr.univlille.info.J2.main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class NoIAMonster implements IMonsterStrategy{

	@Override
	public ICoordinate play() { return null; }

	@Override
	public void update(ICellEvent arg0) { /*No ia, so no update*/ }

	@Override
	public void initialize(boolean[][] arg0) { /*No ia, so no initialization*/ }

}
