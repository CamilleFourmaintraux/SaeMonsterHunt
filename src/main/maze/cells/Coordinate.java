package main.maze.cells;

public class Coordinate implements ICoordinate{
	int row;
	int col;
	
	public Coordinate() {}

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return this.row;
	}

	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return this.col;
	}
	
	public void setCoordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	
	
	

}
