package fr.univlille.info.J2.main.application.system;

import java.io.Serializable;

public class SaveExitData implements Serializable{
	private static final long serialVersionUID = 4790349523153458010L;
	private int rowE;
	private int colE;
	
	public SaveExitData(int rowE, int colE) {
		this.rowE = rowE;
		this.colE = colE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getRowE() {
		return rowE;
	}

	public int getColE() {
		return colE;
	}
	
}
