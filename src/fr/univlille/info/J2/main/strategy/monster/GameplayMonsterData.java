package fr.univlille.info.J2.main.strategy.monster;

import java.io.Serializable;

public class GameplayMonsterData implements Serializable{
	private static final long serialVersionUID = -8494532638037902202L;
	
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
	
	public GameplayMonsterData(String name, String IA, boolean isVisionLimited, int visionRange, int movingRange) {
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
	protected boolean isVisionLimited() {
		return isVisionLimited;
	}
	
	/**
     * Obtient la portée de vision du monstre.
     * La portée de vision représente la distance maximale à laquelle le monstre peut voir.
     *
     * @return La portée de vision du monstre.
     */
	protected int getVisionRange() {
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
	
	protected String getName() {
		return this.name;
	}


	protected void setName(String name) {
		this.name = name;
	}


	protected void setVisionLimited(boolean isVisionLimited) {
		this.isVisionLimited = isVisionLimited;
	}


	protected void setVisionRange(int visionRange) {
		this.visionRange = visionRange;
	}


	protected void setMovingRange(int movingRange) {
		this.movingRange = movingRange;
	}


	protected void setIA(String iA) {
		IA = iA;
	}
	
	
}
