package main.strategy;

import main.maze.cells.ICoordinate;

public interface IStrategy {
	public ICoordinate play();
	public void update();
}
