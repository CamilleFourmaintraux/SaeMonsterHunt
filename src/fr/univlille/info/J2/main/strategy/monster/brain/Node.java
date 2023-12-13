package fr.univlille.info.J2.main.strategy.monster.brain;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Node {
    private ICoordinate coord;
    private boolean traversable;
    private int costStartToThis; //g
    private int costThisToEnd; //h
    private int costTotal; //f
    private Node previous;
    private boolean marking;

    public Node(int row, int col, boolean traversable) {
        this.coord=new Coordinate(row,col);
        this.traversable = traversable;
        this.costStartToThis = Integer.MAX_VALUE; //On initie à +infini
        this.costThisToEnd = 0; 
        this.costTotal = 0;
        this.previous = null;
    }

	public int getRow() {
		return this.coord.getRow();
	}

	public int getCol() {
		return this.coord.getCol();
	}

	public boolean isTraversable() {
		return traversable;
	}

	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}

	public int getCostStartToThis() {
		return costStartToThis;
	}

	public void setCostStartToThis(int costStartToThis) {
		this.costStartToThis = costStartToThis;
	}

	public int getCostThisToEnd() {
		return costThisToEnd;
	}

	public void setCostThisToEnd(int costThisToEnd) {
		this.costThisToEnd = costThisToEnd;
	}

	public int getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(int costTotal) {
		this.costTotal = costTotal;
	}

	public Node getPrevious() {
		return this.previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	public ICoordinate getCoord() {
		return this.coord;
	}
	
	public void setCoord(ICoordinate newCoord) {
		this.coord=newCoord;
	}

	public boolean isMarking() {
		return marking;
	}

	public void setMarking(boolean marking) {
		this.marking = marking;
	}
    
}
