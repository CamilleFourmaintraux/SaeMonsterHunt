package fr.univlille.info.J2.main.management;

import java.io.Serializable;

import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;

public class SaveManagementData implements Serializable{

	private static final long serialVersionUID = -6622533595241810229L;

	/**
	 * Nom du thème associé à la partie
	 */
	private String theme;
	
	private boolean isSameScreen;
	
	public SaveManagementData(String theme, boolean isSameScreen) {
		this.theme=theme;
		this.isSameScreen=isSameScreen;
	}
	
	public String getTheme() {
		return this.theme;
	}
	protected void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isSameScreen() {
		return isSameScreen;
	}

	protected void setSameScreen(boolean isSameScreen) {
		this.isSameScreen = isSameScreen;
	}
	
	
}
