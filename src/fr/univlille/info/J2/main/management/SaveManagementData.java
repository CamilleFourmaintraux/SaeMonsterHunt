package fr.univlille.info.J2.main.management;

import java.io.Serializable;

public class SaveManagementData implements Serializable{

	private static final long serialVersionUID = -6622533595241810229L;

	/**
	 * Nom du thème associé à la partie
	 */
	private String theme;
	
	boolean splitScreen;
	
	public SaveManagementData() {
		
	}
	
	public String getTheme() {
		return this.theme;
	}
	protected void setTheme(String theme) {
		this.theme = theme;
	}
}
