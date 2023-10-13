package main.Strategy;

import main.maze.ICoordinate;

public class Monster implements IMonsterStrategy{
	private boolean[][] maze;
	private ICoordinate coord;
	private ICoordinate coord_exit;
	@Override
	public ICoordinate play() {//move
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update() {//update de "update strategy" et non de Observeur/Observé
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initialize(boolean[][] walls) {
		// TODO Auto-generated method stub
		
	}
	public void visualizeMaze() {
		
	}
}
