package fr.univlille.info.J2.main.strategy.hunter;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class NoIAHunter implements IHunterStrategy{
	@Override
	public ICoordinate play() {return null;}
	@Override
	public void update(ICellEvent arg0) { /* No IA, so no update */ }
	@Override
	public void initialize(int arg0, int arg1) { /* No IA, so no initialization */  }
}
