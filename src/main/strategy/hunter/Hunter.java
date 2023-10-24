package main.strategy.hunter;

import main.maze.cells.ICoordinate;
import main.utils.Subject;
import main.maze.cells.Coordinate;

public class Hunter extends Subject implements IHunterStrategy{
	protected int[][] traces;
	protected ICoordinate lastShot;
	protected boolean monsterTurn;
	
	public Hunter(int height, int width, ICoordinate coord_hunter) {
		this.lastShot=coord_hunter;
		this.initialize(height, width);
		this.monsterTurn=true;
	}
	
	public Hunter() {
		this.lastShot=new Coordinate();
	}
	
	@Override
	public ICoordinate play() {//shot
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initialize(int nbrRows, int nbrCols) {
		this.traces=new int[nbrRows][nbrCols];
		for(int h=0; h<this.traces.length;h++) {
			for(int l=0; l<this.traces[h].length;l++) {
				traces[h][l]=0;
			}
		}
		
	}
	public void shoot(ICoordinate newCoord) {
		this.lastShot=newCoord;
		this.monsterTurn=true;
		this.notifyObservers(newCoord);
	}
	
	public int[][] getTraces() {
		return traces;
	}

	public void actualizeTraces(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
		this.notifyObservers();
	}
	
	public void setMonsterTurn(boolean b) {
		this.monsterTurn=b;
	}
	
	public int getRow() {
		return this.lastShot.getRow();
	}
	
	public int getCol() {
		return this.lastShot.getCol();
	}
	public ICoordinate getCoord() {
		return this.lastShot;
	}

	public boolean isMonsterTurn() {
		return monsterTurn;
	}
}
