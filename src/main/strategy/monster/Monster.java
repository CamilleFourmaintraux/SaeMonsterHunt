package main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Monster implements IMonsterStrategy{
	public boolean[][] walls;
	public ICoordinate coord;
	
	
	public Monster(boolean[][] walls,ICoordinate coord) {
		super();
		this.initialize(walls);
		this.coord = coord;
	}
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
	}
	
	// Getters & Setters
	
	public int getRow() {
		return this.coord.getRow();
	}
	
	public int getCol() {
		return this.coord.getCol();
	}
	
	public ICoordinate getCoord() {
		return this.coord;
	}

	public void setCoord(ICoordinate c) {
		this.coord=c;
	}

	public boolean[][] getWalls() {
		return walls;
	}
	
	@Override
	public fr.univlille.iutinfo.cam.player.perception.ICoordinate play() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(ICellEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
