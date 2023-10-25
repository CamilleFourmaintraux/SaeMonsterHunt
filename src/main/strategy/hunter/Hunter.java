package main.strategy.hunter;

import main.maze.cells.ICoordinate;

public class Hunter implements IHunterStrategy{
	public int[][] traces;
	public ICoordinate coord;
	
	public Hunter(int height, int width, ICoordinate coord_hunter) {
		this.coord=coord_hunter;
		this.initialize(height, width);
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
				traces[h][l]=-2;// -2 -> Inexploré, -1 -> Mur, 0 -> pas de trace >0 -> trace (tour)
			}
		}
		
	}
	
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	
	public int getTrace(ICoordinate c) {
		return this.traces[c.getRow()][c.getCol()];
	}
	
	//Métodes à placer dans Maze
	/*public void shoot(ICoordinate newCoord) {
		this.coord=newCoord;
		this.monsterTurn=true;
		this.notifyObservers(newCoord);
	}
	
	public void actualizeTraces(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
		this.notifyObservers();
	}*/
	
	public int getRow() {
		return this.coord.getRow();
	}
	
	public int getCol() {
		return this.coord.getCol();
	}
	public ICoordinate getCoord() {
		return this.coord;
	}
	
	public void setCoord(ICoordinate c) {
		this.coord=c;
	}
}
