package fr.univlille.info.J2.main.management;

import java.io.Serializable;

public class SaveManagementData implements Serializable{

	private static final long serialVersionUID = -6622533595241810229L;

	/**
	 * Nom du thème associé à la partie
	 */
	private String theme;
	
	/**
	 * Mode de vue du jeu true->Ecran partagé sinon écran scindé.
	 */
	private boolean isSameScreen;
	
<<<<<<< HEAD
	/**
	 * Constructeur de la sauvegarde de Management.
	 * 
	 * @param theme			Nom du thème de la partie.
	 * @param isSameScreen	Mode de vue du jeu.
	 */
	public SaveManagementData(String theme, boolean isSameScreen) {
=======
	private boolean areAudioActivated;
	
	public SaveManagementData(String theme, boolean isSameScreen, boolean areAudioActivated) {
>>>>>>> master
		this.theme=theme;
		this.isSameScreen=isSameScreen;
		this.areAudioActivated=areAudioActivated;
	}
	
	/**
	 * Récupère le thème de la partie à sauvegarder.
	 * 
	 * @return le nom du thème.
	 */
	public String getTheme() {
		return this.theme;
	}
	
	/**
	 * Définit le thème à partir du nom de celui-ci
	 * 
	 * @param theme le nom du thème.
	 */
	protected void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Retourne le mode de vue du jeu.
	 * 
	 * @return boolean true->Ecran partagé sinon écran scindé.
	 */
	public boolean isSameScreen() {
		return isSameScreen;
	}
	
	public boolean isAudioActivated() {
		return areAudioActivated;
	}

	/**
	 * Définit le mode de vue du jeu.
	 * 
	 * @param isSameScreen mode de vue.
	 */
	protected void setSameScreen(boolean isSameScreen) {
		this.isSameScreen = isSameScreen;
	}
	
	protected void setAudioActivated (boolean areAudioActivated) {
		this.areAudioActivated = areAudioActivated;
	}
	
	
}
