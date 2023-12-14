package fr.univlille.info.J2.main.application.system;

import java.io.Serializable;

public class SaveMazeData implements Serializable{
	
	private static final long serialVersionUID = -6244168822609852642L;
	private boolean[][] maze;
	private int[][] traces;
	private int turn;
	private boolean isMonsterTurn;
	private String theme;
	
	public SaveMazeData(boolean[][] maze, int[][] traces, int turn, boolean isMonsterTurn, String theme) {
		this.maze = maze;
		this.traces = traces;
		this.turn = turn;
		this.isMonsterTurn = isMonsterTurn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean[][] getMaze() {
		return maze;
	}

	public int[][] getTraces() {
		return traces;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isMonsterTurn() {
		return isMonsterTurn;
	}
	
}
