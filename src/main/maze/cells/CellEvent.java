package main.maze.cells;

public class CellEvent implements ICellEvent{
	ICoordinate coord;
	int turn;
	CellInfo state;
	
	public CellEvent(ICoordinate coord, int turn) {
		this.coord = coord;
		this.state = null;
		this.turn = turn;
		
	}
	@Override
	public CellInfo getState() {
		return this.state;
	}
	@Override
	public int getTurn() {
		return this.turn;
	}
	@Override
	public ICoordinate getCoord() {
		return this.coord;
	}
}
