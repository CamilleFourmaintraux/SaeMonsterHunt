package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

/**
 * Cette classe représente les données de gameplay associées à un monstre dans le jeu.
 * Elle implémente l'interface Serializable pour permettre la sérialisation des objets.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class GameplayMonsterData implements Serializable{
	private static final long serialVersionUID = -8494532638037902202L;
	
	/**
	* String du nom du joueur
	**/
	private String name;
	
	/**
	 * Détermine si le monstre aura une vision limité dans la labyrinthe ou non.
	 */
	private boolean isVisionLimited;
	
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int visionRange;
	
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int movingRange;
	
	/**
	* String de la strategy du Monstre
	**/
	private String IA;
	
	/**
	 * Crée un objet pour stocker les données du monstre concernant les paramètres de gameplay.
	 * @param name				Nom du joueur associé à ce objet de données
	 * @param IA 				Niveau de l'IA du monstre.
     * @param isVisionLimited 	boolean indiquant si la vision du monstre est limitée.
     * @param visionRange 		int correspondant à la distance jusqu'où le monstre peut voir (seulement si limitedVision est True).
     * @param movingRange 		int correspondant à la portée de déplacement du monstre.
	 */
	public GameplayMonsterData(String name, String IA, boolean isVisionLimited, int visionRange, int movingRange) {
		this.IA=IA;
		this.isVisionLimited=isVisionLimited;
		this.visionRange=visionRange;
		this.movingRange=movingRange;
		this.name=name;
	}

	
	/**
    * Obtient de si la vision limité est activé ou non
    * La portée de déplacement représente la distance maximale que le monstre peut se déplacer en un tour.
    *
    * @return Le boolean du brouillard actif ou non.
    */
	public boolean isVisionLimited() {
		return isVisionLimited;
	}
	
	/**
     * Obtient la portée de vision du monstre.
     * La portée de vision représente la distance maximale à laquelle le monstre peut voir.
     *
     * @return La portée de vision du monstre.
     */
	public int getVisionRange() {
		return visionRange;
	}
	
	/**
     * Obtient la portée de déplacement du monstre.
     * La portée de déplacement représente la distance maximale que le monstre peut se déplacer en un tour.
     *
     * @return La portée de déplacement du monstre.
     */
	public int getMovingRange() {
		return movingRange;
	}

	/**
     * Obtient le nom de l'intelligence artificielle (IA) du monstre.
     *
     * @return Le nom de l'IA du monstre.
     */
	public String getIA() {
		return IA;
	}
	
	/**
     * Obtient le nom du joueur associé à cet objet de données.
     *
     * @return Le nom du joueur.
     */
	public String getName() {
		return this.name;
	}

	/**
     * Modifie le nom du joueur associé à cet objet de données.
     *
     * @param name Le nouveau nom du joueur.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Modifie l'indicateur de vision limitée du monstre.
     *
     * @param isVisionLimited true si la vision est limitée, sinon false.
     */
	public void setVisionLimited(boolean isVisionLimited) {
		this.isVisionLimited = isVisionLimited;
	}


    /**
     * Modifie la portée de vision du monstre.
     *
     * @param visionRange La nouvelle portée de vision du monstre.
     */
	public void setVisionRange(int visionRange) {
		this.visionRange = visionRange;
	}

	/**
     * Modifie la portée de déplacement du monstre.
     *
     * @param movingRange La nouvelle portée de déplacement du monstre.
     */
	public void setMovingRange(int movingRange) {
		this.movingRange = movingRange;
	}

	/**
     * Modifie le niveau de l'intelligence artificielle (IA) du monstre.
     *
     * @param iA Le nouveau niveau de l'IA du monstre.
     */
	public void setIA(String iA) {
		IA = iA;
	}
	
	
}
