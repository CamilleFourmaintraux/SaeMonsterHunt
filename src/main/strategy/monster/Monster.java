package main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import main.maze.cells.Coordinate;
import main.utils.Utils;

public class Monster implements IMonsterStrategy{
	public boolean[][] walls;
	public ICoordinate coord;
	public String IA_level;
	
	
	public Monster(boolean[][] walls,ICoordinate coord, String IA_level) {
		super();
		this.initialize(walls);
		this.coord = coord;
		this.IA_level=IA_level;
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
	
	public ICoordinate easy_IA_action() {
		int x = this.getCol()+(Utils.random.nextInt(3)-1);
		int y = this.getRow()+(Utils.random.nextInt(3)-1);
		return new Coordinate(y,x);
	}
	
	public ICoordinate moderate_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	public ICoordinate hardcore_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	@Override
	public ICoordinate play() {
		// TODO Auto-generated method stub
		return this.easy_IA_action();
	}
	@Override
	public void update(ICellEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
