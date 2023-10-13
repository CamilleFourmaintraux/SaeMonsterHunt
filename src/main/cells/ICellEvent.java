package main.cells;

import main.maze.ICoordinate;

public interface ICellEvent {
	public CellInfo getState();
	public int getTurn();
	public ICoordinate getCoord();
	public enum CellInfo {
		EMPTY, WALL, MONSTER, HUNTER, EXIT
	}
	
}
