package main.Strategy;

import main.maze.ICoordinate;

public interface IStrategy {
	public ICoordinate play();
	public void update();
}
