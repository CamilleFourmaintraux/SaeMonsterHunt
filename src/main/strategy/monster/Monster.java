package main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import main.maze.cells.ICoordinate;

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
	
	/* méthodes à déplacer dans maze
	public void move(ICoordinate newCoord) {
		this.coord=newCoord;
		this.notifyObservers(newCoord);
		this.monsterTurn=false;
	}*/
	
	/*public void actualizeShot(ICoordinate newCoord) {
		this.coord_hunted=newCoord;
		this.monsterTurn=true;
		this.notifyObservers();
	}*/
	
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
