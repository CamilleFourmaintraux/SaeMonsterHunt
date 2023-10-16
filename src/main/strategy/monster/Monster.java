package main.strategy.monster;

import main.maze.cells.ICoordinate;
import main.utils.Subject;

public class Monster extends Subject implements IMonsterStrategy{
	protected boolean[][] walls;
	protected ICoordinate coord;
	protected ICoordinate coord_exit;
	protected ICoordinate coord_hunted;
	
	
	
	public Monster(boolean[][] walls,ICoordinate coord, ICoordinate coord_exit, ICoordinate coord_hunted) {
		super();
		this.initialize(walls);
		this.coord = coord;
		this.coord_exit = coord_exit;
		this.coord_hunted = coord_hunted;
	}
	@Override
	public ICoordinate play() {//move
		
		return null;
	}
	@Override
	public void update() {//update de "update strategy de IA" et non de "Observeur/Observé"
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
		
	}
	
	public void move(ICoordinate newCoord) {
		this.coord=newCoord;
		this.notifyObservers(newCoord);
	}
	
	public void actualizeShot(ICoordinate newCoord) {
		this.coord_hunted=newCoord;
		this.notifyObservers();
	}
}
