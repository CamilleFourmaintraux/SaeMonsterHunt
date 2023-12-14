package fr.univlille.info.J2.main.strategy.hunter;

import java.io.Serializable;

public class SaveHunterData implements Serializable{
	private static final long serialVersionUID = -8756361376167378970L;
	private GameplayHunterData dataH;
	private int[][] tracesH;
	private int rowH;
	private int colH;
	
	public SaveHunterData(GameplayHunterData dataH, int[][] tracesH, int rowH, int colH) {
		this.dataH = dataH;
		this.tracesH = tracesH;
		this.rowH = rowH;
		this.colH = colH;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public GameplayHunterData getDataH() {
		return dataH;
	}
	public int[][] getTracesH() {
		return tracesH;
	}
	public int getRowH() {
		return rowH;
	}
	public int getColH() {
		return colH;
	}
	
}
