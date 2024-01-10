package fr.univlille.info.J2.main.management;

import java.io.Serializable;

/**
 * La classe SaveManagementData représente les données de gestion de sauvegarde.
 * Ces données incluent le thème du jeu, l'indication si l'écran est partagé, et l'état d'activation audio.
 * Les instances de cette classe sont sérialisables pour faciliter la sauvegarde et la restauration.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveManagementData implements Serializable{

	private static final long serialVersionUID = -6622533595241810229L;

	/**
     * Le thème actuel du jeu.
     */
	private String theme;
	
	/**
     * Indique si le jeu est en mode écran partagé.
     */
	private boolean isSameScreen;
	
	/**
     * Indique si le son est activé dans le jeu.
     */
	private boolean areAudioActivated;
	
	/**
     * Constructeur de la classe SaveManagementData.
     * 
     * @param theme               Le thème du jeu.
     * @param isSameScreen        Indique si l'écran est partagé.
     * @param areAudioActivated   Indique si l'audio est activé.
     */
	public SaveManagementData(String theme, boolean isSameScreen, boolean areAudioActivated) {
		this.theme=theme;
		this.isSameScreen=isSameScreen;
		this.areAudioActivated=areAudioActivated;
	}
	
	/**
     * Récupère le thème actuel du jeu.
     * 
     * @return Le thème du jeu.
     */
	public String getTheme() {
		return this.theme;
	}
	
	/**
     * Modifie le thème du jeu.
     * 
     * @param theme Le nouveau thème du jeu.
     */
	protected void setTheme(String theme) {
		this.theme = theme;
	}

	/**
     * Vérifie si le jeu est en mode écran partagé.
     * 
     * @return true si l'écran est partagé, sinon false.
     */
	public boolean isSameScreen() {
		return isSameScreen;
	}
	
	/**
     * Vérifie si le son est activé dans le jeu.
     * 
     * @return true si le son est activé, sinon false.
     */
	public boolean isAudioActivated() {
		return areAudioActivated;
	}

	/**
     * Modifie l'indicateur d'écran partagé.
     * 
     * @param isSameScreen Nouvel état d'écran partagé.
     */
	protected void setSameScreen(boolean isSameScreen) {
		this.isSameScreen = isSameScreen;
	}
	
	/**
     * Modifie l'indicateur d'activation audio.
     * 
     * @param areAudioActivated Nouvel état d'activation audio.
     */
	protected void setAudioActivated (boolean areAudioActivated) {
		this.areAudioActivated = areAudioActivated;
	}
	
	
}
