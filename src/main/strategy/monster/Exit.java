package main.strategy.monster;

import main.maze.cells.ICoordinate;

public class Exit {
	ICoordinate coord;
	public Exit(ICoordinate coord) {
		this.coord=coord;
	}
	public int getRow() {
		return this.coord.getRow();
	}
	
	public int getCol() {
		return this.coord.getCol();
	}
	
	public ICoordinate getCoord() {
		return this.coord;
	}
}

