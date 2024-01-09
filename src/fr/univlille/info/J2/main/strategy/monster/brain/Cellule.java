package fr.univlille.info.J2.main.strategy.monster.brain;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Cellule {
	ICoordinate pos;
	Cellule previous;
	
	public Cellule() {
	}
	
	public Cellule(ICoordinate pos, Cellule previous) {
		this.pos=pos;
		this.previous=previous;
	}

	public ICoordinate getPos() {
		return pos;
	}

	public void setPos(ICoordinate pos) {
		this.pos = pos;
	}

	public Cellule getPrevious() {
		return previous;
	}

	public void setPrevious(Cellule previous) {
		this.previous = previous;
	}
	
	
}
