package main.maze;

public class Coordinate implements ICoordinate{
	int row;
	int col;

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

}
