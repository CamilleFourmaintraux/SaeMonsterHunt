package fr.univlille.info.J2.main.strategy.monster.brain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

//import java.util.Queue;

public class AStar {
	int height;
	int width;
	boolean[][] visited;
	boolean[][] walls;
	Stack<Cellule> pile;
	ICoordinate posMonster;
	ICoordinate posExit;
	Cellule current;
	
	public AStar(boolean[][] walls) {
		height = walls.length;
		width = walls[0].length;
		this.walls=walls;
		posMonster = new Coordinate();
		posExit = new Coordinate();
		current = new Cellule(posMonster,null);
	}
	
	public List<ICoordinate> pathFind(ICoordinate newMonsterPos) {
		pile = new Stack<>();
		pile.add(current);
		visited = new boolean[height][width];
		setVisited(posMonster);
		while(!pile.isEmpty()) {
			if(pile.peek().equals(posExit)) {
				break;
			}else {
				ICoordinate thisWay = nextPath(pile.peek().getPos());
				if(thisWay!=null) {
					current=new Cellule(thisWay,pile.peek());
					pile.add(current);
					this.setVisited(thisWay);
				}else {
					pile.pop();
				}
			}
		}
		ArrayList<ICoordinate> result = new ArrayList<>();
		while(!pile.isEmpty()) {
			result.add(0,pile.pop().getPos());
		}
		return result;
	}
	
	public ICoordinate nextPath(int row, int col) {
		if (!isInvalidWay(row+1,col)){
			return new Coordinate(row+1,col);
		}else if (!isInvalidWay(row-1,col)){
			return new Coordinate(row-1,col);
		}else if (!isInvalidWay(row,col+1)){
			return new Coordinate(row,col+1);
		}else if (!isInvalidWay(row,col-1)){
			return new Coordinate(row,col-1);
		}
		return null;
	}
	
	public ICoordinate nextPath(ICoordinate coord) {
		return nextPath(coord.getRow(),coord.getCol());
	}
	
	public boolean isInvalidWay(int row, int col){
		try {
			return !isFloor(row,col);
		}catch(ArrayIndexOutOfBoundsException aioobe) {
			return true; //The path is invalid
		}
	}
	
	public boolean isFloor(int row, int col) throws ArrayIndexOutOfBoundsException {
		return walls[row][col];
	}
	
	public boolean isVisited(int row, int col) {
		return this.visited[row][col];
	}
	
	public void setVisited(ICoordinate pos) {
		this.visited[pos.getRow()][pos.getCol()]=true;
	}
	
	public void setMonsterPos(ICoordinate pos) {
		this.posMonster=pos;
	}
	
	public void setExitPos(ICoordinate pos) {
		this.posExit=pos;
	}
	
	public ICoordinate getMonsterPos() {
		return this.posMonster;
	}
}
