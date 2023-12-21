package fr.univlille.info.J2.main.management.exit;

import java.io.Serializable;

public class SaveExitData implements Serializable{
	private static final long serialVersionUID = 4790349523153458010L;
	private int row;
	private int col;
	
	public SaveExitData(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
}
