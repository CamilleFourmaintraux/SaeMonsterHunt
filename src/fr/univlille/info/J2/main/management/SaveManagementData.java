package fr.univlille.info.J2.main.management;

import java.io.Serializable;

public class SaveManagementData implements Serializable{

	private static final long serialVersionUID = -6622533595241810229L;

	/**
	 * Nom du thème associé à la partie
	 */
	private String theme;
	
	private boolean isSameScreen;
	
	private boolean areAudioActivated;
	
	public SaveManagementData(String theme, boolean isSameScreen, boolean areAudioActivated) {
		this.theme=theme;
		this.isSameScreen=isSameScreen;
		this.areAudioActivated=areAudioActivated;
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
	
	public boolean isAudioActivated() {
		return areAudioActivated;
	}

	protected void setSameScreen(boolean isSameScreen) {
		this.isSameScreen = isSameScreen;
	}
	
	protected void setAudioActivated (boolean areAudioActivated) {
		this.areAudioActivated = areAudioActivated;
	}
	
	
}
