package main.maze.cells;

public interface ICellEvent {
	public CellInfo getState();
	public int getTurn();
	public ICoordinate getCoord();
	public enum CellInfo {
		EMPTY, WALL, MONSTER, HUNTER, EXIT
	}
	
}
