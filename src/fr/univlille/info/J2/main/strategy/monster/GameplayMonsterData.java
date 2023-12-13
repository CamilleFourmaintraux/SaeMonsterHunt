package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

public class GameplayMonsterData implements Serializable{
	private static final long serialVersionUID = -8494532638037902202L;
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int visionRange;
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int movingRange;
	/**
	 * Détermine si le monstre aura une vision limité dans la labyrinthe ou non.
	 */
	private boolean isVisionLimited;
	/**
	* String de la strategy du Monstre
	**/
	private String IA;
	
	public GameplayMonsterData(String IA, boolean isVisionLimited, int visionRange, int movingRange) {
		this.IA=IA;
		this.isVisionLimited=isVisionLimited;
		this.visionRange=visionRange;
		this.movingRange=movingRange;
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
	protected int getMovingRange() {
		return movingRange;
	}

	/**
     * Obtient le nom de l'intelligence artificielle (IA) du monstre.
     *
     * @return Le nom de l'IA du monstre.
     */
	protected String getIA() {
		return IA;
	}
}
