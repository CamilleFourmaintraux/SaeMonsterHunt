package fr.univlille.info.J2.main.application.system;

import java.io.Serializable;

import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;

public class SaveMonsterData implements Serializable{
	private static final long serialVersionUID = 522923773838437211L;
	private GameplayMonsterData dataM;
	private boolean[][] exploredM;
	private boolean[][] wallsM;
	private int rowM;
	private int colM;
	private String name;
	
	public SaveMonsterData(GameplayMonsterData dataM, boolean[][] exploredM, boolean[][] wallsM, int rowM, int colM) {
		this.dataM = dataM;
		this.exploredM = exploredM;
		this.wallsM = wallsM;
		this.rowM = rowM;
		this.colM = colM;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GameplayMonsterData getDataM() {
		return dataM;
	}

	public boolean[][] getExploredM() {
		return exploredM;
	}

	public boolean[][] getWallsM() {
		return wallsM;
	}

	public int getRowM() {
		return rowM;
	}

	public int getColM() {
		return colM;
	}
	
	public String getName() {
		return this.name;
	}
	
}
